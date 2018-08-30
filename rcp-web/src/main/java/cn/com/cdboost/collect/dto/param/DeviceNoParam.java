package cn.com.cdboost.collect.dto.param;

/**
 * 远程通断列表查询vo
 */
public class DeviceNoParam  {

    private String electricDeviceNo;
    private String waterDeviceNo;
    private String gasDeviceNo;

    public String getElectricDeviceNo() {
        return electricDeviceNo;
    }

    public void setElectricDeviceNo(String electricDeviceNo) {
        this.electricDeviceNo = electricDeviceNo;
    }

    public String getWaterDeviceNo() {
        return waterDeviceNo;
    }

    public void setWaterDeviceNo(String waterDeviceNo) {
        this.waterDeviceNo = waterDeviceNo;
    }

    public String getGasDeviceNo() {
        return gasDeviceNo;
    }

    public void setGasDeviceNo(String gasDeviceNo) {
        this.gasDeviceNo = gasDeviceNo;
    }
}
