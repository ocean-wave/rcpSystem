package cn.com.cdboost.collect.dto.response;

/**
 * 返回给微信的首页信息
 */
public class MainInfo4WeChat {
    /**
     * 是否绑定微信 1表示已绑定，0表示未绑定
     */
    private int isBind = 1;

    /**
     * 电表总数
     */
    private int electricMeterTotal;

    /**
     * 水表总数
     */
    private int waterMeterTotal;

    /**
     * 气表总数
     */
    private int gasMeterTotal;

    /**
     * 电表告警总数
     */
    private int electricAlarmTotal;

    /**
     * 水表告警总数
     */
    private int waterAlarmTotal;

    /**
     * 气表告警总数
     */
    private int gasAlarmTotal;

    /**
     * 客户地址
     */
    private String customerAddr;

    /**
     * 客户联系方式
     */
    private String customerContact;

    /**
     * 微信openId
     */
    private String openId;

    public int getIsBind() {
        return isBind;
    }

    public void setIsBind(int isBind) {
        this.isBind = isBind;
    }

    public int getElectricMeterTotal() {
        return electricMeterTotal;
    }

    public void setElectricMeterTotal(int electricMeterTotal) {
        this.electricMeterTotal = electricMeterTotal;
    }

    public int getWaterMeterTotal() {
        return waterMeterTotal;
    }

    public void setWaterMeterTotal(int waterMeterTotal) {
        this.waterMeterTotal = waterMeterTotal;
    }

    public int getGasMeterTotal() {
        return gasMeterTotal;
    }

    public void setGasMeterTotal(int gasMeterTotal) {
        this.gasMeterTotal = gasMeterTotal;
    }

    public int getElectricAlarmTotal() {
        return electricAlarmTotal;
    }

    public void setElectricAlarmTotal(int electricAlarmTotal) {
        this.electricAlarmTotal = electricAlarmTotal;
    }

    public int getWaterAlarmTotal() {
        return waterAlarmTotal;
    }

    public void setWaterAlarmTotal(int waterAlarmTotal) {
        this.waterAlarmTotal = waterAlarmTotal;
    }

    public int getGasAlarmTotal() {
        return gasAlarmTotal;
    }

    public void setGasAlarmTotal(int gasAlarmTotal) {
        this.gasAlarmTotal = gasAlarmTotal;
    }

    public String getCustomerAddr() {
        return customerAddr;
    }

    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
