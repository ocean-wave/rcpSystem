package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_d_customerinfo_his")
public class CustomerInfoHis implements Serializable {
    private static final long serialVersionUID = 1302853051017654485L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户编号
     */
    @Column(name = "customer_no")
    private String customerNo;

    /**
     * 用户名称
     */
    @Column(name = "customer_name")
    private String customerName;

    /**
     * 联系电话
     */
    @Column(name = "customer_contact")
    private String customerContact;

    /**
     * 用户地址
     */
    @Column(name = "customer_addr")
    private String customerAddr;

    /**
     * 单位名称
     */
    @Column(name = "property_name")
    private String propertyName;

    /**
     * 是否有效
     */
    @Column(name = "is_enabled")
    private Integer isEnabled;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 是否自动发催费短信（0-否，1-是）
     */
    @Column(name = "is_auto_sms")
    private Integer isAutoSms;

    @Column(name = "last_send_sms_time")
    private Date lastSendSmsTime;

    /**
     * 删除时间
     */
    @Column(name = "delete_time")
    private Date deleteTime;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
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

    /**
     * 获取用户名称
     *
     * @return customer_name - 用户名称
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 设置用户名称
     *
     * @param customerName 用户名称
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
     * 获取用户地址
     *
     * @return customer_addr - 用户地址
     */
    public String getCustomerAddr() {
        return customerAddr;
    }

    /**
     * 设置用户地址
     *
     * @param customerAddr 用户地址
     */
    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr;
    }

    /**
     * 获取单位名称
     *
     * @return property_name - 单位名称
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * 设置单位名称
     *
     * @param propertyName 单位名称
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * 获取是否有效
     *
     * @return is_enabled - 是否有效
     */
    public Integer getIsEnabled() {
        return isEnabled;
    }

    /**
     * 设置是否有效
     *
     * @param isEnabled 是否有效
     */
    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
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
     * 获取创建人
     *
     * @return create_user_id - 创建人
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建人
     *
     * @param createUserId 创建人
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取是否自动发催费短信（0-否，1-是）
     *
     * @return is_auto_sms - 是否自动发催费短信（0-否，1-是）
     */
    public Integer getIsAutoSms() {
        return isAutoSms;
    }

    /**
     * 设置是否自动发催费短信（0-否，1-是）
     *
     * @param isAutoSms 是否自动发催费短信（0-否，1-是）
     */
    public void setIsAutoSms(Integer isAutoSms) {
        this.isAutoSms = isAutoSms;
    }

    /**
     * @return last_send_sms_time
     */
    public Date getLastSendSmsTime() {
        return lastSendSmsTime;
    }

    /**
     * @param lastSendSmsTime
     */
    public void setLastSendSmsTime(Date lastSendSmsTime) {
        this.lastSendSmsTime = lastSendSmsTime;
    }

    /**
     * 获取删除时间
     *
     * @return delete_time - 删除时间
     */
    public Date getDeleteTime() {
        return deleteTime;
    }

    /**
     * 设置删除时间
     *
     * @param deleteTime 删除时间
     */
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }
}