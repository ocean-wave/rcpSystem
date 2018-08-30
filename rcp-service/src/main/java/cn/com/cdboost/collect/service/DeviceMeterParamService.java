package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.ImportantCurveDerailDTO;
import cn.com.cdboost.collect.dto.ImportantCurveInfo;
import cn.com.cdboost.collect.dto.ImportantCustomerInfo;
import cn.com.cdboost.collect.dto.param.ImportantABCVo;
import cn.com.cdboost.collect.dto.param.ImportantCurveVo;
import cn.com.cdboost.collect.dto.param.ImportantCustomerVo;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.DeviceMeterParam;

import java.util.List;

/**
 * 设备参数表服务接口
 */
public interface DeviceMeterParamService extends BaseService<DeviceMeterParam>{

	// 更新表参数测量点为0
	int updateCommPointCode2Zero(String cno);

	int batchUpdateSendFlag2Zero(List<String> cno);

	// 批量更新表参数测量点为0
	void batchUpdateCommPointCode2Zero(List<String> cnos);

	// 根据cnos列表，批量删除
	void batchDeleteByCnos(List<String> cnos);
	/**
	 * @Description 选择档案同步
	 * @param listDevParam
	 * @return
	 * @throws Exception
	 */
	int syncDeviceMeterParam(List<DeviceMeterParam> listDevParam, String sendGuid);

	/**
	 * @Description 读取档案同步状态
	 * @param sendGuid
	 * @return
	 * @throws Exception
	 */
	int readSyncState(String sendGuid);
	
	
	/**
	 * @Description 同步电表档案资料到集中器
	 * @param cno 表cno
	 * @return 
	 * @throws Exception
	 */
	int syncMeterToJzq(String cno);

	/**
	 * @Description 取消下发用户档案
	 * @param guid
	 * @return
	 */
	boolean cancelSyncMeter(String guid);

	// 根据cno，查询可用的设备参数表信息
	DeviceMeterParam queryEffectiveParamByCno(String cno) throws BusinessException;
	DeviceMeterParam queryDictIteamByCno(String cno) throws BusinessException;
	// 根据cnos列表，查询可用的设备参数表信息
	List<DeviceMeterParam> findDeviceMeterParamByCnos(List<String> cnos);

	// 创建设备参数表信息
	void createDeviceMeterParam(DeviceMeterParam deviceMeterParam);

	//根据cno更新重点用户
	int updateSelectiveByCno(DeviceMeterParam deviceMeterParam);
	//查询重点用户
	List<ImportantCustomerInfo> queryImportantCustomer(ImportantCustomerVo ImportantVo);

	//查询重点用户曲线
	List<ImportantCurveInfo> queryImportantCurve(ImportantCurveVo ImportantVo);
	//查询重点用户采集数据
	List<ImportantCurveDerailDTO> queryImportantCollection(ImportantCurveVo ImportantVo);
	//查询重点用户ABC
	List<ImportantCurveInfo> queryImportantABC(ImportantABCVo ImportantABCVo);

}
