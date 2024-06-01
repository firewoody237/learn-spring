package firewoody.learnspring.advanced.app.pattern.strategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextV2 {

    // 먼저 조립하고 사용하는 방식보다 더 유연하게 전략 패턴을 사용

    public void execute(Strategy strategy) {
        long startTime = System.currentTimeMillis();
        strategy.call();
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}
