package cn.com.cdboost.collect.listener;

import cn.com.cdboost.collect.constant.WebSocketConstant;
import cn.com.cdboost.collect.dto.response.AFN08Response;
import cn.com.cdboost.collect.dto.response.WebSocketResponse;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.handler.SpringWebSocketHandler;
import cn.com.cdboost.collect.util.CNoUtil;
import com.alibaba.fastjson.JSON;
import com.example.clienttest.client.AFN08DataBackLisener;
import com.example.clienttest.client.AFN08Object;
import com.example.clienttest.client.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 通断数据回调接口实现类
 */
@Component("aFN08ResultDataBackLisener")
public class AFN08ResultDataBackLisener implements AFN08DataBackLisener {
    private static final Logger logger = LoggerFactory.getLogger(AFN08ResultDataBackLisener.class);

    @Autowired
    SpringWebSocketHandler springWebSocketHandler;

    @Override
    public void onDataBack(AFN08Object afn08Object, ResultInfo resultInfo) {
        logger.info("通断回调接口接收数据：AFN08Object=" + JSON.toJSONString(afn08Object) + ",ResultInfo=" + JSON.toJSONString(resultInfo));
        logger.info("通断回调接口接收数据");
        try {
            CopyOnWriteArraySet<WebSocketSession> users = springWebSocketHandler.getUsers();
            if (CollectionUtils.isEmpty(users)) {
                logger.info("webSocket 在线用户数为空");
                return;
            }

            String sessionId = afn08Object.getSessionId();
            for (WebSocketSession session : users) {
                HttpSession httpSession = (HttpSession) session.getAttributes().get(GlobalConstant.HTTP_SESSION_OBJECT);
                logger.info("WebSocket中记录的sessionId=" + httpSession.getId());
//                if (true) {
                if (sessionId.equals(httpSession.getId())) {
                    Boolean stopFlag = (Boolean) session.getAttributes().get(GlobalConstant.ON_OFF_STOP_FLAG);
                    if (stopFlag != null && stopFlag) {
                        logger.info("前端已主动关闭通断操作,本条记录不返回给前端");
                        break;
                    }
                    AFN08Response response = new AFN08Response();
                    response.setTaskStatus(resultInfo.getStatus());
                    String cno = CNoUtil.CreateCNo(afn08Object.getMoteType(), afn08Object.getDbdz());
                    response.setCno(cno);
                    response.setState(afn08Object.getState());
                    response.setDealNum(resultInfo.getDealNum());
                    ArrayList<AFN08Object> failAFN08List = resultInfo.getFailAFN08List();
                    if (!CollectionUtils.isEmpty(failAFN08List)) {
                        response.setFailNum(failAFN08List.size());
                    }
                    response.setSuccessfulNum(resultInfo.getSuccessfulNum());
                    response.setUndealNum(resultInfo.getUndealNum());

                    // 发送消息给前端
                    WebSocketResponse<AFN08Response> socketResponse = new WebSocketResponse<>();
                    socketResponse.setDataFlag(WebSocketConstant.DataFlagEnum.ON_OFF.getFlag());
                    socketResponse.setData(response);
                    session.sendMessage(new TextMessage(JSON.toJSONString(socketResponse)));
                    logger.info("通断websocket send success: data=" + JSON.toJSONString(socketResponse));
                }
            }
        } catch (Exception e) {
            logger.error("通断回调接口异常：",e);
        }

    }
}
