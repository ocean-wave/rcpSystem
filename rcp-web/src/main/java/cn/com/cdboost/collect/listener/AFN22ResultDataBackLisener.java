package cn.com.cdboost.collect.listener;

import cn.com.cdboost.collect.constant.WebSocketConstant;
import cn.com.cdboost.collect.dto.response.AFN22Response;
import cn.com.cdboost.collect.dto.response.WebSocketResponse;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.handler.SpringWebSocketHandler;
import cn.com.cdboost.collect.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.example.clienttest.client.AFN22DataBackLisener;
import com.example.clienttest.client.AFN22Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpSession;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 查询充电桩阈值回调
 */
@Component("aFN22ResultDataBackLisener")
public class AFN22ResultDataBackLisener implements AFN22DataBackLisener {
    private static final Logger logger = LoggerFactory.getLogger(AFN22ResultDataBackLisener.class);

    @Override
    public void onDataBack(AFN22Object afn22Object) {
        logger.info("查询充电桩阈值回调接口接收数据：AFN22Object=" + JSON.toJSONString(afn22Object));

        // WEB推送
        this.sendWebUser(afn22Object);

    }

    /**
     * WEB端推送
     * @param afn22Object
     */
    private void sendWebUser(AFN22Object afn22Object){
        try {
            CopyOnWriteArraySet<WebSocketSession> users = SpringWebSocketHandler.getUsers();
            if (CollectionUtils.isEmpty(users)) {
                logger.info("webSocket 在线用户数为空");
                return;
            }

            for (WebSocketSession session :users){
                HttpSession httpSession = (HttpSession) session.getAttributes().get(GlobalConstant.HTTP_SESSION_OBJECT);
                if (httpSession != null){
                    logger.info("WebSocket中记录的sessionId=" + httpSession.getId());
                    //主动推送
                    WebSocketResponse<AFN22Response> response = new WebSocketResponse();
                    AFN22Response res = new AFN22Response();
                    //设置值
                    if (!StringUtil.isEmpty(afn22Object.getOverCurrent())){
                        res.setOverCurrent(Float.parseFloat(afn22Object.getOverCurrent()));
                    }
                    if (!StringUtil.isEmpty(afn22Object.getOverVoltage())){
                        res.setOverVoltage(Float.parseFloat(afn22Object.getOverVoltage()));
                    }
                    if (!StringUtil.isEmpty(afn22Object.getUnderVoltage())){
                        res.setUnderVoltage(Float.parseFloat(afn22Object.getUnderVoltage()));
                    }
                    if (!StringUtil.isEmpty(afn22Object.getFullCurrent())){
                        res.setUnderCurrent(Float.parseFloat(afn22Object.getFullCurrent()));
                    }
                    if (!StringUtil.isEmpty(afn22Object.getFullTime())){
                        res.setUnderCurrentDelay(Integer.parseInt(afn22Object.getFullTime()));
                    }
                    res.setCommNo(afn22Object.getMoteEUI());
                    res.setPort(afn22Object.getPort());
                    res.setStatus(afn22Object.getStatus());
                    response.setData(res);
                    response.setDataFlag(WebSocketConstant.DataFlagEnum.CHARGE_THRESHOLD.getFlag());
                    session.sendMessage(new TextMessage(JSON.toJSONString(response)));
                    logger.info("查询充电桩阈值websocket send success: data=" + JSON.toJSONString(response));
                }
            }
        }catch (Exception e) {
            logger.error("查询充电桩阈值WebSocket推送数据异常",e);
        }
    }
}
