 package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.constant.ClientConstant;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.DeviceListInfo;
import cn.com.cdboost.collect.dto.response.ReadConverterTimeInfo;
import cn.com.cdboost.collect.enums.Action;
import cn.com.cdboost.collect.enums.Auth;
import cn.com.cdboost.collect.enums.DeviceType;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.model.Impot;
import cn.com.cdboost.collect.service.DeviceInfoService;
import cn.com.cdboost.collect.service.DeviceMeterParamService;
import cn.com.cdboost.collect.service.GenerateFileService;
import cn.com.cdboost.collect.service.UserLogService;
import cn.com.cdboost.collect.util.DateUtil;
import cn.com.cdboost.collect.util.UuidUtil;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author linyang
 * @date 2017年5月19日
 */
@Controller
@RequestMapping("/deviceInfo")
public class DeviceInfoController {
	private static final Logger logger = LoggerFactory.getLogger(DeviceInfoController.class);

	@Autowired
	private DeviceInfoService deviceInfoService;
	@Autowired
	private DeviceMeterParamService deviceMeterParamService;
	@Autowired
	private UserLogService userLogService;
	@Autowired
	private GenerateFileService generateFileService;
	// 查询设备档案列表信息
	@SystemControllerLog(description = "查询设备档案列表信息")
	@RequestMapping(value = "/queryList")
	@ResponseBody
	public String queryListOld(HttpSession session, @Valid @RequestBody DeviceInfoQueryParam queryParam) {
		PageResult<List<DeviceListInfo>> result = new PageResult<>();
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		DeviceInfoQueryVo queryVo = new DeviceInfoQueryVo();
		BeanUtils.copyProperties(queryParam,queryVo);
		queryVo.setUserId(Long.valueOf(currentUser.getId()));
		Integer isOnline = queryParam.getIsOnline();
		if (isOnline != null) {
			queryVo.setIsOnline(String.valueOf(isOnline));
		}
		queryVo.setInstallDateSTime("1900-01-01");
		Date endTimeOfCurrentDay = DateUtil.getEndTimeOfCurrentDay();
		String endTimeStr = DateUtil.convertDate2String(endTimeOfCurrentDay, DateUtil.DATETIME_FOMAT);
		queryVo.setInstallDateETime(endTimeStr);

		List<DeviceInfoDto> list = deviceInfoService.listDeviceRecords(queryVo);
		if (CollectionUtils.isEmpty(list)) {
			List<DeviceListInfo> emptyList = Lists.newArrayList();
			result.setData(emptyList);
			return JSON.toJSONString(result);
		}

		// 转成前端需要的vo
		List<DeviceListInfo> dataList = Lists.newArrayList();
		for (DeviceInfoDto infoDto : list) {
			DeviceListInfo info = new DeviceListInfo();
			BeanUtils.copyProperties(infoDto,info);
			info.setDeviceCno(infoDto.getCno());
			dataList.add(info);
		}

		result.setData(dataList);
		result.setTotal(queryVo.getTotal());
		return JSON.toJSONString(result);
	}
	// 查询设备档案列表信息
	@SystemControllerLog(description = "查询设备档案列表信息")
	@RequestMapping(value = "/queryListNew")
	@ResponseBody
	public String queryListNew(HttpSession session, @Valid @RequestBody DeviceInfoQueryParam queryParam) {
		PageResult<List<DeviceListInfo>> result = new PageResult<>();
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		DeviceInfoQueryVo queryVo = new DeviceInfoQueryVo();
		BeanUtils.copyProperties(queryParam,queryVo);
		queryVo.setUserId(Long.valueOf(currentUser.getId()));
		Integer isOnline = queryParam.getIsOnline();
		if (isOnline != null) {
			queryVo.setIsOnline(String.valueOf(isOnline));
		}
		queryVo.setInstallDateSTime("1900-01-01");
		Date endTimeOfCurrentDay = DateUtil.getEndTimeOfCurrentDay();
		String endTimeStr = DateUtil.convertDate2String(endTimeOfCurrentDay, DateUtil.DATETIME_FOMAT);
		queryVo.setInstallDateETime(endTimeStr);
		String s = generateInfo(session, queryParam);
		queryVo.setImportGuid(s);
		List<DeviceInfoDto> list = deviceInfoService.listDeviceRecordsNew(queryVo);
		if (CollectionUtils.isEmpty(list)) {
			List<DeviceListInfo> emptyList = Lists.newArrayList();
			result.setData(emptyList);
			return JSON.toJSONString(result);
		}

		// 转成前端需要的vo
		List<DeviceListInfo> dataList = Lists.newArrayList();
		for (DeviceInfoDto infoDto : list) {
			DeviceListInfo info = new DeviceListInfo();
			BeanUtils.copyProperties(infoDto,info);
			info.setDeviceCno(infoDto.getCno());
			dataList.add(info);
		}

		result.setData(dataList);
		result.setTotal(queryVo.getTotal());
		return JSON.toJSONString(result);
	}
	// 查询电表明细信息
	@SystemControllerLog(description = "查询电表明细信息")
	@RequestMapping(value = "/queryElectricDetail")
	@ResponseBody
	public String queryElectricDetail(@RequestParam String deviceNo) {
		Result<ElectricDetailInfo> result = new Result<>();
		if (StringUtils.isEmpty(deviceNo)) {
			result.error("deviceNo不能为空");
			return JSON.toJSONString(result);
		}

		ElectricDetailInfo detailInfo = deviceInfoService.queryElectricDetail(deviceNo);
		result.setData(detailInfo);
		return JSON.toJSONString(result);
	}

