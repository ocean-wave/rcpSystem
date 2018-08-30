package cn.com.cdboost.collect.model;


import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


public class DeviceEventRequestParam implements Serializable {
    private static final long serialVersionUID = 6427586599101066920L;
    /**
     * 主键
     */

    private Integer id;

    /**
     * 用户编号
     */
    @NotBlank(message = "用户编号不能为空")
    private String customer_no;

    /**
     * 设备编号
     */
    @NotBlank(message="设备编号不能为空")
    private String cno;

    /**
     * 事件等级
     */

    private String event_level;

    /**
     * 事件类别
     */
    private String event_category;

    /**
     * 事件描述
     */

    private String event_content;

    /**
     * 事件发生时间
     */
    private Date event_time;

    /**
     * '标志 0 未处理 1已处理',
     */

    private Integer event_flag;

    /**
     * 事件处理人员
     */
    private int solve_user_id;

    /**
     * 解决方案
     */

    private String solve_content;

    /**
     * '处理时间'
     */

    private Date solve_time;

    /**
     * '创建时间'
     */

    private Date create_time;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomer_no() {
        return customer_no;
    }

    public void setCustomer_no(String customer_no) {
        this.customer_no = customer_no;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getEvent_level() {
        return event_level;
    }

    public void setEvent_level(String event_level) {
        this.event_level = event_level;
    }

    public String getEvent_category() {
        return event_category;
    }

    public void setEvent_category(String event_category) {
        this.event_category = event_category;
    }

    public String getEvent_content() {
        return event_content;
    }

    public void setEvent_content(String event_content) {
        this.event_content = event_content;
    }

    public Date getEvent_time() {
        return event_time;
    }

    public void setEvent_time(Date event_time) {
        this.event_time = event_time;
    }

    public Integer getEvent_flag() {
        return event_flag;
    }

    public void setEvent_flag(Integer event_flag) {
        this.event_flag = event_flag;
    }

    public int getSolve_user_id() {
        return solve_user_id;
    }

    public void setSolve_user_id(int solve_user_id) {
        this.solve_user_id = solve_user_id;
    }

    public String getSolve_content() {
        return solve_content;
    }

    public void setSolve_content(String solve_content) {
        this.solve_content = solve_content;
    }

    public Date getSolve_time() {
        return solve_time;
    }

    public void setSolve_time(Date solve_time) {
        this.solve_time = solve_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}