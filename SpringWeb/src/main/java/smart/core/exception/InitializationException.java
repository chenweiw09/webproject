package smart.core.exception;

/**
 * Created by Cruise on 2017/7/2.
 */
public class InitializationException extends Error {
    public InitializationException(){
        super();
    }

    public InitializationException(String msg){
        super(msg);
    }

    public InitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InitializationException(Throwable cause) {
        super(cause);
    }
}
