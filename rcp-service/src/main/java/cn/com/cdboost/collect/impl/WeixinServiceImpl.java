package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.constant.FeeControlConstant;
import cn.com.cdboost.collect.constant.SmsConstant;
import cn.com.cdboost.collect.dto.BindDeviceInfo;
import cn.com.cdboost.collect.dto.DeviceInfo4WeChat;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.enums.DeviceType;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.*;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.*;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zc
 * @desc
 * @create 2017-12-08 15:20
 **/
@Service("weixinService")
public class WeixinServiceImpl implements WeixinService {
    private static final Logger logger = LoggerFactory.getLogger(WeixinServiceImpl.class);
    private static final String orderQueryUrl = "https://api.mch.weixin.qq.com/pay/orderquery";
    // 订单付款成功通知模板消息id
    private static final String PAY_SUCCESS_TEMPLATE_ID="Vta1r56TcZhEhQ2Y2DRn2HaL5h5Qljb6F4fBluaoKpw";
    // 设备远程充值成功模板消息id
    private static final String REMOTE_PAY_SUCCESS_TEMPLATE_ID="6SMhItRJtQATHb-Dnad7JBCjBjs7pNKOn30PePG6du8";
    // 告警提醒模板消息id
    private static final String ALARM_TEMPLATE_ID="3ORgmp-b75EGs52cSHytoy3-kUSxqZwnARUmFJt4QiM";
    // 发送消息模板url
    private final String SEND_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
    // 微信access_token 缓存key
    public static final String WEXIN_ACCESS_TOKEN = "WEXIN_ACCESS_TOKEN";
    // 微信jsapi ticket 缓存key
    public static final String WEXIN_JSAPI_TICKET = "WEXIN_JSAPI_TICKET";

    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private CustomerDevMapService customerDevMapService;
    @Autowired
    private CustomerPhoneBindService customerPhoneBindService;
    @Autowired
    private CustomerWxBindService customerWxBindService;
    @Autowired
    private DeviceInfoService deviceInfoService;
    @Autowired
    private FeePayService feePayService;
    @Autowired
    private DeviceMeterParamService deviceMeterParamService;
    @Autowired
    private MonthSumDataService monthSumDataService;
    @Autowired
    private FeePayOrderService feePayOrderService;
    @Autowired
    private AliyunSmsService aliyunSmsService;
    @Autowired
    private SmsSchemeService smsSchemeService;

    @Value("${smsCodeContent}")
    private String smsCodeContent;

    @Value("${paySuccessTitle}")
    private String paySuccessTitle;

    @Value("${paySuccessRemark}")
    private String paySuccessRemark;

    @Value("${remoteSuccessTitle}")
    private String remoteSuccessTitle;

    @Value("${remoteSuccessRemark}")
    private String remoteSuccessRemark;

    @Value("${appId}")
    private String appId;
    @Value("${secret}")
    private String secret;
    @Value("${mchId}")
    private String mchId;
    @Value("${partnerkey}")
    private String partnerkey;


    @Override
    public Map<String, Object> orderQuery(String tradeNo) {

        Map<String, Object> returnMap = Maps.newHashMap();
        String nonceStr = StringUtil.getNonceStr();
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", appId);
        packageParams.put("mch_id", mchId);
        packageParams.put("out_trade_no", tradeNo);
        packageParams.put("nonce_str", nonceStr);

        RequestHandler reqHandler = new RequestHandler(null, null);
        reqHandler.init(appId, secret, partnerkey);

        String sign = reqHandler.createSign(packageParams);

        String xml = "<xml>"
                + "<appid>" + appId + "</appid>"
                + "<mch_id>"+ mchId + "</mch_id>"
                + "<out_trade_no>" + tradeNo + "</out_trade_no>"
                + "<nonce_str>" + nonceStr + "</nonce_str>"
                + "<sign>" + sign + "</sign>"
                + "</xml>";


        logger.info("orderQuery 请求参数:" + xml);

        returnMap = GetWxOrderno.weixinRequest(orderQueryUrl, xml);

        logger.info("orderQuery 返回结果:" + JSONObject.fromObject(returnMap));
        return returnMap;
    }

