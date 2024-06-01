package firewoody.learnspring.advanced.app.proxydecorator.v2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 인터페이스 없는 구체 클래스 - 스프링 빈으로 수동 등록

@Slf4j
@RestController("proxyOrderControllerV2")
public class OrderControllerV2 {

    private final OrderServiceV2 orderService;

    public OrderControllerV2(OrderServiceV2 orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/proxydecorator/v2/request")
    public String request(String itemId) {
        orderService.orderItem(itemId);
        return "ok";
    }

    @GetMapping("/proxydecorator/v2/no-log")
    public String noLog() {
        return "ok";
    }
}
