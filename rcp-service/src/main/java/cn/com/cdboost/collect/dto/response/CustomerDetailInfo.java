package cn.com.cdboost.collect.dto.response;

import java.util.List;

/**
 * 客户档案明细页面，需要显示的信息
 */
public class CustomerDetailInfo {
    /**
     * 用户名称
     */
    private CustomerInfoDetailInfo customerInfo;

    /**
     * 电表信息
     */
    private List<ElectricMeterInfo> electricMeter;

    /**
     * 水表信息
     */
    private List<WaterMeterInfo> waterMeter;

    /**
     * 气表信息
     */
    private List<GasMeterInfo> gasMeter;



    public CustomerInfoDetailInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfoDetailInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public List<ElectricMeterInfo> getElectricMeter() {
        return electricMeter;
    }

    public void setElectricMeter(List<ElectricMeterInfo> electricMeter) {
        this.electricMeter = electricMeter;
    }

    public List<WaterMeterInfo> getWaterMeter() {
        return waterMeter;
    }

    public void setWaterMeter(List<WaterMeterInfo> waterMeter) {
        this.waterMeter = waterMeter;
    }

    public List<GasMeterInfo> getGasMeter() {
        return gasMeter;
    }

    public void setGasMeter(List<GasMeterInfo> gasMeter) {
        this.gasMeter = gasMeter;
    }
}
