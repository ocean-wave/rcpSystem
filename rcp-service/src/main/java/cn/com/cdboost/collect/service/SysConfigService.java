package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.ServiceHostPara;
import cn.com.cdboost.collect.dto.SmsConfigPara;
import cn.com.cdboost.collect.dto.SumDataGetDTO;
import cn.com.cdboost.collect.dto.SysConfigPara;
import cn.com.cdboost.collect.dto.param.GetForMonthQueryVo;
import cn.com.cdboost.collect.dto.param.SystemConfigParamVo;
import cn.com.cdboost.collect.model.SysConfig;

import java.util.List;

/**
 * 系统配置服务接口
 */
public interface SysConfigService extends BaseService<SysConfig> {
	String getConfigValue(String configName);
	/**
	 * 获取某类配置文件
	 * @param
	 * @return
	 */
	ServiceHostPara getServiceHostPara();
	
	/**
	 * 获取系统配置参数(图标，名称，背景图片，结算日)
	 * @return
	 * @throws Exception
	 */
	SysConfigPara getSysConfigPara();
	
	
	/**
	 * 发送短信配置参数
	 * @return
	 * @throws Exception
	 */
	SmsConfigPara getSmsConfigPara();

	/**
	 * 获取webSocket地址
	 * @return
	 */
	String getWebSocketUrl();

	/**
	 * 获取版权信息
	 * @return
	 */
	String getcopyRight();
	/**
	 * 通过配置名称查询对应实体
	 * @param configName
	 * @return
	 */
	SysConfig queryByConfigName(String configName);

	// 查询一年内每月的电、水、气的使用量
	List<SumDataGetDTO> sumGetForMonth(GetForMonthQueryVo queryVo);

	void batchUpdate(SystemConfigParamVo paramVo);
}
