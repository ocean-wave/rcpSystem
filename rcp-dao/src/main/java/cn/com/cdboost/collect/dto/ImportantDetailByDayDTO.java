package cn.com.cdboost.collect.dto;

/**
 * 实时数据、历史数据明细页面，用户相关信息
 */
public class ImportantDetailByDayDTO {


    private String collectDate;
    private String activeTotal;
    private String unactiveTotal;
    private String customerNo;
    private String meterUserNo;
    private String customerContact;
    private String customerAddr;
    private String propertyName;
    private String InstallAddr;
    private String cno;

    public String getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(String collectDate) {
        this.collectDate = collectDate;
    }

    public String getActiveTotal() {
        return activeTotal;
    }

    public void setActiveTotal(String activeTotal) {
        this.activeTotal = activeTotal;
    }

    public String getUnactiveTotal() {
        return unactiveTotal;
    }

    public void setUnactiveTotal(String unactiveTotal) {
        this.unactiveTotal = unactiveTotal;
    }

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

    public String getInstallAddr() {
        return InstallAddr;
    }

    public void setInstallAddr(String installAddr) {
        InstallAddr = installAddr;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }
}
