package com.zhilink.app_middle.base;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import com.zhilink.lib_crash_exception.CrashHandler;
import com.zhilink.utils.TelephonyUtils;

/**
 * 定义BaseApplication集成友盟统计等其他工具
 *
 * @author xiemeng
 * @date 2018-8-29 14:59
 */

public class BaseApplication extends Application {
    private static BaseApplication applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;

        uM();
//        crashException();
    }

    /**
     * 友盟统计
     */
    private void uM() {
        MobclickAgent.setScenarioType(applicationContext, MobclickAgent.EScenarioType.E_UM_NORMAL);
        int deviceType = UMConfigure.DEVICE_TYPE_PHONE;
        if (TelephonyUtils.isTabletDevice(applicationContext)) {
            deviceType = UMConfigure.DEVICE_TYPE_BOX;
        }
        //清单文件配置AppKey和渠道，最后一个为推送，暂不集成
        UMConfigure.init(applicationContext, deviceType, "");
    }

    /**
     * 全局异常捕获
     * 太坑了不建议使用
     */
    private void crashException() {
        final CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext(), new CrashHandler.CrashExceptionListener() {
            @Override
            public void crashException(Throwable ex) {
                MobclickAgent.reportError(applicationContext, ex);

            }
        });
    }

}
