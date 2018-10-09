package com.zhilink.app_middle.net;


import com.zhilink.retrofit.RetrofitUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * WMS进一步封装网络请求
 * 如SRM等其他平台新写一个Api即可
 *
 * @author xiemeng
 * @date 2018-8-24 11:59
 */

public class Api {

    private volatile static ApiService apiService;

    public static ApiService getApiService() {
        if (apiService == null) {
            synchronized (Api.class) {
                if (apiService == null) {
                    new Api();
                }
            }
        }
        return apiService;
    }


    public static ApiService getApiSrmService() {
        if (apiService == null) {
            synchronized (Api.class) {
                if (apiService == null) {
                    new Api("srm");
                }
            }
        }
        return apiService;
    }

    private Api() {
        RetrofitUtils baseApi = new RetrofitUtils();
        String baseUrl = "http://172.31.75.222:8084/adpweb/api/wms/";
//         baseUrl = "http://192.168.13.138:8086/adpweb/api/wms/";

        apiService = baseApi.getRetrofit(baseUrl, new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return null;
            }
        }).create(ApiService.class);
    }

    private Api(String srm) {
        RetrofitUtils baseApi = new RetrofitUtils();
        String baseUrl = "http://172.31.75.222:8084/adpweb/api/wms/";
        apiService = baseApi.getRetrofit(baseUrl, new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return null;
            }
        }).create(ApiService.class);
    }
}
