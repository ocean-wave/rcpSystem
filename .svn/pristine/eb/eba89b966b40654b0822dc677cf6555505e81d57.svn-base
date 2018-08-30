package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 历史数据列表，查询参数
 */
public class ImportantABCParam {

    /**
     * 设备编号
     */
    @NotBlank(message = "customerNo 不能为空")
    private String customerNo ;

    /**
     * 用户姓名
     */
    @NotBlank(message = "meterUserNo 不能为空")
    private String meterUserNo ;

    /**
     * 用户地址
     */
    @NotBlank(message = "dataMark 不能为空")
    private String dataMark ;

    /**
     * 起始时间
     */
    private String deviceType;
    /**
     * 联系电话
     */
    @NotBlank(message = "searchType 不能为空")
    private String searchType  ;

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

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
}
