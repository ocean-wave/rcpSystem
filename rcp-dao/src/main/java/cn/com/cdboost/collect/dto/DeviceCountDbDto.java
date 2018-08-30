package cn.com.cdboost.collect.dto;

public class DeviceCountDbDto {
    /**
     * 通执行日期
     */
    private String startDate;
    /**
     * 通执行时间
     */
    private String startTime;
    /**
     * 断执行日期
     */
    private String endDate;
    /**
     * 断执行时间
     */
    private String endTime;

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
}
