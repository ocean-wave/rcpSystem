package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.*;

import cn.com.cdboost.collect.dto.param.AddSchemeParam;
import cn.com.cdboost.collect.dto.param.EditSchemeParam;
import cn.com.cdboost.collect.dto.param.QuerySchemeParam;
import cn.com.cdboost.collect.dto.param.QuerySchemeVo;

import cn.com.cdboost.collect.dto.param.*;

import cn.com.cdboost.collect.enums.Action;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.StringUtil;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.*;

/**
 * 方案
 */
@Controller
@RequestMapping(value = "/scheme")
public class SchemeController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerInfoController.class);
    @Autowired
    private QuerySchemeService querySchemeService;
    @Autowired
    private QuerySchememetService querySchememetService;
    @Autowired
    private GenerateFileService generateFileService;
    @Autowired
    private UserLogService userLogService;
    @Autowired
    private RedisService redisService;


    /**
     * 根据方案标识删除方案
     * @param session
     * @param schemeFlag
     * @return
     */
    @SystemControllerLog(description = "根据方案标识删除方案")
    @RequestMapping(value="/delete")
    @ResponseBody
    public String delete(HttpSession session, @RequestParam String schemeFlag){
        Result result = new Result();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

        //判断参数是否合法
        if(!StringUtil.isEmpty(schemeFlag)){
            int deleteFlag = querySchemeService.deleteBySchemeFlag(schemeFlag);
            if(deleteFlag ==1){
                result.setCode(1);
                result.setMessage("删除成功");
                //记录日志到日志表
                userLogService.create(currentUser.getId(), Action.DELETE.getActionId(),"方案","schemeFlag", "","删除档案"+ schemeFlag, JSON.toJSONString(schemeFlag));
                return JSON.toJSONString(result);
            }else {
                result.setCode(0);
                result.error("删除失败");
                return JSON.toJSONString(result);
            }
        }else {
            result.setCode(0);
            result.error("参数为空");
            return JSON.toJSONString(result);
        }
    }

    /**
     * 方案列表分页查询
     * @param session
     * @param param
     * @return
     */
    @SystemControllerLog(description = "方案列表分页查询")
    @RequestMapping(value="/queryList", method = RequestMethod.POST)
    @ResponseBody
    public String queryList(HttpSession session, @RequestBody QuerySchemeParam param){
        PageResult<List<QuerySchemeListInfo>> result = new PageResult<>();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        QuerySchemeVo querySchemeVo = new QuerySchemeVo();
        BeanUtils.copyProperties(param,querySchemeVo);

        //查询数据权限
        Set<Long> dataOrgNos = redisService.queryUserOrgNoByUserId(currentUser.getId());
        List<Long> orgNos =  Lists.newArrayList(dataOrgNos);
        querySchemeVo.setOrgNos(orgNos);
        //查询数据库
        List<QuerySchemeListInfo> querySchemeListInfos = querySchemeService.queryList(querySchemeVo);

        result.setData(querySchemeListInfos);
        result.setTotal(querySchemeVo.getTotal());
        result.setCode(1);
        return JSON.toJSONString(result);
    }
    /**
     * 树菜单选择电表后，右边列表查询
     * @param cnos
     * @return
     */
    @SystemControllerLog(description = "树菜单选择电表后，右边列表查询")
    @RequestMapping(value="/queryByCnos", method = RequestMethod.POST)
    @ResponseBody
    public String queryByCnos(@RequestParam String[] cnos){
        //实例化结果集对象
        PageResult<List<QueryByCnosDto>> result = new PageResult<>();
        //判断传入的参数是否合法
        if(cnos==null||cnos.length==0){
            result.error("传入的查询表号为空！");
            return JSON.toJSONString(result);
        }
        //查询数据
        List<QueryByCnosDto> list = querySchemeService.queryByCnos(cnos);
        //判断是否有数据
        if(list==null||list.size()==0){
            result.error("未查询到数据！");
            return JSON.toJSONString(result);
        }
        //设置结果集
        result.setData(list);
        //返回数据
        return JSON.toJSONString(result);
    }

    /**
     * 方案保存
     * @param session
     * @param addSchemeParam
     * @return
     */
    @SystemControllerLog(description = "方案保存")
    @RequestMapping(value="/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(HttpSession session,@RequestBody AddSchemeParam addSchemeParam){
        //实例化结果对象
        Result result = new Result();
        //是否成功标志，默认不成功
        int isSuccess = 0;
        //获取登录用户信息
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        //判断是否获取登录用户
        if(currentUser!=null){
            //保存方案信息
           isSuccess = querySchemeService.addScheme(addSchemeParam,currentUser.getId());
        }
        //判断成功标识，返回对应的提示
        if(isSuccess!=0){
            result.setCode(1);
            result.setMessage("保存成功");
            return JSON.toJSONString(result);
        }else{
            result.error("保存失败");
            return JSON.toJSONString(result);
        }
    }

    /**
     * 方案修改
     * @param session
     * @return
     */
    @SystemControllerLog(description = "方案修改")
    @RequestMapping(value="/edit", method = RequestMethod.POST)
    @ResponseBody
    public String edit(HttpSession session,@RequestBody EditSchemeParam editSchemeParam){
        //实例化结果对象
        Result result = new Result();
        //是否成功标志，默认不成功
        int isSuccess = 0;
        //获取登录用户信息
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        //判断是否获取登录用户
        if(currentUser!=null){
            //修改方案信息
            isSuccess = querySchemeService.editScheme(editSchemeParam,currentUser.getId());
        }
        //判断成功标识，返回对应的提示
        if(isSuccess!=0){
            result.setCode(1);
            result.setMessage("修改成功");
            return JSON.toJSONString(result);
        }else{
            result.error("修改失败");
            return JSON.toJSONString(result);
        }
    }

    /**
     * 方案详情关联的设备列表查询
     * @param param
     * @return
     */
    @SystemControllerLog(description = "方案详情关联的设备列表查询")
    @RequestMapping(value="/queryMeterList", method = RequestMethod.POST)
    @ResponseBody
    public String queryMeterList(@RequestBody SchemeMeterQueryParam param){
        PageResult<List<SchemeMeterRes>> result = new PageResult<>();
        SchemeMeterQueryVo schemeMeterQueryVo = new SchemeMeterQueryVo();
        BeanUtils.copyProperties(param,schemeMeterQueryVo);
        //分页查询方案详情
        List<SchemeMeterRes> schemeMeterResList = querySchememetService.queryMeterList(schemeMeterQueryVo);

        result.setData(schemeMeterResList);
        result.setTotal(schemeMeterQueryVo.getTotal());
        return JSON.toJSONString(result);
    }

    /**
     * 方案详情关联的设备列表导出
     * @param param
     * @return
     */
    @SystemControllerLog(description = "方案详情关联的设备列表导出")
    @RequestMapping(value="/downloadMeterList",method=RequestMethod.POST)
    @ResponseBody
    public void downloadMeterList(HttpServletResponse response, HttpSession session, SchemeMeterQueryParam param){
        SchemeMeterQueryVo schemeMeterQueryVo = new SchemeMeterQueryVo();
        BeanUtils.copyProperties(param,schemeMeterQueryVo);
        //分页查询方案详情
        List<SchemeMeterRes> schemeMeterRes = querySchememetService.queryMeterList(schemeMeterQueryVo);

        try {
            XSSFWorkbook workBook = generateFileService.generateMeterListExcel("设备列表",schemeMeterRes);
            //通过Response把数据以Excel格式保存
            response.reset();
            //设置response流信息的头类型，MIME码
            response.setContentType("application/vnd.ms-excel");
            ServletOutputStream out = null;
            response.addHeader("Content-Disposition", "attachment;filename=\""
                    + new String(("导出表" + System.currentTimeMillis() + ".xls").getBytes("utf-8"),
                    "ISO8859_1") + "\"");
            //创建输出流对象
            out=response.getOutputStream();
            //将创建的Excel对象利用二进制流的形式强制输出到客户端去
            workBook.write(out);
            //强制将数据从内存中保存
            out.flush();
            out.close();
        } catch (Exception e) {
            String message = "方案设备列表";
            logger.error(message,e);
        }

    }

    /**
     * 设备方案统计
     * @param cno
     * @return
     */
    @SystemControllerLog(description = "设备方案统计")
    @RequestMapping(value="/deviceCount")
    @ResponseBody
    public String deviceCount(@RequestParam String cno){
        //实例化结果对象
        Result result = new Result();
        //判断参数是否合法
        if(StringUtils.isEmpty(cno)){
            result.error("没有传入可查询的设备cno");
            return JSON.toJSONString(result);
        }
        List<DeviceCountDto> list = querySchemeService.deviceCount(cno);
        //判断是否查询到数据
        if(list==null||list.size()==0){
            result.error("未查询到数据");
            return JSON.toJSONString(result);
        }
        //设置结果集
        Map map = new HashMap();
        map.put("list",list);
        result.setData(map);
        //返回结果集
        return JSON.toJSONString(result);
    }

    /**
     * 设备关联用户列表查询
     * @return
     */
    @SystemControllerLog(description = "设备关联用户列表查询")
    @RequestMapping(value="/deviceUserList")
    @ResponseBody
    public String deviceUserList(HttpSession session,@RequestBody DeviceUserListVo deviceUserListVo){
        //实例化结果集
        PageResult result = new PageResult<>();
        /*
        //实例化查询对象
        DeviceUserListVo deviceUserListVo = new DeviceUserListVo();
        //设置查询参数
        deviceUserListVo.setPageNumber(pageNumber);
        deviceUserListVo.setPageSize(pageSize);
        deviceUserListVo.setDeviceNo(deviceNo);
        */
        //获取当前登录用户信息
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        //查询数据权限
        Set<Long> dataOrgNos = redisService.queryUserOrgNoByUserId(currentUser.getId());
        List<Long> orgNos =  Lists.newArrayList(dataOrgNos);
        deviceUserListVo.setOrgNos(orgNos);
        //查询数据
        List<DeviceUserListDto> list = querySchemeService.deviceUserList(deviceUserListVo);
        //判断是否查询到数据
        if(list==null||list.size()==0){
            result.error("未查询到数据");
            return JSON.toJSONString(result);
        }
        //查询总数
        Long total = querySchemeService.queryDeviceUserListTotal(deviceUserListVo);
        //设置数据

        result.setData(list);
        result.setTotal(total);
        return JSON.toJSONString(result);
    }
}
