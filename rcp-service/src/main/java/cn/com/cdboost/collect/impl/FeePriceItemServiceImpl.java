package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.FeePriceItemMapper;
import cn.com.cdboost.collect.dto.FeePriceItemParam;
import cn.com.cdboost.collect.dto.FeePriceItemParamEntity;
import cn.com.cdboost.collect.dto.FeePriceItemParamInfo;
import cn.com.cdboost.collect.model.FeePriceItem;
import cn.com.cdboost.collect.service.FeePriceItemService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 费率参数项服务接口实现类
 */
@Service
public class FeePriceItemServiceImpl extends BaseServiceImpl<FeePriceItem> implements FeePriceItemService {

    @Autowired
    private FeePriceItemMapper feePriceItemMapper;

    @Override
    public List<FeePriceItemParamEntity> queryData(String priceSolsCode) {
        List<FeePriceItemParamEntity> resultList = Lists.newArrayList();
        List<FeePriceItemParamInfo> list = feePriceItemMapper.queryData(priceSolsCode);
        FeePriceItemParamEntity feePriceItemParamEntity = null;
        for (int i = 0; i < list.size(); i++) {
            if(i % 2 == 0){
                feePriceItemParamEntity = new FeePriceItemParamEntity();
                feePriceItemParamEntity.setPriceSolsCode(list.get(i).getPriceSolsCode());
                feePriceItemParamEntity.setItemCode1(list.get(i).getItemCode());
                feePriceItemParamEntity.setItemName1(list.get(i).getItemName());
                feePriceItemParamEntity.setDataValue1(list.get(i).getDataValue());
            }else {
                feePriceItemParamEntity.setPriceSolsCode(list.get(i).getPriceSolsCode());
                feePriceItemParamEntity.setItemCode2(list.get(i).getItemCode());
                feePriceItemParamEntity.setItemName2(list.get(i).getItemName());
                feePriceItemParamEntity.setDataValue2(list.get(i).getDataValue());

                resultList.add(feePriceItemParamEntity);
            }
        }

        return resultList;
    }

    @Override
    public List<FeePriceItemParam> queryParam() {
        List<FeePriceItemParam> resultList = Lists.newArrayList();
        List<FeePriceItem> list = feePriceItemMapper.queryParam();
        FeePriceItemParam feePriceItemParam = null;
        for (int i = 0; i < list.size(); i++) {
            if(i % 2 == 0){
                feePriceItemParam = new FeePriceItemParam();
                feePriceItemParam.setItemCode1(list.get(i).getItemCode());
                feePriceItemParam.setItemName1(list.get(i).getItemName());
            }else {
                feePriceItemParam.setItemCode2(list.get(i).getItemCode());
                feePriceItemParam.setItemName2(list.get(i).getItemName());

                resultList.add(feePriceItemParam);
            }
        }
        return resultList;
    }
}
