package cn.com.cdboost.collect.listener;

import cn.com.cdboost.collect.constant.WebSocketConstant;
import cn.com.cdboost.collect.dto.response.ChargerStatusRes;
import cn.com.cdboost.collect.dto.response.MonitorDeviceRes;
import cn.com.cdboost.collect.dto.response.WebSocketResponse;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.handler.SpringWebSocketHandler;
import com.alibaba.fastjson.JSON;
import com.example.clienttest.client.AFN21DataBackLisener;
import com.example.clienttest.client.AFN21Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpSession;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 设置充电桩阈值回调
 */
@Component("aFN21ResultDataBackLisener")
public class AFN21ResultDataBackLisener implements AFN21DataBackLisener {
    private static final Logger logger = LoggerFactory.getLogger(AFN21ResultDataBackLisener.class);

    @Override
    public void onDataBack(AFN21Object afn21Object) {
        logger.info("设置充电桩阈值回调接口接收数据：AFN21Object=" + JSON.toJSONString(afn21Object));

        // WEB推送
        this.sendWebUser(afn21Object);

    }

    /**
     * WEB端推送
     * @param afn21Object
     */
    private void sendWebUser(AFN21Object afn21Object){
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
                    WebSocketResponse<ChargerStatusRes> response = new WebSocketResponse();
                    ChargerStatusRes res = new ChargerStatusRes();
                    res.setStatus(afn21Object.getStatus());
                    res.setCommNo(afn21Object.getMoteEUI());
                    res.setPort(afn21Object.getPort());
                    response.setData(res);
                    response.setDataFlag(WebSocketConstant.DataFlagEnum.CHARGE_SET_THRESHOLD.getFlag());
                    session.sendMessage(new TextMessage(JSON.toJSONString(response)));
                    logger.info("设置充电桩阈值websocket send success: data=" + JSON.toJSONString(response));
                }
            }
        }catch (Exception e) {
            logger.error("设置充电桩阈值WebSocket推送数据异常",e);
        }
    }
}
