package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.constant.ChargeConstant;
import cn.com.cdboost.collect.dao.ChargingPayChemeMapper;
import cn.com.cdboost.collect.dao.ChargingUseDetailedMapper;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.ChargerSchemeEditParam;
import cn.com.cdboost.collect.dto.param.ChargerSchemeQueryVo;
import cn.com.cdboost.collect.dto.param.OffOnSchemeParam;
import cn.com.cdboost.collect.dto.response.ChargingSchemeInfo;
import cn.com.cdboost.collect.dto.response.PofitableCountInfo;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.ChargingPayCheme;
import cn.com.cdboost.collect.service.ChargingPayChemeService;
import cn.com.cdboost.collect.util.DateUtil;
import cn.com.cdboost.collect.util.StringUtil;
import cn.com.cdboost.collect.util.UuidUtil;
import com.google.common.base.Function;
import com.google.common.collect.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 充值方案信息表接口实现类
 */
@Service
public class ChargingPayChemeServiceImpl extends BaseServiceImpl<ChargingPayCheme> implements ChargingPayChemeService {

    @Autowired
    private ChargingPayChemeMapper chargingPayChemeMapper;
    @Autowired
    private ChargingUseDetailedMapper chargingUseDetailedMapper;

    @Override
    public PofitableCountInfo countDayPofitable(ChargerSchemeQueryVo queryVo) {
        PofitableCountInfo pofitableCountInfo = new PofitableCountInfo();
        List<String> xData = Lists.newArrayList();
        List<BigDecimal> yPofitableData  = Lists.newArrayList();
        //转换成开始和结束时间
        this.getTime(queryVo);
        List<SchemePofitableListDto> list = chargingUseDetailedMapper.querySchemePofitable(queryVo);
        for (SchemePofitableListDto schemePofitableListDto : list) {
            xData.add(schemePofitableListDto.getDayStr());
            yPofitableData.add(schemePofitableListDto.getProfitable());
        }
        return pofitableCountInfo;
    }

