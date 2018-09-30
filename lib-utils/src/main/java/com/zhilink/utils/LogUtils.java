package com.zhilink.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 带日志文件输入的，又可控开关的日志调试
 *
 * @author BaoHang
 * @version 1.0
 * @data 2012-2-20
 */
public class LogUtils {
    /**
     * 输入日志类型，w代表只输出告警信息等，v代表输出所有信息
     */
    public static char logType = 'v';
    /**
     * 日志文件总开关
     */
    public static Boolean LOG_SWITCH = true;
    /**
     * 日志写入文件开关
     */
    public static Boolean LOG_WRITE_TO_FILE = true;
    /**
     * 日志文件在sdcard中的路径
     */
    public static String LOG_PATH_SDCARD_DIR = "/sdcard/";
    /**
     * sd卡中日志文件的最多保存天数
     */
    public static int SDCARD_LOG_FILE_SAVE_DAYS = 10;
    /**
     * 本类输出的日志文件名称
     */
    public static String LOG_FILE_NAME = "TestLog.txt";
    /**
     * 日志的输出格式
     */
    public static SimpleDateFormat LOG_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 日志文件格式
     */
    public static SimpleDateFormat LOG_FILE;


    private static FileWriter mFileWriter = null;

    public static void w(String tag, Object msg) { // 警告信息
        log(tag, msg.toString(), logTypeW);
    }

    public static void e(String tag, Object msg) { // 错误信息
        log(tag, msg.toString(), logTypeE);
    }

    public static void d(String tag, Object msg) {// 调试信息
        log(tag, msg.toString(), logTypeD);
    }

    public static void i(String tag, Object msg) {//
        log(tag, msg.toString(), logTypeI);
    }

    public static void v(String tag, Object msg) {
        log(tag, msg.toString(), logTypeV);
    }

    public static void w(String tag, String text) {
        log(tag, text, logTypeW);
    }

    public static void e(String tag, String text) {
        log(tag, text, logTypeE);
    }

    public static void d(String tag, String text) {
        log(tag, text, logTypeD);
    }

    public static void i(String tag, String text) {
        log(tag, text, logTypeI);
    }

    public static void v(String tag, String text) {
        log(tag, text, logTypeV);
    }

    private static char logTypeV = 'v';
    private static char logTypeE = 'e';
    private static char logTypeW = 'w';
    private static char logTypeD = 'd';
    private static char logTypeI = 'i';

    /**
     * 打印日志
     *
     * @param tag   tag
     * @param msg   信息
     * @param level 级别
     */
    private static void log(String tag, String msg, char level) {
        if (LOG_SWITCH) {
            // 输出错误信息
            if (logTypeE == level && (logTypeE == logType || logTypeV == logType)) {
                Log.e(tag, msg);
            } else if (logTypeW == level && (logTypeW == logType || logTypeV == logType)) {
                Log.w(tag, msg);
            } else if (logTypeD == level && (logTypeD == logType || logTypeV == logType)) {
                Log.d(tag, msg);
            } else if (logTypeI == level && (logTypeD == logType || logTypeV == logType)) {
                Log.i(tag, msg);
            } else {
                Log.v(tag, msg);
            }
            if (!LOG_WRITE_TO_FILE) {
                writeLogToFile(String.valueOf(level), tag, msg);
            }
        }
    }

    /**
     * 打开日志文件并写入日志
     **/
    @SuppressLint("SimpleDateFormat")
    private static void writeLogToFile(String logType, String tag, String text) {
        try {
            Date nowTime = new Date();
            LOG_FILE = new SimpleDateFormat("yyyy-MM-dd");
            String needWriteFile = LOG_FILE.format(nowTime);
            String needWriteMessage = LOG_SDF.format(nowTime) + "    " + logType + "    " + tag + "    " + text;

            File file = new File(LOG_PATH_SDCARD_DIR, needWriteFile + LOG_FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (mFileWriter == null) {
                // 后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
                mFileWriter = new FileWriter(file, true);
            }
            BufferedWriter bufWriter = new BufferedWriter(mFileWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            mFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 删除制定的日志文件
     */
    public static void delFile() {
        String needDelFile = LOG_FILE.format(getDateBefore());
        File file = new File(LOG_PATH_SDCARD_DIR, needDelFile + LOG_FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 得到现在时间前的几天日期，用来得到需要删除的日志文件名
     */
    private static Date getDateBefore() {
        Date nowTime = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(nowTime);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - SDCARD_LOG_FILE_SAVE_DAYS);
        return now.getTime();
    }

}
