package cn.com.cdboost.collect.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;


/**
 * 前端Ajax操作返回类
 * @author Administrator
 *
 */
public class AjaxUtils {

	/**
	 * 将object转化JSON格式字符串，不带状态数据status
	 * @param obj object对象
	 * @return Json字符串
	 */
	public static String toCustom(Object obj){ 
		return JSON.toJSONString(obj,SerializerFeature.DisableCircularReferenceDetect);
	}
}
