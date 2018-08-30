package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 用能统计页面，柱状图查询参数
 */
public class HistogramDataQueryParam {

    /**
     * 查询开始时间
     */
    @NotBlank(message = "startDate不能为空")
    private String startDate;

    /**
     * 查询结束时间
     */
    @NotBlank(message = "endDate不能为空")
    private String endDate;

    /**
     * 设备类型
     */
    private String deviceType;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
