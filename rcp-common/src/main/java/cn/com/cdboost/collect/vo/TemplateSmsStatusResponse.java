package cn.com.cdboost.collect.vo;

import java.util.List;

/**
 * 网易云通知类和运营类短信发送状态
 */
public class TemplateSmsStatusResponse {
    private int code;
    private List<TemplateSmsStatusObj> obj;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<TemplateSmsStatusObj> getObj() {
        return obj;
    }

    public void setObj(List<TemplateSmsStatusObj> obj) {
        this.obj = obj;
    }
}
