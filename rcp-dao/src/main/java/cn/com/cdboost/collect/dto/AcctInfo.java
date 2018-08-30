package cn.com.cdboost.collect.dto;

import java.math.BigDecimal;

/**
 * @author zc
 * @desc 电卡查询结果实体类
 * @create 2017-07-10 17:33
 **/
public class AcctInfo {

    /**
     * 设备cno
     */
    private String cno;

    /**
     * 用户唯一标识
     */
    private String customerNo;

    /**
     * 用户姓名
     */
    private String customerName;

    /**
     * 用户电话
     */
    private String customerContact;

    /**
     * 用户地址
     */
    private String customerAddr;

    /**
     * 表计户号
     */
    private String meterUserNo;

    /**
     * 用户类型
     */
    private String dictItemName;

    /**
     * 用户类型值
     */
    private String dictItemValue;

    /**
     * 电价码
     */
    private String priceSolsCode;

    /**
     * 剩余金额
     */
    private BigDecimal remainAmount;

    /**
     * IC卡开户状态
     */
    private int isAccount;

    /**
     * IC卡开户时间
     */
    private String acctDatetime;

    /**
     * 购电卡号
     */
    private long cardId;

    /**
     * 所属部门
     */
    private String orgName;

    /**
     * 是否启用
     */
    private int isEnabled;

    /**
     * 门牌编号
     */
    private String propertyName;

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

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

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }

    public String getDictItemName() {
        return dictItemName;
    }

    public void setDictItemName(String dictItemName) {
        this.dictItemName = dictItemName;
    }

    public String getDictItemValue() {
        return dictItemValue;
    }

    public void setDictItemValue(String dictItemValue) {
        this.dictItemValue = dictItemValue;
    }

    public String getPriceSolsCode() {
        return priceSolsCode;
    }

    public void setPriceSolsCode(String priceSolsCode) {
        this.priceSolsCode = priceSolsCode;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public int getIsAccount() {
        return isAccount;
    }

    public void setIsAccount(int isAccount) {
        this.isAccount = isAccount;
    }

    public String getAcctDatetime() {
        return acctDatetime;
    }

    public void setAcctDatetime(String acctDatetime) {
        this.acctDatetime = acctDatetime;
    }

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public int getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(int isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
