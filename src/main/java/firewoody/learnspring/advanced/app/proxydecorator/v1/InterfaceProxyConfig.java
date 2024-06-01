package firewoody.learnspring.advanced.app.proxydecorator.v1;

import firewoody.learnspring.advanced.app.logproject.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 인터페이스에 대해 프록시 객체를 만들고, 스프링 컨테이너에는 프록시 객체만 등록한다.

//@Configuration
public class InterfaceProxyConfig {

    @Bean(name = "proxyOrderControllerV1")
    public OrderControllerV1 orderController(LogTrace logTrace) {
        OrderControllerV1Impl controllerImpl = new OrderControllerV1Impl(orderService(logTrace));
        return new OrderControllerInterfaceProxy(controllerImpl, logTrace);
    }

    @Bean(name = "proxyOrderServiceV1")
    public OrderServiceV1 orderService(LogTrace logTrace) {
        OrderServiceV1Impl serviceImpl = new OrderServiceV1Impl(orderRepository(logTrace));
        return new OrderServiceInterfaceProxy(serviceImpl, logTrace);
    }

    @Bean(name = "proxyOrderRepositoryV1")
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
        OrderRepositoryV1Impl repositoryImpl = new OrderRepositoryV1Impl();
        return new OrderRepositoryInterfaceProxy(repositoryImpl, logTrace);
    }
}
