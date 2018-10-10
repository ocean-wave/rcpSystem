package cn.com.cdboost.collect.listener;

import cn.com.cdboost.collect.model.ChargingPayCheme;
import cn.com.cdboost.collect.model.ChargingUseDetailed;
import cn.com.cdboost.collect.service.*;
import com.alibaba.fastjson.JSON;
import com.example.clienttest.client.AFN25DataBackLisener;
import com.example.clienttest.client.AFN25Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 提档成功回调监听
 */
@Component("aFN25ResultDataBackLisener")
public class AFN25ResultDataBackLisener implements AFN25DataBackLisener {

    private static final Logger logger = LoggerFactory.getLogger(AFN25ResultDataBackLisener.class);

    @Autowired
    private ChargingCardService chargingCardService;
    @Autowired
    private ChargingCstService chargingCstService;
    @Autowired
    private ChargingUseDetailedService chargingUseDetailedService;
    @Autowired
    private ChargingPayChemeService chargingPayChemeService;
    @Autowired
    private ChargingMonthCardAcctService monthCardAcctService;

    @Override
    public void onDataBack(AFN25Object afn25Object) {
        logger.info("功率越限，提档成功返回AFN25Object=" + JSON.toJSONString(afn25Object));
        try {
            // 查询提档的使用记录
            ChargingUseDetailed useDetailed = chargingUseDetailedService.queryChargingRecordByChargingGuid(afn25Object.getChargingGUID());
            if (useDetailed.getState() == 1) {
                // 充电已完成，直接返回
                logger.info("充电已完成，直接返回chargingGuid=" + afn25Object.getChargingGUID());
                return;
            }

            // 查询提档的方案
            ChargingPayCheme payCheme = chargingPayChemeService.queryBySchemeGuid(afn25Object.getSchemeGUID());

            // 退费并重新扣款
//            this.refundAndDeduct(useDetailed,payCheme);
        } catch (Exception e) {
            logger.error("提档回调逻辑处理异常：",e);
        }
    }


