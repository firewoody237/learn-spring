package firewoody.learnspring.advanced.app.proxydecorator.v1;

import org.springframework.web.bind.annotation.*;

// 인터페이스와 구현 클래스 - 스프링 빈으로 수동 등록

// 참고 : 인터페이스에 `@RestController`를 사용해도 구현체가 아니기 때문에 빈으로 등록되지 않는다.
//      추가로 `@Configuration`에 빈 등록을 해주어야 한다.
@RestController("proxyOrderControllerV1")
public interface OrderControllerV1 {

    @GetMapping("/proxydecorator/v1/request")
    String request(@RequestParam("itemId") String itemId);

    @GetMapping("/proxydecorator/v1/no-log")
    String noLog();
}
