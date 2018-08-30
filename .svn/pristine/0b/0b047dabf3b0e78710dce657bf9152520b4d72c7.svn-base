package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.exception.BusinessException;

import java.util.List;

/**
 * PC端补抄管理服务接口
 */
public interface ReplenishService {

    // 查询补抄档案
    List<MeterSuppCstValueText> queryReplenishMeter(MeterSuppCstQueryVo queryVo);

    // 创建补抄工单
    void createReplenishWorkOrder(ReplenishWorkOrderStrJsonVo strJsonVo, Integer userId) throws BusinessException;

    // 补抄工单列表
    List<WorkOrderDetialDTO> replenishWorkOrderList(MeterSuppTaskGetQueryVo queryVo);

    // 补抄数据详情
    List<ReplenishDataDTO> replenishDataDetial(MeterSuppTaskDetailQueryVo queryVo);

    // 补抄工单列表
    List<MeterSuppQueryInfo> requestWorkOrderList(String taskNo);

    /**
     * APP端查询补抄工单列表
     * @param userId 用户id
     * @param pageIndex 页号
     * @param pageSize 每页条数
     * @param total 存放本次调用数据库返回的总条数
     * @return
     */
    List<WorkOrder> queryWorkOrder(long userId, int pageIndex, int pageSize, StringBuilder total);

    /**
     * APP端查询补抄工单详情信息
     * @param pageIndex 页号
     * @param pageSize 每页条数
     * @param taskNo 任务编号
     * @param total 存放本次调用数据库返回的总条数
     * @return
     */
    List<WorkOrderDetail> queryWorkOrderDetail(int pageIndex, int pageSize, String taskNo, StringBuilder total);

    /**
     * APP端上传补抄采集结果数据
     * @param collectResult
     * @param userId
     * @return
     */
    int uploadCollectResult(UploadCollectResult collectResult, String userId);

    List<WorkOrderDetail> appCreateNewWorkOrderDetail(List<String> cnoList);

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
    int manualRecordData(CreateManualRecordParam param,Integer userId);
}
