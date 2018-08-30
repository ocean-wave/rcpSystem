package cn.com.cdboost.collect;

import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.*;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import javax.sound.midi.Soundbank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/11 0011.
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(new NodeData()));
       // NodeData nodeData = ;
   }




    public static void loginSusscee() {
        Result<LoginSuccessInfo> result = new Result<>();
        LoginSuccessInfo info = new LoginSuccessInfo();
        info.setUserId(1);
        info.setRoleId(1);
        info.setUserName("系统管理员");
        info.setUserMobile("13510241548");
        info.setUserMail("763214524@qq.com");
        result.setData(info);
        String s = JSON.toJSONString(result);
        System.out.println(s);
    }

    public static void querySystemConfig() {
        Result<SystemConfigInfo> result = new Result<>();
        SystemConfigInfo configInfo = new SystemConfigInfo();
        configInfo.setFrontProcessorIp("10.10.1.250");
        configInfo.setFrontProcessorPort("7000");
        configInfo.setApn("CMNET");
        configInfo.setSysName("社区能源管理平台");
        configInfo.setBackground("");
        configInfo.setBalanceDate("28");
        configInfo.setVersion("1.1.11");
        configInfo.setPayAddr("四川省成都市武侯区武兴三路18号");
        configInfo.setCompanyName("龙湖时代天街");
        result.setData(configInfo);
        String s = JSON.toJSONString(result);
        System.out.println(s);
    }

    public static void realDataQuery() {
        PageResult<List<MeterCollectDataListInfo>> result = new PageResult<>();
        List<MeterCollectDataListInfo> dataList = Lists.newArrayList();
        MeterCollectDataListInfo info1 = new MeterCollectDataListInfo();
        info1.setJzqNo("999999999");
        info1.setJzqCno("040000000000000000000999999999");
        info1.setAlarmThreshold(BigDecimal.valueOf(100));
        info1.setBalance(BigDecimal.valueOf(200));
        info1.setCollectTime("2017-12-11 10:12:54");
        info1.setCommRule(30);
        info1.setCustomerAddr("武侯区天使公园1楼");
        info1.setCustomerContact("13510241478");
        info1.setCustomerNo("6c45c24b-1cf1-45cb-83e2-13f27c9d5d99");
        info1.setCustomerName("zhangsan");
        info1.setDeviceCno("070000000000000000901176000001");
        info1.setDeviceNo("901176000001");
        info1.setGroupGuid("7879a1c0-da2c-11e7-a53d-902b34df9a12");
        info1.setIsAccount(0);
        info1.setIsOnline(1);
        info1.setMoteType("C");
        info1.setOrgName("成都博高");
        info1.setPr0(BigDecimal.valueOf(200));
        info1.setPropertyName("1-101");
        info1.setSendFlag(1);
        info1.setReadStatus(0);

        MeterCollectDataListInfo info2 = new MeterCollectDataListInfo();
        info2.setJzqNo("999999999");
        info2.setJzqCno("040000000000000000000999999999");
        info2.setAlarmThreshold(BigDecimal.valueOf(100));
        info2.setBalance(BigDecimal.valueOf(200));
        info2.setCollectTime("2017-12-11 10:12:54");
        info2.setCommRule(30);
        info2.setCustomerAddr("武侯区天使公园1楼");
        info2.setCustomerContact("13510241478");
        info2.setCustomerNo("6c45c24b-1cf1-45cb-83e2-13f27c9d5d99");
        info2.setCustomerName("zhangsan");
        info2.setDeviceCno("070000000000000000901176000001");
        info2.setDeviceNo("901176000001");
        info2.setGroupGuid("7879a1c0-da2c-11e7-a53d-902b34df9a12");
        info2.setIsAccount(0);
        info2.setIsOnline(1);
        info2.setMoteType("C");
        info2.setOrgName("成都博高");
        info2.setPr0(BigDecimal.valueOf(200));
        info2.setPropertyName("1-101");
        info2.setSendFlag(1);
        info2.setReadStatus(0);

        dataList.add(info1);
        dataList.add(info2);
        result.setData(dataList);
        result.setTotal(100L);

        String s = JSON.toJSONString(result);
        System.out.println(s);
    }

    public static void realDataQueryParam() {
        Result<RealTimeDataQueryParam> result = new Result<>();
        RealTimeDataQueryParam param = new RealTimeDataQueryParam();
        param.setDeviceType("07");
        param.setPageNumber(1);
        param.setPageSize(20);
        result.setData(param);

        String s = JSON.toJSONString(result);
        System.out.println(s);
    }

    public static void batchSendCollectInstructionsQuery() {
        BatchSendCollectQueryParam param = new BatchSendCollectQueryParam();
        param.setGuid("080dada8-e133-c9c1-da83-ae5d04886548");
        List<RealCollectMeterParam> meters = Lists.newArrayList();
        RealCollectMeterParam param1 = new RealCollectMeterParam();
        param1.setJzqCno("040000000000000000000999999999");
        param1.setDeviceCno("070000000000000000901176000055");
        param1.setGroupGuid("12308b8a-de3d-11e7-a53d-902b34df9a12");

        RealCollectMeterParam param2 = new RealCollectMeterParam();
        param2.setJzqCno("040000000000000000000999999999");
        param2.setDeviceCno("070000000000000000901176000054");
        param2.setGroupGuid("12308b8a-de3d-11e7-a53d-902b34df9a12");

        meters.add(param1);
        meters.add(param2);
        param.setMeters(meters);

        String s = JSON.toJSONString(param);
        System.out.println(s);
    }

    public static void batchSendCollectInstructionsReturn() {
        Result<String> result = new Result<>();
        result.setData("2017-12-11 10:10:25");
        String s = JSON.toJSONString(result);
        System.out.println(s);
    }

    public static void readCollectListStateFailList() {
        Result<RealTimeDataStatuListInfo> result = new Result<>();
        RealTimeDataStatuListInfo info = new RealTimeDataStatuListInfo();
        info.setStatus(1);
        info.setIsUpdate(Boolean.TRUE);
        info.setTotal(2);
        info.setDealNum(2);
        info.setUndealNum(0);
        info.setSuccessfulNum(0);
        info.setFailNum(2);
        List<MeterCollectDataListInfo> successList = Lists.newArrayList();
        info.setSuccessList(successList);
        List<String> cnoList = Lists.newArrayList();
        cnoList.add("070000000000000000901176000054");
        cnoList.add("070000000000000000901176000055");
        info.setCnoList(cnoList);
        List<MeterCollectDataFailInfo> failList = Lists.newArrayList();
        MeterCollectDataFailInfo info1 = new MeterCollectDataFailInfo();
        info1.setDeviceCno("070000000000000000901176000054");
        info1.setDeviceNo("901176000054");
        info1.setCustomerNo("f3e71517-5514-435c-b4e7-6f7db48ee6a3");
        info1.setCustomerName("张三");
        info1.setCustomerContact("18012345678");
        info1.setCustomerAddr("1-1054");
        info1.setJzqCno("040000000000000000000999999999");
        info1.setIsOnline("1");
        info1.setGroupGuid("e11ed906-de3e-11e7-a53d-902b34df9a12");

        MeterCollectDataFailInfo info2 = new MeterCollectDataFailInfo();
        info2.setDeviceCno("070000000000000000901176000055");
        info2.setDeviceNo("901176000055");
        info2.setCustomerNo("eaa31ae0-0e8e-4f1f-8f4c-6a385af112b8");
        info2.setCustomerName("李四");
        info2.setCustomerContact("18012345678");
        info2.setCustomerAddr("1-1055");
        info2.setJzqCno("040000000000000000000999999999");
        info2.setIsOnline("1");
        info2.setGroupGuid("e11ed906-de3e-11e7-a53d-902b34df9a12");

        failList.add(info1);
        failList.add(info2);
        info.setFailList(failList);
        result.setData(info);

        String s = JSON.toJSONString(result);
        System.out.println(s);
    }

    public static void stop() {
        Result<Boolean> result = new Result<>();
        result.setData(Boolean.TRUE);
        System.out.println(JSON.toJSONString(result));
    }

    public static void queryFailParam() {
        RealTimeDataFailQueryParam param = new RealTimeDataFailQueryParam();
        param.setSearchDate("2017-11-12 12:14:56");
        param.setDeviceType("07");
        param.setGuid("080dada8-e133-c9c1-da83-ae5d04886548");
        param.setPageSize(20);
        param.setPageNumber(1);

        System.out.println(JSON.toJSONString(param));
    }

    public static void queryFailReturn() {
        PageResult<List<MeterCollectDataFailInfo>> result = new PageResult<>();
        List<MeterCollectDataFailInfo> dataList = Lists.newArrayList();
        MeterCollectDataFailInfo info1 = new MeterCollectDataFailInfo();
        info1.setDeviceCno("070000000000000000901176000054");
        info1.setDeviceNo("901176000054");
        info1.setCustomerNo("f3e71517-5514-435c-b4e7-6f7db48ee6a3");
        info1.setCustomerName("张三");
        info1.setCustomerContact("18012345678");
        info1.setCustomerAddr("1-1054");
        info1.setJzqCno("040000000000000000000999999999");
        info1.setIsOnline("1");
        info1.setGroupGuid("e11ed906-de3e-11e7-a53d-902b34df9a12");

        MeterCollectDataFailInfo info2 = new MeterCollectDataFailInfo();
        info2.setDeviceCno("070000000000000000901176000055");
        info2.setDeviceNo("901176000055");
        info2.setCustomerNo("eaa31ae0-0e8e-4f1f-8f4c-6a385af112b8");
        info2.setCustomerName("李四");
        info2.setCustomerContact("18012345678");
        info2.setCustomerAddr("1-1055");
        info2.setJzqCno("040000000000000000000999999999");
        info2.setIsOnline("1");
        info2.setGroupGuid("e11ed906-de3e-11e7-a53d-902b34df9a12");

        dataList.add(info1);
        dataList.add(info2);
        result.setData(dataList);
        result.setTotal(2L);

        System.out.println(JSON.toJSONString(result));

    }

    public static void popupDownload() {
        PopupDownloadQueryParam param = new PopupDownloadQueryParam();
        param.setGuid("e11ed906-de3e-11e7-a53d-902b34df9a12");
        param.setDeviceType("07");
        param.setSearchDate("2017-11-15 14:24:35");
        param.setFlag(1);

        System.out.println(JSON.toJSONString(param));
    }

    public static void readCollectRecordReturn () {
        Result<List<CollectRecordDTO>> result = new Result<>();
        List<CollectRecordDTO> dataList = Lists.newArrayList();
        CollectRecordDTO dto1 = new CollectRecordDTO();
        dto1.setCollectDate("2017-12-12");
        dto1.setCustomerCount(200);
        dto1.setSuccessCount(100);
        dto1.setFailCount(100);
        dto1.setUpdateTime(new Date());

        CollectRecordDTO dto2 = new CollectRecordDTO();
        dto2.setCollectDate("2017-12-12");
        dto2.setCustomerCount(300);
        dto2.setSuccessCount(200);
        dto2.setFailCount(100);
        dto2.setUpdateTime(new Date());


        dataList.add(dto1);
        dataList.add(dto2);
        result.setData(dataList);

        System.out.println(JSON.toJSONString(result));
    }

    public static void readCollectRecordDetialParam() {
        CollectRecordDetialQueryParam param = new CollectRecordDetialQueryParam();
        param.setDate("2017-11-12");
        param.setDeviceType("07");
        param.setFlag(1);
        param.setPageNumber(1);
        param.setPageSize(20);

        System.out.println(JSON.toJSONString(param));
    }

    public static void readCollectRecordDetialReturn() {
        PageResult<List<CollectDetialDTO>> result = new PageResult<>();
        List<CollectDetialDTO> dataList = Lists.newArrayList();
        CollectDetialDTO dto = new CollectDetialDTO();
        dto.setId("11");
        dto.setCno("070000000000000000000000123456");
        dto.setDeviceNo("123456");
        dto.setCustomerNo("dfgfdgdfgfdgdsfsdfsdfsd");
        dto.setCustomerName("zhangsan");
        dto.setCollectDate("2017-11-15");
        dto.setOrgName("成都博高");
      //  dto.setPayCount(12);
        dto.setCollectNo("werewr");

        CollectDetialDTO dto2 = new CollectDetialDTO();
        dto2.setId("11");
        dto2.setCno("070000000000000000000000123456");
        dto2.setDeviceNo("123456");
        dto2.setCustomerNo("dfgfdgdfgfdgdsfsdfsdfsd");
        dto2.setCustomerName("zhangsan");
        dto2.setCollectDate("2017-11-15");
        dto2.setOrgName("成都博高");
        //dto2.setPayCount(12);
        dto2.setCollectNo("werewr");

        dataList.add(dto);
        dataList.add(dto2);
        result.setData(dataList);
        result.setTotal(100L);

        System.out.println(JSON.toJSONString(result));

    }

    public static void distributionStatisticsParam() {
        FreeDayCollectGetQueryParam param = new FreeDayCollectGetQueryParam();
        param.setDeviceType("07");
        param.setUpdateTime("2017-11-11");
        param.setPageNumber(1);
        param.setPageSize(20);

        System.out.println(JSON.toJSONString(param));
    }
    public static void distributionStatisticsReturn() {
        PageResult<List<CollectNoStatisticsDTO>> result = new PageResult<>();
        List<CollectNoStatisticsDTO> dataList = Lists.newArrayList();
        CollectNoStatisticsDTO dto1 = new CollectNoStatisticsDTO();
        dto1.setSuccessCount(100);
        dto1.setFailCount(50);
        dto1.setCustomerCount(150);
        dto1.setUpdateTime("2017-11-11");
        dto1.setCollectNo("dggsdfsdfsda");
        dto1.setInstallAddr("sdfsdfsd");

        CollectNoStatisticsDTO dto2 = new CollectNoStatisticsDTO();
        dto2.setSuccessCount(100);
        dto2.setFailCount(50);
        dto2.setCustomerCount(150);
        dto2.setUpdateTime("2017-11-11");
        dto2.setCollectNo("dggsdfsdfsda");
        dto2.setInstallAddr("sdfsdfsd");

        dataList.add(dto1);
        dataList.add(dto2);

        result.setData(dataList);
        result.setTotal(100L);

        System.out.println(JSON.toJSONString(result));
    }
}
