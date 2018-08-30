package cn.com.cdboost.collect.dto.response;

/**
 * 集中器心跳，下发，读取，主动推送给给前端的数据格式
 */
public class AFN07Response {
    /**
     * 集中器返回状态
     */
    private int status;

    /**
     * 心跳值
     */
    private String hbCycle;

    /**
     * 描述信息
     */
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getHbCycle() {
        return hbCycle;
    }

    public void setHbCycle(String hbCycle) {
        this.hbCycle = hbCycle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
