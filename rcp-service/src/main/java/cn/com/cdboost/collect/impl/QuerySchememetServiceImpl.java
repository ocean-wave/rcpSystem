package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.cache.CustomerCacheVo;
import cn.com.cdboost.collect.cache.DeviceCacheVo;
import cn.com.cdboost.collect.cache.DeviceMapCacheVo;
import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.QuerySchememetMapper;
import cn.com.cdboost.collect.dto.SchemeMeterRes;
import cn.com.cdboost.collect.dto.param.SchemeMeterQueryVo;
import cn.com.cdboost.collect.model.QuerySchememet;
import cn.com.cdboost.collect.service.QuerySchememetService;
import cn.com.cdboost.collect.service.RedisService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * 查询方案详细接口实现
 */
@Service
public class QuerySchememetServiceImpl extends BaseServiceImpl<QuerySchememet> implements QuerySchememetService {

    @Autowired
    private QuerySchememetMapper querySchememetMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public List<SchemeMeterRes> queryMeterList(SchemeMeterQueryVo schemeMeterQueryVo) {
        List<SchemeMeterRes> schemeMeterRess = Lists.newArrayList();
        // 查询通断记录
        Condition condition = new Condition(QuerySchememet.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("schemeFlag",schemeMeterQueryVo.getSchemeFlag());


        //int total = querySchememetMapper.selectCountByCondition(condition);

        // 设置分页信息
        PageHelper.startPage(schemeMeterQueryVo.getPageNumber(),schemeMeterQueryVo.getPageSize(),"id desc");
        //分页查询方案详情
        List<QuerySchememet> querySchememets = querySchememetMapper.selectByCondition(condition);
        // 设置分页总条数
        PageInfo pageInfo = new PageInfo(querySchememets);
        schemeMeterQueryVo.setTotal(pageInfo.getTotal());

        //设备号集合
        List<String> cnos = Lists.newArrayList();
        //用户编号集合
        Set<String> customerNos = new HashSet<>();
        for(QuerySchememet querySchememet: querySchememets){
            if (querySchememet.getCno() != null){
                cnos.add(querySchememet.getCno());
                customerNos.add(querySchememet.getCustomerNo());
            }
        }
        //读取缓存
        Map<String, DeviceMapCacheVo> deviceMapCacheVoMap = redisService.queryDeviceMapCacheMap(cnos);
        Map<String, CustomerCacheVo> customerCacheVoMap = redisService.queryCustomerCacheMap(customerNos);
        Map<String, DeviceCacheVo> deviceCacheVoMap = redisService.queryDeviceCacheMap(cnos);

        for(QuerySchememet querySchememet: querySchememets){
            SchemeMeterRes schemeMeterRes = new SchemeMeterRes();

            //设置表计户号
            BeanUtils.copyProperties(querySchememet,schemeMeterRes);
            //设置用户名
            schemeMeterRes.setCustomerName(customerCacheVoMap.get(querySchememet.getCustomerNo()).getCustomerName());
            //设置变压器号
            schemeMeterRes.setTransformerNo(deviceMapCacheVoMap.get(querySchememet.getCno()).getTransformerNo());
            //设置楼栋编号
            schemeMeterRes.setBuildNo(deviceMapCacheVoMap.get(querySchememet.getCno()).getBuildingNo());
            //设置安装地址
            schemeMeterRes.setInstallAddr(deviceCacheVoMap.get(querySchememet.getCno()).getInstallAddr());
            //设置表号
            schemeMeterRes.setDeviceNo(deviceCacheVoMap.get(querySchememet.getCno()).getDeviceNo());

            schemeMeterRess.add(schemeMeterRes);
        }
        return schemeMeterRess;
    }

    @Override
    public int deleteByCustomerNo(String customerNo) {
        QuerySchememet param = new QuerySchememet();
        param.setCustomerNo(customerNo);
        return querySchememetMapper.delete(param);
    }

    @Override
    public List<QuerySchememet> queryByCustomerNo(String customerNo) {
        QuerySchememet param = new QuerySchememet();
        param.setCustomerNo(customerNo);
        return querySchememetMapper.select(param);
    }

    @Override
    public int batchDeleteByIds(Collection<Integer> ids) {
        String idStr = Joiner.on(",").join(ids);
        return querySchememetMapper.deleteByIds(idStr);
    }

    @Override
    public int deleteByParams(String customerNo, String cno) {
        QuerySchememet param = new QuerySchememet();
        param.setCustomerNo(customerNo);
        param.setCno(cno);
        return querySchememetMapper.delete(param);
    }

    @Override
    public List<QuerySchememet> queryByParams(String customerNo, String cno) {
        QuerySchememet param = new QuerySchememet();
        param.setCustomerNo(customerNo);
        param.setCno(cno);
        return querySchememetMapper.select(param);
    }
}
