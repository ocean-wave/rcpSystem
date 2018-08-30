package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.OrgAppMapper;
import cn.com.cdboost.collect.model.OrgApp;
import cn.com.cdboost.collect.service.OrgAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 组织APP服务接口实现类
 */
@Service
public class OrgAppServiceImpl extends BaseServiceImpl<OrgApp> implements OrgAppService {
    @Autowired
    private OrgAppMapper orgAppMapper;

    @Override
    public OrgApp queryByOrgNo(Long orgNo) {
        OrgApp param = new OrgApp();
        param.setOrgNo(orgNo);
        return orgAppMapper.selectOne(param);
    }
}