    @Override
    public List<ChargerSchemeDto> shemeList(ChargerSchemeQueryVo queryVo) {
        /*ChargingPayCheme param = new ChargingPayCheme();
        BeanUtils.copyProperties(queryVo, param);
        param.setIsEnable(ChargeConstant.SchemeIsEnable.ABLE.getType());*/
        Condition condition = new Condition(ChargingPayCheme.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("isEnable",ChargeConstant.SchemeIsEnable.ABLE.getType());
        criteria.andEqualTo("projectGuid",queryVo.getProjectGuid());
        criteria.andEqualTo("schemeType",queryVo.getSchemeType());
        criteria.andNotEqualTo("payCategory",ChargeConstant.SchemePayCategory.BALANCE_RECHARGE.getType());
        //按项目查询所有可用的，非活动的方案
        List<ChargingPayCheme> chargingPayChemes = chargingPayChemeMapper.selectByCondition(condition);
        //返回对象集合
        List<ChargerSchemeDto> chargerSchemeDtos = Lists.newArrayList();

        //按功率分组
        ImmutableListMultimap<Integer, ChargingPayCheme> mulPayCheme = Multimaps.index(chargingPayChemes, new Function<ChargingPayCheme, Integer>() {
            @Nullable
            @Override
            public Integer apply(@Nullable ChargingPayCheme chargingPayCheme) {
                return chargingPayCheme.getMaxPower() + chargingPayCheme.getMinPower();
            }
        });
        //遍历分组后的集合，组装返回对象
        ImmutableSet<Integer> keys = mulPayCheme.keySet();
        for (Integer key : keys) {
            //对应功率下的方案
            ImmutableList<ChargingPayCheme> chargingPayChemes1 = mulPayCheme.get(key);
            List<ChargingSchemeDto> chargingSchemeDtos = Lists.newArrayList();
            ChargerSchemeDto chargerSchemeDto = new ChargerSchemeDto();
            for (ChargingPayCheme chargingPayCheme : chargingPayChemes1) {
                ChargingSchemeDto dto = new ChargingSchemeDto();
                BeanUtils.copyProperties(chargingPayCheme, dto);
                chargingSchemeDtos.add(dto);
                BeanUtils.copyProperties(chargingPayCheme, chargerSchemeDto);
                chargerSchemeDto.setSchemeList(chargingSchemeDtos);
            }
            chargerSchemeDtos.add(chargerSchemeDto);
        }
        return chargerSchemeDtos;
    }

    @Override
    public List<ChargingUseDetailedDto> shemeUseList(ChargerSchemeQueryVo queryVo) {
        if (StringUtil.isEmpty(queryVo.getSchemeGuid())){
            throw new BusinessException("schemeGuid为空");
        }
        //转换成开始和结束时间
        this.getTime(queryVo);
        queryVo.setPageIndex((queryVo.getPageNumber() - 1) * queryVo.getPageSize());
        List<ChargingUseDetailedDto> useDetailedDtos = chargingPayChemeMapper.shemeUseList(queryVo);

        //查询总数
        Integer total = chargingPayChemeMapper.queryTotal(queryVo);
        queryVo.setTotal(total.longValue());
        return useDetailedDtos;
    }

    @Override
    public List<SchemePofitableDto> countPofitable(ChargerSchemeQueryVo queryVo) {
        //转换成开始和结束时间
        this.getTime(queryVo);
        return chargingPayChemeMapper.countPofitable(queryVo);
    }

    /**
     * 将前端传的统计类型转换成开始和结束时间
     * @param queryVo
     * @return
     */
    private ChargerSchemeQueryVo getTime(ChargerSchemeQueryVo queryVo){
        Integer countType = queryVo.getCountType();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        c.setTime(now);
        queryVo.setEndTime(DateUtil.formatDate(now));
        switch (countType){
            case 1:
                //过去七天
                c.add(Calendar.DATE, - 7);
                Date d = c.getTime();
                String day = format.format(d);
                queryVo.setBeginTime(day);
                break;
            case 2:
                //过去一月
                c.add(Calendar.MONTH, -1);
                Date m = c.getTime();
                String mon = format.format(m);
                queryVo.setBeginTime(mon);
                break;
            case 3:
                //过去三个月
                c.add(Calendar.MONTH, -3);
                Date m3 = c.getTime();
                String mon3 = format.format(m3);
                queryVo.setBeginTime(mon3);
        }
        return queryVo;
    }

    @Override
    @Transactional
    public void edit(ChargerSchemeEditParam param, Integer id) {
        List<ChargingSchemeDto> chargingSchemeDtos = param.getSchemeList();
        if (!CollectionUtils.isEmpty(chargingSchemeDtos)){
            //新增的方案
            List<ChargingPayCheme> addScheme = Lists.newArrayList();
            //删除的方案
            List<String> deleteScheme = Lists.newArrayList();
            //停用或启用的方案
            List<ChargingPayCheme> offOnScheme = Lists.newArrayList();
            for (ChargingSchemeDto chargingSchemeDto : chargingSchemeDtos) {
                ChargingPayCheme chargingPayCheme = new ChargingPayCheme();
                if (chargingSchemeDto.getFlag() != null) {
                    if (chargingSchemeDto.getFlag() == 0) {//判断该方案是新增还是删除 0-新增 1-删除 2-修改
                        BeanUtils.copyProperties(chargingSchemeDto, chargingPayCheme);

                        //暂时写死月卡为1个月
                        if (chargingSchemeDto.getPayCategory() == ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType()){
                            chargingPayCheme.setNumMonths(1);
                        }else {
                            chargingPayCheme.setNumMonths(0);
                        }
                        chargingPayCheme.setSchemeGuid(UuidUtil.getUuid());
                        //chargingPayCheme.setIsEnable(ChargeConstant.SchemeIsEnable.ABLE.getType());
                        //后端写死排序，月卡排前面
                        if (chargingSchemeDto.getPayCategory() == ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType()) {
                            chargingPayCheme.setSortNo(1);
                        } else if (chargingSchemeDto.getPayCategory() == ChargeConstant.SchemePayCategory.RECHARGE_FULL.getType()) {
                            chargingPayCheme.setSortNo(2);
                        } else if (chargingSchemeDto.getPayCategory() == ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getType()) {
                            chargingPayCheme.setSortNo(3);
                        } else {
                            chargingPayCheme.setSortNo(4);
                        }
                        addScheme.add(chargingPayCheme);
                    } else if (chargingSchemeDto.getFlag() == 1) {
                        deleteScheme.add(chargingSchemeDto.getSchemeGuid());
                    } else if (chargingSchemeDto.getFlag() == 2) {
                    //删除编辑前的方案
                    deleteScheme.add(chargingSchemeDto.getSchemeGuid());
                    //新增编辑后的方案
                    BeanUtils.copyProperties(chargingSchemeDto, chargingPayCheme);
                    chargingPayCheme.setSchemeGuid(UuidUtil.getUuid());
                    //chargingPayCheme.setIsEnable(ChargeConstant.SchemeIsEnable.ABLE.getType());
                    //后端写死排序，月卡排前面
                    if (chargingSchemeDto.getPayCategory() == ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType()) {
                        chargingPayCheme.setSortNo(1);
                    } else if (chargingSchemeDto.getPayCategory() == ChargeConstant.SchemePayCategory.RECHARGE_FULL.getType()) {
                        chargingPayCheme.setSortNo(2);
                    } else if (chargingSchemeDto.getPayCategory() == ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getType()) {
                        chargingPayCheme.setSortNo(3);
                    } else {
                        chargingPayCheme.setSortNo(4);
                    }
                    addScheme.add(chargingPayCheme);
                    } else if (chargingSchemeDto.getFlag() == 3) {
                        BeanUtils.copyProperties(chargingSchemeDto,chargingPayCheme);
                        offOnScheme.add(chargingPayCheme);
                    }
                }
            }
            if (!CollectionUtils.isEmpty(deleteScheme)){
                this.updateSchemeIsEnable(deleteScheme,ChargeConstant.SchemeIsEnable.UNABLE.getType());
            }
            if (!CollectionUtils.isEmpty(addScheme)) {
                //插入新方案
                chargingPayChemeMapper.insertList(addScheme);

                /*//插入相同的ic卡充电方案
                List<ChargingPayCheme> addIcScheme = Lists.newArrayList();
                for (ChargingPayCheme chargingPayCheme : addScheme) {
                    if (chargingPayCheme.getSchemeType()==1){
                        chargingPayCheme.setPayCategory(5);
                        addIcScheme.add(chargingPayCheme);
                    }
                }
                if (!CollectionUtils.isEmpty(addIcScheme)){
                    chargingPayChemeMapper.insertList(addIcScheme);
                }*/
            }
            if (!CollectionUtils.isEmpty(offOnScheme)) {
                //修改方案状态
                chargingPayChemeMapper.batchUpdate(offOnScheme);
            }
        }
    }

    /**
     * 修改数据库方案状态
     * @param schemeGuids 方案唯一标识
     * @param isEnable
     */
    private void updateSchemeIsEnable(List<String> schemeGuids, Integer isEnable){
        ChargingPayCheme chargingPayCheme = new ChargingPayCheme();
        chargingPayCheme.setIsEnable(isEnable);
        Condition condition = new Condition(ChargingPayCheme.class);
        Condition.Criteria criteria = condition.createCriteria();
        criteria.andIn("schemeGuid",schemeGuids);
        chargingPayChemeMapper.updateByConditionSelective(chargingPayCheme, condition);
    }

    @Override
    @Transactional
    public void offOnScheme(OffOnSchemeParam param, Integer id) {
        ChargingPayCheme payCheme = new ChargingPayCheme();
        if (param.getOnOrOff() == 0){
            payCheme.setIsEnable(ChargeConstant.SchemeIsEnable.ABLE.getType());
        }else if (param.getOnOrOff() == 1){
            payCheme.setIsEnable(ChargeConstant.SchemeIsEnable.UNABLE.getType());
        }
        //payCheme.setUpdateTime(new Date());
        //批量修改充电方案状态
        Condition condition = new Condition(ChargingPayCheme.class);
        Condition.Criteria criteria = condition.createCriteria();
        criteria.andIn("schemeGuid",param.getSchemeGuids());
        chargingPayChemeMapper.updateByConditionSelective(payCheme, condition);
    }

    @Override
    public ChargingSchemeInfo queryDetailByProjectGuid(String projectGuid) {
        //查询数据库
        List<ChargingPayCheme> payChemeList = chargingPayChemeMapper.queryPower(projectGuid);
        Set<Integer> powerSet = Sets.newTreeSet();
        for (ChargingPayCheme chargingPayCheme : payChemeList) {
            powerSet.add(chargingPayCheme.getMinPower());
            powerSet.add(chargingPayCheme.getMaxPower());
        }
        ChargingSchemeInfo chargingSchemeInfo = new ChargingSchemeInfo();
        chargingSchemeInfo.setPower(powerSet);
        return chargingSchemeInfo;
    }

    @Override
    public List<ChargingPayCheme> queryMonthSchemeList() {
        Condition condition = new Condition(ChargingPayCheme.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("payCategory", ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType());
        criteria.andEqualTo("isEnable",1);
        condition.setOrderByClause("sort_no ASC");
        return chargingPayChemeMapper.selectByCondition(condition);
    }

    @Override
    public List<ChargingPayCheme> querySchemeList4ChargePage(String projectGuid) {
        Condition condition = new Condition(ChargingPayCheme.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("projectGuid", projectGuid);
        criteria.andEqualTo("isEnable",1);
        criteria.andEqualTo("schemeType",0);
        Set<Integer> set = Sets.newHashSet();
        set.add(ChargeConstant.SchemePayCategory.BALANCE_RECHARGE.getType());
        set.add(ChargeConstant.SchemePayCategory.IC_RECHARGE.getType());
        criteria.andNotIn("payCategory", set);
        condition.setOrderByClause("power,sort_no,money ASC");
        return chargingPayChemeMapper.selectByCondition(condition);
    }

    @Override
    public List<ChargingPayCheme> querySchemeTwoList(String projectGuid) {
        Condition condition = new Condition(ChargingPayCheme.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("projectGuid", projectGuid);
        criteria.andEqualTo("isEnable",1);
        criteria.andEqualTo("schemeType",1);
        criteria.andEqualTo("payCategory",ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getType());
        condition.setOrderByClause("power,money ASC");
        return chargingPayChemeMapper.selectByCondition(condition);
    }

    @Override
    public List<ChargingPayCheme> querySchemeList4ActivityPage() {
        Condition condition = new Condition(ChargingPayCheme.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("payCategory",ChargeConstant.SchemePayCategory.BALANCE_RECHARGE.getType());
        criteria.andEqualTo("isEnable",1);
        criteria.andEqualTo("schemeType",0);
        condition.setOrderByClause("money ASC");
        return chargingPayChemeMapper.selectByCondition(condition);
    }

    @Override
    public ChargingPayCheme queryMonthCardScheme() {
        ChargingPayCheme param = new ChargingPayCheme();
        param.setPayCategory(ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType());
        param.setIsEnable(1);
        return chargingPayChemeMapper.selectOne(param);
    }

    @Override
    public ChargingPayCheme queryBySchemeGuid(String schemeGuid) {
        ChargingPayCheme param = new ChargingPayCheme();
        param.setSchemeGuid(schemeGuid);
        return chargingPayChemeMapper.selectOne(param);
    }

    @Override
    public List<ChargingPayCheme> batchQuery(List<String> schemeGuids) {
        Condition condition = new Condition(ChargingPayCheme.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("schemeGuid",schemeGuids);

        return chargingPayChemeMapper.selectByCondition(condition);
    }

    @Override
    public ChargingPayCheme querySuitableByPower(String projectGuid, Integer schemeType,Integer payCategory, Integer chargeTime, Integer power) {
        Condition condition = new Condition(ChargingPayCheme.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("projectGuid",projectGuid);
        criteria.andEqualTo("payCategory",payCategory);
        criteria.andEqualTo("chargingTime",chargeTime);
        criteria.andEqualTo("isEnable",1);
        criteria.andEqualTo("schemeType",schemeType);
        criteria.andLessThanOrEqualTo("minPower",power);
        criteria.andGreaterThanOrEqualTo("maxPower",power);
        List<ChargingPayCheme> chemes = chargingPayChemeMapper.selectByCondition(condition);
        if (CollectionUtils.isEmpty(chemes)) {
            return null;
        }

        return chemes.get(0);
    }

    @Override
    public ChargingPayCheme querySuitableScheme2ByPower(String projectGuid, Integer schemeType, Integer payCategory, BigDecimal money, Integer power) {
        Condition condition = new Condition(ChargingPayCheme.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("projectGuid",projectGuid);
        criteria.andEqualTo("payCategory",payCategory);
        criteria.andEqualTo("money",money);
        criteria.andEqualTo("isEnable",1);
        criteria.andEqualTo("schemeType",schemeType);
        criteria.andLessThanOrEqualTo("minPower",power);
        criteria.andGreaterThanOrEqualTo("maxPower",power);
        List<ChargingPayCheme> chemes = chargingPayChemeMapper.selectByCondition(condition);
        if (CollectionUtils.isEmpty(chemes)) {
            return null;
        }

        return chemes.get(0);
    }
}
