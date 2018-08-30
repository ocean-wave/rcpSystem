package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.cache.MenuCacheVo;
import cn.com.cdboost.collect.model.Menu;
import cn.com.cdboost.collect.service.CacheLoad;
import cn.com.cdboost.collect.service.MenuService;
import cn.com.cdboost.collect.util.RedisUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单缓存加载实现类
 */
@Service
public class MenuCacheLoadImpl implements CacheLoad {
    private static final Logger logger = LoggerFactory.getLogger(MenuCacheLoadImpl.class);

    @Autowired
    private MenuService menuService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void loadCache() {
        long start = System.currentTimeMillis();
        // 查询所有有效的菜单
        List<Menu> list = menuService.listAllMenus();

        // 返回菜单缓存列表
        List<MenuCacheVo> menuCacheList = this.getMenuCacheList(list);

        // 设置缓存
        redisUtil.setMenuCacheList(menuCacheList);
        long end = System.currentTimeMillis();
        logger.info("加载菜单相关缓存完毕，耗时{}秒 ", (end - start) / 1000);
    }

    @Override
    public void deleteCache() {

    }

    private List<MenuCacheVo> getMenuCacheList(List<Menu> list) {
        List<MenuCacheVo> dataList = Lists.newArrayList();
        for (Menu menu : list) {
            MenuCacheVo cacheVo = new MenuCacheVo();
            BeanUtils.copyProperties(menu,cacheVo);
            dataList.add(cacheVo);
        }
        return dataList;
    }
}
