package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.constant.FeeControlConstant;
import cn.com.cdboost.collect.constant.NeteaseSmsConstant;
import cn.com.cdboost.collect.constant.SmsConstant;
import cn.com.cdboost.collect.dao.SmsMapper;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.enums.DeviceType;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.CustomerPhoneBind;
import cn.com.cdboost.collect.model.Sms;
import cn.com.cdboost.collect.model.SmsScheme;
import cn.com.cdboost.collect.service.CustomerPhoneBindService;
import cn.com.cdboost.collect.service.SmsSchemeService;
import cn.com.cdboost.collect.service.SmsService;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.util.DateUtil;
import cn.com.cdboost.collect.util.MathUtil;
import cn.com.cdboost.collect.util.SmsUtil;
import cn.com.cdboost.collect.vo.*;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 用户短信发送消息记录服务接口实现类
 */
@Service("smsService")
public class SmsServiceImpl extends BaseServiceImpl<Sms> implements SmsService{
    private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    @Autowired
    private SmsMapper smsMapper;
    @Autowired
    private CustomerPhoneBindService customerPhoneBindService;
    @Autowired
    private SmsSchemeService smsSchemeService;

    @Value("${appKey}")
    private String appKey;

    @Value("${appSecret}")
    private String appSecret;

    @Value("${smsCodeTemplateId}")
    private String smsCodeTemplateId;

    @Value("${paySuccessSmsTemplateId}")
    private String paySuccessSmsTemplateId;

    @Value("${paySuccessSmsContent}")
    private String paySuccessSmsContent;

    @Value("${remoteSuccessSmsTemplateId}")
    private String remoteSuccessSmsTemplateId;

    @Value("${remoteSuccessSmsContent}")
    private String remoteSuccessSmsContent;

    @Value("${alarmSmsTemplateId}")
    private String alarmSmsTemplateId;

    @Value("${alarmSmsContent}")
    private String alarmSmsContent;

    @Value("${sendTemplateUrl}")
    private String sendTemplateUrl;

    @Value("${queryStatusUrl}")
    private String queryStatusUrl;

    @Value("${sendCodeUrl}")
    private String sendCodeUrl;

    @Value("${verifyCodeUrl}")
    private String verifyCodeUrl;

    @Value("${sendStart}")
    private String sendStart;

    @Value("${sendEnd}")
    private String sendEnd;

    @Value("${job.num}")
    private String sendNum;

    @Value("${job.time}")
    private String delayTime;

    @Override
    public String sendSmsCode(String mobilePhone) {
        SmsCodeParam param = new SmsCodeParam();
        param.setUrl(sendCodeUrl);
        param.setAppKey(appKey);
        param.setAppSecret(appSecret);
        param.setTemplateId(Integer.valueOf(smsCodeTemplateId));
        param.setMobilePhone(mobilePhone);
        String code = SmsUtil.sendSmsCode(param);
        return code;
    }

    @Override
    public boolean verifySmsCode(String mobilePhone, String code) {
        VerifySmsCodeParam param = new VerifySmsCodeParam();
        param.setUrl(verifyCodeUrl);
        param.setAppKey(appKey);
        param.setAppSecret(appSecret);
        param.setCode(code);
        param.setMobliePhone(mobilePhone);
        boolean verifycode = SmsUtil.verifycode(param);
        return verifycode;
    }

