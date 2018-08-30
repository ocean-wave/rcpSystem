package cn.com.cdboost.collect.exception;

/**
 * service层抛出的数据库异常，Controller层捕获
 */
public class DataBaseException extends RuntimeException{
    private int errorCode;
    private String errorMessage;

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }

    public DataBaseException() {
        super();
    }

    public DataBaseException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public DataBaseException(int code, String message) {
        super(message);
        this.errorCode = code;
        this.errorMessage = message;
    }

    public DataBaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = code;
        this.errorMessage = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
