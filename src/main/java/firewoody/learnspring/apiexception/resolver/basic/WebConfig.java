package firewoody.learnspring.apiexception.resolver.basic;

import firewoody.learnspring.apiexception.resolver.basic.MyHandlerExceptionResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

//@Configuration
public class WebConfig implements WebMvcConfigurer {

    // configureHandlerExceptionResolvers()는 스프링이 기본으로 등록하는 ExceptionResolver를 제거한다.
    // 따라서 가급적 extendHandlerExceptionResolvers()를 사용한다.
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(new MyHandlerExceptionResolver());
    }
}
