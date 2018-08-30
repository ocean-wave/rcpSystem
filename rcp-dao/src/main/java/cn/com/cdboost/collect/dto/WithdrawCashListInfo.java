package cn.com.cdboost.collect.dto;

/**
 * @author wt
 * @desc
 * @create in  2018/8/16
 **/
public class WithdrawCashListInfo {
    private String date;
    /**
     * 提现类型 1微信提现，2支付宝提现
     */
    private String withdrawMethod;
    private String withdrawMoney="";
    private String remainAmount="";
    private String deviceNo;
    private String userId;
    /**
     * '0，提现处理中，1 提现成功， 2提现失败',
     */
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if("1".equals(status)){
            status="提现成功";
        }else if("0".equals(status)){
            status="提现处理中";
        }else if("2".equals(status)){
            status="提现失败";
        }
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWithdrawMethod() {
        return withdrawMethod;
    }

    public void setWithdrawMethod(String withdrawMethod) {
        if("1".equals(withdrawMethod)){
            withdrawMethod="微信";
        }else if("2".equals(withdrawMethod)){
            withdrawMethod="支付宝";
        }
        this.withdrawMethod = withdrawMethod;
    }

    public String getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(String withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
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
