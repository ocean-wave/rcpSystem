package cn.com.cdboost.collect.cache;

import java.io.Serializable;

/**
 * 客户设备关联表缓存信息
 */
public class DeviceMapCacheVo implements Serializable{
    /**
     * 用户编号
     */
    private String customerNo;

    /**
     * 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    private String cno;

    /**
     * 表计户号
     */
    private String meterUserNo;

    /**
     * 变压器号
     */
    private String transformerNo;

    /**
     * 楼栋编号
     */
    private String buildingNo;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }

    public String getTransformerNo() {
        return transformerNo;
    }

    public void setTransformerNo(String transformerNo) {
        this.transformerNo = transformerNo;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }
}
