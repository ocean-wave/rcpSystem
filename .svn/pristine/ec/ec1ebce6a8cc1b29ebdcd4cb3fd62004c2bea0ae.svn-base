package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 新增客户档案时，电表添加传入参数
 */
public class ElectricMeterAddParam extends BaseDeviceAddParam{
    /**
     * 费控方式
     * 电表：远程费控，本地费控，其他
     */
    @NotNull(message = "localControl不能为null")
    private Integer localControl;

    /**
     * 电流互感变比
     */
    @NotNull(message = "ratio不能为null")
    private Integer ratio;

    /**
     * 正向尖示数
     */
    private BigDecimal readValue1;

    /**
     * 正向峰示数
     */
    private BigDecimal readValue2;

    /**
     * 正向平示数
     */
    private BigDecimal readValue3;

    /**
     * 正向谷示数
     */
    private BigDecimal readValue4;

    /**
     * 用电类型值,取字典表中的值 code=19
     */
    @NotBlank(message = "elecType不能为空")
    private String elecType;
    /**
     * 电表类别,取字典表中的值 code=20
     */
    @NotBlank(message = "elecMeterCategory不能为null")
    private String elecMeterCategory;

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    public Integer getLocalControl() {
        return localControl;
    }

    public void setLocalControl(Integer localControl) {
        this.localControl = localControl;
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

    public String getElecType() {
        return elecType;
    }

    public void setElecType(String elecType) {
        this.elecType = elecType;
    }

    public String getElecMeterCategory() {
        return elecMeterCategory;
    }

    public void setElecMeterCategory(String elecMeterCategory) {
        this.elecMeterCategory = elecMeterCategory;
    }
}
