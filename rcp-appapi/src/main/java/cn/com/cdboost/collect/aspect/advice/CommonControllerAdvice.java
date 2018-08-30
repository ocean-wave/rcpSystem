package cn.com.cdboost.collect.aspect.advice;

import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.exception.DataBaseException;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * controller 统一异常处理
 */
@ControllerAdvice
public class CommonControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(CommonControllerAdvice.class);
    /**
     * 全局异常处理
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String errorHandler(Exception ex) {
        logger.error("系统异常：",ex);
        Result result = new Result();
        result.error("系统异常");
        return JSON.toJSONString(result);
    }

    /**
     * 登录时账号密码错误异常处理
     * @param uae
     * @return
     */
    @ExceptionHandler(value = UnknownAccountException.class)
    @ResponseBody
    public String unknownAccountErrorHandler(UnknownAccountException uae) {
        logger.error("账号或密码错误异常：",uae);
        Result result = new Result();
        result.error("账号或密码错误");
        return JSON.toJSONString(result);
    }

    /**
     * 数据校验异常处理
     * @param be
     * @param bindingResult
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public String validateErrorHandler(BindException be, BindingResult bindingResult) {
        logger.error("参数校验异常：",be);
        Result result = new Result();
        StringBuffer sb = new StringBuffer();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage()).append("|");
            }
            sb.deleteCharAt(sb.lastIndexOf("|"));
            result.error(sb.toString());
        } else {
            result.error("参数校验异常:" + be.getMessage());
        }

        return JSON.toJSONString(result);
    }

    /**
     * 方法参数检验异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public String validateErrorHandler(MethodArgumentNotValidException e) {
        logger.error("参数校验异常：",e);
        Result result = new Result();
        StringBuffer sb = new StringBuffer();
        BindingResult bindingResult = e.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        sb.append(fieldError.getDefaultMessage());
        result.error(sb.toString());

        return JSON.toJSONString(result);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public String validateErrorHandler(MissingServletRequestParameterException e) {
        logger.error("参数校验异常：",e);
        Result result = new Result();
        StringBuffer sb = new StringBuffer();
        sb.append("缺少参数:" + e.getParameterName());
        sb.append(",");
        sb.append("参数类型:" + e.getParameterType());
        result.error(sb.toString());

        return JSON.toJSONString(result);
    }

    /**
     * 自定义业务异常处理
     * @param be
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public String businessErrorHandler(BusinessException be) {
        logger.error("业务异常：",be);
        Result result = new Result();
        result.error(be.getErrorCode(),be.getErrorMessage());
        return JSON.toJSONString(result);
    }
    /**
     * 数据库异常处理
     * @param be
     * @return
     */
    @ExceptionHandler(value = DataBaseException.class)
    @ResponseBody
    public String DataBaseException(DataBaseException be) {
        logger.error("数据库异常：",be);
        Result result = new Result();
        result.error(be.getMessage());
        return JSON.toJSONString(result);
    }
}
