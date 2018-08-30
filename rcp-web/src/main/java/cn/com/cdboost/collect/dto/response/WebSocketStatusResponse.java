package cn.com.cdboost.collect.dto.response;

/**
 * 服务器跟前端定义的返回格式,只是针对状态查询的返回格式
 */
public class WebSocketStatusResponse<T> extends WebSocketResponse<T>{
    /**
     * 后端任务状态，1已完成 0未完成
     */
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
