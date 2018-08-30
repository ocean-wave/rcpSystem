package cn.com.cdboost.collect.dao;


import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReplenishMapper {

    /**
     * 查询需要补抄的用户信息
     * @param queryVo
     * @return
     */
    List<BaseHandheldReplenishDTO> meterSuppCstSearch(MeterSuppCstQueryVo queryVo);


    /**
     * 创建补抄工单
     * @param paramVo
     */
    void meterSuppTaskAdd(ReplenishWorkOrderParamVo paramVo);

    /**
     * 查询补抄工单数据
     * @param queryVo
     * @return
     */
    List<WorkOrderDetialDTO> meterSuppTaskGet(MeterSuppTaskGetQueryVo queryVo);


    /**
     * 查询补抄工单的详细信息
     * @param queryVo
     * @return
     */
    List<ReplenishDataDTO> meterSuppTaskDetail(MeterSuppTaskDetailQueryVo queryVo);

    /**
     * APP 查询补抄工单列表
     * @param searchMap
     * @return
     */
    List<WorkOrder> queryWorkOrder(Map<String, Object> searchMap);

    /**
     * APP 查询补抄工单列表,会返回多个列表集
     * @param searchMap
     * @return
     */
    List<List<?>> queryWorkOrderDetail(Map<String, Object> searchMap);

    /**
     * APP 上传补抄采集结果信息
     * @param dataMap
     */
    void uploadCollectResult(Map<String, Object> dataMap);

    /**
     * 查询待补录数据
     * @param queryVo
     * @return
     */
    List<MakeupDataDto> queryMakeupData(MakeupDataVo queryVo);

    /**
     * 手动补录采集数据
     * @param param
     * @return
     */
    void manualRecordData(CreateManualRecordParamVo param);
}