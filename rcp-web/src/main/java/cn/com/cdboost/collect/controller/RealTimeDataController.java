package cn.com.cdboost.collect.controller;


import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.constant.ClientConstant;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.BroadcastResult;
import cn.com.cdboost.collect.dto.response.MeterCollectDataFailInfo;
import cn.com.cdboost.collect.enums.Action;
import cn.com.cdboost.collect.enums.Auth;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.model.CustomerInfo;
import cn.com.cdboost.collect.model.Impot;
import cn.com.cdboost.collect.model.SummryData;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.util.UuidUtil;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static cn.com.cdboost.collect.util.DownLoadUtil.downExcel;

@Controller
@RequestMapping(value = "/realTimeData",produces={"application/json;","text/html;charset=UTF-8;"})
public class RealTimeDataController {
	private static final Logger logger = LoggerFactory.getLogger(RealTimeDataController.class);

	@Autowired
	private RealTimeDataService realTimeDataService;
	@Autowired
	private GenerateFileService generateFileService;
	@Autowired
	private UserLogService userLogService;
	@Autowired
	private MeterDayPowerService meterDayPowerService;
    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private HistoricalDataService historicalDataService;

    // 查询用户明细
    @SystemControllerLog(description = "实时数据，查询用户明细")
    @RequestMapping(value = "/queryDetails")
    @ResponseBody
    public String queryDetails(@RequestParam String customerNo, @RequestParam String deviceCno) {
        return getString(customerNo, deviceCno, customerInfoService.getCustomerDetails(customerNo, deviceCno));
    }
    // 实时数据模块：明细页面，某月水电气数据下载
    // 实时数据模块：明细页面，查询某月水电气数据
    @SystemControllerLog(description = "实时数据模块：明细页面，查询某月水电气数据")
    @RequestMapping(value = "/queryListDetails")
    @ResponseBody
    public String queryListDetails(@RequestBody @Valid RealTimeDataListParam realTimeDataListParam) {
        PageResult<List<CustomerData4Month>> result = new PageResult<>();

        List<CustomerData4Month> dataForMonth = historicalDataService.getDataList(realTimeDataListParam);
        result.setData(dataForMonth);
        result.setTotal(Long.valueOf(realTimeDataListParam.getTotal()));
        return JSON.toJSONString(result);
    }

	// 实时数据模块：明细页面，查询某月水电气数据
	@SystemControllerLog(description = "实时数据模块：重点用户明细页面，查询某月水电气数据")
	@RequestMapping(value = "/queryImpListDetails")
	@ResponseBody
	public String queryImpListDetails(@RequestBody @Valid RealTimeDataListParam realTimeDataListParam) {
		Result<List<ImportantCurveDerailDTO>> result = new Result<>();

		List<ImportantCurveDerailDTO> dataForMonth = historicalDataService.getImpDataList(realTimeDataListParam);
		result.setData(dataForMonth);

		return JSON.toJSONString(result);
	}

	// 实时数据模块：明细页面，某月水电气数据下载
	@Auth(menuID=100012L,actionID=6L)
	@SystemControllerLog(description = "实时数据模块：明细页面，某月水电气数据下载")
	@RequestMapping("/downUserDetail")
	public void downUserDetail(HttpSession session,  HttpServletResponse response, RealTimeDataListParam realTimeDataListParam ) {
		try {
			List<CustomerData4Month> dataForMonthList = historicalDataService.getDataList(realTimeDataListParam);

			XSSFWorkbook workBook = generateFileService.generateData4MonthExcel("详情数据", dataForMonthList,realTimeDataListParam.getCustomerNo());
			// 通过Response把数据以Excel格式保存
			response.reset();
			// 设置response流信息的头类型，MIME码
			response.setContentType("application/vnd.ms-excel");
			LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
			CustomerInfo customerInfo = customerInfoService.queryByCustomerNo(realTimeDataListParam.getCustomerNo());
			userLogService.create(currentUser.getId(), Action.OPEN_ICCARD.getActionId(),"实时数据下载","customerNo",customerInfo.getCustomerNo(),"下载用户["+ customerInfo.getCustomerName()+"]"+realTimeDataListParam.getStartDate()+"至"+realTimeDataListParam.getEndDate()+"数据详情" , JSON.toJSONString(realTimeDataListParam));
			downExcel(response, workBook);
		} catch (Exception e) {
			logger.error("系统异常：",e);
		}
	}


