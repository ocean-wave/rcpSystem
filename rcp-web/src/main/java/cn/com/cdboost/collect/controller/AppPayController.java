package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.constant.CustomerInfoConstant;
import cn.com.cdboost.collect.constant.FeeControlConstant;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.RealMeterCollectFailQueryVo;
import cn.com.cdboost.collect.dto.param.WeChatBindParam;
import cn.com.cdboost.collect.dto.param.WeChatBindVo;
import cn.com.cdboost.collect.dto.response.*;
import cn.com.cdboost.collect.enums.DeviceType;
import cn.com.cdboost.collect.enums.ResultCode;
import cn.com.cdboost.collect.enums.ReturnStringEnum;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.*;
import cn.com.cdboost.collect.model.CustomerInfo;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.*;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.google.common.base.Function;
import com.google.common.collect.*;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Nullable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/appPay")
public class AppPayController {
	private static final Logger logger = LoggerFactory.getLogger(AppPayController.class);
	// 微信支付成功后通知地址 必须要求80端口并且地址不能带参数
	private static final String NOTIFY_URL = "/appPay/weChatPayNotice";
	private static final String ALIPAY_NOTIFY_URL = "/appPay/aliPayCallback";
	private static final String CHARSET = "utf-8";

	@Autowired
	private FeePayService feePayService;
	@Autowired
	private CustomerDevMapService customerDevMapService;
	@Autowired
	private CustomerPhoneBindService customerPhoneBindService;
	@Autowired
	private CustomerInfoService customerInfoService;
	@Autowired
	private FeePayOrderService feePayOrderService;
	@Autowired
	private WeixinService weixinService;
	@Autowired
	private AliyunSmsService aliyunSmsService;
	@Autowired
	private DeviceInfoService deviceInfoService;
	@Autowired
	private CustomerWxBindService customerWxBindService;
	@Autowired
	private RealTimeDataService realTimeDataService;
	@Autowired
	private DeviceMeterParamService deviceMeterParamService;
	@Autowired
	private ChargingPayChemeService chargingPayChemeService;
	@Autowired
	private AlipayService alipayService;

	@Value("${callback.url}")
	private String host;
	@Value("${appId}")
	private String appId;
	@Value("${secret}")
	private String secret;
	@Value("${mchId}")
	private String mchId;
	@Value("${partnerkey}")
	private String partnerkey;

	@Value("${alipay.appId}")
	private String alipayAppId;
	@Value("${alipay.app.private.key}")
	private String alipayAppPrivateKey;
	@Value("${alipay.public.key}")
	private String alipayPublicKey;

	private static String access_token = "";
	private static long expires = 0;
	private static String access_token_p = "";
	private static long expires_p = 0;
	private static long ex = 1;

