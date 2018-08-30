package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.cache.DeviceCacheVo;
import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.constant.CustomerInfoConstant;
import cn.com.cdboost.collect.constant.DictCodeConstant;
import cn.com.cdboost.collect.dao.DeviceHeartbeatMapper;
import cn.com.cdboost.collect.dao.DeviceInfoMapper;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.OnlineStatus;
import cn.com.cdboost.collect.dto.response.OtherMainSubDto;
import cn.com.cdboost.collect.enums.DeviceType;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.*;
import cn.com.cdboost.collect.model.CustomerInfo;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.util.DateUtil;
import cn.com.cdboost.collect.util.UuidUtil;
import com.alibaba.fastjson.JSON;
import com.example.clienttest.client.AFN05Object;
import com.example.clienttest.client.AFN07Object;
import com.example.clienttest.clientfuture.ClientManager;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 设备档案信息服务接口实现类
 */
@Service("deviceInfoService")
public class DeviceInfoServiceImpl extends BaseServiceImpl<DeviceInfo> implements DeviceInfoService {
	private static final Logger logger = LoggerFactory.getLogger(DeviceInfoServiceImpl.class);

	@Autowired
	private DeviceInfoMapper deviceInfoMapper;
	@Autowired
	private DeviceConnParamService deviceConnParamService;
	@Autowired
	private DeviceMeterParamService deviceMeterParamService;
	@Autowired
	private DeviceMeterConfigService deviceMeterConfigService;
	@Autowired
	private CustomerDevMapService customerDevMapService;
	@Autowired
	private CustomerInfoService customerInfoService;
	@Autowired
	private OrgService orgService;
	@Autowired
	private InstructService instructService;
	@Autowired
	private DeviceInfoDeviceStateService deviceInfoDeviceStateService;
	@Autowired
	private DictItemService dictItemService;
	@Autowired
	private OrgAppService orgAppService;
	@Autowired
	private DeviceHeartbeatMapper deviceHeartbeatMapper;

	@Override
	public PageInfo queryJzqStateDetail(String deviceCno) {
		PageHelper.orderBy("create_time desc");
		DeviceHeartbeat deviceHeartbeat=new DeviceHeartbeat();
		deviceHeartbeat.setCno(deviceCno);
        List<DeviceHeartbeat> deviceHeartbeats = deviceHeartbeatMapper.select(deviceHeartbeat);
        List<DeviceHeartbeatInfo> deviceHeartbeatInfos = Lists.newArrayList();
        for (DeviceHeartbeat heartbeat : deviceHeartbeats) {
            DeviceHeartbeatInfo deviceHeartbeatInfo=new DeviceHeartbeatInfo();
            deviceHeartbeatInfo.setCreateTime(DateUtil.formatDate(heartbeat.getCreateTime()));
            deviceHeartbeatInfo.setState(heartbeat.getState());
            deviceHeartbeatInfos.add(deviceHeartbeatInfo);
        }
        PageInfo pageInfo=new PageInfo(deviceHeartbeatInfos);
		return pageInfo;
	}

	@Override
	public DeviceInfo queryDeviceInfoByCno(String cno) {
		DeviceInfo param = new DeviceInfo();
		param.setCno(cno);
		param.setIsEnabled(1);
		return deviceInfoMapper.selectOne(param);
	}

	@Override
	public List<DeviceInfo> queryListByPdeviceNo(String pDeviceNo) {
		DeviceInfo param = new DeviceInfo();
		param.setpDeviceNo(pDeviceNo);
		return deviceInfoMapper.select(param);
	}

