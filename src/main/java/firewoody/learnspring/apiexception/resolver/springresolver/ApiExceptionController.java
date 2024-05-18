package firewoody.learnspring.apiexception.resolver.springresolver;

import firewoody.learnspring.apiexception.domain.MemberDto;
import firewoody.learnspring.apiexception.resolver.userexception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ApiExceptionController {

    // 여기의 ExceptionHandler는 현재 Controller만을 대상으로 한다. (`@ControllerAdvice` 또는 `@RestControllerAdvice`로 분리할 수 있다.)
    // 우선순위는 자세한것을 우선순위로 한다.
    // 자식 예외도 포함하여 Catch한다.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExceptionHandler(IllegalArgumentException e) {
        // 실행 흐름
        // 1. 컨트롤러 실행 결과 `IllegalArgumentException`예외가 컨트롤러 밖으로 던져진다.
        // 2. 예외가 발생했으므로 `ExceptionResolver`가 작동한다. 가장 우선순위가 높은 `ExceptionHandlerExceptionResolver`가 실행된다.
        // 3. `ExceptionHandleExceptionResolver`는 해당 컨트롤러에 `IllegalArgumentException`을 처리할 수 있는 `@ExceptionHandler`가 있는지 확인한다.
        // 4. `illegalExceptionHandler()`를 실행한다. `@RestController`이므로 `illegalExceptionHandler()`에도 `@ResponseBody`가 적용된다.
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    // `@ExceptionHandler`의 파라미터가 생략되면, 메서드 파라미터의 예외가 지정된다.
    // 파라미터와 응답은 아래에서 확인이 가능하다.
    // https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-exceptionhandler.html#mvc-ann-exceptionhandler-args
    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExceptionHandler(UserException e) {
        log.error("[exc exceptionHandle] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exceptionHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }

    @GetMapping("/member/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {
        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }

        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }

        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id);
    }
}