	/**
	 * 微信公众号银联支付成功，点击返回时的请求
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value="/unionPayResult")
	public void htmlView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
//		request.getRequestDispatcher("/wePay/paymain.html").forward(request, response);
		response.sendRedirect("/wePay/paymain.html");
	}
	@Autowired
	AppChargerService appChargerService;

	@SystemControllerLog(description = "实时采集设备剩余金额")
	@RequestMapping(value = "/collectDeviceData")
	@ResponseBody
	public String collectDeviceData(HttpServletResponse response,
									@RequestParam String guid,
									@RequestParam Integer isImportant,
									@RequestParam String jzqCno,
									@RequestParam String deviceType,
									@RequestParam String meterUserNo,
									@RequestParam String groupGuid) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		WxResult result = new WxResult<>();
		if (StringUtils.isEmpty(guid)) {
			result.error("guid不能为空");
			return JSON.toJSONString(result);
		}

		if (StringUtils.isEmpty(jzqCno)) {
			result.error("jzqCno不能为空");
			return JSON.toJSONString(result);
		}

		if (StringUtils.isEmpty(deviceType)) {
			result.error("deviceType不能为空");
			return JSON.toJSONString(result);
		}

		if (StringUtils.isEmpty(meterUserNo)) {
			result.error("meterUserNo不能为空");
			return JSON.toJSONString(result);
		}

		if (StringUtils.isEmpty(groupGuid)) {
			result.error("groupGuid不能为空");
			return JSON.toJSONString(result);
		}
		// 查询表cno
		CustomerDevMap devMap = customerDevMapService.queryByMeterUserNo(deviceType, meterUserNo);
		if (devMap == null) {
			result.setCode(-1);
			result.setMessage("该设备不存在，可能已删除");
			return JSON.toJSONString(result);
		}
		String cno = devMap.getCno();
		DeviceMeterParam deviceMeterParam = deviceMeterParamService.queryEffectiveParamByCno(cno);
		Integer localControl = deviceMeterParam.getLocalControl();
		if (CustomerInfoConstant.FeeControlType.REMOTE.getCode().equals(localControl)) {
			// 远程费控是不支持抄剩余金额的，所以直接查询数据库剩余金额返回
//			BigDecimal remainAmount1 = devMap.getRemainAmount();
			BigDecimal remainAmount1 = BigDecimal.ZERO;
			if (remainAmount1 != null) {
				BigDecimal remainAmount = MathUtil.setPrecision(remainAmount1);
				result.setData(remainAmount.toString());
			} else {
				result.setData("");
			}

//			Date amountCollectTime = devMap.getAmountCollectTime();
			Date amountCollectTime =new Date();
			if (amountCollectTime != null) {
				String dateStr = DateUtil.formatDate(amountCollectTime);
				result.setAmountCollectTime(dateStr);
			} else {
				result.setAmountCollectTime("");
			}

			return JSON.toJSONString(result);
		}

		// 发送抄表指令
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		Date date =  calendar.getTime();
		result.setData(sdf.format(date));

		SendRealCollectQueryParam param = new SendRealCollectQueryParam();
		param.setUserId(1);
		param.setDate(date);
		param.setGuid(guid);
		param.setIsImportant(isImportant);
		List<RealCollectMeterParam> meters = Lists.newArrayList();
		RealCollectMeterParam meterParam = new RealCollectMeterParam();
		meterParam.setJzqCno(jzqCno);
		meterParam.setDeviceCno(cno);
		meterParam.setGroupGuid(groupGuid);
		meters.add(meterParam);
		param.setMeters(meters);

		// 默认只抄剩余金额
		String[] types =  new String[] {"00900200"};
		param.setTypes(types);
		String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
		param.setSessionId(sessionId);
		int status = realTimeDataService.sendRealCollectList(param);
		logger.info("status = " + status);
		if (status != 1) {
			result.error("中间件指令发送失败");
		}

		return JSON.toJSONString(result);
	}


	@RequestMapping(value = "queryCollectStatus", method={RequestMethod.POST})
	@SystemControllerLog(description = "微信端查询采集状态")
	@ResponseBody
	public String queryCollectStatus(HttpServletResponse response,
									 @RequestParam String guid,
									 @RequestParam String deviceType,
									 @RequestParam String date,
									 @RequestParam Integer stopFlag) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<RealTimeDataStatuListInfo> result = new Result<>();
		if (StringUtils.isEmpty(guid)) {
			result.error("guid不能为空");
			return JSON.toJSONString(result);
		}

		if (StringUtils.isEmpty(deviceType)) {
			result.error("deviceType不能为空");
			return JSON.toJSONString(result);
		}

		if (StringUtils.isEmpty(date)) {
			result.error("date不能为空");
			return JSON.toJSONString(result);
		}

		if (stopFlag == 0) {
			RealTimeDataStatuListInfo info = realTimeDataService.realTimeDataListStatus(1L, guid, deviceType, date);
			Integer status = info.getStatus();
			Boolean isUpdate = info.getIsUpdate();
			if(status.intValue() != 101 || isUpdate){
				this.setFailList(guid, deviceType, info, date);
			}

			result.setData(info);
		}

		return JSON.toJSONString(result);
	}

	private void setFailList(String guid, String deviceType,RealTimeDataStatuListInfo info, String date) {
		RealMeterCollectFailQueryVo queryVo = new RealMeterCollectFailQueryVo();
		queryVo.setGuid(guid);
		queryVo.setSearchDate(date);
		queryVo.setDeviceType(deviceType);
		queryVo.setPageSize(info.getTotal());
		List<MeterCollectDataFailInfo> dataFailInfos = realTimeDataService.listRealTimeFailData(queryVo);

		Boolean isUpdate = info.getIsUpdate();
		if(isUpdate) {
			List<String> cnoList = info.getCnoList();
			if (CollectionUtils.isEmpty(cnoList)) {
				dataFailInfos = null;
			} else {
				Iterator<MeterCollectDataFailInfo> iterator = dataFailInfos.iterator();
				while (iterator.hasNext()){
					MeterCollectDataFailInfo failInfo = iterator.next();
					if(!cnoList.contains(failInfo.getDeviceCno())){
						iterator.remove();
					}
				}
			}
		}
		info.setFailList(dataFailInfos);
	}

	@SystemControllerLog(description = "获取某个设备相关信息")
	@RequestMapping(value = "/queryDeviceInfo")
	@ResponseBody
	public String queryDeviceInfo(HttpServletResponse response, @RequestParam String deviceType, @RequestParam String meterUserNo) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<DeviceInfo4WeChat> result = new Result<>();
		if (StringUtils.isEmpty(deviceType)) {
			result.error("deviceType不能为空");
			return JSON.toJSONString(result);
		}

		if (StringUtil.isEmpty(meterUserNo)) {
			result.error("meterUserNo不能为空");
			return JSON.toJSONString(result);
		}

		CustomerDevMap devMap = customerDevMapService.queryByMeterUserNo(deviceType, meterUserNo);
		if (devMap == null) {
			result.setCode(-1);
			result.setMessage("该设备不存在，可能已删除");
			return JSON.toJSONString(result);
		}

		DeviceInfo4WeChat info4WeChat = weixinService.queryDeviceInfo(deviceType,meterUserNo);
		result.setData(info4WeChat);
		return JSON.toJSONString(result);
	}

	@SystemControllerLog(description = "查询设备缴费记录列表，默认最多返回5条")
	@RequestMapping(value = "/queryFeePayList")
	@ResponseBody
	public String queryFeePayList(HttpServletResponse response, @RequestParam String deviceType, @RequestParam String meterUserNo) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<List<FeePay4WeChat>> result = new Result<>();
		if (StringUtils.isEmpty(deviceType)) {
			result.error("deviceType不能为空");
			return JSON.toJSONString(result);
		}

		if (StringUtils.isEmpty(meterUserNo)) {
			result.error("meterUserNo不能为空");
			return JSON.toJSONString(result);
		}
		CustomerDevMap devMap = customerDevMapService.queryByMeterUserNo(deviceType, meterUserNo);
		if (devMap == null) {
			result.setCode(-1);
			result.setMessage("该设备不存在，可能已删除");
			return JSON.toJSONString(result);
		}

		String cno = devMap.getCno();
		List<FeePay> feePays = feePayService.getLastNFeePay(cno, 5);
		List<FeePay4WeChat> dataList = Lists.newArrayList();
		if (!CollectionUtils.isEmpty(feePays)) {
			for (FeePay feePay : feePays) {
				FeePay4WeChat pay4WeChat = new FeePay4WeChat();
				pay4WeChat.setPayMoney(feePay.getPayMoney());
				pay4WeChat.setWriteMeter(feePay.getWriteMeter());
				String dateStr = DateUtil.formatDate(feePay.getPayDate());
				pay4WeChat.setPayDate(dateStr);

				dataList.add(pay4WeChat);
			}
		}
		result.setData(dataList);
		return JSON.toJSONString(result);
	}

	@RequestMapping(value = "/sendSmsCode")
	@SystemControllerLog(description = "获取短信验证码")
	@ResponseBody
	public String sendSmsCode(HttpServletResponse response,
							  @RequestParam String mobilePhone,
							  @RequestParam String meterUserNo,
							  @RequestParam String deviceType) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<String> result = new Result<>("短信验证码发送成功");
		if (StringUtils.isEmpty(mobilePhone)) {
			result.error("mobilePhone不能为空");
			return JSON.toJSONString(result);
		}

		if (StringUtil.isEmpty(meterUserNo)) {
			result.error("meterUserNo不能为空");
			return JSON.toJSONString(result);
		}

		if (StringUtil.isEmpty(deviceType)) {
			result.error("deviceType不能为空");
			return JSON.toJSONString(result);
		}
		// 表计户号补全12位
		String newMeterUserNo = CNoUtil.fillStrLengthTo12(meterUserNo);

		CustomerDevMap devMap = customerDevMapService.queryByMeterUserNo(deviceType, newMeterUserNo);
		if (devMap == null) {
			throw new BusinessException("系统中不存在此表计户号");
		}
		CustomerPhoneBind customerPhoneBind = customerPhoneBindService.queryByParam(devMap.getCustomerNo(), mobilePhone);
		if (customerPhoneBind == null) {
			throw new BusinessException("系统不存在该手机号的绑定信息");
		}

		aliyunSmsService.sendSmsCode(mobilePhone,devMap.getCustomerNo());
		return JSON.toJSONString(result);
	}

	/**
	 * 网页跨域访问存在问题，传json对象字符串，还是报跨域
	 * @param response
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/validateAndBindWeChat2")
	@SystemControllerLog(description = "用户第一次绑定设备时，校验并绑定设备信息")
	@ResponseBody
	public String validateAndBindWeChat2(HttpServletResponse response,@Valid @RequestBody WeChatBindParam param) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<String> result = new Result<>("绑定成功");
		WeChatBindVo bindVo = new WeChatBindVo();
		BeanUtils.copyProperties(param,bindVo);
		weixinService.validateAndBindWeChat(bindVo);
		return JSON.toJSONString(result);
	}

	@RequestMapping(value = "/validateAndBindWeChat")
	@SystemControllerLog(description = "用户第一次绑定设备时，校验并绑定设备信息")
	@ResponseBody
	public String validateAndBindWeChat(HttpServletResponse response,
										@RequestParam String mobilePhone,
										@RequestParam String meterUserNo,
										@RequestParam String deviceType,
										@RequestParam String smsCode,
										@RequestParam String openId,
										String remark) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<String> result = new Result<>("绑定成功");

		// 表计户号补全12位
		String newMeterUserNo = CNoUtil.fillStrLengthTo12(meterUserNo);

		WeChatBindVo bindVo = new WeChatBindVo();
		bindVo.setDeviceType(deviceType);
		bindVo.setMobilePhone(mobilePhone);
		bindVo.setMeterUserNo(newMeterUserNo);
		bindVo.setSmsCode(smsCode);
		bindVo.setOpenId(openId);
		bindVo.setRemark(remark);
		weixinService.validateAndBindWeChat(bindVo);
		return JSON.toJSONString(result);
	}

	@RequestMapping("/bindDevice4WeChat")
	@SystemControllerLog(description = "微信绑定其他设备信息")
	@ResponseBody
	public String bindDevice4WeChat(HttpServletResponse response,
									@RequestParam String meterUserNo,
									@RequestParam String deviceType,
									@RequestParam String openId,
									String remark) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<String> result = new Result<>("绑定成功");
		if (StringUtil.isEmpty(meterUserNo)) {
			result.error("meterUserNo不能为空");
			return JSON.toJSONString(result);
		}

		if (StringUtil.isEmpty(deviceType)) {
			result.error("deviceType不能为空");
			return JSON.toJSONString(result);
		}

		if (StringUtil.isEmpty(openId)) {
			result.error("openId不能为空");
			return JSON.toJSONString(result);
		}

		// 表计户号补全12位
		String newMeterUserNo = CNoUtil.fillStrLengthTo12(meterUserNo);

		weixinService.bindWeChat(newMeterUserNo,deviceType,openId,remark);
		return JSON.toJSONString(result);
	}

	@SystemControllerLog(description = "测试支付宝支付下单")
	@RequestMapping("/GetZFBPayInfo")
	@ResponseBody
	public String getZFBappPayInfo(){
		String s="";
		String APP_ID="2017092208867749";
		String APP_PRIVATE_KEY="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDDxFm6aXI4D0ulM3QohjlL0a4EbP6yp+IPEiUtS/3DenWPUaqXNXfbQKETLoAVlvfvoFOzYeEa5qRTgptgt/Gj/ehhJZ1WpzblEg6aSHO2QQ9okEkrt40xZ+dvm0itx+RJkf7SRJ32a0FNcnHR6y8OmIF7CMZa97BEy1txSD1vt4YmoHwFRRI4ZArfpHb5qpzYNHYUwk8lRDnh8ziT/tknwB58rC0xX1cnOB+IGj44unOiu4Ja97tZvNiR1Ky6P3ztwUMFBtc0O8ETK9knjHXiZLlHE/gwwp0oMK7ctQ0MkcNRAG5ezHZGEqGFnsLs1oNNnF9fvAeBtZ13Io/K0QcHAgMBAAECggEBAIHI0pO7BHYV7dh/3Cphkeqp/+v/NnvKwRuss+jr0DigHv/87FOCpws8NY057sBhbc33RIZQBaSH87lJhSkBybkmw8Bj54NjKAmFhxjRn6TcTRLNVTUeYwS2FcDv+ab8NmRv3bgYtYCAarKlhL/eYzwR7wVaM+Zq/vkcuGrJsbE1mMX7zSwKXbel6Zeug60tRcb5koB/YXORKyCk3kb4xUj/kNtT41Qr8uPi0FfF8rIBJmZu1KN2RnQZFuLQT/ctO3WSS6DerwET6jebj7MOpEy4T5PJ/1ODzLJXSldX6P+gWEduhtnr+0rYWreJLKa9zHDDrxItPgGKrnHHfEMJqIECgYEA7zwdn88oMjlg+S9WImcOZNowXWzHvPwioqAXgiqm0VMPeBQqhcCnG/AJ0NyZl8ip8gcIrDETHlQ/chDpSUaOv42ZYIiMk0hYvlrndfU5zMzjzOPwjDPAU8fXQEgk4diMbb/JPqvuyEGBdhe+lR9zv+9LEIA6X5BunxCwtgqDKokCgYEA0XxrdjVQbqrZ9V+nflBsr/o/tzZSI7+3xsKu5EzU6wUSKker2XO9nEVuAuflFYobxQQriSz8pFBMdG7e7ZDWYvNglU38sZAkLnEmmeInKDExYNaRL2KSAWjPe2japJVNpIXQxKX7HArEhBgcZSQyhgE4wDPbHuA1sdLfIBB/AQ8CgYAHGZijeolt3CgVnSMISFi0A4m17BmNcUox22B3O37R7Y4i+S494/bRrHEOIs3oJEtu5GiMe7RejQ+I+hmCVDa+Vpx7sfHCGT6Q/6+o34tEmbwVA5rK/mox0leAD+QQU9XvF+cIFiAzD0ahU5kTYeiUhKyMo9GXrnip/dvQu0UjSQKBgDY2PJkasvXwwPb/ChlmNrpTyKlhJWCa/LQzLY9nUrJ/XXQTmCVuDmOilBWsbVxloUobCik1uyvKm/6FsqtIEDMLSLNau0o4QyTBo3uvSvLbyCuHW4sntyEmPotxVyPAf5dUCfLaAWIAZ1rH4pFKDMkhB861nL8gxsR+Db/+hN+BAoGAPRRu1GXUzhp04VqjDVCOWEEXTihC0yeIqIJMF0iK3MNfNwIJXg8rbAI2IqR+DnLVsDA+DrMkXjdRkByHbcx8OOk5wqOYSH3UQqFZeDcvgiinlilCzs2IywndRjIKm/61FfKAiDDmWxOKPYDKNG4sa581MZLJIL6scIgLw9vmJzo=",
				CHARSET="utf-8",
				ALIPAY_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAw8RZumlyOA9LpTN0KIY5S9GuBGz+sqfiDxIlLUv9w3p1j1GqlzV320ChEy6AFZb376BTs2HhGuakU4KbYLfxo/3oYSWdVqc25RIOmkhztkEPaJBJK7eNMWfnb5tIrcfkSZH+0kSd9mtBTXJx0esvDpiBewjGWvewRMtbcUg9b7eGJqB8BUUSOGQK36R2+aqc2DR2FMJPJUQ54fM4k/7ZJ8AefKwtMV9XJzgfiBo+OLpzoruCWve7WbzYkdSsuj987cFDBQbXNDvBEyvZJ4x14mS5RxP4MMKdKDCu3LUNDJHDUQBuXsx2RhKhhZ7C7NaDTZxfX7wHgbWddyKPytEHBwIDAQAB";

		//实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
		//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest requestThis = new AlipayTradeAppPayRequest();
		//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody("我是测试数据");
		model.setSubject("App支付测试Java");
		model.setOutTradeNo(StringUtil.getNonceStr());
		model.setTimeoutExpress("30m");
		model.setTotalAmount("0.01");
		model.setProductCode("QUICK_MSECURITY_PAY");
		requestThis.setBizModel(model);
		requestThis.setNotifyUrl("http://17qp387663.iask.in:14456/rmcSystem/realTimeData/GetZFBInfo.do");
		try {
			//这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse responseThis = alipayClient.sdkExecute(requestThis);
			System.out.println(responseThis.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
			s=responseThis.getBody();
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return s;
	}

	@SystemControllerLog(description = "测试支付宝支付下单异步通知")
	@RequestMapping("/GetZFBInfo")
	@ResponseBody
	public void checkZFBAsynchronousNotification(HttpServletRequest request){
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
		//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
		String alipaypublicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsnClxKbcqY9f2EcjQ4Mem7d0rfQFc75X2xVe7+Vgy+9U3FEIJin4dGefoGjZRqQS5yDWgi2Br0G1uTvgFP3ZtFV91B0NU4pycZnC4ZZdKZB6ckyY+jmx8FS/yQQWvHLupZuQQlv8+koqvSeZut1h5mEPvVDDVlYt7gtv9AWR4F2Mw4vFGrZxoziTIiJRmBtxq2vCKExbKvC8Ms1aJC3IcBAryfZCyi4gyh5k33fGFX3JoQwfXRS9IYGC1S71c9IFhBvzO8tzg1QIWUYEAgMVgZX/22TfzOTP8xlRGdY6MmpBE1+JStw4CrVycZ3EqxptxXsj3x13kSvb7LwV7v+qKQIDAQAB",charset="utf-8";
		try {
			boolean flag = AlipaySignature.rsaCheckV1(params, alipaypublicKey, charset,"RSA2");
			if(flag){
				if("TRADE_SUCCESS".equals(params.get("trade_status"))){
					//付款金额
					String amount = params.get("buyer_pay_amount");
					//商户订单号
					String out_trade_no = params.get("out_trade_no");
					//支付宝交易号
					String trade_no = params.get("trade_no");
					//附加数据
					String passback_params = URLDecoder.decode(params.get("passback_params"));
				}
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
	}

	@SystemControllerLog(description = "获取微信签名和参数")
	@RequestMapping("/GetWeiXinSign")
	@ResponseBody
	public Map<String, Object> GetWXParams(HttpServletRequest request, String strjson) {
		logger.info("RealTimeDataController:GetWeiXinSign:" + strjson);
		Map<String,String[]> map = request.getParameterMap();
		Map<String, Object> returnMap = new HashMap<>();

		// 1、获取Ticket
		String jsApiTicket = weixinService.getJsapiTicket();

		// 2、时间戳和随机字符串
		String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳

		// 3、获取url
		String url = map.get("localurl")[0];

		// 4、将参数排序并拼接字符串
		String str = "jsapi_ticket="+jsApiTicket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
		logger.info("weixin"+"-GetWeiXinSign:"+str);
		// 5、将字符串进行sha1加密
		String signature =SHA1(str);
		logger.info("weixin"+"-signature:"+signature);
		try {
			returnMap.put("nonceStr",noncestr);
			returnMap.put("timestamp",timestamp);
			returnMap.put("signature",signature);
		} catch (Exception e) {
			returnMap.put("resultCode", ResultCode.Fail.getValue());
			returnMap.put("msg", "失败");
		}
		return returnMap;
	}

	@SystemControllerLog(description = "微信统一下单接口")
	@RequestMapping("/wxPrePay")
	@ResponseBody
	public String wxPrePay(HttpServletResponse response, String openId, String ip,String deviceType,String meterUserNo, String payMoney){
		response.setHeader("Access-Control-Allow-Origin", "*");
		//微信支付商户开通后 微信会提供appid和appsecret和商户号partner
		Result<PrePayReturn> result = new Result<>();
		if (StringUtils.isEmpty(openId)) {
			result.error("openId不能为空");
			return JSON.toJSONString(result);
		}

		if (StringUtils.isEmpty(ip)) {
			result.error("ip不能为空");
			return JSON.toJSONString(result);
		}

		if (StringUtils.isEmpty(deviceType)) {
			result.error("deviceType不能为空");
			return JSON.toJSONString(result);
		}

		if (StringUtils.isEmpty(meterUserNo)) {
			result.error("meterUserNo不能为空");
			return JSON.toJSONString(result);
		}

		if (StringUtils.isEmpty(payMoney)) {
			result.error("payMoney不能为空");
			return JSON.toJSONString(result);
		}

		// 判断是否已开户
		CustomerDevMap devMap = customerDevMapService.queryByMeterUserNo(deviceType,meterUserNo);
		if (devMap == null) {
			result.setCode(-1);
			result.setMessage("该设备不存在，可能已删除");
			return JSON.toJSONString(result);
		}
		String cno = devMap.getCno();
		Integer isAccount = devMap.getIsAccount();
		if (!FeeControlConstant.OpenAccountStatus.OPEN_ACCOUNT_SUCCESS.getStatus().equals(isAccount)) {
			// 未开户或开户失败的
			result.error("请先开户再充值");
			return JSON.toJSONString(result);
		}

		// 订单号(支付标识)
		String payGuid = UUID.randomUUID().toString().replace("-", "");

		// 总金额以分为单位，不带小数点
		String totalFee = getMoney(payMoney);
		String spbill_create_ip = ip;
		String trade_type = "JSAPI";
		// 随机字符串
		String nonce_str = StringUtil.getNonceStr();
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

		String time_start = format.format(new Date());
		// 商户订单号
		String out_trade_no = payGuid;

		String body = "微信测试支付";
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appId);
		packageParams.put("body", body);
		packageParams.put("openid",openId);
		packageParams.put("time_start",time_start);
		packageParams.put("mch_id", mchId);
		packageParams.put("nonce_str", nonce_str);

		String notifyUrl = host + NOTIFY_URL;
		packageParams.put("notify_url", notifyUrl);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("spbill_create_ip", spbill_create_ip);
		// 这里写的金额为1 分到时修改
		packageParams.put("total_fee", totalFee);
		packageParams.put("trade_type", trade_type);

		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appId, secret, partnerkey);

		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>"
				+ "<appid>" + appId + "</appid>"
				+ "<mch_id>"+ mchId + "</mch_id>"
				+ "<nonce_str>" + nonce_str+ "</nonce_str>"
				+ "<body><![CDATA[" + body + "]]></body>"
				+ "<time_start>" + time_start + "</time_start>"
				+ "<out_trade_no>" + out_trade_no+ "</out_trade_no>"
				+ "<total_fee>" + totalFee + "</total_fee>"
				+ "<spbill_create_ip>" + spbill_create_ip+ "</spbill_create_ip>"
				+ "<notify_url>" + notifyUrl+ "</notify_url>"
				+ "<openid>" + openId + "</openid>"
				+ "<trade_type>" + trade_type+ "</trade_type>"
				+ "<sign>" + sign + "</sign>"
				+ "</xml>";
		String prepay_id = "";
		logger.info("weixin"+"-xml:"+xml);
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

		prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
		// 预订单生成后，本地保存相应的订单号
		if(!StringUtils.isEmpty(prepay_id)){
			FeePayOrder feePayOrder = new FeePayOrder();
			feePayOrder.setTradeNo(payGuid);
			feePayOrder.setAppId(appId);
			feePayOrder.setMchId(mchId);
			feePayOrder.setNonceStr(nonce_str);
			feePayOrder.setSignType("MD5");
			feePayOrder.setSign(sign);
			feePayOrder.setTradeBody(body);
			feePayOrder.setTimeStart(time_start);
			feePayOrder.setTotalFee(Integer.parseInt(totalFee));
			feePayOrder.setSpbillCreateIp(spbill_create_ip);
			feePayOrder.setNotifyUrl(notifyUrl);
			feePayOrder.setTradeType(trade_type);
			feePayOrder.setCreateTime(new Date());
			feePayOrder.setPayFlag(0);
			feePayOrder.setCno(cno);

			feePayOrderService.insert(feePayOrder);
		}

		//获取prepay_id后，拼接最后请求支付所需要的package
		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		String timestamp = Sha1Util.getTimeStamp();
		nonce_str = StringUtil.getNonceStr();
		finalpackage.put("appId", appId);
		finalpackage.put("timeStamp", timestamp);
		finalpackage.put("nonceStr", nonce_str);
		finalpackage.put("package", "prepay_id="+prepay_id);
		finalpackage.put("signType", "MD5");
		//要签名
		String finalsign = reqHandler.createSign(finalpackage);

		PrePayReturn payReturn = new PrePayReturn();
		payReturn.setAppid(appId);
		payReturn.setPartnerid(mchId);
		payReturn.setPrepayid(prepay_id);
		payReturn.setNoncestr(nonce_str);
		payReturn.setTimestamp(timestamp);
		payReturn.setSign(finalsign);
		payReturn.setTradeNo(out_trade_no);
		payReturn.setOpenId(openId);
		result.setData(payReturn);
		return JSON.toJSONString(result);
	}

	/**
	 * 测试微信支付下单异步通知
	 * 需要校验签名和订单信息，避免获取到“假通知”
	 * 校验成功后返回微信服务器success信息，以免微信服务器重复发送通知(貌似没用)
	 */
	@SystemControllerLog(description = "测试微信支付下单异步通知")
	@RequestMapping("/weChatPayNotice")
	@ResponseBody
	public void weChatPayNotice(HttpServletRequest request, HttpServletResponse response){
		try {
			InputStream io = request.getInputStream();
			String xmlStr = InputStreamUtil.convert2String(io);
			Document document = XmlUtil.getDocument(xmlStr);
			Map<String, String> map = new HashMap<>();
			XmlUtil.getMapByDocument(document.getRootElement(), map);
			logger.info("微信支付回调通知入参=：" + JSON.toJSONString(map));

			// 商户系统对于支付结果通知的内容一定要做签名验证，防止数据泄漏导致出现“假通知”，造成资金损失。
			boolean flag = WeiXinUtil2.checkSign(map,partnerkey);
			if (!flag) {
				logger.info("支付结果通知的内容签名校验不通过,直接抛弃");
				return;
			}
			String return_code = map.get("return_code");
			String result_code = map.get("result_code");
			if (!"SUCCESS".equals(return_code)) {
				// 通信失败
				logger.info("微信回调通信失败");
				return;
			}

			if(!"SUCCESS".equals(result_code)) {
				logger.info("微信回调业务结果返回FAIL");
				return;
			}

			// 立即通知微信
			this.writeResponse(response);

			// 查询博高订单
			String outTradeNo = map.get("out_trade_no");
			String totalFee = map.get("total_fee").toString();
			String timeEnd = map.get("time_end").toString();
			weixinService.processWeChatCallback(outTradeNo,totalFee,timeEnd);
		} catch (Exception e) {
			logger.error("微信回调通知接口异常：",e);
		}

	}


