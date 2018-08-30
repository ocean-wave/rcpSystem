package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.cache.CustomerCacheVo;
import cn.com.cdboost.collect.cache.DeviceCacheVo;
import cn.com.cdboost.collect.cache.DeviceMapCacheVo;
import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.constant.DictCodeConstant;
import cn.com.cdboost.collect.dao.QuerySchemeMapper;
import cn.com.cdboost.collect.dao.QuerySchememetMapper;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.model.DictItem;
import cn.com.cdboost.collect.model.QueryScheme;
import cn.com.cdboost.collect.model.QuerySchememet;
import cn.com.cdboost.collect.service.DictItemService;
import cn.com.cdboost.collect.service.QuerySchemeService;
import cn.com.cdboost.collect.service.RedisService;
import cn.com.cdboost.collect.util.DateUtil;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.*;


/**
 * 查询方案服务接口实现类
 */
@Service
public class QuerySchemeServiceImpl extends BaseServiceImpl<QueryScheme> implements QuerySchemeService {

    @Autowired
    QuerySchemeMapper querySchemeMapper;
    @Autowired
    QuerySchememetMapper querySchememetMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private DictItemService dictItemService;

    @Override
    @Transactional
    public int deleteBySchemeFlag(String schemeFlag) {
        QueryScheme queryScheme = new QueryScheme();
        queryScheme.setSchemeFlag(schemeFlag);
        //删除方案表
        int delete = querySchemeMapper.delete(queryScheme);
        //设置方案详情表删除条件
        Condition condition = new Condition(QuerySchememet.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("schemeFlag",schemeFlag);
        //删除方案设备表
        int i = querySchememetMapper.deleteByCondition(condition);
        if (delete > 0 && i > 0){
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public int deleteByIds(Collection<Integer> ids) {
        String idStr = Joiner.on(",").join(ids);
        return querySchemeMapper.deleteByIds(idStr);
    }

    @Override
    public List<QuerySchemeListInfo> queryList(QuerySchemeVo querySchemeVo) {
        querySchemeVo.setStart((querySchemeVo.getPageNumber() - 1) * querySchemeVo.getPageSize());
        List<QuerySchemeDto> querySchemeDtos = querySchemeMapper.queryList(querySchemeVo);
        //返回方案列表数据集合
        List<QuerySchemeListInfo> querySchemeListInfos = Lists.newArrayList();

        //查字典表
        List<DictItem> dictCode = dictItemService.findByDictCode(DictCodeConstant.SCHEME_TYPE.getDictCode());

        // 转map
        Map<String,String> dictMap = new HashMap<>();
        for (DictItem dictItem : dictCode) {
            dictMap.put(dictItem.getDictItemValue(),dictItem.getDictItemName());
        }

        //赋值给前端需要的对象
        for (QuerySchemeDto querySchemeDto:querySchemeDtos){
            QuerySchemeListInfo querySchemeListInfo = new QuerySchemeListInfo();
            BeanUtils.copyProperties(querySchemeDto,querySchemeListInfo);
            querySchemeListInfo.setDesc(querySchemeDto.getSchemeRemark());
            String type = dictMap.get(String.valueOf(querySchemeDto.getSchemeType()));
            querySchemeListInfo.setSchemeType(querySchemeDto.getSchemeType());
            querySchemeListInfo.setSchemeTypeName(type);
            querySchemeListInfos.add(querySchemeListInfo);
        }

        //查询总数
        Long count = querySchemeMapper.selectTotal(querySchemeVo);
        querySchemeVo.setTotal(count);
        return querySchemeListInfos;
    }

    @Override
    public List<QueryByCnosDto> queryByCnos(String[] cnos) {
        //实例化结果集
        List<QueryByCnosDto> queryByCnosDto = new ArrayList<QueryByCnosDto>();
        //实例化用户编号集合
        Set<String> customerNoSet = new HashSet<>();
        //从redis中获取设备数据
        Map<String,DeviceCacheVo> deviceDataList = redisService.queryDeviceCacheMap(Arrays.asList(cnos));
        //从redis中获取对应的客户设备关系记录缓存
        Map<String,DeviceMapCacheVo> deviceMapCacheList = redisService.queryDeviceMapCacheMap(Arrays.asList(cnos));
        //设置数据
        if((deviceDataList!=null&&deviceDataList.size()>0)||(deviceMapCacheList!=null&&deviceMapCacheList.size()>0)){
            for(int i=0;i<cnos.length;i++){
                QueryByCnosDto queryByCnosDtoL = new QueryByCnosDto(); //实例化结果对象
                if(!StringUtils.isEmpty(deviceDataList.get(cnos[i]))){    //判断是否取到需要的数
                    queryByCnosDtoL.setDeviceNo(deviceDataList.get(cnos[i]).getDeviceNo()); //设置设备编号
                    queryByCnosDtoL.setInstallAddr(deviceDataList.get(cnos[i]).getInstallAddr()); //设置安装地址
                    queryByCnosDtoL.setCno(deviceDataList.get(cnos[i]).getCno()); //设置设备cno

                }
                if(!StringUtils.isEmpty(deviceMapCacheList.get(cnos[i]))){
                    //设置楼栋编号
                    queryByCnosDtoL.setBuildingNo(deviceMapCacheList.get(cnos[i]).getBuildingNo());
                    //设置变压器号
                    queryByCnosDtoL.setTransformerNo(deviceMapCacheList.get(cnos[i]).getTransformerNo());
                    //设置表计户号
                    queryByCnosDtoL.setMeterUserNo(deviceMapCacheList.get(cnos[i]).getMeterUserNo());
                    //用户编号放入集合
                    customerNoSet.add(deviceMapCacheList.get(cnos[i]).getCustomerNo());
                    //设置用户编号
                    queryByCnosDtoL.setCustomerNo(deviceMapCacheList.get(cnos[i]).getCustomerNo());
                }
                if(queryByCnosDtoL!=null){
                    //加入结果集
                    queryByCnosDto.add(queryByCnosDtoL);
                }
            }
            if(customerNoSet!=null&&customerNoSet.size()>0){
                //从redis中获取用户信息
                Map<String,CustomerCacheVo> customerCacheList = redisService.queryCustomerCacheMap(customerNoSet);
                //判断是否满足条件
                if(customerCacheList!=null&&customerCacheList.size()>0){
                    for(int i= 0;i<queryByCnosDto.size();i++){
                        //判断是否满足条件
                        if(!StringUtils.isEmpty(customerCacheList.get(queryByCnosDto.get(i).getCustomerNo()))){
                            //设置客户名称
                             queryByCnosDto.get(i).setCustomerName(customerCacheList.get(queryByCnosDto.get(i).getCustomerNo()).getCustomerName());
                        }
                    }
                }

            }
        }
        return queryByCnosDto;
    }

    @Override
    public int addScheme(AddSchemeParam addSchemeParam,Integer createId) {
        //设置日期格式
        java.text.SimpleDateFormat format_date = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.text.SimpleDateFormat format_time = new java.text.SimpleDateFormat("HH:mm:ss");
        java.text.SimpleDateFormat format_date_time = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //实例化增加的方案对象
        QueryScheme queryScheme = new QueryScheme();
        List<QuerySchememet> querySchememetList = new ArrayList<>();
        //设置数据
        queryScheme.setSchemeName(addSchemeParam.getSchemeName()); //设置方案名称
        queryScheme.setSchemeType(addSchemeParam.getSchemeType()); //设置方案类型
        //判断是否有设备
        if(addSchemeParam.getMeterList()!=null&&addSchemeParam.getMeterList().size()>0)
           queryScheme.setUserCount(addSchemeParam.getMeterList().size());//设置电表数量
        queryScheme.setCreateUserId(createId);

        //设置方案标识
        queryScheme.setSchemeFlag(UUID.randomUUID().toString().replace("-",""));
        try {
            //设置创建时间
            queryScheme.setCreateTime(format_date_time.parse(DateUtil.CurrentDateTime()));
            //设置通阀起始日期
            if(!StringUtils.isEmpty(addSchemeParam.getStartDate()))
             queryScheme.setOnDate(format_date.parse(addSchemeParam.getStartDate()));
            //设置通阀起始时间
            if(!StringUtils.isEmpty(addSchemeParam.getStartTime()))
             queryScheme.setOnTime(format_time.parse(addSchemeParam.getStartTime()));
            //设置断阀起始日期
            if(!StringUtils.isEmpty(addSchemeParam.getEndDate()))
             queryScheme.setOffDate(format_date.parse(addSchemeParam.getEndDate()));
            //设置断阀起始时间
            if(!StringUtils.isEmpty(addSchemeParam.getEndTime()))
             queryScheme.setOffTime(format_time.parse(addSchemeParam.getEndTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        queryScheme.setSchemeRemark(addSchemeParam.getRemark()); //设置方案备注
        //保存数据
        int isSuccess = querySchemeMapper.insertSelective(queryScheme);
        //判断数据是否保存成功
        if(isSuccess!=0){
            //遍历方案详数组，保存数据
            for(AddSchemeNodeParam addSchemeNodeParam: addSchemeParam.getMeterList()){
                //实例化增加的对象
                QuerySchememet querySchememet = new QuerySchememet();
                //设置方案标识
                querySchememet.setSchemeFlag(queryScheme.getSchemeFlag());
                //设置设备编号
                querySchememet.setCno(addSchemeNodeParam.getCno());
                //设置表计户号
                querySchememet.setMeterUserNo(addSchemeNodeParam.getMeterUserNo());
                //设置用户编号
                querySchememet.setCustomerNo(addSchemeNodeParam.getCustomerNo());
                //设置创建人员ID
                querySchememet.setCreateUserId(createId);
                try {
                    //设置创建时间
                    querySchememet.setCreateTime(format_date_time.parse(DateUtil.CurrentDateTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                querySchememetList.add(querySchememet);
            }
            //判断将插入的数组是否为空
            if(querySchememetList!=null&&querySchememetList.size()>0){
                //插入数据
                int fg = querySchememetMapper.insertList(querySchememetList);
                return fg;
            }else{
                return 0;
            }

        }else{
            return isSuccess;
        }
    }

    @Override
    public int editScheme(EditSchemeParam editSchemeParam, Integer createId) {
        //设置日期格式
        java.text.SimpleDateFormat format_date = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.text.SimpleDateFormat format_time = new java.text.SimpleDateFormat("HH:mm:ss");
        java.text.SimpleDateFormat format_date_time = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //实例化修改的方案对象
        QueryScheme queryScheme = new QueryScheme();
        //删除操作集合
        List<QuerySchememet> queryDeleteSchememetList = new ArrayList<>();
        //新增操作集合
        List<QuerySchememet> queryInsertSchememetList = new ArrayList<>();
        //设置数据
        queryScheme.setSchemeName(editSchemeParam.getSchemeName()); //设置方案名称
        queryScheme.setSchemeType(editSchemeParam.getSchemeType()); //设置方案类型

        try {
            //设置通阀起始日期
            if(!StringUtils.isEmpty(editSchemeParam.getStartDate()))
                queryScheme.setOnDate(format_date.parse(editSchemeParam.getStartDate()));
            //设置通阀起始时间
            if(!StringUtils.isEmpty(editSchemeParam.getStartTime()))
                queryScheme.setOnTime(format_time.parse(editSchemeParam.getStartTime()));
            //设置断阀起始日期
            if(!StringUtils.isEmpty(editSchemeParam.getEndDate()))
                queryScheme.setOffDate(format_date.parse(editSchemeParam.getEndDate()));
            //设置断阀起始时间
            if(!StringUtils.isEmpty(editSchemeParam.getEndTime()))
                queryScheme.setOffTime(format_time.parse(editSchemeParam.getEndTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        queryScheme.setSchemeRemark(editSchemeParam.getRemark()); //设置方案备注
        //设置条件
        Condition condition=new Condition(QueryScheme.class);
        condition.createCriteria().andCondition("scheme_flag = '"+editSchemeParam.getSchemeFlag()+"'");
        //查询方案数据
        List<QueryScheme> querySchemeList = querySchemeMapper.selectByCondition(condition);
        //判断是否为空
        if(querySchemeList!=null&&querySchemeList.size()>0){
            //获取电表总数
            int meterTotal = querySchemeList.get(0).getUserCount();
            //判断是否有需要修改的设备
            if(editSchemeParam.getMeterList()!=null&&editSchemeParam.getMeterList().size()>0){
                //遍历获取设备数量
                for(EditSchemeNodeParam editSchemeNodeParam: editSchemeParam.getMeterList()){
                    //标识为0标识删除设备
                    if(editSchemeNodeParam.getFlag()==0){
                        meterTotal = meterTotal-1;
                    }else if(editSchemeNodeParam.getFlag()==1){  //标识为1标识新增设备
                        meterTotal = meterTotal+1;
                    }
                }
            }
            //设置电表总数
            queryScheme.setUserCount(meterTotal);
        }
        //修改数据
        int isSuccess = querySchemeMapper.updateByConditionSelective(queryScheme,condition);
        int insert_fg = 0;
        int delete_fg = 0;
        //判断数据是否修改成功
        if(isSuccess!=0){
            //判断是否满足设备修改的条件
            if(editSchemeParam.getMeterList()!=null&&editSchemeParam.getMeterList().size()>0){
                //遍历方案详数组，编辑数据
                for(EditSchemeNodeParam editSchemeNodeParam: editSchemeParam.getMeterList()){
                    //实例化增加的对象
                    QuerySchememet querySchememet = new QuerySchememet();
                    //设置设备编号
                    querySchememet.setCno(editSchemeNodeParam.getCno());
                    //设置方案详情ID
                    querySchememet.setId(editSchemeNodeParam.getId());
                    //设置表计户号
                    querySchememet.setMeterUserNo(editSchemeNodeParam.getMeterUserNo());
                    //设置用户编号
                    querySchememet.setCustomerNo(editSchemeNodeParam.getCustomerNo());
                    //设置方案标识
                    querySchememet.setSchemeFlag(editSchemeParam.getSchemeFlag());
                    //判断标识  1标识新增，0标识删除，没有修改的前端不需要提交后端
                    if(editSchemeNodeParam.getFlag()==0){
                        queryDeleteSchememetList.add(querySchememet);
                    }else if(editSchemeNodeParam.getFlag()==1){
                        //设置创建人员ID
                        querySchememet.setCreateUserId(createId);
                        try {
                            //设置创建时间
                            querySchememet.setCreateTime(format_date_time.parse(DateUtil.CurrentDateTime()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        queryInsertSchememetList.add(querySchememet);
                    }

                }
                //判断将插入的数组是否为空
                if(queryInsertSchememetList!=null&&queryInsertSchememetList.size()>0){
                    //插入数据
                    insert_fg = querySchememetMapper.insertList(queryInsertSchememetList);
                }
                //判断修改的数组是否为空
                if(queryDeleteSchememetList!=null&&queryDeleteSchememetList.size()>0){
                    String schememetIds="";
                    for(int i=0;i<queryDeleteSchememetList.size();i++){
                        String schememetId =Integer.toString(queryDeleteSchememetList.get(i).getId());
                        schememetIds = schememetIds+schememetId+",";
                    }
                    if(schememetIds!=""){
                        String ids = schememetIds.trim().substring(0,schememetIds.trim().length()-1);
                        delete_fg = querySchememetMapper.deleteByIds(ids);
                    }
                }
                if(delete_fg!=0||insert_fg!=0){
                    return 1;
                }else{
                    return isSuccess;
                }
            }else{
                return isSuccess;
            }
        }else{
            return isSuccess;
        }
    }

    @Override
    public List<DeviceCountDto> deviceCount(String cno) {
        //实例化结果集
        List<DeviceCountDto> resultList = new ArrayList<>();
        //实例化查询对象
        DeviceCountVo deviceCountVo = new DeviceCountVo();
        //设置查询参数
        deviceCountVo.setCno(cno);
        //查询数据
        List<DeviceCountDbDto> deviceCountDbDtos = querySchemeMapper.queryDeviceCount(deviceCountVo);
        //判断是否查询到数据
        if(deviceCountDbDtos!=null&&deviceCountDbDtos.size()>0){
            //遍历判断获取需要的数据
            for(DeviceCountDbDto deviceCountDbDto:deviceCountDbDtos){
                if(deviceCountDbDto!=null){
                    //判断是否满足断电条件
                    if(!StringUtils.isEmpty(deviceCountDbDto.getEndDate())||!StringUtils.isEmpty(deviceCountDbDto.getEndTime())){
                        //实例化中间对象
                        DeviceCountDto deviceCountDto = new DeviceCountDto();
                        deviceCountDto.setIsOn("断电");
                        if(deviceCountDbDto.getEndDate()!=null)
                            deviceCountDto.setSchemeDate(deviceCountDbDto.getEndDate());
                        if(deviceCountDbDto.getEndTime()!=null)
                            deviceCountDto.setSchemeTime(deviceCountDbDto.getEndTime());
                        resultList.add(deviceCountDto);
                    }
                    //判断是否满足通电条件
                    if(!StringUtils.isEmpty(deviceCountDbDto.getStartDate())||!StringUtils.isEmpty(deviceCountDbDto.getStartDate())){
                        //实例化中间对象
                        DeviceCountDto deviceCountDto = new DeviceCountDto();
                        deviceCountDto.setIsOn("通电");
                        if(deviceCountDbDto.getStartDate()!=null)
                            deviceCountDto.setSchemeDate(deviceCountDbDto.getStartDate());
                        if(deviceCountDbDto.getStartTime()!=null)
                            deviceCountDto.setSchemeTime(deviceCountDbDto.getStartTime());
                        resultList.add(deviceCountDto);
                    }
                }

            }
        }
        return resultList;
    }

    @Override
    public List<DeviceUserListDto> deviceUserList(DeviceUserListVo deviceUserListVo) {
        List<DeviceUserListDto> list = querySchemeMapper.queryDeviceUserList(deviceUserListVo);
        return list;
    }

    @Override
    public Long queryDeviceUserListTotal(DeviceUserListVo deviceUserListVo) {
        Long total = querySchemeMapper.queryDeviceTotal(deviceUserListVo);
        return total;
    }

    @Override
    public List<QueryScheme> batchQueryBySchemeFlags(Collection<String> schemeFlags) {
        Condition condition = new Condition(QueryScheme.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("schemeFlag",schemeFlags);
        return querySchemeMapper.selectByCondition(condition);
    }

    @Override
    public int updateUserCountByIds(List<QueryScheme> list) {
        return querySchemeMapper.updateUserCountByIds(list);
    }
}
