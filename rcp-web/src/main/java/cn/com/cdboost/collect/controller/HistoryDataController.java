package cn.com.cdboost.collect.controller;


import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.CollectAnalyzeDataForMonthInfo;
import cn.com.cdboost.collect.dto.CustomerData4Month;
import cn.com.cdboost.collect.dto.CustomerDeviceInfo;
import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.dto.param.CollectDataGetQueryParam;
import cn.com.cdboost.collect.dto.param.CollectDataGetQueryVo;
import cn.com.cdboost.collect.dto.param.ImpCollectDataGetQueryParam;
import cn.com.cdboost.collect.dto.param.ImpCollectDataGetQueryVo;
import cn.com.cdboost.collect.dto.response.CollectDataDownInfo;
import cn.com.cdboost.collect.dto.response.CollectDataGetInfo;
import cn.com.cdboost.collect.dto.response.ImpCollectDataGetInfo;
import cn.com.cdboost.collect.enums.Action;
import cn.com.cdboost.collect.enums.Auth;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.service.CustomerInfoService;
import cn.com.cdboost.collect.service.GenerateFileService;
import cn.com.cdboost.collect.service.HistoricalDataService;
import cn.com.cdboost.collect.service.UserLogService;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.util.DownLoadUtil;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/historyData")
public class HistoryDataController {
	private static final Logger logger = LoggerFactory.getLogger(HistoryDataController.class);

	@Autowired
	private HistoricalDataService historicalDataService;
	@Autowired
	private CustomerInfoService customerInfoService;
	@Autowired
	private GenerateFileService generateFileService;
	@Autowired
	private UserLogService userLogService;

	// 历史数据列表查询
	@Auth(menuID=10003502L,actionID=4L)
	@SystemControllerLog(description = "历史数据列表查询")
	@RequestMapping(value = "/queryList")
	@ResponseBody
	public String queryList(HttpSession session, @Valid @RequestBody CollectDataGetQueryParam queryParam) {
		PageResult<List<CollectDataGetInfo>> result = new PageResult<>();

		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		Long userId = Long.valueOf(currentUser.getId());

		CollectDataGetQueryVo queryVo = new CollectDataGetQueryVo();
		BeanUtils.copyProperties(queryParam,queryVo);
		queryVo.setUserId(userId);
		Integer isRealTime = queryParam.getIsRealTime();
		if (isRealTime == null) {
			queryVo.setIsRealTime("");
		} else {
			queryVo.setIsRealTime(String.valueOf(isRealTime));
		}
		queryVo.setSize(String.valueOf(queryParam.getPageSize()));
		queryVo.setStart(String.valueOf(queryParam.getPageNumber()));

		List<CollectDataGetInfo> infoList = historicalDataService.listHistoricalData(queryVo);
		result.setData(infoList);
		result.setTotal(queryVo.getTotal());
		return JSON.toJSONString(result);
	}

	// 重点用户，历史数据列表查询
	@Auth(menuID=10003501L,actionID=4L)
	@SystemControllerLog(description = "重点用户，历史数据列表查询")
	@RequestMapping(value = "/queryImportantList")
	@ResponseBody
	public String queryImportantList(HttpSession session, @Valid @RequestBody ImpCollectDataGetQueryParam queryParam) {
		PageResult<List<ImpCollectDataGetInfo>> result = new PageResult<>();
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		Long userId = Long.valueOf(currentUser.getId());

		ImpCollectDataGetQueryVo queryVo = new ImpCollectDataGetQueryVo();
		BeanUtils.copyProperties(queryParam,queryVo);
		queryVo.setUserId(userId);

		List<ImpCollectDataGetInfo> infoList = historicalDataService.listImpHistoricalData(queryVo);
		result.setData(infoList);
		result.setTotal(queryVo.getTotal());
		return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
	}

