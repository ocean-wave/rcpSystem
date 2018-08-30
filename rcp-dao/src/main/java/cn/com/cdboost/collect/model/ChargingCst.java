package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_charging_cst")
public class ChargingCst implements Serializable {
    /**
     * 标识id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 客户唯一标识
     */
    @Column(name = "customer_guid")
    private String customerGuid;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_contact")
    private String customerContact;

    /**
     * 微信号
     */
    @Column(name = "webchar_no")
    private String webcharNo;

    /**
     * 阿里支付用户id
     */
    @Column(name = "alipay_user_id")
    private String alipayUserId;

    /**
     * 阿里支付用户昵称
     */
    @Column(name = "alipay_nick_name")
    private String alipayNickName;

    /**
     * 车辆类别 1-电瓶车 2-电动车
     */
    @Column(name = "car_category")
    private Integer carCategory;

    /**
     * 车辆功率
     */
    @Column(name = "car_power")
    private Integer carPower;

    @Column(name = "remain_amount")
    private BigDecimal remainAmount;

    /**
     * 剩余充电次数(包月)
     */
    @Column(name = "remain_cnt")
    private Integer remainCnt;

    /**
     * 剩余充电次数(包月)
     */
    @Column(name = "expire_time")
    private Date expireTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否接收短信（0不接收，1接收）
     */
    @Column(name = "is_receive_sms")
    private Integer isReceiveSms;
    /**
     * '客户状态 1 正常 0 禁用'
     */
    @Column(name = "customer_state")
    private Integer customerState;

    public Integer getCustomerState() {
        return customerState;
    }

    public void setCustomerState(Integer customerState) {
        this.customerState = customerState;
    }

    /**
     * 获取标识id
     *
     * @return id - 标识id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置标识id
     *
     * @param id 标识id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取客户唯一标识
     *
     * @return customer_guid - 客户唯一标识
     */
    public String getCustomerGuid() {
        return customerGuid;
    }

    /**
     * 设置客户唯一标识
     *
     * @param customerGuid 客户唯一标识
     */
    public void setCustomerGuid(String customerGuid) {
        this.customerGuid = customerGuid;
    }

    /**
     * @return customer_name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return customer_contact
     */
    public String getCustomerContact() {
        return customerContact;
    }

    /**
     * @param customerContact
     */
    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    /**
     * 获取微信号
     *
     * @return webchar_no - 微信号
     */
    public String getWebcharNo() {
        return webcharNo;
    }

    /**
     * 设置微信号
     *
     * @param webcharNo 微信号
     */
    public void setWebcharNo(String webcharNo) {
        this.webcharNo = webcharNo;
    }

    public String getAlipayUserId() {
        return alipayUserId;
    }

    public void setAlipayUserId(String alipayUserId) {
        this.alipayUserId = alipayUserId;
    }

    public String getAlipayNickName() {
        return alipayNickName;
    }

    public void setAlipayNickName(String alipayNickName) {
        this.alipayNickName = alipayNickName;
    }

    /**
     * 获取车辆类别 1-电瓶车 2-电动车
     *
     * @return car_category - 车辆类别 1-电瓶车 2-电动车
     */
    public Integer getCarCategory() {
        return carCategory;
    }

    /**
     * 设置车辆类别 1-电瓶车 2-电动车
     *
     * @param carCategory 车辆类别 1-电瓶车 2-电动车
     */
    public void setCarCategory(Integer carCategory) {
        this.carCategory = carCategory;
    }

    /**
     * 获取车辆功率
     *
     * @return car_power - 车辆功率
     */
    public Integer getCarPower() {
        return carPower;
    }

    /**
     * 设置车辆功率
     *
     * @param carPower 车辆功率
     */
    public void setCarPower(Integer carPower) {
        this.carPower = carPower;
    }

    /**
     * @return remain_amount
     */
    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    /**
     * @param remainAmount
     */
    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    /**
     * 获取剩余充电次数(包月)
     *
     * @return remain_cnt - 剩余充电次数(包月)
     */
    public Integer getRemainCnt() {
        return remainCnt;
    }

    /**
     * 设置剩余充电次数(包月)
     *
     * @param remainCnt 剩余充电次数(包月)
     */
    public void setRemainCnt(Integer remainCnt) {
        this.remainCnt = remainCnt;
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
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getIsReceiveSms() {
        return isReceiveSms;
    }

    public void setIsReceiveSms(Integer isReceiveSms) {
        this.isReceiveSms = isReceiveSms;
    }
}