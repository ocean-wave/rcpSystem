package cn.com.cdboost.collect.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "em_d_charging_card")
public class ChargingCard implements Serializable {
    /**
     * 标识id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 项目标识
     */
    @Column(name = "project_guid")
    private String projectGuid;

    /**
     * 客户唯一标识
     */
    @Column(name = "customer_guid")
    private String customerGuid;

    /**
     * ic卡编号
     */
    @Column(name = "card_id")
    private String cardId;

    /**
     *IC卡所属人员名称
     */
    @Column(name = "customer_name")
    private String customerName;

    /**
     *IC所属人员电话
     */
    @Column(name = "customer_contact")
    private String customerContact;

    /**
     * ic卡状态 0初始、1 -启用、2-停用
     */
    @Column(name = "card_state")
    private Integer cardState;

    /**
     * 使用次数
     */
    @Column(name = "use_cnt")
    private Integer useCnt;

    /**
     * 预置预留
     */
    @Column(name = "init_amount")
    private BigDecimal initAmount;

    /**
     * 剩余金额
     */
    @Column(name = "remain_amount")
    private BigDecimal remainAmount;

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
     *备注
     */
    @Column(name = "remark")
    private String remark;


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
     * 获取项目标识
     *
     * @return project_guid - 项目标识
     */
    public String getProjectGuid() {
        return projectGuid;
    }

    /**
     * 设置项目标识
     *
     * @param projectGuid 项目标识
     */
    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
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
     * 获取ic卡编号
     *
     * @return card_id - ic卡编号
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * 设置ic卡编号
     *
     * @param cardId ic卡编号
     */
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    /**
     * 获取ic卡状态 0初始、1 -启用、2-停用
     *
     * @return card_state - ic卡状态 0初始、1 -启用、2-停用
     */
    public Integer getCardState() {
        return cardState;
    }

    /**
     * 设置ic卡状态 0初始、1 -启用、2-停用
     *
     * @param cardState ic卡状态 0初始、1 -启用、2-停用
     */
    public void setCardState(Integer cardState) {
        this.cardState = cardState;
    }

    /**
     * 获取使用次数
     *
     * @return use_cnt - 使用次数
     */
    public Integer getUseCnt() {
        return useCnt;
    }

    /**
     * 设置使用次数
     *
     * @param useCnt 使用次数
     */
    public void setUseCnt(Integer useCnt) {
        this.useCnt = useCnt;
    }

    /**
     * 获取预置预留
     *
     * @return init_amount - 预置预留
     */
    public BigDecimal getInitAmount() {
        return initAmount;
    }

    /**
     * 设置预置预留
     *
     * @param initAmount 预置预留
     */
    public void setInitAmount(BigDecimal initAmount) {
        this.initAmount = initAmount;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}