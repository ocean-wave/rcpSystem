package cn.com.cdboost.collect.model;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_d_metersuppquery")
public class MeterSuppQuery implements Serializable {
    private static final long serialVersionUID = -1459859182130879989L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 任务编号
     */
    @Column(name = "task_no")
    private String taskNo;

    /**
     * 请求人员
     */
    @Column(name = "query_user_id")
    private Integer queryUserId;

    /**
     * 请求时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "query_time")
    private Date queryTime;

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
     * 获取任务编号
     *
     * @return task_no - 任务编号
     */
    public String getTaskNo() {
        return taskNo;
    }

    /**
     * 设置任务编号
     *
     * @param taskNo 任务编号
     */
    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    /**
     * 获取请求人员
     *
     * @return query_user_id - 请求人员
     */
    public Integer getQueryUserId() {
        return queryUserId;
    }

    /**
     * 设置请求人员
     *
     * @param queryUserId 请求人员
     */
    public void setQueryUserId(Integer queryUserId) {
        this.queryUserId = queryUserId;
    }

    /**
     * 获取请求时间
     *
     * @return query_time - 请求时间
     */
    public Date getQueryTime() {
        return queryTime;
    }

    /**
     * 设置请求时间
     *
     * @param queryTime 请求时间
     */
    public void setQueryTime(Date queryTime) {
        this.queryTime = queryTime;
    }
}