	@Override
	public List<String> filterDeletableParentDeviceCno(Set<String> inSet, Set<String> notInSet) {
		Condition condition = new Condition(DeviceInfo.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andIn("pDeviceNo",inSet);
		criteria.andNotIn("cno",notInSet);
		List<DeviceInfo> deviceInfos = deviceInfoMapper.selectByCondition(condition);

		// 可删除的列表
		List<String> list = Lists.newArrayList(inSet);
		if (!CollectionUtils.isEmpty(deviceInfos)) {
			for (DeviceInfo deviceInfo : deviceInfos) {
				list.remove(deviceInfo.getpDeviceNo());
			}
		}
		return list;
	}

	@Override
	public List<String> filterDeletableRelyDeviceCno(Set<String> inSet, Set<String> notInSet) {
		Condition condition = new Condition(DeviceInfo.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andIn("relyCno",inSet);
		criteria.andNotIn("cno",notInSet);
		List<DeviceInfo> deviceInfos = deviceInfoMapper.selectByCondition(condition);

		// 可删除的列表
		List<String> list = Lists.newArrayList(inSet);
		if (!CollectionUtils.isEmpty(deviceInfos)) {
			for (DeviceInfo deviceInfo : deviceInfos) {
				list.remove(deviceInfo.getpDeviceNo());
			}
		}
		return list;
	}

	@Override
	public Boolean isPdeviceDeletable(String cno) {
		Condition condition = new Condition(DeviceInfo.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andEqualTo("pDeviceNo",cno);
		List<DeviceInfo> deviceInfos = deviceInfoMapper.selectByCondition(condition);
		if (CollectionUtils.isEmpty(deviceInfos)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public Boolean isRelyDeviceDeletable(String relyCno) {
		Condition condition = new Condition(DeviceInfo.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andEqualTo("relyCno",relyCno);
		criteria.andNotEqualTo("cno",relyCno);
		List<DeviceInfo> deviceInfos = deviceInfoMapper.selectByCondition(condition);
		if (CollectionUtils.isEmpty(deviceInfos)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public void batchDeleteJzq(List<String> cnoList) {
		Condition condition = new Condition(DeviceInfo.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andIn("cno",cnoList);
		deviceInfoMapper.deleteByCondition(condition);
	}

	@Override
	public List<DeviceInfo> batchQueryByCnos(List<String> cnos) {
		Condition condition = new Condition(DeviceInfo.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andIn("cno",cnos);
		return deviceInfoMapper.selectByCondition(condition);
	}

	@Override
	@Transactional
	public int updateDeviceOrgNo(long orgNo,String customerNo) {
		return deviceInfoMapper.updateDeviceOrgNo(orgNo, customerNo);
	}

	@Override
	@Transactional
	public int deleteDevice(String cno) {
		DeviceInfo param = new DeviceInfo();
		param.setCno(cno);
		return deviceInfoMapper.delete(param);
	}

	@Override
	@Transactional
	public void batchDeleteByCnos(List<String> cnos) {
		Condition condition = new Condition(DeviceInfo.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andIn("cno",cnos);
		deviceInfoMapper.deleteByCondition(condition);
	}

	@Override
	@Transactional
	public void batchDeleteDeviceInfo(List<String> cnos) throws BusinessException{
		// 水电气一类，放入集合one
		List<String> one = Lists.newArrayList();
		// 采集器,集中器,转换器一类,放入集合two
		List<String> two = Lists.newArrayList();
		for (String cno : cnos) {
			if (cno.startsWith(DeviceType.ELECTRIC_METER.getCode())) {
				one.add(cno);
			} else if (cno.startsWith(DeviceType.WATER_METER.getCode())) {
				one.add(cno);
			} else if (cno.startsWith(DeviceType.GAS_METER.getCode())) {
				one.add(cno);
			} else if (cno.startsWith(DeviceType.JZQ.getCode())) {
				two.add(cno);
			} else if (cno.startsWith(DeviceType.CJQ.getCode())) {
				two.add(cno);
			} else if (cno.startsWith(DeviceType.CONVERTER.getCode())) {
				two.add(cno);
			} else {
				throw new BusinessException("未知设备类型cno=" + cno);
			}
		}

		// 校验是否满足删除条件
		this.checkDelete(one,two);

		// 水电气表删除
		this.deleteOne(one);
		// 集中器，采集器，转换器删除
		this.deleteTwo(two);
	}

	/**
	 * 批量删除水电气表
	 * @param one
	 */
	private void deleteOne(List<String> one) {
		logger.info("批量删除水电气开始：one=" + JSON.toJSONString(one));
		if (CollectionUtils.isEmpty(one)) {
			return;
		}

		// 删除水电气表，设备表信息
		Condition condition = new Condition(DeviceInfo.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andIn("cno",one);
		deviceInfoMapper.deleteByCondition(condition);

		// 更新水电气表，设备参数表
		deviceMeterParamService.batchUpdateCommPointCode2Zero(one);

		// 删除水电气表，设备状态信息表
		deviceInfoDeviceStateService.batchDeleteByCnos(one);
	}

	/**
	 * 批量删除集中器，采集器，转换器
	 * @param two
	 */
	private void deleteTwo(List<String> two) {
		logger.info("批量删除集中器，采集器，转换器开始：two=" + JSON.toJSONString(two));
		if (CollectionUtils.isEmpty(two)) {
			return;
		}

		// 删除表档案
		Condition condition = new Condition(DeviceInfo.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andIn("cno",two);
		deviceInfoMapper.deleteByCondition(condition);

		// 删除连接参数表记录
		deviceConnParamService.batchDeleteByCnos(two);

		// 删除设备状态信息表
		deviceInfoDeviceStateService.batchDeleteByCnos(two);
	}


	/**
	 * 校验水电气表，集中器，采集器，转换器是否满足删除条件
	 * @param one
	 * @param two
	 */
	private void checkDelete(List<String> one, List<String> two) {
		// 校验水电气设备是否能够删除
		if (!CollectionUtils.isEmpty(one)) {
			List<CustomerDevMap> customerDevMaps = customerDevMapService.batchQueryByCnos(one);
			if (!CollectionUtils.isEmpty(customerDevMaps)) {
				StringBuffer sb = new StringBuffer("设备[");
				// 选择第一个不满足条件的cno提示
				CustomerDevMap devMap = customerDevMaps.get(0);
				String deviceNo = CNoUtil.getNo(devMap.getCno());
				sb.append(deviceNo);
				sb.append("]存在关联用户，不允许删除");
				throw new BusinessException(sb.toString());
			}
		}

		// 校验集中器,采集器,转换器是否能够删除
		if (!CollectionUtils.isEmpty(two)) {
			// 查询采集器，集中器，转换器是否还有下级设备
			Condition condition = new Condition(DeviceInfo.class);
			Example.Criteria criteria = condition.createCriteria();
			criteria.andIn("pDeviceNo",two);
			List<DeviceInfo> deviceInfos = deviceInfoMapper.selectByCondition(condition);
			if (!CollectionUtils.isEmpty(deviceInfos)) {
				StringBuffer sb = new StringBuffer("设备[");
				// 选择第一个不满足条件的cno提示
				String deviceNo = CNoUtil.getDeviceNoByCno(two.get(0));
				sb.append(deviceNo);
				sb.append("]正在被使用，不允许删除");
				throw new BusinessException(sb.toString());
			}
		}
	}


	// 创建设备
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public void createJzq(JzqParam jzqParam, long createUserId) throws BusinessException{
		logger.info("DeviceInfoServiceImpl - createJzq jzqParam: " + JSON.toJSONString(jzqParam));
		DeviceInfo deviceInfo = new DeviceInfo();
		BeanUtils.copyProperties(jzqParam, deviceInfo);

		// 生成设备编号
		String deviceNo = deviceInfo.getDeviceNo();
		String deviceType = DeviceType.JZQ.getCode();
		String cno = CNoUtil.CreateCNo(deviceType, deviceNo);

		// 判断集中器类型
		deviceInfo.setRelyCno(cno);
		// 添加设备连接参数表
		DeviceInfo tempDeviceInfo = this.queryDeviceInfoByCno(cno);
		if (tempDeviceInfo != null) {
			throw new BusinessException("集中器编号:" + deviceNo + "已经存在！");
		}

		// 添加集中器
		DeviceConnParam deviceConnParam = new DeviceConnParam();
		BeanUtils.copyProperties(jzqParam, deviceConnParam);
		deviceConnParam.setCno(cno);
		deviceConnParam.setWebsiteIp(jzqParam.getWebSiteIP());
		deviceConnParam.setWebsitePort(jzqParam.getWebSitePort());
		deviceConnParam.setApn(jzqParam.getAPN());
		deviceConnParamService.insertSelective(deviceConnParam);

		// 提交设备档案数据
		deviceInfo.setInstallDate(new Date());
		deviceInfo.setCno(cno);
		deviceInfo.setDeviceType(deviceType);
		deviceInfo.setCreateUserId(createUserId);
		deviceInfoMapper.insertSelective(deviceInfo);

		// 添加集中器依赖设备状态表信息
		DeviceInfoDeviceState deviceState = new DeviceInfoDeviceState();
		deviceState.setCno(cno);
		deviceState.setIsOnline(0);
		// TODO 暂时按照orgNo=1000查询
		OrgApp orgApp = orgAppService.queryByOrgNo(1000L);
		deviceState.setAppEui(orgApp.getAppEui());
		// 集中器填非32即可
		deviceState.setCommPort(31);
		deviceInfoDeviceStateService.insertSelective(deviceState);
	}

	@Override
	@Transactional
	public void editJzqBaseInfo(JzqEditBaseInfoParam param, Long currentUserId) {
		DeviceInfo deviceInfo = new DeviceInfo();
		BeanUtils.copyProperties(param, deviceInfo);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date installData = format.parse(param.getInstallDate());
			deviceInfo.setInstallDate(installData);
		} catch (ParseException e) {
			throw new BusinessException("installDate日期转换异常");
		}
		deviceInfo.setUpdateUserId(currentUserId);
		deviceInfo.setUpdateTime(new Date());

		String cno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), deviceInfo.getDeviceNo());
		Condition condition = new Condition(DeviceInfo.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andEqualTo("cno",cno);
		deviceInfoMapper.updateByConditionSelective(deviceInfo,condition);
	}

	@Override
	@Transactional
	public void editJzqConnectionParam(JzqEditConnectionParam param) {
		String cno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), param.getDeviceNo());
		DeviceConnParam deviceConnParam = new DeviceConnParam();
		BeanUtils.copyProperties(param, deviceConnParam);
		deviceConnParam.setCno(cno);
		deviceConnParamService.updateSelectiveByCno(deviceConnParam);
	}

	@Override
	@Transactional
	public void editJzqHeartBeat(String deviceNo, Integer hbCycle) {
		String cno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), deviceNo);
		DeviceConnParam deviceConnParam = new DeviceConnParam();
		deviceConnParam.setCno(cno);
		deviceConnParam.setHbCycle(hbCycle);
		deviceConnParamService.updateSelectiveByCno(deviceConnParam);
	}



	// 查询设备列表
	// 查询设备列表
	@Override
	public List<DeviceInfoDto> listDeviceRecords(DeviceInfoQueryVo deviceInfoQueryVo) {
		if(deviceInfoQueryVo.getDeviceNo()==null){
			deviceInfoQueryVo.setDeviceNo("");
		}
		if(deviceInfoQueryVo.getDeviceFactory()==null){
			deviceInfoQueryVo.setDeviceFactory("");
		}
		if(deviceInfoQueryVo.getDeviceType()==null){
			deviceInfoQueryVo.setDeviceType("");
		}
		if(deviceInfoQueryVo.getIsOnline()==null){
			deviceInfoQueryVo.setIsOnline("");
		}
		logger.info("DeviceInfoServiceImpl - listDeviceRecords query: " + JSON.toJSONString(deviceInfoQueryVo));
		List<DeviceInfoDto> list = deviceInfoMapper.listDeviceRecords(deviceInfoQueryVo);
		return list;
	}

	// 查询设备列表
	@Override
	public List<DeviceInfoDto> listDeviceRecordsNew(DeviceInfoQueryVo deviceInfoQueryVo) {
		logger.info("DeviceInfoServiceImpl - listDeviceRecords query: " + JSON.toJSONString(deviceInfoQueryVo));
		if(deviceInfoQueryVo.getDeviceNo()==null){
			deviceInfoQueryVo.setDeviceNo("");
		}
		if(deviceInfoQueryVo.getDeviceFactory()==null){
			deviceInfoQueryVo.setDeviceFactory("");
		}
		if(deviceInfoQueryVo.getDeviceType()==null){
			deviceInfoQueryVo.setDeviceType("");
		}
		if(deviceInfoQueryVo.getIsOnline()==null){
			deviceInfoQueryVo.setIsOnline("");
		}
		List<DeviceInfoDto> list = deviceInfoMapper.listDeviceRecordsNew(deviceInfoQueryVo);
		return list;
	}
	@Override
	public void converterReboot(String converterNo) {
		String jzqCno = this.getConverterPdeviceNo(converterNo);
		String jzqAddr = jzqCno.substring(jzqCno.length() - 9);
		instructService.converterReboot(jzqAddr,converterNo);
	}

	@Override
	public void converterInit(String converterNo) {
		String jzqCno = this.getConverterPdeviceNo(converterNo);
		String jzqAddr = jzqCno.substring(jzqCno.length() - 9);
		instructService.converterInit(jzqAddr,converterNo);
	}

	@Override
	public String readConverterTime(String converterNo) {
		String jzqCno = this.getConverterPdeviceNo(converterNo);
		String jzqAddr = jzqCno.substring(jzqCno.length() - 9);
		String time = instructService.readConverterTime(jzqAddr, converterNo);
		return time;
	}

	@Override
	public void setConverterTime(String converterNo) {
		String jzqCno = this.getConverterPdeviceNo(converterNo);
		String jzqAddr = jzqCno.substring(jzqCno.length() - 9);
		instructService.setConverterTime(jzqAddr,converterNo);
	}

	@Override
	public int jzqReboot(String jzqNo) {
		return instructService.jzqReboot(jzqNo);
	}

	@Override
	public int jzqInitialize(String jzqNo) {
		return instructService.jzqInitialize(jzqNo);
	}

	@Override
	public int jzqHeartBeatSend(String guid,String jzqNo, Integer hbCycle) {
		try{
			String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
			AFN07Object afn07Object = new AFN07Object(guid,jzqNo,"1",String.valueOf(hbCycle),sessionId);
			int retVal = ClientManager.sendAFN07Msg(afn07Object);
			return retVal;
	     }catch (Exception e){
			throw new BusinessException("前置机未连接");
		}
	}
	@Override
	public int jzqHeartBeatRead(String guid,String jzqNo) {
		try {
			String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
			AFN07Object afn07Object = new AFN07Object(guid,jzqNo,"0","",sessionId);
			int retVal = ClientManager.sendAFN07Msg(afn07Object);
			return retVal;
		}catch (Exception e){
			throw new BusinessException("前置机未连接");
		}
	}

	@Override
	public int jzqConnectionParamSend(JzqConnectParamSendVo sendVo) {
		String queueGuid = UuidUtil.getUuid();
		String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
		AFN05Object afn05Object = new AFN05Object(queueGuid,
				sendVo.getJzqNo(),
				sendVo.getWebsiteIp(),
				sendVo.getWebsitePort(),
				sendVo.getSpareIp(),
				sendVo.getSparePort(),
				sendVo.getApn(),
				sessionId);

		int retVal = ClientManager.sendAFN05Msg(afn05Object);
		return retVal;
	}

	@Override
	public ElectricDetailInfo queryElectricDetail(String deviceNo) throws BusinessException{
		String cno = CNoUtil.CreateCNo(DeviceType.ELECTRIC_METER.getCode(), deviceNo);
		DeviceInfo param = new DeviceInfo();
		param.setCno(cno);
		DeviceInfo deviceInfo = deviceInfoMapper.selectOne(param);

		// 取设备表上信息赋值
		ElectricDetailInfo detailInfo = new ElectricDetailInfo();
		BeanUtils.copyProperties(deviceInfo,detailInfo);

		DeviceMeterParam meterParam = deviceMeterParamService.queryEffectiveParamByCno(cno);

		// 查询设备参数配置表信息
		DeviceMeterConfig deviceMeterConfig = deviceMeterConfigService.queryByParamFlag(meterParam.getParamFlag());
		// 取设备参数表上信息赋值
		BeanUtils.copyProperties(meterParam,detailInfo);
		Integer commBaudrate = deviceMeterConfig.getCommBaudrate();
		Integer rate = CustomerInfoConstant.CommBaudrateEnum.getRateByValue(commBaudrate);
		detailInfo.setCommBaudrate(rate);

		detailInfo.setMeterMode(deviceMeterConfig.getMeterMode());
		detailInfo.setMeterCategory(deviceMeterConfig.getMeterCategory());
		detailInfo.setMeterType(deviceMeterConfig.getMeterType());
		detailInfo.setKeyGrade(deviceMeterConfig.getKeyGrade());
		detailInfo.setMeterUserName(deviceMeterConfig.getMeterUserName());
		detailInfo.setMeterUserPwd(deviceMeterConfig.getMeterUserPwd());
		detailInfo.setIsValveControl(deviceMeterConfig.getIsValveControl());

		// 查询字典项名称
		DictItem dictItem = dictItemService.findByCodeAndValue(DictCodeConstant.USE_ELECTRIC_TYPE.getDictCode(), meterParam.getDictItemValue());
		detailInfo.setDictItemName(dictItem.getDictItemName());

		// 设置集中器号
		String jzqNo = CNoUtil.getJzqNoByJzqCno(meterParam.getJzqCno());
		detailInfo.setJzqNo(jzqNo);

		// 设置预留金额
		CustomerDevMap devMap = customerDevMapService.queryByCno(cno);
		detailInfo.setInitAmount(devMap.getInitAmount());
		detailInfo.setMeterUserNo(devMap.getMeterUserNo());
		detailInfo.setTransformerNo(devMap.getTransformerNo());
		detailInfo.setBuildingNo(devMap.getBuildingNo());
		detailInfo.setAlarmThreshold(devMap.getAlarmThreshold());
		detailInfo.setAlarmThreshold1(devMap.getAlarmThreshold1());
		detailInfo.setElecMeterCategory(devMap.getElecMeterCategory());
		// 查询字典项名称
		DictItem elecMeterCategoryName = dictItemService.findByCodeAndValue(DictCodeConstant.ELECMETERCATEGORY.getDictCode(), devMap.getElecMeterCategory());
		detailInfo.setElecMeterCategoryName(elecMeterCategoryName.getDictItemName());
		detailInfo.setElecType(devMap.getElecType());
		// 查询字典项名称
		DictItem elecType = dictItemService.findByCodeAndValue(DictCodeConstant.ELECTYPE.getDictCode(), devMap.getElecType());
		detailInfo.setElecTypeName(elecType.getDictItemName());
		// 设置组织名称
		Org org = orgService.queryByOrgNo(deviceInfo.getOrgNo());
		detailInfo.setOrgName(org.getOrgName());

		//设置用户信息
		CustomerInfo customerInfo = customerInfoService.queryByCustomerNo(devMap.getCustomerNo());
		detailInfo.setCustomerName(customerInfo.getCustomerName());
		detailInfo.setCustomerContact(customerInfo.getCustomerContact());
		detailInfo.setCustomerAddr(customerInfo.getCustomerAddr());
		detailInfo.setPropertyName(customerInfo.getPropertyName());
		detailInfo.setCreateTime(customerInfo.getCreateTime());
		detailInfo.setRemark2(customerInfo.getRemark());
		detailInfo.setIsEnabled(customerInfo.getIsEnabled());
		detailInfo.setCustomerBrand(customerInfo.getCustomerBrand());
		return detailInfo;
	}

	@Override
	public WaterDetailInfo queryWaterDetail(String deviceNo) throws BusinessException {
		String cno = CNoUtil.CreateCNo(DeviceType.WATER_METER.getCode(), deviceNo);
		DeviceInfo param = new DeviceInfo();
		param.setCno(cno);
		DeviceInfo deviceInfo = deviceInfoMapper.selectOne(param);

		// 取设备参数表上信息赋值
		WaterDetailInfo detailInfo = new WaterDetailInfo();
		BeanUtils.copyProperties(deviceInfo,detailInfo);

		DeviceMeterParam meterParam = deviceMeterParamService.queryEffectiveParamByCno(cno);
		// 取设备参数表上信息赋值
		BeanUtils.copyProperties(meterParam,detailInfo);
		// 查询字典项名称
		DictItem dictItem = dictItemService.findByCodeAndValue(DictCodeConstant.USE_WATER_TYPE.getDictCode(), meterParam.getDictItemValue());
		detailInfo.setDictItemName(dictItem.getDictItemName());

		// 设置集中器号
		String jzqNo = CNoUtil.getJzqNoByJzqCno(meterParam.getJzqCno());
		detailInfo.setJzqNo(jzqNo);

		// 设置预留金额
		CustomerDevMap devMap = customerDevMapService.queryByCno(cno);
		detailInfo.setInitAmount(devMap.getInitAmount());
		detailInfo.setMeterUserNo(devMap.getMeterUserNo());
		detailInfo.setTransformerNo(devMap.getTransformerNo());
		detailInfo.setBuildingNo(devMap.getBuildingNo());

		// 设置组织名称
		Org org = orgService.queryByOrgNo(deviceInfo.getOrgNo());
		detailInfo.setOrgName(org.getOrgName());

		//设置用户信息
		CustomerInfo customerInfo = customerInfoService.queryByCustomerNo(devMap.getCustomerNo());
		detailInfo.setCustomerName(customerInfo.getCustomerName());
		detailInfo.setCustomerContact(customerInfo.getCustomerContact());
		detailInfo.setCustomerAddr(customerInfo.getCustomerAddr());
		detailInfo.setPropertyName(customerInfo.getPropertyName());
		detailInfo.setCreateTime(customerInfo.getCreateTime());
		detailInfo.setRemark2(customerInfo.getRemark());
		detailInfo.setIsEnabled(customerInfo.getIsEnabled());
		detailInfo.setCustomerBrand(customerInfo.getCustomerBrand());
		return detailInfo;
	}

	@Override
	public GasDetailInfo queryGasDetail(String deviceNo) throws BusinessException {
		String cno = CNoUtil.CreateCNo(DeviceType.GAS_METER.getCode(), deviceNo);
		DeviceInfo param = new DeviceInfo();
		param.setCno(cno);
		DeviceInfo deviceInfo = deviceInfoMapper.selectOne(param);

		// 取设备参数表上信息赋值
		GasDetailInfo detailInfo = new GasDetailInfo();
		BeanUtils.copyProperties(deviceInfo,detailInfo);

		DeviceMeterParam meterParam = deviceMeterParamService.queryEffectiveParamByCno(cno);
		// 取设备参数表上信息赋值
		BeanUtils.copyProperties(meterParam,detailInfo);
		// 查询字典项名称
		DictItem dictItem = dictItemService.findByCodeAndValue(DictCodeConstant.USE_GAS_TYPE.getDictCode(), meterParam.getDictItemValue());
		detailInfo.setDictItemName(dictItem.getDictItemName());

		// 设置集中器号
		String jzqNo = CNoUtil.getJzqNoByJzqCno(meterParam.getJzqCno());
		detailInfo.setJzqNo(jzqNo);

		// 设置预留金额
		CustomerDevMap devMap = customerDevMapService.queryByCno(cno);
		detailInfo.setInitAmount(devMap.getInitAmount());
		detailInfo.setMeterUserNo(devMap.getMeterUserNo());
		detailInfo.setTransformerNo(devMap.getTransformerNo());
		detailInfo.setBuildingNo(devMap.getBuildingNo());

		// 设置组织名称
		Org org = orgService.queryByOrgNo(deviceInfo.getOrgNo());
		detailInfo.setOrgName(org.getOrgName());

		//设置用户信息
		CustomerInfo customerInfo = customerInfoService.queryByCustomerNo(devMap.getCustomerNo());
		detailInfo.setCustomerName(customerInfo.getCustomerName());
		detailInfo.setCustomerContact(customerInfo.getCustomerContact());
		detailInfo.setCustomerAddr(customerInfo.getCustomerAddr());
		detailInfo.setPropertyName(customerInfo.getPropertyName());
		detailInfo.setCreateTime(customerInfo.getCreateTime());
		detailInfo.setRemark2(customerInfo.getRemark());
		detailInfo.setIsEnabled(customerInfo.getIsEnabled());
		detailInfo.setCustomerBrand(customerInfo.getCustomerBrand());
		return detailInfo;
	}

	@Override
	public CjqDetailInfo queryCjqDetail(String deviceNo) throws BusinessException {
		String cno = CNoUtil.CreateCNo(DeviceType.CJQ.getCode(), deviceNo);
		DeviceInfo param = new DeviceInfo();
		param.setCno(cno);
		DeviceInfo deviceInfo = deviceInfoMapper.selectOne(param);
		if (deviceInfo == null) {
			throw new BusinessException("采集器设备表记录为空");
		}

		// 取设备参数表上信息赋值
		CjqDetailInfo detailInfo = new CjqDetailInfo();
		BeanUtils.copyProperties(deviceInfo,detailInfo);
		return detailInfo;
	}

	@Override
	public ConverterDetailInfo queryConverterDetail(String deviceNo) throws BusinessException {
		String cno = CNoUtil.CreateCNo(DeviceType.CONVERTER.getCode(), deviceNo);
		DeviceInfo param = new DeviceInfo();
		param.setCno(cno);
		DeviceInfo deviceInfo = deviceInfoMapper.selectOne(param);
		if (deviceInfo == null) {
			throw new BusinessException("转换器设备表记录为空");
		}

		// 取设备参数表上信息赋值
		ConverterDetailInfo detailInfo = new ConverterDetailInfo();
		BeanUtils.copyProperties(deviceInfo,detailInfo);
		return detailInfo;
	}

	@Override
	public JzqDetailInfo queryJzqDetail(String deviceNo) throws BusinessException {
		String cno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), deviceNo);
		DeviceInfo param = new DeviceInfo();
		param.setCno(cno);
		DeviceInfo deviceInfo = deviceInfoMapper.selectOne(param);
		if (deviceInfo == null) {
			throw new BusinessException("集中器设备表记录为空");
		}

		// 取设备参数表上信息赋值
		JzqDetailInfo detailInfo = new JzqDetailInfo();
		BeanUtils.copyProperties(deviceInfo,detailInfo);

		// 查询集中器连接参数表信息
		DeviceConnParam connParam = deviceConnParamService.selectByCno(cno);
		if (connParam == null) {
			throw new BusinessException("集中器连接参数表记录为空");
		}
		// 取设备连接参数表上信息赋值
		BeanUtils.copyProperties(connParam,detailInfo);
		String spareIp = connParam.getSpareIp();
		if (spareIp == null) {
			detailInfo.setSpareIp("");
		}
		String sparePort = connParam.getSparePort();
		if (sparePort == null) {
			detailInfo.setSparePort("");
		}

		// 设置组织名称
		Org org = orgService.queryByOrgNo(deviceInfo.getOrgNo());
		if (org == null) {
			throw new BusinessException("集中器设备对应的组织信息为空");
		}
		detailInfo.setOrgName(org.getOrgName());
		return detailInfo;
	}

	@Override
	public Integer queryTotalNum(String deviceType, List<Long> dataOrgList) {
		Condition condition = new Condition(DeviceInfo.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andEqualTo("deviceType",deviceType);
		criteria.andEqualTo("isEnabled",1);
		criteria.andIn("orgNo",dataOrgList);
		criteria.andNotEqualTo("cno","040000000000000000000999999999");
		int num = deviceInfoMapper.selectCountByCondition(condition);
		return num;
	}

	@Override
	public List<OtherMainSubDto> queryOtherMainSubTree(String deviceType, String deviceNo, Integer onlineStatus, List<Long> orgNoList) {
		List<OtherMainSubDto> otherMainSubDtos = deviceInfoMapper.queryOtherMainSubTree(deviceType,deviceNo,onlineStatus,orgNoList);
		return otherMainSubDtos;
	}

	@Override
	public Map<String,OnlineStatus> queryMainSubOnlineStatus(List<String> cnoList) {
		List<OnlineStatus> onlineStatuses = deviceInfoMapper.queryMainSubOnlineStatus(cnoList);

		// 分组
		ImmutableMap<String, OnlineStatus> statusMap = Maps.uniqueIndex(onlineStatuses, new Function<OnlineStatus, String>() {
			@Nullable
			@Override
			public String apply(@Nullable OnlineStatus onlineStatus) {
				return onlineStatus.getCno();
			}
		});

		return statusMap;
	}

	@Override
	public List<DeviceCacheVo> queryAllDeviceCache() {
		return deviceInfoMapper.selectAllDeviceCache();
	}

	@Override
	public int updateOffSchemeInfo(List<String> cnoList, Integer offScheme, Integer offParam) {
		Condition condition = new Condition(DeviceInfo.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andIn("cno",cnoList);

		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setOffScheme(offScheme);
		deviceInfo.setOffParam(offParam);
		return deviceInfoMapper.updateByConditionSelective(deviceInfo,condition);
	}

	/**
	 * 获取采集器挂靠的集中器Cno
	 * @param cjqNo
	 * @return
	 */
	private String getJzqCno(String cjqNo) {
		String cno = CNoUtil.CreateCNo(DeviceType.CONVERTER.getCode(), cjqNo);
		DeviceInfo param = new DeviceInfo();
		param.setCno(cno);
		DeviceInfo deviceInfo = deviceInfoMapper.selectOne(param);
		return deviceInfo.getpDeviceNo();
	}

	/**
	 * 获取转换器的pDeviceNo
	 * @param converterNo
	 * @return
	 */
	private String getConverterPdeviceNo(String converterNo) {
		String cno = CNoUtil.CreateCNo(DeviceType.CONVERTER.getCode(), converterNo);
		DeviceInfo param = new DeviceInfo();
		param.setCno(cno);
		DeviceInfo deviceInfo = deviceInfoMapper.selectOne(param);
		if (deviceInfo == null) {
			throw new BusinessException("转换器[" + converterNo + "]设备表中不存在");
		}
		return deviceInfo.getpDeviceNo();
	}

}