    /**
     * 提档先退款，然后再重新扣费，并更新使用记录相关信息
     * @param useDetailed
     * @param payCheme
     */
//    private void refundAndDeduct(ChargingUseDetailed useDetailed, ChargingPayCheme payCheme) {
//        // 更新使用记录
//        Integer openType = useDetailed.getOpenMeans();
//        if (ChargeAppConstant.OpenMeansConstant.ICCARD.getType().equals(openType)) {
//            // 退IC卡账户
//            ChargingCard card = chargingCardService.queryByCardIdForUpdate(useDetailed.getOpenNo());
//
//            // 退费,并记录流水
//            AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.POWER_UP_IC_CARD_REFUND, useDetailed.getDeductMoney(),null);
//            operateVo.setGuid(useDetailed.getChargingGuid());
//            operateVo.setAccountId(0);
//
//            ChargingCard param = new ChargingCard();
//            param.setId(card.getId());
//            BigDecimal totalAmount = card.getRemainAmount().add(useDetailed.getDeductMoney());
//            param.setRemainAmount(totalAmount);
//            param.setUpdateTime(new Date());
//            chargingCardService.updateIcCardAccount(param,operateVo);
//
//            // 重新扣款，并记录流水
//            AccountOperateVo operateVo2 = AccountUtil.getAccountOperateVo(BusinessType.POWER_UP_IC_CARD_DEDUCT, payCheme.getMoney(),null);
//            operateVo2.setGuid(useDetailed.getChargingGuid());
//            operateVo2.setAccountId(0);
//
//            BigDecimal remainAmount = totalAmount.subtract(payCheme.getMoney());
//            param.setRemainAmount(remainAmount);
//            param.setUpdateTime(new Date());
//            chargingCardService.updateIcCardAccount(param,operateVo2);
//
//            // 更新使用记录扣费金额
//            ChargingUseDetailed updateParam = new ChargingUseDetailed();
//            updateParam.setId(useDetailed.getId());
//            updateParam.setDeductMoney(payCheme.getMoney());
//            updateParam.setSchemeGuid(payCheme.getSchemeGuid());
//            updateParam.setAfterRemainAmount(remainAmount);
//            chargingUseDetailedService.updateByPrimaryKeySelective(updateParam);
//        } else {
//            // 退微信或支付宝账户
//            String chargingGuid = useDetailed.getChargingGuid();
//            // 查询退款账户
//            ChargingCst chargingCst = chargingCstService.queryByCustomerGuidForUpdate(useDetailed.getCustomerGuid());
//
//            Integer payCategory = useDetailed.getPayCategory();
//            if (ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(payCategory)) {
//                String customerGuid = useDetailed.getCustomerGuid();
//                // 查询之前档位月卡账户
//                ChargingMonthCardAcct oldCardAcct = monthCardAcctService.queryByParams(customerGuid, useDetailed.getSchemeGuid());
//
//                // 判断用户是否购买了提档方案的月卡
//                ChargingMonthCardAcct cardAcct = monthCardAcctService.queryByParams(customerGuid, payCheme.getSchemeGuid());
//                if (cardAcct != null) {
//                    // 1、退之前档位的月卡次数,并记录流水
//                    AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.POWER_UP_REFUND_MONTH, null,1);
//                    operateVo.setGuid(chargingGuid);
//                    operateVo.setMonthAccountId(cardAcct.getId());
//                    ChargingMonthCardAcct acctParam = new ChargingMonthCardAcct();
//                    acctParam.setId(oldCardAcct.getId());
//                    acctParam.setRemainCnt(oldCardAcct.getRemainCnt() + 1);
//                    acctParam.setUpdateTime(new Date());
//                    monthCardAcctService.updateAccount(acctParam,operateVo);
//                    logger.info("月卡账户退次数成功，用户customerGuid=" + customerGuid + "月卡账户方案schemeGuid=" + useDetailed.getSchemeGuid());
//
//                    // 2、重新扣除提档方案的月卡账户次数,并记录流水
//                    AccountOperateVo operateVo2 = AccountUtil.getAccountOperateVo(BusinessType.POWER_UP_DEDUCT_MONTH, null,1);
//                    operateVo2.setGuid(chargingGuid);
//                    operateVo2.setMonthAccountId(cardAcct.getId());
//                    ChargingMonthCardAcct param = new ChargingMonthCardAcct();
//                    param.setId(cardAcct.getId());
//                    param.setRemainCnt(cardAcct.getRemainCnt() - 1);
//                    param.setUpdateTime(new Date());
//                    monthCardAcctService.updateAccount(param,operateVo2);
//                    logger.info("月卡账户扣次数成功，用户customerGuid=" + customerGuid + "月卡账户方案schemeGuid=" + cardAcct.getSchemeGuid());
//
//                    // 更新使用记录表
//                    ChargingUseDetailed updateParam = new ChargingUseDetailed();
//                    updateParam.setId(useDetailed.getId());
//                    // 重新计算月卡，每次扣减金额
//                    BigDecimal deductMoney = MathUtil.divide(payCheme.getMoney(), new BigDecimal(payCheme.getChargingCnt()));
//                    updateParam.setDeductMoney(MathUtil.setPrecision(deductMoney));
//                    updateParam.setSchemeGuid(payCheme.getSchemeGuid());
//                    chargingUseDetailedService.updateByPrimaryKeySelective(updateParam);
//                } else {
//                    // 当前月卡账户，再扣减一次
//                    AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.POWER_UP_DEDUCT_MONTH, null,1);
//                    operateVo.setGuid(chargingGuid);
//                    operateVo.setMonthAccountId(oldCardAcct.getId());
//                    ChargingMonthCardAcct acctParam = new ChargingMonthCardAcct();
//                    acctParam.setId(oldCardAcct.getId());
//                    acctParam.setRemainCnt(oldCardAcct.getRemainCnt() - 1);
//                    acctParam.setUpdateTime(new Date());
//                    monthCardAcctService.updateAccount(acctParam,operateVo);
//                    logger.info("月卡账户再次扣减次数成功，用户customerGuid=" + customerGuid + "月卡账户方案schemeGuid=" + useDetailed.getSchemeGuid());
//
//                    // 更新使用记录表
//                    ChargingUseDetailed updateParam = new ChargingUseDetailed();
//                    updateParam.setId(useDetailed.getId());
//
//                    // 查询之前方案信息
//                    ChargingPayCheme oldScheme = chargingPayChemeService.queryBySchemeGuid(useDetailed.getSchemeGuid());
//                    BigDecimal amount = MathUtil.divide(oldScheme.getMoney(), new BigDecimal(oldScheme.getChargingCnt())); // 月卡每次平均消费金额
//                    BigDecimal deductMoney = useDetailed.getDeductMoney().add(amount);
//                    // 重新计算扣减金额
//                    updateParam.setDeductMoney(MathUtil.setPrecision(deductMoney));
//                    updateParam.setSchemeGuid(payCheme.getSchemeGuid());
//                    chargingUseDetailedService.updateByPrimaryKeySelective(updateParam);
//                    logger.info("使用记录更新成功,更新参数updateParam=" + JSON.toJSONString(updateParam));
//                }
//            } else {
//                // 1、退剩余金额
//                AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.POWER_UP_REFUND_AMOUNT, useDetailed.getDeductMoney(),null);
//                operateVo.setGuid(useDetailed.getChargingGuid());
//                operateVo.setAccountId(chargingCst.getId());
//
//                ChargingCst param = new ChargingCst();
//                param.setId(chargingCst.getId());
//                param.setUpdateTime(new Date());
//                BigDecimal totalAmount = chargingCst.getRemainAmount().add(useDetailed.getDeductMoney());
//                param.setRemainAmount(totalAmount);
//                chargingCstService.updateAccountNew(param,operateVo);
//
//                // 2、剩余金额重新扣款，并记录账户变动流水
//                operateVo = AccountUtil.getAccountOperateVo(BusinessType.POWER_UP_DEDUCT_AMOUNT, payCheme.getMoney(),null);
//                operateVo.setGuid(useDetailed.getChargingGuid());
//                operateVo.setAccountId(chargingCst.getId());
//
//                param.setId(chargingCst.getId());
//                BigDecimal remainAmount = param.getRemainAmount().subtract(payCheme.getMoney());
//                param.setRemainAmount(remainAmount);
//                param.setUpdateTime(new Date());
//                chargingCstService.updateAccountNew(param,operateVo);
//
//                // 3、更新使用记录扣费金额金额
//                ChargingUseDetailed updateParam = new ChargingUseDetailed();
//                updateParam.setId(useDetailed.getId());
//                updateParam.setDeductMoney(payCheme.getMoney());
//                updateParam.setSchemeGuid(payCheme.getSchemeGuid());
//                updateParam.setAfterRemainAmount(remainAmount);
//                chargingUseDetailedService.updateByPrimaryKeySelective(updateParam);
//            }
//        }
//    }
}
