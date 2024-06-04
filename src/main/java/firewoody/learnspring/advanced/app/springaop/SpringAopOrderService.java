package firewoody.learnspring.advanced.app.springaop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SpringAopOrderService {

    private final SpringAopOrderRepository springAopOrderRepository;

    public SpringAopOrderService(SpringAopOrderRepository orderRepository) {
        this.springAopOrderRepository = orderRepository;
    }

    public void orderItem(String itemId) {
        log.info("[springAopOrderService] 실행");
        springAopOrderRepository.save(itemId);
    }
}
