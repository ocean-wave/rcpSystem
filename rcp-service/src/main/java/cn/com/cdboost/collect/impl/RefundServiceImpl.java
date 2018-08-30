package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.cache.OrgCacheVo;
import cn.com.cdboost.collect.dao.FeePayMapper;
import cn.com.cdboost.collect.dao.FeeRefundMapper;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.QueryRechargeRecordInfo;
import cn.com.cdboost.collect.model.Org;
import cn.com.cdboost.collect.model.UserOrg;
import cn.com.cdboost.collect.service.OrgService;
import cn.com.cdboost.collect.service.RefundService;
import cn.com.cdboost.collect.service.UserOrgService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class RefundServiceImpl implements RefundService {
    @Autowired
    private UserOrgService userOrgService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private FeeRefundMapper feeRefundMapper;
    @Autowired
    private FeePayMapper feePayMapper;
    @Override
    public RefundQueryListInfo queryList(RefundQueryListVo refundQueryListVo, Integer userId) {
        //实例化结果集
        RefundQueryListInfo refundQueryListInfo = new RefundQueryListInfo();
        RefundQueryListMoneyCount refundQueryListMoneyCount = new RefundQueryListMoneyCount();
        //查询数据权限
        Set<Long> dataOrgNos = this.queryUserOrgNoByUserId(userId);
        List<Long> orgNos =  Lists.newArrayList(dataOrgNos);
        refundQueryListVo.setOrgNos(orgNos);
        //查询数据列表
        List<RefundQueryListDto> list = feeRefundMapper.queryList(refundQueryListVo);
        refundQueryListInfo.setList(list);
        //退费总金额查询
        Float totalMoney = feeRefundMapper.queryRefundMoney(refundQueryListVo);
        refundQueryListMoneyCount.setRefundMoney(totalMoney);
        refundQueryListInfo.setStatistics(refundQueryListMoneyCount);
        return refundQueryListInfo;
    }

    @Override
    public Long queryCount(RefundQueryListVo refundQueryListVo, Integer userId) {
        //查询数据权限
        Set<Long> dataOrgNos = this.queryUserOrgNoByUserId(userId);
        List<Long> orgNos =  Lists.newArrayList(dataOrgNos);
        refundQueryListVo.setOrgNos(orgNos);
        Long total = feeRefundMapper.queryCount(refundQueryListVo);
        //返回查询总数
        return total;
    }

    @Override
    public int customerRefund(CustomerRefundVo customerRefundVo, Integer userId) {
        //实例化退费操作实体
        CustomerRefundCallVo customerRefundCallVo = new CustomerRefundCallVo();
        //设置数据
        customerRefundCallVo.setCustomerNo(customerRefundVo.getCustomerNo());
        customerRefundCallVo.setRefundMoney(new BigDecimal(customerRefundVo.getRefundMoney()));
        customerRefundCallVo.setRefundMethod(Integer.parseInt(customerRefundVo.getRefundMethod()));
        customerRefundCallVo.setRefundGuid(customerRefundVo.getRefundGuid());
        if(!StringUtils.isEmpty(customerRefundVo.getRemark())) {
            customerRefundCallVo.setRemark(customerRefundVo.getRemark());
        }

        customerRefundCallVo.setCreateUserId(userId);
        //操作退款
        feeRefundMapper.customerRefund(customerRefundCallVo);
        //返回结果
        return customerRefundCallVo.getResult();
    }

    @Override
    public QueryDetailDto queryDetai(QueryDetialVo queryDetialVo, Integer userId) {
        //查询数据权限
        Set<Long> dataOrgNos = this.queryUserOrgNoByUserId(userId);
        List<Long> orgNos =  Lists.newArrayList(dataOrgNos);
        queryDetialVo.setOrgNos(orgNos);
        //查询数据
        QueryDetailDto queryDetailDto = feeRefundMapper.queryDetial(queryDetialVo);
        return queryDetailDto;
    }

    @Override
    public List<QueryDetailDto> queryListByCst(QueryListByCstVo queryListByCstVo, Integer userId) {
        //查询数据权限
        Set<Long> dataOrgNos = this.queryUserOrgNoByUserId(userId);
        List<Long> orgNos =  Lists.newArrayList(dataOrgNos);
        queryListByCstVo.setOrgNos(orgNos);
        //查询数据
        List<QueryDetailDto> dataList = Lists.newArrayList();
        List<QueryDetailDto> list = feeRefundMapper.queryListByCst(queryListByCstVo);
        if (CollectionUtils.isEmpty(list)) {
            return dataList;
        }
        return list;
    }

    @Override
    public QueryRefundRecordInfo queryRefundRecord(QueryRefundRecordVo queryRefundRecordVo, Integer userId) {
        //查询数据权限
        Set<Long> dataOrgNos = this.queryUserOrgNoByUserId(userId);
        List<Long> orgNos =  Lists.newArrayList(dataOrgNos);
        queryRefundRecordVo.setOrgNos(orgNos);
        //实例化结果对象
        QueryRefundRecordInfo queryRefundRecordInfo = new QueryRefundRecordInfo();
        QueryRefundRecordMoneyCount queryRefundRecordMoneyCount = new QueryRefundRecordMoneyCount();
        //查询列表
        List<QueryRefundRecordDto> list = feeRefundMapper.queryRefundRecord(queryRefundRecordVo);
        //查询退款总金额
        Float totalMoney = feeRefundMapper.queryRefundRecordMoneyCount(queryRefundRecordVo);
        queryRefundRecordInfo.setList(list);
        queryRefundRecordMoneyCount.setRefundMoney(totalMoney);
        queryRefundRecordInfo.setStatistics(queryRefundRecordMoneyCount);
        return queryRefundRecordInfo;
    }

    @Override
    public Long queryRefundRecordCount(QueryRefundRecordVo queryRefundRecordVo, Integer userId) {
        //查询数据权限
        Set<Long> dataOrgNos = this.queryUserOrgNoByUserId(userId);
        List<Long> orgNos =  Lists.newArrayList(dataOrgNos);
        queryRefundRecordVo.setOrgNos(orgNos);
        //查询数据
        Long total = feeRefundMapper.queryRefundRecordCount(queryRefundRecordVo);
        return total;
    }

    @Override
    public QueryRechargeRecordInfo queryRechargeRecord(QueryPr0Vo queryVo, Integer userId) {
        // 查询用户的管辖权限
        List<UserOrg> userOrgs = userOrgService.queryByUserId(userId);
        Set<Long> orgNoSet = Sets.newHashSet();
        for (UserOrg userOrg : userOrgs) {
            orgNoSet.add(userOrg.getOrgNo());
        }

        // 根据管辖权限，查询用户
        List<Long> orgNoList = orgService.queryDataOrg(orgNoSet);
        queryVo.setOrgNoList(orgNoList);
        queryVo.setStart((queryVo.getPageNumber() - 1) * queryVo.getPageSize());
        queryVo.setStartDate(queryVo.getStartDate()+" 00:00:00");
        queryVo.setEndDate(queryVo.getEndDate()+" 24:00:00");
        //查询总数
        Long total = feePayMapper.queryTotal(queryVo).longValue();
        queryVo.setTotal(total);
        QueryRechargeRecordInfo queryRechargeRecordInfo = new QueryRechargeRecordInfo();

        List<QueryRechargeRecordDto> queryRechargeRecordDto = feePayMapper.queryRechargeRecord(queryVo);

        if (queryRechargeRecordDto != null){
            queryRechargeRecordInfo.setList(queryRechargeRecordDto);
        }
        Statistics statistics = new Statistics();
        BigDecimal totalPayMoney = feePayMapper.queryPayMoneyTotal(queryVo);
        /*if (queryRechargeRecordInfo != null){
            if (queryRechargeRecordInfo.getList() != null){
                //汇总金额
                for (QueryRechargeRecordDto queryRechargeRecordDto:queryRechargeRecordInfo.getList()){
                    totalPayMoney += queryRechargeRecordDto.getPayMoney();
                }
            }
            statistics.setPayMoney(totalPayMoney);
        }*/
        if (totalPayMoney != null){
            statistics.setPayMoney(totalPayMoney.floatValue());
            //设置总金额
            queryRechargeRecordInfo.setStatistics(statistics);
        }

        return queryRechargeRecordInfo;
    }

    public List<OrgCacheVo> queryChildOrg(Long orgNo){
        List<Org> children = orgService.queryChildren(orgNo);
        List<OrgCacheVo> cacheVos = Lists.newArrayList();
        for (Org org : children) {
            OrgCacheVo cacheVo = new OrgCacheVo();
            BeanUtils.copyProperties(org,cacheVo);
            cacheVos.add(cacheVo);
        }
        return cacheVos;
    }
    public Set<Long> queryDataOrgs(Set<Long> orgs){
        Set<Long> dataSet = Sets.newHashSet();
        for (Long orgNo : orgs) {
            List<OrgCacheVo> orgCacheVoList = this.queryChildOrg(orgNo);
            for (OrgCacheVo orgCacheVo : orgCacheVoList) {
                dataSet.add(orgCacheVo.getOrgNo());
            }
        }
        return  dataSet;
    }
    public Set<Long> queryUserOrgNoByUserId(Integer userId){
        List<UserOrg> userOrgs = userOrgService.queryByUserId(userId);
        Set<Long> orgNoSet = Sets.newHashSet();
        for (UserOrg userOrg : userOrgs) {
            orgNoSet.add(userOrg.getOrgNo());
        }
        // 查询当前组织以及该组织的所有孩子节点
        Set<Long> allDataOrgs = this.queryDataOrgs(orgNoSet);
        return allDataOrgs;
    }

}
