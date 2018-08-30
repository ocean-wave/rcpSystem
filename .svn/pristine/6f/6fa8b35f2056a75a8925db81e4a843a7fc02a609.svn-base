package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.cache.DeviceMapCacheVo;
import cn.com.cdboost.collect.constant.CustomerInfoConstant;
import cn.com.cdboost.collect.constant.MeterDiConstant;
import cn.com.cdboost.collect.constant.NeteaseSmsConstant;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.MeterCollectDataFailInfo;
import cn.com.cdboost.collect.dto.response.MeterCollectDataInfo;
import cn.com.cdboost.collect.dto.response.MeterCollectImpDataInfo;
import cn.com.cdboost.collect.enums.DeviceType;
import cn.com.cdboost.collect.enums.InstructCode;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.*;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.util.JsonUtil;
import cn.com.cdboost.collect.util.MathUtil;
import cn.com.cdboost.collect.util.MsgHttpUtil;
import com.alibaba.fastjson.JSON;
import com.example.clienttest.client.AFN04Object;
import com.example.clienttest.client.AFN15Object;
import com.example.clienttest.client.ResultInfo;
import com.example.clienttest.clientfuture.ClientManager;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.sf.json.JSONArray;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 实时抄表服务接口实现类
 */
@Service("realTimeDataService")
public class RealTimeDataServiceImpl implements RealTimeDataService {
    private static final Logger logger = LoggerFactory.getLogger(RealTimeDataServiceImpl.class);

    @Autowired
    private CustomerDevMapService customerDevMapService;
    @Autowired
    private MeterCollectDataService meterCollectDataService;
    @Autowired
    private DeviceMeterParamService deviceMeterParamService;
    @Autowired
    private DeviceMeterConfigService deviceMeterConfigService;
    @Autowired
    private MeterReadQueueService meterReadQueueService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private InstructService instructService;
    @Autowired
    private CustomerPhoneBindService customerPhoneBindService;
    @Autowired
    private RedisService redisService;

    @Value("${templateid}")
    private String templateid;
    @Value("${appKey}")
    private String appKey;
    @Value("${appSecret}")
    private String appSecret;

    /**
     * 读取实时采集数据
     * @param queryVo
     * @return
     */
    @Override
    public List<MeterCollectDataListInfo> listRealTimeData(RealMeterCollectQueryVo queryVo) {
        // null转空字符串
        this.setEmptyStr(queryVo);

        List<MeterCollectDataListInfo> list = Lists.newArrayList();
        // 读取采集数据
        List<MeterCollectDataInfo> dataInfoList = meterCollectDataService.listRealMeterCollectData(queryVo);
        if(!CollectionUtils.isEmpty(dataInfoList)){
            for (MeterCollectDataInfo meterCollectData :dataInfoList){
                MeterCollectDataListInfo info = new MeterCollectDataListInfo();
                BeanUtils.copyProperties(meterCollectData,info);

                info.setDeviceNo(CNoUtil.getNo(meterCollectData.getDeviceCno()));
                info.setJzqNo(CNoUtil.getNo(meterCollectData.getJzqCno()));
                list.add(info);
            }
        }

        return list;
    }
    /**
     * 读取实时采集数据
     * @param queryVo
     * @return
     */
    @Override
    public List<MeterCollectDataListInfo> listRealTimeDataNew(RealMeterCollectQueryNewVo queryVo) {

        List<MeterCollectDataListInfo> list = Lists.newArrayList();
        // 读取采集数据
        if(queryVo.getSearchDate()==null){
            queryVo.setSearchDate("");
        }
        if(queryVo.getIsOnline()==null){
            queryVo.setIsOnline("");
        }
        List<MeterCollectDataInfo> dataInfoList = meterCollectDataService.listRealMeterCollectDataNew(queryVo);
        if(!CollectionUtils.isEmpty(dataInfoList)){
            for (MeterCollectDataInfo meterCollectData :dataInfoList){
                MeterCollectDataListInfo info = new MeterCollectDataListInfo();
                BeanUtils.copyProperties(meterCollectData,info);

                info.setDeviceNo(CNoUtil.getNo(meterCollectData.getDeviceCno()));
                info.setJzqNo(CNoUtil.getNo(meterCollectData.getJzqCno()));
                list.add(info);
            }
        }

        return list;
    }

