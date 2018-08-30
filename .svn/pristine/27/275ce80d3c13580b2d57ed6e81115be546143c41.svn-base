package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_d_fee_payorder")
public class FeePayOrder implements Serializable {
    private static final long serialVersionUID = -2905291116763701940L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商户订单号
     */
    @Column(name = "trade_no")
    private String tradeNo;

    /**
     * 公众账号ID
     */
    @Column(name = "app_id")
    private String appId;

    /**
     * 商户号
     */
    @Column(name = "mch_id")
    private String mchId;

    /**
     * 随机字符串
     */
    @Column(name = "nonce_str")
    private String nonceStr;

    /**
     * 签名类型 默认为MD5，支持HMAC-SHA256和MD5。
     */
    @Column(name = "sign_type")
    private String signType;

    /**
     * 签名
     */
    private String sign;

    /**
     * 商品描述
     */
    @Column(name = "trade_body")
    private String tradeBody;

    /**
     * 交易起始时间
     */
    @Column(name = "time_start")
    private String timeStart;

    /**
     * 交易结束时间
     */
    @Column(name = "time_expire")
    private String timeExpire;

    /**
     * 标价金额(单位 分)
     */
    @Column(name = "total_fee")
    private Integer totalFee;

    /**
     * 终端IP
     */
    @Column(name = "spbill_create_ip")
    private String spbillCreateIp;

    /**
     * 通知地址
     */
    @Column(name = "notify_url")
    private String notifyUrl;

    /**
     * 交易类型 JSAPI，NATIVE，APP
     */
    @Column(name = "trade_type")
    private String tradeType;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 支付状态 -1 支付失败 0 正在支付 1 支付成功
     */
    @Column(name = "pay_flag")
    private Integer payFlag;

    /**
     * 支付完成时间
     */
    @Column(name = "finish_time")
    private Date finishTime;

    /**
     * 设备cno
     */
    private String cno;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商户订单号
     *
     * @return trade_no - 商户订单号
     */
    public String getTradeNo() {
        return tradeNo;
    }

    /**
     * 设置商户订单号
     *
     * @param tradeNo 商户订单号
     */
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    /**
     * 获取公众账号ID
     *
     * @return app_id - 公众账号ID
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置公众账号ID
     *
     * @param appId 公众账号ID
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 获取商户号
     *
     * @return mch_id - 商户号
     */
    public String getMchId() {
        return mchId;
    }

    /**
     * 设置商户号
     *
     * @param mchId 商户号
     */
    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    /**
     * 获取随机字符串
     *
     * @return nonce_str - 随机字符串
     */
    public String getNonceStr() {
        return nonceStr;
    }

    /**
     * 设置随机字符串
     *
     * @param nonceStr 随机字符串
     */
    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    /**
     * 获取签名类型 默认为MD5，支持HMAC-SHA256和MD5。
     *
     * @return sign_type - 签名类型 默认为MD5，支持HMAC-SHA256和MD5。
     */
    public String getSignType() {
        return signType;
    }

    /**
     * 设置签名类型 默认为MD5，支持HMAC-SHA256和MD5。
     *
     * @param signType 签名类型 默认为MD5，支持HMAC-SHA256和MD5。
     */
    public void setSignType(String signType) {
        this.signType = signType;
    }

    /**
     * 获取签名
     *
     * @return sign - 签名
     */
    public String getSign() {
        return sign;
    }

    /**
     * 设置签名
     *
     * @param sign 签名
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * 获取商品描述
     *
     * @return trade_body - 商品描述
     */
    public String getTradeBody() {
        return tradeBody;
    }

    /**
     * 设置商品描述
     *
     * @param tradeBody 商品描述
     */
    public void setTradeBody(String tradeBody) {
        this.tradeBody = tradeBody;
    }

    /**
     * 获取交易起始时间
     *
     * @return time_start - 交易起始时间
     */
    public String getTimeStart() {
        return timeStart;
    }

    /**
     * 设置交易起始时间
     *
     * @param timeStart 交易起始时间
     */
    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    /**
     * 获取交易结束时间
     *
     * @return time_expire - 交易结束时间
     */
    public String getTimeExpire() {
        return timeExpire;
    }

    /**
     * 设置交易结束时间
     *
     * @param timeExpire 交易结束时间
     */
    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
    }

    /**
     * 获取标价金额(单位 分)
     *
     * @return total_fee - 标价金额(单位 分)
     */
    public Integer getTotalFee() {
        return totalFee;
    }

    /**
     * 设置标价金额(单位 分)
     *
     * @param totalFee 标价金额(单位 分)
     */
    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    /**
     * 获取终端IP
     *
     * @return spbill_create_ip - 终端IP
     */
    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    /**
     * 设置终端IP
     *
     * @param spbillCreateIp 终端IP
     */
    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    /**
     * 获取通知地址
     *
     * @return notify_url - 通知地址
     */
    public String getNotifyUrl() {
        return notifyUrl;
    }

    /**
     * 设置通知地址
     *
     * @param notifyUrl 通知地址
     */
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    /**
     * 获取交易类型 JSAPI，NATIVE，APP
     *
     * @return trade_type - 交易类型 JSAPI，NATIVE，APP
     */
    public String getTradeType() {
        return tradeType;
    }

    /**
     * 设置交易类型 JSAPI，NATIVE，APP
     *
     * @param tradeType 交易类型 JSAPI，NATIVE，APP
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
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
     * 获取支付状态 -1 支付失败 0 正在支付 1 支付成功
     *
     * @return pay_flag - 支付状态 -1 支付失败 0 正在支付 1 支付成功
     */
    public Integer getPayFlag() {
        return payFlag;
    }

    /**
     * 设置支付状态 -1 支付失败 0 正在支付 1 支付成功
     *
     * @param payFlag 支付状态 -1 支付失败 0 正在支付 1 支付成功
     */
    public void setPayFlag(Integer payFlag) {
        this.payFlag = payFlag;
    }

    /**
     * 获取支付完成时间
     *
     * @return finish_time - 支付完成时间
     */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
     * 设置支付完成时间
     *
     * @param finishTime 支付完成时间
     */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }
}