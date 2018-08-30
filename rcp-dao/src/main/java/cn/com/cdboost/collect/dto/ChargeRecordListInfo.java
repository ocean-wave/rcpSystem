package cn.com.cdboost.collect.dto;

/**
 * @author wt
 * @desc
 * @create in  2018/8/16
 **/
public class ChargeRecordListInfo {

    private String date;
    /**
     * '充值方式 1-微信 2-支付宝 3-现金 4-余额'
     */
    private String payMethod;
    private String payMoney;
    private String remainAmount;
    private String deviceNo;
    private String userId;
    /**
     *  '支付状态 0-待支付 1-支付成功',
     */
    private String payState;

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        if("0".equals(payState)){
            payState="待支付";
        }else if("1".equals(payState)){
            payState="支付成功";
        }
        this.payState = payState;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
            payMethod="现金";
        }else if("4".equals(payMethod)){
            payMethod="余额";
        }
        this.payMethod = payMethod;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(String remainAmount) {
        this.remainAmount = remainAmount;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