	@Auth(menuID=10003502L,actionID=6L)
	@SystemControllerLog(description = "历史数据生成excel")
	@RequestMapping("/generateExcel")
	public void generateExcel(HttpServletResponse response, HttpSession session, CollectDataGetQueryParam queryParam) {
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		userLogService.create(currentUser.getId(), Action.DOWNLOAD.getActionId(),"历史数据","deviceNo",queryParam.getDeviceNo(),"下载普通用户历史数据列表",JSON.toJSONString(queryParam));
		//获取userId
		Long userId = Long.valueOf(currentUser.getId());
		try {
			CollectDataGetQueryVo queryVo = new CollectDataGetQueryVo();
			BeanUtils.copyProperties(queryParam,queryVo);
			queryVo.setUserId(userId);
			queryVo.setSize(String.valueOf(queryParam.getPageSize()));
			queryVo.setStart(String.valueOf(queryParam.getPageNumber()));
			Integer isRealTime = queryParam.getIsRealTime();
			if (isRealTime == null) {
				queryVo.setIsRealTime("");
			} else {
				queryVo.setIsRealTime(String.valueOf(isRealTime));
			}
			queryVo.setEnd(String.valueOf(queryParam.getPageEndNum()));
			List<CollectDataDownInfo> dataDownInfos = historicalDataService.listHistoricalData4Download(queryVo);
			List<String> types = queryParam.getTypes();
			XSSFWorkbook workBook = generateFileService.generateHistoricalDataExcel("历史数据",types,dataDownInfos);
			DownLoadUtil.downExcel(response,workBook);
		} catch (Exception e) {
			logger.error("系统异常：",e);
		}
	}

	@Auth(menuID=10003501L,actionID=6L)
	@SystemControllerLog(description = "重点用户历史数据列表下载")
	@RequestMapping("/downloadImpHistoryData")
	public void downloadImpHistoryData(HttpServletResponse response, HttpSession session, ImpCollectDataGetQueryParam queryParam) {
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        userLogService.create(currentUser.getId(), Action.DOWNLOAD.getActionId(),"历史数据","deviceNo",queryParam.getDeviceNo(),"下载重点用户历史数据列表",JSON.toJSONString(queryParam));
        //获取userId
        Long userId = Long.valueOf(currentUser.getId());
        try {
            ImpCollectDataGetQueryVo queryVo = new ImpCollectDataGetQueryVo();
            BeanUtils.copyProperties(queryParam,queryVo);
            queryVo.setUserId(userId);
            //　最大只能下载10000条数据
			/*Integer pageSize = queryParam.getPageSize();
			if (pageSize > 10000) {
				queryVo.setPageSize(10000);
			}*/
            queryVo.setPageSize(queryParam.getPageSize());
            queryVo.setPageEndIndex(Integer.valueOf(queryParam.getPageEndNum()));
            List<ImpCollectDataGetInfo> infoList = historicalDataService.listImpHistoricalData(queryVo);
            List<String> types = queryParam.getTypes();
            XSSFWorkbook workBook = generateFileService.generateImpHistoricalDataExcel("历史数据", types, infoList);
            // 通过Response把数据以Excel格式保存
            response.reset();
            // 设置response流信息的头类型，MIME码
            response.setContentType("application/vnd.ms-excel");
            downExcel(response, workBook);
        } catch (Exception e) {
            logger.error("系统异常：",e);
        }

	}

	// 历史数据模块，明细页面用户基础信息查询
	@SystemControllerLog(description = "历史数据模块，明细页面用户基础信息查询")
	@RequestMapping(value = "/queryDetails")
	@ResponseBody
	public String queryDetails(@RequestParam String customerNo, @RequestParam String deviceCno) {
		Result<CustomerDeviceInfo> result = new Result<>();
		if (StringUtils.isEmpty(customerNo)) {
			result.error("customerNo不能为空");
			return JSON.toJSONString(result);
		}

		if (StringUtils.isEmpty(deviceCno)) {
			result.error("deviceCno不能为空");
			return JSON.toJSONString(result);
		}
		CustomerDeviceInfo customerDeviceInfo = customerInfoService.getCustomerDetails(customerNo,deviceCno);
		result.setData(customerDeviceInfo);
		return JSON.toJSONString(result);
	}

