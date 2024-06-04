package firewoody.learnspring.advanced.app.springaop.v1;

// @Pointcut을 사용해 별도로 분리

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

    // 메서드 이름 + 파라미터 -> 시그니처
    // 메서드 반환 타입은 void여야 함
    // 코드 내용은 비움
    @Pointcut("execution(* firewoody.learnspring.advanced.app..*(..))")
    private void allOrder() {}

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }
}
