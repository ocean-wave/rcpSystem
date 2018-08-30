package cn.com.cdboost.collect.dto.param;

import java.util.List;

/**
 * 短信告警需要的参数
 */
public class SmsAlarmParam extends AlarmBaseParam{

    /**
     * 该用户所关联的手机号
     */
    private List<String> mobiles;

    public List<String> getMobiles() {
        return mobiles;
    }

    public void setMobiles(List<String> mobiles) {
        this.mobiles = mobiles;
    }
}
