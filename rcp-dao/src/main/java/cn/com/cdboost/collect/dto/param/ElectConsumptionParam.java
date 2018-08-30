package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.QueryListParam;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

/**
 * 客户档案分页查询参数
 */
public class ElectConsumptionParam extends QueryListParam {
    private long id;

    @NotBlank(message = "startDate 不能为空")
    private String startDate;
    @NotBlank(message = "endDate 不能为空")
    private String endDate;
    private String customerName="";
    private String customerAddr="";
    private String customerContact="";
    private String meterUserNo="";
    private String deviceNo="";
    private String propertyName="";
    private BigDecimal sumPower;
    private BigDecimal sumMoney;

    public BigDecimal getSumPower() {
        return sumPower;
    }

    public void setSumPower(BigDecimal sumPower) {
        this.sumPower = sumPower;
    }

    public BigDecimal getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(BigDecimal sumMoney) {
        this.sumMoney = sumMoney;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
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

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    @Override
    protected String defaultSortName() {
        return null;
    }

    @Override
    protected String defaultSortOrder() {
        return null;
    }
}
