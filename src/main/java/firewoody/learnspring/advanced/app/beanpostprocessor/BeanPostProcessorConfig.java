package firewoody.learnspring.advanced.app.beanpostprocessor;

import firewoody.learnspring.advanced.app.logproject.logtrace.LogTrace;
import firewoody.learnspring.advanced.app.proxydecorator.v1.AppV1Config;
import firewoody.learnspring.advanced.app.proxydecorator.v1.proxyfactory.LogTraceAdvice;
import firewoody.learnspring.advanced.app.proxydecorator.v2.AppV2Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
//@Configuration
//@Import({AppV1Config.class, AppV2Config.class}) // v3는 자동으로 등록 됨
public class BeanPostProcessorConfig {

    @Bean
    public PackageLogTraceProxyProcessor logTraceProxyProcessor(LogTrace logTrace) {
        return new PackageLogTraceProxyProcessor("firewoody.learnspring.advanced.app", getAdvisor(logTrace));
    }

    private Advisor getAdvisor(LogTrace logTrace) {
        // pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        // advisor
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
