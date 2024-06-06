package firewoody.learnspring.advanced.app.caution;

import firewoody.learnspring.advanced.app.pointcut.MemberService;
import firewoody.learnspring.advanced.app.pointcut.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"})
@Import(ProxyDIAspect.class)
public class ProxyDITest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberServiceImpl memberServiceImpl;

    @Test
    void go() {
        log.info("memberService class={}", memberService.getClass());

        // MemberService 인터페이스 기반으로 만들어지므로 MemberServiceImpl 타입이 뭔지 전혀 모른다.
        // 그래서 해당 타입에 주입할 수없다. memberServiceImpl = JDK Proxy가 성립하지 않는다.(캐스팅 불가)
        log.info("memberServiceImpl class={}", memberServiceImpl.getClass());
        memberService.hello("hello");
    }
}
