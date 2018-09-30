package com.zhilink.app_middle.net;

import android.arch.lifecycle.Lifecycle;

import com.trello.rxlifecycle2.LifecycleProvider;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 接口调用处，如果json返回格式未完全解析或者不符合BaseResponse格式，请不要使用LinkObserver
 *
 * @author xiemeng
 * @date 2018-8-24
 */
public class ApiMethods {

    private static LifecycleProvider<Lifecycle.Event> mLifecycleProvider;

    /**
     * 封装线程管理和订阅的过程
     *
     * @param observable 生产者
     * @param observer   消费者
     */
    public static void apiSubscribe(final Observable observable, final LinkObserver observer) {
        if (null != mLifecycleProvider) {
            observable
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .compose(mLifecycleProvider.<BaseResponse>bindUntilEvent(Lifecycle.Event.ON_STOP))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } else {
            observable
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }
    }

    public static void setLifecycleProvider(LifecycleProvider<Lifecycle.Event> lifecycleProvider) {
        mLifecycleProvider = lifecycleProvider;
    }

    private static ApiService getService() {
        return Api.getApiService();
    }


    public static void login(LoginBean req, LinkObserver<LoginBean> observer) {
        apiSubscribe(getService().loginPostString(req.getLoginName(), req.getPassword(), req.getAppid()), observer);
    }
}