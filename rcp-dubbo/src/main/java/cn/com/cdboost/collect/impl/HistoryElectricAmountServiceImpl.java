package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.dao.MeterDayPowerMapper;
import cn.com.cdboost.collect.dto.param.DayElectricInfoResponseParam;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.CustomerInfo;
import cn.com.cdboost.collect.param.HistoryElectricAmountParam;
import cn.com.cdboost.collect.service.CustomerInfoService;
import cn.com.cdboost.collect.service.HistoryElectricAmountService;
import cn.com.cdboost.collect.util.DateUtil;
import cn.com.cdboost.collect.vo.DayElectricInfoResponseVo;
import cn.com.cdboost.collect.vo.Result;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimaps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author wt
 * @desc
 * @create in  2018/5/4
 **/
@Service
public class HistoryElectricAmountServiceImpl implements HistoryElectricAmountService {

    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private MeterDayPowerMapper meterDayPowerMapper;
    @Override
    public List<DayElectricInfoResponseVo> queryHistoryElectricAmount(HistoryElectricAmountParam historyElectricAmountParam) throws ParseException {
        Map<String,String> map = DateUtil.paresedataFlag(historyElectricAmountParam.getDataFlag(), historyElectricAmountParam.getStartDate(), historyElectricAmountParam.getEndDate());
        CustomerInfo customerInfo=new CustomerInfo();
        customerInfo.setPropertyName(historyElectricAmountParam.getAddrCode());
        customerInfo = customerInfoService.selectOne(customerInfo);
        if(customerInfo==null){
            throw new BusinessException(100, Result.errorType.datenotexist.getdesc());
        }
        List<DayElectricInfoResponseParam> costCalculates = meterDayPowerMapper.queryHistoryElectricAmount(customerInfo.getCustomerNo(),map.get("startDateSub"),map.get("endDateSub"));
        if(costCalculates==null||costCalculates.size()==0){
            throw new BusinessException(100, Result.errorType.datenotexist.getdesc());
        }
        ImmutableListMultimap<String,DayElectricInfoResponseParam> immutableListMultimap= Multimaps.index(costCalculates, new Function<DayElectricInfoResponseParam, String>() {
            @Nullable
            @Override
            public String apply(@Nullable DayElectricInfoResponseParam input) {
                return input.getDate();
            }
        });
        List<DayElectricInfoResponseVo> dayElectricInfoResponseVos= Lists.newArrayList();
        for (Map.Entry<String, Collection<DayElectricInfoResponseParam>> electricInfoResponseParamEntry : immutableListMultimap.asMap().entrySet()) {
            DayElectricInfoResponseVo dayElectricInfoResponseVo=new DayElectricInfoResponseVo();
            dayElectricInfoResponseVo.setDate(electricInfoResponseParamEntry.getKey());
            Collection<DayElectricInfoResponseParam> value = electricInfoResponseParamEntry.getValue();
            BigDecimal power=new BigDecimal(0);
            BigDecimal readValue=new BigDecimal(0);
            for (DayElectricInfoResponseParam electricInfoResponseParam : value) {
                if(electricInfoResponseParam.getPower()==-1){
                    power=power.add(BigDecimal.valueOf(0));
                }else {
                    power=power.add(BigDecimal.valueOf(electricInfoResponseParam.getPower()));
                }
                if(electricInfoResponseParam.getReadValue()==-1){
                    readValue=readValue.add(BigDecimal.valueOf(0));
                }else {
                    readValue=readValue.add(BigDecimal.valueOf(electricInfoResponseParam.getReadValue()));
                }
            }
            dayElectricInfoResponseVo.setDate(electricInfoResponseParamEntry.getKey());
            dayElectricInfoResponseVo.setPower(power.floatValue());
            dayElectricInfoResponseVo.setReadValue(readValue.floatValue());
            dayElectricInfoResponseVos.add(dayElectricInfoResponseVo);
        }
        return dayElectricInfoResponseVos;
    }
}
