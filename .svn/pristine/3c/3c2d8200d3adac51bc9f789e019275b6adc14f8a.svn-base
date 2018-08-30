package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 换表操作时，旧气表传入参数
 */
public class OldGasMeterParam {
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
     * 旧表最后一次算费总示数
     */
    @NotNull(message = "lastReadValue不能为null")
    private BigDecimal lastReadValue;

    /**
     * 剩余金额
     */
    @NotNull(message = "remainAmount不能为null")
    private BigDecimal remainAmount;

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

    public BigDecimal getLastReadValue() {
        return lastReadValue;
    }

    public void setLastReadValue(BigDecimal lastReadValue) {
        this.lastReadValue = lastReadValue;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }
}
