package firewoody.learnspring.database.dbaccess.jpa;

import firewoody.learnspring.database.dbaccess.ItemSearchCond;
import firewoody.learnspring.database.dbaccess.ItemUpdateDto;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Item save(Item item);

    void update(Long itemId, ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findAll(ItemSearchCond cond);
}
