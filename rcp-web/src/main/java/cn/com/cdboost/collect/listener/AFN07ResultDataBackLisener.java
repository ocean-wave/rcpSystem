package cn.com.cdboost.collect.listener;

import cn.com.cdboost.collect.constant.ClientConstant;
import cn.com.cdboost.collect.constant.WebSocketConstant;
import cn.com.cdboost.collect.dto.response.AFN07Response;
import cn.com.cdboost.collect.dto.response.WebSocketResponse;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.handler.SpringWebSocketHandler;
import com.alibaba.fastjson.JSON;
import com.example.clienttest.client.AFN07DataBackLisener;
import com.example.clienttest.client.AFN07Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpSession;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 集中器心跳，下发、读取回调接口实现类
 */
@Component("aFN07ResultDataBackLisener")
public class AFN07ResultDataBackLisener implements AFN07DataBackLisener {
    private static final Logger logger = LoggerFactory.getLogger(AFN07ResultDataBackLisener.class);

    @Autowired
    SpringWebSocketHandler springWebSocketHandler;

    @Override
    public void onDataBack(AFN07Object afn07Object) {
        logger.info("集中器心跳读取回调接口接收数据：AFN07Object=" + JSON.toJSONString(afn07Object));
        try {
            CopyOnWriteArraySet<WebSocketSession> users = springWebSocketHandler.getUsers();
            if (CollectionUtils.isEmpty(users)) {
                logger.info("webSocket 在线用户数为空");
                return;
            }

            String sessionId = afn07Object.getSessionId();
            String flag = afn07Object.getFlag();
            int status = afn07Object.getStatus();
            for (WebSocketSession socketSession : users) {
                HttpSession httpSession = (HttpSession) socketSession.getAttributes().get(GlobalConstant.HTTP_SESSION_OBJECT);
                logger.info("WebSocket中记录的sessionId=" + httpSession.getId());
//                if (true) {
                if (sessionId.equals(httpSession.getId())) {
                    if (ClientConstant.AFN07ObjectFlag.READ.getFlag().equals(flag)) {
                        // 读取返回
                        WebSocketResponse<AFN07Response> socketResponse = new WebSocketResponse<>();
                        AFN07Response response = new AFN07Response();
                        response.setStatus(status);
                        response.setHbCycle(afn07Object.getHeartbeat());
                        String message = ClientConstant.FrontProcessorReturnCode.getDescByCode(status);
                        response.setMessage(message);
                        socketResponse.setData(response);
                        socketResponse.setDataFlag(WebSocketConstant.DataFlagEnum.JZQ_HEART_BEAT_READ.getFlag());
                        socketSession.sendMessage(new TextMessage(JSON.toJSONString(socketResponse)));
                        logger.info("集中器读取返回websocket send success: data=" + JSON.toJSONString(socketResponse));
                    } else if(ClientConstant.AFN07ObjectFlag.SEND.getFlag().equals(flag)) {
                        // 下发返回
                        WebSocketResponse<String> response = new WebSocketResponse<>();
                        String message = ClientConstant.FrontProcessorReturnCode.getDescByCode(status);
                        response.setData(message);
                        response.setDataFlag(WebSocketConstant.DataFlagEnum.JZQ_HEART_BEAT_SEND.getFlag());
                        socketSession.sendMessage(new TextMessage(JSON.toJSONString(response)));
                        logger.info("集中器下发返回websocket send success: data=" + JSON.toJSONString(response));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("集中器心跳读取回调接口异常：",e);
        }
    }
}
