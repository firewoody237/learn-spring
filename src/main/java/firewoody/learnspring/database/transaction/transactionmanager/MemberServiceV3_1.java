package firewoody.learnspring.database.transaction.transactionmanager;

// 트랜잭션 매니저 사용 (커넥션을 파라미터로 넘기지 않아도 됨)
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

import firewoody.learnspring.database.jdbc.connection.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.SQLException;

@Slf4j
@RequiredArgsConstructor
public class MemberServiceV3_1 {

    // JDBC 기술을 사용하기에 DataSourceTransactionManager 구현체를 주입받는다.
    private final PlatformTransactionManager transactionManager;
    private final MemberRepositoryV3 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        // DefaultTransactionDefinition은 트랜잭션 관련 옵션을 지정할 수 있다.
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            bizLogic(fromId, toId, money);
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status); // 실패시 롤백
            throw new IllegalStateException(e);
        }
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
