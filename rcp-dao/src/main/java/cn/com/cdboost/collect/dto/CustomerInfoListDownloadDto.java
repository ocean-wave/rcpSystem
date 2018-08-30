package cn.com.cdboost.collect.dto;

/**
 * 客户档案列表下载相关dto
 */
public class CustomerInfoListDownloadDto extends CustomerInfoDto {

    /**
     * 电表表号
     */
    private String electricDeviceNo;

    /**
     * 水表表号
     */
    private String waterDeviceNo;

    /**
     * 气表表号
     */
    private String gasDeviceNo;

    /**
     * 表计户号
     */
    private String meterUserNo;

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

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }
}
