package shipping.exception;

public class CustomDAOException extends Exception {

    //public CustomDAOException(){}

    public CustomDAOException(String str){
        super(str);
    }

    public CustomDAOException(String str, Throwable cause){
        super(str, cause);
    }

    public CustomDAOException(Throwable cause){
        super(cause);
    }

}
