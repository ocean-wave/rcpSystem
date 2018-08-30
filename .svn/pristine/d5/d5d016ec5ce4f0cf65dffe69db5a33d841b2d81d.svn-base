package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.constant.SmsConstant;
import cn.com.cdboost.collect.dao.SmsSchemeMapper;
import cn.com.cdboost.collect.dto.param.SmsAlarmParam;
import cn.com.cdboost.collect.dto.param.SmsSchemeUpdateParam;
import cn.com.cdboost.collect.dto.param.WeChatAlarmParam;
import cn.com.cdboost.collect.model.*;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.DateUtil;
import cn.com.cdboost.collect.util.MathUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.*;

/**
 * 消息提醒方案表服务实现类/
 */
@Service("smsSchemeService")
public class SmsSchemeServiceImpl extends BaseServiceImpl<SmsScheme> implements SmsSchemeService {
    private static final Logger logger = LoggerFactory.getLogger(SmsSchemeServiceImpl.class);

    @Autowired
    private SmsSchemeMapper smsSchemeMapper;
    @Autowired
    private AliyunSmsService aliyunSmsService;
    @Autowired
    private WeixinService weixinService;
    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private CustomerInfoCostService customerInfoCostService;

    @Override
    public int updateCountAndSendTime(String customerNo,String mobilePhone, Date sendTime,Date updateTime) {
        return smsSchemeMapper.updateCountAndSendTime(customerNo,mobilePhone,sendTime,updateTime);
    }

    @Override
    public int batchUpdate(List<SmsSchemeUpdateParam> param) {
        return smsSchemeMapper.batchUpdate(param);
    }

