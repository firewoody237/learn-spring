package firewoody.learnspring.advanced.app.caution;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// 자기 자신을 의존관계로 주입 받는 방법

@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 callServiceV1;

    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        // callServiceV1에는 프록시가 주입된다.
        // 생성자 방식은 순환 오류가 나타나므로 수정자를 사용해야 한다.
        this.callServiceV1 = callServiceV1;
    }

    @Test
    void external() {
        log.info("call external");
        // 프록시를 통해 AOP를 호출하게 된다.
        callServiceV1.external();
    }

    @Test
    void internal() {
        log.info("call internal");
    }
}
