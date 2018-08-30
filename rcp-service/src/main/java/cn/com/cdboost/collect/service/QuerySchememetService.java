package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.SchemeMeterRes;
import cn.com.cdboost.collect.dto.param.SchemeMeterQueryVo;
import cn.com.cdboost.collect.model.QuerySchememet;

import java.util.Collection;
import java.util.List;

/**
 * 查询方案详细接口
 */
public interface QuerySchememetService extends BaseService<QuerySchememet> {
    /**
     * 查询方案详细
     * @param schemeMeterQueryVo
     * @return
     */
    List<SchemeMeterRes> queryMeterList(SchemeMeterQueryVo schemeMeterQueryVo);

    /**
     * 根据用户唯一标识删除
     * @param customerNo
     * @return
     */
    int deleteByCustomerNo(String customerNo);

    /**
     * 根据用户唯一标识，查询
     * @param customerNo
     * @return
     */
    List<QuerySchememet> queryByCustomerNo(String customerNo);

    /**
     * 根据主键集合，批量删除
     * @param ids
     * @return
     */
    int batchDeleteByIds(Collection<Integer> ids);

    /**
     * 根据参数删除
     * @param customerNo
     * @param cno
     * @return
     */
    int deleteByParams(String customerNo,String cno);

    /**
     * 根据customerNo和cno，查询改设备所在的所有方案详情
     * @param customerNo
     * @param cno
     * @return
     */
    List<QuerySchememet> queryByParams(String customerNo,String cno);
}
