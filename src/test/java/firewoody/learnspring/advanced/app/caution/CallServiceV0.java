package firewoody.learnspring.advanced.app.caution;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV0 {

    public void external() {
        log.info("call external");
        internal(); // 이 경우 this가 붙어 프록시를 호출하지 않는다.
    }

    public void internal() {
        log.info("call internal");
    }
}
