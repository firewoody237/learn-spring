package firewoody.learnspring.database.dbaccess.jdbctemplate;

import firewoody.learnspring.database.dbaccess.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

// @Configuration
@RequiredArgsConstructor
public class JdbcTemplateConfig {

    private final DataSource dataSource;

    @Bean
    public ItemRepository itemRepository() {
        return new JdbcTemplateRepositoryV1(dataSource);
    }
}
