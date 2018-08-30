package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.ChargeUserDetail;
import cn.com.cdboost.collect.dto.response.FeePayDetailDto;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.FeePay;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xzy
 * @desc 费控-充值记录信息表
 * @create 2017/7/3 0003
 **/
public interface FeePayService extends BaseService<FeePay>{


    ElectResponseParamDto electDetail(String customerNo);
    ElectDetailResponseDto electDetailList(ElectDetailParamDto electDetailParamDto);
    // 批量设置充值无效
    void batchDisableFeePay(List<String> customerNos, List<String> cnos);

    // 删除用户时，作废该用户相关的FeePay记录
    int disableFeePayByCustomerNo(String customerNo);

    // 删除用户的某个设备时，作废该该设备FeePay记录
    int disableFeePayByCustomerNoAndCno(String customerNo,String cno);

    // 查询用户的最后一次购电记录
    List<QueryUserDTO> queryUser(RechargeUserQueryVo queryVo);

    // 根据用户唯一标识，查询充值用户相关信息
    ChargeUserDetail queryChargeUserDetail(String customerNo);

    // 查询用户的IC卡基础信息
    QueryUserPayDTO queryUserPay(String customerNo, String deviceNo);

    // 查询最后一次充值记录
    FeePay queryLastRecord(String customerNo);

    // 充值记录查询
    FeePayRecordInfo queryByProc(RechargeRecordQueryVo queryVo) throws BusinessException;

    // 充值缴费功能
    void rechargePayment(FeePay feePay, Long userId, String meterUserNo);

    BigDecimal remoteRecharge(RemoteRechargeVo rechargeVo);

    /*
     * 更新充值记录的状态
     * 当writeMeter为1的时候，表示写入
     * 当writeMeter为2的时候，表示更新
     */
    void updateWriteMeter(String payGuid, int writeMeter, int payModel, long userId,Integer flag);

    // 保存读取的IC卡记录(并更新fee_acct表)
    void saveReadCardAndUpdateFeeAcct(FeeReadCardParam cardParam, Long userId);

    // 用户信息模糊查询
    List<FuzzyQueryDTO> fuzzyQuery(UserFuzzyQueryVo queryVo);

    // 根据表计户号查询用户缴费信息
    FeePayRecordInfo searchByMeterUserNo(FeePayDetailParam param);

    // 充值缴费功能
    int rechargeStatus(String payGuid);

    //实时召测剩余与充值次数
    void sendReadAmountAndCntCmd(long userID, String deviceCno, String queueGuid) throws BusinessException;

    //读取实时召测剩余金额与充值次数指令执行状态
    int getReadStatus(String queueGuid);

    // 通过payGuid查询缴费信息
    FeePayDetailDto searchFeePayByPayGuid(String payGuid);

    // 设置充值无效
    void disablePay(DisablePayVo disablePayVo);
    // 查询最后五次充值记录
    List<FeePay> getLastNFeePay(String cno, int n);

    // 按条件查询
    FeePay queryByParams(String payGuid,String cno);
}
