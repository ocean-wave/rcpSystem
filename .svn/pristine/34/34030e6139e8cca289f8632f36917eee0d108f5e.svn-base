package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.CollectDataDownInfo;
import cn.com.cdboost.collect.dto.response.UserFuzzyQueryInfo;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.CustomerDevMap;
import cn.com.cdboost.collect.model.CustomerInfo;
import cn.com.cdboost.collect.model.DeviceEvent;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

/**
 * 用户档案接口服务
 */
public interface DeviceEventService extends BaseService<DeviceEvent>{

	// 列表查询
	List<DeviceEvent> queryList(DeviceEvent deviceEvent);
	List<DeviceEvent> batchQueryByCustomerNos(String deviceNo,String eventCategory, String eventStatus, String startDate,String endDate,String cno,List<String> cnolist) throws ParseException;
	//事件查询
	List<QueryEventDTO> getDeviceEvent(QueryEventVo queryEventVo);
	//事件更新
	int updateDeviceEvent(QueryEventDTO queryEventDTO);

	ExcAccountDTO queryExcAccountList(String id,String permissionId, String startDate,String endDate,String deviceType);
	XSSFWorkbook downloadExcelDeviceList(String name ,List<ExcAccountListDTO> queryEventVo,ExcAccountDTO excAccountDTO) throws Exception;
}
