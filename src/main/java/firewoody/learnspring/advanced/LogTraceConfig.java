package firewoody.learnspring.advanced;

import firewoody.learnspring.advanced.app.logtrace.FieldLogTrace;
import firewoody.learnspring.advanced.app.logtrace.LogTrace;
import firewoody.learnspring.advanced.app.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() {
//        return new FieldLogTrace();
        return new ThreadLocalLogTrace();
    }
}