	/**
	 * 通知微信服务器，商户接收通知成功并校验成功
	 * @param response 微信回调时的响应对象
	 */
	public void writeResponse(HttpServletResponse response) {
		Writer writer = null;
		try {
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("return_code", "SUCCESS");
			paramMap.put("return_msg", "OK");
			String str = WeiXinUtil2.toXml(paramMap);
			writer = response.getWriter();
			writer.write(str);
			writer.flush();
			writer.close();
			writer = null;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("运行异常:",e);
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("运行异常:",e);
				}
			}
		}

	}


	// 下单操作
	private void feePay(Map<String, Object> map) {
		String tradeNo = map.get("out_trade_no").toString();
		String totalFee = map.get("total_fee").toString();
		String timeEnd = map.get("time_end").toString();
		FeePayOrder feePayOrder = feePayOrderService.queryByTradeNo(tradeNo);
		if(feePayOrder.getPayFlag() != 1){

			logger.info("===================================进行下单操作");
			CustomerDevMap customerDevMap = customerDevMapService.queryByCno(feePayOrder.getCno());

			// 更改为成功状态
			FeePayOrder payOrder = new FeePayOrder();
			payOrder.setId(feePayOrder.getId());
			payOrder.setPayFlag(1);
			payOrder.setTimeExpire(timeEnd);
			payOrder.setFinishTime(new Date());
			feePayOrderService.updateByPrimaryKeySelective(payOrder);

			// 下单操作
			FeePay feePay = new FeePay();
			feePay.setPayGuid(tradeNo);
			feePay.setPayMoney(BigDecimal.valueOf(Long.parseLong(totalFee)/100.0));
			feePay.setPayModel(FeeControlConstant.PayModelValue.APP.getCode());
			feePay.setCno(customerDevMap.getCno());
			feePay.setCustomerNo(customerDevMap.getCustomerNo());
//			feePay.setPayCount(customerDevMap.getPayCount() + 1);
			feePay.setPayMethod(FeeControlConstant.PayMethod.WE_CHAT.getCode());
			String serialNum = DateUtil.getSerialNum();
			feePay.setSerialNum(serialNum);
			feePayService.rechargePayment(feePay, 0L, customerDevMap.getMeterUserNo());
		}
	}

	@SystemControllerLog(description = "微信支付结果")
	@RequestMapping("/payResult")
	@ResponseBody
	public Map<String, Object> payResult(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		logger.info("AppPayController:payResult begin" );
		Map<String, Object> returnMap = Maps.newHashMap();
		returnMap.put("resultCode", ResultCode.Fail.getValue());
		returnMap.put("msg", "交易失败");
		try{
			Map<String,String[]> map = request.getParameterMap();
			String tradeNo = map.get("tradeNo")[0];
			Map<String, Object> orderQueryMap = weixinService.orderQuery(tradeNo);

			if(ReturnStringEnum.SUCCESS.getValue().equals(orderQueryMap.get("return_code").toString())){
				if(ReturnStringEnum.SUCCESS.getValue().equals(orderQueryMap.get("result_code").toString())){
					if(ReturnStringEnum.SUCCESS.getValue().equals(orderQueryMap.get("trade_state").toString())){
						// 如同步操作失败，则再一次进行下单操作
						feePay(orderQueryMap);
						FeePayOrder feePayOrder = feePayOrderService.queryByTradeNo(tradeNo);

						List<FeePay> list = feePayService.getLastNFeePay(feePayOrder.getCno(),1);
						FeePay feePay = list.get(0);

						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						returnMap.put("cno", feePayOrder.getCno());  // 设备编号
						returnMap.put("tradeNo", feePayOrder.getTradeNo()); // 交易号
						returnMap.put("payMoney", feePay.getPayMoney());  // 充值金额
						returnMap.put("payDate", dateFormat.format(feePay.getPayDate()));  // 充值时间
						returnMap.put("writeMeter", feePay.getWriteMeter());  // 下发状态：0为未充值，1为已下发，2为取消下发 3下发失败
						returnMap.put("witMetTime", feePay.getWitMetTime());  // 下发电表时间
						returnMap.put("resultCode", ResultCode.Success.getValue());
						returnMap.put("msg", "成功");
					}
				}
			}
			logger.info("AppPayController:payResult返回值:" + JSONObject.fromObject(returnMap));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}


	/**
	 *  用户档案解绑微信号
	 */
	@SystemControllerLog(description = "批量解除微信绑定操作")
	@RequestMapping("/unbindWeChat")
	@ResponseBody
	public String unbindWeChat(HttpServletResponse response, String ids) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result result = new Result("解绑成功");
		if (StringUtils.isEmpty(ids)) {
			result.error("ids不能为空");
			return JSON.toJSONString(result);
		}

		int num = customerWxBindService.batchUnBind(ids);
		logger.info("批量解除微信绑定，num=" + num);

		return JSON.toJSONString(result);
	}

	@SystemControllerLog(description = "根据微信号查询首页信息")
	@RequestMapping("/getMainInfoByOpenId")
	@ResponseBody
	public String getMainInfoByOpenId(HttpServletResponse response, @RequestParam String openId) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<MainInfo4WeChat> result = new Result<>();
		if (StringUtils.isEmpty(openId)) {
			result.error("openId不能为空");
			return JSON.toJSONString(result);
		}

		int electricMeterTotal = 0;
		int waterMeterTotal = 0;
		int gasMeterTotal = 0;
		int electricAlarmTotal = 0;
		int waterAlarmTotal = 0;
		int gasAlarmTotal = 0;

		List<CustomerWxBind> wxBinds = customerWxBindService.queryByOpenId(openId);
		if (CollectionUtils.isEmpty(wxBinds)) {
			MainInfo4WeChat info4WeChat = new MainInfo4WeChat();
			info4WeChat.setIsBind(0);
			info4WeChat.setOpenId(openId);
			result.setData(info4WeChat);
			result.setMessage("未绑定用户");
			return JSON.toJSONString(result);
		}

		Set<String> deviceTypeSet = Sets.newHashSet();
		Set<String> meterUserNoSet = Sets.newHashSet();
		for (CustomerWxBind wxBind : wxBinds) {
			deviceTypeSet.add(wxBind.getDeviceType());
			meterUserNoSet.add(wxBind.getMeterUserNo());
		}
		List<CustomerDevMap> customerDevMaps = customerDevMapService.queryList(deviceTypeSet, meterUserNoSet);
		Set<String> customerNoSet = Sets.newHashSet();
		List<String> cnoList = Lists.newArrayList();
		for (CustomerDevMap customerDevMap : customerDevMaps) {
			customerNoSet.add(customerDevMap.getCustomerNo());
			cnoList.add(customerDevMap.getCno());
		}
		// 分组
		ImmutableMap<String, CustomerDevMap> immutableMap = Maps.uniqueIndex(customerDevMaps, new Function<CustomerDevMap, String>() {
			@Nullable
			@Override
			public String apply(@Nullable CustomerDevMap devMap) {
				return devMap.getCno();
			}
		});

		// 查询设备信息
		List<DeviceInfo> deviceInfos = deviceInfoService.batchQueryByCnos(cnoList);

		// 按水电气分组
		ImmutableListMultimap<String, DeviceInfo> multimap = Multimaps.index(deviceInfos, new Function<DeviceInfo, String>() {
			@Nullable
			@Override
			public String apply(@Nullable DeviceInfo deviceInfo) {
				return deviceInfo.getDeviceType();
			}
		});


		for (Map.Entry<String, Collection<DeviceInfo>> entry : multimap.asMap().entrySet()) {
			String deviceType = entry.getKey();
			Collection<DeviceInfo> value = entry.getValue();
			if (DeviceType.ELECTRIC_METER.getCode().equals(deviceType)) {
				electricMeterTotal = value.size();
				int alarmNum = this.isAlarmNum(value, immutableMap);
				electricAlarmTotal = alarmNum;
			} else if (DeviceType.WATER_METER.getCode().equals(deviceType)) {
				waterMeterTotal = value.size();
				int alarmNum = this.isAlarmNum(value, immutableMap);
				waterAlarmTotal = alarmNum;
			} else if (DeviceType.GAS_METER.getCode().equals(deviceType)) {
				gasMeterTotal = value.size();
				int alarmNum = this.isAlarmNum(value, immutableMap);
				gasAlarmTotal = alarmNum;
			}
		}

		List<CustomerInfo> customerInfos = customerInfoService.batchQueryByCustomerNos(customerNoSet);
		CustomerInfo customerInfo = customerInfos.get(0);

		MainInfo4WeChat info4WeChat = new MainInfo4WeChat();
		info4WeChat.setIsBind(1);
		info4WeChat.setElectricMeterTotal(electricMeterTotal);
		info4WeChat.setElectricAlarmTotal(electricAlarmTotal);
		info4WeChat.setWaterMeterTotal(waterMeterTotal);
		info4WeChat.setWaterAlarmTotal(waterAlarmTotal);
		info4WeChat.setGasMeterTotal(gasMeterTotal);
		info4WeChat.setGasAlarmTotal(gasAlarmTotal);
		info4WeChat.setOpenId(openId);

		CustomerWxBind customerWxBind = wxBinds.get(0);
		info4WeChat.setCustomerContact(customerWxBind.getMobilePhone());
		if (customerInfo != null) {
			info4WeChat.setCustomerAddr(customerInfo.getCustomerAddr());
		}
		result.setData(info4WeChat);

		return JSON.toJSONString(result);
	}

	/**
	 * 需要告警的数量
	 * @param collection
	 * @param map
	 * @return
	 */
	private int isAlarmNum(Collection<DeviceInfo> collection,Map<String, CustomerDevMap> map) {
		int num = 0;
		for (DeviceInfo deviceInfo : collection) {
			String cno = deviceInfo.getCno();
			CustomerDevMap devMap = map.get(cno);
			// 告警阈值1
			BigDecimal alarmThreshold = devMap.getAlarmThreshold();
			// 剩余金额
//			BigDecimal remainAmount = devMap.getRemainAmount();
			BigDecimal remainAmount = BigDecimal.ZERO;
			if (remainAmount != null) {
				int result = MathUtil.compareTo(remainAmount, alarmThreshold);
				if (result != 1) {
					num++;
				}
			}
		}

		return num;
	}


	@SystemControllerLog(description = "根据微信号查询绑定设备表信息")
	@RequestMapping("/getBindInfoList")
	@ResponseBody
	public String getBindInfoList(HttpServletResponse response, String openId, String deviceType) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<List<BindDeviceInfo>> result = new Result<>();
		List<BindDeviceInfo> dataList = weixinService.getBindInfoList(openId, deviceType);
		result.setData(dataList);
		return JSON.toJSONString(result);
	}

	@SystemControllerLog(description = "根据表计户号相关信息")
	@RequestMapping("/queryBasicInfo")
	@ResponseBody
	public String queryBasicInfo(HttpServletResponse response, @RequestParam String deviceType, @RequestParam String meterUserNo) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<DeviceBasicInfo> result = new Result<>();
		if (StringUtil.isEmpty(deviceType)) {
			result.error("deviceType不能为空");
			return JSON.toJSONString(result);
		}

		if (StringUtil.isEmpty(meterUserNo)) {
			result.error("meterUserNo不能为空");
			return JSON.toJSONString(result);
		}

		// 表计户号补全12位
		String newMeterUserNo = CNoUtil.fillStrLengthTo12(meterUserNo);
		CustomerDevMap devMap = customerDevMapService.queryByMeterUserNo(deviceType,newMeterUserNo);
		if (devMap == null) {
			result.error("表计户号不存在");
			return JSON.toJSONString(result);
		}

		CustomerInfo customerInfo = customerInfoService.queryByCustomerNo(devMap.getCustomerNo());
		DeviceBasicInfo basicInfo = new DeviceBasicInfo();
		basicInfo.setCustomerName(customerInfo.getCustomerName());
		basicInfo.setCustomerAddr(customerInfo.getCustomerAddr());
		String deviceNo = CNoUtil.getDeviceNoByCno(devMap.getCno());
		basicInfo.setMeterNo(deviceNo);
		basicInfo.setIsAccount(devMap.getIsAccount());
		result.setData(basicInfo);
		return JSON.toJSONString(result);
	}


	@SystemControllerLog(description = "银联支付接口")
	@RequestMapping("/unionPay")
	@ResponseBody
	public String unionPay(HttpServletResponse response,Long orgNo,String ip,String deviceType,String meterUserNo,String orderAmount,Integer payType,String openId) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<UnionPayInfo> result = new Result<>();

		// 查询cno
		CustomerDevMap devMap = customerDevMapService.queryByMeterUserNo(deviceType, meterUserNo);
		if (devMap == null) {
			result.setCode(-1);
			result.setMessage("该设备不存在，可能已删除");
			return JSON.toJSONString(result);
		}
		String cno = devMap.getCno();
		String merId = "";

		String orderNo = UUID.randomUUID().toString().replace("-", "");
		orderNo = orderNo.substring(0,20);
		String curCode = "CNY";
		String orderType = "B2C";
		String callBackUrl = host + "/appPay/unionPayResult";
		String bankCode = null;
		String reserved01 = null;
