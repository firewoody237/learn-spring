package firewoody.learnspring.message.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageConfig {

    @Bean
    public MessageSource messageSource() {
        // 메시지 관리 기능을 사용하려면 MessageSource를 스프링 빈으로 등록해야 한다.
        // 혹은 스프링 부트에서 application.properties에 `spring.messages.basename=messages,config.i18n.messages`로도 가능하다.
        // 빈으로 등록하지 않으면, 기본적으로 "messages"이름을 사용한다.
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // "messages"로 설정했으므로, messages.properties 파일을 읽는다.
        // 국제화 정보는 messages_en.properties, messages_ko.properties 파일을 읽는다.
        messageSource.setBasenames("messages", "errors");
        // 기본 인코딩 정보를 설정한다.
        messageSource.setDefaultEncoding("utf-8");
        return messageSource;
    }
}