    @Override
    public void validateAndBindWeChat(WeChatBindVo bindVo) {
        // 校验验证码
        String mobilePhone = bindVo.getMobilePhone();
        String smsCode = bindVo.getSmsCode();
        aliyunSmsService.verifySmsCode(mobilePhone,smsCode);

        String meterUserNo = bindVo.getMeterUserNo();
        CustomerDevMap devMap = customerDevMapService.queryByMeterUserNo(bindVo.getDeviceType(), meterUserNo);
        if (devMap == null) {
            throw new BusinessException("该设备不存在，可能已删除");
        }
        CustomerPhoneBind customerPhoneBind = customerPhoneBindService.queryByParam(devMap.getCustomerNo(), mobilePhone);
        if (customerPhoneBind == null) {
            throw new BusinessException("系统不存在该手机号的绑定信息");
        }

        // 查询微信绑定记录是否已存在
        CustomerWxBind param = new CustomerWxBind();
        param.setCustomerNo(devMap.getCustomerNo());
        param.setMobilePhone(mobilePhone);
        param.setDeviceType(bindVo.getDeviceType());
        param.setMeterUserNo(meterUserNo);
        param.setOpenId(bindVo.getOpenId());
        CustomerWxBind wxBind = customerWxBindService.selectOne(param);
        if (wxBind != null) {
            // 处理重复请求
            return;
        }

        CustomerWxBind band = new CustomerWxBind();
        band.setCustomerNo(devMap.getCustomerNo());
        band.setMobilePhone(mobilePhone);
        band.setDeviceType(bindVo.getDeviceType());
        band.setMeterUserNo(meterUserNo);
        CustomerWxBind customerWxBind = customerWxBindService.selectOne(band);
        if (customerWxBind != null) {
            String openId = customerWxBind.getOpenId();
            if (!openId.equals(bindVo.getOpenId())) {
                throw new BusinessException("该手机号已被另一个微信号绑定");
            }
        }

        band.setOpenId(bindVo.getOpenId());
        band.setRemark(bindVo.getRemark());
        customerWxBindService.insertSelective(band);
    }

    @Override
    public void bindWeChat(String meterUserNo, String deviceType, String openId, String remark) {
        CustomerDevMap devMap = customerDevMapService.queryByMeterUserNo(deviceType,meterUserNo);
        if (devMap == null) {
            throw new BusinessException("该表计户号在系统中不存在");
        }

        List<CustomerWxBind> wxBands = customerWxBindService.queryByOpenId(openId);
        CustomerWxBind customerWxBind = wxBands.get(0);
        String customerNo = devMap.getCustomerNo();
        String mobilePhone = customerWxBind.getMobilePhone();
        CustomerPhoneBind customerPhoneBind = customerPhoneBindService.queryByParam(customerNo, mobilePhone);
        if (customerPhoneBind == null) {
            throw new BusinessException("系统不存在该手机号的绑定信息");
        }

        CustomerWxBind band = new CustomerWxBind();
        band.setDeviceType(deviceType);
        band.setCustomerNo(customerNo);
        band.setMeterUserNo(meterUserNo);
        band.setMobilePhone(mobilePhone);
        band.setOpenId(openId);
        // 查询是否已绑定
        CustomerWxBind wxBind = customerWxBindService.selectOne(band);
        if (wxBind != null) {
            throw new BusinessException("该设备已被绑定");
        }

        // 真正去绑定
        band.setCreateTime(new Date());
        band.setRemark(remark);
        customerWxBindService.insertSelective(band);
    }

