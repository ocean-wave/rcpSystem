package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 采集数据的状态，查询参数
 */
public class SendCollectStateQueryParam {
    /**
     * 实时召测时，前端生成的guid
     */
    @NotBlank(message = "guid不能为空")
    private String guid;

    /**
     * 设备类型
     */
    @NotBlank(message = "deviceType不能为空")
    private String deviceType;

    /**
     * 日期
     */
    @NotBlank(message = "searchDate不能为空")
    private String searchDate;

    /**
     * 停止标记
     */
    @NotNull(message = "stopFlag不能为空")
    private Integer stopFlag;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

    public Integer getStopFlag() {
        return stopFlag;
    }

    public void setStopFlag(Integer stopFlag) {
        this.stopFlag = stopFlag;
    }
}
