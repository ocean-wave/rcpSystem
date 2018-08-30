package cn.com.cdboost.collect.controller;


import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.dto.ServiceHostPara;
import cn.com.cdboost.collect.dto.SmsConfigPara;
import cn.com.cdboost.collect.dto.SysConfigPara;
import cn.com.cdboost.collect.dto.param.SystemConfigParam;
import cn.com.cdboost.collect.dto.param.SystemConfigParamVo;
import cn.com.cdboost.collect.dto.response.SystemConfigInfo;
import cn.com.cdboost.collect.enums.Action;
import cn.com.cdboost.collect.enums.Auth;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.service.SysConfigService;
import cn.com.cdboost.collect.service.UserLogService;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author linyang
 * @date 2017年6月6日
 */
@Controller
@RequestMapping("/system")
public class SystemConfigController {

	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private UserLogService userLogService;

	@Value("${version}")
	private String version;


	@SystemControllerLog(description = "查询系统配置信息")
	@RequestMapping("/querySystemConfig")
	@ResponseBody
	public String querySystemConfig() {
		Result<SystemConfigInfo> result = new Result<>();
		SystemConfigInfo configInfo = new SystemConfigInfo();
		// 前置机状态
		configInfo.setFrontProcessorStatus(GlobalConstant.FRONT_PROCESSOR_STATUS);
		ServiceHostPara hostPara = sysConfigService.getServiceHostPara();
		if (hostPara != null) {
			configInfo.setFrontProcessorIp(hostPara.getIp());
			configInfo.setFrontProcessorPort(hostPara.getPort());
			configInfo.setApn(hostPara.getApn());
		}

		SysConfigPara sysConfig = sysConfigService.getSysConfigPara();
		if (sysConfig != null) {
			configInfo.setSysName(sysConfig.getName());
			configInfo.setBackground(sysConfig.getBackgroundPicPath());
			configInfo.setBalanceDate(sysConfig.getBalanceDate());
			configInfo.setVersion(version);
		}
		//短信参数
		SmsConfigPara smsConfig = sysConfigService.getSmsConfigPara();
		if (smsConfig != null) {
			configInfo.setPayAddr(smsConfig.getPayAddr());
			configInfo.setCompanyName(smsConfig.getCompanyName());
		}

		// webSocket地址
		String webSocketUrl = sysConfigService.getWebSocketUrl();
		configInfo.setWebSocketUrl(webSocketUrl);
		// 版权信息
		String copRight = sysConfigService.getcopyRight();
		configInfo.setCopyright(copRight);
		result.setData(configInfo);

		return JSON.toJSONString(result);
	}

	/**
	 * 系统参数配置
	 * @param session
	 * @param param
	 * @return
	 */
	@Auth(menuID=100044L,actionID=2L)
	@SystemControllerLog(description = "系统参数配置更新")
	@RequestMapping("/update")
	@ResponseBody
	public String update(HttpSession session, @Valid @RequestBody SystemConfigParam param) {
		Result result = new Result("修改成功");
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		userLogService.create(currentUser.getId(), Action.MODIFY.getActionId(),"系统参数配置","apn",param.getApn(),"修改系统参数配置" , JSON.toJSONString(param));

		SystemConfigParamVo paramVo = new SystemConfigParamVo();
		BeanUtils.copyProperties(param,paramVo);
		sysConfigService.batchUpdate(paramVo);
		return JSON.toJSONString(result);
	}
}
