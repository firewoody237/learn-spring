package firewoody.learnspring.database.dbaccess.mybatis;

import firewoody.learnspring.database.dbaccess.Item;
import firewoody.learnspring.database.dbaccess.ItemSearchCond;
import firewoody.learnspring.database.dbaccess.ItemUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

// `@Mapper` 어노테이션을 통해 마이바티스가 인식
@Mapper
public interface ItemMapper {

    void save(Item item);

    void update(@Param("id") Long id, @Param("updateParam") ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findAll(ItemSearchCond itemSearch);

    // 어노테이션을 사용할 수 있다. (동적 쿼리는 불가능)
    @Select("select * from item")
    Optional<Item> simpleFind(Long id);
}
