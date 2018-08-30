package cn.com.cdboost.collect.dto.param;

/**
 * Created by Administrator on 2017/12/18 0018.
 */
public class ChangeMeterUpdateParam {
    /**
     * 客户唯一标识
     */
    private String customerNo;

    /**
     * 旧表cno
     */
    private String oldCno;

    /**
     * 新表表号
     */
    private String newMeterNo;

    /**
     * 旧表剩余金额
     */
    private String oldRemainAmount;

    /**
     * 新表剩余金额
     */
    private String newRemainAmount;

    /**
     * 旧表总
     */
    private String oldPower;

    /**
     * 新表总
     */
    private String newPower;

    /**
     * 新表倍率
     */
    private String newRatio;

    /**
     * 新表厂家
     */
    private String newFactory;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 换表备注
     */
    private String changeRemark;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getOldCno() {
        return oldCno;
    }

    public void setOldCno(String oldCno) {
        this.oldCno = oldCno;
    }

    public String getNewMeterNo() {
        return newMeterNo;
    }

    public void setNewMeterNo(String newMeterNo) {
        this.newMeterNo = newMeterNo;
    }

    public String getOldRemainAmount() {
        return oldRemainAmount;
    }

    public void setOldRemainAmount(String oldRemainAmount) {
        this.oldRemainAmount = oldRemainAmount;
    }

    public String getNewRemainAmount() {
        return newRemainAmount;
    }

    public void setNewRemainAmount(String newRemainAmount) {
        this.newRemainAmount = newRemainAmount;
    }

    public String getOldPower() {
        return oldPower;
    }

    public void setOldPower(String oldPower) {
        this.oldPower = oldPower;
    }

    public String getNewPower() {
        return newPower;
    }

    public void setNewPower(String newPower) {
        this.newPower = newPower;
    }

    public String getNewRatio() {
        return newRatio;
    }

    public void setNewRatio(String newRatio) {
        this.newRatio = newRatio;
    }

    public String getNewFactory() {
        return newFactory;
    }

    public void setNewFactory(String newFactory) {
        this.newFactory = newFactory;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getChangeRemark() {
        return changeRemark;
    }

    public void setChangeRemark(String changeRemark) {
        this.changeRemark = changeRemark;
    }
}
