package cn.com.cdboost.collect.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * HttpClient工具类
 */
public final class HttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static CloseableHttpClient createDefault() {
        return HttpClientBuilder.create().build();
    }

    /**
     * 获取网易云短信接口的通用HttpPost
     * @param url
     * @param appKey
     * @param appSecret
     * @return
     */
    private static final HttpPost getNeteaseHttpPost(String url,String appKey, String appSecret) {
        //网易云短信接口
        HttpPost httpPost = new HttpPost(url);
        //读取网易云appKey和appSecret
        String nonce = "" + (long)Math.random()*Long.MAX_VALUE;
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        return httpPost;
    }

    /**
     * 发送post请求
     * @param url
     * @param params
     * @return
     */
    public static JSONObject post(String url,String appKey,String appSecret,Map<String,String> params){
        CloseableHttpClient httpclient = HttpClientUtil.createDefault();
        HttpPost httpPost = getNeteaseHttpPost(url,appKey,appSecret);
        //拼接参数
        List<NameValuePair> list = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("key=" + key + " value=" + value);
            NameValuePair pair = new BasicNameValuePair(key, value);
            list.add(pair);
        }

        // 请求发送成功，并得到响应
        JSONObject jsonObject = null;
        CloseableHttpResponse response;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            response = httpclient.execute(httpPost);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity httpEntity = response.getEntity();
                String result = null;
                try {
                    result = EntityUtils.toString(httpEntity);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 返回json格式
                jsonObject = JSONObject.parseObject(result);
                logger.info("post请求返回：" + JSON.toJSONString(jsonObject));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("post请求异常：",e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jsonObject;
    }
}
