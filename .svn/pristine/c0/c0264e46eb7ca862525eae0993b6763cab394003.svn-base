package cn.com.cdboost.collect.util;

import com.google.common.collect.Maps;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author wt
 * @desc
 * @create in  2018/5/4
 **/
public class DateUtil {
    // 获取当前系统时间
    public static String CurrentDate() {
        Date d = new Date();
        SimpleDateFormat sdate = new SimpleDateFormat("yyyy-MM-dd");
        return sdate.format(d);
    }
    /**
     * 获取今天开始时间
     * @return 假设当天为28号，返回“2016-12-28 23:59:59”对应的日期
     */
    public static final Date getCurrentStartTimeDay(){
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 0);
        todayEnd.set(Calendar.MINUTE, 0);
        todayEnd.set(Calendar.SECOND, 0);
        return todayEnd.getTime();
    }
    public static final Date getEndTimeOfCurrentDay(){
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        return todayEnd.getTime();
    }
    public static String pareseToken() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(new Date());
    }

    public static String parese() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(paresedataFlag("3","2018-7-1","2019-8-2"));
    }
    public static Map paresedataFlag(String dataFlag, String startDate ,String endDate) throws ParseException {
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map map= Maps.newHashMap();
        if ("1".equals(dataFlag)) {
            String startDateSub = startDate+" 00:00:00";
            String endDateSub = endDate+" 23:59:59";
            map.put("startDateSub",startDateSub);
            map.put("endDateSub",endDateSub);
            return map;
        }
        if ("2".equals(dataFlag)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            Calendar calendar = Calendar.getInstance();
            Date start= simpleDateFormat.parse(startDate);
            calendar.setTime(start);
            Date time = calendar.getTime();
            String dateformatStart= simpleDateFormat1.format(time);
            Date end= simpleDateFormat.parse(endDate);
            calendar.setTime(end);
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.SECOND,-1);
            time = calendar.getTime();
            String dateformatEnd= simpleDateFormat1.format(time);
            map.put("startDateSub",dateformatStart);
            map.put("endDateSub",dateformatEnd);
            return map;
        }
        if ("3".equals(dataFlag)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
            Calendar calendar = Calendar.getInstance();
            Date start= simpleDateFormat.parse(startDate);
            calendar.setTime(start);
            Date time = calendar.getTime();
            String dateformatStart= simpleDateFormat1.format(time);
            Date end= simpleDateFormat.parse(endDate);
            calendar.setTime(end);
            calendar.add(Calendar.YEAR, 1);
            calendar.add(Calendar.SECOND,-1);
            time = calendar.getTime();
            String dateformatEnd= simpleDateFormat1.format(time);
            map.put("startDateSub",dateformatStart);
            map.put("endDateSub",dateformatEnd);
            return map;
        }
        map.put("startDateSub",startDate);
        map.put("endDateSub",endDate);
        return map;
    }

}