    @Override
    public List<SmsScheme> queryByParams(String customerNo, String cno,List<String> mobiles) {
        Condition condition = new Condition(SmsScheme.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("customerNo",customerNo);
        criteria.andEqualTo("cno",cno);
        criteria.andEqualTo("objType", SmsConstant.SmsSchemeType.MOBILE_PHONE.getType());
        criteria.andIn("sendObj",mobiles);
        List<SmsScheme> smsSchemes = smsSchemeMapper.selectByCondition(condition);
        return smsSchemes;
    }

    @Override
    public void renewData3() {
        // 1、查询满足告警信息的记录
        List<SmsScheme> newList = smsSchemeMapper.queryWarning3();
        // 分组
        ImmutableMap<String, SmsScheme> smsSchemeMap = Maps.uniqueIndex(newList, new Function<SmsScheme, String>() {
            @Nullable
            @Override
            public String apply(@Nullable SmsScheme smsScheme) {
                return smsScheme.getCustomerNo() + smsScheme.getSendObj();
            }
        });

        // 2、查询短信方案表所有告警级别2的数据
        List<SmsScheme> oldList = this.queryByAlarmLevel(3);
        // 分组
        ImmutableMap<String, SmsScheme> oldSchemeMap = Maps.uniqueIndex(oldList, new Function<SmsScheme, String>() {
            @Nullable
            @Override
            public String apply(@Nullable SmsScheme smsScheme) {
                return smsScheme.getCustomerNo() + smsScheme.getSendObj();
            }
        });

        // 3、需要删除的方案id列表
        List<Integer> deleteList = Lists.newArrayList();
        for (SmsScheme scheme : oldList) {
            String key = scheme.getCustomerNo() + scheme.getSendObj();
            if (!smsSchemeMap.containsKey(key)) {
                logger.info("告警级别三，需要删除的方案详细信息SmsScheme=" + JSON.toJSONString(scheme));
                deleteList.add(scheme.getId());
            }
        }

        // 4、需要新增的方案
        List<SmsScheme> insertList = Lists.newArrayList();
        Date date = new Date();
        for (SmsScheme scheme : newList) {
            String key = scheme.getCustomerNo() + scheme.getSendObj();
            if (!oldSchemeMap.containsKey(key)) {
                scheme.setSendCount(0);
                scheme.setCreateTime(date);
                scheme.setAlarmLevel(SmsConstant.AlarmLevel.ALARM_THREE.getLevel());
                insertList.add(scheme);
            }
        }

        // 5、批量删除不满足告警的信息
        if (!CollectionUtils.isEmpty(deleteList)) {
            String deleteIds = Joiner.on(",").join(deleteList);
            int deleteCount = smsSchemeMapper.deleteByIds(deleteIds);
            logger.info("批量删除告警方案记录" + deleteCount + "条");
        }

        // 6、批量新增新加入的告警记录
        if (!CollectionUtils.isEmpty(insertList)) {
            logger.info("批量新增告警级别3的数据insertList=" + JSON.toJSONString(insertList));
            int insertCount = smsSchemeMapper.insertList(insertList);
            logger.info("批量新增告警方案记录" + insertCount + "条");
        }
    }

    @Override
    @Transactional
    public void renewData2() {
        // 1、查询满足告警信息的记录
        List<SmsScheme> newList = smsSchemeMapper.queryWarning2();
        // 分组
        ImmutableMap<String, SmsScheme> smsSchemeMap = Maps.uniqueIndex(newList, new Function<SmsScheme, String>() {
            @Nullable
            @Override
            public String apply(@Nullable SmsScheme smsScheme) {
                return smsScheme.getCustomerNo() + smsScheme.getSendObj();
            }
        });

        // 2、查询短信方案表所有告警级别2的数据
        List<SmsScheme> oldList = this.queryByAlarmLevel(2);
        // 分组
        ImmutableMap<String, SmsScheme> oldSchemeMap = Maps.uniqueIndex(oldList, new Function<SmsScheme, String>() {
            @Nullable
            @Override
            public String apply(@Nullable SmsScheme smsScheme) {
                return smsScheme.getCustomerNo() + smsScheme.getSendObj();
            }
        });

        // 3、需要删除的方案id列表
        List<Integer> deleteList = Lists.newArrayList();
        for (SmsScheme scheme : oldList) {
            String key = scheme.getCustomerNo() + scheme.getSendObj();
            if (!smsSchemeMap.containsKey(key)) {
                logger.info("告警级别二，需要删除的方案详细信息SmsScheme=" + JSON.toJSONString(scheme));
                deleteList.add(scheme.getId());
            }
        }

        // 4、需要新增的方案
        List<SmsScheme> insertList = Lists.newArrayList();
        Date date = new Date();
        for (SmsScheme scheme : newList) {
            String key = scheme.getCustomerNo() + scheme.getSendObj();
            if (!oldSchemeMap.containsKey(key)) {
                scheme.setSendCount(0);
                scheme.setCreateTime(date);
                scheme.setAlarmLevel(SmsConstant.AlarmLevel.ALARM_TWO.getLevel());
                insertList.add(scheme);
            }
        }

        // 5、批量删除不满足告警的信息
        if (!CollectionUtils.isEmpty(deleteList)) {
            String deleteIds = Joiner.on(",").join(deleteList);
            int deleteCount = smsSchemeMapper.deleteByIds(deleteIds);
            logger.info("批量删除告警方案记录" + deleteCount + "条");
        }

        // 6、批量新增新加入的告警记录
        if (!CollectionUtils.isEmpty(insertList)) {
            logger.info("批量新增告警级别2的数据insertList=" + JSON.toJSONString(insertList));
            int insertCount = smsSchemeMapper.insertList(insertList);
            logger.info("批量新增告警方案记录" + insertCount + "条");
        }

    }

    @Override
    @Transactional
    public void renewData1() {
        // 1、查询满足告警信息的记录
        List<SmsScheme> newList = smsSchemeMapper.queryWarning1();
        // 分组
        ImmutableMap<String, SmsScheme> smsSchemeMap = Maps.uniqueIndex(newList, new Function<SmsScheme, String>() {
            @Nullable
            @Override
            public String apply(@Nullable SmsScheme smsScheme) {
                return smsScheme.getCustomerNo() + smsScheme.getSendObj();
            }
        });
        // 2、查询短信方案表所有告警级别1的数据
        List<SmsScheme> oldList = this.queryByAlarmLevel(1);
        // 分组
        ImmutableMap<String, SmsScheme> oldSchemeMap = Maps.uniqueIndex(oldList, new Function<SmsScheme, String>() {
            @Nullable
            @Override
            public String apply(@Nullable SmsScheme smsScheme) {
                return smsScheme.getCustomerNo() + smsScheme.getSendObj();
            }
        });

        // 3、需要删除的方案id列表
        List<Integer> deleteList = Lists.newArrayList();
        for (SmsScheme scheme : oldList) {
            String key = scheme.getCustomerNo() + scheme.getSendObj();
            if (!smsSchemeMap.containsKey(key)) {
                logger.info("告警级别一，需要删除的方案详细信息SmsScheme=" + JSON.toJSONString(scheme));
                deleteList.add(scheme.getId());
            }
        }

        // 4、需要新增的方案
        List<SmsScheme> insertList = Lists.newArrayList();
        Date date = new Date();
        for (SmsScheme scheme : newList) {
            String key = scheme.getCustomerNo() + scheme.getSendObj();
            if (!oldSchemeMap.containsKey(key)) {
                scheme.setSendCount(0);
                scheme.setCreateTime(date);
                scheme.setAlarmLevel(SmsConstant.AlarmLevel.ALARM_ONE.getLevel());
                insertList.add(scheme);
            }
        }

        // 5、批量删除不满足告警的信息
        if (!CollectionUtils.isEmpty(deleteList)) {
            String deleteIds = Joiner.on(",").join(deleteList);
            int deleteCount = smsSchemeMapper.deleteByIds(deleteIds);
            logger.info("批量删除告警方案记录" + deleteCount + "条");
        }

        // 6、批量新增新加入的告警记录
        if (!CollectionUtils.isEmpty(insertList)) {
            logger.info("批量新增告警级别1的数据insertList=" + JSON.toJSONString(insertList));
            int insertCount = smsSchemeMapper.insertList(insertList);
            logger.info("批量新增告警方案记录" + insertCount + "条");
        }
    }

    @Override
    public List<SmsScheme> queryByAlarmLevel(Integer alarmLevel) {
        SmsScheme param = new SmsScheme();
        param.setAlarmLevel(alarmLevel);
        return smsSchemeMapper.select(param);
    }

    @Override
    public List<SmsScheme> queryAlarmThreeSchemeInfo(Integer sendCount, Integer time) {
        return smsSchemeMapper.queryAlarmSchemeInfo(sendCount,time,SmsConstant.AlarmLevel.ALARM_THREE.getLevel());
    }

    @Override
    public List<SmsScheme> queryAlarmTwoSchemeInfo() {
        Condition condition = new Condition(SmsScheme.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("alarmLevel",2);
        criteria.andIsNull("lastSendTime");
        return smsSchemeMapper.selectByCondition(condition);
    }

    @Override
    public List<SmsScheme> queryAlarmOneSchemeInfo() {
        Condition condition = new Condition(SmsScheme.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("alarmLevel",1);
        criteria.andIsNull("lastSendTime");
        return smsSchemeMapper.selectByCondition(condition);
    }

    @Override
    public void sendSmsWeixin(List<SmsScheme> smsSchemes) {
        Map<Integer,List<SmsScheme>> dataMap = Maps.newHashMap();
        Set<String> customerNoSet = Sets.newHashSet();
        for (SmsScheme smsScheme : smsSchemes) {
            customerNoSet.add(smsScheme.getCustomerNo());
            Integer objType = smsScheme.getObjType();
            if (dataMap.containsKey(objType)) {
                List<SmsScheme> tempList = dataMap.get(objType);
                tempList.add(smsScheme);
            } else {
                List<SmsScheme> list = Lists.newArrayList();
                list.add(smsScheme);
                dataMap.put(objType,list);
            }
        }

        // 批量查询用户信息
        List<CustomerInfo> customerInfoList = customerInfoService.batchQueryByCustomerNos(customerNoSet);
        // 转map
        ImmutableMap<String, CustomerInfo> customerInfoMap = Maps.uniqueIndex(customerInfoList, new Function<CustomerInfo, String>() {
            @Nullable
            @Override
            public String apply(@Nullable CustomerInfo customerInfo) {
                return customerInfo.getCustomerNo();
            }
        });

        // 批量查询用户费用信息表
        List<CustomerInfoCost> customerInfoCosts = customerInfoCostService.batchQueryByCustomerNos(customerNoSet);
        // 转map
        ImmutableMap<String, CustomerInfoCost> costMap = Maps.uniqueIndex(customerInfoCosts, new Function<CustomerInfoCost, String>() {
            @Nullable
            @Override
            public String apply(@Nullable CustomerInfoCost customerInfoCost) {
                return customerInfoCost.getCustomerNo();
            }
        });

        // 构造微信消息list
        List<SmsScheme> weChatList = dataMap.get(2);
        if (!CollectionUtils.isEmpty(weChatList)) {
            List<WeChatAlarmParam> weChatAlarmList = this.getWeChatAlarmList(weChatList, customerInfoMap, costMap);
            try {
                // 发送微信告警信息
                weixinService.sendWeChatAlarmMessage(weChatAlarmList);
            } catch (Exception e) {
                logger.error("发送微信告警信息异常",e);
            }
        }


        // 构造短信消息list
        List<SmsScheme> smsList = dataMap.get(1);
        if (!CollectionUtils.isEmpty(smsList)) {
            List<SmsAlarmParam> smsAlarmList = this.getSmsAlarmList(smsList, customerInfoMap, costMap);
            logger.info("短信发送入参smsAlarmList=" + JSON.toJSONString(smsAlarmList));

            // 发送短信告警信息
            aliyunSmsService.sendAlarmSms(smsAlarmList,1);
        }
    }

    private List<WeChatAlarmParam> getWeChatAlarmList(List<SmsScheme> list, Map<String, CustomerInfo> customerInfoMap,Map<String,CustomerInfoCost> costMap) {
        // 按用户分组
        ImmutableListMultimap<String, SmsScheme> multimap = Multimaps.index(list, new Function<SmsScheme, String>() {
            @Nullable
            @Override
            public String apply(@Nullable SmsScheme smsScheme) {
                return smsScheme.getCustomerNo();
            }
        });

        List<WeChatAlarmParam> dataList = Lists.newArrayList();
        for (Map.Entry<String, Collection<SmsScheme>> entry : multimap.asMap().entrySet()) {
            String customerNo = entry.getKey();
            ImmutableList<SmsScheme> smsSchemes = multimap.get(customerNo);
            List<String> openIds = Lists.newArrayList();
            for (SmsScheme smsScheme : smsSchemes) {
                openIds.add(smsScheme.getSendObj());
            }
            WeChatAlarmParam alarmParam = new WeChatAlarmParam();
            alarmParam.setOpenIds(openIds);
            SmsScheme scheme = smsSchemes.get(0);
            CustomerInfo customerInfo = customerInfoMap.get(scheme.getCustomerNo());
            if (customerInfo != null) {
                alarmParam.setCustomerName(customerInfo.getCustomerName());
            }

            CustomerInfoCost cost = costMap.get(scheme.getCustomerNo());
            if (cost != null) {
                String time = DateUtil.formatDate(cost.getUpdateTime());
                alarmParam.setCollectTime(time);
                BigDecimal remainAmount = MathUtil.setPrecision(cost.getRemainAmount());
                alarmParam.setRemainAmount(String.valueOf(remainAmount));
                // 告警金额1
                BigDecimal alarmThreshold = MathUtil.setPrecision(cost.getAlarmThreshold());
                // 告警金额2
                BigDecimal alarmThreshold1 = MathUtil.setPrecision(cost.getAlarmThreshold1());
                // 告警金额3
                BigDecimal alarmThreshold2 = MathUtil.setPrecision(cost.getAlarmThreshold2());
                boolean flag = MathUtil.isLessThan(remainAmount, alarmThreshold2);
                if (flag) {
                    alarmParam.setAlarmThreshold(String.valueOf(alarmThreshold2));
                } else {
                    boolean flag2 = MathUtil.isLessThan(remainAmount, alarmThreshold1);
                    if (flag2) {
                        alarmParam.setAlarmThreshold(String.valueOf(alarmThreshold1));
                    } else {
                        alarmParam.setAlarmThreshold(String.valueOf(alarmThreshold));
                    }
                }

                int i = MathUtil.compareTo(remainAmount, alarmThreshold1);
                if (i <= 0) {
                    alarmParam.setAlarmThreshold(String.valueOf(alarmThreshold1));
                } else {
                    alarmParam.setAlarmThreshold(String.valueOf(alarmThreshold));
                }

            }
            alarmParam.setCustomerNo(scheme.getCustomerNo());
            dataList.add(alarmParam);
        }

        return dataList;
    }

    private List<SmsAlarmParam> getSmsAlarmList(List<SmsScheme> list, Map<String, CustomerInfo> customerInfoMap,Map<String,CustomerInfoCost> costMap) {
        // 按cno分组
        ImmutableListMultimap<String, SmsScheme> multimap = Multimaps.index(list, new Function<SmsScheme, String>() {
            @Nullable
            @Override
            public String apply(@Nullable SmsScheme smsScheme) {
                return smsScheme.getCustomerNo();
            }
        });

        List<SmsAlarmParam> dataList = Lists.newArrayList();
        for (Map.Entry<String, Collection<SmsScheme>> entry : multimap.asMap().entrySet()) {
            String customerNo = entry.getKey();
            ImmutableList<SmsScheme> smsSchemes = multimap.get(customerNo);
            List<String> mobiles = Lists.newArrayList();
            for (SmsScheme smsScheme : smsSchemes) {
                mobiles.add(smsScheme.getSendObj());
            }
            SmsAlarmParam alarmParam = new SmsAlarmParam();
            alarmParam.setMobiles(mobiles);
            SmsScheme scheme = smsSchemes.get(0);
            CustomerInfo customerInfo = customerInfoMap.get(scheme.getCustomerNo());
            if (customerInfo != null) {
                alarmParam.setCustomerName(customerInfo.getCustomerName());
            }

            CustomerInfoCost cost = costMap.get(scheme.getCustomerNo());
            if (cost != null) {
                String time = DateUtil.formatDate(cost.getUpdateTime());
                alarmParam.setCollectTime(time);
                BigDecimal remainAmount = MathUtil.setPrecision(cost.getRemainAmount());
                alarmParam.setRemainAmount(String.valueOf(remainAmount));
                // 告警金额1
                BigDecimal alarmThreshold = MathUtil.setPrecision(cost.getAlarmThreshold());
                // 告警金额2
                BigDecimal alarmThreshold1 = MathUtil.setPrecision(cost.getAlarmThreshold1());
                // 告警金额3
                BigDecimal alarmThreshold2 = MathUtil.setPrecision(cost.getAlarmThreshold2());

                boolean flag = MathUtil.isLessThan(remainAmount, alarmThreshold2);
                if (flag) {
                    alarmParam.setAlarmThreshold(String.valueOf(alarmThreshold2));
                } else {
                    boolean flag2 = MathUtil.isLessThan(remainAmount, alarmThreshold1);
                    if (flag2) {
                        alarmParam.setAlarmThreshold(String.valueOf(alarmThreshold1));
                    } else {
                        alarmParam.setAlarmThreshold(String.valueOf(alarmThreshold));
                    }
                }
            }

            alarmParam.setCustomerNo(scheme.getCustomerNo());
            dataList.add(alarmParam);
        }

        return dataList;
    }
}
