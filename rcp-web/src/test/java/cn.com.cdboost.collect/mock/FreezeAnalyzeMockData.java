package cn.com.cdboost.collect.mock;

import cn.com.cdboost.collect.dto.CollectRecordDTO;
import cn.com.cdboost.collect.dto.MoteAnalyzeInfo;
import cn.com.cdboost.collect.dto.RoundChartVo;
import cn.com.cdboost.collect.dto.SucRateByCjqVo;
import cn.com.cdboost.collect.dto.param.FreeDayByCollGetQueryParam;
import cn.com.cdboost.collect.dto.response.RoundPercentInfo;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/21 0021.
 */
public class FreezeAnalyzeMockData {

    @Test
    public void roundChart() {
        Result<List<RoundChartVo>> result = new Result<>();
        List<RoundChartVo> list = Lists.newArrayList();
        RoundChartVo chartVo = new RoundChartVo();
        chartVo.setRemark("hahhah");
        chartVo.setRunTime("00:30-04:30");
        chartVo.setSuccRate(0.75);
        chartVo.setFailCnt(30);
        chartVo.setRound(1);
        chartVo.setTotal(120);

        RoundChartVo chartVo2 = new RoundChartVo();
        chartVo2.setRemark("hahhah");
        chartVo2.setRunTime("05:30-06:30");
        chartVo2.setSuccRate(0.95);
        chartVo2.setFailCnt(10);
        chartVo2.setRound(2);
        chartVo2.setTotal(120);

        list.add(chartVo);
        list.add(chartVo2);
        result.setData(list);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void rateChart() {
        Result<List<CollectRecordDTO>> result = new Result<>();
        List<CollectRecordDTO> list = Lists.newArrayList();
        CollectRecordDTO dto = new CollectRecordDTO();
        dto.setUpdateTime(new Date());
        dto.setFailCount(20);
        dto.setCustomerCount(100);
        dto.setSuccessCount(80);
        dto.setCollectDate("2017-11-11");

        CollectRecordDTO dto2 = new CollectRecordDTO();
        dto2.setUpdateTime(new Date());
        dto2.setFailCount(20);
        dto2.setCustomerCount(100);
        dto2.setSuccessCount(80);
        dto2.setCollectDate("2017-11-11");

        list.add(dto);
        list.add(dto2);
        result.setData(list);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void rateGroupByCjq() {
        FreeDayByCollGetQueryParam param = new FreeDayByCollGetQueryParam();
        param.setDeviceType("07");
        param.setFreezeDate("2017-11-12");
        param.setMoteEui("sdfsdfsdf");
        param.setPageNumber(1);
        param.setPageSize(20);

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void rateGroupByCjqReturn() {
        PageResult<List<MoteAnalyzeInfo>> result = new PageResult<>();
        List<MoteAnalyzeInfo> list = Lists.newArrayList();
        MoteAnalyzeInfo info = new MoteAnalyzeInfo();
        info.setEndRound(3);
        info.setFailCount(20);
        info.setInstallAddr("sdfsdfsd");
        info.setMeterCount(200);
        info.setMoteEui("dssfsd");
        info.setRunRound(2);
        info.setStartRound(1);

        MoteAnalyzeInfo info2 = new MoteAnalyzeInfo();
        info2.setEndRound(3);
        info2.setFailCount(20);
        info2.setInstallAddr("sdfsdfsd");
        info2.setMeterCount(200);
        info2.setMoteEui("dssfsd");
        info2.setRunRound(2);
        info2.setStartRound(1);

        list.add(info);
        list.add(info2);
        result.setData(list);
        result.setTotal(200L);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void roundPercent() {
        Result<List<RoundPercentInfo>> result = new Result<>();
        List<RoundPercentInfo> list = Lists.newArrayList();
        RoundPercentInfo info = new RoundPercentInfo();
        info.setLabel("label1");
        info.setPercent("0.8");
        info.setRound(1);
        info.setMeterCnt(200);
        info.setMoteCnt(5);
        info.setRound(10);

        RoundPercentInfo info2 = new RoundPercentInfo();
        info2.setLabel("label1");
        info2.setPercent("0.8");
        info2.setRound(1);
        info2.setMeterCnt(200);
        info2.setMoteCnt(5);
        info2.setRound(10);

        list.add(info);
        list.add(info2);
        result.setData(list);

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void sucRateByCjq() {
        Result<List<SucRateByCjqVo>> result = new Result<>();
        List<SucRateByCjqVo> list = Lists.newArrayList();
        SucRateByCjqVo vo = new SucRateByCjqVo();
        vo.setSucRate("0.8");
        vo.setFailCnt(20);
        vo.setRoundCnt(20);
        vo.setMeterCnt(100);
        vo.setDateTime("2017-11-12");
        vo.setGetWay("sfsdf");

        SucRateByCjqVo vo2 = new SucRateByCjqVo();
        vo2.setSucRate("0.8");
        vo2.setFailCnt(20);
        vo2.setRoundCnt(20);
        vo2.setMeterCnt(100);
        vo2.setDateTime("2017-11-12");
        vo2.setGetWay("sfsdf");

        list.add(vo);
        list.add(vo2);
        result.setData(list);

        System.out.println(JSON.toJSONString(result));
    }
}
