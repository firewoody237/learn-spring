package firewoody.learnspring.login.cookie;

import firewoody.learnspring.login.domain.LoginInfo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login/cookie")
public class LoginCookieController {

    // 쿠키 값은 임의로 변경할 수 있다.
    // 쿠키에 보관된 값은 훔쳐갈 수 있다.
    // 해커가 쿠키를 한번 훔쳐가면 평생 사용 할 수 있다.
    // 따라서 서버에서 세션을 관리하고, 관리하는 토큰 값에 만료 기간을 짧게 주어 쿠키에 저장하여 사용한다.


    // 로그인 한 후 Cookie를 반환하여 사용자 정보값을 활용하도록 한다.
    @ResponseBody
    @PostMapping("/response-cookie")
    public String responseCookie(@ModelAttribute LoginInfo loginInfo, HttpServletResponse response) {
        Cookie cookie = new Cookie("memberId", String.valueOf(loginInfo.getUserId()));
        response.addCookie(cookie);

        return "ok";
    }

    @ResponseBody
    @GetMapping
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId) {
        if (memberId == null) {
            return "no-cookie";
        }

        return "yes-cookie";
    }

    @ResponseBody
    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {

        Cookie cookie = new Cookie("memberId", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "ok";
    }
}
