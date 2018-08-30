package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.DictItemMapper;
import cn.com.cdboost.collect.model.DictItem;
import cn.com.cdboost.collect.service.DictItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


/**
 * 字典项服务接口实现类
 */
@Service("dictItemService")
public class DictItemServiceImpl extends BaseServiceImpl<DictItem> implements DictItemService {
    private static final Logger logger = LoggerFactory.getLogger(DictItemServiceImpl.class);

    @Autowired
    private DictItemMapper dictItemMapper;

    @Override
    public DictItem findByNameAndValue(String dictCode, String dictItemName) {
        DictItem param = new DictItem();
        param.setDictCode(dictCode);
        param.setDictItemName(dictItemName);
        param.setIsEnabled(1);
        return dictItemMapper.selectOne(param);
    }

    @Override
    public List<DictItem> findByDictCode(String dictCode) {
        logger.info("DictItemServiceImpl-findByDictCode query:"+dictCode);
        DictItem param = new DictItem();
        param.setDictCode(dictCode);
        param.setIsEnabled(1);
        List<DictItem> list = dictItemMapper.select(param);
        logger.info("DictItemServiceImpl-findByDictCode 返回值:"+list.toString());
        return list;
    }

    @Override
    public List<DictItem> batchQueryByDictItemValues(List<String> values) {
        Condition condition = new Condition(DictItem.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("dictCode",14);
        criteria.andIn("dictItemValue",values);
        return dictItemMapper.selectByCondition(condition);
    }

    @Override
    public List<DictItem> batchQuery(List<String> dictCodes, List<String> dictItemValues) {
        Condition condition = new Condition(DictItem.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("dictCode",dictCodes);
        criteria.andIn("dictItemValue",dictItemValues);
        return dictItemMapper.selectByCondition(condition);
    }

    @Override
    public DictItem findByCodeAndValue(String dictCode, String dictItemValue) {
        DictItem param = new DictItem();
        param.setDictCode(dictCode);
        param.setDictItemValue(dictItemValue);
        param.setIsEnabled(1);
        return dictItemMapper.selectOne(param);
    }


    @Override
    public List<DictItem> findByDictName(String dictName) {
        logger.info("DictItemServiceImpl-findBydictName query:"+dictName);
        DictItem param = new DictItem();
        param.setDictItemName(dictName);
        param.setIsEnabled(1);
        List<DictItem> list = dictItemMapper.select(param);
        logger.info("DictItemServiceImpl-findByBydictName 返回值:"+list.toString());
        return list;
    }
}