	@SystemControllerLog(description = "实时数据模块：汇总数据")
	@RequestMapping("/summaryData")
	@ResponseBody
	public String summaryData(
			@RequestParam String deviceCno,
			@RequestParam String customerNo
	) throws ParseException {

		Result result=new Result();
		SummryData meterDayPower = meterDayPowerService.querydatabyCustomerCno(deviceCno, customerNo);
		result.setData(meterDayPower);
		return  JSON.toJSONString(result);

	}
	/**
	 * 实时数据模块：实时数据列表查询
	 * @param session
	 * @param queryParam
	 * @return
	 */
	@Auth(menuID=10001101L,actionID=4L)
	@SystemControllerLog(description = "实时数据模块：实时数据列表查询")
	@RequestMapping("/queryList")
	@ResponseBody
	public String queryListOld(HttpSession session, @Valid @RequestBody RealTimeDataQueryParam queryParam) {
		PageResult<List<MeterCollectDataListInfo>> result = new PageResult<>();
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		// 组织查询参数
		RealMeterCollectQueryVo queryVo = new RealMeterCollectQueryVo();
		BeanUtils.copyProperties(queryParam,queryVo);
		queryVo.setUserId(currentUser.getId());
		Integer isOnline = queryParam.getIsOnline();
		if (isOnline != null) {
			queryVo.setIsOnline(String.valueOf(isOnline));
		}

		// 查询
		List<MeterCollectDataListInfo> dataListInfos = realTimeDataService.listRealTimeData(queryVo);
		result.setData(dataListInfos);
		result.setTotal(queryVo.getTotal());

		return JSON.toJSONString(result);
	}

    /**
     * 实时数据模块：实时数据列表查询
     *
     * @param session
     * @param
     * @return
     */
    @Auth(menuID = 10001101L, actionID = 4L)
    @SystemControllerLog(description = "实时数据模块：实时数据列表查询")
    @RequestMapping("/queryListNew")
    @ResponseBody
    public String queryListNew(HttpSession session, @Valid @RequestBody RealTimeNewParam queryParam) {
		long start = System.currentTimeMillis();
        PageResult<List<MeterCollectDataListInfo>> result = new PageResult<>();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        // 组织查询参数
		List<MeterCollectDataListInfo> dataListInfos;
		RealMeterCollectQueryNewVo queryVo = new RealMeterCollectQueryNewVo();
        BeanUtils.copyProperties(queryParam,queryVo);
        queryVo.setUserId(currentUser.getId());
        Integer isOnline = queryParam.getIsOnline();
        if (isOnline != null) {
            queryVo.setIsOnline(String.valueOf(isOnline));
        }
		String uuid = UuidUtil.getUuid();
        Integer integer = generateInfo(uuid,session, queryParam);
		queryVo.setImportGuid(uuid);
		if (integer!=1){
			queryVo.setImportGuid("");
		}
        // 查询
		dataListInfos = realTimeDataService.listRealTimeDataNew(queryVo);
        result.setData(dataListInfos);
        result.setTotal(queryVo.getTotal());
		long end = System.currentTimeMillis();
		logger.info("实时列表查询结束，耗时{}秒 ", (end - start) / 1000);
        return JSON.toJSONString(result);
	}



