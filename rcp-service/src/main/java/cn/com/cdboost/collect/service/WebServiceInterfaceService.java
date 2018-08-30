package cn.com.cdboost.collect.service;

import net.sf.json.JSONObject;

/**
 * webservice服务接口
 */
public interface WebServiceInterfaceService {


	/**
	 * 注册节点
	 * @param obj
	 * @return 结果说明
	 */
	String RegeditMote(JSONObject obj);


	/**
	 * 删除节点
	 * @param moteEUI
	 * @param cusID
	 * @return 结果说明
	 */
	String DelMote(String moteEUI, String cusID);
	
}  