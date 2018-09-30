package com.zhilink.swmsapp.login;

import android.view.WindowManager;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import com.zhilink.app_middle.base.BaseTitleActivity;
import com.zhilink.swmsapp.TestActivity;
import com.zhilink.swmsapp.R;

/**
 * 欢迎页面
 * @author xiemeng
 * @date 2018-8-30 10:34
 */

public class WelComeActivity extends BaseTitleActivity {

    @Override
    public int bindView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.aty_welcome;
    }

    @Override
    protected boolean isUseToolBar() {
        return false;
    }

    @Override
    protected void doBusiness() {
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        startActivity(TestActivity.class);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
