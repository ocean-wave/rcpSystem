package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_b_userorg")
public class UserOrg implements Serializable{
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 组织结构
     */
    @Column(name = "org_no")
    private Long orgNo;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

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
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取组织结构
     *
     * @return org_no - 组织结构
     */
    public Long getOrgNo() {
        return orgNo;
    }

    /**
     * 设置组织结构
     *
     * @param orgNo 组织结构
     */
    public void setOrgNo(Long orgNo) {
        this.orgNo = orgNo;
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
}