package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.cache.DeviceRelationCacheVo;
import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.constant.RedisKeyConstant;
import cn.com.cdboost.collect.dao.ChangeMeterMapper;
import cn.com.cdboost.collect.dto.ChangeMeterDetailInfo;
import cn.com.cdboost.collect.dto.ChangeMeterInfo;
import cn.com.cdboost.collect.dto.param.ChangeMeterListQueryVo;
import cn.com.cdboost.collect.dto.param.ChangeMeterParamVo;
import cn.com.cdboost.collect.dto.param.ChangeMeterVo;
import cn.com.cdboost.collect.enums.DeviceType;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.ChangeMeter;
import cn.com.cdboost.collect.model.MeterRelation;
import cn.com.cdboost.collect.model.CustomerDevMap;
import cn.com.cdboost.collect.service.ChangeMeterService;
import cn.com.cdboost.collect.service.CustomerDevMapService;
import cn.com.cdboost.collect.service.MeterRelationService;
import cn.com.cdboost.collect.util.MathUtil;
import cn.com.cdboost.collect.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * 换表服务接口实现类
 */
@Service
public class ChangeMeterServiceImpl extends BaseServiceImpl<ChangeMeter> implements ChangeMeterService {
    private static final Logger logger = LoggerFactory.getLogger(ChangeMeterServiceImpl.class);

    @Autowired
    private ChangeMeterMapper changeMeterMapper;
    @Autowired
    private CustomerDevMapService customerDevMapService;
    @Autowired
    private MeterRelationService meterRelationService;
    @Autowired
    private RedisUtil redisUtil;


    @Override
    @Transactional
    public int updateChangeMeter(ChangeMeterParamVo paramVo, Integer userId, String changeRemark,String guid) throws BusinessException {
        ChangeMeterVo meterVo = new ChangeMeterVo();
        meterVo.setUserId(String.valueOf(userId));
        meterVo.setChangeRemark(changeRemark);
        meterVo.setGuid(guid);
        meterVo.setStrJson(JSON.toJSONString(paramVo));
        changeMeterMapper.updateChangeMeter(meterVo);
        String result = meterVo.getResult();
        Integer retVal = Integer.parseInt(result);
        String oldCno = paramVo.getOldCno();
        String deviceType = oldCno.substring(0, 2);
        if (DeviceType.ELECTRIC_METER.getCode().equals(deviceType)) {
            if (retVal == 1) {
                // 记录需要重新缓存的对象列表
                List<DeviceRelationCacheVo> cacheVoList = Lists.newArrayList();

                // 查询该新表以及该新表下的所有孩子节点
                List<MeterRelation> meterRelations = meterRelationService.queryChildTreeByCno(paramVo.getNewCno());
                if (!CollectionUtils.isEmpty(meterRelations)) {
                    for (MeterRelation relation : meterRelations) {
                        DeviceRelationCacheVo cacheVo = new DeviceRelationCacheVo();
                        BeanUtils.copyProperties(relation,cacheVo);
                        cacheVoList.add(cacheVo);
                    }
                }
                // redis 批量缓存设备关系缓存
                redisUtil.setDeviceRelationList(cacheVoList);

                Set<String> keys = Sets.newHashSet();
                // redis 操作
                String key = RedisKeyConstant.DEVICE_PREFIX + oldCno;
                keys.add(key);

                // 删除旧表的relation key
                String relationKey = RedisKeyConstant.DEVICE_RELATION_PREFIX + oldCno;
                keys.add(relationKey);

                // redis 批量删除keys
                redisUtil.batchDeleteKeys(keys);
            }
        }

        return retVal;
    }

    @Override
    public List<ChangeMeterInfo> queryChangeMeters(ChangeMeterListQueryVo queryVo) {
        String deviceNo = queryVo.getDeviceNo();
        if (deviceNo == null) {
            queryVo.setDeviceNo("");
        }

        String deviceType = queryVo.getDeviceType();
        if (deviceType == null) {
            queryVo.setDeviceType(DeviceType.ELECTRIC_METER.getCode());
        }

        String customerNo = queryVo.getCustomerNo();
        if (customerNo == null) {
            queryVo.setCustomerNo("");
        }

        String customerName = queryVo.getCustomerName();
        if (customerName == null) {
            queryVo.setCustomerName("");
        }

        String customerAddr = queryVo.getCustomerAddr();
        if (customerAddr == null) {
            queryVo.setCustomerAddr("");
        }

        String customerContact = queryVo.getCustomerContact();
        if (customerContact == null) {
            queryVo.setCustomerContact("");
        }

        String meterUserNo = queryVo.getMeterUserNo();
        if (meterUserNo == null) {
            queryVo.setMeterUserNo("");
        }

        String changeDateStart = queryVo.getChangeDateStart();
        if (changeDateStart == null) {
            queryVo.setChangeDateStart("");
        }

        String changeDateEnd = queryVo.getChangeDateEnd();
        if (changeDateEnd == null) {
            queryVo.setChangeDateEnd("");
        }

        List<ChangeMeterInfo> list = changeMeterMapper.queryChangeMeters(queryVo);
        return list;

    }

    @Override
    public ChangeMeterDetailInfo queryChangeMeterDetail(String cno, String customerNo,String deviceType) {
        CustomerDevMap devMap = customerDevMapService.queryByCno4ChangeDetail(cno);

        ChangeMeterDetailInfo detailInfo = changeMeterMapper.queryChangeMeterDetail(cno,customerNo, deviceType,devMap.getMeterUserNo());
        BigDecimal power = detailInfo.getPower();
        detailInfo.setPower(MathUtil.setPrecision(power));

        BigDecimal newPower = detailInfo.getNewPower();
        detailInfo.setNewPower(MathUtil.setPrecision(newPower));

        BigDecimal remainAmount = detailInfo.getRemainAmount();
        detailInfo.setRemainAmount(MathUtil.setPrecision(remainAmount));

        BigDecimal newRemainAmount = detailInfo.getNewRemainAmount();
        detailInfo.setNewRemainAmount(MathUtil.setPrecision(newRemainAmount));

        BigDecimal meterTotalAmount = detailInfo.getMeterTotalAmount();
        detailInfo.setMeterTotalAmount(MathUtil.setPrecision(meterTotalAmount));

        BigDecimal newMeterTotalAmount = detailInfo.getNewMeterTotalAmount();
        detailInfo.setNewMeterTotalAmount(MathUtil.setPrecision(newMeterTotalAmount));

        BigDecimal totalAmount = detailInfo.getTotalAmount();
        detailInfo.setTotalAmount(MathUtil.setPrecision(totalAmount));

        BigDecimal newTotalAmount = detailInfo.getNewTotalAmount();
        detailInfo.setNewTotalAmount(MathUtil.setPrecision(newTotalAmount));

        return detailInfo;
    }
}