	// 历史数据、实时数据模块：明细页面，查询某年12个月份水电气数据
	@SystemControllerLog(description = "历史数据、实时数据模块：明细页面，查询某年12个月份水电气数据")
	@RequestMapping("/dataForYear")
	@ResponseBody
	public String dataForYear(HttpSession session, @RequestParam String customerNo, @RequestParam String deviceCno) {
		Result<CollectAnalyzeDataForMonthInfo> result = new Result<>();
		if (StringUtils.isEmpty(customerNo)) {
			result.error("customerNo不能为空");
			return JSON.toJSONString(result);
		}

		if (StringUtils.isEmpty(deviceCno)) {
			result.error("deviceCno不能为空");
			return JSON.toJSONString(result);
		}

		LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		long userId = user.getId();

		// 当前年每月的水电气总数
		CollectAnalyzeDataForMonthInfo monthInfo = historicalDataService.listCollectAnalyzeDataForMonth(userId, customerNo,deviceCno);
		result.setData(monthInfo);
		System.out.println(JSON.toJSONString(result));

		return JSON.toJSONString(result);
	}

	// 历史数据、实时数据模块：明细页面，查询某月水电气数据
	@SystemControllerLog(description = "历史数据、实时数据模块：明细页面，查询某月水电气数据")
	@RequestMapping(value = "/dataForMonth")
	@ResponseBody
	public String dataForMonth(@RequestParam String customerNo,
							   @RequestParam Integer yearMonth,
							   @RequestParam String deviceCno) {
		Result<List<CustomerData4Month>> result = new Result<>();
		if (StringUtils.isEmpty(customerNo)) {
			result.error("customerNo不能为空");
			return JSON.toJSONString(result);
		}

		if (StringUtils.isEmpty(deviceCno)) {
			result.error("deviceCno不能为空");
			return JSON.toJSONString(result);
		}
		List<CustomerData4Month> dataForMonth = historicalDataService.getDataForMonth(customerNo, deviceCno, yearMonth);
		result.setData(dataForMonth);

		return JSON.toJSONString(result);
	}

	// 历史数据模块：明细页面，某月水电气数据下载
	@Auth(menuID=10003502L,actionID=6L)
	@SystemControllerLog(description = "历史数据模块：明细页面，某月水电气数据下载")
	@RequestMapping("/downUserDetail")
	public void downUserDetail(HttpSession session, HttpServletResponse response,
							   @RequestParam String customerNo,
							   @RequestParam String deviceCno,
							   @RequestParam Integer yearMonth) {
		try {
			List<CustomerData4Month> dataForMonthList = historicalDataService.getDataForMonth(customerNo, deviceCno, yearMonth);
			String deviceType = deviceCno.substring(0,2);
			XSSFWorkbook workBook = generateFileService.generateData4MonthExcel("详情数据",deviceType, dataForMonthList);
			// 通过Response把数据以Excel格式保存
			response.reset();
			// 设置response流信息的头类型，MIME码
			response.setContentType("application/vnd.ms-excel");
			LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
			userLogService.create(currentUser.getId(), Action.OPEN_ICCARD.getActionId(),"历史数据下载","customerNo",customerNo,"下载设备["+ CNoUtil.getDeviceNoByCno(deviceCno)+"]"+String.valueOf(yearMonth).substring(4)+"月数据详情" , JSON.toJSONString(customerNo));
			downExcel(response, workBook);
		} catch (Exception e) {
			logger.error("系统异常：",e);
		}
	}

	public void downExcel(HttpServletResponse response, XSSFWorkbook workBook) throws IOException {
		ServletOutputStream out;
		response.addHeader("Content-Disposition", "attachment;filename=\""
                + new String(("导出表" + System.currentTimeMillis() + ".xls").getBytes("utf-8"), "ISO8859_1")
                + "\"");
		// 创建输出流对象
		out = response.getOutputStream();
		// 将创建的Excel对象利用二进制流的形式强制输出到客户端去
		workBook.write(out);
		// 强制将数据从内存中保存
		out.flush();
		out.close();
	}

}
