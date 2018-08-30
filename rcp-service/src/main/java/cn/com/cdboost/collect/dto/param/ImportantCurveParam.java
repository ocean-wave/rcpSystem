package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.QueryListParam;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 重点用户曲线
 */
public class ImportantCurveParam {

    /**
     * 用户编号
     */
    @NotBlank(message = "customerNo 不能为空")
    private String customerNo ;

    /**
     * 表计户号
     */
    @NotBlank(message = "meterUserNo 不能为空")
    private String meterUserNo ;

    /**
     * 时间
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
    @NotBlank(message = "model 不能为空")
    /**
     * 1-按月
     2-按日
     3-按小时
     */
    private String model ;
    private String endTime;
    @NotBlank(message = "deviceType 不能为空")
    /**
     * 设备类型
     */
    private String deviceType;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
