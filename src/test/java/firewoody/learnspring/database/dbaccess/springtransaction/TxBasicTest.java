package firewoody.learnspring.database.dbaccess.springtransaction;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class TxBasicTest {

    @Autowired
    BasicService basicService;

    @Test
    void proxyCheck() {
        // `@Transactional`이 메서드나 클래스에 하나라도 있으면 실제 객체 대신 프록시가 주입된다.$
        log.info("aop class={}", basicService.getClass());
        assertThat(AopUtils.isAopProxy(basicService)).isTrue();
    }

    @Test
    void txTest() {
        basicService.tx();
        basicService.nonTx();
    }

    @TestConfiguration
    static class TxApplyBasicConfig {
        @Bean
        BasicService basicService() {
            return new BasicService();
        }
    }

    @Slf4j
    static class BasicService {

        @Transactional
        public void tx() {
            // 1. 프록시는 tx()메서드가 트랜잭션을 사용할 수 있는지 확인한다. (`@Transactional` 존재)
            // 2. 트랜잭션을 시작한 후 basicService.tx()를 호출한다.
            // 3. 제어가 프록시로 돌아오면, 프록시는 커밋하거나 롤백하여 트랜잭션을 종료한다.
            log.info("call tx");
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active={}", txActive);
        }

        public void nonTx() {
            // 1. 프록시는 tx()메서드가 트랜잭션을 사용할 수 있는지 확인한다. (`@Transactional` 미존재)
            // 2. 트랜잭션을 시작하지 않고 basicService.nonTx()를 호출하고 종료한다.
            log.info("call nonTx");
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive(); // 현재 쓰레드에 트랜잭션이 적용되어있는지 확인할 수 있다.
            log.info("tx active={}", txActive);
        }
    }
}
