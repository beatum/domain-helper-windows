package beatum.happy.domain.helper;

import beatum.happy.domain.helper.dataRep.ConfigRep;
import beatum.happy.domain.helper.forms.Main;
import beatum.happy.domain.helper.service.InstanceService;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author Happy.He
 * @version 1.0
 * {@code @date} 7/1/2022 3:28 PM
 */
@Configuration
@PropertySource("classpath:application.properties")
public class SpringContext {
    @Value("${app.application.name}")
    String titleOfMainForm;

    @Autowired
    private Environment environment;


    //datasource for sqlite db
    @Bean(name = "datasourceOfSqlite")
    public DataSource datasourceOfSqlite() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(environment.getProperty("database.sqlite.datasource.driverClassName"));
        dataSource.setJdbcUrl(environment.getProperty("database.sqlite.datasource.url"));
        return dataSource;
    }

    @Bean(name = "jdbcTemplateSqlite")
    public JdbcTemplate jdbcTemplateOfSqlite(@Qualifier("datasourceOfSqlite") DataSource db) {
        return new JdbcTemplate(db);
    }

    @Bean(name = "configRep")
    public ConfigRep configRep(@Qualifier("jdbcTemplateSqlite") JdbcTemplate jdbcTemplate) {
        return new ConfigRep(jdbcTemplate);
    }

    @Bean(name = "instanceService")
    public InstanceService instanceService(){
        return new InstanceService();
    }

    @Bean(name = "mainFrom")
    public Main createMainForm(@Qualifier("instanceService")InstanceService instanceService) {
        return new Main(titleOfMainForm , instanceService);
    }

}
