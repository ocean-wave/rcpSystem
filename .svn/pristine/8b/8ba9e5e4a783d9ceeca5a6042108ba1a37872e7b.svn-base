package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_d_freezeflg")
public class FreezeFlg implements Serializable {
    private static final long serialVersionUID = 5843657790736610177L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 集中器编号
     */
    @Column(name = "con_no")
    private String conNo;

    /**
     * 测量点号
     */
    @Column(name = "point_code")
    private Integer pointCode;

    /**
     * 电表地址
     */
    @Column(name = "comm_addr")
    private String commAddr;

    /**
     * 抄表数据项掩码(上一次日冻结时间、上一次日冻结数据、剩余金额、上一次累计购电次数、上一次累计购电金额）
     */
    @Column(name = "freeze_flg")
    private Integer freezeFlg;

    /**
     * 冻结日期
     */
    @Column(name = "freeze_date")
    private Date freezeDate;

    @Column(name = "group_guid")
    private String groupGuid;

    /**
     * 设备唯一标识
     */
    @Column(name = "dev_cno")
    private String devCno;

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
     * 获取集中器编号
     *
     * @return con_no - 集中器编号
     */
    public String getConNo() {
        return conNo;
    }

    /**
     * 设置集中器编号
     *
     * @param conNo 集中器编号
     */
    public void setConNo(String conNo) {
        this.conNo = conNo;
    }

    /**
     * 获取测量点号
     *
     * @return point_code - 测量点号
     */
    public Integer getPointCode() {
        return pointCode;
    }

    /**
     * 设置测量点号
     *
     * @param pointCode 测量点号
     */
    public void setPointCode(Integer pointCode) {
        this.pointCode = pointCode;
    }

    /**
     * 获取电表地址
     *
     * @return comm_addr - 电表地址
     */
    public String getCommAddr() {
        return commAddr;
    }

    /**
     * 设置电表地址
     *
     * @param commAddr 电表地址
     */
    public void setCommAddr(String commAddr) {
        this.commAddr = commAddr;
    }

    /**
     * 获取抄表数据项掩码(上一次日冻结时间、上一次日冻结数据、剩余金额、上一次累计购电次数、上一次累计购电金额）
     *
     * @return freeze_flg - 抄表数据项掩码(上一次日冻结时间、上一次日冻结数据、剩余金额、上一次累计购电次数、上一次累计购电金额）
     */
    public Integer getFreezeFlg() {
        return freezeFlg;
    }

    /**
     * 设置抄表数据项掩码(上一次日冻结时间、上一次日冻结数据、剩余金额、上一次累计购电次数、上一次累计购电金额）
     *
     * @param freezeFlg 抄表数据项掩码(上一次日冻结时间、上一次日冻结数据、剩余金额、上一次累计购电次数、上一次累计购电金额）
     */
    public void setFreezeFlg(Integer freezeFlg) {
        this.freezeFlg = freezeFlg;
    }

    /**
     * 获取冻结日期
     *
     * @return freeze_date - 冻结日期
     */
    public Date getFreezeDate() {
        return freezeDate;
    }

    /**
     * 设置冻结日期
     *
     * @param freezeDate 冻结日期
     */
    public void setFreezeDate(Date freezeDate) {
        this.freezeDate = freezeDate;
    }

    /**
     * @return group_guid
     */
    public String getGroupGuid() {
        return groupGuid;
    }

    /**
     * @param groupGuid
     */
    public void setGroupGuid(String groupGuid) {
        this.groupGuid = groupGuid;
    }

    /**
     * 获取设备唯一标识
     *
     * @return dev_cno - 设备唯一标识
     */
    public String getDevCno() {
        return devCno;
    }

    /**
     * 设置设备唯一标识
     *
     * @param devCno 设备唯一标识
     */
    public void setDevCno(String devCno) {
        this.devCno = devCno;
    }
}