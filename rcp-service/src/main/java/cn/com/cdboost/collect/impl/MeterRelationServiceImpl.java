package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.MeterRelationMapper;
import cn.com.cdboost.collect.dto.response.MainSubDto;
import cn.com.cdboost.collect.model.MeterRelation;
import cn.com.cdboost.collect.service.MeterRelationService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 电表之间的关系服务接口实现类
 */
@Service
public class MeterRelationServiceImpl extends BaseServiceImpl<MeterRelation> implements MeterRelationService {

    @Autowired
    private MeterRelationMapper meterRelationMapper;

    @Override
    public List<MeterRelation> queryChildNode(Collection<String> parentCnoList) {
        Condition condition = new Condition(MeterRelation.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("pMeterCno",parentCnoList);
        List<MeterRelation> meterRelations = meterRelationMapper.selectByCondition(condition);
        return meterRelations;
    }

    @Override
    public List<String> queryChildCnoList(String parentCno) {
        Condition condition = new Condition(MeterRelation.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andLike("level","%" + parentCno + "%");
        List<MeterRelation> meterRelations = meterRelationMapper.selectByCondition(condition);
        Set<String> set = Sets.newHashSet();
        for (MeterRelation meterRelation : meterRelations) {
            if (!parentCno.equals(meterRelation.getMeterCno())) {
                set.add(meterRelation.getMeterCno());
            }
        }
        List<String> dataList = Lists.newArrayList(set);
        return dataList;
    }

    @Override
    public Map<String, Boolean> isExistChildMap(Collection<String> parentCnoList, Integer onlineStatus) {

        return null;
    }

    @Override
    public List<MeterRelation> queryChildNodeByParentCno(String parentCno) {
        MeterRelation param = new MeterRelation();
        param.setpMeterCno(parentCno);
        List<MeterRelation> select = meterRelationMapper.select(param);
        return select;
    }

    @Override
    public List<MeterRelation> queryChildNode(String parentCno, Integer onlineStatus) {
        List<MeterRelation> meterRelations = meterRelationMapper.queryChildByParam(parentCno, onlineStatus);
        return meterRelations;
    }

    @Override
    public List<MeterRelation> queryNextNodeImpDevice(String parentCno) {
        List<MeterRelation> meterRelations = meterRelationMapper.queryNextNodeImpDevice(parentCno);
        return meterRelations;
    }

    @Override
    public List<MeterRelation> queryChildTreeByCno(String cno) {
        Condition condition = new Condition(MeterRelation.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andLike("level","%" + cno + "%");
        List<MeterRelation> meterRelations = meterRelationMapper.selectByCondition(condition);
        return meterRelations;
    }

    @Override
    public List<MeterRelation> batchQueryByMeterCnos(Collection<String> cnos) {
        Condition condition = new Condition(MeterRelation.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("meterCno",cnos);
        List<MeterRelation> meterRelations = meterRelationMapper.selectByCondition(condition);
        return meterRelations;
    }

    @Override
    public List<MainSubDto> queryMainSubTree4CommonUser(String deviceType, List<Long> orgNoList) {
        return meterRelationMapper.queryMainSubTree4CommonUser(deviceType,orgNoList);
    }

    @Override
    public List<MainSubDto> queryDeviceMainSubTree(String deviceType, Integer onlineStatus, List<Long> orgNoList) {
        return meterRelationMapper.queryDeviceMainSubTree(deviceType,onlineStatus,orgNoList);
    }

    @Override
    public List<MainSubDto> queryMainDeviceList(String deviceType,Integer onlineStatus,List<Long> orgNoList) {
        List<MainSubDto> mainSubDtos = meterRelationMapper.queryMainSubTree(deviceType,onlineStatus, orgNoList);
        return mainSubDtos;
    }

    @Override
    public List<MainSubDto> queryMeterRelation4Imp(String deviceType,List<Long> orgNoList) {
        List<MainSubDto> mainSubDtos = meterRelationMapper.queryMeterRelation4Imp(deviceType, orgNoList);
        return mainSubDtos;
    }

    @Override
    public List<MainSubDto> fuzzyQueryTree(String deviceType, String deviceNo, Integer onlineStatus,List<Long> orgNoList) {
        List<MainSubDto> mainSubDtos = meterRelationMapper.fuzzyQueryTree(deviceType, deviceNo, onlineStatus, orgNoList);
        return mainSubDtos;
    }

    @Override
    public List<MainSubDto> fuzzyQueryTree4Imp(String deviceType, String deviceNo, List<Long> orgNoList) {
        List<MainSubDto> mainSubDtos = meterRelationMapper.fuzzyQueryTree4Imp(deviceType, deviceNo, orgNoList);
        return mainSubDtos;
    }

    @Override
    public MeterRelation queryByCno(String cno) {
        MeterRelation param = new MeterRelation();
        param.setMeterCno(cno);
        MeterRelation relation = meterRelationMapper.selectOne(param);
        return relation;
    }

    @Override
    @Transactional
    public int batchUpdateByPrimaryKey(List<MeterRelation> list) {
        int num = meterRelationMapper.batchUpdateByPrimaryKey(list);
        return num;
    }

    @Override
    public int deleteByCno(String cno) {
        MeterRelation param = new MeterRelation();
        param.setMeterCno(cno);
        int num = meterRelationMapper.delete(param);
        return num;
    }

    @Override
    public List<MeterRelation> queryAll() {
        return meterRelationMapper.selectAll();
    }
}
