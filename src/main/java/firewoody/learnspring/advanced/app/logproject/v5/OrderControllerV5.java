package firewoody.learnspring.advanced.app.logproject.v5;

import firewoody.learnspring.advanced.app.logproject.logtrace.LogTrace;
import firewoody.learnspring.advanced.app.logproject.templatecallback.TraceCallback;
import firewoody.learnspring.advanced.app.logproject.templatecallback.TraceTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// FieldLogTrace 적용

@RestController
@RequestMapping("/advanced")
public class OrderControllerV5 {

    private final OrderServiceV5 orderService;
    private final TraceTemplate template;

    public OrderControllerV5(OrderServiceV5 orderService, LogTrace trace) {
        this.orderService = orderService;
        this.template = new TraceTemplate(trace);
    }

    @GetMapping("/v5/request")
    public String request(String itemId) {
        return template.execute("OrderControllerV5.request()", new TraceCallback<String>() {
            @Override
            public String call() {
                orderService.orderItem(itemId);
                return "ok";
            }
        });
    }
}