//		String reserved02 = "";
		if (payType == FeeControlConstant.UnionPayType.NO_CARD_CHANNEL.getType()) {
			// 无卡支付
			bankCode = "00010000";
			reserved01 = "DeviceInfo=MOBILE|Body=测试";
		} else if (payType == FeeControlConstant.UnionPayType.WEIXIN_CHANNEL.getType()) {
			// 微信支付
			bankCode = "88988888";
			reserved01 = "DeviceInfo=WEB|Goods={\"goodsCategory\":\"缴费\",\"goodsId\":\"A0001\",\"goodsName\":\"电费\",\"body\":\"电表充值\",\"price\":\"" + orderAmount + "\",\"quantity\":\"1\"}|PayType=WXPay.jsPay|SubOpenId=" + openId;
		} else {
			logger.info("payType无效");
		}

		String langType = "UTF-8";
		String buzType = "01";

		// 测试地址
//		String FormUrl = "http://test.gnetpg.com:8089/GneteMerchantAPI/api/PayV36";
		// 正式环境支付地址
		String FormUrl = "https://www.gnetpg.com/GneteMerchantAPI/api/PayV36";


		StringBuffer SourceText = new StringBuffer();
		SourceText.append(merId);
		SourceText.append(orderNo);
		SourceText.append(orderAmount);
		SourceText.append(curCode);
		SourceText.append(orderType);
		SourceText.append(callBackUrl);
		SourceText.append(bankCode);
		SourceText.append(langType);
		SourceText.append(buzType);
		SourceText.append(reserved01);
