package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.service.CacheLoad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 缓存加载bean
 */
@Service
public class CacheLoadService implements Runnable,InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(CacheLoadService.class);

    @Autowired
    private List<CacheLoad> cacheLoadList;
    @Value("${isLoadCache}")
    private String isLoadCache;

    @Override
    public void run() {
        if ("0".equals(isLoadCache)) {
            return;
        }
        logger.info("------缓存加载开始-----");
        long start = System.currentTimeMillis();
        if (cacheLoadList != null && cacheLoadList.size() > 0) {
            for (CacheLoad cacheLoad : cacheLoadList) {
                cacheLoad.loadCache();
            }
        }
        long end = System.currentTimeMillis();
        logger.info("------缓存加载结束，总耗时{}秒-----",(end - start) / 1000);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Thread t = new Thread(this);
        t.start();
    }
}
