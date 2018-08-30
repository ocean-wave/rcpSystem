package cn.com.cdboost.collect.util;


import cn.com.cdboost.collect.enums.ResultCode;
import cn.com.cdboost.collect.exception.ParameterException;
import com.google.common.base.Preconditions;

import java.math.BigDecimal;

/**
 * Created by zc
 */
public class PreconditionsUtil {
    public static void checkArgument(int errorCode, boolean expression,  String errorMessageTemplate) {
        try {
            Preconditions.checkArgument(expression, errorMessageTemplate);
        } catch (Exception e) {
            throw new ParameterException(errorCode, e.getMessage());
        }
    }
    public static void checkNotNull(int errorCode, Object obj,  String errorMessageTemplate) {
        try {
            Preconditions.checkNotNull(obj, errorMessageTemplate);
        } catch (Exception e) {
            throw new ParameterException(errorCode, e.getMessage());
        }
    }

    public static void checkMoney(BigDecimal money) {
        if((money.compareTo(BigDecimal.valueOf(100000)) == 1) || (money.compareTo(BigDecimal.valueOf(0)) == -1)){
            throw new ParameterException(ResultCode.MONEY_ERROR.getValue(), ResultCode.MONEY_ERROR.getDesc());
        }
    }

}
