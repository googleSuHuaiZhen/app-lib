package com.zhilink.utils;

import android.app.Activity;

import java.lang.ref.WeakReference;

/**
 * 设置获取当前activity
 *
 * @author xiemeng
 * @date 2018-11-13 17:01
 */
public class CurrentActivityUtils {

    private static CurrentActivityUtils sInstance = new CurrentActivityUtils();

    private WeakReference<Activity> sCurrentActivityWeakRef;


    private CurrentActivityUtils() {

    }

    public static CurrentActivityUtils getInstance() {
        return sInstance;
    }


    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (sCurrentActivityWeakRef != null) {
            currentActivity = sCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        sCurrentActivityWeakRef = new WeakReference<>(activity);
    }

}
