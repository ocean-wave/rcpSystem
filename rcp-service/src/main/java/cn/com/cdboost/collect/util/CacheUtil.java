package cn.com.cdboost.collect.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * 缓存工具类
 */
public class CacheUtil {
    // 存储微信access_token或者ticket,2小时内有效，过期需要重新获取,1天内获取次数有限，开发者需自行存储
    private static Cache<String, String> cache = CacheBuilder.newBuilder()
            .concurrencyLevel(5)
            .expireAfterWrite(2, TimeUnit.HOURS)
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
        set("fanlvyu1","hahhah");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String fanlvyu = get("fanlvyu1");
                long size = cache.size();
                System.out.println(size);
                System.out.println(fanlvyu);

                try {
                    Thread.sleep(1000*30);
                    String str = get("fanlvyu1");
                    System.out.println("first sleep:" + str);

                    Thread.sleep(1000*60);
                    String str2 = get("fanlvyu1");
                    System.out.println("second sleep:" + str2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();


    }
}