    @Override
    public List<BindDeviceInfo> getBindInfoList(String openId, String deviceType) {
        List<BindDeviceInfo> dataList = Lists.newArrayList();
        List<CustomerWxBind> wxBinds = customerWxBindService.queryByDeviceType(openId,deviceType);
        if (CollectionUtils.isEmpty(wxBinds)) {
            return dataList;
        }

        // 分组
        ImmutableMap<String, CustomerWxBind> wxBindImmutableMap = Maps.uniqueIndex(wxBinds, new Function<CustomerWxBind, String>() {
            @Nullable
            @Override
            public String apply(@Nullable CustomerWxBind customerWxBind) {
                return customerWxBind.getMeterUserNo();
            }
        });
        Set<String> customerNoSet = Sets.newHashSet();
        Set<String> meterUserNoSet = Sets.newHashSet();
        for (CustomerWxBind wxBind : wxBinds) {
            customerNoSet.add(wxBind.getCustomerNo());
            meterUserNoSet.add(wxBind.getMeterUserNo());
        }

        List<CustomerDevMap> devMaps = customerDevMapService.queryListByDeviceType(deviceType,meterUserNoSet);
        if (CollectionUtils.isEmpty(devMaps)) {
            return dataList;
        }

        List<String> cnoList = Lists.newArrayList();
        for (CustomerDevMap devMap : devMaps) {
            cnoList.add(devMap.getCno());
        }

        List<CustomerInfo> customerInfos = customerInfoService.batchQueryByCustomerNos(customerNoSet);
        // 按照分组
        ImmutableMap<String, CustomerInfo> immutableMap = Maps.uniqueIndex(customerInfos, new Function<CustomerInfo, String>() {
            @Nullable
            @Override
            public String apply(@Nullable CustomerInfo customerInfo) {
                return customerInfo.getCustomerNo();
            }
        });

        for(CustomerDevMap devMap : devMaps){
            BindDeviceInfo info = new BindDeviceInfo();
            CustomerInfo customerInfo = immutableMap.get(devMap.getCustomerNo());
            info.setCustomerName(customerInfo.getCustomerName());
            String meterUserNo = devMap.getMeterUserNo();
            info.setMeterUserNo(meterUserNo);
            CustomerWxBind customerWxBind = wxBindImmutableMap.get(meterUserNo);
            info.setId(customerWxBind.getId());

            String cno = devMap.getCno();
            info.setMeterNo(cno);
//            BigDecimal remainAmount = devMap.getRemainAmount();
            BigDecimal remainAmount = BigDecimal.ZERO;
            if (remainAmount != null) {
                BigDecimal decimal = MathUtil.setPrecision(remainAmount);
                info.setRemainAmount(String.valueOf(decimal));
                int result = MathUtil.compareTo(remainAmount, devMap.getAlarmThreshold());
                if (result != 1) {
                    // 剩余金额小于或等于告警阈值
                    info.setUnderAlarm("1");
                } else {
                    info.setUnderAlarm("0");
                }
            } else {
                info.setRemainAmount("");
                info.setUnderAlarm("0");
            }

            info.setRefreshDate("");
            dataList.add(info);
        }

        return dataList;
    }

