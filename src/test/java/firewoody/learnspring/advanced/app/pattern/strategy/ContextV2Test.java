package firewoody.learnspring.advanced.app.pattern.strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV2Test {

    @Test
    void strategytV1() {
        // 실행할 때마다 전략을 인수로 전달하는 방법

        ContextV2 contextV2 = new ContextV2();
        contextV2.execute(new StrategyLogic1());
        contextV2.execute(new StrategyLogic2());

        contextV2.execute(new Strategy() {
            @Override
            public void call() {
                log.info("익명함수 비즈니스 로직");
            }
        });
    }
}
