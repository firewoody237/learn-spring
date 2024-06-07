package firewoody.learnspring.boot.tomcat;

import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

// 컴포넌트 스캔에 사용

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ComponentScan
public @interface MySpringBootApplication {
}
