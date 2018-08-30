package cn.com.cdboost.collect.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 设备档案，集中器，采集器明细公共字段
 */
public class JzqCjqDetailBaseInfo {
    /**
     * 表号
     */
    private String deviceNo;

    /**
     * 生产厂家
     */
    private String deviceFactory;

    /**
     * 安装日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date installDate;

    /**
     * 备注
     */
    private String remark;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getDeviceFactory() {
        return deviceFactory;
    }

    public void setDeviceFactory(String deviceFactory) {
        this.deviceFactory = deviceFactory;
    }

    public Date getInstallDate() {
        return installDate;
    }

    public void setInstallDate(Date installDate) {
        this.installDate = installDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
