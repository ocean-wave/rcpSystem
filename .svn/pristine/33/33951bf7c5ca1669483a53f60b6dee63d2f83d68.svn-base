package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.param.OrgInforVo;
import cn.com.cdboost.collect.model.Org;

import java.util.List;

public interface OrgMapper extends CommonMapper<Org> {
    // 根据用户ID获取组织信息
    List<Org> selectByUserId(Long userId);
    Integer insertOrg(OrgInforVo orgInforVo);
    int batchUpdate(List list);
}