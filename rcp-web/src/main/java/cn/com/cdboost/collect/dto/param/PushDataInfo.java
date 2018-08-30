package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.SmokeDeviceSelectTotalInfo;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author zc
 * @desc 烟感主动上报数据
 * @create 2018-01-04 14:17
 **/
public class PushDataInfo {

    private String cno;
    private String devType;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private String DataTime;
    private String params;
    private SmokeDeviceSelectTotalInfo statusTotal;

    public SmokeDeviceSelectTotalInfo getStatusTotal() {
        return statusTotal;
    }

    public void setStatusTotal(SmokeDeviceSelectTotalInfo statusTotal) {
        this.statusTotal = statusTotal;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public String getDataTime() {
        return DataTime;
    }

    public void setDataTime(String dataTime) {
        DataTime = dataTime;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
