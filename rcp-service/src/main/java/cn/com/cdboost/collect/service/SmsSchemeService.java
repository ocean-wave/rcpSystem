package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.param.SmsSchemeUpdateParam;
import cn.com.cdboost.collect.model.SmsScheme;

import java.util.Date;
import java.util.List;

/**
 * 消息提醒方案表服务/
 */
public interface SmsSchemeService extends BaseService<SmsScheme> {
    int updateCountAndSendTime(String customerNo,String mobilePhone,Date sendTime, Date updateTime);

    // 根据条件批量更新
    int batchUpdate(List<SmsSchemeUpdateParam> param);

    // 按条件查询
    List<SmsScheme> queryByParams(String customerNo,String cno,List<String> mobiles);

    void renewData3();

    void renewData2();

    // 更新告警级别一的数据（删除不满足告警的，加入新增满足告警的）
    void renewData1();

    List<SmsScheme> queryByAlarmLevel(Integer alarmLevel);

    List<SmsScheme> queryAlarmThreeSchemeInfo(Integer sendCount, Integer time);
    List<SmsScheme> queryAlarmTwoSchemeInfo();

    List<SmsScheme> queryAlarmOneSchemeInfo();

    // 发送短信和微信通知
    void sendSmsWeixin(List<SmsScheme> smsSchemes);
}
