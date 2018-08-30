package cn.com.cdboost.collect.dto.response;

import cn.com.cdboost.collect.dto.MonthlySchemeDto;
import cn.com.cdboost.collect.dto.TemporarySchemeDto;

import java.util.List;

/**
 * 方案详情
 */
public class ChargingSchemeInfo {
    private MonthlySchemeDto monthly;
    private List<TemporarySchemeDto> temporary;

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
