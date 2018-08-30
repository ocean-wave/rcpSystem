package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_s_dict")
public class Dict implements Serializable {
    private static final long serialVersionUID = 7803520579350192426L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 字典id
     */
    @Column(name = "dict_id")
    private Long dictId;

    /**
     * 字典类别
     */
    @Column(name = "dict_code")
    private String dictCode;

    /**
     * 字典名称
     */
    @Column(name = "dict_name")
    private String dictName;

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
     * 获取字典id
     *
     * @return dict_id - 字典id
     */
    public Long getDictId() {
        return dictId;
    }

    /**
     * 设置字典id
     *
     * @param dictId 字典id
     */
    public void setDictId(Long dictId) {
        this.dictId = dictId;
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
     * 获取字典名称
     *
     * @return dict_name - 字典名称
     */
    public String getDictName() {
        return dictName;
    }

    /**
     * 设置字典名称
     *
     * @param dictName 字典名称
     */
    public void setDictName(String dictName) {
        this.dictName = dictName;
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