package cn.com.cdboost.collect.dto.response;

/**
 * 充值缴费，用户查询返回信息
 */
public class UserFuzzyQueryInfo {
    /**
     * 用户编号
     */
    private String customerNo;

    /**
     * 用户名称
     */
    private String customerName;

    /**
     * 联系电话
     */
    private String customerContact;

    /**
     * 用户地址
     */
    private String customerAddr;


    private String propertyName;


    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getCustomerAddr() {
        return customerAddr;
    }

    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
