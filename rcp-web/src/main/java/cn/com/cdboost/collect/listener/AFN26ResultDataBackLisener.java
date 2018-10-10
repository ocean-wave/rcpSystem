package cn.com.cdboost.collect.listener;


import cn.com.cdboost.collect.constant.WebSocketConstant;
import cn.com.cdboost.collect.dto.response.AFN26Response;
import cn.com.cdboost.collect.dto.response.WebSocketResponse;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.handler.SpringWebSocketHandler;
import com.alibaba.fastjson.JSON;
import com.example.clienttest.client.AFN26DataBackLisener;
import com.example.clienttest.client.AFN26Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpSession;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 下发读取ic卡档案回调
 */
@Component("aFN26ResultDataBackLisener")
public class AFN26ResultDataBackLisener implements AFN26DataBackLisener {
    private static final Logger logger = LoggerFactory.getLogger(AFN26ResultDataBackLisener.class);

    @Override
    public void onDataBack(AFN26Object afn26Object) {
        try {
            CopyOnWriteArraySet<WebSocketSession> users = SpringWebSocketHandler.getUsers();
            if (CollectionUtils.isEmpty(users)) {
                logger.info("webSocket 在线用户数为空");
                return;
            }
            String sessionId = afn26Object.getSessionId();
            for (WebSocketSession session :users){
                HttpSession httpSession = (HttpSession) session.getAttributes().get(GlobalConstant.HTTP_SESSION_OBJECT);
                if (httpSession != null){
                 //if (sessionId.equals(httpSession.getId())) {
                    logger.info("WebSocket中记录的sessionId=" + httpSession.getId());
                    //主动推送
                    WebSocketResponse<AFN26Response> response = new WebSocketResponse();
                    AFN26Response res = new AFN26Response();
                    //设置值
                    res.setStatus(afn26Object.getStatus());
                    //判断指令类型 0:下发ic卡档案   1:读取ic卡档案
                    if (afn26Object.getSendFlag() == 0){
                        response.setDataFlag(WebSocketConstant.DataFlagEnum.SEND_IC_CARD.getFlag());
                    }else if (afn26Object.getSendFlag() == 1){
                        res.setExist(afn26Object.isExist());
                        response.setDataFlag(WebSocketConstant.DataFlagEnum.READ_IC_CARD.getFlag());
                    }
                    response.setData(res);
                    session.sendMessage(new TextMessage(JSON.toJSONString(response)));
                    logger.info("下发读取ic卡档案 websocket send success: data=" + JSON.toJSONString(response));
                }
            }
        }catch (Exception e) {
            logger.error("下发读取ic卡档案websocket推送数据异常",e);
        }
    }
}
