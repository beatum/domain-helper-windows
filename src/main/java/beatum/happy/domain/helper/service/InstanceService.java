package beatum.happy.domain.helper.service;

import beatum.happy.domain.helper.dataRep.IconfigRep;
import beatum.happy.domain.helper.model.Instance;
import beatum.happy.domain.helper.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Happy.He
 * @version 1.0
 * @date 7/5/2022 9:30 AM
 */

@Service
public class InstanceService implements IinstanceService {
    @Autowired
    @Qualifier("configRep")
    public IconfigRep configRep = null;

    /*
     * gets all instances
     * */
    public Response getAllInstances() {
        Response response = new Response();
        try {
            List<Instance> instances = configRep.getAllInstances();
            response.setObject(instances);
        } catch (Exception ex) {
            response.setResult(false);
            response.setObject(null);
        }
        return response;
    }

    /*
     * get instance by key
     * */
    public Response getInstanceByKey(String instanceKey) {
        Response response = new Response();
        try {
            Instance instance = configRep.getInstanceByKey(instanceKey);
            response.setObject(instance);
        } catch (Exception ex) {
            response.setResult(false);
            response.setObject(null);
        }
        return response;
    }

    /*
     * get instance by name
     * */
    public Response getInstanceByName(String instanceName) {
        Response response = new Response();
        try {
            Instance instance = configRep.getInstanceByName(instanceName);
            response.setObject(instance);
        } catch (Exception ex) {
            response.setResult(false);
            response.setObject(null);
        }
        return response;
    }
}
