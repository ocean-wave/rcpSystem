package cn.com.cdboost.collect.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class WeiXinUtil2 {
    private static final Logger logger = LoggerFactory.getLogger(WeiXinUtil2.class);
    private static final String SIGN = "sign";

    /**
     * 创建签名
     * @param params 签名参数
     * @param KEY API密钥
     * @return 签名信息
     */
    public static String createSign(SortedMap<String, String> params, String KEY) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String,String> entry : params.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("&");
        }
        sb.append("key=").append(KEY);
		String sign = Md5Util2.md5(sb.toString()).toUpperCase();
        return sign;

    }

    /**
     * 拼接成xml格式字符串
     * @param map
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String toXml(Map<String,String> map) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        for (Map.Entry<String,String> entry : map.entrySet()) {
            String key = entry.getKey();
            sb.append("<" + key + ">");
            sb.append("<![CDATA[" + entry.getValue() + "]]>");
            sb.append("</" + key + ">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

	/**
	 * 检查签名
	 * 微信回调时，支付结果通知的内容一定要做签名验证
	 * @param paramMap
	 * @return
	 */
	public static boolean checkSign(Map<String,String> paramMap,String apiKey) {
		boolean flag = false;
		try {
			if (paramMap != null && !paramMap.isEmpty()) {
				SortedMap<String,String> sortedMap = new TreeMap<String,String>();
				for (Map.Entry<String,String> entry : paramMap.entrySet()) {
					if (SIGN.equals(entry.getKey())) {
						continue;
					}
					sortedMap.put(entry.getKey(),entry.getValue());
				}
				String weiXinSign = paramMap.get(SIGN);
				String newSign = createSign(sortedMap,apiKey);
				if (weiXinSign != null && weiXinSign.equals(newSign)) {
					flag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("运行异常:",e);
		}
		return flag;
	}
}