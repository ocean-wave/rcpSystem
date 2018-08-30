package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.QueryListParam;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 客户档案分页查询参数
 */
public class CustomerInfoQueryParam extends QueryListParam{
    /**
     * 用户姓名
     */
    private String customerName="";

    /**
     * 门牌编号
     */
    private String propertyName="";

    /**
     * 联系电话
     */
    private String customerContact="";

    /**
     * 用户地址
     */
    private String customerAddr="";

    /**
     * 表计户号
     */
    private String meterUserNo="";

    /**
     * 用户表号
     */
    private String deviceNo="";

    /**
     * 节点类型 1标识组织，2标识楼栋
     */
    private Integer nodeType;

    /**
     * 节点值
     */
    private String nodeNo;

    /**
     * 父节点值,只有当nodeType=2时，才会有值
     */
    private String pNodeNo;

    @Override
    protected String defaultSortName() {
        return null;
    }

    @Override
    protected String defaultSortOrder() {
        return null;
    }

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

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeNo() {
        return nodeNo;
    }

    public void setNodeNo(String nodeNo) {
        this.nodeNo = nodeNo;
    }

    public String getpNodeNo() {
        return pNodeNo;
    }

    public void setpNodeNo(String pNodeNo) {
        this.pNodeNo = pNodeNo;
    }
}
