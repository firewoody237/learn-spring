package firewoody.learnspring.login.argumentresolver;

import firewoody.learnspring.login.argumentresolver.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/argument-resolver")
public class ArgumentResolverController {

    @GetMapping
    @ResponseBody
    public String argumentResolver(@Login Member loginMember) {
        if (loginMember == null) {
            return "no-member";
        }

        return "yes-member";
    }
}
