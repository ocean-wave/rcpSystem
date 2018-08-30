package cn.com.cdboost.collect.dto;

import cn.com.cdboost.collect.vo.QueryListParamDate;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author wt
 * @desc
 * @create in  2018/8/16
 **/
public class WithdrawCashListDto extends QueryListParamDate{
    private Integer userId;
    @NotBlank(message = "customerGuid 不能为空")
    private String customerGuid;
    @NotBlank(message = "withdrawMethod 不能为空")
    private String withdrawMethod;
    @NotBlank(message = "status 不能为空")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCustomerGuid() {
        return customerGuid;
    }

    public void setCustomerGuid(String customerGuid) {
        this.customerGuid = customerGuid;
    }

    public String getWithdrawMethod() {
        return withdrawMethod;
    }

    public void setWithdrawMethod(String withdrawMethod) {
        this.withdrawMethod = withdrawMethod;
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
