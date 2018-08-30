package cn.com.cdboost.collect.mock;

import cn.com.cdboost.collect.dto.response.BaseMenuInfo;
import cn.com.cdboost.collect.dto.response.MenuActionInfo;
import cn.com.cdboost.collect.dto.response.MenuTreeInfo;
import cn.com.cdboost.collect.model.RoleRight;
import cn.com.cdboost.collect.util.TreeParser;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * Created by Administrator on 2017/12/19 0019.
 */
public class MenuMockData {

    @Test
    public void listMainMenus() {
        Result<List<BaseMenuInfo>> result = new Result<>();
        List<BaseMenuInfo> list = Lists.newArrayList();
        BaseMenuInfo info = new BaseMenuInfo();
        info.setMenuId(100010L);
        info.setMenuIcon("collect");
        info.setMenuTitle("数据采集");
        info.setMenuUrl("/Main/Collection");
        info.setParentMenuId(0L);

        BaseMenuInfo info4 = new BaseMenuInfo();
        info4.setMenuId(100020L);
        info4.setMenuIcon("costControl");
        info4.setMenuTitle("费控管理");
        info4.setMenuUrl("/Main/Fee");
        info4.setParentMenuId(0L);

        BaseMenuInfo info2 = new BaseMenuInfo();
        info2.setMenuId(100030L);
        info2.setMenuIcon("file");
        info2.setMenuTitle("档案管理");
        info2.setMenuUrl("/Main/Record");
        info2.setParentMenuId(0L);

        BaseMenuInfo info3 = new BaseMenuInfo();
        info3.setMenuId(100040L);
        info3.setMenuIcon("systemSet");
        info3.setMenuTitle("系统管理");
        info3.setMenuUrl("/Main/System");
        info3.setParentMenuId(0L);

        list.add(info);
        list.add(info2);
        list.add(info3);
        list.add(info4);
        result.setData(list);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void queryAllMenuAction() {
        Result<List<MenuActionInfo>> result = new Result<>();
        List<MenuActionInfo> list = Lists.newArrayList();
        MenuActionInfo info = new MenuActionInfo();
        info.setMenuId(1);
        info.setActionId(1);
        info.setActionName("新增");

        MenuActionInfo info2 = new MenuActionInfo();
        info2.setMenuId(1);
        info2.setActionId(2);
        info2.setActionName("修改");

        MenuActionInfo info3 = new MenuActionInfo();
        info3.setMenuId(1);
        info3.setActionId(3);
        info3.setActionName("删除");

        MenuActionInfo info4 = new MenuActionInfo();
        info4.setMenuId(2);
        info4.setActionId(1);
        info4.setActionName("新增");

        MenuActionInfo info5 = new MenuActionInfo();
        info5.setMenuId(2);
        info5.setActionId(2);
        info5.setActionName("修改");

        list.add(info);
        list.add(info2);
        list.add(info3);
        list.add(info4);
        list.add(info5);
        result.setData(list);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void queryRolePermissions() {
        Result<List<RoleRight>> result = new Result<>();
        List<RoleRight> list = Lists.newArrayList();
        RoleRight right = new RoleRight();
        right.setId(1);
        right.setRoleId(1L);
        right.setMenuId(1L);
        right.setActionId(1L);

        RoleRight right2 = new RoleRight();
        right2.setId(2);
        right2.setRoleId(1L);
        right2.setMenuId(1L);
        right2.setActionId(2L);

        RoleRight right3 = new RoleRight();
        right3.setId(3);
        right3.setRoleId(1L);
        right3.setMenuId(1L);
        right3.setActionId(3L);

        list.add(right);
        list.add(right2);
        list.add(right3);
        result.setData(list);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void empty() {
        Result<List<MenuTreeInfo>> result = new Result<>();
        System.out.println(JSON.toJSONString(result));

        List<MenuTreeInfo> emptyList = Lists.newArrayList();
        List<MenuTreeInfo> treeList = TreeParser.getTreeList(0L, emptyList);
        System.out.println(treeList.size());
    }
}
