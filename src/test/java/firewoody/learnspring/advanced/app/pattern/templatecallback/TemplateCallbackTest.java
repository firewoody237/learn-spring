package firewoody.learnspring.advanced.app.pattern.templatecallback;

import firewoody.learnspring.advanced.app.pattern.strategy.ContextV2;
import firewoody.learnspring.advanced.app.pattern.strategy.Strategy;
import firewoody.learnspring.advanced.app.pattern.strategy.StrategyLogic1;
import firewoody.learnspring.advanced.app.pattern.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateCallbackTest {

    @Test
    void callbackV1() {
        TimeLogTemplate template = new TimeLogTemplate();

        template.execute(new Callback() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        });

        template.execute(() -> log.info("비즈니스 로직 실행 : 람다"));
    }
}
