package firewoody.learnspring.validation.beanvalidator.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class Item {
    // jakarta.validation은 특정 구현에 관계없이 제공되는 표준 인터페이스
    // org.hibernate는 하이버네이트 validator 구현체를 사용할 때만 제공되는 검증 기능

    // 등록과 수정 시 검증 조건이 다를 때
    // 1. Bean Validation - groups 사용 (잘 사용하지 않음)
    // 2. ItemSaveForm, ItemUpdateForm과 같이 Form을 나눠서 사용 (주로 사용)
    @NotNull(groups = UpdateCheck.class)
    private long id;

    // 에러 메시지 찾는 순서
    // 1. 생성된 메시지 코드 순서대로 messageSource에서 메시지 찾기(errors.properties)
    // 2. 애노테이션의 message 속성 사용
    // 3. 라이브러리가 제공하는 기본 값 사용

    // 에러 메시지 명
    // 1. NotBlank.item.itemName
    // 2. NotBlank.itemName
    // 3. NotBlank.java.lang.String
    // 4. NotBlankp
    @NotBlank(message = "공백! {0}", groups = {SaveCheck.class, UpdateCheck.class})
    private String itemName;

    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    @Range(min = 1000, max = 1000000, groups = {SaveCheck.class, UpdateCheck.class})
    private Integer price;

    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    @Max(value = 9999, groups = SaveCheck.class)
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
