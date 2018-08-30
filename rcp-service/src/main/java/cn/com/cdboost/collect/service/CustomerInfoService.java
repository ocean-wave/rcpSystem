package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.cache.CustomerCacheVo;
import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.*;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.CustomerInfo;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

/**
 * 用户档案接口服务
 */
public interface CustomerInfoService extends BaseService<CustomerInfo>{
	// 添加客户档案和设备信息
	ElectRecordInfo queryelectConsumption(ElectConsumptionParam param);
	// 添加客户档案和设备信息
	String addCustomerInfoAndDeviceInfo(CustomerInfoCreateParam param,Integer currentUserId);
	// 列表查询
	List<CustomerInfoDto> queryList(CustomerInfoQueryVo customerInfoQueryVo);
	// 列表查询
	List<CustomerInfoDto> queryListNew(CustomerInfoQueryNewVo customerInfoQueryVo);
	// 新客户档案列表查询
	List<CustomerInfoDtodownload> querydownlist(CustomerInfoQueryNewVo customerInfoQueryVo);
	// 根据customerNo，删除
	void deleteByCustomerNo(String customer) throws BusinessException;
    List queryByCoustomerInfo(CustomerInfo customerInfo);

    // 树形菜单上的用户模糊查询
    List<FuzzyQueryUserDto> fuzzyQueryUserInfo(FuzzyQueryUserVo param);
	/**
	 * 更新用户档案信息
	 * @param
	 * @throws Exception
	 */
	void updateCustomerInfo(CustomerInfoEditVo editVo) throws BusinessException;

	void batchDeleteCustomerInfo(Set<String> customerNoSet) throws BusinessException;

	// 修改客户电表信息
	void editCustomerElectricMeter(ElectricMeterAddParam param, Long currentUserId, Long orgNo,String customerNo);

	// 修改客户水表信息
	void editCustomerWaterMeter(WaterMeterAddParam param, Long currentUserId, Long orgNo,String customerNo);

	// 修改客户气表信息
	void editCustomerGasMeter(GasMeterAddParam param, Long currentUserId, Long orgNo,String customerNo) throws BusinessException;

	// 删除客户档案上的水电气表
	void deleteCustomerMeter(String customerNo, String deviceNo,String deviceType,String operator) throws BusinessException;

	// 客户档案明细页面，添加电表信息
	void addSingleElectricMeter(ElectricMeterAddParam param, String customerNo, Long currentUserId) throws BusinessException;

	// 客户档案明细页面，添加水表信息
	void addSingleWaterMeter(WaterMeterAddParam param, String customerNo, Long currentUserId) throws BusinessException;

	// 客户档案明细页面，添加气表信息
	void addSingleGasMeter(GasMeterAddParam param, String customerNo, Long currentUserId) throws BusinessException;


	/**
	 *@Description:根据客户编号查询客户客户详情包括设备编号
	 *@param customerNo
	 *@return CustomerRecord
	 *@throws Exception
	 */
	CustomerDeviceInfo getCustomerDetails(String customerNo, String deviceCno) throws BusinessException;
	/**
	 *@Description:excel批量导入客户档案
	 *@param
	 *@return Integer
	 *@throws Exception
	 */
	CustomerBatchImportInfo excelImport(HttpSession session, String excelName, String uuid);

	/**
	 * 根据用户customerNo查询详细信息
	 * @param customerNo
	 * @return
	 * @throws Exception
	 */
	CustomerInfo4ChangeMeterDetail queryDetail(String customerNo,String cno);

	/**
	 * 新增客户档案信息时,检查用户门牌编号是否存在
	 * @param propertyName 门牌编号
	 * @throws BusinessException
	 */
	void checkByPropertyName4Add(String propertyName) throws BusinessException;

	/**
	 * 修改客户档案信息时,检查用户门牌编号是否存在
	 * @param id 客户档案主键
	 * @param propertyName 门牌编号
	 * @throws BusinessException
	 */
	void checkByPropertyName4Edit(Long id, String propertyName) throws BusinessException;

	/**
	 * 通过guid查询下发的数据情况
	 * @param guid
	 * @return
	 */
	String countByGuidAndSendFlag(String guid) throws BusinessException;

	/**
	 * @Description:销户
	 * @param customerNo
	 * @throws Exception
	 */
	void closeAccount(String customerNo);

	// 查询用户的最后一次购电记录
	List<QueryUserDTO> queryUser(RechargeUserQueryVo queryVo);

	// 查询用户的IC卡基础信息
	QueryUserPayDTO queryUserPay(String customerNo, String deviceNo);

	// 用户信息模糊查询
	List<UserFuzzyQueryInfo> fuzzyQuery(UserFuzzyQueryVo queryVo);

	// 根据customerNo 查询用户信息
	CustomerInfo queryByCustomerNo(String customerNo);

	// 根据用户唯一标识，查询用户档案明细页面信息
	CustomerDetailInfo queryDetails(String customerNo);

	List<CustomerInfo> batchQueryByCustomerNos(Set<String> customerNos);

	// 微信解绑
	void weChatUnBind(String cno);

	// 根据数据权限查询统计楼栋信息
	List<BuildInfoDto> queryBuildInfo(Set<Long> dataOrgSet);

	// 查询总表信息
	List<MainSubDto> queryMainSubTree(Long orgNo, String deviceType);

	// 根据设备编号，模糊查询，返回设备相关的树的节点信息
	List<MainSubDto> fuzzyQueryTree(Long orgNo, String deviceType, String deviceNo);

	// 查询所有的客户档案信息
	List<CustomerCacheVo> queryAll();
}
