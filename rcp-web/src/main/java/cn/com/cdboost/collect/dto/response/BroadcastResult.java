package cn.com.cdboost.collect.dto.response;

import cn.com.cdboost.collect.vo.Result;

/**
 * 广播
 */
public class BroadcastResult<T> extends Result<T> {
    /**
     * 中间件指令发送返回状态
     */
    private int state;

    /**
     * 指令发送时间
     */
    private String date;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
