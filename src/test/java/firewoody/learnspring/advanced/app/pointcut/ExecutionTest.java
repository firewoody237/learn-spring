package firewoody.learnspring.advanced.app.pointcut;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        log.info("helloMethod={}", helloMethod);
    }

    // execution 문법
    // execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)
    // execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
    // `?`는 생략 가능
    // `*` 같은 패턴 지정 가능
    // 파라미터의 `..`는 파라미터의 타입과 파라미터 수가 상관없다는 뜻
    // 패키지의 `.`는 정확하게 해당 위치의 패키지
    // 패키지의 `..`는 해당 위치의 패키지와 그 하위 패키지도 포함

    // 파라미터 규칙
    // (String) : 적확하게 String 타입 파라미터
    // () : 파라미터가 없어야 한다.
    // (*) : 정확히 하나의 파라미터, 단 모든 타입을 허용
    // (*, *) : 정확히 두 개의 파라미터, 단 모든 타입을 허용
    // (..) : 숫자와 무관하게 모든 파라미터, 모든 타입 허용, 파라미터가 없어도 됨
    // (String, ..) : String 타입으로 시작해야 한다. 숫자와 무관하게 모든 파라미터, 모든 타입을 허용

    @Test
    void exactMatch() {
        pointcut.setExpression("execution(public String firewoody.learnspring.advanced.app.pointcut.MemberServiceImpl.hello(String))");
        // matchs를 통해 포인트컷의 매칭 여부를 알 수 있다.
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 많이 생략
    @Test
    void allMatch() {
        pointcut.setExpression("execution(* *(..))"); // 접근지시자는 생략이고, 반환타입은 *
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 메서드 이름 매칭 관련 포인트컷1
    @Test
    void nameMatch() {
        pointcut.setExpression("execution(* hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 메서드 이름 매칭 관련 포인트컷2
    @Test
    void nameMatchStart1() {
        pointcut.setExpression("execution(* hel*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 메서드 이름 매칭 관련 포인트컷3
    @Test
    void nameMatchStart2() {
        pointcut.setExpression("execution(* *el*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 메서드 이름 매칭 관련 포인트컷4
    @Test
    void nameMatchFalse() {
        pointcut.setExpression("execution(* nono(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    // 패키지 매칭 관련 포인트컷1
    @Test
    void packageExactMatch1() {
        pointcut.setExpression("execution(* firewoody.learnspring.advanced.app.pointcut.MemberServiceImpl.hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 패키지 매칭 관련 포인트컷2
    @Test
    void packageExactMatch2() {
        pointcut.setExpression("execution(* firewoody.learnspring.advanced.app.pointcut.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }


    // 패키지 매칭 관련 포인트컷3
    @Test
    void packageExactMatchFalse() {
        pointcut.setExpression("execution(* firewoody.learnspring.advanced.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    // 패키지 매칭 관련 포인트컷4
    @Test
    void packageMatchSubPackage1() {
        pointcut.setExpression("execution(* firewoody.learnspring.advanced..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 패키지 매칭 관련 포인트컷4
    @Test
    void packageMatchSubPackage2() {
        pointcut.setExpression("execution(* firewoody.learnspring..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 타입 매칭 - 부모 타입 허용 1
    @Test
    void typeExactMatch() {
        pointcut.setExpression("execution(* firewoody.learnspring.advanced.app.pointcut.MemberServiceImpl.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    //  타입 매칭 - 부모 타입에 있는 메서드만 허용1
    @Test
    void typeMatchSuperType() throws NoSuchMethodException {
        pointcut.setExpression("execution(* firewoody.learnspring.advanced.app.pointcut.MemberServiceImpl.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isTrue();
    }

    // 타입 매칭 - 부모 타입에 있는 메서드만 허용2
    // 포인트컷으로 지정한 MemberService는 internal 이라는 이름의 메서드가 없다.
    @Test
    void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {
        pointcut.setExpression("execution(* firewoody.learnspring.advanced.app.pointcut.MemberService.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
    }

    // 파라미터 매칭1
    // String 타입의 파라미터 허용
    @Test
    void argsMatch() {
        pointcut.setExpression("execution(* *(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 파라미터 매칭2
    // 파라미터가 없어야 함
    @Test
    void argsMatchNoArgs() {
        pointcut.setExpression("execution(* *())");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    // 파라미터 매칭3
    // 정확히 하나의 파라미터 허용, 모든 타입 허용
    @Test
    void argsMatchStar() {
        pointcut.setExpression("execution(* *(*))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 파라미터 매칭4
    // 숫자와 무관하게 모든 파라미터, 모든 타입 허용
    // 파라미터가 없어도 됨
    @Test
    void argsMatchAll() {
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 파라미터 매칭5
    // String 타입으로 시작, 숫자와 무관하게 모든 파라미터, 모든 타입 허용
    @Test
    void argsMatchComplex() {
        pointcut.setExpression("execution(* *(String, ..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
