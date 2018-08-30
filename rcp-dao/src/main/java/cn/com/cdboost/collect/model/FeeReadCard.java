package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_fee_readcard")
public class FeeReadCard implements Serializable {
    private static final long serialVersionUID = 6659218265983705209L;
    /**
     * 主键
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
     * 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    private String cno;

    /**
     * 购电卡号
     */
    @Column(name = "card_id")
    private String cardId;

    /**
     * 电流互感器变比
     */
    @Column(name = "cur_tranf_rto")
    private Integer curTranfRto;

    /**
     * 电压互感器变比
     */
    @Column(name = "vol_tranf_rto")
    private Integer volTranfRto;

    /**
     * 购电次数
     */
    @Column(name = "pay_count")
    private Integer payCount;

    /**
     * 返写时间
     */
    @Column(name = "re_wrt_time")
    private String reWrtTime;

    /**
     * 非法插卡次数
     */
    @Column(name = "error_cnt")
    private Integer errorCnt;

    /**
     * 倍率
     */
    private Integer ratio;

    /**
     * 密钥状态
     */
    @Column(name = "key_state")
    private Integer keyState;

    /**
     * 密钥条数
     */
    @Column(name = "key_count")
    private Integer keyCount;

    /**
     * 密钥版本
     */
    @Column(name = "key_ver")
    private Integer keyVer;

    /**
     * 剩余金额
     */
    @Column(name = "remain_amount")
    private BigDecimal remainAmount;

    /**
     * 透支金额
     */
    @Column(name = "overdraft_fee")
    private BigDecimal overdraftFee;

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
     * 表号
     */
    @Column(name = "meter_no")
    private String meterNo;

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
     * 获取设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     *
     * @return cno - 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    public String getCno() {
        return cno;
    }

    /**
     * 设置设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     *
     * @param cno 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    public void setCno(String cno) {
        this.cno = cno;
    }

    /**
     * 获取购电卡号
     *
     * @return card_id - 购电卡号
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * 设置购电卡号
     *
     * @param cardId 购电卡号
     */
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    /**
     * 获取电流互感器变比
     *
     * @return cur_tranf_rto - 电流互感器变比
     */
    public Integer getCurTranfRto() {
        return curTranfRto;
    }

    /**
     * 设置电流互感器变比
     *
     * @param curTranfRto 电流互感器变比
     */
    public void setCurTranfRto(Integer curTranfRto) {
        this.curTranfRto = curTranfRto;
    }

    /**
     * 获取电压互感器变比
     *
     * @return vol_tranf_rto - 电压互感器变比
     */
    public Integer getVolTranfRto() {
        return volTranfRto;
    }

    /**
     * 设置电压互感器变比
     *
     * @param volTranfRto 电压互感器变比
     */
    public void setVolTranfRto(Integer volTranfRto) {
        this.volTranfRto = volTranfRto;
    }

    /**
     * 获取购电次数
     *
     * @return pay_count - 购电次数
     */
    public Integer getPayCount() {
        return payCount;
    }

    /**
     * 设置购电次数
     *
     * @param payCount 购电次数
     */
    public void setPayCount(Integer payCount) {
        this.payCount = payCount;
    }

    /**
     * 获取返写时间
     *
     * @return re_wrt_time - 返写时间
     */
    public String getReWrtTime() {
        return reWrtTime;
    }

    /**
     * 设置返写时间
     *
     * @param reWrtTime 返写时间
     */
    public void setReWrtTime(String reWrtTime) {
        this.reWrtTime = reWrtTime;
    }

    /**
     * 获取非法插卡次数
     *
     * @return error_cnt - 非法插卡次数
     */
    public Integer getErrorCnt() {
        return errorCnt;
    }

    /**
     * 设置非法插卡次数
     *
     * @param errorCnt 非法插卡次数
     */
    public void setErrorCnt(Integer errorCnt) {
        this.errorCnt = errorCnt;
    }

    /**
     * 获取倍率
     *
     * @return ratio - 倍率
     */
    public Integer getRatio() {
        return ratio;
    }

    /**
     * 设置倍率
     *
     * @param ratio 倍率
     */
    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    /**
     * 获取密钥状态
     *
     * @return key_state - 密钥状态
     */
    public Integer getKeyState() {
        return keyState;
    }

    /**
     * 设置密钥状态
     *
     * @param keyState 密钥状态
     */
    public void setKeyState(Integer keyState) {
        this.keyState = keyState;
    }

    /**
     * 获取密钥条数
     *
     * @return key_count - 密钥条数
     */
    public Integer getKeyCount() {
        return keyCount;
    }

    /**
     * 设置密钥条数
     *
     * @param keyCount 密钥条数
     */
    public void setKeyCount(Integer keyCount) {
        this.keyCount = keyCount;
    }

    /**
     * 获取密钥版本
     *
     * @return key_ver - 密钥版本
     */
    public Integer getKeyVer() {
        return keyVer;
    }

    /**
     * 设置密钥版本
     *
     * @param keyVer 密钥版本
     */
    public void setKeyVer(Integer keyVer) {
        this.keyVer = keyVer;
    }

    /**
     * 获取剩余金额
     *
     * @return remain_amount - 剩余金额
     */
    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    /**
     * 设置剩余金额
     *
     * @param remainAmount 剩余金额
     */
    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    /**
     * 获取透支金额
     *
     * @return overdraft_fee - 透支金额
     */
    public BigDecimal getOverdraftFee() {
        return overdraftFee;
    }

    /**
     * 设置透支金额
     *
     * @param overdraftFee 透支金额
     */
    public void setOverdraftFee(BigDecimal overdraftFee) {
        this.overdraftFee = overdraftFee;
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
     * 获取表号
     *
     * @return meter_no - 表号
     */
    public String getMeterNo() {
        return meterNo;
    }

    /**
     * 设置表号
     *
     * @param meterNo 表号
     */
    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }
}