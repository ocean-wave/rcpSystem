package cn.com.cdboost.collect.dto.param;

/**
 * @author wt
 * @desc
 * @create in  2018/7/12
 **/
public class QueryLineLostListInfo {
    /**
     * 台区名称
     */
    String platform="";
    /**
     * 电表数量
     */
    String electDeviceAmount="";
    /**
     * 供电量(千瓦时)
     */
    String powerSupply="";
    /**
     * 售电量(千瓦时)
     */
    String electSale="";
    /**
     * 损耗电量(前)
     */
    String electLoss="";
    /**
     * 占比
     */
    String occupation="";
    /**
     * 台区总表
     */
    String meterCno="";

    public String getMeterCno() {
        return meterCno;
    }

    public void setMeterCno(String meterCno) {
        this.meterCno = meterCno;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getElectDeviceAmount() {
        return electDeviceAmount;
    }

    public void setElectDeviceAmount(String electDeviceAmount) {
        this.electDeviceAmount = electDeviceAmount;
    }

    public String getPowerSupply() {
        return powerSupply;
    }

    public void setPowerSupply(String powerSupply) {
        this.powerSupply = powerSupply;
    }

    public String getElectSale() {
        return electSale;
    }

    public void setElectSale(String electSale) {
        this.electSale = electSale;
    }

    public String getElectLoss() {
        return electLoss;
    }

    public void setElectLoss(String electLoss) {
        this.electLoss = electLoss;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
