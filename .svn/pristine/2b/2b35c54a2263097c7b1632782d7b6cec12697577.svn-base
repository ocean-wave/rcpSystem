package cn.com.cdboost.collect.listener;

import cn.com.cdboost.collect.constant.ClientConstant;
import cn.com.cdboost.collect.constant.CustomerInfoConstant;
import cn.com.cdboost.collect.constant.WebSocketConstant;
import cn.com.cdboost.collect.dto.response.AFN14Response;
import cn.com.cdboost.collect.dto.response.WebSocketResponse;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.handler.SpringWebSocketHandler;
import com.alibaba.fastjson.JSON;
import com.example.clienttest.client.AFN14DataBackLisener;
import com.example.clienttest.client.AFN14Object;
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
 * 集中器读取客户档案资料，数据返回监听实现类
 */
@Component("aFN14ResultDataBackLisener")
public class AFN14ResultDataBackLisener implements AFN14DataBackLisener {

    private static final Logger logger = LoggerFactory.getLogger(AFN14ResultDataBackLisener.class);

    @Autowired
    private SpringWebSocketHandler springWebSocketHandler;

    @Override
    public void onDataBack(AFN14Object afn14Object) {
        logger.info("集中器读取客户资料回调接口接收数据：AFN14Object=" + JSON.toJSONString(afn14Object));
        try {
            CopyOnWriteArraySet<WebSocketSession> users = springWebSocketHandler.getUsers();
            if (CollectionUtils.isEmpty(users)) {
                logger.info("webSocket 在线用户数为空");
                return;
            }

            String sessionId = afn14Object.getSessionId();
            for (WebSocketSession socketSession : users) {
                HttpSession httpSession = (HttpSession) socketSession.getAttributes().get(GlobalConstant.HTTP_SESSION_OBJECT);
                logger.info("WebSocket中记录的sessionId=" + httpSession.getId());
//                if (true) {
                if (sessionId.equals(httpSession.getId())) {
                    AFN14Response response = new AFN14Response();
                    response.setJzqNo(afn14Object.getJzqAddr());
                    response.setCommSetupSn(Integer.valueOf(afn14Object.getJld()));
                    response.setCommPointCode(Integer.valueOf(afn14Object.getCld()));
                    Integer rate = CustomerInfoConstant.CommBaudrateEnum.getRateByValue(Integer.valueOf(afn14Object.getBaudrate()));
                    response.setCommBaudrate(rate);
                    String descByCommPort = ClientConstant.DataCollectPort.getDescByCommPort(Integer.valueOf(afn14Object.getPort()));
                    response.setCommPort(descByCommPort);
                    String descByCommRule = ClientConstant.CommRuleDescEnum.getDescByCommRule(Integer.valueOf(afn14Object.getGy()));
                    response.setCommRule(descByCommRule);
                    String deviceNo = afn14Object.getDbdz();
                    String tempDeviceNo = deviceNo.replaceAll("^0*", "");
                    response.setDeviceNo(tempDeviceNo);
                    response.setFeeRateNum(Integer.valueOf(afn14Object.getFlnum()));
                    response.setCjqNo(afn14Object.getCjq());

                    // 发送消息给前端
                    WebSocketResponse<AFN14Response> socketResponse = new WebSocketResponse<>();
                    socketResponse.setDataFlag(WebSocketConstant.DataFlagEnum.JZQ_READ_CUSTOMER_INFO.getFlag());
                    socketResponse.setData(response);
                    socketSession.sendMessage(new TextMessage(JSON.toJSONString(socketResponse)));
                    logger.info("集中器读取客户资料websocket send success: data=" + JSON.toJSONString(socketResponse));
                }
            }
        } catch (Exception e) {
            logger.error("集中器读取客户资料回调接口异常：",e);
        }
    }
}
