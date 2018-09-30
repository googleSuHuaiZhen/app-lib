package com.zhilink.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 系统，网络工具
 *
 * @author xiemeng
 * @date 2018-8-20
 */
public class TelephonyUtils {
    private static final String TAG = "TelephonyUtils";

    /**
     * 获取软件信息
     */
    public static String getSoftwareInfo() {
        String softwareVersion = android.os.Build.VERSION.RELEASE;
        LogUtils.i(softwareVersion, "softwareVersion :" + softwareVersion);
        return softwareVersion;
    }

    /**
     * 获取硬件信息
     */
    public static String getHardwareInfo() {
        String hardwareInfo = android.os.Build.MODEL;
        LogUtils.i(TAG, "hardwareInfo : " + hardwareInfo);
        return hardwareInfo;
    }

    /**
     * 获取应用版本
     */
    public static double getMAppVersion(Context context) {
        double versionName = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionName
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = Double.valueOf(packageInfo.versionName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;

    }

    /**
     * 获取应用版本
     */
    public static String getVersion(Context context) {
        String versionName = "";
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;

    }
    /**
     * 判断是否平板设备
     * @param context context
     * @return true:平板,false:手机
     */
    public static boolean isTabletDevice(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    /**
     * 判断是否有网络连接
     *
     * @param context context
     * @return true有网络
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断当前网络是否是wifi网络
     * if(activeNetInfo.getType()==ConnectivityManager.TYPE_MOBILE) { //判断3G网
     *
     * @return boolean
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 判断当前网络是否是wifi网络
     * if(activeNetInfo.getType()==ConnectivityManager.TYPE_MOBILE) { //判断3G网
     *
     * @return boolean
     */
    public static boolean isMobileNet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前网络连接的类型信息
     *
     * @param context 网络类型
     * @return int
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }


    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * * @param context
     */
    @SuppressLint("SdCardPath")
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/databases"));
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
     * context
     */
    @SuppressLint("SdCardPath")
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    /**
     * 删除指定目录下文件及目录
     */
    public void deleteFolderFile(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    //处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath());
                    }
                }

                if (!file.isDirectory()) {
                    // 如果是文件，删除
                    file.delete();
                } else {// 目录
                    if (file.listFiles().length == 0) {
                        // 目录下没有文件或者目录，删除
                        file.delete();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取唯一设备号
     */
    public static String getDeviceId(Context aty) {
        StringBuilder sb = new StringBuilder();
        sb.append(getIME(aty));

        String wifi = getWifi(aty);
        sb.append(wifi);

        String androidId = getAndroidId(aty);
        sb.append(androidId);
        return sb.toString();
    }

    /**
     * 获取设备号
     */
    @SuppressLint("HardwareIds")
    private static String getIME(final Context context) {
        String szIme = "unknown";
        try {
            final TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= 23) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
                    }
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                szIme = manager.getImei();
            } else {
                szIme = manager.getDeviceId();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "getDeviceId异常" + e);
        }
        return szIme;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        LogUtils.i(TAG, "getSystemModel" + android.os.Build.MODEL);
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    @SuppressLint("HardwareIds")
    private static String getWifi(Context context) {
        String mac = "";
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String wifiMac = info.getMacAddress();
        if (!StringUtils.isBlank(wifiMac)) {
            mac = wifiMac;
            return mac;
        }
        LogUtils.i(TAG, "getWifi : " + mac);
        return mac;
    }

    /**
     * 获取网络ip地址
     */
    public static String getWifiIP(Context context) {
        String ip = "";
        NetworkInfo info = ((ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface networkInterface = en.nextElement();
                        for (Enumeration<InetAddress> iNetAddresses = networkInterface.getInetAddresses(); iNetAddresses.hasMoreElements(); ) {
                            InetAddress inetAddress = iNetAddresses.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                //当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                //得到IPV4地址
                return intIP2StringIP(wifiInfo.getIpAddress());
            }
        } else {
            //当前无网络连接,请在设置中打开网络
            Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
            context.startActivity(intent);
        }
        return ip;
    }

    private static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    @SuppressLint("HardwareIds")
    private static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    /**
     * 判断当前应用是否是debug状态
     */
    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }


}
