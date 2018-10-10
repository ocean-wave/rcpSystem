package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.constant.BusinessType;
import cn.com.cdboost.collect.constant.ChargeAppConstant;
import cn.com.cdboost.collect.constant.ChargeConstant;
import cn.com.cdboost.collect.dao.*;
import cn.com.cdboost.collect.dto.RegisterDto;
import cn.com.cdboost.collect.dto.chargerApp.Ajax;
import cn.com.cdboost.collect.dto.chargerApp.*;
import cn.com.cdboost.collect.dto.chargerApp.vo.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.*;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.*;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.service.util.XMLUtil;
import cn.com.cdboost.collect.util.*;
import cn.com.cdboost.collect.vo.PageData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.example.clienttest.client.AFN19Object;
import com.example.clienttest.clientfuture.ClientManager;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Service
public class AppChargerServiceImpl implements AppChargerService {
    private static final Logger logger = LoggerFactory.getLogger(AppChargerServiceImpl.class);

    private static final String CHARSET = "utf-8";

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
    @Autowired
    private WeixinService weixinService;
    @Autowired
    private ChargingMonthCardAcctService chargingMonthCardAcctService;
    @Autowired
    private ChargingCardListService chargingCardListService;
    @Autowired
    private ChargingSpiltAccountService spiltAccountService;
    @Autowired
    private XMLUtil xmlUtil;


    @Value("${onElecSuccessTemplateId}")
    private String onElecSuccessTemplateId;

    @Value("${balanceOffElecTemplateId}")
    private String balanceOffElecTemplateId;

    @Value("${monthOffElecTemplateId}")
    private String monthOffElecTemplateId;

    @Value("${powerUpTemplateId}")
    private String powerUpTemplateId;

    @Value("${powerUpFirstContent}")
    private String powerUpFirstContent;

    @Value("${powerUpRemarkContent}")
    private String powerUpRemarkContent;

    @Value("${onElecSuccessTitle}")
    private String onElecSuccessTitle;

    @Value("${aliyun.chargeEndSwitch}")
    private String chargeEndSwitch;

    @Value("${aliyun.powerUpSwitch}")
    private String powerUpSwitch;

    @Value("${appId}")
    private String appId;
    @Value("${mchId}")
    private String mchId;
    @Value("${partnerkey}")
    private String partnerkey;

    @Value("${alipay.appId}")
    private String alipayAppId;
    @Value("${alipay.app.private.key}")
    private String alipayAppPrivateKey;
    @Value("${alipay.public.key}")
    private String alipayPublicKey;

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
                dto.setImgFlag(1);
                return dto;
            }
            // 查询项目信息
            ChargingProject chargingProject = chargingProjectService.queryByProjectGuid(chargingDevice.getProjectGuid());
            if(chargingProject==null){
                dto.setIsJump(0);
                dto.setIsJumpDesc("项目不存在");
                dto.setImgFlag(1);
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
                dto.setImgFlag(1);
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
                    dto.setImgFlag(0);
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

                        Integer schemeType = chargingProject.getSchemeType();
                        dto.setSchemeType(schemeType);
                        if (schemeType == 0) {
                            // 方案一、设置购买方案列表
                            this.setSchemeOneList(dto,chargingCst,chargingDevice.getProjectGuid());
                        } else {
                            // 方案二、设置购买方案列表
                            this.setSchemeTwoList(dto,chargingDevice.getProjectGuid());
                        }

                    } else if (ChargeConstant.DeviceRunState.UN_USE.getState().equals(runState)) {
                        // 跳转到设备停用页面
                        dto.setState(0);
                        dto.setIsJump(0);
                        dto.setStateDesc("设备已停用");
                        dto.setIsJumpDesc("设备已停用");
                        dto.setImgFlag(1);
                    } else if (ChargeConstant.DeviceRunState.FAULT.getState().equals(runState)) {
                        // 跳转到设备故障页面
                        dto.setState(0);
                        dto.setIsJump(0);
                        dto.setStateDesc("设备故障");
                        dto.setIsJumpDesc("设备故障");
                        dto.setImgFlag(2);
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
//        boolean monthUser = this.isMonthUser(chargingCst);
        if (false) {
            // 包月用户
//            dto.setCustomerType(ChargeAppConstant.UserType.USER_MONTH.getType());
//            dto.setCustomerTypeDesc(ChargeAppConstant.UserType.USER_MONTH.getTypeDesc());
//            String dateStr = DateUtil.getDateStr(chargingCst.getExpireTime());
//            dto.setEndTime(dateStr);
//            dto.setRemainCnt(chargingCst.getRemainCnt());
//            dto.setRemainAmount(chargingCst.getRemainAmount());
        } else {
            // 非包月用户
            dto.setCustomerType(ChargeAppConstant.UserType.USER_COMMON.getType());
            dto.setCustomerTypeDesc(ChargeAppConstant.UserType.USER_COMMON.getTypeDesc());
            dto.setRemainAmount(chargingCst.getRemainAmount());
            dto.setRemainCnt(0);
        }
    }

    private void setSchemeTwoList(WxBaseInfoDto dto,String projectGuid) {
        List<ChargingPayCheme> payChemes = chargingPayChemeService.querySchemeTwoList(projectGuid);
        // 分组
        SortedMap<String,List<ChargingPayCheme>> map = new TreeMap<>();
        for (ChargingPayCheme payCheme : payChemes) {
            BigDecimal money = payCheme.getMoney();
            // 统一转成2位精度
            String key = String.valueOf(MathUtil.setPrecision(money));
            boolean flag = map.containsKey(key);
            if (flag) {
                List<ChargingPayCheme> list = map.get(key);
                list.add(payCheme);
            } else {
                List<ChargingPayCheme> list = Lists.newArrayList();
                list.add(payCheme);
                map.put(key,list);
            }
        }

        // 收费信息描述（XX钱/XX小时）
        ChargingPayCheme cheme = payChemes.get(0);
        String key = String.valueOf(MathUtil.setPrecision(cheme.getMoney()));
        List<PriceInfo> priceInfos = Lists.newArrayList();
        List<ChargingPayCheme> list = map.get(key);
        for (ChargingPayCheme payCheme : list) {
            PriceInfo priceInfo = new PriceInfo();
            String priceDesc = payCheme.getMoney() + "元/" + payCheme.getChargingTime() + "小时";
            priceInfo.setPriceDesc(priceDesc);
            String powerDesc = payCheme.getMinPower() + "~" + payCheme.getMaxPower() + "W";
            priceInfo.setPowerDesc(powerDesc);
            priceInfos.add(priceInfo);
        }

        // 可选择支付金额列表
        List<PriceDto> priceDtos = Lists.newArrayList();
        for (Map.Entry<String, List<ChargingPayCheme>> entry : map.entrySet()) {
            List<ChargingPayCheme> value = entry.getValue();
            // 任取一条
            ChargingPayCheme payCheme = value.get(0);
            PriceDto priceDto = new PriceDto();
            priceDto.setPriceId(payCheme.getId());
            priceDto.setPayCategory(payCheme.getPayCategory());
            priceDto.setMoney(payCheme.getMoney());
            priceDto.setPayDesc(payCheme.getMoney() + "元");
            priceDtos.add(priceDto);
        }

        dto.setPriceList(priceInfos);
        dto.setList_price(priceDtos);
    }

    /**
     * 设置充电购买方案列表
     * @param dto
     * @param chargingCst
     * @param projectGuid
     */
    private void setSchemeOneList(WxBaseInfoDto dto, ChargingCst chargingCst, String projectGuid) {
//        List<ChargingMonthCardAcct> monthCardAccts = chargingMonthCardAcctService.queryUseableMonthCard(chargingCst.getCustomerGuid());
//        List<String> list = Lists.newArrayList();
//        for (ChargingMonthCardAcct monthCardAcct : monthCardAccts) {
//            list.add(monthCardAcct.getSchemeGuid());
//        }
//
//        // 批量查询方案信息
//        List<ChargingPayCheme> userSchemes = chargingPayChemeService.batchQuery(list);
//
//        // 分组
//        Maps.uniqueIndex(userSchemes, new Function<ChargingPayCheme, String>() {
//            @Nullable
//            @Override
//            public String apply(@Nullable ChargingPayCheme payCheme) {
//                return payCheme.getSchemeGuid();
//            }
//        });
//

        List<ChargingPayCheme> chemeList = chargingPayChemeService.querySchemeList4ChargePage(projectGuid);
        List<String> list = Lists.newArrayList();
        for (ChargingPayCheme payCheme : chemeList) {
            Integer payCategory = payCheme.getPayCategory();
            if (ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(payCategory)) {
                list.add(payCheme.getSchemeGuid());
            }
        }

        // 查询该用户是否存在有效的月卡账户
        Map<String,ChargingMonthCardAcct> map = Maps.newHashMap();
        if (!CollectionUtils.isEmpty(list)) {
            // 批量查询用户月卡可用账户信息
            List<ChargingMonthCardAcct> monthCardAccts = chargingMonthCardAcctService.queryUserBuyMonthCard(chargingCst.getCustomerGuid(),list);
            if (!CollectionUtils.isEmpty(monthCardAccts)) {
                for (ChargingMonthCardAcct monthCardAcct : monthCardAccts) {
                    map.put(monthCardAcct.getSchemeGuid(),monthCardAcct);
                }
            }
        }

        List<PriceDto> priceDtoList = new ArrayList<>();
        for (ChargingPayCheme payCheme : chemeList) {
            String schemeGuid = payCheme.getSchemeGuid();
            PriceDto priceDto = new PriceDto();
            priceDto.setChargeTime(payCheme.getChargingTime());
            priceDto.setMoney(MathUtil.setPrecision(payCheme.getMoney()));
            priceDto.setPayCategory(payCheme.getPayCategory());
            priceDto.setPriceId(payCheme.getId());
            priceDto.setPower(payCheme.getPower());
            StringBuffer sbr = new StringBuffer();
            sbr.append("功率").append(payCheme.getMinPower()).append("~").append(payCheme.getMaxPower()).append("W");
            priceDto.setPowerDesc(sbr.toString());
            priceDtoList.add(priceDto);

            Integer payCategory = payCheme.getPayCategory();
            StringBuilder sb = new StringBuilder();
            if (ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getType().equals(payCategory)) {
                sb.append("充").append(payCheme.getChargingTime()).append("小时");
                priceDto.setPayDesc(sb.toString());
            } else if (ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(payCategory)) {
                // 查询月卡账户
                ChargingMonthCardAcct monthCardAcct = map.get(schemeGuid);
                if (monthCardAcct != null) {
                    boolean monthAccount = this.isMonthAccount(monthCardAcct);
                    if (monthAccount) {
                        // 包月用户
                        sb.append("剩余").append(monthCardAcct.getRemainCnt()).append("次");
                        priceDto.setPayDesc(sb.toString());
                        priceDto.setExpireTime(monthCardAcct.getExpireTime());
                        priceDto.setRemainCnt(monthCardAcct.getRemainCnt());
                    } else {
                        // 月卡过期或者次数小于等于0
                        Integer remainCnt = monthCardAcct.getRemainCnt();
                        if (remainCnt < 0) {
                            sb.append("欠费").append(-remainCnt).append("次");
                            priceDto.setRemainCnt(remainCnt);
                        } else if (remainCnt == 0) {
                            sb.append("剩余0次");
                            priceDto.setRemainCnt(0);
                        } else {
                            // 次数清0
                            AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.MONTH_CARD_REMAIN_CNT_ZERO, null,remainCnt);
                            String uuid = UuidUtil.getUuid();
                            operateVo.setGuid(uuid);
                            operateVo.setMonthAccountId(monthCardAcct.getId());
                            ChargingMonthCardAcct param = new ChargingMonthCardAcct();
                            param.setId(monthCardAcct.getId());
                            param.setRemainCnt(0);
                            param.setUpdateTime(new Date());
                            chargingMonthCardAcctService.updateAccount(param,operateVo);

                            sb.append("剩余0次");
                            priceDto.setRemainCnt(0);
                        }

                        priceDto.setPayDesc(sb.toString());
                    }
                } else {
                    priceDto.setPayDesc("剩余0次");
                    priceDto.setRemainCnt(0);
                }
            } else if (ChargeConstant.SchemePayCategory.RECHARGE_FULL.getType().equals(payCategory)) {
                sb.append("一键充满");
                priceDto.setPayDesc(sb.toString());
            }
        }

        dto.setList_price(priceDtoList);
    }

    /**
     * 是否可用的包月账户
     * @param monthCardAcct
     * @return
     */
    private boolean isMonthAccount(ChargingMonthCardAcct monthCardAcct) {
        // 是否包月用户
        boolean flag = false;
        Date expireTime = monthCardAcct.getExpireTime();
        if (expireTime != null) {
            Integer remainCnt = monthCardAcct.getRemainCnt();
            Date current = new Date();
            if (current.before(expireTime) && remainCnt > 0) {
                // 剩余次数大于0，并且在有效期内
                flag = true;
            }
        }
        return flag;
    }

