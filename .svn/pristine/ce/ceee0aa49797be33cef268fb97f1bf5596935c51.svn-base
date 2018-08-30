package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_d_metercollectgroup")
public class MeterCollectGroup implements Serializable {
    private static final long serialVersionUID = -189699738942042182L;
    /**
     * 数据标识
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    private String cno;

    /**
     * 抄表日期
     */
    @Column(name = "collect_date")
    private Date collectDate;

    /**
     * 抄表时间
     */
    @Column(name = "collect_time")
    private Date collectTime;

    /**
     * 组数据标识,标识一组数据
     */
    @Column(name = "group_guid")
    private String groupGuid;

    /**
     * 是否实时数据(1-实时，0-历史)
     */
    @Column(name = "is_real_time")
    private Integer isRealTime;

    /**
     * 数据来源 0 标识集中 1 掌机补抄
     */
    @Column(name = "data_src")
    private Integer dataSrc;

    /**
     * 表计户号
     */
    @Column(name = "meter_user_no")
    private String meterUserNo;

    /**
     * 客户标识
     */
    @Column(name = "customer_no")
    private String customerNo;


    /**
     * 获取数据标识
     *
     * @return id - 数据标识
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置数据标识
     *
     * @param id 数据标识
     */
    public void setId(Integer id) {
        this.id = id;
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
     * 获取抄表日期
     *
     * @return collect_date - 抄表日期
     */
    public Date getCollectDate() {
        return collectDate;
    }

    /**
     * 设置抄表日期
     *
     * @param collectDate 抄表日期
     */
    public void setCollectDate(Date collectDate) {
        this.collectDate = collectDate;
    }

    /**
     * 获取抄表时间
     *
     * @return collect_time - 抄表时间
     */
    public Date getCollectTime() {
        return collectTime;
    }

    /**
     * 设置抄表时间
     *
     * @param collectTime 抄表时间
     */
    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    /**
     * 获取组数据标识,标识一组数据
     *
     * @return group_guid - 组数据标识,标识一组数据
     */
    public String getGroupGuid() {
        return groupGuid;
    }

    /**
     * 设置组数据标识,标识一组数据
     *
     * @param groupGuid 组数据标识,标识一组数据
     */
    public void setGroupGuid(String groupGuid) {
        this.groupGuid = groupGuid;
    }

    /**
     * 获取是否实时数据(1-实时，0-历史)
     *
     * @return is_real_time - 是否实时数据(1-实时，0-历史)
     */
    public Integer getIsRealTime() {
        return isRealTime;
    }

    /**
     * 设置是否实时数据(1-实时，0-历史)
     *
     * @param isRealTime 是否实时数据(1-实时，0-历史)
     */
    public void setIsRealTime(Integer isRealTime) {
        this.isRealTime = isRealTime;
    }

    /**
     * 获取数据来源 0 标识集中 1 掌机补抄
     *
     * @return data_src - 数据来源 0 标识集中 1 掌机补抄
     */
    public Integer getDataSrc() {
        return dataSrc;
    }

    /**
     * 设置数据来源 0 标识集中 1 掌机补抄
     *
     * @param dataSrc 数据来源 0 标识集中 1 掌机补抄
     */
    public void setDataSrc(Integer dataSrc) {
        this.dataSrc = dataSrc;
    }

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }
}