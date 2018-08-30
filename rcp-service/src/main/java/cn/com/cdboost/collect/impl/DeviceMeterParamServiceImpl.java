package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.DeviceMeterParamMapper;
import cn.com.cdboost.collect.dto.ImportantCurveDerailDTO;
import cn.com.cdboost.collect.dto.ImportantCurveInfo;
import cn.com.cdboost.collect.dto.ImportantCustomerInfo;
import cn.com.cdboost.collect.dto.param.DeviceMeterParamVo;
import cn.com.cdboost.collect.dto.param.ImportantABCVo;
import cn.com.cdboost.collect.dto.param.ImportantCurveVo;
import cn.com.cdboost.collect.dto.param.ImportantCustomerVo;
import cn.com.cdboost.collect.emums.ProcedureErrorCode;
import cn.com.cdboost.collect.enums.InstructAFN;
import cn.com.cdboost.collect.enums.InstructCode;
import cn.com.cdboost.collect.enums.ResultCode;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.*;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.*;
import com.example.clienttest.client.AFN03Node;
import com.example.clienttest.client.AFN03Object;
import com.example.clienttest.client.AbsBaseDataObject;
import com.example.clienttest.client.BaseAFNObject;
import com.example.clienttest.clientfuture.ClientManager;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.*;

/**
 * 设备参数表服务接口实现类
 */
@Service("deviceMeterParamService")
public class DeviceMeterParamServiceImpl extends BaseServiceImpl<DeviceMeterParam> implements DeviceMeterParamService {
	private static final Logger logger = LoggerFactory.getLogger(DeviceMeterParamServiceImpl.class);

	@Autowired
	private DeviceMeterParamMapper deviceMeterParamMapper;
	@Autowired
	private DeviceInfoService deviceInfoService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private MeterReadQueueService meterReadQueueService;
	@Autowired
	private DeviceMeterConfigService deviceMeterConfigService;

	@Override
	@Transactional
	public int updateCommPointCode2Zero(String cno) {
		DeviceMeterParam updateParam = new DeviceMeterParam();
		updateParam.setCommPointCode(0);
		Condition cond = new Condition(DeviceMeterParam.class);
		Example.Criteria condCriteria = cond.createCriteria();
		condCriteria.andEqualTo("cno",cno);
		return deviceMeterParamMapper.updateByConditionSelective(updateParam,cond);
	}

