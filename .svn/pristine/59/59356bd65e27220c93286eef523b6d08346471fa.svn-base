package cn.com.cdboost.collect.mock;

import cn.com.cdboost.collect.dto.param.UserAddParam;
import cn.com.cdboost.collect.dto.param.UserEditParam;
import cn.com.cdboost.collect.dto.param.UserQueryParam;
import cn.com.cdboost.collect.dto.param.UserSaveOrUpdateParam;
import cn.com.cdboost.collect.dto.response.UserQueryInfo;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户mock数据
 */
public class UserControllerMockData {

    @Test
    public void saveOrUpdateParam() {
        UserSaveOrUpdateParam param = new UserSaveOrUpdateParam();
        param.setUserId(0);
        param.setLoginName("zhangsan");
        param.setUserPassword("123456");
        param.setOrgNo(2L);
        param.setRoleId(2);
        param.setUserName("张三");
        param.setUserMail("763201424@qq.com");
        param.setUserMobile("13502154125");
        param.setRemark("备注哈哈");

        System.out.println(JSON.toJSON(param));
    }

    @Test
    public void addOrUpdateSuccess() {
        Result result = new Result();
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void queryListParam() {
        UserQueryParam param = new UserQueryParam();
        param.setUserName("张三");
        param.setRoleName("成都博高");
        param.setUserMobile("13214254857");
        param.setPageNumber(1);
        param.setPageSize(20);
        param.setSortName("create_time");
        param.setSortOrder("desc");

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void queryListReturn() {
        PageResult<List<UserQueryInfo>> result = new PageResult<>();
        List<UserQueryInfo> dataList = Lists.newArrayList();
        UserQueryInfo info1 = new UserQueryInfo();
        info1.setId(2);
        info1.setUserName("zsdfsd");
        info1.setLoginName("sdfdsf");
        info1.setUserPassword("21321");
        info1.setUserMobile("13512451425");
        info1.setUserMail("763215414@qq.com");
        info1.setIsEnabled(1);
        info1.setCreateTime(new Date());
        info1.setCreateUserId(1);
        info1.setRemark("");
        info1.setOrgNo(2);
        info1.setOrgName("成都博高");
        info1.setRoleId(2);
        info1.setRoleName("系统管理员");

        UserQueryInfo info2 = new UserQueryInfo();
        info2.setId(2);
        info2.setUserName("zsdfsd");
        info2.setLoginName("sdfdsf");
        info2.setUserPassword("21321");
        info2.setUserMobile("13512451425");
        info2.setUserMail("763215414@qq.com");
        info2.setIsEnabled(1);
        info2.setCreateTime(new Date());
        info2.setCreateUserId(1);
        info2.setRemark("");
        info2.setOrgNo(2);
        info2.setOrgName("成都博高");
        info2.setRoleId(2);
        info2.setRoleName("系统管理员");

        dataList.add(info1);
        dataList.add(info2);
        result.setData(dataList);
        result.setTotal(100L);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void userAdd() {
        UserAddParam param = new UserAddParam();
        param.setUserName("张三");
        param.setOrgNo(1000L);
        param.setRoleId(1);
        param.setUserMail("763514842@qq.com");
        param.setUserMobile("13512045874");
        param.setLoginName("admin");
        param.setUserPassword("123456");
        param.setRemark("hahaha");
        List<Long> dataOrgList = Lists.newArrayList();
        dataOrgList.add(1000L);
        dataOrgList.add(1001L);
        dataOrgList.add(1002L);
        param.setDataOrgList(dataOrgList);

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void userEdit() {
        UserEditParam param = new UserEditParam();
        param.setUserId(11);
        param.setUserName("李四");
        param.setOrgNo(1000L);
        param.setRoleId(22);
        param.setUserMail("45872154@qq.com");
        param.setUserMobile("13425487924");
        param.setRemark("sdfsd");
        List<Long> dataOrgList = Lists.newArrayList();
        dataOrgList.add(1000L);
        dataOrgList.add(1004L);
        dataOrgList.add(1003L);
        param.setDataOrgList(dataOrgList);
        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void userList() {
        PageResult<List<UserQueryInfo>> result = new PageResult<>();
        List<UserQueryInfo> list = new ArrayList<>();
        UserQueryInfo info = new UserQueryInfo();
        info.setId(1);
        info.setUserName("张三");
        info.setLoginName("admin");
        info.setUserPassword("123456");
        info.setUserMobile("13548796325");
        info.setUserMail("762548124@qq.com");
        info.setIsEnabled(1);
        info.setCreateTime(new Date());
        info.setCreateUserId(0);
        info.setRemark("hahja");
        info.setOrgNo(1000);
        info.setRoleName("系统管理员角色");
        info.setRoleId(1);
        info.setOrgName("成都博高");
        List<Long> dataOrgList = new ArrayList<>();
        dataOrgList.add(1000L);
        dataOrgList.add(1001L);
        dataOrgList.add(1002L);
        info.setDataOrgList(dataOrgList);
        list.add(info);
        result.setData(list);
        result.setTotal(1L);
        System.out.println(JSON.toJSONString(result));
    }
}
