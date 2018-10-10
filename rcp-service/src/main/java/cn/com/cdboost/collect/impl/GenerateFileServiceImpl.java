package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.constant.ChargeConstant;
import cn.com.cdboost.collect.constant.ClientConstant;
import cn.com.cdboost.collect.constant.RealTimeDataConstant;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.CollectDataDownInfo;
import cn.com.cdboost.collect.dto.response.DaySettlementResponse;
import cn.com.cdboost.collect.dto.response.ImpCollectDataGetInfo;
import cn.com.cdboost.collect.enums.ChargingEnum;
import cn.com.cdboost.collect.enums.DeviceType;
import cn.com.cdboost.collect.model.CustomerInfo;
import cn.com.cdboost.collect.model.Impot;
import cn.com.cdboost.collect.model.SmokeDevice;
import cn.com.cdboost.collect.model.SmokeDevlog;
import cn.com.cdboost.collect.service.CustomerInfoService;
import cn.com.cdboost.collect.service.GenerateFileService;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.util.DateUtil;
import cn.com.cdboost.collect.util.StringUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.csvreader.CsvWriter;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * excel文档生成服务接口实现类
 */
@Service("generateFileService")
public class GenerateFileServiceImpl implements GenerateFileService {
    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private DruidDataSource dataSource;

