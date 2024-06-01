package firewoody.learnspring.advanced.app.logproject;

import firewoody.learnspring.advanced.app.logproject.logtrace.LogTrace;
import firewoody.learnspring.advanced.app.logproject.logtrace.ThreadLocalLogTrace;
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
