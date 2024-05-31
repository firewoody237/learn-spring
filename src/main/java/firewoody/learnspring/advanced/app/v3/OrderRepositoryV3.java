package firewoody.learnspring.advanced.app.v3;

import firewoody.learnspring.advanced.app.hellotrace.HelloTraceV2;
import firewoody.learnspring.advanced.app.logtrace.LogTrace;
import firewoody.learnspring.advanced.app.trace.TraceId;
import firewoody.learnspring.advanced.app.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV3 {

    private final LogTrace trace;

    public void save(String itemId) {

        TraceStatus status = null;
        try {
            status = trace.begin("OrderRepositoryV3.request()");

            if (itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생");
            }
            sleep(1000);

            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
