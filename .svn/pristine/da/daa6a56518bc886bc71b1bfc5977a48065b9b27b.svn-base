package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.dao.DeviceMeterParamMapper;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.CollectDataDownInfo;
import cn.com.cdboost.collect.dto.response.CollectDataGetInfo;
import cn.com.cdboost.collect.dto.response.ImpCollectDataGetInfo;
import cn.com.cdboost.collect.enums.DeviceType;
import cn.com.cdboost.collect.service.HistoricalDataService;
import cn.com.cdboost.collect.service.MeterCollectDataService;
import cn.com.cdboost.collect.service.SysConfigService;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.util.DateUtil;
import cn.com.cdboost.collect.util.MathUtil;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 历史数据服务接口实现类
 */
@Service("historicalDataService")
public class HistoricalDataServiceImpl implements HistoricalDataService {

	@Autowired
	private MeterCollectDataService meterCollectDataService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private DeviceMeterParamMapper deviceMeterParamMapper;
	@Override
	public List<CustomerData4Month> getDataList(RealTimeDataListParam realTimeDataListParam) {
		List<CustomerData4Month> lists = meterCollectDataService.getDataList(realTimeDataListParam);
		List<CustomerData4Month> emptyList = Lists.newArrayList();
		if (CollectionUtils.isEmpty(lists)) {
			return emptyList;
		}

		for (CustomerData4Month customerData4Month : lists) {
			// 处理精度
			BigDecimal pr0 = MathUtil.setPrecision(customerData4Month.getPr0());
			customerData4Month.setPr0(pr0);
			customerData4Month.setBalance(MathUtil.setPrecision(customerData4Month.getBalance()));
//			if(StringUtils.isEmpty(customerData4Month.getPayMoney())){
//				customerData4Month.setPayMoney(BigDecimal.ZERO);
//			}
			if(!StringUtils.isEmpty(customerData4Month.getDeviceNo())){
				customerData4Month.setDeviceNo(CNoUtil.getDeviceNoByCno(customerData4Month.getDeviceNo()));
			}
		}
		return lists;
	}
	// l读取历史数据 历史数据驼峰修改
	@Override
	public List<CollectDataGetInfo> listHistoricalData(CollectDataGetQueryVo queryVo) {
		String deviceNo = queryVo.getDeviceNo();
		if (deviceNo == null) {
			queryVo.setDeviceNo("");
		}

		String customerName = queryVo.getCustomerName();
		if (customerName == null) {
			queryVo.setCustomerName("");
		}

		String customerAddr = queryVo.getCustomerAddr();
		if (customerAddr == null) {
			queryVo.setCustomerAddr("");
		}

		String customerContact = queryVo.getCustomerContact();
		if (customerContact == null) {
			queryVo.setCustomerContact("");
		}

		String meterUserNo = queryVo.getMeterUserNo();
		if (meterUserNo == null) {
			queryVo.setMeterUserNo("");
		}

		String deviceType = queryVo.getDeviceType();
		if (deviceType == null) {
			queryVo.setDeviceType("");
		}

		String isRealTime = queryVo.getIsRealTime();
		if (isRealTime == null) {
			queryVo.setIsRealTime("");
		}

		String status = queryVo.getStatus();
		if (status == null) {
			queryVo.setStatus("");
		}

		String propertyName = queryVo.getPropertyName();
		if (propertyName == null) {
			queryVo.setPropertyName("");
		}

		String endDate = queryVo.getEndDate();
		if (endDate == null) {
			queryVo.setEndDate(DateUtil.CurrentDate());
		}
		String startDate = queryVo.getStartDate();
		if (startDate == null) {
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(calendar.MONTH, -1);// 把日期往前减一个月
			date = calendar.getTime();
			queryVo.setStartDate(format.format(date));
		}

		// 读取历史采集数据
		List<CollectDataGetInfo> dataGetInfos = meterCollectDataService.listMeterCollectData(queryVo);
		if (CollectionUtils.isEmpty(dataGetInfos)) {
			List<CollectDataGetInfo> emptyList = Lists.newArrayList();
			return emptyList;
		}

		for (CollectDataGetInfo meterCollectData :dataGetInfos){
			meterCollectData.setDeviceNo(CNoUtil.getNo(meterCollectData.getDeviceCno()));
			// 设置精度
			meterCollectData.setPr0(MathUtil.setPrecision(meterCollectData.getPr0()));
			meterCollectData.setBalance(MathUtil.setPrecision(meterCollectData.getBalance()));
			meterCollectData.setMonthFreezeP(MathUtil.setPrecision(meterCollectData.getMonthFreezeP()));
			meterCollectData.setDayFreezeP(MathUtil.setPrecision(meterCollectData.getDayFreezeP()));
		}
		return dataGetInfos;
	}

