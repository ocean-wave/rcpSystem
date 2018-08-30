package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.QueryListParam;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 充值缴费记录查询vo
 */
public class RechargeRecordQueryParam extends QueryListParam{
    /**
     * 用户唯一标识
     */
    private String customerNo;
    /**
     * 门牌编号
     */
    private String propertyName;
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
     * 用户表号
     */
    private String deviceNo;

    /**
     * 查询开始日期
     */
    @NotBlank(message = "startDate不能为空")
    private String startDate;

    /**
     * 查询结束日期
     */
    @NotBlank(message = "endDate不能为空")
    private String endDate;

    /**
     * 设备类型
     */
    @NotBlank(message = "deviceType不能为空")
    private String deviceType;

    /**
     * 前端查询条件状态对应字段
     * 1充值成功，-2正在远程充值，2已取消远程充值，-1写卡失败，3远程充值失败，不传表示查全部状态
     */
    private String writeMeter;

    /**
     * 1远程充值，2售电卡，不传表示查全部
     */
    private String payModel;
    /**
     * 支付方式
     */
    private String payMethod;

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    protected String defaultSortName() {
        return null;
    }

    @Override
    protected String defaultSortOrder() {
        return null;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
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

    public String getWriteMeter() {
        return writeMeter;
    }

    public void setWriteMeter(String writeMeter) {
        this.writeMeter = writeMeter;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPayModel() {
        return payModel;
    }

    public void setPayModel(String payModel) {
        this.payModel = payModel;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }
}
