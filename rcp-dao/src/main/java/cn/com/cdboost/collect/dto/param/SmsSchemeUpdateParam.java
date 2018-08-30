package cn.com.cdboost.collect.dto.param;

import java.util.Date;

/**
 * 短信发送方案更新对象
 */
public class SmsSchemeUpdateParam {
    /**
     * 用户唯一标识
     */
    private String customerNo;

    /**
     * 设备cno
     */
    private String cno;

    /**
     * 更新对象类型 1-手机号码 2-微信
     */
    private Integer objType;

    /**
     * 手机号或者微信openId
     */
    private String sendObj;

    /**
     * 发送状态：0：已发送，1：发送完成
     */
    private Integer smsscStatus;

    /**
     * 最后发送时间
     */
    private Date lastSendTime;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public Integer getObjType() {
        return objType;
    }

    public void setObjType(Integer objType) {
        this.objType = objType;
    }

    public String getSendObj() {
        return sendObj;
    }

    public void setSendObj(String sendObj) {
        this.sendObj = sendObj;
    }

    public Integer getSmsscStatus() {
        return smsscStatus;
    }

    public void setSmsscStatus(Integer smsscStatus) {
        this.smsscStatus = smsscStatus;
    }

    public Date getLastSendTime() {
        return lastSendTime;
    }

    public void setLastSendTime(Date lastSendTime) {
        this.lastSendTime = lastSendTime;
    }
}
