package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.constant.RedisKeyConstant;
import cn.com.cdboost.collect.dto.BuildingTreeInfo;
import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.dto.MainSubTreeInfo;
import cn.com.cdboost.collect.dto.ProjectTreeInfo;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.MainSubResult;
import cn.com.cdboost.collect.enums.DeviceType;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.service.TreeService;
import cn.com.cdboost.collect.util.RedisUtil;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * 树形菜单相关接口
 */
@Controller
@RequestMapping("/tree")
public class TreeController {

    @Autowired
    private TreeService treeService;
    @Autowired
    private RedisUtil redisUtil;
    /**
     * 实时数据菜单树模糊查询
     * @param session
     * @param param
     * @return
     */
    @SystemControllerLog(description = "实时数据菜单树模糊查询")
    @RequestMapping(value = "/projectfuzzyQueryTree")
    @ResponseBody
    public String projectfuzzyQueryTree(HttpSession session,@Valid @RequestBody ProjectTreeParam param) {
        Result<List<ProjectTreeInfo>> result = new Result<>();
        long begin = System.currentTimeMillis();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        param.setUserId(currentUser.getId());
        List<ProjectTreeInfo> dataList;

        dataList = treeService.projectfuzzyQueryTree(param);
        result.setData(dataList);
        long end = System.currentTimeMillis();
        System.out.println("树菜单模糊查询总耗时"+(end-begin)+ "毫秒");
        return JSON.toJSONString(result);
    }

    /**
     * 项目组织树
     * @param session
     * @return
     */
    @SystemControllerLog(description = "项目组织树查询")
    @RequestMapping(value = "/projectTree")
    @ResponseBody
    public String projectTree(HttpSession session) {
        Result<List<ProjectTreeInfo>> result = new Result<>();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        List<ProjectTreeInfo> treeInfos = treeService.queryProjectTree(currentUser.getId());
        result.setData(treeInfos);
        return JSON.toJSONString(result);
    }
    /**
     * 项目组织树子树
     * @return
     */
    @SystemControllerLog(description = "项目组织树查询")
    @RequestMapping(value = "/projectTreeMainSubTree")
    @ResponseBody
    public String projectTree(@RequestBody @Valid ProjectQueryParam projectQueryParam) {
        Result<List<ProjectTreeInfo>> result = new Result<>();
        List<ProjectTreeInfo> treeInfos = treeService.projectTreeMainSubTree(projectQueryParam);
        result.setData(treeInfos);
        return JSON.toJSONString(result);
    }
    /**
     * 用户楼栋组织树
     * @param session
     * @return
     */
    @SystemControllerLog(description = "用户楼栋组织树查询")
    @RequestMapping(value = "/buildingTree")
    @ResponseBody
    public String buildingTree(HttpSession session) {
        Result<List<BuildingTreeInfo>> result = new Result<>();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        List<BuildingTreeInfo> treeInfos = treeService.queryBuildingTree(currentUser.getId());
        result.setData(treeInfos);
        return JSON.toJSONString(result);
    }

    /**
     * 实时数据菜单树
     * nodeId为空，表示查询登录用户所能看到组织-总表树
     * nodeId非空，表示查询该节点下一级节点列表
     * @param session
     * @param queryParam
     * @return
     */
    @SystemControllerLog(description = "实时数据菜单树查询")
    @RequestMapping(value = "/queryMainSubTree")
    @ResponseBody
    public String queryMainSubTree(HttpSession session, @RequestBody MainSubTreeQueryParam queryParam) {
        MainSubResult<List<MainSubTreeInfo>> result = new MainSubResult<>();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        String nodeId = queryParam.getNodeId();
        Integer onlineStatus = queryParam.getOnlineStatus();
        Integer isImportant = queryParam.getIsImportant();
        if (StringUtils.isEmpty(nodeId)) {
            long begin = System.currentTimeMillis();
            String deviceType = queryParam.getDeviceType();
            // 查询最上层总表信息
            Integer userId = currentUser.getId();
            List<MainSubTreeInfo> dataList;
            if (DeviceType.ELECTRIC_METER.getCode().equals(deviceType)
                    || DeviceType.WATER_METER.getCode().equals(deviceType)
                    || DeviceType.GAS_METER.getCode().equals(deviceType)) {
                if (isImportant == 0) {
                    // 采集数据，普通用户召测
                    dataList = treeService.queryMainSubTree4Common(userId, deviceType);
                    if (onlineStatus != null) {
                        // 档案管理，设备档案
                        dataList = treeService.queryDeviceMainSubTree(userId,deviceType,onlineStatus);
                    }
                } else {
                    // 采集数据，重点用户召测
                    dataList = treeService.queryMainSubTree4Imp(userId, deviceType);
                }
            } else {
                // 集中器、转换器、采集器
                dataList = treeService.queryMainSubTree4Ohter(userId, deviceType,null,onlineStatus);
            }

            Integer total = treeService.queryTotalDevice(userId, deviceType);
            result.setData(dataList);
            result.setTotal(total);
            long end = System.currentTimeMillis();
            System.out.println("树菜单结构查询总耗时"+(end-begin)+ "毫秒");
        } else {
            long begin = System.currentTimeMillis();
            // 查询该节点下一级节点信息
            List<MainSubTreeInfo> treeInfoList = treeService.queryNextNode(nodeId,isImportant,onlineStatus);
            result.setData(treeInfoList);
            long end = System.currentTimeMillis();
            System.out.println("树菜单根据节点id查询总耗时"+(end-begin)+ "毫秒");
        }
        return JSON.toJSONString(result);
    }

    /**
     * 实时数据菜单树模糊查询
     * @param session
     * @param param
     * @return
     */
    @SystemControllerLog(description = "实时数据菜单树模糊查询")
    @RequestMapping(value = "/fuzzyQueryTree")
    @ResponseBody
    public String fuzzyQueryTree(HttpSession session,@Valid @RequestBody FuzzyQueryTreeParam param) {
        Result<List<MainSubTreeInfo>> result = new Result<>();
        long begin = System.currentTimeMillis();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        FuzzyQueryTreeVo treeVo = new FuzzyQueryTreeVo();
        BeanUtils.copyProperties(param,treeVo);
        treeVo.setUserId(currentUser.getId());

        List<MainSubTreeInfo> dataList;
        String deviceType = param.getDeviceType();
        if (DeviceType.ELECTRIC_METER.getCode().equals(deviceType)
                || DeviceType.WATER_METER.getCode().equals(deviceType)
                || DeviceType.GAS_METER.getCode().equals(deviceType)) {
            dataList = treeService.fuzzyQueryTree(treeVo);
        } else {
            // 集中器、转换器、采集器
            dataList = treeService.queryMainSubTree4Ohter(currentUser.getId(), deviceType,param.getDeviceNo(),param.getOnlineStatus());
        }

        result.setData(dataList);
        long end = System.currentTimeMillis();
        System.out.println("树菜单模糊查询总耗时"+(end-begin)+ "毫秒");
        return JSON.toJSONString(result);
    }


    @SystemControllerLog(description = "实时数据菜单树模糊查询")
    @RequestMapping(value = "/queryAllKey")
    @ResponseBody
    public void queryAllKey() {
        Set<String> keys = redisUtil.keys(RedisKeyConstant.DEVICE_PREFIX + "*");
        for (String key : keys) {
            System.out.println(key);
        }
    }
    
}
