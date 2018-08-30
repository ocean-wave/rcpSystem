package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.QueryListParam;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author wt
 * @desc
 * @create in  2018/7/12
 **/
public class QueryDayLostDto extends QueryListParam{

    @NotBlank(message = "meterCno 不能为空")
    private String meterCno;
    @NotBlank(message = "startDate 不能为空")
    private String startDate;
    @NotBlank(message = "endDate 不能为空")
    private String endDate;

    public String getMeterCno() {
        return meterCno;
    }

    public void setMeterCno(String meterCno) {
        this.meterCno = meterCno;
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
