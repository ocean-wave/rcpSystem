package cn.com.cdboost.collect.listener;

import cn.com.cdboost.collect.constant.WebSocketConstant;
import cn.com.cdboost.collect.dto.response.AFN26Response;
import cn.com.cdboost.collect.dto.response.WebSocketResponse;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.handler.SpringWebSocketHandler;
import com.alibaba.fastjson.JSON;
import com.example.clienttest.client.AFN27DataBackLisener;
import com.example.clienttest.client.AFN27Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpSession;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 清除ic卡档案
 */
@Component("aFN27ResultDataBackLisener")
public class AFN27ResultDataBackLisener implements AFN27DataBackLisener {
    private static final Logger logger = LoggerFactory.getLogger(AFN26ResultDataBackLisener.class);

    @Override
    public void onDataBack(AFN27Object afn27Object) {
        try {
            CopyOnWriteArraySet<WebSocketSession> users = SpringWebSocketHandler.getUsers();
            if (CollectionUtils.isEmpty(users)) {
                logger.info("webSocket 在线用户数为空");
                return;
            }
            String sessionId = afn27Object.getSessionId();
            for (WebSocketSession session :users){
                HttpSession httpSession = (HttpSession) session.getAttributes().get(GlobalConstant.HTTP_SESSION_OBJECT);
                if (httpSession != null){
                //if (sessionId.equals(httpSession.getId())) {
                    logger.info("WebSocket中记录的sessionId=" + httpSession.getId());
                    WebSocketResponse<AFN26Response> response = new WebSocketResponse();
                    AFN26Response afn26Response = new AFN26Response();
                    response.setDataFlag(WebSocketConstant.DataFlagEnum.CLEAR_IC_CARD.getFlag());
                    afn26Response.setStatus(afn27Object.getStatus());
                    response.setData(afn26Response);
                    session.sendMessage(new TextMessage(JSON.toJSONString(response)));
                    logger.info("清除ic卡档案 websocket send success: data=" + JSON.toJSONString(response));
                }
            }
        }catch (Exception e) {
            logger.error("清除ic卡档案websocket推送数据异常",e);
        }
    }
}
