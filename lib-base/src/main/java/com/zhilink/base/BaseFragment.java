package com.zhilink.base;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhilink.activitymanager.ActivityManagerUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 * @author xiemeng
 * @date 2018-8-23 09:12
 */

public abstract class BaseFragment extends Fragment {
    protected Context context;

    protected Activity activity;

    protected View mFragmentView;

    protected final String TAG = getClass().getSimpleName();

    Unbinder unbind;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context =context;
        activity = getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mFragmentView == null) {
            mFragmentView = inflater.inflate(bindLayoutId(), null);
        }
        unbind = ButterKnife.bind(this, mFragmentView);
        doBusiness();
        return mFragmentView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
        if (null != mFragmentView) {
            ((ViewGroup) mFragmentView.getParent()).removeView(mFragmentView);
        }
    }


    /**
     * 布局
     * @return 布局文件
     */
    protected abstract int bindLayoutId();

    protected void beforeCreateView() {
    }

    /**
     * 显示完界面之后的事件
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
    protected void showLoadingDialog() {

    }


    protected void dismissLoadingDialog() {

    }
}