	// 查询水表明细信息
	@SystemControllerLog(description = "查询水表明细信息")
	@RequestMapping(value = "/queryWaterDetail")
	@ResponseBody
	public String queryWaterDetail(@RequestParam String deviceNo) {
		Result<WaterDetailInfo> result = new Result<>();
		if (StringUtils.isEmpty(deviceNo)) {
			result.error("deviceNo不能为空");
			return JSON.toJSONString(result);
		}

		WaterDetailInfo detailInfo = deviceInfoService.queryWaterDetail(deviceNo);
		result.setData(detailInfo);
		return JSON.toJSONString(result);
	}

	// 查询气表明细信息
	@SystemControllerLog(description = "查询气表明细信息")
	@RequestMapping(value = "/queryGasDetail")
	@ResponseBody
	public String queryGasDetail(@RequestParam String deviceNo) {
		Result<GasDetailInfo> result = new Result<>();
		if (StringUtils.isEmpty(deviceNo)) {
			result.error("deviceNo不能为空");
			return JSON.toJSONString(result);
		}

		GasDetailInfo detailInfo = deviceInfoService.queryGasDetail(deviceNo);
		result.setData(detailInfo);
		return JSON.toJSONString(result);
	}

	// 查询采集器明细信息
	@SystemControllerLog(description = "查询采集器明细信息")
	@RequestMapping(value = "/queryCjqDetail")
	@ResponseBody
	public String queryCjqDetail(@RequestParam String deviceNo) {
		Result<CjqDetailInfo> result = new Result<>();
		if (StringUtils.isEmpty(deviceNo)) {
			result.error("deviceNo不能为空");
			return JSON.toJSONString(result);
		}

		CjqDetailInfo detailInfo = deviceInfoService.queryCjqDetail(deviceNo);
		result.setData(detailInfo);
		return JSON.toJSONString(result);
	}

	// 查询转换器明细信息
	@SystemControllerLog(description = "查询转换器明细信息")
	@RequestMapping(value = "/queryConverterDetail")
	@ResponseBody
	public String queryConverterDetail(@RequestParam String deviceNo) {
		Result<ConverterDetailInfo> result = new Result<>();
		if (StringUtils.isEmpty(deviceNo)) {
			result.error("deviceNo不能为空");
			return JSON.toJSONString(result);
		}

		ConverterDetailInfo detailInfo = deviceInfoService.queryConverterDetail(deviceNo);
		result.setData(detailInfo);
		return JSON.toJSONString(result);
	}

