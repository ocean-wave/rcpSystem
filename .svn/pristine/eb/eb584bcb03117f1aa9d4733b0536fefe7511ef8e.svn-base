package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.*;
import cn.com.cdboost.collect.model.MeterCollectData;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 抄表数据服务接口
 */
public interface MeterCollectDataService extends BaseService<MeterCollectData>{
    List<CustomerData4Month> getDataList(RealTimeDataListParam realTimeDataListParam);
    // 查询采集的实时数据
    List<MeterCollectDataInfo> listRealMeterCollectData(RealMeterCollectQueryVo queryVo);
    // 查询采集的实时数据
    List<MeterCollectDataInfo> listRealMeterCollectDataNew(RealMeterCollectQueryNewVo queryVo);

    // 查询采集的重点用户实时数据
    List<MeterCollectImpDataInfo> listImpRealTimeData(RealMeterCollectQueryVo queryVo);
    // 查询采集的重点用户实时数据
    List<MeterCollectImpDataInfo> listImpRealTimeDataNew(RealMeterCollectQueryNewVo queryVo);

    // 查询实时采集失败的数据
    List<MeterCollectDataFailInfo> listRealMeterCollectFailData(RealMeterCollectFailQueryVo queryVo);

    // 汇总结算周期内日冻结的采集情况
    List<CollectRecordDTO> listReadCollectRecord(@Param("userId") Integer userId, @Param("deviceType") String deviceType);

    // 查询每天的日冻结采集详情
    List<CollectDetialDTO> listReadCollectRecordDetail(ReadCollectRecordDetailQueryVo queryVo);

    // 自动汇总日冻结的抄收情况
    String sumFreeForDay();

    // 查询采集的历史数据
    List<CollectDataGetInfo> listMeterCollectData(CollectDataGetQueryVo queryVo);

    // 查询采集的重点用户历史数据
    List<ImpCollectDataGetInfo> listImpCollectHistoryData(ImpCollectDataGetQueryVo queryVo);

    // 下载历史数据
    List<CollectDataDownInfo> collectDataDown(CollectDataGetQueryVo queryVo);

    // 查询欠费用户
    List<ArrearageCustomer> listArrearageCustomers(ArrearageCustomersQueryVo queryVo);

    // 查询客户每月每天信息
    List<CustomerData4Month> getDataForMonth(String customerNo, String deviceCno, Integer yearMonth);

    // 获取告警用户数量
    void getAlarmUserCount(Map<String, Object> cntMap);

    // 按楼栋统计用电量
    HistogramBuildingData statisticsByBuildingNo(HistogramDataQueryVo queryVo);

    //用电条形图
    Map getCustomElechart(ElecDataVo elecDataVo);
    //用电列表
    List getCustomEledata(ElecDataVo elecDataVo);


    CollectDataPerDay getCollectDataForDay(CollectDataForPerDayQueryVo queryVo);
}
