package cn.com.cdboost.collect.dto.response;

import java.math.BigDecimal;

/**
 * 楼栋用能数据
 */
public class BuildingUseEnergyData {
    /**
     * 楼栋号
     */
    private String buildingNo;

    /**
     * 对应的用能数
     */
    private BigDecimal amount;

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
