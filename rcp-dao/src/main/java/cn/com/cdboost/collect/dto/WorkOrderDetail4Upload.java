package cn.com.cdboost.collect.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 工单采集信息上传时，工单关联的设备明细信息
 */
public class WorkOrderDetail4Upload {
    /**
     * 客户唯一标识
     */
    @NotBlank(message = "customerNo不能为空")
    private String customerNo;

    /**
     * 表的唯一标识
     */
    @NotBlank(message = "cno不能为空")
    private String cno;

    /**
     * 采集数据标识
     */
    @NotBlank(message = "groupGuid不能为空")
    private String groupGuid;

    /**
     * 采集顺序
     */
    @NotBlank(message = "collectSort不能为空")
    private String collectSort;

    /**
     * 采集标识 0未采集 1已采集
     */
    @NotBlank(message = "flag不能为空")
    private String flag;

    /**
     * 采集方式 1标识红外 2标识手动采集
     */
    @NotBlank(message = "dataSrc不能为空")
    private String dataSrc;

    /**
     * 补抄时间
     */
    private Date suppTime;

    /**
     * 电表异常代码
     */
    @NotNull(message = "errCode不能为空")
    private Integer errCode;

    /**
     * 异常信息
     */
    private String errInfo;

    /**
     * 该电表对应的抄表项以及对应的数据
     */
    @NotEmpty(message = "collectDataItems列表不能为空")
    @Valid
    private List<CollectDataItem4Upload> collectDataItems;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getGroupGuid() {
        return groupGuid;
    }

    public void setGroupGuid(String groupGuid) {
        this.groupGuid = groupGuid;
    }

    public String getCollectSort() {
        return collectSort;
    }

    public void setCollectSort(String collectSort) {
        this.collectSort = collectSort;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDataSrc() {
        return dataSrc;
    }

    public void setDataSrc(String dataSrc) {
        this.dataSrc = dataSrc;
    }

    public Date getSuppTime() {
        return suppTime;
    }

    public void setSuppTime(Date suppTime) {
        this.suppTime = suppTime;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public List<CollectDataItem4Upload> getCollectDataItems() {
        return collectDataItems;
    }

    public void setCollectDataItems(List<CollectDataItem4Upload> collectDataItems) {
        this.collectDataItems = collectDataItems;
    }
}
