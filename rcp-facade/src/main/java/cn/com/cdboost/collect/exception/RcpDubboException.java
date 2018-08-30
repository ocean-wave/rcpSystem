package cn.com.cdboost.collect.exception;

/**
 * @author wt
 * @desc
 * @create in  2018/8/24
 **/
public class RcpDubboException extends RuntimeException {
    private int errorCode;
    private String errorMessage;

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }

    public RcpDubboException() {
        super();
    }

    public RcpDubboException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public RcpDubboException(int code, String message) {
        super(message);
        this.errorCode = code;
        this.errorMessage = message;
    }

    public RcpDubboException(int code, String message, Throwable cause) {
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
