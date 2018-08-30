package cn.com.cdboost.collect.dto.param;

/**
 * service层用户模糊查询传入参数
 */
public class FuzzyQueryUserVo {
    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 门牌编号
     */
    private String propertyName;

    /**
     * 联系电话
     */
    private String customerContact;

    /**
     * 用户地址
     */
    private String customerAddr;

    /**
     * 是否重点用户 1重点用户，0普通用户
     */
    private Integer isImportant;

    /**
     * 登录用户id
     */
    private Integer userId;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
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

    public Integer getIsImportant() {
        return isImportant;
    }

    public void setIsImportant(Integer isImportant) {
        this.isImportant = isImportant;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
