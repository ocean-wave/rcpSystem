package cn.com.cdboost.collect.model;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_s_userlog")
public class UserLog implements Serializable {
    private static final long serialVersionUID = -727979632128124829L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 操作类型(1-登录，2-新增，3-修改，4-删除，5-发送召测命令)
     */
    @Column(name = "opt_type")
    private Integer optType;

    /**
     * 操作对象
     */
    @Column(name = "opt_object")
    private String optObject;

    /**
     * 对象键名
     */
    @Column(name = "object_key")
    private String objectKey;

    /**
     * 对象键值
     */
    @Column(name = "object_key_value")
    private String objectKeyValue;

    /**
     * 操作内容
     */
    @Column(name = "opt_content")
    private String optContent;

    /**
     * 操作参数
     */
    @Column(name = "opt_param")
    private String optParam;
    @Transient
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOptParam() {
        return optParam;
    }

    public void setOptParam(String optParam) {
        this.optParam = optParam;
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
     * 获取操作类型(1-登录，2-新增，3-修改，4-删除，5-发送召测命令)
     *
     * @return opt_type - 操作类型(1-登录，2-新增，3-修改，4-删除，5-发送召测命令)
     */
    public Integer getOptType() {
        return optType;
    }

    /**
     * 设置操作类型(1-登录，2-新增，3-修改，4-删除，5-发送召测命令)
     *
     * @param optType 操作类型(1-登录，2-新增，3-修改，4-删除，5-发送召测命令)
     */
    public void setOptType(Integer optType) {
        this.optType = optType;
    }

    /**
     * 获取操作对象
     *
     * @return opt_object - 操作对象
     */
    public String getOptObject() {
        return optObject;
    }

    /**
     * 设置操作对象
     *
     * @param optObject 操作对象
     */
    public void setOptObject(String optObject) {
        this.optObject = optObject;
    }

    /**
     * 获取对象键名
     *
     * @return object_key - 对象键名
     */
    public String getObjectKey() {
        return objectKey;
    }

    /**
     * 设置对象键名
     *
     * @param objectKey 对象键名
     */
    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    /**
     * 获取对象键值
     *
     * @return object_key_value - 对象键值
     */
    public String getObjectKeyValue() {
        return objectKeyValue;
    }

    /**
     * 设置对象键值
     *
     * @param objectKeyValue 对象键值
     */
    public void setObjectKeyValue(String objectKeyValue) {
        this.objectKeyValue = objectKeyValue;
    }

    /**
     * 获取操作内容
     *
     * @return opt_content - 操作内容
     */
    public String getOptContent() {
        return optContent;
    }

    /**
     * 设置操作内容
     *
     * @param optContent 操作内容
     */
    public void setOptContent(String optContent) {
        this.optContent = optContent;
    }
}