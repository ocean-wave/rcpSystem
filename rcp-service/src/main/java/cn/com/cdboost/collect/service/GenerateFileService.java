package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.CollectDataDownInfo;
import cn.com.cdboost.collect.dto.response.DaySettlementResponse;
import cn.com.cdboost.collect.dto.response.ImpCollectDataGetInfo;
import cn.com.cdboost.collect.model.Impot;
import cn.com.cdboost.collect.model.SmokeDevice;
import cn.com.cdboost.collect.model.SmokeDevlog;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.ParseException;
import java.util.List;

/**
 * excel文档生成服务接口
 */
public interface GenerateFileService {
	XSSFWorkbook smokeDeviceStatusListDownload(String name,  List<SmokeDevlog> list);
	XSSFWorkbook smokeDeviceListDownload(String name,  List<SmokeDevice> list);
	XSSFWorkbook customerInfoListDownload(String name, List<CustomerInfoListInfo> list);
	XSSFWorkbook useRecordListDownload(String name,List< UseRecordListInfo> list);
	XSSFWorkbook chargeRecordListDownload(String name,List<ChargeRecordListInfo> chargeRecordListInfos);
	XSSFWorkbook withdrawCashListDownload(String name,List<WithdrawCashListInfo> withdrawCashListInfos);
	XSSFWorkbook dayLineLossDownload(String name, DayLineLossInfo dayLineLossInfo);
	XSSFWorkbook totalLineLossDownload(String name, TotalLineLossInfo totalLineLossInfo);
	XSSFWorkbook queryChangeMetersDownload(String name, List<ChangeMeterInfo> changeMeterInfos);
	XSSFWorkbook queryResidentialListDownload(String name, List<QueryResidentialListInfo> queryResidentialListInfos);
	XSSFWorkbook queryDayLostDownload(String name, List<QueryDayLostInfo> queryDayLostInfos);
	XSSFWorkbook queryDayCollectSucceedRateDownload(String name, List<QueryDayCollectSucceedRateInfo> queryLineLostListInfos);
	XSSFWorkbook queryConfessElectDetailDownload(String name, List<QueryConfessElectDetailInfo> queryLineLostListInfos);
	XSSFWorkbook querySupplyElectDetailDownload(String name, List<QuerySupplyElectDetailInfo> querySupplyElectDetailInfos);
	XSSFWorkbook queryLineLostListDownload(String name, List<QueryLineLostListInfo> queryLineLostListInfos);

	XSSFWorkbook queryDaySettlementDown(String name, List<DaySettlementResponse> electRecordInfo);
	/**
	 *@Description:生成实时数据Excel文件
	 *@return HSSFWorkbook
	 *@throws Exception
	 */
	XSSFWorkbook generateelectDetailExcel(String name, ElectRecordInfo electRecordInfo);

	/**
	 *@Description:生成实时数据Excel文件
	 *@param list
	 *@return HSSFWorkbook
	 *@throws Exception
	 */
	XSSFWorkbook generateRealTimeDataExcel(String name,List<String> types, List<MeterCollectDataListInfo> list) throws Exception;

	// 生成重点用户实时数据excel
	XSSFWorkbook generateImpRealTimeDataExcel(String name, List<String> types, List<CollectImportantDataListInfo> list) throws Exception;

	/**
	 *@Description:生史数据Excel文件
	 *@param list
	 *@return HSSFWorkbook
	 *@throws Exception
	 */
	XSSFWorkbook generateHistoricalDataExcel(String name,List<String> types, List<CollectDataDownInfo> list) throws Exception;

	// 生成重点用户历史数据Excel文件
	XSSFWorkbook generateImpHistoricalDataExcel(String name, List<String> types,List<ImpCollectDataGetInfo> list) throws Exception;

	/**
	 *@Description:生成Excel文件
	 *@param list
	 *@return HSSFWorkbook
	 *@throws Exception
	 */
	XSSFWorkbook generateData4MonthExcel(String name, List<CustomerData4Month> list,String customerNo) throws Exception;
	XSSFWorkbook generateData4MonthExcel(String name, String deviceType, List<CustomerData4Month> list) throws Exception;

	/**
	 *@Description:生成用户档案Excel文件
	 *@param list
	 *@return HSSFWorkbook
	 *@throws Exception
	 */
	XSSFWorkbook generateCustomerRecordExcel(String name, List<CustomerInfoDtodownload> list) throws Exception;

