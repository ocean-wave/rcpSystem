package cn.com.cdboost.collect.dto.chargerApp;

import java.math.BigDecimal;
import java.util.List;

public class WxBaseInfoDto {
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 设备编号
     */
    private String deviceNo;
    /**
     * 状态描述
     */
    private String stateDesc;
    /**
     * 设备状态
     */
    private Integer state;
    /**
     * 用户账户剩余金额
     */
    private BigDecimal remainAmount;
    /**
     * 充值类型
     */
    private Integer payCategory;
    /**
     * 客服电话
     */
    private String phone;
    /**
     * 剩余次数
     */
    private Integer remainCnt;
    /**
     * 设备运行状态
     */
    private Integer runState;
    /**
     * 充值方案查询
     */
    List<PriceDto> list_price;
    /**
     * 端口号
     */
    private String port;
    /**
     * 信号强度
     */
    private Integer signal;

    private Integer customerType;

    private String customerTypeDesc;

    private String endTime;

    private Integer isJump;

    private String isJumpDesc;

    public String getIsJumpDesc() {
        return isJumpDesc;
    }

    public void setIsJumpDesc(String isJumpDesc) {
        this.isJumpDesc = isJumpDesc;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public String getCustomerTypeDesc() {
        return customerTypeDesc;
    }

    public void setCustomerTypeDesc(String customerTypeDesc) {
        this.customerTypeDesc = customerTypeDesc;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getIsJump() {
        return isJump;
    }

    public void setIsJump(Integer isJump) {
        this.isJump = isJump;
    }

    public Integer getRunState() {
        return runState;
    }

    public void setRunState(Integer runState) {
        this.runState = runState;
    }

    public List<PriceDto> getList_price() {
        return list_price;
    }

    public void setList_price(List<PriceDto> list_price) {
        this.list_price = list_price;
    }

    public Integer getRemainCnt() {
        return remainCnt;
    }

    public void setRemainCnt(Integer remainCnt) {
        this.remainCnt = remainCnt;
    }

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public Integer getPayCategory() {
        return payCategory;
    }

    public void setPayCategory(Integer payCategory) {
        this.payCategory = payCategory;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Integer getSignal() {
        return signal;
    }

    public void setSignal(Integer signal) {
        this.signal = signal;
    }
}
