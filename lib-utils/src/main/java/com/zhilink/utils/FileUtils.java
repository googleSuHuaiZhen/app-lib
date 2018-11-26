package com.zhilink.utils;


import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * copy from https://blog.csdn.net/youmingyu/article/details/52583409
 *
 * @author xiemeng
 * @date 2018-11-23 14:24
 */
public class FileUtils {

    private String sdCardRoot;

    /**
     * 得到当前外部存储设备的目录
     */
    public FileUtils() {
        //File.separator为文件分隔符”/“,方便之后在目录下创建文件
        sdCardRoot = Environment.getExternalStorageDirectory() + File.separator;
    }

    /**
     * 在SD卡上创建文件
     */
    public File createFileInSDCard(String fileName, String dir) throws IOException {
        File file = new File(sdCardRoot + dir + File.separator + fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 只能创建一层文件目录，mkdirs()可以创建多层文件目录
     */
    public File createSDDir(String dir) throws IOException {
        File dirFile = new File(sdCardRoot + dir);
        dirFile.mkdir();
        return dirFile;
    }

    /**
     * 只能创建一层文件目录，mkdirs()可以创建多层文件目录
     */
    public File createSDDir2(String dir) throws IOException {
        File dirFile = new File(dir);
        dirFile.mkdirs();
        return dirFile;
    }

    /**
     * 判断文件是否存在
     */
    public boolean isFileExist(String fileName, String dir) {
        File file = new File(sdCardRoot + dir + File.separator + fileName);
        return file.exists();
    }

    /**
     * 将一个InoutStream里面的数据写入到SD卡中
     */
    public File write2SDFromInput(String fileName, String dir, InputStream input) {
        File file = null;
        OutputStream output = null;
        try {
            //创建目录
            createSDDir(dir);
            //创建文件
            file = createFileInSDCard(fileName, dir);
            //写数据流
            output = new FileOutputStream(file);
            //每次存4K
            byte buffer[] = new byte[4 * 1024];
            int temp;
            //写入数据
            while ((temp = input.read(buffer)) != -1) {
                output.write(buffer, 0, temp);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return file;
    }

}

