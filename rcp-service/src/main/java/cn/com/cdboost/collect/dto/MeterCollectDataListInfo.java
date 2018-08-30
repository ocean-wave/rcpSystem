package cn.com.cdboost.collect.dto;

import cn.com.cdboost.collect.dto.response.MeterCollectDataInfo;

/**
 * 实时采集数据列表返回信息
 */
public class MeterCollectDataListInfo extends MeterCollectDataInfo{

    /**
     * 集中器编号
     */
    private String jzqNo;

    public String getJzqNo() {
        return jzqNo;
    }

    public void setJzqNo(String jzqNo) {
        this.jzqNo = jzqNo;
    }
}