    @Override
    public DeviceInfo4WeChat queryDeviceInfo(String deviceType, String meterUserNo) {
        // 查询cno
        CustomerDevMap devMap = customerDevMapService.queryByMeterUserNo(deviceType, meterUserNo);
        String cno = devMap.getCno();
        DeviceInfo deviceInfo = deviceInfoService.queryDeviceInfoByCno(cno);
        CustomerInfo customerInfo =customerInfoService.queryByCustomerNo(devMap.getCustomerNo());
        DeviceMeterParam deviceMeterParam = deviceMeterParamService.queryEffectiveParamByCno(cno);

        DeviceInfo4WeChat deviceInfo4WeChat = new DeviceInfo4WeChat();
        deviceInfo4WeChat.setJzqCno(deviceMeterParam.getJzqCno());
        deviceInfo4WeChat.setUserName(customerInfo.getCustomerName());
        deviceInfo4WeChat.setCustomerNo(customerInfo.getCustomerNo());
        deviceInfo4WeChat.setMeterUserNo(meterUserNo);
        deviceInfo4WeChat.setOrgNo(deviceInfo.getOrgNo());
        deviceInfo4WeChat.setLocalControl(deviceMeterParam.getLocalControl());
        deviceInfo4WeChat.setCustomerAddr(customerInfo.getCustomerAddr());
//        BigDecimal remainAmount = devMap.getRemainAmount();
        BigDecimal remainAmount = BigDecimal.ZERO;
        if (remainAmount != null) {
            BigDecimal decimal = MathUtil.setPrecision(remainAmount);
            deviceInfo4WeChat.setRemainAmount(decimal.toString());
        } else {
            deviceInfo4WeChat.setRemainAmount("");
        }

//        Date amountCollectTime = devMap.getAmountCollectTime();
        Date amountCollectTime = new Date();
        if (amountCollectTime != null) {
            String dateStr = DateUtil.formatDate(amountCollectTime);
            deviceInfo4WeChat.setAmountCollectTime(dateStr);
        } else {
            deviceInfo4WeChat.setAmountCollectTime("");
        }

        deviceInfo4WeChat.setIsOn(deviceInfo.getIsOn());

        // 查询最近半年的用电量
        List<MonthSumData> list = monthSumDataService.getNMonthSumDataByCno(cno, 6);
        List<BigDecimal> monthSumDatas = Lists.newArrayList();
        List<String> yearMonthDatas = Lists.newArrayList();
        if(!CollectionUtils.isEmpty(list)){
            // 设置本月用电量
            deviceInfo4WeChat.setCurrentMonthSumData(list.get(0).getSumData());
            for(MonthSumData monthSumData: list){
                BigDecimal decimal = MathUtil.setPrecision(monthSumData.getSumData());
                monthSumDatas.add(decimal);

                Integer sumYear = monthSumData.getSumYear();
                Integer sumMonth = monthSumData.getSumMonth();
                StringBuffer yearMonth = new StringBuffer();
                yearMonth.append(sumYear);
                yearMonth.append("-");
                if (sumMonth < 10) {
                    yearMonth.append("0" + sumMonth);
                } else {
                    yearMonth.append(sumMonth);
                }
                yearMonthDatas.add(yearMonth.toString());
            }
            int size = list.size();
            if (size < 6) {
                // 补全剩余的月份
                MonthSumData monthSumData = list.get(size - 1);
                Integer sumYear = monthSumData.getSumYear();
                Integer sumMonth = monthSumData.getSumMonth();
                int length = 6-size;
                for (int i=0; i< length; i++) {
                    StringBuffer sb = new StringBuffer();
                    // 先减一个月
                    sumMonth--;
                    if (sumMonth == 0) {
                        sumMonth = 12;
                        sumYear = sumYear-1;
                        sb.append(sumYear).append("-").append(sumMonth);
                    } else {
                        if (sumMonth < 10) {
                            sb.append(sumYear).append("-").append("0").append(sumMonth);
                        } else {
                            sb.append(sumYear).append("-").append(sumMonth);
                        }
                    }

                    monthSumDatas.add(MathUtil.setPrecision(BigDecimal.ZERO));
                    yearMonthDatas.add(sb.toString());
                }
            }
        } else {
            int currentMonth = DateUtil.getCurrentMonth();
            int currentYear = DateUtil.getCurrentYear();
            // 为空的情况
            BigDecimal decimal = MathUtil.setPrecision(BigDecimal.ZERO);
            deviceInfo4WeChat.setCurrentMonthSumData(decimal);
            for (int i = 0; i < 6; i++) {
                monthSumDatas.add(decimal);

                StringBuffer sb = new StringBuffer();
                // 设置x横坐标
                int temp = currentMonth-1;
                if (temp == 0) {
                    sb.append(currentYear).append("-").append("0").append(currentMonth);
                    currentMonth = 12;
                    currentYear=currentYear-1;
                } else {
                    if (currentMonth < 10) {
                        sb.append(currentYear).append("-").append("0").append(currentMonth);
                    } else {
                        sb.append(currentYear).append("-").append(currentMonth);
                    }
                    currentMonth--;
                }

                yearMonthDatas.add(sb.toString());
            }
        }

        // 倒叙排列
        Collections.reverse(monthSumDatas);
        Collections.reverse(yearMonthDatas);
        deviceInfo4WeChat.setMonthSumData(monthSumDatas);
        deviceInfo4WeChat.setYearMonthData(yearMonthDatas);

        // 查询该设备是否存在未下发的缴费记录
        List<FeePay> feePays = feePayService.getLastNFeePay(cno, 1);
        deviceInfo4WeChat.setMeterPayStatus(1);
        if (!CollectionUtils.isEmpty(feePays)) {
            FeePay feePay = feePays.get(0);
            Integer writeMeter = feePay.getWriteMeter();
            if (writeMeter == 0) {
                deviceInfo4WeChat.setMeterPayStatus(0);
            }
        }

        return deviceInfo4WeChat;
    }

    @Override
    public void sendWeChatPaySuccess(List<WxSendPaySuccessParam> successParams) {
        logger.info("微信支付成功，发送微信模板消息开始SendPaySuccessParam=" + com.alibaba.fastjson.JSON.toJSONString(successParams));
        try {
            for (WxSendPaySuccessParam param : successParams) {
                Template template = constructTemplate(param);
                String accessToken = getAccessToken();
                logger.info("获取到的accessToken=" + accessToken);
                String url = SEND_MESSAGE_URL.replace("ACCESS_TOKEN", accessToken);
                boolean result = sendTemplateMsg(url, template);
            }
        } catch (Exception e) {
            logger.error("发送微信模板信息异常：",e);
        }
    }

