package cn.com.cdboost.collect.controller;


import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.constant.CustomerInfoConstant;
import cn.com.cdboost.collect.constant.DictCodeConstant;
import cn.com.cdboost.collect.dto.response.DictItemInfo;
import cn.com.cdboost.collect.enums.DeviceType;
import cn.com.cdboost.collect.model.DictItem;
import cn.com.cdboost.collect.service.DictItemService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author xzy
 * @desc 数据字典
 * @create 2017/7/12 0012
 **/
@Controller
@RequestMapping("/dictItem")
public class DictItemController {
    private static final Logger logger = LoggerFactory.getLogger(DictItemController.class);

    @Autowired
    private DictItemService dictItemService;


    /**
     * 通过字典类别，查询静态字典明细记录
     * @param dictCode
     * @return
     */
    @SystemControllerLog(description = "通过字典类别，查询静态字典明细记录")
    @RequestMapping(value = "queryByDictCode", method = RequestMethod.POST)
    @ResponseBody
    public String queryByDictCode(@RequestParam Integer dictCode){
        Result<List<DictItemInfo>> result = new Result<>();
        List<DictItem> list = dictItemService.findByDictCode(String.valueOf(dictCode));
        List<DictItemInfo> convert = this.convert(list);
        result.setData(convert);
        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "通过设备类别，采集方式，查询对应的采集设备类型字典项值")
    @RequestMapping(value = "queryCollectDevType", method = RequestMethod.POST)
    @ResponseBody
    public String queryCollectDevType(@RequestParam String deviceType,@RequestParam Integer commPort) {
        Result<List<DictItemInfo>> result = new Result();
        if (StringUtils.isEmpty(deviceType)) {
            result.error("deviceType不能为空");
            return JSON.toJSONString(result);
        }

        String diceCode = this.getDiceCode(deviceType, commPort);
        List<DictItem> list = dictItemService.findByDictCode(diceCode);
        List<DictItemInfo> convert = this.convert(list);
        result.setData(convert);
        return JSON.toJSONString(result);
    }

    /**
     * 转换成前端需要的信息
     * @param list
     * @return
     */
    private List<DictItemInfo> convert(List<DictItem> list) {
        List<DictItemInfo> itemInfos = Lists.newArrayList();
        if (CollectionUtils.isEmpty(list)) {
            return itemInfos;
        }

        for (DictItem item : list) {
            DictItemInfo info = new DictItemInfo();
            BeanUtils.copyProperties(item,info);
            itemInfos.add(info);
        }
        return itemInfos;
    }

    /**
     * 获取字典表中对应的dictCode
     * @param deviceType
     * @param commPort
     * @return
     */
    private String getDiceCode(String deviceType, Integer commPort) {
        String dictCode = null;
        if (DeviceType.ELECTRIC_METER.getCode().equals(deviceType)) {
            if (CustomerInfoConstant.ElectricCommPort.LORA_WAN.getCode().equals(commPort)) {
                dictCode = DictCodeConstant.LORAWAN_ELEC_COLLECT_DEV_TYPE.getDictCode();
            } else if (CustomerInfoConstant.ElectricCommPort.CARRIER.getCode().equals(commPort)) {
                dictCode = DictCodeConstant.CARRIER_ELEC_COLLECT_DEV_TYPE.getDictCode();
            }
        } else if (DeviceType.WATER_METER.getCode().equals(deviceType)) {
            if (CustomerInfoConstant.WaterCommPort.LORA_WAN.getCode().equals(commPort)) {
                dictCode = DictCodeConstant.LORAWAN_WATER_COLLECT_DEV_TYPE.getDictCode();
            } else if (CustomerInfoConstant.WaterCommPort.CARRIER.getCode().equals(commPort)) {
                dictCode = DictCodeConstant.CARRIER_WATER_COLLECT_DEV_TYPE.getDictCode();
            }
        } else if (DeviceType.GAS_METER.getCode().equals(deviceType)) {
            if (CustomerInfoConstant.GasCommPort.LORA_WAN.getCode().equals(commPort)) {
                dictCode = DictCodeConstant.LORAWAN_GAS_COLLECT_DEV_TYPE.getDictCode();
            } else if (CustomerInfoConstant.GasCommPort.CARRIER.getCode().equals(commPort)) {
                dictCode = DictCodeConstant.CARRIER_GAS_COLLECT_DEV_TYPE.getDictCode();
            }
        }
        return dictCode;
    }
}
