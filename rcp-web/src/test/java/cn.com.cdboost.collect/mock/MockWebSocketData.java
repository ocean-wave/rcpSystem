package cn.com.cdboost.collect.mock;

import cn.com.cdboost.collect.constant.WebSocketConstant;
import cn.com.cdboost.collect.dto.response.*;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

/**
 * Created by Administrator on 2018/3/6 0006.
 */
public class MockWebSocketData {

    @Test
    public void linkCheck() {
        WebSocketResponse<Boolean> response = new WebSocketResponse<>();
        response.setDataFlag(WebSocketConstant.DataFlagEnum.LINK_CHECK.getFlag());
        response.setData(Boolean.FALSE);

        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void alarm() {
        WebSocketResponse<Integer> response = new WebSocketResponse<>();
        response.setDataFlag(WebSocketConstant.DataFlagEnum.ALARM.getFlag());
        response.setData(10);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void realCollectData() {
        AFN04Response response = new AFN04Response();
        response.setTaskStatus(101);
        response.setDeviceCollectStatus(1);
        response.setCno("070000000000000000000000456789");
        response.setBalance("20.00");
        response.setPr0("100.23");
        response.setCollectTime("2018-03-06 12:02:15");

        WebSocketResponse<AFN04Response> socketResponse = new WebSocketResponse<>();
        socketResponse.setData(response);
        socketResponse.setDataFlag(WebSocketConstant.DataFlagEnum.COLLECT_DATA.getFlag());

        System.out.println(JSON.toJSONString(socketResponse));
    }

    @Test
    public void onOff() {
        AFN08Response response = new AFN08Response();
        response.setTaskStatus(101);
        response.setCno("070000000000000000000000456789");
        response.setState("1");
        response.setDealNum(2);
        response.setFailNum(0);
        response.setSuccessfulNum(2);
        response.setUndealNum(3);

        // 发送消息给前端
        WebSocketResponse<AFN08Response> socketResponse = new WebSocketResponse<>();
        socketResponse.setDataFlag(WebSocketConstant.DataFlagEnum.ON_OFF.getFlag());
        socketResponse.setData(response);

        System.out.println(JSON.toJSONString(socketResponse));
    }

    @Test
    public void jzqReadCustomerInfo() {
        AFN14Response response = new AFN14Response();
        response.setJzqNo("123456789");
        response.setCommSetupSn(2);
        response.setCommPointCode(2);
        response.setCommBaudrate(3);
        response.setCommPort("载波");
        response.setCommRule("97表");
        response.setDeviceNo("54521321");
        response.setFeeRateNum(4);
        response.setCjqNo("13121231");

        WebSocketResponse<AFN14Response> socketResponse = new WebSocketResponse<>();
        socketResponse.setDataFlag(WebSocketConstant.DataFlagEnum.JZQ_READ_CUSTOMER_INFO.getFlag());
        socketResponse.setData(response);

        System.out.println(JSON.toJSONString(socketResponse));
    }

    @Test
    public void jzqHeartBeatSend() {
        WebSocketResponse<String> response = new WebSocketResponse<>();
        response.setData("下发成功");
        response.setDataFlag(WebSocketConstant.DataFlagEnum.JZQ_HEART_BEAT_SEND.getFlag());

        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void jzqHeartBeatRead() {
        WebSocketResponse<AFN07Response> socketResponse = new WebSocketResponse<>();
        AFN07Response response = new AFN07Response();
        response.setStatus(1);
        response.setHbCycle("5");
        response.setMessage("执行成功");
        socketResponse.setData(response);
        socketResponse.setDataFlag(WebSocketConstant.DataFlagEnum.JZQ_HEART_BEAT_READ.getFlag());

        System.out.println(JSON.toJSONString(socketResponse));
    }

    @Test
    public void jzqConnectionParamSend() {
        WebSocketResponse<String> response = new WebSocketResponse<>();
        response.setData("执行成功");
        response.setDataFlag(WebSocketConstant.DataFlagEnum.JZQ_CONNECT_PARAM_SEND.getFlag());

        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void broadcast() {
        WebSocketResponse<AFN15Response> response = new WebSocketResponse<>();
        response.setDataFlag(WebSocketConstant.DataFlagEnum.COLLECT_DATA_BROADCAST.getFlag());

        AFN15Response afn15Response = new AFN15Response();
        afn15Response.setStatus(1);
        afn15Response.setPayMoney("20.12");
        afn15Response.setPayCount("11");
        afn15Response.setPr0("100.12");
        afn15Response.setPr1("11");
        afn15Response.setPr2("12");
        afn15Response.setPr3("13");
        afn15Response.setPr4("14");
        afn15Response.setCurrentA("21");
        afn15Response.setCurrentB("21");
        afn15Response.setCurrentC("85");
        afn15Response.setVoltageA("35");
        afn15Response.setVoltageB("45");
        afn15Response.setVoltageC("55");
        afn15Response.setSmokeBatteryLevel("51");
        afn15Response.setSmokeAlarm("55");
        afn15Response.setTemperature("20");
        afn15Response.setHumidity("52");
        afn15Response.setIlluminance("21");
        response.setData(afn15Response);

        System.out.println(JSON.toJSONString(response));
    }
}
