package cn.com.cdboost.collect.dto.param;


import cn.com.cdboost.collect.vo.QueryListParam;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 充值报表汇总
 */
public class ChargeSummaryParam extends PageQueryVo{
    @NotBlank(message = "startDate 不能为空")
    private String startDate;
    @NotBlank(message = "endDate 不能为空")
    private String endDate;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


}
