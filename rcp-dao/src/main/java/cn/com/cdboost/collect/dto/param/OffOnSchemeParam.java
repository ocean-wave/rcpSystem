package cn.com.cdboost.collect.dto.param;

import java.util.List;

/**
 * 停用启用充电方案
 */
public class OffOnSchemeParam {

    private List<String> schemeGuids;

    //停用或启用标识（0-停用；1-启用）
    private Integer onOrOff;

    public List<String> getSchemeGuids() {
        return schemeGuids;
    }

    public void setSchemeGuids(List<String> schemeGuids) {
        this.schemeGuids = schemeGuids;
    }

    public Integer getOnOrOff() {
        return onOrOff;
    }

    public void setOnOrOff(Integer onOrOff) {
        this.onOrOff = onOrOff;
    }

}