//		SourceText.append(reserved02);

		String EncodePKey = Md5.md5("", "UTF-8");
		SourceText.append(EncodePKey);
		String SignMsg = Md5.md5(SourceText.toString(), "UTF-8");

		UnionPayInfo payInfo = new UnionPayInfo();
		payInfo.setMerId(merId);
		payInfo.setOrderNo(orderNo);
		payInfo.setOrderAmount(orderAmount);
		payInfo.setCurrCode(curCode);
		payInfo.setOrderType(orderType);
		payInfo.setCallBackUrl(callBackUrl);
		payInfo.setFormUrl(FormUrl);
		payInfo.setBankCode(bankCode);
		payInfo.setLangType(langType);
		payInfo.setBuzType(buzType);
		payInfo.setReserved01(reserved01);
		payInfo.setSignMsg(SignMsg);
		result.setData(payInfo);

		// 新增支付订单
		FeePayOrder feePayOrder = new FeePayOrder();
		feePayOrder.setTradeNo(orderNo);
		feePayOrder.setAppId("wxe78c9f29d7113572");
		feePayOrder.setMchId(merId);
		feePayOrder.setNonceStr("");
		feePayOrder.setSignType("MD5");
		feePayOrder.setSign(SignMsg);
		feePayOrder.setTradeBody("电表充值");

		// 元转分
		String payMoney = getMoney(orderAmount);
		feePayOrder.setTotalFee(Integer.valueOf(payMoney));
		feePayOrder.setSpbillCreateIp(ip);
		feePayOrder.setNotifyUrl(callBackUrl);
		feePayOrder.setTradeType("");
		feePayOrder.setCreateTime(new Date());
		feePayOrder.setPayFlag(0);
		feePayOrder.setCno(cno);
		feePayOrderService.insert(feePayOrder);
		return JSON.toJSONString(result);
	}

	@SystemControllerLog(description = "银联异步通知接口")
	@RequestMapping("/unionPayNotice")
	@ResponseBody
	public void unionPayNotice(HttpServletRequest request,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String OrderNo = request.getParameter("OrderNo");      //商户支付流水号
		String PayNo = request.getParameter("PayNo");          //银联支付单号
		String PayAmount = request.getParameter("PayAmount");  //支付金额
		String CurrCode = request.getParameter("CurrCode");    //支付币种
		String SystemSSN = request.getParameter("SystemSSN");  //银联系统参考号
		String RespCode = request.getParameter("RespCode");    //交易响应码
		String SettDate = request.getParameter("SettDate");    //清算日期，格式（MMDD）
		String Reserved01 = request.getParameter("Reserved01");//保留域1
		String Reserved02 = request.getParameter("Reserved02");//保留域2
		String SignMsg = request.getParameter("SignMsg");      //签名数据，32位MD5加密

		Integer payMethod = FeeControlConstant.PayMethod.UNION_PAY.getCode();
		if (Reserved01.contains("PayType=WXPay")) {
			payMethod = FeeControlConstant.PayMethod.WE_CHAT.getCode();
		}

		// 根据订单号查询订单
		FeePayOrder feePayOrder = feePayOrderService.queryByTradeNo(OrderNo);
		String PKey = "";

		//检验数据是否正确
		if(SignMsg == null || SignMsg.length() == 0) {
//			out.write("OK");
			System.out.println("【返回的签名数据不完整!】");
			logger.info("【返回的签名数据不完整!】");
			return;
		}
		//组装签名数据，OrderNo值+PayNo值+PayAmount值+CurrCode值+SystemSSN值+RespCode值+SettDate值+Reserved01值+Reserved02值
		StringBuffer SourceText = new StringBuffer();
		SourceText.append(OrderNo);
		SourceText.append(PayNo);
		SourceText.append(PayAmount);
		SourceText.append(CurrCode);
		SourceText.append(SystemSSN);
		SourceText.append(RespCode);
		SourceText.append(SettDate);
		SourceText.append(Reserved01);
		SourceText.append(Reserved02);

		//对返回的信息进行加密签名
		String EncodePKey = Md5.md5(PKey, "UTF-8");
		SourceText.append(EncodePKey);
		String EncodeMsg = Md5.md5(SourceText.toString(), "UTF-8");

		//校验签名是否一致
		if(!SignMsg.equals(EncodeMsg)) {
//			out.write("OK");
			System.out.println("【签名数据不一致!】");
			logger.info("【签名数据不一致!】");
			return;
		}

		//将银联返回的支付结果更新到商户本地数据库
		// ----------------------------------------------------------------------------------------
		// 此部分操作由商户自行开发，建议将银联返回的EncodeMsg、SignMsg同时写入数据库
		//

		/***********************开始 - 打印接收到数据日志****************************/
		Enumeration<?> e = request.getParameterNames();
		StringBuilder ReqInfo = new StringBuilder ();
		while (e.hasMoreElements()) {
			String key = e.nextElement().toString().trim();
			String value = new String(request.getParameter(key).trim());
			ReqInfo.append(key + "=" + value + "&");
		}
		/***********************结束 - 打印接收到数据日志****************************/
		System.out.println(ReqInfo);

		// 输出支付结果给顾客
		if("00".equals(RespCode)) {
//			out.write("OK");
			// 元转分
			String payMoney = MathUtil.yuan2Fen(PayAmount);
			// 支付成功
			weixinService.processUnionCallback(OrderNo,payMoney,payMethod);
			System.out.println("【支付成功!系统参考号为："+SystemSSN+"】订单号:" + OrderNo);
		} else {
//			out.write("ERROR");
			System.out.println("【支付失败!响应码为："+RespCode+"】");
		}

	}

	@SystemControllerLog(description = "银联交易查询接口")
	@RequestMapping("/unionPayTransactionQuery")
	@ResponseBody
	public Map<String, Object> unionPayTransactionQuery(HttpServletRequest request,HttpServletResponse response) {

		response.setHeader("Access-Control-Allow-Origin", "*");
		logger.info("AppPayController:getByOpenId begin" );
		Map<String, Object> returnMap = Maps.newHashMap();
		String tranType  = "100";
		String javaCharset = "UTF-8";
		String version = "V60";
		String userId = "198";
		String pwd = "123!@#QAZ";
		String merId = "198";
		String payStatus = "2";
		String shoppingTime = "";
		String beginTime = "";
		String endTime = "";
		String orderNo = "";

		Map<String,String[]> map = request.getParameterMap();
		shoppingTime = map.get("ShoppingTime")[0];
		orderNo = map.get("OrderNo")[0];
//		String requestUrl = "https://www.gnetpg.com/GneteMerchantAPI/Trans.action";

		//对密码进行Md5加密
		pwd = Md5.md5(pwd, "UTF-8");

		//组装请求参数
		StringBuffer SourceText = new StringBuffer();
		SourceText.append("TranType=").append(tranType);
		SourceText.append("&JavaCharset=").append(javaCharset);
		SourceText.append("&Version=").append(version);
		SourceText.append("&UserId=").append(userId);
		SourceText.append("&Pwd=").append(pwd);
		SourceText.append("&MerId=").append(merId);
		SourceText.append("&PayStatus=").append(payStatus);
		SourceText.append("&ShoppingTime=").append(shoppingTime);
		SourceText.append("&BeginTime=").append(beginTime);
		SourceText.append("&EndTime=").append(endTime);
		SourceText.append("&OrderNo=").append(orderNo);

		String Param = SourceText.toString();

//		out.print(Param + "<br><br>");

		//交易结果查询接口地址
		//商户网站域名
		String MerDomain = "www.商户域名.com";
		String Url = "http://test.gnetpg.com:8089/GneteMerchantAPI/Trans.action";
		System.out.println("【访问的URL】" + Url);

		HttpUtils http = new HttpUtils();
		String Resp = http.doHttpPost(Url, Param, "UTF-8", MerDomain);

//		out.println("<BR><bR>【交易查询结果】\n" + Resp);

		if(Resp == null || Resp.length() == 0)
		{
//			out.write("<div class='InfoPage'><div class='InfoContext'>返回的交易结果为空！</div></div>");
			System.out.println("【返回的交易结果为空!】");
			return null;
		}
		//处理查询结果

		String Code = PayUtils.GetValue(Resp, "Code");
		String Message = "查询成功";
		String[] ResponseArrays = Resp.replace("\\n","#").split("#");
		if(Code != null && !"".equals(Code.trim()))
		{
			Message = PayUtils.GetValue(Resp, "Message");
		}else{
			Code = "1";
		}
		returnMap.put("Message",Message);
		returnMap.put("Code",Code);
		if(ResponseArrays.length >= 9) {
			returnMap.put("ShoppingDate", ResponseArrays[0]);
			returnMap.put("OrderAmount", ResponseArrays[1]);
			returnMap.put("RespCode", ResponseArrays[5]);
		}
		return returnMap;
	}

	/**
	 * 元转换成分
	 * @return
	 */
	public static String getMoney(String amount) {
		if(amount==null){
			return "";
		}
		// 金额转化为分为单位
		String currency =  amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额
		int index = currency.indexOf(".");
		int length = currency.length();
		Long amLong = 0l;
		if(index == -1){
			amLong = Long.valueOf(currency+"00");
		}else if(length - index >= 3){
			amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));
		}else if(length - index == 2){
			amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);
		}else{
			amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");
		}
		return amLong.toString();
	}

	private String SHA1(String decript) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	@SystemControllerLog(description = "获取openId接口")
	@RequestMapping("/getOpenId")
	@ResponseBody
	public String getOpenId(HttpServletResponse response, @RequestParam String accessCode) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<String> result = new Result<>();
		if (StringUtils.isEmpty(accessCode)) {
			result.error("accessCode不能为空");
			return JSON.toJSONString(result);
		}

		String openId;
		// 奥园的，测试时暂时写死
