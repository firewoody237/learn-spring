package firewoody.learnspring.login.session;

// 스프링에서 제공하는 기능

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/session-attribute")
public class SessionAttributeController {

    // 세션 만료 시간은 `application.properties`에서 글로벌 설정이 가능
    // server.servlet.session.timeout=60
    // 혹은 특정 세션 단위로 설정 가능
    // session.setMaxInactiveInterval(1800);

    // lastAccessedTime(최근 세션 접근 시간)이후로 timeout 시간이 지나면, WAS가 내부에서 해당 세션을 제거한다.

    // 세션은 용량이슈가 있을수 있으므로 최소한의 정보만 저장해야 한다.

    @ResponseBody
    @GetMapping("/")
    public String sessionAttribute(@SessionAttribute(name = "mySession", required = false) String mySession) {
        if (mySession == null) {
            return "no-session";
        }

        return "yes-session";
    }
}
