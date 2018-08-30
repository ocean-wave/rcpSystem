package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.dto.OrgTreeInfo;
import cn.com.cdboost.collect.dto.OrgTreeNode;
import cn.com.cdboost.collect.enums.Action;
import cn.com.cdboost.collect.enums.Auth;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.model.Org;
import cn.com.cdboost.collect.service.OrgService;
import cn.com.cdboost.collect.service.TreeService;
import cn.com.cdboost.collect.service.UserLogService;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 组织相关
 * @author boost
 */
@Controller
@RequestMapping(value = "org")
public class OrgController {

    @Autowired
    private OrgService orgService;
    @Autowired
    private TreeService treeService;
    @Autowired
    private UserLogService userLogService;
    /**
     * 查询登录用户能看到的组织树信息
     * @param session
     * @return
     */
    @SystemControllerLog(description = "查询登录用户能看到的组织树信息")
    @RequestMapping(value = "queryOrgTreeInfo" , method = RequestMethod.POST)
    @ResponseBody
    public String queryOrgTreeInfo(HttpSession session,@RequestParam(value = "orgNo",required = false) String orgNo){
        Result<List<OrgTreeInfo>> result = new Result<>();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        List<OrgTreeInfo> treeInfos = orgService.queryByUserId(Long.valueOf(currentUser.getId()),orgNo);
        result.setData(treeInfos);
        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "客户档案新增时，查询用户组织树")
    @RequestMapping(value = "/queryOrgTreeByOrgNo")
    @ResponseBody
    public String queryOrgTreeByOrgNo(HttpSession session) {
        Result<List<OrgTreeNode>> result = new Result<>();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        List<OrgTreeNode> orgTreeNodes = treeService.queryOrgTreeByOrgNo(currentUser.getOrgNo());
        result.setData(orgTreeNodes);
        return JSON.toJSONString(result);
    }

    /**
     * 新增机构
     * @param session
     * @return
     */
    @Auth(menuID=100045L,actionID=1L)
    @SystemControllerLog(description = "新增机构组织树信息")
    @RequestMapping(value = "add" , method = RequestMethod.POST)
    @ResponseBody
    public String queryOrgTreeInfoAdd(HttpSession session,
                                      @RequestParam String orgName,
                                      @RequestParam String pOrgNo
    )
    {
        Result<List<OrgTreeInfo>> result = new Result<>("成功");
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        int treeInfos = orgService.addByorgNo(pOrgNo,orgName,currentUser.getId());
        if(treeInfos!=1){
            result.error("新增失败");
        }else{
            LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
            userLogService.create(user.getId(), Action.CHANGE_METER.getActionId(),"用户档案","customerNo", orgName, "新增机构组织["+orgName+"]",JSON.toJSONString(orgName));
        }

        return JSON.toJSONString(result);
    }
    /**
     * 删除机构
     * @param
     * @return
     */
    @Auth(menuID=100045L,actionID=3L)
    @SystemControllerLog(description = "删除机构组织树信息")
    @RequestMapping(value = "delete" , method = RequestMethod.POST)
    @ResponseBody
    public String queryOrgTreeInfoDelete(HttpSession session,
                                         @RequestParam Long orgNo
                                         ){
        Result<List<OrgTreeInfo>> result = new Result<>("成功");
        Org org = orgService.queryByOrgNo(orgNo);
        if (org == null) {
            result.error("该组织不存在");
            return JSON.toJSONString(result);
        }

        if(org.getIsSys()==1){
            result.error("系统初始信息无法删除");
            return JSON.toJSONString(result);
        }

        int treeInfos = orgService.deletebyorgNo(orgNo);
        if(treeInfos==0){
            result.error("删除失败");
        }else{
            LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
            String s = org != null ? org.getOrgName() : "";
            userLogService.create(user.getId(), Action.CHANGE_METER.getActionId(),"用户档案","customerNo", String.valueOf(orgNo), "删除机构组织["+s+"]",JSON.toJSONString(orgNo));
        }

        return JSON.toJSONString(result);
    }
    /**
     * 修改
     * @param session
     * @return
     */
    @Auth(menuID=100045L,actionID=2L)
    @SystemControllerLog(description = "修改机构组织树信息")
    @RequestMapping(value = "edit" , method = RequestMethod.POST)
    @ResponseBody
    public String queryOrgTreeInfoEdit(HttpSession session,
                                       @RequestParam String orgNo,
                                       @RequestParam String orgName,
                                       @RequestParam String pOrgNo

    ){
        Result result = new Result<>("成功");
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        int treeInfos = orgService.updateByorgNo(orgNo,orgName,currentUser.getId(), pOrgNo);
        if(treeInfos!=1){
            result.error("修改失败");
        }else{
            LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
            userLogService.create(user.getId(), Action.CHANGE_METER.getActionId(),"用户档案","customerNo", orgNo, "修改机构组织["+orgName+"]",JSON.toJSONString(orgNo));
        }
        return JSON.toJSONString(result);
    }
}
