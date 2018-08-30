package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.QueryListParam;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 采集器页签，查询参数
 */
public class FreeDayByCollGetQueryParam extends QueryListParam {

    /**
     * 冻结日期
     */
    @NotBlank(message = "freezeDate不能为空")
    private String freezeDate;

    /**
     * 设备类型
     */
    @NotBlank(message = "deviceType不能为空")
    private String deviceType;

    /**
     * 节点类型
     */
    private String moteEui;

    @Override
    protected String defaultSortName() {
        return null;
    }

    @Override
    protected String defaultSortOrder() {
        return null;
    }

    public String getFreezeDate() {
        return freezeDate;
    }

    public void setFreezeDate(String freezeDate) {
        this.freezeDate = freezeDate;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getMoteEui() {
        return moteEui;
    }

    public void setMoteEui(String moteEui) {
        this.moteEui = moteEui;
    }
}
