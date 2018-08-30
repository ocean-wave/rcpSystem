package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dao.MeterRelationMapper;
import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.dto.param.CustomerInfoParam;
import cn.com.cdboost.collect.dto.param.FuzzyQueryUserVo;
import cn.com.cdboost.collect.dto.response.FuzzyQueryUserDto;
import cn.com.cdboost.collect.dto.response.NodeData;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.model.DeviceInfo;
import cn.com.cdboost.collect.model.MeterRelation;
import cn.com.cdboost.collect.model.User;
import cn.com.cdboost.collect.service.CustomerInfoService;
import cn.com.cdboost.collect.service.DeviceInfoService;
import cn.com.cdboost.collect.service.UserService;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @author wt
 * @desc
 * @create in  2018/5/15
 **/
@Controller
@RequestMapping("meterrelation")
public class MeterrelationController {
    private static final Logger logger = LoggerFactory.getLogger(MeterrelationController.class);
    @Autowired
    UserService userService;
    @Autowired
    DeviceInfoService deviceInfoService;
    @Autowired
    MeterRelationMapper meterRelationMapper;
    @Autowired
    CustomerInfoService customerInfoService;

    /**
     * 查询
     *
     * @param session
     * @return
     */
    @SystemControllerLog(description = "根据用户权限查询所有总表信息")
    @RequestMapping(value = "/queryMeterInfo", method = {RequestMethod.POST})
    @ResponseBody
    public String queryMeterInfo(HttpSession session, @RequestParam(required = false) String nodeId) {
        Result result = new Result();
        List<NodeData> nodeDataList = Lists.newArrayList();
        if (StringUtils.isEmpty(nodeId)) {
            LoginUser currentUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
            List list = Lists.newArrayList();
            list.add(currentUser.getId());
            List list1 = userService.batchQueryByIds(list);
            User user = (User) list1.get(0);
            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setOrgNo(user.getOrgNo());
            deviceInfo.setIsEnabled(1);
            List<DeviceInfo> select = deviceInfoService.select(deviceInfo);
            for (DeviceInfo info : select) {
                MeterRelation meterRelation = new MeterRelation();
                meterRelation.setMeterCno(info.getCno());
                meterRelation = meterRelationMapper.selectOne(meterRelation);
                NodeData nodeData = new NodeData();
                nodeData.setNodeName(info.getInstallAddr());
                nodeData.setNodeId(String.valueOf(meterRelation.getId()));
                nodeData.setNodeValue(info.getDeviceNo());
                MeterRelation meterrelationp = new MeterRelation();
                meterrelationp.setMeterCno(meterRelation.getpMeterCno());
                meterrelationp = meterRelationMapper.selectOne(meterRelation);
                nodeData.setpNodeId(meterrelationp == null ? String.valueOf(meterRelation.getId()) : String.valueOf(meterrelationp.getId()));
                nodeData.setChildren(new NodeData[]{});
                List childByNodeid = meterRelationMapper.findChildByNodeid(Integer.valueOf(meterRelation.getId()));
                if (childByNodeid.size() > 0) {
                    nodeData.setHasChild(true);
                }
                nodeDataList.add(nodeData);
            }
        } else {
            List childByNodeid = meterRelationMapper.findChildByNodeid(Integer.valueOf(nodeId));
            for (Object o : childByNodeid) {
                MeterRelation meterRelation = (MeterRelation) o;
                NodeData nodeData = new NodeData();
                meterRelation.setId(meterRelation.getId());
                DeviceInfo deviceInfo = deviceInfoService.queryDeviceInfoByCno(meterRelation.getMeterCno());
                nodeData.setNodeName(deviceInfo.getInstallAddr());
                nodeData.setNodeValue(deviceInfo.getDeviceNo());
                nodeData.setpNodeId(nodeId);
                nodeData.setNodeId(String.valueOf(meterRelation.getId()));
                nodeData.setChildren(new NodeData[]{});
                List childByNodeidc = meterRelationMapper.findChildByNodeid(Integer.valueOf(meterRelation.getId()));
                if (childByNodeidc.size() > 0) {
                    nodeData.setHasChild(true);
                }
                nodeDataList.add(nodeData);
            }
        }
        result.setData(nodeDataList);
        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "根据设备编号+设备类型模糊查询该节点信息，以及该节点的上级，上上级，直至最一级，按照树形结构返回")
    @RequestMapping(value = "/queryMeterByDeviceNo", method = {RequestMethod.POST})
    @ResponseBody
    public String queryMeterByDeviceNo(@RequestParam("deviceType") String DeviceType,
                                       @RequestParam("deviceNo") String DeviceNo
    ) {
        Result result = new Result();
        List<NodeData> nodeDataList = Lists.newArrayList();
        String cNo = CNoUtil.CreateCNo(DeviceType, DeviceNo);
        NodeData nodeData = new NodeData();
        MeterRelation meterRelation = new MeterRelation();
        meterRelation.setMeterCno(cNo);
        meterRelation = meterRelationMapper.selectOne(meterRelation);
        DeviceInfo deviceInfo = deviceInfoService.queryDeviceInfoByCno(meterRelation.getMeterCno());
        nodeData.setNodeName(deviceInfo.getInstallAddr());
        nodeData.setNodeValue(deviceInfo.getDeviceNo());
        MeterRelation meterrelationp = getNodeData(cNo);
        nodeData.setpNodeId(meterrelationp == null ? String.valueOf(meterRelation.getId()) : String.valueOf(meterrelationp.getId()));
        nodeData.setNodeId(String.valueOf(meterRelation.getId()));


        List childByNodeid = meterRelationMapper.findChildByNodeid(Integer.valueOf(meterRelation.getId()));
        NodeData[] nodeDatac = new NodeData[childByNodeid.size()];
        for (int i = 0; i < childByNodeid.size(); i++) {
            NodeData nodeData1=new NodeData();
            MeterRelation meterrelationc = (MeterRelation)childByNodeid.get(i);
            DeviceInfo deviceInfo1 = deviceInfoService.queryDeviceInfoByCno(meterrelationc.getMeterCno());
            nodeData1.setNodeName(deviceInfo1.getInstallAddr());
            nodeData1.setNodeValue(deviceInfo1.getDeviceNo());
            nodeData1.setNodeId(String.valueOf(meterrelationc.getId()));
            nodeData1.setChildren(new NodeData[]{});
            List childByNodeid1 = meterRelationMapper.findChildByNodeid(Integer.valueOf(meterrelationc.getId()));
            if (childByNodeid1.size() > 0) {
                nodeData1.setHasChild(true);
            }
            nodeData1.setpNodeId(String.valueOf(meterRelation.getId()));
            nodeDatac[i]=nodeData1;
        }

        if (childByNodeid.size() > 0) {
            nodeData.setHasChild(true);
        }
        nodeData.setChildren(nodeDatac);
        nodeData = generatePnode(cNo, nodeData);
        nodeDataList.add(nodeData);
        result.setData(nodeDataList);

        return JSON.toJSONString(result);
    }


