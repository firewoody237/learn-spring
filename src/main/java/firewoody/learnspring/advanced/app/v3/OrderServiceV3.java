package firewoody.learnspring.advanced.app.v3;

import firewoody.learnspring.advanced.app.hellotrace.HelloTraceV2;
import firewoody.learnspring.advanced.app.logtrace.LogTrace;
import firewoody.learnspring.advanced.app.trace.TraceId;
import firewoody.learnspring.advanced.app.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {

    private final OrderRepositoryV3 orderRepository;
    private final LogTrace trace;

    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderServiceV3.request()");
            orderRepository.save(itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
