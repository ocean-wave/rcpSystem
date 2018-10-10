package cn.com.cdboost.collect.dto;

import cn.com.cdboost.collect.vo.QueryListParam;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * @author wt
 * @desc
 * @create in  2018/8/15
 **/
public class CustomerInfoListDto extends QueryListParam{
    @NotBlank(message = "customerState不能为null")
    private String customerState;

    private String webcharNo;

    private String alipayUserId;

    private String customerName;

    private String alipayNickName;

    private String customerContact;
    private String nodeId;
    private Integer nodeType;
    private List<String> proGuids;

    public String getCustomerState() {
        return customerState;
    }

    public void setCustomerState(String customerState) {
        this.customerState = customerState;
    }

    public String getWebcharNo() {
        return webcharNo;
    }

    public void setWebcharNo(String webcharNo) {
        this.webcharNo = webcharNo;
    }

    public String getAlipayUserId() {
        return alipayUserId;
    }

    public void setAlipayUserId(String alipayUserId) {
        this.alipayUserId = alipayUserId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAlipayNickName() {
        return alipayNickName;
    }

    public void setAlipayNickName(String alipayNickName) {
        this.alipayNickName = alipayNickName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    @Override
    protected String defaultSortName() {
        return null;
    }

    @Override
    protected String defaultSortOrder() {
        return null;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public List<String> getProGuids() {
        return proGuids;
    }

    public void setProGuids(List<String> proGuids) {
        this.proGuids = proGuids;
    }
}
