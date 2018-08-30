package cn.com.cdboost.collect.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.*;

public class WeiXinUtil {
    private static final Logger logger = LoggerFactory.getLogger(WeiXinUtil.class);

    public static final String API_KEY="HYwu7NzyaDZ8axVYozseVqhwSP5Z3eEU";
    public static final String APP_ID="wx7f484e7b57ce4a98";
    public static final String MCH_ID_VALUE="1485651082";

    // 微信支付回调URL
    private static String NOTIFY_URL_VALUE = "http://pay.cdboost.cn/appPay/WXAyncNotice2";
	// 微信订单查询
    private static String WEIXIN_ORDER_QUERY = "https://api.mch.weixin.qq.com/pay/orderquery";
    // 微信统一下单url
    private static final String WEIXIN_UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";


    // 微信预支付需要的参数名常量
    private static final String APPID = "appid";
    private static final String BODY = "body";
    private static final String MCH_ID = "mch_id";
    private static final String NONCE_STR = "nonce_str";
    private static final String NOTIFY_URL = "notify_url";
    private static final String OPENID = "openid";
    private static final String OUT_TRADE_NO = "out_trade_no";
    private static final String SPBILL_CREATE_IP = "spbill_create_ip";
    private static final String TOTAL_FEE = "total_fee";
    private static final String TRADE_TYPE = "trade_type";
    private static final String SIGN = "sign";
    private static final String PREPAY_ID = "prepay_id";

    // 微信JSAPI支付时需要的参数名常量
    private static final String APPID_JSAPI = "appId";
    private static final String TIMESTAMP = "timeStamp";
    private static final String NONCESTR = "nonceStr";
    private static final String PACKAGE = "package";
    private static final String SIGNTYPE = "signType";
    private static final String PAYSIGN = "paySign";


    /**        
     * 创建预支付需要的请求参数
     * @param paramsMap 预支付需要的外部额外参数map
     * @return xml形式的请求参数
     */
    private static String createRequestParam(Map<String,String> paramsMap) {
        try {
            String nonceStr = genNonceStr();
            String body = "博高支付服务";
            SortedMap<String,String> paramMap = new TreeMap<String,String>();
            paramMap.put(APPID,APP_ID);
            paramMap.put(BODY,body);
            paramMap.put(MCH_ID, MCH_ID_VALUE);
            paramMap.put(NONCE_STR,nonceStr);
            paramMap.put(NOTIFY_URL, NOTIFY_URL_VALUE);
            paramMap.put(OPENID,paramsMap.get(OPENID));
            paramMap.put(OUT_TRADE_NO, paramsMap.get(OUT_TRADE_NO));
            paramMap.put(SPBILL_CREATE_IP,paramsMap.get(SPBILL_CREATE_IP));
            paramMap.put(TOTAL_FEE,paramsMap.get(TOTAL_FEE));
            paramMap.put(TRADE_TYPE,"JSAPI");

            String sign = createSign(paramMap,API_KEY);
            paramMap.put(SIGN,sign);

            String xmlString = toXml(paramMap);
            return xmlString;
        } catch (Exception e) {
            e.printStackTrace();
			logger.error("运行异常:",e);
        }
        return null;
    }

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
     * 生成随机字符串
     * @return
     */
    public static String genNonceStr() {
        Random random = new Random();
        String str = String.valueOf(random.nextInt(10000000));
        return Md5Util2.md5(str);
    }

    private static class SSLSocketFactoryEx extends SSLSocketFactory {

        SSLContext sslContext = SSLContext.getInstance("TLS");

        public SSLSocketFactoryEx(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
            super(truststore);

            TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            };

            sslContext.init(null, new TrustManager[] { tm }, null);
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }
    }

    private static HttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
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
     * 发送http的post请求
     * @param url
     * @param entity
     * @return
     */
    public static String httpPost(String url , String entity) {
        if (url == null || url.length() == 0) {
            return null;
        }
        HttpClient httpClient = getNewHttpClient();
        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setEntity(new StringEntity(entity,"UTF-8"));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse resp = httpClient.execute(httpPost);
            if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                return null;
            }
            return InputStreamUtil.convert2String(resp.getEntity().getContent());
        } catch (Exception e) {
            e.printStackTrace();
			logger.error("运行异常:",e);
            return null;
        }
    }

    /**
     * 得到商户订单号，测试用，实际业务中需要商户系统提供
     * @return
     */
    public static String genOutTradeNo() {
        Random random = new Random();
        return Md5Util2.md5(String.valueOf(random.nextInt(1000000) + System.currentTimeMillis()));
    }

    public static long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 微信预支付入口
     * @param paramsMap
     * @return
     * @throws Exception
     */
    public static Map<String,String> weiXinPrePay(Map<String,String> paramsMap){
        String requestParam = createRequestParam(paramsMap);
		String xmlStr = httpPost(WEIXIN_UNIFIEDORDER_URL, requestParam);
        Document document = XmlUtil.getDocument(xmlStr);
        Map<String, String> map = new HashMap<String, String>();
		XmlUtil.getMapByDocument(document.getRootElement(), map);
        return map;
    }

    /**
     * 通过预支付id，构造支付参数
     * @param prepayId 预支付id
     * @return
     */
    public static Map<String,String> constructPayParam(String prepayId) throws UnsupportedEncodingException{
		String packageName = "prepay_id=" + prepayId;
		String nonceStr = UUID.randomUUID().toString();
		String signType = "MD5";
		String timeStamp = String.valueOf(genTimeStamp());

        SortedMap<String,String> paramMap = new TreeMap<String,String>();
        paramMap.put(APPID_JSAPI,APP_ID);
        paramMap.put(TIMESTAMP,timeStamp);
        paramMap.put(NONCESTR,nonceStr);
        paramMap.put(PACKAGE,packageName);
        paramMap.put(SIGNTYPE, signType);

        String sign = createSign(paramMap, API_KEY);
        paramMap.put(PAYSIGN, sign);

        return paramMap;
    }

	/**
	 * 查询微信订单
	 * @param out_trade_no 商户订单号
	 * @return
	 */
    public static Map<String,String> queryWeiXinOrder(String out_trade_no) {
        String requestParam = getQueryOrderXmlParam(out_trade_no);
		if (requestParam != null) {
			String content = httpPost(WEIXIN_ORDER_QUERY, requestParam);
			Document document = XmlUtil.getDocument(content);
			Map<String, String> map = new HashMap<String, String>();
			XmlUtil.getMapByDocument(document.getRootElement(), map);
			return map;
		}
        return null;
    }

	/**
	 * 通过商户订单号构造查询微信订单参数
	 * @param out_trade_no 商户订单号
	 * @return
	 */
    public static String getQueryOrderXmlParam(String out_trade_no) {
		try {
			String nonce_str = genNonceStr();
			SortedMap<String,String> paramMap = new TreeMap<String,String>();
			paramMap.put(APPID,APP_ID);
			paramMap.put(MCH_ID, MCH_ID_VALUE);
			paramMap.put(OUT_TRADE_NO,out_trade_no);
			paramMap.put(NONCE_STR,nonce_str);
			String sign = createSign(paramMap, API_KEY);
			paramMap.put(SIGN, sign);

			String xmlstring = toXml(paramMap);
			return xmlstring;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("运行异常:",e);
			return null;
		}

    }

	/**
	 * 检查签名
	 * 微信回调时，支付结果通知的内容一定要做签名验证
	 * @param paramMap
	 * @return
	 */
	public static boolean checkSign(Map<String,String> paramMap) {
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
				String newSign = createSign(sortedMap,API_KEY);
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