package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_d_metersuppinfo")
public class MeterSuppInfo implements Serializable {
    private static final long serialVersionUID = -7244510452321863597L;
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
     * 任务编号
     */
    @Column(name = "task_no")
    private String taskNo;

    /**
     * 设备编号
     */
    private String cno;

    /**
     * 补采人员
     */
    @Column(name = "supp_user_id")
    private Integer suppUserId;

    /**
     * 补采时间
     */
    @Column(name = "supp_time")
    private Date suppTime;

    /**
     * 标志 0 待抄 1已采
     */
    private Integer flag;

    /**
     * 采集顺序
     */
    @Column(name = "collect_sort")
    private Integer collectSort;

    /**
     * 采集分组的GroupGuid
     */
    @Column(name = "group_guid")
    private String groupGuid;

    /**
     * 1-红外 2-手动
     */
    @Column(name = "data_src")
    private Integer dataSrc;

    /**
     * 异常码
     */
    @Column(name = "err_code")
    private Integer errCode;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 异常信息
     */
    @Column(name = "err_info")
    private String errInfo;

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
     * 获取补采人员
     *
     * @return supp_user_id - 补采人员
     */
    public Integer getSuppUserId() {
        return suppUserId;
    }

    /**
     * 设置补采人员
     *
     * @param suppUserId 补采人员
     */
    public void setSuppUserId(Integer suppUserId) {
        this.suppUserId = suppUserId;
    }

    /**
     * 获取补采时间
     *
     * @return supp_time - 补采时间
     */
    public Date getSuppTime() {
        return suppTime;
    }

    /**
     * 设置补采时间
     *
     * @param suppTime 补采时间
     */
    public void setSuppTime(Date suppTime) {
        this.suppTime = suppTime;
    }

    /**
     * 获取标志 0 待抄 1已采
     *
     * @return flag - 标志 0 待抄 1已采
     */
    public Integer getFlag() {
        return flag;
    }

    /**
     * 设置标志 0 待抄 1已采
     *
     * @param flag 标志 0 待抄 1已采
     */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    /**
     * 获取采集顺序
     *
     * @return collect_sort - 采集顺序
     */
    public Integer getCollectSort() {
        return collectSort;
    }

    /**
     * 设置采集顺序
     *
     * @param collectSort 采集顺序
     */
    public void setCollectSort(Integer collectSort) {
        this.collectSort = collectSort;
    }

    /**
     * 获取采集分组的GroupGuid
     *
     * @return group_guid - 采集分组的GroupGuid
     */
    public String getGroupGuid() {
        return groupGuid;
    }

    /**
     * 设置采集分组的GroupGuid
     *
     * @param groupGuid 采集分组的GroupGuid
     */
    public void setGroupGuid(String groupGuid) {
        this.groupGuid = groupGuid;
    }

    /**
     * 获取1-红外 2-手动
     *
     * @return data_src - 1-红外 2-手动
     */
    public Integer getDataSrc() {
        return dataSrc;
    }

    /**
     * 设置1-红外 2-手动
     *
     * @param dataSrc 1-红外 2-手动
     */
    public void setDataSrc(Integer dataSrc) {
        this.dataSrc = dataSrc;
    }

    /**
     * 获取异常码
     *
     * @return err_code - 异常码
     */
    public Integer getErrCode() {
        return errCode;
    }

    /**
     * 设置异常码
     *
     * @param errCode 异常码
     */
    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
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

    public String getErrInfo() {
        return errInfo;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }
}