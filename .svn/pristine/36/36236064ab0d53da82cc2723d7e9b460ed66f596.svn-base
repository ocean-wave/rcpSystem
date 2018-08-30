package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.model.DictItem;

import java.util.List;

/**
 * 字典项服务接口
 */
public interface DictItemService extends BaseService<DictItem> {
    // 通过dictCode查询,已启用的字典项
    List<DictItem> findByDictName(String dictName);
    // 通过dictCode查询,已启用的字典项
    List<DictItem> findByDictCode(String dictCode);

    List<DictItem> batchQueryByDictItemValues(List<String> values);

    List<DictItem> batchQuery(List<String> dictCodes,List<String> dictItemValues);

    DictItem findByCodeAndValue(String dictCode, String dictItemValue);

    DictItem findByNameAndValue(String dictCode, String dictItemName);
}
