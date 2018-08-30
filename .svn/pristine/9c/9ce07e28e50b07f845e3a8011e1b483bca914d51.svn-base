package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.HistogramDataInfo;
import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.dto.param.HistogramDataQueryParam;
import cn.com.cdboost.collect.dto.param.HistogramDataQueryVo;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.service.UseEnergyStatisticsService;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 用能统计
 */
@Controller
@RequestMapping("/energyStatistics")
public class UseEnergyStatisticsController {

    @Autowired
    private UseEnergyStatisticsService useEnergyStatisticsService;

    @SystemControllerLog(description = "查询柱状图数据，包含楼栋和变压器统计数据")
    @RequestMapping("/queryHistogramData")
    @ResponseBody
    public String queryHistogramData(HttpSession session, @Valid @RequestBody HistogramDataQueryParam queryParam) {
        Result<HistogramDataInfo> result = new Result<>();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        HistogramDataQueryVo queryVo = new HistogramDataQueryVo();
        BeanUtils.copyProperties(queryParam,queryVo);
        queryVo.setOrgNo(currentUser.getOrgNo());

        HistogramDataInfo dataInfo = useEnergyStatisticsService.statistics(queryVo);
        result.setData(dataInfo);
        return JSON.toJSONString(result);
    }

}
