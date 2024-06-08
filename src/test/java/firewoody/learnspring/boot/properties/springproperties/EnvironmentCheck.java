package firewoody.learnspring.boot.properties.springproperties;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EnvironmentCheck {

    // Environment를 통해 설정을 읽는 방법을 추상화
    // 어떤 설정을 사용하던 모두 같은 방법으로 읽을 수 있다.

    private final Environment env;

    public EnvironmentCheck(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void init() {
        String url = env.getProperty("url");
        String username = env.getProperty("username");
    }
}
