package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "em_d_device_heartbeat")
public class DeviceHeartbeat {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 设备唯一标识
     */
    private String cno;

    /**
     * 集中器编号
     */
    @Column(name = "device_no")
    private String deviceNo;

    /**
     * 状态  0-掉线  1-登陆  2-心跳
     */
    private Integer state;

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
     * 获取设备唯一标识
     *
     * @return cno - 设备唯一标识
     */
    public String getCno() {
        return cno;
    }

    /**
     * 设置设备唯一标识
     *
     * @param cno 设备唯一标识
     */
    public void setCno(String cno) {
        this.cno = cno;
    }

    /**
     * 获取集中器编号
     *
     * @return device_no - 集中器编号
     */
    public String getDeviceNo() {
        return deviceNo;
    }

    /**
     * 设置集中器编号
     *
     * @param deviceNo 集中器编号
     */
    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    /**
     * 获取状态  0-掉线  1-登陆  2-心跳
     *
     * @return state - 状态  0-掉线  1-登陆  2-心跳
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置状态  0-掉线  1-登陆  2-心跳
     *
     * @param state 状态  0-掉线  1-登陆  2-心跳
     */
    public void setState(Integer state) {
        this.state = state;
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