    /**
	 * 实时数据模块：召测时弹出框，查询召测成功的列表
	 * @param session
	 * @param queryParam
	 * @return
	 */
	@Auth(menuID=10001101L,actionID=5L)
	@SystemControllerLog(description = "实时数据模块：召测时弹出框，查询召测成功的列表")
	@RequestMapping("/querySuccessList")
	@ResponseBody
	public String querySuccessList(HttpSession session, @Valid @RequestBody RealTimeDataSuccessQueryParam queryParam) {
		PageResult<List<MeterCollectDataListInfo>> result = new PageResult<>();

		RealMeterCollectQueryNewVo queryVo = new RealMeterCollectQueryNewVo();
		BeanUtils.copyProperties(queryParam,queryVo);
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		queryVo.setUserId(currentUser.getId());
		Integer isOnline = queryParam.getIsOnline();
		if (isOnline != null) {
			queryVo.setIsOnline(String.valueOf(isOnline));
		}
		queryVo.setImportGuid("");
		List<MeterCollectDataListInfo> list = realTimeDataService.listRealTimeDataNew(queryVo);
		result.setData(list);
		result.setTotal(queryVo.getTotal());

		return JSON.toJSONString(result);
	}

	/**
	 * 实时数据模块：召测时弹出框，查询召测失败的列表
	 * @param param
	 * @return
	 */
	@Auth(menuID=10001101L,actionID=5L)
	@SystemControllerLog(description = "实时数据模块：召测时弹出框，查询召测失败的列表")
	@RequestMapping("/queryFailList")
	@ResponseBody
	public String queryFailList(@Valid @RequestBody RealTimeDataFailQueryParam param) {
		PageResult<List<MeterCollectDataFailInfo>> result = new PageResult<>();
		RealMeterCollectFailQueryVo queryVo = new RealMeterCollectFailQueryVo();
		BeanUtils.copyProperties(param,queryVo);

		List<MeterCollectDataFailInfo> dataFailInfos = realTimeDataService.listRealTimeFailData(queryVo);
		result.setData(dataFailInfos);
		result.setTotal(queryVo.getTotal());
		return JSON.toJSONString(result);
	}

	/**
	 * 实时数据模块：重点用户列表查询
	 * @param
	 * @param
	 * @return
	 */
	@Auth(menuID=10001102L,actionID=4L)
	@SystemControllerLog(description = "实时数据模块：重点用户列表查询")
	@RequestMapping("/queryImportantListNew")
	@ResponseBody
	public String queryImportantListNew(HttpSession session, @Valid @RequestBody RealTimeNewParam queryParam) {
		long start = System.currentTimeMillis();
		PageResult<List<CollectImportantDataListInfo>> result = new PageResult<>();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        // 组织查询参数
		List<CollectImportantDataListInfo> infos;
        RealMeterCollectQueryNewVo queryVo = new RealMeterCollectQueryNewVo();
        BeanUtils.copyProperties(queryParam,queryVo);
        queryVo.setUserId(currentUser.getId());
        Integer isOnline = queryParam.getIsOnline();
        if (isOnline != null) {
            queryVo.setIsOnline(String.valueOf(isOnline));
        }
		String uuid = UuidUtil.getUuid();
		Integer integer = generateInfo(uuid, session, queryParam);
		queryVo.setImportGuid(uuid);
		if (integer!=1){
			queryVo.setImportGuid("");
		}
        // 查询
		infos = realTimeDataService.listImpRealTimeDataNew(queryVo);
        result.setData(infos);
        result.setTotal(queryVo.getTotal());
		long end = System.currentTimeMillis();
		logger.info("重点用户列表查询完毕，耗时{}秒 ", (end - start) / 1000);
        return JSON.toJSONString(result);
	}

