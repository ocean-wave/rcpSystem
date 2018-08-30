package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "em_d_meterrelation")
public class MeterRelation implements Serializable{
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 上级表号
     */
    @Column(name = "p_meter_cno")
    private String pMeterCno;

    /**
     * 表号
     */
    @Column(name = "meter_cno")
    private String meterCno;

    /**
     * 层级
     */
    private String level;

    /**
     * 用户编号
     */
    @Column(name = "customer_no")
    private String customerNo;

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
     * 获取上级表号
     *
     * @return p_meter_cno - 上级表号
     */
    public String getpMeterCno() {
        return pMeterCno;
    }

    /**
     * 设置上级表号
     *
     * @param pMeterCno 上级表号
     */
    public void setpMeterCno(String pMeterCno) {
        this.pMeterCno = pMeterCno;
    }

    /**
     * 获取表号
     *
     * @return meter_cno - 表号
     */
    public String getMeterCno() {
        return meterCno;
    }

    /**
     * 设置表号
     *
     * @param meterCno 表号
     */
    public void setMeterCno(String meterCno) {
        this.meterCno = meterCno;
    }

    /**
     * 获取层级
     *
     * @return level - 层级
     */
    public String getLevel() {
        return level;
    }

    /**
     * 设置层级
     *
     * @param level 层级
     */
    public void setLevel(String level) {
        this.level = level;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }
}