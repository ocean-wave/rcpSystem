package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.QueryListParam;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 换表列表查询参数
 */
public class ChangeMeterListQueryParam extends QueryListParam{
    /**
     * 设备编号
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
     * 查询开始日期
     */
    @NotBlank(message = "changeDateStart不能为空")
    private String changeDateStart;

    /**
     * 查询结束日期
     */
    @NotBlank(message = "changeDateEnd不能为空")
    private String changeDateEnd;

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

    public String getChangeDateStart() {
        return changeDateStart;
    }

    public void setChangeDateStart(String changeDateStart) {
        this.changeDateStart = changeDateStart;
    }

    public String getChangeDateEnd() {
        return changeDateEnd;
    }

    public void setChangeDateEnd(String changeDateEnd) {
        this.changeDateEnd = changeDateEnd;
    }
}