	// 查询集中器明细信息
	@SystemControllerLog(description = "查询集中器明细信息")
	@RequestMapping(value = "/queryJzqDetail")
	@ResponseBody
	public String queryJzqDetail(@RequestParam String deviceNo) {
		Result<JzqDetailInfo> result = new Result<>();
		if (StringUtils.isEmpty(deviceNo)) {
			result.error("deviceNo不能为空");
			return JSON.toJSONString(result);
		}

		JzqDetailInfo detailInfo = deviceInfoService.queryJzqDetail(deviceNo);
		result.setData(detailInfo);
		return JSON.toJSONString(result);
	}
	// 查询集中器状态明细信息
	@SystemControllerLog(description = "查询集中器状态明细信息")
	@RequestMapping(value = "/queryJzqStateDetail")
	@ResponseBody
	public String queryJzqStateDetail(@RequestParam String deviceCno) {
		PageResult result = new PageResult<>();
		if (StringUtils.isEmpty(deviceCno)) {
			result.error("deviceCno不能为空");
			return JSON.toJSONString(result);
		}
		PageInfo pageInfo= deviceInfoService.queryJzqStateDetail(deviceCno);
		result.setData(pageInfo.getList());
		result.setTotal(pageInfo.getTotal());
		return JSON.toJSONString(result);
	}
	// 设备档案添加集中器
	@Auth(menuID = 10002102L, actionID = 1L)
	@SystemControllerLog(description = "设备档案添加集中器")
	@RequestMapping(value = "/addJzq")
	@ResponseBody
	public String addJzq(HttpSession session, @Valid @RequestBody JzqAddParam jzqAddParam) {
		LoginUser currentUser= (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		userLogService.create(currentUser.getId(), Action.OPEN_ICCARD.getActionId(),"设备档案","deviceNo",jzqAddParam.getDeviceNo(),"添加集中器["+jzqAddParam.getDeviceNo()+"]" ,JSON.toJSONString(jzqAddParam));
		Result result = new Result("添加成功");
		JzqParam jzqParam = new JzqParam();
		BeanUtils.copyProperties(jzqAddParam, jzqParam);
		jzqParam.setAPN(jzqAddParam.getApn());
		jzqParam.setWebSiteIP(jzqAddParam.getWebSiteIp());

		long createUserId = currentUser.getCreateUserId();

		deviceInfoService.createJzq(jzqParam, createUserId);
		return JSON.toJSONString(result);
	}

	@Auth(menuID = 10002102L, actionID = 2L)
	@SystemControllerLog(description = "设备档案修改集中器基础信息")
	@RequestMapping(value = "/editJzqBaseInfo")
	@ResponseBody
	public String editJzqBaseInfo(HttpSession session, @Valid @RequestBody JzqEditBaseInfoParam param) {
		LoginUser currentUser= (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		userLogService.create(currentUser.getId(), Action.OPEN_ICCARD.getActionId(),"设备档案","deviceNo",param.getDeviceNo(),"修改集中器["+param.getDeviceNo()+"]基础信息", JSON.toJSONString(param));
		Result result = new Result("修改成功");
		deviceInfoService.editJzqBaseInfo(param,Long.valueOf(currentUser.getId()));
		return JSON.toJSONString(result);
	}

	@Auth(menuID = 10002102L, actionID = 2L)
	@SystemControllerLog(description = "设备档案修改集中器主站连接参数信息")
	@RequestMapping(value = "/editJzqConnectionParam")
	@ResponseBody
	public String editJzqConnectionParam(HttpSession session,@Valid @RequestBody JzqEditConnectionParam param) {
		LoginUser currentUser= (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		userLogService.create(currentUser.getId(), Action.MODIFY.getActionId(),"设备档案","deviceNo",param.getDeviceNo(),"修改集中器:["+param.getDeviceNo()+"]主站连接参数信息", JSON.toJSONString(param));
		Result result = new Result("修改成功");
		deviceInfoService.editJzqConnectionParam(param);
		return JSON.toJSONString(result);
	}

	@Auth(menuID = 10002102L, actionID = 2L)
	@SystemControllerLog(description = "设备档案修改集中器心跳")
	@RequestMapping(value = "/editJzqHeartBeat")
	@ResponseBody
	public String editJzqHeartBeat(HttpSession session,@RequestParam String deviceNo, @RequestParam Integer hbCycle) {
		Map map= Maps.newHashMap();
		map.put("deviceNo",deviceNo);map.put("hbCycle",hbCycle);
		LoginUser currentUser= (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		userLogService.create(currentUser.getId(), Action.MODIFY.getActionId(),"设备档案","deviceNo",deviceNo,"修改集中器["+deviceNo+"]心跳", JSON.toJSONString(map));
		Result result = new Result("修改成功");
		if (StringUtils.isEmpty(deviceNo)) {
			result.error("deviceNo不能为空");
			return JSON.toJSONString(result);
		}

		deviceInfoService.editJzqHeartBeat(deviceNo,hbCycle);
		return JSON.toJSONString(result);
	}

//	@Auth(menuID = 100032L, actionID = 2L)
//	@SystemControllerLog(description = "设备档案集中器明细页面，主站IP和端口下发操作")
//	@RequestMapping(value = "/syncJzqConnectionParam")
//	@ResponseBody
//	public String syncJzqConnectionParam(@Valid @RequestBody JzqEditConnectionParam param) {
//		Result result = new Result("下发成功");
//		int code = deviceInfoService.syncJzqConnectionParam(param);
//		String message = ClientManagerEnum.TaskStatus.getMessageByCode(code);
//		logger.info("集中器主站IP和端口，下发结果code=" + code + ",message=" + message);
//		if (ClientManagerEnum.TaskStatus.SUCCESS.getCode() != code) {
//			result.error(message);
//		}
//		return JSON.toJSONString(result);
//	}

//	@Auth(menuID = 100032L, actionID = 2L)
//	@SystemControllerLog(description = "设备档案集中器明细页面，心跳下发操作")
//	@RequestMapping(value = "/syncJzqHeartBeat")
//	@ResponseBody
//	public String syncJzqHeartBeat(@RequestParam String deviceNo, @RequestParam Integer hbCycle) {
//		Result result = new Result("下发成功");
//		if (StringUtils.isEmpty(deviceNo)) {
//			result.error("deviceNo不能为空");
//			return JSON.toJSONString(result);
//		}
//
//		int code = deviceInfoService.syncJzqHeartBeat(deviceNo,hbCycle);
//		String message = ClientManagerEnum.TaskStatus.getMessageByCode(code);
//		logger.info("集中器心跳，下发结果code=" + code + ",message=" + message);
//		if (ClientManagerEnum.TaskStatus.SUCCESS.getCode() != code) {
//			result.error(message);
//		}
//		return JSON.toJSONString(result);
//	}

//	@Auth(menuID = 100032L, actionID = 2L)
//	@SystemControllerLog(description = "设备档案集中器明细页面，主站IP和端口读取操作")
//	@RequestMapping(value = "/readJzqConnectionParam")
//	@ResponseBody
//	public String readJzqConnectionParam(@RequestParam String deviceNo) {
//		Result<CommonJzqConnectionParam> result = new Result<>();
//		if (StringUtils.isEmpty(deviceNo)) {
//			result.error("deviceNo不能为空");
//			return JSON.toJSONString(result);
//		}
//
//		// TODO 调用中间件
//		CommonJzqConnectionParam data = new CommonJzqConnectionParam();
//		result.setData(data);
//		return JSON.toJSONString(result);
//	}
//
//	@Auth(menuID = 100032L, actionID = 2L)
//	@SystemControllerLog(description = "设备档案集中器明细页面，心跳读取操作")
//	@RequestMapping(value = "/readJzqHeartBeat")
//	@ResponseBody
//	public String readJzqHeartBeat(@RequestParam String deviceNo) {
//		Result<Integer> result = new Result<>();
//		if (StringUtils.isEmpty(deviceNo)) {
//			result.error("deviceNo不能为空");
//			return JSON.toJSONString(result);
//		}
//
//		// TODO 调用中间件
//		result.setData(100);
//		return JSON.toJSONString(result);
//	}

	@Auth(menuID = 10002102L, actionID = 3L)
	@SystemControllerLog(description = "设备档案批量删除")
	@RequestMapping(value = "/batchDelete")
	@ResponseBody
	public String batchDelete(HttpSession session, @RequestBody List<String> cnos) {
		Result result = new Result("删除成功");
		if (CollectionUtils.isEmpty(cnos)) {
			result.error("参数列表为空");
			return JSON.toJSONString(result);
		}

		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);


		// 批量删除
		deviceInfoService.batchDeleteDeviceInfo(cnos);

		// 同步设备档案
		for (String cno : cnos) {
			deviceMeterParamService.syncMeterToJzq(cno);
		}
		if(cnos.size()>1){
			userLogService.create(currentUser.getId(), Action.MODIFY.getActionId(),"设备档案","cnos", "","批量删除设备档案", JSON.toJSONString(cnos));
		}else{
			String cno=cnos.get(0).substring(0,2);
			if(cno.equals(DeviceType.JZQ.getCode())){
				cno=DeviceType.JZQ.getMessage();
			}
			if(cno.equals(DeviceType.GAS_METER.getCode())){
				cno=DeviceType.GAS_METER.getMessage();
			}
			if(cno.equals(DeviceType.WATER_METER.getCode())){
				cno=DeviceType.WATER_METER.getMessage();
			}
			if(cno.equals(DeviceType.ELECTRIC_METER.getCode())){
				cno=DeviceType.ELECTRIC_METER.getMessage();
			}
			if(cno.equals(DeviceType.CONVERTER.getCode())){
				cno=DeviceType.CONVERTER.getMessage();
			}
			if(cno.equals(DeviceType.CJQ.getCode())){
				cno=DeviceType.CJQ.getMessage();
			}
			userLogService.create(currentUser.getId(), Action.MODIFY.getActionId(),"设备档案","cnos", "","删除"+cno+"["+cnos.get(0).substring(cnos.get(0).length()-9)+"]", JSON.toJSONString(cnos));
		}

		return JSON.toJSONString(result);
	}

	@Auth(menuID = 10002102L, actionID = 23L)
	@SystemControllerLog(description = "转换器重启")
	@RequestMapping(value = "/converterReboot")
	@ResponseBody
	public String converterReboot(@RequestParam String converterNo) {
		Result result = new Result("重启成功");
		if (StringUtils.isEmpty(converterNo)) {
			result.error("converterNo不能为空");
			return JSON.toJSONString(result);
		}
		deviceInfoService.converterReboot(converterNo);
		return JSON.toJSONString(result);
	}

	@Auth(menuID = 10002102L, actionID = 24L)
	@SystemControllerLog(description = "转换器初始化")
	@RequestMapping(value = "/converterInit")
	@ResponseBody
	public String converterInit(@RequestParam String converterNo) {
		Result result = new Result("初始化成功");
		if (StringUtils.isEmpty(converterNo)) {
			result.error("converterNo不能为空");
			return JSON.toJSONString(result);
		}

		deviceInfoService.converterInit(converterNo);
		return JSON.toJSONString(result);
	}

	@Auth(menuID = 10002102L, actionID = 25L)
	@SystemControllerLog(description = "转换器读时钟")
	@RequestMapping(value = "/readConverterTime")
	@ResponseBody
	public String readConverterTime(@RequestParam String converterNo) {
		Result<ReadConverterTimeInfo> result = new Result();
		if (StringUtils.isEmpty(converterNo)) {
			result.error("converterNo不能为空");
			return JSON.toJSONString(result);
		}

		String converterTime = deviceInfoService.readConverterTime(converterNo);
		ReadConverterTimeInfo timeInfo = new ReadConverterTimeInfo();
		timeInfo.setConverterTime(converterTime);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = format.format(new Date());
		timeInfo.setServerTime(currentTime);

		result.setData(timeInfo);
		return JSON.toJSONString(result);
	}

	@Auth(menuID = 10002102L, actionID = 26L)
	@SystemControllerLog(description = "转换器设置时钟")
	@RequestMapping(value = "/setConverterTime")
	@ResponseBody
	public String setConverterTime(@RequestParam String converterNo) {
		Result result = new Result("设置成功");
		if (StringUtils.isEmpty(converterNo)) {
			result.error("converterNo不能为空");
			return JSON.toJSONString(result);
		}

		deviceInfoService.setConverterTime(converterNo);
		return JSON.toJSONString(result);
	}

	@Auth(menuID = 10002102L, actionID = 16L)
	@SystemControllerLog(description = "集中器重启")
	@RequestMapping(value = "/jzqReboot")
	@ResponseBody
	public String jzqReboot(HttpSession session, @RequestParam String jzqNo) {
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

		Result result = new Result("重启成功");

		// 参数校验
		boolean flag = this.checkParam(jzqNo, result);
		if (!flag) {
			return JSON.toJSONString(result);
		}

		int i = deviceInfoService.jzqReboot(jzqNo);
		if(1==i){
			userLogService.create(currentUser.getId(), Action.JZQ_REBOOT.getActionId(),"设备档案","", "","重启集中器["+jzqNo+"]", JSON.toJSONString(jzqNo));
			result.setMessage("集中器重启成功");
		}else{
			result.error("集中器重启失败");
		}
		return JSON.toJSONString(result);
	}

	@Auth(menuID = 10002102L, actionID = 17L)
	@SystemControllerLog(description = "集中器初始化")
	@RequestMapping("/jzqInit")
	@ResponseBody
	public String jzqInit(HttpSession session, @RequestParam String jzqNo) {
		Result result = new Result();
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		// 参数校验
		boolean flag = this.checkParam(jzqNo, result);
		if (!flag) {
			return JSON.toJSONString(result);
		}

		int i = deviceInfoService.jzqInitialize(jzqNo);
		if(1==i){
			userLogService.create(currentUser.getId(), Action.MODIFY.getActionId(),"设备档案","cnos", "","初始化集中器["+jzqNo+"]", JSON.toJSONString(jzqNo));
			result.setMessage("集中器初始化成功");
		}else{

			result.error("集中器初始化失败");
		}
		return JSON.toJSONString(result);
	}

	@SystemControllerLog(description = "集中器心跳下发")
	@RequestMapping("/jzqHeartBeatSend")
	@ResponseBody
	public String jzqHeartBeatSend(@RequestParam String guid,
								   @RequestParam String jzqNo,
								   @RequestParam Integer hbCycle) {
		Result result = new Result();
		// 参数校验
		if (StringUtils.isEmpty(guid)) {
			result.error("guid不能为空");
			return JSON.toJSONString(result);
		}
		// 校验集中器号
		boolean flag = this.checkParam(jzqNo, result);
		if (!flag) {
			return JSON.toJSONString(result);
		}

		int retVal = deviceInfoService.jzqHeartBeatSend(guid,jzqNo, hbCycle);
		logger.info("中间件返回retVal=" + retVal);
		if (ClientConstant.FrontProcessorReturnCode.SUCCESS.getCode() != retVal) {
			String desc = ClientConstant.FrontProcessorReturnCode.getDescByCode(retVal);
			result.error(desc);
		}
		return JSON.toJSONString(result);
	}


	@SystemControllerLog(description = "集中器心跳读取")
	@RequestMapping("/jzqHeartBeatRead")
	@ResponseBody
	public String jzqHeartBeatRead(@RequestParam String guid, @RequestParam String jzqNo) {
		Result result = new Result();
		// 参数校验
		if (StringUtils.isEmpty(guid)) {
			result.error("guid不能为空");
			return JSON.toJSONString(result);
		}
		// 校验集中器号
		boolean flag = this.checkParam(jzqNo, result);
		if (!flag) {
			return JSON.toJSONString(result);
		}

		int retVal = deviceInfoService.jzqHeartBeatRead(guid,jzqNo);
		logger.info("中间件返回retVal=" + retVal);
		if (ClientConstant.FrontProcessorReturnCode.SUCCESS.getCode() != retVal) {
			String desc = ClientConstant.FrontProcessorReturnCode.getDescByCode(retVal);
			result.error(desc);
		}
		return JSON.toJSONString(result);
	}

//	@SystemControllerLog(description = "集中器连接参数下发")
//	@RequestMapping("/jzqConnectParamSend")
//	@ResponseBody
//	public String jzqConnectParamSend(@Valid @RequestBody JzqEditConnectionParam param) {
//		Result result = new Result();
//		String deviceNo = param.getDeviceNo();
//		// 参数校验
//		boolean flag = this.checkParam(deviceNo, result);
//		if (!flag) {
//			return JSON.toJSONString(result);
//		}
//
//		JzqConnectParamSendVo sendVo = new JzqConnectParamSendVo();
//		BeanUtils.copyProperties(param,sendVo);
//		sendVo.setJzqNo(deviceNo);
//
//		int retVal = deviceInfoService.jzqConnectionParamSend(sendVo);
//		logger.info("中间件返回retVal=" + retVal);
//		if (ClientConstant.FrontProcessorReturnCode.SUCCESS.getCode() != retVal) {
//			String desc = ClientConstant.FrontProcessorReturnCode.getDescByCode(retVal);
//			result.error(desc);
//		}
//		return JSON.toJSONString(result);
//	}

	/**
	 * 参数校验
	 * @param jzqNo
	 * @param result
	 */
	private boolean checkParam(String jzqNo,Result result) {
		if (StringUtils.isEmpty(jzqNo)) {
			result.error("jzqNo不能为空");
			return false;
		}

		int length = jzqNo.length();
		if (length != 9) {
			result.error("jzqNo长度不正确");
			return false;
		}

		return true;
	}
	private String generateInfo(HttpSession session, DeviceInfoQueryParam realTimeDataParam) {
		String uuid = UuidUtil.getUuid();
		List list= Lists.newArrayList();
			Impot impot=new Impot();
			impot.setCreateTime(new Date());
		if("2".equals(realTimeDataParam.getNodeType())){
			impot.setDataType(1);
		}
		if("1".equals(realTimeDataParam.getNodeType())){
			impot.setDataType(3);
		}
			impot.setImpotBatch(uuid);
			impot.setSearchNo(realTimeDataParam.getNodeId());
			impot.setIsSearchChild(realTimeDataParam.getIsSearchChild());
			list.add(impot);
		String savePath = session.getServletContext().getRealPath("/WEB-INF/upload/" + uuid + ".csv");
		File saveCSV = new File(savePath);
		if (!saveCSV.getParentFile().exists()) {
			boolean result1 = saveCSV.getParentFile().mkdirs();
			if (!result1) {
				logger.error("创建文件异常");
			}
		}
		if(!saveCSV.exists()||!saveCSV.isFile()){
			try {
				saveCSV.createNewFile();
			} catch (IOException e) {
				logger.error("创建文件异常", e);
			}
		}


		generateFileService.generateRealTimeData(savePath, list);
		return uuid;
	}

}
