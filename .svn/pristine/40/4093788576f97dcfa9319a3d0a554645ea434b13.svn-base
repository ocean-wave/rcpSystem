package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.DeviceEventMapper;
import cn.com.cdboost.collect.dao.DeviceInfoMapper;
import cn.com.cdboost.collect.dto.ExcAccountDTO;
import cn.com.cdboost.collect.dto.ExcAccountListDTO;
import cn.com.cdboost.collect.dto.QueryEventDTO;
import cn.com.cdboost.collect.dto.StatisticsDTO;
import cn.com.cdboost.collect.dto.param.QueryEventVo;
import cn.com.cdboost.collect.model.DeviceEvent;
import cn.com.cdboost.collect.model.DeviceInfo;
import cn.com.cdboost.collect.service.DeviceEventService;
import cn.com.cdboost.collect.service.GenerateFileService;
import cn.com.cdboost.collect.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 异常事件接口类
 */
@Service("deviceEventService")
public class DeviceEventServiceImpl extends BaseServiceImpl<DeviceEvent> implements DeviceEventService {
    private static final Logger logger = LoggerFactory.getLogger(DeviceEventServiceImpl.class);

    @Autowired
    private DeviceEventMapper deviceEventMapper;
    @Autowired
    private DeviceInfoMapper deviceInfoMapper;
    @Autowired
    private GenerateFileService generateFileService;
    @Override
    public List<DeviceEvent> queryList(DeviceEvent DeviceEvent) {

        return deviceEventMapper.select(DeviceEvent);
    }

    @Override
    public List<DeviceEvent> batchQueryByCustomerNos(String deviceInfo, String eventCategory, String eventStatus, String startDate, String endDate, String customerNos, List<String> cnolist) throws ParseException {
        Condition condition = new Condition(DeviceEvent.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("cno", cnolist);
        if (!StringUtil.isEmpty(customerNos)) {
            criteria.andLike("customerNo", customerNos);
        }
        if (!StringUtil.isEmpty(eventStatus)) {
            criteria.andLike("eventFlag", eventStatus);
        }
        if (!StringUtil.isEmpty(eventCategory)) {
            criteria.andLike("eventCategory", eventCategory);
        }
        if (!StringUtil.isEmpty(deviceInfo)) {
            Condition condition1 = new Condition(DeviceInfo.class);
            Example.Criteria criteria1 = condition.createCriteria();
            criteria.andLike("deviceNo", deviceInfo);
            deviceInfoMapper.selectByCondition(condition1);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(endDate);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        //把日期往后增加一天.整数往后推,负数往前移动
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();
        criteria.andBetween("createTime", startDate, date);
        return deviceEventMapper.selectByCondition(condition);
    }

    @Override
    public List<QueryEventDTO> getDeviceEvent(QueryEventVo queryEventVo) {
        if(queryEventVo.getDeviceNo()==null){
            queryEventVo.setDeviceNo("");
        }
        if(queryEventVo.getCustomerName()==null){
            queryEventVo.setCustomerName("");
        }
        if(queryEventVo.getInstallAddr()==null){
            queryEventVo.setInstallAddr("");
        }
        if(queryEventVo.getEventCategory()==null){
            queryEventVo.setEventCategory("");
        }
        if(queryEventVo.getEventStatus()==null){
            queryEventVo.setEventStatus("");
        }

        List<QueryEventDTO> queryEventDTOList = deviceEventMapper.getDeviceEventcall(queryEventVo);
        for (QueryEventDTO queryEventDTO : queryEventDTOList) {
            queryEventDTO.setCno(null);
            queryEventDTO.setAlarmReason(null);
        }
        return queryEventDTOList;
    }

    @Override
    @Transactional
    public int updateDeviceEvent(QueryEventDTO queryEventDTO) {
        return deviceEventMapper.updateDeviceEvent(queryEventDTO);
    }

    @Override
    public ExcAccountDTO queryExcAccountList(String id,String permissionId,String startDate, String endDate,String deviceType) {
        ExcAccountDTO excAccountDTO=new ExcAccountDTO();
        StatisticsDTO statisticsDTO=new StatisticsDTO();
        List<ExcAccountListDTO> deviceEventList = deviceEventMapper.queryExcAccountListDTO(startDate,endDate,permissionId, Integer.valueOf(id),deviceType);
        Integer accountNum=0;
        Integer totalDeal = 0;
        Integer totalUnDeal=0;
        for (ExcAccountListDTO excAccountListDTO : deviceEventList) {
            accountNum+=excAccountListDTO.getAccountNum();
            totalDeal+=excAccountListDTO.getDealNum();
            totalUnDeal+=excAccountListDTO.getUndealNum();
        }

        statisticsDTO.setAccountNum(accountNum);
        statisticsDTO.setDealNum(totalDeal);
        statisticsDTO.setUndealNum(totalUnDeal);
        excAccountDTO.setStatistics(statisticsDTO);
        excAccountDTO.setList(deviceEventList);
        return excAccountDTO;
    }

    @Override
    public XSSFWorkbook downloadExcelDeviceList(String name, List<ExcAccountListDTO> queryEventVo,ExcAccountDTO excAccountDTO)  {

        XSSFWorkbook sheets = generateFileService.generateDeviceEventExcel(name, queryEventVo,excAccountDTO);

        return sheets;
    }
}
