package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.dto.KeyCollectDTO;
import cn.com.cdboost.collect.dto.KeyCollectionAnalysisDTO;
import cn.com.cdboost.collect.dto.param.KeyCollectVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.List;

/**
 * Created by 110 on 2018/4/16.
 */
public class keyServiceTest extends BaseServiceReadTest{
    @Autowired
    private KeyCollectionService keyCollectionService;

    @Test
    public void query() throws ParseException {
        KeyCollectVo vo = new KeyCollectVo();
        vo.setPageNumber(1);
        vo.setPageSize(10);
        vo.setQueueGuid("02ad1612-d650-4dbe-a486-de1c06799f7e");

        List<KeyCollectDTO> list = keyCollectionService.queryKeyCollectSucc(vo);
        System.out.println("++++++++++++++++"+list.size());
    }



}
