package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.constant.ChargeConstant;
import cn.com.cdboost.collect.dao.ChargingDeviceMapper;
import cn.com.cdboost.collect.dao.ChargingPayChemeMapper;
import cn.com.cdboost.collect.dao.ChargingProjectMapper;
import cn.com.cdboost.collect.dto.MonthlySchemeDto;
import cn.com.cdboost.collect.dto.ProjectDto;
import cn.com.cdboost.collect.dto.TemporarySchemeDto;
import cn.com.cdboost.collect.dto.param.ChargerProjectEditParam;
import cn.com.cdboost.collect.dto.param.ChargerProjectSchemeAddParam;
import cn.com.cdboost.collect.dto.param.ChargerSchemeQueryVo;
import cn.com.cdboost.collect.dto.ChargingProjectDto;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.ChargingDevice;
import cn.com.cdboost.collect.model.ChargingPayCheme;
import cn.com.cdboost.collect.model.ChargingProject;
import cn.com.cdboost.collect.service.ChargingProjectService;
import cn.com.cdboost.collect.util.StringUtil;
import cn.com.cdboost.collect.util.UuidUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class ChargingProjectServiceImpl extends BaseServiceImpl<ChargingProject> implements ChargingProjectService {
    @Autowired
    private ChargingProjectMapper chargingProjectMapper;
    @Autowired
    private ChargingPayChemeMapper chargingPayChemeMapper;
    @Autowired
    private ChargingDeviceMapper chargingDeviceMapper;

    @Override
    @Transactional
    public void deleteProject(List<String> projectGuids, Integer id) {
        Condition conditionDevice = new Condition(ChargingDevice.class);
        Condition.Criteria criteriaDevice = conditionDevice.createCriteria();
        criteriaDevice.andIn("projectGuid",projectGuids);
        int i = chargingDeviceMapper.selectCountByCondition(conditionDevice);
        if (i>0){
            throw new BusinessException("项目下还有设备，不能删除！");
        }

        ChargingProject chargingProject = new ChargingProject();
        chargingProject.setIsDel(ChargeConstant.IsDel.DEL.getStatus());
        Condition condition = new Condition(ChargingProject.class);
        Condition.Criteria criteria = condition.createCriteria();
        criteria.andIn("projectGuid",projectGuids);
        chargingProjectMapper.updateByConditionSelective(chargingProject, condition);
    }

    @Override
    @Transactional
    public void edit(ChargerProjectEditParam param, Integer id) {
        //项目名去重
        Condition condition1 = new Condition(ChargingProject.class);
        Example.Criteria criteria1 = condition1.createCriteria();
        criteria1.andNotEqualTo("projectGuid",param.getProjectGuid());
        criteria1.andEqualTo("projectName",param.getProjectName());
        int i = chargingProjectMapper.selectCountByCondition(condition1);
        if (i > 0){
            throw new BusinessException("该项目名已存在！");
        }

        //修改项目
        ChargingProject project = new ChargingProject();
        Condition condition = new Condition(ChargingProject.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("projectGuid",param.getProjectGuid());
        BeanUtils.copyProperties(param,project);
        project.setIsDel(ChargeConstant.IsDel.NOTDEL.getStatus());
        project.setPrice(project.getBasePrice().add(project.getUpPrice()));
        project.setUpdateUserId(id.longValue());
        project.setUpdateTime(new Date());

        chargingProjectMapper.updateByConditionSelective(project,condition);
    }

    @Override
    @Transactional
    public void add(ChargerProjectSchemeAddParam param, Integer id) {
        ChargingProject project = new ChargingProject();
        project.setProjectName(param.getProjectName());
        project.setIsDel(ChargeConstant.IsDel.NOTDEL.getStatus());
        //判断项目名是否存在
        List<ChargingProject> select = chargingProjectMapper.select(project);
        if (select.size() > 0){
            throw new BusinessException("该项目名已存在！");
        }
        BeanUtils.copyProperties(param,project);
        String projectGuid = UuidUtil.getUuid();
        project.setProjectGuid(projectGuid);
        project.setCreateUserId(id.longValue());
        project.setPrice(project.getBasePrice().add(project.getUpPrice()));
        //插入项目信息表
        chargingProjectMapper.insertSelective(project);

        //插入包月方案
        MonthlySchemeDto monthly = param.getMonthly();
        if (monthly != null){
            ChargingPayCheme payCheme = new ChargingPayCheme();
            payCheme.setPayCategory(ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType());
            payCheme.setChargingCnt(monthly.getMonthlyCnt());
            payCheme.setMoney(monthly.getMonthlyPrice().multiply(new BigDecimal(monthly.getMonthlyCnt())));
            payCheme.setChargingTime(monthly.getMonthlyTime());
            payCheme.setSchemeGuid(UuidUtil.getUuid());
            payCheme.setProjectGuid(projectGuid);
            payCheme.setSortNo(1);
            payCheme.setPower(monthly.getPowerType());
            payCheme.setIsEnable(ChargeConstant.SchemeIsEnable.ABLE.getType());
            chargingPayChemeMapper.insertSelective(payCheme);
        }

        //插入临充方案
        List<TemporarySchemeDto> temporary = param.getTemporary();
        for (TemporarySchemeDto temporarySchemeDto : temporary) {
            ChargingPayCheme payCheme2 = new ChargingPayCheme();
            payCheme2.setSchemeGuid(UuidUtil.getUuid());
            payCheme2.setProjectGuid(projectGuid);
            if (temporarySchemeDto.getTime() == -1){//传-1标识一次充满
                payCheme2.setPayCategory(ChargeConstant.SchemePayCategory.RECHARGE_FULL.getType());
                payCheme2.setChargingTime(8);
            }else {
                payCheme2.setPayCategory(ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getType());
                payCheme2.setChargingTime(temporarySchemeDto.getTime());
            }
            payCheme2.setMoney(temporarySchemeDto.getPrice());
            payCheme2.setIsEnable(ChargeConstant.SchemeIsEnable.ABLE.getType());
            payCheme2.setSortNo(2);
            payCheme2.setPower(temporarySchemeDto.getPowerType());
            chargingPayChemeMapper.insertSelective(payCheme2);
        }
    }

    @Override
    public List<ProjectDto> queryAllProject() {
        //查询所有项目
        ChargingProject project = new ChargingProject();
        project.setIsDel(ChargeConstant.IsDel.NOTDEL.getStatus());
        List<ChargingProject> chargingProjects = chargingProjectMapper.select(project);
        List<ProjectDto> projectDtos = Lists.newArrayList();
        for (ChargingProject chargingProject:chargingProjects){
            ProjectDto projectDto = new ProjectDto();
            BeanUtils.copyProperties(chargingProject,projectDto);
            projectDtos.add(projectDto);
        }
        return projectDtos;
    }

    @Override
    public ChargingProject queryByProjectGuid(String projectGuid) {
        ChargingProject param = new ChargingProject();
        param.setProjectGuid(projectGuid);
        return chargingProjectMapper.selectOne(param);
    }

    @Override
    public ChargingProject queryProjectInfo() {
        List<ChargingProject> chargingProjects = chargingProjectMapper.selectAll();
        ChargingProject chargingProject = chargingProjects.get(0);
        return chargingProject;
    }

    @Override
    public List<ChargingProjectDto> queryList(ChargerSchemeQueryVo queryVo) {
        //查询列表
        List<ChargingProjectDto> chargingProjectInfos = chargingProjectMapper.queryList(queryVo);

        Condition condition = new Condition(ChargingProject.class);
        Example.Criteria criteria = condition.createCriteria();
        if (!StringUtil.isEmpty(queryVo.getProjectName())){
            criteria.andLike("projectName",queryVo.getProjectName());
        }
        //设置总条数
        int total = chargingProjectMapper.selectCountByCondition(condition);
        queryVo.setTotal((long)total);
        return chargingProjectInfos;
    }
}