package cn.com.cdboost.collect.dto;

import cn.com.cdboost.collect.vo.QueryListParamDate;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author boost
 */
public class TotalLineLossDto extends QueryListParamDate {

    @NotBlank(message = "deviceNo不能为null")
    private String deviceNo;
    @NotBlank(message = "deviceType不能为null")
    private String deviceType;
    private String endDateLast;
    private String deviceCno;

    public String getEndDateLast() {
        return endDateLast;
    }

    public void setEndDateLast(String endDateLast) {
        this.endDateLast = endDateLast;
    }

    public String getDeviceCno() {
        return deviceCno;
    }

    public void setDeviceCno(String deviceCno) {
        this.deviceCno = deviceCno;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    protected String defaultSortName() {
        return null;
    }

    @Override
    protected String defaultSortOrder() {
        return null;
    }
}
