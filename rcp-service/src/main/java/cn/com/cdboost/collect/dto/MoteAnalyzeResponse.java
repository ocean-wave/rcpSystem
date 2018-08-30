package cn.com.cdboost.collect.dto;

/**
 * 采集分析，采集数据详情页面，采集器页签数据返回vo
 */
public class MoteAnalyzeResponse {
    /**
     * 节点地址
     */
    private String moteEui;

    /**
     * 安装位置
     */
    private String installAddr;

    /**
     * 电表数量
     */
    private Integer meterCount;

    /**
     * 失败数量
     */
    private Integer failCount;

    /**
     * 累计抄收轮次
     */
    private Integer runRound;

    /**
     * 开始轮次-时间
     */
    private String startTime;

    /**
     * 结束轮次-时间
     */
    private String endTime;

    public String getMoteEui() {
        return moteEui;
    }

    public void setMoteEui(String moteEui) {
        this.moteEui = moteEui;
    }

    public String getInstallAddr() {
        return installAddr;
    }

    public void setInstallAddr(String installAddr) {
        this.installAddr = installAddr;
    }

    public Integer getMeterCount() {
        return meterCount;
    }

    public void setMeterCount(Integer meterCount) {
        this.meterCount = meterCount;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    public Integer getRunRound() {
        return runRound;
    }

    public void setRunRound(Integer runRound) {
        this.runRound = runRound;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
