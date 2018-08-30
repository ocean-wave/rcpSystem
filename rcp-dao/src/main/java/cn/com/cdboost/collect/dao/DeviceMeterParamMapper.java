package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.DeviceMeterParamInfo;
import cn.com.cdboost.collect.dto.ImportantCurveDerailDTO;
import cn.com.cdboost.collect.dto.ImportantCurveInfo;
import cn.com.cdboost.collect.dto.ImportantCustomerInfo;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.model.DeviceMeterParam;

import java.util.List;

public interface DeviceMeterParamMapper extends CommonMapper<DeviceMeterParam> {

    //根据客户的编号查看客户的电表信息
    List<DeviceMeterParamInfo> queryDetail(String customerNo);
    // 1.写入电表参数
    void creatDeviceMeterParam(DeviceMeterParamVo deviceMeterParam);

    List<ImportantCustomerInfo> queryImportantCustomer(ImportantCustomerVo importantVo);

    List<ImportantCurveInfo> queryImportantCurve(ImportantCurveVo importantCurveVo);
    List<ImportantCurveDerailDTO> queryImportCollection(ImportantCurveVo importantCurveVo);
    List<ImportantCurveInfo> queryImportantABC(ImportantABCVo importantABCVo);
    List<ImportantCurveDerailDTO>  getImpDataList(RealTimeDataListParam realTimeDataListParam);
}