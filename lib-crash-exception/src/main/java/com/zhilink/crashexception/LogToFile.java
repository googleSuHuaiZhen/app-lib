package com.zhilink.crashexception;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 异常信息记录
 *
 * @author xiemeng
 * @date 2018-8-29 16:53
 */
public class LogToFile {
    /**
     * 日志是否需要读写开关
     */
    public static final boolean WRITE_FILE = true;

    private static String TAG = "LogToFile";
    /**
     * log日志存放路径
     */
    private static String logPath = null;
    /**
     * 日期格式
     */
    private String dateFormatString = "yyyy-MM-dd HH:mm:ss";
    /**
     * 因为log日志是使用日期命名的，使用静态成员变量主要是为了在整个程序运行期间只存在一个.log文件中
     */
    private static Date date = new Date();

    /**
     * 初始化，须在使用之前设置，最好在Application创建时调用
     * 获得文件储存路径,在后面加"/Logs"建立子文件夹
     */
    public static void init(Context context) {
        logPath = getFilePath(context) + "/Logs";
    }

    /**
     * 获得文件存储路径
     */
    private static String getFilePath(Context context) {

        if (Environment.MEDIA_MOUNTED.equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()
                && null != context.getExternalFilesDir(null)) {
            //如果外部储存可用
            return context.getExternalFilesDir(null).getPath();
        } else {
            //直接存在/data/data里，非root手机是看不到的
            return context.getFilesDir().getPath();
        }
    }

    private static final char VERBOSE = 'v';

    private static final char DEBUG = 'd';

    private static final char INFO = 'i';

    private static final char WARN = 'w';

    private static final char ERROR = 'e';

    public static void v(String tag, String msg) {
        if (WRITE_FILE) {
            writeToFile(VERBOSE, tag, msg);
        }

    }

    public static void d(String tag, String msg) {
        if (WRITE_FILE) {
            writeToFile(DEBUG, tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (WRITE_FILE) {
            writeToFile(INFO, tag, msg);
        }

    }

    /**
     * 保存错误报告文件
     */
    public static void w(String tag, String msg) {
        if (WRITE_FILE) {
            writeToFile(WARN, tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (WRITE_FILE) {
            writeToFile(ERROR, tag, msg);
        }

    }

    /**
     * 将log信息写入文件中
     */
    @SuppressLint("SimpleDateFormat")
    private static void writeToFile(char type, String tag, String msg) {

        if (null == logPath) {
            Log.e(TAG, "logPath == null ，未初始化LogToFile");
            return;
        }
        //log日志名，使用时间命名，保证不重复
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String fileName = logPath + "/log_" +  df.format(calendar.getTime()) + ".log";
        //log日志内容，可以自行定制
        String log = df.format(calendar.getTime()) + " " + type + " " + tag + " " + msg + "\n";
        //如果父路径不存在
        File file = new File(logPath);
        if (!file.exists()) {
            //创建父路径
            file.mkdirs();
        }
        //FileOutputStream会自动调用底层的close()方法，不用关闭
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            //这里的第二个参数代表追加还是覆盖，true为追加，false为覆盖
            fos = new FileOutputStream(fileName, true);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(log);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}