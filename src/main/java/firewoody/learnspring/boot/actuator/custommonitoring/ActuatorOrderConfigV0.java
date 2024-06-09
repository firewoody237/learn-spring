package firewoody.learnspring.boot.actuator.custommonitoring;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Time;

@Configuration
public class ActuatorOrderConfigV0 {

//    @Bean
//    ActuatorOrderService actuatorOrderService() {
//        return new ActuatorOrderServiceV0();
//    }

//    @Bean
//    ActuatorOrderService actuatorOrderService(MeterRegistry registry) {
//        return new ActuatorOrderServiceV1(registry);
//    }

//    @Bean
//    ActuatorOrderService actuatorOrderService() {
//        return new ActuatorOrderServiceV2();
//    }

//    @Bean
//    public CountedAspect countedAspect(MeterRegistry registry) {
//        return new CountedAspect(registry);
//    }

//    @Bean
//    ActuatorOrderService actuatorOrderService(MeterRegistry registry) {
//        return new ActuatorOrderServiceV3(registry);
//    }

    @Bean
    ActuatorOrderService actuatorOrderService() {
        return new ActuatorOrderServiceV4();
    }

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
}
