package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.QueryListParam;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author wt
 * @desc
 * @create in  2018/7/17
 **/
public class QueryResidentialListDto extends QueryListParam {
    private Integer id;
    private String queryGuid;
    private String residential;
    @NotBlank(message = "startDate不能为空")
    private String startDate;
    @NotBlank(message = "endDate不能为空")
    private String endDate;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQueryGuid() {
        return queryGuid;
    }

    public void setQueryGuid(String queryGuid) {
        this.queryGuid = queryGuid;
    }

    public String getResidential() {
        return residential;
    }

    public void setResidential(String residential) {
        this.residential = residential;
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
