package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.dao.UserMapper;
import cn.com.cdboost.collect.model.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("customRealm")
public class CustomRealm extends AuthorizingRealm {
	@Autowired
	private UserMapper userMapper;
	
	//授权方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//认证方法
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String loginName = (String) token.getPrincipal();
		User saftyUser = null;
		try{
			User param = new User();
			param.setLoginName(loginName);
			param.setIsEnabled(1);
			saftyUser= userMapper.selectOne(param);
		} catch (Exception E) {
			E.printStackTrace();
		}
		if(saftyUser == null){
			return null;
		}
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(saftyUser.getLoginName(), saftyUser.getUserPassword(),"customRealm");
		return simpleAuthenticationInfo;
	}
;
}
