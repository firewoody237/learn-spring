package firewoody.learnspring.exception.controller;

// 컨트롤러에서 예외가 발생하면 WAS까지 예외가 전파된다. : 컨트롤러 -> 인터셉터 -> 서블릿 -> 필터 -> WAS
// application.properties에서 whitelabel 페이지 옵션을 끄면, 기본 서블릿 오류 페이지가 활성화된다.

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Slf4j
@Controller
public class ServletExceptionController {

    @GetMapping("/error-exception")
    public void errorException() {
        throw new RuntimeException("런타임 예외 발생");
    }

    // sendError를 사용하면, 서블릿 컨테이너는 고객에게 응답 전에 response에 sendError가 호출되었는지 확인한다.
    // 호출 되었다면, 오류 코드에 맞춰 오류 페이지를 보여준다.
    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        response.sendError(404, "404 오류");
    }

    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500);
    }

    @GetMapping("/error-400")
    public void error400(HttpServletResponse response) throws IOException {
        response.sendError(400);
    }
}
