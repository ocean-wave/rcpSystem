package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 换表操作时，新气表传入参数
 */
public class NewGasMeterParam {

    /**
     * 气表类型
     */
    @NotBlank(message = "gasType不能为空")
    private String gasType;

    /**
     * 气表表号
     */
    @NotBlank(message = "deviceNo不能为空")
    private String deviceNo;

    /**
     * 总示数
     */
    @NotNull(message = "readValue不能为null")
    private BigDecimal readValue;

    /**
     * 剩余金额
     */
    @NotNull(message = "remainAmount不能为null")
    private BigDecimal remainAmount;

    /**
     * 参数标识
     */
    @NotBlank(message = "paramFlag不能为空")
    private String paramFlag;

    /**
     * 设备厂家字典表值
     */
    @NotBlank(message = "deviceFactoryValue不能为")
    private String deviceFactoryValue;

    public String getGasType() {
        return gasType;
    }

    public void setGasType(String gasType) {
        this.gasType = gasType;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public BigDecimal getReadValue() {
        return readValue;
    }

    public void setReadValue(BigDecimal readValue) {
        this.readValue = readValue;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public String getParamFlag() {
        return paramFlag;
    }

    public void setParamFlag(String paramFlag) {
        this.paramFlag = paramFlag;
    }

    public String getDeviceFactoryValue() {
        return deviceFactoryValue;
    }

    public void setDeviceFactoryValue(String deviceFactoryValue) {
        this.deviceFactoryValue = deviceFactoryValue;
    }
}
