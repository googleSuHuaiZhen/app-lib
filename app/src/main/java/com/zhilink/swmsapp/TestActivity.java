package com.zhilink.swmsapp;

import android.arch.lifecycle.Lifecycle;
import android.util.Log;
import android.widget.Button;

import com.zhilink.app_middle.base.BaseTitleActivity;
import com.zhilink.reasourselibrary.WaveProgressView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.progressmanager.ProgressListener;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.progressmanager.body.ProgressInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 测试
 *
 * @author xiemeng
 * @date 2018-9-4
 */
public class TestActivity extends BaseTitleActivity {


    @BindView(R.id.tv_test)
    Button tvTest;
    @BindView(R.id.wpv_git)
    WaveProgressView wpvGit;


    @OnClick(R.id.tv_test)
    void click() {
    }

    OkHttpClient okHttpClient;
    String url = "https://codeload.github.com/xiemeng99/digiwin/zip/master";

    @Override
    public int bindView() {
        return R.layout.activity_main;
    }

    @Override
    protected void doBusiness() {
        setToolBarTitle("默认体哦吧");
        okHttpClient = ProgressManager.getInstance().with(new OkHttpClient.Builder())
                .build();
        ProgressManager.getInstance().addResponseListener(url, new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                int progress = progressInfo.getPercent();
                progress = (int) (progressInfo.getCurrentbytes() / 100000);
                wpvGit.setProgress(progress);
                wpvGit.setText(progress + "%");
                Log.d(TAG, "--Download-- " + progress + " %  " + progressInfo.getSpeed() + " byte/s  " + progressInfo.toString());
            }

            @Override
            public void onError(long id, Exception e) {
            }
        });
        downloadStart();
    }


    /**
     * 点击开始下载资源,为了演示,就不做重复点击的处理,即允许用户在还有进度没完成的情况下,使用同一个 url 开始新的下载
     */
    private void downloadStart() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> str) {
                download();
            }
        }).subscribeOn(Schedulers.newThread())
                .compose(lifecycleProvider.<String>bindUntilEvent(Lifecycle.Event.ON_DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void download() {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = okHttpClient.newCall(request).execute();

            InputStream is = response.body().byteStream();
            //为了方便就不动态申请权限了,直接将文件放到CacheDir()中
            File file = new File(getCacheDir(), "download");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
            fos.close();
            bis.close();
            is.close();


        } catch (IOException e) {
            e.printStackTrace();
            //当外部发生错误时,使用此方法可以通知所有监听器的 onError 方法
            ProgressManager.getInstance().notifyOnErorr(url, e);
        }
    }

}
