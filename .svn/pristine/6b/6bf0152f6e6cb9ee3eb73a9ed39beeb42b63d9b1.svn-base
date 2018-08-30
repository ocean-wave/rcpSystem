package cn.com.cdboost.collect.exception;

/**
 * 错误枚举值异常
 */
public class ErrorEnumValueException extends RuntimeException {
    private int errorCode;
    private String errorMessage;

    public ErrorEnumValueException() {
        super();
    }

    public ErrorEnumValueException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public ErrorEnumValueException(int code, String message) {
        super(message);
        this.errorCode = code;
        this.errorMessage = message;
    }

    public ErrorEnumValueException(int code, String message, Throwable cause) {
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
