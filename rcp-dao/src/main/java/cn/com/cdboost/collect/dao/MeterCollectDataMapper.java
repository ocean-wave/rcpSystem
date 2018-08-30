package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.*;
import cn.com.cdboost.collect.model.MeterCollectData;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MeterCollectDataMapper extends CommonMapper<MeterCollectData> {
    // 查询采集的实时数据
    List<MeterCollectDataInfo> listRealMeterCollectData(RealMeterCollectQueryVo queryVo);
    // 查询采集的实时数据
    List<MeterCollectDataInfo> listRealMeterCollectDataNew(RealMeterCollectQueryNewVo queryVo);
    // 查询重点用户实时数据
    List<MeterCollectImpDataInfo> listRealMeterCollectImpData(RealMeterCollectQueryVo queryVo);
    // 查询重点用户实时数据
    List<MeterCollectImpDataInfo> listRealMeterCollectImpDataNew(RealMeterCollectQueryNewVo queryVo);
    // 查询实时采集失败的数据
    List<MeterCollectDataFailInfo> listRealMeterCollectFailData(RealMeterCollectFailQueryVo queryVo);

    /**
     * 汇总结算周期内日冻结的采集情况
     * @param userId
     * @param deviceType
     * @return
     */
    List<CollectRecordDTO> listReadCollectRecord(@Param("userId") Integer userId,@Param("deviceType") String deviceType);
    // 查询每天的日冻结采集详情
    List<CollectDetialDTO> listReadCollectRecordDetail(ReadCollectRecordDetailQueryVo queryVo);
    /**
     * 自动汇总日冻结的抄收情况
     */
    String sumFreeForDay();

    /**
     * 查询采集的历史数据
     * @param queryVo
     * @return
     */
    List<CollectDataGetInfo> listMeterCollectData(CollectDataGetQueryVo queryVo);

    // 查询重点用户历史采集数据
    List<ImpCollectDataGetInfo> listImpCollectHistoryData(ImpCollectDataGetQueryVo queryVo);

    /**
     * 下载历史数据
     * @param queryVo
     * @return
     */
    List<CollectDataDownInfo> collectDataDown(CollectDataGetQueryVo queryVo);

    /**
     * 查询欠费用户
     * @param queryVo
     * @return
     */
    List<ArrearageCustomer> listArrearageCustomers(ArrearageCustomersQueryVo queryVo);

    /**
     * 查询客户每月每天信息
     * @param customerNo
     * @param deviceType
     * @param yearMonth
     * @return
     */
    List<CustomerData4Month> getDataForMonth(@Param("customerNo") String customerNo,
                                             @Param("deviceType") String deviceType,
                                             @Param("yearMonth") Integer yearMonth,
                                             @Param("cno") String cno);
    /**
     * 获取告警用户数量
     *
     * @return
     */
    void getAlarmUserCount(Map<String, Object> cntMap);

    /**
     * 查询用户用能排行柱状图
     * @param queryVo
     * @return
     */
    List<CustomElectDTO> getCustomElechart(ElecDataVo queryVo);
    /**
     * 查询用户区域用电排行
     */
    List<CustomElectDTO> getCustomElecData(ElecDataVo queryVo);




    /**
     * 综合查询，抄表数据查询
     * @param queryVo
     * @return
     */
    List<List<?>> getCollectDataForPerDay(CollectDataForPerDayQueryVo queryVo);

    List<CustomerData4Month> getDataList(RealTimeDataListParam realTimeDataListParam);
}