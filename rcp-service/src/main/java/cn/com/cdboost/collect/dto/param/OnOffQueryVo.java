package cn.com.cdboost.collect.dto.param;

/**
 * 通断查询vo
 */
public class OnOffQueryVo extends PageQueryVo{
    /**
     * 用户唯一标识
     */
    private String customerNo;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 表计户号
     */
    private String meterUserNo;

    /**
     * 查询开始日期
     */
    private String startTime;

    /**
     * 查询结束日期
     */
    private String endTime;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
