package firewoody.learnspring.apiexception.resolver.basic;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    // ModelAndView 를 반환하는 이유는 예외를 처리해 정상 흐름 처럼 변경하는 것이 목적이기 때문이다.
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                // 빈 ModelAndView : 뷰를 렌더링 하지 않고 정상 흐름으로 서블릿 리턴
                // 지정 ModelAndView : 뷰를 렌더링
                // null : 다음 ExceptionResolver 를 찾아서 실행
                return new ModelAndView();
            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }

        return null;
    }
}
