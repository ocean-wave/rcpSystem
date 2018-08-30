package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_d_fee_payopt")
public class FeePayOpt implements Serializable {
    private static final long serialVersionUID = 5905037776373539378L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 1 撤销充值记录
     */
    @Column(name = "opt_type")
    private Integer optType;

    /**
     * 支付标识
     */
    @Column(name = "pay_guid")
    private String payGuid;

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
     * 操作备注
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
     * 获取1 撤销充值记录
     *
     * @return opt_type - 1 撤销充值记录
     */
    public Integer getOptType() {
        return optType;
    }

    /**
     * 设置1 撤销充值记录
     *
     * @param optType 1 撤销充值记录
     */
    public void setOptType(Integer optType) {
        this.optType = optType;
    }

    /**
     * 获取支付标识
     *
     * @return pay_guid - 支付标识
     */
    public String getPayGuid() {
        return payGuid;
    }

    /**
     * 设置支付标识
     *
     * @param payGuid 支付标识
     */
    public void setPayGuid(String payGuid) {
        this.payGuid = payGuid;
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
     * 获取操作备注
     *
     * @return remark - 操作备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置操作备注
     *
     * @param remark 操作备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}