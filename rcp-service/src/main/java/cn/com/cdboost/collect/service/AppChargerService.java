package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.dto.RegisterDto;
import cn.com.cdboost.collect.dto.chargerApp.*;
import cn.com.cdboost.collect.dto.chargerApp.vo.*;
import cn.com.cdboost.collect.dto.param.EventQueryParam;
import cn.com.cdboost.collect.dto.response.*;
import cn.com.cdboost.collect.model.*;
import cn.com.cdboost.collect.vo.PageData;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AppChargerService {

    WxBaseInfoDto getBaseInfoNew(WxBaseInfoVo wxBaseInfoVo);

    /**
     * 获取充电实时数据
     * @param chargeOnlineVo
     * @return
     */
    ChargeOnlineDto getChargeOnline(ChargeOnlineVo chargeOnlineVo);

    /**
     * 充电
     * @param chargeVo
     * @return
     */
    ChargeDto charge(ChargeVo chargeVo);

    ChargeDto weChatReCharge(String deviceNo,String openId,String ip);

    /**
     * 通过微信给IC卡充值
     * @param openId
     * @param cardId
     * @param amount
     * @param ip
     * @return
     */
    Map<String, String> chargeIcCardByWeChat(String openId, String cardId, BigDecimal amount, String ip);

    /**
     * 通过支付宝给IC卡充值
     * @param userId
     * @param cardId
     * @param amount
     * @return
     */
    String chargeIcCardByAlipay(String userId,String cardId,BigDecimal amount);

    AlipayChargeDto alipaycharge(String alipayUserId,String deviceNo,Integer priceId);


    AlipayChargeDto alipayRecharge(String alipayUserId,String deviceNo);

    /**
     * 获取充电记录
     * @param historyVo
     * @return
     */
    List<ChargeHistoryDto> chargeHistory(HistoryVo historyVo);

    /**
     * 获取充值记录
     * @param historyVo
     * @return
     */
    List<ChargeMoneyHistoryDto> chargeMoneyHistory(HistoryVo historyVo);

    /**
     * 停止充电,1标识停止充电成功，0标识停止中，前端等待websocket通知
     * @return
     */
    Integer stopCharge(StopChargeVo stopChargeVo);


    /**
     * 获取消息通知
     * @param param
     * @return
     */
    List<MessageDto> alarm(EventQueryParam param);

    /**
     * 获取用户
     * @param appType
     * @param openId
     * @return
     */
    AppUserDto getAppUser(Integer appType,String openId);

    /**
     * 更新用户
     * @param chargingCst
     * @return
     */
    Integer updateCustomer(ChargingCst chargingCst);

    /**
     * 获取客户使用记录总数
     * @param historyVo
     * @return
     */
    Long queryUseRecordTotal(HistoryVo historyVo);

    /**
     * 获取客户充值总数
     * @param historyVo
     * @return
     */
    Long queryChargeTotal(HistoryVo historyVo);

    /**
     * 获取用户曲线接口
     * @param openId
     * @param deviceNo
     * @return
     */
    CurveListNDto deviceCurve(String openId, String deviceNo);

    /**
     * 开电时的事物操作
     * @param deviceNo
     * @return
     */
    Integer startCharge(String deviceNo);

    /**
     * 断电时的事物操作
     * @param chargingGuid
     * @return
     */
    void stopCharge(String chargingGuid);

    /**
     * 获取用户最近一次使用记录
     * @param appType
     * @param openId
     * @return
     */
    LastUseRecordDto getLateUseRecord(Integer appType,String openId);

    /**
     * 获取用户充值方案选择列表
      * @param openId
     * @return
     */
    ChargeIntecerListDto chargeList(Integer appType,String openId);

    /**
     * 客户充值接口
     * @param request
     * @param openId
     * @param priceId
     * @return
     */
    ChargeIntecerDto chargeMoney(HttpServletRequest request, String ip, String openId, String priceId);

    // 支付宝账户余额充值
    String chargeMoneyByAlipay(String alipayUserId, String priceId);

    /**
     * 充电完成弹出接口
     * @param chargingGuid
     * @return
     */
    ChargeCompleteDto chargeComplete(String chargingGuid);

    /**
     * 消息列表详情
     * @param chargingGuid
     * @return
     */
    ChargeCompleteDto chargeMessageComplete(String chargingGuid, Integer messageType);

    /**
     * 月卡购买详情页面
     * @param priceId
     * @return
     */
    MonthDetialDto monthlyCardDetial(Integer priceId);
    /**
     * 用户注册
     * @param appType
     * @param openId
     * @param phoneNumber
     * @param verificationCode
     * @return
     */
    void register(Integer appType,String openId,String phoneNumber,String nickName,String verificationCode);

    // 微信月卡支付
    MonthChargeDto monthOfCharge(String openId,String ip,Integer priceId);

    // 支付宝月卡支付
    MonthChargeDto4Alipay monthOfChargeByAlipay(String alipayUserId,Integer priceId);

    /**
     * 更新用户接收短信标志
     * @param appType
     * @param openId
     * @param isReceiveSms
     * @return
     */
    Integer isReceiveSms(Integer appType,String openId,Integer isReceiveSms);

    /**
     * 用户修改绑定的手机号
     * @param appType
     * @param openId
     * @param phoneNumber
     * @param verificationCode
     * @return
     */
    RegisterDto updatePhoneNumber(Integer appType,String openId,String phoneNumber,String verificationCode);

    // 发送异常关电，告警信息
    void sendAbnormalAlarmMessage(ChargingDevlog chargingDevlog, ChargingDevice chargingDevice, String chargingGuid);

    // 发送正常关电，告警信息
    void sendNormalAlarmMessage(ChargingDevice chargingDevice,ChargingUseDetailed useDetailed);

    // 发送功率超限短信消息
    void sendPowerUpMessage(ChargingUseDetailed useDetailed,ChargingDevice chargingDevice,ChargingPayCheme oldScheme,ChargingPayCheme payCheme,String currentPower);

    // 开电成功，发送微信模板消息
    void sendWxMessage4OnElec(ChargingDevice chargingDevice,String chargingGuid);

    // 关电成功，发送微信模板消息
    void sendWxMessage4OffElec(ChargingDevice chargingDevice,String chargingGuid,String titleDesc);

    // 功率超限，发送微信模板消息
    void sendWxMessage4PowerUp(ChargingUseDetailed useDetailed,ChargingDevice chargingDevice,ChargingPayCheme oldScheme,ChargingPayCheme payCheme,String currentPower);

    // 断电时，发送短信，微信消息
    void sendMessage4OffElec(ChargingDevice chargingDevice,String chargingGuid);

    // 5分钟内断电，退费操作
    void refundMoney(ChargingUseDetailed useDetailed, BigDecimal refundMoney);

    // 停止充电逻辑
    void stopChargeElec(ChargingUseDetailed useDetailed);

    // 断电后，给物业分账
    void splitAccount(String chargingGuid,String projectGuid);

    // 给微信账户分账
    void split2Wechat(ChargingSpiltAccount account,String openId,String chargingGuid,BigDecimal amount);

    // 给支付宝账户分账
    void split2Alipay(ChargingSpiltAccount account,String alipayUserId,String chargingGuid,BigDecimal amount);

    // 微信企业付款订单查询，并更新商户分账订单状态
    void wxOrderQueryProcess(ChargingSpiltAccount account);

    /**
     * 进入提现页面，查询相关信息
     * @param appType
     * @param openId
     * @return
     */
    WithdrawCashInfo queryCashInfo(Integer appType,String openId);

    /**
     * IC卡绑定操作
     * @param chargingCard
     * @param customerGuid
     */
    void bindIcCard(ChargingCard chargingCard,String customerGuid,String userPhone);

    void unBindIcCard(ChargingCard chargingCard, String customerGuid, Integer isReserve, BigDecimal amount);

    // 根据cardId查询该卡最近3条使用记录，和最近3条支付记录
    IcCardInfo queryIcCardInfo(String cardId);

    // 分页查询IC卡使用记录信息
    PageData<IcCardUseInfo> queryIcCardUseInfo(String cardId,Integer appType,String openId, Integer pageNumber, Integer pageSize);

    // 分页查询IC卡支付记录信息
    PageData<IcCardPayInfo> queryIcCardPayInfo(String cardId,Integer appType,String openId, Integer pageNumber, Integer pageSize);

    // 根据cardId查询绑定的手机号
    String queryIcCardContactPhone(String cardId);

    List<MonthCardAccountInfo> queryUserMonthCardAcctList(Integer appType,String openId);

    // 提档先退款，然后再重新扣费，并更新使用记录相关信息
    void refundAndDeduct4PowerUp(ChargingUseDetailed useDetailed, ChargingPayCheme oldScheme,ChargingPayCheme payCheme);

    // 查询用户绑定的充电中的IC卡列表信息
    List<ChargingIcCardInfo> queryChargingIcCardList(Integer appType,String openId);

    // 查询充电状态
    Integer queryIcChargingState(String chargingGuid);

    // 查询充电中页面相关数据
    ChargeOnlineDto queryIcChargeOnline(String chargingGuid);
}
