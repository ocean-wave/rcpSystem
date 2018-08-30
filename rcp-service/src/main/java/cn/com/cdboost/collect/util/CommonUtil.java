package cn.com.cdboost.collect.util;


import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;


/**
 * @author zc
 * @desc  通用工具类
 * @create 2017-12-07 15:18
 **/
public class CommonUtil {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * 发送 https 请求
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject（通过 JSONObject.get(key) 的方式获取 JSON 对象的属性值）
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {

        JSONObject jsonObject = null;

        try {
            // 创建 SSLContext 对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new TrustAnyTrustManager() { }};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述 SSLContext 对象中得到 SSLSocketFactory 对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当 outputStr 不为 null 时，向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();

                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();

            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            conn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            logger.error(" 连接超时：{}", ce);
        } catch (Exception e) {
            logger.error("https 请求异常：{}", e);
        }

        return jsonObject;
    }

    /**
     * URL编码（utf-8）
     *
     * @param source
     * @return
     */
    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }


}