    @Override
    public void sendOrderPaySuccess(SmsPaySuccessParam param) {
        // 查询需要发送的电话号码
        String customerNo = param.getCustomerNo();
        List<CustomerPhoneBind> phoneBinds = customerPhoneBindService.queryByCustomerNo(customerNo);
        List<String> mobilePhones = Lists.newArrayList();
        for (CustomerPhoneBind phoneBind : phoneBinds) {
            mobilePhones.add(phoneBind.getMobilePhone());
        }

        TemplateSmsParam smsParam = new TemplateSmsParam();
        smsParam.setUrl(sendTemplateUrl);
        smsParam.setAppKey(appKey);
        smsParam.setAppSecret(appSecret);
        smsParam.setTemplateId(Integer.valueOf(paySuccessSmsTemplateId));
        smsParam.setMobiles(JSON.toJSONString(mobilePhones));

        List<String> params = Lists.newArrayList();
        // 姓名
        params.add(param.getCustomerName());
        // 时间
        params.add(param.getPayTime());
        // 支付方式
        Integer payMethod = param.getPayMethod();
        String payMethodStr = FeeControlConstant.PayMethod.getDescByCode(payMethod);
        params.add(payMethodStr);
        // 表号
        String cno = param.getCno();
        String deviceNo = CNoUtil.getNo(cno);
        params.add(deviceNo);
        // 表类型
        String deviceType = cno.substring(0, 2);
        String deviceTypeStr = DeviceType.getMessageByCode(deviceType);
        params.add(deviceTypeStr);
        // 支付金额
        String payMoney = param.getPayMoney();
        String payMoneyStr = MathUtil.fen2yuan(payMoney);
        params.add(payMoneyStr);
        smsParam.setParams(JSON.toJSONString(params));

        List<Object> lists = Lists.newArrayList();
        lists.add(param.getCustomerName());
        lists.add(param.getPayTime());
        lists.add(payMethodStr);
        lists.add(deviceNo);
        lists.add(deviceTypeStr);
        lists.add(payMoneyStr);
        String feeType = FeeControlConstant.FeeType.getDescByCode(deviceType);
        lists.add(feeType);
        lists.add(deviceTypeStr);
        Object[] objects = lists.toArray(new Object[lists.size()]);
        String smsContent = String.format(paySuccessSmsContent,objects);
        Integer type = SmsConstant.SmsType.PAY_SUCCESS.getType();
        this.sendSmsProcess(smsParam,customerNo,type,smsContent);
    }

    @Override
    public void remoteRechargeSuccess(SmsRemoteSuccessParam param) {
        String customerNo = param.getCustomerNo();
        List<CustomerPhoneBind> phoneBinds = customerPhoneBindService.queryByCustomerNo(customerNo);
        List<String> mobilePhones = Lists.newArrayList();
        for (CustomerPhoneBind phoneBind : phoneBinds) {
            mobilePhones.add(phoneBind.getMobilePhone());
        }

        TemplateSmsParam smsParam = new TemplateSmsParam();
        smsParam.setUrl(sendTemplateUrl);
        smsParam.setAppKey(appKey);
        smsParam.setAppSecret(appSecret);
        smsParam.setTemplateId(Integer.valueOf(remoteSuccessSmsTemplateId));
        smsParam.setMobiles(JSON.toJSONString(mobilePhones));

        List<String> params = Lists.newArrayList();
        // 姓名
        params.add(param.getCustomerName());
        // 时间
        params.add(param.getPayTime());
        // 支付方式
        Integer payMethod = param.getPayMethod();
        String payMethodStr = FeeControlConstant.PayMethod.getDescByCode(payMethod);
        params.add(payMethodStr);
        // 支付金额
        params.add(param.getPayMoney());
        // 表号
        String cno = param.getCno();
        String deviceNo = CNoUtil.getNo(cno);
        params.add(deviceNo);
        smsParam.setParams(JSON.toJSONString(params));

        Integer type = SmsConstant.SmsType.REMOTE_PAY_SUCCESS.getType();
        List<Object> paramList = Lists.newArrayList();
        paramList.add(param.getCustomerName());
        paramList.add(param.getPayTime());
        paramList.add(payMethodStr);
        paramList.add(param.getPayMoney());
        String deviceType = cno.substring(0, 2);
        String deviceTypeStr = FeeControlConstant.FeeType.getDescByCode(deviceType);
        paramList.add(deviceTypeStr);
        String deviceName = DeviceType.getMessageByCode(deviceType);
        paramList.add(deviceName);
        paramList.add(deviceNo);
        Object[] objects = paramList.toArray(new Object[paramList.size()]);
        String smsContent = String.format(remoteSuccessSmsContent,objects);
        this.sendSmsProcess(smsParam,customerNo,type,smsContent);
    }

