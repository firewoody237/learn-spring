package firewoody.learnspring.boot.autoconfiguration.memory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
//@Conditional(MemoryCondition.class) // 조건(Condition)을 추가하여 사용. MemoryCondition 에서 true를 반환해줘야 동작한다. false이면 무효화 된다.(빈이 등록되지 않는다.)
@ConditionalOnProperty(name = "memory", havingValue = "on") // 환경 정보가 `memory=on`이라는 조건에 맞으면 동작하고, 그렇지 않으면 동작하지 않는다.
public class MemoryConfig {

    @Bean
    public MemoryController memoryController() {
        return new MemoryController(memoryFinder());
    }

    @Bean
    public MemoryFinder memoryFinder() {
        return new MemoryFinder();
    }
}
