package cn.com.cdboost.collect.dto.param;

import java.util.List;

/**
 * 用户档案查询vo
 */
public class CustomerInfoQueryNewVo extends CustomerInfoQueryVo{

    /**
     * 节点类型 1标识组织，2标识楼栋
     */
    private Integer nodeType;

    /**
     * 节点值
     */
    private List<String> nodeNoList;

    /**
     * 父节点值,只有当nodeType=2时，才会有值
     */
    private List<Long> pNodeNoList;
    private String importGuid;

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public List<String> getNodeNoList() {
        return nodeNoList;
    }

    public void setNodeNoList(List<String> nodeNoList) {
        this.nodeNoList = nodeNoList;
    }

    public List<Long> getpNodeNoList() {
        return pNodeNoList;
    }

    public void setpNodeNoList(List<Long> pNodeNoList) {
        this.pNodeNoList = pNodeNoList;
    }

    public String getImportGuid() {
        return importGuid;
    }

    public void setImportGuid(String importGuid) {
        this.importGuid = importGuid;
    }
}
