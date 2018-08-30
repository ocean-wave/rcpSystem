package cn.com.cdboost.collect.dto;

import cn.com.cdboost.collect.util.TreeEntity4;
import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 客户档案新增时，选择组织树返回结构
 */
public class OrgTreeNode implements TreeEntity4<OrgTreeNode> {
    /**
     * 组织编号
     */
    private Long orgNo;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 上级机构编号
     */
    private Long pOrgNo;

    /**
     * 叶子节点，1是，0不是
     */
    private Integer leaf;

    /**
     * 孩子节点
     */
    private List<OrgTreeNode> children;

    @Override
    @JSONField(serialize=false)
    public Long getId() {
        return orgNo;
    }

    @Override
    @JSONField(serialize=false)
    public Long getParentId() {
        return pOrgNo;
    }

    @Override
    public void setChildList(List<OrgTreeNode> childList) {
        this.children = childList;
        if (CollectionUtils.isEmpty(childList)) {
            this.leaf = 1;
        } else {
            this.leaf = 0;
        }
    }

    public Long getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(Long orgNo) {
        this.orgNo = orgNo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getpOrgNo() {
        return pOrgNo;
    }

    public void setpOrgNo(Long pOrgNo) {
        this.pOrgNo = pOrgNo;
    }

    public Integer getLeaf() {
        return leaf;
    }

    public void setLeaf(Integer leaf) {
        this.leaf = leaf;
    }

    public List<OrgTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<OrgTreeNode> children) {
        this.children = children;
    }
}
