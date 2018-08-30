package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.dto.ArrearageCustomer;
import cn.com.cdboost.collect.dto.CollectAnalyzeDataForMonthInfo;
import cn.com.cdboost.collect.dto.CustomerData4Month;
import cn.com.cdboost.collect.dto.ImportantCurveDerailDTO;
import cn.com.cdboost.collect.dto.param.ArrearageCustomersQueryVo;
import cn.com.cdboost.collect.dto.param.CollectDataGetQueryVo;
import cn.com.cdboost.collect.dto.param.ImpCollectDataGetQueryVo;
import cn.com.cdboost.collect.dto.param.RealTimeDataListParam;
import cn.com.cdboost.collect.dto.response.CollectDataDownInfo;
import cn.com.cdboost.collect.dto.response.CollectDataGetInfo;
import cn.com.cdboost.collect.dto.response.ImpCollectDataGetInfo;

import java.util.List;

/**
 * 历史数据服务接口
 */
public interface HistoricalDataService {
	List<CustomerData4Month> getDataList(RealTimeDataListParam realTimeDataListParam);
	/**
	 * @param
	 * @sbTotal 查询的总数据
	 * @return
	 * @throws Exception
	 * @Description 查询采集的历史数据
	 */
	List<CollectDataGetInfo> listHistoricalData(CollectDataGetQueryVo queryVo);

	// 查询重点用户历史数据列表（只查询实时数据）
	List<ImpCollectDataGetInfo> listImpHistoricalData(ImpCollectDataGetQueryVo queryVo);

	List<CollectDataDownInfo> listHistoricalData4Download(CollectDataGetQueryVo queryVo);

	/**
	 * @return
	 * @throws Exception
	 * @Description 每个月电表、气表、水表汇总数据
	 */
	CollectAnalyzeDataForMonthInfo listCollectAnalyzeDataForMonth(long userId, String customerNo,String deviceCno);

	/**
	 * @param
	 * @return
	 * @throws Exception
	 * @Description 查询欠费用户
	 */
	List<ArrearageCustomer> listArrearageCustomers(ArrearageCustomersQueryVo queryVo);
	
	/**
	 * @param
	 * @return
	 * @throws Exception
	 * @Description 查询欠费用户
	 */
	List<CustomerData4Month> getDataForMonth(String customerNo, String deviceCno, Integer month);

	int getAlarmUserCount(Long userId);

	List<ImportantCurveDerailDTO> getImpDataList(RealTimeDataListParam realTimeDataListParam);

}
