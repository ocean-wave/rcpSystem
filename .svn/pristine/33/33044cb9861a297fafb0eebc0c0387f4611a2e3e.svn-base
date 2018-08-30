package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.Length;

/**
 * 新增客户档案时，气表添加传入参数
 */
public class GasMeterAddParam extends BaseDeviceAddParam {
    /**
     * 气表类型，两位数字
     */
    @Length(min=2, max=2, message = "gasType长度必须是2位")
    private String gasType;

    public String getGasType() {
        return gasType;
    }

    public void setGasType(String gasType) {
        this.gasType = gasType;
    }
}