	@Override
	public List<ImpCollectDataGetInfo> listImpHistoricalData(ImpCollectDataGetQueryVo queryVo) {
		String deviceNo = queryVo.getDeviceNo();
		if (deviceNo == null) {
			queryVo.setDeviceNo("");
		}

		String customerName = queryVo.getCustomerName();
		if (customerName == null) {
			queryVo.setCustomerName("");
		}

		String customerAddr = queryVo.getCustomerAddr();
		if (customerAddr == null) {
			queryVo.setCustomerAddr("");
		}

		String customerContact = queryVo.getCustomerContact();
		if (customerContact == null) {
			queryVo.setCustomerContact("");
		}

		String meterUserNo = queryVo.getMeterUserNo();
		if (meterUserNo == null) {
			queryVo.setMeterUserNo("");
		}

		String propertyName = queryVo.getPropertyName();
		if (propertyName == null) {
			queryVo.setPropertyName("");
		}
		List<ImpCollectDataGetInfo> dataGetInfos = meterCollectDataService.listImpCollectHistoryData(queryVo);
		if (CollectionUtils.isEmpty(dataGetInfos)) {
			List<ImpCollectDataGetInfo> emptyList = Lists.newArrayList();
			return emptyList;
		}

		for (ImpCollectDataGetInfo dataGetInfo : dataGetInfos) {
			// 设置设备编号
			dataGetInfo.setDeviceNo(CNoUtil.getNo(dataGetInfo.getDeviceCno()));
			// 设置精度
//			dataGetInfo.setPr0(MathUtil.setPrecision(dataGetInfo.getPr0()));
//			dataGetInfo.setActiveTotal(MathUtil.setPrecision(dataGetInfo.getActiveTotal()));
//			dataGetInfo.setCurrentA(MathUtil.setPrecision(dataGetInfo.getCurrentA()));
//			dataGetInfo.setCurrentB(MathUtil.setPrecision(dataGetInfo.getCurrentB()));
//			dataGetInfo.setCurrentC(MathUtil.setPrecision(dataGetInfo.getCurrentC()));
//			dataGetInfo.setVoltageA(MathUtil.setPrecision(dataGetInfo.getVoltageA()));
//			dataGetInfo.setVoltageB(MathUtil.setPrecision(dataGetInfo.getVoltageB()));
//			dataGetInfo.setVoltageC(MathUtil.setPrecision(dataGetInfo.getVoltageC()));
//			dataGetInfo.setInstantPower(MathUtil.setPrecision(dataGetInfo.getInstantPower()));
//			dataGetInfo.setInstantPowerA(MathUtil.setPrecision(dataGetInfo.getInstantPowerA()));
//			dataGetInfo.setInstantPowerB(MathUtil.setPrecision(dataGetInfo.getInstantPowerB()));
//			dataGetInfo.setInstantPowerC(MathUtil.setPrecision(dataGetInfo.getInstantPowerC()));
		}

		return dataGetInfos;
	}

	@Override
	public List<CollectDataDownInfo> listHistoricalData4Download(CollectDataGetQueryVo queryVo) {
		String status = queryVo.getStatus();
		if (status == null) {
			queryVo.setStatus("");
		}
		String propertyName = queryVo.getPropertyName();
		if (propertyName == null) {
			queryVo.setPropertyName("");
		}

		String deviceNo = queryVo.getDeviceNo();
		if (deviceNo == null) {
			queryVo.setDeviceNo("");
		}
		String customerAddr = queryVo.getCustomerAddr();
		if (customerAddr == null) {
			queryVo.setCustomerAddr("");
		}
		String customerName = queryVo.getCustomerName();
		if (customerName == null) {
			queryVo.setCustomerName("");
		}
		String customerContact = queryVo.getCustomerContact();
		if (customerContact == null) {
			queryVo.setCustomerContact("");
		}
		String meterUserNo = queryVo.getMeterUserNo();
		if (meterUserNo == null) {
			queryVo.setMeterUserNo("");
		}
		String deviceType = queryVo.getDeviceType();
		if (deviceType == null) {
			queryVo.setDeviceType("");
		}
		String endDate = queryVo.getEndDate();
		if (endDate == null) {
			queryVo.setEndDate(DateUtil.CurrentDate());
		}
		String startDate = queryVo.getStartDate();
		if (startDate == null) {
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(calendar.MONTH, -1);// 把日期往前减一个月
			date = calendar.getTime();
			queryVo.setStartDate(format.format(date));
		}

		// 下载历史数据
		List<CollectDataDownInfo>  lists = meterCollectDataService.collectDataDown(queryVo);
		if (CollectionUtils.isEmpty(lists)) {
			List<CollectDataDownInfo> emptyList = Lists.newArrayList();
			return emptyList;
		}

		for (CollectDataDownInfo downInfo :lists){
			downInfo.setDeviceNo(CNoUtil.getNo(downInfo.getDeviceCno()));
			// 设置精度
			downInfo.setPr0(MathUtil.setPrecision(downInfo.getPr0()));
			downInfo.setBalance(MathUtil.setPrecision(downInfo.getBalance()));
			downInfo.setMonthFreezeP(MathUtil.setPrecision(downInfo.getMonthFreezeP()));
			downInfo.setDayFreezeP(MathUtil.setPrecision(downInfo.getDayFreezeP()));
			downInfo.setPower(MathUtil.setPrecision(downInfo.getPower()));
			downInfo.setRemainAmount(MathUtil.setPrecision(downInfo.getRemainAmount()));
		}

		return lists;
	}

