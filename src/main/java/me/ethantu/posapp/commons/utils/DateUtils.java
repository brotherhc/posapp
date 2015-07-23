package me.ethantu.posapp.commons.utils;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * User: EthanTu <ethan.l.tu@gmail.com>
 * Date: 14-4-3
 * Time: AM10:50
 */
public class DateUtils {


    static private final String yyyy_MM_dd_HH_mm_ss = "yyyyMMddHHmmss";

    static private final String hh_mm_ss = "hhmmss";


    /**
     * date yyyy_MM_dd_HH_mm_ss
     *
     * @return
     */
    public static String getDate() {
        return currentDateFormatFor(yyyy_MM_dd_HH_mm_ss);
    }


    /**
     * timestamp hh_mm_ss
     * @return
     */
    public static String getTimestamp() {
        return currentDateFormatFor(hh_mm_ss);
    }

    public static String currentDateFormatFor(String pattern) {
        return dateFormatFor(new Date(), pattern);
    }

    public static String dateFormatFor(Date date, String pattern) {
        FastDateFormat fastDateFormat = FastDateFormat.getInstance(pattern);
        return fastDateFormat.format(date);
    }

    public static Date dateFormatParse(String date, String pattern) throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(pattern);
        return (Date)simpleDateFormat.parseObject(date);
    }

    public static Date getDateByDayEarly(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        return c.getTime();
    }

    public static Date getDateByDayLastly(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);

        return c.getTime();
    }

    public static void main(String[] args) {
        System.out.println(Calendar.getInstance(TimeZone.getTimeZone("GMT+8")).get(Calendar.HOUR_OF_DAY));
    }
}
