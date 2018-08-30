package cn.com.cdboost.collect.lock;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RedissonUtil{

    private static RedissonClient redissonClient;

    public static RedissonClient getRedissonClient() {
        return redissonClient;
    }

    private RedissonUtil() {
    }

    public static RedissonUtil getInstance() {
        return InstanceHolder.instance;
    }

    private static class InstanceHolder {
        private static final RedissonUtil instance = new RedissonUtil();
    }

    @PostConstruct
    private void init() {
        Config config = new Config();
        String hostName = "127.0.0.1";
        String password = "";
        String port = "6379";
        config.useSingleServer().setAddress("redis://" + hostName + ":" + port);
        if (StringUtils.isNotBlank(password)) {
            config.useSingleServer().setPassword(password);
        }
        redissonClient = Redisson.create(config);
    }
}
