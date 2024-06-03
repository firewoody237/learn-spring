package firewoody.learnspring.advanced.app.proxydecorator.v2.proxyfactory;

import firewoody.learnspring.advanced.app.logproject.logtrace.LogTrace;
import firewoody.learnspring.advanced.app.proxydecorator.v1.*;
import firewoody.learnspring.advanced.app.proxydecorator.v1.proxyfactory.LogTraceAdvice;
import firewoody.learnspring.advanced.app.proxydecorator.v2.OrderControllerV2;
import firewoody.learnspring.advanced.app.proxydecorator.v2.OrderRepositoryV2;
import firewoody.learnspring.advanced.app.proxydecorator.v2.OrderServiceV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;

@Slf4j
//@Configuration
public class ProxyFactoryConfigV2 {

    @Bean(name = "proxyOrderControllerV2")
    public OrderControllerV2 orderControllerV1(LogTrace logTrace) {
        OrderControllerV2 orderController = new OrderControllerV2(orderServiceV1(logTrace));

        ProxyFactory factory = new ProxyFactory(orderController);
        factory.addAdvisor(getAdvisor(logTrace));
        OrderControllerV2 proxy = (OrderControllerV2) factory.getProxy();
        log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), orderController.getClass());
        return proxy;
    }

    @Bean(name = "proxyOrderServiceV2")
    public OrderServiceV2 orderServiceV1(LogTrace logTrace) {
        OrderServiceV2 orderService = new OrderServiceV2(orderRepositoryV1(logTrace));

        ProxyFactory factory = new ProxyFactory(orderService);
        factory.addAdvisor(getAdvisor(logTrace));
        OrderServiceV2 proxy = (OrderServiceV2) factory.getProxy();
        log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), orderService.getClass());
        return proxy;
    }

    @Bean(name = "proxyOrderRepositoryV2")
    public OrderRepositoryV2 orderRepositoryV1(LogTrace logTrace) {
        OrderRepositoryV2 orderRepository = new OrderRepositoryV2();

        ProxyFactory factory = new ProxyFactory(orderRepository);
        factory.addAdvisor(getAdvisor(logTrace));
        OrderRepositoryV2 proxy = (OrderRepositoryV2) factory.getProxy();
        log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), orderRepository.getClass());
        return proxy;
    }

    private Advisor getAdvisor(LogTrace logTrace) {
        // pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");
        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        // advisor
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
