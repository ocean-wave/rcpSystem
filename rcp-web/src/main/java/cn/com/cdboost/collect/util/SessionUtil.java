package cn.com.cdboost.collect.util;

import cn.com.cdboost.collect.dto.LoginUser;

/**
 * Session工具类
 */
public class SessionUtil {
    private static ThreadLocal<LoginUser> local = new ThreadLocal<LoginUser>();

    /**
     * 赋值
     * @param session
     */
    public static void set(LoginUser session) {
        local.set(session);
    }
    /**
     * 取值
     * @return
     */
    public static LoginUser get() {
        return local.get();
    }
    /**
     * 移除
     */
    public static void remove(){
        local.remove();
    }
}
