package cn.com.cdboost.collect.mock;

import cn.com.cdboost.collect.dto.OrgTreeInfo;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/19 0019.
 */
public class OrgMockData {
    @Test
    public void queryOrgTreeInfo () {
        Result<List<OrgTreeInfo>> result = new Result<>();
        List<OrgTreeInfo> list = Lists.newArrayList();
        OrgTreeInfo info1 = new OrgTreeInfo();
        info1.setId(1);
        info1.setCreateTime(new Date());
        info1.setCreateUserId(1L);
        info1.setFirstPinyin("sdfs");
        info1.setFullPinyin("sdfsd");
        info1.setIsEnabled(1);
        info1.setLevel(1);
        info1.setLevelCode("/1000");
        info1.setSortNo(1);
        info1.setOrgNo(1000L);
        info1.setOrgName("成都博高");
        info1.setpOrgNo(0L);

        List<OrgTreeInfo> children = Lists.newArrayList();
        OrgTreeInfo info2 = new OrgTreeInfo();
        info2.setId(2);
        info2.setCreateTime(new Date());
        info2.setCreateUserId(1L);
        info2.setFirstPinyin("FDGD");
        info2.setFullPinyin("DSFfsd");
        info2.setIsEnabled(1);
        info2.setLevel(2);
        info2.setLevelCode("/1000/1001");
        info2.setSortNo(1);
        info2.setOrgNo(1001L);
        info2.setOrgName("采购组");
        info2.setpOrgNo(1000L);

        OrgTreeInfo info3 = new OrgTreeInfo();
        info3.setId(3);
        info3.setCreateTime(new Date());
        info3.setCreateUserId(1L);
        info3.setFirstPinyin("FDGdsD");
        info3.setFullPinyin("DSFfdssd");
        info3.setIsEnabled(1);
        info3.setLevel(2);
        info3.setLevelCode("/1000/1002");
        info3.setSortNo(1);
        info3.setOrgNo(1002L);
        info3.setOrgName("销售组");
        info3.setpOrgNo(1000L);

        children.add(info2);
        children.add(info3);

        info1.setChildren(children);

        list.add(info1);
        result.setData(list);
        System.out.println(JSON.toJSONString(result));

    }
}