//    /**
//     * 判断用户是否是月卡用户
//     * @param chargingCst
//     * @return
//     */
//    private boolean isMonthUser(ChargingCst chargingCst) {
//        // 是否包月用户
//        boolean flag = false;
//        Date expireTime = chargingCst.getExpireTime();
//        if (expireTime != null) {
//            Integer remainCnt = chargingCst.getRemainCnt();
//            Date current = new Date();
//            if (current.before(expireTime) && remainCnt > 0) {
//                // 剩余次数大于0，并且在有效期内
//                flag = true;
//            }
//        }
//        return flag;
//    }

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
            chargeOnlineDto.setChargingGuid(chargingUseDetailed.getChargingGuid());
            // 查询充值方案
            ChargingPayCheme chargingPayCheme = chargingPayChemeService.queryBySchemeGuid(chargingUseDetailed.getSchemeGuid());
            Integer schemeType = chargingPayCheme.getSchemeType();
            if (schemeType == 1) {
                chargeOnlineDto.setPriceDesc(chargingPayCheme.getMoney() + "元充电方案");
            } else {
                Integer payCategory = chargingPayCheme.getPayCategory();
                StringBuffer sb = new StringBuffer();
                if (ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(payCategory)) {
                    sb.append("月卡1次充");
                    sb.append(chargingPayCheme.getChargingTime());
                    sb.append("小时");
                } else {
                    sb.append(chargingPayCheme.getMoney());
                    sb.append("元充");
                    sb.append(chargingPayCheme.getChargingTime());
                    sb.append("小时");
                }
                chargeOnlineDto.setPriceDesc(sb.toString());
            }

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
        Integer maxPower = chargingPayCheme.getMaxPower();

        String openId = chargeVo.getOpenId();
        ChargingCst chargingCst = chargingCstService.queryByOpenId(openId);

        ChargingDevice chargingDevice = chargingDeviceService.queryByDeviceNo(deviceNo,port);
        String commNo = chargingDevice.getCommNo();
        chargeDto.setIsCharge(1);

        Integer chargingTime = chargingPayCheme.getChargingTime();
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
                String workMode = String.valueOf(ChargeAppConstant.ChargingWay.CHARGING_TIME.getWay());
                Integer measure = ChargeAppConstant.MeasureType.RE_MEASURE.getType();
                String chargingGuid = UuidUtil.getUuid();
                this.sendChargeInstruction(commNo,port,openId,String.valueOf(priceId),String.valueOf(chargingTime),workMode,openMeans,measure,chargingGuid,maxPower);
            }
        } else if(ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(chargingPayCheme.getPayCategory())){
            // 包月用户的处理
            Integer measure = ChargeAppConstant.MeasureType.RE_MEASURE.getType();
            this.monthCardProcess4Charge(chargingPayCheme,chargingDevice,chargingCst,openMeans,measure,openId,chargeDto);
        }
        return chargeDto;
    }

    /**
     * 微信支付宝、月卡处理
     * @param payCheme
     * @param chargingDevice
     * @param chargingCst
     * @param openMeans
     * @param measure
     * @param openId
     * @param chargeDto
     */
    private void monthCardProcess4Charge(ChargingPayCheme payCheme, ChargingDevice chargingDevice,ChargingCst chargingCst,Integer openMeans,Integer measure, String openId,ChargeDto chargeDto) {
        ChargingMonthCardAcct monthCardAcct = chargingMonthCardAcctService.queryUseableMonthCard(chargingCst.getCustomerGuid(), payCheme.getSchemeGuid());
        if (monthCardAcct != null && monthCardAcct.getRemainCnt() > 0) {
            // 发送通电指令
            String workMode = String.valueOf(ChargeAppConstant.ChargingWay.CHARGING_TIME.getWay());
            String chargingGuid = UuidUtil.getUuid();
            Integer maxPower = payCheme.getMaxPower();
            Integer priceId = payCheme.getId();
            this.sendChargeInstruction(chargingDevice.getCommNo(),chargingDevice.getPort(),openId,String.valueOf(priceId),String.valueOf(payCheme.getChargingTime()),workMode,openMeans,measure,chargingGuid,maxPower);
        } else {
            //无剩余次数
            chargeDto.setIsCharge(0);
        }
    }


    @Override
    public ChargeDto weChatReCharge(String deviceNo, String openId, String ip) {
        // 查询设备信息
        String tempDeviceNo = deviceNo.substring(0,7);
        String port = deviceNo.substring(7,deviceNo.length());
        ChargingDevice chargingDevice = chargingDeviceService.queryByDeviceNo(tempDeviceNo,port);
        String commNo = chargingDevice.getCommNo();

        // 查询该设备充电中记录
        ChargingUseDetailed useDetailed = chargingUseDetailedService.queryChargingRecordByPlieGuid(chargingDevice.getChargingPlieGuid());
        if (useDetailed == null) {
            new BusinessException("该设备不存在充电中记录，不允许发起续充电");
        }

        // 查询充电方案
        ChargingPayCheme chargingPayCheme = chargingPayChemeService.queryBySchemeGuid(useDetailed.getSchemeGuid());
        Integer chargingTime = chargingPayCheme.getChargingTime();
        Integer maxPower = chargingPayCheme.getMaxPower();

        // 查询用户信息
        ChargingCst chargingCst = chargingCstService.queryByOpenId(openId);

        ChargeDto chargeDto = new ChargeDto();
        chargeDto.setIsCharge(1);
        Integer openMeans = ChargeAppConstant.OpenMeansConstant.WECHAT.getType();
        Integer payCategory = chargingPayCheme.getPayCategory();
        if(ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getType().equals(payCategory) ||
                ChargeConstant.SchemePayCategory.RECHARGE_FULL.getType().equals(payCategory)) {
            BigDecimal remainAmount = chargingCst.getRemainAmount();
            boolean lessThanZero = MathUtil.isLessThanZero(remainAmount);
            if (lessThanZero) {
                throw new BusinessException("账户已欠费，无法续充");
            }
            //获取用户余额，和传入价格方案的金额进行对比
            boolean greaterEqual = MathUtil.isGreaterEqual(chargingCst.getRemainAmount(),chargingPayCheme.getMoney());
            if (!greaterEqual) {
                //剩余金额小于价格方案的金额，则调取支付接口进行支付，支付成功后，前端再次则就调用充电接口，准备充电
                //获取差价
                BigDecimal payMoney = MathUtil.sub(chargingPayCheme.getMoney(), chargingCst.getRemainAmount());
                chargeDto.setIsCharge(0);
                chargeDto.setPay(MathUtil.setPrecision(payMoney));

                //调用支付接口
                Ajax ajax = wxChargerPayService.chargePay(ChargeAppConstant.PayScene.CHARGE_ELEC.getType(),null,payMoney,ip,chargingPayCheme,chargingCst.getCustomerGuid(),openId);
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
                // 钱够，就直接下发续充电指令
                String workMode = String.valueOf(ChargeAppConstant.ChargingWay.CHARGING_TIME.getWay());
                Integer measure = ChargeAppConstant.MeasureType.APPEND_MEASURE.getType();
                this.sendChargeInstruction(commNo,port,openId,String.valueOf(chargingPayCheme.getId()),String.valueOf(chargingTime),workMode,openMeans,measure,useDetailed.getChargingGuid(),maxPower);
            }
        } else if(ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(payCategory)){
            // 包月用户的处理
            Integer measure = ChargeAppConstant.MeasureType.APPEND_MEASURE.getType();
            this.monthCardProcess4ReCharge(chargingPayCheme,chargingDevice,chargingCst,openMeans,measure,openId,useDetailed.getChargingGuid(),chargeDto);
        }
        return chargeDto;
    }

    /**
     * 微信，支付宝，月卡续充处理
     * @param payCheme
     * @param chargingDevice
     * @param chargingCst
     * @param openMeans
     * @param measure
     * @param openId
     * @param chargeDto
     */
    private void monthCardProcess4ReCharge(ChargingPayCheme payCheme, ChargingDevice chargingDevice,ChargingCst chargingCst,Integer openMeans,Integer measure, String openId,String chargingGuid,ChargeDto chargeDto) {
        ChargingMonthCardAcct monthCardAcct = chargingMonthCardAcctService.queryUseableMonthCard(chargingCst.getCustomerGuid(), payCheme.getSchemeGuid());
        if (monthCardAcct != null) {
            Integer remainCnt = monthCardAcct.getRemainCnt();
            if (remainCnt < 0) {
                throw new BusinessException("月卡账户剩余次数为负，不支持续充");
            }
            // 发送续充电指令
            String workMode = String.valueOf(ChargeAppConstant.ChargingWay.CHARGING_TIME.getWay());
            Integer maxPower = payCheme.getMaxPower();
            Integer priceId = payCheme.getId();
            this.sendChargeInstruction(chargingDevice.getCommNo(),chargingDevice.getPort(),openId,String.valueOf(priceId),String.valueOf(payCheme.getChargingTime()),workMode,openMeans,measure,chargingGuid,maxPower);
        } else {
            //无剩余次数
            chargeDto.setIsCharge(0);
        }
    }

    @Override
    public Map<String, String> chargeIcCardByWeChat(String openId, String cardId, BigDecimal amount,String ip) {
        // 新增IC卡充值记录
        ChargingCard card = chargingCardService.queryByCardId(cardId);
        if (card == null) {
            throw new BusinessException("IC卡不存在");
        }
        Integer cardState = card.getCardState();
        if (ChargeAppConstant.ICcardStatus.INIT.getStatus().equals(cardState)) {
            throw new BusinessException("IC卡未启用");
        }
        if (ChargeAppConstant.ICcardStatus.DISABLE.getStatus().equals(cardState)) {
            throw new BusinessException("IC卡已停用");
        }
        if (ChargeAppConstant.ICcardStatus.SUSPEND.getStatus().equals(cardState)) {
            throw new BusinessException("IC卡已挂失");
        }

        //实例化用户充值记录对象
        ChargingPay chargingPay = new ChargingPay();
        chargingPay.setCustomerGuid("");
        chargingPay.setCardId(cardId);
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
        chargingPay.setPayWay(ChargeAppConstant.PayWayConstant.WECHAT.getType());
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
        String customerGuid = card.getCustomerGuid();
        if (!StringUtils.isEmpty(customerGuid)) {
            throw new BusinessException("IC卡已绑定，不支持充值");
        }

        //实例化用户充值记录对象
        ChargingPay chargingPay = new ChargingPay();
        chargingPay.setCustomerGuid("");
        chargingPay.setCardId(cardId);
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
        chargingPay.setPayWay(ChargeAppConstant.PayWayConstant.ALIPAY.getType());
        chargingPayMapper.insertSelective(chargingPay);

        String tradeNo = alipayService.charge(chargingPay);
        return tradeNo;
    }

    @Override
    public AlipayChargeDto alipaycharge(String alipayUserId,String deviceNo,Integer priceId) {
        // 查询价格方案
        ChargingPayCheme chargingPayCheme = chargingPayChemeService.selectByPrimaryKey(priceId);
        Integer chargingTime = chargingPayCheme.getChargingTime();
        Integer maxPower = chargingPayCheme.getMaxPower();
        String schemeGuid = chargingPayCheme.getSchemeGuid();

        // 查询设备信息
        String realDeviceNo = deviceNo.substring(0,7);
        String port = deviceNo.substring(7,deviceNo.length());
        ChargingDevice chargingDevice = chargingDeviceService.queryByDeviceNo(realDeviceNo,port);
        String commNo = chargingDevice.getCommNo();

        // 查询用户账户信息
        ChargingCst chargingCst = chargingCstService.queryByAlipayUserId(alipayUserId);
        String customerGuid = chargingCst.getCustomerGuid();

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
                orderParam.setSchemeGuid(chargingPayCheme.getSchemeGuid());
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
                String workMode = String.valueOf(ChargeAppConstant.ChargingWay.CHARGING_TIME.getWay());
                Integer measure = ChargeAppConstant.MeasureType.RE_MEASURE.getType();
                String chargingGuid = UuidUtil.getUuid();
                this.sendChargeInstruction(commNo,port,alipayUserId,String.valueOf(priceId),String.valueOf(chargingTime),workMode,openMeans,measure,chargingGuid,maxPower);
            }
        } else if(ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(chargingPayCheme.getPayCategory())){
            // 包月用户的处理
            ChargingMonthCardAcct monthCardAcct = chargingMonthCardAcctService.queryUseableMonthCard(customerGuid, schemeGuid);

            // 判断是否包月用户
            if (monthCardAcct != null && monthCardAcct.getRemainCnt() > 0) {
                // 发送通电指令
                String workMode = String.valueOf(ChargeAppConstant.ChargingWay.CHARGING_TIME.getWay());
                Integer measure = ChargeAppConstant.MeasureType.RE_MEASURE.getType();
                String chargingGuid = UuidUtil.getUuid();
                this.sendChargeInstruction(commNo,port,alipayUserId,String.valueOf(priceId),String.valueOf(chargingTime),workMode,openMeans,measure,chargingGuid,maxPower);
            } else {
                //无剩余次数
                dto.setIsCharge(0);
            }
        }
        return dto;
    }

    @Override
    public AlipayChargeDto alipayRecharge(String alipayUserId, String deviceNo) {
        // 查询设备信息
        String tempDeviceNo = deviceNo.substring(0,7);
        String port = deviceNo.substring(7,deviceNo.length());
        ChargingDevice chargingDevice = chargingDeviceService.queryByDeviceNo(tempDeviceNo,port);
        String commNo = chargingDevice.getCommNo();

        // 查询该设备充电中记录
        ChargingUseDetailed useDetailed = chargingUseDetailedService.queryChargingRecordByPlieGuid(chargingDevice.getChargingPlieGuid());
        if (useDetailed == null) {
            new BusinessException("该设备不存在充电中记录，不允许发起续充电");
        }

        // 查询充电方案
        ChargingPayCheme chargingPayCheme = chargingPayChemeService.queryBySchemeGuid(useDetailed.getSchemeGuid());
        Integer payCategory = chargingPayCheme.getPayCategory();
        Integer chargingTime = chargingPayCheme.getChargingTime();
        Integer maxPower = chargingPayCheme.getMaxPower();
        String schemeGuid = chargingPayCheme.getSchemeGuid();

        // 查询用户信息
        ChargingCst chargingCst = chargingCstService.queryByAlipayUserId(alipayUserId);
        String customerGuid = chargingCst.getCustomerGuid();
        Integer openMeans = ChargeAppConstant.OpenMeansConstant.ALIPAY.getType();

        AlipayChargeDto dto = new AlipayChargeDto();
        dto.setIsCharge(1);
        if(ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getType().equals(payCategory)
                || ChargeConstant.SchemePayCategory.RECHARGE_FULL.getType().equals(payCategory)) {
            BigDecimal remainAmount = chargingCst.getRemainAmount();
            boolean lessThanZero = MathUtil.isLessThanZero(remainAmount);
            if (lessThanZero) {
                throw new BusinessException("账户已欠费，无法续充");
            }
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
                orderParam.setSchemeGuid(chargingPayCheme.getSchemeGuid());
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
                // 钱够，就直接下发续充电指令
                String workMode = String.valueOf(ChargeAppConstant.ChargingWay.CHARGING_TIME.getWay());
                Integer measure = ChargeAppConstant.MeasureType.APPEND_MEASURE.getType();
                this.sendChargeInstruction(commNo,port,alipayUserId,String.valueOf(chargingPayCheme.getId()),String.valueOf(chargingTime),workMode,openMeans,measure,useDetailed.getChargingGuid(),maxPower);
            }
        } else if(ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(chargingPayCheme.getPayCategory())){
            // 包月用户的处理
            ChargingMonthCardAcct monthCardAcct = chargingMonthCardAcctService.queryUseableMonthCard(customerGuid, schemeGuid);
            if (monthCardAcct != null) {
                Integer remainCnt = monthCardAcct.getRemainCnt();
                if (remainCnt < 0) {
                    throw new BusinessException("月卡账户剩余次数为负，不支持续充");
                }
                // 发送续充电指令
                String workMode = String.valueOf(ChargeAppConstant.ChargingWay.CHARGING_TIME.getWay());
                Integer measure = ChargeAppConstant.MeasureType.APPEND_MEASURE.getType();
                this.sendChargeInstruction(commNo,port,alipayUserId,String.valueOf(chargingPayCheme.getId()),String.valueOf(chargingTime),workMode,openMeans,measure,useDetailed.getChargingGuid(),maxPower);
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
    private void sendChargeInstruction(String commNo,String port,String openId,String priceId,String chargeTime,String workMode,Integer openMeans,Integer measure,String chargingGuid,Integer alarmPower) {
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
                workMode,
                openMeans,
                measure,String.valueOf(alarmPower));
        //下发数据
        afn19Object.setChargingGuid(chargingGuid);
        logger.info("下发充电指令AFN19Object=" + JSON.toJSONString(afn19Object));
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
            BigDecimal refundMoney = dto.getRefundMoney();
            boolean greateThanZero = MathUtil.isGreateThanZero(refundMoney);
            Integer payCategory = dto.getPayCategory();
            if (ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getType().equals(payCategory) || ChargeConstant.SchemePayCategory.RECHARGE_FULL.getType().equals(payCategory)) {
                if (greateThanZero) {
                    // 有退款
                    BigDecimal payMoney = MathUtil.sub(dto.getDeductMoney(), refundMoney);
                    String desc = ChargeAppConstant.PayWay.PAY_USER_BALANCE.getPayWayDesc() + payMoney.toString() +  "元";
                    dto.setPayWayDesc(desc);
                } else {
                    String desc = ChargeAppConstant.PayWay.PAY_USER_BALANCE.getPayWayDesc() + dto.getDeductMoney() +  "元";
                    dto.setPayWayDesc(desc);
                }
            } else if(ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(payCategory)) {
                if (greateThanZero) {
                    // 有退款
                    String desc = ChargeAppConstant.PayWay.PAY_MONTH_CNT.getPayWayDesc()  +  "0次";
                    dto.setPayWayDesc(desc);
                } else {
                    String desc = ChargeAppConstant.PayWay.PAY_MONTH_CNT.getPayWayDesc() + dto.getDeductCnt()+  "次";
                    dto.setPayWayDesc(desc);
                }
            } else if (ChargeConstant.SchemePayCategory.IC_RECHARGE.getType().equals(payCategory)) {
                if (greateThanZero) {
                    // 有退款
                    BigDecimal payMoney = MathUtil.sub(dto.getDeductMoney(), refundMoney);
                    String desc = ChargeAppConstant.PayWay.IC_CARD_BALANCE.getPayWayDesc() + payMoney.toString() +  "元";
                    dto.setPayWayDesc(desc);
                } else {
                    String desc = ChargeAppConstant.PayWay.IC_CARD_BALANCE.getPayWayDesc() + dto.getDeductMoney() +  "元";
                    dto.setPayWayDesc(desc);
                }
            }

            dto.setPayWay(payCategory);
            dto.setChargeTime(DateUtil.getTimeDifference(dto.getEndTime(),dto.getStartTime()));

            Integer eventCode = dto.getEventCode();
            if (eventCode != null) {
                if (eventCode == -2) {
                    // 表示异常导致充电结束
                    dto.setIsEvent(1);
                    dto.setIsEventDesc(dto.getSmsContent());
                } else if (eventCode == -1) {
                    // 正常结束
                    dto.setIsEvent(0);
                } else {
                    // 正常情况，代码不可能执行到此处
                    dto.setIsEvent(0);
                    logger.info("代码执行到此处，系统存在bug");
                }
            } else {
                // 这种情况可能出现，开电后立马断电，devlog表可能没数据，使用记录表的dev_log字段可能为空
                dto.setIsEvent(0);
            }

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
    public Integer stopCharge(StopChargeVo stopChargeVo) {
        ChargingUseDetailed useDetailed = chargingUseDetailedService.queryChargingRecordByChargingGuid(stopChargeVo.getChargingGuid());
        if (useDetailed.getState() == 1) {
            // 已停止充电
            return 1;
        }

        ChargingDevice chargingDevice = chargingDeviceService.queryByChargingPlieGuid(useDetailed.getChargingPlieGuid());

        // app停止充电
        String uuid = UuidUtil.getUuid();
        AFN19Object afn19Object = new AFN19Object(uuid,
                "19","999999999",
                "42475858fffffa",
                chargingDevice.getCommNo(),
                "0",
                chargingDevice.getPort(),
                "off",
                null,
                stopChargeVo.getOpenId(),
                "1",
                "0",
                Integer.toString(ChargeAppConstant.ChargingWay.CHARGING_TIME.getWay()),0,0,"");

        //下发数据
        int result =  ClientManager.sendAFN19Msg(afn19Object);
        if (result != 1) {
            throw new BusinessException("停止充电发送中间件指令失败");
        }

        return 0;
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
            messageDto.setMessageContent(chargingDevlog.getSmsContent());
            Integer eventCode = chargingDevlog.getEventCode();
            // 这里eventCode 只可能为-2、-1、1
            messageDto.setMessageType(eventCode);
            String createTime = DateUtil.formatDate(chargingDevlog.getCreateTime());
            messageDto.setCreateTime(createTime);
            if(eventCode == ChargeAppConstant.MessageType.MESSAGE_ALARM.getType()){
                messageDto.setMessageTypeDesc(ChargeAppConstant.MessageType.MESSAGE_ALARM.getTypeDesc());
            }else if(eventCode == ChargeAppConstant.MessageType.MESSAGE_CHARGE_STOP.getType()){
                messageDto.setMessageTypeDesc(ChargeAppConstant.MessageType.MESSAGE_CHARGE_STOP.getTypeDesc());
            } else if (eventCode == ChargeAppConstant.MessageType.MESSAGE_CHARGE_STOP_ERROR.getType()) {
                messageDto.setMessageTypeDesc(ChargeAppConstant.MessageType.MESSAGE_CHARGE_STOP_ERROR.getTypeDesc());
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

        List<ChargingMonthCardAcct> monthCardAccts = chargingMonthCardAcctService.queryUseableMonthCardList(chargingCst.getCustomerGuid());
        if (CollectionUtils.isEmpty(monthCardAccts)) {
            // 不存在月卡账户
            appUserDto.setMonthCardFlag(0);
            appUserDto.setCustomerType(ChargeAppConstant.UserType.USER_COMMON.getType());
            appUserDto.setCustomerTypeDesc(ChargeAppConstant.UserType.USER_COMMON.getTypeDesc());
        } else {
            appUserDto.setMonthCardFlag(1);
            appUserDto.setCustomerType(ChargeAppConstant.UserType.USER_MONTH.getType());
            appUserDto.setCustomerTypeDesc(ChargeAppConstant.UserType.USER_MONTH.getTypeDesc());

            Integer totalCount = 0;
            for (ChargingMonthCardAcct monthCardAcct : monthCardAccts) {
                totalCount += monthCardAcct.getRemainCnt();
            }
            appUserDto.setRemainCnt(totalCount);
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

        ChargingUseDetailed useDetailed = chargingUseDetailedService.queryRecentUseRecordByCustomerGuid(chargingCst.getCustomerGuid());
        if (useDetailed == null) {
            return lastUseRecordDto;
        }

        lastUseRecordDto.setChargeGuid(useDetailed.getChargingGuid());

        ChargingDevice chargingDevice = chargingDeviceService.queryByChargingPlieGuid(useDetailed.getChargingPlieGuid());
        //设置设备编号
        lastUseRecordDto.setDeviceNo(chargingDevice.getDeviceNo());
        //设置开始时间
        String startTime = DateUtil.formatDate(useDetailed.getStartTime());
        lastUseRecordDto.setStartTime(startTime);
        //设置充电状态
        lastUseRecordDto.setState(chargingDevice.getRunState());
        //设置状态描述
        String descByState = ChargeConstant.DeviceRunState.getDescByState(chargingDevice.getRunState());
        lastUseRecordDto.setStateDesc(descByState);

        //设置安装地址
        lastUseRecordDto.setInstallAddr(chargingDevice.getInstallAddr());

        ChargingDevlog devlog = chargingDevlogService.selectByPrimaryKey(useDetailed.getDevLogId());
        Integer eventCode = devlog.getEventCode();
        if (eventCode == -2) {
            // 表示异常导致充电结束
            lastUseRecordDto.setIsEvent(0);
            lastUseRecordDto.setEventCodeDesc(devlog.getSmsContent());
            lastUseRecordDto.setEventCode(devlog.getEventCode());
        } else if (eventCode == -1) {
            // 正常结束
            lastUseRecordDto.setIsEvent(1);
            lastUseRecordDto.setEventCodeDesc(devlog.getSmsContent());
            lastUseRecordDto.setEventCode(devlog.getEventCode());
        } else {
            // 正常情况，代码不可能执行到此处
            lastUseRecordDto.setEventCodeDesc(devlog.getSmsContent());
            lastUseRecordDto.setIsEvent(1);
            lastUseRecordDto.setEventCode(devlog.getEventCode());
            logger.info("代码执行到此处，系统存在bug");
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
        orderParam.setSchemeGuid(chargingPayCheme.getSchemeGuid());
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
    public ChargeCompleteDto chargeComplete(String chargingGuid) {
        ChargingUseDetailed useDetailed = chargingUseDetailedService.queryChargingRecordByChargingGuid(chargingGuid);

        //设置选择充电的套餐类型
        ChargeCompleteDto resultDto = new ChargeCompleteDto();
        this.setCompleteParams(useDetailed,resultDto);
        Integer devLogId = useDetailed.getDevLogId();
        if (devLogId != null) {
            ChargingDevlog devlog = chargingDevlogService.selectByPrimaryKey(devLogId);
            Integer eventCode = devlog.getEventCode();
            if (eventCode == -1) {
                // 手动结束充电
                resultDto.setIsEvent(0);
            } else if (eventCode == -2) {
                // 异常结束
                //设置异常
                resultDto.setIsEvent(1);
                resultDto.setEventCodeDesc(devlog.getSmsContent());
            } else {
                // 正常情况，代码不可能进入此处
                resultDto.setIsEvent(0);
                logger.info("代码执行到此处，系统存在bug");
            }
        } else {
            resultDto.setIsEvent(0);
        }

        return resultDto;
    }

    @Override
    public ChargeCompleteDto chargeMessageComplete(String chargingGuid, Integer messageType) {
        //创建结果对象
        ChargeCompleteDto resultDto = new ChargeCompleteDto();

        //根据charging_guid查询
        ChargingUseDetailed useDetailed = chargingUseDetailedService.queryChargingRecordByChargingGuid(chargingGuid);

        this.setCompleteParams(useDetailed,resultDto);
        //查询异常
        if (messageType == -2) {
            // 异常导致断点
            ChargingDevlog devlog = chargingDevlogService.selectByPrimaryKey(useDetailed.getDevLogId());
            //设置异常
            resultDto.setIsEvent(1);
            resultDto.setEventCodeDesc(devlog.getSmsContent());
        } else if (messageType == -1) {
            // 正常断电
            resultDto.setIsEvent(0);
        } else if (messageType == 1) {
            // 告警，不会断电
            resultDto.setIsEvent(1);
            ChargingDevlog devlog = chargingDevlogService.queryRecentEvent(chargingGuid,messageType);
            resultDto.setEventCodeDesc(devlog.getSmsContent());
        } else {
            // 正常情况，代码不可能执行到此处
            resultDto.setIsEvent(0);
            logger.info("代码执行到此处，系统存在bug");
        }

        return resultDto;
    }

    @Override
    public MonthDetialDto monthlyCardDetial(Integer priceId) {
        ChargingPayCheme payCheme = chargingPayChemeService.selectByPrimaryKey(priceId);

        MonthDetialDto monthDetialDto = new MonthDetialDto();
        monthDetialDto.setChargingCnt(payCheme.getChargingCnt());
        monthDetialDto.setChargingTime(payCheme.getNumMonths() * 30);
        monthDetialDto.setMoney(payCheme.getMoney());
        monthDetialDto.setPriceId(payCheme.getId());
        String powerLimit = payCheme.getMinPower() + "~" + payCheme.getMaxPower() + "W";
        monthDetialDto.setPowerLimit(powerLimit);

        // 查询站点信息
        ChargingProject chargingProject = chargingProjectService.queryByProjectGuid(payCheme.getProjectGuid());
        monthDetialDto.setProjectName(chargingProject.getProjectName());
        return monthDetialDto;
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
            orderParam.setSchemeGuid(chargingPayCheme.getSchemeGuid());
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

        BigDecimal remainAmount = chargingCst.getRemainAmount();
        BigDecimal sub = MathUtil.sub(remainAmount, chargingPayCheme.getMoney());
        boolean flag = MathUtil.isLessThanZero(sub);
        if (flag) {
            // 取相反数
            BigDecimal chargeMoney = sub.negate();

            // 生成支付订单
            CreateOrderParam orderParam = new CreateOrderParam();
            orderParam.setCustomerGuid(chargingCst.getCustomerGuid());
            orderParam.setSchemeGuid(chargingPayCheme.getSchemeGuid());
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
            orderParam.setSchemeGuid(chargingPayCheme.getSchemeGuid());
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
    public void sendAbnormalAlarmMessage(ChargingDevlog chargingDevlog, ChargingDevice chargingDevice, String chargingGuid) {
        if ("0".equals(chargeEndSwitch)) {
            return;
        }

        //根据chargingGuid去查询使用记录
        ChargingUseDetailed useDetailed = chargingUseDetailedService.queryChargingRecordByChargingGuid(chargingDevlog.getChargingGuid());
        String format = "yyyy年MM月dd日 HH:mm:ss";
        String startTime = DateUtil.formatDateByParam(useDetailed.getStartTime(),format);
        String endTime = DateUtil.formatDateByParam(useDetailed.getEndTime(),format);
        Integer openMeans = useDetailed.getOpenMeans();
        if (ChargeAppConstant.OpenMeansConstant.ICCARD.getType().equals(openMeans)) {
            ChargingCard card = chargingCardService.queryByCardId(useDetailed.getOpenNo());
            String customerContact = card.getCustomerContact();
            if (!StringUtils.isEmpty(customerContact)) {
                // 给IC卡绑定者发送短信
                UserAbnormalAlarmParam param = new UserAbnormalAlarmParam();
                param.setCustomerNo(card.getCardId());
                param.setMobilePhone(customerContact);
                param.setChargingGuid(chargingGuid);
                String smsContent = chargingDevlog.getSmsContent();
                param.setEventDesc(smsContent);
                String adviceDesc = this.getAdviceDesc(smsContent);
                param.setAdviceDesc(adviceDesc);
                String deviceNo = chargingDevice.getDeviceNo() + chargingDevice.getPort();
                param.setDeviceNo(deviceNo);
                param.setStartTime(startTime);
                param.setEndTime(endTime);
                BigDecimal refundMoney = useDetailed.getRefundMoney();
                boolean flag = MathUtil.isGreateThanZero(refundMoney);
                if (flag) {
                    BigDecimal sub = MathUtil.sub(useDetailed.getDeductMoney(), refundMoney);
                    param.setAmount(sub + "元");
                } else {
                    param.setAmount(useDetailed.getDeductMoney() + "元");
                }

                param.setRemainAmount(useDetailed.getAfterRemainAmount() + "元");
                param.setAddress(chargingDevice.getInstallAddr());
                aliyunSmsService.sendAbnormalAlarm4User(param);
            }
        } else {
            ChargingCst chargingCst = chargingCstService.queryByCustomerGuid(useDetailed.getCustomerGuid());
            String customerContact = chargingCst.getCustomerContact();
            String customerGuid = chargingCst.getCustomerGuid();

            if(chargingCst.getIsReceiveSms() == 1){
                // 微信或支付宝充电
                Integer payCategory = useDetailed.getPayCategory();
                if (ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(payCategory)) {
                    MonthAbnormalAlarmParam param = new MonthAbnormalAlarmParam();
                    param.setCustomerNo(customerGuid);
                    param.setMobilePhone(customerContact);
                    param.setChargingGuid(chargingGuid);
                    String smsContent = chargingDevlog.getSmsContent();
                    param.setEventDesc(smsContent);
                    String adviceDesc = this.getAdviceDesc(smsContent);
                    param.setAdviceDesc(adviceDesc);
                    String deviceNo = chargingDevice.getDeviceNo() + chargingDevice.getPort();
                    param.setDeviceNo(deviceNo);
                    param.setStartTime(startTime);
                    param.setEndTime(endTime);
                    BigDecimal refundMoney = useDetailed.getRefundMoney();
                    boolean flag = MathUtil.isGreateThanZero(refundMoney);
                    if (flag) {
                        param.setDeductNum("0次");
                    } else {
                        param.setDeductNum(useDetailed.getDeductCnt() + "次");
                    }
                    param.setRemainNum(useDetailed.getAfterRemainCnt() + "次");
                    param.setAddress(chargingDevice.getInstallAddr());
                    aliyunSmsService.sendAbnormalAlarm4Month(param);
                } else {
                    UserAbnormalAlarmParam param = new UserAbnormalAlarmParam();
                    param.setCustomerNo(customerGuid);
                    param.setMobilePhone(customerContact);
                    param.setChargingGuid(chargingGuid);
                    String smsContent = chargingDevlog.getSmsContent();
                    param.setEventDesc(smsContent);
                    String adviceDesc = this.getAdviceDesc(smsContent);
                    param.setAdviceDesc(adviceDesc);
                    String deviceNo = chargingDevice.getDeviceNo() + chargingDevice.getPort();
                    param.setDeviceNo(deviceNo);
                    param.setStartTime(startTime);
                    param.setEndTime(endTime);
                    BigDecimal refundMoney = useDetailed.getRefundMoney();
                    boolean flag = MathUtil.isGreateThanZero(refundMoney);
                    if (flag) {
                        BigDecimal sub = MathUtil.sub(useDetailed.getDeductMoney(), refundMoney);
                        param.setAmount(sub + "元");
                    } else {
                        param.setAmount(useDetailed.getDeductMoney() + "元");
                    }
                    param.setRemainAmount(useDetailed.getAfterRemainAmount() + "元");
                    param.setAddress(chargingDevice.getInstallAddr());
                    aliyunSmsService.sendAbnormalAlarm4User(param);
                }
            }
        }
    }

    private String getAdviceDesc(String smsContent) {
        if ("功率超限".equals(smsContent)) {
            return ",请重新选择大功率充电方案";
        } else {
            return "";
        }
    }

    @Override
    public void sendNormalAlarmMessage(ChargingDevice chargingDevice, ChargingUseDetailed useDetailed) {
        if ("0".equals(chargeEndSwitch)) {
            return;
        }

        String chargingGuid = useDetailed.getChargingGuid();
        String format = "yyyy年MM月dd日 HH:mm:ss";
        String startTime = DateUtil.formatDateByParam(useDetailed.getStartTime(),format);
        String endTime = DateUtil.formatDateByParam(useDetailed.getEndTime(),format);

        Integer openMeans = useDetailed.getOpenMeans();
        if (ChargeAppConstant.OpenMeansConstant.ICCARD.getType().equals(openMeans)) {
            ChargingCard card = chargingCardService.queryByCardId(useDetailed.getOpenNo());
            String customerContact = card.getCustomerContact();
            if (!StringUtils.isEmpty(customerContact)) {
                // 给IC卡绑定者发送短信
                UserNormalAlarmParam param = new UserNormalAlarmParam();
                param.setCustomerNo(card.getCardId());
                param.setMobilePhone(customerContact);
                param.setChargingGuid(chargingGuid);
                String deviceNo = chargingDevice.getDeviceNo() + chargingDevice.getPort();
                param.setDeviceNo(deviceNo);
                param.setStartTime(startTime);
                param.setEndTime(endTime);
                BigDecimal refundMoney = useDetailed.getRefundMoney();
                boolean flag = MathUtil.isGreateThanZero(refundMoney);
                if (flag) {
                    BigDecimal sub = MathUtil.sub(useDetailed.getDeductMoney(), refundMoney);
                    param.setAmount(sub + "元");
                } else {
                    param.setAmount(useDetailed.getDeductMoney() + "元");
                }
                param.setRemainAmount(useDetailed.getAfterRemainAmount() + "元");
                param.setAddress(chargingDevice.getInstallAddr());
                aliyunSmsService.sendNormalAlarm4User(param);
            }
        } else {
            ChargingCst chargingCst = chargingCstService.queryByCustomerGuid(useDetailed.getCustomerGuid());
            String customerContact = chargingCst.getCustomerContact();
            String customerGuid = chargingCst.getCustomerGuid();

            if(chargingCst.getIsReceiveSms()== 1){
                // 微信或支付宝充电
                Integer payCategory = useDetailed.getPayCategory();
                if (ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(payCategory)) {
                    MonthNormalAlarmParam param = new MonthNormalAlarmParam();
                    param.setCustomerNo(customerGuid);
                    param.setMobilePhone(customerContact);
                    param.setChargingGuid(chargingGuid);
                    String deviceNo = chargingDevice.getDeviceNo() + chargingDevice.getPort();
                    param.setDeviceNo(deviceNo);
                    param.setStartTime(startTime);
                    param.setEndTime(endTime);
                    BigDecimal refundMoney = useDetailed.getRefundMoney();
                    boolean flag = MathUtil.isGreateThanZero(refundMoney);
                    if (flag) {
                        param.setDeductNum("0次");
                    } else {
                        param.setDeductNum(useDetailed.getDeductCnt() + "次");
                    }
                    param.setRemainNum(useDetailed.getAfterRemainCnt() + "次");
                    param.setAddress(chargingDevice.getInstallAddr());
                    aliyunSmsService.sendNormalAlarm4Month(param);
                } else {
                    UserNormalAlarmParam param = new UserNormalAlarmParam();
                    param.setCustomerNo(customerGuid);
                    param.setMobilePhone(customerContact);
                    param.setChargingGuid(chargingGuid);
                    String deviceNo = chargingDevice.getDeviceNo() + chargingDevice.getPort();
                    param.setDeviceNo(deviceNo);
                    param.setStartTime(startTime);
                    param.setEndTime(endTime);
                    BigDecimal refundMoney = useDetailed.getRefundMoney();
                    boolean flag = MathUtil.isGreateThanZero(refundMoney);
                    if (flag) {
                        BigDecimal sub = MathUtil.sub(useDetailed.getDeductMoney(), refundMoney);
                        param.setAmount(sub + "元");
                    } else {
                        param.setAmount(useDetailed.getDeductMoney() + "元");
                    }
                    param.setRemainAmount(useDetailed.getAfterRemainAmount() + "元");
                    param.setAddress(chargingDevice.getInstallAddr());
                    aliyunSmsService.sendNormalAlarm4User(param);
                }
            }
        }
    }

    @Override
    public void sendPowerUpMessage(ChargingUseDetailed useDetailed,ChargingDevice chargingDevice,ChargingPayCheme oldScheme,ChargingPayCheme payCheme,String currentPower) {
        if ("0".equals(powerUpSwitch)) {
            return;
        }

        Integer openMeans = useDetailed.getOpenMeans();
        if (ChargeAppConstant.OpenMeansConstant.ICCARD.getType().equals(openMeans)) {
            ChargingCard card = chargingCardService.queryByCardId(useDetailed.getOpenNo());
            String customerContact = card.getCustomerContact();
            String customerGuid = card.getCustomerGuid();
            if (!StringUtils.isEmpty(customerContact)) {
                PowerUpAlarmParam alarmParam = this.getPowerUpAlarmParam(customerGuid, customerContact, currentPower, useDetailed, chargingDevice, oldScheme,payCheme);
                aliyunSmsService.sendPowerUpAlarm(alarmParam);
            }
        } else {
            ChargingCst chargingCst = chargingCstService.queryByCustomerGuid(useDetailed.getCustomerGuid());
            String customerContact = chargingCst.getCustomerContact();
            String customerGuid = chargingCst.getCustomerGuid();

            if(chargingCst.getIsReceiveSms() == 1){
                // 微信或支付宝充电
                PowerUpAlarmParam alarmParam = this.getPowerUpAlarmParam(customerGuid, customerContact, currentPower, useDetailed, chargingDevice, oldScheme,payCheme);
                aliyunSmsService.sendPowerUpAlarm(alarmParam);
            }
        }
    }

    private PowerUpAlarmParam getPowerUpAlarmParam(String customerGuid,String customerContact,String currentPower,ChargingUseDetailed useDetailed,ChargingDevice chargingDevice,ChargingPayCheme oldScheme,ChargingPayCheme payCheme) {
        PowerUpAlarmParam alarmParam = new PowerUpAlarmParam();
        alarmParam.setCustomerNo(customerGuid);
        alarmParam.setMobilePhone(customerContact);
        alarmParam.setChargingGuid(useDetailed.getChargingGuid());
        alarmParam.setCurrentPower(currentPower);
        alarmParam.setPowerUp(String.valueOf(oldScheme.getMaxPower()));

        String deviceNo = chargingDevice.getDeviceNo() + chargingDevice.getPort();
        alarmParam.setDeviceNo(deviceNo);
        String startTime = DateUtil.formatDateByParam(useDetailed.getStartTime(),"MM月dd日 HH:mm:ss");
        alarmParam.setStartTime(startTime);

        Integer payCategory = useDetailed.getPayCategory();
        if (ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(payCategory)) {
            StringBuffer sb = new StringBuffer();
            sb.append("1次");
            sb.append(oldScheme.getChargingTime()).append("小时(");
            sb.append(oldScheme.getMinPower()).append("W~");
            sb.append(oldScheme.getMaxPower()).append("W)");
            String chargeScheme = sb.toString();
            alarmParam.setChargeScheme(chargeScheme);
        } else {
            StringBuffer sb = new StringBuffer();
            sb.append(oldScheme.getMoney()).append("元");
            sb.append(oldScheme.getChargingTime()).append("小时(");
            sb.append(oldScheme.getMinPower()).append("W~");
            sb.append(oldScheme.getMaxPower()).append("W)");
            String chargeScheme = sb.toString();
            alarmParam.setChargeScheme(chargeScheme);
        }

        alarmParam.setAddress(chargingDevice.getInstallAddr());
        alarmParam.setMinute(5);

        // 查询适合提档的新方案
        if (ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(payCategory)) {
            StringBuffer sbr = new StringBuffer();
            sbr.append("1次");
            sbr.append(payCheme.getChargingTime()).append("小时(");
            sbr.append(payCheme.getMinPower()).append("W~");
            sbr.append(payCheme.getMaxPower()).append("W)");
            String schemeDesc = sbr.toString();
            alarmParam.setSchemeDesc(schemeDesc);
        } else {
            StringBuffer sbr = new StringBuffer();
            sbr.append(payCheme.getMoney()).append("元");
            sbr.append(payCheme.getChargingTime()).append("小时(");
            sbr.append(payCheme.getMinPower()).append("W~");
            sbr.append(payCheme.getMaxPower()).append("W)");
            String schemeDesc = sbr.toString();
            alarmParam.setSchemeDesc(schemeDesc);
        }

        return alarmParam;
    }

    @Override
    public void sendWxMessage4OnElec(ChargingDevice chargingDevice, String chargingGuid) {
        // 查询使用记录
        ChargingUseDetailed useDetailed = chargingUseDetailedService.queryChargingRecordByChargingGuid(chargingGuid);

        Integer openMeans = useDetailed.getOpenMeans();
        String openNo = useDetailed.getOpenNo();
        if (ChargeAppConstant.OpenMeansConstant.WECHAT.getType().equals(openMeans)) {
            this.sendWxMsg4OnElec(chargingDevice,useDetailed,openNo);
        } else if (ChargeAppConstant.OpenMeansConstant.ALIPAY.getType().equals(openMeans)) {
            // 查询是否绑定微信
            ChargingCst chargingCst = chargingCstService.queryByCustomerGuid(useDetailed.getCustomerGuid());
            String webcharNo = chargingCst.getWebcharNo();
            if (!StringUtils.isEmpty(webcharNo)) {
                this.sendWxMsg4OnElec(chargingDevice,useDetailed,webcharNo);
            }
        } else if (ChargeAppConstant.OpenMeansConstant.ICCARD.getType().equals(openMeans)) {
            // 查询是否绑定用户，并且该用户绑定了微信
            ChargingCard card = chargingCardService.queryByCardId(openNo);
            String customerGuid = card.getCustomerGuid();
            if (!StringUtils.isEmpty(customerGuid)) {
                ChargingCst chargingCst = chargingCstService.queryByCustomerGuid(customerGuid);
                String webcharNo = chargingCst.getWebcharNo();
                if (!StringUtils.isEmpty(webcharNo)) {
                    this.sendWxMsg4OnElec(chargingDevice,useDetailed,webcharNo);
                }
            }
        }
    }

    /**
     * 开电成功，发送微信模板消息
     * @param chargingDevice
     * @param useDetailed
     */
    private void sendWxMsg4OnElec(ChargingDevice chargingDevice, ChargingUseDetailed useDetailed, String openId) {
        ChargeStartWxParam wxParam = new ChargeStartWxParam();
        String deviceNo = chargingDevice.getDeviceNo() + chargingDevice.getPort();
        wxParam.setDeviceNo(deviceNo);
        wxParam.setOpenId(openId);
        Date startTime = useDetailed.getStartTime();
        String startTimeStr = DateUtil.formatDateByParam(startTime, "MM月dd日 HH:mm");
        wxParam.setStartTime(startTimeStr);
        Integer payCategory = useDetailed.getPayCategory();
        String chargeScheme;
        if (ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(payCategory)) {
            chargeScheme = "1次充" + useDetailed.getChargingTime() + "小时";
        } else {
            chargeScheme = useDetailed.getDeductMoney() + "元充" + useDetailed.getChargingTime() + "小时";
        }
        wxParam.setChargeScheme(chargeScheme);
        String installAddr = chargingDevice.getInstallAddr();
        if (StringUtils.isEmpty(installAddr)) {
            wxParam.setAddress("");
        } else {
            wxParam.setAddress(installAddr);
        }

        Template template = this.getOnElecTemplate(wxParam);
        weixinService.sendWxTemplateMessage(template);
    }


    /**
     * 用户余额充电，构造发送微信模板参数
     * @param param
     * @return
     */
    private Template getOnElecTemplate(ChargeStartWxParam param) {
        Template template = new Template();
        template.setTemplateId(onElecSuccessTemplateId);
        template.setTopColor("#00DD00");
        template.setToUser(param.getOpenId());
        template.setUrl("");

        List<TemplateParam> params = Lists.newArrayList();
        params.add(new TemplateParam("first",onElecSuccessTitle,"#FF3333"));

        // 设备编号
        params.add(new TemplateParam("keyword1",param.getDeviceNo(),"#0044BB"));

        // 开始时间
        params.add(new TemplateParam("keyword2",param.getStartTime(),"#0044BB"));

        // 充电方案
        params.add(new TemplateParam("keyword3",param.getChargeScheme(),"#0044BB"));

        // 充电位置
        params.add(new TemplateParam("keyword4",param.getAddress(),"#0044BB"));

        params.add(new TemplateParam("remark","","#AAAAAA"));
        template.setTemplateParamList(params);
        return template;
    }

    @Override
    public void sendWxMessage4OffElec(ChargingDevice chargingDevice, String chargingGuid,String titleDesc) {
        // 查询使用记录
        ChargingUseDetailed useDetailed = chargingUseDetailedService.queryChargingRecordByChargingGuid(chargingGuid);
        Integer openMeans = useDetailed.getOpenMeans();
        String openNo = useDetailed.getOpenNo();
        if (ChargeAppConstant.OpenMeansConstant.WECHAT.getType().equals(openMeans)) {
            this.sendWxMsg4OffElec(chargingDevice,useDetailed,openNo,titleDesc);
        } else if (ChargeAppConstant.OpenMeansConstant.ALIPAY.getType().equals(openMeans)) {
            // 查询是否绑定微信
            ChargingCst chargingCst = chargingCstService.queryByCustomerGuid(useDetailed.getCustomerGuid());
            String webcharNo = chargingCst.getWebcharNo();
            if (!StringUtils.isEmpty(webcharNo)) {
                this.sendWxMsg4OffElec(chargingDevice,useDetailed,webcharNo,titleDesc);
            }
        } else if (ChargeAppConstant.OpenMeansConstant.ICCARD.getType().equals(openMeans)) {
            // 查询是否绑定用户，并且该用户绑定了微信
            ChargingCard card = chargingCardService.queryByCardId(openNo);
            String customerGuid = card.getCustomerGuid();
            if (!StringUtils.isEmpty(customerGuid)) {
                ChargingCst chargingCst = chargingCstService.queryByCustomerGuid(customerGuid);
                String webcharNo = chargingCst.getWebcharNo();
                if (!StringUtils.isEmpty(webcharNo)) {
                    this.sendWxMsg4OffElec(chargingDevice,useDetailed,webcharNo,titleDesc);
                }
            }
        }
    }

    /**
     * 断电时，发送微信模板消息
     * @param chargingDevice
     * @param useDetailed
     * @param titleDesc
     */
    private void sendWxMsg4OffElec(ChargingDevice chargingDevice, ChargingUseDetailed useDetailed,String openId,String titleDesc) {
        String deviceNo = chargingDevice.getDeviceNo() + chargingDevice.getPort();
        String startTime = DateUtil.formatDateByParam(useDetailed.getStartTime(),"MM月dd日 HH:mm");
        String endTime = DateUtil.formatDateByParam(useDetailed.getEndTime(),"MM月dd日 HH:mm");
        String startEndTime = startTime + "-" + endTime;
        Template template;
        Integer payCategory = useDetailed.getPayCategory();
        if (ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(payCategory)) {
            MonthChargeEndWxParam wxParam = new MonthChargeEndWxParam();
            wxParam.setDeviceNo(deviceNo);
            wxParam.setOpenId(openId);
            wxParam.setTitleDesc(titleDesc);
            wxParam.setStartEndTime(startEndTime);
            BigDecimal refundMoney = useDetailed.getRefundMoney();
            boolean flag = MathUtil.isGreateThanZero(refundMoney);
            if (flag) {
                wxParam.setDeductNum("0次");
            } else {
                wxParam.setDeductNum(useDetailed.getDeductCnt() + "次");
            }

            wxParam.setRemainNum(useDetailed.getAfterRemainCnt() + "次");
            String installAddr = chargingDevice.getInstallAddr();
            if (StringUtils.isEmpty(installAddr)) {
                wxParam.setAddress("");
            } else {
                wxParam.setAddress(installAddr);
            }

            template= getMonthChargeEndTemplate(wxParam);
        } else {
            UserChargeEndWxParam wxParam = new UserChargeEndWxParam();
            wxParam.setDeviceNo(deviceNo);
            wxParam.setOpenId(openId);
            wxParam.setTitleDesc(titleDesc);
            wxParam.setStartEndTime(startEndTime);
            BigDecimal refundMoney = useDetailed.getRefundMoney();
            boolean flag = MathUtil.isGreateThanZero(refundMoney);
            if (flag) {
                BigDecimal sub = MathUtil.sub(useDetailed.getDeductMoney(), refundMoney);
                wxParam.setAmount(sub + "元");
            } else {
                wxParam.setAmount(useDetailed.getDeductMoney() + "元");
            }

            wxParam.setRemainAmount(useDetailed.getAfterRemainAmount() + "元");
            String installAddr = chargingDevice.getInstallAddr();
            if (StringUtils.isEmpty(installAddr)) {
                wxParam.setAddress("");
            } else {
                wxParam.setAddress(installAddr);
            }
            template = getUserChargeEndTemplate(wxParam);
        }

        weixinService.sendWxTemplateMessage(template);
    }

    @Override
    public void sendWxMessage4PowerUp(ChargingUseDetailed useDetailed,ChargingDevice chargingDevice,ChargingPayCheme oldScheme,ChargingPayCheme payCheme,String currentPower) {
        // 查询使用记录
        Integer openMeans = useDetailed.getOpenMeans();
        String openNo = useDetailed.getOpenNo();
        if (ChargeAppConstant.OpenMeansConstant.WECHAT.getType().equals(openMeans)) {
            this.sendWxMsg4PowerUp(useDetailed,chargingDevice,oldScheme,payCheme,currentPower,openNo);
        } else if (ChargeAppConstant.OpenMeansConstant.ALIPAY.getType().equals(openMeans)) {
            // 查询是否绑定微信
            ChargingCst chargingCst = chargingCstService.queryByCustomerGuid(useDetailed.getCustomerGuid());
            String webcharNo = chargingCst.getWebcharNo();
            if (!StringUtils.isEmpty(webcharNo)) {
                this.sendWxMsg4PowerUp(useDetailed,chargingDevice,oldScheme,payCheme,currentPower,webcharNo);
            }
        } else if (ChargeAppConstant.OpenMeansConstant.ICCARD.getType().equals(openMeans)) {
            // 查询是否绑定用户，并且该用户绑定了微信
            ChargingCard card = chargingCardService.queryByCardId(openNo);
            String customerGuid = card.getCustomerGuid();
            if (!StringUtils.isEmpty(customerGuid)) {
                ChargingCst chargingCst = chargingCstService.queryByCustomerGuid(card.getCustomerGuid());
                String webcharNo = chargingCst.getWebcharNo();
                if (!StringUtils.isEmpty(webcharNo)) {
                    this.sendWxMsg4PowerUp(useDetailed,chargingDevice,oldScheme,payCheme,currentPower,webcharNo);
                }
            }
        }
    }

    private void sendWxMsg4PowerUp(ChargingUseDetailed useDetailed,ChargingDevice chargingDevice,ChargingPayCheme oldScheme,ChargingPayCheme payCheme,String currentPower,String openId) {
        PowerUpWxParam wxParam = new PowerUpWxParam();
        wxParam.setOpenId(openId);
        List<Object> lists = Lists.newArrayList();
        lists.add(currentPower + "W");
        lists.add(oldScheme.getMaxPower() + "W");
        Object[] objects = lists.toArray(new Object[lists.size()]);
        String firstDesc = String.format(powerUpFirstContent,objects);
        wxParam.setFirstDesc(firstDesc);
        String deviceNo = chargingDevice.getDeviceNo() + chargingDevice.getPort();
        wxParam.setDeviceNo(deviceNo);

        String startTime = DateUtil.formatDateByParam(useDetailed.getStartTime(),"MM月dd日 HH:mm:ss");
        wxParam.setStartTime(startTime);

        Integer payCategory = useDetailed.getPayCategory();
        if (ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(payCategory)) {
            StringBuffer sb = new StringBuffer();
            sb.append("1次");
            sb.append(oldScheme.getChargingTime()).append("小时(");
            sb.append(oldScheme.getMinPower()).append("W~");
            sb.append(oldScheme.getMaxPower()).append("W)");
            String chargeScheme = sb.toString();
            wxParam.setChargeScheme(chargeScheme);

            StringBuffer sbr = new StringBuffer();
            sbr.append("1次");
            sbr.append(payCheme.getChargingTime()).append("小时(");
            sbr.append(payCheme.getMinPower()).append("W~");
            sbr.append(payCheme.getMaxPower()).append("W)");
            String remark = String.format(powerUpRemarkContent,sbr.toString());
            wxParam.setRemark(remark);
        } else {
            StringBuffer sb = new StringBuffer();
            sb.append(oldScheme.getMoney()).append("元");
            sb.append(oldScheme.getChargingTime()).append("小时(");
            sb.append(oldScheme.getMinPower()).append("W~");
            sb.append(oldScheme.getMaxPower()).append("W)");
            String chargeScheme = sb.toString();
            wxParam.setChargeScheme(chargeScheme);

            StringBuffer sbr = new StringBuffer();
            sbr.append(payCheme.getMoney()).append("元");
            sbr.append(payCheme.getChargingTime()).append("小时(");
            sbr.append(payCheme.getMinPower()).append("W~");
            sbr.append(payCheme.getMaxPower()).append("W)");
            String remark = String.format(powerUpRemarkContent,sbr.toString());
            wxParam.setRemark(remark);
        }

        wxParam.setAddress(chargingDevice.getInstallAddr());
        Template template = this.getPowerUpWxTemplate(wxParam);
        weixinService.sendWxTemplateMessage(template);
    }

    @Override
    public void sendMessage4OffElec(ChargingDevice chargingDevice,String chargingGuid) {
        try {
            // 必须重新查询一遍，之前的逻辑有更新过该对象相关字段，这里发短信会用到
            ChargingUseDetailed useDetailed = chargingUseDetailedService.queryChargingRecordByChargingGuid(chargingGuid);
            Integer devLogId = useDetailed.getDevLogId();
            ChargingDevlog devlog = chargingDevlogService.selectByPrimaryKey(devLogId);
            Integer eventCode = devlog.getEventCode();
            String titleDesc;
            if (eventCode == -2) {
                // 异常导致的断电,发送告警短信
                this.sendAbnormalAlarmMessage(devlog,chargingDevice,chargingGuid);
                String smsContent =  devlog.getSmsContent();
                titleDesc = smsContent + ",充电异常结束";
            } else {
                // 正常断电,发送短信信息
                this.sendNormalAlarmMessage(chargingDevice,useDetailed);
                titleDesc = "充电正常结束";
            }

            // 发送微信模板信息
            this.sendWxMessage4OffElec(chargingDevice,chargingGuid,titleDesc);

            // 更新使用记录消息发送标志
            ChargingUseDetailed param = new ChargingUseDetailed();
            param.setId(useDetailed.getId());
            param.setMessageState(1);
            chargingUseDetailedService.updateByPrimaryKeySelective(param);
        } catch (Exception e) {
            logger.error("发送断电消息异常:",e);
        }
    }

    @Override
    @Transactional
    public void refundMoney(ChargingUseDetailed useDetailed,BigDecimal refundMoney) {
        String chargingGuid = useDetailed.getChargingGuid();
        // 查询充值记录
        Integer openType = useDetailed.getOpenMeans();
        if (ChargeAppConstant.OpenMeansConstant.ICCARD.getType().equals(openType)) {
            // 退IC卡账户
//            ChargingCard card = chargingCardService.queryByCardIdForUpdate(useDetailed.getOpenNo());
//
//            AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.IC_CARD_REFUND, refundMoney,null);
//            operateVo.setGuid(chargingGuid);
//            operateVo.setAccountId(0);
//            operateVo.setCardId(card.getCardId());
//
//            ChargingCard param = new ChargingCard();
//            param.setId(card.getId());
//            BigDecimal totalAmount = card.getRemainAmount().add(useDetailed.getDeductMoney());
//            param.setRemainAmount(totalAmount);
//            param.setUpdateTime(new Date());
//            chargingCardService.updateIcCardAccount(param,operateVo);
//
//            // 更新使用记录
//            ChargingUseDetailed updateParam = new ChargingUseDetailed();
//            updateParam.setId(useDetailed.getId());
//            updateParam.setAfterRemainAmount(totalAmount);
//            updateParam.setRefundState(1);
//            chargingUseDetailedService.updateByPrimaryKeySelective(updateParam);
        } else {
            // 退微信或支付宝账户
            // 查询退款账户
            ChargingCst chargingCst = chargingCstService.queryByCustomerGuidForUpdate(useDetailed.getCustomerGuid());

            Integer payCategory = useDetailed.getPayCategory();
            if (ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(payCategory)) {
                // 退月卡次数
                ChargingMonthCardAcct cardAcct = chargingMonthCardAcctService.queryByParams(useDetailed.getCustomerGuid(), useDetailed.getSchemeGuid());

                AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.CHARGE_REFUND_MONTH, null,useDetailed.getDeductCnt());
                operateVo.setGuid(chargingGuid);
                operateVo.setMonthAccountId(cardAcct.getId());
                ChargingMonthCardAcct param = new ChargingMonthCardAcct();
                param.setId(cardAcct.getId());
                int remainCnt = cardAcct.getRemainCnt() + useDetailed.getDeductCnt();
                param.setRemainCnt(remainCnt);
                param.setUpdateTime(new Date());
                chargingMonthCardAcctService.updateAccount(param,operateVo);

                // 更新使用记录
                ChargingUseDetailed updateParam = new ChargingUseDetailed();
                updateParam.setId(useDetailed.getId());
                updateParam.setAfterRemainCnt(remainCnt);
                updateParam.setRefundState(1);
                chargingUseDetailedService.updateByPrimaryKeySelective(updateParam);
            } else {
                // 退余额
                AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.CHARGE_REFUND_AMOUNT, refundMoney,null);
                operateVo.setGuid(chargingGuid);
                operateVo.setAccountId(chargingCst.getId());

                ChargingCst param = new ChargingCst();
                param.setId(chargingCst.getId());
                param.setUpdateTime(new Date());
                BigDecimal remainAmount = chargingCst.getRemainAmount().add(refundMoney);
                param.setRemainAmount(remainAmount);
                chargingCstService.updateAccountNew(param,operateVo);

                // 更新使用记录
                ChargingUseDetailed updateParam = new ChargingUseDetailed();
                updateParam.setId(useDetailed.getId());
                updateParam.setAfterRemainAmount(remainAmount);
                updateParam.setRefundState(1);
                chargingUseDetailedService.updateByPrimaryKeySelective(updateParam);

                // 更新IC卡下发状态
                BigDecimal temp = chargingCst.getRemainAmount();
                boolean lessThanZero = MathUtil.isLessThanZero(temp);
                boolean greateThanZero = MathUtil.isGreateThanZero(remainAmount);
                if (lessThanZero && greateThanZero) {
                    // 查询账户绑定的IC卡列表
                    List<ChargingCard> chargingCards = chargingCardService.queryAllByCustomerGuid(chargingCst.getCustomerGuid());
                    if (!CollectionUtils.isEmpty(chargingCards)) {
                        Set<String> cardIdSet = Sets.newHashSet();
                        Set<Integer> idSet = Sets.newHashSet();
                        for (ChargingCard chargingCard : chargingCards) {
                            idSet.add(chargingCard.getId());
                            cardIdSet.add(chargingCard.getCardId());
                        }

                        // 批量更新IC卡状态为正常
                        chargingCardService.batchUpdateCardOwe(idSet);

                        // 批量更新IC卡list表state为正常，下发标志为未下发
                        chargingCardListService.updateSendFlag(cardIdSet);
                    }
                }
            }
        }
    }

    @Override
    public void stopChargeElec(ChargingUseDetailed useDetailed) {
        Integer openMeans = useDetailed.getOpenMeans();
        if (ChargeAppConstant.OpenMeansConstant.ICCARD.getType().equals(openMeans)) {
            // 更新充电后剩余金额
            ChargingCard card = chargingCardService.queryByCardId(useDetailed.getOpenNo());

            ChargingUseDetailed param = new ChargingUseDetailed();
            param.setId(useDetailed.getId());
            param.setAfterRemainAmount(card.getRemainAmount());
            chargingUseDetailedService.updateByPrimaryKeySelective(param);
        } else {
            // 微信、支付宝断电 此处逻辑已经在开电成功时计算了afterRemainAmount字段，此处无需在维护
        }
    }

    @Override
    public void splitAccount(String chargingGuid,String projectGuid) {
        logger.info("开始分账逻辑，chargingGuid=" + chargingGuid);
        try {
            ChargingUseDetailed useDetailed = chargingUseDetailedService.queryChargingRecordByChargingGuid(chargingGuid);

            BigDecimal profitable = useDetailed.getProfitable();
            boolean flag = MathUtil.isGreateThanZero(profitable);
            if (!flag) {
                // 盈利小于等于0，不分账
                return;
            }

            if (useDetailed.getSplitAccountState() == 1) {
                return;
            }

            ChargingProject chargingProject = chargingProjectService.queryByProjectGuid(projectGuid);

            // 计算博高抽取金额
            BigDecimal boostAmount = BigDecimal.ZERO;
            BigDecimal profitRatio = chargingProject.getProfitRatio();
            if (!BigDecimal.ZERO.equals(profitRatio)) {
                BigDecimal temp = new BigDecimal("100");
                BigDecimal divide = profitable.multiply(profitRatio).divide(temp);
                boostAmount = MathUtil.setPrecision(divide);
            }

            // 计算给物业分账金额
            BigDecimal amount = MathUtil.sub(useDetailed.getDeductMoney(),boostAmount);
            String customerGuid = chargingProject.getCustomerGuid();
            if (StringUtils.isEmpty(customerGuid)) {
                logger.info("未配置分账用户，不分账");
                return;
            }

            // 加悲观锁
            ChargingCst chargingCst = chargingCstService.queryByCustomerGuidForUpdate(customerGuid);

            // 分账，并记录账户流水
            AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.SPLIT_ACCOUNT, amount,null);
            operateVo.setGuid(chargingGuid);
            operateVo.setAccountId(chargingCst.getId());

            // 账户余额新增
            ChargingCst updateParam = new ChargingCst();
            updateParam.setId(chargingCst.getId());
            BigDecimal remainAmount = chargingCst.getRemainAmount().add(amount);
            updateParam.setRemainAmount(remainAmount);
            updateParam.setUpdateTime(new Date());
            chargingCstService.updateAccountNew(updateParam,operateVo);

            // 新增分账记录
            ChargingSpiltAccount spiltAccount = new ChargingSpiltAccount();
            spiltAccount.setCustomerGuid(customerGuid);
            spiltAccount.setChargingGuid(chargingGuid);
            spiltAccount.setBoostAmount(boostAmount);
            spiltAccount.setAmount(amount);
            spiltAccount.setProfitRatio(profitRatio);
            spiltAccount.setCreateTime(new Date());
            spiltAccountService.insertSelective(spiltAccount);

            // 更新使用记录
            ChargingUseDetailed param = new ChargingUseDetailed();
            param.setId(useDetailed.getId());
            param.setSplitAccountState(1);
            chargingUseDetailedService.updateByPrimaryKeySelective(param);
        } catch (Exception e) {
            logger.error("分账逻辑异常",e);
        }
    }

    /**
     * 支付宝分账处理逻辑
     * @param alipayUserId
     * @param amount
     */
    @Override
    public void split2Alipay(ChargingSpiltAccount account,String alipayUserId,String chargingGuid,BigDecimal amount) {
        logger.info("给支付宝账户分账alipayUserId={},amount={}",alipayUserId,amount);
        try {
            AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",alipayAppId,alipayAppPrivateKey,"json",CHARSET,alipayPublicKey,"RSA2");
            AlipayFundTransToaccountTransferRequest transferRequest = new AlipayFundTransToaccountTransferRequest();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("out_biz_no",chargingGuid);
            jsonObject.put("payee_type","ALIPAY_USERID");
            jsonObject.put("payee_account",alipayUserId);
            jsonObject.put("amount",amount);
            transferRequest.setBizContent(JSON.toJSONString(jsonObject));

            AlipayFundTransToaccountTransferResponse response = alipayClient.execute(transferRequest);
            logger.info("支付宝提现接口返回：" + JSON.toJSONString(response));
            if(response.isSuccess()){
                logger.info(alipayUserId + "第一次调用成功");
                this.doAlipaySuccess(account.getId());
                return;
            }

            String subCode = response.getSubCode();
            String subMsg = response.getSubMsg();
            if ("SYSTEM_ERROR".equals(subCode)) {
                // 采用相同的outBizNo 重发请求
                AlipayFundTransToaccountTransferResponse response2 = alipayClient.execute(transferRequest);
                if (response2.isSuccess()) {
                    logger.info(alipayUserId + "第二次调用成功");
                    this.doAlipaySuccess(account.getId());
                    return;
                }

                String subCode2 = response2.getSubCode();
                String subMsg2 = response2.getSubMsg();
                if ("SYSTEM_ERROR".equals(subCode2)) {
                    // 处理中
                    return;
                }

                // 处理失败
                this.doAlipayError(account.getId(),subCode2,subMsg2);
                return;
            }

            this.doAlipayError(account.getId(),subCode,subMsg);
        } catch (AlipayApiException e) {
            logger.error("调用支付宝转账接口异常",e);
            throw new BusinessException("支付宝提现异常");
        }
    }

    /**
     * 支付宝分账成功操作
     * @param id
     */
    private void doAlipaySuccess(Integer id) {
//        ChargingSpiltAccount account = new ChargingSpiltAccount();
//        account.setId(id);
//        account.setStatus(ChargeAppConstant.WithdrawCashStatus.SUCCESS.getStatus());
//        account.setUpdateTime(new Date());
//        spiltAccountService.updateByPrimaryKeySelective(account);
    }

    /**
     * 提现失败操作
     * @param id
     * @param errorCode
     * @param errorMsg
     */
    private void doAlipayError(Integer id, String errorCode, String errorMsg) {
        // 更新提现记录状态
//        ChargingSpiltAccount account = new ChargingSpiltAccount();
//        account.setId(id);
//        account.setStatus(ChargeAppConstant.WithdrawCashStatus.FAIL.getStatus());
//        account.setErrorCode(errorCode);
//        account.setErrorMsg(errorMsg);
//        account.setUpdateTime(new Date());
//        spiltAccountService.updateByPrimaryKeySelective(account);
    }

    /**
     * 微信分账逻辑处理
     * @param openId
     * @param amount
     */
    @Override
    public void split2Wechat(ChargingSpiltAccount account,String openId,String chargingGuid,BigDecimal amount) {
//        logger.info("给微信账户分账openId={},amount={}",openId,amount);
//        try {
//            // 调用微信企业像个人付款接口
//            String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
//            String nonce_str = StringUtil.getNonceStr();
//            String check_name = "NO_CHECK";//校验用户姓名选项 //	NO_CHECK：不校验真实姓名        FORCE_CHECK：强校验真实姓名
//            String desc = "用户充电完成分账";//企业付款描述信息
//            //获取的是本地的IP地址
//            InetAddress address = null;
//            try {
//                address = InetAddress.getLocalHost();
//            } catch (UnknownHostException e) {
//                logger.error("获取本地ip地址失败",e);
//            }
//            String spbill_create_ip = address.getHostAddress(); //调用接口的终端ip
//
//            Map<String, String> data = new HashMap<>();
//            data.put("mch_appid", appId); //商户号
//            data.put("mchid",mchId);//注意这里没下划线 ,微信支付的时候这里是带下划线的,
//            data.put("nonce_str",nonce_str);
//            data.put("partner_trade_no",chargingGuid);
//            data.put("openid",openId);
//            data.put("check_name",check_name);
//            // 元转分
//            String cashMoney = MathUtil.yuan2Fen(String.valueOf(amount));
//            data.put("amount",cashMoney);
//            data.put("desc",desc);
//            data.put("spbill_create_ip",spbill_create_ip);
//            String sign = WxPaySignatureUtils.signature(data,partnerkey);
//            data.put("sign",sign);
//
//            WxTransfers transfers = new WxTransfers();
//            transfers.setMch_appid(appId);
//            transfers.setMchid(mchId);
//            transfers.setNonce_str(nonce_str);
//            transfers.setPartner_trade_no(chargingGuid);
//            transfers.setOpenid(openId);
//            transfers.setCheck_name(check_name);
//            transfers.setAmount(cashMoney);
//            transfers.setDesc(desc);
//            transfers.setSpbill_create_ip(spbill_create_ip);
//            transfers.setSign(sign);
//
//            //***********************企业付款请求*********************************
//            xmlUtil.xstream().alias("xml", WxTransfers.class);
//            String xml = xmlUtil.xstream().toXML(transfers);
//            String ssl = WxHttpUtil.ssl4Job(url, mchId, xml);
//            if (StringUtils.isEmpty(ssl)) {
//                return;
//            }
//
//            logger.info("调用企业付款到个人零钱接口返回值：" + ssl);
//            Document document = XmlUtil.getDocument(ssl);
//            Map<String, String> map = new HashMap<>();
//            XmlUtil.getMapByDocument(document.getRootElement(), map);
//
//            String return_code = map.get("return_code");
//            String result_code = map.get("result_code");
//            if (!"SUCCESS".equals(return_code)) {
//                // 通信失败
//                return;
//            }
//
//            if(!"SUCCESS".equals(result_code)) {
//                logger.info("接口返回FAIL");
//                String errCode = map.get("err_code");
//                String errCodeDes = map.get("err_code_des");
//                if ("SYSTEMERROR".equals(errCode)) {
//                    // 这种情况，不能理解为转账失败，需要主动查询状态
//                    // 主动查询一次
//                    this.wxOrderQueryProcess(account);
//                } else {
//                    this.doWechatError(account.getId(),errCode,errCodeDes);
//                }
//
//                // 直接返回
//                return;
//            }
//
//            // 成功
//            ChargingSpiltAccount spiltAccount = new ChargingSpiltAccount();
//            spiltAccount.setId(account.getId());
//            spiltAccount.setStatus(ChargeAppConstant.WithdrawCashStatus.SUCCESS.getStatus());
//            spiltAccount.setUpdateTime(new Date());
//            spiltAccountService.updateByPrimaryKeySelective(spiltAccount);
//        } catch (Exception e) {
//            logger.error("微信分账接口异常",e);
//        }
    }

    /**
     * 查询微信企业付款订单状态，然后更新商户分账记录状态
     * @param account
     */
    @Override
    public void wxOrderQueryProcess(ChargingSpiltAccount account) {
//        // 主动查询一次
//        String xmlData = this.queryTransferInfo(account.getPartnerTradeNo());
//        if (StringUtils.isEmpty(xmlData)) {
//            return;
//        }
//
//        Document document2 = XmlUtil.getDocument(xmlData);
//        Map<String, String> queryMap = new HashMap<>();
//        XmlUtil.getMapByDocument(document2.getRootElement(), queryMap);
//        String returnCode = queryMap.get("return_code");
//        String resultcode = queryMap.get("result_code");
//        if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultcode)) {
//            String status = queryMap.get("status");
//            String reason = queryMap.get("reason");
//
//            if ("SUCCESS".equals(status)) {
//                // 成功
//                ChargingSpiltAccount spiltAccount = new ChargingSpiltAccount();
//                spiltAccount.setId(account.getId());
//                spiltAccount.setStatus(ChargeAppConstant.WithdrawCashStatus.SUCCESS.getStatus());
//                spiltAccount.setUpdateTime(new Date());
//                spiltAccountService.updateByPrimaryKeySelective(spiltAccount);
//            } else if("PROCESSING".equals(status)) {
//                // 处理中
//            } else {
//                // 转账失败
//                this.doWechatError(account.getId(),"",reason);
//            }
//        }
    }

    private void doWechatError(Integer id,String errorCode,String errorMsg) {
//        ChargingSpiltAccount spiltAccount = new ChargingSpiltAccount();
//        spiltAccount.setId(id);
//        spiltAccount.setStatus(ChargeAppConstant.WithdrawCashStatus.FAIL.getStatus());
//        spiltAccount.setErrorCode(errorCode);
//        spiltAccount.setErrorMsg(errorMsg);
//        spiltAccount.setUpdateTime(new Date());
//        spiltAccountService.updateByPrimaryKeySelective(spiltAccount);
    }

    /**
     * 根据商户订单号，查询企业付款信息
     * @param partnerTradeNo
     */
    private String queryTransferInfo(String partnerTradeNo) {
        String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo";
        String nonceStr = StringUtil.getNonceStr();
        Map<String, String> data = new HashMap<>();
        data.put("mch_id", mchId);
        data.put("appid",appId);
        data.put("nonce_str",nonceStr);
        data.put("partner_trade_no",partnerTradeNo);

        String sign = WxPaySignatureUtils.signature(data,partnerkey);
        data.put("sign",sign);

        String dataXML = XMLBeanUtils.map2XmlString(data);
        String ssl = WxHttpUtil.ssl4Job(url, mchId, dataXML);
        return ssl;
    }

    /**
     * 用户余额充电，构造发送微信模板参数
     * @param param
     * @return
     */
    private Template getMonthChargeEndTemplate(MonthChargeEndWxParam param) {
        Template template = new Template();
        template.setTemplateId(monthOffElecTemplateId);
        template.setTopColor("#00DD00");
        template.setToUser(param.getOpenId());
        template.setUrl("");

        List<TemplateParam> params = Lists.newArrayList();
        params.add(new TemplateParam("first",param.getTitleDesc(),"#FF3333"));

        // 设备编号
        params.add(new TemplateParam("keyword1",param.getDeviceNo(),"#0044BB"));

        // 起止时间
        params.add(new TemplateParam("keyword2",param.getStartEndTime(),"#0044BB"));

        // 扣减次数
        params.add(new TemplateParam("keyword3",param.getDeductNum(),"#0044BB"));

        // 剩余次数
        params.add(new TemplateParam("keyword4",param.getRemainNum(),"#0044BB"));

        // 充电位置
        params.add(new TemplateParam("keyword5",param.getAddress(),"#0044BB"));

        params.add(new TemplateParam("remark","","#AAAAAA"));
        template.setTemplateParamList(params);
        return template;
    }


    /**
     * 用户余额充电，构造发送微信模板参数
     * @param param
     * @return
     */
    private Template getUserChargeEndTemplate(UserChargeEndWxParam param) {
        Template template = new Template();
        template.setTemplateId(balanceOffElecTemplateId);
        template.setTopColor("#00DD00");
        template.setToUser(param.getOpenId());
        template.setUrl("");

        List<TemplateParam> params = Lists.newArrayList();
        params.add(new TemplateParam("first",param.getTitleDesc(),"#FF3333"));

        // 设备编号
        params.add(new TemplateParam("keyword1",param.getDeviceNo(),"#0044BB"));

        // 起止时间
        params.add(new TemplateParam("keyword2",param.getStartEndTime(),"#0044BB"));

        // 消费金额
        params.add(new TemplateParam("keyword3",param.getAmount(),"#0044BB"));

        // 账户余额
        params.add(new TemplateParam("keyword4",param.getRemainAmount(),"#0044BB"));

        // 充电位置
        params.add(new TemplateParam("keyword5",param.getAddress(),"#0044BB"));

        params.add(new TemplateParam("remark","","#AAAAAA"));
        template.setTemplateParamList(params);
        return template;
    }

    /**
     * 功率超限时，构造发送微信模板消息
     * @param param
     * @return
     */
    private Template getPowerUpWxTemplate(PowerUpWxParam param) {
        Template template = new Template();
        template.setTemplateId(powerUpTemplateId);
        template.setTopColor("#00DD00");
        template.setToUser(param.getOpenId());
        template.setUrl("");

        List<TemplateParam> params = Lists.newArrayList();
        params.add(new TemplateParam("first",param.getFirstDesc(),"#FF3333"));

        // 设备编号
        params.add(new TemplateParam("keyword1",param.getDeviceNo(),"#0044BB"));

        // 开始时间
        params.add(new TemplateParam("keyword2",param.getStartTime(),"#0044BB"));

        // 充电方案
        params.add(new TemplateParam("keyword3",param.getChargeScheme(),"#0044BB"));

        // 充电位置
        params.add(new TemplateParam("keyword4",param.getAddress(),"#0044BB"));

        params.add(new TemplateParam("remark",param.getRemark(),"#AAAAAA"));
        template.setTemplateParamList(params);
        return template;
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
        if (!StringUtils.isEmpty(userPhone)) {
            param.setCustomerContact(userPhone);
        }
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
            BigDecimal totalAmount = chargingCard.getRemainAmount().add(amount);
            card.setRemainAmount(totalAmount);
            card.setUpdateTime(new Date());
            chargingCardService.updateByPrimaryKeySelective(card);

            // 解绑用户的账户余额扣除IC卡预留金额
            ChargingCst chargingCst = chargingCstService.queryByCustomerGuidForUpdate(customerGuid);

            // 更新账户并记录流水
            AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.UNBIND_ICCARD_RECHARGE, amount,null);
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
        cardInfo.setRemainAmount(chargingCard.getRemainAmount());
        cardInfo.setUpdateTime(chargingCard.getUpdateTime());
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
            useInfo.setAfterRemainAmount(useDetailed.getAfterRemainAmount());
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
            payInfo.setAfterRemainAmount(chargingPay.getAfterRemainAmount());
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

        Condition condition = new Condition(ChargingUseDetailed.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.orEqualTo("customerGuid",chargingCst.getCustomerGuid());
        criteria.orEqualTo("customerGuid","");
        criteria.orIsNull("customerGuid");

        Example.Criteria criteria1 = condition.createCriteria();
        criteria1.andEqualTo("openNo",cardId);
        criteria1.andEqualTo("state",1);
        condition.and(criteria1);

        PageHelper.startPage(pageNumber,pageSize,"end_time desc");
        List<ChargingUseDetailed> list = chargingUseDetailedMapper.selectByCondition(condition);
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


        Condition condition = new Condition(ChargingPay.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.orEqualTo("customerGuid",chargingCst.getCustomerGuid());
        criteria.orEqualTo("customerGuid","");
        criteria.orIsNull("customerGuid");

        Example.Criteria criteria1 = condition.createCriteria();
        criteria1.andEqualTo("cardId",cardId);
        criteria1.andEqualTo("payState",1);
        condition.and(criteria1);

        PageHelper.startPage(pageNumber,pageSize,"id desc");
        List<ChargingPay> payList = chargingPayMapper.selectByCondition(condition);
        PageInfo<ChargingPay> pageInfo = new PageInfo(payList);

        List<IcCardPayInfo> icCardPayInfoList = this.getIcCardPayInfoList(payList);
        PageData<IcCardPayInfo> pageData = new PageData<>();
        pageData.setTotal(pageInfo.getTotal());
        pageData.setList(icCardPayInfoList);
        return pageData;
    }

    @Override
    public String queryIcCardContactPhone(String cardId) {
        ChargingCard card = chargingCardService.queryByCardId(cardId);
        if (card == null) {
            throw new BusinessException("IC卡号不存在");
        }

        String customerContact = card.getCustomerContact();
        if (StringUtils.isEmpty(customerContact)) {
            return "";
        }
        return customerContact;
    }

    @Override
    public List<MonthCardAccountInfo> queryUserMonthCardAcctList(Integer appType, String openId) {
        ChargingCst chargingCst;
        if (ChargeAppConstant.AppType.WECHAT.getType().equals(appType)) {
            chargingCst = chargingCstService.queryByOpenId(openId);
        } else {
            chargingCst = chargingCstService.queryByAlipayUserId(openId);
        }

        List<MonthCardAccountInfo> dataList = Lists.newArrayList();
        List<ChargingMonthCardAcct> list = chargingMonthCardAcctService.queryUseableMonthCardList(chargingCst.getCustomerGuid());
        if (CollectionUtils.isEmpty(list)) {
            return dataList;
        }

        Set<String> set = Sets.newHashSet();
        for (ChargingMonthCardAcct cardAcct : list) {
            set.add(cardAcct.getProjectGuid());
        }

        // 批量查询站点信息
        List<ChargingProject> projects = chargingProjectService.batchQuery(set);
        // 分组
        ImmutableMap<String, ChargingProject> projectMap = Maps.uniqueIndex(projects, new Function<ChargingProject, String>() {
            @Nullable
            @Override
            public String apply(@Nullable ChargingProject chargingProject) {
                return chargingProject.getProjectGuid();
            }
        });

        for (ChargingMonthCardAcct cardAcct : list) {
            MonthCardAccountInfo info = new MonthCardAccountInfo();
            ChargingProject chargingProject = projectMap.get(cardAcct.getProjectGuid());
            info.setProjectName(chargingProject.getProjectName());
            info.setPower(cardAcct.getPower());
            String powerLimit = cardAcct.getMinPower() + "~" + cardAcct.getMaxPower() + "W";
            info.setPowerLimit(powerLimit);
            info.setRemainCnt(cardAcct.getRemainCnt());
            info.setExpireTime(cardAcct.getExpireTime());
            dataList.add(info);
        }

        return dataList;
    }

    @Override
    @Transactional
    public void refundAndDeduct4PowerUp(ChargingUseDetailed useDetailed, ChargingPayCheme oldScheme,ChargingPayCheme payCheme) {
        // 更新使用记录
        Integer openType = useDetailed.getOpenMeans();
        if (ChargeAppConstant.OpenMeansConstant.ICCARD.getType().equals(openType)) {
            // 退IC卡账户
            ChargingCard card = chargingCardService.queryByCardIdForUpdate(useDetailed.getOpenNo());

            // 退费,并记录流水
            AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.POWER_UP_IC_CARD_REFUND, useDetailed.getDeductMoney(),null);
            operateVo.setGuid(useDetailed.getChargingGuid());
            operateVo.setAccountId(0);
            operateVo.setCardId(card.getCardId());

            ChargingCard param = new ChargingCard();
            param.setId(card.getId());
            BigDecimal totalAmount = card.getRemainAmount().add(useDetailed.getDeductMoney());
            param.setRemainAmount(totalAmount);
            param.setUpdateTime(new Date());
            chargingCardService.updateIcCardAccount(param,operateVo);

            // 重新扣款，并记录流水
            AccountOperateVo operateVo2 = AccountUtil.getAccountOperateVo(BusinessType.POWER_UP_IC_CARD_DEDUCT, payCheme.getMoney(),null);
            operateVo2.setGuid(useDetailed.getChargingGuid());
            operateVo2.setAccountId(0);
            operateVo2.setCardId(card.getCardId());

            BigDecimal remainAmount = totalAmount.subtract(payCheme.getMoney());
            param.setRemainAmount(remainAmount);
            param.setUpdateTime(new Date());
            chargingCardService.updateIcCardAccount(param,operateVo2);

            // 更新使用记录扣费金额
            ChargingUseDetailed updateParam = new ChargingUseDetailed();
            updateParam.setId(useDetailed.getId());
            updateParam.setDeductMoney(payCheme.getMoney());
            updateParam.setSchemeGuid(payCheme.getSchemeGuid());
            updateParam.setAfterRemainAmount(remainAmount);
            chargingUseDetailedService.updateByPrimaryKeySelective(updateParam);
        } else {
            // 退微信或支付宝账户
            String chargingGuid = useDetailed.getChargingGuid();
            // 查询退款账户
            ChargingCst chargingCst = chargingCstService.queryByCustomerGuidForUpdate(useDetailed.getCustomerGuid());

            Integer payCategory = useDetailed.getPayCategory();
            if (ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(payCategory)) {
                String customerGuid = useDetailed.getCustomerGuid();
                // 查询之前档位月卡账户
                ChargingMonthCardAcct oldCardAcct = chargingMonthCardAcctService.queryByParams(customerGuid, useDetailed.getSchemeGuid());

                // 判断用户是否购买了提档方案的月卡
                ChargingMonthCardAcct cardAcct = chargingMonthCardAcctService.queryByParams(customerGuid, payCheme.getSchemeGuid());
                if (cardAcct != null) {
                    // 1、退之前档位的月卡次数,并记录流水
                    AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.POWER_UP_REFUND_MONTH, null,1);
                    operateVo.setGuid(chargingGuid);
                    operateVo.setMonthAccountId(cardAcct.getId());
                    ChargingMonthCardAcct acctParam = new ChargingMonthCardAcct();
                    acctParam.setId(oldCardAcct.getId());
                    acctParam.setRemainCnt(oldCardAcct.getRemainCnt() + 1);
                    acctParam.setUpdateTime(new Date());
                    chargingMonthCardAcctService.updateAccount(acctParam,operateVo);
                    logger.info("月卡账户退次数成功，用户customerGuid=" + customerGuid + "月卡账户方案schemeGuid=" + useDetailed.getSchemeGuid());

                    // 2、重新扣除提档方案的月卡账户次数,并记录流水
                    AccountOperateVo operateVo2 = AccountUtil.getAccountOperateVo(BusinessType.POWER_UP_DEDUCT_MONTH, null,1);
                    operateVo2.setGuid(chargingGuid);
                    operateVo2.setMonthAccountId(cardAcct.getId());
                    ChargingMonthCardAcct param = new ChargingMonthCardAcct();
                    param.setId(cardAcct.getId());
                    int remainCnt = cardAcct.getRemainCnt() - 1;
                    param.setRemainCnt(remainCnt);
                    param.setUpdateTime(new Date());
                    chargingMonthCardAcctService.updateAccount(param,operateVo2);
                    logger.info("月卡账户扣次数成功，用户customerGuid=" + customerGuid + "月卡账户方案schemeGuid=" + cardAcct.getSchemeGuid());

                    // 更新使用记录表
                    ChargingUseDetailed updateParam = new ChargingUseDetailed();
                    updateParam.setId(useDetailed.getId());
                    // 重新计算月卡，每次扣减金额
                    BigDecimal deductMoney = MathUtil.divide(payCheme.getMoney(), new BigDecimal(payCheme.getChargingCnt()));
                    updateParam.setDeductMoney(MathUtil.setPrecision(deductMoney));
                    updateParam.setAfterRemainCnt(remainCnt);
                    updateParam.setSchemeGuid(payCheme.getSchemeGuid());
                    chargingUseDetailedService.updateByPrimaryKeySelective(updateParam);
                } else {
                    // 当前月卡账户，再扣减一次
                    AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.POWER_UP_DEDUCT_MONTH, null,1);
                    operateVo.setGuid(chargingGuid);
                    operateVo.setMonthAccountId(oldCardAcct.getId());
                    ChargingMonthCardAcct acctParam = new ChargingMonthCardAcct();
                    acctParam.setId(oldCardAcct.getId());
                    int remainCnt = oldCardAcct.getRemainCnt() - 1;
                    acctParam.setRemainCnt(remainCnt);
                    acctParam.setUpdateTime(new Date());
                    chargingMonthCardAcctService.updateAccount(acctParam,operateVo);
                    logger.info("月卡账户再次扣减次数成功，用户customerGuid=" + customerGuid + "月卡账户方案schemeGuid=" + useDetailed.getSchemeGuid());

                    // 更新使用记录表
                    ChargingUseDetailed updateParam = new ChargingUseDetailed();
                    updateParam.setId(useDetailed.getId());

                    // 查询之前方案信息
                    BigDecimal amount = MathUtil.divide(oldScheme.getMoney(), new BigDecimal(oldScheme.getChargingCnt())); // 月卡每次平均消费金额
                    BigDecimal deductMoney = useDetailed.getDeductMoney().add(amount);
                    // 重新计算扣减金额
                    updateParam.setDeductMoney(MathUtil.setPrecision(deductMoney));
                    updateParam.setSchemeGuid(payCheme.getSchemeGuid());
                    updateParam.setDeductCnt(useDetailed.getDeductCnt() + 1);
                    updateParam.setAfterRemainCnt(remainCnt);
                    chargingUseDetailedService.updateByPrimaryKeySelective(updateParam);
                    logger.info("使用记录更新成功,更新参数updateParam=" + JSON.toJSONString(updateParam));
                }
            } else {
                // 1、退剩余金额
                AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.POWER_UP_REFUND_AMOUNT, useDetailed.getDeductMoney(),null);
                operateVo.setGuid(useDetailed.getChargingGuid());
                operateVo.setAccountId(chargingCst.getId());

                ChargingCst param = new ChargingCst();
                param.setId(chargingCst.getId());
                param.setUpdateTime(new Date());
                BigDecimal totalAmount = chargingCst.getRemainAmount().add(useDetailed.getDeductMoney());
                param.setRemainAmount(totalAmount);
                chargingCstService.updateAccountNew(param,operateVo);

                // 2、剩余金额重新扣款，并记录账户变动流水
                operateVo = AccountUtil.getAccountOperateVo(BusinessType.POWER_UP_DEDUCT_AMOUNT, payCheme.getMoney(),null);
                operateVo.setGuid(useDetailed.getChargingGuid());
                operateVo.setAccountId(chargingCst.getId());

                param.setId(chargingCst.getId());
                BigDecimal remainAmount = param.getRemainAmount().subtract(payCheme.getMoney());
                param.setRemainAmount(remainAmount);
                param.setUpdateTime(new Date());
                chargingCstService.updateAccountNew(param,operateVo);

                // 3、更新使用记录扣费金额金额
                ChargingUseDetailed updateParam = new ChargingUseDetailed();
                updateParam.setId(useDetailed.getId());
                updateParam.setDeductMoney(payCheme.getMoney());
                updateParam.setSchemeGuid(payCheme.getSchemeGuid());
                updateParam.setAfterRemainAmount(remainAmount);
                chargingUseDetailedService.updateByPrimaryKeySelective(updateParam);
            }
        }
    }

    @Override
    public List<ChargingIcCardInfo> queryChargingIcCardList(Integer appType, String openId) {
        ChargingCst chargingCst;
        if(ChargeAppConstant.AppType.WECHAT.getType().equals(appType)) {
            chargingCst = chargingCstService.queryByOpenId(openId);
        } else {
            chargingCst = chargingCstService.queryByAlipayUserId(openId);
        }

        List<ChargingIcCardInfo> dataList = Lists.newArrayList();
        List<ChargingUseDetailed> list = chargingUseDetailedService.queryChargingIcCards(chargingCst.getCustomerGuid());
        if (!CollectionUtils.isEmpty(list)) {
            for (ChargingUseDetailed useDetailed : list) {
                ChargingIcCardInfo info = new ChargingIcCardInfo();
                info.setChargingGuid(useDetailed.getChargingGuid());
                info.setCardId(useDetailed.getOpenNo());
                dataList.add(info);
            }
        }
        return dataList;
    }

    @Override
    public Integer queryIcChargingState(String chargingGuid) {
        ChargingUseDetailed useDetailed = chargingUseDetailedService.queryChargingRecordByChargingGuid(chargingGuid);
        return useDetailed.getState();
    }

    @Override
    public ChargeOnlineDto queryIcChargeOnline(String chargingGuid) {
        ChargeOnlineDto chargeOnlineDto = new ChargeOnlineDto();
        ChargingUseDetailed useDetailed = chargingUseDetailedService.queryChargingRecordByChargingGuid(chargingGuid);
        Integer state = useDetailed.getState();
        if (state == 1) {
            // 标识已充电完成
            return chargeOnlineDto;
        }
        chargeOnlineDto.setChargingGuid(useDetailed.getChargingGuid());

        // 查询充值方案
        ChargingPayCheme payCheme = chargingPayChemeService.queryBySchemeGuid(useDetailed.getSchemeGuid());
        StringBuffer sb = new StringBuffer();
        sb.append(payCheme.getMoney());
        sb.append("元充");
        sb.append(payCheme.getChargingTime());
        sb.append("小时");
        chargeOnlineDto.setPriceDesc(sb.toString());

        // 查询充值设备
        ChargingDevice chargingDevice = chargingDeviceService.queryByChargingPlieGuid(useDetailed.getChargingPlieGuid());
        //设置状态
        chargeOnlineDto.setState(chargingDevice.getRunState());
        String descByState = ChargeConstant.DeviceRunState.getDescByState(chargingDevice.getRunState());
        chargeOnlineDto.setStateDesc(descByState);

        //设置设备编号
        chargeOnlineDto.setDeviceNo(chargingDevice.getDeviceNo());
        //设置端口号
        chargeOnlineDto.setPort(Integer.toString(Integer.parseInt(chargingDevice.getPort(),16)));
        //设置已冲电量
        Integer devLogId = useDetailed.getDevLogId();
        if (devLogId != null) {
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
        Date sTime = useDetailed.getStartTime();
        Date eTime = new Date();
        long ycTime = ((eTime.getTime()-sTime.getTime())/1000);
        long syTime = (useDetailed.getChargingTime()*60*60)-((eTime.getTime()-sTime.getTime())/1000);
        chargeOnlineDto.setRemainTime(syTime);
        chargeOnlineDto.setChargeTime(ycTime);
        chargeOnlineDto.setChemeDesc(ChargeConstant.SchemePayCategory.IC_RECHARGE.getDesc());
        return chargeOnlineDto;
    }

    private void setCompleteParams(ChargingUseDetailed useDetailed, ChargeCompleteDto resultDto ){
        BigDecimal refundMoney = useDetailed.getRefundMoney();
        boolean flag = MathUtil.isGreateThanZero(refundMoney);

        Integer payCategory = useDetailed.getPayCategory();
        resultDto.setPayCategory(payCategory);
        if(ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getType().equals(payCategory)){
            resultDto.setPayCategoryDesc(ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getDesc());
            //设置充电金额
            if (flag) {
                BigDecimal sub = MathUtil.sub(useDetailed.getDeductMoney(), refundMoney);
                resultDto.setPayMoney(sub);
            } else {
                resultDto.setPayMoney(useDetailed.getDeductMoney());
            }

            //设置剩余金额
            resultDto.setRemainAmount(useDetailed.getAfterRemainAmount());
        } else if(ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(payCategory)){
            resultDto.setPayCategoryDesc(ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getDesc());
            //设置扣除次数
            if (flag) {
                resultDto.setUseCnt(0);
            } else {
                resultDto.setUseCnt(useDetailed.getDeductCnt());
            }

            //设置剩余次数
            resultDto.setRemainCnt(useDetailed.getAfterRemainCnt());
        } else if(ChargeConstant.SchemePayCategory.RECHARGE_FULL.getType().equals(payCategory)){
            resultDto.setPayCategoryDesc(ChargeConstant.SchemePayCategory.RECHARGE_FULL.getDesc());
            //设置充电金额
            if (flag) {
                BigDecimal sub = MathUtil.sub(useDetailed.getDeductMoney(), refundMoney);
                resultDto.setPayMoney(sub);
            } else {
                resultDto.setPayMoney(useDetailed.getDeductMoney());
            }
            //设置剩余金额
            resultDto.setRemainAmount(useDetailed.getAfterRemainAmount());
        } else if(ChargeConstant.SchemePayCategory.IC_RECHARGE.getType().equals(payCategory)) {
            resultDto.setPayCategoryDesc(ChargeConstant.SchemePayCategory.IC_RECHARGE.getDesc());
            //设置充电金额
            if (flag) {
                BigDecimal sub = MathUtil.sub(useDetailed.getDeductMoney(), refundMoney);
                resultDto.setPayMoney(sub);
            } else {
                resultDto.setPayMoney(useDetailed.getDeductMoney());
            }
            //设置剩余金额
            resultDto.setRemainAmount(useDetailed.getAfterRemainAmount());
        }
        //设置充电时长
        String eDate = DateUtil.formatDate(useDetailed.getEndTime());
        String sDate = DateUtil.formatDate(useDetailed.getStartTime());
        String chargeTime = DateUtil.getTimeDifference(eDate,sDate);
        resultDto.setChargeTime(chargeTime);

        //设置开始充电时间
        resultDto.setStartTime(DateUtil.formatDate(useDetailed.getStartTime()));
        //设置结束充电时间
        resultDto.setEndTime(DateUtil.formatDate(useDetailed.getEndTime()));
        //设置联系电话
        ChargingDevice chargingDevice = chargingDeviceService.queryByChargingPlieGuid(useDetailed.getChargingPlieGuid());
        resultDto.setDeviceNo(chargingDevice.getDeviceNo() + chargingDevice.getPort());
        ChargingProject chargingProject = chargingProjectService.queryByProjectGuid(chargingDevice.getProjectGuid());
        resultDto.setContact(chargingProject.getContactTelphone());
    }
}
