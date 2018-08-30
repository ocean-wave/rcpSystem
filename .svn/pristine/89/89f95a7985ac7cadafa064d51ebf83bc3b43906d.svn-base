package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.param.CustomerLogParam;
import cn.com.cdboost.collect.model.UserLog;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 用户操作日志服务接口
 */
public interface UserLogService extends BaseService<UserLog>{

	/**
	 * 新建系统日志
	 * @throws Exception
	 */
	int create(long userID, int optType, String optObject, String objectKey, String objectKeyValue, String optContent,String optParam);

	PageInfo<UserLog> queryUserLog(CustomerLogParam customerLogParam);

}
