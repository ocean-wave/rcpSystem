package cn.com.cdboost.collect.exception;

/**
 * service层抛出的业务异常，Controller层捕获
 */
public class BusinessException extends RuntimeException{
    private int errorCode;
    private String errorMessage;

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.errorCode = code;
        this.errorMessage = message;
    }

    public BusinessException(int code, String message, Throwable cause) {
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
