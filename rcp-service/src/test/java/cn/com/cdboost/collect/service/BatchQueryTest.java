package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.dto.response.OnlineStatus;
import cn.com.cdboost.collect.enums.DeviceType;
import cn.com.cdboost.collect.model.DeviceMeterParam;
import cn.com.cdboost.collect.util.CNoUtil;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/1 0001.
 */
public class BatchQueryTest extends BaseServiceReadTest{
    @Autowired
    private DeviceMeterParamService deviceMeterParamService;
    @Autowired
    private DeviceInfoService deviceInfoService;
    
    @Test
    public void batchQuery() {
        List<String> cnos = Lists.newArrayList();
        for (int i = 0; i < 100000; i++) {
            String deviceNo = String.valueOf(i + 65536);
            String cno = CNoUtil.CreateCNo(DeviceType.ELECTRIC_METER.getCode(), deviceNo);
            cnos.add(cno);
        }
        long begin = System.currentTimeMillis();
        List<DeviceMeterParam> meterParams = deviceMeterParamService.findDeviceMeterParamByCnos(cnos);
        System.out.println(meterParams.size());
        long end = System.currentTimeMillis();
        System.out.println("树菜单结构查询总耗时"+(end-begin)+ "毫秒");
    }

    @Test
    public void query2() {
        List<String> cnos = Lists.newArrayList();
        for (int i = 0; i < 100000; i++) {
            String deviceNo = String.valueOf(i + 65536);
            String cno = CNoUtil.CreateCNo(DeviceType.ELECTRIC_METER.getCode(), deviceNo);
            cnos.add(cno);
        }
        long begin = System.currentTimeMillis();
        Map<String, OnlineStatus> onlineStatusMap = deviceInfoService.queryMainSubOnlineStatus(cnos);
        System.out.println(onlineStatusMap.size());
        long end = System.currentTimeMillis();
        System.out.println("树菜单结构查询总耗时"+(end-begin)+ "毫秒");
    }
}
