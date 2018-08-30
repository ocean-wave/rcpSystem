package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.FeePriceItemParam;
import cn.com.cdboost.collect.dto.FeePriceSolsDTO;
import cn.com.cdboost.collect.dto.param.FeePriceSolsQueryParam;
import cn.com.cdboost.collect.dto.param.FeePriceSolsQueryVo;
import cn.com.cdboost.collect.enums.Auth;
import cn.com.cdboost.collect.service.FeePriceItemService;
import cn.com.cdboost.collect.service.FeePriceSolsService;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author xzy
 * @desc 电价方案模块
 * @create 2017/7/5 0005
 **/
@Controller
@RequestMapping(value = "price")
public class PriceSchemeController {
    private static final Logger logger = LoggerFactory.getLogger(PriceSchemeController.class);

    @Autowired
    private FeePriceSolsService feePriceSolsService;
    @Autowired
    private FeePriceItemService feePriceItemService;

    // 电价方案列表查询
    @Auth(menuID=10002201L,actionID=4L)
    @SystemControllerLog(description = "电价方案列表查询")
    @RequestMapping(value = "queryList" , method = RequestMethod.POST)
    @ResponseBody
    public String queryList(@Valid @RequestBody FeePriceSolsQueryParam queryParam){
        PageResult<List<FeePriceSolsDTO>> result = new PageResult<>();
        FeePriceSolsQueryVo queryVo = new FeePriceSolsQueryVo();
        BeanUtils.copyProperties(queryParam,queryVo);
        Integer isEnabled = queryParam.getIsEnabled();
        if (isEnabled != null) {
            queryVo.setIsEnabled(String.valueOf(isEnabled));
        }

        List<FeePriceSolsDTO> list = feePriceSolsService.query(queryVo);
        result.setData(list);
        result.setTotal(queryVo.getRowCount());

        return JSON.toJSONString(result);
    }

    // 查询参数
    @SystemControllerLog(description = "查询参数")
    @RequestMapping(value = "/queryParam", method = RequestMethod.POST)
    @ResponseBody
    public String queryParam() {
        Result<List<FeePriceItemParam>> result = new Result<>();
        List<FeePriceItemParam> list = feePriceItemService.queryParam();
        if(CollectionUtils.isEmpty(list)){
            List<FeePriceItemParam> emptyList = Lists.newArrayList();
            result.setData(emptyList);
            return JSON.toJSONString(result);
        }

        result.setData(list);
        return JSON.toJSONString(result);
    }

    // 查询所有参数数据(电价方案详情查询)
    @SystemControllerLog(description = "电价方案详情查询")
    @RequestMapping(value = "/queryData",method = RequestMethod.POST)
    @ResponseBody
    public String queryData(@RequestParam String priceSolsCode){
        Result<FeePriceSolsDTO> result = new Result<>();
        if (StringUtils.isEmpty(priceSolsCode)) {
            result.error("priceSolsCode不能为空");
            return JSON.toJSONString(result);
        }
        FeePriceSolsDTO feePriceSolsDTO = feePriceSolsService.queryData(priceSolsCode);
        if(feePriceSolsDTO == null){
            result.error("未查出电价方案详情");
            return JSON.toJSONString(result);
        }

        result.setData(feePriceSolsDTO);
        return JSON.toJSONString(result);
    }

}
