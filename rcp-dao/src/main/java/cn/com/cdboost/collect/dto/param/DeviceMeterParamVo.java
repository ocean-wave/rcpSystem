package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.model.DeviceMeterParam;

/**
 * Created by Administrator on 2017/12/26 0026.
 */
public class DeviceMeterParamVo extends DeviceMeterParam {
    private Integer meterType;

    /**
     * 用于接收存储过程返回值
     */
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getMeterType() {
        return meterType;
    }

    public void setMeterType(Integer meterType) {
        this.meterType = meterType;
    }
}
