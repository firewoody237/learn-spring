package firewoody.learnspring.boot.actuator.custommonitoring;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ActuatorOrderConfigV2 {

    @Bean
    public MeterBinder stockSize(ActuatorOrderService actuatorOrderService) {
        return registry -> Gauge.builder("my.stock", actuatorOrderService, service -> {
            log.info("stock gauge call");
            return service.getStock().get();
        }).register(registry);
    }
}
