package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_d_sms")
public class Sms implements Serializable {
    private static final long serialVersionUID = 4696397432931684223L;
    /**
     * 唯一标识ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 联系电话
     */
    @Column(name = "customer_contact")
    private String customerContact;

    /**
     * 用户编号
     */
    @Column(name = "customer_no")
    private String customerNo;

    /**
     * 发送的短信内容
     */
    @Column(name = "message_content")
    private String messageContent;

    /**
     * 创建人员ID
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 发送时间
     */
    @Column(name = "send_time")
    private Date sendTime;

    /**
     * 1-告警 2-充值到账提醒 3-充值成功 4-验证码
     */
    @Column(name = "msg_type")
    private Integer msgType;

    /**
     * 消息的发送状态 0 待发送 1标识已发送 2标识发送失败
     */
    @Column(name = "message_status")
    private Integer messageStatus;

    /**
     * 短信生成方式 0标识手动生成 1标识自动生成
     */
    @Column(name = "is_auto_buider")
    private Integer isAutoBuider;

    /**
     * 短信服务的厂商
     */
    @Column(name = "sms_factory")
    private Integer smsFactory;

    /**
     * 短信发送标识
     */
    @Column(name = "sms_uuid")
    private String smsUuid;

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
     * 获取联系电话
     *
     * @return customer_contact - 联系电话
     */
    public String getCustomerContact() {
        return customerContact;
    }

    /**
     * 设置联系电话
     *
     * @param customerContact 联系电话
     */
    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
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

    /**
     * 获取发送的短信内容
     *
     * @return message_content - 发送的短信内容
     */
    public String getMessageContent() {
        return messageContent;
    }

    /**
     * 设置发送的短信内容
     *
     * @param messageContent 发送的短信内容
     */
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    /**
     * 获取创建人员ID
     *
     * @return create_user_id - 创建人员ID
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建人员ID
     *
     * @param createUserId 创建人员ID
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
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

    /**
     * 获取发送时间
     *
     * @return send_time - 发送时间
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * 设置发送时间
     *
     * @param sendTime 发送时间
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    /**
     * 获取消息的发送状态 0 待发送 1标识已发送 2标识发送失败
     *
     * @return message_status - 消息的发送状态 0 待发送 1标识已发送 2标识发送失败
     */
    public Integer getMessageStatus() {
        return messageStatus;
    }

    /**
     * 设置消息的发送状态 0 待发送 1标识已发送 2标识发送失败
     *
     * @param messageStatus 消息的发送状态 0 待发送 1标识已发送 2标识发送失败
     */
    public void setMessageStatus(Integer messageStatus) {
        this.messageStatus = messageStatus;
    }

    /**
     * 获取短信生成方式 0标识手动生成 1标识自动生成
     *
     * @return is_auto_buider - 短信生成方式 0标识手动生成 1标识自动生成
     */
    public Integer getIsAutoBuider() {
        return isAutoBuider;
    }

    /**
     * 设置短信生成方式 0标识手动生成 1标识自动生成
     *
     * @param isAutoBuider 短信生成方式 0标识手动生成 1标识自动生成
     */
    public void setIsAutoBuider(Integer isAutoBuider) {
        this.isAutoBuider = isAutoBuider;
    }

    /**
     * 获取短信服务的厂商
     *
     * @return sms_factory - 短信服务的厂商
     */
    public Integer getSmsFactory() {
        return smsFactory;
    }

    /**
     * 设置短信服务的厂商
     *
     * @param smsFactory 短信服务的厂商
     */
    public void setSmsFactory(Integer smsFactory) {
        this.smsFactory = smsFactory;
    }

    public String getSmsUuid() {
        return smsUuid;
    }

    public void setSmsUuid(String smsUuid) {
        this.smsUuid = smsUuid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}