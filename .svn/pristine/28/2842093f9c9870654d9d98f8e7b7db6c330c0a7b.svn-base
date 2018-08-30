package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_s_dictitem")
public class DictItem implements Serializable {
    private static final long serialVersionUID = 6609656358236501480L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 字典项id
     */
    @Column(name = "dict_item_id")
    private Long dictItemId;

    /**
     * 字典类别
     */
    @Column(name = "dict_code")
    private String dictCode;

    /**
     * 字典项值
     */
    @Column(name = "dict_item_value")
    private String dictItemValue;

    /**
     * 字典项名称
     */
    @Column(name = "dict_item_name")
    private String dictItemName;

    /**
     * 父级字典项ID
     */
    @Column(name = "p_dict_item_id")
    private Long pDictItemId;

    /**
     * 树级别
     */
    @Column(name = "tree_level")
    private Integer treeLevel;

    /**
     * 是否末级 0标识非末级
     */
    @Column(name = "is_lowest")
    private Integer isLowest;

    /**
     * 序号
     */
    @Column(name = "sort_no")
    private Integer sortNo;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 是否有效(0-无效，1-有效)
     */
    @Column(name = "is_enabled")
    private Integer isEnabled;

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
     * 获取字典项id
     *
     * @return dict_item_id - 字典项id
     */
    public Long getDictItemId() {
        return dictItemId;
    }

    /**
     * 设置字典项id
     *
     * @param dictItemId 字典项id
     */
    public void setDictItemId(Long dictItemId) {
        this.dictItemId = dictItemId;
    }

    /**
     * 获取字典类别
     *
     * @return dict_code - 字典类别
     */
    public String getDictCode() {
        return dictCode;
    }

    /**
     * 设置字典类别
     *
     * @param dictCode 字典类别
     */
    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    /**
     * 获取字典项值
     *
     * @return dict_item_value - 字典项值
     */
    public String getDictItemValue() {
        return dictItemValue;
    }

    /**
     * 设置字典项值
     *
     * @param dictItemValue 字典项值
     */
    public void setDictItemValue(String dictItemValue) {
        this.dictItemValue = dictItemValue;
    }

    /**
     * 获取字典项名称
     *
     * @return dict_item_name - 字典项名称
     */
    public String getDictItemName() {
        return dictItemName;
    }

    /**
     * 设置字典项名称
     *
     * @param dictItemName 字典项名称
     */
    public void setDictItemName(String dictItemName) {
        this.dictItemName = dictItemName;
    }

    /**
     * 获取父级字典项ID
     *
     * @return p_dict_item_id - 父级字典项ID
     */
    public Long getpDictItemId() {
        return pDictItemId;
    }

    /**
     * 设置父级字典项ID
     *
     * @param pDictItemId 父级字典项ID
     */
    public void setpDictItemId(Long pDictItemId) {
        this.pDictItemId = pDictItemId;
    }

    /**
     * 获取树级别
     *
     * @return tree_level - 树级别
     */
    public Integer getTreeLevel() {
        return treeLevel;
    }

    /**
     * 设置树级别
     *
     * @param treeLevel 树级别
     */
    public void setTreeLevel(Integer treeLevel) {
        this.treeLevel = treeLevel;
    }

    /**
     * 获取是否末级 0标识非末级
     *
     * @return is_lowest - 是否末级 0标识非末级
     */
    public Integer getIsLowest() {
        return isLowest;
    }

    /**
     * 设置是否末级 0标识非末级
     *
     * @param isLowest 是否末级 0标识非末级
     */
    public void setIsLowest(Integer isLowest) {
        this.isLowest = isLowest;
    }

    /**
     * 获取序号
     *
     * @return sort_no - 序号
     */
    public Integer getSortNo() {
        return sortNo;
    }

    /**
     * 设置序号
     *
     * @param sortNo 序号
     */
    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取创建人
     *
     * @return create_user_id - 创建人
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建人
     *
     * @param createUserId 创建人
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
     * 获取是否有效(0-无效，1-有效)
     *
     * @return is_enabled - 是否有效(0-无效，1-有效)
     */
    public Integer getIsEnabled() {
        return isEnabled;
    }

    /**
     * 设置是否有效(0-无效，1-有效)
     *
     * @param isEnabled 是否有效(0-无效，1-有效)
     */
    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }
}