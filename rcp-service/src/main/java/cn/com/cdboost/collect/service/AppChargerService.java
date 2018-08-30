package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.dto.RegisterDto;
import cn.com.cdboost.collect.dto.chargerApp.*;
import cn.com.cdboost.collect.dto.chargerApp.vo.*;
import cn.com.cdboost.collect.dto.param.EventQueryParam;
import cn.com.cdboost.collect.dto.response.IcCardInfo;
import cn.com.cdboost.collect.dto.response.IcCardPayInfo;
import cn.com.cdboost.collect.dto.response.IcCardUseInfo;
import cn.com.cdboost.collect.dto.response.WithdrawCashInfo;
import cn.com.cdboost.collect.model.ChargingCard;
import cn.com.cdboost.collect.model.ChargingCst;
import cn.com.cdboost.collect.model.ChargingDevlog;
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
     * 停止充电
     * @return
     */
    void stopCharge(StopChargeVo stopChargeVo);


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
     * @param openId
     * @param chargingGuid
     * @return
     */
    ChargeCompleteDto chargeComplete(String openId, String chargingGuid);

    /**
     * 消息列表详情
     * @param chargingGuid
     * @return
     */
    ChargeCompleteDto chargeMessageComplete(String chargingGuid, Integer messageType);

    /**
     * 月卡购买详情页面
     * @param openId
     * @return
     */
    List<MonthDetialDto> monthlyCardDetial(String openId);
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

    /**
     * 发送警告信息
     * @param chargingDevlog
     * @param address
     * @param createTime
     */
    void sendAlarmMessage(ChargingDevlog chargingDevlog, String address, String createTime);

    /**
     * 发送断电信息
      * @param chargingGuid
     * @param address
     * @param createTime
     */
    void sendStopMessage(String chargingGuid,String address,String createTime);

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
}
