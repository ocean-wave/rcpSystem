package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.model.Org;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 组织服务查询服务单元测试
 */
public class OrgServiceReadTest extends BaseServiceReadTest{
    @Autowired
    private OrgService orgService;

    @Test
    public void queryChildren() {
        List<Org> orgs = orgService.queryChildren(1001L);
        System.out.println(orgs.size());
    }
}
