package com.zhilink.lib_router;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 路由跳转简单封装
 * 均无拦截器
 *
 * @author xiemeng
 * @date 2018-9-12 17:33
 */

public class ARouterHelper {

    public static void startActivity(String path) {
        ARouter.getInstance().
                build(path)
                .navigation();
    }

    public static void startActivityBundle(String path, Bundle params) {
        ARouter.getInstance()
                .build(path)
                .with(params)
                .navigation();
    }

    public static void startActivityResult(Activity activity, String path, int reqCode) {
        ARouter.getInstance()
                .build(path)
                .navigation(activity, reqCode);
    }

    public static void startActivityBundleResult(Activity activity, String path, Bundle params, int reqCode) {
        ARouter.getInstance()
                .build(path)
                .with(params)
                .navigation(activity, reqCode);
    }

    public static Fragment getFragment(String path) {
        return (Fragment) ARouter.getInstance()
                .build(path)
                .navigation();
    }
}
