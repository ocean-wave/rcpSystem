package cn.com.cdboost.collect.dto;

/**
 * @author wt
 * @desc
 * @create in  2018/8/16
 **/
public class UseRecordListInfo {

    private String date;
    private String deviceState;
    /**
     * 1-按时充电 2-按电量充电 3-充满断电,
     */
    private String payCategory;
    /**
     * '开启方式 1-微信 2-支付宝 3-IC卡',
     */
    private String payMethod;
    private String deviceNo;
    private String port;
    private String deviceElect;
    private String useTime;
    private String startDate;
    private String endDate;
    private String installAddress;
    private String price;
    private String chargingGuid;
    private String chargingPlieGuid;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(String deviceState) {
        if("1".equals(deviceState)){
            deviceState="手动停止";
        }else if("0".equals(deviceState)){
            deviceState="自动停止";
        }else{
            deviceState="异常停止";
        }
        this.deviceState = deviceState;
    }

    public String getPayCategory() {
        return payCategory;
    }

    public void setPayCategory(String payCategory) {
        this.payCategory = payCategory;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        if("1".equals(payMethod)){
            payMethod="微信";
        }else if("2".equals(payMethod)){
            payMethod="支付宝";
        }else if("3".equals(payMethod)){
            payMethod="IC卡";
        }
        this.payMethod = payMethod;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDeviceElect() {
        return deviceElect;
    }

    public void setDeviceElect(String deviceElect) {
        this.deviceElect = deviceElect;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getInstallAddress() {
        return installAddress;
    }

    public void setInstallAddress(String installAddress) {
        this.installAddress = installAddress;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getChargingGuid() {
        return chargingGuid;
    }

    public void setChargingGuid(String chargingGuid) {
        this.chargingGuid = chargingGuid;
    }

    public String getChargingPlieGuid() {
        return chargingPlieGuid;
    }

    public void setChargingPlieGuid(String chargingPlieGuid) {
        this.chargingPlieGuid = chargingPlieGuid;
    }
}
