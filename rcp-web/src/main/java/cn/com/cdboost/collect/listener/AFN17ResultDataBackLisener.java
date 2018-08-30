package cn.com.cdboost.collect.listener;

import cn.com.cdboost.collect.enums.Action;
import cn.com.cdboost.collect.service.UserLogService;
import com.alibaba.fastjson.JSON;
import com.example.clienttest.client.AFN17DataBackLisener;
import com.example.clienttest.client.AFN17Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 立即算费数据返回
 */
@Component("aFN17ResultDataBackLisener")
public class AFN17ResultDataBackLisener implements AFN17DataBackLisener {

    private static final Logger logger = LoggerFactory.getLogger(AFN17ResultDataBackLisener.class);

    @Autowired
    UserLogService  userLogService;
    @Override
    public void onDataBack(AFN17Object afn17Object) {
        logger.info("立即算费回调接口接收数据：AFN17Object=" + JSON.toJSONString(afn17Object) + ",ResultInfo=" + JSON.toJSONString(afn17Object));
        try {
           /* String msg = afn17Object.getMsg();
            String queueGuid = afn17Object.getQueueGuid();
            userLogService.create(1, Action.CHANGE_METER.getActionId(),"立即算费","queueGuid", queueGuid, "立即算费批次["+queueGuid+"]"+msg,JSON.toJSONString(queueGuid));*/
        } catch (Exception e) {
            logger.error("立即算费调接口异常：",e);
        }
    }

}
