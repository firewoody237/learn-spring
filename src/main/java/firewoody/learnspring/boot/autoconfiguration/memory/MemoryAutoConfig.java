package firewoody.learnspring.boot.autoconfiguration.memory;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;@Configuration

@AutoConfiguration
@ConditionalOnProperty(name = "memory", havingValue = "on") // 환경 정보가 `memory=on`이라는 조건에 맞으면 동작하고, 그렇지 않으면 동작하지 않는다.
public class MemoryAutoConfig {

    @Bean
    public MemoryController memoryController() {
        return new MemoryController(memoryFinder());
    }

    @Bean
    public MemoryFinder memoryFinder() {
        return new MemoryFinder();
    }
}
