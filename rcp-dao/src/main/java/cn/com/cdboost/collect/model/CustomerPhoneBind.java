package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_d_customer_phonebind")
public class CustomerPhoneBind implements Serializable{

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 移动电话
     */
    @Column(name = "mobile_phone")
    private String mobilePhone;

    /**
     * 客户档案编号
     */
    @Column(name = "customer_no")
    private String customerNo;

    /**
     * 创建人员
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 短信发送时间
     */
    @Column(name = "last_send_sms_time")
    private Date lastSendSmsTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return mobile_phone
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * @param mobilePhone
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * @return customer_no
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * @param customerNo
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getLastSendSmsTime() {
        return lastSendSmsTime;
    }

    public void setLastSendSmsTime(Date lastSendSmsTime) {
        this.lastSendSmsTime = lastSendSmsTime;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}