package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.Length;

/**
 * 新增客户档案时，水表添加传入参数
 */
public class WaterMeterAddParam extends BaseDeviceAddParam{
    /**
     * 水表类型，两位数字
     */
    @Length(min=2, max=2, message = "waterType长度必须是2位")
    private String waterType;

    public String getWaterType() {
        return waterType;
    }

    public void setWaterType(String waterType) {
        this.waterType = waterType;
    }
}
