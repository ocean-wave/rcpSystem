package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.QueryListParam;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 远程通断列表查询vo
 */
public class CstOnOffGetQueryParam extends QueryListParam {
    /**
     * 用户表号
     */
    private String deviceNo;

    /**
     * 用户姓名
     */
    private String customerName;

    /**
     * 用户地址
     */
    private String customerAddr;

    /**
     * 联系电话
     */
    private String customerContact;

    /**
     * 表计户号
     */
    private String meterUserNo;

    /**
     * 设备类型
     */
    @NotBlank(message = "deviceType不能为空")
    private String deviceType;

    /**
     * 1通电,0断点，不传查全部状态
     */
    private Integer onOff;

    @Override
    protected String defaultSortName() {
        return null;
    }

    @Override
    protected String defaultSortOrder() {
        return null;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

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

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getOnOff() {
        return onOff;
    }

    public void setOnOff(Integer onOff) {
        this.onOff = onOff;
    }
}
