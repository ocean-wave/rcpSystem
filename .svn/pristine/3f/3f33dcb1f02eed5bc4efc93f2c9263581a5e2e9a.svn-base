package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 电表更换时，新旧电表需要传入的参数，这只是个基类
 */
public class ElectricMeterChangeParam {
    /**
     * 电表表号
     */
    @NotBlank(message = "deviceNo不能为空")
    private String deviceNo;

    /**
     * 剩余金额
     */
    @NotNull(message = "remainAmount不能为null")
    private BigDecimal remainAmount;

    /**
     * 总示数
     */
    @NotNull(message = "readValue不能为null")
    private BigDecimal readValue;

    /**
     * 尖示数
     */
    private BigDecimal readValue1;

    /**
     * 峰示数
     */
    private BigDecimal readValue2;

    /**
     * 平示数
     */
    private BigDecimal readValue3;

    /**
     * 谷示数
     */
    private BigDecimal readValue4;

    /**
     * 参数标识
     */
    @NotBlank(message = "paramFlag不能为")
    private String paramFlag;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public BigDecimal getReadValue() {
        return readValue;
    }

    public void setReadValue(BigDecimal readValue) {
        this.readValue = readValue;
    }

    public BigDecimal getReadValue1() {
        return readValue1;
    }

    public void setReadValue1(BigDecimal readValue1) {
        this.readValue1 = readValue1;
    }

    public BigDecimal getReadValue2() {
        return readValue2;
    }

    public void setReadValue2(BigDecimal readValue2) {
        this.readValue2 = readValue2;
    }

    public BigDecimal getReadValue3() {
        return readValue3;
    }

    public void setReadValue3(BigDecimal readValue3) {
        this.readValue3 = readValue3;
    }

    public BigDecimal getReadValue4() {
        return readValue4;
    }

    public void setReadValue4(BigDecimal readValue4) {
        this.readValue4 = readValue4;
    }

    public String getParamFlag() {
        return paramFlag;
    }

    public void setParamFlag(String paramFlag) {
        this.paramFlag = paramFlag;
    }
}
