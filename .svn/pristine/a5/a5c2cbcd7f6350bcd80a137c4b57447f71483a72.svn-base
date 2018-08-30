package cn.com.cdboost.collect.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * http相关工具类
 */
public final class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 获取访问用户的客户端IP（适用于公网与局域网）.
     */
    public static final String getClientIpAddr(final HttpServletRequest request) {
        String ipString = "";
        try {
            ipString = request.getHeader("x-forwarded-for");
            if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
                ipString = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
                ipString = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
                ipString = request.getRemoteAddr();
            }

            // 多个路由时，取第一个非unknown的ip
            final String[] arr = ipString.split(",");
            for (final String str : arr) {
                if (!"unknown".equalsIgnoreCase(str)) {
                    ipString = str;
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("获取真实client端IP异常：",e);
        }

        return ipString;
    }

}
