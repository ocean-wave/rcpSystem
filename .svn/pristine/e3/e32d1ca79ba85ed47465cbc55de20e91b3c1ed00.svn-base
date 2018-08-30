package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.constant.FeeControlConstant;
import cn.com.cdboost.collect.dto.AcctDetailInfo;
import cn.com.cdboost.collect.dto.AcctInfo;
import cn.com.cdboost.collect.dto.FeeChangeICCardInfo;
import cn.com.cdboost.collect.dto.param.ChangeICCardParamVo;
import cn.com.cdboost.collect.dto.param.CustomerReAcctVo;
import cn.com.cdboost.collect.dto.param.FeeAcctGetQueryVo;
import cn.com.cdboost.collect.dto.param.IcCardAddParamVo;
import cn.com.cdboost.collect.enums.DeviceType;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.*;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.DateUtil;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zc
 * @desc IC卡接口实现类
 * @create 2017-07-05 17:48
 **/
@Service("iCCardService")
public class ICCardServiceImpl implements ICCardService {

    @Autowired
    private CustomerDevMapService customerDevMapService;
    @Autowired
    private FeeChangeIcCardService feeChangeIcCardService;
    @Autowired
    private FeePayService feePayService;
    @Autowired
    private FeeAcctService feeAcctService;
    @Autowired
    private UserService userService;

    @Override
    public List<AcctInfo> query(FeeAcctGetQueryVo queryVo) {
        List<AcctInfo> lists = feeAcctService.query(queryVo);
        return lists;
    }

