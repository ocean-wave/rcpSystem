package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_charging_withdraw_cash")
public class ChargingWithdrawCash implements Serializable{
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户唯一标识
     */
    @Column(name = "customer_guid")
    private String customerGuid;

    /**
     * 微信openId
     */
    @Column(name = "webchar_no")
    private String webcharNo;

    /**
     * 商户订单号
     */
    @Column(name = "partner_trade_no")
    private String partnerTradeNo;

    /**
     * 提现金额
     */
    private BigDecimal amount;

    /**
     * 提现后剩余金额
     */
    @Column(name = "after_remain_amount")
    private BigDecimal afterRemainAmount;

    /**
     * 1 提现成功， 2提现失败
     */
    private Integer status;

    /**
     * 微信接口返回的错误代码
     */
    @Column(name = "error_code")
    private String errorCode;

    /**
     * 微信接口返回的错误代码描述
     */
    @Column(name = "error_msg")
    private String errorMsg;

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
     * 前端请求唯一标识
     */
    private String guid;

    /**
     * 提现类型 1微信提现，2支付宝提现
     */
    private Integer type;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerGuid() {
        return customerGuid;
    }

    public void setCustomerGuid(String customerGuid) {
        this.customerGuid = customerGuid;
    }

    /**
     * 获取微信openId
     *
     * @return webchar_no - 微信openId
     */
    public String getWebcharNo() {
        return webcharNo;
    }

    /**
     * 设置微信openId
     *
     * @param webcharNo 微信openId
     */
    public void setWebcharNo(String webcharNo) {
        this.webcharNo = webcharNo;
    }

    /**
     * 获取商户订单号
     *
     * @return partner_trade_no - 商户订单号
     */
    public String getPartnerTradeNo() {
        return partnerTradeNo;
    }

    /**
     * 设置商户订单号
     *
     * @param partnerTradeNo 商户订单号
     */
    public void setPartnerTradeNo(String partnerTradeNo) {
        this.partnerTradeNo = partnerTradeNo;
    }

    /**
     * 获取提现金额
     *
     * @return amount - 提现金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置提现金额
     *
     * @param amount 提现金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAfterRemainAmount() {
        return afterRemainAmount;
    }

    public void setAfterRemainAmount(BigDecimal afterRemainAmount) {
        this.afterRemainAmount = afterRemainAmount;
    }

    /**
     * 获取1 提现成功， 2提现失败
     *
     * @return status - 1 提现成功， 2提现失败
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置1 提现成功， 2提现失败
     *
     * @param status 1 提现成功， 2提现失败
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取微信接口返回的错误代码
     *
     * @return error_code - 微信接口返回的错误代码
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 设置微信接口返回的错误代码
     *
     * @param errorCode 微信接口返回的错误代码
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 获取微信接口返回的错误代码描述
     *
     * @return error_msg - 微信接口返回的错误代码描述
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * 设置微信接口返回的错误代码描述
     *
     * @param errorMsg 微信接口返回的错误代码描述
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}