package firewoody.learnspring.database.dbaccess.springtransaction;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class InternalCallV1Test {

    @Autowired
    CallService callService;

    @Test
    void printProxy() {
        log.info("callService class={}", callService.getClass());
    }

    @Test
    void internalCall() {
        // 프록시가 실제 객체를 호출하며, 큰 이상이 없다.
        callService.internal();
    }

    @Test
    void externalCall() {
        // 트랜잭션이 없는 external이 트랜잭션이 있는 internal을 호출해서 트랜잭션이 적용될 것같지만 적용되지 않는다.
        // 1. external은 트랜잭션이 없기에, 프록시는 트랜잭션이 없는 상태로 실제 external을 호출하게 된다.
        // 2. 여기서 internal의 프록시가 아닌, 자기 자신의 internal을 호출하게 된다. (this.로 자기자신을 호출하기 때문)
        callService.external();
    }

    @TestConfiguration
    static class InternalCallV1Config {
        @Bean
        CallService callService() {
            return new CallService();
        }
    }

    @Slf4j
    static class CallService {
        // 트랜잭션이 하나라도 있으면 트랜잭션 프록시 객체가 만들어진다.

        public void external() {
            log.info("call external");
            printTxInfo();
            internal();
        }

        @Transactional
        public void internal() {
            log.info("call internal");
            printTxInfo();
        }

        private void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active={}", txActive);
        }
    }
}