    /**
     * 实时数据模块：重点用户列表查询
     * @param session
     * @param queryParam
     * @return
     */
    @Auth(menuID=10001102L,actionID=4L)
    @SystemControllerLog(description = "实时数据模块：重点用户列表查询")
    @RequestMapping("/queryImportantList")
    @ResponseBody
    public String queryImportantListold(HttpSession session, @Valid @RequestBody RealTimeDataQueryParam queryParam) {
        PageResult<List<CollectImportantDataListInfo>> result = new PageResult<>();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        // 组织查询参数
        RealMeterCollectQueryVo queryVo = new RealMeterCollectQueryVo();
        BeanUtils.copyProperties(queryParam,queryVo);
        queryVo.setUserId(currentUser.getId());
        Integer isOnline = queryParam.getIsOnline();
        if (isOnline != null) {
            queryVo.setIsOnline(String.valueOf(isOnline));
        }

        // 查询
        List<CollectImportantDataListInfo> infos = realTimeDataService.listImpRealTimeData(queryVo);
        result.setData(infos);
        result.setTotal(queryVo.getTotal());
        return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
    }
	/**
	 * 实时数据模块：重点用户列表查询
	 * @param session
	 * @param queryParam
	 * @return
	 */
	@Auth(menuID=10001102L,actionID=4L)
	@SystemControllerLog(description = "实时数据模块：重点用户列表查询")
	@RequestMapping("/queryImportantSuccessList")
	@ResponseBody
	public String queryImportantSuccessList(HttpSession session, @Valid @RequestBody CollectDataSuccessQueryParam queryParam) {
		PageResult<List<CollectImportantDataListInfo>> result = new PageResult<>();
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		// 组织查询参数
		RealMeterCollectQueryNewVo queryVo = new RealMeterCollectQueryNewVo();
		BeanUtils.copyProperties(queryParam,queryVo);
		queryVo.setUserId(currentUser.getId());
		// 查询
		queryVo.setImportGuid("");
		List<CollectImportantDataListInfo> infos = realTimeDataService.listImpRealTimeDataNew(queryVo);
		result.setData(infos);
		result.setTotal(queryVo.getTotal());
		return JSON.toJSONString(result);
	}

	// 实时数据模块：重点用户列表数据下载
	@Auth(menuID=10001102L,actionID=6L)
	@SystemControllerLog(description = "实时数据模块：重点用户列表数据下载")
	@RequestMapping("/downloadImportantList")
	public void downloadImportantList(HttpServletResponse response, HttpSession session, RealTimeNewParam queryParam) {
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		userLogService.create(currentUser.getId(), Action.DOWNLOAD.getActionId(),"实时数据","deviceNo",queryParam.getDeviceNo(),"下载重点用户实时数据列表",JSON.toJSONString(queryParam));

		try {
			// 设置查询参数
			RealMeterCollectQueryNewVo queryVo = new RealMeterCollectQueryNewVo();
			BeanUtils.copyProperties(queryParam,queryVo);
			queryVo.setUserId(currentUser.getId());
			Integer isOnline = queryParam.getIsOnline();
			if (isOnline != null) {
				queryVo.setIsOnline(String.valueOf(isOnline));
			}
			Integer pageSize = queryParam.getPageSize();
			// 最大只能下载10000条数据
			pageSize = pageSize > 10000 ? 10000:pageSize;
			queryVo.setPageSize(pageSize);
			String uuid = UuidUtil.getUuid();
			queryVo.setImportGuid(uuid);
			Integer integer = generateInfo(uuid, session, queryParam);
			if(integer!=1){
				queryVo.setImportGuid("");
			}
			List<CollectImportantDataListInfo> infos = realTimeDataService.listImpRealTimeDataNew(queryVo);
			List<String> types = queryParam.getTypes();
				XSSFWorkbook workBook = generateFileService.generateImpRealTimeDataExcel("重点用户实时数据",types,infos);
				//通过Response把数据以Excel格式保存
				response.reset();
				//设置response流信息的头类型，MIME码
				response.setContentType("application/vnd.ms-excel");
				ServletOutputStream out;
				response.addHeader("Content-Disposition", "attachment;filename=\""
						+ new String(("导出表" + System.currentTimeMillis() + ".xls").getBytes("utf-8"),
						"ISO8859_1") + "\"");
				//创建输出流对象
				out=response.getOutputStream();
				//将创建的Excel对象利用二进制流的形式强制输出到客户端去
				workBook.write(out);
				//强制将数据从内存中保存
				out.flush();
				out.close();
		} catch (Exception e) {
			logger.error("系统异常：",e);
		}
	}

