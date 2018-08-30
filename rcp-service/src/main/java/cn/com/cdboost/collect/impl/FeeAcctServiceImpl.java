package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.FeeAcctMapper;
import cn.com.cdboost.collect.dto.AcctDetailInfo;
import cn.com.cdboost.collect.dto.AcctInfo;
import cn.com.cdboost.collect.dto.param.FeeAcctGetQueryVo;
import cn.com.cdboost.collect.enums.DeviceType;
import cn.com.cdboost.collect.enums.ResultCode;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.FeeAcct;
import cn.com.cdboost.collect.service.FeeAcctService;
import cn.com.cdboost.collect.util.PreconditionsUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 费控IC卡信息服务接口实现类
 */
@Service
public class FeeAcctServiceImpl extends BaseServiceImpl<FeeAcct> implements FeeAcctService {
    private static final Logger logger = LoggerFactory.getLogger(FeeAcctServiceImpl.class);

    @Autowired
    private FeeAcctMapper feeAcctMapper;

    @Override
    @Transactional
    public void addFeeAcct(FeeAcct feeAcct) {
        feeAcctMapper.insertSelective(feeAcct);
    }

    @Override
    @Transactional
    public int deleteFeeAcct(String cno) {
        FeeAcct feeAcct = new FeeAcct();
        feeAcct.setCno(cno);
        return feeAcctMapper.delete(feeAcct);
    }

    @Override
    public int deleteByParams(String customerNo, String cno) {
        FeeAcct feeAcct = new FeeAcct();
        feeAcct.setCustomerNo(customerNo);
        feeAcct.setCno(cno);
        return feeAcctMapper.delete(feeAcct);
    }

    @Override
    public List<FeeAcct> batchQueryByCnos(List<String> cnos) {
        Condition condition = new Condition(FeeAcct.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("cno",cnos);
        return feeAcctMapper.selectByCondition(condition);
    }

    @Override
    @Transactional
    public void batchDeleteByCnos(List<String> cnos) {
        Condition condition = new Condition(FeeAcct.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("cno",cnos);
        feeAcctMapper.deleteByCondition(condition);
    }

    @Override
    public int deleteByCustomerNo(String customerNo) {
        FeeAcct param = new FeeAcct();
        param.setCustomerNo(customerNo);
        return feeAcctMapper.delete(param);
    }

    @Override
    public List<AcctInfo> query(FeeAcctGetQueryVo queryVo) {
        String customerAddr = queryVo.getCustomerAddr();
        if (customerAddr == null) {
            queryVo.setCustomerAddr("");
        }

        String customerContact = queryVo.getCustomerContact();
        if (customerContact == null) {
            queryVo.setCustomerContact("");
        }

        String meterUserNo = queryVo.getMeterUserNo();
        if (meterUserNo == null) {
            queryVo.setMeterUserNo("");
        }

        String customerName = queryVo.getCustomerName();
        if (customerName == null) {
            queryVo.setCustomerName("");
        }

        String deviceNo = queryVo.getDeviceNo();
        if (deviceNo == null) {
            queryVo.setDeviceNo("");
        }

        String deviceType = queryVo.getDeviceType();
        if (deviceType == null) {
            queryVo.setDeviceType(DeviceType.ELECTRIC_METER.getCode());
        }
        String status = queryVo.getStatus();
        if (status == null) {
            queryVo.setStatus("");
        }

        return feeAcctMapper.query(queryVo);
    }

    @Override
    public AcctDetailInfo queryDetail(String customerNo, String cno) {
        return feeAcctMapper.queryDetail(customerNo,cno);
    }

    @Override
    @Transactional
    public void updateFeeAcctByCno(FeeAcct feeAcct) throws BusinessException{
        String cno = feeAcct.getCno();
        Condition condition = new Condition(FeeAcct.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("cno",cno);
        feeAcctMapper.updateByConditionSelective(feeAcct,condition);
    }

    @Override
    public FeeAcct query(String cno,String customerNo) {
        FeeAcct param = new FeeAcct();
        param.setCno(cno);
        if (!StringUtils.isEmpty(customerNo)) {
            param.setCustomerNo(customerNo);
        }

        return feeAcctMapper.selectOne(param);
    }

    @Override
    public AcctDetailInfo queryAccount(String customerNo, String cno, int flag) {
        return feeAcctMapper.queryAccount(customerNo,cno,flag);
    }

    @Override
    public AcctDetailInfo queryRepectPay(String customerNo, String cno, String payGuid) {
        PreconditionsUtil.checkArgument(ResultCode.ParamError.getValue(), !StringUtils.isEmpty(customerNo),ResultCode.ParamError.getDesc());
        PreconditionsUtil.checkArgument(ResultCode.ParamError.getValue(), !StringUtils.isEmpty(cno),ResultCode.ParamError.getDesc());
        return feeAcctMapper.queryRepectPay(customerNo,cno, payGuid);
    }
}
