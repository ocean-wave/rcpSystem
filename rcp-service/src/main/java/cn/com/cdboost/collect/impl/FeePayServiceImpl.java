package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.constant.CustomerInfoConstant;
import cn.com.cdboost.collect.constant.FeeControlConstant;
import cn.com.cdboost.collect.dao.CostCalculateMapper;
import cn.com.cdboost.collect.dao.FeePayMapper;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.CancelOffResponse;
import cn.com.cdboost.collect.dto.response.ChargeUserDetail;
import cn.com.cdboost.collect.dto.response.FeePayDetailDto;
import cn.com.cdboost.collect.dto.response.UserFuzzyQueryInfo;
import cn.com.cdboost.collect.enums.DeviceType;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.*;
import cn.com.cdboost.collect.model.CustomerInfo;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.util.DateUtil;
import cn.com.cdboost.collect.util.MathUtil;
import cn.com.cdboost.collect.util.UuidUtil;
import com.example.clienttest.client.AbsBaseDataObject;
import com.example.clienttest.client.ResultInfo;
import com.example.clienttest.clientfuture.ClientManager;
import com.google.common.collect.Lists;
import net.sf.json.util.JSONUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xzy
 * @desc 费控-充值记录信息表
 * @create 2017/7/3 0003
 **/
@Service("feePayService")
public class FeePayServiceImpl extends BaseServiceImpl<FeePay> implements FeePayService {
    private static final Logger logger = LoggerFactory.getLogger(FeePayServiceImpl.class);
    @Autowired
    private UserLogService userLogService;
    @Autowired
    private FeePayMapper feePayMapper;
    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private CustomerInfoCostService customerInfoCostService;
    @Autowired
    private InstructService instructService;
    @Autowired
    private DeviceMeterParamService deviceMeterParamService;
    @Autowired
    private FeeAcctService feeAcctService;
    @Autowired
    private FeeReadCardService feeReadCardService;
    @Autowired
    private MeterReadQueueService meterReadQueueService;
    @Autowired
    private CostCalculateMapper costCalculateMapper;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private CustomerDevMapService customerDevMapService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private UserService userService;

    @Override
    public ElectResponseParamDto electDetail(String customerNo) {
        String balanceTime = sysConfigService.queryByConfigName("balanceTime").getConfigValue();
        BigDecimal currentMonthFee=new BigDecimal(0);
        BigDecimal currentMonthTotal=new BigDecimal(0);
        String currentDateTime = DateUtil.CurrentDateTime();
        String currentMonthBalanceDayTime = DateUtil.getCurrentMonthBalanceDayTime(Integer.valueOf(balanceTime));
        String lastMonthBalanceTime = DateUtil.getLastMonthBalanceDayTime(Integer.valueOf(balanceTime));
        String nextMonthBalanceDayTime = DateUtil.getNextMonthBalanceDayTime(Integer.valueOf(balanceTime));
        int i = DateUtil.compareDate(currentDateTime, currentMonthBalanceDayTime);
        if(i==-1){
            List<CostCalculate> costCalculates = costCalculateMapper.electDetail(customerNo, lastMonthBalanceTime, currentMonthBalanceDayTime);
            for (CostCalculate costCalculate : costCalculates) {
                if(costCalculate.getCalcMoney()==null|| costCalculate.getCalcMoney().intValue()==-1){
                    costCalculate.setCalcMoney(BigDecimal.valueOf(0));
                }
                if(costCalculate.getEqValue()==null|| costCalculate.getEqValue().intValue()==-1){
                    costCalculate.setEqValue(BigDecimal.valueOf(0));
                }
                currentMonthFee=currentMonthFee.add(costCalculate.getCalcMoney());
                currentMonthTotal=currentMonthTotal.add(costCalculate.getEqValue());
            }
        }else{
            List<CostCalculate> costCalculates = costCalculateMapper.electDetail(customerNo, currentMonthBalanceDayTime, nextMonthBalanceDayTime);
            for (CostCalculate costCalculate : costCalculates) {
                if(costCalculate.getCalcMoney()==null|| costCalculate.getCalcMoney().intValue()==-1){
                    costCalculate.setCalcMoney(BigDecimal.valueOf(0));
                }
                if(costCalculate.getEqValue()==null|| costCalculate.getEqValue().intValue()==-1){
                    costCalculate.setEqValue(BigDecimal.valueOf(0));
                }
                currentMonthFee=currentMonthFee.add(costCalculate.getCalcMoney());
                currentMonthTotal=currentMonthTotal.add(costCalculate.getEqValue());
            }
        }
        CustomerInfo customerInfo = customerInfoService.queryByCustomerNo(customerNo);
        ElectResponseParamDto electResponseParamDto=new ElectResponseParamDto();
        electResponseParamDto.setCurrentMonthFee(currentMonthFee);
        electResponseParamDto.setCurrentMonthTotal(currentMonthTotal);
        CustomerInfoCost customerInfoCost = customerInfoCostService.queryByCustomerNo(customerInfo.getCustomerNo());
        BigDecimal remainAmount = customerInfoCost.getRemainAmount();
        electResponseParamDto.setRemainAmount(remainAmount.setScale(2,BigDecimal.ROUND_HALF_UP));
        return electResponseParamDto;
    }
    @Override
    public ElectDetailResponseDto electDetailList(ElectDetailParamDto electDetailParamDto) {
        ElectDetailResponseDto electDetailResponseDto=new ElectDetailResponseDto();
        List<ElectDetailDto> electDetailDtos = costCalculateMapper.electDetailList(electDetailParamDto);
        for (ElectDetailDto electDetailDto : electDetailDtos) {
            if("1".equals(electDetailDto.getCalc_data_type())){
                electDetailDto.setCalc_data_type("实时扣费");
            }
            if("0".equals(electDetailDto.getCalc_data_type())){
                electDetailDto.setCalc_data_type("冻结扣费");
            }
        }
        electDetailResponseDto.setList(electDetailDtos);
        SummryDto summryDto=new SummryDto();
        summryDto.setElectricTotal(electDetailParamDto.getElectricTotal());
        summryDto.setFeePayMoney(electDetailParamDto.getFeePayMoney());
        electDetailResponseDto.setStatistics(summryDto);
        return electDetailResponseDto;
    }

