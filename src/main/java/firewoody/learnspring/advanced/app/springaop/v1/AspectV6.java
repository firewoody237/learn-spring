package firewoody.learnspring.advanced.app.springaop.v1;

// 포인트컷을 모아놓은 클래스를 만들어 참조

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6 {

    // 여러개 존재할 때 실행 순서 : @Around -> @Before -> @After -> @AfterReturning -> @AfterThrowing
    @Around("firewoody.learnspring.advanced.app.springaop.v1.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable { // @Around만 ProceedingJoinPoint를 사용한다.
        try {
            // @Before
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            // @AfterReturning
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            // @AfterThrowing
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            // @After
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Before("firewoody.learnspring.advanced.app.springaop.v1.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        // 객체 변경 불가
        log.info("[Before] {}", joinPoint.getSignature());
    }

    @AfterReturning(value = "firewoody.learnspring.advanced.app.springaop.v1.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        // returning 속성에 사용된 이름은 어드바이스 매개변수 이름과 일치해야 한다.
        // returning 절에 지정된 타입의 값을 반환하는 메서드만 대상으로 실행한다. (부모타입의 경우, 모든 자식 클래스 타입)
        // 객체 변경 불가
        log.info("[AfterReturning] {} return={}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "firewoody.learnspring.advanced.app.springaop.v1.Pointcuts.orderAndService()", throwing = "e")
    public void doThrowing(JoinPoint joinPoint, Exception e) {
        // throwing 속성에 사용된 이름은 어드바이스 매개변수 이름과 일치해야 한다.
        // throwing 절에 지정된 타입과 맞는 예외를 대상으로 실행 (부모타입의 경우, 모든 자식 예외 타입)
        log.info("[AfterThrowing] {} message={}", joinPoint.getSignature(), e.getMessage());
    }

    @After(value = "firewoody.learnspring.advanced.app.springaop.v1.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        // 메서드 실행이 종료되면 실행
        // 주로 리소스 해제에 사용
        log.info("[After] {}", joinPoint.getSignature());
    }

}
