package cn.com.cdboost.collect.dto.param;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 水表更换操作输入参数
 */
public class ChangeWaterMeterParam extends ChangeMeterBaseParam{

    /**
     * 旧水表相关信息
     */
    @NotNull(message = "oldWaterMeter不能为null")
    @Valid
    private OldWaterMeterParam oldWaterMeter;

    /**
     * 新水表相关信息
     */
    @NotNull(message = "newWaterMeter不能为null")
    @Valid
    private NewWaterMeterParam newWaterMeter;

    public OldWaterMeterParam getOldWaterMeter() {
        return oldWaterMeter;
    }

    public void setOldWaterMeter(OldWaterMeterParam oldWaterMeter) {
        this.oldWaterMeter = oldWaterMeter;
    }

    public NewWaterMeterParam getNewWaterMeter() {
        return newWaterMeter;
    }

    public void setNewWaterMeter(NewWaterMeterParam newWaterMeter) {
        this.newWaterMeter = newWaterMeter;
    }
}
