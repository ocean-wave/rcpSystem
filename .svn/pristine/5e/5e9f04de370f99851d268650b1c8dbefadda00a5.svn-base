package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.MeterCollectDataFailInfo;
import cn.com.cdboost.collect.exception.BusinessException;

import java.util.List;

/**
 * 实时抄表服务接口
 */
public interface RealTimeDataService {
	/**
	 * 请求实时数据
	 * @param queryVo
	 * @return
	 */
	List<MeterCollectDataListInfo> listRealTimeData(RealMeterCollectQueryVo queryVo);
	/**
	 * 请求实时数据
	 * @param queryVo
	 * @return
	 */
	List<MeterCollectDataListInfo> listRealTimeDataNew(RealMeterCollectQueryNewVo queryVo);
	// 查询重点用户实时数据
	List<CollectImportantDataListInfo> listImpRealTimeData(RealMeterCollectQueryVo queryVo);
	// 查询重点用户实时数据
	List<CollectImportantDataListInfo> listImpRealTimeDataNew(RealMeterCollectQueryNewVo queryVo);
	/**
	 * 请求实时抄收失败的数据
	 * @param queryVo
	 * @return
	 */
	List<MeterCollectDataFailInfo> listRealTimeFailData(RealMeterCollectFailQueryVo queryVo);
	// 重构版本
	int sendRealCollectList(SendRealCollectQueryParam queryParam);
	/**
	 * 给欠费用户发短信
	 * @param smsParam
	 * @return
	 * @throws BusinessException
	 */
	void sendmsg(SendSmsParamVo smsParam);

	/**
	 * 保存批量采集任务
	 * @param param
	 * @return
	 * @throws Exception
	 */
/*	int SendRealCollectList(SendRealCollectQueryParam param);
	// 重构版本
	int SendRealCollectList2(SendRealCollectQueryParam queryParam);*/

	/**
	 * @Description 获取批量抄表状态
	 * @param guid 查询的GUID
	 * @return
	 * @throws Exception
	 */
	RealTimeDataStatuListInfo realTimeDataListStatus(Long userId, String guid, String deviceType, String date);

	/**
	 * @Description 停止批量抄表
	 * @param guid 查询的GUID
	 * @return
	 * @throws Exception
	 */
	boolean StopCollectList(String guid);

	/**
	 * 广播抄表
	 * @param guid
	 * @return
	 */
	int broadcast(String guid, String sessionId);

	/**
	 * 前端主动停止广播
	 * @param guid
	 * @return
	 */
	boolean broadcastStop(String guid);

	/**
	 * 汇总结算周期内日冻结的采集情况
	 * @param deviceType
	 * @param total
	 * @param userId
	 * @return
	 */
	List<CollectRecordDTO> ReadCollectRecord(String deviceType, StringBuilder total, Integer userId);

	/**
	 * 查询每天的日冻结采集详情
	 * @param queryVo
	 * @return
	 */
	List<CollectDetialDTO> readCollectRecordDetail(ReadCollectRecordDetailQueryVo queryVo);

	/**
	 * 自动汇总日冻结的抄收情况
	 */
	String SumFreeForDay();
}
