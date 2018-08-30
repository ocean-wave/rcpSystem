package cn.com.cdboost.collect.dto.param;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AddSchemeParam {
    /**
     * 方案名称
     */
    @NotNull(message = "方案名称不能为空")
    private String schemeName;
    /**
     * 方案类型
     */
    @NotNull(message = "方案类型不能为空")
    private Integer schemeType;
    /**
     * 通阀起始日期
     */
    private String startDate;
    /**
     * 通阀起始时间
     */
    private String startTime;
    /**
     * 断阀起始日期
     */
    private String endDate;
    /**
     *断阀起始时间
     */
    private String endTime;
    /**
     * 方案备注
     */
    private String remark;

    List<AddSchemeNodeParam> meterList;

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public Integer getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(Integer schemeType) {
        this.schemeType = schemeType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<AddSchemeNodeParam> getMeterList() {
        return meterList;
    }

    public void setMeterList(List<AddSchemeNodeParam> meterList) {
        this.meterList = meterList;
    }
}
