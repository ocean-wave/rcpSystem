package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.QueryListParam;
import org.hibernate.validator.constraints.NotBlank;

public class MakeupDataParam extends QueryListParam {
    /**
     * 查询结束时间
     */
    @NotBlank(message = "endDate 不能为空")
    private String endDate;
    /**
     * 查询开始时间
     */
    @NotBlank(message = "startDate 不能为空")
    private String startDate;
    /**
     * 客户编号
     */
    @NotBlank(message = "customerNo 不能为空")
    private String customerNo;

    @Override
    protected String defaultSortName() {
        return null;
    }

    @Override
    protected String defaultSortOrder() {
        return null;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }
}
