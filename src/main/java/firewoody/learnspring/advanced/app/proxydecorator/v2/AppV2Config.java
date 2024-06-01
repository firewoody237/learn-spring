package firewoody.learnspring.advanced.app.proxydecorator.v2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 참고 : Bean을 등록할 때, 컴포넌트 스캔을 통해 먼저 빈 등록을 진행 한 후 `@Configuration`에 있는 빈을 등록한다.(빈 등록 순서)
//      따라서 `@Configuration`에 빈 이름을 다르게 설정 해주더라도, 컴포넌트 스캔부에 중복된 빈 이름이 있다면
//      컴포넌트 스캔을 통해 빈 등록 -> 설정파일 스캔 순서가 되어 빈 이름 충돌이 발생할 수 있다.
//@Configuration
public class AppV2Config {

    @Bean(name = "proxyOrderServiceV2")
    public OrderServiceV2 orderServiceV2() {
        return new OrderServiceV2(orderRepositoryV2());
    }

    @Bean(name = "proxyOrderRepositoryV2")
    public OrderRepositoryV2 orderRepositoryV2() {
        return new OrderRepositoryV2();
    }
}
