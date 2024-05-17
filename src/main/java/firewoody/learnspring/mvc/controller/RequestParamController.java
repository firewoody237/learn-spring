package firewoody.learnspring.mvc.controller;

import firewoody.learnspring.mvc.domain.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // GET의 쿼리 파라미터의 파라미터를 읽을 수 있다.
        // ex. localhost:8080/request-param-v1?username=hello
        // POST의 파라미터도 읽을 수 있다.
        String username = request.getParameter("username");
        log.info("username={}", username);
        response.getWriter().write("ok");
    }

    // GET의 쿼리 파라미터의 파라미터를 읽을 수 있다.
    // POST의 파라미터도 읽을 수 있다.
    @ResponseBody // HTTP message body에 직접 내용 출력
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("username") String memberName) {
        log.info("memberName={}", memberName);
        return "ok";
    }

    // 변수이름과 파라미터 이름이 같으면, `("username")`을 생략할 수 있다.
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(@RequestParam String username) {
        log.info("username={}", username);
        return "ok";
    }

    // 변수가 String, int 등의 단순 타입이면 `@RequestParam`도 생략이 가능하다.
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username) {
        log.info("username={}", username);
        return "ok";
    }

    // `required`로 파라미터 필수 여부를 결정할 수 있다.
    // 필수 파라미터가 없으면 400에러가 발생한다.
    // 기본형에 null을 입력하는 것은 불가능하다. (500에러 발생)
    // `defaultValue`로 기본 값을 적용할 수 있다.
    // 빈 문자의 경우에도 설정한 기본 값이 적용된다.
    @ResponseBody
    @RequestMapping("/request-param-v5")
    public String requestParamV5(@RequestParam(required = true, defaultValue = "guest") String username) {
        log.info("username={}", username);
        return "ok";
    }

    // 파라미터를 Map으로 조회
    // Map혹은 MultiValueMap으로 조회할 수 있다.
    @ResponseBody
    @RequestMapping("/request-param-v6")
    public String requestParamV6(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    // `@ModelAttribute`로 객체에 매핑할 수 있다.
    // 해당 객체의 setter를 호출해서 파라미터의 값을 바인딩한다.
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    // `@ModelAttribute`는 생략할 수 있다.
    // 스프링은 String, int, Integer 같은 단순 타입을 생략하면 `@RequestParam`으로 인식하고, 나머지는 `@ModelAttribute`의 생략으로 인식한다.
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }
}
