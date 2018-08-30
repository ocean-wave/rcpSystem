package cn.com.cdboost.collect.cache;

import java.io.Serializable;

/**
 * 设备关系缓存vo
 */
public class DeviceRelationCacheVo implements Serializable{
    /**
     * 上级表号
     */
    private String pMeterCno;

    /**
     * 表号
     */
    private String meterCno;

    /**
     * 层级
     */
    private String level;

    /**
     * 用户编号
     */
    private String customerNo;

    public String getpMeterCno() {
        return pMeterCno;
    }

    public void setpMeterCno(String pMeterCno) {
        this.pMeterCno = pMeterCno;
    }

    public String getMeterCno() {
        return meterCno;
    }

    public void setMeterCno(String meterCno) {
        this.meterCno = meterCno;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }
}
