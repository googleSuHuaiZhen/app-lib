package com.zhilink.retrofit;

import android.content.Context;
import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Observer基本封装，避免界面直接调用
 *
 * @author xiemeng
 * @date 2018-8-24 10:56
 */

public class BaseObserver<T> implements Observer<T> {
    protected final String TAG = this.getClass().getSimpleName();

    protected ObserverOnNextErrorStringListener listener = null;

    protected ObserverOnNextErrorListener listener2 = null;

    protected Context context;


    public BaseObserver(ObserverOnNextErrorStringListener listener) {
        this.listener = listener;
    }

    public BaseObserver(Context context, ObserverOnNextErrorStringListener listener) {
        this.listener = listener;
        this.context = context;
    }

    public BaseObserver(Context context, ObserverOnNextErrorListener listener) {
        this.listener2 = listener;
        this.context = context;
    }


    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(T t) {
        if (null != listener) {
            listener.onNext(t);
        }
        if (null != listener2) {
            listener2.onNext(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (null != listener) {
            listener.onError(e.getMessage());
        }
        if (null != listener2) {
            listener2.onError(e);
        }
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete: ");
    }

}
