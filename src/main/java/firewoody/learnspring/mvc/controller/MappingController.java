package firewoody.learnspring.mvc.controller;

import org.springframework.web.bind.annotation.*;

// `@RestController`는 반환값으로 뷰를 찾지 않고, HTTP 메시지 바디에 바로 입력한다.
@RestController
@RequestMapping("/request-mapping")
public class MappingController {

    // GET이 아닌 요청이 들어오면 405 에러를 반환한다.
    @RequestMapping(value = {"/get", "get2"}, method = RequestMethod.GET)
    public String requestMappingGet() {
        return "ok";
    }

    // 위의 `@RequestMapping`을 축약할 수 있다.
    @GetMapping("/get-mapping")
    public String requestMappingGetMapping() {
        return "ok";
    }

    // 파라미터와 이름이 같으면 `("userId")` 부분을 생략할 수 있다.
    @GetMapping("/path-variable")
    public String requestMappingPathVariable(@PathVariable("userId") String data) {
        return "ok";
    }

    // 특정 파라미터 조건을 매핑할 수 있다.
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String requestMappingParam() {
        return "ok";
    }

    // 특정 헤더 조건을 매핑할 수 있다.
    @GetMapping(value = "/mapping-header", params = "mode=debug")
    public String requestMappingHeader() {
        return "ok";
    }

    // 미디어 타입 조건을 매핑할 수 있다.
    @GetMapping(value = "/mapping-header", consumes = "application/json")
    public String requestMappingConsumes() {
        return "ok";
    }

    // 미디어 타입 조건(Accept)을 매핑할 수 있다.
    @GetMapping(value = "/mapping-produce", produces = "text/html")
    public String requestMappingProduces() {
        return "ok";
    }
}
