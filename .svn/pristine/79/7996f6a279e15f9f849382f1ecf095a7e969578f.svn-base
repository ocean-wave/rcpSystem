package cn.com.cdboost.collect.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * 验证码缓存工具类
 */
public class CodeCacheUtil {
    // 存储手机号的验证码,5分钟内有效
    private static Cache<String, String> cache = CacheBuilder.newBuilder()
            .concurrencyLevel(5)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    /**
     * 设置
     */
    public static final void set(String key, String value) {
        cache.put(key, value);
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    public static final String get(String key) {
        String ifPresent = cache.getIfPresent(key);
        return ifPresent;
    }

    public static void main(String[] args) {
        set("13810944812","123456");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String fanlvyu = get("13810944812");
                long size = cache.size();
                System.out.println(size);
                System.out.println(fanlvyu);

                try {
                    Thread.sleep(1000*30);
                    String str = get("13810944812");
                    System.out.println("first sleep:" + str);

                    Thread.sleep(1000*60);
                    String str2 = get("13810944812");
                    System.out.println("second sleep:" + str2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }
}
