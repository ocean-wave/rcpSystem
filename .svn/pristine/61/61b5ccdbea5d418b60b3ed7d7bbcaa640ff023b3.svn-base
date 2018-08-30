package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * 实时召测弹出框，下载时传入参数
 */
public class PopupDownloadQueryParam {
    /**
     * 前端召测时，生成的guid
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
     * 1代表成功数据；0代表失败数据
     */
    private Integer flag;
    /**
     * di 列表
     */
    private List<String> types;

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