    @Override
    public void sendAlarmSms(List<SmsAlarmParam> alarmParams) {
        logger.info("发送短信告警信息入参alarmParams=" + JSON.toJSONString(alarmParams));
        if (CollectionUtils.isEmpty(alarmParams)) {
            return;
        }
        // 存放sendId集合
        Map<Long,SmsSendDto> sendIdMap= Maps.newHashMap();
        for (SmsAlarmParam param : alarmParams) {
            String customerNo = param.getCustomerNo();
            TemplateSmsParam smsParam = new TemplateSmsParam();
            smsParam.setUrl(sendTemplateUrl);
            smsParam.setAppKey(appKey);
            smsParam.setAppSecret(appSecret);
            smsParam.setTemplateId(Integer.valueOf(alarmSmsTemplateId));
            List<String> mobiles = param.getMobiles();
            smsParam.setMobiles(JSON.toJSONString(mobiles));

            List<String> params = Lists.newArrayList();
            // 姓名
            String customerName = param.getCustomerName();
            params.add(customerName);
            // 采集时间
            String collectTime = param.getCollectTime();
            params.add(collectTime);
            // 设备类型
            String deviceTypeStr = DeviceType.getMessageByCode(param.getDeviceType());
            params.add(deviceTypeStr);
            // 告警金额
            String alarmThreshold = param.getAlarmThreshold();
            params.add(alarmThreshold);
            // 剩余金额
            String remainAmount = param.getRemainAmount();
            params.add(remainAmount);
            smsParam.setParams(JSON.toJSONString(params));

            // 发送
            TemplateSmsResponse smsResponse = SmsUtil.sendTemplateSms(smsParam);
            if (smsResponse == null) {
                logger.info("-----------短信发送接口异常----------");
                continue;
            }
            Integer code = smsResponse.getCode();
            if (code != 200) {
                logger.info("短信发送接口返回code=" + code + ",msg=" + smsResponse.getMsg());
                continue;
            }

            Long obj = smsResponse.getObj();
            SmsSendDto sendDto = new SmsSendDto();
            sendDto.setCustomerNo(customerNo);
            sendDto.setCustomerName(customerName);
            sendDto.setCollectTime(collectTime);
            sendDto.setDeviceName(deviceTypeStr);
            sendDto.setAlarmThreshold(alarmThreshold);
            sendDto.setRemainAmount(remainAmount);
            sendIdMap.put(obj,sendDto);
        }

        if (sendIdMap.isEmpty()) {
            return;
        }

        // 延迟5秒查询
        final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                List<SmsSchemeUpdateParam> updateParams = Lists.newArrayList();
                List<Sms> insertList = Lists.newArrayList();
                for (Map.Entry<Long, SmsSendDto> entry : sendIdMap.entrySet()) {
                    Long sendId = entry.getKey();
                    SmsStatusQueryParam param = new SmsStatusQueryParam();
                    param.setSendId(sendId);
                    param.setUrl(queryStatusUrl);
                    param.setAppKey(appKey);
                    param.setAppSecret(appSecret);
                    TemplateSmsStatusResponse response = SmsUtil.querySmsStatus(param);
                    if (response == null) {
                        logger.info("短信状态查询异常,sendId=" + sendId);
                        continue;
                    }
                    int code = response.getCode();
                    if (code != 200) {
                        logger.info("短信状态查询接口返回code=" + code);
                        continue;
                    }

                    SmsSendDto sendDto = entry.getValue();
                    List<TemplateSmsStatusObj> obj = response.getObj();
                    for (TemplateSmsStatusObj statusObj : obj) {
                        String customerNo = sendDto.getCustomerNo();
                        int status = statusObj.getStatus();
                        String mobile = statusObj.getMobile();
                        Long updatetime = statusObj.getUpdatetime();
                        if (status == NeteaseSmsConstant.SmsSendStatus.SEND_SUCCESS.getStatus()) {
                            // 发送成功，记录发送的时间，统一批量更新
                            SmsSchemeUpdateParam updateParam = new SmsSchemeUpdateParam();
                            updateParam.setCustomerNo(customerNo);
                            updateParam.setCno(sendDto.getCno());
                            updateParam.setObjType(SmsConstant.SmsSchemeType.MOBILE_PHONE.getType());
                            updateParam.setSendObj(mobile);
                            Date date = new Date(updatetime);
                            updateParam.setLastSendTime(date);

                            updateParams.add(updateParam);
                        }

                        Sms sms = new Sms();
                        sms.setCustomerContact(mobile);
                        sms.setCustomerNo(customerNo);
                        List<Object> params = Lists.newArrayList();
                        params.add(sendDto.getCustomerName());
                        params.add(sendDto.getCollectTime());
                        params.add(sendDto.getInstallAddr());
                        params.add(sendDto.getDeviceNo());
                        params.add(sendDto.getDeviceName());
                        params.add(sendDto.getAlarmThreshold());
                        params.add(sendDto.getRemainAmount());
                        Object[] objects = params.toArray(new Object[params.size()]);
                        String str = String.format(alarmSmsContent,objects);
                        sms.setMessageContent(str);
                        sms.setCreateUserId(0L);
                        sms.setCreateTime(new Date());
                        if (updatetime == null) {
                            sms.setSendTime(new Date());
                        } else {
                            Date sendTime = new Date(updatetime);
                            sms.setSendTime(sendTime);
                        }
                        sms.setMsgType(SmsConstant.SmsType.ALARM.getType());
                        sms.setMessageStatus(status);
                        insertList.add(sms);
                    }
                }

                // 更新成功发送的短信
                int count = smsSchemeService.batchUpdate(updateParams);
                logger.info("批量更新消息提醒方案表记录：count=" + count);

                // 新增短信发送历史记录表
                int insertCount = smsMapper.insertList(insertList);
                logger.info("批量新增短信历史记录：insertCount=" + insertCount);

                executorService.shutdown();
            }
        },10, TimeUnit.MINUTES);
    }

    private void sendSmsProcess(TemplateSmsParam smsParam, String customerNo, Integer msgType,String smsContent) {
        TemplateSmsResponse smsResponse = SmsUtil.sendTemplateSms(smsParam);
        if (smsResponse == null) {
            logger.info("-----------短信发送接口异常----------");
        }
        Integer code = smsResponse.getCode();
        if (code != 200) {
            logger.info("短信发送接口返回code=" + code + ",msg=" + smsResponse.getMsg());
            return;
        }
        Long sendId = smsResponse.getObj();

        final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                // 查询短信发送状态
                SmsStatusQueryParam queryParam = new SmsStatusQueryParam();
                queryParam.setUrl(queryStatusUrl);
                queryParam.setAppKey(appKey);
                queryParam.setAppSecret(appSecret);
                queryParam.setSendId(sendId);
                TemplateSmsStatusResponse response = SmsUtil.querySmsStatus(queryParam);
                if (response != null) {
                    int code2 = response.getCode();
                    if (code2 == 200) {
                        List<TemplateSmsStatusObj> obj = response.getObj();
                        // 批量新增sms表记录
                        List<Sms> dataList = Lists.newArrayList();
                        for (TemplateSmsStatusObj statusObj : obj) {
                            int status = statusObj.getStatus();
                            Long updatetime = statusObj.getUpdatetime();
                            Sms sms = new Sms();
                            sms.setCustomerContact(statusObj.getMobile());
                            sms.setCustomerNo(customerNo);
                            sms.setMessageContent(smsContent);
                            sms.setMessageStatus(status);
                            // 微信支付发送的短信，默认为0
                            sms.setCreateUserId(0L);
                            sms.setCreateTime(new Date());
                            if (updatetime == null) {
                                sms.setSendTime(new Date());
                            } else {
                                Date date = new Date(updatetime);
                                sms.setSendTime(date);
                            }
                            sms.setMsgType(msgType);
                            sms.setMessageStatus(status);
                            dataList.add(sms);
                        }
                        smsMapper.insertList(dataList);
                    }
                }

                executorService.shutdown();
            }
        },5,TimeUnit.MINUTES);
    }

    @Override
    public boolean canSendSms(String customerNo, String cno, List<String> mobiles) {
        // 是否在有效的时间范围内
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date current = new Date();
        String str = format.format(current);
        try {
            Date nowTime = format.parse(str);
            Date startTime = format.parse(sendStart);
            Date endTime = format.parse(sendEnd);
            boolean effectiveDate = DateUtil.isEffectiveDate(nowTime, startTime, endTime);
            if (!effectiveDate) {
                throw new BusinessException("请在有效的时间范围内发送短信");
            }
        } catch (ParseException e) {
            logger.error("时间格式化异常：",e);
        }

        // 查询方案表中是否存在
        List<SmsScheme> smsSchemes = smsSchemeService.queryByParams(customerNo, cno, mobiles);
        if (CollectionUtils.isEmpty(smsSchemes)) {
            // 插入
            List<SmsScheme> list = Lists.newArrayList();
            for (String mobile : mobiles) {
                SmsScheme scheme = new SmsScheme();
                scheme.setCustomerNo(customerNo);
                scheme.setSendObj(mobile);
                scheme.setObjType(SmsConstant.SmsSchemeType.MOBILE_PHONE.getType());
                scheme.setSendCount(0);
                scheme.setCreateTime(new Date());
                list.add(scheme);
            }
            int insertCount = smsSchemeService.insertList(list);
            logger.info("批量插入消息提醒方案表insertCount" + insertCount);
            return true;
        }

        // 判断短信方案表存在的手机是否满足发送短信的条件
        List<String> existPhones = Lists.newArrayList();
        for (SmsScheme smsScheme : smsSchemes) {
            Integer sendCount = smsScheme.getSendCount();
            Date lastSendTime = smsScheme.getLastSendTime();
            existPhones.add(smsScheme.getSendObj());
        }

        // 存在的手机号，查看短信发送次数和最后发送时间


        // 取差集
        Set<String> differenceSet = Sets.difference(Sets.newHashSet(mobiles), Sets.newHashSet(existPhones));
        ArrayList<String> insertList = Lists.newArrayList(differenceSet);
        if (!CollectionUtils.isEmpty(insertList)) {
            // 插入
            List<SmsScheme> dataList = Lists.newArrayList();
            for (String mobile : insertList) {
                SmsScheme scheme = new SmsScheme();
                scheme.setCustomerNo(customerNo);
                scheme.setSendObj(mobile);
                scheme.setObjType(SmsConstant.SmsSchemeType.MOBILE_PHONE.getType());
                scheme.setSendCount(0);
                scheme.setCreateTime(new Date());
                dataList.add(scheme);
            }
            int insertCount = smsSchemeService.insertList(dataList);
            logger.info("批量插入消息提醒方案表insertCount" + insertCount);
        }

        return false;
    }

    @Override
    public int updateSms(String smsUuid, String mobilePhone, Integer sendStatus) {
        Condition condition = new Condition(Sms.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("customerContact",mobilePhone);
        criteria.andEqualTo("smsUuid",smsUuid);

        Sms param = new Sms();
        param.setMessageStatus(sendStatus);
        return smsMapper.updateByConditionSelective(param,condition);
    }

    @Override
    public Sms queryByParam(String smsUuid, String mobilePhone) {
        Sms param = new Sms();
        param.setSmsUuid(smsUuid);
        param.setCustomerContact(mobilePhone);
        return smsMapper.selectOne(param);
    }

    @Override
    public List<Sms> queryNotReceivedNoticeSmsList() {
        Condition condition = new Condition(Sms.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("messageStatus",0);
//        Date startTimeCurrentDay = DateUtil.getStartTimeCurrentDay();
        // 从昨天开始时间查询，避免出现昨天定时任务之外，用户手动点击发送短信，导致有sms记录未更新的bug
        Date startTimeOfYesterday = DateUtil.getStartTimeOfYesterday();
        Date endTimeOfCurrentDay = DateUtil.getEndTimeOfCurrentDay();
        criteria.andBetween("createTime",startTimeOfYesterday,endTimeOfCurrentDay);
        return smsMapper.selectByCondition(condition);
    }
}
