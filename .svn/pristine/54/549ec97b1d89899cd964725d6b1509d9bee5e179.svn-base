package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.ChargingICCardDto;
import cn.com.cdboost.collect.dto.CustomerBatchImportInfo;
import cn.com.cdboost.collect.dto.param.ChargerICCardAddParam;
import cn.com.cdboost.collect.dto.param.ChargerICCardEditParam;
import cn.com.cdboost.collect.dto.param.ChargerICCardQueryVo;
import cn.com.cdboost.collect.dto.param.OffOnCardParam;
import cn.com.cdboost.collect.model.ChargingCard;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 充电桩ic卡
 */
public interface ChargingCardService extends BaseService<ChargingCard> {
    List<ChargingICCardDto> queryList(ChargerICCardQueryVo queryVo);

    void addCard(ChargerICCardAddParam param, Integer id);

    void editCard(ChargerICCardEditParam param, Integer id);

    void delete(List<String> cardIds, Integer id);

    void offOnCard(OffOnCardParam param, Integer id);

    CustomerBatchImportInfo excelImport(HttpSession session, String saveName, String uuid);

    // 根据IC卡卡号查询
    ChargingCard queryByCardId(String cardId);

    // 根据IC卡卡号查询,加悲观锁
    ChargingCard queryByCardIdForUpdate(String cardId);

    // 根据绑定用户customerGuid查询
    List<ChargingCard> queryByCustomerGuid(String customerGuid);
}
