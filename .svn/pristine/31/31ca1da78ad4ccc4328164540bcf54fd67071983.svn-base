package cn.com.cdboost.collect.controller;


import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.dto.param.UserAddParam;
import cn.com.cdboost.collect.dto.param.UserEditParam;
import cn.com.cdboost.collect.dto.param.UserGetQueryVo;
import cn.com.cdboost.collect.dto.param.UserQueryParam;
import cn.com.cdboost.collect.dto.response.UserQueryInfo;
import cn.com.cdboost.collect.enums.Action;
import cn.com.cdboost.collect.enums.Auth;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.model.User;
import cn.com.cdboost.collect.model.UserLog;
import cn.com.cdboost.collect.service.UserLogService;
import cn.com.cdboost.collect.service.UserService;
import cn.com.cdboost.collect.util.CryptographyUtil;
import cn.com.cdboost.collect.util.DESUtil;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author linyang
 * @date 2017年5月15日
 * @Description:用户操作Controller层
 */
@Controller
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private UserLogService userLogService;


	/**
	 * 用户新增
	 * @param session
	 * @param param
	 * @return
	 */
	@Auth(menuID=100042L,actionID={1L})
	@SystemControllerLog(description = "用户新增")
	@RequestMapping(value = "/add")
	@ResponseBody
	public String add(HttpSession session, @Valid @RequestBody UserAddParam param) {
		Result result = new Result();
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		Integer currentUserId = currentUser.getId();

		User user = new User();
		BeanUtils.copyProperties(param,user);
		user.setCreateUserId(Long.valueOf(currentUserId));
		if(param.getDataOrgList()==null||param.getDataOrgList().size()<1|| null==param.getDataOrgList().get(0)){
			result.error("管辖区域不能为空");
			return JSON.toJSONString(result);
		}
		userService.addUserInfo(user,param.getDataOrgList(),param.getRoleId());

		// 操作日志
		UserLog log = new UserLog();
		log.setUserId(Long.valueOf(currentUserId));
		log.setOptType(Action.ADD.getActionId());
		log.setOptObject("用户新增");
		log.setObjectKey("");
		log.setObjectKeyValue("");
		String content = "添加人员["+param.getUserName()+"]" ;
		log.setOptContent(content);
		log.setOptParam(JSON.toJSONString(param));
		userLogService.insertSelective(log);

		result.setMessage("添加成功");
		return JSON.toJSONString(result);
	}

	/**
	 * 用户修改
	 * @param session
	 * @param param
	 * @return
	 */
	@Auth(menuID=100042L,actionID={2L})
	@SystemControllerLog(description = "用户修改")
	@RequestMapping(value = "/edit")
	@ResponseBody
	public String edit(HttpSession session, @Valid @RequestBody UserEditParam param) {
		Result result = new Result();
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

		User user = new User();
		BeanUtils.copyProperties(param,user);
		user.setId(param.getUserId());
		if(param.getDataOrgList()==null||param.getDataOrgList().size()<1|| null==param.getDataOrgList().get(0)){
			result.error("管辖区域不能为空");
			return JSON.toJSONString(result);
		}
		userService.updateUserInfo(user, param.getDataOrgList(), param.getRoleId());

		// 操作日志
		UserLog log = new UserLog();
		log.setUserId(Long.valueOf(currentUser.getId()));
		log.setOptType(Action.MODIFY.getActionId());
		log.setOptObject("用户修改");
		log.setObjectKey("userId");
		log.setObjectKeyValue(String.valueOf( param.getUserId()));
		String content = "修改人员["+param.getUserName()+"]信息";
		log.setOptContent(content);
		log.setOptParam(JSON.toJSONString(param));
		userLogService.insertSelective(log);
		result.setMessage("修改成功");
		return JSON.toJSONString(result);
	}

	/**
	 * 批量删除用户
	 * @param session
	 * @param ids
	 * @return
	 */
	@Auth(menuID=100042L,actionID=3L)
	@SystemControllerLog(description = "批量删除用户")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public String delete(HttpSession session, @RequestBody Integer[] ids) {
		Result result = new Result();
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		List list=Lists.newArrayList();
		for (Integer id : ids) {
			final User user = userService.selectByPrimaryKey(id);
			list.add(user.getUserName());
		}

		Set<Integer> idSet = Sets.newHashSet(ids);
		userService.batchDeleteByIds(idSet,currentUser.getId());

		// 操作日志
		userLogService.create(currentUser.getId(), Action.DELETE.getActionId(),"用户删除操作","ids", "","删除人员"+list.toString(), JSON.toJSONString(ids));
		result.setMessage("删除成功");
		return JSON.toJSONString(result);
	}


	/**
	 * 用户列表查询
	 * @param session
	 * @param queryParam
	 * @return
	 */
	@Auth(menuID=100042L,actionID={1L,2L,3L,4L})
	@SystemControllerLog(description = "用户列表查询")
	@RequestMapping(value = "/queryList")
	@ResponseBody
	public String queryList(HttpSession session, @Valid @RequestBody UserQueryParam queryParam) {
		PageResult<List<UserQueryInfo>> result = new PageResult<>();
		UserGetQueryVo queryVo = new UserGetQueryVo();
		BeanUtils.copyProperties(queryParam,queryVo);
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		queryVo.setUserId(currentUser.getId());
		List<UserQueryInfo> list = userService.query(queryVo);
		result.setData(list);
		result.setTotal(queryVo.getRowCount());

		return JSON.toJSONString(result);
	}

	/**
	 * 登录用户，设置自己的新密码
	 * @param session
	 * @param newPassword
	 * @return
	 */
	@SystemControllerLog(description = "登录用户，设置自己的新密码")
	@RequestMapping(value="updatePassword")
	@ResponseBody
	public String updatePassword(HttpSession session,@RequestParam String oldPassword, @RequestParam String newPassword) {
		Result result = new Result("修改成功");
		if (StringUtils.isEmpty(oldPassword)) {
			result.error("参数oldPassword不能是空字符串");
			return JSON.toJSONString(result);
		}

		if (StringUtils.isEmpty(newPassword)) {
			result.error("参数newPassword不能是空字符串");
			return JSON.toJSONString(result);
		}
		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

		// 解密
		String oldPwd = null;
		String newPwd = null;
		try {
			oldPwd = DESUtil.decryption(oldPassword, GlobalConstant.SECRET_KEY);
			newPwd = DESUtil.decryption(newPassword, GlobalConstant.SECRET_KEY);
		} catch (Exception e) {
			logger.error("密码解密异常",e);
		}

		// 校验密码是否正确
		boolean flag = userService.validatePassword(currentUser.getId(), oldPwd);
		if (!flag) {
			result.error("旧密码错误");
			return JSON.toJSONString(result);
		}

		// 更新数据库记录
		userService.changeUserPassword(currentUser.getId(),newPwd);

		// 操作日志
		userLogService.create(currentUser.getId(), Action.MODIFY.getActionId(),"用户修改密码","list", "","用户["+currentUser.getUserName()+"]修改密码","用户修改密码 userID:" + currentUser.getId());
		return JSON.toJSONString(result);
	}

	// 修改密码时，检验密码是否正确
	@SystemControllerLog(description = "修改密码时，检验密码是否正确")
	@RequestMapping(value="/validate",method={RequestMethod.POST})
	@ResponseBody
	public String validate(HttpSession session, @RequestParam String password) {
		Result result = new Result();
		if (StringUtils.isEmpty(password)) {
			result.error("参数password不能为空");
			return JSON.toJSONString(result);
		}

		LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
		// 解密
		String pwd = null;
		try {
			pwd = DESUtil.decryption(password, GlobalConstant.SECRET_KEY);
		} catch (Exception e) {
			logger.error("密码解密异常",e);
		}

		password = CryptographyUtil.md5(pwd, GlobalConstant.SECRET_SALT);
		if(currentUser.getUserPassword().equals(password)){
			session.setAttribute("date",new Date());
		}else {
			result.error("密码不正确");
		}

		return JSON.toJSONString(result);
	}
}
