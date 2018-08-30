package cn.com.cdboost.collect.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "em_d_query_schememet")
public class QuerySchememet implements Serializable {
    private static final long serialVersionUID = 798356140673015063L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 方案标识
     */
    @Column(name = "scheme_flag")
    private String schemeFlag;

    /**
     * 用户编号
     */
    @Column(name = "customer_no")
    private String customerNo;

    /**
     * 表计户号
     */
    @Column(name = "meter_user_no")
    private String meterUserNo;

    /**
     * 设备编号
     */
    private String cno;

    /**
     * 创建人员
     */
    @Column(name = "create_user_id")
    private Integer createUserId;

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
     * 获取方案标识
     *
     * @return scheme_flag - 方案标识
     */
    public String getSchemeFlag() {
        return schemeFlag;
    }

    /**
     * 设置方案标识
     *
     * @param schemeFlag 方案标识
     */
    public void setSchemeFlag(String schemeFlag) {
        this.schemeFlag = schemeFlag;
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
     * 获取表计户号
     *
     * @return meter_user_no - 表计户号
     */
    public String getMeterUserNo() {
        return meterUserNo;
    }

    /**
     * 设置表计户号
     *
     * @param meterUserNo 表计户号
     */
    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
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
     * 获取创建人员
     *
     * @return create_user_id - 创建人员
     */
    public Integer getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建人员
     *
     * @param createUserId 创建人员
     */
    public void setCreateUserId(Integer createUserId) {
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
}