package cn.com.cdboost.collect.listener;

import cn.com.cdboost.collect.constant.ChargeAppConstant;
import cn.com.cdboost.collect.constant.ChargeConstant;
import cn.com.cdboost.collect.constant.RedisKeyConstant;
import cn.com.cdboost.collect.constant.WebSocketConstant;
import cn.com.cdboost.collect.dto.MonitorDeviceDto;
import cn.com.cdboost.collect.dto.chargerApp.CurveDto;
import cn.com.cdboost.collect.dto.param.ChargerDeviceQueryVo;
import cn.com.cdboost.collect.dto.response.*;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.handler.SpringWebSocketHandler;
import cn.com.cdboost.collect.lock.Lock;
import cn.com.cdboost.collect.model.ChargingDevice;
import cn.com.cdboost.collect.model.ChargingPayCheme;
import cn.com.cdboost.collect.model.ChargingUseDetailed;
import cn.com.cdboost.collect.service.AppChargerService;
import cn.com.cdboost.collect.service.ChargingDeviceService;
import cn.com.cdboost.collect.service.ChargingPayChemeService;
import cn.com.cdboost.collect.service.ChargingUseDetailedService;
import cn.com.cdboost.collect.util.MathUtil;
import cn.com.cdboost.collect.util.RedisUtil;
import cn.com.cdboost.collect.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.example.clienttest.client.AFN20DataBackLisener;
import com.example.clienttest.client.AFN20Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component("aFN20ResultDataBackLisener")
public class AFN20ResultDataBackLisener implements AFN20DataBackLisener {
    private static final Logger logger = LoggerFactory.getLogger(AFN20ResultDataBackLisener.class);
    @Autowired
    private ChargingDeviceService chargingDeviceService;
    @Autowired
    private ChargingUseDetailedService chargingUseDetailedService;
    @Autowired
    private ChargingPayChemeService chargingPayChemeService;
    @Autowired
    private AppChargerService appChargerService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void onDataBack(AFN20Object afn20Object) {
//        logger.info("实时充电回调接口接收数据：AFN20Object=" + JSON.toJSONString(afn20Object));
        // 查询使用记录,可能为空，所以后面逻辑需要针对使用记录判断是否非空
        ChargingUseDetailed useDetailed = chargingUseDetailedService.queryChargingRecordByChargingGuid(afn20Object.getChargingGuid());

        ChargingDevice chargingDevice = chargingDeviceService.queryByCommon(afn20Object.getMoteEUI(), afn20Object.getPort());

        // 功率超限逻辑
        this.powerUpLimit(afn20Object,useDetailed,chargingDevice);

        // APP端扫码充电中推送
        this.sendAppUser(afn20Object,useDetailed,chargingDevice);

        // APP端设备信号强度推送
        this.sendToAppSignal(afn20Object,chargingDevice);

        // APP端IC卡充电中推送
        this.sendAppIcCrad(afn20Object,useDetailed,chargingDevice);

        // WEB推送
        this.sendWebUser(afn20Object,chargingDevice,useDetailed);

    }

