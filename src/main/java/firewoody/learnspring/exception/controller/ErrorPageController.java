package firewoody.learnspring.exception.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class ErrorPageController {

    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 404");
        printErrorInfo(request);
        return "exception/error-page/404";
    }

    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 500");
        printErrorInfo(request);
        return "exception/error-page/500";
    }

    // WAS가 오류 페이지를 다시 호출 할 때 오류 정보를 확인할 수 있다.
    private void printErrorInfo(HttpServletRequest request) {
        log.info("ERROR_EXCEPTIPON : ex = {}", request.getAttribute("javax.servlet.error.exception")); // 예외
        log.info("ERROR_EXCEPTIPON_TYPE : {}", request.getAttribute("javax.servlet.error.exception_type")); // 예외 타입
        log.info("ERROR_MESSAGE : {}", request.getAttribute("javax.servlet.error.message")); // 오류 메시지
        log.info("ERROR_REQUEST_URI : {}", request.getAttribute("javax.servlet.error.request_uri")); // 클라이언트 요청 URI
        log.info("ERROR_SERVLET_NAME : {}", request.getAttribute("javax.servlet.error.servlet_name")); // 오류가 발생한 서블릿 이름
        log.info("ERROR_STATUS_CODE : {}", request.getAttribute("javax.servlet.error.status_code")); // HTTP 상태 코드

        // DispatcherType
        // 1. FORWARD : 서블릿에서 다른 서블릿이나 JSP를 호출할 떄
        // 2. INCLUDE : 서블릿에서 다른 서블릿이나 JSP의 결과를 포함할 때
        // 3. REQUEST : 클라이언트 요청
        // 4. ASYNC : 서블릿 비동기 호출
        // 5. ERROR : 오류 요청
        // 필터를 등록할 때 `setDispatcherTypes`로 시점 등록이 가능하다.
        log.info("dispatchType = {}", request.getDispatcherType());
    }
}
