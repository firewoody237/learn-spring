package firewoody.learnspring.database.dbaccess.jdbctemplate;

import firewoody.learnspring.database.dbaccess.Item;
import firewoody.learnspring.database.dbaccess.ItemRepository;
import firewoody.learnspring.database.dbaccess.ItemSearchCond;
import firewoody.learnspring.database.dbaccess.ItemUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// NamedParameterJdbcTemplate는 파라미터 이름으로 바인딩하도록 도와준다.
// 기존 JdbcTemplate에서는 파라미터 순서에 따른 문제가 발생할 수도 있다.

@Slf4j
@Repository
public class JdbcTemplateRepositoryV2 implements ItemRepository {

    private final NamedParameterJdbcTemplate template;

    // JdbcTemplate은 DataSource가 필요하다.
    public JdbcTemplateRepositoryV2(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Item save(Item item) {
        // Map, SqlParameterSource, BeanPropertySqlParameterSource를 주로 사용
        String sql = "insert into item (item_name, price, quantity) values (:itemName, :price, :quantity)";
        // BeanPropertySqlParameterSource는 자바빈 프로퍼티 규약을 통해 자동으로 파라미터를 생성한다.
        SqlParameterSource param = new BeanPropertySqlParameterSource(item);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(sql, param, keyHolder);

        long key = keyHolder.getKey().longValue();
        item.setId(key);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        String sql = "update item set item_name=:itemName, price=:price, quantity=:quantity where id=:id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("itemName", updateParam.getItemName())
                .addValue("price", updateParam.getPrice())
                .addValue("quantity", updateParam.getQuantity())
                .addValue("id", itemId);
        template.update(sql, param);
    }

    @Override
    public Optional<Item> findById(Long id) {
        String sql = "select id, item_name, price, quantity from item where id = :id";
        try {
            // 결과가 없으면 `EmptyResultDataAccessException`이 발생
            // 결과가 둘 이상이면 `IncorrectResultSizeDataAccessException` 발생
            Map<String, Object> param = Map.of("id", id);
            Item item = template.queryForObject(sql, param, itemRowMapper());
            return Optional.of(item);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        SqlParameterSource param = new BeanPropertySqlParameterSource(cond);

        String sql = "select id, item_name, price, quantity from item";

        // 동적 쿼리
        if (StringUtils.hasText(itemName) || maxPrice != null) {
            sql += " where";
        }

        boolean andFlag = false;

        if (StringUtils.hasText(itemName)) {
            sql += " item_name like concat('%', :itemName, '%')";
            andFlag = true;
        }

        if (maxPrice != null) {
            if (andFlag) {
                sql += " and";
            }
            sql += " price <= :maxPrice";
        }

        log.info("sql={}", sql);
        return template.query(sql, param, itemRowMapper());
    }

    private RowMapper<Item> itemRowMapper() {
        // ResultSet의 결과를 받아서 자바빈 규약에 맞추어 데이터를 변환한다.
        // 그러므로 컬럼 이름이 완전히 다른 (예를들면) select item_name의 경우, select item_name as userName처럼 고쳐줘야 한다.
        // BeanPropertyRowMapper는 언더스코어 표기법을 카멜로 자동으로 변환 해 준다.
        return BeanPropertyRowMapper.newInstance(Item.class);
    }
}
