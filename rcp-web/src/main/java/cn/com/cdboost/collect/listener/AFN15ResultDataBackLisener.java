package cn.com.cdboost.collect.listener;

import cn.com.cdboost.collect.constant.ClientConstant;
import cn.com.cdboost.collect.constant.WebSocketConstant;
import cn.com.cdboost.collect.dto.response.AFN04ObjectDataField;
import cn.com.cdboost.collect.dto.response.AFN15Response;
import cn.com.cdboost.collect.dto.response.WebSocketResponse;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.handler.SpringWebSocketHandler;
import cn.com.cdboost.collect.util.CNoUtil;
import com.alibaba.fastjson.JSON;
import com.example.clienttest.client.AFN15DataBackLisener;
import com.example.clienttest.client.AFN15Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 重点用户广播数据返回
 */
@Component("aFN15ResultDataBackLisener")
public class AFN15ResultDataBackLisener implements AFN15DataBackLisener {

    private static final Logger logger = LoggerFactory.getLogger(AFN15ResultDataBackLisener.class);

    @Autowired
    SpringWebSocketHandler springWebSocketHandler;

    @Override
    public void onDataBack(AFN15Object afn15Object) {
        logger.info("重点用户广播回调接口接收数据：AFN15Object=" + JSON.toJSONString(afn15Object) + ",ResultInfo=" + JSON.toJSONString(afn15Object));
        try {
            CopyOnWriteArraySet<WebSocketSession> users = springWebSocketHandler.getUsers();
            if (CollectionUtils.isEmpty(users)) {
                logger.info("webSocket 在线用户数为空");
                return;
            }

            String sessionId = afn15Object.getSessionId();
            for (WebSocketSession session : users) {
                HttpSession httpSession = (HttpSession) session.getAttributes().get(GlobalConstant.HTTP_SESSION_OBJECT);
//                logger.info("WebSocket中记录的sessionId=" + httpSession.getId());
//                if (true) {
                if (sessionId.equals(httpSession.getId())) {
                    Boolean stopFlag = (Boolean) httpSession.getAttribute(GlobalConstant.BROADCAST_STOP_FLAG);
                    if (stopFlag != null && stopFlag) {
                        logger.info("前端已主动关闭广播抄表,之后记录都不返回给前端");
                        break;
                    }
                    int status = afn15Object.getStatus();
                    AFN15Response afn15Response = new AFN15Response();
                    afn15Response.setStatus(status);
                    afn15Response.setMeterReadedTotal(afn15Object.getMeterReadedTotal());
                    afn15Response.setMeterTotal(afn15Object.getMeterTotal());
                    afn15Response.setCno(afn15Object.getCNo());
                    String deviceNo = CNoUtil.getNo(afn15Object.getCNo());
                    afn15Response.setDeviceNo(deviceNo);
                    if (status == 1) {
                        String data = afn15Object.getData();
                        if (StringUtils.isEmpty(data)) {
                            logger.info("广播抄表返回数据为空，丢弃");
                            break;
                        }

                        List<AFN04ObjectDataField> dataFields = JSON.parseArray(data, AFN04ObjectDataField.class);
                        if (CollectionUtils.isEmpty(dataFields)) {
                            logger.info("中间件返回抄表数据data项为空");
                            break;
                        }

                        this.setResponseData(dataFields,afn15Response);
                        WebSocketResponse<AFN15Response> response = new WebSocketResponse<>();
                        response.setDataFlag(WebSocketConstant.DataFlagEnum.COLLECT_DATA_BROADCAST.getFlag());
                        response.setData(afn15Response);
                        session.sendMessage(new TextMessage(JSON.toJSONString(response)));
                        logger.info("websocket send success: data=" + JSON.toJSONString(response));
                    } else {
                        WebSocketResponse<AFN15Response> response = new WebSocketResponse<>();
                        response.setDataFlag(WebSocketConstant.DataFlagEnum.COLLECT_DATA_BROADCAST.getFlag());
                        response.setData(afn15Response);
                        session.sendMessage(new TextMessage(JSON.toJSONString(response)));
                        logger.info("websocket send success: data=" + JSON.toJSONString(response));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("重点用户广播回调接口异常：",e);
        }
    }

    private void setResponseData(List<AFN04ObjectDataField> dataFields, AFN15Response response) {
        for (AFN04ObjectDataField dataField : dataFields) {
            String readType = dataField.getReadType();
            String readValue = dataField.getReadValue();
            if (ClientConstant.ReadTypeEnum.PAY_MONEY.getCode().equals(readType)) {
                response.setPayMoney(readValue);
            } else if (ClientConstant.ReadTypeEnum.PAY_COUNT.getCode().equals(readType)) {
                response.setPayCount(readValue);
            } else if (ClientConstant.ReadTypeEnum.BALANCE.getCode().equals(readType)) {
                response.setBalance(readValue);
            } else if (ClientConstant.ReadTypeEnum.PR0.getCode().equals(readType)) {
                response.setPr0(readValue);
            } else if (ClientConstant.ReadTypeEnum.PR1.getCode().equals(readType)) {
                response.setPr1(readValue);
            } else if (ClientConstant.ReadTypeEnum.PR2.getCode().equals(readType)) {
                response.setPr2(readValue);
            } else if (ClientConstant.ReadTypeEnum.PR3.getCode().equals(readType)) {
                response.setPr3(readValue);
            } else if (ClientConstant.ReadTypeEnum.PR4.getCode().equals(readType)) {
                response.setPr4(readValue);
            } else if (ClientConstant.ReadTypeEnum.CURRENT_A.getCode().equals(readType)) {
                response.setCurrentA(readValue);
            } else if (ClientConstant.ReadTypeEnum.CURRENT_B.getCode().equals(readType)) {
                response.setCurrentB(readValue);
            } else if (ClientConstant.ReadTypeEnum.CURRENT_C.getCode().equals(readType)) {
                response.setCurrentC(readValue);
            } else if (ClientConstant.ReadTypeEnum.VOLTAGE_A.getCode().equals(readType)) {
                response.setVoltageA(readValue);
            } else if (ClientConstant.ReadTypeEnum.VOLTAGE_B.getCode().equals(readType)) {
                response.setVoltageB(readValue);
            } else if (ClientConstant.ReadTypeEnum.VOLTAGE_C.getCode().equals(readType)) {
                response.setVoltageC(readValue);
            } else if (ClientConstant.ReadTypeEnum.SMOKE_BATTERY_LEVEL.getCode().equals(readType)){
                response.setSmokeBatteryLevel(readValue);
            } else if (ClientConstant.ReadTypeEnum.SMOKE_ALARM.getCode().equals(readType)){
                response.setSmokeAlarm(readValue);
            } else if (ClientConstant.ReadTypeEnum.TEMPERATURE.getCode().equals(readType)){
                response.setTemperature(readValue);
            } else if (ClientConstant.ReadTypeEnum.HUMIDITY.getCode().equals(readType)){
                response.setHumidity(readValue);
            } else if (ClientConstant.ReadTypeEnum.ILLUMINANCE.getCode().equals(readType)){
                response.setIlluminance(readValue);
            }
        }
    }
}
