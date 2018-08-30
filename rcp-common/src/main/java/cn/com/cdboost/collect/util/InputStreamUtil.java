package cn.com.cdboost.collect.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class InputStreamUtil {

    public static final int BUFFER_SIZE = 4096;

    /**
     * 将InputStream转换成String
     * @param in
     * @return
     * @throws Exception
     */
    public static String convert2String(InputStream in) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while((count = in.read(data,0,BUFFER_SIZE)) != -1){
            outStream.write(data, 0, count);
        }
        return new String(outStream.toByteArray(),"UTF-8");
    }
}