package com.zhilink.lib_retrofit;

/**
 * 回写next和error方法
 * error固定好String格式，使用较多
 *
 * @author xiemeng
 * @date 2018-8-24 10:57
 */
public interface ObserverOnNextErrorStringListener<T> {
    /**
     * next方法返回数据
     * @param t 返回类型
     */
    void onNext(T t);

    /**
     * error错误
     * @param msg 错误描述
     */
    void onError(String msg);
}