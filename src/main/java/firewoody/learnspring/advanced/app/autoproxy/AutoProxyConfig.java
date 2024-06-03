package firewoody.learnspring.advanced.app.autoproxy;

import firewoody.learnspring.advanced.app.logproject.logtrace.LogTrace;
import firewoody.learnspring.advanced.app.proxydecorator.v1.AppV1Config;
import firewoody.learnspring.advanced.app.proxydecorator.v1.proxyfactory.LogTraceAdvice;
import firewoody.learnspring.advanced.app.proxydecorator.v2.AppV2Config;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//@Configuration
//@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

//    @Bean
    public Advisor advisor1(LogTrace logTrace) {
        // 빈 후처리기를 따로 등록해 주지 않아도된다. AnnotationAwareAspectJAutoProxyCreator 빈 후처리기를 자동으로 등록해준다.
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }

//    @Bean
    public Advisor advisor2(LogTrace logTrace) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* firewoody.learnspring.advanced.app..*(..))"); // AspectJ가 제공하는 포인트컷 표현식
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    @Bean
    public Advisor advisor3(LogTrace logTrace) {
        // Advisor만 스프링 빈으로 등록하면, AnnotationAwareAspectJAutoProxyCreator 빈 후처리기가 자동으로 적용 시켜 준다.
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* firewoody.learnspring.advanced.app..*(..)) && !execution(* firewoody.learnspring.advanced.app..noLog(..))"); // AspectJ가 제공하는 포인트컷 표현식
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
