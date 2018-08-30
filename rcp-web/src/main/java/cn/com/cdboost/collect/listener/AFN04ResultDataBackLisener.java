package cn.com.cdboost.collect.listener;

import cn.com.cdboost.collect.constant.ClientConstant;
import cn.com.cdboost.collect.constant.WebSocketConstant;
import cn.com.cdboost.collect.dto.response.AFN04ObjectDataField;
import cn.com.cdboost.collect.dto.response.AFN04Response;
import cn.com.cdboost.collect.dto.response.WebSocketResponse;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.handler.SpringWebSocketHandler;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.util.MathUtil;
import com.alibaba.fastjson.JSON;
import com.example.clienttest.client.AFN04DataBackLisener;
import com.example.clienttest.client.AFN04Object;
import com.example.clienttest.client.ResultInfo;
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
 * 实时抄表数据回调接口实现类
 */
@Component("aFN04ResultDataBackLisener")
public class AFN04ResultDataBackLisener implements AFN04DataBackLisener {
    private static final Logger logger = LoggerFactory.getLogger(AFN04ResultDataBackLisener.class);

    @Autowired
    SpringWebSocketHandler springWebSocketHandler;

    @Override
    public void onDataBack(AFN04Object afn04Object, ResultInfo resultInfo) {
        logger.info("实时抄表回调接口接收数据：AFN04Object=" + JSON.toJSONString(afn04Object) + ",ResultInfo=" + JSON.toJSONString(resultInfo));
        try {
            CopyOnWriteArraySet<WebSocketSession> users = springWebSocketHandler.getUsers();
            if (CollectionUtils.isEmpty(users)) {
                logger.info("webSocket 在线用户数为空");
                return;
            }

            String sessionId = afn04Object.getSessionId();
            for (WebSocketSession session : users) {
                HttpSession httpSession = (HttpSession) session.getAttributes().get(GlobalConstant.HTTP_SESSION_OBJECT);
                logger.info("WebSocket中记录的sessionId=" + httpSession.getId());
//                if (true) {
                if (sessionId.equals(httpSession.getId())) {
                    Boolean stopFlag = (Boolean) httpSession.getAttribute(GlobalConstant.COLLECT_DATA_STOP_FLAG);
                    if (stopFlag != null && stopFlag) {
                        logger.info("前端已主动关闭抄表,本条记录不返回给前端");
                        break;
                    }
                    AFN04Response response = new AFN04Response();
                    response.setTaskStatus(resultInfo.getStatus());
                    response.setDeviceCollectStatus(afn04Object.getStatus());
                    // 该字段是发送时，给AFN04Object对象传入的MoteType
                    String moteType = afn04Object.getMoteType();
                    String cno = CNoUtil.CreateCNo(moteType, afn04Object.getDbdz());
                    response.setCno(cno);
                    String data = afn04Object.getData();
                    if (!StringUtils.isEmpty(data)) {
                        List<AFN04ObjectDataField> dataFields = JSON.parseArray(data, AFN04ObjectDataField.class);
                        if (CollectionUtils.isEmpty(dataFields)) {
                            logger.info("中间件返回抄表数据data项为空");
                            break;
                        }
                        String collectType = afn04Object.getCollectType();
                        if ("1".equals(collectType)) {
                            // 重点用户抄表数据返回
                            this.setImportantUserData(dataFields,response);
                        } else {
                            // 普通用户抄表数据返回
                            this.setCommonUserData(dataFields,response);
                        }
                    }

                    response.setCollectTime(afn04Object.getTime());
                    response.setDealNum(resultInfo.getDealNum());

                    // 发送
                    WebSocketResponse<AFN04Response> socketResponse = new WebSocketResponse<>();
                    socketResponse.setData(response);
                    socketResponse.setDataFlag(WebSocketConstant.DataFlagEnum.COLLECT_DATA.getFlag());
                    session.sendMessage(new TextMessage(JSON.toJSONString(socketResponse)));
                    logger.info("实时抄表websocket send success: data=" + JSON.toJSONString(socketResponse));
                }
            }
        } catch (Exception e) {
            logger.error("实时抄表回调接口异常：",e);
        }
    }


