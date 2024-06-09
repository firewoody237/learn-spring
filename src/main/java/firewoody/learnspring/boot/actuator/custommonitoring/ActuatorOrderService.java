package firewoody.learnspring.boot.actuator.custommonitoring;

import java.util.concurrent.atomic.AtomicInteger;

public interface ActuatorOrderService {
    void order();
    void cancel();
    AtomicInteger getStock();
}
