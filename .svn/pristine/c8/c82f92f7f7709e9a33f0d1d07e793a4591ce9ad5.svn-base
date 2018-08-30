package cn.com.cdboost.collect.model;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_d_fee_pricesols")
public class FeePriceSols implements Serializable {
    private static final long serialVersionUID = -2830700508867362686L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 电价方案代码,时间加序号(2017062701)
     */
    @Column(name = "price_sols_code")
    private String priceSolsCode;

    /**
     * 方案名称
     */
    @Column(name = "price_sols_name")
    private String priceSolsName;

    /**
     * 用户类型值,取字典表中的值
     */
    @Column(name = "dict_item_value")
    private String dictItemValue;

    /**
     * 创建人员ID
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
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
    @JSONField(format = "yyyy-MM-dd")
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否有效
     */
    @Column(name = "is_enabled")
    private Integer isEnabled;

    /**
     * 电价方案备注信息
     */
    @Column(name = "sols_remark")
    private String solsRemark;

    /**
     * 方案实施时间
     */
    @JSONField(format = "yyyy-MM-dd")
    @Column(name = "effective_date")
    private Date effectiveDate;

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
     * 获取电价方案代码,时间加序号(2017062701)
     *
     * @return price_sols_code - 电价方案代码,时间加序号(2017062701)
     */
    public String getPriceSolsCode() {
        return priceSolsCode;
    }

    /**
     * 设置电价方案代码,时间加序号(2017062701)
     *
     * @param priceSolsCode 电价方案代码,时间加序号(2017062701)
     */
    public void setPriceSolsCode(String priceSolsCode) {
        this.priceSolsCode = priceSolsCode;
    }

    /**
     * 获取方案名称
     *
     * @return price_sols_name - 方案名称
     */
    public String getPriceSolsName() {
        return priceSolsName;
    }

    /**
     * 设置方案名称
     *
     * @param priceSolsName 方案名称
     */
    public void setPriceSolsName(String priceSolsName) {
        this.priceSolsName = priceSolsName;
    }

    /**
     * 获取用户类型值,取字典表中的值
     *
     * @return dict_item_value - 用户类型值,取字典表中的值
     */
    public String getDictItemValue() {
        return dictItemValue;
    }

    /**
     * 设置用户类型值,取字典表中的值
     *
     * @param dictItemValue 用户类型值,取字典表中的值
     */
    public void setDictItemValue(String dictItemValue) {
        this.dictItemValue = dictItemValue;
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
     * 获取是否有效
     *
     * @return is_enabled - 是否有效
     */
    public Integer getIsEnabled() {
        return isEnabled;
    }

    /**
     * 设置是否有效
     *
     * @param isEnabled 是否有效
     */
    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * 获取电价方案备注信息
     *
     * @return sols_remark - 电价方案备注信息
     */
    public String getSolsRemark() {
        return solsRemark;
    }

    /**
     * 设置电价方案备注信息
     *
     * @param solsRemark 电价方案备注信息
     */
    public void setSolsRemark(String solsRemark) {
        this.solsRemark = solsRemark;
    }

    /**
     * 获取方案实施时间
     *
     * @return effective_date - 方案实施时间
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * 设置方案实施时间
     *
     * @param effectiveDate 方案实施时间
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}