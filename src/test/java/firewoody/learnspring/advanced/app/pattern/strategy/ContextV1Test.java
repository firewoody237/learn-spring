package firewoody.learnspring.advanced.app.pattern.strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {

    @Test
    void strategytV0() {
        logic1();
        logic2();
    }

    @Test
    void starteV1() {
        Strategy strategy1 = new StrategyLogic1();
        ContextV1 contextV1 = new ContextV1(strategy1);
        contextV1.execute();

        Strategy strategy2 = new StrategyLogic2();
        ContextV1 contextV2 = new ContextV1(strategy2);
        contextV2.execute();
    }

    @Test
    void starteV2() {
        Strategy strategy1 = () -> log.info("비즈니스 로직1 실행");
        log.info("strategyLogic1={}", strategy1.getClass());
        ContextV1 contextV1 = new ContextV1(strategy1);
        contextV1.execute();

        Strategy strategy2 = () -> log.info("비즈니스 로직1 실행");
        log.info("strategyLogic2={}", strategy2.getClass());
        ContextV1 contextV2 = new ContextV1(strategy2);
        contextV2.execute();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();
        log.info("비즈니스 로직1 실행");
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();
        log.info("비즈니스 로직2 실행");
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}
