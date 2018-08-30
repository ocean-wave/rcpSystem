package cn.com.cdboost.collect.util;

import org.apache.http.client.methods.HttpPost;

import java.security.MessageDigest;
import java.util.Date;

public class MsgHttpUtil {

	/**
	 * 获取网易云，发送短信的HttpPost对象
	 * @param url
	 * @param appKey
	 * @param appSecret
	 * @return
	 */
	public static HttpPost getHttpPost(String url,String appKey, String appSecret){
		//网易云短信接口
		HttpPost httpPost = new HttpPost(url);
		//读取网易云appKey和appSecret
	    String nonce = "" + (long)Math.random()*Long.MAX_VALUE;
	    String curTime = String.valueOf((new Date()).getTime() / 1000L);
	    String checkSum = getCheckSum(appSecret, nonce ,curTime);
	    // 设置请求的header
	    httpPost.addHeader("AppKey", appKey);
	    httpPost.addHeader("Nonce", nonce);
	    httpPost.addHeader("CurTime", curTime);
	    httpPost.addHeader("CheckSum", checkSum);
	    httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
	    return httpPost;
	}
    
	// 计算并获取CheckSum
    private static String getCheckSum(String appSecret, String nonce, String curTime) {
        return encode("sha1", appSecret + nonce + curTime);
    }

    // 计算并获取md5值
    private static String getMD5(String requestBody) {
        return encode("md5", requestBody);
    }

    private static String encode(String algorithm, String value) {
        if (value == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(value.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
}
