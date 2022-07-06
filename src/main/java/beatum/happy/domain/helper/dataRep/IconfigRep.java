package beatum.happy.domain.helper.dataRep;

import beatum.happy.domain.helper.model.Instance;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author Happy.He
 * @version 1.0
 * @date 7/4/2022 1:17 PM
 */
public interface IconfigRep {
    /*
     * Jdbc query
     * */
    public List<Instance> jdbcQuery(String sql, @Nullable Object... args) throws Exception;

    /*
     * gets all instances
     * */
    public List<Instance> getAllInstances() throws Exception;

    /*
     * get instance by key
     * */
    public Instance getInstanceByKey(String instanceKey) throws Exception;

    /*
     * get instance by name
     * */
    public Instance getInstanceByName(String instanceName) throws Exception;

}
