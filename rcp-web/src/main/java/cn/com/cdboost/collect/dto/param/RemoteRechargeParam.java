package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 远程充值接口，传入参数
 */
public class RemoteRechargeParam {

    /**
     * 1  APP 充值 2 购电卡充值
     */
    @NotNull(message = "payModel不能为null")
    private Integer payModel;

    /**
     * 购电金额
     */
    @DecimalMin(value = "0",message = "payMoney必须大于等于0")
    private BigDecimal payMoney;

    /**
     * 购电次数
     */
    @Min(value = 1,message = "payCount必须大于等于1")
    private Integer payCount;

    /**
     * 用户编号
     */
    @NotBlank(message = "customerNo不能为空")
    private String customerNo;


    public Integer getPayModel() {
        return payModel;
    }

    public void setPayModel(Integer payModel) {
        this.payModel = payModel;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public Integer getPayCount() {
        return payCount;
    }

    public void setPayCount(Integer payCount) {
        this.payCount = payCount;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }
}
