package cn.com.cdboost.collect.dto.param;

public class MeterDayPowerQueryVo {
    private Long orgNo;
    private Integer usePowerType;
    private String meterUserNo;
    private String countDate;

    public Long getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(Long orgNo) {
        this.orgNo = orgNo;
    }

    public Integer getUsePowerType() {
        return usePowerType;
    }

    public void setUsePowerType(Integer usePowerType) {
        this.usePowerType = usePowerType;
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
}
