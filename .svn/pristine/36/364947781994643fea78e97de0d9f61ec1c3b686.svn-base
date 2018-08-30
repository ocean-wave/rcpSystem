package cn.com.cdboost.collect.listener;

import cn.com.cdboost.collect.constant.ClientConstant;
import cn.com.cdboost.collect.constant.WebSocketConstant;
import cn.com.cdboost.collect.dto.response.WebSocketResponse;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.handler.SpringWebSocketHandler;
import com.alibaba.fastjson.JSON;
import com.example.clienttest.client.AFN05DataBackLisener;
import com.example.clienttest.client.AFN05Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 集中器主站连接参数下发，回调接口实现类
 */
@Component("aFN05ResultDataBackLisener")
public class AFN05ResultDataBackLisener implements AFN05DataBackLisener {
    private static final Logger logger = LoggerFactory.getLogger(AFN05ResultDataBackLisener.class);

    @Autowired
    SpringWebSocketHandler springWebSocketHandler;

    @Override
    public void onDataBack(AFN05Object afn05Object) {
        logger.info("集中器主站连接参数回调接口接收数据：AFN05Object=" + JSON.toJSONString(afn05Object));
        try {
            CopyOnWriteArraySet<WebSocketSession> users = springWebSocketHandler.getUsers();
            if (CollectionUtils.isEmpty(users)) {
                logger.info("webSocket 在线用户数为空");
                return;
            }

            int status = afn05Object.getStatus();
            String sessionId = afn05Object.getSessionId();
            for (WebSocketSession socketSession : users) {
                String httpSessionId = (String) socketSession.getAttributes().get(GlobalConstant.HTTP_SESSION_ID);
                logger.info("WebSocket中记录的sessionId=" + httpSessionId);
                if (sessionId.equals(httpSessionId)) {
//                if (true) {
                    WebSocketResponse<String> response = new WebSocketResponse<>();
                    String message = ClientConstant.FrontProcessorReturnCode.getDescByCode(status);
                    response.setData(message);
                    response.setDataFlag(WebSocketConstant.DataFlagEnum.JZQ_CONNECT_PARAM_SEND.getFlag());
                    socketSession.sendMessage(new TextMessage(JSON.toJSONString(response)));
                    logger.info("websocket send success: data=" + JSON.toJSONString(response));
                }
            }
        } catch (Exception e) {
            logger.error("集中器主站连接参数下发回调接口异常：",e);
        }
    }
}
