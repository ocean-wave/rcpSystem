package cn.com.cdboost.collect.controller;


import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.ArrearageCustomerQueryParam;
import cn.com.cdboost.collect.dto.param.ArrearageCustomersQueryVo;
import cn.com.cdboost.collect.dto.response.StaticCountInfo;
import cn.com.cdboost.collect.enums.Action;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.service.FirstPageService;
import cn.com.cdboost.collect.service.GenerateFileService;
import cn.com.cdboost.collect.service.HistoricalDataService;
import cn.com.cdboost.collect.service.UserLogService;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/first")
public class FirstController {
	private static final Logger logger = LoggerFactory.getLogger(FirstController.class);

	@Autowired
	private HistoricalDataService historicalDataService;
	@Autowired
	private UserLogService userLogService;
	@Autowired
	private GenerateFileService generateFileService;
	@Autowired
	private FirstPageService firstPageService;

	/**
	 * 首页分页查询欠费用户信息
	 * @param session
	 * @param queryParam
	 * @return
	 */
	@SystemControllerLog(description = "分页查询欠费用户信息")
	@RequestMapping("/arrearageCustomers")
	@ResponseBody
	public String arrearageCustomers(HttpSession session, @Valid @RequestBody ArrearageCustomerQueryParam queryParam) {
		PageResult<List<ArrearageCustomer>> result = new PageResult<>();
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		ArrearageCustomersQueryVo queryVo = new ArrearageCustomersQueryVo();
		BeanUtils.copyProperties(queryParam,queryVo);
		queryVo.setUserId(Long.valueOf(currentUser.getId()));
		List<ArrearageCustomer> listAC = historicalDataService.listArrearageCustomers(queryVo);
		result.setData(listAC);
		result.setTotal(queryVo.getTotal());

		return JSON.toJSONString(result);
	}

	// 获取报警数量
	@SystemControllerLog(description = "获取报警数量")
	@RequestMapping("/alarmUserCnt")
	@ResponseBody
	public String getAlarmUserCnt(HttpSession session) {
		Result<Integer> result = new Result<>();
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		int alarmUserCount = historicalDataService.getAlarmUserCount(Long.valueOf(currentUser.getId()));
		result.setData(alarmUserCount);

		return JSON.toJSONString(result);
	}

	/**
	 * 下载告警数据列表
	 * @param response
	 * @param session
	 * @param queryParam
	 */
	@SystemControllerLog(description = "下载告警数据列表")
	@RequestMapping("/generateExcel")
	public void generateExcel(HttpServletResponse response, HttpSession session, ArrearageCustomerQueryParam queryParam) {
		LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		userLogService.create(user.getId(), Action.DOWNLOAD.getActionId(),"告警数据","","","下载告警数据", JSON.toJSONString(queryParam));
		try {
			ArrearageCustomersQueryVo queryVo = new ArrearageCustomersQueryVo();
			BeanUtils.copyProperties(queryParam,queryVo);
			queryVo.setUserId(Long.valueOf(user.getId()));

			List<ArrearageCustomer> list = historicalDataService.listArrearageCustomers(queryVo);

			XSSFWorkbook workBook = generateFileService.generateAlarmExcel("告警数据", list);
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

	/**
	 * 商户信息模块：总数，告警，拉闸，异常数据统计
	 * 告警数据，后端返回一直是0，前端直接从alarmUserCnt接口获取
	 * @param session
	 * @return
	 */
	@SystemControllerLog(description = "商户信息模块：总数，告警，拉闸，异常数据统计")
	@RequestMapping(value = "pageStaticCount", method = RequestMethod.POST)
	@ResponseBody
	public String pageStaticCount(HttpSession session) {
		Result<StaticCountInfo> result = new Result<>();
		LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		StaticCountInfo info = firstPageService.pageStaticCount(Long.valueOf(user.getId()));
		result.setData(info);
		return JSON.toJSONString(result);
	}

	/**
	 * 分类用能情况模块：汇总当月的电、水、气的使用量
	 * @param session
	 * @return
	 */
	@SystemControllerLog(description = "分类用能情况模块：汇总当月的电、水、气的使用量")
	@RequestMapping(value = "pageSumDataGet", method = RequestMethod.POST)
	@ResponseBody
	public String pageSumDataGet(HttpSession session) {
		Result<PageSumDataGetInfo> result = new Result<>();
		LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		PageSumDataGetInfo info = firstPageService.pageSumDataGet(Long.valueOf(user.getId()));
		result.setData(info);
		return JSON.toJSONString(result);
	}

	/**
	 * 剩余金额比重情况模块：饼图数据查询
	 * @param session
	 * @param deviceType
	 * @return
	 */
	@SystemControllerLog(description = "剩余金额比重情况模块：饼图数据查询")
	@RequestMapping(value = "pageAnalAmount", method = RequestMethod.POST)
	@ResponseBody
	public String pageAnalAmount(HttpSession session, @RequestParam String deviceType) {
		Result<AnalAmountDTO> result = new Result<>();
		if (StringUtils.isEmpty(deviceType)) {
			result.error("deviceType不能为空");
			return JSON.toJSONString(result);
		}
		LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		AnalAmountDTO analAmountDTO = firstPageService.pageAnalAmount(Long.valueOf(user.getId()), deviceType);
		result.setData(analAmountDTO);
		return JSON.toJSONString(result);
	}


	/**
	 * 用能模块：查询去年和今年每月的电、水、气的使用量
	 * @param session
	 * @return
	 */
	@SystemControllerLog(description = "用能模块：查询去年和今年每月的电、水、气的使用量")
	@RequestMapping(value = "sumGetForMonth", method = RequestMethod.POST)
	@ResponseBody
	public String sumGetForMonth(HttpSession session, String customerNo, @RequestParam String deviceType) {
		Result<GetForYearInfo> result = new Result<>();
		if (StringUtils.isEmpty(deviceType)) {
			result.error("deviceType不能为空");
			return JSON.toJSONString(result);
		}
		LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		GetForYearInfo yearInfo = firstPageService.sumGetForMonth(customerNo, deviceType, Long.valueOf(user.getId()));
		result.setData(yearInfo);
		return JSON.toJSONString(result);
	}


	/**
	 * 用能模块：汇总当月水电气量和上月水电气表的数据及环比
	 * @param session
	 * @return
	 */
	@SystemControllerLog(description = "用能模块：汇总当月水电气量和上月水电气表的数据及环比")
	@RequestMapping(value = "pageSumMonthPer", method = RequestMethod.POST)
	@ResponseBody
	public String pageSumMonthPer(HttpSession session, @RequestParam String deviceType) {
		Result<SumMonthPerInfo> result = new Result<>();
		if (StringUtils.isEmpty(deviceType)) {
			result.error("deviceType不能为空");
			return JSON.toJSONString(result);
		}
		LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		SumMonthPerInfo info = firstPageService.pageSumMonthPer(Long.valueOf(user.getRoleId()), deviceType);
		result.setData(info);

		return JSON.toJSONString(result);
	}

}