    @Override
    @Transactional
    public void batchDisableFeePay(List<String> customerNos, List<String> cnos) {
        FeePay updateParam = new FeePay();
        updateParam.setIsValid(0);
        updateParam.setRemark("删除表");
        Condition condition = new Condition(FeePay.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("customerNo",customerNos);
        criteria.andIn("cno",cnos);
        feePayMapper.updateByConditionSelective(updateParam,condition);
    }

    @Override
    public int disableFeePayByCustomerNo(String customerNo) {
        FeePay updateParam = new FeePay();
        updateParam.setIsValid(0);
        updateParam.setRemark("删除用户");
        Condition condition = new Condition(FeePay.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("customerNo",customerNo);
        return feePayMapper.updateByConditionSelective(updateParam,condition);
    }

    @Override
    public int disableFeePayByCustomerNoAndCno(String customerNo, String cno) {
        FeePay updateParam = new FeePay();
        updateParam.setIsValid(0);
        updateParam.setRemark("删除设备");
        Condition condition = new Condition(FeePay.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("customerNo",customerNo);
        criteria.andEqualTo("cno",cno);
        return feePayMapper.updateByConditionSelective(updateParam,condition);
    }

    // 查询用户的最后一次购电记录
    @Override
    public List<QueryUserDTO> queryUser(RechargeUserQueryVo queryVo) {
        return customerInfoService.queryUser(queryVo);
    }

    @Override
    public ChargeUserDetail queryChargeUserDetail(String customerNo) {
        ChargeUserDetail detail = new ChargeUserDetail();
        // 查询用户基本信息
        CustomerInfo customerInfo = customerInfoService.queryByCustomerNo(customerNo);
        BeanUtils.copyProperties(customerInfo,detail);

        // 查询用户费用信息表
        CustomerInfoCost cost = customerInfoCostService.queryByCustomerNo(customerNo);
        BeanUtils.copyProperties(cost,detail);
        // 设置剩余金额，最后一次计算时间
        detail.setRemainAmount(MathUtil.setPrecision(cost.getRemainAmount()));
        detail.setAmountCollectTime(cost.getUpdateTime());

        // 查询组织
        Org org = orgService.queryByOrgNo(customerInfo.getOrgNo());
        detail.setOrgName(org.getOrgName());

        // 查询最后一次购电记录
        FeePay feePay = this.queryLastRecord(customerNo);
        if (feePay != null) {
            detail.setPayDate(feePay.getPayDate());
            detail.setPayCount(feePay.getPayCount());
            detail.setPayGuid(feePay.getPayGuid());
            detail.setPayMoney(feePay.getPayMoney());
            detail.setWriteMeter(feePay.getWriteMeter());
            detail.setSerialNum(feePay.getSerialNum());
            String descByCode = FeeControlConstant.PayMethod.getDescByCode(feePay.getPayMethod());
            detail.setPayMethod(descByCode);

            // 查询售电人员
            Long createUserId = feePay.getCreateUserId();
            detail.setChargeUserName("自助缴费");
            if (createUserId != null && createUserId != 0) {
                Integer id = createUserId.intValue();
                User user = userService.selectByPrimaryKey(id);
                detail.setChargeUserName(user.getUserName());
            }
        }

        // 查询用户合闸时，用户表先关信息
        Map<String, CancelOffResponse> map = feePayMapper.queryCancelOffMap(customerNo);
        List<OnOffMeterVo> meters = Lists.newArrayList();
        for (Map.Entry<String, CancelOffResponse> entry : map.entrySet()) {
            CancelOffResponse response = entry.getValue();
            OnOffMeterVo meterVo = new OnOffMeterVo();
            meterVo.setCustomerNo(customerNo);
            meterVo.setDeviceCno(response.getCno());
            meterVo.setJzqCno(response.getJzqCno());
            meters.add(meterVo);
        }
        detail.setMeters(meters);

        return detail;
    }

    @Override
    public FeePay queryLastRecord(String customerNo) {
        Condition condition = new Condition(FeePay.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("customerNo",customerNo);
        condition.setOrderByClause("id desc limit 1");
        List<FeePay> feePays = feePayMapper.selectByCondition(condition);
        if (CollectionUtils.isEmpty(feePays)) {
            return null;
        }

        FeePay feePay = feePays.get(0);
        return feePay;
    }

    @Override
    public QueryUserPayDTO queryUserPay(String customerNo, String deviceNo) {
        return customerInfoService.queryUserPay(customerNo,deviceNo);
    }


    @Override
    public FeePayRecordInfo queryByProc(RechargeRecordQueryVo queryVo) throws BusinessException{
        String propertyName = queryVo.getPropertyName();
        if (propertyName == null) {
            propertyName = "";
        }
        String customerNo = queryVo.getCustomerNo();
        if (customerNo == null) {
            customerNo = "";
        }
        // 如果设备类型为空，则给一个默认的设备类型
        String deviceType = queryVo.getDeviceType();
        if (deviceType == null) {
            deviceType = DeviceType.ELECTRIC_METER.getCode();
        }
        String customerContact = queryVo.getCustomerContact();
        if (customerContact == null) {
            customerContact = "";
        }
        String customerAddr = queryVo.getCustomerAddr();
        if (customerAddr == null) {
            customerAddr = "";
        }
        String customerName = queryVo.getCustomerName();
        if (customerName == null) {
            customerName = "";
        }
        String meterUserNo = queryVo.getMeterUserNo();
        if (meterUserNo == null) {
            meterUserNo = "";
        }
        String writeMeter = queryVo.getWriteMeter();
        if (writeMeter == null) {
            writeMeter = "";
        }
        String payModel = queryVo.getPayModel();
        if (payModel == null) {
            payModel = "";
        }
        String deviceNo = queryVo.getDeviceNo();
        if (deviceNo == null) {
            deviceNo = "";
        }
        Integer pageSize = queryVo.getPageSize();
        if (pageSize == null) {
            pageSize = 20;
        }
        Integer pageNumber = queryVo.getPageNumber();
        if (pageNumber == null) {
            pageNumber = 1;
        }

        BaseParam param = new BaseParam();
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
        try {
            param.setSdate(sdf.parse(queryVo.getSdate()));
            param.setEdate(sdf.parse(queryVo.getEdate()));
        } catch (ParseException e) {
            String msg = "查询时间转换异常";
            logger.error("msg",e);
            throw new BusinessException(msg);
        }
        param.setCustomerNo(customerNo);
        param.setDeviceType(deviceType);
        param.setCustomerContact(customerContact);
        param.setCustomerAddr(customerAddr);
        param.setCustomerName(customerName);
        param.setMeterUserNo(meterUserNo);
        param.setWriteMeter(writeMeter);
        param.setPayModel(payModel);
        param.setDeviceNo(deviceNo);
        param.setPageSize(pageSize);
        param.setPageNumber(pageNumber);
        param.setPropertyName(propertyName);
        param.setUserId(queryVo.getUserId());
        param.setPayMethod(queryVo.getPayMethod()==null?"":queryVo.getPayMethod());
        List<QueryProcDTO> list = feePayMapper.queryByProc(param);

        // 统计数据json
        FeePayStatisticInfo statisticInfo = new FeePayStatisticInfo();
        statisticInfo.setPayment(param.getSumPayment());
        statisticInfo.setAdjustAmount(param.getSumAdjust());
        statisticInfo.setPayMoney(param.getSumPayMoney());

        FeePayRecordInfo info = new FeePayRecordInfo();
        info.setList(list);
        info.setStatistics(statisticInfo);
        // 把获取返回的条数放入
        queryVo.setTotal(Long.valueOf(param.getRowCount()));
        return info;
    }


    // 充值缴费功能
    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=RuntimeException.class)
    public void rechargePayment(FeePay feePay, Long userId, String meterUserNo){
        // 获取payModel
        Integer payModel = feePay.getPayModel();
        String cno = feePay.getCno();

        // 充值缴费调用存储过程的传入参数
        RechargePaymentParam paymentParam = new RechargePaymentParam();
        paymentParam.setPayMoney(String.valueOf(feePay.getPayMoney()));
        paymentParam.setPayCount(String.valueOf(feePay.getPayCount()));
        paymentParam.setPayModel(String.valueOf(payModel));
        paymentParam.setCustomerNo(feePay.getCustomerNo());
        paymentParam.setCno(cno);
        paymentParam.setPayGuid(feePay.getPayGuid());
        paymentParam.setCreateUserId(String.valueOf(userId));
        paymentParam.setPayMethod(feePay.getPayMethod());
        paymentParam.setSerialNum(feePay.getSerialNum());

        // 充值方式判断   1为远程充值，2为普通充值
        if(FeeControlConstant.RechargeType.COMMON_RECHARGE.getCode().equals(payModel)){
            try{
                // 普通充值
                rechargePayment(paymentParam);
              /*  userLogService.create(1, 1,"充值缴费","customerNo","","设备["+CNoUtil.getDeviceNoByCno(cno)+"]购电卡充值缴费["+paymentParam.getPayMoney()+"]元,第["+paymentParam.getPayCount()+"]次充值成功" , JSONUtils.valueToString(paymentParam));*/
            }
            catch (Exception e){
                userLogService.create(1, 1,"充值缴费","customerNo","","设备["+CNoUtil.getDeviceNoByCno(cno)+"]购电卡充值缴费["+paymentParam.getPayMoney()+"]元,第["+paymentParam.getPayCount()+"]次充值失败" , JSONUtils.valueToString(paymentParam));
                throw e;
            }
        } else {
            // 远程充值
            BigDecimal remainAmount;
            try {
                remainAmount = rechargePayment(paymentParam);
                logger.info("充值成功，剩余金额remainAmount=" + remainAmount);
            }catch (Exception e){
                userLogService.create(1, 1,"充值缴费","customerNo","","设备["+CNoUtil.getDeviceNoByCno(cno)+"]远程充值缴费["+paymentParam.getPayMoney()+"]元,第["+paymentParam.getPayCount()+"]次充值失败" , JSONUtils.valueToString(paymentParam));
                throw e;
            }
            // 获取电表参数
            String meterCno = CNoUtil.CreateCNo(DeviceType.ELECTRIC_METER.getCode(), cno);
            DeviceMeterParam devParaMeter = deviceMeterParamService.queryEffectiveParamByCno(meterCno);
            Integer localControl = devParaMeter.getLocalControl();
            if (CustomerInfoConstant.FeeControlType.LOCAL.getCode().equals(localControl)) {
                // 本地费控
                String jzqCNo = devParaMeter.getJzqCno().substring(2).replaceAll("^0*", "");
                // 获取并设置中间件的传入参数
                PaymentParam param = new PaymentParam();
                param.setPayGuid(feePay.getPayGuid());
                param.setCommAddr(devParaMeter.getCommAddr());
                param.setCommPort(String.valueOf(devParaMeter.getCommPort()));
                param.setMeterUserNo(meterUserNo);
                param.setJzqNo(jzqCNo);
                param.setMoteEUI(devParaMeter.getMoteEui());
                param.setMoteType(cno.substring(0,2));
                param.setOverTime(String.valueOf(60));
                param.setPayCount(String.valueOf(feePay.getPayCount()));
                param.setPayMoney(String.valueOf(feePay.getPayMoney()));

                int sendResult = instructService.PaymentStruct(param);
                if(sendResult != 1){
                    // 表示调用中间件失败
                    String message = "充值失败";
                    throw new BusinessException(message);
                }
            } else {
                // 远程费控或者其他
                int i = MathUtil.compareTo(remainAmount, BigDecimal.ZERO);
                if (i > 0) {
                    CancelOffParam cancelOffParam=new CancelOffParam();
                    cancelOffParam.setCno(cno);
                    cancelOffParam.setCustomerNo(feePay.getCustomerNo());
                    cancelOffParam.setGuid(feePay.getPayGuid());
                    String jzqCNo = devParaMeter.getJzqCno().substring(2).replaceAll("^0*", "");
                    cancelOffParam.setJzqNo(jzqCNo);
                    // 发送取消拉闸指令
                    int sendResult = instructService.CancelOff(cancelOffParam);
                    if(sendResult != 1){
                        // 表示调用中间件失败
                        String message = "充值失败";
                        throw new BusinessException(message);
                    }
                }
            }
        }
    }

    private BigDecimal rechargePayment(RechargePaymentParam paymentParam){
        // 充值缴费
        feePayMapper.rechargePayment(paymentParam);
        int status = Integer.parseInt(paymentParam.getResult());
        BigDecimal afterAmunt = paymentParam.getAfterAmunt();
        logger.info("存储过程充值返回status=" + status);
        switch (status) {
            case 0:
                throw new BusinessException("保存失败");
            case 1:
                break;
            case 2:
                throw new BusinessException("购电次数异常");
            case 3:
                throw new BusinessException("用户未开卡");
            case 4:
                throw new BusinessException("用户有购电记录未充值到电表");
            default:
                throw new BusinessException("未定义的状态");
        }
        return afterAmunt;
    }

    @Override
    @Transactional
    public BigDecimal remoteRecharge(RemoteRechargeVo rechargeVo) {
        // 中天只有远程费控表，payModel=1
        String customerNo = rechargeVo.getCustomerNo();
        // 远程充值
        BigDecimal remainAmount;
        // 充值缴费调用存储过程的传入参数
        RechargePaymentParam paymentParam = new RechargePaymentParam();
        paymentParam.setPayMoney(String.valueOf(rechargeVo.getPayMoney()));
        paymentParam.setPayCount(String.valueOf(rechargeVo.getPayCount()));
        paymentParam.setPayModel(String.valueOf(rechargeVo.getPayModel()));
        paymentParam.setCustomerNo(customerNo);
        paymentParam.setCno("");
        paymentParam.setPayGuid(rechargeVo.getPayGuid());
        paymentParam.setCreateUserId(String.valueOf(rechargeVo.getUserId()));
        paymentParam.setPayMethod(FeeControlConstant.PayMethod.CASH.getCode());
        paymentParam.setSerialNum(DateUtil.getSerialNum());
        try {
            remainAmount = rechargePayment(paymentParam);
            logger.info("充值成功，剩余金额remainAmount=" + remainAmount);
        } catch (Exception e){
            CustomerInfo customerInfo = customerInfoService.queryByCustomerNo(rechargeVo.getCustomerNo());
            userLogService.create(1, 1,"充值缴费","customerNo","","用户["+customerInfo.getCustomerName()+"]远程充值缴费["+rechargeVo.getPayMoney()+"]元,第["+rechargeVo.getPayCount()+"]次充值失败" , JSONUtils.valueToString(paymentParam));
            throw e;
        }
        return remainAmount;
    }

    /**
     * 更新充值记录的状态
     * 当writeMeter为1的时候，表示写入
     * 当writeMeter为2的时候，表示更新
     *
     * @param payGuid
     * @param writeMeter
     * @return
     */
    @Override
    public void updateWriteMeter(String payGuid, int writeMeter, int payModel, long userId,Integer flag) {
        // 更新下发状态和购电方式
//        FeePayDTO feePay = feePayMapper.selectByPayGuid(payGuid);
        // 中天不会用到开户，这里直接new一个解决报错问题
        FeePayDTO feePay = new FeePayDTO();

        FeePay updateFeePay = new FeePay();
        updateFeePay.setId(feePay.getId());
        updateFeePay.setPayModel(payModel);
        updateFeePay.setWriteMeter(writeMeter);
        updateFeePay.setWitMetTime(new Date());
        feePayMapper.updateByPrimaryKeySelective(updateFeePay);

        // 如果payModel=1并且writeMeter=0,此时需要下发远程充值的指令
        if(FeeControlConstant.PayModelValue.APP.getCode().intValue() == payModel && writeMeter == 0){
            // 获取cno
            String cno = feePay.getCno();
            // 获取电表参数
            String meterCNo = CNoUtil.CreateCNo(DeviceType.ELECTRIC_METER.getCode(), cno);
            DeviceMeterParam devParaMeter = deviceMeterParamService.queryEffectiveParamByCno(meterCNo);
            String jzqNo = CNoUtil.getJzqNoByJzqCno(devParaMeter.getJzqCno());

            // 获取并设置中间件的传入参数
            PaymentParam param = new PaymentParam();
            param.setPayGuid(feePay.getPayGuid());
            param.setCommAddr(devParaMeter.getCommAddr());
            param.setCommPort(String.valueOf(devParaMeter.getCommPort()));
            param.setMeterUserNo(feePay.getMeterUserNo());
            param.setJzqNo(jzqNo);
            param.setMoteEUI(devParaMeter.getMoteEui());
            param.setMoteType(cno.substring(0,2));
            param.setOverTime(String.valueOf(60));
            param.setPayCount(String.valueOf(feePay.getPayCount()));
            param.setPayMoney(String.valueOf(feePay.getPayMoney()));

            int sendResult = instructService.PaymentStruct(param);
            logger.info("远程充值指令发送结果：" + sendResult);
        }else if(FeeControlConstant.PayModelValue.APP.getCode().intValue() == payModel && writeMeter == 2){
            userLogService.create(1, 1,"充值缴费","customerNo","","设备["+CNoUtil.getDeviceNoByCno(feePay.getCno())+"]取消远程充值成功" , JSONUtils.valueToString(feePay));
        }else if(FeeControlConstant.PayModelValue.IC_CARD.getCode().intValue() == payModel&& writeMeter == 1&&flag==0){
            userLogService.create(1, 1,"充值缴费","customerNo","","设备["+CNoUtil.getDeviceNoByCno(feePay.getCno())+"]开户购电卡充值缴费["+feePay.getPayMoney()+"]元,第["+feePay.getPayCount()+"]次充值成功" , JSONUtils.valueToString(feePay));
        }else if(FeeControlConstant.PayModelValue.IC_CARD.getCode().intValue() == payModel&& writeMeter == 1&&flag==1){
            userLogService.create(1, 1,"充值缴费","customerNo","","设备["+CNoUtil.getDeviceNoByCno(feePay.getCno())+"]补开户购电卡充值缴费["+feePay.getPayMoney()+"]元,第["+feePay.getPayCount()+"]次充值成功" , JSONUtils.valueToString(feePay));
        }else if(FeeControlConstant.PayModelValue.IC_CARD.getCode().intValue() == payModel&& writeMeter == 1&&flag==8){
            userLogService.create(1, 1,"充值缴费","customerNo","","设备["+CNoUtil.getDeviceNoByCno(feePay.getCno())+"]补第["+feePay.getPayCount()+"]次售电卡成功" , JSONUtils.valueToString(feePay));
        }else if(FeeControlConstant.PayModelValue.IC_CARD.getCode().intValue() == payModel&& writeMeter == 1&&flag==9){
            userLogService.create(1, 1,"充值缴费","customerNo","","设备["+CNoUtil.getDeviceNoByCno(feePay.getCno())+"]购电卡充值缴费["+feePay.getPayMoney()+"]元,第["+feePay.getPayCount()+"]次充值成功" , JSONUtils.valueToString(feePay));
        }else if(FeeControlConstant.PayModelValue.IC_CARD.getCode().intValue() == payModel&& writeMeter == 1&&flag==10){
            userLogService.create(1, 1,"充值缴费","customerNo","","设备["+CNoUtil.getDeviceNoByCno(feePay.getCno())+"]重新开户缴费["+feePay.getPayment()+"]元,写卡["+feePay.getPayMoney()+"]元" , JSONUtils.valueToString(feePay));
        }
    }


    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=RuntimeException.class)
    public void saveReadCardAndUpdateFeeAcct(FeeReadCardParam param, Long userId) {
        Date createTime = new Date();
        FeeReadCard readCard = new FeeReadCard();
        BeanUtils.copyProperties(param,readCard);

        readCard.setCreateUserId(userId);
        readCard.setCreateTime(createTime);
        String cardId = param.getCardId();
        if (StringUtils.isEmpty(cardId)) {
            readCard.setCardId("");
        }

        // 保存读取的IC卡记录
        feeReadCardService.insertSelective(readCard);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date reWrtTime;
        try {
            reWrtTime = format.parse(param.getReWrtTime());
        } catch (ParseException e) {
            try {
                reWrtTime = format.parse("0001-01-01 00:00");
            } catch (ParseException e1) {
                logger.error("时间解析异常",e1);
                throw new RuntimeException();
            }
        }

        //更新fee_acct表: 返写时间、非法插卡次数、更新时间、更新人员id
        FeeAcct feeAcct = new FeeAcct();
        feeAcct.setReWrtTime(reWrtTime);
        feeAcct.setErrorCnt(param.getErrorCnt());
        feeAcct.setUpdateTime(createTime);
        feeAcct.setUpdateUserId(userId);
        feeAcct.setCno(param.getCno());
        feeAcctService.updateFeeAcctByCno(feeAcct);
    }

    @Override
    public List<FuzzyQueryDTO> fuzzyQuery(UserFuzzyQueryVo queryVo) {
        List<UserFuzzyQueryInfo> list = customerInfoService.fuzzyQuery(queryVo);
        if(CollectionUtils.isEmpty(list)){
            List<FuzzyQueryDTO> emptyList = Lists.newArrayList();
            return emptyList;
        }

        List<FuzzyQueryDTO> dtoList = new LinkedList<>();
        for (UserFuzzyQueryInfo customerInfo : list) {
            String customerName = customerInfo.getCustomerName();
            String customerAddr = customerInfo.getCustomerAddr();
            String customerContact = customerInfo.getCustomerContact();
            String propertyName = customerInfo.getPropertyName();
            StringBuilder builder = new StringBuilder();

            if(!StringUtils.isEmpty(customerName)){
                builder.append("用户姓名：");
                builder.append(customerName);
            }

            if(!StringUtils.isEmpty(customerContact)){
                builder.append(" ||用户电话：");
                builder.append(customerContact);
            }

            if(!StringUtils.isEmpty(propertyName)){
                builder.append(" ||门牌编号：");
                builder.append(propertyName);
            }

            if(!StringUtils.isEmpty(customerAddr)){
                builder.append(" ||用户地址：");
                int length = customerAddr.length();
                if (length > 20) {
                    customerAddr = customerAddr.substring(0,20) + "...";
                }
                builder.append(customerAddr);
            }


            String text = builder.toString();
            FuzzyQueryDTO fuzzyQueryDTO = new FuzzyQueryDTO();
            fuzzyQueryDTO.setText(text);
            String customerNo = customerInfo.getCustomerNo();
            String value = customerNo;
            fuzzyQueryDTO.setValue(value);
            dtoList.add(fuzzyQueryDTO);
        }
        logger.info("FeePayServiceImpl-fuzzyQuery 返回参数:"+dtoList.toString());
        return dtoList;
    }

    @Override
    public FeePayRecordInfo searchByMeterUserNo(FeePayDetailParam detailParam) {
        // 如果设备类型为空，则给一个默认的设备类型
        String deviceType = detailParam.getDeviceType();
        if (StringUtils.isEmpty(deviceType)) {
            deviceType = DeviceType.ELECTRIC_METER.getCode();
        }

        Integer pageSize = detailParam.getPageSize();
        if (pageSize == null) {
            pageSize = 20;
        }

        Integer pageNumber = detailParam.getPageNumber();
        if (pageNumber == null) {
            pageNumber = 1;
        }
        BaseParam param = new BaseParam();
        try {
            SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            // 获取当前系统时间
            Date date = new Date();
            calendar.setTime(date);
            // 获取当前系统时间的前一个月的时间
            calendar.add(Calendar.MONTH, -1);
            Date prMonthTime = calendar.getTime();
            // 如果开始时间为空，则给一个默认的开始时间(系统当前时间的一个月前的时间)
            String sdate = detailParam.getSdate();
            if(StringUtils.isEmpty(sdate)){
                param.setSdate(prMonthTime);
            }else{
                param.setSdate(sdf.parse(sdate));
            }
            // 如果结束时间为空，则给一个默认的结束时间(系统当前时间)
            String edate = detailParam.getEdate();
            if(StringUtils.isEmpty(edate)){
                param.setEdate(date);
            }else{
                param.setEdate(sdf.parse(edate));
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }

        param.setDeviceType(deviceType);
        param.setCustomerNo("");
        param.setCustomerContact("");
        param.setCustomerAddr("");
        param.setCustomerName("");
        param.setMeterUserNo(detailParam.getMeterUserNo());
        param.setWriteMeter("");
        param.setPayModel("");
        param.setPageSize(pageSize);
        param.setPageNumber(pageNumber);
        param.setUserId(detailParam.getUserId());

        List<QueryProcDTO> list = feePayMapper.queryByProc(param);

        // 统计数据json
        FeePayStatisticInfo statisticInfo = new FeePayStatisticInfo();
        statisticInfo.setPayment(param.getSumPayment());
        statisticInfo.setAdjustAmount(param.getSumAdjust());
        statisticInfo.setPayMoney(param.getSumPayMoney());

        FeePayRecordInfo recordInfo = new FeePayRecordInfo();
        recordInfo.setList(list);
        recordInfo.setStatistics(statisticInfo);
        detailParam.setTotal(Long.valueOf(param.getRowCount()));

        return recordInfo;

    }


    @Override
    public int rechargeStatus(String payGuid) {
        try {
            AbsBaseDataObject dataObject = ClientManager.getClientFutureStatus(payGuid);
            int status;
            status= dataObject.getStatus();
            logger.info("获取payGuid:" + payGuid + "远程充值状态结果：" + status);
            return status;
        }catch (Exception e){
            throw new BusinessException("前置机未连接");
        }

    }

    @Override
    @Transactional
    public void sendReadAmountAndCntCmd(long userID, String deviceCno, String queueGuid) throws BusinessException{
        MeterReadQueue meterQueue = new MeterReadQueue();
        meterQueue.setMeterCno(deviceCno);
        // 00900200剩余金额  00010100正向有功总 03330201上1次购电后总购电次数  03330601上1次购电后累计购电金额
        meterQueue.setDi645(new String[]{"00900200", "03330201","00010100","03330601"});

        DeviceMeterParam deviceMeterParam = deviceMeterParamService.queryEffectiveParamByCno(deviceCno);
        if(deviceMeterParam.getCommPort() == 32){
            if(deviceMeterParam.getMoteType().equals("C")){
                meterQueue.setOverTime(20);
            }else {
                meterQueue.setOverTime(10);
            }
        }else {
            meterQueue.setOverTime(60);
        }

        meterQueue.setQueueGuid(queueGuid);
        String groupGuid = UuidUtil.getUuid();
        meterQueue.setGroupGuid(groupGuid);
        meterQueue.setCreateUserId(userID);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        meterQueue.setCreateTime(calendar.getTime());
        meterQueue.setIsImportant(0);
        int ret = instructService.SendCollectInstruct(meterQueue);
        if (ret == 1) {
            MeterReadQueue param = new MeterReadQueue();
            param.setMeterCno(deviceCno);
            param.setQueueGuid(queueGuid);
            int count = meterReadQueueService.selectCount(param);
            if (count > 0) {
                MeterReadQueue updateParam = new MeterReadQueue();
                updateParam.setReadStatus(0);
                updateParam.setUpdateTime(new Date());
                updateParam.setCreateTime(meterQueue.getCreateTime());
                updateParam.setMeterCno(deviceCno);
                updateParam.setQueueGuid(queueGuid);
                meterReadQueueService.updateSelectiveByGuidAndCno(updateParam);
            } else {
                meterReadQueueService.insertSelective(meterQueue);
            }
        } else {
            throw new BusinessException("指令发送失败");
        }
    }

    @Override
    public int getReadStatus(String queueGuid) {
        ResultInfo resultInfo = ClientManager.getMulReadMeterStatus(queueGuid);
        if (resultInfo == null) {
            return 0;
        }
        return resultInfo.getStatus();
    }

    @Override
    public FeePayDetailDto searchFeePayByPayGuid(String payGuid) {
        FeePayDetailDto feePayDetailDto = feePayMapper.selectByPayGuid(payGuid);
        String descByCode = FeeControlConstant.PayMethod.getDescByCode(feePayDetailDto.getPayMethodValue());
        feePayDetailDto.setPayMethod(descByCode);
        return feePayDetailDto;
    }

    @Override
    @Transactional
    public void disablePay(DisablePayVo disablePayVo) {
        feePayMapper.disablePay(disablePayVo);
    }

    @Override
    public List<FeePay> getLastNFeePay(String cno, int n) {
        return feePayMapper.getLastNFeePay(cno,n);
    }

    @Override
    public FeePay queryByParams(String payGuid, String cno) {
        FeePay param = new FeePay();
        param.setPayGuid(payGuid);
        param.setCno(cno);
        param.setIsValid(1);
        FeePay feePay = feePayMapper.selectOne(param);
        return feePay;
    }
}