    /**
     * 构造发送微信模板参数
     * @param param
     * @return
     */
    private Template constructTemplate(WxSendPaySuccessParam param) {
        Template template = new Template();
        template.setTemplateId(PAY_SUCCESS_TEMPLATE_ID);
        template.setTopColor("#00DD00");
        template.setToUser(param.getOpenId());
        template.setUrl("");

        List<TemplateParam> params = Lists.newArrayList();
        String cno = param.getCno();
        String deviceType = cno.substring(0, 2);
        String message = DeviceType.getMessageByCode(deviceType);
        String feeType = FeeControlConstant.FeeType.getDescByCode(deviceType);

        String title = String.format(paySuccessTitle,param.getCustomerName(),feeType);
        params.add(new TemplateParam("first",title,"#FF3333"));
        String payTime = param.getPayTime();
        String time = this.converTime(payTime);
        // 支付时间
        params.add(new TemplateParam("keyword1",time,"#0044BB"));
        // 支付金额
        // 分转元
        String str = MathUtil.fen2yuan(param.getPayMoney());
        String payMoneyStr = str + "元";
        params.add(new TemplateParam("keyword2",payMoneyStr,"#0044BB"));

        // 支付方式
        Integer payMethod = param.getPayMethod();
        String payMethodStr = FeeControlConstant.PayMethod.getDescByCode(payMethod);
        params.add(new TemplateParam("keyword3",payMethodStr,"#0044BB"));

        // 流水号
        params.add(new TemplateParam("keyword4",param.getSerialNum(),"#0044BB"));

        String deviceNo = CNoUtil.getNo(cno);
        String remark = String.format(paySuccessRemark,message,deviceNo);
        params.add(new TemplateParam("remark",remark,"#AAAAAA"));
        template.setTemplateParamList(params);
        return template;
    }

    @Override
    public void sendWeChatRechargeSuccess(DeviceRechargeSuccessParam param) {
        logger.info("远程充值成功，发送微信模板消息开始DeviceRechargeSuccessParam=" + com.alibaba.fastjson.JSON.toJSONString(param));
        String cno = param.getCno();
        CustomerDevMap devMap = customerDevMapService.queryByCno(cno);
        // 查询该表所关联的所有微信
        List<CustomerWxBind> wxBinds = customerWxBindService.queryByParams(devMap.getMeterUserNo(), devMap.getDeviceType());
        // 给所有微信发送消息
        for (CustomerWxBind wxBind : wxBinds) {
            Template template = getRemoteTemplate(param, wxBind.getOpenId());
            String accessToken = getAccessToken();
            logger.info("获取到的accessToken=" + accessToken);
            String url = SEND_MESSAGE_URL.replace("ACCESS_TOKEN", accessToken);
            boolean result = sendTemplateMsg(url, template);
        }
    }

    private Template getRemoteTemplate(DeviceRechargeSuccessParam param,String openId) {
        Template template = new Template();
        template.setTemplateId(REMOTE_PAY_SUCCESS_TEMPLATE_ID);
        template.setTopColor("#00DD00");
        template.setToUser(openId);
        template.setUrl("");

        List<TemplateParam> params = Lists.newArrayList();
        String cno = param.getCno();
        String deviceType = cno.substring(0, 2);
        String message = DeviceType.getMessageByCode(deviceType);
        String deviceNo = CNoUtil.getNo(cno);
        Integer payMethod = param.getPayMethod();
        String payMethodStr = FeeControlConstant.PayMethod.getDescByCode(payMethod);

        String feeType = FeeControlConstant.FeeType.getDescByCode(deviceType);
        String payMoney = param.getPayMoney();
        List<Object> objectParams = Lists.newArrayList();
        objectParams.add(param.getCustomerName());
        objectParams.add(payMethodStr);
        objectParams.add(payMoney);
        objectParams.add(feeType);
        objectParams.add(message);
        objectParams.add(deviceNo);
        Object[] objects = objectParams.toArray(new Object[objectParams.size()]);
        String title = String.format(remoteSuccessTitle,objects);
        params.add(new TemplateParam("first",title,"#FF3333"));

        String payMoneyStr = payMoney + "元";
        params.add(new TemplateParam("keyword1",payMoneyStr,"#0044BB"));
        String payTime = param.getPayTime();
        params.add(new TemplateParam("keyword2",payTime,"#0044BB"));

        String remark = remoteSuccessRemark;
        params.add(new TemplateParam("remark",remark,"#AAAAAA"));
        template.setTemplateParamList(params);
        return template;
    }

