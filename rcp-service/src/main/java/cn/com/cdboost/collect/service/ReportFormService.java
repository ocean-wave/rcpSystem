package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.dto.ChargeDetailDTO;
import cn.com.cdboost.collect.dto.ChargeSummaryDTO;
import cn.com.cdboost.collect.dto.param.ChargeSummaryParam;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.ParseException;
import java.util.List;

/**
 * 统计报表
 */
public interface ReportFormService {

	/**
	 * 充值汇总表
	 */
	XSSFWorkbook queryCharge(ChargeSummaryParam chargeSummaryParam);
	/**
	 * 充值汇总表
	 */
	List<ChargeSummaryDTO> queryChargeList(ChargeSummaryParam chargeSummaryParam);
	/**
	 * 充值明细表
	 */
	XSSFWorkbook queryDetail(ChargeSummaryParam chargeSummaryParam) throws ParseException;
	/**
	 * 充值明细表
	 */
	List<ChargeDetailDTO> queryDetailList(ChargeSummaryParam chargeSummaryParam);

}
