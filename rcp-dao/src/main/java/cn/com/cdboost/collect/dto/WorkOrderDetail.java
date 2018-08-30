package cn.com.cdboost.collect.dto;

import java.util.List;

/**
 * APP工单详情请求，返回的数据
 */
public class WorkOrderDetail extends CustomerInfo {
    /**
     * 采集数据项列表
     */
    private List<CollectDataItem> collectDataItems;

    public List<CollectDataItem> getCollectDataItems() {
        return collectDataItems;
    }

    public void setCollectDataItems(List<CollectDataItem> collectDataItems) {
        this.collectDataItems = collectDataItems;
    }
}
