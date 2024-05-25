package firewoody.learnspring.database.transaction.transactionmanager;

// 트랜잭션 매니저 사용 (커넥션을 파라미터로 넘기지 않아도 됨) + 템플릿 콜백 패턴 사용(반복되는 패턴 제거)
// 테스트 코드 존재

// 플로우
// 1. 서비스 계층에서 `transactionManager.getTransaction()`으로 트랜잭션을 시작한다.
// 2. 트랜잭션 매니저는 내부에서 데이터소스를 사용해서 커넥션을 생셔ㅓㅇ한다.
// 3. 커넥션을 수동 커밋 모드로 변경해서 데이터베이스 트랜잭션을 시작한다.
// 4. 커넥션을 "트랜잭션 동기화 매니저"에 보관한다. (멀티 스레드에 안전)
// 5. 서비스는 비즈니스 로직을 실행하면서 리포지토리의 메서드를 호출하고, `DataSourceUtils.getConnection()`을 사용해 "트랜잭션 동기화 매니저"에 보관된 커넥션을 꺼내 사용한다.
// 6. 비즈니스 로직이 끝나고, 트랜잭션을 종료하기 위해 "트랜잭션 매니저"는 "트랜잭션 동기화 매니저"를 통해 커넥션을 획득한다.
// 7. 획득한 커넥션으로 데이터베이스에 커밋하거나 롤백한다.
// 8. 전체 리소스를 정리한다. (트랜잭션 동기화 매니저, 쓰레드 로컬, 트랜잭션 설정, 커넥션 종료 등)

// 스프링은 `TransactionTemplate`이라는 템플릿 클래스를 제공한다.

import firewoody.learnspring.database.jdbc.connection.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;

@Slf4j
public class MemberServiceV3_2 {

    private final TransactionTemplate txTemplate;
    private final MemberRepositoryV3 memberRepository;

    public MemberServiceV3_2(PlatformTransactionManager transactionManager, MemberRepositoryV3 memberRepository) {
        this.txTemplate = new TransactionTemplate(transactionManager);
        this.memberRepository = memberRepository;
    }

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        txTemplate.executeWithoutResult((status) -> {
            try {
                bizLogic(fromId, toId, money);
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    private void bizLogic(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
}
