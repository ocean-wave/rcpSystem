package cn.com.cdboost.collect.exception;

/**
 * Created by zc
 */
public class ParameterException extends RuntimeException{
    private  int errorCode;
    private String desc;
    public ParameterException(int value, String desc) {
        super(desc);
        this.setErrorCode(value);
    }
  public ParameterException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.setErrorCode(errorCode);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
