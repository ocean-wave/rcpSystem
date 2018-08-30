package cn.com.cdboost.collect;

import cn.com.cdboost.collect.dto.response.MenuTreeInfo;
import cn.com.cdboost.collect.util.TreeParser;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/12/19 0019.
 */
public class TreeParserTest {

    @Test
    public void aaa() {
        List<MenuTreeInfo> list = Lists.newArrayList();
        MenuTreeInfo info = new MenuTreeInfo();
        info.setMenuId(100010L);
        info.setMenuIcon("collect");
        info.setMenuTitle("数据采集");
        info.setMenuUrl("/Main/Collection");
        info.setParentMenuId(0L);

        MenuTreeInfo info2 = new MenuTreeInfo();
        info2.setMenuId(100011L);
        info2.setMenuIcon("realData");
        info2.setMenuTitle("实时数据");
        info2.setMenuUrl("/Main/Measure");
        info2.setParentMenuId(100010L);

        MenuTreeInfo info3 = new MenuTreeInfo();
        info3.setMenuId(100012L);
        info3.setMenuIcon("historicalData");
        info3.setMenuTitle("历史数据");
        info3.setMenuUrl("/Main/HistoricalData");
        info3.setParentMenuId(100010L);


        MenuTreeInfo info4 = new MenuTreeInfo();
        info4.setMenuId(100020L);
        info4.setMenuIcon("costControl");
        info4.setMenuTitle("费控管理");
        info4.setMenuUrl("/Main/Fee");
        info4.setParentMenuId(0L);

        MenuTreeInfo info5 = new MenuTreeInfo();
        info5.setMenuId(100021L);
        info5.setMenuIcon("payment");
        info5.setMenuTitle("缴费记录");
        info5.setMenuUrl("/Main/Payment");
        info5.setParentMenuId(100020L);

        MenuTreeInfo info6 = new MenuTreeInfo();
        info6.setMenuId(100022L);
        info6.setMenuIcon("account");
        info6.setMenuTitle("用户开户");
        info6.setMenuUrl("/Main/Account");
        info6.setParentMenuId(100020L);

        list.add(info);
        list.add(info2);
        list.add(info3);
        list.add(info4);
        list.add(info5);
        list.add(info6);

        List<MenuTreeInfo> treeList = TreeParser.getTreeList(0L, list);
        System.out.println(JSON.toJSON(treeList));
    }
    @Test
    public void Test(){
        String Test="/1000/1002/1003";
        List list = CollectionUtils.arrayToList(Test.split("/"));
        ArrayList arrayList=new ArrayList(list);
        for (int i = 0; i < 3; i++) {
            arrayList.remove(0);
        }
        System.out.println(arrayList);
    }
}
