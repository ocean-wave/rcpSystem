package cn.com.cdboost.collect.dto.response;

import cn.com.cdboost.collect.dto.param.OnOffMeterVo;
import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 充值缴费，页面相关信息
 */
public class ChargeUserDetail {

    /**
     * 用户唯一标识
     */
    private String customerNo;

    /**
     * 用户名称
     */
    private String customerName;

    /**
     * 门牌编号
     */
    private String propertyName;

    /**
     * 联系电话
     */
    private String customerContact;

    /**
     * 用户地址
     */
    private String customerAddr;

    /**
     * 透支金额
     */
    private Integer overdraftAmount;

    /**
     * 告警阈值1
     */
    private BigDecimal alarmThreshold;

    /**
     * 告警阈值2
     */
    private BigDecimal alarmThreshold1;

    /**
     * 告警阈值3
     */
    private BigDecimal alarmThreshold2;

    /**
     * 机构名称
     */
    private String orgName;

    // 如下字段用户，最后一次购电记录

    /**
     * 支付guid
     */
    private String payGuid;

    /**
     * 购电日期
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date payDate;

    /**
     * 购电金额
     */
    private BigDecimal payMoney;

    /**
     * 剩余金额
     */
    private BigDecimal remainAmount;

    /**
     * 购电次数
     */
    private Integer payCount;

    /**
     * 剩余金额采集时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date amountCollectTime;

    /**
     * 购电金额是否下发APP充值需要下发，购电卡充值不需要要下发 0- 未写入 1-写入 2-取消远程 3-远程下发失败
     */
    private Integer writeMeter;

    /**
     * 售电人员名称
     */
    private String chargeUserName;

    /**
     * 支付方式
     */
    private String payMethod;

    /**
     * 流水号
     */
    private String serialNum;

    // 如下字段，用户远程合闸用
    private List<OnOffMeterVo> meters;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getCustomerAddr() {
        return customerAddr;
    }

    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr;
    }

    public Integer getOverdraftAmount() {
        return overdraftAmount;
    }

    public void setOverdraftAmount(Integer overdraftAmount) {
        this.overdraftAmount = overdraftAmount;
    }

    public BigDecimal getAlarmThreshold() {
        return alarmThreshold;
    }

    public void setAlarmThreshold(BigDecimal alarmThreshold) {
        this.alarmThreshold = alarmThreshold;
    }

    public BigDecimal getAlarmThreshold1() {
        return alarmThreshold1;
    }

    public void setAlarmThreshold1(BigDecimal alarmThreshold1) {
        this.alarmThreshold1 = alarmThreshold1;
    }

    public BigDecimal getAlarmThreshold2() {
        return alarmThreshold2;
    }

    public void setAlarmThreshold2(BigDecimal alarmThreshold2) {
        this.alarmThreshold2 = alarmThreshold2;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPayGuid() {
        return payGuid;
    }

    public void setPayGuid(String payGuid) {
        this.payGuid = payGuid;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public Integer getPayCount() {
        return payCount;
    }

    public void setPayCount(Integer payCount) {
        this.payCount = payCount;
    }

    public Date getAmountCollectTime() {
        return amountCollectTime;
    }

    public void setAmountCollectTime(Date amountCollectTime) {
        this.amountCollectTime = amountCollectTime;
    }

    public Integer getWriteMeter() {
        return writeMeter;
    }

    public void setWriteMeter(Integer writeMeter) {
        this.writeMeter = writeMeter;
    }

    public String getChargeUserName() {
        return chargeUserName;
    }

    public void setChargeUserName(String chargeUserName) {
        this.chargeUserName = chargeUserName;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public List<OnOffMeterVo> getMeters() {
        return meters;
    }

    public void setMeters(List<OnOffMeterVo> meters) {
        this.meters = meters;
    }
}
