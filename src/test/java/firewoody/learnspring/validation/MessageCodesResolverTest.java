package firewoody.learnspring.validation;

import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.assertThat;

class MessageCodesResolverTest {

    // MessageCodesResolver 인터페이스는 검증 오류 코드로 메시지 코드들을 생성한다.
    // 오류 코드는 구체적인 것에서 덜 구체적인 것 순으로 만드는 것이 좋다.
    // 메시지 생성 규칙
    // 1. 객체 오류
    //      code + "." + object name
    //      code
    // 2. 필드 오류
    //      code + "." + object name + "." + field
    //      code + "." + field
    //      code + "." + field type
    //      code
    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodesResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        assertThat(messageCodes).containsExactly(
                "required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required"
        );
    }
}