//		String openIdStr = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxe78c9f29d7113572&secret=419055bc03c5f12c31b0d667454ea5e4&code=" + accessCode + "&grant_type=authorization_code";

		// 正式环境用这个地址，取配置文件配置的公众号信息
		String openIdStr = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + secret + "&code=" + accessCode + "&grant_type=authorization_code";

		String resultOpenid = "-null";
		// 根据地址获取请求
		HttpGet requestOpenid = new HttpGet(openIdStr);//这里发送get请求
		// 获取当前客户端对象
		HttpClient httpClient = new DefaultHttpClient();
		// 通过请求对象获取响应对象
		try {
			HttpResponse responseOpenid = httpClient.execute(requestOpenid);
			// 判断网络连接状态码是否正常(0--200都数正常)
			if (responseOpenid != null && responseOpenid.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				resultOpenid = EntityUtils.toString(responseOpenid.getEntity(), "utf-8");
			}
		} catch (IOException e) {
			logger.error("获取openId接口异常:",e);
		}
		logger.info("weixin-openid-：" + resultOpenid);
		JSONObject jsonObject = JSONObject.fromObject(resultOpenid);
		openId = jsonObject.getString("openid");
		if (StringUtils.isEmpty(openId)) {
			result.error("微信openId返回值为空");
			return JSON.toJSONString(result);
		}

		result.setData(openId);
		return JSON.toJSONString(result);
	}

	/**
	 * 获取用户信息接口
	 * @param response
	 * @param accessCode
	 * @return
	 */
	@SystemControllerLog(description = "获取用户信息接口")
	@RequestMapping("/getWxUserInfo")
	@ResponseBody
	public String getWxUserInfo(HttpServletResponse response, @RequestParam String accessCode) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<Map> result = new Result<>();
		if (StringUtils.isEmpty(accessCode)) {
			result.error("accessCode不能为空");
			return JSON.toJSONString(result);
		}

		Map paramsMap = this.getAccessToken_charger(accessCode);
		String accessToken =  ((String)paramsMap.get("access_token"));
		String openId = ((String)paramsMap.get("openid"));
		String urlStr = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
		try {
			//获取用户信息
			String resultInfo = this.sendUrlRequest(urlStr);
			JSONObject jsonInfo = JSONObject.fromObject(resultInfo);
			String nickName = jsonInfo.getString("nickname");
			String headimgurl = jsonInfo.getString("headimgurl");

			nickName = this.filterEmoji(nickName, "");

			Map map = new HashMap();
			map.put("name",nickName == null ? "" : nickName);
			map.put("openId",openId);
			map.put("url",headimgurl == null ? "" : headimgurl);
			result.setData(map);
			return JSON.toJSONString(result);
		} catch (Exception e) {
			logger.error("获取用户信息错误",e);
		}
		result.error("未找到用户信息");
		return JSON.toJSONString(result);
	}

	/**
	 * emoji表情替换
	 *
	 * @param source 原字符串
	 * @param slipStr emoji表情替换成的字符串
	 * @return 过滤后的字符串
	 */
	public static String filterEmoji(String source,String slipStr) {
		if(!StringUtil.isEmpty(source)){
			return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", slipStr);
		}else{
			return source;
		}
	}


	/**
	 * 根据URL路径，获取服务器返回的字符串
	 */
	public String sendUrlRequest(String urlStr)throws Exception{
		HttpURLConnection url_con=null;
		String tempStr = null;
		try{
			URL url=new URL(urlStr);
			StringBuffer bankXmlBuffer=new StringBuffer();
			//创建URL连接，提交到数据，获取返回结果
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setRequestProperty("User-Agent","directclient");
			PrintWriter out=new PrintWriter(new OutputStreamWriter(connection.getOutputStream(),"GBK"));
			out.println();
			out.close();
			BufferedReader in=new BufferedReader(new InputStreamReader(connection
					.getInputStream(),"UTF-8"));
			String inputLine;
			while((inputLine = in.readLine())!=null){
				bankXmlBuffer.append(inputLine);
			}
			in.close();
			tempStr = bankXmlBuffer.toString();
		}
		catch(Exception e)
		{
			logger.error("发送GET错误",e);
			e.printStackTrace();
		}finally{
			if(url_con!=null)
				url_con.disconnect();
		}
		return tempStr;
	}

	/**
	 * 网页授权
	 * @param code
	 * @return
	 */
	Map getAccessToken_charger(String code) {
		String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
		String str = WxHttpUtil.sendGet(tokenUrl,"grant_type=authorization_code&appid=" + appId + "&secret=" + secret + "&code="+code,"utf-8");
		logger.info("微信网页授权获取assess_token："+str);
		Map<String, Object>  map = JSON.parseObject(str, Map.class);
		return map;
	}

	String getAccessTokenP() {
		String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token";
		Date now =new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
		String date = sdf.format(now);
		long current = 0;
		try {
			current = sdf.parse(date).getTime();
			if (access_token_p == "" || current > expires_p){
				//微信token7200秒过期，为了防止网络延时导致提前过期，系统设置为提前5分钟后过期
				String str = WxHttpUtil.sendGet(tokenUrl,"grant_type=client_credential&appid=wx7f484e7b57ce4a98&secret=e47ceafba85961d7a2e5f45d318a5a8e","utf-8");
				logger.info("获取assess_token："+str);
				Map<String, Object> map = JSON.parseObject(str, Map.class);
				expires_p = current +(((Integer)(map.get("expires_in"))*1000-60*5*1000));
				access_token_p = ((String)map.get("access_token"));
			}
		} catch (ParseException e) {
			logger.error("错误信息",e);
		}

		return access_token_p;
	}

	@SystemControllerLog(description = "获取微信签名和参数")
	@RequestMapping("/GetWeiXinSignCharger")
	@ResponseBody
	public String GetWXParamsCharging(HttpServletResponse response,HttpServletRequest request) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result result = new Result();
		Map<String,String[]> map = request.getParameterMap();
		Map<String, Object> returnMap = new HashMap<>();

		// 1、获取Ticket
		String jsApiTicket = weixinService.getJsapiTicket();

		// 2、时间戳和随机字符串
		String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳

		// 3、获取url
		String url = map.get("localurl")[0];

		// 4、将参数排序并拼接字符串
		String str = "jsapi_ticket="+jsApiTicket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
		logger.info("weixin"+"-GetWeiXinSign:"+str);
		// 5、将字符串进行sha1加密
		String signature =SHA1(str);
		logger.info("weixin"+"-signature:"+signature);
		try {
			returnMap.put("nonceStr",noncestr);
			returnMap.put("timestamp",timestamp);
			returnMap.put("signature",signature);
		} catch (Exception e) {
			logger.error("错误信息",e);
			returnMap.put("resultCode", ResultCode.Fail.getValue());
			returnMap.put("msg", "失败");
			result.error("获取微信签名和参数异常");
			return  JSON.toJSONString(result);
		}
		result.setData(returnMap);
		return JSON.toJSONString(result);
	}

	@SystemControllerLog(description = "支付宝获取用户信息")
	@RequestMapping(value = "/getAlipayUserInfo")
	@ResponseBody
	public String getAlipayUserInfo(HttpServletResponse response,@RequestParam String authCode) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<AlipayUserInfo> result = new Result<>();
		if (StringUtils.isEmpty(authCode)) {
			result.error("authCode不能为空");
			return JSON.toJSONString(result);
		}

		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", alipayAppId, alipayAppPrivateKey, "json", CHARSET, alipayPublicKey, "RSA2"); //获得初始化的AlipayClient
		AlipaySystemOauthTokenRequest tokenRequest = new AlipaySystemOauthTokenRequest();
		tokenRequest.setCode(authCode);
		tokenRequest.setGrantType("authorization_code");
		try {
			// 获取用户信息授权
			AlipaySystemOauthTokenResponse tokenResponse = alipayClient.execute(tokenRequest);

			// 获取用户信息
			AlipayUserInfoShareRequest aliPayRequest = new AlipayUserInfoShareRequest();
			AlipayUserInfoShareResponse shareResponse = alipayClient.execute(aliPayRequest, tokenResponse.getAccessToken());
			String userId = shareResponse.getUserId();
			String nickName = shareResponse.getNickName();
			nickName = this.filterEmoji(nickName, "");
			String avatar = shareResponse.getAvatar();

			AlipayUserInfo userInfo = new AlipayUserInfo();
			userInfo.setUserId(userId);
			userInfo.setNickName(nickName == null ? "" : nickName);
			userInfo.setAvatar(avatar == null ? "" : avatar);
			result.setData(userInfo);
		} catch (AlipayApiException e) {
			logger.error("支付宝获取用户昵称异常",e);
		}

		return JSON.toJSONString(result);
	}


	@SystemControllerLog(description = "支付宝支付")
	@RequestMapping(value = "/aliPay")
	@ResponseBody
	public String aliPay(HttpServletResponse response,@RequestParam Integer priceId,@RequestParam String alipayUserId) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<String> result = new Result<>();
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", alipayAppId, alipayAppPrivateKey, "json", CHARSET, alipayPublicKey, "RSA2"); //获得初始化的AlipayClient
		AlipayTradeCreateRequest alipayRequest = new AlipayTradeCreateRequest();
		String notifyUrl = host + ALIPAY_NOTIFY_URL;
		logger.info("支付宝支付回调地址：" + notifyUrl);
		alipayRequest.setNotifyUrl(notifyUrl);//在公共参数中设置回跳和通知地址

		ChargingPayCheme chargingPayCheme = chargingPayChemeService.selectByPrimaryKey(priceId);
		if (chargingPayCheme == null) {
			result.error("不存在此价格方案");
			return JSON.toJSONString(result);
		}

		BigDecimal money = chargingPayCheme.getMoney();
		String out_trade_no = UuidUtil.getUuid();
		com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
		jsonObject.put("out_trade_no",out_trade_no);
		jsonObject.put("total_amount",MathUtil.setPrecision(money));
		jsonObject.put("subject","充电桩充电");
		jsonObject.put("buyer_id",alipayUserId);
		alipayRequest.setBizContent(JSON.toJSONString(jsonObject));//填充业务参数
		try {
			AlipayTradeCreateResponse execute = alipayClient.execute(alipayRequest);
			if (execute.isSuccess()) {
				String tradeNo = execute.getTradeNo();
				result.setData(tradeNo);
				logger.info(tradeNo);
			} else {
				logger.info("支付宝支付调用失败");
			}
		} catch (AlipayApiException e) {
			result.error("创建交易订单异常");
			logger.error("创建订单异常",e);
		}

		return JSON.toJSONString(result);
	}


	@SystemControllerLog(description = "支付宝订单状态查询")
	@RequestMapping(value = "/aliPayOrderStatusQuery")
	@ResponseBody
	public String aliPayOrderStatusQuery(HttpServletResponse response,@RequestParam String tradeNo) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<Integer> result = new Result<>();
		if (StringUtils.isEmpty(tradeNo)) {
			result.error("tradeNo不能为空");
			return JSON.toJSONString(result);
		}

		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", alipayAppId, alipayAppPrivateKey, "json", CHARSET, alipayPublicKey, "RSA2"); //获得初始化的AlipayClient
		AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
		com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
		jsonObject.put("trade_no",tradeNo);
		alipayRequest.setBizContent(JSON.toJSONString(jsonObject));//填充业务参数
		try {
			AlipayTradeQueryResponse execute = alipayClient.execute(alipayRequest);
			logger.info("支付宝订单状态查询返回：AlipayTradeQueryResponse=" + JSON.toJSONString(execute));
			if (execute.isSuccess()) {
				String tradeStatus = execute.getTradeStatus();
				if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
					result.setData(1);
				} else {
					result.setData(0);
				}
			} else {
				logger.info("支付宝订单状态第一次查询调用失败");
				String subCode = execute.getSubCode();
				if ("ACQ.SYSTEM_ERROR".equals(subCode)) {
					// 重新发起一次请求
					AlipayTradeQueryResponse execute2 = alipayClient.execute(alipayRequest);
					logger.info("支付宝第二次订单状态查询返回：AlipayTradeQueryResponse=" + JSON.toJSONString(execute2));
					if (execute2.isSuccess()) {
						String tradeStatus = execute2.getTradeStatus();
						if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
							result.setData(1);
						} else {
							result.setData(0);
						}
					}
				}
			}
		} catch (AlipayApiException e) {
			result.error("创建交易订单异常");
			logger.error("创建订单异常",e);
		}

		return JSON.toJSONString(result);
	}


	@SystemControllerLog(description = "支付宝支付回调通知")
	@RequestMapping(value = "/aliPayCallback")
	@ResponseBody
	public void aliPayCallback(HttpServletRequest request,HttpServletResponse response) throws IOException, AlipayApiException {
		response.setHeader("Access-Control-Allow-Origin", "*");

		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		logger.info("支付宝回调通知参数：" + JSON.toJSONString(requestParams));
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号
		String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		String totalAmount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
		String sellerId = new String(request.getParameter("seller_id").getBytes("ISO-8859-1"),"UTF-8");
		String appId = new String(request.getParameter("app_id").getBytes("ISO-8859-1"),"UTF-8");

		//支付宝交易号
		String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//交易状态
		String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		//计算得出通知验证结果
		//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
		boolean verify_result = AlipaySignature.rsaCheckV1(params, alipayPublicKey, CHARSET, "RSA2");

		if(verify_result){
			//验证成功
			//请在这里加上商户的业务逻辑程序代码
			alipayService.orderNotify(outTradeNo,totalAmount,sellerId,appId,tradeStatus);
			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）
			response.getWriter().write("success");
			response.getWriter().flush();
			response.getWriter().close();
		} else {
			// 验证失败
			response.getWriter().write("fail");
			response.getWriter().flush();
			response.getWriter().close();
		}
	}

}