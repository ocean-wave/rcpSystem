package cn.com.cdboost.collect.controller;


import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.ImpCollectionAnalysisDTO;
import cn.com.cdboost.collect.dto.KeyCollectDTO;
import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.dto.param.KeyCollectQueryParam;
import cn.com.cdboost.collect.dto.param.KeyCollectVo;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.service.GenerateFileService;
import cn.com.cdboost.collect.service.KeyCollectionService;
import cn.com.cdboost.collect.vo.PageResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;


/**
 * 重点采集分析模块相关接口
 */
@Controller
@RequestMapping(value = "/keyCollection")
public class KeyCollectionController {
    private static final Logger logger = LoggerFactory.getLogger(KeyCollectionController.class);

    @Autowired
    private KeyCollectionService keyCollectionService;
    @Autowired
    private GenerateFileService generateFileService;

    /**
     * 重点采集分析数据
     * @param
     * @param
     * @return
     */
    @SystemControllerLog(description = "重点采集数据")
    @RequestMapping(value = "/analysis", method = RequestMethod.POST)
    @ResponseBody
    public String analysis(HttpSession session, @RequestParam String searchDate) throws ParseException {
        PageResult<List<ImpCollectionAnalysisDTO>> result =  new PageResult<>();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        if (StringUtils.isEmpty(searchDate)) {
            result.error("dataFlag不能为空");
            return JSON.toJSONString(result);
        }

        List<ImpCollectionAnalysisDTO> dataList = keyCollectionService.queryKeyCollectionOriginalNew(currentUser.getId(), searchDate);
        result.setData(dataList);
        result.setTotal(Long.valueOf(dataList.size()));
        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "重点采集数据下载")
    @RequestMapping("/downKeyCollectionAnalysis")
    public void downloadCollectResultData(HttpSession session, HttpServletResponse response, @RequestParam String searchDate) {
        try {
            LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
            List<ImpCollectionAnalysisDTO> analysisDTOs = keyCollectionService.queryKeyCollectionOriginalNew(currentUser.getId(), searchDate);
            XSSFWorkbook workBook = generateFileService.generateKeyCollectionAnalysisExcel("重点采集分析", analysisDTOs);
            //通过Response把数据以Excel格式保存
            response.reset();
            //设置response流信息的头类型，MIME码
            response.setContentType("application/vnd.ms-excel");
            ServletOutputStream out;
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
            logger.error("系统异常：",e);
        }
    }

    @SystemControllerLog(description = "重点用户批次采集成功")
    @RequestMapping(value = "/collectSucc", method = RequestMethod.POST)
    @ResponseBody
    public String collectSucc(@Valid @RequestBody KeyCollectQueryParam queryParam) {

        PageResult<List<KeyCollectDTO>> result =  new PageResult<>();
        KeyCollectVo queryVo = new KeyCollectVo();
        BeanUtils.copyProperties(queryParam,queryVo);
        String queueGuid = queryParam.getQueueGuid();
        if (queueGuid != null) {
            queryVo.setQueueGuid(queueGuid);
        }

        List<KeyCollectDTO> list = keyCollectionService.queryKeyCollectSucc(queryVo);
        result.setData(list);
        //PS:暂时
        result.setTotal(queryVo.getRowCount());

        return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
    }

    @SystemControllerLog(description = "重点用户批次采集失败")
    @RequestMapping(value = "/collectFail", method = RequestMethod.POST)
    @ResponseBody
    public String collectFail(@Valid @RequestBody KeyCollectQueryParam queryParam) {

        PageResult<List<KeyCollectDTO>> result =  new PageResult<>();
        KeyCollectVo queryVo = new KeyCollectVo();
        BeanUtils.copyProperties(queryParam,queryVo);
        String queueGuid = queryParam.getQueueGuid();
        if (queueGuid != null) {
            queryVo.setQueueGuid(queueGuid);
        }

        List<KeyCollectDTO> list = keyCollectionService.queryKeyCollectFail(queryVo);
        result.setData(list);
        result.setTotal(queryVo.getRowCount());

        return JSON.toJSONString(result);
    }


}
