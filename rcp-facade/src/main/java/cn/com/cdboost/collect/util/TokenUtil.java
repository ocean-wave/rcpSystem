package cn.com.cdboost.collect.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author wt
 * @desc
 * @create in  2018/5/4
 **/
public class TokenUtil {
    public static String generate(String registor,String timeMark) throws NoSuchAlgorithmException {
        String encrypt=registor+timeMark;
        MessageDigest instance = MessageDigest.getInstance("SHA-1");
        instance.update(encrypt.getBytes());
        byte[] digest = instance.digest();
        return encodeBase64(digest).substring(0,encodeBase64(digest).length()-1);
    }
    /**
     * 编码
     *
     * @param bstr
     * @return String
     */
    public static String encodeBase64(byte[] bstr) {
        return new sun.misc.BASE64Encoder().encode(bstr);
    }

    /**
     * 解码
     *
     * @param str
     * @return string
     */
    public static byte[] decodeBase64(String str) {
        byte[] bt = null;
        try {
            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
            bt = decoder.decodeBuffer(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bt;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(DateUtil.parese());
        System.out.println(generate("ZT001",DateUtil.parese()));
    }
}