    private NodeData generatePnode(String cNo, NodeData nodeData) {
        NodeData nodeDataF = new NodeData();
        MeterRelation meterRelation = new MeterRelation();
        meterRelation.setMeterCno(cNo);
        meterRelation = meterRelationMapper.selectOne(meterRelation);
        MeterRelation meterrelationp = getNodeData(cNo);
        if (meterrelationp != null) {
            List childByNodeid = meterRelationMapper.findChildByNodeid(Integer.valueOf(meterrelationp.getId()));
            if (childByNodeid.size() > 0) {
                nodeDataF.setHasChild(true);
            }
            NodeData[] nodeDatac = new NodeData[1];
            for (int i = 0; i < childByNodeid.size(); i++) {
                MeterRelation meterrelationc = (MeterRelation) childByNodeid.get(i);
                if (meterrelationc.getId().equals(meterRelation.getId())) {
                    nodeDatac[i] = nodeData;
                }
            }
            nodeDataF.setChildren(nodeDatac);
            nodeDataF.setNodeId(String.valueOf(meterrelationp.getId()));
            MeterRelation nodeData1 = getNodeData(meterrelationp.getMeterCno());
            nodeDataF.setpNodeId(nodeData1 == null ? String.valueOf(meterrelationp.getId()) : String.valueOf(nodeData1.getId()));
            DeviceInfo deviceInfo = deviceInfoService.queryDeviceInfoByCno(meterrelationp.getMeterCno());
            nodeDataF.setNodeValue(deviceInfo.getDeviceNo());
            nodeDataF.setNodeName(deviceInfo.getInstallAddr());
        }
        if (meterrelationp != null) {
            return generatePnode(meterrelationp.getMeterCno(), nodeDataF);
        }
        return nodeData;


    }

    private MeterRelation getNodeData(String cNo) {
        MeterRelation meterRelation = new MeterRelation();
        meterRelation.setMeterCno(cNo);
        meterRelation = meterRelationMapper.selectOne(meterRelation);
        MeterRelation meterrelationp = new MeterRelation();
        meterrelationp.setMeterCno(meterRelation.getpMeterCno());
        return meterRelationMapper.selectOne(meterrelationp);
    }

    @SystemControllerLog(description = "用户信息模糊查询接口")
    @RequestMapping(value = "/queryCustomerInfo", method = {RequestMethod.POST})
    @ResponseBody
    public String queryCustomerInfo(HttpSession session,@Valid @RequestBody CustomerInfoParam param) {
        Result<List<FuzzyQueryUserDto>> result = new Result();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        FuzzyQueryUserVo userVo = new FuzzyQueryUserVo();
        BeanUtils.copyProperties(param,userVo);
        userVo.setUserId(currentUser.getId());
        List<FuzzyQueryUserDto> userList = customerInfoService.fuzzyQueryUserInfo(userVo);
        if (userList.size() > 500) {
            result.error("请缩小搜索范围后再次查询");
            return JSON.toJSONString(result);
        }
        result.setData(userList);
        return JSON.toJSONString(result);
    }
}
