package cn.com.cdboost.collect.dto.param;

/**
 * 抄表采集失败数据，查询vo
 */
public class RealMeterCollectFailQueryVo extends PageQueryVo{
    /**
     * guid
     */
    private String guid;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * date
     */
    private String searchDate;

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
}
