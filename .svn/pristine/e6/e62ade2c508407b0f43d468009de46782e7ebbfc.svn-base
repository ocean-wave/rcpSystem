package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.QueryListParam;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 客户档案分页查询参数
 */
public class DaySettlementParam extends QueryListParam {
    @NotBlank(message = "startDate 不能为空")
    private String startDate;
    @NotBlank(message = "endDate 不能为空")
    private String endDate;

    private String lastBanlance;
    private Integer flag;
    private String balanceDay;

    public String getBalanceDay() {
        return balanceDay;
    }

    public void setBalanceDay(String balanceDay) {
        this.balanceDay = balanceDay;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getLastBanlance() {
        return lastBanlance;
    }

    public void setLastBanlance(String lastBanlance) {
        this.lastBanlance = lastBanlance;
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

    @Override
    protected String defaultSortName() {
        return null;
    }

    @Override
    protected String defaultSortOrder() {
        return null;
    }
}
