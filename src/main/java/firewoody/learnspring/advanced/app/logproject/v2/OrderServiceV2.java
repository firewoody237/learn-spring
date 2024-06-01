package firewoody.learnspring.advanced.app.logproject.v2;

import firewoody.learnspring.advanced.app.logproject.hellotrace.HelloTraceV2;
import firewoody.learnspring.advanced.app.logproject.trace.TraceId;
import firewoody.learnspring.advanced.app.logproject.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepository;
    private final HelloTraceV2 trace;

    public void orderItem(TraceId traceId, String itemId) {
        TraceStatus status = null;
        try {
            status = trace.beginSync(traceId, "OrderServiceV2.request()");
            orderRepository.save(status.getTraceId(), itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