    @Override
    public void sendWeChatAlarmMessage(List<WeChatAlarmParam> alarmParams) {
        logger.info("发送微信告警模板消息开始DeviceRechargeSuccessParam=" + com.alibaba.fastjson.JSON.toJSONString(alarmParams));
        if (CollectionUtils.isEmpty(alarmParams)) {
            return;
        }
        try {
            List<SmsSchemeUpdateParam> updateList = Lists.newArrayList();
            for (WeChatAlarmParam param : alarmParams) {
                String customerNo = param.getCustomerNo();
                List<String> openIds = param.getOpenIds();
                for (String openId : openIds) {
                    Template template = getAlarmTemplate(param, openId);
                    String accessToken = getAccessToken();
                    logger.info("获取到的accessToken=" + accessToken);
                    String url = SEND_MESSAGE_URL.replace("ACCESS_TOKEN", accessToken);
                    boolean result = sendTemplateMsg(url, template);
                    if (result) {
                        // 发送成功
                        SmsSchemeUpdateParam updateParam = new SmsSchemeUpdateParam();
                        updateParam.setCustomerNo(customerNo);
                        updateParam.setObjType(SmsConstant.SmsSchemeType.WEI_XIN.getType());
                        updateParam.setSendObj(openId);

                        Date date = new Date();
                        updateParam.setLastSendTime(date);
                        updateParam.setSmsscStatus(1);
                        updateList.add(updateParam);
                    }
                }
            }

            // 批量更新消息提醒方案表
            if (!CollectionUtils.isEmpty(updateList)) {
                int count = smsSchemeService.batchUpdate(updateList);
                logger.info("批量更新消息提醒方案表count=" + count);
            }
        } catch (Exception e) {
            logger.error("发送微信告警模板异常:",e);
            throw new BusinessException("发送微信模板通知异常");
        }
    }

    private Template getAlarmTemplate(WeChatAlarmParam param,String openId) {
        Template template = new Template();
        template.setTemplateId(ALARM_TEMPLATE_ID);
        template.setTopColor("#00DD00");
        template.setToUser(openId);
        template.setUrl("");

        List<TemplateParam> params = Lists.newArrayList();
        String message = DeviceType.getMessageByCode(param.getDeviceType());
        String collectTime = param.getCollectTime();
        StringBuffer title = new StringBuffer();
        title.append("尊敬的"+param.getCustomerName()+"用户您好,");
        title.append("截止到"+ collectTime +","+ message +"的"+ message +"剩余金额已低于");
        title.append(param.getAlarmThreshold() + "元，请及时充值");
        params.add(new TemplateParam("first",title.toString(),"#FF3333"));
        String remainAmount = param.getRemainAmount();
        String tempStr = MathUtil.setPrecision(remainAmount, 2);
        String remainAmountStr = tempStr + "元";
        params.add(new TemplateParam("keyword1",remainAmountStr,"#0044BB"));
        params.add(new TemplateParam("keyword2",collectTime,"#0044BB"));

        String remark = "感谢您的使用。";
        params.add(new TemplateParam("remark",remark,"#AAAAAA"));
        template.setTemplateParamList(params);
        return template;
    }

    /**
     * 将微信支付时间转换成产品消息模板的格式
     * @param payTime
     * @return
     */
    private String converTime(String payTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = null;
        try {
            Date parse = sdf.parse(payTime);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            str = sdf2.format(parse);
        } catch (ParseException e) {
            logger.error("时间转换异常：",e);
        }
        return str;
    }

