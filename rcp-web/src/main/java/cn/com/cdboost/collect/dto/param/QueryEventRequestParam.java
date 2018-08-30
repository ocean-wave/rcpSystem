package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.QueryListParam;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.test.annotation.Rollback;

/**
 * 综合查询相关页签，一些公共的查询参数
 */
public class QueryEventRequestParam extends QueryListParam{

    /**
     *表计户号
     */
    private String meterUserNo;
    /**
     * 设备编号
     */
    private String deviceNo;
    /**
     * 设备安装地址
     */
    private String installAddr;
    /**
     * "用户联系方式"
     */
    private String customerContact;
    /**
     * 门牌编号
     */
    private String propertyName;
    /**
     * 用户姓名
     */
    private String customerName;
    /**
     * 设备类型
     */
    private  String deviceType;
    /**
     * 用户唯一标识
     */
    private String customerNo;
    /**
     *事件等级
     */
    @NotEmpty(message = "permissionId 不能为空")
    private String permissionId ;
    /**
     * 类别
     */

    private String eventCategory;
    /**
     * 事件状态
     */

    private String eventStatus;
    /**
     * 查询开始日期
     */
    @NotBlank(message = "startDate不能为空")
    private String startDate;

    /**
     * 查询结束日期
     */
    @NotBlank(message = "endDate不能为空")
    private String endDate;
    /**
     * 是否需要customNo
     */
    @NotBlank(message = "cnoFlag不能为空")
    private String cnoFlag;
    @Override
    protected String defaultSortName() {
        return null;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getCnoFlag() {
        return cnoFlag;
    }

    public void setCnoFlag(String cnoFlag) {
        this.cnoFlag = cnoFlag;
    }

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getInstallAddr() {
        return installAddr;
    }

    public void setInstallAddr(String installAddr) {
        this.installAddr = installAddr;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    protected String defaultSortOrder() {
        return null;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
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
}
