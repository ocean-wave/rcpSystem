package cn.com.cdboost.collect.param;

import java.io.Serializable;

/**
 * @author wt
 * @desc
 * @create in  2018/9/6
 **/
public class BaseSychronizeReseponse implements Serializable{
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
