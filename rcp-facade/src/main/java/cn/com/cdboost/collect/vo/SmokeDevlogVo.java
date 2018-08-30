package cn.com.cdboost.collect.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SmokeDevlogVo implements Serializable {
    private Long id;

    /**
     * 设备编号
     */
    private String cno;

    /**
     * 通信编号
     */
    private String commNo;

    /**
     * 状态(在线-1、离线-2、停用-3、告警-4、欠压-5
     */
    private String status;

    /**
     * 电池电量
     */
    private BigDecimal power;

    /**
     * 电池电压
     */
    private String level;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取设备编号
     *
     * @return cno - 设备编号
     */
    public String getCno() {
        return cno;
    }

    /**
     * 设置设备编号
     *
     * @param cno 设备编号
     */
    public void setCno(String cno) {
        this.cno = cno;
    }

    /**
     * 获取通信编号
     *
     * @return comm_no - 通信编号
     */
    public String getCommNo() {
        return commNo;
    }

    /**
     * 设置通信编号
     *
     * @param commNo 通信编号
     */
    public void setCommNo(String commNo) {
        this.commNo = commNo;
    }

    /**
     * 获取状态(在线-1、离线-2、停用-3、告警-4、欠压-5
     *
     * @return status - 状态(在线-1、离线-2、停用-3、告警-4、欠压-5
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态(在线-1、离线-2、停用-3、告警-4、欠压-5
     *
     * @param status 状态(在线-1、离线-2、停用-3、告警-4、欠压-5
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取电池电量
     *
     * @return power - 电池电量
     */
    public BigDecimal getPower() {
        return power;
    }

    /**
     * 设置电池电量
     *
     * @param power 电池电量
     */
    public void setPower(BigDecimal power) {
        this.power = power;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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
}