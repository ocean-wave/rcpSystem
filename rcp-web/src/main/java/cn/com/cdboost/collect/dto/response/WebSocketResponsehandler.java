package cn.com.cdboost.collect.dto.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 服务器跟前端定义的返回格式
 */
public class WebSocketResponsehandler extends WebSocketResponse{
@JSONField(format = "yyyy-MM-dd HH:mm:ss")
 public Date sysdate;

    public Date getSysdate() {
        return sysdate;
    }
    public void setSysdate(Date sysdate) {
        this.sysdate = sysdate;
    }
}
