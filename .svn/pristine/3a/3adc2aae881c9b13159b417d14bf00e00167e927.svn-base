package cn.com.cdboost.collect.dto;

import cn.com.cdboost.collect.vo.QueryListParam;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

/**
 * @author wt
 * @desc 电量电费返回
 * @create 2017/7/11 0011
 **/
public class ElectDetailParamDto extends QueryListParam {
    private String  meterUserNo="" ;
    private String  customerNo="";
    private String  deviceNo="" ;

    @NotBlank(message = "startDate 不能为空")
    private String startDate;
    @NotBlank(message = "endDate 不能为空")
    private String endDate;

    private BigDecimal electricTotal;
    private BigDecimal feePayMoney;

    public BigDecimal getElectricTotal() {
        return electricTotal;
    }

    public void setElectricTotal(BigDecimal electricTotal) {
        this.electricTotal = electricTotal;
    }

    public BigDecimal getFeePayMoney() {
        return feePayMoney;
    }

    public void setFeePayMoney(BigDecimal feePayMoney) {
        this.feePayMoney = feePayMoney;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
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