    @Override
    @Transactional
    public void processWeChatCallback(String outTradeNo,String totalFee,String timeEnd) {
        FeePayOrder feePayOrder = feePayOrderService.queryByTradeNo(outTradeNo);
        Integer payFlag = feePayOrder.getPayFlag();
        if (payFlag != 0) {
            // 说明已处理
            return;
        }

        logger.info("===================================进行下单操作");
        String cno = feePayOrder.getCno();
        CustomerDevMap customerDevMap = customerDevMapService.queryByCno(cno);
        String meterUserNo = customerDevMap.getMeterUserNo();
        String deviceType = customerDevMap.getDeviceType();
        String customerNo = customerDevMap.getCustomerNo();

        // 查询客户名称
        CustomerInfo customerInfo = customerInfoService.queryByCustomerNo(customerNo);
        String customerName = customerInfo.getCustomerName();

        // 更改为成功状态
        FeePayOrder payOrder = new FeePayOrder();
        payOrder.setId(feePayOrder.getId());
        payOrder.setPayFlag(1);
        payOrder.setTimeExpire(timeEnd);
        payOrder.setFinishTime(new Date());
        feePayOrderService.updateByPrimaryKeySelective(payOrder);

        // 下单操作
        FeePay feePay = new FeePay();
        feePay.setPayGuid(outTradeNo);
        feePay.setPayMoney(BigDecimal.valueOf(Long.parseLong(totalFee)/100.0));
        feePay.setPayModel(FeeControlConstant.PayModelValue.APP.getCode());
        feePay.setCno(customerDevMap.getCno());
        feePay.setCustomerNo(customerDevMap.getCustomerNo());
//        feePay.setPayCount(customerDevMap.getPayCount() + 1);
        String serialNum = DateUtil.getSerialNum();
        feePay.setSerialNum(serialNum);
        Integer payMethod = FeeControlConstant.PayMethod.WE_CHAT.getCode();
        feePay.setPayMethod(payMethod);
        feePayService.rechargePayment(feePay, 0L, meterUserNo);

        // 异步发送微信模板消息
        List<CustomerWxBind> wxBinds = customerWxBindService.queryByParams(meterUserNo, deviceType);
        List<WxSendPaySuccessParam> successParams = Lists.newArrayList();
        for (CustomerWxBind wxBind : wxBinds) {
            WxSendPaySuccessParam param = new WxSendPaySuccessParam();
            param.setOpenId(wxBind.getOpenId());
            param.setCno(cno);
            param.setCustomerName(customerName);
            param.setPayTime(timeEnd);
            param.setPayMoney(totalFee);
            param.setSerialNum(serialNum);
            param.setPayMethod(payMethod);
            successParams.add(param);
        }
        this.sendWeChatPaySuccess(successParams);

        // 异步发送短信消息
        SmsPaySuccessParam param = new SmsPaySuccessParam();
        param.setCustomerNo(customerNo);
        param.setCno(cno);
        param.setCustomerName(customerName);
        param.setPayMethod(payMethod);
        param.setPayMoney(totalFee);

        String payTime = this.converTime(timeEnd);
        param.setPayTime(payTime);
        aliyunSmsService.sendOrderPaySuccess(param);
    }

    @Override
    @Transactional
    public void processUnionCallback(String outTradeNo, String totalFee, Integer payMethod) {
        logger.info("银联支付成功，回调通知业务处理outTradeNo=" + outTradeNo + ",totalFee=" + totalFee + ",payMethod=" + payMethod);
        FeePayOrder feePayOrder = feePayOrderService.queryByTradeNo(outTradeNo);
        Integer payFlag = feePayOrder.getPayFlag();
        if (payFlag != 0) {
            // 说明已处理
            return;
        }

        String cno = feePayOrder.getCno();
        CustomerDevMap customerDevMap = customerDevMapService.queryByCno(cno);
        String meterUserNo = customerDevMap.getMeterUserNo();
        String deviceType = customerDevMap.getDeviceType();
        String customerNo = customerDevMap.getCustomerNo();

        // 查询客户名称
        CustomerInfo customerInfo = customerInfoService.queryByCustomerNo(customerNo);
        String customerName = customerInfo.getCustomerName();

        // 更改为成功状态
        FeePayOrder payOrder = new FeePayOrder();
        payOrder.setId(feePayOrder.getId());
        payOrder.setPayFlag(1);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeEnd = format.format(new Date());
        payOrder.setTimeExpire(timeEnd);
        payOrder.setFinishTime(new Date());
        feePayOrderService.updateByPrimaryKeySelective(payOrder);

        // 下单操作
        FeePay feePay = new FeePay();
        feePay.setPayGuid(outTradeNo);
        String payMoney = MathUtil.fen2yuan(totalFee);
        BigDecimal decimal = new BigDecimal(payMoney);
        feePay.setPayMoney(decimal);
        feePay.setPayModel(FeeControlConstant.PayModelValue.APP.getCode());
        feePay.setCno(customerDevMap.getCno());
        feePay.setCustomerNo(customerDevMap.getCustomerNo());
//        feePay.setPayCount(customerDevMap.getPayCount() + 1);
        String serialNum = DateUtil.getSerialNum();
        feePay.setSerialNum(serialNum);
        feePay.setPayMethod(payMethod);
        feePayService.rechargePayment(feePay, 0L, meterUserNo);

        // 异步发送微信模板消息
        List<CustomerWxBind> wxBinds = customerWxBindService.queryByParams(meterUserNo, deviceType);
        List<WxSendPaySuccessParam> successParams = Lists.newArrayList();
        for (CustomerWxBind wxBind : wxBinds) {
            WxSendPaySuccessParam param = new WxSendPaySuccessParam();
            param.setOpenId(wxBind.getOpenId());
            param.setCno(cno);
            param.setCustomerName(customerName);
            param.setPayTime(timeEnd);
            param.setPayMoney(totalFee);
            param.setSerialNum(serialNum);
            param.setPayMethod(payMethod);
            successParams.add(param);
        }
        this.sendWeChatPaySuccess(successParams);


        // 异步发送短信消息
        SmsPaySuccessParam param = new SmsPaySuccessParam();
        param.setCustomerNo(customerNo);
        param.setCno(cno);
        param.setCustomerName(customerName);
        param.setPayMethod(payMethod);
        param.setPayMoney(totalFee);

        String payTime = this.converTime(timeEnd);
        param.setPayTime(payTime);
//        smsService.sendOrderPaySuccess(param);
        aliyunSmsService.sendOrderPaySuccess(param);
    }

