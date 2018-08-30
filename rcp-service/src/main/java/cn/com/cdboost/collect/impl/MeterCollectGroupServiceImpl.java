package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.MeterCollectGroupMapper;
import cn.com.cdboost.collect.dto.MeterCollectGroupDto;
import cn.com.cdboost.collect.dto.param.QueryPr0Vo;
import cn.com.cdboost.collect.model.MeterCollectGroup;
import cn.com.cdboost.collect.model.UserOrg;
import cn.com.cdboost.collect.service.MeterCollectGroupService;
import cn.com.cdboost.collect.service.OrgService;
import cn.com.cdboost.collect.service.UserOrgService;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 客户正向有功总报表
 */
@Service("meterCollectGroupService")
public class MeterCollectGroupServiceImpl extends BaseServiceImpl<MeterCollectGroup> implements MeterCollectGroupService {

    @Autowired
    MeterCollectGroupMapper meterCollectGroupMapper;
    @Autowired
    private UserOrgService userOrgService;
    @Autowired
    private OrgService orgService;
    @Override
    public List<MeterCollectGroupDto> queryPr0(Integer userId,QueryPr0Vo queryVo) {
        // 查询用户的管辖权限
        List<UserOrg> userOrgs = userOrgService.queryByUserId(userId);
        Set<Long> orgNoSet = Sets.newHashSet();
        for (UserOrg userOrg : userOrgs) {
            orgNoSet.add(userOrg.getOrgNo());
        }

        // 根据管辖权限，查询用户
        List<Long> orgNoList = orgService.queryDataOrg(orgNoSet);
        queryVo.setOrgNoList(orgNoList);
        queryVo.setStart((queryVo.getPageNumber() - 1) * queryVo.getPageSize());
        queryVo.setStartDate(queryVo.getStartDate()+" 00:00:00");
        queryVo.setEndDate(queryVo.getEndDate()+" 24:00:00");
        //查询分页数据
        List<MeterCollectGroupDto> meterCollectGroupDtos = meterCollectGroupMapper.queryPr0(queryVo);
        //查询总条数
        queryVo.setTotal(meterCollectGroupMapper.queryTotal(queryVo).longValue());
        return meterCollectGroupDtos;
    }
}
