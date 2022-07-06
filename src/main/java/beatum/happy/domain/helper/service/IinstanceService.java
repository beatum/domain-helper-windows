package beatum.happy.domain.helper.service;

import beatum.happy.domain.helper.model.Instance;
import beatum.happy.domain.helper.model.Response;

import java.util.List;

/**
 * @author Happy.He
 * @version 1.0
 * @date 7/5/2022 9:25 AM
 */
public interface IinstanceService {
    /*
     * gets all instances
     * */
    public Response getAllInstances();

    /*
     * get instance by key
     * */
    public Response getInstanceByKey(String instanceKey);

    /*
     * get instance by name
     * */
    public Response getInstanceByName(String instanceName);
}
