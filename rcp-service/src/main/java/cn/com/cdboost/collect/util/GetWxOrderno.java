package cn.com.cdboost.collect.util;

import com.google.common.collect.Maps;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GetWxOrderno {

	private static final Logger logger = LoggerFactory.getLogger(GetWxOrderno.class);

	public static DefaultHttpClient httpclient;

	static {
		httpclient = new DefaultHttpClient();
		httpclient = (DefaultHttpClient) HttpClientConnectionManager.getSSLInstance(httpclient);
	}


	/**
	 *description:获取预支付id
	 *@param url
	 *@param xmlParam
	 *@return
	 * @author ex_yangxiaoyi
	 * @see
	 */
	public static String getPayNo(String url,String xmlParam){
		logger.info("weixin"+"-PayNo:"+"start");
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
		HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
		String prepay_id = "";
		try {
			httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			HttpResponse response = httpclient.execute(httpost);
			String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			System.out.println(jsonStr);
			logger.info("weixin"+"-PayNo:"+jsonStr);
			if(jsonStr.indexOf("FAIL")!=-1){
				return prepay_id;
			}
			System.out.println("接收:" + jsonStr);
			Map map = doXMLParse(jsonStr);
			prepay_id  = (String) map.get("prepay_id");
		} catch (Exception e) {
			logger.error("获取预支付id异常",e);
		}
		return prepay_id;
	}

	/**
	 *description:微信请求调用
	 *@param url
	 *@param xmlParam
	 *@return
	 * @see
	 */
	public static Map<String, Object> weixinRequest(String url,String xmlParam) {
		logger.info("weixinRequest url:" + url + ",xmlParam:" + xmlParam);

		Map<String, Object> returnMap = Maps.newHashMap();
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
		HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);

		try {
			httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			HttpResponse response = httpclient.execute(httpost);
			String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			logger.info("weixinRequest 返回数据:" + jsonStr);

			returnMap = doXMLParse(jsonStr);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("weixinRequest 返回结果数据:" + JSONObject.fromObject(returnMap));
		return returnMap;
	}

	/**
	 *description:获取扫码支付连接
	 *@param url
	 *@param xmlParam
	 *@return
	 * @author ex_yangxiaoyi
	 * @see
	 */
	public static String getCodeUrl(String url,String xmlParam){
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
		HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
		String code_url = "";
		try {
			httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			HttpResponse response = httpclient.execute(httpost);
			String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			System.out.println(jsonStr);
			if(jsonStr.indexOf("FAIL")!=-1){
				return code_url;
			}
			Map map = doXMLParse(jsonStr);
			code_url  = (String) map.get("code_url");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code_url;
	}


	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * @param strxml
	 * @return
	 * @throws IOException
	 */
	public static Map<String, Object> doXMLParse(String strxml) throws Exception {
		if(null == strxml || "".equals(strxml)) {
			return null;
		}

		Map<String, Object> m = new HashMap();
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if(children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}

			m.put(k, v);
		}

		//关闭流
		in.close();

		return m;
	}
	/**
	 * 获取子结点的xml
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if(!children.isEmpty()) {
			Iterator it = children.iterator();
			while(it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if(!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}

		return sb.toString();
	}
	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}

}