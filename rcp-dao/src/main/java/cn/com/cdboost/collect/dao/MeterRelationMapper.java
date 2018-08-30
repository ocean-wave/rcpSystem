package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.response.MainSubDto;
import cn.com.cdboost.collect.model.MeterRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MeterRelationMapper extends CommonMapper<MeterRelation> {
     List findChildByNodeid(@Param("nodeId") Integer nodeId);

     // 采集数据，普通用户召测，返回总表信息，查总表时最多返回500条
     List<MainSubDto> queryMainSubTree4CommonUser(@Param("deviceType") String deviceType,
                                                  @Param("orgNoList") List<Long> orgNoList);

     List<MainSubDto> queryMainSubTree(@Param("deviceType") String deviceType,
                                       @Param("onlineStatus") Integer onlineStatus,
                                       @Param("orgNoList") List<Long> orgNoList);

     // 档案管理，设备档案，查询设备总表
     List<MainSubDto> queryDeviceMainSubTree(@Param("deviceType") String deviceType,
                                       @Param("onlineStatus") Integer onlineStatus,
                                       @Param("orgNoList") List<Long> orgNoList);


     // 查询重点用户表
     List<MainSubDto> queryMeterRelation4Imp(@Param("deviceType") String deviceType,
                                             @Param("orgNoList") List<Long> orgNoList);

     List<MainSubDto> fuzzyQueryTree(@Param("deviceType") String deviceType,
                                     @Param("deviceNo") String deviceNo,
                                     @Param("onlineStatus") Integer onlineStatus,
                                     @Param("orgNoList") List<Long> orgNoList);


     // 重点用户模糊查询
     List<MainSubDto> fuzzyQueryTree4Imp(@Param("deviceType") String deviceType,
                                         @Param("deviceNo") String deviceNo,
                                         @Param("orgNoList") List<Long> orgNoList);

     /**
      * 根据父节点id，查询对应节点下的重点用户表
      * @param pMeterCno
      * @return
      */
     List<MeterRelation> queryNextNodeImpDevice(String pMeterCno);

     /**
      * 查询pMeterCno节点下的孩子节点，满足在线条件onlineStatus
      * @param pMeterCno
      * @param onlineStatus
      * @return
      */
     List<MeterRelation> queryChildByParam(@Param("pMeterCno") String pMeterCno,
                                        @Param("onlineStatus") Integer onlineStatus);

     /**
      * 根据主键批量更新
      * @param list
      * @return
      */
     int batchUpdateByPrimaryKey(List<MeterRelation> list);
}