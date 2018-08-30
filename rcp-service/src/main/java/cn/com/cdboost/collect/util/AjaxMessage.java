package cn.com.cdboost.collect.util;

import java.io.Serializable;


/**
 * 消息定义
 * @author Administrator
 *
 */
public class AjaxMessage implements Serializable {
	public static final String getSuccess="获取成功";
	public static final String getFail="获取失败";
	public static final String getFinish="操作完成";
	
	
	public static final String saveSuccess="数据保存成功";
	public static final String saveFail="数据保存失败";
	
	
	public static final String operateSuccess="操作成功";
	public static final String operateFail="操作失败";
	public static final String operateFinish="操作完成";
	
	public static final String deleteSuccess="删除成功";
	public static final String deleteFail="删除失败";
	
	public static final String roleFail="没有该功能权限";
	
	public static final String noticeTrue="发布成功";
	public static final String noticeFalse="发布失败";
	
	public static final String skinFalse="更换失败";
	public static final String skinTrue="更换成功";
	
	public static final String paramerError="参数错误";
	public static final String dataIsNull="数据不存在";
	public static final String checkdUserName="该账号已存在\n请从新输入";
	public static final String checkdRoleName="该名称已存在\n请从新输入";
}
