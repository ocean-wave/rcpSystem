package cn.com.cdboost.collect.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能说明
 * <ul>
 * <li>创建文件描述：HttpUtils工具，doPost、doGet</li>
 * <li>修改文件描述：</li>
 * </ul>
 * 
 * @author <ul>
 *         <li>创建者：<a href="mailto:43840397@qq.com">蔡越锐 </a></li>
 *         <li>修改者：</li>
 *         </ul>
 * @version <ul>
 *          <li>创建版本：v1.0.0 日期：2011-11-19</li>
 *          <li>修改版本：</li>
 *          </ul>
 */
public class HttpUtils
{
	private int httpStatus = -1;
	private final int TIME_OUT = 60000;
	
	public String doHttpPost(String postUrl, String params, String charset, String Referer)
	{
		return doHttpPost(postUrl, params, charset, Referer, null);
	}

	public String doHttpPost(String postUrl, String params, String charset, String Referer, String ContentType)
	{
		System.out.println("系统使用HTTP-doHttpPost");
		int timeOut = TIME_OUT;
		charset = charset == null ? "UTF-8" : charset;
		StringBuffer buffer = new StringBuffer();
		buffer.append("发送失败");
		HttpURLConnection c = null;
		try
		{
			if (postUrl == null || "".equals(postUrl.trim()))
				throw new Exception("PostUrl不能为空");
			if (params == null || "".equals(params.trim()))
				throw new Exception("POST参数不能为空");
			if (Referer == null || "".equals(Referer.trim()))
				throw new Exception("商户域名不能为空");

			URL url = new URL(postUrl);
			c = (HttpURLConnection) url.openConnection();
			c.setRequestMethod("POST");
			c.setRequestProperty("Accept-Language", "zh-cn"); 
			c.setRequestProperty("Accept", "image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, application/x-shockwave-flash, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			if (ContentType == null || "".equals(ContentType.trim())) c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			else c.setRequestProperty("Content-Type", ContentType);
			c.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Mathon 2.0)");
			c.setRequestProperty("Connection", "Keep-Alive");
			c.setRequestProperty("Accept-Charset", charset);
			c.setRequestProperty("Charsert", charset);
			c.setRequestProperty("Referer", Referer);
			c.setDoOutput(true);
			c.setDoInput(true);
			c.setConnectTimeout(timeOut);// 设置连接主机超时（单位：毫秒）
			c.setReadTimeout(timeOut);// 设置从主机读取数据超时（单位：毫秒）
			c.connect();
			PrintWriter out = new PrintWriter(new OutputStreamWriter(c.getOutputStream(), charset));// 发送数据
			out.print(params);
			out.flush();
			out.close();
			String header;
			for (int i = 0; true; i++)
			{
				header = c.getHeaderField(i);
				if (header == null)
					break;
			}
			int res = 0;
			res = c.getResponseCode();
			this.httpStatus = res;
			InputStream u = c.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(u, charset));
			String line = "";
			buffer = new StringBuffer();
			char _line = 10;
			while ((line = in.readLine()) != null)
			{
				buffer.append(line).append(_line);
			}
			u.close();
			in.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			return ex.toString();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return ex.toString();
		}
		finally
		{
			if (c != null)
				c.disconnect();
		}
		return buffer.toString();
	}

	public Map<String, String> getParam(String httpParam)
	{
		String[] httpParamList = httpParam.split("&");
		Map<String, String> map = new HashMap<String, String>();
		for (String Param : httpParamList)
		{
			int flag = Param.indexOf("=");
			if (flag == -1)
				continue;
			String _key = Param.substring(0, flag);
			String _value = Param.substring(flag + 1, Param.length());
			map.put(_key, _value);
		}
		return map;
	}

	public int getHttpStatus()
	{
		return httpStatus;
	}
}
