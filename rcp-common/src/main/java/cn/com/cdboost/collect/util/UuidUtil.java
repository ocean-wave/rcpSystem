package cn.com.cdboost.collect.util;

import java.util.UUID;

/**
 * uuid工具类
 */
public final class UuidUtil {

    /**
     * 生成uuid，并返回去掉横线的字符串
     * @return
     */
    public static final String getUuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-","");
    }

    public static void main(String[] args) {
        String uuid = getUuid();
        System.out.println(uuid);
    }
}
