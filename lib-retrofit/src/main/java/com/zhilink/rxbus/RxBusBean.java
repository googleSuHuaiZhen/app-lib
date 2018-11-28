package com.zhilink.rxbus;

/**
 * 可不使用
 * @author xiemeng
 * @date 2018-11-28 11:33
 */
public class RxBusBean<T> {

    private int  code;

    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