	// 实时数据模块：重点用户实时召测弹出框中，成功数据列表下载
	@Auth(menuID=10001102L,actionID=6L)
	@SystemControllerLog(description = "实时数据模块：重点用户实时召测弹出框中，成功数据列表下载")
	@RequestMapping("/downloadImpSuccessList")
	public void downloadImpSuccessList(HttpServletResponse response, HttpSession session, PopupDownloadQueryParam queryParam) {
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		userLogService.create(currentUser.getId(), Action.DOWNLOAD.getActionId(),"实时数据","guid",queryParam.getGuid(),"下载重点用户实时召测成功列表数据",JSON.toJSONString(queryParam) );
		try {
			Integer flag = queryParam.getFlag();
			// 下载召测成功的列表
			if(flag.intValue() == 1){
				RealMeterCollectQueryVo queryVo = new RealMeterCollectQueryVo();
				BeanUtils.copyProperties(queryParam,queryVo);
				queryVo.setUserId(currentUser.getId());
				queryVo.setPageNumber(1);
				queryVo.setPageSize(1000);

				List<CollectImportantDataListInfo> infos = realTimeDataService.listImpRealTimeData(queryVo);
				List<String> types = queryParam.getTypes();
				XSSFWorkbook workBook = generateFileService.generateImpRealTimeDataExcel("重点用户实时数据成功列表", types,infos);
				//通过Response把数据以Excel格式保存
				response.reset();
				//设置response流信息的头类型，MIME码
				response.setContentType("application/vnd.ms-excel");
				ServletOutputStream out = null;
				response.addHeader("Content-Disposition", "attachment;filename=\""
						+ new String(("导出表" + System.currentTimeMillis() + ".xls").getBytes("utf-8"),
						"ISO8859_1") + "\"");
				//创建输出流对象
				out=response.getOutputStream();
				//将创建的Excel对象利用二进制流的形式强制输出到客户端去
				workBook.write(out);
				//强制将数据从内存中保存
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			logger.error("系统异常：",e);
		}
	}

	// 实时数据模块：列表数据下载
	@Auth(menuID=10001101L,actionID=6L)
	@SystemControllerLog(description = "实时数据模块：列表数据下载")
	@RequestMapping("/generateExcel")
	public void generateExcel(HttpServletResponse response, HttpSession session, RealTimeNewParam queryParam) {
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		userLogService.create(currentUser.getId(), Action.DOWNLOAD.getActionId(),"实时数据","jzqNo",queryParam.getJzqNo(),"下载普通用户实时数据列表",JSON.toJSONString(queryParam));

		try {
			// 设置查询参数
			RealMeterCollectQueryNewVo queryVo = new RealMeterCollectQueryNewVo();
			BeanUtils.copyProperties(queryParam,queryVo);
			queryVo.setUserId(currentUser.getId());
			Integer isOnline = queryParam.getIsOnline();
			if (isOnline != null) {
				queryVo.setIsOnline(String.valueOf(isOnline));
			}
			String uuid = UuidUtil.getUuid();
			Integer integer = generateInfo(uuid, session, queryParam);
			queryVo.setImportGuid(uuid);
			if(integer!=1){
				queryVo.setImportGuid("");
			}
				List<MeterCollectDataListInfo> dataListInfos = realTimeDataService.listRealTimeDataNew(queryVo);
			List<String> types = queryParam.getTypes();
				XSSFWorkbook workBook = generateFileService.generateRealTimeDataExcel("实时数据",types, dataListInfos);
				//通过Response把数据以Excel格式保存

				//设置response流信息的头类型，MIME码
				response.setContentType("application/vnd.ms-excel");

				response.addHeader("Content-Disposition", "attachment;filename=\""
						+ new String(("导出表" + System.currentTimeMillis() + ".xls").getBytes("utf-8"),
						"ISO8859_1") + "\"");
				//创建输出流对象
				ServletOutputStream out;
				out=response.getOutputStream();
				//将创建的Excel对象利用二进制流的形式强制输出到客户端去
				workBook.write(out);
				//强制将数据从内存中保存
				out.flush();
				out.close();
		} catch (Exception e) {
			logger.error("系统异常：",e);
		}

	}

	/**
	 * 批量发送采集指令
	 * @param session
	 * @param queryParam
	 * @return
	 */
	@SystemControllerLog(description = "批量发送采集指令")
	@RequestMapping("/batchSendCollectInstructions")
	@ResponseBody
	public String batchSendCollectInstructions(HttpSession session, @Valid @RequestBody BatchSendCollectQueryParam queryParam) {
		Result<String> result = new Result<>();
		session.setAttribute(GlobalConstant.COLLECT_DATA_STOP_FLAG, false);
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		// 发送抄表指令
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		Date date =  calendar.getTime();
		result.setData(sdf.format(date));

		SendRealCollectQueryParam param = new SendRealCollectQueryParam();
		param.setUserId(currentUser.getId());
		param.setDate(date);
		param.setGuid(queryParam.getGuid());
		param.setIsImportant(queryParam.getIsImportant());
		param.setMeters(queryParam.getMeters());
		param.setTypes(queryParam.getTypes());
		String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
		param.setSessionId(sessionId);
		int status = realTimeDataService.sendRealCollectList(param);
		logger.info("status = " + status);
		if (status != 1) {
			if(status == -20000) {
				result.error("设备不支持所选择的抄表项");
			} else {
				result.error("中间件指令发送失败");
			}
		}

		return JSON.toJSONString(result);
	}

	// 实时数据模块：实时召测弹出框中，成功数据列表下载
	@Auth(menuID=10001101L,actionID=6L)
	@SystemControllerLog(description = "实时数据模块：实时召测弹出框中，成功数据列表下载")
	@RequestMapping("/generateStatusExcel")
	public void generateStatusExcel(HttpServletResponse response, HttpSession session, PopupDownloadQueryParam queryParam) {
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		userLogService.create(currentUser.getId(), Action.DOWNLOAD.getActionId(),"实时数据","guid",queryParam.getGuid(),"下载实时召测数据",JSON.toJSONString(queryParam) );
		try {
			Integer flag = queryParam.getFlag();
			// 下载召测成功的列表
			if(flag.intValue() == 1){
				RealMeterCollectQueryVo queryVo = new RealMeterCollectQueryVo();
				BeanUtils.copyProperties(queryParam,queryVo);
				queryVo.setUserId(currentUser.getId());
				queryVo.setPageNumber(1);
				queryVo.setPageSize(1000);

				List<MeterCollectDataListInfo> list = realTimeDataService.listRealTimeData(queryVo);
				List<String> types = queryParam.getTypes();
				XSSFWorkbook workBook = generateFileService.generateRealTimeDataExcel("实时数据",types, list);
				//通过Response把数据以Excel格式保存
				response.reset();
				//设置response流信息的头类型，MIME码
				response.setContentType("application/vnd.ms-excel");
				ServletOutputStream out = null;
				response.addHeader("Content-Disposition", "attachment;filename=\""
						+ new String(("导出表" + System.currentTimeMillis() + ".xls").getBytes("utf-8"),
						"ISO8859_1") + "\"");
				//创建输出流对象
				out=response.getOutputStream();
				//将创建的Excel对象利用二进制流的形式强制输出到客户端去
				workBook.write(out);
				//强制将数据从内存中保存
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			logger.error("系统异常：",e);
		}
	}

	/**
	 * 停止批量抄表
	 * @param session
	 * @param guid
	 * @return
	 */
	@SystemControllerLog(description = "停止批量抄表")
	@RequestMapping("/stopCollectList")
	@ResponseBody
	public String stopCollectList(HttpSession session, @RequestParam String guid) {
		Result<Boolean> result = new Result<>();
		session.setAttribute(GlobalConstant.COLLECT_DATA_STOP_FLAG, true);
		if (StringUtils.isEmpty(guid)) {
			result.error("guid不能为空");
			return JSON.toJSONString(result);
		}
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		userLogService.create(currentUser.getId(), Action.CALL.getActionId(),"实时数据","guid",guid,"停止批量抄表", guid);
		boolean b = realTimeDataService.StopCollectList(guid);
		result.setData(b);

		return JSON.toJSONString(result);
	}


	@SystemControllerLog(description = "重点用户广播指令")
	@RequestMapping("/broadcast")
	@ResponseBody
	public String broadcast(HttpSession session, @RequestParam String guid) {
		BroadcastResult result = new BroadcastResult();
		session.setAttribute(GlobalConstant.BROADCAST_STOP_FLAG, false);
		if (StringUtils.isEmpty(guid)) {
			result.error("guid不能为空");
			return JSON.toJSONString(result);
		}
		// 发送广播指令时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		Date date =  calendar.getTime();
		result.setDate(sdf.format(date));

		String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
		int retVal = realTimeDataService.broadcast(guid, sessionId);
		result.setState(retVal);
		if (retVal != ClientConstant.FrontProcessorReturnCode.SUCCESS.getCode()) {
			result.error(ClientConstant.FrontProcessorReturnCode.getDescByCode(retVal));
		}
		return JSON.toJSONString(result);
	}

	@SystemControllerLog(description = "重点用户广播指令，前端关闭")
	@RequestMapping("/broadcastStop")
	@ResponseBody
	public String broadcastStop(HttpSession session, @RequestParam String guid) {
		Result<Boolean> result = new Result<>();
		session.setAttribute(GlobalConstant.BROADCAST_STOP_FLAG, true);
		if (StringUtils.isEmpty(guid)) {
			result.error("guid不能为空");
			return JSON.toJSONString(result);
		}
		boolean b = realTimeDataService.broadcastStop(guid);
		logger.info("广播停止返回：" + b);
		result.setData(b);
		return JSON.toJSONString(result);
	}
    private Integer generateInfo(String uuid,HttpSession session, RealTimeNewParam realTimeDataParam) {
        List<String> customerNoList = realTimeDataParam.getCustomerNoList();
        List<String> deviceNoList = realTimeDataParam.getDeviceNoList();
        List nolist = null;
        if (!CollectionUtils.isEmpty(realTimeDataParam.getCustomerNoList())){
			List list= Lists.newArrayList();
			for (String customerNo : customerNoList) {
				Impot impot=new Impot();
				impot.setCreateTime(new Date());
				impot.setDataType(2);
				impot.setImpotBatch(uuid);
				impot.setSearchNo(customerNo);
				impot.setIsSearchChild(realTimeDataParam.getIsSearchChild());
				list.add(impot);
			}
			nolist=list;
        }
        if (!CollectionUtils.isEmpty(deviceNoList)){
            List list= Lists.newArrayList();
            for (String s : deviceNoList) {
                Impot impot=new Impot();
                impot.setCreateTime(new Date());
                impot.setDataType(1);
                impot.setImpotBatch(uuid);
                String cNo = CNoUtil.CreateCNo(realTimeDataParam.getDeviceType(), s);
                impot.setSearchNo(cNo);
                impot.setIsSearchChild(realTimeDataParam.getIsSearchChild());
                list.add(impot);
            }
            nolist=list;
        }
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
        if(!CollectionUtils.isEmpty(nolist)){
			return generateFileService.generateRealTimeData(savePath, nolist);
		}else{
        	return 0;
		}

	}

    static String getString(@RequestParam String customerNo, @RequestParam String deviceCno, CustomerDeviceInfo customerDetails) {
        Result<CustomerDeviceInfo> result = new Result<>();
        if (StringUtils.isEmpty(customerNo)) {
            result.error("customerNo不能为空");
            return JSON.toJSONString(result);
        }

        if (StringUtils.isEmpty(deviceCno)) {
            result.error("deviceCno不能为空");
            return JSON.toJSONString(result);
        }
        CustomerDeviceInfo customerDeviceInfo = customerDetails;
        result.setData(customerDeviceInfo);
        return JSON.toJSONString(result);
    }
}