    private void setEmptyStr(RealMeterCollectQueryVo queryVo) {
        String jzqNo = queryVo.getJzqNo();
        if (jzqNo == null) {
            queryVo.setJzqNo("");
        }

        String deviceNo = queryVo.getDeviceNo();
        if (deviceNo == null) {
            queryVo.setDeviceNo("");
        }

        String customerName = queryVo.getCustomerName();
        if (customerName == null) {
            queryVo.setCustomerName("");
        }

        String customerAddr = queryVo.getCustomerAddr();
        if (customerAddr == null) {
            queryVo.setCustomerAddr("");
        }

        String customerContact = queryVo.getCustomerContact();
        if (customerContact == null) {
            queryVo.setCustomerContact("");
        }

        String meterUserNo = queryVo.getMeterUserNo();
        if (meterUserNo == null) {
            queryVo.setMeterUserNo("");
        }

        String searchDate = queryVo.getSearchDate();
        if (searchDate == null) {
            queryVo.setSearchDate("");
        }

        String isOnline = queryVo.getIsOnline();
        if (isOnline == null) {
            queryVo.setIsOnline("");
        }

        String propertyName = queryVo.getPropertyName();
        if (propertyName == null) {
            queryVo.setPropertyName("");
        }

        String guid = queryVo.getGuid();
        if (guid == null) {
            queryVo.setGuid("");
        }
    }

