package cn.com.cdboost.collect.model;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_fee_acct")
public class FeeAcct implements Serializable {
    private static final long serialVersionUID = 6395035542409281207L;
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
     * 参数更新 1,2,3,81,82,83 字典表
     */
    @Column(name = "update_param")
    private Integer updateParam;

    /**
     * 电卡类型 字典表
     */
    @Column(name = "ic_card_type")
    private String icCardType;

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
     * 报警金额1
     */
    @Column(name = "alert_fee1")
    private BigDecimal alertFee1;

    /**
     * 报警金额2
     */
    @Column(name = "alert_fee2")
    private BigDecimal alertFee2;

    /**
     * 透支金额限值
     */
    @Column(name = "overdraft_fee")
    private BigDecimal overdraftFee;

    /**
     * 囤积金额限
     */
    @Column(name = "corner_fee")
    private BigDecimal cornerFee;

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
     * 更新人员ID
     */
    @Column(name = "update_user_id")
    private Long updateUserId;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 返写时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "re_wrt_time")
    private Date reWrtTime;

    /**
     * 非法插卡次数
     */
    @Column(name = "error_cnt")
    private Integer errorCnt;

    /**
     * 设备类型 0其他 1 -09电表 2-13表
     */
    @Column(name = "meter_type")
    private Integer meterType;

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
     * 获取参数更新 1,2,3,81,82,83 字典表
     *
     * @return update_param - 参数更新 1,2,3,81,82,83 字典表
     */
    public Integer getUpdateParam() {
        return updateParam;
    }

    /**
     * 设置参数更新 1,2,3,81,82,83 字典表
     *
     * @param updateParam 参数更新 1,2,3,81,82,83 字典表
     */
    public void setUpdateParam(Integer updateParam) {
        this.updateParam = updateParam;
    }

    /**
     * 获取电卡类型 字典表
     *
     * @return ic_card_type - 电卡类型 字典表
     */
    public String getIcCardType() {
        return icCardType;
    }

    /**
     * 设置电卡类型 字典表
     *
     * @param icCardType 电卡类型 字典表
     */
    public void setIcCardType(String icCardType) {
        this.icCardType = icCardType;
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
     * 获取报警金额1
     *
     * @return alert_fee1 - 报警金额1
     */
    public BigDecimal getAlertFee1() {
        return alertFee1;
    }

    /**
     * 设置报警金额1
     *
     * @param alertFee1 报警金额1
     */
    public void setAlertFee1(BigDecimal alertFee1) {
        this.alertFee1 = alertFee1;
    }

    /**
     * 获取报警金额2
     *
     * @return alert_fee2 - 报警金额2
     */
    public BigDecimal getAlertFee2() {
        return alertFee2;
    }

    /**
     * 设置报警金额2
     *
     * @param alertFee2 报警金额2
     */
    public void setAlertFee2(BigDecimal alertFee2) {
        this.alertFee2 = alertFee2;
    }

    /**
     * 获取透支金额限值
     *
     * @return overdraft_fee - 透支金额限值
     */
    public BigDecimal getOverdraftFee() {
        return overdraftFee;
    }

    /**
     * 设置透支金额限值
     *
     * @param overdraftFee 透支金额限值
     */
    public void setOverdraftFee(BigDecimal overdraftFee) {
        this.overdraftFee = overdraftFee;
    }

    /**
     * 获取囤积金额限
     *
     * @return corner_fee - 囤积金额限
     */
    public BigDecimal getCornerFee() {
        return cornerFee;
    }

    /**
     * 设置囤积金额限
     *
     * @param cornerFee 囤积金额限
     */
    public void setCornerFee(BigDecimal cornerFee) {
        this.cornerFee = cornerFee;
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
     * 获取更新人员ID
     *
     * @return update_user_id - 更新人员ID
     */
    public Long getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 设置更新人员ID
     *
     * @param updateUserId 更新人员ID
     */
    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
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

    /**
     * 获取返写时间
     *
     * @return re_wrt_time - 返写时间
     */
    public Date getReWrtTime() {
        return reWrtTime;
    }

    /**
     * 设置返写时间
     *
     * @param reWrtTime 返写时间
     */
    public void setReWrtTime(Date reWrtTime) {
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
     * 获取设备类型 0其他 1 -09电表 2-13表
     *
     * @return meter_type - 设备类型 0其他 1 -09电表 2-13表
     */
    public Integer getMeterType() {
        return meterType;
    }

    /**
     * 设置设备类型 0其他 1 -09电表 2-13表
     *
     * @param meterType 设备类型 0其他 1 -09电表 2-13表
     */
    public void setMeterType(Integer meterType) {
        this.meterType = meterType;
    }
}