	@Override
	public int batchUpdateSendFlag2Zero(List<String> cno) {
		Condition condition = new Condition(DeviceMeterParam.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andIn("cno",cno);

		DeviceMeterParam param = new DeviceMeterParam();
		param.setSendFlag(0);
		return deviceMeterParamMapper.updateByConditionSelective(param,condition);
	}

	@Override
	@Transactional
	public void batchUpdateCommPointCode2Zero(List<String> cnos) {
		DeviceMeterParam updateParam = new DeviceMeterParam();
		updateParam.setCommPointCode(0);
		Condition cond = new Condition(DeviceMeterParam.class);
		Example.Criteria condCriteria = cond.createCriteria();
		condCriteria.andIn("cno",cnos);
		deviceMeterParamMapper.updateByConditionSelective(updateParam,cond);
	}

	@Override
	@Transactional
	public void batchDeleteByCnos(List<String> cnos) {
		Condition condition = new Condition(DeviceMeterParam.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andIn("cno",cnos);
		deviceMeterParamMapper.deleteByCondition(condition);
	}

	// 同步设备档案（选择）
	@Override
	@Transactional
	public int syncDeviceMeterParam(List<DeviceMeterParam> listDevParam, String sendGuid){
		// 待发送集合
		int index = 0;
		int len = listDevParam.size();
		List<DeviceMeterParam> list = new ArrayList<>();
        List<String> listCNos= new ArrayList<>();
		// 获取发送的列表
		while (index < len) {

			listCNos.add(listDevParam.get(index++).getCno());
			if ((index > 0 && index % 50 == 0) || index == len) {
				List<DeviceMeterParam> listTemp = this.findDeviceMeterParamByCnos(listCNos);
				for (DeviceMeterParam mParam : listTemp) {
					list.add(mParam);

					MeterReadQueue meterReadQueue = new MeterReadQueue();
					String jzqNo = CNoUtil.getNo(mParam.getJzqCno());
					String meterNo = CNoUtil.getNo(mParam.getCno());
					meterReadQueue.setJzqNo(jzqNo);
					meterReadQueue.setMeterNo(meterNo);
					meterReadQueue.setMeterCno(mParam.getCno());
					meterReadQueue.setJzqCno(mParam.getJzqCno());
					meterReadQueue.setQueueGuid(sendGuid);
					meterReadQueue.setCreateTime(new Date());
					// 把下发信息放入队列表
					meterReadQueueService.insertSelective(meterReadQueue);
				}
				// 更新档案下发的状态
				Condition condition = new Condition(DeviceMeterParam.class);
				Example.Criteria criteria = condition.createCriteria();
				criteria.andIn("cno",listCNos);

				DeviceMeterParam param = new DeviceMeterParam();
				param.setSendFlag(0);
				deviceMeterParamMapper.updateByConditionSelective(param,condition);
				listCNos.clear();
			}
		}
		if(!CollectionUtils.isEmpty(list)){
			return SysnMeterToJzq(list, false, sendGuid, CNoUtil.getNo(list.get(0).getJzqCno()));
		}else {
			return 1;
		}
	}

	/**
	 * @Description 同步档案参数
	 * @param list
	 *            档案集合
	 * @param isInit
	 *            是否初始化
	 * @return
	 */
	private Integer SysnMeterToJzq(List<DeviceMeterParam> list, boolean isInit, String sendGuid, String JzqAddr) {
		boolean isInitSuccess = true;// 是否同步成功；
		int rst = 0; // 操作结果
		boolean flag = true;

		if (isInit) {
			// 初始化终端档案
			rst =  ClientManager.sendAFN01Msg(new BaseAFNObject(sendGuid, InstructAFN.Reboot.GetValueToStr(), JzqAddr));
			// 初始化档案是否发送成功
			if (rst != InstructCode.Success.getValue()) {
				isInitSuccess = false;
			}
		}
		List<String> cnoList = Lists.newArrayList();
		Set<String> paramFlagSet = Sets.newHashSet();
		for (DeviceMeterParam meterParam : list) {
			cnoList.add(meterParam.getCno());
			paramFlagSet.add(meterParam.getParamFlag());
		}

		// 批量查询设备信息
		List<DeviceInfo> deviceInfos = deviceInfoService.batchQueryByCnos(cnoList);
		// 分组
		ImmutableMap<String, DeviceInfo> deviceMap = Maps.uniqueIndex(deviceInfos, new Function<DeviceInfo, String>() {
			@Nullable
			@Override
			public String apply(@Nullable DeviceInfo deviceInfo) {
				return deviceInfo.getCno();
			}
		});

		// 批量查询设备参数配置表信息
		ImmutableMap<String, DeviceMeterConfig> meterConfigMap = deviceMeterConfigService.batchQueryByParamFlags(paramFlagSet);

		Map<String, List<AFN03Node>> map = new HashMap<>();
		for (DeviceMeterParam deviceMeterParam : list) {
			String cno = deviceMeterParam.getCno();
			DeviceInfo deviceInfo = deviceMap.get(cno);

			if (isInitSuccess) {
				String jzqCNo = deviceMeterParam.getJzqCno().substring(2).replaceAll("^0*", "");
				List<AFN03Node> listAFN03;

				if (map.containsKey(jzqCNo)) {
					listAFN03 = map.get(jzqCNo);
				} else {
					listAFN03 = new ArrayList<AFN03Node>();
					map.put(jzqCNo, listAFN03); // 添加
				}
				DeviceMeterConfig deviceMeterConfig = meterConfigMap.get(deviceMeterParam.getParamFlag());
				// 添加发送的档案
				listAFN03.add(new AFN03Node(deviceMeterParam.getMoteType(), StringUtil.getMoteType(deviceInfo.getCno().substring(0, 2)),
						String.valueOf(deviceMeterParam.getCommSetupSn()), String.valueOf(deviceMeterParam.getCommPointCode()),
						String.valueOf(deviceMeterConfig.getCommBaudrate()),
						String.valueOf(deviceMeterParam.getCommPort()), String.valueOf(deviceMeterConfig.getCommRule()),
						deviceMeterParam.getCommAddr(), String.valueOf(deviceMeterConfig.getCommFactorCnt()),
						deviceMeterParam.getCommCollectionNo()));
			}
		}

		ArrayList<AFN03Object> listobj = new ArrayList<>();
		for (String strKey : map.keySet()) {
			ArrayList<AFN03Node> afn3NodeList = (ArrayList<AFN03Node>) map.get(strKey);
			if(!CollectionUtils.isEmpty(afn3NodeList)){
				AFN03Node afn03Node = afn3NodeList.get(0);
				SysConfig sysConfig = sysConfigService.queryByConfigName("AppEUI");

				AFN03Object afn03Object = new AFN03Object(sendGuid, JzqAddr,sysConfig.getConfigValue(),
						afn03Node.getPort(),(ArrayList<AFN03Node>) map.get(strKey));
				listobj.add(afn03Object);
			}
		}
		// 发送档案
		int result = 1;
		if(!CollectionUtils.isEmpty(listobj)){
			result = ClientManager.sendAFN03Msg(listobj);
			logger.info("同步档案结果为：" + result);
		}

		if(flag && (result == 1)){
			rst = 1;
		}
		logger.info("DeviceMeterParamServiceImpl - syncMeterToJzq 返回结果: "+rst);
		return rst;
	}


	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public int readSyncState(String sendGuid) {
		AbsBaseDataObject clientFutureStatus = ClientManager.getClientFutureStatus(sendGuid);
		return clientFutureStatus.getStatus();
	}

	@Override
	@Transactional
	public int syncMeterToJzq(String cno){
		List<DeviceMeterParam> meterParm = Lists.newArrayList();
		DeviceMeterParam dParam = new DeviceMeterParam();
		dParam.setCno(cno);
		meterParm.add(dParam); // 添加参数
		// 同步档案
		String sendGuid = UuidUtil.getUuid();
		return syncDeviceMeterParam(meterParm, sendGuid);
	}

	/**
	 * 取消下发客户档案
	 * @param guid
	 * @return
	 */
	@Override
	public boolean cancelSyncMeter(String guid) {
		logger.info("DeviceMeterParamServiceImpl--cancelSyncMeter query：" + guid);
		PreconditionsUtil.checkArgument(ResultCode.ParamError.getValue(),!StringUtils.isEmpty(guid),ResultCode.ParamError.getDesc());
		// 取消下发客户档案，调用中间件
		boolean flag = ClientManager.stopSendAFN03(guid);
		logger.info("DeviceMeterParamServiceImpl--cancelSyncMeter 返回数据：" + flag);
		return flag;
	}

	@Override
	public DeviceMeterParam queryEffectiveParamByCno(String cno) throws BusinessException {
		Condition condition = new Condition(DeviceMeterParam.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andEqualTo("cno", cno);
		criteria.andGreaterThan("commPointCode",0);
		List<DeviceMeterParam> meterParams = deviceMeterParamMapper.selectByCondition(condition);
		if (CollectionUtils.isEmpty(meterParams)) {
			return null;
		}

		int size = meterParams.size();
		if (size > 1) {
			throw new BusinessException("查询出多条数据");
		}

		return meterParams.get(0);
	}
	@Override
	public DeviceMeterParam queryDictIteamByCno(String cno) throws BusinessException {
		Condition condition = new Condition(DeviceMeterParam.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andEqualTo("cno", cno);
		List<DeviceMeterParam> meterParams = deviceMeterParamMapper.selectByCondition(condition);
		if (CollectionUtils.isEmpty(meterParams)) {
			return null;
		}
		return meterParams.get(0);
	}
	@Override
	public List<DeviceMeterParam> findDeviceMeterParamByCnos(List<String> cnos) {
		Condition condition = new Condition(DeviceMeterParam.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andIn("cno",cnos);
		criteria.andGreaterThan("commPointCode",0);
		return deviceMeterParamMapper.selectByCondition(condition);
	}

	@Override
	@Transactional(rollbackFor = BusinessException.class)
	public void createDeviceMeterParam(DeviceMeterParam deviceMeterParam) {
		DeviceMeterParamVo paramVo = new DeviceMeterParamVo();
		BeanUtils.copyProperties(deviceMeterParam,paramVo);
		deviceMeterParamMapper.creatDeviceMeterParam(paramVo);
		String result = paramVo.getResult();
		if (!ProcedureErrorCode.SUCCESS.getCode().equals(Integer.parseInt(result))) {
			logger.info("设备参数表保存调用存储过程失败,result=" + result);
			throw new BusinessException("系统异常");
		}
	}

	@Override
    @Transactional
	public int updateSelectiveByCno(DeviceMeterParam deviceMeterParam) {
		Condition condition=new Condition(DeviceMeterParam.class);
		Example.Criteria  criteria =condition.createCriteria();
		criteria.andEqualTo("cno",deviceMeterParam.getCno());
		criteria.andNotEqualTo("commPointCode","0");
		return deviceMeterParamMapper.updateByConditionSelective(deviceMeterParam,condition);
	}



	@Override
	public List<ImportantCustomerInfo> queryImportantCustomer(ImportantCustomerVo ImportantVo) {
		return deviceMeterParamMapper.queryImportantCustomer(ImportantVo);
	}

	@Override
	public List<ImportantCurveInfo> queryImportantCurve(ImportantCurveVo ImportantVo) {
		return deviceMeterParamMapper.queryImportantCurve(ImportantVo);
	}

	@Override
	public List<ImportantCurveDerailDTO> queryImportantCollection(ImportantCurveVo ImportantVo) {
		List<ImportantCurveDerailDTO> importantCurveDerailDTOS = deviceMeterParamMapper.queryImportCollection(ImportantVo);
		for (ImportantCurveDerailDTO importantCurveDerailDTO : importantCurveDerailDTOS) {
			if(importantCurveDerailDTO.getActiveTotal()!=null){
				importantCurveDerailDTO.setActiveTotal(String.valueOf(new BigDecimal(importantCurveDerailDTO.getActiveTotal()).setScale(2,BigDecimal.ROUND_HALF_UP)));
			}
			if(importantCurveDerailDTO.getActiveTotalValue()!=null){
				importantCurveDerailDTO.setActiveTotalValue(String.valueOf(new BigDecimal(importantCurveDerailDTO.getActiveTotalValue()).setScale(2,BigDecimal.ROUND_HALF_UP)));
			}
		}
		return importantCurveDerailDTOS;
	}

	@Override
	public List<ImportantCurveInfo> queryImportantABC(ImportantABCVo ImportantABCVo) {
		return deviceMeterParamMapper.queryImportantABC(ImportantABCVo);
	}


}
