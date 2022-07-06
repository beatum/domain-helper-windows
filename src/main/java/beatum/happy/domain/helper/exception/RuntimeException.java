package beatum.happy.domain.helper.exception;

/**
 * @author Happy.He
 * @version 1.0
 * @date 7/5/2022 10:48 AM
 */
public class RuntimeException extends Exception {
    public RuntimeException(String message) {
        super(message);
    }

    public RuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
