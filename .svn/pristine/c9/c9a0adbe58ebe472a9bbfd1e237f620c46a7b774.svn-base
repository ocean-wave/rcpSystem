package cn.com.cdboost.collect.mock;

import cn.com.cdboost.collect.dto.response.DictItemInfo;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * Created by Administrator on 2017/12/22 0022.
 */
public class DictItemMockData {

    @Test
    public void queryByDictCode() {
        Result<List<DictItemInfo>> result = new Result<>();
        List<DictItemInfo> list = Lists.newArrayList();
        DictItemInfo info = new DictItemInfo();
        info.setId(1);
        info.setDictItemName("普通用电");
        info.setDictItemValue("1");

        DictItemInfo info2 = new DictItemInfo();
        info2.setId(2);
        info2.setDictItemName("商业用电");
        info2.setDictItemValue("2");

        list.add(info);
        list.add(info2);
        result.setData(list);

        System.out.println(JSON.toJSONString(result));
    }
}
