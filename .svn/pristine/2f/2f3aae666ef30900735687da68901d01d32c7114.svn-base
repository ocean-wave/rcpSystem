package cn.com.cdboost.collect.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 通断明细，返回给前端的信息
 */
public class FeeOnOffDetailInfo {
    /**
     * 用户姓名
     */
    private String customerName;

    /**
     * 用户地址
     */
    private String customerAddr;

    /**
     * 通断状态 (0 -断开 1-连通)
     */
    private Integer onOff;

    /**
     * 操作结果 1标识成功 2标识失败
     */
    private int status;

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 通断原因
     */
    private String remark;

    /**
     * 操作人员
     */
    private String userName;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddr() {
        return customerAddr;
    }

    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr;
    }

    public Integer getOnOff() {
        return onOff;
    }

    public void setOnOff(Integer onOff) {
        this.onOff = onOff;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
