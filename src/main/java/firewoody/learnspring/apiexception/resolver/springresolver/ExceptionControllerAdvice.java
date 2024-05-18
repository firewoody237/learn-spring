package firewoody.learnspring.apiexception.resolver.springresolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.AbstractController;

// `@ControllerAdvice`는 대상으로 지정한 여러 컨트롤러에 @ExceptionHandler, @InitBind 기능을 부여해주는 역할을 한다.
// 대상을 지정하지 않으면 모든 컨트롤러에 글로벌 적용된다.
// @RestControllerAdvice는 @ControllerAdvice에 @ResponseBody가 추가된 것이다.

@Slf4j
//@RestControllerAdvice
//@ControllerAdvice(annotations = RestController.class)
//@ControllerAdvice("org.example.controllers") // 해당 패키지와 그 하위 패키지를 선정
//@ControllerAdvice(assignableTypes = {AbstractController.class})
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }
}
