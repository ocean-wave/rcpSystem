package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.constant.BusinessType;
import cn.com.cdboost.collect.constant.ChargeAppConstant;
import cn.com.cdboost.collect.constant.ChargeConstant;
import cn.com.cdboost.collect.dao.*;
import cn.com.cdboost.collect.dto.RegisterDto;
import cn.com.cdboost.collect.dto.chargerApp.*;
import cn.com.cdboost.collect.dto.chargerApp.vo.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.IcCardInfo;
import cn.com.cdboost.collect.dto.response.IcCardPayInfo;
import cn.com.cdboost.collect.dto.response.IcCardUseInfo;
import cn.com.cdboost.collect.dto.response.WithdrawCashInfo;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.*;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.AccountUtil;
import cn.com.cdboost.collect.util.DateUtil;
import cn.com.cdboost.collect.util.MathUtil;
import cn.com.cdboost.collect.util.UuidUtil;
import cn.com.cdboost.collect.vo.PageData;
import com.example.clienttest.client.AFN19Object;
import com.example.clienttest.clientfuture.ClientManager;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AppChargerServiceImpl implements AppChargerService {
    private static final Logger logger = LoggerFactory.getLogger(AppChargerServiceImpl.class);

    @Autowired
    ChargingUseDetailedMapper chargingUseDetailedMapper;
    @Autowired
    ChargingPayMapper chargingPayMapper;
    @Autowired
    ChargingCstMapper chargingCstMapper;
    @Autowired
    ChargingDeviceMapper chargingDeviceMapper;
    @Autowired
    WxChargerPayService wxChargerPayService;
    @Autowired
    ChargingDevlogMapper chargingDevlogMapper;
    @Autowired
    private ChargingDeviceService chargingDeviceService;
    @Autowired
    private ChargingUseDetailedService chargingUseDetailedService;
    @Autowired
    private ChargingDevlogService chargingDevlogService;
    @Autowired
    private ChargingPayChemeService chargingPayChemeService;
    @Autowired
    private ChargingCstService chargingCstService;
    @Autowired
    private ChargingPayService chargingPayService;
    @Autowired
    private ChargingProjectService chargingProjectService;
    @Autowired
    private AliyunSmsService aliyunSmsService;
    @Autowired
    private AlipayService alipayService;
    @Autowired
    private ChargingCardService chargingCardService;

    @Override
    public WxBaseInfoDto getBaseInfoNew(WxBaseInfoVo wxBaseInfoVo) {
        Integer appType = wxBaseInfoVo.getAppType();
        String openId = wxBaseInfoVo.getOpenId();
        ChargingCst chargingCst;
        if (ChargeAppConstant.AppType.WECHAT.getType().equals(appType)) {
            // 微信
            chargingCst = chargingCstService.queryByOpenId(openId);
        } else {
            // 支付宝
            chargingCst = chargingCstService.queryByAlipayUserId(openId);
        }

        WxBaseInfoDto dto = new WxBaseInfoDto();
        if (chargingCst == null) {
            dto.setIsJump(4);
            dto.setIsJumpDesc("用户未注册");
            return dto;
        }

        String customerGuid = chargingCst.getCustomerGuid();
        if (StringUtils.isEmpty(wxBaseInfoVo.getDeviceNo())) {
            // 公众号进入
            dto = this.publicNumberOpen(customerGuid);
        } else {
            String deviceNo = (wxBaseInfoVo.getDeviceNo()).substring(0,7);
            String port = (wxBaseInfoVo.getDeviceNo()).substring(7,wxBaseInfoVo.getDeviceNo().length());
            // 扫描设备二维码进入
            dto = this.qrCodeOpen(customerGuid,deviceNo,port);
        }
        return dto;
    }

    /**
     * 公众号进入处理逻辑
     * @param customerGuid
     */
    private WxBaseInfoDto publicNumberOpen(String customerGuid) {
        // 判断该用户下是否存在充电中设备
        ChargingUseDetailed useDetailed = chargingUseDetailedService.queryChargingRecordByCustomerGuid(customerGuid);
        WxBaseInfoDto dto = new WxBaseInfoDto();
        if (useDetailed != null) {
            ChargingDevice params = new ChargingDevice();
            params.setChargingPlieGuid(useDetailed.getChargingPlieGuid());
            ChargingDevice chargingDevice = chargingDeviceService.selectOne(params);
            dto.setState(2);
            dto.setIsJump(3);
            dto.setStateDesc("设备充电中");
            dto.setDeviceNo(chargingDevice.getDeviceNo()+chargingDevice.getPort());
            dto.setIsJumpDesc("充电中");
        } else {
            // 不存在充电中设备,二维码扫码页面
            dto.setIsJump(1);
            dto.setIsJumpDesc("用户空闲中");
        }

        return dto;
    }

    /**
     * 二维码扫描处理逻辑
     * @param customerGuid
     * @param deviceNo
     */
    private WxBaseInfoDto qrCodeOpen(String customerGuid, String deviceNo,String port) {
        // 判断该用户下是否存在充电中设备
        ChargingUseDetailed useDetailed = chargingUseDetailedService.queryChargingRecordByCustomerGuid(customerGuid);
        WxBaseInfoDto dto = new WxBaseInfoDto();
        if (useDetailed != null) {
            // 前端跳转到充电中页面
            ChargingDevice params = new ChargingDevice();
            params.setChargingPlieGuid(useDetailed.getChargingPlieGuid());
            ChargingDevice chargingDevice = chargingDeviceService.selectOne(params);
            dto.setState(2);
            dto.setIsJump(3);
            dto.setDeviceNo(chargingDevice.getDeviceNo()+chargingDevice.getPort());
            dto.setStateDesc("设备充电中");
            dto.setIsJumpDesc("充电中");
        } else {
            // 查询对应充电桩设备
            ChargingDevice chargingDevice = chargingDeviceService.queryByDeviceNo(deviceNo,port);
            if(chargingDevice==null){
                dto.setIsJump(0);
                dto.setIsJumpDesc("设备不存在");
                return dto;
            }
            // 查询项目信息
            ChargingProject chargingProject = chargingProjectService.queryByProjectGuid(chargingDevice.getProjectGuid());
            if(chargingProject==null){
                dto.setIsJump(0);
                dto.setIsJumpDesc("项目不存在");
                return dto;
            }
            dto.setPhone(chargingProject.getContactTelphone());
            dto.setDeviceNo(deviceNo);
            dto.setDeviceName(chargingDevice.getDeviceName());
            dto.setRunState(chargingDevice.getRunState());
            //设置端口
            dto.setPort(Integer.toString(Integer.parseInt(chargingDevice.getPort(),16)));

            Integer runState = chargingDevice.getRunState();
            if (ChargeConstant.DeviceRunState.CHARGING.getState().equals(runState)) {
                // 别人正在充电中
                dto.setState(0);
                dto.setIsJump(0);
                dto.setStateDesc("设备被占用，正在充电中");
                dto.setIsJumpDesc("设备被占用，正在充电中");
            } else {
                // 未被别人占用
                Integer online = chargingDevice.getOnline();
                if (ChargeConstant.DeviceOnlineStatus.OFFLINE.getStatus().equals(online)) {
                    // 离线
                    // 前端跳转到离线页面
                    dto.setState(0);
                    dto.setIsJump(0);
                    dto.setStateDesc("设备已离线");
                    dto.setIsJumpDesc("设备已离线");
                } else {
                    // 在线
                    if (ChargeConstant.DeviceRunState.IDEL.getState().equals(runState)) {
                        // 跳转到设备充值购买页面
                        dto.setState(1);
                        dto.setStateDesc("设备空闲");
                        dto.setIsJump(2);
                        dto.setIsJumpDesc("设备空闲中");

                        // 设置用户信息
                        ChargingCst chargingCst = chargingCstService.queryByCustomerGuid(customerGuid);
                        this.setUserInfo(dto,chargingCst);

                        // 设置购买方案列表
                        this.setSchemeList(dto,chargingCst,chargingDevice.getProjectGuid());
                    } else if (ChargeConstant.DeviceRunState.UN_USE.getState().equals(runState)) {
                        // 跳转到设备停用页面
                        dto.setState(0);
                        dto.setIsJump(0);
                        dto.setStateDesc("设备已停用");
                        dto.setIsJumpDesc("设备已停用");
                    } else if (ChargeConstant.DeviceRunState.FAULT.getState().equals(runState)) {
                        // 跳转到设备故障页面
                        dto.setState(0);
                        dto.setIsJump(0);
                        dto.setStateDesc("设备故障");
                        dto.setIsJumpDesc("设备故障");
                    }
                }
            }
        }

        return dto;
    }

    /**
     * 往WxBaseInfoDto中设置用户相关信息
     * @param dto
     */
    private void setUserInfo(WxBaseInfoDto dto, ChargingCst chargingCst) {
        boolean monthUser = this.isMonthUser(chargingCst);
        if (monthUser) {
            // 包月用户
            dto.setCustomerType(ChargeAppConstant.UserType.USER_MONTH.getType());
            dto.setCustomerTypeDesc(ChargeAppConstant.UserType.USER_MONTH.getTypeDesc());
            String dateStr = DateUtil.getDateStr(chargingCst.getExpireTime());
            dto.setEndTime(dateStr);
            dto.setRemainCnt(chargingCst.getRemainCnt());
            dto.setRemainAmount(chargingCst.getRemainAmount());
        } else {
            // 非包月用户
            dto.setCustomerType(ChargeAppConstant.UserType.USER_COMMON.getType());
            dto.setCustomerTypeDesc(ChargeAppConstant.UserType.USER_COMMON.getTypeDesc());
            dto.setRemainAmount(chargingCst.getRemainAmount());
            dto.setRemainCnt(0);
        }
    }

    /**
     * 设置充电购买方案列表
     * @param dto
     * @param chargingCst
     * @param projectGuid
     */
    private void setSchemeList(WxBaseInfoDto dto, ChargingCst chargingCst, String projectGuid) {
        List<ChargingPayCheme> chemeList = chargingPayChemeService.querySchemeList4ChargePage(projectGuid);
        List<PriceDto> priceDtoList = new ArrayList<>();
        for (ChargingPayCheme chargingPayCheme : chemeList) {
            PriceDto priceDto = new PriceDto();
            priceDto.setChargeTime(chargingPayCheme.getChargingTime());
            priceDto.setMoney(MathUtil.setPrecision(chargingPayCheme.getMoney()));
            priceDto.setPayCategory(chargingPayCheme.getPayCategory());
            priceDto.setPriceId(chargingPayCheme.getId());
            priceDto.setPower(chargingPayCheme.getPower());
            priceDtoList.add(priceDto);

            Integer payCategory = chargingPayCheme.getPayCategory();
            StringBuilder sb = new StringBuilder();
            if (ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getType().equals(payCategory)) {
                sb.append("充").append(chargingPayCheme.getChargingTime()).append("小时");
                priceDto.setPayDesc(sb.toString());
            } else if (ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(payCategory)) {
                boolean monthUser = this.isMonthUser(chargingCst);
                if (monthUser) {
                    // 包月用户
                    sb.append("剩余").append(chargingCst.getRemainCnt()).append("次");
                    priceDto.setPayDesc(sb.toString());
                } else {
                    priceDto.setPayDesc("剩余0次");
                }

            } else if (ChargeConstant.SchemePayCategory.RECHARGE_FULL.getType().equals(payCategory)) {
                sb.append("一键充满");
                priceDto.setPayDesc(sb.toString());
            }
        }

        dto.setList_price(priceDtoList);
    }

    /**
     * 判断用户是否是月卡用户
     * @param chargingCst
     * @return
     */
    private boolean isMonthUser(ChargingCst chargingCst) {
        // 是否包月用户
        boolean flag = false;
        Date expireTime = chargingCst.getExpireTime();
        if (expireTime != null) {
            Integer remainCnt = chargingCst.getRemainCnt();
            Date current = new Date();
            if (current.before(expireTime) && remainCnt > 0) {
                // 剩余次数大于0，并且在有效期内
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public ChargeOnlineDto getChargeOnline(ChargeOnlineVo chargeOnlineVo) {
        Integer appType = chargeOnlineVo.getAppType();
        String openId = chargeOnlineVo.getOpenId();
        ChargingCst chargingCst;
        if (ChargeAppConstant.AppType.WECHAT.getType().equals(appType)) {
            chargingCst = chargingCstService.queryByOpenId(openId);
        } else {
            chargingCst = chargingCstService.queryByAlipayUserId(openId);
        }

        ChargeOnlineDto chargeOnlineDto = new ChargeOnlineDto();
        ChargingUseDetailed chargingUseDetailed = chargingUseDetailedService.queryChargingRecordByCustomerGuid(chargingCst.getCustomerGuid());
        if(chargingUseDetailed!=null){
            // 查询充值设备
            ChargingDevice chargingDevice = chargingDeviceService.queryByChargingPlieGuid(chargingUseDetailed.getChargingPlieGuid());
            //设置状态
            chargeOnlineDto.setState(chargingDevice.getRunState());
            String descByState = ChargeConstant.DeviceRunState.getDescByState(chargingDevice.getRunState());
            chargeOnlineDto.setStateDesc(descByState);

            //设置设备编号
            chargeOnlineDto.setDeviceNo(chargingDevice.getDeviceNo());
            //设置端口号
            chargeOnlineDto.setPort(Integer.toString(Integer.parseInt(chargingDevice.getPort(),16)));
            //设置已冲电量
            Integer devLogId = chargingUseDetailed.getDevLogId();
            if (devLogId != null) {
                // 查询充电桩上报日志表记录
                ChargingDevlog chargingDevlog = chargingDevlogService.selectByPrimaryKey(devLogId);
                // 设置已充电量
                chargeOnlineDto.setChargedElectric(chargingDevlog.getChargingPercent());
                chargeOnlineDto.setPower(chargingDevlog.getPower());
            } else {
                // 设置已充电量
                chargeOnlineDto.setChargedElectric(MathUtil.setPrecision(BigDecimal.ZERO));
                chargeOnlineDto.setPower(BigDecimal.ZERO);
            }
            // 剩余时间
            Date sTime = chargingUseDetailed.getStartTime();
            Date eTime = new Date();
            long ycTime = ((eTime.getTime()-sTime.getTime())/1000);
            long syTime = (chargingUseDetailed.getChargingTime()*60*60)-((eTime.getTime()-sTime.getTime())/1000);
            chargeOnlineDto.setRemainTime(syTime);
            chargeOnlineDto.setChargeTime(ycTime);
            if(chargingUseDetailed.getPayCategory()==ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getType()){
                chargeOnlineDto.setChemeDesc(ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getDesc());
            }else if(chargingUseDetailed.getPayCategory()==ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType()){
                chargeOnlineDto.setChemeDesc(ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getDesc());
            }else if(chargingUseDetailed.getPayCategory()==ChargeConstant.SchemePayCategory.RECHARGE_FULL.getType()){
                chargeOnlineDto.setChemeDesc(ChargeConstant.SchemePayCategory.RECHARGE_FULL.getDesc());
            }
        }

        return chargeOnlineDto;
    }

    @Override
    public ChargeDto charge(ChargeVo chargeVo) {
        String deviceNo = chargeVo.getDeviceNo().substring(0,7);
        String port = chargeVo.getDeviceNo().substring(7,chargeVo.getDeviceNo().length());
        //实例化结果对象
        ChargeDto chargeDto = new ChargeDto();
        //获取价格方案的金额
        Integer priceId = chargeVo.getPriceId();
        ChargingPayCheme chargingPayCheme = chargingPayChemeService.selectByPrimaryKey(priceId);

        String openId = chargeVo.getOpenId();
        ChargingCst chargingCst = chargingCstService.queryByOpenId(openId);

        ChargingDevice chargingDevice = chargingDeviceService.queryByDeviceNo(deviceNo,port);
        String commNo = chargingDevice.getCommNo();
        chargeDto.setIsCharge(1);

        // TODO 充值时间，单位小时，测试时除以60, 正式发布时，需要改回来
        Integer chargingTime = chargingPayCheme.getChargingTime();
//        BigDecimal divide = MathUtil.divide(BigDecimal.valueOf(chargingTime), BigDecimal.valueOf(60));
//        BigDecimal decimal = MathUtil.setPrecision(divide);

        Integer openMeans = ChargeAppConstant.OpenMeansConstant.WECHAT.getType();
        //针对不包月购买次数的用户
        if(ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getType().equals(chargingPayCheme.getPayCategory()) ||
                ChargeConstant.SchemePayCategory.RECHARGE_FULL.getType().equals(chargingPayCheme.getPayCategory())) {
            //获取用户余额，和传入价格方案的金额进行对比
            boolean greaterEqual = MathUtil.isGreaterEqual(chargingCst.getRemainAmount(),chargingPayCheme.getMoney());
            if (!greaterEqual) {
                //剩余金额小于价格方案的金额，则调取支付接口进行支付，支付成功后，前端再次则就调用充电接口，准备充电
                //获取差价
                BigDecimal payMoney = MathUtil.sub(chargingPayCheme.getMoney(), chargingCst.getRemainAmount());
                chargeDto.setIsCharge(0);
                chargeDto.setPay(MathUtil.setPrecision(payMoney));

                //调用支付接口
                Ajax ajax = wxChargerPayService.chargePay(ChargeAppConstant.PayScene.CHARGE_ELEC.getType(),null,payMoney,chargeVo.getIp(),chargingPayCheme,chargingCst.getCustomerGuid(),chargeVo.getOpenId());
                //状态为1标识下单成功
                if(ajax.status==1){
                    //设置微信下单参数
                    chargeDto.setAppId(ajax.getParams().get("appId"));
                    chargeDto.setNonceStr(ajax.getParams().get("nonceStr"));
                    chargeDto.setTimeStamp(ajax.getParams().get("timeStamp"));
                    chargeDto.setPackages(ajax.getParams().get("package"));
                    chargeDto.setPaySign(ajax.getParams().get("paySign"));
                    chargeDto.setSignType(ajax.getParams().get("signType"));
                }
            }else{
                // 钱够，就直接下发充电指令
                // 发送通电指令
                this.sendChargeInstruction(commNo,port,openId,String.valueOf(priceId),String.valueOf(chargingTime),openMeans);
            }
        } else if(ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(chargingPayCheme.getPayCategory())){
            // 包月用户的处理
            // 判断是否包月用户
            boolean monthUser = this.isMonthUser(chargingCst);
            if (monthUser) {
                // 发送通电指令
                this.sendChargeInstruction(commNo,port,openId,String.valueOf(priceId),String.valueOf(chargingTime),openMeans);
            } else {
                //无剩余次数
                chargeDto.setIsCharge(0);
            }
        }
        return chargeDto;
    }

    @Override
    public Map<String, String> chargeIcCardByWeChat(String openId, String cardId, BigDecimal amount,String ip) {
        // 新增IC卡充值记录
        ChargingCard card = chargingCardService.queryByCardId(cardId);

        //实例化用户充值记录对象
        ChargingPay chargingPay = new ChargingPay();
        chargingPay.setCustomerGuid("");
        //设置用户充值金额,单位元
        chargingPay.setPayMoney(amount);
        chargingPay.setAccountChargeMoney(amount);
        chargingPay.setAccountDeductMoney(BigDecimal.ZERO);
        // IC卡充值
        chargingPay.setPayCategory(5);
        chargingPay.setWebcharNo(openId);
        chargingPay.setCreateTime(new Date());
        String outTradeNo = UuidUtil.getUuid();
        chargingPay.setPayFlag(outTradeNo);
        chargingPay.setPayState(0);
        chargingPay.setSerialNum(DateUtil.getSerialNum());
        // 充值后剩余金额，此处是先将充值前剩余金额赋值上，避免用户点击支付，然后又取消了，导致支付失败的订单上该字段为null
        // 充值成功后，在回调处还会更新此字段
        chargingPay.setAfterRemainAmount(card.getRemainAmount());
        chargingPay.setType(ChargeAppConstant.PayScene.ICCARD_CHARGE.getType());
        chargingPayMapper.insertSelective(chargingPay);

        // 调用微信支付
        // 总金额以分为单位，不带小数点
        String payMoney = MathUtil.yuan2Fen(String.valueOf(amount));
        Map<String, String> payMap = wxChargerPayService.weChatPrePay(payMoney, outTradeNo, openId, ip);
        return payMap;
    }

    @Override
    public String chargeIcCardByAlipay(String userId, String cardId, BigDecimal amount) {
        // 新增IC卡充值记录
        ChargingCard card = chargingCardService.queryByCardId(cardId);

        //实例化用户充值记录对象
        ChargingPay chargingPay = new ChargingPay();
        chargingPay.setCustomerGuid("");
        //设置用户充值金额,单位元
        chargingPay.setPayMoney(amount);
        chargingPay.setAccountChargeMoney(amount);
        chargingPay.setAccountDeductMoney(BigDecimal.ZERO);
        // IC卡充值
        chargingPay.setPayCategory(5);
        chargingPay.setWebcharNo(userId);
        chargingPay.setCreateTime(new Date());
        String outTradeNo = UuidUtil.getUuid();
        chargingPay.setPayFlag(outTradeNo);
        chargingPay.setPayState(0);
        chargingPay.setSerialNum(DateUtil.getSerialNum());
        // 充值后剩余金额，此处是先将充值前剩余金额赋值上，避免用户点击支付，然后又取消了，导致支付失败的订单上该字段为null
        // 充值成功后，在回调处还会更新此字段
        chargingPay.setAfterRemainAmount(card.getRemainAmount());
        chargingPay.setType(ChargeAppConstant.PayScene.ICCARD_CHARGE.getType());
        chargingPayMapper.insertSelective(chargingPay);

        String tradeNo = alipayService.charge(chargingPay);
        return tradeNo;
    }

    @Override
    public AlipayChargeDto alipaycharge(String alipayUserId,String deviceNo,Integer priceId) {
        // 查询价格方案
        ChargingPayCheme chargingPayCheme = chargingPayChemeService.selectByPrimaryKey(priceId);
        // TODO 充值时间，单位小时，测试时除以60, 正式发布时，需要改回来
        Integer chargingTime = chargingPayCheme.getChargingTime();
//        BigDecimal divide = MathUtil.divide(BigDecimal.valueOf(chargingTime), BigDecimal.valueOf(60));
//        BigDecimal decimal = MathUtil.setPrecision(divide);

        // 查询设备信息
        String realDeviceNo = deviceNo.substring(0,7);
        String port = deviceNo.substring(7,deviceNo.length());
        ChargingDevice chargingDevice = chargingDeviceService.queryByDeviceNo(realDeviceNo,port);
        String commNo = chargingDevice.getCommNo();

        // 查询用户账户信息
        ChargingCst chargingCst = chargingCstService.queryByAlipayUserId(alipayUserId);

        AlipayChargeDto dto = new AlipayChargeDto();
        dto.setIsCharge(1);
        Integer payCategory = chargingPayCheme.getPayCategory();
        Integer openMeans = ChargeAppConstant.OpenMeansConstant.ALIPAY.getType();
        if(ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getType().equals(payCategory)
                || ChargeConstant.SchemePayCategory.RECHARGE_FULL.getType().equals(payCategory)) {
            //获取用户余额，和传入价格方案的金额进行对比
            BigDecimal sub = MathUtil.sub(chargingCst.getRemainAmount(), chargingPayCheme.getMoney());
            boolean flag = MathUtil.isLessThanZero(sub);
            if (flag) {
                // 余额不足
                dto.setIsCharge(0);
                BigDecimal chargeMoney = MathUtil.negate(sub);
                // 生成订单
                CreateOrderParam orderParam = new CreateOrderParam();
                orderParam.setCustomerGuid(chargingCst.getCustomerGuid());
                orderParam.setPayFlag(UuidUtil.getUuid());
                orderParam.setPayMoney(chargeMoney);
                orderParam.setAccountChargeMoney(chargeMoney);
                orderParam.setChargingTime(chargingTime);
                orderParam.setPayCategory(payCategory);
                orderParam.setChargingCnt(chargingPayCheme.getChargingCnt());
                orderParam.setOpenNo(alipayUserId);
                orderParam.setRemainAmount(chargingCst.getRemainAmount());
                orderParam.setType(ChargeAppConstant.PayScene.CHARGE_ELEC.getType());
                orderParam.setPayWay(ChargeAppConstant.PayWayConstant.ALIPAY.getType());
                orderParam.setPayState(0);
                ChargingPay order = alipayService.order(orderParam);
                // 支付宝支付
                String tradeNo = alipayService.charge(order);
                dto.setTradeNo(tradeNo);
            } else {
                // 钱够，就直接下发充电指令
                // 发送通电指令
                this.sendChargeInstruction(commNo,port,alipayUserId,String.valueOf(priceId),String.valueOf(chargingTime),openMeans);
            }
        } else if(ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(chargingPayCheme.getPayCategory())){
            // 包月用户的处理
            // 判断是否包月用户
            boolean monthUser = this.isMonthUser(chargingCst);
            if (monthUser) {
                // 发送通电指令
                this.sendChargeInstruction(commNo,port,alipayUserId,String.valueOf(priceId),String.valueOf(chargingTime),openMeans);
            } else {
                //无剩余次数
                dto.setIsCharge(0);
            }
        }
        return dto;
    }

    /**
     * 发送充电指令
     */
    private void sendChargeInstruction(String commNo,String port,String openId,String priceId,String chargeTime,Integer openMeans) {
        String queueGuid = UuidUtil.getUuid();
        AFN19Object afn19Object = new AFN19Object(queueGuid,
                "19",
                "999999999",
                "42475858fffffa",
                commNo,
                "20000",
                port,
                "on",
                "",
                openId,
                priceId,
                chargeTime,
                Integer.toString(ChargeAppConstant.ChargingWay.CHARGING_TIME.getWay()),
                openMeans);
        //下发数据
        String chargingGuid = UuidUtil.getUuid();
        afn19Object.setChargingGuid(chargingGuid);
        logger.info("下发充电指令chargingGuid=" + chargingGuid);
        int result = ClientManager.sendAFN19Msg(afn19Object);
        logger.info("下发充电指令返回result=" + result);
        if (result != 1) {
            throw new BusinessException("下发充电指令失败");
        }
    }

    @Override
    public List<ChargeHistoryDto> chargeHistory(HistoryVo historyVo) {
        List<ChargeHistoryDto> dataList = Lists.newArrayList();
        //查询使用记录数据
        dataList = chargingUseDetailedMapper.queryUseRecord(historyVo);
        if (CollectionUtils.isEmpty(dataList)) {
            return dataList;
        }

        for (ChargeHistoryDto dto : dataList) {
            Integer payCategory = dto.getPayCategory();
            if (ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getType().equals(payCategory) || ChargeConstant.SchemePayCategory.RECHARGE_FULL.getType().equals(payCategory)) {
                dto.setPayWayDesc(ChargeAppConstant.PayWay.PAY_USER_BALANCE.getPayWayDesc());
            } else if(ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(payCategory)) {
                dto.setPayWayDesc(ChargeAppConstant.PayWay.PAY_MONTH_CNT.getPayWayDesc());
            }

            dto.setPayWay(payCategory);
            dto.setChargeTime(DateUtil.getTimeDifference(dto.getEndTime(),dto.getStartTime()));
        }
        return dataList;
    }

    @Override
    public List<ChargeMoneyHistoryDto> chargeMoneyHistory(HistoryVo historyVo) {
        List<ChargeMoneyHistoryDto> dataList = Lists.newArrayList();
        //查询用户充值列表
        dataList = chargingPayMapper.queryCharge(historyVo);
        if (CollectionUtils.isEmpty(dataList)) {
            return dataList;
        }
        for (ChargeMoneyHistoryDto dto : dataList) {
            Integer payWay = dto.getPayWay();
            String descByType = ChargeAppConstant.PayWayConstant.getDescByType(payWay);
            BigDecimal realPayMoney = dto.getRealPayMoney();
            BigDecimal accountDeductMoney = dto.getAccountDeductMoney();
            StringBuffer sb = new StringBuffer();
            sb.append(descByType).append(realPayMoney).append("元");
            if (accountDeductMoney != null) {
                if (MathUtil.isGreateThanZero(accountDeductMoney)) {
                    sb.append(",余额支付").append(accountDeductMoney).append("元");
                }
            }

            dto.setPayWayDesc(sb.toString());
            if(dto.getPayCategory()==2){
                dto.setPayCategoryDesc("月卡包月");
            }else{
                dto.setPayCategoryDesc("账户充值");
            }
        }

        //返回数据
        return dataList;
    }

    @Override
    public void stopCharge(StopChargeVo stopChargeVo) {
        String deviceNo = stopChargeVo.getDeviceNo().substring(0,7);
        String port = stopChargeVo.getDeviceNo().substring(7,stopChargeVo.getDeviceNo().length());
       ChargingDevice chargingDevice = chargingDeviceService.queryByDeviceNo(deviceNo,port);
        AFN19Object afn19Object;
        //设置下发参数
        String errorMsg;
        if(!StringUtils.isEmpty(stopChargeVo.getOpenId())){
            // app停止充电
            afn19Object = new AFN19Object(UUID.randomUUID().toString(),
                    "19","999999999",
                    "42475858fffffa"
                    ,chargingDevice.getCommNo(),
                    "0",
                     port,
                    "off",
                    null,
                     stopChargeVo.getOpenId(),
                    "1",
                    "0",
                    Integer.toString(ChargeAppConstant.ChargingWay.CHARGING_TIME.getWay()),0);
            errorMsg = "停止充电发送中间件指令失败";
        } else {
            // web端停止充电
            afn19Object = new AFN19Object(UUID.randomUUID().toString(),
                    "19",
                    "999999999",
                    "0042475858fffaa",
                    chargingDevice.getCommNo(),
                    "0",
                     port,
                    "off",
                     stopChargeVo.getSessionId(),
                    null,
                    "",
                    "0",
                    Integer.toString(ChargeAppConstant.ChargingWay.CHARGING_TIME.getWay()),0);
            errorMsg = "停用充电桩中间件指令失败";
        }

        //下发数据
        int result =  ClientManager.sendAFN19Msg(afn19Object);
        if (result != 1) {
            throw new BusinessException(errorMsg);
        }
    }

    @Override
    public List<MessageDto> alarm(EventQueryParam param) {
        Integer appType = param.getAppType();
        String openId = param.getOpenId();
        ChargingCst chargingCst;
        if (ChargeAppConstant.AppType.WECHAT.getType().equals(appType)) {
            chargingCst = chargingCstService.queryByOpenId(openId);
        } else {
            chargingCst = chargingCstService.queryByAlipayUserId(openId);
        }

        List<MessageDto> messageDtoList = new ArrayList<>();

        // 查询用户的使用记录
        List<ChargingUseDetailed> list = chargingUseDetailedService.queryByCustomerGuid(chargingCst.getCustomerGuid());
        if (CollectionUtils.isEmpty(list)) {
            return messageDtoList;
        }

        List<String> chargingGuidList = Lists.newArrayList();
        List<String> plieGuidList = Lists.newArrayList();
        for (ChargingUseDetailed detailed : list) {
            chargingGuidList.add(detailed.getChargingGuid());
            plieGuidList.add(detailed.getChargingPlieGuid());
        }

        // 查询充电桩信息
        List<ChargingDevice> chargingDevices = chargingDeviceService.batchQueryByChargingPlieGuids(plieGuidList);
        // 分组
        ImmutableMap<String, ChargingDevice> deviceMap = Maps.uniqueIndex(chargingDevices, new Function<ChargingDevice, String>() {
            @Nullable
            @Override
            public String apply(@Nullable ChargingDevice chargingDevice) {
                return chargingDevice.getChargingPlieGuid();
            }
        });

        //查询异常数据
        Condition conditionC=new Condition(ChargingDevlog.class);
        Example.Criteria criteria = conditionC.createCriteria();
        criteria.andIn("chargingGuid",chargingGuidList);
        criteria.andNotEqualTo("eventCode",0);
        // 设置分页信息
        PageHelper.startPage(param.getPageNumber(),param.getPageSize(),"create_time DESC");
        List<ChargingDevlog> eventList = chargingDevlogMapper.selectByCondition(conditionC);
        // 设置分页总条数
        PageInfo pageInfo = new PageInfo(eventList);
        param.setTotal(pageInfo.getTotal());

        for (ChargingDevlog chargingDevlog : eventList) {
            MessageDto messageDto = new MessageDto();
            // 设置设备编号（包含端口）
            ChargingDevice chargingDevice = deviceMap.get(chargingDevlog.getChargingPlieGuid());
            messageDto.setDeviceNo(chargingDevice.getDeviceNo() + chargingDevice.getPort());
            messageDto.setMessageContent(chargingDevlog.getEventContent());
            messageDto.setMessageType(chargingDevlog.getEventCode());
            String createTime = DateUtil.formatDate(chargingDevlog.getCreateTime());
            messageDto.setCreateTime(createTime);
            if(messageDto.getMessageType()==ChargeAppConstant.MessageType.MESSAGE_ALARM.getType()){
                messageDto.setMessageTypeDesc(ChargeAppConstant.MessageType.MESSAGE_ALARM.getTypeDesc());
            }else if(messageDto.getMessageType()==ChargeAppConstant.MessageType.MESSAGE_CHARGE_STOP.getType()){
                messageDto.setMessageTypeDesc(ChargeAppConstant.MessageType.MESSAGE_CHARGE_STOP.getTypeDesc());
            }
            messageDto.setChargeGuid(chargingDevlog.getChargingGuid());

            String dateStr = DateUtil.getDateStr(chargingDevlog.getCreateTime());
            messageDto.setDate(dateStr);
            String timeStr = DateUtil.getTimeStr(chargingDevlog.getCreateTime());
            messageDto.setTime(timeStr);
            messageDtoList.add(messageDto);
        }

        return messageDtoList;
    }

    @Override
    public AppUserDto getAppUser(Integer appType,String openId) {
        AppUserDto appUserDto = new AppUserDto();
        ChargingCst chargingCst;
        if (ChargeAppConstant.AppType.WECHAT.getType().equals(appType)) {
            chargingCst =  chargingCstService.queryByOpenId(openId);
            appUserDto.setName(chargingCst.getCustomerName());
        } else {
            chargingCst = chargingCstService.queryByAlipayUserId(openId);
            appUserDto.setName(chargingCst.getAlipayNickName());
        }

        // TODO 上线放开注释 查询月卡方案
//        ChargingPayCheme chargingPayCheme = chargingPayChemeService.queryMonthCardScheme();
//        if (chargingPayCheme != null) {
//            appUserDto.setMonthCardFlag(1);
//        } else {
//            appUserDto.setMonthCardFlag(0);
//        }
        appUserDto.setMonthCardFlag(1);

        boolean monthUser = this.isMonthUser(chargingCst);
        if (monthUser) {
            // 包月用户
            appUserDto.setCustomerType(ChargeAppConstant.UserType.USER_MONTH.getType());
            appUserDto.setCustomerTypeDesc(ChargeAppConstant.UserType.USER_MONTH.getTypeDesc());
            appUserDto.setRemainCnt(chargingCst.getRemainCnt());
            String dateStr = DateUtil.getDateStr(chargingCst.getExpireTime());
            appUserDto.setEndTime(dateStr);
        } else {
            // 非包月用户
            appUserDto.setCustomerType(ChargeAppConstant.UserType.USER_COMMON.getType());
            appUserDto.setCustomerTypeDesc(ChargeAppConstant.UserType.USER_COMMON.getTypeDesc());
        }

        if(!StringUtils.isEmpty(chargingCst.getCustomerContact())){
            appUserDto.setAuthentication(ChargeAppConstant.IsAuthentication.IS_AUTHENTICATION_HAVED.getState());
            appUserDto.setAuthenticationDesc(ChargeAppConstant.IsAuthentication.IS_AUTHENTICATION_HAVED.getDesc());
            appUserDto.setPhoneNumber(chargingCst.getCustomerContact());
        }else{
            appUserDto.setAuthentication(ChargeAppConstant.IsAuthentication.IS_AUTHENTICATION_NULL.getState());
            appUserDto.setAuthenticationDesc(ChargeAppConstant.IsAuthentication.IS_AUTHENTICATION_NULL.getDesc());
        }
        appUserDto.setIsReceiveSms(chargingCst.getIsReceiveSms());
        appUserDto.setOpenId(openId);
        appUserDto.setRemainAmount(MathUtil.setPrecision(chargingCst.getRemainAmount()));
        return appUserDto;
    }

    @Override
    public Integer updateCustomer(ChargingCst chargingCst) {
        Condition condition=new Condition(ChargingCst.class);
        condition.createCriteria().andCondition("webchar_no = '"+chargingCst.getWebcharNo()+"'");
        Integer is_success = chargingCstMapper.updateByConditionSelective(chargingCst,condition);
        return is_success;
    }

    @Override
    public Long queryUseRecordTotal(HistoryVo historyVo) {
        return chargingPayMapper.queryChargeTotal(historyVo);
    }

    @Override
    public Long queryChargeTotal(HistoryVo historyVo) {
        return chargingUseDetailedMapper.queryUseRecordTotal(historyVo);
    }

    @Override
    public CurveListNDto deviceCurve(String openId, String deviceNo) {
        CurveListDto curveListDtoCurrent = new CurveListDto();  //设置电流临时对象
        CurveListDto curveListDtoVoltage = new CurveListDto();  //设置电压临时对象
        CurveListDto curveListDtoPower = new CurveListDto();    //设置功率临时对象
        //电流数组
        List<BigDecimal> listCurrent = new ArrayList<>();
        List<String> listCurrentTime = new ArrayList<>();
        //电压数组
        List<BigDecimal> listVoltage = new ArrayList<>();
        List<String> listVoltageTime = new ArrayList<>();
        //功率数组
        List<BigDecimal> listPower = new ArrayList<>();
        List<String> listPowerTime = new ArrayList<>();

        ChargingUseDetailed chargingUseDetailed = chargingUseDetailedService.queryChargingRecordByOpenId(openId);

        // 查询充电日志
        List<ChargingDevlog> chargingDevlogList = chargingDevlogService.queryByChargingGuid(chargingUseDetailed.getChargingGuid());

        CurveListNDto curveListNDto = new CurveListNDto();
        if (CollectionUtils.isEmpty(chargingDevlogList)) {
            return curveListNDto;
        }

        //遍历获取需要的数据
        for(ChargingDevlog devlog: chargingDevlogList){
            //设置当前电流
            listCurrent.add(devlog.getCurrent());
            String hourMinuteStr = DateUtil.getHourMinuteStr(devlog.getCreateTime());
            listCurrentTime.add(hourMinuteStr);
            //设置当前电压
            listVoltage.add(devlog.getVoltage());
            listVoltageTime.add(hourMinuteStr);
            //设置当前功率
            listPower.add(devlog.getPower());
            listPowerTime.add(hourMinuteStr);
        }
        curveListDtoCurrent.setValueList(listCurrent);
        curveListDtoCurrent.setTimeList(listCurrentTime);

        curveListDtoVoltage.setValueList(listVoltage);
        curveListDtoVoltage.setTimeList(listVoltageTime);

        curveListDtoPower.setValueList(listPower);
        curveListDtoPower.setTimeList(listPowerTime);

        //设置电流曲线
        curveListNDto.setList_current(curveListDtoCurrent);
        //设置功率曲线
        curveListNDto.setList_power(curveListDtoPower);
        //设置电压曲线
        curveListNDto.setList_voltage(curveListDtoVoltage);
        return curveListNDto;
    }

    @Override
    @Transactional
    public Integer startCharge(String deviceNo) {
        //设置更新对象
        ChargingDevice chargingDevice_c = new ChargingDevice();
        //设置更新条件
        Condition condition=new Condition(ChargingDevice.class);
        condition.createCriteria().andCondition("device_no = '"+deviceNo+"'");
        //设置更新参数
        //如果通电则修改设备状态为充电状态
        chargingDevice_c.setRunState(1);
        //更新设备状态
        int is_success = chargingDeviceMapper.updateByConditionSelective(chargingDevice_c,condition);
        return is_success;
    }


    @Override
    @Transactional
    public void stopCharge(String chargingGuid) {
        // 更新使用记录表算费功能
        ChargingUseDetailed chargingUseDetailed = chargingUseDetailedService.queryChargingRecordByChargingGuid(chargingGuid);

        //设置实际消费金额
        BigDecimal useMoney = chargingUseDetailed.getPrice().multiply(chargingUseDetailed.getUsePower());
        chargingUseDetailed.setConsumptionMoney(MathUtil.setPrecision(useMoney));
        //计算盈利
        BigDecimal profMoney = chargingUseDetailed.getDeductMoney().subtract(useMoney);
        //设置盈利费用
        chargingUseDetailed.setProfitable(MathUtil.setPrecision(profMoney));
        //更新充电桩使用记录
        chargingUseDetailedMapper.updateByPrimaryKeySelective(chargingUseDetailed);
    }

    @Override
    public LastUseRecordDto getLateUseRecord(Integer appType,String openId) {
        LastUseRecordDto lastUseRecordDto = new LastUseRecordDto();
        // 查询用户账户信息
        ChargingCst chargingCst;
        if (ChargeAppConstant.AppType.WECHAT.getType().equals(appType)) {
            chargingCst = chargingCstService.queryByOpenId(openId);
        } else {
            chargingCst = chargingCstService.queryByAlipayUserId(openId);
        }

        ChargingUseDetailed chargingUseDetailed = chargingUseDetailedService.queryRecentUseRecordByCustomerGuid(chargingCst.getCustomerGuid());
        if (chargingUseDetailed == null) {
            return lastUseRecordDto;
        }

        lastUseRecordDto.setChargeGuid(chargingUseDetailed.getChargingGuid());

        ChargingDevice chargingDevice = chargingDeviceService.queryByChargingPlieGuid(chargingUseDetailed.getChargingPlieGuid());
        //设置设备编号
        lastUseRecordDto.setDeviceNo(chargingDevice.getDeviceNo());
        //设置开始时间
        String startTime = DateUtil.formatDate(chargingUseDetailed.getStartTime());
        lastUseRecordDto.setStartTime(startTime);
        //设置充电状态
        lastUseRecordDto.setState(chargingDevice.getRunState());
        //设置状态描述
        String descByState = ChargeConstant.DeviceRunState.getDescByState(chargingDevice.getRunState());
        lastUseRecordDto.setStateDesc(descByState);

        //设置安装地址
        lastUseRecordDto.setInstallAddr(chargingDevice.getInstallAddr());

        ChargingDevlog chargingDevlog = chargingDevlogService.queryRecentError(chargingUseDetailed.getChargingGuid());
        if (chargingDevlog == null) {
            lastUseRecordDto.setIsEvent(1);
        } else {
            lastUseRecordDto.setIsEvent(0);
            lastUseRecordDto.setEventCode(chargingDevlog.getEventCode());
            lastUseRecordDto.setEventCodeDesc(chargingDevlog.getEventContent());
        }
        return lastUseRecordDto;
    }

    @Override
    public ChargeIntecerListDto chargeList(Integer appType,String openId) {
        ChargeIntecerListDto chargeIntecerListDto = new ChargeIntecerListDto();

        ChargingCst chargingCst;
        if (ChargeAppConstant.AppType.WECHAT.getType().equals(appType)) {
            chargingCst = chargingCstService.queryByOpenId(openId);
        } else {
            chargingCst = chargingCstService.queryByAlipayUserId(openId);
        }

        //设置用余额
        chargeIntecerListDto.setRemainAmount(MathUtil.setPrecision(chargingCst.getRemainAmount()));

        //查询用户充值记录表
        List<ChargingPay> list = chargingPayService.queryByCustomerGuid(chargingCst.getCustomerGuid());
        BigDecimal totalMoney = BigDecimal.ZERO;
        if(!CollectionUtils.isEmpty(list)){
            //循环获取数据
            for(ChargingPay pay: list){
                totalMoney = totalMoney.add(pay.getPayMoney());
            }
        }
        //设置已充值金额
        chargeIntecerListDto.setChargedMoney(totalMoney);

        //查询活动充值方案
        List<ChargingPayCheme> listCheme =chargingPayChemeService.querySchemeList4ActivityPage();
        List<ChargeListDto> chargeListDtos = new ArrayList<>();
        if(listCheme!=null){
            for(ChargingPayCheme chargingPayCheme:listCheme){
                ChargeListDto chargeListDto = new ChargeListDto();
                chargeListDto.setPriceId(chargingPayCheme.getId());
                chargeListDto.setChageMoney(chargingPayCheme.getPayMoney().floatValue());
                chargeListDto.setMoney(chargingPayCheme.getMoney().floatValue());
                chargeListDto.setChargeDesc("售价:"+chargingPayCheme.getPayMoney().floatValue());
                chargeListDtos.add(chargeListDto);
            }
        }
        //设置方案列表
        chargeIntecerListDto.setList(chargeListDtos);
        return chargeIntecerListDto;
    }

    @Override
    public ChargeIntecerDto chargeMoney(HttpServletRequest request, String ip, String openId, String priceId) {
        // 查询对应的价格方案
        ChargingPayCheme chargingPayCheme = chargingPayChemeService.selectByPrimaryKey(Integer.parseInt(priceId));
        if (chargingPayCheme == null) {
            throw new BusinessException("对应的价格方案不存在");
        }

        // 查询充值客户信息
        ChargingCst chargingCst = chargingCstService.queryByOpenId(openId);

        //调用支付接口
        Ajax ajax = wxChargerPayService.chargePay(ChargeAppConstant.PayScene.ACTIVITY_CHARGE.getType(),null,chargingPayCheme.getPayMoney(),ip,chargingPayCheme,chargingCst.getCustomerGuid(),openId);

        //微信预支付下单成功，设置微信下单参数
        ChargeIntecerDto chargeIntecerDto = new ChargeIntecerDto();
        chargeIntecerDto.setAppId(ajax.getParams().get("appId"));
        chargeIntecerDto.setNonceStr(ajax.getParams().get("nonceStr"));
        chargeIntecerDto.setTimeStamp(ajax.getParams().get("timeStamp"));
        chargeIntecerDto.setPackages(ajax.getParams().get("package"));
        chargeIntecerDto.setPaySign(ajax.getParams().get("paySign"));
        chargeIntecerDto.setSignType(ajax.getParams().get("signType"));
        return chargeIntecerDto;
    }

    @Override
    public String chargeMoneyByAlipay(String alipayUserId, String priceId) {
        // 查询对应的价格方案
        ChargingPayCheme chargingPayCheme = chargingPayChemeService.selectByPrimaryKey(Integer.parseInt(priceId));

        Integer payCategory = chargingPayCheme.getPayCategory();
        if (!ChargeConstant.SchemePayCategory.BALANCE_RECHARGE.getType().equals(payCategory)) {
            throw new BusinessException("充值方案id错误");
        }
        // 查询充值客户信息
        ChargingCst chargingCst = chargingCstService.queryByAlipayUserId(alipayUserId);

        // 生成订单
        CreateOrderParam orderParam = new CreateOrderParam();
        orderParam.setCustomerGuid(chargingCst.getCustomerGuid());
        orderParam.setPayFlag(UuidUtil.getUuid());
        orderParam.setPayMoney(chargingPayCheme.getPayMoney());
        orderParam.setAccountChargeMoney(chargingPayCheme.getMoney());
        orderParam.setChargingTime(chargingPayCheme.getChargingTime());
        orderParam.setPayCategory(payCategory);
        orderParam.setChargingCnt(chargingPayCheme.getChargingCnt());
        orderParam.setOpenNo(alipayUserId);
        orderParam.setRemainAmount(chargingCst.getRemainAmount());
        orderParam.setType(ChargeAppConstant.PayScene.ACTIVITY_CHARGE.getType());
        orderParam.setPayWay(ChargeAppConstant.PayWayConstant.ALIPAY.getType());
        orderParam.setPayState(0);
        ChargingPay order = alipayService.order(orderParam);

        // 支付宝创建订单
        String tradeNo = alipayService.charge(order);
        return tradeNo;
    }

    @Override
    public ChargeCompleteDto chargeComplete(String openId, String chargingGuid) {
        //创建结果对象
        ChargeCompleteDto resultDto = new ChargeCompleteDto();
        ChargingUseDetailed chargingUseDetailed;
        //判断是否传入charging_guid
        if(StringUtils.isEmpty(chargingGuid)){
            //查询最近一条充电桩使用记录
            chargingUseDetailed = chargingUseDetailedService.queryRecentUseRecord(openId);
        }else{
            //根据charging_guid查询
            chargingUseDetailed = chargingUseDetailedService.queryChargingRecordByChargingGuid(chargingGuid);
        }

        //设置选择充电的套餐类型
        this.setCompleteParams(chargingUseDetailed,resultDto);

        //查询异常
        ChargingDevlog chargingDevlog = chargingDevlogService.queryRecentError(chargingUseDetailed.getChargingGuid());
        resultDto.setIsEvent(0);
        if (chargingDevlog != null) {
            //设置异常
            resultDto.setIsEvent(1);
            resultDto.setEventCodeDesc(chargingDevlog.getEventContent());
        }
        return resultDto;
    }

    @Override
    public ChargeCompleteDto chargeMessageComplete(String chargingGuid, Integer messageType) {
        //创建结果对象
        ChargeCompleteDto resultDto = new ChargeCompleteDto();

        //根据charging_guid查询
        ChargingUseDetailed chargingUseDetailed = chargingUseDetailedService.queryChargingRecordByChargingGuid(chargingGuid);

        this.setCompleteParams(chargingUseDetailed,resultDto);
        //查询异常
        ChargingDevlog chargingDevlog = chargingDevlogService.queryRecentEvent(chargingGuid,messageType);
        resultDto.setIsEvent(0);
        if (chargingDevlog.getEventCode() == 1) {
            //设置异常
            resultDto.setIsEvent(1);
            resultDto.setEventCodeDesc(chargingDevlog.getEventContent());
        }
        return resultDto;
    }

    @Override
    public List<MonthDetialDto> monthlyCardDetial(String openId) {
        //实例化返回对象
        List<MonthDetialDto> list = new ArrayList<>();
        //查询月卡方案
        List<ChargingPayCheme> payChemeList = chargingPayChemeService.queryMonthSchemeList();
        //设置数据
        for(ChargingPayCheme cheme:payChemeList){
            MonthDetialDto monthDetialDto = new MonthDetialDto();
            monthDetialDto.setChargingCnt(cheme.getChargingCnt());
            monthDetialDto.setChargingTime((cheme.getNumMonths()*30));
            monthDetialDto.setMoney(cheme.getMoney());
            monthDetialDto.setPriceId(cheme.getId());
            list.add(monthDetialDto);
        }
        return list;
    }

    @Override
    public void register(Integer appType,String openId, String phoneNumber,String nickName, String verificationCode) {
        //验证验证码是否正确
        aliyunSmsService.verifySmsCode(phoneNumber,verificationCode);

        // 按手机号查询，该用户账户是否存在了
        ChargingCst cst = chargingCstService.queryByPhoneNumber(phoneNumber);
        if (cst == null) {
            // 手机号第一次注册
            if (ChargeAppConstant.AppType.WECHAT.getType().equals(appType)) {
                ChargingCst chargingCst = new ChargingCst();
                chargingCst.setCustomerName(nickName);
                chargingCst.setWebcharNo(openId);
                chargingCst.setCustomerGuid(UuidUtil.getUuid());
                chargingCst.setCustomerContact(phoneNumber);
                chargingCst.setCreateTime(new Date());
                chargingCstService.insertSelective(chargingCst);
            } else {
                ChargingCst chargingCst = new ChargingCst();
                chargingCst.setAlipayNickName(nickName);
                chargingCst.setAlipayUserId(openId);
                chargingCst.setCustomerGuid(UuidUtil.getUuid());
                chargingCst.setCustomerContact(phoneNumber);
                chargingCst.setCreateTime(new Date());
                chargingCstService.insertSelective(chargingCst);
            }
        } else {
            if (ChargeAppConstant.AppType.WECHAT.getType().equals(appType)) {
                // 更新账户绑定微信信息
                ChargingCst updateParam = new ChargingCst();
                updateParam.setId(cst.getId());
                updateParam.setWebcharNo(openId);
                updateParam.setCustomerName(nickName);
                updateParam.setUpdateTime(new Date());
                chargingCstService.updateByPrimaryKeySelective(updateParam);
            } else {
                // 更新账户绑定支付宝信息
                ChargingCst updateParam = new ChargingCst();
                updateParam.setId(cst.getId());
                updateParam.setAlipayUserId(openId);
                updateParam.setAlipayNickName(nickName);
                updateParam.setUpdateTime(new Date());
                chargingCstService.updateByPrimaryKeySelective(updateParam);
            }
        }
    }

    @Override
    public MonthChargeDto monthOfCharge(String openId,String ip,Integer priceId) {
        //查询价格方案
        ChargingPayCheme chargingPayCheme = chargingPayChemeService.selectByPrimaryKey(priceId);
        Integer payCategory = chargingPayCheme.getPayCategory();
        if (!ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(payCategory)) {
            throw new BusinessException("价格方案id错误");
        }

        MonthChargeDto monthChargeDto = new MonthChargeDto();
        // 查询充值客户信息
        ChargingCst chargingCst= chargingCstService.queryByOpenId(openId);

        monthChargeDto.setIsCharge(ChargeAppConstant.IsCHargeCardOfMonth.CHARGE_CARD_ENABLE.getState());
        monthChargeDto.setIsChargeDesc(ChargeAppConstant.IsCHargeCardOfMonth.CHARGE_CARD_ENABLE.getDesc());
        //判断该客户是否是包月用户
        if(this.isMonthUser(chargingCst)){
            monthChargeDto.setIsCharge(ChargeAppConstant.IsCHargeCardOfMonth.CHARGE_CARD_UNABLE.getState());
            monthChargeDto.setIsChargeDesc(ChargeAppConstant.IsCHargeCardOfMonth.CHARGE_CARD_UNABLE.getDesc());
            return monthChargeDto;
        }

        BigDecimal subtract = chargingCst.getRemainAmount().subtract(chargingPayCheme.getMoney());
        boolean flag = MathUtil.isLessThanZero(subtract);
        if (flag) {
            // 取相反数
            BigDecimal negate = subtract.negate();

            //调用充值接口
            Ajax ajax = wxChargerPayService.chargePay(ChargeAppConstant.PayScene.MONTH_CHARGE.getType(),chargingCst.getRemainAmount(),negate,ip,chargingPayCheme,chargingCst.getCustomerGuid(),openId);
            //微信预支付下单成功，设置微信下单参数
            monthChargeDto.setAppId(ajax.getParams().get("appId"));
            monthChargeDto.setNonceStr(ajax.getParams().get("nonceStr"));
            monthChargeDto.setTimeStamp(ajax.getParams().get("timeStamp"));
            monthChargeDto.setPackages(ajax.getParams().get("package"));
            monthChargeDto.setPaySign(ajax.getParams().get("paySign"));
            monthChargeDto.setSignType(ajax.getParams().get("signType"));
            monthChargeDto.setIsPay(ChargeAppConstant.IsPay.PAY_ENABLE.getPay());
            monthChargeDto.setIsPayDesc(ChargeAppConstant.IsPay.PAY_ENABLE.getPayDesc());
            return monthChargeDto;
        } else {
            // 余额足够直接扣减余额
            monthChargeDto.setIsPay(ChargeAppConstant.IsPay.PAY_UNABLE.getPay());
            monthChargeDto.setIsPayDesc(ChargeAppConstant.IsPay.PAY_UNABLE.getPayDesc());

            // 纯余额购买月卡，生成余额支付订单
            CreateOrderParam orderParam = new CreateOrderParam();
            orderParam.setCustomerGuid(chargingCst.getCustomerGuid());
            orderParam.setPayFlag(UuidUtil.getUuid());
            orderParam.setPayMoney(chargingPayCheme.getMoney());
            orderParam.setAccountChargeMoney(BigDecimal.ZERO);
            orderParam.setChargingTime(chargingPayCheme.getChargingTime());
            orderParam.setPayCategory(payCategory);
            orderParam.setChargingCnt(chargingPayCheme.getChargingCnt());
            orderParam.setOpenNo(openId);
            orderParam.setRemainAmount(subtract);
            orderParam.setType(ChargeAppConstant.PayScene.MONTH_CHARGE.getType());
            orderParam.setPayWay(ChargeAppConstant.PayWayConstant.BALANCE.getType());
            orderParam.setPayState(1);

            //调用扣减接口
            wxChargerPayService.deductionsOfMonth(chargingCst,chargingPayCheme,orderParam);
            return monthChargeDto;
        }
    }

    @Override
    public MonthChargeDto4Alipay monthOfChargeByAlipay(String alipayUserId, Integer priceId) {
        //查询价格方案
        ChargingPayCheme chargingPayCheme = chargingPayChemeService.selectByPrimaryKey(priceId);
        Integer payCategory = chargingPayCheme.getPayCategory();
        if (!ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(payCategory)) {
            throw new BusinessException("价格方案id错误");
        }

        ChargingCst chargingCst= chargingCstService.queryByAlipayUserId(alipayUserId);
        MonthChargeDto4Alipay dto = new MonthChargeDto4Alipay();
        dto.setIsCharge(ChargeAppConstant.IsCHargeCardOfMonth.CHARGE_CARD_ENABLE.getState());
        dto.setIsChargeDesc(ChargeAppConstant.IsCHargeCardOfMonth.CHARGE_CARD_ENABLE.getDesc());
        //判断该客户是否是包月用户
        if(this.isMonthUser(chargingCst)){
            dto.setIsCharge(ChargeAppConstant.IsCHargeCardOfMonth.CHARGE_CARD_UNABLE.getState());
            dto.setIsChargeDesc(ChargeAppConstant.IsCHargeCardOfMonth.CHARGE_CARD_UNABLE.getDesc());
            return dto;
        }

        BigDecimal remainAmount = chargingCst.getRemainAmount();
        BigDecimal sub = MathUtil.sub(remainAmount, chargingPayCheme.getMoney());
        boolean flag = MathUtil.isLessThanZero(sub);
        if (flag) {
            // 取相反数
            BigDecimal chargeMoney = sub.negate();

            // 生成支付订单
            CreateOrderParam orderParam = new CreateOrderParam();
            orderParam.setCustomerGuid(chargingCst.getCustomerGuid());
            orderParam.setPayFlag(UuidUtil.getUuid());
            orderParam.setPayMoney(chargeMoney);
            orderParam.setAccountChargeMoney(chargeMoney);
            orderParam.setAccountDeductMoney(remainAmount);
            orderParam.setChargingTime(chargingPayCheme.getChargingTime());
            orderParam.setPayCategory(payCategory);
            orderParam.setChargingCnt(chargingPayCheme.getChargingCnt());
            orderParam.setOpenNo(alipayUserId);
            orderParam.setRemainAmount(chargingCst.getRemainAmount());
            orderParam.setType(ChargeAppConstant.PayScene.MONTH_CHARGE.getType());
            orderParam.setPayWay(ChargeAppConstant.PayWayConstant.ALIPAY.getType());
            orderParam.setPayState(0);
            ChargingPay order = alipayService.order(orderParam);

            //调用充值接口
            String tradeNo = alipayService.charge(order);
            dto.setIsPay(ChargeAppConstant.IsPay.PAY_ENABLE.getPay());
            dto.setIsPayDesc(ChargeAppConstant.IsPay.PAY_ENABLE.getPayDesc());
            dto.setTradeNo(tradeNo);
            return dto;
        } else {
            // 余额足够直接扣减余额
            dto.setIsPay(ChargeAppConstant.IsPay.PAY_UNABLE.getPay());
            dto.setIsPayDesc(ChargeAppConstant.IsPay.PAY_UNABLE.getPayDesc());

            // 纯余额购买月卡，生成余额支付订单
            CreateOrderParam orderParam = new CreateOrderParam();
            orderParam.setCustomerGuid(chargingCst.getCustomerGuid());
            orderParam.setPayFlag(UuidUtil.getUuid());
            orderParam.setPayMoney(chargingPayCheme.getMoney());
            orderParam.setAccountChargeMoney(BigDecimal.ZERO);
            orderParam.setChargingTime(chargingPayCheme.getChargingTime());
            orderParam.setPayCategory(payCategory);
            orderParam.setChargingCnt(chargingPayCheme.getChargingCnt());
            orderParam.setOpenNo(alipayUserId);
            orderParam.setRemainAmount(sub);
            orderParam.setType(ChargeAppConstant.PayScene.MONTH_CHARGE.getType());
            orderParam.setPayWay(ChargeAppConstant.PayWayConstant.BALANCE.getType());
            orderParam.setPayState(1);

            //调用扣减接口
            wxChargerPayService.deductionsOfMonth(chargingCst,chargingPayCheme,orderParam);
            return dto;
        }
    }

    @Override
    public Integer isReceiveSms(Integer appType,String openId, Integer isReceiveSms) {
        //查询到用户账户
        ChargingCst chargingCst;
        if (ChargeAppConstant.AppType.WECHAT.getType().equals(appType)) {
            chargingCst = chargingCstService.queryByOpenId(openId);
        } else {
            chargingCst = chargingCstService.queryByAlipayUserId(openId);
        }

        ChargingCst param = new ChargingCst();
        param.setId(chargingCst.getId());
        param.setIsReceiveSms(isReceiveSms);
        param.setUpdateTime(new Date());
        //更新用户接收短信的标志
        Integer isSuccess = chargingCstService.updateByPrimaryKeySelective(param);
        return isSuccess;
    }

    @Override
    public RegisterDto updatePhoneNumber(Integer appType,String openId, String phoneNumber, String verificationCode) {
        RegisterDto registerDto = new RegisterDto();
        ChargingCst chargingCst = chargingCstService.queryByPhoneNumber(phoneNumber);

        //查询该手机号是否被注册过
        if(chargingCst != null){
            registerDto.setIsSuccess(ChargeAppConstant.UpdatePhone.UPDATE_REGISTER_HAVED.getState());
            registerDto.setIsSuccessDesc(ChargeAppConstant.UpdatePhone.UPDATE_REGISTER_HAVED.getDesc());
            return registerDto;
        }

        ChargingCst cst;
        if (ChargeAppConstant.AppType.WECHAT.getType().equals(appType)) {
            cst = chargingCstService.queryByOpenId(openId);
        } else {
            cst = chargingCstService.queryByAlipayUserId(openId);
        }

        //验证验证码是否正确
        aliyunSmsService.verifySmsCode(cst.getCustomerContact(),verificationCode);

        //更新客户信息
        ChargingCst param = new ChargingCst();
        param.setId(cst.getId());
        param.setCustomerContact(phoneNumber);
        Integer isSuccess = chargingCstService.updateByPrimaryKeySelective(param);
        if(isSuccess==0){
            registerDto.setIsSuccess(ChargeAppConstant.UpdatePhone.UPDATE_FAILE.getState());
            registerDto.setIsSuccessDesc(ChargeAppConstant.UpdatePhone.UPDATE_FAILE.getDesc());
            return registerDto;
        }
        registerDto.setIsSuccess(ChargeAppConstant.UpdatePhone.UPDATE_SUCCESS.getState());
        registerDto.setIsSuccessDesc(ChargeAppConstant.UpdatePhone.UPDATE_SUCCESS.getDesc());
        return registerDto;
    }

    @Override
    public void sendAlarmMessage(ChargingDevlog chargingDevlog,String address,String createTime) {
        //根据chargingGuid去查询使用记录
        ChargingUseDetailed chargingUseDetailed = chargingUseDetailedService.queryChargingRecordByChargingGuid(chargingDevlog.getChargingGuid());
        //获取到相应的客户信息
        ChargingCst chargingCst = chargingCstService.queryByCustomerGuid(chargingUseDetailed.getCustomerGuid());
        //给相应的客户发送警告信息
        if(chargingCst.getIsReceiveSms()==1 && !StringUtils.isEmpty(chargingCst.getCustomerContact())){
            ChargeAbnormalAlarmParam alarmParam = new ChargeAbnormalAlarmParam();
            alarmParam.setChargingGuid(chargingUseDetailed.getChargingGuid());
            alarmParam.setOpenId(chargingUseDetailed.getWebcharNo());
            // TODO 暂时写死
            alarmParam.setContactPhone("028-85363622");
            alarmParam.setMobilePhone(chargingCst.getCustomerContact());
            SimpleDateFormat format = new SimpleDateFormat("HH时mm分ss秒");
            String endTime = format.format(chargingDevlog.getCreateTime());
            alarmParam.setEndTime(endTime);
            alarmParam.setEventContent(chargingDevlog.getEventContent());
            aliyunSmsService.sendChargeAbnormalAlarm(alarmParam);
        }
    }

    @Override
    public void sendStopMessage(String chargingGuid, String address, String createTime) {
        //根据chargingGuid去查询使用记录
        ChargingUseDetailed chargingUseDetailed = chargingUseDetailedService.queryChargingRecordByChargingGuid(chargingGuid);
        //获取到相应的客户信息
        ChargingCst chargingCst = chargingCstService.queryByCustomerGuid(chargingUseDetailed.getCustomerGuid());
        //根据chagingGuid查询日志断电记录
        ChargingDevlog chargingDevlog = chargingDevlogService.queryRecentStop(chargingGuid);
        //给相应的客户发送警告信息
        if(chargingCst.getIsReceiveSms()==1&&!StringUtils.isEmpty(chargingCst.getCustomerContact())){
            ChargeNormalAlarmParam alarmParam = new ChargeNormalAlarmParam();
            alarmParam.setChargingGuid(chargingUseDetailed.getChargingGuid());
            alarmParam.setOpenId(chargingUseDetailed.getWebcharNo());
            alarmParam.setMobilePhone(chargingCst.getCustomerContact());
            Date endTime = chargingUseDetailed.getEndTime();
            Date startTime = chargingUseDetailed.getStartTime();
            String chargeTime = DateUtil.getTimeDifferenceNew(endTime, startTime);
            alarmParam.setChargeTime(chargeTime);
            SimpleDateFormat format = new SimpleDateFormat("HH时mm分");
            String beginTime = format.format(startTime);
            alarmParam.setBeginTime(beginTime);
            alarmParam.setEndTime(format.format(endTime));
            aliyunSmsService.sendChargeNormalAlarm(alarmParam);
        }
    }

    @Override
    public WithdrawCashInfo queryCashInfo(Integer appType,String openId) {
        WithdrawCashInfo cashInfo = new WithdrawCashInfo();
        ChargingCst cst;
        if (ChargeAppConstant.AppType.WECHAT.getType().equals(appType)) {
            cst = chargingCstService.queryByOpenId(openId);
            String alipayUserId = cst.getAlipayUserId();
            if (!StringUtils.isEmpty(alipayUserId)) {
                cashInfo.setHasAccount(1);
                cashInfo.setUserId(alipayUserId);
            }
        } else {
            cst = chargingCstService.queryByAlipayUserId(openId);
            String webcharNo = cst.getWebcharNo();
            if (!StringUtils.isEmpty(webcharNo)) {
                cashInfo.setHasAccount(1);
                cashInfo.setUserId(webcharNo);
            }
        }

        cashInfo.setRemainAmount(cst.getRemainAmount());
        cashInfo.setWithdrawCashAmount(cst.getRemainAmount());

        ChargingProject chargingProject = chargingProjectService.queryProjectInfo();
        cashInfo.setContactTelphone(chargingProject.getContactTelphone());
        return cashInfo;
    }

    @Override
    @Transactional
    public void bindIcCard(ChargingCard chargingCard, String customerGuid,String userPhone) {
        // 绑定IC卡信息
        ChargingCard param = new ChargingCard();
        param.setId(chargingCard.getId());
        param.setCustomerContact(userPhone);
        param.setCustomerGuid(customerGuid);
        param.setUpdateTime(new Date());
        chargingCardService.updateByPrimaryKeySelective(param);

//        BigDecimal remainAmount = chargingCard.getRemainAmount();
//        boolean greateThanZero = MathUtil.isGreateThanZero(remainAmount);
//        if (greateThanZero) {
//            // 绑定人账户转入IC卡余额
//            ChargingCst chargingCst = chargingCstService.queryByCustomerGuidForUpdate(customerGuid);
//
//            // 更新账户并记录流水
//            AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.BIND_ICCARD_RECHARGE, remainAmount);
//            operateVo.setGuid(UuidUtil.getUuid());
//            operateVo.setAccountId(chargingCst.getId());
//
//            ChargingCst cstParam = new ChargingCst();
//            cstParam.setId(chargingCst.getId());
//            cstParam.setUpdateTime(new Date());
//            BigDecimal totalAmount = chargingCst.getRemainAmount().add(remainAmount);
//            cstParam.setRemainAmount(totalAmount);
//            chargingCstService.updateAccountNew(cstParam,operateVo);
//        }
    }

    @Override
    @Transactional
    public void unBindIcCard(ChargingCard chargingCard, String customerGuid, Integer isReserve, BigDecimal amount) {
        // 解除IC卡绑定信息
        if (isReserve == 0) {
            // 不需要给IC卡预留金额
            ChargingCard card = new ChargingCard();
            card.setId(chargingCard.getId());
            card.setCustomerGuid("");
            card.setUpdateTime(new Date());
            chargingCardService.updateByPrimaryKeySelective(card);
        } else {
            // 需要给IC卡预留金额
            ChargingCard card = new ChargingCard();
            card.setId(chargingCard.getId());
            card.setCustomerGuid("");
            card.setRemainAmount(amount);
            card.setUpdateTime(new Date());
            chargingCardService.updateByPrimaryKeySelective(card);

            // 解绑用户的账户余额扣除IC卡预留金额
            ChargingCst chargingCst = chargingCstService.queryByCustomerGuidForUpdate(customerGuid);

            // 更新账户并记录流水
            AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.UNBIND_ICCARD_RECHARGE, amount);
            operateVo.setGuid(UuidUtil.getUuid());
            operateVo.setAccountId(chargingCst.getId());

            ChargingCst cstParam = new ChargingCst();
            cstParam.setId(chargingCst.getId());
            cstParam.setUpdateTime(new Date());
            BigDecimal remainAmount = chargingCst.getRemainAmount().subtract(amount);
            boolean lessThanZero = MathUtil.isLessThanZero(remainAmount);
            if (lessThanZero) {
                throw new BusinessException("剩余金额不足以抵扣IC卡预留金额");
            }

            cstParam.setRemainAmount(remainAmount);
            chargingCstService.updateAccountNew(cstParam,operateVo);
        }
    }

    @Override
    public IcCardInfo queryIcCardInfo(String cardId) {
        ChargingCard chargingCard = chargingCardService.queryByCardId(cardId);
        if (chargingCard == null) {
            throw new BusinessException("IC卡号不正确");
        }

        // 查询最近3条IC卡使用记录
        List<ChargingUseDetailed> useDetaileds = chargingUseDetailedService.queryRecentNlist(cardId, 3);
        List<IcCardUseInfo> useInfoList = this.getIcCardUseInfoList(useDetaileds);

        // 查询最近3条IC卡充值记录
        List<ChargingPay> payList = chargingPayService.queryRecentNlist(cardId, 3);
        List<IcCardPayInfo> payInfoList = this.getIcCardPayInfoList(payList);

        IcCardInfo cardInfo = new IcCardInfo();
        cardInfo.setUseInfoList(useInfoList);
        cardInfo.setPayInfoList(payInfoList);
        return cardInfo;
    }

    /**
     * 获取IC卡使用记录列表
     * @param useDetaileds
     * @return
     */
    private List<IcCardUseInfo> getIcCardUseInfoList(List<ChargingUseDetailed> useDetaileds) {
        List<IcCardUseInfo> useInfoList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(useDetaileds)) {
            return useInfoList;
        }

        for (ChargingUseDetailed useDetailed : useDetaileds) {
            IcCardUseInfo useInfo = new IcCardUseInfo();
            useInfo.setCardId(useDetailed.getOpenNo());
            useInfo.setDeductMoney(useDetailed.getDeductMoney());
            useInfo.setBeginTime(useDetailed.getStartTime());
            useInfo.setEndTime(useDetailed.getEndTime());
            useInfoList.add(useInfo);
        }

        return useInfoList;
    }

    /**
     * 获取IC卡使用支付列表
     * @param payList
     * @return
     */
    private List<IcCardPayInfo> getIcCardPayInfoList(List<ChargingPay> payList) {
        List<IcCardPayInfo> payInfoList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(payList)) {
            return payInfoList;
        }

        for (ChargingPay chargingPay : payList) {
            IcCardPayInfo payInfo = new IcCardPayInfo();
            payInfo.setCardId(chargingPay.getCardId());
            payInfo.setPayMoney(chargingPay.getPayMoney());
            String payMethod = ChargeAppConstant.PayWayConstant.getDescByType(chargingPay.getPayWay());
            payInfo.setPayMethod(payMethod);
            payInfo.setPayTime(chargingPay.getUpdateTime());
            payInfoList.add(payInfo);
        }
        return payInfoList;
    }

    @Override
    public PageData<IcCardUseInfo> queryIcCardUseInfo(String cardId,Integer appType,String openId, Integer pageNumber, Integer pageSize) {
        // 查询用户
        ChargingCst chargingCst;
        if (ChargeAppConstant.AppType.WECHAT.getType().equals(appType)) {
            chargingCst = chargingCstService.queryByOpenId(openId);
        } else {
            chargingCst = chargingCstService.queryByAlipayUserId(openId);
        }
        if (chargingCst == null) {
            throw new BusinessException("用户不存在");
        }

        // 查询IC卡信息
        ChargingCard card = chargingCardService.queryByCardId(cardId);
        if (card == null) {
            throw new BusinessException("IC卡号不正确");
        }

        String customerGuid = card.getCustomerGuid();
        if (StringUtils.isEmpty(customerGuid)) {
            throw new BusinessException("不允许查询他人IC卡信息");
        }

        if (!chargingCst.getCustomerGuid().equals(customerGuid)) {
            throw new BusinessException("不允许查询他人IC卡信息");
        }

        ChargingUseDetailed param = new ChargingUseDetailed();
        param.setCustomerGuid(chargingCst.getCustomerGuid());
        param.setOpenNo(cardId);
        param.setState(1);
        PageHelper.startPage(pageNumber,pageSize,"id desc");
        List<ChargingUseDetailed> list = chargingUseDetailedService.select(param);
        PageInfo<ChargingUseDetailed> pageInfo = new PageInfo(list);

        List<IcCardUseInfo> icCardUseInfo = this.getIcCardUseInfoList(list);
        PageData<IcCardUseInfo> pageData = new PageData<>();
        pageData.setTotal(pageInfo.getTotal());
        pageData.setList(icCardUseInfo);
        return pageData;
    }

    @Override
    public PageData<IcCardPayInfo> queryIcCardPayInfo(String cardId,Integer appType,String openId, Integer pageNumber, Integer pageSize) {
        // 查询用户
        ChargingCst chargingCst;
        if (ChargeAppConstant.AppType.WECHAT.getType().equals(appType)) {
            chargingCst = chargingCstService.queryByOpenId(openId);
        } else {
            chargingCst = chargingCstService.queryByAlipayUserId(openId);
        }
        if (chargingCst == null) {
            throw new BusinessException("用户不存在");
        }

        // 查询IC卡信息
        ChargingCard card = chargingCardService.queryByCardId(cardId);
        if (card == null) {
            throw new BusinessException("IC卡号不正确");
        }

        String customerGuid = card.getCustomerGuid();
        if (StringUtils.isEmpty(customerGuid)) {
            throw new BusinessException("不允许查询他人IC卡信息");
        }

        if (!chargingCst.getCustomerGuid().equals(customerGuid)) {
            throw new BusinessException("不允许查询他人IC卡信息");
        }

        ChargingPay param = new ChargingPay();
        param.setCustomerGuid(chargingCst.getCustomerGuid());
        param.setCardId(cardId);
        param.setPayState(1);
        PageHelper.startPage(pageNumber,pageSize,"id desc");
        List<ChargingPay> payList = chargingPayService.select(param);
        PageInfo<ChargingPay> pageInfo = new PageInfo(payList);

        List<IcCardPayInfo> icCardPayInfoList = this.getIcCardPayInfoList(payList);
        PageData<IcCardPayInfo> pageData = new PageData<>();
        pageData.setTotal(pageInfo.getTotal());
        pageData.setList(icCardPayInfoList);
        return pageData;
    }

    private void setCompleteParams(ChargingUseDetailed chargingUseDetailed, ChargeCompleteDto resultDto ){
        Integer payCategory = chargingUseDetailed.getPayCategory();
        resultDto.setPayCategory(payCategory);
        if(ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getType().equals(payCategory)){
            resultDto.setPayCategoryDesc(ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getDesc());
            //设置充电金额
            resultDto.setPayMoney(chargingUseDetailed.getDeductMoney());
            //设置剩余金额
            resultDto.setRemainAmount(chargingUseDetailed.getAfterRemainAmount());
        }else if(ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(payCategory)){
            resultDto.setPayCategoryDesc(ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getDesc());
            //设置扣除次数
            resultDto.setUseCnt(1);
            //设置剩余次数
            resultDto.setRemainCnt(chargingUseDetailed.getAfterRemainCnt());
        }else if(ChargeConstant.SchemePayCategory.RECHARGE_FULL.getType().equals(payCategory)){
            resultDto.setPayCategoryDesc(ChargeConstant.SchemePayCategory.RECHARGE_FULL.getDesc());
            //设置充电金额
            resultDto.setPayMoney(chargingUseDetailed.getDeductMoney());
            //设置剩余金额
            resultDto.setRemainAmount(chargingUseDetailed.getAfterRemainAmount());
        }
        //设置充电时长
        String eDate = DateUtil.formatDate(chargingUseDetailed.getEndTime());
        String sDate = DateUtil.formatDate(chargingUseDetailed.getStartTime());
        String chargeTime = DateUtil.getTimeDifference(eDate,sDate);
        resultDto.setChargeTime(chargeTime);

        //设置开始充电时间
        resultDto.setStartTime(DateUtil.formatDate(chargingUseDetailed.getStartTime()));
        //设置结束充电时间
        resultDto.setEndTime(DateUtil.formatDate(chargingUseDetailed.getEndTime()));
        //设置联系电话
        ChargingDevice chargingDevice = chargingDeviceService.queryByChargingPlieGuid(chargingUseDetailed.getChargingPlieGuid());
        resultDto.setDeviceNo(chargingDevice.getDeviceNo() + chargingDevice.getPort());
        ChargingProject chargingProject = chargingProjectService.queryByProjectGuid(chargingDevice.getProjectGuid());
        resultDto.setContact(chargingProject.getContactTelphone());
    }
}
