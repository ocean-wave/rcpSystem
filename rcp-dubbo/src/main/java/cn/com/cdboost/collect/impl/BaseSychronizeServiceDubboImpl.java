package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.dao.MeterDayPowerMapper;
import cn.com.cdboost.collect.dto.param.BaseSychronizeDtoVo;
import cn.com.cdboost.collect.param.BaseSychronizeDto;
import cn.com.cdboost.collect.param.BaseSychronizeReseponse;
import cn.com.cdboost.collect.service.BaseSychronizeServiceDubbo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wt
 * @desc
 * @create in  2018/9/6
 **/
@Service
public class BaseSychronizeServiceDubboImpl implements BaseSychronizeServiceDubbo {
    @Autowired
    private MeterDayPowerMapper meterDayPowerMapper;
    @Override
    public BaseSychronizeReseponse baseSychronizeService(BaseSychronizeDto baseSychronizeDto) {
        BaseSychronizeDtoVo baseSychronizeDtoVo=new BaseSychronizeDtoVo();
        BeanUtils.copyProperties(baseSychronizeDto,baseSychronizeDtoVo);
        if("orgInfo".equals(baseSychronizeDtoVo.getDataType())){
            meterDayPowerMapper.OrgFromService( baseSychronizeDtoVo);
        }
        if("users".equals(baseSychronizeDtoVo.getDataType())){
            meterDayPowerMapper.CstFromService( baseSychronizeDtoVo);
        }
        if("elecType".equals(baseSychronizeDtoVo.getDataType())){
            meterDayPowerMapper.ElecFromService( baseSychronizeDtoVo);
        }
        if("payment".equals(baseSychronizeDtoVo.getDataType())){
            meterDayPowerMapper.PayFromService( baseSychronizeDtoVo);
        }
        BaseSychronizeReseponse baseSychronizeReseponse=new BaseSychronizeReseponse();
        if(baseSychronizeDtoVo.getResult()==1){
            baseSychronizeReseponse.setCode(1);
            baseSychronizeReseponse.setMsg("消费成功");
        }
        if(baseSychronizeDtoVo.getResult()==0){
            baseSychronizeReseponse.setCode(0);
            baseSychronizeReseponse.setMsg("消费失败");
        }
        if(baseSychronizeDtoVo.getResult()==2){
            baseSychronizeReseponse.setCode(100);
            baseSychronizeReseponse.setMsg("队列表表不存在数据或已消费");
        }
        return baseSychronizeReseponse;
    }
}
