package cn.com.cdboost.collect.dto.response;

/**
 * 银联支付返回给前端的信息
 */
public class UnionPayInfo {
    private String MerId;
    private String OrderNo;
    private String OrderAmount;
    private String CurrCode;
    private String OrderType;
    private String CallBackUrl;
    private String FormUrl;
    private String BankCode;
    private String LangType;
    private String BuzType;
    private String Reserved01;
    private String SignMsg;

    public String getMerId() {
        return MerId;
    }

    public void setMerId(String merId) {
        MerId = merId;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        OrderAmount = orderAmount;
    }

    public String getCurrCode() {
        return CurrCode;
    }

    public void setCurrCode(String currCode) {
        CurrCode = currCode;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    public String getCallBackUrl() {
        return CallBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        CallBackUrl = callBackUrl;
    }

    public String getFormUrl() {
        return FormUrl;
    }

    public void setFormUrl(String formUrl) {
        FormUrl = formUrl;
    }

    public String getBankCode() {
        return BankCode;
    }

    public void setBankCode(String bankCode) {
        BankCode = bankCode;
    }

    public String getLangType() {
        return LangType;
    }

    public void setLangType(String langType) {
        LangType = langType;
    }

    public String getBuzType() {
        return BuzType;
    }

    public void setBuzType(String buzType) {
        BuzType = buzType;
    }

    public String getReserved01() {
        return Reserved01;
    }

    public void setReserved01(String reserved01) {
        Reserved01 = reserved01;
    }

    public String getSignMsg() {
        return SignMsg;
    }

    public void setSignMsg(String signMsg) {
        SignMsg = signMsg;
    }
}
