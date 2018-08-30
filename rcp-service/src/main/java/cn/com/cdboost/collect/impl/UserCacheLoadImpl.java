package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.model.Org;
import cn.com.cdboost.collect.model.UserOrg;
import cn.com.cdboost.collect.service.CacheLoad;
import cn.com.cdboost.collect.service.OrgService;
import cn.com.cdboost.collect.service.UserOrgService;
import cn.com.cdboost.collect.util.RedisUtil;
import com.google.common.base.Function;
import com.google.common.collect.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户相关缓存加载实现类
 */
@Service
public class UserCacheLoadImpl implements CacheLoad{
    private static final Logger logger = LoggerFactory.getLogger(UserCacheLoadImpl.class);

    @Autowired
    private UserOrgService userOrgService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void loadCache() {
        long start = System.currentTimeMillis();
        // 查询系统所有用户
        List<UserOrg> userOrgs = userOrgService.queryAllUserOrg();

        // 分组
        ImmutableListMultimap<Integer, UserOrg> multimap = Multimaps.index(userOrgs, new Function<UserOrg, Integer>() {
            @Nullable
            @Override
            public Integer apply(@Nullable UserOrg userOrg) {
                return userOrg.getUserId();
            }
        });

        // TODO 这块是否可优化
        Map<Integer, List<Long>> dataMap = Maps.newHashMap();
        for (Map.Entry<Integer, Collection<UserOrg>> entry : multimap.asMap().entrySet()) {
            Integer userId = entry.getKey();
            Collection<UserOrg> value = entry.getValue();
            Set<Long> dataOrgSet = Sets.newHashSet();
            for (UserOrg userOrg : value) {
                List<Org> children = orgService.queryChildren(userOrg.getOrgNo());
                for (Org child : children) {
                    dataOrgSet.add(child.getOrgNo());
                }
            }
            List<Long> list = Lists.newArrayList(dataOrgSet);
            dataMap.put(userId,list);
        }
        redisUtil.setUserOrgMap(dataMap);
        long end = System.currentTimeMillis();
        logger.info("加载用户相关缓存完毕，耗时{}秒 ", (end - start) / 1000);
    }

    @Override
    public void deleteCache() {

    }
}
