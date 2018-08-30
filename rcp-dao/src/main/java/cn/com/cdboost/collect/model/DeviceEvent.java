package cn.com.cdboost.collect.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "em_d_device_event")
public class DeviceEvent implements Serializable {
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
     * 设备编号
     */
    private String cno;

    /**
     * 事件等级
     */
    @Column(name = "event_level")
    private Integer eventLevel;

    /**
     * 事件类别
     */
    @Column(name = "event_category")
    private String eventCategory="";

    /**
     * 事件描述
     */
    @Column(name = "event_content")
    private String eventContent;

    /**
     * 事件发生时间
     */
    @Column(name = "event_time")
    private Date eventTime;

    /**
     * 标志 0 未处理 1已处理
     */
    @Column(name = "event_flag")
    private Integer eventFlag;

    /**
     * 事件处理人员
     */
    @Column(name = "solve_user_id")
    private Integer solveUserId;

    /**
     * 解决方案
     */
    @Column(name = "solve_content")
    private String solveContent;

    /**
     * 处理时间
     */
    @Column(name = "solve_time")
    private Date solveTime;

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
     * 获取事件等级
     *
     * @return event_level - 事件等级
     */
    public Integer getEventLevel() {
        return eventLevel;
    }

    /**
     * 设置事件等级
     *
     * @param eventLevel 事件等级
     */
    public void setEventLevel(Integer eventLevel) {
        this.eventLevel = eventLevel;
    }

    /**
     * 获取事件类别
     *
     * @return event_category - 事件类别
     */
    public String getEventCategory() {
        return eventCategory;
    }

    /**
     * 设置事件类别
     *
     * @param eventCategory 事件类别
     */
    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    /**
     * 获取事件描述
     *
     * @return event_content - 事件描述
     */
    public String getEventContent() {
        return eventContent;
    }

    /**
     * 设置事件描述
     *
     * @param eventContent 事件描述
     */
    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    /**
     * 获取事件发生时间
     *
     * @return event_time - 事件发生时间
     */
    public Date getEventTime() {
        return eventTime;
    }

    /**
     * 设置事件发生时间
     *
     * @param eventTime 事件发生时间
     */
    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    /**
     * 获取标志 0 未处理 1已处理
     *
     * @return event_flag - 标志 0 未处理 1已处理
     */
    public Integer getEventFlag() {
        return eventFlag;
    }

    /**
     * 设置标志 0 未处理 1已处理
     *
     * @param eventFlag 标志 0 未处理 1已处理
     */
    public void setEventFlag(Integer eventFlag) {
        this.eventFlag = eventFlag;
    }

    /**
     * 获取事件处理人员
     *
     * @return solve_user_id - 事件处理人员
     */
    public Integer getSolveUserId() {
        return solveUserId;
    }

    /**
     * 设置事件处理人员
     *
     * @param solveUserId 事件处理人员
     */
    public void setSolveUserId(Integer solveUserId) {
        this.solveUserId = solveUserId;
    }

    /**
     * 获取解决方案
     *
     * @return solve_content - 解决方案
     */
    public String getSolveContent() {
        return solveContent;
    }

    /**
     * 设置解决方案
     *
     * @param solveContent 解决方案
     */
    public void setSolveContent(String solveContent) {
        this.solveContent = solveContent;
    }

    /**
     * 获取处理时间
     *
     * @return solve_time - 处理时间
     */
    public Date getSolveTime() {
        return solveTime;
    }

    /**
     * 设置处理时间
     *
     * @param solveTime 处理时间
     */
    public void setSolveTime(Date solveTime) {
        this.solveTime = solveTime;
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