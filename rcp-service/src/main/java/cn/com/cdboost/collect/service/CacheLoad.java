package cn.com.cdboost.collect.service;

/**
 * 实现接口，在应用启动时加载热数据
 */
public interface CacheLoad {
    /**
     * 加载缓存
     */
    void loadCache();

    /**
     * 删除缓存
     */
    void deleteCache();
}
