package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.dto.ChargingSchemeDto;

import javax.validation.Valid;
import java.util.List;

/**
 * 方案编辑
 */
public class ChargerSchemeEditParam {
    //充电方案
    @Valid
    private List<ChargingSchemeDto> schemeList;

    public List<ChargingSchemeDto> getSchemeList() {
        return schemeList;
    }

    public void setSchemeList(List<ChargingSchemeDto> schemeList) {
        this.schemeList = schemeList;
    }
}
