package cn.com.cdboost.collect.dto;

import cn.com.cdboost.collect.util.TreeEntity2;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 总表，子表数返回
 */
public class MainSubTreeInfo implements TreeEntity2<MainSubTreeInfo> {

    /**
     * 是否展开,默认都返回false
     */
    private boolean open = false;

    /**
     * 是否有子节点,默认true
     */
    private boolean hasChild = true;

    /**
     * 设备安装地址,没有就传空
     */
    private String nodeName;

    /**
     * 节点类型 1表示组织节点，2表示具体水电气表节点
     */
    private Integer nodeType;

    /**
     * 父节点值
     * nodeType=1时，表示上级组织orgNo
     * nodeType=2时，表示上级
     */
    private String pNodeId;

    /**
     * 节点值
     * nodeType=1时，表示组织orgNo
     * nodeType=2时，表示水电气表设备编号
     */
    private String nodeId;

    /**
     * 是否选中,默认都为false
     */
    private boolean selected = false;

    /**
     * nodeType=2时，标识在线状态
     */
    private Integer onlineStatus;

    /**
     * 是否重点表
     */
    private Integer isImportant;

    /**
     * 孩子节点列表
     */
    private List<MainSubTreeInfo> children;

    @Override
    @JSONField(serialize=false)
    public String getId() {
        return nodeId;
    }

    @Override
    @JSONField(serialize=false)
    public String getParentId() {
        return pNodeId;
    }

    @Override
    public void setChildList(List<MainSubTreeInfo> childList) {
        this.children = childList;
    }

    @Override
    public void setIsOpen(boolean isOpen) {
        this.open = isOpen;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public String getpNodeId() {
        return pNodeId;
    }

    public void setpNodeId(String pNodeId) {
        this.pNodeId = pNodeId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Integer getIsImportant() {
        return isImportant;
    }

    public void setIsImportant(Integer isImportant) {
        this.isImportant = isImportant;
    }

    public List<MainSubTreeInfo> getChildren() {
        return children;
    }

    public void setChildren(List<MainSubTreeInfo> children) {
        this.children = children;
    }
}