    /**
     * 检查功率是否超限，超限发送短信，微信，
     * @param afn20Object
     * @param useDetailed
     * @param chargingDevice
     */
    private void powerUpLimit(AFN20Object afn20Object,ChargingUseDetailed useDetailed,ChargingDevice chargingDevice) {
        if (useDetailed == null) {
            return;
        }
        Lock lock = new Lock();
        try {
            String chargingGuid = afn20Object.getChargingGuid();
            String key = RedisKeyConstant.POWER_UP_PREFIX + chargingGuid;
            lock.lock(key);

            boolean upChargingLevel = afn20Object.isUpChargingLevel();

            // 查询当前选择的充电方案
            ChargingPayCheme oldScheme = chargingPayChemeService.queryBySchemeGuid(useDetailed.getSchemeGuid());
            Integer schemeType = oldScheme.getSchemeType();
            if (schemeType == 1) {
                // 方案二提档
                if (upChargingLevel) {
                    logger.info("方案二提档开始，chargingGuid=" + chargingGuid);
                    if (ChargeAppConstant.OpenMeansConstant.ICCARD.getType().equals(useDetailed.getOpenMeans())) {
                        logger.info("IC卡功率超限提档，后端不处理提档，直接pass");
                        return;
                    }

                    if (useDetailed.getState() == 1) {
                        // 充电结束，直接返回
                        logger.info("充电已完成，直接返回chargingGuid=" + useDetailed.getChargingGuid());
                        return;
                    }
                    String tempKey = RedisKeyConstant.POWER_UP_SUCCESS_PREFIX + chargingGuid;
                    String s = redisUtil.get(tempKey, "-1");
                    if ("1".equals(s)) {
                        // 说明已经被处理过，直接返回
                        return;
                    }

                    // 查询提档方案
                    BigDecimal currentPower = new BigDecimal(afn20Object.getOverChargingPower());
                    ChargingPayCheme payCheme = chargingPayChemeService.querySuitableScheme2ByPower(oldScheme.getProjectGuid(),oldScheme.getSchemeType(), oldScheme.getPayCategory(), oldScheme.getMoney(), currentPower.intValue());

                    // 更新充电时间
                    ChargingUseDetailed param = new ChargingUseDetailed();
                    param.setId(useDetailed.getId());
                    param.setSchemeGuid(payCheme.getSchemeGuid());
                    param.setChargingTime(payCheme.getChargingTime());
                    chargingUseDetailedService.updateByPrimaryKeySelective(param);

                    // 设置提档已经处理,10秒后过期
                    redisUtil.set(tempKey,"1",10);
                }
            } else {
                // 方案一提档
                // 是否发送功率超限消息
                String alarm = afn20Object.getAlarm();
                if (!StringUtils.isEmpty(alarm)) {
                    int temp = Integer.parseInt(alarm);
                    if((temp & 0x4000) == 0x4000) {
                        logger.info("方案一功率超限，开始处理发送通知逻辑chargingGuid=" + chargingGuid);
                        // 表示超限了
                        if (ChargeAppConstant.OpenMeansConstant.ICCARD.getType().equals(useDetailed.getOpenMeans())) {
                            logger.info("IC卡功率超限，后端不发送消息，直接pass");
                            return;
                        }
                        String tempKey = RedisKeyConstant.POWER_UP_MSG_PREFIX + chargingGuid;
                        String s = redisUtil.get(tempKey, "-1");
                        if ("1".equals(s)) {
                            return;
                        }

                        String overChargingPower = afn20Object.getOverChargingPower();
                        logger.info("功率超限了chargingGuid" + chargingGuid + ",overChargingPower=" + overChargingPower);

                        // 查询提档方案
                        BigDecimal currentPower = new BigDecimal(overChargingPower);
                        ChargingPayCheme payCheme = chargingPayChemeService.querySuitableByPower(oldScheme.getProjectGuid(),oldScheme.getSchemeType(), oldScheme.getPayCategory(), oldScheme.getChargingTime(), currentPower.intValue());

                        // 发短信
                        appChargerService.sendPowerUpMessage(useDetailed,chargingDevice,oldScheme,payCheme,overChargingPower);

                        // 发微信
                        appChargerService.sendWxMessage4PowerUp(useDetailed,chargingDevice,oldScheme,payCheme,overChargingPower);

                        // 设置功率超限，消息发送已处理，10秒后过期
                        redisUtil.set(tempKey,"1",10);
                    }
                }

                // 功率超限提档
                if (upChargingLevel) {
                    // 功率超限，需要提档
                    logger.info("方案一功率超限，提档开始chargingGuid=" + chargingGuid);

                    if (ChargeAppConstant.OpenMeansConstant.ICCARD.getType().equals(useDetailed.getOpenMeans())) {
                        logger.info("IC卡功率超限提档，后端不处理提档，直接pass");
                        return;
                    }

                    if (useDetailed.getState() == 1) {
                        // 充电结束，直接返回
                        logger.info("充电已完成，直接返回chargingGuid=" + chargingGuid);
                        return;
                    }
                    String tempKey = RedisKeyConstant.POWER_UP_SUCCESS_PREFIX + chargingGuid;
                    String s = redisUtil.get(tempKey, "-1");
                    if ("1".equals(s)) {
                        return;
                    }

                    // 提档，查询下一档
                    BigDecimal temp = new BigDecimal(afn20Object.getOverChargingPower());
                    ChargingPayCheme payCheme = chargingPayChemeService.querySuitableByPower(oldScheme.getProjectGuid(), oldScheme.getSchemeType(),oldScheme.getPayCategory(), oldScheme.getChargingTime(), temp.intValue());

                    // 退费并重新扣款
                    appChargerService.refundAndDeduct4PowerUp(useDetailed,oldScheme,payCheme);

                    // 设置功率超限，提档已处理，10秒后过期
                    redisUtil.set(tempKey,"1",10);
                }
            }
        } catch (Exception e) {
            logger.error("功率超限业务处理异常：",e);
        } finally {
            lock.unLock();
        }
    }


