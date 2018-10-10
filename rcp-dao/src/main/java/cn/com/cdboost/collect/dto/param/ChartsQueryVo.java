package cn.com.cdboost.collect.dto.param;

import java.util.List;

public class ChartsQueryVo {
    private Integer userId;
    private Integer model;
    private String orgNo;
    private String nodeId;
    private Integer nodeType;
    private List<Long> orgNoList;
    public Integer getUserId() {
        return userId;
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

    public List<Long> getOrgNoList() {
        return orgNoList;
    }

    public void setOrgNoList(List<Long> orgNoList) {
        this.orgNoList = orgNoList;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }
}
