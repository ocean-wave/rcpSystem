package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.QueryListParam;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 实时召测成功列表查询vo
 */
public class CollectDataSuccessQueryParam extends QueryListParam{
    /**
     * 设备类型
     */
    @NotBlank(message = "deviceType不能为空")
    private String deviceType;

    /**
     * 实时召测，前端生成的guid，该字段前端只会在实时召测的时候刷新列表传给后端
     */
    @NotBlank(message = "guid不能为空")
    private String guid;

    /**
     * 查询时间
     */
    @NotBlank(message = "searchDate不能为空")
    private String searchDate;

    @Override
    protected String defaultSortName() {
        return null;
    }

    @Override
    protected String defaultSortOrder() {
        return null;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }
}
