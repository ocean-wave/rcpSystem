package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.AnalAmountDTO;
import cn.com.cdboost.collect.dto.SumDataGetDTO;
import cn.com.cdboost.collect.dto.param.GetForMonthQueryVo;
import cn.com.cdboost.collect.model.SysConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysConfigMapper extends CommonMapper<SysConfig> {
    /**
     * 查询一年内每月的电、水、气的使用量
     * @param queryVo
     * @return
     */
    List<SumDataGetDTO> sumGetForMonth(GetForMonthQueryVo queryVo);
    /**
     * 首页面汇总用户的档案未下发数量、异常数量等，根据用户ID查询
     * @param map
     */
    void pageStaticCount(Map<String, Object> map);

    /**
     * 页面汇总剩余金额的分布
     * @param userId
     * @param deviceType
     * @return
     */
    AnalAmountDTO pageAnalAmount(@Param("userId") String userId, @Param("deviceType") String deviceType);
    /**
     * 汇总当月的电、水、气的使用量
     * @param map
     * @return
     */
    List<SumDataGetDTO> pageSumDataGet(Map<String, Object> map);
    /**
     * 汇总当月电量和上月电表的数据及环比
     * @param map
     */
    void pageSumMonthPer(Map<String, Object> map);

    /**
     * 批量更新
     * @param list
     */
    void batchUpdate(List<SysConfig> list);
}