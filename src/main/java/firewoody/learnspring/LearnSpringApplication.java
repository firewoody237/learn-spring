package firewoody.learnspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 컴포넌트 스캔 외 여러 기능
public class LearnSpringApplication
//        implements WebMvcConfigurer
{

    public static void main(String[] args) {
        SpringApplication.run(LearnSpringApplication.class, args); // 보통 `@SpringBootApplication`이 붙은 현재 클래스를 지정
    }

//    // 검증기의 글로벌 설정 (모든 컨트롤러에 적용)
//    @Override
//    public Validator getValidator() {
//        return new ItemValidator();
//    }
}
