package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.param.SmsSchemeUpdateParam;
import cn.com.cdboost.collect.model.SmsScheme;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SmsSchemeMapper extends CommonMapper<SmsScheme> {
    // 根据手机号更新短信发送时间，并且发送次数加1
    int updateCountAndSendTime(@Param("customerNo") String customerNo,
                               @Param("mobilePhone") String mobilePhone,
                               @Param("sendTime") Date sendTime,
                               @Param("updateTime") Date updateTime);

    // 根据条件批量更新
    int batchUpdate(List<SmsSchemeUpdateParam> param);

    List<SmsScheme>  queryWarning3();

    List<SmsScheme>  queryWarning2();

    List<SmsScheme>  queryWarning1();

    List<SmsScheme> queryAlarmSchemeInfo(@Param("sendCount") Integer sendCount,
                                         @Param("time") Integer time,
                                         @Param("alarmLevel") Integer alarmLevel);
}