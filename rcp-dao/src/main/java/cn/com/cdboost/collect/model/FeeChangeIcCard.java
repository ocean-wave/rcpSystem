package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_fee_changeiccard")
public class FeeChangeIcCard implements Serializable {
    private static final long serialVersionUID = -8329950031511712426L;
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
     * 原IC卡
     */
    @Column(name = "old_card_id")
    private String oldCardId;

    /**
     * 新IC卡
     */
    @Column(name = "card_id")
    private String cardId;

    /**
     * 更换原因
     */
    @Column(name = "change_remark")
    private String changeRemark;

    /**
     * 更换人员
     */
    @Column(name = "change_user_id")
    private Long changeUserId;

    /**
     * 更换时间
     */
    @Column(name = "change_time")
    private Date changeTime;

    /**
     * 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    private String cno;

    /**
     * 补卡类型 1 开户卡 2补售电卡
     */
    @Column(name = "repet_type")
    private Integer repetType;

    /**
     * 补卡时的购电总额
     */
    @Column(name = "repet_total_amount")
    private BigDecimal repetTotalAmount;

    /**
     * 补卡时的剩余金额
     */
    @Column(name = "repet_remain_amount")
    private BigDecimal repetRemainAmount;

    /**
     * 补卡时的购电次数
     */
    @Column(name = "repet_pay_count")
    private Integer repetPayCount;

    /**
     * 补卡时的电表总额
     */
    @Column(name = "repet_m_total_amount")
    private BigDecimal repetMTotalAmount;

    /**
     * 补卡时的电表购电次数
     */
    @Column(name = "repet_m_remain_amount")
    private Integer repetMRemainAmount;

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
     * 获取原IC卡
     *
     * @return old_card_id - 原IC卡
     */
    public String getOldCardId() {
        return oldCardId;
    }

    /**
     * 设置原IC卡
     *
     * @param oldCardId 原IC卡
     */
    public void setOldCardId(String oldCardId) {
        this.oldCardId = oldCardId;
    }

    /**
     * 获取新IC卡
     *
     * @return card_id - 新IC卡
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * 设置新IC卡
     *
     * @param cardId 新IC卡
     */
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    /**
     * 获取更换原因
     *
     * @return change_remark - 更换原因
     */
    public String getChangeRemark() {
        return changeRemark;
    }

    /**
     * 设置更换原因
     *
     * @param changeRemark 更换原因
     */
    public void setChangeRemark(String changeRemark) {
        this.changeRemark = changeRemark;
    }

    /**
     * 获取更换人员
     *
     * @return change_user_id - 更换人员
     */
    public Long getChangeUserId() {
        return changeUserId;
    }

    /**
     * 设置更换人员
     *
     * @param changeUserId 更换人员
     */
    public void setChangeUserId(Long changeUserId) {
        this.changeUserId = changeUserId;
    }

    /**
     * 获取更换时间
     *
     * @return change_time - 更换时间
     */
    public Date getChangeTime() {
        return changeTime;
    }

    /**
     * 设置更换时间
     *
     * @param changeTime 更换时间
     */
    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
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
     * 获取补卡类型 1 开户卡 2补售电卡
     *
     * @return repet_type - 补卡类型 1 开户卡 2补售电卡
     */
    public Integer getRepetType() {
        return repetType;
    }

    /**
     * 设置补卡类型 1 开户卡 2补售电卡
     *
     * @param repetType 补卡类型 1 开户卡 2补售电卡
     */
    public void setRepetType(Integer repetType) {
        this.repetType = repetType;
    }

    /**
     * 获取补卡时的购电总额
     *
     * @return repet_total_amount - 补卡时的购电总额
     */
    public BigDecimal getRepetTotalAmount() {
        return repetTotalAmount;
    }

    /**
     * 设置补卡时的购电总额
     *
     * @param repetTotalAmount 补卡时的购电总额
     */
    public void setRepetTotalAmount(BigDecimal repetTotalAmount) {
        this.repetTotalAmount = repetTotalAmount;
    }

    /**
     * 获取补卡时的剩余金额
     *
     * @return repet_remain_amount - 补卡时的剩余金额
     */
    public BigDecimal getRepetRemainAmount() {
        return repetRemainAmount;
    }

    /**
     * 设置补卡时的剩余金额
     *
     * @param repetRemainAmount 补卡时的剩余金额
     */
    public void setRepetRemainAmount(BigDecimal repetRemainAmount) {
        this.repetRemainAmount = repetRemainAmount;
    }

    /**
     * 获取补卡时的购电次数
     *
     * @return repet_pay_count - 补卡时的购电次数
     */
    public Integer getRepetPayCount() {
        return repetPayCount;
    }

    /**
     * 设置补卡时的购电次数
     *
     * @param repetPayCount 补卡时的购电次数
     */
    public void setRepetPayCount(Integer repetPayCount) {
        this.repetPayCount = repetPayCount;
    }

    /**
     * 获取补卡时的电表总额
     *
     * @return repet_m_total_amount - 补卡时的电表总额
     */
    public BigDecimal getRepetMTotalAmount() {
        return repetMTotalAmount;
    }

    /**
     * 设置补卡时的电表总额
     *
     * @param repetMTotalAmount 补卡时的电表总额
     */
    public void setRepetMTotalAmount(BigDecimal repetMTotalAmount) {
        this.repetMTotalAmount = repetMTotalAmount;
    }

    /**
     * 获取补卡时的电表购电次数
     *
     * @return repet_m_remain_amount - 补卡时的电表购电次数
     */
    public Integer getRepetMRemainAmount() {
        return repetMRemainAmount;
    }

    /**
     * 设置补卡时的电表购电次数
     *
     * @param repetMRemainAmount 补卡时的电表购电次数
     */
    public void setRepetMRemainAmount(Integer repetMRemainAmount) {
        this.repetMRemainAmount = repetMRemainAmount;
    }
}