package com.zhilink.lib_retrofit;

/**
 * 回写next和error方法
 * @author xiemeng
 * @date 2018-8-24 10:57
 */
public interface
ObserverOnNextErrorListener<T,K> {
    /**
     * next方法返回数据
     * @param t 返回类型
     */
    void onNext(T t);
    /**
     * error错误
     * @param t 错误类型
     */
    void onError(K t);
}