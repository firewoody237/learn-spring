package firewoody.learnspring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RseponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello!");

        return mav;
    }

    // String을 반환하면 viewResolver가 호출된다.
    // 단, `@ResponseBody`가 있으면 뷰 리졸버를 호출하지 않고 직접 문자가 입력된다.
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!");

        return "response/hello";
    }

    // `@Controller`를 사용하고 Void를 반환하고, HttpServletResponse, OutputStream(Writer)같은 HTTP 메시지 바디를 처리하는 파라미터가 없으면
    // 요청 URL을 참고해서 논리 뷰 이름으로 사용한다.
    // 잘 사용되지 않는다.
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!");
    }
}
