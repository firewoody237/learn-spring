package firewoody.learnspring.mvc.controller;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    // HTTP message body로 데이터가 들어오면 `@RequestParam`, `@ModelAttribute`를 사용할 수 없다.(HTML Form 형식은 가능)
    // 메시지 바디에 대한 조회는 `@RequestParam`, `@ModelAttribute`와는 관계 없다.

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);

        response.getWriter().write("ok");
    }

    // 파라미터의 InputStream은 HTTP 요청 메시지 바디의 내용을 직접 조회한다.
    // OutputStream(Writer)는 HTTP 응답 메시지 바디에 직접 결과를 출력한다.
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);

        responseWriter.write("ok");
    }

    // HttpEntity를 요청 및 응답에 사용할 수 있다.
    // 메시지 바디 정보를 직접 조회한다. HttpMessageConverter에서 StringHttpMessageConverter를 사용한다.
    // 메시지 바디 정보를 직접 반환한다. HttpMessageConverter에서 StringHttpMessageConverter를 사용한다.
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) {

        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);

        return new HttpEntity<>("ok");
    }

    // RequestEntity와 ResponseEntity는 HttpEntity를 상속한다.
    @PostMapping("/request-body-string-v4")
    public ResponseEntity<String> requestBodyStringV4(RequestEntity<String> requestEntity) {

        String messageBody = requestEntity.getBody();
        log.info("messageBody={}", messageBody);

        return new ResponseEntity<>("ok", HttpStatus.CREATED);
    }

    // `@RequestBody` : 메시지 바디 정보를 직접 조회. StringHttpMessageConverter
    // `@ResponseBody` : 메시지 바디 정보를 직접 잔환. StringHttpMessageConverter
    @ResponseBody
    @PostMapping("/request-body-string-v5")
    public String requestBodyStringV4(@RequestBody String messageBody) {
        log.info("messageBody={}", messageBody);
        return "ok";
    }
}
