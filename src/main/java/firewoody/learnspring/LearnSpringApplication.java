package firewoody.learnspring;

import firewoody.learnspring.boot.springproperties.configurationproperties.MyDataSourceConfigV1;
import firewoody.learnspring.boot.springproperties.configurationproperties.MyDataSourceConfigV2;
import firewoody.learnspring.boot.springproperties.configurationproperties.MyDataSourceConfigV3;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

//@Import(MyDataSourceConfigV1.class)
//@Import(MyDataSourceConfigV2.class)
@Import(MyDataSourceConfigV3.class)
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
