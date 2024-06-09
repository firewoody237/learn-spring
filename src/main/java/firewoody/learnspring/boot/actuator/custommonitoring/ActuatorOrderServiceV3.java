package firewoody.learnspring.boot.actuator.custommonitoring;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ActuatorOrderServiceV3 implements ActuatorOrderService {

    private final MeterRegistry registry;
    private AtomicInteger stock = new AtomicInteger(100);

    public ActuatorOrderServiceV3(MeterRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void order() {
        Timer timer = Timer.builder("my.order")
                .tag("class", this.getClass().getName())
                .tag("method", "order")
                .description("order")
                .register(registry);

        timer.record(() -> {
            log.info("주문");
            stock.decrementAndGet();
            sleep(500);
        });
    }

    @Counted("my.order")
    @Override
    public void cancel() {
        Timer timer = Timer.builder("my.order")
                .tag("class", this.getClass().getName())
                .tag("method", "cancel")
                .description("order")
                .register(registry);

        timer.record(() -> {
            log.info("취소");
            stock.decrementAndGet();
            sleep(200);
        });
    }

    public static void sleep(int l) {
        try {
            Thread.sleep(l + new Random().nextInt(200));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AtomicInteger getStock() {
        return stock;
    }
}
