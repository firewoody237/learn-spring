package firewoody.learnspring.validation.beanvalidator.domain;

import lombok.Data;

@Data
public class Product {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;
}
