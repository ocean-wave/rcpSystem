package cn.com.cdboost.collect.param;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author wt
 * @desc
 * @create in  2018/5/7
 **/
public class CustomerOnOffParam extends BasequeryParam {
    @NotBlank(message = "onoff不能为空")
    private int onOff;
    @NotBlank(message = "deviceType不能为空")
    private String deviceType;

    private String meterNo;

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public int getOnOff() {
        return onOff;
    }

    public void setOnOff(int onOff) {
        this.onOff = onOff;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
