package com.zhilink.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期时间获取格式化
 *
 * @author xiemeng
 * @date 2018-8-20 11:33
 */
@SuppressLint("SimpleDateFormat")
public class DateUtils {


    /**
     * 获取当前时间
     *
     * @return 获取当前时间
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        LogUtils.i("",df.format(calendar.getTime()));
        return df.format(calendar.getTime());
    }

    /**
     * 获取当前时间
     */
    public static String getDateAndTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 获取当前时间
     */
    public static String getDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }
    /**
     * 获取当前时间
     */
    public static String getDateString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }
    /**
     * 获取当前时间
     */
    public static String getTime() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 获取时间
     *
     * @return
     */
    public static String formatDateTime(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = null;
        try {
            parse = formatter.parse(date);
            return formatter.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    /**
     * 获取时间
     *
     * @return
     */
    public static String formatDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = null;
        try {
            parse = formatter.parse(date);
            return formatter.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    /**
     * 时间转换为时间戳
     *
     * @param time:需要转换的时间
     * @return
     */
    public static String dateTimeToStamp(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        return String.valueOf(ts);
    }

    /**
     * 获取距现在某一小时的时刻
     *
     * @param hour hour=-1为上一个小时，hour=1为下一个小时
     * @return
     */
    public static String getLongTime(int hour) {
        Calendar c = Calendar.getInstance(); // 当时的日期和时间
        int h; // 需要更改的小时
        h = c.get(Calendar.HOUR_OF_DAY) - hour;
        c.set(Calendar.HOUR_OF_DAY, h);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log.v("time", df.format(c.getTime()));
        return df.format(c.getTime());
    }

    /**
     * 比较时间大小
     *
     * @param str1：要比较的时间
     * @param str2：要比较的时间
     * @return
     */
    public static boolean isDateOneBigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);

            if (dt1.getTime() > dt2.getTime()) {
                isBigger = true;
            } else if (dt1.getTime() < dt2.getTime()) {
                isBigger = false;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isBigger;
    }

    /**
     * 当地时间 ---> UTC时间
     *
     * @return
     */
    public static String local2UTC() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("gmt"));
        return sdf.format(new Date());
    }

    /**
     * UTC时间 ---> 当地时间
     *
     * @param utcTime UTC时间
     * @return
     */
    public static String utc2Local(String utcTime) {
        //UTC时间格式
        SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormat.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //当地时间格式
        SimpleDateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        localFormat.setTimeZone(TimeZone.getDefault());
        return localFormat.format(gpsUTCDate.getTime());

    }
}