package cn.com.cdboost.collect.dto.param;

import java.util.List;

public class QueryDetialVo {
    /**
     * 流水号
     */
    private String refundGuid;
    /**
     * 组织
     */
    private List<Long> orgNos;

    public String getRefundGuid() {
        return refundGuid;
    }

    public void setRefundGuid(String refundGuid) {
        this.refundGuid = refundGuid;
    }

    public List<Long> getOrgNos() {
        return orgNos;
    }

    public void setOrgNos(List<Long> orgNos) {
        this.orgNos = orgNos;
    }
}