    @Override
    public String getAccessToken() {
        String accessToken = CacheUtil.get(WEXIN_ACCESS_TOKEN);
        if (!StringUtils.isEmpty(accessToken)) {
            logger.info("缓存中已存在access_token,直接返回");
            return accessToken;
        }

        String grant_type = "client_credential";//获取access_token填写client_credential
        //这个url链接地址和参数皆不能变
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type="+grant_type+"&appid="+appId+"&secret="+secret;

        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = JSONObject.fromObject(message);
            logger.info("weixin-accesstoken:"+demoJson);
            accessToken = demoJson.getString("access_token");
            if (!StringUtils.isEmpty(accessToken)) {
                logger.info("重新设置access_token，放入本地缓存");
                CacheUtil.set(WEXIN_ACCESS_TOKEN,accessToken);
            }
            is.close();
        } catch (Exception e) {
            logger.error("获取微信access_token接口异常：",e);
        }
        return accessToken;
    }

    @Override
    public String getJsapiTicket() {
        String ticket = CacheUtil.get(WEXIN_JSAPI_TICKET);
        if (!StringUtils.isEmpty(ticket)) {
            logger.info("缓存中已存在ticket,直接返回");
            return ticket;
        }

        // 获取accessToken
        String accessToken = this.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ accessToken +"&type=jsapi";//这个url链接和参数不能变
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = JSONObject.fromObject(message);
            logger.info("获取weixin-ticket:"+demoJson);
            ticket = demoJson.getString("ticket");
            if (!StringUtils.isEmpty(ticket)) {
                logger.info("重新设置ticket，放入本地缓存");
                CacheUtil.set(WEXIN_JSAPI_TICKET,ticket);
            }
            is.close();
        } catch (Exception e) {
            logger.error("获取微信jsapi ticket网络请求异常",e);
        }
        return ticket;
    }

    @Override
    public void sendWxTemplateMessage(Template template) {
        logger.info("发送微信模板消息，Template=" + com.alibaba.fastjson.JSON.toJSONString(template));
        try {
            String accessToken = getAccessToken();
            logger.info("获取到的accessToken=" + accessToken);
            String url = SEND_MESSAGE_URL.replace("ACCESS_TOKEN", accessToken);
            boolean result = sendTemplateMsg(url, template);
        } catch (Exception e) {
            logger.error("发送微信模板信息异常：",e);
        }
    }

    private boolean sendTemplateMsg(String url, Template template){
        boolean flag = false;
        JSONObject jsonResult = CommonUtil.httpsRequest(url, "POST", template.toJSON());
        if(jsonResult != null){
            int errorCode = jsonResult.getInt("errcode");
            String errorMessage = jsonResult.getString("errmsg");
            if(errorCode == 0){
                flag = true;
            }else{
                logger.info("模板消息发送失败 errorCode:" + errorCode + ",errorMessage:" + errorMessage);
                flag = false;
            }
        }
        return flag;
    }

}
