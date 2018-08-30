package cn.com.cdboost.collect.dto.param;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 客户档案创建时，前端需要传入的参数
 */
public class CustomerInfoCreateParam {
    /**
     * 客户档案新增时，填入的客户档案基本信息
     */
    @NotNull(message = "customerInfo不能为null")
    @Valid
    private CustomerInfoAddParam customerInfo;

    /**
     * 电表信息
     */
    @Valid
    private List<ElectricMeterAddParam> electricMeter;

    /**
     * 水表信息
     */
    @Valid
    private List<WaterMeterAddParam> waterMeter;

    /**
     * 气表信息
     */
    @Valid
    private List<GasMeterAddParam> gasMeter;

    public CustomerInfoAddParam getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfoAddParam customerInfo) {
        this.customerInfo = customerInfo;
    }

    public List<ElectricMeterAddParam> getElectricMeter() {
        return electricMeter;
    }

    public void setElectricMeter(List<ElectricMeterAddParam> electricMeter) {
        this.electricMeter = electricMeter;
    }

    public List<WaterMeterAddParam> getWaterMeter() {
        return waterMeter;
    }

    public void setWaterMeter(List<WaterMeterAddParam> waterMeter) {
        this.waterMeter = waterMeter;
    }

    public List<GasMeterAddParam> getGasMeter() {
        return gasMeter;
    }

    public void setGasMeter(List<GasMeterAddParam> gasMeter) {
        this.gasMeter = gasMeter;
    }
}
