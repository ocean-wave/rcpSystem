package cn.com.cdboost.collect.mock;

import cn.com.cdboost.collect.dto.BuildingTreeInfo;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * Created by Administrator on 2018/5/16 0016.
 */
public class TreeMockData {
    @Test
    public void buildingTree() {
        Result<BuildingTreeInfo> result = new Result<>();
        BuildingTreeInfo treeInfo = new BuildingTreeInfo();
        treeInfo.setNodeType(1);
        treeInfo.setNodeNo(1000L);
        treeInfo.setpNodeNo(0L);
        treeInfo.setNodeName("中天集团");
        treeInfo.setHouseHolds(20000L);

        List<BuildingTreeInfo> child2 = Lists.newArrayList();
        BuildingTreeInfo treeInfo1 = new BuildingTreeInfo();
        treeInfo1.setNodeNo(1001L);
        treeInfo1.setpNodeNo(1000L);
        treeInfo1.setNodeName("贵州片区");
        treeInfo1.setNodeType(1);
        treeInfo1.setHouseHolds(10000L);
        List<BuildingTreeInfo> child22 = Lists.newArrayList();
        BuildingTreeInfo b11 = new BuildingTreeInfo();
        b11.setNodeNo(1002L);
        b11.setpNodeNo(1001L);
        b11.setNodeName("项目一");
        b11.setNodeType(1);
        b11.setHouseHolds(5000L);

        List<BuildingTreeInfo> b11Child = Lists.newArrayList();
        BuildingTreeInfo bi1 = new BuildingTreeInfo();
        bi1.setNodeType(2);
        bi1.setNodeName("A区1栋");
        bi1.setpNodeNo(1002L);
        // TODO 默认都是0L
        bi1.setNodeNo(0L);
        bi1.setHouseHolds(1500L);

        BuildingTreeInfo bi2 = new BuildingTreeInfo();
        bi2.setNodeType(2);
        bi2.setNodeName("A区2栋");
        bi2.setpNodeNo(1002L);
        bi2.setNodeNo(0L);
        bi2.setHouseHolds(1200L);

        BuildingTreeInfo bi3 = new BuildingTreeInfo();
        bi3.setNodeType(2);
        bi3.setNodeName("B区1栋");
        bi3.setpNodeNo(1002L);
        bi3.setNodeNo(0L);
        bi3.setHouseHolds(1000L);

        BuildingTreeInfo bi4 = new BuildingTreeInfo();
        bi4.setNodeType(2);
        bi4.setNodeName("B区2栋");
        bi4.setpNodeNo(1002L);
        bi4.setNodeNo(0L);
        bi4.setHouseHolds(1300L);

        b11Child.add(bi1);
        b11Child.add(bi2);
        b11Child.add(bi3);
        b11Child.add(bi4);
        b11.setChildren(b11Child);

        List<BuildingTreeInfo> b12Child = Lists.newArrayList();
        BuildingTreeInfo b12 = new BuildingTreeInfo();
        b12.setNodeType(1);
        b12.setNodeNo(1003L);
        b12.setpNodeNo(1001L);
        b12.setNodeName("项目二");
        b12.setHouseHolds(5000L);

        BuildingTreeInfo bi12 = new BuildingTreeInfo();
        bi12.setNodeType(2);
        bi12.setpNodeNo(1003L);
        bi12.setNodeNo(0L);
        bi12.setNodeName("A区1栋");
        bi12.setHouseHolds(1000L);

        BuildingTreeInfo bi22 = new BuildingTreeInfo();
        bi22.setNodeType(2);
        bi22.setpNodeNo(1003L);
        bi22.setNodeNo(0L);
        bi22.setNodeName("A区2栋");
        bi22.setHouseHolds(1200L);

        BuildingTreeInfo bi32 = new BuildingTreeInfo();
        bi32.setNodeType(2);
        bi32.setpNodeNo(1003L);
        bi32.setNodeNo(0L);
        bi32.setNodeName("B区1栋");
        bi32.setHouseHolds(1500L);

        BuildingTreeInfo bi42 = new BuildingTreeInfo();
        bi42.setNodeType(2);
        bi42.setpNodeNo(1003L);
        bi42.setNodeNo(0L);
        bi42.setNodeName("B区2栋");
        bi42.setHouseHolds(1300L);

        b12Child.add(bi12);
        b12Child.add(bi22);
        b12Child.add(bi32);
        b12Child.add(bi42);

        b12.setChildren(b12Child);
        child22.add(b11);
        child22.add(b12);
        treeInfo1.setChildren(child22);
        child2.add(treeInfo1);
        treeInfo.setChildren(child2);

        result.setData(treeInfo);
        System.out.println(JSON.toJSONString(result));
    }
}
