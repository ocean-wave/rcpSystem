package cn.com.cdboost.collect.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * Created by ZhaoJiwei on 2017/10/11.
 */

//对称加密器
public class CipherAESMessage {

    // 加密函数，将原文加密成密文 AES固定格式为128/192/256 bits
    public static String CipherAESMsg(String key,String plainText) {
        byte[] cipherText = null;

        try {
            Key keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec("aabbccddeeffgghh".getBytes());
            // 生成Cipher对象
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            // 用密钥加密明文(plainText),生成密文(cipherText)
            cipher.init(Cipher.ENCRYPT_MODE, keySpec,ivSpec); // 操作模式为加密(Cipher.ENCRYPT_MODE),key为密钥
            cipherText = cipher.doFinal(plainText.getBytes()); // 得到加密后的字节数组
            // String str = new String(cipherText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toHex(cipherText);
    }

    // 解密函数，将密文解密回原文
    public static String EncipherAESMsg(String key,String cipherText) {
        byte[] sourceText = toByte(cipherText);;

        try {
            IvParameterSpec ivSpec = new IvParameterSpec("aabbccddeeffgghh".getBytes());
            Key keySpec = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);               //与加密时不同MODE:Cipher.DECRYPT_MODE
            byte [] ret = cipher.doFinal(sourceText);
            return new String(ret, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(sourceText);

    }

    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2*buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private final static String HEX = "0123456789ABCDEF";
    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));
    }

    public static byte[] toByte(String hexString) {
        int len = hexString.length()/2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
        return result;
    }

    public static void main(String[] args) {
        String s = CipherAESMsg("lianghuilonglong", "admin");
        System.out.println(s);
        String s1 = EncipherAESMsg("lianghuilonglong", s);
        System.out.println(s1);

    }
}
