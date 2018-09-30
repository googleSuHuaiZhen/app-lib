package com.zhilink.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.zhilink.activitymanager.ActivityManagerUtils;

import butterknife.ButterKnife;

/**
 * BaseActivity
 * ButterKnife绑定
 * 将activity添加到ActivityManagerUtils中
 * Tag初始化
 * 显示启动其他Activity
 * 默认竖屏显示
 * 默认5.1沉浸状态栏
 *
 * @author xiemeng
 * @date 2018-8-21
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context context;

    protected Activity activity;

    protected final String TAG = getClass().getSimpleName();

    /**
     * 是否允许旋转屏幕
     **/
    private boolean isAllowScreenRotate = false;
    /**
     * 是否沉浸状态栏
     **/
    private boolean isSetStatusBar = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        activity = this;
        beforeCreateView();

        setContentView(bindView());
        if (!isAllowScreenRotate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        if (isSetStatusBar) {
            steepStatusBar();
        }
        ButterKnife.bind(this);
        ActivityManagerUtils.addActivity(activity);
        doBusiness();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManagerUtils.removeActivity(activity);
    }


    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

        }
    }

    public void setAllowScreenRotate(boolean isAllowScreenRotate) {
        this.isAllowScreenRotate = isAllowScreenRotate;
    }

    public void setSetStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }

    /**
     * 布局文件
     *
     * @return 布局
     */
    public abstract int bindView();


    protected void beforeCreateView() {
    }

    /**
     * 显示完界面后处理的事件
     */
    protected abstract void doBusiness();

    public void startActivity(Class<?> startActivity) {
        ActivityManagerUtils.startActivity(activity, startActivity);
    }

    public void startActivityBundle(Class<?> startActivity, Bundle bundle) {
        ActivityManagerUtils.startActivityBundle(activity, startActivity, bundle);
    }

    public void startActivityResult(Class<?> startActivity, int requestCode) {
        ActivityManagerUtils.startActivityResult(activity, startActivity, requestCode);
    }

    public void startActivityBundleResult(Class<?> startActivity, Bundle bundle, int requestCode) {
        ActivityManagerUtils.startActivityBundleResult(activity, startActivity, bundle, requestCode);
    }

    public void showLoadingDialog() {

    }

    public void dismissLoadingDialog() {

    }

}
