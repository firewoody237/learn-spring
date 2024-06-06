package firewoody.learnspring.advanced.app.caution;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InternalService {

    public void internal() {
        log.info("call internal");
    }
}
