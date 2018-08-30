package cn.com.cdboost.collect.dto.param;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 电表更换操作输入参数
 */
public class ChangeElectricMeterParam extends ChangeMeterBaseParam{

    /**
     * 旧电表相关信息
     */
    @NotNull(message = "oldElectricMeter不能为null")
    @Valid
    private OldElectricMeterParam oldElectricMeter;

    /**
     * 新电表相关信息
     */
    @NotNull(message = "newElectricMeter不能为null")
    @Valid
    private NewElectricMeterParam newElectricMeter;

    public OldElectricMeterParam getOldElectricMeter() {
        return oldElectricMeter;
    }

    public void setOldElectricMeter(OldElectricMeterParam oldElectricMeter) {
        this.oldElectricMeter = oldElectricMeter;
    }

    public NewElectricMeterParam getNewElectricMeter() {
        return newElectricMeter;
    }

    public void setNewElectricMeter(NewElectricMeterParam newElectricMeter) {
        this.newElectricMeter = newElectricMeter;
    }
}
