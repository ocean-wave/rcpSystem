package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.response.MainSubDto;
import cn.com.cdboost.collect.model.MeterRelation;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 电表之间的关系服务接口
 */
public interface MeterRelationService extends BaseService<MeterRelation>{
    // 根据父节点cno集合，查询对应父节点下一级的孩子节点
    List<MeterRelation> queryChildNode(Collection<String> parentCnoList);

    // 根据父节点cno，查询所有孩子节点cno列表
    List<String> queryChildCnoList(String parentCno);

    // 判断总表下是否存储在满足在线状态条件的表（该表不一定是总表的直接子表，也可能是间接子表）
    Map<String,Boolean> isExistChildMap(Collection<String> parentCnoList, Integer onlineStatus);

    // 根据父节点cno，查询对应父节点下一级的孩子节点
    List<MeterRelation> queryChildNodeByParentCno(String parentCno);

    // 根据父节点cno，查询对应父节点下一级的孩子节点(满足查询条件的)
    List<MeterRelation> queryChildNode(String parentCno, Integer onlineStatus);

    // 根据父节点cno，查询对应父节点下一级的孩子节点(只查询重点用户孩子节点)
    List<MeterRelation> queryNextNodeImpDevice(String parentCno);

    // 根据cno,查询该设备的所有孩子节点
    List<MeterRelation> queryChildTreeByCno(String cno);

    // 根据cno集合，批量查询
    List<MeterRelation> batchQueryByMeterCnos(Collection<String> cnos);

    // 采集数据，普通用户召测，返回总表信息，查总表时最多返回500条
    List<MainSubDto> queryMainSubTree4CommonUser(String deviceType, List<Long> orgNoList);

    // 档案管理，设备档案，查询设备总表
    List<MainSubDto> queryDeviceMainSubTree(String deviceType, Integer onlineStatus, List<Long> orgNoList);


    // 查询所有总表信息
    List<MainSubDto> queryMainDeviceList(String deviceType,Integer onlineStatus, List<Long> orgNoList);

    List<MainSubDto> queryMeterRelation4Imp(String deviceType,List<Long> orgNoList);

    // 根据设备编号，模糊查询，返回设备相关的树的节点信息
    List<MainSubDto> fuzzyQueryTree(String deviceType, String deviceNo, Integer onlineStatus, List<Long> orgNoList);

    // 重点用户模糊查询
    List<MainSubDto> fuzzyQueryTree4Imp(String deviceType, String deviceNo, List<Long> orgNoList);

    // 根据cno查询
    MeterRelation queryByCno(String cno);

    // 根据主键批量更新
    int batchUpdateByPrimaryKey(List<MeterRelation> list);

    // 根据cno删除
    int deleteByCno(String cno);

    // 查询所有记录
    List<MeterRelation> queryAll();
}
