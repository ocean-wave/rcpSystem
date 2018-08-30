package cn.com.cdboost.collect.util;

/**
 * @author zc
 * @desc DI数据格式化工具类
 * @create 2017-07-14 11:48
 **/
public class DIFormatUtil {

    // 数据项DI对应的数据格式
    public static String diFormat(String di) {
        String format = "4.2";
        if ("0010000".equals(di)) {
            format = "4.2";
        } else if ("00900200".equals(di)) {
            format = "4.2";
        }
        return format;
    }

    // 数据项DI对应的数据格式
    public static String[] diForamt(String[] di) {
        if (di == null || di.length == 0)
            return new String[] {};

        String[] format = new String[di.length];
        for (int i = 0; i < di.length; i++) {
            format[i] = diFormat(di[i]);
        }
        return format;
    }
}
