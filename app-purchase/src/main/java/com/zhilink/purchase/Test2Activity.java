package com.zhilink.purchase;

import android.util.Log;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import com.zhilink.app_middle.base.BaseTitleActivity;
import com.zhilink.lib_retrofit.ObserverOnNextErrorStringListener;
import com.zhilink.app_middle.net.Api;
import com.zhilink.app_middle.net.ApiMethods;
import com.zhilink.app_middle.net.ApiService;
import com.zhilink.app_middle.net.BaseResponse;
import com.zhilink.app_middle.net.LinkObserver;
import com.zhilink.app_middle.net.LoginBean;
import com.zhilink.poplibrary.ToastUtils;

/**
 * 测试
 *
 * @author xiemeng
 * @date 2018-8-22
 */
public class Test2Activity extends BaseTitleActivity {


    @BindView(R2.id.tv_purchase)
    TextView tvPurchase;

    @OnClick(R2.id.tv_purchase)
    void click() {
        ToastUtils.showShort(context, "fuck库文件" + TAG);
    }

    @Override
    public int bindView() {
        return R.layout.activity_purchase;
    }



    @Override
    protected void doBusiness() {

        //账号统计使用app名字+账号
        MobclickAgent.onProfileSignIn("WB","userID");
        //注销使用
        MobclickAgent.onProfileSignOff();
        init();
        second();

    }

    @Override
    protected void onResume() {
        super.onResume();
//        int a= Integer.parseInt("q");
    }

    LinkObserver b;
    private void second() {
        String loginName = "wyq";
        String password = "wyq";
        LoginBean loginBean = new LoginBean();
        loginBean.setLoginName(loginName);
        loginBean.setNo("");
        loginBean.setPassword(password);
         b = new LinkObserver<LoginBean>(activity, new ObserverOnNextErrorStringListener<LoginBean>() {
            @Override
            public void onNext(LoginBean o) {
                Log.d(TAG, "onNext: " + o);
                tvPurchase.setText(o.toString());
            }

            @Override
            public void onError(String t) {
                Log.d(TAG, "onError: " + t);
                tvPurchase.setText(t);
            }
        });
        ApiMethods.login(loginBean, b);
    }

    private void first() {
        ApiService apiService = Api.getApiService();
        String loginName = "wyq";
        String password = "wyq";
        LoginBean loginBean = new LoginBean();
        loginBean.setLoginName(loginName);
        loginBean.setNo("");
        loginBean.setPassword(password);
        apiService.loginPostString(loginName, password, "123")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<LoginBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(BaseResponse<LoginBean> movie) {
                        Log.d(TAG, "onNext: " + movie);
                        tvPurchase.setText(movie.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Over!");
                    }
                });
    }
    protected void init() {
        setToolBarTitle("中间");
    }


}