    @Override
    public AcctDetailInfo queryDetail(String customerNo, String cno) {
        return feeAcctService.queryDetail(customerNo, cno);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void add(IcCardAddParamVo addParam, Long currentUserId) throws BusinessException{
        String cno = addParam.getCno();
        String customerNo = addParam.getCustomerNo();
        FeeAcct acctParam = new FeeAcct();
        acctParam.setCno(cno);
        acctParam.setCustomerNo(customerNo);
        FeeAcct acct = feeAcctService.selectOne(acctParam);
        if (acct == null) {
            // 第一次开户，新增客户IC卡信息
            FeeAcct feeAcct = new FeeAcct();
            feeAcct.setCustomerNo(customerNo);
            feeAcct.setCno(cno);
            feeAcct.setUpdateParam(131);
            feeAcct.setCurTranfRto(addParam.getCurTranfRto());
            feeAcct.setVolTranfRto(addParam.getVolTranfRto());
            feeAcct.setAlertFee1(addParam.getAlertFee1());
            feeAcct.setAlertFee2(addParam.getAlertFee2());
            feeAcct.setCreateUserId(currentUserId);
            feeAcct.setCreateTime(new Date());
            feeAcct.setUpdateUserId(currentUserId);
            feeAcct.setUpdateTime(new Date());
            feeAcct.setErrorCnt(0);
            feeAcct.setMeterType(Integer.parseInt(addParam.getMeterType()));
            feeAcctService.addFeeAcct(feeAcct);
        }

        // 查询客户关联表信息
        CustomerDevMap param = new CustomerDevMap();
        param.setCno(cno);
        param.setCustomerNo(customerNo);
        CustomerDevMap customerDevMap = customerDevMapService.selectOne(param);

        // 2. 充值缴费操作
        FeePay feePay = new FeePay();
        feePay.setCustomerNo(addParam.getCustomerNo());
        feePay.setCno(cno);
        feePay.setDeviceType(DeviceType.ELECTRIC_METER.getCode());
        feePay.setPayCount(addParam.getPayCount());
        feePay.setPayDate(new Date());
        feePay.setPayMoney(addParam.getPayMoney());
        feePay.setPayment(addParam.getPayment());
        // 购电卡充值
        feePay.setPayModel(FeeControlConstant.PayModelValue.IC_CARD.getCode());
        feePay.setCreateUserId(currentUserId);
        feePay.setWriteMeter(0); // 购电金额是否下发APP充值需要下发，购电卡充值不需要要下发 0- 未写入 1-写入
        feePay.setWitMetTime(new Date());

        feePay.setPayGuid(addParam.getPayGuid());
        feePay.setIsValid(1);
        feePay.setAdjusAmount(feePay.getPayment().subtract(feePay.getPayMoney()));
        feePay.setMeterUserNo(customerDevMap.getMeterUserNo());
        String serialNum = DateUtil.getSerialNum();
        feePay.setSerialNum(serialNum);
        feePayService.insertSelective(feePay);

        // 更新开户状态
        CustomerDevMap updateMap = new CustomerDevMap();
        updateMap.setId(customerDevMap.getId());
        updateMap.setIsAccount(FeeControlConstant.OpenAccountStatus.CREATE_IC_CARD_FAIL.getStatus());
        updateMap.setAcctDatetime(new Date());
        // 剩余金额=充值金额+前一次剩余金额
//        BigDecimal totalAmount = customerDevMap.getTotalAmount();
        BigDecimal totalAmount = BigDecimal.ZERO;
        totalAmount = totalAmount.add(addParam.getPayment());
//        updateMap.setTotalAmount(totalAmount);
//        updateMap.setPayCount(1);
        updateMap.setAdjusAmount(customerDevMap.getInitAmount());
        customerDevMapService.updateByPrimaryKeySelective(updateMap);
    }

    @Override
    public List<FeeChangeICCardInfo> queryChangeICCard(String cno, String customerNo) {
        return feeChangeIcCardService.queryChangeICCardByCustomerNo(cno, customerNo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Map addChangeICCard(ChangeICCardParamVo paramVo) {
        String cno = paramVo.getCno();
        String customerNo = paramVo.getCustomerNo();
        FeeChangeIcCard feeChangeICCard = new FeeChangeIcCard();
        feeChangeICCard.setCustomerNo(customerNo);
        feeChangeICCard.setChangeRemark(paramVo.getChangeRemark());
        feeChangeICCard.setChangeUserId(Long.valueOf(paramVo.getUserId()));
        feeChangeICCard.setChangeTime(new Date());
        feeChangeICCard.setRepetType(2);
        feeChangeICCard.setCno(cno);

        // 1. 添加IC卡补卡记录
        feeChangeIcCardService.insertSelective(feeChangeICCard);

        FeeAcct feeAcct = feeAcctService.query(cno,customerNo);
        // 2. 更新IC卡信息
        FeeAcct updateParam = new FeeAcct();
        updateParam.setId(feeAcct.getId());
        updateParam.setUpdateUserId(Long.valueOf(paramVo.getUserId()));
        updateParam.setUpdateTime(new Date());
        feeAcctService.updateByPrimaryKeySelective(updateParam);
        Map map= Maps.newHashMap();
        User user = userService.selectByPrimaryKey(feeChangeICCard.getChangeUserId().intValue());
        map.put("chargeUserName",user.getUserName());
        map.put("createDate",DateUtil.formatDate(feeChangeICCard.getChangeTime()));
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(String cno,String customerNo, Long updateUserId,String payGuid, Integer flag) {
        CustomerDevMap param = new CustomerDevMap();
        param.setCustomerNo(customerNo);
        param.setCno(cno);
        CustomerDevMap customerDevMap = customerDevMapService.selectOne(param);

        // flag = 1表示补卡开户流程调用
        if (flag == 1) {
            // 添加IC卡补卡记录
            FeeChangeIcCard feeChangeICCard = new FeeChangeIcCard();
            feeChangeICCard.setCustomerNo(customerNo);
            feeChangeICCard.setChangeRemark("补卡开户");
            feeChangeICCard.setChangeUserId(updateUserId);
            feeChangeICCard.setChangeTime(new Date());
            feeChangeICCard.setCno(cno);
            feeChangeICCard.setRepetType(1);
            feeChangeIcCardService.insertSelective(feeChangeICCard);
        }

        // 更新开户状态
        CustomerDevMap updateMap = new CustomerDevMap();
        updateMap.setId(customerDevMap.getId());
        updateMap.setIsAccount(FeeControlConstant.OpenAccountStatus.OPEN_ACCOUNT_SUCCESS.getStatus());
        updateMap.setWirteCardTime(new Date());
        customerDevMapService.updateByPrimaryKeySelective(updateMap);

        FeeAcct feeAcct = feeAcctService.query(cno,customerNo);
        // 更新IC卡信息
        FeeAcct updateParam = new FeeAcct();
        updateParam.setId(feeAcct.getId());
        updateParam.setUpdateUserId(updateUserId);
        updateParam.setUpdateTime(new Date());
        updateParam.setReWrtTime(new Date());
        feeAcctService.updateByPrimaryKeySelective(updateParam);

        // 更新充值缴费(IC卡充值payModel永远为2)
        feePayService.updateWriteMeter(payGuid,1, FeeControlConstant.PayModelValue.IC_CARD.getCode(),updateUserId,flag);
    }

    @Override
    @Transactional
    public void reAdd(CustomerReAcctVo acctVo) throws BusinessException{
        feeChangeIcCardService.reAdd(acctVo);
        int result = Integer.parseInt(acctVo.getResult());
        if(result == 1){
            // 成功
        }else if(result == 2){
            throw new BusinessException("数据已经提交");
        }else if(result == 3) {
            throw new BusinessException("guid重复");
        }else {
            throw new BusinessException("result=" + result);
        }
    }

    @Override
    public AcctDetailInfo queryAccount(String customerNo, String cno, int flag) {
        return feeAcctService.queryAccount(customerNo, cno, flag);
    }

    @Override
    public AcctDetailInfo queryRepectPay(String customerNo, String cno, String payGuid) {
        return feeAcctService.queryRepectPay(customerNo, cno, payGuid);
    }
}
