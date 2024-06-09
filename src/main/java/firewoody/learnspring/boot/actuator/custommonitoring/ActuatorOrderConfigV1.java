package firewoody.learnspring.boot.actuator.custommonitoring;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActuatorOrderConfigV1 {

    @Bean
    public MyStockMetric myStockMetric(ActuatorOrderService actuatorOrderService, MeterRegistry registry) {
        return new MyStockMetric(actuatorOrderService, registry);
    }

    @Slf4j
    static class MyStockMetric {
        private ActuatorOrderService actuatorOrderService;
        private MeterRegistry registry;

        public MyStockMetric(ActuatorOrderService actuatorOrderService, MeterRegistry registry) {
            this.actuatorOrderService = actuatorOrderService;
            this.registry = registry;
        }

        @PostConstruct
        public void init() {
            Gauge.builder("my.stock", actuatorOrderService, service -> { // 여기서 넘기는 함수는 외부에서 메트릭을 확인할 때 마다 호출된다.
                log.info("stock gauge call");
                return service.getStock().get();
            }).register(registry);
        }
    }
}
