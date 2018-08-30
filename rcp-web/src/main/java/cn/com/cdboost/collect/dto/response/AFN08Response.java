package cn.com.cdboost.collect.dto.response;

/**
 * 通断，主动推送给给前端的数据格式
 */
public class AFN08Response {

    /**
     * 整个抄表任务状态
     */
    private int taskStatus;

    /**
     * 操作结果 1表示操作成功，非1表示操作失败
     */
    private String state;

    /**
     * 设备cno
     */
    private String cno;

    /**
     * 整个任务处理数量
     */
    private int dealNum;

    /**
     * 整个任务成功数量
     */
    private int successfulNum;

    /**
     * 整个任务失败数量
     */
    private int failNum;

    /**
     * 整个任务未处理数量
     */
    private int undealNum;

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public int getDealNum() {
        return dealNum;
    }

    public void setDealNum(int dealNum) {
        this.dealNum = dealNum;
    }

    public int getSuccessfulNum() {
        return successfulNum;
    }

    public void setSuccessfulNum(int successfulNum) {
        this.successfulNum = successfulNum;
    }

    public int getFailNum() {
        return failNum;
    }

    public void setFailNum(int failNum) {
        this.failNum = failNum;
    }

    public int getUndealNum() {
        return undealNum;
    }

    public void setUndealNum(int undealNum) {
        this.undealNum = undealNum;
    }
}
