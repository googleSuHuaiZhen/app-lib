package com.zhilink.lib_retrofit;

import java.util.LinkedHashMap;
import java.util.Set;

import io.reactivex.disposables.Disposable;


/**
 * 管理rxJava发的请求
 *
 * @author xiemeng
 * @date 2018-8-27 14:05
 */
public class RxActionManager {
    private static final String TAG = "RxActionManager";

    private static RxActionManager sInstance = null;

    private LinkedHashMap<Object, Disposable> maps;

    public static RxActionManager get() {
        if (sInstance == null) {
            synchronized (RxActionManager.class) {
                if (sInstance == null) {
                    sInstance = new RxActionManager();
                }
            }
        }
        return sInstance;
    }

    private RxActionManager() {
        maps = new LinkedHashMap<>();
    }

    public void add(Object tag, Disposable disposable) {
        maps.put(tag, disposable);
    }

    public void remove(Object tag) {
        if (!maps.isEmpty()) {
            maps.remove(tag);
        }
    }

    public void removeAll() {
        if (!maps.isEmpty()) {
            maps.clear();
        }
    }

    /**
     * 取消 订阅
     *
     * @param tag activity名
     */
    public void cancel(Object tag) {
        if (maps.isEmpty()) {
            return;
        }
        if (maps.get(tag) == null) {
            return;
        }
        if (!maps.get(tag).isDisposed()) {
            maps.get(tag).dispose();
            maps.remove(tag);
        }
    }


    public void cancelAll() {
        if (maps.isEmpty()) {
            return;
        }
        Set<Object> keys = maps.keySet();
        for (Object apiKey : keys) {
            cancel(apiKey);
        }
    }
}