    @Override
    public XSSFWorkbook generateDeviceUseListExcel(String name, List<DeviceUseCountListDto> list) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("状态"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("设备编号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充电次数"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电量（KW·H）"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电费（元）"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("营收（元）"));
        cellIndex++;



        XSSFRow row;
        XSSFCell cell;
        DeviceUseCountListDto useDetailedDto;

        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                useDetailedDto = list.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if(useDetailedDto.getRunState() == ChargingEnum.FREE_STATE.getKey()){
                    cell.setCellValue(new XSSFRichTextString(ChargingEnum.FREE_STATE.getMessage()));
                }else if (useDetailedDto.getRunState() == ChargingEnum.ON_STATE.getKey()){
                    cell.setCellValue(new XSSFRichTextString(ChargingEnum.ON_STATE.getMessage()));
                }else if (useDetailedDto.getRunState() == ChargingEnum.OFF_STATE.getKey()){
                    cell.setCellValue(new XSSFRichTextString(ChargingEnum.OFF_STATE.getMessage()));
                }else if (useDetailedDto.getRunState() == ChargingEnum.ERROR_STATE.getKey()){
                    cell.setCellValue(new XSSFRichTextString(ChargingEnum.ERROR_STATE.getMessage()));
                }
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(useDetailedDto.getDeviceNo()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if (useDetailedDto.getChargingNum() != null){
                    cell.setCellValue(new XSSFRichTextString(String.valueOf(useDetailedDto.getChargingNum())));
                } else {
                    cell.setCellValue(new XSSFRichTextString("0"));
                }
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if (useDetailedDto.getProjectPower() != null){
                    cell.setCellValue(new XSSFRichTextString(String.valueOf(useDetailedDto.getProjectPower())));
                } else {
                    cell.setCellValue(new XSSFRichTextString("0"));
                }
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if (useDetailedDto.getProjectFee() != null){
                    cell.setCellValue(new XSSFRichTextString(String.valueOf(useDetailedDto.getProjectFee())));
                } else {
                    cell.setCellValue(new XSSFRichTextString("0"));
                }
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if (useDetailedDto.getProjectProfitable() != null){
                    cell.setCellValue(new XSSFRichTextString(String.valueOf(useDetailedDto.getProjectProfitable())));
                } else {
                    cell.setCellValue(new XSSFRichTextString("0"));
                }
                index++;
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generateProjectUseListExcel(String name, List<ProjectUseCountListDto> list) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("站点名称"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("所属组织"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("设备数量"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("使用率（%）"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充电次数"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电量（KW·H）"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电费（元）"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("盈利（元）"));
        cellIndex++;



        XSSFRow row;
        XSSFCell cell;
        ProjectUseCountListDto useDetailedDto;

        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                useDetailedDto = list.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(useDetailedDto.getProjectName()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(useDetailedDto.getOrgName()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if (useDetailedDto.getDeviceNum() != null){
                    cell.setCellValue(new XSSFRichTextString(String.valueOf(useDetailedDto.getDeviceNum())));
                } else {
                    cell.setCellValue(new XSSFRichTextString("0"));
                }
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if (useDetailedDto.getProjectDeviceUseRate() != null){
                    cell.setCellValue(new XSSFRichTextString(String.valueOf(useDetailedDto.getProjectDeviceUseRate())));
                } else {
                    cell.setCellValue(new XSSFRichTextString("0"));
                }
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if (useDetailedDto.getChargingNum() != null){
                    cell.setCellValue(new XSSFRichTextString(String.valueOf(useDetailedDto.getChargingNum())));
                } else {
                    cell.setCellValue(new XSSFRichTextString("0"));
                }
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if (useDetailedDto.getProjectPower() != null){
                    cell.setCellValue(new XSSFRichTextString(String.valueOf(useDetailedDto.getProjectPower())));
                } else {
                    cell.setCellValue(new XSSFRichTextString("0"));
                }
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if (useDetailedDto.getProjectFee() != null){
                    cell.setCellValue(new XSSFRichTextString(String.valueOf(useDetailedDto.getProjectFee())));
                } else {
                    cell.setCellValue(new XSSFRichTextString("0"));
                }
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if (useDetailedDto.getProjectProfitable() != null){
                    cell.setCellValue(new XSSFRichTextString(String.valueOf(useDetailedDto.getProjectProfitable())));
                } else {
                    cell.setCellValue(new XSSFRichTextString("0"));
                }
                index++;
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generateChargingSchemeUseListExcel(String name, List<ChargingUseDetailedDto> list) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("端口号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("类别"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("计费方式"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充值费用(元)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("使用电量(KW·H)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("盈利费用"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("状态"));
        cellIndex++;


        XSSFRow row;
        XSSFCell cell;
        ChargingUseDetailedDto useDetailedDto;

        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                useDetailedDto = list.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(useDetailedDto.getPort()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if (useDetailedDto.getCarCategory() == ChargeConstant.CarType.ELECTRO_MOBILE.getType()){
                    cell.setCellValue(new XSSFRichTextString(ChargeConstant.CarType.ELECTRO_MOBILE.getDesc()));
                } else if (useDetailedDto.getCarCategory() == ChargeConstant.CarType.CAR.getType()) {
                    cell.setCellValue(new XSSFRichTextString(ChargeConstant.CarType.CAR.getDesc()));
                } else {
                    cell.setCellValue(new XSSFRichTextString("电瓶车"));
                }
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if (useDetailedDto.getPayCategory() == ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getType()){
                    cell.setCellValue(new XSSFRichTextString(ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getDesc()));
                } else if (useDetailedDto.getPayCategory() == ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType()){
                    cell.setCellValue(new XSSFRichTextString(ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getDesc()));
                } else if (useDetailedDto.getPayCategory() == ChargeConstant.SchemePayCategory.RECHARGE_FULL.getType()){
                    cell.setCellValue(new XSSFRichTextString(ChargeConstant.SchemePayCategory.RECHARGE_FULL.getDesc()));
                }else if (useDetailedDto.getPayCategory() == ChargeConstant.SchemePayCategory.BALANCE_RECHARGE.getType()){
                    cell.setCellValue(new XSSFRichTextString(ChargeConstant.SchemePayCategory.BALANCE_RECHARGE.getDesc()));
                }else if (useDetailedDto.getPayCategory() == ChargeConstant.SchemePayCategory.IC_RECHARGE.getType()){
                    cell.setCellValue(new XSSFRichTextString(ChargeConstant.SchemePayCategory.IC_RECHARGE.getDesc()));
                }
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if (useDetailedDto.getPayMoney() != null){
                    cell.setCellValue(new XSSFRichTextString(String.valueOf(useDetailedDto.getPayMoney())));
                }else {
                    cell.setCellValue(new XSSFRichTextString("0"));
                }

                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(useDetailedDto.getUsePower())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(useDetailedDto.getProfitable())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if (useDetailedDto.getState() == ChargeConstant.ChargeState.CHARGING.getState()){
                    cell.setCellValue(new XSSFRichTextString(ChargeConstant.ChargeState.CHARGING.getDesc()));
                }else if (useDetailedDto.getState() == ChargeConstant.ChargeState.COMPLETED.getState()){
                    cell.setCellValue(new XSSFRichTextString(ChargeConstant.ChargeState.COMPLETED.getDesc()));
                }
                index++;
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generateChargingProjectListExcel(String name, List<ChargingProjectDto> list) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("项目名称"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("设备数量"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("小区名称"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("物业公司"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("创建时间"));
        cellIndex++;


        XSSFRow row;
        XSSFCell cell;
        ChargingProjectDto chargingProjectDto;

        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                chargingProjectDto = list.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(chargingProjectDto.getProjectName()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(chargingProjectDto.getDeviceNum()==null?0:chargingProjectDto.getDeviceNum())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(chargingProjectDto.getCommunityName()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(chargingProjectDto.getCompanyName()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(chargingProjectDto.getCreateTime()));
                index++;
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook smokeDeviceStatusListDownload(String name, List<SmokeDevlog> list) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("设备编号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("更新时间"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电池电量(KW·h)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("设备状态"));
        cellIndex++;

        XSSFRow row;
        XSSFCell cell;
        SmokeDevlog smokeDevice;

        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                smokeDevice = list.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(smokeDevice.getCno()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = simpleDateFormat.format(smokeDevice.getCreateTime());
                cell.setCellValue(new XSSFRichTextString(String.valueOf(format)));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(smokeDevice.getPower())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                String status = smokeDevice.getStatus();
                /**
                 * 状态(在线-1、离线-2、停用-3、告警-4、欠压-5、启用-6)',
                 */
                if("1".equals(status)){
                    status="在线";
                }else if("2".equals(status)){
                    status="离线";
                }else if("3".equals(status)){
                    status="停用";
                }else if("4".equals(status)){
                    status="告警";
                }else if("5".equals(status)){
                    status="欠压";
                }else if("6".equals(status)){
                    status="启用";
                }
                cell.setCellValue(new XSSFRichTextString(status));
                index++;
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook smokeDeviceListDownload(String name, List<SmokeDevice> list) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("序号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("状态"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("设备编号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("通信编号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("小区信息"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("安装位置"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("安装时间"));
        cellIndex++;

        XSSFRow row;
        XSSFCell cell;
        SmokeDevice smokeDevice;

        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                smokeDevice = list.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(i+1)));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                String status = smokeDevice.getStatus();
                /**
                 * 状态(在线-1、离线-2、停用-3、告警-4、欠压-5、启用-6)',
                 */
                if("1".equals(status)){
                    status="在线";
                }else if("2".equals(status)){
                    status="离线";
                }else if("3".equals(status)){
                    status="停用";
                }else if("4".equals(status)){
                    status="告警";
                }else if("5".equals(status)){
                    status="欠压";
                }else if("6".equals(status)){
                    status="启用";
                }
                cell.setCellValue(new XSSFRichTextString(status));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(smokeDevice.getCno()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(smokeDevice.getCommNo())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(smokeDevice.getResident())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(smokeDevice.getInstallAddr())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = simpleDateFormat.format(smokeDevice.getCreateTime());
                cell.setCellValue(new XSSFRichTextString(String.valueOf(format)));
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook customerInfoListDownload(String name, List<CustomerInfoListInfo> list) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("序号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("状态"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("手机号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("账户余额(元)"));
        cellIndex++;

        /*cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("月卡剩余次数"));
        cellIndex++;*/

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充电次数"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("支付宝(昵称)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("微信(昵称)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("IC卡"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("更新时间"));
        cellIndex++;



        XSSFRow row;
        XSSFCell cell;
        CustomerInfoListInfo customerInfoListInfo;

        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                customerInfoListInfo = list.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(i+1)));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(customerInfoListInfo.getCustomerState()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(customerInfoListInfo.getCustomerContact()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(customerInfoListInfo.getRemainAmount())));
                index++;

                /*cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(customerInfoListInfo.getRemainCnt())));
                index++;*/

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(customerInfoListInfo.getChargeCount())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(customerInfoListInfo.getAlipayNickName())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(customerInfoListInfo.getCustomerName())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(customerInfoListInfo.getCardId())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(customerInfoListInfo.getUpdateTime())));
                index++;
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook useRecordListDownload(String name, List<UseRecordListInfo> list) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("序号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("时间"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("状态"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("方案"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("方式"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("设备编号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电量"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充电时长"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("开始时间"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("结束时间"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("设备地址"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电价"));
        cellIndex++;

        XSSFRow row;
        XSSFCell cell;
        UseRecordListInfo useRecordListInfo;

        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                useRecordListInfo = list.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(i+1)));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(useRecordListInfo.getDate()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(useRecordListInfo.getDeviceState()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(useRecordListInfo.getPayCategory())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(useRecordListInfo.getPayMethod())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(useRecordListInfo.getDeviceNo())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(useRecordListInfo.getDeviceElect())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(useRecordListInfo.getUseTime())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(useRecordListInfo.getStartDate())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(useRecordListInfo.getEndDate())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(useRecordListInfo.getInstallAddress())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(useRecordListInfo.getPrice())));
                index++;
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook chargeRecordListDownload(String name, List<ChargeRecordListInfo> chargeRecordListInfos) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("序号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("状态"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充值时间"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充值方式"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充值金额"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("账户余额"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("人员ID"));
        cellIndex++;

        XSSFRow row;
        XSSFCell cell;
        ChargeRecordListInfo chargeRecordListInfo;

        if (!CollectionUtils.isEmpty(chargeRecordListInfos)) {
            for (int i = 0; i < chargeRecordListInfos.size(); i++) {
                chargeRecordListInfo = chargeRecordListInfos.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(i+1)));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(chargeRecordListInfo.getPayState()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(chargeRecordListInfo.getDate()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(chargeRecordListInfo.getPayMethod()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(chargeRecordListInfo.getPayMoney())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(chargeRecordListInfo.getRemainAmount())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(chargeRecordListInfo.getUserId())));
                index++;

            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook withdrawCashListDownload(String name, List<WithdrawCashListInfo> withdrawCashListInfos) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("序号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("状态"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("提现时间"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("提现方式"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("提现金额"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("账户余额"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("人员ID"));
        cellIndex++;

        XSSFRow row;
        XSSFCell cell;
        WithdrawCashListInfo withdrawCashListInfo;

        if (!CollectionUtils.isEmpty(withdrawCashListInfos)) {
            for (int i = 0; i < withdrawCashListInfos.size(); i++) {
                withdrawCashListInfo = withdrawCashListInfos.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(i+1)));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(withdrawCashListInfo.getStatus()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(withdrawCashListInfo.getDate()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(withdrawCashListInfo.getWithdrawMethod()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(withdrawCashListInfo.getWithdrawMoney())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(withdrawCashListInfo.getRemainAmount())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(withdrawCashListInfo.getUserId())));
                index++;

            }
        }
        return workbook;
    }




    @Override
    public XSSFWorkbook generateChargingICUseListExcel(String name, List<ChargingICUseDto> chargingICUseDtos) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("序号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("设备编号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("端口号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("时长（分钟）"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("开始时间"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("结束时间"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("扣费金额（元）"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("余额（元）"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("地点"));
        cellIndex++;

        XSSFRow row;
        XSSFCell cell;
        ChargingICUseDto chargingICUseDto;

        if (!CollectionUtils.isEmpty(chargingICUseDtos)) {
            for (int i = 0; i < chargingICUseDtos.size(); i++) {
                chargingICUseDto = chargingICUseDtos.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(i+1)));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(chargingICUseDto.getDeviceNo()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(chargingICUseDto.getPort()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(chargingICUseDto.getChargingTime())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(chargingICUseDto.getStartTime())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(chargingICUseDto.getEndTime())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(chargingICUseDto.getDeductMoney())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(chargingICUseDto.getRemainAmount())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(chargingICUseDto.getInstallAddr())));
                index++;
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generateChargingICPayListExcel(String name, List<ChargingICPayDto> chargingICPayDtos) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("序号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充值金额"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("账户余额"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充值方式"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("操作人员"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充值时间"));
        cellIndex++;


        XSSFRow row;
        XSSFCell cell;
        ChargingICPayDto chargingICPayDto;

        if (!CollectionUtils.isEmpty(chargingICPayDtos)) {
            for (int i = 0; i < chargingICPayDtos.size(); i++) {
                chargingICPayDto = chargingICPayDtos.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(i+1)));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(chargingICPayDto.getPayMoney())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(chargingICPayDto.getICRemainAmount())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if (chargingICPayDto.getPayWay() == 1){
                    cell.setCellValue(new XSSFRichTextString("微信"));
                }else if (chargingICPayDto.getPayWay() == 2){
                    cell.setCellValue(new XSSFRichTextString("支付宝"));
                }else if (chargingICPayDto.getPayWay() == 3){
                    cell.setCellValue(new XSSFRichTextString("现金"));
                }else if (chargingICPayDto.getPayWay() == 4){
                    cell.setCellValue(new XSSFRichTextString("余额"));
                }
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(chargingICPayDto.getPayUser()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(chargingICPayDto.getCreateTime())));
                index++;

            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generateChargingICCardListExcel(String name, List<ChargingICCardDto> chargingICCardDtos) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("序号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("状态"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("卡号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("剩余金额（元）"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("使用次数"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充值次数"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("创建时间"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("更新时间"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("备注"));
        cellIndex++;

        XSSFRow row;
        XSSFCell cell;
        ChargingICCardDto chargingICCardDto;

        if (!CollectionUtils.isEmpty(chargingICCardDtos)) {
            for (int i = 0; i < chargingICCardDtos.size(); i++) {
                chargingICCardDto = chargingICCardDtos.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(i+1)));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if (chargingICCardDto.getCardState() == 0){
                    cell.setCellValue(new XSSFRichTextString("初始"));
                } else if (chargingICCardDto.getCardState() == 1){
                    cell.setCellValue(new XSSFRichTextString("启用"));
                }else if (chargingICCardDto.getCardState() == 2){
                    cell.setCellValue(new XSSFRichTextString("停用"));
                }
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(chargingICCardDto.getCardId()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(chargingICCardDto.getIcRemainAmount())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(chargingICCardDto.getUseCnt())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(chargingICCardDto.getPayCnt())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(chargingICCardDto.getCreateTime())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(chargingICCardDto.getUpdateTime())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(chargingICCardDto.getRemark()));
                index++;
            }
        }
        return workbook;
    }



    @Override
    public XSSFWorkbook dayLineLossDownload(String name, DayLineLossInfo dayLineLossInfo) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("设备编号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("端口号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电量(KW·h)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("状态"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充电时长(分钟)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("开始时间"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("结束时间"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("最大功率(W)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("最大电流(A)"));
        cellIndex++;

        XSSFRow row;
        XSSFCell cell;
        DayLineLossInfoList dayLineLossInfoList;

        if (!CollectionUtils.isEmpty(dayLineLossInfo.getList())) {
            for (int i = 0; i < dayLineLossInfo.getList().size(); i++) {
                dayLineLossInfoList = dayLineLossInfo.getList().get(i);
                row = sheet.createRow(i + 2);
                int index = 0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(dayLineLossInfoList.getDeviceNo())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(dayLineLossInfoList.getPort()));

                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(dayLineLossInfoList.getDeviceElect()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(dayLineLossInfoList.getState()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(dayLineLossInfoList.getUserTime())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(dayLineLossInfoList.getStartDate())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(dayLineLossInfoList.getEndDate())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(dayLineLossInfoList.getMostPower())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(dayLineLossInfoList.getMostCurrent())));
                index++;
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook totalLineLossDownload(String name, TotalLineLossInfo totalLineLossInfo) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("日期"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("总表表号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("总表起度"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("总表止度"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电表电量(kW·h)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("设备电量(kW·h)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("损耗电量(kW·h)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("损耗率(%)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充电次数"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("位置"));
        cellIndex++;

        XSSFRow row;
        XSSFCell cell;
        LineLossList lineLossList;
        int total=0;
        if (!CollectionUtils.isEmpty(totalLineLossInfo.getList())) {
            for (int i = 0; i < totalLineLossInfo.getList().size(); i++) {
                lineLossList = totalLineLossInfo.getList().get(i);
                row = sheet.createRow(i + 2);
                int index = 0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(lineLossList.getDate())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(lineLossList.getDeviceNo()));

                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(lineLossList.getLastReadValue()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(lineLossList.getReadValue())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(lineLossList.getMeterElect())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(lineLossList.getDeviceElect())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(lineLossList.getLossElect())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(lineLossList.getLossRate())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(lineLossList.getChargeTime())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(lineLossList.getInstallAddr())));
                index++;
                total++;
            }
        }
        row1 = sheet.createRow(total+2);
        CellRangeAddress callRangeAddress = new CellRangeAddress(total+2,total+2,0,3);
        sheet.addMergedRegion(callRangeAddress);

        cell1 = row1.createCell(0);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("总计"));

        cell1 = row1.createCell(4);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString(String.valueOf(totalLineLossInfo.getStatistics().getMeterElect())));

        cell1 = row1.createCell(5);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString(String.valueOf(totalLineLossInfo.getStatistics().getDeviceElect())));

        cell1 = row1.createCell(6);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString(String.valueOf(totalLineLossInfo.getStatistics().getLossElect())));

        cell1 = row1.createCell(7);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString(String.valueOf(totalLineLossInfo.getStatistics().getLossRate())));

        return workbook;
    }


    @Override
    public XSSFWorkbook generateHeartListExcel(String name, List<HeartDto> list) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("时间"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("状态"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("事件内容"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电压（V）"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电流（A）"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("功率（W）"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电量(kW·h)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("剩余时间（分钟）"));
        cellIndex++;



        XSSFRow row;
        XSSFCell cell;
        HeartDto heartDto;

        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                heartDto = list.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(DateUtil.formatDate(heartDto.getHeartTime())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if (heartDto.getState() == 0){
                    cell.setCellValue(new XSSFRichTextString("正常"));
                }else if (heartDto.getState() == 1 || heartDto.getState() == -2){
                    cell.setCellValue(new XSSFRichTextString("告警"));
                }else if (heartDto.getState() == -1){
                    cell.setCellValue(new XSSFRichTextString("充电结束）"));
                }
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(heartDto.getEventContent()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(heartDto.getVoltage())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(heartDto.getCurrent())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(heartDto.getPower())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(heartDto.getActiveTotal())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(heartDto.getRemainTime())));
                index++;
            }
        }
        return workbook;
    }


    @Override
    public XSSFWorkbook queryChangeMetersDownload(String name, List<ChangeMeterInfo> changeMeterInfos) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户姓名"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("表计户号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("联系电话"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("旧表号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("旧表剩余金额"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("旧表总示数"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("新表号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("新表剩余金额"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("新表总示数"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户地址"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("换表人员"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("换表时间"));
        cellIndex++;
        XSSFRow row;
        XSSFCell cell;
        ChangeMeterInfo changeMeterInfo;

        if (!CollectionUtils.isEmpty(changeMeterInfos)) {
            for (int i = 0; i < changeMeterInfos.size(); i++) {
                changeMeterInfo = changeMeterInfos.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(changeMeterInfo.getCustomerName()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(changeMeterInfo.getMeterUserNo()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(changeMeterInfo.getCustomerContact()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(CNoUtil.getDeviceNoByCno(changeMeterInfo.getCno())));
                index++;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(changeMeterInfo.getRemainAmount())));
                index++;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(changeMeterInfo.getPower())));
                index++;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(CNoUtil.getDeviceNoByCno(changeMeterInfo.getNewCno())));
                index++;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(changeMeterInfo.getNewRemainAmount())));
                index++;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(changeMeterInfo.getNewPower())));
                index++;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(changeMeterInfo.getCustomerAddr()));
                index++;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(changeMeterInfo.getUserName()));
                index++;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(DateUtil.formatDate(changeMeterInfo.getChangeTime())));
                index++;
            }
        }
        return workbook;
    }
    @Override
    public XSSFWorkbook generateDeviceDetailListExcel(String name, List<EnergyEfficiencyDetailDto> list) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("序号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户名称"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("表号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用电量（KW·h）"));
        cellIndex++;



        XSSFRow row;
        XSSFCell cell;
        EnergyEfficiencyDetailDto energyEfficiencyDetailDto;

        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                energyEfficiencyDetailDto = list.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(i + 1)));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(energyEfficiencyDetailDto.getCustomerName()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(energyEfficiencyDetailDto.getMeterNo())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(energyEfficiencyDetailDto.getPower())));
                index++;


            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generateElectricityListExcel(String name, List<ElectricityCountDto> list) {

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();

        CellRangeAddress callRangeAddress = new CellRangeAddress(0,1,0,0);
        CellRangeAddress callRangeAddress1 = new CellRangeAddress(0,1,1,1);
        CellRangeAddress callRangeAddress2 = new CellRangeAddress(0,1,2,2);
        CellRangeAddress callRangeAddress3 = new CellRangeAddress(0,0,3,4);
        CellRangeAddress callRangeAddress4 = new CellRangeAddress(0,0,5,6);
        CellRangeAddress callRangeAddress5 = new CellRangeAddress(0,0,7,8);
        CellRangeAddress callRangeAddress6 = new CellRangeAddress(0,0,9,10);
        sheet.addMergedRegion(callRangeAddress);
        sheet.addMergedRegion(callRangeAddress1);
        sheet.addMergedRegion(callRangeAddress2);
        sheet.addMergedRegion(callRangeAddress3);
        sheet.addMergedRegion(callRangeAddress4);
        sheet.addMergedRegion(callRangeAddress5);
        sheet.addMergedRegion(callRangeAddress6);

        workbook.setSheetName(0, name);
        XSSFRow row1 = sheet.createRow(0);
        int cellIndex = 0;

        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("序号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("时间"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("总电量"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("照明插座（KW·h）"));
        cellIndex=cellIndex+2;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("空调（KW·h）"));
        cellIndex=cellIndex+2;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("动力（KW·h）"));
        cellIndex=cellIndex+2;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("特殊用电（KW·h）"));
        cellIndex++;

        row1=sheet.createRow(1);
        cell1 = row1.createCell(3);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("设备数量"));

        cell1 = row1.createCell(4);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("总耗电量"));

        cell1 = row1.createCell(5);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("设备数量"));

        cell1 = row1.createCell(6);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("总耗电量"));

        cell1 = row1.createCell(7);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("设备数量"));

        cell1 = row1.createCell(8);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("总耗电量"));

        cell1 = row1.createCell(9);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("设备数量"));

        cell1 = row1.createCell(10);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("总耗电量"));

        XSSFRow row;
        XSSFCell cell;
        ElectricityCountDto ElectricityCountDto;

        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                ElectricityCountDto = list.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(i+1);
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(ElectricityCountDto.getCountDate()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(ElectricityCountDto.getTotalElectricity())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(ElectricityCountDto.getSocketNum())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(ElectricityCountDto.getSocketElectricity())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(ElectricityCountDto.getAirNum())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(ElectricityCountDto.getAirElectricity())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(ElectricityCountDto.getPowerNum())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(ElectricityCountDto.getPowerElectricity())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(ElectricityCountDto.getSpecialNum())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(ElectricityCountDto.getSpecialElectricity())));
                index++;
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generatevillageElectricityListExcel(String name, List<VillageElectricityDto> list) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("小区名称"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电表数量"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("照明插座（KW·h）"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("空调（KW·h）"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("动力（KW·h）"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("特殊用电（KW·h）"));
        cellIndex++;


        XSSFRow row;
        XSSFCell cell;
        VillageElectricityDto villageElectricityDto;

        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                villageElectricityDto = list.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(villageElectricityDto.getOrgName()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(villageElectricityDto.getAmmeterNum())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(villageElectricityDto.getSocketElectricity())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(villageElectricityDto.getAirElectricity())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(villageElectricityDto.getPowerElectricity())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(villageElectricityDto.getSpecialElectricity()==null?"":villageElectricityDto.getSpecialElectricity())));
                index++;


            }
        }
        return workbook;
    }
    @Override
    public XSSFWorkbook queryResidentialListDownload(String name, List<QueryResidentialListInfo> queryResidentialListInfos) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();

        CellRangeAddress callRangeAddress = new CellRangeAddress(0,1,0,0);
        CellRangeAddress callRangeAddress1 = new CellRangeAddress(0,1,1,1);
        CellRangeAddress callRangeAddress2 = new CellRangeAddress(0,1,2,2);
        CellRangeAddress callRangeAddress3 = new CellRangeAddress(0,0,3,4);
        CellRangeAddress callRangeAddress4 = new CellRangeAddress(0,0,5,6);
        CellRangeAddress callRangeAddress5 = new CellRangeAddress(0,0,7,8);
        sheet.addMergedRegion(callRangeAddress);
        sheet.addMergedRegion(callRangeAddress1);
        sheet.addMergedRegion(callRangeAddress2);
        sheet.addMergedRegion(callRangeAddress3);
        sheet.addMergedRegion(callRangeAddress4);
        sheet.addMergedRegion(callRangeAddress5);

        workbook.setSheetName(0, name);
        XSSFRow row1 = sheet.createRow(0);
        int cellIndex = 0;

        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("序号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("小区名称"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("台区数"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("线损率<0%"));
        cellIndex=cellIndex+2;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("0%<=线损率<=10%"));
        cellIndex=cellIndex+2;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("线损率>10%"));
        cellIndex++;

        row1=sheet.createRow(1);
        cell1 = row1.createCell(3);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("台区数"));

        cell1 = row1.createCell(4);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("占比%"));

        cell1 = row1.createCell(5);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("台区数"));

        cell1 = row1.createCell(6);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("占比%"));

        cell1 = row1.createCell(7);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("台区数"));

        cell1 = row1.createCell(8);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("占比%"));

        XSSFRow row;
        XSSFCell cell;
        QueryResidentialListInfo queryResidentialListInfo;

        if (!CollectionUtils.isEmpty(queryResidentialListInfos)) {
            for (int i = 0; i < queryResidentialListInfos.size(); i++) {
                queryResidentialListInfo = queryResidentialListInfos.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(i+1);
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryResidentialListInfo.getResidential()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryResidentialListInfo.getPlatform_total()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryResidentialListInfo.getLine_loss_rate_less0_platform())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryResidentialListInfo.getLineLossRateLess0occupation())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryResidentialListInfo.getLine_loss_rate_less10_platform())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryResidentialListInfo.getLineLossRateLess10occupation())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryResidentialListInfo.getLine_loss_rate_bigger10_platform())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryResidentialListInfo.getLineLossRateBigger10occupation())));
                index++;
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook queryDayLostDownload(String name, List<QueryDayLostInfo> queryDayLostInfos) {

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("序号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("日期"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("供电量(KW·h)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("售电量(KW·h)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("损耗电量(KW·h)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("损耗率(%)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("成功数"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("成功率(%)"));

        cellIndex++;
        XSSFRow row;
        XSSFCell cell;
        QueryDayLostInfo queryDayLostInfo;

        if (!CollectionUtils.isEmpty(queryDayLostInfos)) {
            for (int i = 0; i < queryDayLostInfos.size(); i++) {
                queryDayLostInfo = queryDayLostInfos.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(i+1);
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryDayLostInfo.getCalc_date()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryDayLostInfo.getPower_supply()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryDayLostInfo.getElect_sale())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryDayLostInfo.getElect_loss())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryDayLostInfo.getLoss_rate())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryDayLostInfo.getSucceed_amount())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryDayLostInfo.getSucceedRate())));
                index++;
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook queryDayCollectSucceedRateDownload(String name, List<QueryDayCollectSucceedRateInfo> queryLineLostListInfos) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("序号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("日期"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电表总数"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("成功数"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("失败数"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("成功率(%)"));
        cellIndex++;

        XSSFRow row;
        XSSFCell cell;
        QueryDayCollectSucceedRateInfo queryDayCollectSucceedRateInfo;

        if (!CollectionUtils.isEmpty(queryLineLostListInfos)) {
            for (int i = 0; i < queryLineLostListInfos.size(); i++) {
                queryDayCollectSucceedRateInfo = queryLineLostListInfos.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(i+1);
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryDayCollectSucceedRateInfo.getCollect_date()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryDayCollectSucceedRateInfo.getElect_device_amount()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryDayCollectSucceedRateInfo.getSucceed_amount())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryDayCollectSucceedRateInfo.getFail_amount())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryDayCollectSucceedRateInfo.getSucceed_rate())));
                index++;
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook queryConfessElectDetailDownload(String name, List<QueryConfessElectDetailInfo> queryLineLostListInfos) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("日期"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户名称"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("门牌号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("表号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("倍率"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电量(KW·h)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("起表码"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("止表码"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("采集时间"));
        cellIndex++;

        XSSFRow row;
        XSSFCell cell;
        QueryConfessElectDetailInfo queryConfessElectDetailInfo;

        if (!CollectionUtils.isEmpty(queryLineLostListInfos)) {
            for (int i = 0; i < queryLineLostListInfos.size(); i++) {
                queryConfessElectDetailInfo = queryLineLostListInfos.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryConfessElectDetailInfo.getCalc_date()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryConfessElectDetailInfo.getCustomer_name()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryConfessElectDetailInfo.getProperty_name()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryConfessElectDetailInfo.getElect_deviceNo()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryConfessElectDetailInfo.getRatio()));
                index++;


                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryConfessElectDetailInfo.getElect())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryConfessElectDetailInfo.getStart_code())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryConfessElectDetailInfo.getEnd_code())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryConfessElectDetailInfo.getCollect_time())));
                index++;
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook querySupplyElectDetailDownload(String name, List<QuerySupplyElectDetailInfo> list) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("日期"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户名称"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电表表号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("倍率"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电量(KW·h)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("起表码"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("止表码"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("采集时间"));
        cellIndex++;

        XSSFRow row;
        XSSFCell cell;
        QuerySupplyElectDetailInfo querySupplyElectDetailInfo;

        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                querySupplyElectDetailInfo = list.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(querySupplyElectDetailInfo.getCalc_date()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(querySupplyElectDetailInfo.getCustomer_name()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(querySupplyElectDetailInfo.getElect_deviceNo()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(querySupplyElectDetailInfo.getRatio())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(querySupplyElectDetailInfo.getElect())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(querySupplyElectDetailInfo.getStart_code())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(querySupplyElectDetailInfo.getEnd_code())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(querySupplyElectDetailInfo.getCollect_time())));
                index++;
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook queryLineLostListDownload(String name, List<QueryLineLostListInfo> list) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("台区名称"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电表数量"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("供电量(KW·H)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("售电量(KW·H)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("损耗电量(KW·H)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("损耗率(%)"));
        cellIndex++;

        XSSFRow row;
        XSSFCell cell;
        QueryLineLostListInfo queryLineLostListInfo;

        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                queryLineLostListInfo = list.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryLineLostListInfo.getPlatform()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryLineLostListInfo.getElectDeviceAmount()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryLineLostListInfo.getPowerSupply())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryLineLostListInfo.getElectSale())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryLineLostListInfo.getElectLoss())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryLineLostListInfo.getOccupation())));
                index++;
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook queryDaySettlementDown(String name, List<DaySettlementResponse> list) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户姓名"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("门牌编号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("表计户号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("表号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("结算时间"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("结算用电量"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("结算用电金额"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("联系方式"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户地址"));
        cellIndex++;

        XSSFRow row;
        XSSFCell cell;
        DaySettlementResponse electDetailDto;

        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                electDetailDto = list.get(i);
                row = sheet.createRow(i + 2);
                int index = 0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(electDetailDto.getCustomerName()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(electDetailDto.getPropertyName()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(electDetailDto.getMeterUserNo())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(electDetailDto.getDeviceNo())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(electDetailDto.getEqTime())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(electDetailDto.getEqValue()==null?"":electDetailDto.getEqValue())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(electDetailDto.getCalcMoney()==null?"":electDetailDto.getCalcMoney())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(electDetailDto.getCustomerContact())));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(electDetailDto.getCustomerAddr())));
                index++;

            }
        }
        return workbook;
    }
    @Override
    public XSSFWorkbook generateMonitorDeviceListExcel(String name, List<MonitorDeviceDto> monitorDeviceDtos) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("设备编号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("设备通信状态"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("端口号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("设备运行状态"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充电时间"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充电时长(分钟)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("剩余时长(分钟)"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充值方式"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("车辆类别"));
        cellIndex++;


        XSSFRow row;
        XSSFCell cell;
        MonitorDeviceDto monitorDeviceDto;
        if(!CollectionUtils.isEmpty(monitorDeviceDtos)){
            for (int i = 0; i < monitorDeviceDtos.size(); i++) {
                monitorDeviceDto = monitorDeviceDtos.get(i);
                row = sheet.createRow(i + 2);

                /*cell = row.createCell(0);
                cell.setCellStyle(cellStyle);*/

                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(monitorDeviceDto.getDeviceNo()));

                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);
                if (monitorDeviceDto.getOnline() == ChargeConstant.DeviceOnlineStatus.ONLINE.getStatus()){
                    cell.setCellValue(new XSSFRichTextString(ChargeConstant.DeviceOnlineStatus.ONLINE.getDesc()));
                }else if (monitorDeviceDto.getOnline() == ChargeConstant.DeviceOnlineStatus.OFFLINE.getStatus()){
                    cell.setCellValue(new XSSFRichTextString(ChargeConstant.DeviceOnlineStatus.OFFLINE.getDesc()));
                }


                cell = row.createCell(2);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(monitorDeviceDto.getPort()));

                cell = row.createCell(3);
                cell.setCellStyle(cellStyle);
                if(monitorDeviceDto.getRunState() == ChargingEnum.FREE_STATE.getKey()){
                    cell.setCellValue(new XSSFRichTextString(ChargingEnum.FREE_STATE.getMessage()));
                }else if (monitorDeviceDto.getRunState() == ChargingEnum.ON_STATE.getKey()){
                    cell.setCellValue(new XSSFRichTextString(ChargingEnum.ON_STATE.getMessage()));
                }else if (monitorDeviceDto.getRunState() == ChargingEnum.OFF_STATE.getKey()){
                    cell.setCellValue(new XSSFRichTextString(ChargingEnum.OFF_STATE.getMessage()));
                }else if (monitorDeviceDto.getRunState() == ChargingEnum.ERROR_STATE.getKey()){
                    cell.setCellValue(new XSSFRichTextString(ChargingEnum.ERROR_STATE.getMessage()));
                }


                cell = row.createCell(4);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(monitorDeviceDto.getStartTime()));


                cell = row.createCell(5);
                cell.setCellStyle(cellStyle);
                if (monitorDeviceDto.getUseTime() == null){
                    cell.setCellValue(new XSSFRichTextString("0"));
                }else {
                    cell.setCellValue(new XSSFRichTextString(monitorDeviceDto.getUseTime().toString()));
                }


                cell = row.createCell(6);
                cell.setCellStyle(cellStyle);
                if (monitorDeviceDto.getRemainTime() != null){
                    cell.setCellValue(new XSSFRichTextString(String.valueOf(monitorDeviceDto.getRemainTime().setScale(0,BigDecimal.ROUND_HALF_DOWN))));
                }else {
                    cell.setCellValue(new XSSFRichTextString(""));
                }


                cell = row.createCell(7);
                cell.setCellStyle(cellStyle);
                if (monitorDeviceDto.getPayCategory() != null){
                    if (monitorDeviceDto.getPayCategory() == ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getType()){
                        cell.setCellValue(new XSSFRichTextString(ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getDesc()));
                    } else if (monitorDeviceDto.getPayCategory() == ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType()){
                        cell.setCellValue(new XSSFRichTextString(ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getDesc()));
                    }else if (monitorDeviceDto.getPayCategory() == ChargeConstant.SchemePayCategory.RECHARGE_FULL.getType()){
                        cell.setCellValue(new XSSFRichTextString(ChargeConstant.SchemePayCategory.RECHARGE_FULL.getDesc()));
                    }else if (monitorDeviceDto.getPayCategory() == ChargeConstant.SchemePayCategory.BALANCE_RECHARGE.getType()){
                        cell.setCellValue(new XSSFRichTextString(ChargeConstant.SchemePayCategory.BALANCE_RECHARGE.getDesc()));
                    }else if (monitorDeviceDto.getPayCategory() == ChargeConstant.SchemePayCategory.IC_RECHARGE.getType()){
                        cell.setCellValue(new XSSFRichTextString(ChargeConstant.SchemePayCategory.IC_RECHARGE.getDesc()));
                    }
                } else {
                    cell.setCellValue(new XSSFRichTextString(""));
                }


                cell = row.createCell(8);
                cell.setCellStyle(cellStyle);
                if (monitorDeviceDto.getCarCategory() != null){
                    if (monitorDeviceDto.getCarCategory() ==1){
                        cell.setCellValue(new XSSFRichTextString("电瓶车"));
                    } else if (monitorDeviceDto.getCarCategory() ==2){
                        cell.setCellValue(new XSSFRichTextString("电动车"));
                    }
                }else {
                    cell.setCellValue(new XSSFRichTextString("电瓶车"));
                }

            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generateElectricAndFeeListExcel(String name, List<ElectricAndFeeDto> electricAndFeeDtoList) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("设备编号"));
        cellIndex++;


        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("安装地址"));
        cellIndex++;


        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("使用次数"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电量（KW·H）"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电费（元）"));
        cellIndex++;


        XSSFRow row;
        XSSFCell cell;
        ElectricAndFeeDto electricAndFeeDto;
        if(!CollectionUtils.isEmpty(electricAndFeeDtoList)){
            for (int i = 0; i < electricAndFeeDtoList.size(); i++) {
                electricAndFeeDto = electricAndFeeDtoList.get(i);
                row = sheet.createRow(i + 2);

                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(electricAndFeeDto.getDeviceNo()));

                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(electricAndFeeDto.getInstallAddr()));

                cell = row.createCell(2);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(electricAndFeeDto.getUseNumber().toString()));


                cell = row.createCell(3);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(electricAndFeeDto.getElectricQuantity().toString()));

                cell = row.createCell(4);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(electricAndFeeDto.getElectricityFees().toString()));

            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generateUseDetailedListExcel(String name, List<ChargingUseDetailedDto> chargingUseDetailedDtos) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("端口号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("类别"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("计费方式"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充值费用（元）"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("使用电量（KW·H）"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("盈利费用（元）"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("状态"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充电时长（分钟）"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("开始时间"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("结束时间"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("人员ID"));
        cellIndex++;


        XSSFRow row;
        XSSFCell cell;
        ChargingUseDetailedDto chargingUseDetailedDto;
        if(!CollectionUtils.isEmpty(chargingUseDetailedDtos)){
            for (int i = 0; i < chargingUseDetailedDtos.size(); i++) {
                chargingUseDetailedDto = chargingUseDetailedDtos.get(i);
                row = sheet.createRow(i + 2);

                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(chargingUseDetailedDto.getPort()));

                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);
                if (chargingUseDetailedDto.getCarCategory() != null){
                    if (chargingUseDetailedDto.getCarCategory() == 1){
                        cell.setCellValue(new XSSFRichTextString("电瓶车"));
                    }else if (chargingUseDetailedDto.getCarCategory() == 2){
                        cell.setCellValue(new XSSFRichTextString("电动车"));
                    }
                }else {
                    cell.setCellValue(new XSSFRichTextString("电瓶车"));
                }

                cell = row.createCell(2);
                cell.setCellStyle(cellStyle);
                if (chargingUseDetailedDto.getPayCategory() == ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getType()){
                    cell.setCellValue(new XSSFRichTextString(ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getDesc()));
                } else if (chargingUseDetailedDto.getPayCategory() == ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType()){
                    cell.setCellValue(new XSSFRichTextString(ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getDesc()));
                } else if (chargingUseDetailedDto.getPayCategory() == ChargeConstant.SchemePayCategory.RECHARGE_FULL.getType()){
                    cell.setCellValue(new XSSFRichTextString(ChargeConstant.SchemePayCategory.RECHARGE_FULL.getDesc()));
                }else if (chargingUseDetailedDto.getPayCategory() == ChargeConstant.SchemePayCategory.BALANCE_RECHARGE.getType()){
                    cell.setCellValue(new XSSFRichTextString(ChargeConstant.SchemePayCategory.BALANCE_RECHARGE.getDesc()));
                }else if (chargingUseDetailedDto.getPayCategory() == ChargeConstant.SchemePayCategory.IC_RECHARGE.getType()){
                    cell.setCellValue(new XSSFRichTextString(ChargeConstant.SchemePayCategory.IC_RECHARGE.getDesc()));
                }

                cell = row.createCell(3);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(chargingUseDetailedDto.getPayMoney())));

                cell = row.createCell(4);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(chargingUseDetailedDto.getUsePower())));

                cell = row.createCell(5);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(chargingUseDetailedDto.getProfitable())));

                cell = row.createCell(6);
                cell.setCellStyle(cellStyle);
                if (chargingUseDetailedDto.getState() == 0){
                    cell.setCellValue(new XSSFRichTextString("正在充电"));
                }else if (chargingUseDetailedDto.getState() == 1){
                    cell.setCellValue(new XSSFRichTextString("已完成"));
                }


                cell = row.createCell(7);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(chargingUseDetailedDto.getUseTime())));

                cell = row.createCell(8);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(chargingUseDetailedDto.getStartTime()));

                cell = row.createCell(9);
                cell.setCellStyle(cellStyle);
                if (!StringUtil.isEmpty(chargingUseDetailedDto.getEndTime())){
                    cell.setCellValue(new XSSFRichTextString(chargingUseDetailedDto.getEndTime()));
                }else {
                    cell.setCellValue(new XSSFRichTextString(""));
                }

                cell = row.createCell(10);
                cell.setCellStyle(cellStyle);
                if (!StringUtil.isEmpty(chargingUseDetailedDto.getCustomerGuid())){
                    cell.setCellValue(new XSSFRichTextString(chargingUseDetailedDto.getCustomerGuid()));
                }else if (!StringUtil.isEmpty(chargingUseDetailedDto.getOpenMeans() == 3)){
                    cell.setCellValue(new XSSFRichTextString(chargingUseDetailedDto.getOpenNo()));
                } else {
                    cell.setCellValue(new XSSFRichTextString(""));
                }

            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generateChargingDeviceListExcel(String name, List<ChargingDeviceDto> chargingDevices) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("设备编号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("设备程序版本"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("端口1"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("端口2"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("通信方式"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("通信编号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("安装时间"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("安装地址"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("备注"));
        cellIndex++;


        XSSFRow row;
        XSSFCell cell;
        ChargingDeviceDto chargingDeviceDto;
        if(!CollectionUtils.isEmpty(chargingDevices)){
            for (int i = 0; i < chargingDevices.size(); i++) {
                chargingDeviceDto = chargingDevices.get(i);
                row = sheet.createRow(i + 2);

                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(chargingDeviceDto.getDeviceNo()));

                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(chargingDeviceDto.getVer())));

                cell = row.createCell(2);
                cell.setCellStyle(cellStyle);
                if (chargingDeviceDto.getOnline() == ChargeConstant.DeviceOnlineStatus.ONLINE.getStatus()){
                    if(chargingDeviceDto.getRunState1() == ChargingEnum.FREE_STATE.getKey()){
                        cell.setCellValue(new XSSFRichTextString(ChargingEnum.FREE_STATE.getMessage()));
                    }else if (chargingDeviceDto.getRunState1() == ChargingEnum.ON_STATE.getKey()){
                        cell.setCellValue(new XSSFRichTextString(ChargingEnum.ON_STATE.getMessage()));
                    }else if (chargingDeviceDto.getRunState1() == ChargingEnum.OFF_STATE.getKey()){
                        cell.setCellValue(new XSSFRichTextString(ChargingEnum.OFF_STATE.getMessage()));
                    }else if (chargingDeviceDto.getRunState1() == ChargingEnum.ERROR_STATE.getKey()){
                        cell.setCellValue(new XSSFRichTextString(ChargingEnum.ERROR_STATE.getMessage()));
                    }
                }else {
                    cell.setCellValue(new XSSFRichTextString("离线"));
                }


                cell = row.createCell(3);
                cell.setCellStyle(cellStyle);
                if (chargingDeviceDto.getOnline() == ChargeConstant.DeviceOnlineStatus.ONLINE.getStatus()) {
                    if (chargingDeviceDto.getRunState2() == ChargingEnum.FREE_STATE.getKey()) {
                        cell.setCellValue(new XSSFRichTextString(ChargingEnum.FREE_STATE.getMessage()));
                    } else if (chargingDeviceDto.getRunState2() == ChargingEnum.ON_STATE.getKey()) {
                        cell.setCellValue(new XSSFRichTextString(ChargingEnum.ON_STATE.getMessage()));
                    } else if (chargingDeviceDto.getRunState2() == ChargingEnum.OFF_STATE.getKey()) {
                        cell.setCellValue(new XSSFRichTextString(ChargingEnum.OFF_STATE.getMessage()));
                    } else if (chargingDeviceDto.getRunState2() == ChargingEnum.ERROR_STATE.getKey()) {
                        cell.setCellValue(new XSSFRichTextString(ChargingEnum.ERROR_STATE.getMessage()));
                    }
                }else {
                    cell.setCellValue(new XSSFRichTextString("离线"));
                }

                cell = row.createCell(4);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(chargingDeviceDto.getComMethodName()));

                cell = row.createCell(5);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(chargingDeviceDto.getCommNo()));

                cell = row.createCell(6);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(chargingDeviceDto.getInstallDate()));

                cell = row.createCell(7);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(chargingDeviceDto.getInstallAddr()));

                cell = row.createCell(8);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(chargingDeviceDto.getRemark()));

            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generateelectDetailExcel(String name, ElectRecordInfo electRecordInfo) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户姓名"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("门牌编号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用电量"));
        cellIndex++;


        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("消费金额"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("剩余金额"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户地址"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("联系电话"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("部门机构"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("汇总天数"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("缺失数据天数"));
        cellIndex++;


        XSSFRow row;
        XSSFCell cell;
        ElectConsumptionResponseParam electDetailDto;
        if(electRecordInfo!=null) {
            List  list = electRecordInfo.getList();
            int   size = list.size();

            if (!CollectionUtils.isEmpty(list)) {
                for (int i = 0; i < list.size(); i++) {
                    electDetailDto = (ElectConsumptionResponseParam) list.get(i);
                    row = sheet.createRow(i + 2);
                    int index = 0;
                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(new XSSFRichTextString(electDetailDto.getCustomer_name()));
                    index++;

                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(new XSSFRichTextString(electDetailDto.getProperty_name()));
                    index++;

                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(new XSSFRichTextString(String.valueOf(electDetailDto.getDay_eq_value())));
                    index++;

                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(new XSSFRichTextString(String.valueOf(electDetailDto.getDay_calc_money())));
                    index++;

                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(new XSSFRichTextString(String.valueOf(electDetailDto.getRemain_amount()==null?"":
                            electDetailDto.getRemain_amount().setScale(2,BigDecimal.ROUND_HALF_UP))));
                    index++;

                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(new XSSFRichTextString(electDetailDto.getCustomer_addr()));
                    index++;

                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(new XSSFRichTextString(String.valueOf(electDetailDto.getCustomer_contact())));
                    index++;

                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(new XSSFRichTextString(String.valueOf(electDetailDto.getOrg_name())));
                    index++;

                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(new XSSFRichTextString(String.valueOf(electDetailDto.getAnalyze_day())));
                    index++;

                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(new XSSFRichTextString(String.valueOf(electDetailDto.getErr_day())));
                    index++;
                }
            }
            row = sheet.createRow(size + 2);
            cell = row.createCell(0);
            cell.setCellStyle(cellStyleTitle);
            cell.setCellValue(new XSSFRichTextString("汇总用电量"));
            cell = row.createCell(1);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(new XSSFRichTextString(String.valueOf(electRecordInfo.getStatistics().getSumPower()!=null?electRecordInfo.getStatistics().getSumPower():0)));
            cell = row.createCell(2);
            cell.setCellStyle(cellStyleTitle);
            cell.setCellValue(new XSSFRichTextString("汇总消费金额"));
            cell = row.createCell(3);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(new XSSFRichTextString(String.valueOf(electRecordInfo.getStatistics().getSumMoney()!=null?electRecordInfo.getStatistics().getSumMoney():0)));
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generateRealTimeDataExcel(String name, List<String> types,List<MeterCollectDataListInfo> list)  {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("状态"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户姓名"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户表号"));
        cellIndex++;

        // 总
        if (types.contains(ClientConstant.CommonUserDIEnum.PR0.getDiValue())) {
            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("正向有功总"));
            cellIndex++;
        }

        // 剩余金额
        if (types.contains(ClientConstant.CommonUserDIEnum.BALANCE.getDiValue())) {
            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("剩余金额（元）"));
            cellIndex++;
        }

        // 月冻结总
        if (types.contains(ClientConstant.CommonUserDIEnum.MONTH_FREEZE_P.getDiValue())) {
            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("月冻结总"));
            cellIndex++;
        }

        // 日冻结总
        if (types.contains(ClientConstant.CommonUserDIEnum.DAY_FREEZE_P.getDiValue())) {
            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("日冻结总"));
            cellIndex++;
        }

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("联系方式"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("表计户号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户地址"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("集中器"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("时间"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("门牌编号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("开户状态"));
        cellIndex++;

        XSSFRow row;
        XSSFCell cell;
        MeterCollectDataListInfo meterCollectData;

        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                meterCollectData = list.get(i);
                row = sheet.createRow(i + 2);

                int index = 0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                Integer isOnline = meterCollectData.getIsOnline();
                if(isOnline != null){
                    if(RealTimeDataConstant.OnlineStatus.ONLINE.getStatus().equals(isOnline)){
                        cell.setCellValue(new XSSFRichTextString(RealTimeDataConstant.OnlineStatus.ONLINE.getDesc()));
                    }else{
                        cell.setCellValue(new XSSFRichTextString(RealTimeDataConstant.OnlineStatus.OFFLINE.getDesc()));
                    }
                }else {
                    cell.setCellValue(new XSSFRichTextString("-"));
                }
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(meterCollectData.getCustomerName()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(meterCollectData.getDeviceNo()));
                index++;

                // 总
                if (types.contains(ClientConstant.CommonUserDIEnum.PR0.getDiValue())) {
                    cell = row.createCell(index);
                    BigDecimal pr0 = meterCollectData.getPr0();
                    if(pr0 == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(String.valueOf(pr0)));
                    }
                    cell.setCellStyle(cellStyle);
                    index++;
                }

                // 剩余金额
                if (types.contains(ClientConstant.CommonUserDIEnum.BALANCE.getDiValue())) {
                    cell = row.createCell(index);
                    BigDecimal balance = meterCollectData.getBalance();
                    if(balance == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(String.valueOf(balance)));
                    }
                    cell.setCellStyle(cellStyle);
                    index++;
                }

                // 月冻结总
                if (types.contains(ClientConstant.CommonUserDIEnum.MONTH_FREEZE_P.getDiValue())) {
                    cell = row.createCell(index);
                    BigDecimal monthFreezeP = meterCollectData.getMonthFreezeP();
                    if(monthFreezeP == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(String.valueOf(monthFreezeP)));
                    }
                    cell.setCellStyle(cellStyle);
                    index++;
                }

                // 日冻结总
                if (types.contains(ClientConstant.CommonUserDIEnum.DAY_FREEZE_P.getDiValue())) {
                    cell = row.createCell(index);
                    BigDecimal dayFreezeP = meterCollectData.getDayFreezeP();
                    if(dayFreezeP == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(String.valueOf(dayFreezeP)));
                    }
                    cell.setCellStyle(cellStyle);
                    index++;
                }

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(meterCollectData.getCustomerContact()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(meterCollectData.getMeterUserNo()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(meterCollectData.getCustomerAddr()));
                index++;


                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if(meterCollectData.getJzqCno() == null){
                    cell.setCellValue(new XSSFRichTextString(""));
                }else {
                    cell.setCellValue(new XSSFRichTextString(meterCollectData.getJzqNo()));
                }
                index++;

                cell = row.createCell(index);
                if(meterCollectData.getCollectTime() == null){
                    cell.setCellValue(new XSSFRichTextString(""));
                }else {
                    cell.setCellValue(new XSSFRichTextString(String.valueOf(meterCollectData.getCollectTime())));
                }
                cell.setCellStyle(cellStyle);
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(meterCollectData.getPropertyName()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if(meterCollectData.getIsAccount() == 0){
                    cell.setCellValue(new XSSFRichTextString("未开户"));
                }else {
                    cell.setCellValue(new XSSFRichTextString("已开户"));
                }
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generateImpRealTimeDataExcel(String name, List<String> types,List<CollectImportantDataListInfo> list) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex =0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("状态"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户姓名"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户表号"));
        cellIndex++;

        // 总
        if (types.contains(ClientConstant.ImpUserDIEnum.PR0.getDiValue())) {
            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("正向有功总示数"));
            cellIndex++;
        }

        // ABC电流
        if (types.contains(ClientConstant.ImpUserDIEnum.ABC_CURRENT.getDiValue())) {
            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("A相电流"));
            cellIndex++;

            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("B相电流"));
            cellIndex++;

            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("C相电流"));
            cellIndex++;
        }

        // ABC电压
        if (types.contains(ClientConstant.ImpUserDIEnum.ABC_VOLTAGE.getDiValue())) {
            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("A相电压"));
            cellIndex++;

            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("B相电压"));
            cellIndex++;

            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("C相电压"));
            cellIndex++;
        }

        // 瞬时有功功率
        if (types.contains(ClientConstant.ImpUserDIEnum.INSTANT_POWER.getDiValue())) {
            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("瞬时正向有功总"));
            cellIndex++;

            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("瞬时正向有功A相功率"));
            cellIndex++;

            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("瞬时正向有功B相功率"));
            cellIndex++;

            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("瞬时正向有功C相功率"));
            cellIndex++;
        }

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("时间"));

        XSSFRow row;
        XSSFCell cell;
        CollectImportantDataListInfo listInfo;

        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                listInfo = list.get(i);
                row = sheet.createRow(i + 2);

                int index = 0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                Integer isOnline = listInfo.getIsOnline();
                if(isOnline != null){
                    if(RealTimeDataConstant.OnlineStatus.ONLINE.getStatus().equals(isOnline)){
                        cell.setCellValue(new XSSFRichTextString(RealTimeDataConstant.OnlineStatus.ONLINE.getDesc()));
                    }else{
                        cell.setCellValue(new XSSFRichTextString(RealTimeDataConstant.OnlineStatus.OFFLINE.getDesc()));
                    }
                }else {
                    cell.setCellValue(new XSSFRichTextString("-"));
                }
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(listInfo.getCustomerName()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(listInfo.getDeviceNo()));
                index++;

                // 总
                if (types.contains(ClientConstant.ImpUserDIEnum.PR0.getDiValue())) {
                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    BigDecimal pr0 = listInfo.getPr0();
                    if(pr0 == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(String.valueOf(pr0)));
                    }
                    index++;
                }

                // ABC电流
                if (types.contains(ClientConstant.ImpUserDIEnum.ABC_CURRENT.getDiValue())) {
                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    BigDecimal currentA = listInfo.getCurrentA();
                    if(currentA == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(currentA.toString()));
                    }
                    index++;

                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    BigDecimal currentB = listInfo.getCurrentB();
                    if(currentB == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(currentB.toString()));
                    }
                    index++;

                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    BigDecimal currentC = listInfo.getCurrentC();
                    if(currentC == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(currentC.toString()));
                    }
                    index++;
                }

                // ABC电压
                if (types.contains(ClientConstant.ImpUserDIEnum.ABC_VOLTAGE.getDiValue())) {
                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    BigDecimal voltageA = listInfo.getVoltageA();
                    if(voltageA == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(voltageA.toString()));
                    }
                    index++;

                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    BigDecimal voltageB = listInfo.getVoltageB();
                    if(voltageB == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(voltageB.toString()));
                    }
                    index++;

                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    BigDecimal voltageC = listInfo.getVoltageC();
                    if(voltageC == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(voltageC.toString()));
                    }
                    index++;
                }

                // 瞬时有功功率
                if (types.contains(ClientConstant.ImpUserDIEnum.INSTANT_POWER.getDiValue())) {
                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    BigDecimal instantPower = listInfo.getInstantPower();
                    if(instantPower == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(instantPower.toString()));
                    }
                    index++;

                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    BigDecimal instantPowerA = listInfo.getInstantPowerA();
                    if(instantPowerA == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(instantPowerA.toString()));
                    }
                    index++;

                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    BigDecimal instantPowerB = listInfo.getInstantPowerB();
                    if(instantPowerB == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(instantPowerB.toString()));
                    }
                    index++;

                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    BigDecimal instantPowerC = listInfo.getInstantPowerC();
                    if(instantPowerC == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(instantPowerC.toString()));
                    }
                    index++;
                }

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if(listInfo.getCollectTime() == null){
                    cell.setCellValue(new XSSFRichTextString(""));
                }else {
                    cell.setCellValue(new XSSFRichTextString(String.valueOf(listInfo.getCollectTime())));
                }
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generateHistoricalDataExcel(String name, List<String> types, List<CollectDataDownInfo> list) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户姓名"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户表号"));
        cellIndex++;

        // 前端选择了总
        if (types.contains(ClientConstant.CommonUserDIEnum.PR0.getDiValue())) {
            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("正向有功总"));
            cellIndex++;
        }

        // 前端选择了剩余金额
        if (types.contains(ClientConstant.CommonUserDIEnum.BALANCE.getDiValue())) {
            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("剩余金额(元)"));
            cellIndex++;
        }

        // 前端选择了月冻结总
        if (types.contains(ClientConstant.CommonUserDIEnum.MONTH_FREEZE_P.getDiValue())) {
            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("月冻结总"));
            cellIndex++;
        }

        // 前端选择了日冻结总
        if (types.contains(ClientConstant.CommonUserDIEnum.DAY_FREEZE_P.getDiValue())) {
            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("日冻结总"));
            cellIndex++;
        }


        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("联系方式"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("表计户号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户地址"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("部门机构"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("时间"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("门牌编号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("开户状态"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("数据类型"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("旧表表号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("旧表总"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("旧表剩余金额"));
        cellIndex++;

        XSSFRow row;
        XSSFCell cell;
        CollectDataDownInfo meterCollectData;

        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                meterCollectData = list.get(i);
                row = sheet.createRow(i + 2);

                int index = 0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(meterCollectData.getCustomerName()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(meterCollectData.getDeviceCno().substring(meterCollectData.getDeviceCno().length() -12)));
                index++;

                // 前端选择了总
                if (types.contains(ClientConstant.CommonUserDIEnum.PR0.getDiValue())) {
                    cell = row.createCell(index);
                    if(meterCollectData.getPr0() == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(String.valueOf(meterCollectData.getPr0())));
                    }
                    cell.setCellStyle(cellStyle);
                    index++;
                }

                // 前端选择了剩余金额
                if (types.contains(ClientConstant.CommonUserDIEnum.BALANCE.getDiValue())) {
                    cell = row.createCell(index);
                    if(meterCollectData.getBalance() == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(String.valueOf(meterCollectData.getBalance())));
                    }
                    cell.setCellStyle(cellStyle);
                    index++;
                }

                // 前端选择了月冻结总
                if (types.contains(ClientConstant.CommonUserDIEnum.MONTH_FREEZE_P.getDiValue())) {
                    cell = row.createCell(index);
                    BigDecimal monthFreezeP = meterCollectData.getMonthFreezeP();
                    if(monthFreezeP == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(String.valueOf(monthFreezeP)));
                    }
                    cell.setCellStyle(cellStyle);
                    index++;
                }

                // 前端选择了日冻结总
                if (types.contains(ClientConstant.CommonUserDIEnum.DAY_FREEZE_P.getDiValue())) {
                    cell = row.createCell(index);
                    BigDecimal dayFreezeP = meterCollectData.getDayFreezeP();
                    if(dayFreezeP == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(String.valueOf(dayFreezeP)));
                    }
                    cell.setCellStyle(cellStyle);
                    index++;
                }

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(meterCollectData.getCustomerContact()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(meterCollectData.getMeterUserNo()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(meterCollectData.getCustomerAddr()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(meterCollectData.getOrgName()));
                index++;

                cell = row.createCell(index);
                if(meterCollectData.getCollectTime() == null){
                    cell.setCellValue(new XSSFRichTextString(""));
                }else {
                    cell.setCellValue(new XSSFRichTextString(String.valueOf(meterCollectData.getCollectTime())));
                }
                cell.setCellStyle(cellStyle);
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(meterCollectData.getPropertyName()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if(meterCollectData.getIsAccount() == 0){
                    cell.setCellValue(new XSSFRichTextString("未开户"));
                }else {
                    cell.setCellValue(new XSSFRichTextString("已开户"));
                }
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if(meterCollectData.getIsRealTime() == 0){
                    cell.setCellValue(new XSSFRichTextString("零点冻结"));
                }else {
                    cell.setCellValue(new XSSFRichTextString("实时召测"));
                }
                index++;

                String meterNo = meterCollectData.getOldMeterNo();
                boolean exists = StringUtils.isEmpty(meterNo);

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if(exists){
                    cell.setCellValue(new XSSFRichTextString(""));
                }else {
                    cell.setCellValue(new XSSFRichTextString(meterCollectData.getOldMeterNo()));
                }
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if(exists){
                    cell.setCellValue(new XSSFRichTextString(""));
                }else {
                    BigDecimal power = meterCollectData.getPower();
                    if (power == null) {
                        cell.setCellValue(new XSSFRichTextString(""));
                    } else {
                        cell.setCellValue(new XSSFRichTextString(String.valueOf(power)));
                    }
                }
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                if(exists){
                    cell.setCellValue(new XSSFRichTextString(""));
                }else {
                    BigDecimal remainAmount = meterCollectData.getRemainAmount();
                    if (remainAmount == null) {
                        cell.setCellValue(new XSSFRichTextString(""));
                    } else {
                        cell.setCellValue(new XSSFRichTextString(String.valueOf(remainAmount)));
                    }
                }
                index++;
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generateImpHistoricalDataExcel(String name, List<String> types, List<ImpCollectDataGetInfo> list) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);

        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        int cellIndex = 0;
        XSSFCell cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户姓名"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户表号"));
        cellIndex++;

        // 总
        if (types.contains(ClientConstant.ImpUserDIEnum.PR0.getDiValue())) {
            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("有功总"));
            cellIndex++;
        }

        // ABC电流
        if (types.contains(ClientConstant.ImpUserDIEnum.ABC_CURRENT.getDiValue())) {
            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("A相电流"));
            cellIndex++;

            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("B相电流"));
            cellIndex++;

            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("C相电流"));
            cellIndex++;
        }

        // ABC电压
        if (types.contains(ClientConstant.ImpUserDIEnum.ABC_VOLTAGE.getDiValue())) {
            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("A相电压"));
            cellIndex++;

            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("B相电压"));
            cellIndex++;

            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("C相电压"));
            cellIndex++;
        }

        // 瞬时有功功率
        if (types.contains(ClientConstant.ImpUserDIEnum.INSTANT_POWER.getDiValue())) {
            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("瞬时正向有功总"));
            cellIndex++;

            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("瞬时正向有功A相"));
            cellIndex++;

            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("瞬时正向有功B相"));
            cellIndex++;

            cell1 = row1.createCell(cellIndex);
            cell1.setCellStyle(cellStyleTitle);
            cell1.setCellValue(new XSSFRichTextString("瞬时正向有功C相"));
            cellIndex++;
        }

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("联系方式"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("表计户号"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户地址"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("部门机构"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("时间"));
        cellIndex++;

        cell1 = row1.createCell(cellIndex);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("门牌编号"));
        cellIndex++;

        XSSFRow row;
        XSSFCell cell;
        ImpCollectDataGetInfo dataGetInfo;
        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                dataGetInfo = list.get(i);
                row = sheet.createRow(i + 2);

                int index = 0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(dataGetInfo.getCustomerName()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(dataGetInfo.getDeviceCno().substring(dataGetInfo.getDeviceCno().length() -12)));
                index++;

                // 总
                if (types.contains(ClientConstant.ImpUserDIEnum.PR0.getDiValue())) {
                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    BigDecimal pr0 = dataGetInfo.getPr0();
                    if(pr0 == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(String.valueOf(pr0)));
                    }
                    index++;
                }

                // ABC电流
                if (types.contains(ClientConstant.ImpUserDIEnum.ABC_CURRENT.getDiValue())) {
                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    BigDecimal currentA = dataGetInfo.getCurrentA();
                    if(currentA == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(String.valueOf(currentA)));
                    }
                    index++;

                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    BigDecimal currentB = dataGetInfo.getCurrentB();
                    if(currentB == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(String.valueOf(currentB)));
                    }
                    index++;

                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    BigDecimal currentC = dataGetInfo.getCurrentC();
                    if(currentC == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(String.valueOf(currentC)));
                    }
                    index++;
                }

                // ABC电压
                if (types.contains(ClientConstant.ImpUserDIEnum.ABC_VOLTAGE.getDiValue())) {
                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    BigDecimal voltageA = dataGetInfo.getVoltageA();
                    if(voltageA == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(String.valueOf(voltageA)));
                    }
                    index++;

                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    BigDecimal voltageB = dataGetInfo.getVoltageB();
                    if(voltageB == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(String.valueOf(voltageB)));
                    }
                    index++;

                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    BigDecimal voltageC = dataGetInfo.getVoltageC();
                    if(voltageC == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(String.valueOf(voltageC)));
                    }
                    index++;
                }

                // 瞬时有功功率
                if (types.contains(ClientConstant.ImpUserDIEnum.INSTANT_POWER.getDiValue())) {
                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    BigDecimal instantPower = dataGetInfo.getInstantPower();
                    if(instantPower == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(String.valueOf(instantPower)));
                    }
                    index++;

                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    BigDecimal instantPowerA = dataGetInfo.getInstantPowerA();
                    if(instantPowerA == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(String.valueOf(instantPowerA)));
                    }
                    index++;

                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    BigDecimal instantPowerB = dataGetInfo.getInstantPowerB();
                    if(instantPowerB == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(String.valueOf(instantPowerB)));
                    }
                    index++;

                    cell = row.createCell(index);
                    cell.setCellStyle(cellStyle);
                    BigDecimal instantPowerC = dataGetInfo.getInstantPowerC();
                    if(instantPowerC == null){
                        cell.setCellValue(new XSSFRichTextString(""));
                    }else {
                        cell.setCellValue(new XSSFRichTextString(String.valueOf(instantPowerC)));
                    }
                    index++;
                }

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(dataGetInfo.getCustomerContact()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(dataGetInfo.getMeterUserNo()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(dataGetInfo.getCustomerAddr()));
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(dataGetInfo.getOrgName()));
                index++;

                cell = row.createCell(index);
                if(dataGetInfo.getCollectTime() == null){
                    cell.setCellValue(new XSSFRichTextString(""));
                }else {
                    cell.setCellValue(new XSSFRichTextString(String.valueOf(dataGetInfo.getCollectTime())));
                }
                cell.setCellStyle(cellStyle);
                index++;

                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(dataGetInfo.getPropertyName()));
            }
        }
        return workbook;
    }
    @Override
    public XSSFWorkbook generateData4MonthExcel(String name, String deviceType,List<CustomerData4Month> list) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);

        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(0);
        XSSFCell cell1 = row1.createCell(0);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("时间"));

        cell1 = row1.createCell(1);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("表号"));

        cell1 = row1.createCell(2);
        cell1.setCellStyle(cellStyleTitle);
        if (DeviceType.ELECTRIC_METER.getCode().equals(deviceType)) {
            cell1.setCellValue(new XSSFRichTextString("总(度)"));
        } else if (DeviceType.WATER_METER.getCode().equals(deviceType)) {
            cell1.setCellValue(new XSSFRichTextString("总(吨)"));
        } else if (DeviceType.GAS_METER.getCode().equals(deviceType)) {
            cell1.setCellValue(new XSSFRichTextString("总(立方)"));
        }

        cell1 = row1.createCell(3);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("剩余金额(元)"));

        cell1 = row1.createCell(4);
        cell1.setCellStyle(cellStyleTitle);
        if (DeviceType.ELECTRIC_METER.getCode().equals(deviceType)) {
            cell1.setCellValue(new XSSFRichTextString("购电总金额(元)"));
        } else if (DeviceType.WATER_METER.getCode().equals(deviceType)) {
            cell1.setCellValue(new XSSFRichTextString("购水总金额(元)"));
        } else if (DeviceType.GAS_METER.getCode().equals(deviceType)) {
            cell1.setCellValue(new XSSFRichTextString("购气总金额(元)"));
        }

        cell1 = row1.createCell(5);
        cell1.setCellStyle(cellStyleTitle);
        if (DeviceType.ELECTRIC_METER.getCode().equals(deviceType)) {
            cell1.setCellValue(new XSSFRichTextString("购电次数"));
        } else if (DeviceType.WATER_METER.getCode().equals(deviceType)) {
            cell1.setCellValue(new XSSFRichTextString("购水次数"));
        } else if (DeviceType.GAS_METER.getCode().equals(deviceType)) {
            cell1.setCellValue(new XSSFRichTextString("购气次数"));
        }

        XSSFRow row;
        XSSFCell cell;
        CustomerData4Month customerData4Month = null;

        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                customerData4Month = list.get(i);
                row = sheet.createRow(i + 1);

                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(customerData4Month.getCollectDate());

                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(customerData4Month.getDeviceNo());

                cell = row.createCell(2);
                cell.setCellStyle(cellStyle);
                if(customerData4Month.getPr0() == null){
                    cell.setCellValue("");
                }else{
                    cell.setCellValue(new XSSFRichTextString(customerData4Month.getPr0().toString()));
                }

                cell = row.createCell(3);
                cell.setCellStyle(cellStyle);
                if(customerData4Month.getBalance() == null){
                    cell.setCellValue("");
                }else{
                    cell.setCellValue(new XSSFRichTextString(customerData4Month.getBalance().toString()));
                }

                cell = row.createCell(4);
                cell.setCellStyle(cellStyle);
                BigDecimal payMoney = customerData4Month.getPayMoney();
                cell.setCellValue(new XSSFRichTextString(payMoney.toString()));

                cell = row.createCell(5);
                cell.setCellStyle(cellStyle);
                int payCount = customerData4Month.getPayCount();
                cell.setCellValue(new XSSFRichTextString(String.valueOf(payCount)));
            }
        }
        return workbook;
    }
    @Override
    public XSSFWorkbook generateData4MonthExcel(String name, List<CustomerData4Month> list, String customerNo)  {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);
        CustomerInfo customerInfo = customerInfoService.queryByCustomerNo(customerNo);
        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(0);

        XSSFCell cell1 = row1.createCell(0);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("时间"));

        cell1 = row1.createCell(1);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("表号"));

        cell1 = row1.createCell(2);
        cell1.setCellStyle(cellStyleTitle);

        cell1.setCellValue(new XSSFRichTextString("有功总"));


        cell1 = row1.createCell(3);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("剩余金额(元)"));

        cell1 = row1.createCell(4);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户姓名"));
        cell1 = row1.createCell(5);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户电话"));
        cell1 = row1.createCell(6);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户地址"));
        cell1 = row1.createCell(7);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("数据类型"));
        XSSFRow row;
        XSSFCell cell;
        CustomerData4Month customerData4Month;

        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                customerData4Month = list.get(i);
                row = sheet.createRow(i + 1);

                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(customerData4Month.getCollectDate());

                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(customerData4Month.getDeviceNo());

                cell = row.createCell(2);
                cell.setCellStyle(cellStyle);
                if(customerData4Month.getPr0() == null){
                    cell.setCellValue("");
                }else{
                    cell.setCellValue(new XSSFRichTextString(customerData4Month.getPr0().toString()));
                }

                cell = row.createCell(3);
                cell.setCellStyle(cellStyle);
                if(customerData4Month.getBalance() == null){
                    cell.setCellValue("");
                }else{
                    cell.setCellValue(new XSSFRichTextString(customerData4Month.getBalance().toString()));
                }


                cell = row.createCell(4);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(customerInfo.getCustomerName())));


                cell = row.createCell(5);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(customerInfo.getCustomerContact())));

                cell = row.createCell(6);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(customerInfo.getCustomerAddr())));

                cell = row.createCell(7);
                cell.setCellStyle(cellStyle);
                String dataType = String.valueOf(customerData4Month.getDataType());
                if(!StringUtils.isEmpty(dataType)){
                    if("1".equals(dataType)){
                        cell.setCellValue(new XSSFRichTextString("实时"));
                    }else {
                        cell.setCellValue(new XSSFRichTextString("冻结"));
                    }

                }
            }
        }
        return workbook;
    }
    @Override
    public XSSFWorkbook generateCustomerRecordExcel(String name, List<CustomerInfoDtodownload> list) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);

        XSSFCell cell1 = row1.createCell(0);

        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户姓名"));

        cell1 = row1.createCell(1);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("联系电话"));

        cell1 = row1.createCell(2);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户地址"));

        cell1 = row1.createCell(3);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("部门机构"));

        cell1 = row1.createCell(4);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("门牌编号"));

        cell1 = row1.createCell(5);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("下发状态"));

        cell1 = row1.createCell(6);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("表计类型"));

        cell1 = row1.createCell(7);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("表计编号"));

        cell1 = row1.createCell(8);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("告警阀值"));

        cell1 = row1.createCell(9);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("表计户号"));

        cell1 = row1.createCell(10);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("变压器编号"));

        cell1 = row1.createCell(11);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("楼栋编号"));

        cell1 = row1.createCell(12);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户状态"));
        cell1 = row1.createCell(13);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("创建时间"));
        cell1 = row1.createCell(14);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("创建人"));

        XSSFRow row;
        XSSFCell cell;

        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                CustomerInfoDtodownload customerInfo = list.get(i);
                row = sheet.createRow(i + 2);



                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(customerInfo.getCustomerName()));

                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(customerInfo.getCustomerContact()));

                cell = row.createCell(2);
                cell.setCellStyle(cellStyle);
                if(!StringUtils.isEmpty(customerInfo)){
                    cell.setCellValue(new XSSFRichTextString(customerInfo.getCustomerAddr()));
                }else{
                    cell.setCellValue("");
                }
                cell = row.createCell(3);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(customerInfo.getOrgName()));
                cell = row.createCell(4);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(customerInfo.getPropertyName()));

                cell = row.createCell(5);
                cell.setCellStyle(cellStyle);
                if(customerInfo.getSendFlag().equals(0)){
                    cell.setCellValue(new XSSFRichTextString("待下发"));

                }else{
                    cell.setCellValue(new XSSFRichTextString("已下发"));
                }

                cell = row.createCell(6);
                cell.setCellStyle(cellStyle);
                if (customerInfo.getDeviceType().equals(DeviceType.ELECTRIC_METER.getCode())) {
                    cell.setCellValue(new XSSFRichTextString(DeviceType.ELECTRIC_METER.getMessage()));
                }
                if (customerInfo.getDeviceType().equals(DeviceType.WATER_METER.getCode())) {
                    cell.setCellValue(new XSSFRichTextString(DeviceType.WATER_METER.getMessage()));
                }
                if (customerInfo.getDeviceType().equals(DeviceType.GAS_METER.getCode())) {
                    cell.setCellValue(new XSSFRichTextString(DeviceType.GAS_METER.getMessage()));
                }
                cell = row.createCell(7);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(customerInfo.getDeviceNo()));

                cell = row.createCell(8);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(customerInfo.getAlarmThreshold())));

                cell = row.createCell(9);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(customerInfo.getMeterUserNo()));

                cell = row.createCell(10);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(customerInfo.getTransformerNo()));

                cell = row.createCell(11);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(customerInfo.getBuildingNo()));

                cell = row.createCell(12);
                cell.setCellStyle(cellStyle);
                if (customerInfo.getIsEnabled()==1) {
                    cell.setCellValue(new XSSFRichTextString("正常"));
                }else{
                    cell.setCellValue(new XSSFRichTextString("销户"));
                }
                cell = row.createCell(13);
                cell.setCellStyle(cellStyle);
                String createTime = DateUtil.formatDate(customerInfo.getCreateTime());
                cell.setCellValue(new XSSFRichTextString(createTime));
                cell = row.createCell(14);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(customerInfo.getCreateUserName()));
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generateMeterListExcel(String name, List<SchemeMeterRes> list) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);


        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);

        XSSFCell cell1 = row1.createCell(0);

        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户名称"));

        cell1 = row1.createCell(1);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("表计户号"));

        cell1 = row1.createCell(2);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("表号"));

        cell1 = row1.createCell(3);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("安装地址"));

        cell1 = row1.createCell(4);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("楼栋编号"));

        cell1 = row1.createCell(5);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("变压器号"));


        XSSFRow row;
        XSSFCell cell;

        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                SchemeMeterRes customerInfo = list.get(i);
                row = sheet.createRow(i + 2);

                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(customerInfo.getCustomerName()));

                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(customerInfo.getMeterUserNo()));

                cell = row.createCell(2);
                cell.setCellStyle(cellStyle);
                if(!StringUtils.isEmpty(customerInfo)){
                    cell.setCellValue(new XSSFRichTextString(customerInfo.getDeviceNo()));
                }else{
                    cell.setCellValue("");
                }
                cell = row.createCell(3);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(customerInfo.getInstallAddr()));
                cell = row.createCell(4);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(customerInfo.getBuildNo()));

                cell = row.createCell(5);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(customerInfo.getTransformerNo()));

            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generateFeePayExcel(String name, List<QueryProcDTO> list) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);

        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);

        XSSFCell cell1 = row1.createCell(0);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("记录状态"));

        cell1 = row1.createCell(1);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充值状态"));

        cell1 = row1.createCell(2);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("购电方式"));

        cell1 = row1.createCell(3);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户姓名"));

        cell1 = row1.createCell(4);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("缴费金额"));

        cell1 = row1.createCell(5);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充值金额"));

        cell1 = row1.createCell(6);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("调整金额"));

        cell1 = row1.createCell(7);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("购电日期"));

        cell1 = row1.createCell(8);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("购电次数"));

        cell1 = row1.createCell(9);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("联系电话"));

        cell1 = row1.createCell(10);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户地址"));

        cell1 = row1.createCell(11);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("门牌号"));

        cell1 = row1.createCell(12);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("部门机构"));

        cell1 = row1.createCell(13);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("售电员"));

        cell1 = row1.createCell(14);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("交易流水号"));

        XSSFRow row;
        XSSFCell cell;
        QueryProcDTO queryProcDTO;

        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                queryProcDTO = list.get(i);
                row = sheet.createRow(i + 2);
                int index=0;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                int isValid = queryProcDTO.getIsValid();
                if(isValid == 1){
                    cell.setCellValue(new XSSFRichTextString("正常"));
                }else {
                    cell.setCellValue(new XSSFRichTextString("已撤销"));
                }
                index++;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                /**
                 * 充值状态需要根据payModel和writeMeter判断
                 */
                int payModel = queryProcDTO.getPayModel();          // 1- 远程  2- 售电卡
                int writeMeter = queryProcDTO.getWriteMeter();      // 0- 未写入  1-写入  2-取消

                if(payModel == 1 && writeMeter == 0){
                    cell.setCellValue(new XSSFRichTextString("正在远程充值"));
                }else if(payModel == 1 && writeMeter == 1){
                    cell.setCellValue(new XSSFRichTextString("充值成功"));//远程充值成功
                }else if(payModel == 1 && writeMeter == 2){
                    cell.setCellValue(new XSSFRichTextString("取消远程充值"));
                } else if (payModel == 1 && writeMeter == 3) {
                    cell.setCellValue(new XSSFRichTextString("远程充值失败"));
                } else if(payModel == 2 && writeMeter == 0){
                    cell.setCellValue(new XSSFRichTextString("制卡失败"));// 售电卡写卡失败
                }else if(payModel == 2 && writeMeter == 1){
                    cell.setCellValue(new XSSFRichTextString("充值成功"));// 售电卡充值成功
                }else{
                    cell.setCellValue(new XSSFRichTextString("未知的充值状态"));
                }
                index++;
                cell = row.createCell(   index);
                cell.setCellStyle(cellStyle);
                if(queryProcDTO.getPayModel() == 1){
                    cell.setCellValue(new XSSFRichTextString("远程充值"));
                }else {
                    cell.setCellValue(new XSSFRichTextString("售电卡"));
                }
                index++;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryProcDTO.getCustomerName()));
                index++;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryProcDTO.getPayment())));
                index++;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryProcDTO.getPayMoney())));
                index++;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryProcDTO.getAdjustAmount())));
                index++;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                cell.setCellValue(new XSSFRichTextString(format.format(queryProcDTO.getPayDate())));
                index++;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryProcDTO.getPayCount())));
                index++;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryProcDTO.getCustomerContact()));
                index++;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryProcDTO.getCustomerAddr()));
                index++;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryProcDTO.getPropertyName()));
                index++;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryProcDTO.getOrgName()));
                index++;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryProcDTO.getCreateUserName()));
                index++;
                cell = row.createCell(index);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryProcDTO.getSerialNum()));
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generateImportantExcel(String name, List<ImportantCurveDerailDTO> list) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);

        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);

        XSSFCell cell1 = row1.createCell(0);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("抄表时间"));
        cell1 = row1.createCell(1);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("正向有功总电量"));
        cell1 = row1.createCell(2);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("正向有功总示数"));

        cell1 = row1.createCell(3);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("A相电流"));

        cell1 = row1.createCell(4);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("B相电流"));

        cell1 = row1.createCell(5);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("C相电流"));

        cell1 = row1.createCell(6);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("A相电压"));

        cell1 = row1.createCell(7);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("B相电压"));

        cell1 = row1.createCell(8);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("C相电压"));


        XSSFRow row;
        XSSFCell cell;

        ImportantCurveDerailDTO arrearageCustomer ;

        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                arrearageCustomer = list.get(i);
                row = sheet.createRow(i + 2);

                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(arrearageCustomer.getCollectDate()));
                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(arrearageCustomer.getActiveTotal()));
                cell = row.createCell(2);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(arrearageCustomer.getActiveTotalValue()));

                cell = row.createCell(3);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(arrearageCustomer.getCurrentA()));

                cell = row.createCell(4);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(arrearageCustomer.getCurrentB()));

                cell = row.createCell(5);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(arrearageCustomer.getCurrentC()));

                cell = row.createCell(6);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(arrearageCustomer.getVoltageA()));
                cell = row.createCell(7);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(arrearageCustomer.getVoltageB()));
                cell = row.createCell(8);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(arrearageCustomer.getVoltageC()));

            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generateAlarmExcel(String name, List<ArrearageCustomer> list) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);

        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);

        XSSFCell cell1 = row1.createCell(0);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("商户名称"));
        cell1 = row1.createCell(1);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("商家品牌"));
        cell1 = row1.createCell(2);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户地址"));

        cell1 = row1.createCell(3);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("门牌编号"));

        cell1 = row1.createCell(4);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电话"));

        cell1 = row1.createCell(5);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("剩余金额"));

        cell1 = row1.createCell(6);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("剩余电量"));

        cell1 = row1.createCell(7);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("部门机构"));



        cell1 = row1.createCell(8);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("采集日期"));

        XSSFRow row;
        XSSFCell cell;

        ArrearageCustomer arrearageCustomer = new ArrearageCustomer();

        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                arrearageCustomer = list.get(i);
                row = sheet.createRow(i + 2);

                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(arrearageCustomer.getCustomerName()));
                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(arrearageCustomer.getCustomerBrand().toString()));
                cell = row.createCell(2);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(arrearageCustomer.getCustomerAddr()));

                cell = row.createCell(3);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(arrearageCustomer.getPropertyName()));


                cell = row.createCell(4);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(arrearageCustomer.getCustomerContact()));

                cell = row.createCell(5);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(arrearageCustomer.getReadValue().toString()));
                cell = row.createCell(6);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(arrearageCustomer.getRemainPower().toString()));
                cell = row.createCell(7);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(arrearageCustomer.getOrgName().toString()));


                cell = row.createCell(8);
                cell.setCellStyle(cellStyle);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                cell.setCellValue(new XSSFRichTextString(format.format(arrearageCustomer.getCollectDate())));
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generateReadCollectRecordExcel(String name, List<CollectRecordDTO> list) throws Exception {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);

        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        XSSFCell cell1 = row1.createCell(0);

        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("抄表时间"));

        cell1 = row1.createCell(1);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("召测户数"));

        cell1 = row1.createCell(2);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("成功数"));

        cell1 = row1.createCell(3);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("失败总数"));

        cell1 = row1.createCell(4);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("成功率(%)"));

        cell1 = row1.createCell(5);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("更新时间"));

        XSSFRow row;
        XSSFCell cell;
        CollectRecordDTO collectRecordDTO = null;

        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                collectRecordDTO = list.get(i);
                row = sheet.createRow(i + 2);

                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(collectRecordDTO.getCollectDate()));

                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectRecordDTO.getCustomerCount())));

                cell = row.createCell(2);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectRecordDTO.getSuccessCount())));

                cell = row.createCell(3);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectRecordDTO.getFailCount())));

                cell = row.createCell(4);
                cell.setCellStyle(cellStyle);
                BigDecimal successRate = collectRecordDTO.getSuccessRate();
                if(successRate == null){
                    cell.setCellValue("0.00");
                }else{
                    cell.setCellValue(new XSSFRichTextString(collectRecordDTO.getSuccessRate().toString()));
                }

                cell = row.createCell(5);
                cell.setCellStyle(cellStyle);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if(collectRecordDTO.getUpdateTime() == null){
                    cell.setCellValue("");
                }else{
                    cell.setCellValue(new XSSFRichTextString(format.format(collectRecordDTO.getUpdateTime())));
                }

            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generateReadCollectRecordDetialExcel(String name, List<CollectDetialDTO> list) throws Exception {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);

        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        XSSFCell cell1 = row1.createCell(0);

        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("采集时间"));

        cell1 = row1.createCell(1);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("表计户号"));

        cell1 = row1.createCell(2);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户表号"));

        cell1 = row1.createCell(3);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("采集器号"));

        cell1 = row1.createCell(4);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("剩余金额"));

        cell1 = row1.createCell(5);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("有功总"));

        cell1 = row1.createCell(6);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("姓名"));

        cell1 = row1.createCell(7);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户地址"));

        XSSFRow row;
        XSSFCell cell;
        CollectDetialDTO collectDetialDTO = null;

        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                collectDetialDTO = list.get(i);
                row = sheet.createRow(i + 2);

                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(collectDetialDTO.getCollectDate()));

                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getMeterUserNo())));

                cell = row.createCell(2);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getDeviceNo())));

                cell = row.createCell(3);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(collectDetialDTO.getCollectNo()));

                cell = row.createCell(4);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(collectDetialDTO.getBalance()==null?"":collectDetialDTO.getBalance().toString()));


                cell = row.createCell(5);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(collectDetialDTO.getPr0()==null?"":collectDetialDTO.getPr0().toString()));


                cell = row.createCell(6);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(collectDetialDTO.getCustomerName()));

                cell = row.createCell(7);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(collectDetialDTO.getCustomerAddr()));

            }
        }
        return workbook;
    }


    @Override
    public XSSFWorkbook generateDeviceEventExcel(String name, List<ExcAccountListDTO> list, ExcAccountDTO excAccountDTO)  {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);

        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        XSSFCell cell1 = row1.createCell(0);

        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("异常类型"));

        cell1 = row1.createCell(1);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("异常告警数"));

        cell1 = row1.createCell(2);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("已处理数"));

        cell1 = row1.createCell(3);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("未处理数"));

        XSSFRow row;
        XSSFCell cell;
        ExcAccountListDTO collectDetialDTO = null;
        int total = 0;
        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                collectDetialDTO = list.get(i);
                row = sheet.createRow(i + 2);
                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(collectDetialDTO.getEventCategory()));

                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getAccountNum())));

                cell = row.createCell(2);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getDealNum())));

                cell = row.createCell(3);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(collectDetialDTO.getUndealNum().toString()));
                total++;
            }
        }
        row1 = sheet.createRow(total+2);
        cell1 = row1.createCell(0);

        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("总计"));

        cell1 = row1.createCell(1);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString(String.valueOf(excAccountDTO.getStatistics().getAccountNum())));

        cell1 = row1.createCell(2);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString(String.valueOf(excAccountDTO.getStatistics().getDealNum())));

        cell1 = row1.createCell(3);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString(String.valueOf(excAccountDTO.getStatistics().getUndealNum())));

        return workbook;

    }
    @Override
    public XSSFWorkbook generateChargeSummaryExcel(String name, List<ChargeSummaryDTO> list) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        cellStyleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
        cellStyleTitle.setFillForegroundColor(HSSFColor.LIME.index);//前景填充色
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        /*XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);*/

        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        XSSFCell cell1 = row1.createCell(0);

        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("序号"));

        cell1 = row1.createCell(1);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("商户名称"));

        cell1 = row1.createCell(2);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("商户铺号"));

        cell1 = row1.createCell(3);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("商家品牌"));
        cell1 = row1.createCell(4);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电表编号"));

        cell1 = row1.createCell(5);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("商户电费单价"));
        cell1 = row1.createCell(6);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充值金额"));
        cell1 = row1.createCell(7);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("当前余额"));
        cell1 = row1.createCell(8);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("当期使用金额"));
        cell1 = row1.createCell(9);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("读数(起/度数)"));
        cell1 = row1.createCell(10);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("读数(止/度数)"));
        cell1 = row1.createCell(11);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("当期用电量（度）"));
        cell1 = row1.createCell(12);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("剩余电量（度）"));
        XSSFRow row;
        XSSFCell cell;
        ChargeSummaryDTO collectDetialDTO = null;
        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                collectDetialDTO = list.get(i);
                row = sheet.createRow(i + 2);
                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getSequence())));

                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getCustomerName())));

                cell = row.createCell(2);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getPropertyName())));

                cell = row.createCell(3);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(collectDetialDTO.getCustomerBrand() == null ? "" : collectDetialDTO.getCustomerBrand()));
                cell = row.createCell(4);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(collectDetialDTO.getDeviceNo()));
                cell = row.createCell(5);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getUnitPrice())));
                cell = row.createCell(6);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getPayment())));
                cell = row.createCell(7);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getRemainAmount())));
                cell = row.createCell(8);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getExpenseMoney())));
                cell = row.createCell(9);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getStartReadValue())));
                cell = row.createCell(10);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getEndReadValue())));
                cell = row.createCell(11);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getEqValue())));
                cell = row.createCell(12);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getRemainPower())));
            }
        }

        return workbook;


    }

    @Override
    public XSSFWorkbook generateChargeDetailExcel(String name, List<ChargeDetailDTO> list) throws ParseException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        cellStyleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
        cellStyleTitle.setFillForegroundColor(HSSFColor.LIME.index);//前景填充色
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        /*XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);*/

        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        XSSFCell cell1 = row1.createCell(0);

        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("序号"));

        cell1 = row1.createCell(1);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充值时间"));

        cell1 = row1.createCell(2);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("商户名"));

        cell1 = row1.createCell(3);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("商户铺号"));

        cell1 = row1.createCell(4);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("商家品牌"));
        cell1 = row1.createCell(5);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("商户电费单价"));
        cell1 = row1.createCell(6);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电表编号"));
        cell1 = row1.createCell(7);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("本次充值金额"));
        cell1 = row1.createCell(8);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充值前金额"));
        cell1 = row1.createCell(9);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充值后剩余金额"));
        cell1 = row1.createCell(10);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("本次充值电量（度）"));
        cell1 = row1.createCell(11);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充值前剩余电量（度）"));
        cell1 = row1.createCell(12);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充值后剩余电量（度）"));
        cell1 = row1.createCell(13);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("支付方式"));
        XSSFRow row;
        XSSFCell cell;
        ChargeDetailDTO collectDetialDTO = null;
        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                collectDetialDTO = list.get(i);
                row = sheet.createRow(i + 2);
                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getSequence())));

                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getChargeTime())));

                cell = row.createCell(2);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getCustomerName())));

                cell = row.createCell(3);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(collectDetialDTO.getPropertyName()));
                cell = row.createCell(4);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getCustomerBrand()==null?"":collectDetialDTO.getCustomerBrand())));
                cell = row.createCell(5);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getCustomerElecBill())));
                cell = row.createCell(6);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(collectDetialDTO.getDeviceNo()));
                cell = row.createCell(7);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getChargeAmount())));
                cell = row.createCell(8);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getPreChargeAmount())));
                cell = row.createCell(9);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getSurplusAmount())));
                cell = row.createCell(10);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getChargeElecAmount())));
                cell = row.createCell(11);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getPreChargeElecAmount())));
                cell = row.createCell(12);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(collectDetialDTO.getSurplusChargeElecAmount())));
                cell = row.createCell(13);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(collectDetialDTO.getPayType()));
            }
        }

        return workbook;


    }

    @Override
    public XSSFWorkbook generateKeyCollectionAnalysisExcel(String name, List<ImpCollectionAnalysisDTO> list) throws ParseException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);

        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        XSSFCell cell1 = row1.createCell(0);

        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("采集时间"));

        cell1 = row1.createCell(1);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("电表总数"));

        cell1 = row1.createCell(2);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("失败数"));

        cell1 = row1.createCell(3);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("上报成功率%"));

        cell1 = row1.createCell(4);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("召测成功率%"));
        cell1 = row1.createCell(5);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("总成功率%"));


        XSSFRow row;
        XSSFCell cell;
        ImpCollectionAnalysisDTO keyCollectionAnalysisDTO;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                keyCollectionAnalysisDTO = list.get(i);
                row = sheet.createRow(i + 2);
                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(keyCollectionAnalysisDTO.getCollectTime()!=null?simpleDateFormat.format(keyCollectionAnalysisDTO.getCollectTime()):""));

                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(keyCollectionAnalysisDTO.getSumCount())));

                cell = row.createCell(2);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(keyCollectionAnalysisDTO.getFailCount())));

                cell = row.createCell(3);
                cell.setCellStyle(cellStyle);
                String radioRate = keyCollectionAnalysisDTO.getRadioRate();
                if (radioRate != null) {
                    cell.setCellValue(new XSSFRichTextString(radioRate));
                } else {
                    cell.setCellValue(new XSSFRichTextString(""));
                }

                cell = row.createCell(4);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(keyCollectionAnalysisDTO.getCallRate())));
                cell = row.createCell(5);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(keyCollectionAnalysisDTO.getSuccessRate())));
            }
        }

        return workbook;

    }

    @Override
    public Integer generateRealTimeData(String csvPath, List<Impot> list)   {
        excel2csv(csvPath,list);
        // jdbc提交.csv文件到MySQL
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = dataSource.getConnection();
            String newSavePath = csvPath.replace('\\', '/');
            String sql = "LOAD DATA LOCAL INFILE '" + newSavePath + "' REPLACE INTO TABLE " + "em_d_query_impot"
                    + " FIELDS TERMINATED BY ','" + " ESCAPED BY '' LINES TERMINATED BY '\n' "
                    + "(search_no,data_type,is_search_child,impot_batch,"
                    + "create_time,child_flag)";
            pstmt = conn.prepareStatement(sql);
            if (pstmt.isWrapperFor(com.mysql.cj.api.jdbc.Statement.class)) {
                com.mysql.cj.jdbc.PreparedStatement mysqlStatement = pstmt.unwrap(com.mysql.cj.jdbc.PreparedStatement.class);
                mysqlStatement.executeUpdate();
            }
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("失败");
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                new File(csvPath).delete();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }
    /**
     * 写csv文件
     *
     * @param csvWriter
     * @param dataList
     * @throws Exception
     */
    private void writeCsvFile(CsvWriter csvWriter, List<Impot> dataList) throws IOException, IllegalAccessException {
        for (Impot info : dataList) {
            //通过反射获取属性字段的值并包装成字符数组写入csv文件
            Class<? extends Impot> cinfoCla = info.getClass();
            Field[] fs = cinfoCla.getDeclaredFields();
            String[] content = new String[fs.length - 1];
            for (int i = 2; i < fs.length; i++) {
                Field f = fs[i];
                f.setAccessible(true); // 设置些属性是可以访问的
                Object val = f.get(info);// 得到此属性的值
                if (val == null) {
                    content[i - 2] = "";
                    continue;
                }
                if (val instanceof Date) {
                    Date tempDate = (Date) val;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    content[i - 2] = format.format(tempDate);
                } else {
                    content[i - 2] = val.toString();
                }
            }
            csvWriter.writeRecord(content);
        }
        csvWriter.close();
    }

    @Override
    public XSSFWorkbook generateQueryListExcel(String name, List<RefundQueryListDto> list) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);

        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);

        XSSFCell cell1 = row1.createCell(0);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("记录状态"));


        cell1 = row1.createCell(1);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户姓名"));

        cell1 = row1.createCell(2);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("退费金额"));

        cell1 = row1.createCell(3);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("退费方式"));

        cell1 = row1.createCell(4);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("剩余金额"));

        cell1 = row1.createCell(5);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("操作人员"));


        cell1 = row1.createCell(6);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("操作时间"));

        cell1 = row1.createCell(7);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户地址"));

        cell1 = row1.createCell(8);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("联系电话"));

        cell1 = row1.createCell(9);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("流水号"));


        XSSFRow row;
        XSSFCell cell;

        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                RefundQueryListDto refundQueryListDto = list.get(i);
                row = sheet.createRow(i + 2);

                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);
                if(refundQueryListDto.getIsValid()==1) {
                    cell.setCellValue(new XSSFRichTextString("正常"));
                } else if(refundQueryListDto.getIsValid()==0) {
                    cell.setCellValue(new XSSFRichTextString("无效"));
                }

                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(refundQueryListDto.getCustomerName()));

                cell = row.createCell(2);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(Float.toString(refundQueryListDto.getRefundMoney())));

                cell = row.createCell(3);
                cell.setCellStyle(cellStyle);
                if(refundQueryListDto.getRefundMethod()==1){
                    cell.setCellValue(new XSSFRichTextString("现金"));
                }else if(refundQueryListDto.getRefundMethod()==2){
                    cell.setCellValue(new XSSFRichTextString("微信"));
                }else if(refundQueryListDto.getRefundMethod()==3){
                    cell.setCellValue(new XSSFRichTextString("银联"));
                }else if(refundQueryListDto.getRefundMethod()==4){
                    cell.setCellValue(new XSSFRichTextString("支付宝"));
                }


                cell = row.createCell(4);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(Float.toString(refundQueryListDto.getAfterRemainAmount())));

                cell = row.createCell(5);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(refundQueryListDto.getCreateUserName()));

                cell = row.createCell(6);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(refundQueryListDto.getRefundDate()));

                cell = row.createCell(7);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(refundQueryListDto.getCustomerAddr()));

                cell = row.createCell(8);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(refundQueryListDto.getCustomerContact()));

                cell = row.createCell(9);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(refundQueryListDto.getSerialNum()));

            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generateListByCstExcel(String name, List<QueryDetailDto> list) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);

        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);

        XSSFCell cell1 = row1.createCell(0);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("状态"));


        cell1 = row1.createCell(1);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("退费方式"));

        cell1 = row1.createCell(2);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("退费金额"));

        cell1 = row1.createCell(3);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("剩余金额"));

        cell1 = row1.createCell(4);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("退费原因"));

        cell1 = row1.createCell(5);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("操作人员"));

        cell1 = row1.createCell(6);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("操作时间"));

        cell1 = row1.createCell(7);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("流水号"));

        XSSFRow row;
        XSSFCell cell;

        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                QueryDetailDto queryDetailDto = list.get(i);
                row = sheet.createRow(i + 2);

                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);
                if(queryDetailDto.getIsValid()==1)
                    cell.setCellValue(new XSSFRichTextString("正常"));
                else if(queryDetailDto.getIsValid()==0)
                    cell.setCellValue(new XSSFRichTextString("无效"));

                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);
                if(queryDetailDto.getRefundMethod()==1){
                    cell.setCellValue(new XSSFRichTextString("现金"));
                }else if(queryDetailDto.getRefundMethod()==2){
                    cell.setCellValue(new XSSFRichTextString("微信"));
                }else if(queryDetailDto.getRefundMethod()==3){
                    cell.setCellValue(new XSSFRichTextString("银联"));
                }else if(queryDetailDto.getRefundMethod()==4){
                    cell.setCellValue(new XSSFRichTextString("支付宝"));
                }


                cell = row.createCell(2);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(Float.toString(queryDetailDto.getRefundMoney())));

                cell = row.createCell(3);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(Float.toString(queryDetailDto.getAfterRemainAmount())));

                cell = row.createCell(4);
                cell.setCellStyle(cellStyle);
                if(!StringUtils.isEmpty(queryDetailDto.getRemarks()))
                    cell.setCellValue(new XSSFRichTextString(queryDetailDto.getRemarks()));

                cell = row.createCell(5);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryDetailDto.getCreateUserName()));

                cell = row.createCell(6);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryDetailDto.getRefundDate()));

                cell = row.createCell(7);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryDetailDto.getSerialNum()));

            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generateQueryRefundRecordExcel(String name, List<QueryRefundRecordDto> list) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);

        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);

        XSSFCell cell1 = row1.createCell(0);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户姓名"));

        cell1 = row1.createCell(1);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("门牌编号"));

        cell1 = row1.createCell(2);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("表号"));

        cell1 = row1.createCell(3);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("表计户号"));

        cell1 = row1.createCell(4);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("退款金额"));

        cell1 = row1.createCell(5);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("退款日期"));

        cell1 = row1.createCell(6);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("联系方式"));

        cell1 = row1.createCell(7);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户地址"));

        XSSFRow row;
        XSSFCell cell;

        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                QueryRefundRecordDto queryRefundRecordDto = list.get(i);
                row = sheet.createRow(i + 2);

                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryRefundRecordDto.getCustomerName()));

                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryRefundRecordDto.getPropertyName()));

                cell = row.createCell(2);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryRefundRecordDto.getDeviceNo()));

                cell = row.createCell(3);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryRefundRecordDto.getMeterUserNo()));

                cell = row.createCell(4);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(Float.toString(queryRefundRecordDto.getRefundMoney())));

                cell = row.createCell(5);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryRefundRecordDto.getRefundDate()));

                cell = row.createCell(6);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryRefundRecordDto.getCustomerContact()));

                cell = row.createCell(7);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(queryRefundRecordDto.getCustomerAddr()));

            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook generateReadValue(String name,List<MeterCollectGroupDto> list) throws ParseException{
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);

        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        XSSFCell cell1 = row1.createCell(0);

        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户姓名"));

        cell1 = row1.createCell(1);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("门牌编号"));

        cell1 = row1.createCell(2);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("表计户号"));

        cell1 = row1.createCell(3);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("表号"));

        cell1 = row1.createCell(4);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("采集时间"));
        cell1 = row1.createCell(5);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("有功总"));
        cell1 = row1.createCell(6);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("剩余金额"));
        cell1 = row1.createCell(7);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("联系方式"));
        cell1 = row1.createCell(8);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户地址"));
        /*cell1 = row1.createCell(9);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("安装地址"));*/


        XSSFRow row;
        XSSFCell cell;
        MeterCollectGroupDto meterCollectGroupDto = null;
        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                meterCollectGroupDto = list.get(i);
                row = sheet.createRow(i + 2);
                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(meterCollectGroupDto.getCustomerName())));

                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(meterCollectGroupDto.getPropertyName())));

                cell = row.createCell(2);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(meterCollectGroupDto.getMeterUserNo())));

                cell = row.createCell(3);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(meterCollectGroupDto.getDeviceNo())));

                cell = row.createCell(4);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(meterCollectGroupDto.getCollectDate())));
                cell = row.createCell(5);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(meterCollectGroupDto.getActiveTotal())));
                cell = row.createCell(6);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(meterCollectGroupDto.getRemainAmount())));
                cell = row.createCell(7);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(meterCollectGroupDto.getCustomerContact())));
                cell = row.createCell(8);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(meterCollectGroupDto.getCustomerAddr())));
                /*cell = row.createCell(9);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(meterCollectGroupDto.getInstallAddr())));*/
            }
        }

        return workbook;

    }

    @Override
    public XSSFWorkbook generateRechargeRecord(String name, List<QueryRechargeRecordDto> list) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyleTitle.setWrapText(true);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 200);
        cellStyleTitle.setFont(font);

        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, name);

        XSSFRow row1 = sheet.createRow(1);
        XSSFCell cell1 = row1.createCell(0);

        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户姓名"));

        cell1 = row1.createCell(1);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("门牌编号"));

        cell1 = row1.createCell(2);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("表号"));

        cell1 = row1.createCell(3);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("表计户号"));

        cell1 = row1.createCell(4);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充值金额"));
        cell1 = row1.createCell(5);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("充值日期"));
        cell1 = row1.createCell(6);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("联系方式"));
        cell1 = row1.createCell(7);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("用户地址"));
        /*cell1 = row1.createCell(8);
        cell1.setCellStyle(cellStyleTitle);
        cell1.setCellValue(new XSSFRichTextString("安装地址"));*/


        XSSFRow row;
        XSSFCell cell;
        QueryRechargeRecordDto queryRechargeRecordDto = null;
        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                queryRechargeRecordDto = list.get(i);
                row = sheet.createRow(i + 2);
                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryRechargeRecordDto.getCustomerName())));

                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryRechargeRecordDto.getPropertyName())));

                cell = row.createCell(2);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryRechargeRecordDto.getDeviceNo())));

                cell = row.createCell(3);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryRechargeRecordDto.getMeterUserNo())));

                cell = row.createCell(4);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryRechargeRecordDto.getPayMoney())));
                cell = row.createCell(5);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryRechargeRecordDto.getPayDate())));
                cell = row.createCell(6);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryRechargeRecordDto.getCustomerContact())));
                cell = row.createCell(7);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryRechargeRecordDto.getCustomerAddr())));
                /*cell = row.createCell(8);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(String.valueOf(queryRechargeRecordDto.getInstallAddr())));*/
            }
        }

        return workbook;

    }

    private void excel2csv( String csvPath,List<Impot>dataList) {

        try {
            // 创建CSV写对象
            CsvWriter csvWriter = new CsvWriter(csvPath, ',', Charset.forName("UTF-8"));
            // 写csv文件
            writeCsvFile(csvWriter, dataList);

        } catch (IOException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
