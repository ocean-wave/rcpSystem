package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.UserLogMapper;
import cn.com.cdboost.collect.dao.UserMapper;
import cn.com.cdboost.collect.dto.param.CustomerLogParam;
import cn.com.cdboost.collect.model.User;
import cn.com.cdboost.collect.model.UserLog;
import cn.com.cdboost.collect.service.UserLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Condition;

/**
 * 用户操作日志服务接口实现类
 */
@Transactional
@Service("userLogService")
public class UserLogServiceImpl extends BaseServiceImpl<UserLog> implements UserLogService {

	@Autowired
	private UserLogMapper userLogMapper;
    @Autowired
	private UserMapper userMapper;
	@Override
	@Transactional
	public int create(long userID, int optType, String optObject, String objectKey, String objectKeyValue, String optContent,String optParam) {
		UserLog userLog = new UserLog();
		userLog.setUserId(userID);
		userLog.setCreateTime(new Date());
		userLog.setOptType(optType);
		userLog.setOptObject(optObject);
		userLog.setObjectKey(objectKey);
		userLog.setObjectKeyValue(objectKeyValue);
		userLog.setOptContent(optContent);
		userLog.setOptParam(optParam);
		return userLogMapper.insert(userLog);

	}

	@Override
	public PageInfo<UserLog> queryUserLog(CustomerLogParam customerLogParam) {
		tk.mybatis.mapper.entity.Condition condition=new tk.mybatis.mapper.entity.Condition(UserLog.class);
		Example.Criteria criteria=condition.createCriteria();

		criteria.andBetween("createTime",customerLogParam.getStartDate(),customerLogParam.getEndDate()+" 23:59:59");
		if(!StringUtils.isEmpty(customerLogParam.getUserId())){
			criteria.andEqualTo("userId",customerLogParam.getUserId());
		}if(!StringUtils.isEmpty(customerLogParam.getContent())){
			criteria.andLike("optContent","%"+customerLogParam.getContent()+"%");
		}
		 	criteria.andNotEqualTo("optObject","自动任务");
		PageHelper.startPage(customerLogParam.getPageNumber(),customerLogParam.getPageSize(),"id desc");
		List<UserLog> userLogs =  userLogMapper.selectByCondition(condition);
		for (UserLog userLog : userLogs) {
			User user = userMapper.selectByPrimaryKey(Integer.valueOf(Math.toIntExact(userLog.getUserId())));
			if(user!=null){
				userLog.setUserName(user.getUserName());
			}
		}
		PageInfo<UserLog> userLogPageInfo= new PageInfo<>(userLogs);
		return userLogPageInfo;
	}
}