package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 通断电操作，传入参数
 */
public class FeeOnOffQueryParam {
    /**
     * guid
     */
    @NotBlank(message = "guid不能为空")
    private String guid;

    /**
     * 1通电，0断电
     */
    @NotNull(message = "onOff不能为null")
    private Integer onOff;

    /**
     * 原因
     */
    @NotBlank(message = "reason不能为空")
    private String reason;

    /**
     * 通断电设备列表
     */
    @NotEmpty(message = "meters列表不能为空")
    @Valid
    private List<OnOffMeterVo> meters;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Integer getOnOff() {
        return onOff;
    }

    public void setOnOff(Integer onOff) {
        this.onOff = onOff;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<OnOffMeterVo> getMeters() {
        return meters;
    }

    public void setMeters(List<OnOffMeterVo> meters) {
        this.meters = meters;
    }
}
