package com.zhilink.app_middle.base;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.umeng.analytics.MobclickAgent;

import com.zhilink.app_middle.R;
import com.zhilink.base.BaseActivity;
import com.zhilink.app_middle.net.ApiMethods;

/**
 * 含toolbar
 * 可能封装基础业务
 * 所有activity必须继承此类
 *
 * @author xiemeng
 * @date 2018-8-27 13:58
 */

public abstract class BaseTitleActivity extends BaseActivity {

    private LinearLayout parentLinearLayout;

    protected ImageView mBack;

    protected TextView mTitle;
    /**
     * 管理rxJava生命周期，防止内存泄漏
     */
    public LifecycleProvider<Lifecycle.Event> lifecycleProvider;

    @Override
    protected void onResume() {
        super.onResume();
        //统计页面
        MobclickAgent.onPageStart(TAG + getToolbarTextString());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG + getToolbarTextString());
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void beforeCreateView() {
        lifecycleProvider = AndroidLifecycle.createLifecycleProvider(this);
        ApiMethods.setLifecycleProvider(lifecycleProvider);
        if (isUseToolBar()) {
            initToolbar(R.layout.toolbar);
        }
    }

    /**
     * 加入toolbar布局
     *
     * @param layoutResID 布局文件
     */
    private void initToolbar(@LayoutRes int layoutResID) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        parentLinearLayout = new LinearLayout(this);
        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        viewGroup.addView(parentLinearLayout);
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
    }

    /**
     * @param layoutResID the layout id of sub Activity
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if (isUseToolBar()) {
            LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
            mTitle = findViewById(R.id.toolbar_title);
            mBack = findViewById(R.id.back);
            setBackIcon();
        } else {
            super.setContentView(layoutResID);
        }
    }


    /**
     * 返回
     */
    private void setBackIcon() {
        if (null != mBack && isShowBacking()) {
            mBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }

    /**
     * set Title
     *
     * @param title 标题
     */
    public void setToolBarTitle(CharSequence title) {
        if (mTitle != null) {
            mTitle.setText(title);
        }
    }

    /**
     * get Title
     */
    public String getToolbarTextString() {
        if (mTitle != null) {
            return mTitle.getText().toString();
        } else {
            return TAG;
        }
    }

    /**
     * the toolbar of this Activity
     */
    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    /**
     * @return 是否显示返回
     */
    protected boolean isShowBacking() {
        return true;
    }

    /**
     * @return 是否使用父类toolbar，false则重写
     */
    protected boolean isUseToolBar() {
        return true;
    }
}


