package cn.com.cdboost.collect.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;

public class SelfDateAnnValidator implements ConstraintValidator<MyDate, String> {
    private MyDate mydt;


    @Override
    public void initialize(MyDate mydt) {
        this.mydt = mydt;
        //取得类属性的注解送入

    }

    /**
     * Date dt,是当前动态录入参数
     */
    @Override
    public boolean isValid(String dt, ConstraintValidatorContext ctx) {
        String pattern = mydt.pattern();
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        boolean isOK = true;
        try {
            df.parse(dt);
        } catch (Exception e) {
            isOK=false;
           /* ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate("测试").addConstraintViolation();*/
            e.printStackTrace();
        }
        return isOK;
    }


}