package firewoody.learnspring.advanced.app.springaop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class SpringAopOrderRepository {

    public String save(String itemId) {
        log.info("[springAopOrderRepository] 실행");
        // 저장 로직
        if (itemId.equals("ex")) {
            throw new IllegalStateException("예외 발생!");
        }
        return "ok";
    }
}
