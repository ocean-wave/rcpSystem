package cn.com.cdboost.collect.listener;

import cn.com.cdboost.collect.constant.WebSocketConstant;
import cn.com.cdboost.collect.dto.response.AFN29Response;
import cn.com.cdboost.collect.dto.response.MonitorDeviceRes;
import cn.com.cdboost.collect.dto.response.WebSocketResponse;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.handler.SpringWebSocketHandler;
import cn.com.cdboost.collect.model.ChargingDevice;
import cn.com.cdboost.collect.model.ChargingUseDetailed;
import cn.com.cdboost.collect.service.ChargingDeviceService;
import cn.com.cdboost.collect.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.example.clienttest.client.AFN29DataBackLisener;
import com.example.clienttest.client.AFN29Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 充电桩设备信号强度实时推送
 */
@Component("aFN29ResultDataBackLisener")
public class AFN29ResultDataBackLisener implements AFN29DataBackLisener{
    private static final Logger logger = LoggerFactory.getLogger(AFN29ResultDataBackLisener.class);
    @Autowired
    private ChargingDeviceService chargingDeviceService;
    @Override
    public void onDataBack(AFN29Object afn29Object) {
        this.sendWebUser(afn29Object);
    }

    private void sendWebUser(AFN29Object afn29Object){
        try {
            CopyOnWriteArraySet<WebSocketSession> users = SpringWebSocketHandler.getUsers();
            if (CollectionUtils.isEmpty(users)) {
//                logger.info("webSocket 在线用户数为空");
                return;
            }
            for (WebSocketSession session :users){
                if (!session.isOpen()) {
                    users.remove(session);
                    continue;
                }

                HttpSession httpSession = (HttpSession) session.getAttributes().get(GlobalConstant.HTTP_SESSION_OBJECT);
                if (httpSession != null){
//                    logger.info("WebSocket中记录的sessionId=" + httpSession.getId());
                    //主动推送
                    WebSocketResponse<AFN29Response> response = new WebSocketResponse<>();
                    AFN29Response afn29Response = new AFN29Response();
                    //设备信号强度
                    if (!StringUtil.isEmpty(afn29Object.getRssi())){
                        afn29Response.setRssi(Integer.parseInt(afn29Object.getRssi()));
                    }
                    if (!StringUtil.isEmpty(afn29Object.getSnr())){
                        afn29Response.setSnr(BigDecimal.valueOf(Double.valueOf(afn29Object.getSnr())));
                    }
                    afn29Response.setCommNo(afn29Object.getComm_no());
                    response.setData(afn29Response);
                    response.setDataFlag(WebSocketConstant.DataFlagEnum.WEB_DEVICE_SINGAL.getFlag());
                    session.sendMessage(new TextMessage(JSON.toJSONString(response)));
                    logger.info("充电桩信号强度websocket send success: data=" + JSON.toJSONString(response));
                }
            }
        }catch (Exception e) {
            logger.error("充电桩信号强度WebSocket推送数据异常",e);
        }
    }
}
