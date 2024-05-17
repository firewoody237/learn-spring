package firewoody.learnspring.login.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// 서블릿에서 제공하는 기능

@Slf4j
@Controller
@RequestMapping("/http-session")
public class HttpSessionController {

    // 서블릿을 통해 `HttpSession`을 생성하면 쿠키 이름이 `JSESSIONID`인 쿠키를 생성한다.

    @ResponseBody
    @PostMapping("/login")
    public String httpSession(HttpServletRequest request) {
        // 세션이 있으면 있는 세션을 반환하고, 없으면 신규로 생성한다.
        // 파라미터로 false를 넣으면 세션이 없을 때 새로 생성하지 않고 null을 반환한다.
        HttpSession session = request.getSession();
        // 세션에 로그인 회원 정보를 보관한다.
        session.setAttribute("loginMember", "yeye");

        lookAndFeelSessionInfo(request);

        return "ok";
    }

    private void lookAndFeelSessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();

        log.info("sessionId={}", session.getId());
        log.info("maxInactiveInterval={} : 세션의 유효 시간(초)", session.getMaxInactiveInterval());
        log.info("creationTime={}", session.getCreationTime());
        log.info("lastAccessedTime={}", session.getLastAccessedTime());
        log.info("isNew={} : 새로 생성된 세션인지 확인", session.isNew());
    }

    @ResponseBody
    @PostMapping("/logout")
    public String httpSessionLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();

        return "ok";
    }
}