	/**
	 * 生成方案详情Excelw文件
	 * @param name
	 * @param list
	 * @return
	 * @throws Exception
	 */
	XSSFWorkbook generateMeterListExcel(String name, List<SchemeMeterRes> list) throws Exception;
	/**
	 *@Description:生成缴费记录Excel文件
	 *@param list
	 *@return HSSFWorkbook
	 *@throws Exception
	 */
	XSSFWorkbook generateFeePayExcel(String name, List<QueryProcDTO> list) throws Exception;
	/**
	 *@Description:生成重点用户Excel文件
	 *@param list
	 *@return HSSFWorkbook
	 *@throws Exception
	 */
	XSSFWorkbook generateImportantExcel(String name, List<ImportantCurveDerailDTO> list) throws Exception;
	/**
	 *@Description:生成告警用户Excel文件
	 *@param list
	 *@return HSSFWorkbook
	 *@throws Exception
	 */
	XSSFWorkbook generateAlarmExcel(String name, List<ArrearageCustomer> list) throws Exception;

	/**
	 * 生成冻结数据详情Excel文件
	 * @param list
	 * @return
	 * @throws Exception
	 */
	XSSFWorkbook generateReadCollectRecordExcel(String name, List<CollectRecordDTO> list) throws Exception;

	XSSFWorkbook generateReadCollectRecordDetialExcel(String name, List<CollectDetialDTO> list) throws Exception;

	XSSFWorkbook generateDeviceEventExcel(String name, List<ExcAccountListDTO> list,ExcAccountDTO excAccountDTO) ;

	XSSFWorkbook generateChargeSummaryExcel(String name, List<ChargeSummaryDTO> list) ;

	XSSFWorkbook generateChargeDetailExcel(String name, List<ChargeDetailDTO> list) throws ParseException;

	/**
	 * 生成重点数据分析Excel文件
	 * @param name
	 * @param list
	 * @return
	 * @throws ParseException
     */
	XSSFWorkbook generateKeyCollectionAnalysisExcel(String name, List<ImpCollectionAnalysisDTO> list) throws ParseException;
	/**
	 * 生成实时召测列表
	 * @param name
	 * @param list
	 * @return
	 * @throws ParseException
	 */
	Integer generateRealTimeData(String name,List<Impot> list) ;

    XSSFWorkbook generateChargingDeviceListExcel(String name, List<ChargingDeviceDto> chargingDevices);

    XSSFWorkbook generateUseDetailedListExcel(String name, List<ChargingUseDetailedDto> chargingUseDetailedDtos);

    XSSFWorkbook generateElectricAndFeeListExcel(String name, List<ElectricAndFeeDto> electricAndFeeDtoList);

    XSSFWorkbook generateMonitorDeviceListExcel(String name, List<MonitorDeviceDto> monitorDeviceDtos);


	/**
	 * 生成客户退费列表信息
	 * @param name
	 * @param list
	 * @return
	 * @throws Exception
	 */
	XSSFWorkbook generateQueryListExcel(String name, List<RefundQueryListDto> list) throws Exception;

	/**
	 * 生成查询客户的退费记录
	 * @param name
	 * @param list
	 * @return
	 * @throws Exception
	 */
	XSSFWorkbook generateListByCstExcel(String name, List<QueryDetailDto> list) throws Exception;

	/**
	 * 生成客户退费记录
	 * @param name
	 * @param list
	 * @return
	 * @throws Exception
	 */
	XSSFWorkbook generateQueryRefundRecordExcel(String name, List<QueryRefundRecordDto> list) throws Exception;

	XSSFWorkbook generateReadValue(String name,List<MeterCollectGroupDto> meterCollectGroupDtos) throws ParseException;

	XSSFWorkbook generateRechargeRecord(String 客户充值记录报表, List<QueryRechargeRecordDto> list);

    XSSFWorkbook generatevillageElectricityListExcel(String name, List<VillageElectricityDto> villageElectricityDtos);

    XSSFWorkbook generateElectricityListExcel(String name, List<ElectricityCountDto> list);

	XSSFWorkbook generateDeviceDetailListExcel(String name, List<EnergyEfficiencyDetailDto> list);

    XSSFWorkbook generateHeartListExcel(String name, List<HeartDto> heartDtos);

    //IC卡列表下载
    XSSFWorkbook generateChargingICCardListExcel(String name, List<ChargingICCardDto> chargingICCardDtos);
	//IC卡充值记录
    XSSFWorkbook generateChargingICPayListExcel(String name, List<ChargingICPayDto> chargingICPayDtos);

    //IC卡使用记录
    XSSFWorkbook generateChargingICUseListExcel(String name, List<ChargingICUseDto> chargingICUseDtos);

    //项目列表
    XSSFWorkbook generateChargingProjectListExcel(String name, List<ChargingProjectDto> chargingProjectDtos);

    //方案使用列表
    XSSFWorkbook generateChargingSchemeUseListExcel(String name, List<ChargingUseDetailedDto> useList);
}
