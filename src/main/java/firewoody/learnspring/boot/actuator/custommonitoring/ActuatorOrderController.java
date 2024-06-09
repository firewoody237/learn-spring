package firewoody.learnspring.boot.actuator.custommonitoring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ActuatorOrderController {

    private final ActuatorOrderService actuatorOrderService;

    public ActuatorOrderController(ActuatorOrderService actuatorOrderService) {
        this.actuatorOrderService = actuatorOrderService;
    }

    @GetMapping("/actuator-order")
    public String order() {
        log.info("order");
        actuatorOrderService.order();
        return "order";
    }

    @GetMapping("/actuator-cancel")
    public String cancel() {
        log.info("cancel");
        actuatorOrderService.cancel();
        return "cancel";
    }

    @GetMapping("/actuator-stock")
    public int stock() {
        log.info("stock");
        return actuatorOrderService.getStock().get();
    }
}
