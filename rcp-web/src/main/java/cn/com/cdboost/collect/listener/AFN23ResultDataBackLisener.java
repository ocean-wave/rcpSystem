package cn.com.cdboost.collect.listener;

import cn.com.cdboost.collect.constant.WebSocketConstant;
import cn.com.cdboost.collect.dto.response.AFN23Response;
import cn.com.cdboost.collect.dto.response.WebSocketResponse;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.handler.SpringWebSocketHandler;
import com.alibaba.fastjson.JSON;
import com.example.clienttest.client.AFN23DataBackLisener;
import com.example.clienttest.client.AFN23Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpSession;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 读取或设置充电桩心跳间隔时间回调
 */
@Component("aFN23ResultDataBackLisener")
public class AFN23ResultDataBackLisener implements AFN23DataBackLisener {
    private static final Logger logger = LoggerFactory.getLogger(AFN23ResultDataBackLisener.class);

    @Override
    public void onDataBack(AFN23Object afn23Object) {
        logger.info("读取或设置充电桩心跳间隔时间回调接口接收数据：AFN23Object=" + JSON.toJSONString(afn23Object));

        // WEB推送
        this.sendWebUser(afn23Object);

    }

    /**
     * WEB端推送
     * @param afn23Object
     */
    private void sendWebUser(AFN23Object afn23Object){
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
                    WebSocketResponse<AFN23Response> response = new WebSocketResponse();
                    AFN23Response res = new AFN23Response();
                    //设置值
                    res.setCommNo(afn23Object.getMoteEUI());
                    res.setStatus(afn23Object.getStatus());
                    //判断指令类型 0:读取心跳   1:设置心跳
                    if ("0".equals(afn23Object.getFlag())){
                        res.setHeartStep(afn23Object.getHeart());
                        response.setDataFlag(WebSocketConstant.DataFlagEnum.CHARGE_HEART_STEP.getFlag());
                    }else if ("1".equals(afn23Object.getFlag())){
                        response.setDataFlag(WebSocketConstant.DataFlagEnum.CHARGE_SETHEART_STEP.getFlag());
                    }
                    response.setData(res);

                    session.sendMessage(new TextMessage(JSON.toJSONString(response)));
                    logger.info("读取或设置充电桩心跳间隔时间websocket send success: data=" + JSON.toJSONString(response));
                }
            }
        }catch (Exception e) {
            logger.error("读取或设置充电桩心跳间隔时间websocket推送数据异常",e);
        }
    }
}
