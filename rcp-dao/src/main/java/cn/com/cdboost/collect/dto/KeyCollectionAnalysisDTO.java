package cn.com.cdboost.collect.dto;



/**
 * 重点采集分析
 */
public class KeyCollectionAnalysisDTO {


    //时间
    private String collectTime;
    private String sumCount;
    private String failCount;
    private String radioRate;
    private String callRate;
    private String successRate;
    private String queueGuid;
    private Long id;

    public String getQueueGuid() {
        return queueGuid;
    }

    public void setQueueGuid(String queueGuid) {
        this.queueGuid = queueGuid;
    }

    public String getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(String successRate) {
        this.successRate = successRate;
    }

    public String getRadioRate() {

        return radioRate;
    }

    public void setRadioRate(String radioRate) {
        this.radioRate = radioRate;
    }

    public String getCallRate() {

        return callRate;
    }

    public void setCallRate(String callRate) {
        this.callRate = callRate;
    }

    public String getFailCount() {

        return failCount;
    }

    public void setFailCount(String failCount) {
        this.failCount = failCount;
    }

    public String getSumCount() {

        return sumCount;
    }

    public void setSumCount(String sumCount) {
        this.sumCount = sumCount;
    }

    public String getCollectTime() {

        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
