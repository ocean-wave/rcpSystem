package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_d_smsscheme")
public class SmsScheme implements Serializable{
    /**
     * 唯一标识ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户编号
     */
    @Column(name = "customer_no")
    private String customerNo;

    /**
     * 移动电话或微信openid，根据objType来判断
     */
    @Column(name = "send_obj")
    private String sendObj;

    /**
     * 1-手机号码 2-微信
     */
    @Column(name = "obj_type")
    private Integer objType;

    /**
     * 短信发送次数
     */
    @Column(name = "send_count")
    private Integer sendCount;

    /**
     * 最后发送时间
     */
    @Column(name = "last_send_time")
    private Date lastSendTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;


    @Column(name = "smssc_status")
    private Integer smsscStatus;

    /**
     * 告警级别（1表示告警级别1,2表示告警级别2）
     */
    @Column(name = "alarm_level")
    private Integer alarmLevel;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 获取唯一标识ID
     *
     * @return id - 唯一标识ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置唯一标识ID
     *
     * @param id 唯一标识ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户编号
     *
     * @return customer_no - 用户编号
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * 设置用户编号
     *
     * @param customerNo 用户编号
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getSendObj() {
        return sendObj;
    }

    public void setSendObj(String sendObj) {
        this.sendObj = sendObj;
    }

    public Integer getObjType() {
        return objType;
    }

    public void setObjType(Integer objType) {
        this.objType = objType;
    }

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    /**
     * 获取最后发送时间
     *
     * @return last_send_time - 最后发送时间
     */
    public Date getLastSendTime() {
        return lastSendTime;
    }

    /**
     * 设置最后发送时间
     *
     * @param lastSendTime 最后发送时间
     */
    public void setLastSendTime(Date lastSendTime) {
        this.lastSendTime = lastSendTime;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Integer getSmsscStatus() {
        return smsscStatus;
    }

    public void setSmsscStatus(Integer smsscStatus) {
        this.smsscStatus = smsscStatus;
    }

    public Integer getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(Integer alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}