    /**
     * APP端扫码充电中页面，实时数据推送
     * @param afn20Object
     * @param useDetailed
     * @param chargingDevice
     */
    private void sendAppUser(AFN20Object afn20Object,ChargingUseDetailed useDetailed,ChargingDevice chargingDevice) {
//        logger.info("-----进入APP推送逻辑--------");
        if (useDetailed == null) {
            return;
        }

        ConcurrentMap<String,WebSocketSession> appUsers = SpringWebSocketHandler.getAppUsers();
        if (CollectionUtils.isEmpty(appUsers)) {
//            logger.info("WebSocket App在线用户数为空");
            return;
        }

        String alarm = afn20Object.getAlarm();
        // TODO 暂时注释，后面恢复
//        if (!"0".equals(alarm)) {
//            // 告警上报的数据，过滤掉
//            logger.info("alarm非0，直接返回");
//            return;
//        }

        String powerState = afn20Object.getPowerState();
        if ("off".equalsIgnoreCase(powerState)) {
//            logger.info("powerState=0ff，直接返回");
            return;
        }

        try {
            for (Map.Entry<String, WebSocketSession> user : appUsers.entrySet()) {
                if(!user.getValue().isOpen()){
                    appUsers.remove(user.getKey(),user.getValue());
                    continue;
                }

                //判断用户的openId是否正确
                if(useDetailed.getOpenNo().equals(user.getKey())){
                    //实例化返回对象
                    AppChargerResponse appChargerResponse = new AppChargerResponse();
                    appChargerResponse.setAlarm(afn20Object.getAlarm());        //设置警告信息
                    appChargerResponse.setDeviceNo(chargingDevice.getDeviceNo());   //设置设备编号
                    appChargerResponse.setState(afn20Object.getStatus());       //设置设备状态
                    appChargerResponse.setRunState(ChargeConstant.ChargeState.CHARGING.getState());
                    appChargerResponse.setRunStateDesc(ChargeConstant.ChargeState.CHARGING.getDesc());
                    appChargerResponse.setState(chargingDevice.getRunState());
                    appChargerResponse.setChargerElectric(afn20Object.getPercent());
                    String descByState = ChargeConstant.DeviceRunState.getDescByState(chargingDevice.getRunState());
                    appChargerResponse.setStateDesc(descByState);

                    CurveDto currentDto = new CurveDto();  //设置电流临时对象
                    CurveDto voltageDto = new CurveDto();  //设置电压临时对象
                    CurveDto powerDto = new CurveDto();    //设置功率临时对象
                    //设置当前电流
                    BigDecimal currentValue = MathUtil.setPrecision(new BigDecimal(afn20Object.getCurrent()));
                    String currentTime = afn20Object.getTime().substring(11,16) ;
                    currentDto.setTime(currentTime);
                    currentDto.setValue(currentValue);
                    //设置当前电压
                    BigDecimal voltageValue = MathUtil.setPrecision(new BigDecimal(afn20Object.getVoltage()));
                    String voltageTime = afn20Object.getTime().substring(11,16) ;
                    voltageDto.setValue(voltageValue);
                    voltageDto.setTime(voltageTime);
                    //设置当前功率
                    BigDecimal powerValue = MathUtil.setPrecision(new BigDecimal(afn20Object.getCurPower()));
                    String powerTime = afn20Object.getTime().substring(11,16) ;
                    powerDto.setValue(powerValue);
                    powerDto.setTime(powerTime);
                    //设置电流
                    appChargerResponse.setCurrentDto(currentDto);
                    //设置电压
                    appChargerResponse.setVoltageDto(voltageDto);
                    //设置功率
                    appChargerResponse.setPowerDto(powerDto);

                    //发送
                    WebSocketResponse<AppChargerResponse> socketResponse = new WebSocketResponse<>();
                    socketResponse.setData(appChargerResponse);
                    socketResponse.setDataFlag(WebSocketConstant.DataFlagEnum.CHARGE_ON_LINE_DATA.getFlag());
//                    logger.info("发送者："+appUsers.get(user.getKey())+"发送信息："+JSON.toJSONString(socketResponse));
                    appUsers.get(user.getKey()).sendMessage(new TextMessage(JSON.toJSONString(socketResponse)));
//                    logger.info("实时数据 websocket send success: data=" + JSON.toJSONString(socketResponse));

                    // 跳出for循环
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("app推送扫码充电实时数据异常",e);
        }
    }


    /**
     * APP端IC卡充电中页面，实时数据推送
     * @param afn20Object
     * @param useDetailed
     * @param chargingDevice
     */
    private void sendAppIcCrad(AFN20Object afn20Object,ChargingUseDetailed useDetailed,ChargingDevice chargingDevice) {
        if (useDetailed == null) {
            return;
        }

        Integer openMeans = useDetailed.getOpenMeans();
        if (!ChargeAppConstant.OpenMeansConstant.ICCARD.getType().equals(openMeans)) {
            // 非IC卡使用记录，直接返回
            return;
        }

        try {
            ConcurrentMap<String,WebSocketSession> icCardMap = SpringWebSocketHandler.getIcCardMap();
            if (icCardMap.isEmpty()) {
                return;
            }

            for (Map.Entry<String, WebSocketSession> entry : icCardMap.entrySet()) {
                if(!entry.getValue().isOpen()){
                    icCardMap.remove(entry.getKey(),entry.getValue());
                    continue;
                }

                //判断cardId是否相等
                if(useDetailed.getChargingGuid().equals(entry.getKey())){
                    // 设置返回
                    AppCharge4IcResponse response = new AppCharge4IcResponse();
                    response.setDeviceNo(chargingDevice.getDeviceNo());
                    response.setState(chargingDevice.getRunState());
                    String descByState = ChargeConstant.DeviceRunState.getDescByState(chargingDevice.getRunState());
                    response.setStateDesc(descByState);
                    response.setChargerElectric(afn20Object.getPercent());
                    //设置当前功率
                    BigDecimal currentPower = MathUtil.setPrecision(new BigDecimal(afn20Object.getCurPower()));
                    response.setCurrentPower(currentPower);

                    //发送
                    WebSocketResponse<AppCharge4IcResponse> socketResponse = new WebSocketResponse<>();
                    socketResponse.setData(response);
                    socketResponse.setDataFlag(WebSocketConstant.DataFlagEnum.CHARGE_ONLINE_IC_CARD_DATA.getFlag());
                    icCardMap.get(entry.getKey()).sendMessage(new TextMessage(JSON.toJSONString(socketResponse)));
                    logger.info("IC卡充电中实时数据上报成功socketResponse" + JSON.toJSONString(socketResponse));
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("app推送IC卡充电实时数据异常");
        }
    }

    /**
     * app信号强度推送
     * @param afn20Object
     * @param chargingDevice
     */
    private void sendToAppSignal(AFN20Object afn20Object,ChargingDevice chargingDevice){
//        logger.info("-----进入APP信号强度推送逻辑--------");
        ConcurrentMap<String, WebSocketSession> deviceApp = SpringWebSocketHandler.getDeviceApp();
        if (CollectionUtils.isEmpty(deviceApp)) {
//            logger.info("WebSocket deviceApp在线用户数为空");
            return;
        }

        try {
            if (chargingDevice == null) {
                return;
            }

            for (Map.Entry<String, WebSocketSession> entry : deviceApp.entrySet()) {
                String deviceNo = entry.getKey();
                WebSocketSession socketSession = entry.getValue();
                if (!socketSession.isOpen()) {
                    deviceApp.remove(deviceNo,socketSession);
                    continue;
                }

                String temp = chargingDevice.getDeviceNo() + chargingDevice.getPort();
                if (temp.equalsIgnoreCase(deviceNo)) {
                    AppDeviceSignalResponse signalResponse = new AppDeviceSignalResponse();
                    signalResponse.setSignal(Integer.parseInt(afn20Object.getSignalStrength()));
                    String desc = ChargeAppConstant.SingalState.getStateDescByState(afn20Object.getSignalStrength());
                    signalResponse.setSignalDesc(desc);

                    //发送
                    WebSocketResponse<AppDeviceSignalResponse> socketResponse = new WebSocketResponse<>();
                    socketResponse.setData(signalResponse);
                    socketResponse.setDataFlag(WebSocketConstant.DataFlagEnum.CHARGE_ON_DEVICE_SINGAL.getFlag());
//                    logger.info("发送者："+socketSession+"发送信息："+JSON.toJSONString(socketResponse));
                    socketSession.sendMessage(new TextMessage(JSON.toJSONString(socketResponse)));
//                    logger.info("信号强度 websocket send success: data=" + JSON.toJSONString(socketResponse));
                }
            }
        } catch (Exception e) {
            logger.error("app推送设备信号强度异常",e);
        }
    }

    /**
     * WEB端推送
     * @param afn20Object
     */
    private void sendWebUser(AFN20Object afn20Object,ChargingDevice device, ChargingUseDetailed useDetailed){
        try {
            CopyOnWriteArraySet<WebSocketSession> users = SpringWebSocketHandler.getUsers();
            if (CollectionUtils.isEmpty(users)) {
//                logger.info("webSocket 在线用户数为空");
                return;
            }
            //端口号
            String port = afn20Object.getPort();
            for (WebSocketSession session :users){
                if (!session.isOpen()) {
                    users.remove(session);
                    continue;
                }

                HttpSession httpSession = (HttpSession) session.getAttributes().get(GlobalConstant.HTTP_SESSION_OBJECT);
                if (httpSession != null){
//                    logger.info("WebSocket中记录的sessionId=" + httpSession.getId());
                    //主动推送
                    WebSocketResponse<MonitorDeviceRes> response = new WebSocketResponse<>();
                    MonitorDeviceRes monitorDeviceRes = new MonitorDeviceRes();
                    /*//设备信号强度
                    monitorDeviceRes.setSignalStrength(Integer.parseInt(afn20Object.getSignalStrength()));*/
                    //设备cpu温度
                    monitorDeviceRes.setDevTemperature(afn20Object.getDevTemperature());
                    //功率
                    if (!StringUtil.isEmpty(afn20Object.getCurPower())) {
                        monitorDeviceRes.setPower(new BigDecimal(afn20Object.getCurPower()).setScale(2, BigDecimal.ROUND_HALF_UP));
                    }else {
                        monitorDeviceRes.setPower(null);
                    }
                    //电压
                    if (!StringUtil.isEmpty(afn20Object.getVoltage())) {
                        monitorDeviceRes.setVoltage(new BigDecimal(afn20Object.getVoltage()).setScale(2, BigDecimal.ROUND_HALF_UP));
                    }else {
                        monitorDeviceRes.setPower(null);
                    }
                    //电流
                    if (!StringUtil.isEmpty(afn20Object.getCurrent())) {
                        monitorDeviceRes.setCurrent(new BigDecimal(afn20Object.getCurrent()).setScale(2, BigDecimal.ROUND_HALF_UP));
                    }else {
                        monitorDeviceRes.setCurrent(null);
                    }
                    //操作时间
                    monitorDeviceRes.setTime(afn20Object.getTime().substring(11,16));
                    //百分比
                    monitorDeviceRes.setPercent(afn20Object.getPercent());
                    //端口号
                    if (!StringUtil.isEmpty(port)){
                        monitorDeviceRes.setPort(port);
                    }
                    //设备编号
                    monitorDeviceRes.setDeviceNo(device.getDeviceNo());
                    //设备信号状态
                    monitorDeviceRes.setOnline(device.getOnline());
                    monitorDeviceRes.setRunState(device.getRunState());
                    if (useDetailed != null){
                        monitorDeviceRes.setCarCategory(1);
                        monitorDeviceRes.setPayCategory(useDetailed.getPayCategory());
                        monitorDeviceRes.setChargingWay(useDetailed.getChargingWay());
                        monitorDeviceRes.setChargingTime(useDetailed.getChargingTime());
                        //剩余时长
                        if (!StringUtil.isEmpty(afn20Object.getTimeRemain())){
                            monitorDeviceRes.setRemainTime(Integer.parseInt(afn20Object.getTimeRemain()));
                            monitorDeviceRes.setUseTime(Integer.parseInt(afn20Object.getCumulativeTime()));
                        }else {
                            monitorDeviceRes.setRemainTime(useDetailed.getChargingTime() * 60);
                        }
                        //实际充电时长
                        if (!StringUtil.isEmpty(afn20Object.getCumulativeTime())){
                            monitorDeviceRes.setUseTime(Integer.parseInt(afn20Object.getCumulativeTime()));
                        }else {
                            monitorDeviceRes.setUseTime(0);
                        }
                        //累计使用电量
                        if (!StringUtil.isEmpty(afn20Object.getCumulativePower())){
                            BigDecimal userPower = BigDecimal.valueOf(Double.parseDouble(afn20Object.getCumulativePower()));
                            monitorDeviceRes.setUsePower(userPower);
                        }
                    }
                    //运行状态
                    /*if (device != null && device.getRunState()==1){
                        ChargerDeviceQueryVo queryVo = new ChargerDeviceQueryVo();
                        queryVo.setChargingPlieGuid(device.getChargingPlieGuid());
                        queryVo.setRunState("1");
                        queryVo.setPageNumber(1);
                        queryVo.setPageSize(20);
                        queryVo.setOrgNoList(new ArrayList<Long>() {{
                            add(1000L);
                        }});
                        //数据库查询充值方式和车辆类别
                        List<MonitorDeviceDto> monitorDeviceDtos = chargingDeviceService.monitorDeviceList(queryVo);
                        monitorDeviceRes.setPayCategory(monitorDeviceDtos.get(0).getPayCategory());
                        monitorDeviceRes.setCarCategory(1);
                        //充电方式（1-按时充电 2-按电量充电 3-充满断电）
                        Integer chargingWay = monitorDeviceDtos.get(0).getChargingWay();
                        monitorDeviceRes.setChargingWay(chargingWay);

                        if (chargingWay != null){
                            if (chargingWay == 1 || chargingWay == 3) {
                                //应充时长
                                Integer chargingTime = monitorDeviceDtos.get(0).getChargingTime();
                                monitorDeviceRes.setChargingTime(chargingTime);
                                //剩余时长
                                if (!StringUtil.isEmpty(afn20Object.getTimeRemain())){
                                    monitorDeviceRes.setRemainTime(Integer.parseInt(afn20Object.getTimeRemain()));
                                    monitorDeviceRes.setUseTime(Integer.parseInt(afn20Object.getCumulativeTime()));
                                }else {
                                    monitorDeviceRes.setRemainTime(chargingTime * 60);
                                }
                                //实际充电时长
                                if (!StringUtil.isEmpty(afn20Object.getCumulativeTime())){
                                    monitorDeviceRes.setUseTime(Integer.parseInt(afn20Object.getCumulativeTime()));
                                }else {
                                    monitorDeviceRes.setUseTime(0);
                                }
                                //累计使用电量
                                if (!StringUtil.isEmpty(afn20Object.getCumulativePower())){
                                    BigDecimal userPower = BigDecimal.valueOf(Double.parseDouble(afn20Object.getCumulativePower()));
                                    monitorDeviceRes.setUsePower(userPower);
                                    //应充电量
                                    *//*Integer chargingPower = monitorDeviceDtos.get(0).getChargingPower();
                                    //剩余电量
                                    monitorDeviceRes.setRemainElectric(BigDecimal.valueOf(chargingPower).subtract(userPower));*//*
                                }
                            }else if (chargingWay == 2){
                                //累计使用电量
                                if (!StringUtil.isEmpty(afn20Object.getCumulativePower())){
                                    BigDecimal userPower = BigDecimal.valueOf(Double.parseDouble(afn20Object.getCumulativePower()));
                                    monitorDeviceRes.setUsePower(userPower);
                                    //应充电量
                                    Integer chargingPower = monitorDeviceDtos.get(0).getChargingPower();
                                    //剩余电量
                                    monitorDeviceRes.setRemainElectric(BigDecimal.valueOf(chargingPower).subtract(userPower));
                                }
                            }*//*else if (chargingWay == 3){
                                monitorDeviceRes.setRemainElectric(null);
                                monitorDeviceRes.setRemainTime(null);
                                //monitorDeviceRes.setChargingPower(monitorDeviceDtos.get(0).getChargingPower());
                                monitorDeviceRes.setChargingTime(monitorDeviceDtos.get(0).getChargingTime());
                            }*//*
                        }
                    }*/

                    response.setData(monitorDeviceRes);
                    response.setDataFlag(WebSocketConstant.DataFlagEnum.MONITOR_DEVICE_LIST.getFlag());
                    session.sendMessage(new TextMessage(JSON.toJSONString(response)));
//                    logger.info("电瓶车充电实时数据websocket send success: data=" + JSON.toJSONString(response));
                }
            }
        }catch (Exception e) {
            logger.error("充电桩WebSocket推送数据异常",e);
        }
    }
}
