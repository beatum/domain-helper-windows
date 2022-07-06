package beatum.happy.domain.helper.model;

import java.io.Serializable;

/**
 * @author Happy.He
 * @version 1.0
 * @date 7/5/2022 10:50 AM
 */
public class Response implements Serializable {
    private boolean result = true;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    private String Message;
    private Object object;
}
