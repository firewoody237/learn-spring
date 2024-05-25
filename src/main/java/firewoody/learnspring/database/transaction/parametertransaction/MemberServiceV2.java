package firewoody.learnspring.database.transaction.parametertransaction;

// 트랜잭션이 없는 버전 -> 세션을 유지하기 위해 파라미터로 트랜잭션을 건내는 버전
// 트랜잭션을 적용할 수 있지만, 서비스 계층이 지저분해지고 코드가 복잡 해 진다.
// 테스트 코드 존재

// 문제 사항
// 1. 트랜잭션 문제
//      서비스계층에 JDBC 구현 누수
//      트랜잭션 동기화 문제 (파라미터로 넘겨야 함)
//      트랜잭션 적용 반복 문제
// 2. 예외 누수
// 3. JDBC 반복 문제

import firewoody.learnspring.database.jdbc.connection.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection con = dataSource.getConnection();
        try {
            con.setAutoCommit(false); // 트랜잭션 시작
            bizLogic(con, fromId, toId, money);
            con.commit();
        } catch (Exception e) {
            con.rollback(); // 실패시 롤백
            throw new IllegalStateException(e);
        } finally {
            release(con);
        }
    }

    private void bizLogic(Connection con, String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(con, fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(con, toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }

    private void release(Connection con) {
        if (con != null) {
            try {
                con.setAutoCommit(true);
                con.close();
            } catch (Exception e) {
                log.info("error", e);
            }
        }
    }
}
