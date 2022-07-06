package beatum.happy.domain.helper.dataRep;

import beatum.happy.domain.helper.model.Instance;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Happy.He
 * @version 1.0
 * @date 7/4/2022 1:19 PM
 */
@Repository
public class ConfigRep implements IconfigRep {
    public ConfigRep(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplateOfSqlite = jdbcTemplate;
    }

    //jdbc template
    private JdbcTemplate jdbcTemplateOfSqlite;

    /*
     * Jdbc query
     * */
    public List<Instance> jdbcQuery(String sql, @Nullable Object... args) throws SQLException {
        return (List<Instance>) jdbcTemplateOfSqlite.query(sql, new BeanPropertyRowMapper<Instance>(Instance.class), args);
    }

    /*
     * gets all instances
     * */
    public List<Instance> getAllInstances() throws Exception {
        return this.jdbcQuery("select * from instance", null);
    }

    /*
     * get instance by key
     * */
    public Instance getInstanceByKey(String instanceKey) throws Exception {
        List<Instance> instances = this.jdbcQuery("select * from instance where item_key = ?", instanceKey);
        if (null == instances || instances.size() <= 0) {
            return null;
        }
        return instances.get(0);
    }

    /*
     * get instance by name
     * */
    public Instance getInstanceByName(String instanceName) throws Exception {
        List<Instance> instances = this.jdbcQuery("select * from instance where instance_name = ?", instanceName);
        if (null == instances || instances.size() <= 0) {
            return null;
        }
        return instances.get(0);
    }


   /*
    private final RowMapper<Instance> instanceRowMapper = (resultSet, rowNum) -> {
        Instance instance = new Instance();
        instance.setInstance_name(resultSet.getString("item_key"));
        instance.setInstance_name(resultSet.getString("instance_name"));
        instance.setInstance_name(resultSet.getString("domain"));
        instance.setInstance_name(resultSet.getString("account"));
        instance.setInstance_name(resultSet.getString("password"));
        return instance;
    };
    */
}
