package shipping.exception;

public class CustomServiceException extends Exception {

    public CustomServiceException(String str){
        super(str);
    }

    public CustomServiceException(String str, Throwable cause){
        super(str, cause);
    }

    public CustomServiceException(Throwable cause){
        super(cause);
    }

}
