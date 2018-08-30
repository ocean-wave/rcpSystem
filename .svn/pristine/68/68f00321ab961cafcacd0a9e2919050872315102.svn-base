package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.dto.MonthlySchemeDto;
import cn.com.cdboost.collect.dto.TemporarySchemeDto;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 方案编辑
 */
public class ChargerSchemeEditParam {
    //项目guid
    @Valid
    private String projectGuid;
    //包月方案
    @Valid
    private MonthlySchemeDto monthly;
    //临充方案
    @Valid
    private List<TemporarySchemeDto> temporary;

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public MonthlySchemeDto getMonthly() {
        return monthly;
    }

    public void setMonthly(MonthlySchemeDto monthly) {
        this.monthly = monthly;
    }

    public List<TemporarySchemeDto> getTemporary() {
        return temporary;
    }

    public void setTemporary(List<TemporarySchemeDto> temporary) {
        this.temporary = temporary;
    }
}
