package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_fee_autodeduct")
public class FeeAutoDeduct implements Serializable{
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
     * 表计户号
     */
    @Column(name = "meter_user_no")
    private String meterUserNo;

    /**
     * 设备唯一标识
     */
    private String cno;

    /**
     * 扣费金额
     */
    @Column(name = "deduct_money")
    private BigDecimal deductMoney;

    /**
     * 1-电费
     */
    @Column(name = "cost_type")
    private Integer costType;

    /**
     * 结算时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 对应计费表em_d_cost_calculate的标识
     */
    @Column(name = "calculate_id")
    private Integer calculateId;

    /**
     * 扣减后客户总账户余额
     */
    @Column(name = "customer_remain_amount")
    private BigDecimal customerRemainAmount;

    /**
     * 扣费后的剩余金额
     */
    @Column(name = "remain_amount")
    private BigDecimal remainAmount;

    /**
     * 对应em_d_metercollectgroup中数据记录GUID
     */
    @Column(name = "group_guid")
    private String groupGuid;

    /**
     * 扣费备注
     */
    private String remark;

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
     * 获取表计户号
     *
     * @return meter_user_no - 表计户号
     */
    public String getMeterUserNo() {
        return meterUserNo;
    }

    /**
     * 设置表计户号
     *
     * @param meterUserNo 表计户号
     */
    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }

    /**
     * 获取设备唯一标识
     *
     * @return cno - 设备唯一标识
     */
    public String getCno() {
        return cno;
    }

    /**
     * 设置设备唯一标识
     *
     * @param cno 设备唯一标识
     */
    public void setCno(String cno) {
        this.cno = cno;
    }

    /**
     * 获取扣费金额
     *
     * @return deduct_money - 扣费金额
     */
    public BigDecimal getDeductMoney() {
        return deductMoney;
    }

    /**
     * 设置扣费金额
     *
     * @param deductMoney 扣费金额
     */
    public void setDeductMoney(BigDecimal deductMoney) {
        this.deductMoney = deductMoney;
    }

    /**
     * 获取1-电费
     *
     * @return cost_type - 1-电费
     */
    public Integer getCostType() {
        return costType;
    }

    /**
     * 设置1-电费
     *
     * @param costType 1-电费
     */
    public void setCostType(Integer costType) {
        this.costType = costType;
    }

    /**
     * 获取结算时间
     *
     * @return create_time - 结算时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置结算时间
     *
     * @param createTime 结算时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取对应计费表em_d_cost_calculate的标识
     *
     * @return calculate_id - 对应计费表em_d_cost_calculate的标识
     */
    public Integer getCalculateId() {
        return calculateId;
    }

    /**
     * 设置对应计费表em_d_cost_calculate的标识
     *
     * @param calculateId 对应计费表em_d_cost_calculate的标识
     */
    public void setCalculateId(Integer calculateId) {
        this.calculateId = calculateId;
    }

    /**
     * 获取扣减后客户总账户余额
     *
     * @return customer_remain_amount - 扣减后客户总账户余额
     */
    public BigDecimal getCustomerRemainAmount() {
        return customerRemainAmount;
    }

    /**
     * 设置扣减后客户总账户余额
     *
     * @param customerRemainAmount 扣减后客户总账户余额
     */
    public void setCustomerRemainAmount(BigDecimal customerRemainAmount) {
        this.customerRemainAmount = customerRemainAmount;
    }

    /**
     * 获取扣费后的剩余金额
     *
     * @return remain_amount - 扣费后的剩余金额
     */
    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    /**
     * 设置扣费后的剩余金额
     *
     * @param remainAmount 扣费后的剩余金额
     */
    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    /**
     * 获取对应em_d_metercollectgroup中数据记录GUID
     *
     * @return group_guid - 对应em_d_metercollectgroup中数据记录GUID
     */
    public String getGroupGuid() {
        return groupGuid;
    }

    /**
     * 设置对应em_d_metercollectgroup中数据记录GUID
     *
     * @param groupGuid 对应em_d_metercollectgroup中数据记录GUID
     */
    public void setGroupGuid(String groupGuid) {
        this.groupGuid = groupGuid;
    }

    /**
     * 获取扣费备注
     *
     * @return remark - 扣费备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置扣费备注
     *
     * @param remark 扣费备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}