package cn.com.cdboost.collect.dto;

import java.math.BigDecimal;

public class MonitorDeviceDto {
    private String chargingPlieGuid;
    private Integer runState;
    private String deviceNo;
    private String deviceName;
    private String commNo;
    private BigDecimal percent = BigDecimal.ZERO;
    /**
     * 开始充电时间
     */
    private String startTime;
    /**
     *设备类型
     */
    private Integer carCategory;
    /**
     *计费方式
     */
    private Integer payCategory;
    /**
     * 充电扣除费用
     */
    private BigDecimal deductMoney;
    /**
     * 充电时长
     */
    private Integer useTime;
    /**
     * 剩余时长
     */
    private BigDecimal remainTime = BigDecimal.ZERO;
    /**
     * 剩余金额
     */
    private BigDecimal remainAmount = BigDecimal.ZERO;
    private BigDecimal current = BigDecimal.ZERO;
    private BigDecimal voltage = BigDecimal.ZERO;
    private BigDecimal power = BigDecimal.ZERO;
    private String updateTime;
    /**
     * 剩余电量
     */
    private BigDecimal remainElectric;
    private String webcharNo;
    private String customerGuid;
    private String customerName;
    //充电使用记录唯一标识
    private String chargingGuid;
    /**
     * 1-按时充电 2-按电量充电 3-充满断电
     */
    private Integer chargingWay;

    /**
     * 0 - 不限制 其他值标识充电时长，单位小时
     */
    private Integer chargingTime;

    /**
     * 端口号
     */
    private String port;

    /**
     * 应充电量
     */
    private Integer chargingPower;

    /**
     * 已使用电量
     */
    private BigDecimal usePower = BigDecimal.ZERO;
    /**
     * 0 -离线 1-在线
     */
    private Integer online;

    private String openNo;

    private String projectName;

    /**
     * 开启方式 1-微信 2-支付宝 3-IC卡
     */
    private Integer openMeans;

    //功率
    private Integer maxPower;
    //
    private BigDecimal schemeMoney;
    private Integer schememTime;
    private Integer chargingCnt;
    //上行信号强度
    private Integer rssi;
    //上行信噪比
    private BigDecimal snr;


    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getChargingPlieGuid() {
        return chargingPlieGuid;
    }

    public void setChargingPlieGuid(String chargingPlieGuid) {
        this.chargingPlieGuid = chargingPlieGuid;
    }

    public String getCommNo() {
        return commNo;
    }

    public void setCommNo(String commNo) {
        this.commNo = commNo;
    }

    public Integer getRunState() {
        return runState;
    }

    public void setRunState(Integer runState) {
        this.runState = runState;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public BigDecimal getDeductMoney() {
        return deductMoney;
    }

    public void setDeductMoney(BigDecimal deductMoney) {
        this.deductMoney = deductMoney;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

    public Integer getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(Integer carCategory) {
        this.carCategory = carCategory;
    }

    public Integer getPayCategory() {
        return payCategory;
    }

    public void setPayCategory(Integer payCategory) {
        this.payCategory = payCategory;
    }

    public Integer getUseTime() {
        return useTime;
    }

    public void setUseTime(Integer useTime) {
        this.useTime = useTime;
    }

    public BigDecimal getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(BigDecimal remainTime) {
        this.remainTime = remainTime;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public BigDecimal getCurrent() {
        return current;
    }

    public void setCurrent(BigDecimal current) {
        this.current = current;
    }

    public BigDecimal getVoltage() {
        return voltage;
    }

    public void setVoltage(BigDecimal voltage) {
        this.voltage = voltage;
    }

    public BigDecimal getPower() {
        return power;
    }

    public void setPower(BigDecimal power) {
        this.power = power;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getRemainElectric() {
        return remainElectric;
    }

    public void setRemainElectric(BigDecimal remainElectric) {
        this.remainElectric = remainElectric;
    }

    public String getWebcharNo() {
        return webcharNo;
    }

    public void setWebcharNo(String webcharNo) {
        this.webcharNo = webcharNo;
    }

    public String getCustomerGuid() {
        return customerGuid;
    }

    public void setCustomerGuid(String customerGuid) {
        this.customerGuid = customerGuid;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getChargingGuid() {
        return chargingGuid;
    }

    public void setChargingGuid(String chargingGuid) {
        this.chargingGuid = chargingGuid;
    }

    public Integer getChargingWay() {
        return chargingWay;
    }

    public void setChargingWay(Integer chargingWay) {
        this.chargingWay = chargingWay;
    }

    public Integer getChargingTime() {
        return chargingTime;
    }

    public void setChargingTime(Integer chargingTime) {
        this.chargingTime = chargingTime;
    }

    public Integer getChargingPower() {
        return chargingPower;
    }

    public void setChargingPower(Integer chargingPower) {
        this.chargingPower = chargingPower;
    }

    public BigDecimal getUsePower() {
        return usePower;
    }

    public void setUsePower(BigDecimal usePower) {
        this.usePower = usePower;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public String getOpenNo() {
        return openNo;
    }

    public void setOpenNo(String openNo) {
        this.openNo = openNo;
    }

    public Integer getOpenMeans() {
        return openMeans;
    }

    public void setOpenMeans(Integer openMeans) {
        this.openMeans = openMeans;
    }

    public Integer getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(Integer maxPower) {
        this.maxPower = maxPower;
    }

    public BigDecimal getSchemeMoney() {
        return schemeMoney;
    }

    public void setSchemeMoney(BigDecimal schemeMoney) {
        this.schemeMoney = schemeMoney;
    }

    public Integer getSchememTime() {
        return schememTime;
    }

    public void setSchememTime(Integer schememTime) {
        this.schememTime = schememTime;
    }

    public Integer getChargingCnt() {
        return chargingCnt;
    }

    public void setChargingCnt(Integer chargingCnt) {
        this.chargingCnt = chargingCnt;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getRssi() {
        return rssi;
    }

    public void setRssi(Integer rssi) {
        this.rssi = rssi;
    }

    public BigDecimal getSnr() {
        return snr;
    }

    public void setSnr(BigDecimal snr) {
        this.snr = snr;
    }
}
