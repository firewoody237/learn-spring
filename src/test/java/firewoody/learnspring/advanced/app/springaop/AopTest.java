package firewoody.learnspring.advanced.app.springaop;

import firewoody.learnspring.advanced.app.springaop.v1.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
//@Import({AspectV1.class}) // 주로 설정파일을 추가할 떄 사용하지만, 스프링 빈도 등록할 수 있다.
//@Import({AspectV2.class})
//@Import({AspectV3.class})
//@Import({AspectV4.class})
//@Import({AspectV5.LogAspect.class, AspectV5.TxAspect.class})
@Import({AspectV6.class})
@SpringBootTest
class AopTest {

    @Autowired
    SpringAopOrderService springAopOrderService;

    @Autowired
    SpringAopOrderRepository springAopOrderRepository;

    @Test
    void aopInfo() {
        log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(springAopOrderService));
        log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(springAopOrderRepository));
    }

    @Test
    void success() {
        springAopOrderService.orderItem("itemA");
    }
}

