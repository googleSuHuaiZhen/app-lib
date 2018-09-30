package com.zhilink.base;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * @author xiemeng
 * @date 2018-8-23 11:46
 */

public abstract class BaseAdapter<T> extends BaseQuickAdapter{

    public BaseAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    public BaseAdapter(@Nullable List data) {
        super(data);
    }

    public BaseAdapter(int layoutResId) {
        super(layoutResId);
    }
}
