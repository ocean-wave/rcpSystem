package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.cache.OrgCacheVo;
import cn.com.cdboost.collect.model.Org;
import cn.com.cdboost.collect.service.CacheLoad;
import cn.com.cdboost.collect.service.OrgService;
import cn.com.cdboost.collect.util.RedisUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 组织相关的缓存操作实现类
 */
@Service
public class OrgCacheLoadImpl implements CacheLoad{
    private static final Logger logger = LoggerFactory.getLogger(OrgCacheLoadImpl.class);

    @Autowired
    private OrgService orgService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void loadCache() {
        long start = System.currentTimeMillis();
        // 1、查询系统所有有效组织
        List<Org> orgs = orgService.queryAll();
        List<OrgCacheVo> orgCacheList = this.getOrgCacheList(orgs);

        // 2、设置组织基本信息缓存
        redisUtil.setOrgCacheList(orgCacheList);

        // 3、设置组织的父组织缓存
        Map<Long, List<Long>> orgParentMap = this.getOrgParentMap(orgs);
        redisUtil.setParentOrgList(orgParentMap);

        // 4、设置组织所在的整个树缓存
        Map<Long, List<OrgCacheVo>> orgTreeMap = this.getOrgTreeMap(orgs);
        redisUtil.setOrgTreeMap(orgTreeMap);

        // 5、设置组织以及他的孩子节点缓存
        Map<Long, List<OrgCacheVo>> orgChildMap = this.getOrgChildMap(orgs);
        redisUtil.setOrgChildMap(orgChildMap);
        long end = System.currentTimeMillis();
        logger.info("加载组织相关缓存完毕，耗时{}秒 ", (end - start) / 1000);
    }

    @Override
    public void deleteCache() {

    }


    /**
     * 获取每个组织的缓存信息列表
     * @param orgs
     * @return
     */
    private List<OrgCacheVo> getOrgCacheList(List<Org> orgs) {
        List<OrgCacheVo> cacheVos = Lists.newArrayList();
        for (Org org : orgs) {
            OrgCacheVo cacheVo = new OrgCacheVo();
            BeanUtils.copyProperties(org,cacheVo);
            cacheVos.add(cacheVo);
        }
        return cacheVos;
    }

    /**
     * 获取每个组织的父组织，转换成map结构返回
     * @param orgs
     * @return
     */
    private Map<Long,List<Long>> getOrgParentMap(List<Org> orgs) {
        Map<Long,List<Long>> dataMap = Maps.newHashMap();
        for (Org org : orgs) {
            String levelCode = org.getLevelCode();
            String tempStr = levelCode.substring(1);
            String[] split = tempStr.split("/");
            List<Long> list = Lists.newArrayList();
            for (String orgNo : split) {
                list.add(Long.valueOf(orgNo));
            }

            dataMap.put(org.getOrgNo(),list);
        }
        return dataMap;
    }

    /**
     * 获取组织所在的整个树节点，已map的结构返回
     * @param orgs
     * @return
     */
    private Map<Long,List<OrgCacheVo>> getOrgTreeMap(List<Org> orgs) {
        Map<Long,List<OrgCacheVo>> dataMap = Maps.newHashMap();
        for (Org org : orgs) {
            Long orgNo = org.getOrgNo();
            List<Org> treeOrgs = orgService.queryTreeByOrgNo(orgNo);
            List<OrgCacheVo> orgCacheList = this.getOrgCacheList(treeOrgs);
            dataMap.put(orgNo,orgCacheList);
        }
        return dataMap;
    }

    /**
     * 获取组织以及该组织的所有孩子节点，以map结构返回
     * @return
     */
    private Map<Long,List<OrgCacheVo>> getOrgChildMap(List<Org> orgs) {
        Map<Long,List<OrgCacheVo>> dataMap = Maps.newHashMap();
        for (Org org : orgs) {
            Long orgNo = org.getOrgNo();
            List<Org> children = orgService.queryChildren(orgNo);
            List<OrgCacheVo> orgCacheList = this.getOrgCacheList(children);
            dataMap.put(orgNo,orgCacheList);
        }
        return dataMap;
    }
}
