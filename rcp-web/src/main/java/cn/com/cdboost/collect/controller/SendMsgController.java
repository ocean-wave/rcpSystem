package cn.com.cdboost.collect.controller;


import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.dto.param.SendSmsParam;
import cn.com.cdboost.collect.dto.param.SmsAlarmParam;
import cn.com.cdboost.collect.enums.Action;
import cn.com.cdboost.collect.enums.Auth;
import cn.com.cdboost.collect.enums.DeviceType;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.model.CustomerPhoneBind;
import cn.com.cdboost.collect.service.AliyunSmsService;
import cn.com.cdboost.collect.service.CustomerPhoneBindService;
import cn.com.cdboost.collect.service.UserLogService;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/system")
public class SendMsgController {
	private static final Logger logger = LoggerFactory.getLogger(SendMsgController.class);

	@Autowired
	private UserLogService userLogService;
	@Autowired
	private AliyunSmsService aliyunSmsService;
	@Autowired
	private CustomerPhoneBindService customerPhoneBindService;

	@Auth(menuID=10001101L,actionID=7L)
	@SystemControllerLog(description = "发送告警短信")
	@RequestMapping("/sendMsg")
	@ResponseBody
	public String sendMsg(HttpSession session, @Valid @RequestBody SendSmsParam param) {
		Result result = new Result("短信发送成功");
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		userLogService.create(currentUser.getId(), Action.SEND_SMS.getActionId(),"短信","customerNo",param.getCustomerNo(),"发送短信给["+param.getCustomerName()+"]" ,JSON.toJSONString(param));

		// 查询需要发送的手机号
		List<CustomerPhoneBind> phoneBinds = customerPhoneBindService.queryByCustomerNo(param.getCustomerNo());
		List<String> mobiles = Lists.newArrayList();
		for (CustomerPhoneBind phoneBind : phoneBinds) {
			mobiles.add(phoneBind.getMobilePhone());
		}

		// 发送告警短信
		SmsAlarmParam alarmParam = new SmsAlarmParam();
		BeanUtils.copyProperties(param,alarmParam);
		alarmParam.setCollectTime(param.getCollectDate());
		alarmParam.setRemainAmount(param.getReadValue());
		alarmParam.setMobiles(mobiles);
		alarmParam.setDeviceType(DeviceType.ELECTRIC_METER.getCode());
		List<SmsAlarmParam> list = Lists.newArrayList(alarmParam);
		aliyunSmsService.sendAlarmSms(list,0);
		return JSON.toJSONString(result);
	}
}
 