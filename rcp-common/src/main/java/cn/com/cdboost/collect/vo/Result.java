package cn.com.cdboost.collect.vo;

import java.io.Serializable;

/**
 * 返回给前端的包装类
 */
public class Result<T> implements Serializable{
    private static final long serialVersionUID = -7784581817460701362L;
    private static final int SUCCESS = 1;
    private static final int FAIL = 0;

    /**
     * 结果码
     */
    private int code;
    /**
     * 结果码对应的描述信息
     */
    private String message;

    /**
     * 请求返回数据
     * 如果请求失败，则数据返回null
     */
    private T data;

    public Result() {
        this.code = SUCCESS;
        this.message = "success";
    }

    public Result(String message) {
        this.code = SUCCESS;
        this.message = message;
    }

    public void error(String message) {
        this.code = FAIL;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
