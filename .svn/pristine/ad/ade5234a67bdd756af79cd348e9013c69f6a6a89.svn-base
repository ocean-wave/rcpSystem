package cn.com.cdboost.collect.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class WxHttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(WxHttpUtil.class);
	 /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     *            请求Map参数，请求参数应该是 {"name1":"value1","name2":"value2"}的形式。
     * @param charset         
     *             发送和接收的格式
     * @return URL 所代表远程资源的响应结果
     */
    @SuppressWarnings("rawtypes")
	public static String sendGet(String url, Map<String,Object> map,String charset){
          StringBuffer sb=new StringBuffer();
          //构建请求参数
          if(map!=null&&map.size()>0){
              Iterator it=map.entrySet().iterator(); //定义迭代器
              while(it.hasNext()){
                 Entry  er= (Entry) it.next();
                 sb.append(er.getKey());
                 sb.append("=");
                 sb.append(er.getValue());
                 sb.append("&");
             }
          } 
       return  sendGet(url,sb.toString(), charset);
    }

    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     *            请求Map参数，请求参数应该是 {"name1":"value1","name2":"value2"}的形式。
     * @param charset         
     *             发送和接收的格式
     * @return URL 所代表远程资源的响应结果
     */
    @SuppressWarnings("rawtypes")
	public static String sendGetSnms(String url, Map<String,Object> map,String charset){
          StringBuffer sb=new StringBuffer();
          //构建请求参数
          if(map!=null&&map.size()>0){
              Iterator it=map.entrySet().iterator(); //定义迭代器
              while(it.hasNext()){
                 Entry  er= (Entry) it.next();
                 sb.append(er.getKey());
                 sb.append("=");
                 sb.append(er.getValue());
                 sb.append("&");
             }
          }
       return  sendGet(url,"data="+sb.toString(), charset);
    }

    /**
     * 向指定URL发送POST方法的请求
     * 
     * @param url
     *            发送请求的URL
     *            请求Map参数，请求参数应该是 {"name1":"value1","name2":"value2"}的形式。
     * @param charset         
     *             发送和接收的格式
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String,Object> map,String charset){
    	StringBuffer sb=new StringBuffer();
        //构建请求参数
        if(map!=null&&map.size()>0){
              for (Entry<String, Object> e : map.entrySet()) {  
                  sb.append(e.getKey());  
                  sb.append("=");  
                  sb.append(e.getValue());  
                  sb.append("&");  
              }  
        }
        return  sendPost(url,sb.toString(),charset);
    }
    /**
     * 向指定URL发送POST方法的请求
     * 
     * @param url
     *            发送请求的URL
     *            请求Map参数，请求参数应该是 {"name1":"value1","name2":"value2"}的形式。
     * @param charset         
     *             发送和接收的格式
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendPostSnms(String url, Map<String,Object> map,String charset){                   
          return  sendPost(url,"data="+AjaxUtils.toCustom(map),charset);
    }
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param charset         
     *             发送和接收的格式
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param,String charset) {
        String result = "";
        String line;
        StringBuffer sb=new StringBuffer();
        BufferedReader in = null;
        try {
            String urlNameString = url ;
            if(!StringUtils.isEmpty(param))
            	urlNameString+= "?" + param;
            	
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性 设置请求格式
            conn.setRequestProperty("contentType", charset); 
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            //设置超时时间
            conn.setConnectTimeout(30*1000);
            conn.setReadTimeout(30*1000);
            // 建立实际的连接
            conn.connect();
            // 定义 BufferedReader输入流来读取URL的响应,设置接收格式
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(),charset));
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            result=sb.toString();
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param charset         
     *             发送和接收的格式       
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param,String charset) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        String line;
        StringBuffer sb=new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接 
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性 设置请求格式
            conn.setRequestProperty("contentType", charset);  
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            //设置超时时间
            conn.setConnectTimeout(30*1000);
            conn.setReadTimeout(30*1000);
            
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"UTF-8"));
            // 发送请求参数           
            out.print(param);           
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应    设置接收格式
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),charset));
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            result=sb.toString();
        } catch (Exception e) {
            System.out.println("发送 POST请求出现异常!"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }


    public static String ssl(HttpServletRequest request,String url, String partnerId, String data){
        StringBuffer message = new StringBuffer();
        try {
            KeyStore keyStore  = KeyStore.getInstance("PKCS12");
            InputStream inputStream = request.getSession().getServletContext().getResourceAsStream("/WEB-INF/apiclient_cert.p12");
            keyStore.load(inputStream, partnerId.toCharArray());
            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, partnerId.toCharArray())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
            HttpPost httpost = new HttpPost(url);

            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            httpost.setEntity(new StringEntity(data, "UTF-8"));

            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        message.append(text);
                    }
                }
                EntityUtils.consume(entity);
            } catch (IOException e) {
                logger.error("系统IO异常",e);
            } finally {
                response.close();
            }
        } catch (Exception e1) {
            logger.error("系统异常",e1);
        }

        return message.toString();
    }


    public static String ssl4Job(String url, String partnerId, String data){
        StringBuffer message = new StringBuffer();
        try {
            KeyStore keyStore  = KeyStore.getInstance("PKCS12");
            String filePath = getFilePath();
            String path = filePath + "apiclient_cert.p12";
            InputStream inputStream = new FileInputStream(new File(path));
            keyStore.load(inputStream, partnerId.toCharArray());
            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, partnerId.toCharArray())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
            HttpPost httpost = new HttpPost(url);

            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            httpost.setEntity(new StringEntity(data, "UTF-8"));

            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        message.append(text);
                    }
                }
                EntityUtils.consume(entity);
            } catch (IOException e) {
                logger.error("系统IO异常",e);
            } finally {
                response.close();
            }
        } catch (Exception e1) {
            logger.error("系统异常",e1);
        }

        return message.toString();
    }


    /**
     * 获取WEB-INF目录下面文件的路径
     * @return
     */
    private static String getFilePath() {
        //file:/D:/JavaWeb/.metadata/.me_tcat/webapps/TestBeanUtils/WEB-INF/classes/
        String path=Thread.currentThread().getContextClassLoader().getResource("").toString();
        path=path.replace('/', '\\'); // 将/换成\
        path=path.replace("file:", ""); //去掉file:
        path=path.replace("classes\\", ""); //去掉class\
        path=path.substring(1); //去掉第一个\,如 \D:\JavaWeb...
        return path;
    }

}
