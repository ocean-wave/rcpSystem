package cn.com.cdboost.collect.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 采集分析，重点用户返回vo
 */
public class ImpCollectionAnalysisDTO {
    /**
     * 抄表日期
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date collectTime;

    /**
     * 抄表总数
     */
    private Integer sumCount;

    /**
     * 失败数
     */
    private Integer failCount;

    /**
     * 上报率
     */
    private String radioRate;

    /**
     * 召测率
     */
    private String callRate;

    /**
     * 成功率
     */
    private String successRate;

    /**
     * 抄表queueGuid
     */
    private String queueGuid;

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    public Integer getSumCount() {
        return sumCount;
    }

    public void setSumCount(Integer sumCount) {
        this.sumCount = sumCount;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
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

    public String getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(String successRate) {
        this.successRate = successRate;
    }

    public String getQueueGuid() {
        return queueGuid;
    }

    public void setQueueGuid(String queueGuid) {
        this.queueGuid = queueGuid;
    }
}
