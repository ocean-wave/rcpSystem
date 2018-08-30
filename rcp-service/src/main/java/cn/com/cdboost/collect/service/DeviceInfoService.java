package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.cache.DeviceCacheVo;
import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.OnlineStatus;
import cn.com.cdboost.collect.dto.response.OtherMainSubDto;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.DeviceInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 设备档案信息服务接口
 */
public interface DeviceInfoService extends BaseService<DeviceInfo>{
	PageInfo queryJzqStateDetail(String deviceCno);
	// 根据设备编号获取设备
	DeviceInfo queryDeviceInfoByCno(String cno);

	// 根据pDeviceNo 查询设备信息列表
	List<DeviceInfo> queryListByPdeviceNo(String pDeviceNo);

	// 过滤出可删除的设备cnos
	List<String> filterDeletableParentDeviceCno(Set<String> inSet, Set<String> notInSet);

	List<String> filterDeletableRelyDeviceCno(Set<String> inSet, Set<String> notInSet);

	// 水电气表上的父设备是否可删除
	Boolean isPdeviceDeletable(String cno);
	// 水电气表上的依赖设备是否可删除
	Boolean isRelyDeviceDeletable(String relyCno);

	void batchDeleteJzq(List<String> cnoList);

	// 根据cnos列表，批量查询
	List<DeviceInfo> batchQueryByCnos(List<String> cnos);

	int updateDeviceOrgNo(long orgNo,String customerNo);

	/**
	 * @category 	删除表档案
	 * @param cno
	 * @throws Exception
	 */
	int deleteDevice(String cno);

	// 根据cnos列表，批量删除
	void batchDeleteByCnos(List<String> cnos);

	void batchDeleteDeviceInfo(List<String> cnos) throws BusinessException;

	/**
	 * @category 新建集中器设备
	 * @param
	 * @throws Exception
	 */
	void createJzq(JzqParam jzqParam, long createUserId) throws BusinessException;

	// 集中器修改，基础信息
	void editJzqBaseInfo(JzqEditBaseInfoParam param, Long currentUserId);

	// 集中器修改，主站连接参数信息
	void editJzqConnectionParam(JzqEditConnectionParam param);

	// 集中器主站IP和端口，下发操作
//	int syncJzqConnectionParam(JzqEditConnectionParam param);

	// 集中器修改，心跳
	void editJzqHeartBeat(String deviceNo,Integer hbCycle);

//	int syncJzqHeartBeat(String jzqNo, Integer hbCycle);

	/**
	 * @category 根据条件列举设备档案信息
	 * @param deviceInfoQueryVo
	 * @return
	 * @throws Exception
	 */
	List<DeviceInfoDto> listDeviceRecords(DeviceInfoQueryVo deviceInfoQueryVo);
	/**
	 * @category 根据条件列举设备档案信息
	 * @param deviceInfoQueryVo
	 * @return
	 * @throws Exception
	 */
	List<DeviceInfoDto> listDeviceRecordsNew(DeviceInfoQueryVo deviceInfoQueryVo);

	// 转换器重启
	void converterReboot(String converterNo);

	// 转换器初始化
	void converterInit(String converterNo);

	// 读转换器时间
	String readConverterTime(String converterNo);

	// 设置转换器时间
	void setConverterTime(String converterNo);

	/**
	 * @category 集中器重启
	 * @param
	 * @return
	 */
	int jzqReboot(String jzqNo);

	/**
	 * @category 集中器初始化
	 * @param
	 * @return
	 */
	int jzqInitialize(String jzqNo);

	/**
	 * 集中器心跳下发
	 * @param guid
	 * @param jzqNo
	 * @param hbCycle
	 * @return
	 */
	int jzqHeartBeatSend(String guid,String jzqNo,Integer hbCycle);

	/**
	 * 集中器心跳读取
	 * @param jzqNo
	 * @return
	 */
	int jzqHeartBeatRead(String guid,String jzqNo);

	/**
	 * 集中器连接参数下发
	 * @param sendVo
	 * @return
	 */
	int jzqConnectionParamSend(JzqConnectParamSendVo sendVo);

	// 查询电表明细
	ElectricDetailInfo queryElectricDetail(String deviceNo) throws BusinessException;
	// 查询水表明细
	WaterDetailInfo queryWaterDetail(String deviceNo) throws BusinessException;
	// 查询气表明细
	GasDetailInfo queryGasDetail(String deviceNo) throws BusinessException;
	// 查询采集器明细
	CjqDetailInfo queryCjqDetail(String deviceNo) throws BusinessException;

	ConverterDetailInfo queryConverterDetail(String deviceNo) throws BusinessException;
	// 查询集中器明细
	JzqDetailInfo queryJzqDetail(String deviceNo) throws BusinessException;

	// 查询对应数据权限下，对应设备类型，的设备总数
	Integer queryTotalNum(String deviceType,List<Long> dataOrgList);

	// 根据设备类型（只会有这三个：集中器，采集器，转换器）
	List<OtherMainSubDto> queryOtherMainSubTree(String deviceType,String deviceNo,Integer onlineStatus,List<Long> orgNoList);

	// 根据cnoList查询设备的在线状态
	Map<String,OnlineStatus> queryMainSubOnlineStatus(List<String> cnoList);

	// 查询所有设备缓存信息
	List<DeviceCacheVo> queryAllDeviceCache();

	// 更新设备拉闸策略参数
	int updateOffSchemeInfo(List<String> cnoList, Integer offScheme, Integer offParam);
}
