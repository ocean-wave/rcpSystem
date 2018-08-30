package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.FeePriceItemParamInfo;
import cn.com.cdboost.collect.model.FeePriceItem;

import java.util.List;

public interface FeePriceItemMapper extends CommonMapper<FeePriceItem> {
    List<FeePriceItemParamInfo> queryData(String priceSolsCode);
    List<FeePriceItem> queryParam();
}