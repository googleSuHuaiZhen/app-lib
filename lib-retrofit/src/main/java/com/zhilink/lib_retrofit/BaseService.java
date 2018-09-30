package com.zhilink.lib_retrofit;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * retrofit接口返回
 * 全部用string定义返回，map定义请求
 * 使用需进一步解析
 *
 * @author xiemeng
 * @date 2018-8-29 09:58
 */

public interface BaseService {

    @FormUrlEncoded
    @POST
    Observable<String> post(@Url String url, @FieldMap Map<String, Object> params);

    @GET
    Observable<String> get(@Url String url, @QueryMap Map<String, Object> map);

    @Multipart
    @POST
    Observable<String> uploadMultipartFile(@Url String ulr, @PartMap Map<String, Object> params);
}