	/**
	 * @Description 汇总每个月的各个类型的总数
	 * @param
	 * @return List<CollectAnalyzeData>
	 */
	@Override
	public CollectAnalyzeDataForMonthInfo listCollectAnalyzeDataForMonth(long userId, String customerNo, String deviceCno) {
        Calendar now = Calendar.getInstance();
		// 今年
        int thisYear = now.get(Calendar.YEAR);
        // 当月
		int currentMonth = now.get(Calendar.MONTH);

		// 访问参数
        BigDecimal[] data = new BigDecimal[currentMonth + 1];
		String[] month = new String[currentMonth + 1];

		GetForMonthQueryVo queryVo = new GetForMonthQueryVo();
		queryVo.setYear(String.valueOf(thisYear));
		String deviceType = deviceCno.substring(0, 2);
		queryVo.setDeviceType(deviceType);
		queryVo.setUserId(String.valueOf(userId));
		queryVo.setCustomerNo(customerNo);
		queryVo.setCno(deviceCno);
		List<SumDataGetDTO> dtoList = sysConfigService.sumGetForMonth(queryVo);

		// 按月分组
		ImmutableMap<String, SumDataGetDTO> dtoMap = Maps.uniqueIndex(dtoList, new Function<SumDataGetDTO, String>() {
			@Nullable
			@Override
			public String apply(@Nullable SumDataGetDTO sumDataGetDTO) {
				return sumDataGetDTO.getCltMonth();
			}
		});

		for (int i = 0; i <= currentMonth; i++) {
			month[i] = DateUtil.getMonthStr(i);
			String monthStr = String.valueOf(i + 1);
			// 设置设备统计信息
			SumDataGetDTO dto = dtoMap.get(monthStr);
			BigDecimal sumPR = dto.getSumPR();
			if (sumPR == null) {
				sumPR = BigDecimal.ZERO;
			}
			data[i] = sumPR;
		}

		CollectAnalyzeDataForMonthInfo monthInfo = new CollectAnalyzeDataForMonthInfo();
		if (DeviceType.ELECTRIC_METER.getCode().equals(deviceType)) {
			monthInfo.setElec(data);
		} else if (DeviceType.WATER_METER.getCode().equals(deviceCno)) {
			monthInfo.setWater(data);
		} else if (DeviceType.GAS_METER.getCode().equals(deviceCno)) {
			monthInfo.setGas(data);
		}
		monthInfo.setYear(thisYear);
		monthInfo.setMonth(month);
		return monthInfo;
	}

	/**
	 * @Description 查询欠费用户信息
	 * @param
	 * @return List<CollectAnalyzeData>
	 */
	@Override
	public List<ArrearageCustomer> listArrearageCustomers(ArrearageCustomersQueryVo queryVo) {
		List<ArrearageCustomer> lists = meterCollectDataService.listArrearageCustomers(queryVo);
		if (!CollectionUtils.isEmpty(lists)) {
			for (ArrearageCustomer list : lists) {
				BigDecimal readValue = MathUtil.setPrecision(list.getReadValue());
				list.setReadValue(readValue);

				BigDecimal alarmThreshold = MathUtil.setPrecision(list.getAlarmThreshold());
				list.setAlarmThreshold(alarmThreshold);
			}
		}
		return lists;
	}

	/**
	 * @Description 查询客户当月详细信息
	 * @param
	 * @return List<CollectAnalyzeData>
	 */
	@Override
	public List<CustomerData4Month> getDataForMonth(String customerNo, String deviceCno, Integer yearMonth) {
		List<CustomerData4Month> lists = meterCollectDataService.getDataForMonth(customerNo, deviceCno, yearMonth);
		List<CustomerData4Month> emptyList = Lists.newArrayList();
		if (CollectionUtils.isEmpty(lists)) {
			return emptyList;
		}

		for (CustomerData4Month customerData4Month : lists) {
			if(StringUtils.isEmpty(customerData4Month.getPayMoney())){
				customerData4Month.setPayMoney(BigDecimal.ZERO);
			}
		}
		return lists;
	}

	@Override
	public int getAlarmUserCount(Long userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		meterCollectDataService.getAlarmUserCount(map);
		int cnt =  ((Integer)map.get("cnt")).intValue();
		return cnt;
	}

	@Override
	public List<ImportantCurveDerailDTO> getImpDataList(RealTimeDataListParam realTimeDataListParam) {
		return deviceMeterParamMapper.getImpDataList(realTimeDataListParam);
	}
}
