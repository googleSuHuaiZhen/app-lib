package com.zhilink.rxbus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * @author xiemeng
 * @date 2018-11-28 11:30
 * ---------------------
 * 作者：Donkor-
 * 来源：CSDN
 * 原文：https://blog.csdn.net/donkor_/article/details/79709366
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 */
public class RxBusNoBackPress {

    private final Subject<Object> mBus;

    private RxBusNoBackPress() {
        // toSerialized method made bus thread safe
        mBus = PublishSubject.create().toSerialized();
    }

    public static RxBusNoBackPress get() {
        return Holder.BUS;
    }

    public void post(Object obj) {
        mBus.onNext(obj);
    }

    public <T> Observable<T> toObservable(Class<T> tClass) {
        return mBus.ofType(tClass);
    }

    public Observable<Object> toObservable() {
        return mBus;
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    private static class Holder {
        private static final RxBusNoBackPress BUS = new RxBusNoBackPress();
    }
}
