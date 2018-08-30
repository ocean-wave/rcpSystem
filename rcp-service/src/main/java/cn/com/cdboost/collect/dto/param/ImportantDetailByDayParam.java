package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

/**
 *重点用户按
 */
public class ImportantDetailByDayParam {

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
    private String startTime;
    /**
     * 联系电话
     */
    private String model ;
    @NotBlank(message = "deviceType 不能为空")
    private String deviceType;

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

    public String getModel() {
        return model;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setModel(String model) {
        this.model = model;
    }


}
