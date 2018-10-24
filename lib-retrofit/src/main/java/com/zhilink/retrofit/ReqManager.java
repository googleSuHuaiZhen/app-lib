package com.zhilink.retrofit;

import java.util.LinkedHashMap;
import java.util.Set;

import io.reactivex.disposables.Disposable;


/**
 * 重复请求管理
 * 判断是否在请求中
 *
 * @author xiemeng
 * @date 2018-8-27 14:05
 */
public class ReqManager {
    private static final String TAG = "ReqManager";

    private static ReqManager sInstance = null;

    private LinkedHashMap<Object, Boolean> maps;

    public static ReqManager get() {
        if (sInstance == null) {
            synchronized (ReqManager.class) {
                if (sInstance == null) {
                    sInstance = new ReqManager();
                }
            }
        }
        return sInstance;
    }

    private ReqManager() {
        maps = new LinkedHashMap<>();
    }

    public void add(Object tag, boolean isRequestIng) {
        maps.put(tag, isRequestIng);
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
        maps.remove(tag);
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