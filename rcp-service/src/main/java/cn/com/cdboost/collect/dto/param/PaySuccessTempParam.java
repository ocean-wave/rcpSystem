package cn.com.cdboost.collect.dto.param;

/**
 * 阿里云短信，缴费到账短信模板参数
 */
public class PaySuccessTempParam extends RemoteSuccessTempParam {
    /**
     * 设备类型名称
     */
    private String deviceTypeName2;

    public String getDeviceTypeName2() {
        return deviceTypeName2;
    }

    public void setDeviceTypeName2(String deviceTypeName2) {
        this.deviceTypeName2 = deviceTypeName2;
    }
}
