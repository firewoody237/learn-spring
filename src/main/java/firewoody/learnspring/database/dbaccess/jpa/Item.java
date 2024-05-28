package firewoody.learnspring.database.dbaccess.jpa;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity // JPA가 사용하는 애노테이션
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", length = 10)
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {
        // JPA는 public 또는 protected의 기본 생성자가 필수이다.
    }

    public Item(Long id, String itemName, Integer price, Integer quantity) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
