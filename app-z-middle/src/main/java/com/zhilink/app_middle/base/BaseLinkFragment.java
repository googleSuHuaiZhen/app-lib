package com.zhilink.app_middle.base;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;

import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.umeng.analytics.MobclickAgent;

import com.zhilink.base.BaseFragment;
import com.zhilink.app_middle.net.ApiMethods;

/**
 * 进一步封装fragment
 * 集成友盟统计
 *
 * @author xiemeng
 * @date 2018-8-29 15:55
 */

public abstract class BaseLinkFragment extends BaseFragment {
    /**
     * 管理rxJava生命周期，防止内存泄漏
     */
    public LifecycleProvider<Lifecycle.Event> lifecycleProvider;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        lifecycleProvider = AndroidLifecycle.createLifecycleProvider(this);
        ApiMethods.setLifecycleProvider(lifecycleProvider);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (activity instanceof BaseTitleActivity) {
            BaseTitleActivity titleActivity = (BaseTitleActivity) activity;
            MobclickAgent.onPageStart(TAG + titleActivity.getToolbarTextString());
        } else {
            MobclickAgent.onPageStart(TAG);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (activity instanceof BaseTitleActivity) {
            BaseTitleActivity titleActivity = (BaseTitleActivity) activity;
            MobclickAgent.onPageEnd(TAG + titleActivity.getToolbarTextString());
        } else {
            MobclickAgent.onPageEnd(TAG);
        }
    }

}
