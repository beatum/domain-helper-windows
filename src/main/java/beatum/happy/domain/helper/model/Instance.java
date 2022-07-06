package beatum.happy.domain.helper.model;

import java.io.Serializable;

/**
 * @author Happy.He
 * @version 1.0
 * @date 7/4/2022 1:09 PM
 */
public class Instance implements Serializable {
    public String getItem_key() {
        return item_key;
    }

    public void setItem_key(String item_key) {
        this.item_key = item_key;
    }

    public String getInstance_name() {
        return instance_name;
    }

    public void setInstance_name(String instance_name) {
        this.instance_name = instance_name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    private String item_key;
    private String instance_name;
    private String domain;
    private String account;
    private String password;
}
