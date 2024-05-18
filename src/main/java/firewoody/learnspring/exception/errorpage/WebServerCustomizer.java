package firewoody.learnspring.exception.errorpage;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

// 서블릿 오류 페이지를 등록한다.
// 서블릿은 Exception이 발생해서 서블릿 밖으로 전달되거나 response.sendError가 호출 되었을 때 설정된 오류 페이지를 찾는다.
//       WAS는 해당 예외를 처리하는 오류 페이지 정보를 확인한다.
// 1. WAS <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러(예외발생)
// 2. WAS `/error-page/500` 다시 요청 -> 필터 -> 서블릿 -> 인터셉터 -> 컨트롤러 -> view
//@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {

        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");
        ErrorPage errorPageException = new ErrorPage(RuntimeException.class, "/error-page/500");
        factory.addErrorPages(errorPage404, errorPage500, errorPageException);
    }
}
