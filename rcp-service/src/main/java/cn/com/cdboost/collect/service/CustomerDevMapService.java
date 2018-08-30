package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.cache.DeviceMapCacheVo;
import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.CustomerInfo4App;
import cn.com.cdboost.collect.dto.DeviceInfoResponse;
import cn.com.cdboost.collect.dto.param.DaySettlementParam;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.CustomerDevMap;
import com.github.pagehelper.PageInfo;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

/**
 * 用户与设备映射表接口服务
 */
public interface CustomerDevMapService extends BaseService<CustomerDevMap>{


    PageInfo queryDaySettlement(DaySettlementParam daySettlementParam, List<Long> orgNoList);
    PageInfo queryMonthSettlement(DaySettlementParam daySettlementParam, List<Long> orgNoList) throws ParseException;
    List queryByFactory(String meterFactory,String deviceType) ;
    /**
     * 通过cno，更新非null实体
     * @param devMap
     */
    void updateByCnoSelective(CustomerDevMap devMap) throws BusinessException;

    /**
     * 通过cno，查询客户设备关联记录实体
     * @param cno
     * @return
     */
    CustomerDevMap queryByCno(String cno);

    /**
     * 换表详细查询换表信息的记录
     * @param cno
     * @return
     */
    CustomerDevMap queryByCno4ChangeDetail(String cno);

    /**
     * 根据customerNos列表，批量删除
     * @param customerNos
     */
    void batchDeleteByCustomerNos(List<String> customerNos);

    /**
     * 根据用户唯一标识删除
     * @param customerNo
     * @return
     */
    int deleteByCustomerNo(String customerNo);

    /**
     * 根据customerNos列表，批量查询
     * @param customerNos
     * @return
     */
    List<CustomerDevMap> batchQueryByCustomerNos(List<String> customerNos);

    // 通过客户唯一标识查询所有记录（包含已换表的）
    List<CustomerDevMap> query4DeleteByCustomerNo(String customerNo);

    // 通过客户唯一标识查询有效的记录
    List<CustomerDevMap> queryByCustomerNo(String customerNo);

    // 根据cnos列表，批量查询
    List<CustomerDevMap> batchQueryByCnos(List<String> cnos);

    // 根据cno数组查询对应的客户信息
    List<CustomerInfo4App> selectCustomerInfosByCnos(List<String> cnoList);

    // 水电气表新增时，校验表计户号是否存在
    Boolean checkMeterUserNoExist4Add(String deviceType,String meterUserNo);

    // 水电气表修改时，校验表计户号是否存在
    Boolean checkMeterUserNoExist4Edit(String cno,String meterUserNo);

    List<DeviceInfoResponse> queryDeviceInfo(String customerNo, String deviceType);

    CustomerDevMap queryByMeterUserNo(String deviceType, String meterUserNo);

    CustomerDevMap queryByParams(String deviceType, String meterUserNo, Set<String> customerNos);

    List<CustomerDevMap> queryByParams(String deviceType, Set<String> customerNos);

    List<CustomerDevMap> queryByParams(String customerNo,String deviceType);

    CustomerDevMap queryByCustomerNoAndCno(String customerNo,String cno);

    List<CustomerDevMap> queryList(Set<String> deviceTypeSet,Set<String> meterUserNoSet);

    List<CustomerDevMap> queryListByDeviceType(String deviceType,Set<String> meterUserNoSet);

    List<DeviceMapCacheVo> queryAll();
}
