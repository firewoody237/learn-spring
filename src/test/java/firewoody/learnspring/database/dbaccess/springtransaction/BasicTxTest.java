package firewoody.learnspring.database.dbaccess.springtransaction;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import javax.sql.DataSource;

@Slf4j
@SpringBootTest
public class BasicTxTest {

    @Autowired
    PlatformTransactionManager txManager;

    @TestConfiguration
    static class Config {
        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }

    @Test
    void commit() {
        log.info("트랜잭션 시작");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("트랜잭션 커밋 시작");
        txManager.commit(status);
        log.info("트랜잭션 커밋 완료");
    }

    @Test
    void rollback() {
        log.info("트랜잭션 시작");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("트랜잭션 롤백 시작");
        txManager.rollback(status);
        log.info("트랜잭션 롤백 완료");
    }

    // 트랜잭션 두 번 사용
    @Test
    void double_commit() {
        // 히카리 커넥션 풀은 내부 관리를 위해 "히카리 프록시 커넥션"이라는 객체를 생성해서 반환한다. 이를 통해 커넥션을 구분할 수 있다. ex. HikariProxyConnection@744077812
        // 아래와 같은 경우, 트랜잭션이 각각 수행되면서 사용되는 DB 커넧니 다르다.
        // 이 경우 트랜잭션을 묶을 수 없다.
        log.info("트랜잭션1 시작");
        TransactionStatus tx1 = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션1 커밋");
        txManager.commit(tx1);

        log.info("트랜잭션2 시작");
        TransactionStatus tx2 = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션2 커밋");
        txManager.commit(tx2);
    }

    // 트랜잭션 전파 기본
    @Test
    void inner_commit() {
        // 내부 트랜잭션이 외부 트랜잭션에 참여한다.
        log.info("외부 트랜잭션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("outer.isNewTransaction() = {}", outer.isNewTransaction()); // true

        log.info("내부 트랜잭션 시작");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionAttribute()); // 기존 트랜잭션에 참여한다.
        log.info("inner.isNewTransaction() = {}", inner.isNewTransaction()); // false
        txManager.commit(inner);

        log.info("외부 트랜잭션 커밋");
        txManager.commit(outer);
    }

    // 트랜잭션 전파 외부 롤백
    @Test
    void outer_rollback() {
        // 외부 트랜잭션이 롤백됨으로써, 물리 트랜잭션이 롤백된다.
        // 따라서 외부, 내부 모두 롤백된다.
        log.info("외부 트랜잭션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("내부 트랜잭션 시작");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionAttribute()); // 기존 트랜잭션에 참여한다.
        log.info("내부 트랜잭션 커밋");
        txManager.commit(inner);

        log.info("외부 트랜잭션 롤백");
        txManager.rollback(outer);
    }

    // 트랜잭션 전파 내부 롤백
    @Test
    void inner_rollback() {
        // 내부 트랜잭션이 롤백됨으로써, 물리 트랜잭션이 롤백된다.
        // 따라서 외부, 내부 모두 롤백된다.
        log.info("외부 트랜잭션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("내부 트랜잭션 시작");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionAttribute()); // 기존 트랜잭션에 참여한다.
        log.info("내부 트랜잭션 롤백");
        txManager.rollback(inner);

        log.info("외부 트랜잭션 커밋");
        Assertions.assertThatThrownBy(() -> txManager.commit(outer))
                .isInstanceOf(UnexpectedRollbackException.class);
    }

    // REQUIRES_NEW
    @Test
    void inner_rollback_requires_new() {
        // 내부 트랜잭션과 외부 트랜잭션이 상관관계가 없다.
        log.info("외부 트랜잭션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("outer.isNewTransaction() = {}", outer.isNewTransaction()); // true

        log.info("내부 트랜잭션 시작");
        DefaultTransactionAttribute definition = new DefaultTransactionAttribute();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus inner = txManager.getTransaction(definition);
        log.info("outer.isNewTransaction() = {}", outer.isNewTransaction()); // true

        log.info("내부 트랜잭션 롤백");
        txManager.rollback(inner);

        log.info("외부 트랜잭션 커밋");
        txManager.commit(outer);
    }
}