    /**
     * 设置重点用户的抄表返回
     * @param dataFields
     * @param response
     */
    private void setImportantUserData(List<AFN04ObjectDataField> dataFields, AFN04Response response) {
        for (AFN04ObjectDataField dataField : dataFields) {
            String readType = dataField.getReadType();
            String readValue = dataField.getReadValue();
            if (ClientConstant.ReadTypeEnum.PR0.getCode().equals(readType)) {
                response.setPr0(MathUtil.setPrecision(readValue, 2));
            } else if (ClientConstant.ReadTypeEnum.PR1.getCode().equals(readType)) {
                response.setPr1(MathUtil.setPrecision(readValue, 2));
            } else if (ClientConstant.ReadTypeEnum.PR2.getCode().equals(readType)) {
                response.setPr2(MathUtil.setPrecision(readValue, 2));
            } else if (ClientConstant.ReadTypeEnum.PR3.getCode().equals(readType)) {
                response.setPr3(MathUtil.setPrecision(readValue, 2));
            } else if (ClientConstant.ReadTypeEnum.PR4.getCode().equals(readType)) {
                response.setPr4(MathUtil.setPrecision(readValue, 2));
            } else if (ClientConstant.ReadTypeEnum.CURRENT_A.getCode().equals(readType)) {
                response.setCurrentA(MathUtil.setPrecision(readValue, 3));
            } else if (ClientConstant.ReadTypeEnum.CURRENT_B.getCode().equals(readType)) {
                response.setCurrentB(MathUtil.setPrecision(readValue, 3));
            } else if (ClientConstant.ReadTypeEnum.CURRENT_C.getCode().equals(readType)) {
                response.setCurrentC(MathUtil.setPrecision(readValue, 3));
            } else if (ClientConstant.ReadTypeEnum.VOLTAGE_A.getCode().equals(readType)) {
                response.setVoltageA(MathUtil.setPrecision(readValue, 1));
            } else if (ClientConstant.ReadTypeEnum.VOLTAGE_B.getCode().equals(readType)) {
                response.setVoltageB(MathUtil.setPrecision(readValue, 1));
            } else if (ClientConstant.ReadTypeEnum.VOLTAGE_C.getCode().equals(readType)) {
                response.setVoltageC(MathUtil.setPrecision(readValue, 1));
            } else if (ClientConstant.ReadTypeEnum.INSTANT_POWER.getCode().equals(readType)) {
                response.setInstantPower(MathUtil.setPrecision(readValue, 4));
            } else if (ClientConstant.ReadTypeEnum.INSTANT_POWER_A.getCode().equals(readType)) {
                response.setInstantPowerA(MathUtil.setPrecision(readValue, 4));
            } else if (ClientConstant.ReadTypeEnum.INSTANT_POWER_B.getCode().equals(readType)) {
                response.setInstantPowerB(MathUtil.setPrecision(readValue, 4));
            } else if (ClientConstant.ReadTypeEnum.INSTANT_POWER_C.getCode().equals(readType)) {
                response.setInstantPowerC(MathUtil.setPrecision(readValue, 4));
            }
        }
    }

    /**
     * 设置普通用户的抄表返回
     * @param field
     * @param response
     */
    private void setCommonUserData(List<AFN04ObjectDataField> field, AFN04Response response) {
        for (AFN04ObjectDataField dataField : field) {
            String readType = dataField.getReadType();
            String readValue = dataField.getReadValue();
            // 处理精度
            String readValueStr = MathUtil.setPrecision(readValue, 2);
            if (ClientConstant.ReadTypeEnum.BALANCE.getCode().equals(readType)) {
                response.setBalance(readValueStr);
            } else if (ClientConstant.ReadTypeEnum.PR0.getCode().equals(readType)) {
                response.setPr0(readValueStr);
            } else if (ClientConstant.ReadTypeEnum.PR1.getCode().equals(readType)) {
                response.setPr1(readValueStr);
            } else if (ClientConstant.ReadTypeEnum.PR2.getCode().equals(readType)) {
                response.setPr2(readValueStr);
            } else if (ClientConstant.ReadTypeEnum.PR3.getCode().equals(readType)) {
                response.setPr3(readValueStr);
            } else if (ClientConstant.ReadTypeEnum.PR4.getCode().equals(readType)) {
                response.setPr4(readValueStr);
            } else if (ClientConstant.ReadTypeEnum.DAY_FREEZE_P.getCode().equals(readType)) {
                response.setDayFreezeP(readValueStr);
            } else if (ClientConstant.ReadTypeEnum.MONTH_FREEZE_P.getCode().equals(readType)) {
                response.setMonthFreezeP(readValueStr);
            }
        }
    }
}
