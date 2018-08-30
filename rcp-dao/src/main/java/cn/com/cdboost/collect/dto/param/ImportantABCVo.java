package cn.com.cdboost.collect.dto.param;

/**
 * Created by Administrator on 2017/12/14 0014.
 */
public class ImportantABCVo extends PageQueryVo {

    /**
     * 设备编号
     */
    private String customerNo ;

    /**
     * 用户姓名
     */
    private String meterUserNo ;

    /**
     * 用户地址
     */
    private String dataMark ;

    /**
     * 起始时间
     */
    private String deviceType;
    /**
     * 联系电话
     */
    private String searchType  ;

    private String startTime;
    private String endTime;

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

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }



    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }



    public String getDataMark() {
        return dataMark;
    }

    public void setDataMark(String dataMark) {
        this.dataMark = dataMark;
    }


}
