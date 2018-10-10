package cn.com.cdboost.collect.listener;

import cn.com.cdboost.collect.dto.param.OnlineOfflineParam;
import cn.com.cdboost.collect.model.ChargingDevice;
import cn.com.cdboost.collect.service.AliyunSmsService;
import cn.com.cdboost.collect.service.ChargingDeviceService;
import com.alibaba.fastjson.JSON;
import com.example.clienttest.client.AFN28DataBackLisener;
import com.example.clienttest.client.AFN28Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 充电桩设备上下线回调通知
 */
@Component("aFN28ResultDataBackLisener")
public class AFN28ResultDataBackLisener implements AFN28DataBackLisener {
    private static final Logger logger = LoggerFactory.getLogger(AFN28ResultDataBackLisener.class);

    @Autowired
    private ChargingDeviceService chargingDeviceService;
    @Autowired
    private AliyunSmsService aliyunSmsService;

    @Override
    public void onDataBack(AFN28Object afn28Object) {
        logger.info("充电桩设备上下线回调：AFN28Object=" + JSON.toJSONString(afn28Object));
        String devNo = afn28Object.getDevNo();
        ChargingDevice chargingDevice = chargingDeviceService.queryByDeviceNo(devNo, "1");

        OnlineOfflineParam param = new OnlineOfflineParam();
        param.setMobilePhone("18030501521");
        param.setDeviceNo(chargingDevice.getDeviceNo());
        param.setInstallAddr(chargingDevice.getInstallAddr());
        String dateTime = afn28Object.getDateTime();
        int event = afn28Object.getEvent();
        String desc;
        if (event == 0) {
            // 离线
            desc = dateTime + "离线";
        } else {
            desc = dateTime + "上线";
        }
        param.setTime(desc);
        aliyunSmsService.sendDeviceOnlineOffline(param);
    }
}
