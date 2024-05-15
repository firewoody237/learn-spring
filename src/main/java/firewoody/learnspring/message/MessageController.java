package firewoody.learnspring.message;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/message")
public class MessageController {

    @GetMapping("")
    public String message() {
        // 요청의 Accept-Language를 변경하면 국제화를 활용할 수 있다.
        // 스프링은 Accept-Language를 활용하기 위해 `AcceptHeaderLocaleResolver`를 사용한다.
        // Local 선택방식을 변경하려면 `AceptHeaderLocaleResolver`의 구현체를 변경해서 쿠키나 세션 기반의 Locale 선택 기능을 사용할 수 있다.
        return "/message/message";
    }
}
