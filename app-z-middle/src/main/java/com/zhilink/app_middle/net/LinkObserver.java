package com.zhilink.app_middle.net;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import io.reactivex.disposables.Disposable;
import com.zhilink.retrofit.BaseObserver;
import com.zhilink.retrofit.ObserverOnNextErrorStringListener;
import com.zhilink.utils.LogUtils;
import com.zhilink.utils.TelephonyUtils;

/**
 * 初步解析
 * 注意使用此类返回数据必须对应
 * 如果json返回格式未完全解析或者不符合BaseResponse格式，请不要使用LinkObserver
 *
 * @author xiemeng
 * @date 2018-8-24 17:19
 */

public class LinkObserver<T> extends BaseObserver<BaseResponse<T>> {


    public LinkObserver(Context context, ObserverOnNextErrorStringListener listener) {
        super(context, listener);
    }

    @Override
    public void onSubscribe(Disposable d) {
        super.onSubscribe(d);

        if (!TelephonyUtils.isNetworkConnected(context)) {
            Toast.makeText(context, "当前网络不可用，请检查网络情况", Toast.LENGTH_SHORT).show();
            onComplete();
        } else {
            showLoadingDialog();
        }
    }


    @Override
    public void onNext(BaseResponse baseResponse) {
        if (null == baseResponse) {
            if (null != listener) {
                listener2.onError("无数据返回");
            }
            if (null != listener2) {
                listener2.onError("无数据返回");
            }
        } else {
            if ("1".equals(baseResponse.getHead().getStatus())) {
                if (null != listener) {
                    listener.onNext(baseResponse.getBody());
                }
                if (null != listener2) {
                    listener2.onNext(baseResponse.getBody());
                }
            } else {
                if (null != listener) {
                    listener.onError(baseResponse.getHead().getErrorMsg());
                }
                if (null != listener2) {
                    listener2.onError(baseResponse.getHead().getErrorMsg());
                }
            }

        }


    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, e.getMessage() + "");
        if (null != listener) {
            listener.onError(e.getMessage());
        }
        if (null != listener2) {
            listener2.onError(e.getMessage());
        }
    }

    @Override
    public void onComplete() {
        super.onComplete();
        dismissLoadingDialog();
    }


    public void showLoadingDialog() {
        LogUtils.i(TAG, "showLoadingDialog");
    }

    public void dismissLoadingDialog() {
        LogUtils.i(TAG, "dismissLoadingDialog");
    }
}
