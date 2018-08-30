package cn.com.cdboost.collect.dto.param;

/**
 * 统计查询，抄表数据查询vo
 */
public class CollectDataForPerDayQueryVo {
    /**
     * 客户唯一标识
     */
    private String customerNo;

    /**
     * 表计户号
     */
    private String meterUserNo;

    /**
     * 设备cno
     */
    private String cno;

    /**
     * 查询开始时间
     */
    private String sTime;

    /**
     * 查询结束时间
     */
    private String eTime;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }
}
