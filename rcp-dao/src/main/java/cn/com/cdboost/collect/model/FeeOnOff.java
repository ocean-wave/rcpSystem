package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_d_fee_onoff")
public class FeeOnOff implements Serializable {
    private static final long serialVersionUID = 5747563891818903040L;
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
     * 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    private String cno;

    /**
     * 通断(0 -断开 1-连通)
     */
    @Column(name = "on_off")
    private Integer onOff;

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
     * 通断原因
     */
    private String remark;

    /**
     * 队列GUID
     */
    @Column(name = "queue_guid")
    private String queueGuid;

    /**
     * 操作结果
     */
    @Column(name = "send_flag")
    private Integer sendFlag;

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
     * 获取设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     *
     * @return cno - 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    public String getCno() {
        return cno;
    }

    /**
     * 设置设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     *
     * @param cno 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    public void setCno(String cno) {
        this.cno = cno;
    }

    /**
     * 获取通断(0 -断开 1-连通)
     *
     * @return on_off - 通断(0 -断开 1-连通)
     */
    public Integer getOnOff() {
        return onOff;
    }

    /**
     * 设置通断(0 -断开 1-连通)
     *
     * @param onOff 通断(0 -断开 1-连通)
     */
    public void setOnOff(Integer onOff) {
        this.onOff = onOff;
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
     * 获取通断原因
     *
     * @return remark - 通断原因
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置通断原因
     *
     * @param remark 通断原因
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取队列GUID
     *
     * @return queue_guid - 队列GUID
     */
    public String getQueueGuid() {
        return queueGuid;
    }

    /**
     * 设置队列GUID
     *
     * @param queueGuid 队列GUID
     */
    public void setQueueGuid(String queueGuid) {
        this.queueGuid = queueGuid;
    }

    /**
     * 获取操作结果
     *
     * @return send_flag - 操作结果
     */
    public Integer getSendFlag() {
        return sendFlag;
    }

    /**
     * 设置操作结果
     *
     * @param sendFlag 操作结果
     */
    public void setSendFlag(Integer sendFlag) {
        this.sendFlag = sendFlag;
    }
}