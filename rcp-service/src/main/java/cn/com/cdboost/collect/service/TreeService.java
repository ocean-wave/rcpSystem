package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.dto.BuildingTreeInfo;
import cn.com.cdboost.collect.dto.MainSubTreeInfo;
import cn.com.cdboost.collect.dto.OrgTreeNode;
import cn.com.cdboost.collect.dto.param.FuzzyQueryTreeVo;
import cn.com.cdboost.collect.dto.response.MainSubDto;
import cn.com.cdboost.collect.exception.BusinessException;

import java.util.List;

/**
 * 树形服务接口
 */
public interface TreeService {

    /**
     * 根据用户数据权限表，查询对应的楼栋树形结构
     * @param userId
     * @return
     */
    List<BuildingTreeInfo> queryBuildingTree(Integer userId);

    /**
     * 查询登录用户拥有的组织权限所能看到的总表树
     * @param userId
     * @param deviceType
     * @param isImportant
     * @param onlineStatus
     * @return
     */
    List<MainSubTreeInfo> queryMainSubTree(Integer userId,String deviceType,Integer isImportant,Integer onlineStatus);

    /**
     * 数据采集，普通用户召测
     * @param userId
     * @param deviceType
     * @return
     */
    List<MainSubTreeInfo> queryMainSubTree4Common(Integer userId,String deviceType);

    /**
     * 数据采集，重点用户召测
     * @param userId
     * @param deviceType
     * @return
     */
    List<MainSubTreeInfo> queryMainSubTree4Imp(Integer userId,String deviceType);

    /**
     * 档案管理，设备档案，水电气设备查询
     * @param userId
     * @param deviceType
     * @return
     */
    List<MainSubTreeInfo> queryDeviceMainSubTree(Integer userId,String deviceType,Integer onlineStatus);

    /**
     * 设备类型是（集中器，采集器，转换器）时，查询菜单树
     * @param userId
     * @param deviceType
     * @return
     */
    List<MainSubTreeInfo> queryMainSubTree4Ohter(Integer userId, String deviceType, String deviceNo,Integer onlineStatus);

    /**
     * 查询总表信息
     * @param userId
     * @param deviceType
     * @return
     */
    List<MainSubDto> queryMainSubDto4Common(Integer userId, String deviceType, Integer onlineStatus);

    /**
     * 根据nodeId查询该节点下一级的孩子节点
     * @param nodeId
     * @param isImportant
     * @return
     */
    List<MainSubTreeInfo> queryNextNode(String nodeId,Integer isImportant,Integer onlineStatus);

    /**
     * 总表树的模糊查询
     * @param queryTreeVo
     * @return
     * @throws BusinessException
     */
    List<MainSubTreeInfo> fuzzyQueryTree(FuzzyQueryTreeVo queryTreeVo) throws BusinessException;

    /**
     * 查询用户组织权限下，对应设备类型的设备总数
     * @param userId
     * @param deviceType
     * @return
     */
    Integer queryTotalDevice(Integer userId,String deviceType);

    /**
     * 根据orgNo查询该组织所在的树结构，每个节点返回是否叶子节点
     * @param orgNo
     * @return
     */
    List<OrgTreeNode> queryOrgTreeByOrgNo(Long orgNo);
}