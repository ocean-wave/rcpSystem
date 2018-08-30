package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 换表操作时，新电表传入参数
 */
public class NewElectricMeterParam extends ElectricMeterChangeParam{

    /**
     * 预留金额
     */
    @NotNull(message = "initAmount不能为null")
    private BigDecimal initAmount;

    /**
     * 设备厂家字典表值
     */
    @NotBlank(message = "deviceFactoryValue不能为")
    private String deviceFactoryValue;

    public BigDecimal getInitAmount() {
        return initAmount;
    }

    public void setInitAmount(BigDecimal initAmount) {
        this.initAmount = initAmount;
    }

    public String getDeviceFactoryValue() {
        return deviceFactoryValue;
    }

    public void setDeviceFactoryValue(String deviceFactoryValue) {
        this.deviceFactoryValue = deviceFactoryValue;
    }
}