    @Override
    public List<CollectImportantDataListInfo> listImpRealTimeData(RealMeterCollectQueryVo queryVo) {
        // null转空字符串
        this.setEmptyStr(queryVo);
        List<MeterCollectImpDataInfo> dataInfos = meterCollectDataService.listImpRealTimeData(queryVo);
        List<CollectImportantDataListInfo> dataList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(dataInfos)) {
            for (MeterCollectImpDataInfo dataInfo : dataInfos) {
                CollectImportantDataListInfo info = new CollectImportantDataListInfo();
                BeanUtils.copyProperties(dataInfo,info);
                info.setDeviceNo(CNoUtil.getNo(dataInfo.getDeviceCno()));
                info.setJzqNo(CNoUtil.getJzqNoByJzqCno(dataInfo.getJzqCno()));

                // 设置精度
//                info.setCurrentA(MathUtil.setPrecision(dataInfo.getCurrentA()));
//                info.setCurrentB(MathUtil.setPrecision(dataInfo.getCurrentB()));
//                info.setCurrentC(MathUtil.setPrecision(dataInfo.getCurrentC()));
//                info.setVoltageA(MathUtil.setPrecision(dataInfo.getVoltageA()));
//                info.setVoltageB(MathUtil.setPrecision(dataInfo.getVoltageB()));
//                info.setVoltageC(MathUtil.setPrecision(dataInfo.getVoltageC()));
//                info.setInstantPower(MathUtil.setPrecision(dataInfo.getInstantPower()));
//                info.setInstantPowerA(MathUtil.setPrecision(dataInfo.getInstantPowerA()));
//                info.setInstantPowerB(MathUtil.setPrecision(dataInfo.getInstantPowerB()));
//                info.setInstantPowerC(MathUtil.setPrecision(dataInfo.getInstantPowerC()));
                dataList.add(info);
            }
        }
        return dataList;
    }
    @Override
    public List<CollectImportantDataListInfo> listImpRealTimeDataNew(RealMeterCollectQueryNewVo queryVo) {
        // null转空字符串
        this.setEmptyStr(queryVo);
        List<MeterCollectImpDataInfo> dataInfos = meterCollectDataService.listImpRealTimeDataNew(queryVo);
        List<CollectImportantDataListInfo> dataList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(dataInfos)) {
            Set<String> cnoSet = Sets.newHashSet();
            for (MeterCollectImpDataInfo dataInfo : dataInfos) {
                String deviceCno = dataInfo.getDeviceCno();
                cnoSet.add(deviceCno);
            }

            // 设置表计户号
            Map<String, DeviceMapCacheVo> map = redisService.queryDeviceMapCacheMap(cnoSet);
            for (MeterCollectImpDataInfo dataInfo : dataInfos) {
                DeviceMapCacheVo deviceMapCacheVo = map.get(dataInfo.getDeviceCno());
                dataInfo.setMeterUserNo(deviceMapCacheVo.getMeterUserNo());
                CollectImportantDataListInfo info = new CollectImportantDataListInfo();
                BeanUtils.copyProperties(dataInfo,info);
                info.setDeviceNo(CNoUtil.getNo(dataInfo.getDeviceCno()));
                info.setJzqNo(CNoUtil.getJzqNoByJzqCno(dataInfo.getJzqCno()));

                // 设置精度
                BigDecimal currentA = dataInfo.getCurrentA();
                info.setCurrentA(MathUtil.setPrecision(currentA));

                BigDecimal currentB = dataInfo.getCurrentB();
                info.setCurrentB(MathUtil.setPrecision(currentB));

                BigDecimal currentC = dataInfo.getCurrentC();
                info.setCurrentC(MathUtil.setPrecision(currentC));

                BigDecimal voltageA = dataInfo.getVoltageA();
                info.setVoltageA(MathUtil.setPrecision(voltageA));

                BigDecimal voltageB = dataInfo.getVoltageB();
                info.setVoltageB(MathUtil.setPrecision(voltageB));

                BigDecimal voltageC = dataInfo.getVoltageC();
                info.setVoltageC(MathUtil.setPrecision(voltageC));

                dataList.add(info);
            }

        }
        return dataList;
    }
    //采集失败数据驼峰修改
    @Override
    public List<MeterCollectDataFailInfo> listRealTimeFailData(RealMeterCollectFailQueryVo queryVo) {
        // 读取失败的采集数据
        return meterCollectDataService.listRealMeterCollectFailData(queryVo);
    }

    @Override
    public int sendRealCollectList(SendRealCollectQueryParam queryParam) {
        Integer userId = queryParam.getUserId();
        String guid = queryParam.getGuid();
        Date date = queryParam.getDate();
        Integer isImportant = queryParam.getIsImportant();
        String[] types =queryParam.getTypes();
        instructService.clearResultInfo(guid);
        List<RealCollectMeterParam> meters = queryParam.getMeters();
        List<String> cnoList = Lists.newArrayList();
        for (RealCollectMeterParam meter : meters) {
            cnoList.add(meter.getDeviceCno());
        }

        // 批量查询设备参数表信息
        List<DeviceMeterParam> meterParams = deviceMeterParamService.findDeviceMeterParamByCnos(cnoList);
        Map<String,DeviceMeterParam> meterParamMap = Maps.newHashMap();
        Set<String> paramFlagSet = Sets.newHashSet();
        for (DeviceMeterParam meterParam : meterParams) {
            meterParamMap.put(meterParam.getCno(),meterParam);
            paramFlagSet.add(meterParam.getParamFlag());
        }
        // 批量查询设备参数配置新
        ImmutableMap<String, DeviceMeterConfig> meterConfigMap = deviceMeterConfigService.batchQueryByParamFlags(paramFlagSet);

        List<MeterReadQueue> list = Lists.newArrayList();
        for (RealCollectMeterParam meter : meters) {
            String jzqCno = meter.getJzqCno();
            String deviceCno = meter.getDeviceCno();

            // 实时采集对象
            MeterReadQueue meterReadQueue = new MeterReadQueue();
            meterReadQueue.setJzqCno(jzqCno);
            meterReadQueue.setMeterCno(deviceCno);
            meterReadQueue.setCreateUserId(Long.valueOf(userId));
            meterReadQueue.setQueueGuid(guid);
            meterReadQueue.setJzqNo(jzqCno.substring(2).replaceAll("^0*", ""));
            meterReadQueue.setMeterNo(deviceCno.substring(2).replaceAll("^0*", ""));
            meterReadQueue.setCreateTime(date);
            meterReadQueue.setGroupGuid(meter.getGroupGuid());
            meterReadQueue.setReadStatus(0);
            meterReadQueue.setIsImportant(queryParam.getIsImportant());

            // 设置di
            DeviceMeterParam meterParam = meterParamMap.get(deviceCno);
            Integer commPort = meterParam.getCommPort();
            String[] di645Array = this.getDi645Array(meterParam, isImportant, types);
            if (di645Array.length == 0) {
                // 不支持抄其他项，队列直接写抄表失败
                meterReadQueue.setReadStatus(2);
            } else {
                if (commPort == 32) {
                    // LoraWAN抄表模式
                    String join = Joiner.on(",").join(di645Array);
                    String[] di645 = new String[] {join};
                    meterReadQueue.setDi645(di645);

                    // 设置超时时间
                    int overTime = this.getOverTime(meterParam,di645,isImportant);
                    meterReadQueue.setOverTime(overTime);
                } else {
                    // 集中器抄表模式
                    meterReadQueue.setDi645(di645Array);

                    // 设置超时时间
                    int overTime = this.getOverTime(meterParam,di645Array,isImportant);
                    meterReadQueue.setOverTime(overTime);
                }
            }

            meterReadQueue.setIsImportant(isImportant);
            list.add(meterReadQueue);
        }

        // 排除掉不能抄表的项
        List<MeterReadQueue> collectList = Lists.newArrayList();
        for (MeterReadQueue queue : list) {
            Integer readStatus = queue.getReadStatus();
            if (readStatus != 2) {
                collectList.add(queue);
            }
        }

        // 批量删除
        int i = meterReadQueueService.deleteByParam(guid, cnoList);
        logger.info("批量删除队列数据num=" + i);

        // 批量新增
        int num = meterReadQueueService.insertList(list);
        logger.info("批量新增队列数据num=" + num);
        logger.info("批量新增队列数据list"+JSON.toJSONString(list));

        if (CollectionUtils.isEmpty(collectList)) {
            logger.info("不支持改抄表项");
            return -20000;
        }

        int sendResult = instructService.SendCollectInstructList(meterParamMap,meterConfigMap,collectList, queryParam.getSessionId());
        logger.info("采集指令结果sendResult：" + sendResult);
        if (sendResult != InstructCode.Success.getValue()) {
            MeterReadQueue updateParam = new MeterReadQueue();
            updateParam.setReadStatus(2);
            updateParam.setUpdateTime(new Date());
            updateParam.setCreateTime(date);
            int num2 = meterReadQueueService.batchUpdateByParams(guid, cnoList, updateParam);
            logger.info("采集指令发送失败，批量更新队列数据num=" + num2);
        }
        return sendResult;
    }

    @Override
    public void sendmsg(SendSmsParamVo smsParam) {
        CustomerDevMap param = new CustomerDevMap();
        String customerNo = smsParam.getCustomerNo();
        param.setCustomerNo(customerNo);
        String deviceCno = CNoUtil.CreateCNo(smsParam.getDeviceType(), smsParam.getDeviceNo());
        param.setCno(deviceCno);
        CustomerDevMap customerDevMap = customerDevMapService.selectOne(param);
        if (customerDevMap == null) {
            throw new BusinessException("客户设备关联表中不存在对应记录");
        }

        Date lastSendSmsTime = customerDevMap.getLastSendSmsTime();
        if (lastSendSmsTime != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(lastSendSmsTime);
            long timestamp1 = cal.getTimeInMillis();
            cal.setTime(new Date());
            long timestamp2 = cal.getTimeInMillis();
            if ((timestamp2 - timestamp1) / (1000 * 60) < 60 * 12) {
                throw new BusinessException("不能频繁向同一用户催发短信");
            }
        }

        // 查询该用户下绑定的所有电话
        List<CustomerPhoneBind> phoneBinds = customerPhoneBindService.queryByCustomerNo(customerNo);
        List<String> mobileList = new ArrayList<>();
        for (CustomerPhoneBind phoneBind : phoneBinds) {
            mobileList.add(phoneBind.getMobilePhone());
        }

        HttpClient client = new DefaultHttpClient();
        String url = "https://api.netease.im/sms/sendtemplate.action";
        //创建发送短信httpPost请求
        HttpPost httpPost = MsgHttpUtil.getHttpPost(url,appKey,appSecret);
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        //读取短信模板ID,模板编号(由客户顾问配置之后告知开发者)
        nvps.add(new BasicNameValuePair("templateid", templateid));
        //接收者号码列表，JSONArray格式,如["186xxxxxxxx","186xxxxxxxx"],最多100个
        nvps.add(new BasicNameValuePair("mobiles", JSON.toJSONString(mobileList)));

        List<Object> paramList = new ArrayList<>();
        //客户姓名
        paramList.add(smsParam.getCustomerName());
        String deviceType = smsParam.getDeviceType();
        if (DeviceType.ELECTRIC_METER.getCode().equals(deviceType)) {
            paramList.add("电");
        } else if (DeviceType.WATER_METER.getCode().equals(deviceType)) {
            paramList.add("水");
        } else if (DeviceType.GAS_METER.getCode().equals(deviceType)) {
            paramList.add("气");
        }

        //用户地址
        paramList.add(smsParam.getCustomerAddr());
        //表号
        paramList.add(smsParam.getDeviceNo());
        //类型
        if (DeviceType.ELECTRIC_METER.getCode().equals(deviceType)) {
            paramList.add("电表");
        } else if (DeviceType.WATER_METER.getCode().equals(deviceType)) {
            paramList.add("水表");
        } else if (DeviceType.GAS_METER.getCode().equals(deviceType)) {
            paramList.add("气表");
        }

        //截止日期
        paramList.add(smsParam.getCollectDate());
        //欠费金额
        paramList.add(smsParam.getReadValue());
        //物业中心地址
        paramList.add(smsParam.getPaymentAddr());
        //短信发送方
        paramList.add(smsParam.getCompanyName());
        //短信参数列表
        nvps.add(new BasicNameValuePair("params", JSON.toJSONString(paramList)));
        Map<String, Object> resultMap2 = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            // 执行请求
            HttpResponse response = client.execute(httpPost);
            // 执行结果
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            Map<String, Object> resultMap = JsonUtil.toHashMap(result);
            int code = Integer.parseInt(resultMap.get("code").toString());
            if (code != 200) {
                String msg = resultMap.get("msg").toString();
                logger.info("发送短信接口地址返回状态code=" + code + ",msg=" + msg);
                throw new BusinessException("短信接口调用异常");
            }

            url = "https://api.netease.im/sms/querystatus.action";
            //创建获取每条发送短信状态httpPost请求
            httpPost = MsgHttpUtil.getHttpPost(url,appKey,appSecret);
            Integer sendId = (Integer) resultMap.get("obj");
            // 设置请求的参数
            List<NameValuePair> nvps2 = new ArrayList<NameValuePair>();
            nvps2.add(new BasicNameValuePair("sendid", String.valueOf(sendId)));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps2, "utf-8"));
            // 执行请求
            HttpResponse response2 = client.execute(httpPost);
            // 执行结果
            String result2 = EntityUtils.toString(response2.getEntity(), "utf-8");
            resultMap2 = JsonUtil.toHashMap(result2);
        } catch (UnsupportedEncodingException e) {
           logger.error("不支持的编码异常：",e);
        } catch (ClientProtocolException e) {
            logger.error("ClientProtocol异常：",e);
        } catch (IOException e) {
            logger.error("系统IO异常：",e);
        }

        int code2 = Integer.parseInt(resultMap2.get("code").toString());
        if (code2 != 200) {
            String msg = resultMap2.get("msg").toString();
            logger.info("短信状态查询接口返回code=" + code2 + ",msg=" + msg);
            throw new BusinessException("短信接口调用异常");
        }
        //读取短信发送状态
        List<Object> lists = JsonUtil.toArrayList(resultMap2.get("obj"));
        StringBuffer sb = new StringBuffer();
        List<Sms> dataList = Lists.newArrayList();
        for (Object list : lists) {
            Map<String, Object> msgMap = JsonUtil.toHashMap(list);
            Date sendTime = new Date(Long.parseLong(msgMap.get("updatetime").toString()));
            Sms sms = new Sms();
            sms.setSendTime(sendTime);
            String mobile = msgMap.get("mobile").toString();
            sms.setCustomerContact(mobile);
            Integer status = (Integer) msgMap.get("status");
            sms.setMessageStatus(status);
            sms.setCreateTime(new Date());
            sms.setCreateUserId(Long.valueOf(smsParam.getCurrentUserId()));
            sms.setCustomerNo(customerNo);
            dataList.add(sms);

            if (NeteaseSmsConstant.SmsSendStatus.NOT_SEND.getStatus() == status.intValue()) {
                String desc = NeteaseSmsConstant.SmsSendStatus.NOT_SEND.getDesc();
                sb.append("手机号["+mobile+"],发送状态["+status+":"+desc+"]\n");
            } else if (NeteaseSmsConstant.SmsSendStatus.SEND_FAIL.getStatus() == status.intValue()) {
                String desc = NeteaseSmsConstant.SmsSendStatus.SEND_FAIL.getDesc();
                sb.append("手机号["+mobile+"],发送状态["+status+":"+desc+"]\n");
            } else if (NeteaseSmsConstant.SmsSendStatus.GARBAGE.getStatus() == status.intValue()) {
                String desc = NeteaseSmsConstant.SmsSendStatus.GARBAGE.getDesc();
                sb.append("手机号["+mobile+"],发送状态["+status+":"+desc+"]\n");
            }
            // TODO 这个字段不维护了
//                else if (1 == msg.getMessageStatus()) {
//                    CustomerDevMap devMap = new CustomerDevMap();
//                    devMap.setLastSendSmsTime(sendtime);
//                    String cno = CNoUtil.CreateCNo(deviceType, smsParam.getDeviceNo());
//                    devMap.setCno(cno);
//                    customerDevMapService.updateByCnoSelective(devMap);
//                }
        }

        // 批量插入
        int num = smsService.insertList(dataList);
        logger.info("批量插入短信记录num=" + num + "条");

        if (!StringUtils.isEmpty(sb.toString())) {
            throw new BusinessException(sb.toString());
        }
    }

    @Override
    public RealTimeDataStatuListInfo realTimeDataListStatus(Long userId, String guid, String deviceType, String date) {
        RealTimeDataStatuListInfo info = new RealTimeDataStatuListInfo();

        ResultInfo resultInfo = ClientManager.getMulReadMeterStatus(guid);
        logger.info("获取批量抄表状态结果：" + JSON.toJSONString(resultInfo));
        if (resultInfo != null) {
            boolean isUpdate = resultInfo.isUpdate();
            int status = resultInfo.getStatus();
            info.setStatus(status);
            info.setIsUpdate(isUpdate);
            info.setTotal(resultInfo.getTotal());
            info.setDealNum(resultInfo.getDealNum());
            info.setUndealNum(resultInfo.getUndealNum());
            info.setSuccessfulNum(resultInfo.getSuccessfulNum());
            ArrayList<AFN04Object> failMeterList = resultInfo.getFailMeterList();
            if (!CollectionUtils.isEmpty(failMeterList)) {
                info.setFailNum(failMeterList.size());
            }

            if (status != 101) {
                instructService.clearResultInfo(guid);
            }

//            if (isUpdate) {
                RealMeterCollectQueryVo queryVo = new RealMeterCollectQueryVo();
                queryVo.setGuid(guid);
                queryVo.setDeviceType(deviceType);
                queryVo.setUserId(userId.intValue());
                queryVo.setSearchDate(date);
                queryVo.setPageNumber(1);
                queryVo.setPageSize(resultInfo.getTotal());

                List<MeterCollectDataListInfo> successList = listRealTimeData(queryVo);
                if (!CollectionUtils.isEmpty(successList)) {
                    for (MeterCollectDataListInfo dataListInfo : successList) {
                        BigDecimal pr0 = dataListInfo.getPr0();
                        if (pr0 == null) {
                            BigDecimal zero = BigDecimal.ZERO;
                            BigDecimal temp = new BigDecimal(zero.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                            dataListInfo.setPr0(temp);
                        } else {
                            BigDecimal temp = new BigDecimal(pr0.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                            dataListInfo.setPr0(temp);
                        }

                        BigDecimal balance = dataListInfo.getBalance();
                        if (balance == null) {
                            BigDecimal zero = BigDecimal.ZERO;
                            BigDecimal temp = new BigDecimal(zero.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                            dataListInfo.setBalance(temp);
                        } else {
                            BigDecimal temp = new BigDecimal(balance.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                            dataListInfo.setBalance(temp);
                        }
                    }
                }
                logger.info("成功列表：guid=" + guid + ",successList=" + JSON.toJSONString(successList));
                info.setSuccessList(successList);

                ArrayList<AFN04Object> meterList = resultInfo.getFailMeterList();
                List<String> cnoList = Lists.newArrayList();
                if (!CollectionUtils.isEmpty(meterList)) {
                    for (AFN04Object afn04Object : meterList) {
                        cnoList.add(CNoUtil.CreateCNo(deviceType, afn04Object.getDbdz()));
                    }
                }
                info.setCnoList(cnoList);
            }
//        }

        return info;

    }

    @Override
    public boolean StopCollectList(String guid) {
        try {
            boolean result = ClientManager.stopReadMeter(guid);
            instructService.clearResultInfo(guid);
            return result;
        }catch (Exception e){
            throw new BusinessException("前置机未连接");
        }
    }

    @Override
    public int broadcast(String guid, String sessionId) {
        try {
            AFN15Object afn15Object = new AFN15Object(guid,"15","999999999","",sessionId);
            int i = ClientManager.sendAFN15Msg(afn15Object);
            return i;
        }catch (Exception e){
            throw new BusinessException("前置机未连接");
        }
    }

    @Override
    public boolean broadcastStop(String guid) {
        try {
            boolean b = ClientManager.stopReadMeter(guid);
            instructService.clearResultInfo(guid);
            return b;
        }catch (Exception e){
            throw new BusinessException("前置机未连接");
        }

    }

    @Override
    public List<CollectRecordDTO> ReadCollectRecord(String deviceType, StringBuilder total, Integer userId) {
        logger.info("RealTimeDataServiceImpl : ReadCollectRecordDetial 访问参数："+deviceType);

        List<CollectRecordDTO> dtoList = meterCollectDataService.listReadCollectRecord(userId,deviceType);
        if (!CollectionUtils.isEmpty(dtoList)) {
            for (CollectRecordDTO dto : dtoList) {
                BigDecimal successRate = dto.getSuccessRate();
                BigDecimal temp = new BigDecimal(successRate.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                dto.setSuccessRate(temp);
            }
        }
        total.append(dtoList.size());
        logger.info("RealTimeDataServiceImpl : ReadCollectRecord 返回结果："+ JSONArray.fromObject(dtoList) +" , "+"total: "+total);
        return dtoList;
    }

    @Override
    public List<CollectDetialDTO> readCollectRecordDetail(ReadCollectRecordDetailQueryVo queryVo) {
        logger.info("RealTimeDataServiceImpl : ReadCollectRecordDetial 访问参数："+ com.alibaba.fastjson.JSON.toJSONString(queryVo));
        List<CollectDetialDTO> dtoList = meterCollectDataService.listReadCollectRecordDetail(queryVo);
        for (CollectDetialDTO collectDetialDTO : dtoList) {
            // 电表设备去0操作
           // collectDetialDTO.setDeviceNo(CNoUtil.getNo(collectDetialDTO.getDeviceNo()));
        }

        return dtoList;
    }

    @Override
    public String SumFreeForDay() {
        String sumDate = meterCollectDataService.sumFreeForDay();
        return sumDate;
    }



    /**
     * 根据设备参数表获取抄表项di
     * @param meterConfigMap
     * @param meterParam
     * @param isImportant
     * @return
     */
    private String[] getDi645(ImmutableMap<String, DeviceMeterConfig> meterConfigMap,DeviceMeterParam meterParam,Integer isImportant) {
        DeviceMeterConfig deviceMeterConfig = meterConfigMap.get(meterParam.getParamFlag());
        Integer deviceType = meterParam.getDeviceType();
        //设置di645
        String[] strArray;
        if (CustomerInfoConstant.DeviceIsImportant.YES.getCode().equals(isImportant)) {
            // 重点用户
            switch (deviceType) {
                case 7:
                    if (deviceMeterConfig.getCommRule() == 30) {
                        // 0001ff00总，0201ff00 电压，0202ff00电流，0203FF00功率
                        //2007
                        if(meterParam.getLocalControl() == 2){
                            //远程费控
                            // 0001ff00 正向有功电能块 包含：总:4.2,尖:4.2,峰:4.2,平:4.2,谷:4.2
                            // 0201ff00 电压
                            // 0202ff00 电流
                            strArray = new String[]{"0001ff00,0201ff00,0202ff00"};
                        } else {
                            //本地费控
                            strArray = new String[]{"0001ff00,0201ff00,0202ff00"};
                        }
                    } else {
                        //90
                        strArray = new String[]{"9010"};
                    }
                    break;
                default:
                    strArray = new String[]{"901F"};
                    break;
            }
        } else {
            // 非重点用户
            switch (deviceType) {
                case 7:
                    if (deviceMeterConfig.getCommRule() == 30) {
                        //2007
                        if(meterParam.getLocalControl() == 2){
                            //远程费控
                            strArray = new String[]{"0001ff00"};
                        } else {
                            //本地费控
                            strArray = new String[]{"0001ff00", "00900200"};
                        }
                    } else {
                        //90
                        strArray = new String[]{"9010"};
                    }
                    break;
                default:
                    strArray = new String[]{"901F"};
                    break;
            }
        }

        return strArray;
    }


    private String[] getDi645Array(DeviceMeterParam meterParam,Integer isImportant,String[] types) {
        Integer deviceType = meterParam.getDeviceType();
        //设置di645
        List<String> diList = Lists.newArrayList();
        if (CustomerInfoConstant.DeviceIsImportant.YES.getCode().equals(isImportant)) {
            // 重点用户
            switch (deviceType) {
                case 7:
                    if (meterParam.getCommRule() == 30) {
                        //13表
                        Integer commFactorCnt = meterParam.getCommFactorCnt();
                        for (String type : types) {
                            List<String> list = MeterDiConstant.ImpUser13.getDiList(type,commFactorCnt);
                            diList.addAll(list);
                        }
                    } else if(meterParam.getCommRule()==CustomerInfoConstant.ElectricCommRule.DL_T645_2018_29.getCommRule()){
                        // 红相表
                        for (String type : types) {
                            List<String> list = MeterDiConstant.ImpUser4Red.getDiList(type);
                            diList.addAll(list);
                        }
                    } else {
                        //97表
                        for (String type : types) {
                            List<String> list = MeterDiConstant.ImpUser97.getDiList(type);
                            diList.addAll(list);
                        }
                    }
                    break;
                default:
                    diList.add("901F");
                    break;
            }
        } else {
            // 非重点用户
            switch (deviceType) {
                case 7:
                    if (meterParam.getCommRule() == 30) {
                        //13表
                        Integer localControl = meterParam.getLocalControl();
                        Integer commFactorCnt = meterParam.getCommFactorCnt();
                        for (String type : types) {
                            List<String> list = MeterDiConstant.CommonUser13.getDiList(type, localControl,commFactorCnt);
                            diList.addAll(list);
                        }
                    } else if(meterParam.getCommRule()==CustomerInfoConstant.ElectricCommRule.DL_T645_2018_29.getCommRule()){
                        // 红相表
                        for (String type : types) {
                            List<String> list = MeterDiConstant.CommonUser4Red.getDiList(type);
                            diList.addAll(list);
                        }
                    } else {
                        //97表
                        for (String type : types) {
                            List<String> list = MeterDiConstant.CommonUser97.getDiList(type);
                            diList.addAll(list);
                        }
                    }
                    break;
                default:
                    diList.add("901F");
                    break;
            }
        }

        String[] result = diList.toArray(new String[diList.size()]);
        return result;
    }

    /**
     * 根据设备参数表，计算超时时间
     * @param meterParam
     * @return
     */
    private int getOverTime(DeviceMeterParam meterParam, String[] di645, Integer isImportant) {
        Integer commPort = meterParam.getCommPort();
        if (commPort != 32) {
            // 集中器抄表
            return 20;
        }

        if (CustomerInfoConstant.DeviceIsImportant.YES.getCode().equals(isImportant)) {
            String str = di645[0];
            String[] split = str.split(",");
            int length = split.length;
            int overTime = 20 * length;
            return overTime;
        } else {
            int overTime = 60;
            if(commPort == 32){
                if(meterParam.getMoteType().equals("C")){
                    overTime = 20;
                }else {
                    overTime = 10;
                }
            }
            return overTime;
        }
    }
}
