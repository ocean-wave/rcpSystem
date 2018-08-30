package cn.com.cdboost.collect.service;

/**
 * 查询方案服务接口
 */
import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.DeviceCountDto;
import cn.com.cdboost.collect.dto.DeviceUserListDto;
import cn.com.cdboost.collect.dto.QueryByCnosDto;
import cn.com.cdboost.collect.dto.QuerySchemeListInfo;
import cn.com.cdboost.collect.dto.param.AddSchemeParam;
import cn.com.cdboost.collect.dto.param.DeviceUserListVo;
import cn.com.cdboost.collect.dto.param.EditSchemeParam;
import cn.com.cdboost.collect.dto.param.QuerySchemeVo;
import cn.com.cdboost.collect.model.QueryScheme;

import java.util.Collection;
import java.util.List;

public interface QuerySchemeService extends BaseService<QueryScheme> {
    /**
     * 通过方案标识删除方案
     * @param schemeFlag
     * @return
     */
    int deleteBySchemeFlag(String schemeFlag);

    /**
     * 根据id批量删除
     * @param ids
     * @return
     */
    int deleteByIds(Collection<Integer> ids);

    List<QuerySchemeListInfo> queryList(QuerySchemeVo querySchemeVo);


    /**
     * 树菜单选择电表后，右边列表查询
     * @param cnos
     * @return
     */
    List<QueryByCnosDto> queryByCnos(String[] cnos );

    /**
     * 方案保存
     * @param addSchemeParam
     * @return
     */
    int addScheme(AddSchemeParam addSchemeParam,Integer createId);

    /**
     * 方案修改
     * @param editSchemeParam
     * @param createId
     * @return
     */
    int editScheme(EditSchemeParam editSchemeParam, Integer createId);

    /**
     * 设备方案统计
     * @param cno
     * @return
     */
    List<DeviceCountDto> deviceCount(String cno);

    /**
     * 设备关联用户列表查询
     * @param deviceUserListVo
     * @return
     */
    List<DeviceUserListDto> deviceUserList(DeviceUserListVo deviceUserListVo);

    /**
     * 查询总数
     * @param deviceUserListVo
     * @return
     */
    Long queryDeviceUserListTotal(DeviceUserListVo deviceUserListVo);

    /**
     * 根据方案标识，批量查询
     * @param schemeFlags
     * @return
     */
    List<QueryScheme> batchQueryBySchemeFlags(Collection<String> schemeFlags);

    /**
     * 按id更新方案电表数量
     * @param list
     * @return
     */
    int updateUserCountByIds(List<QueryScheme> list);
}
