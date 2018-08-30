package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.dao.ReportFormMapper;
import cn.com.cdboost.collect.dto.ChargeDetailDTO;
import cn.com.cdboost.collect.dto.ChargeSummaryDTO;
import cn.com.cdboost.collect.dto.param.ChargeSummaryParam;
import cn.com.cdboost.collect.service.GenerateFileService;
import cn.com.cdboost.collect.service.ReportFormService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

/**
 *
 */
@Service
public class ReportFormServiceImpl implements ReportFormService {


    @Autowired
    GenerateFileService generateFileService;
    @Autowired
    ReportFormMapper reportFormMapper;
    @Override
    public XSSFWorkbook queryCharge(ChargeSummaryParam chargeSummaryParam) {

        List<ChargeSummaryDTO> chargeSummaryDTOS = reportFormMapper.queryChargeSummaryList(chargeSummaryParam);
        Long index= 0L;
        for (ChargeSummaryDTO chargeSummaryDTO : chargeSummaryDTOS) {
            chargeSummaryDTO.setSequence(++index);
        }

        return  generateFileService.generateChargeSummaryExcel("充值汇总表",chargeSummaryDTOS);

    }
    @Override
    public List<ChargeSummaryDTO> queryChargeList(ChargeSummaryParam chargeSummaryParam) {

        List<ChargeSummaryDTO> chargeSummaryDTOS = reportFormMapper.queryChargeSummaryList(chargeSummaryParam);
        Long index= 0L;
        for (ChargeSummaryDTO chargeSummaryDTO : chargeSummaryDTOS) {
            chargeSummaryDTO.setSequence(++index);
        }

        return  chargeSummaryDTOS;

    }
    @Override
    public XSSFWorkbook queryDetail(ChargeSummaryParam chargeSummaryParam) throws ParseException {
        List<ChargeDetailDTO> chargeSummaryDTOS = reportFormMapper.queryChargeDetailList(chargeSummaryParam);
        Long index= Long.valueOf(0);
        for (ChargeDetailDTO chargeSummaryDTO : chargeSummaryDTOS) {
            chargeSummaryDTO.setSequence(++index);
        }

        return generateFileService.generateChargeDetailExcel("充值明细表",chargeSummaryDTOS);

    }
    @Override
    public List<ChargeDetailDTO> queryDetailList(ChargeSummaryParam chargeSummaryParam) {
        List<ChargeDetailDTO> chargeSummaryDTOS = reportFormMapper.queryChargeDetailList(chargeSummaryParam);
        Long index= Long.valueOf(0);
        for (ChargeDetailDTO chargeSummaryDTO : chargeSummaryDTOS) {
            chargeSummaryDTO.setSequence(++index);
        }

        return chargeSummaryDTOS;

    }
}
