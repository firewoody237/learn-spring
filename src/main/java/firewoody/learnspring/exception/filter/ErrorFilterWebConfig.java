package firewoody.learnspring.exception.filter;

import firewoody.learnspring.exception.interceptor.ErrorLogInterceptor;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class ErrorFilterWebConfig implements WebMvcConfigurer {

    // Filter
    // 1. WAS <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러(예외발생)
    // 2. WAS `/error-page/500` 다시 요청 -> 필터 -> 서블릿 -> 인터셉터 -> 컨트롤러 -> view
//    @Bean
    public FilterRegistrationBean errorFilterWebConfig() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new ErrorLogFilter());
        filterFilterRegistrationBean.setOrder(2);
        filterFilterRegistrationBean.addUrlPatterns("/*");
        filterFilterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST); // 클라이언트 요청에 필터가 호출된다.
        return filterFilterRegistrationBean;
    }

    // Interceptor
    // 1. WAS -> 필터 -> 서블릿 -> 인터셉터 -> 컨트롤러
    // 2. WAS(전파) <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러(예외발생)
    // 3. WAS 오류 페이지 확인
    // 4. WAS -> 필터(X) -> 서블릿 -> 인터셉터(X) -> 컨트롤러 ->View
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ErrorLogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error", "/error-page/**");
    }
}
