package firewoody.learnspring.advanced.app.caution;

import firewoody.learnspring.advanced.app.pointcut.MemberService;
import firewoody.learnspring.advanced.app.pointcut.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        // 이렇게 하더라도 JDK 동적 프록시는 인터페이스 기반으로 만듦
        // 따라서 Impl로 캐스팅하면 문제 발생

        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false);

        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        log.info("proxy class={}", memberServiceProxy.getClass());

        Assertions.assertThatThrownBy(() -> {
            MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        }).isInstanceOf(ClassCastException.class);
    }

    @Test
    void cglibProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        // 구체 클래스 타입 기반이므로 캐스팅이 가능하다.

        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);

        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        log.info("proxy class={}", memberServiceProxy.getClass());

        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
    }
}
