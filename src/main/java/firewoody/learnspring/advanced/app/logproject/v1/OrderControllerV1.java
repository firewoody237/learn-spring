package firewoody.learnspring.advanced.app.logproject.v1;

import firewoody.learnspring.advanced.app.logproject.hellotrace.HelloTraceV1;
import firewoody.learnspring.advanced.app.logproject.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// HelloTraceV1 적용

@RestController
@RequiredArgsConstructor
@RequestMapping("/advanced")
public class OrderControllerV1 {

    private final OrderServiceV1 orderService;
    private final HelloTraceV1 trace;

    @GetMapping("/v1/request")
    public String request(String itemId) {

        TraceStatus status = null;
        try {
            status = trace.begin("OrderControllerV1.request()");
            orderService.orderItem(itemId);
            trace.end(status);
            return "ok";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
