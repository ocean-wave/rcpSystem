package cn.com.cdboost.collect.dto.param;

import java.util.List;

/**
 * 充电桩IC卡查询vo
 */
public class ChargerICCardQueryVo  extends PageQueryVo {
    //卡号
    private String cardId;
    //ic卡状态 0初始、1 -启用、2-停用
    private Integer cardState;

    private String startDate;

    private String endDate;

    private String projectGuid;

    private String deviceNo;

    private String commNo;

    private Integer sendFlag;

    private String nodeId;

    private Integer nodeType;

    private List<Long> orgNoList;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Integer getCardState() {
        return cardState;
    }

    public void setCardState(Integer cardState) {
        this.cardState = cardState;
    }

    @Override
    public Integer getPageIndex() {
        return (this.getPageNumber()-1)*this.getPageSize();
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

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public Integer getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(Integer sendFlag) {
        this.sendFlag = sendFlag;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getCommNo() {
        return commNo;
    }

    public void setCommNo(String commNo) {
        this.commNo = commNo;
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
}
