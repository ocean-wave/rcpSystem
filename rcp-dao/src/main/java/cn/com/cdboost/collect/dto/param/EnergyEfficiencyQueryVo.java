package cn.com.cdboost.collect.dto.param;

import java.util.List;

/**
 * 能效分析查询vo
 */
public class EnergyEfficiencyQueryVo extends PageQueryVo{
    private String beginDate;
    private String endDate;
    //小区名称
    private String orgName;
    private Integer model;
    private String orgNo;
    private Integer elecType;
    private String meterUserNo;
    private String countDate;
    private Integer userId;
    private Integer usePowerType;

    @Override
    public Integer getPageIndex() {
        return (this.getPageNumber()-1)*this.getPageSize();
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public Integer getElecType() {
        return elecType;
    }

    public void setElecType(Integer elecType) {
        this.elecType = elecType;
    }

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }

    public String getCountDate() {
        return countDate;
    }

    public void setCountDate(String countDate) {
        this.countDate = countDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUsePowerType() {
        return usePowerType;
    }

    public void setUsePowerType(Integer usePowerType) {
        this.usePowerType = usePowerType;
    }
}
