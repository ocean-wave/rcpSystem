package cn.com.cdboost.collect.dto;



/**
 * 重点采集分析原始数据
 */
public class KeyCollectionOriginalDTO {

    private Long id;

    /**
     * 采集时间
     */
    private String collectDate;
    /**
     * 采集类型
     */
    private String loraPort;
    /**
     * 批次号
     */
    private String queueGuid;
    /**
     * 产品号
     */
    private String cno;
    /**
     * 次数
     */
    private Integer counts;

    /**
     * 成功率
     */
    private String rate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public String getCno() {

        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getQueueGuid() {

        return queueGuid;
    }

    public void setQueueGuid(String queueGuid) {
        this.queueGuid = queueGuid;
    }

    public String getLoraPort() {

        return loraPort;
    }

    public void setLoraPort(String loraPort) {
        this.loraPort = loraPort;
    }

    public String getCollectDate() {

        return collectDate;
    }

    public void setCollectDate(String collectDate) {
        this.collectDate = collectDate;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
