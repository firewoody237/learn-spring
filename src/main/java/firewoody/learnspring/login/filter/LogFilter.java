package firewoody.learnspring.login.filter;

// HTTP 요청 -> WAS -> 필터 -> 서블릿 -> 컨트롤러
// 필터는 서블릿이 제공하는 기능이다.
// 여러개의 필터를 체인이 가능하다.

// init() : 필터 초기화 메서드, 서블릿 컨테이너가 생성될 때 호출된다.
// doFilter() : 고객의 요청이 올 때마다 호출된다. 필터의 로직을 구현한다.
// destroy() : 서블릿 컨테이너가 종료될 때 호출된다.

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;


@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }

    // HTTP 요청이 아닌 것까지 고려하여 만든 인터페이스이므로 ServletRequest를 사용한다.
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString();

        try {
            log.info("REQUEST [{}][{}]", uuid, requestURI);
            filterChain.doFilter(servletRequest, servletResponse); // 다음 필터가 있으면 필터를 호출하고, 없으면 서블릿을 호출한다. 이 과정에서 request와 rseponse를 교체할 수 있다.
            // 만약 체이닝을 하지 않고 return;을 해버리면, 서블릿까지 가지 않고 종료된다.
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("RESPONSE [{}][{}]", uuid, requestURI);
        }
    }
}
