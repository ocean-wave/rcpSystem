package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.model.DeviceConnParam;

import java.util.List;


/**
 * 设备连接参数接口服务
 */
public interface DeviceConnParamService extends BaseService<DeviceConnParam> {

	/**
	 * 根据cno，更新字段非空的实体对象，参数对象中cno必须有值
	 * @param deviceConnParam
	 * @throws Exception
	 */
	int updateSelectiveByCno(DeviceConnParam deviceConnParam);

	/**
	 * 通过cno查询设备连接参数实体对象
	 * @param cno
	 * @return
	 */
	DeviceConnParam selectByCno(String cno);

	// 根绝cnos列表，批量删除
	void batchDeleteByCnos(List<String> cnos);

	// 根绝cno，删除
	void deleteByCno(String cno);
}
