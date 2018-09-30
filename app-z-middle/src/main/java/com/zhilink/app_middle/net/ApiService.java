package com.zhilink.app_middle.net;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 接口
 *
 * @author xiemeng
 * @date 2018-8-24 10:48
 */
public interface ApiService {

    /**
     * demo:get方式发送数据
     *
     * @param start 请求参数
     * @return
     */
    @GET("login")
    Observable<BaseResponse<LoginBean>> loginGet(@Query("start") String start);

    /**
     * demo:post方式发送表单数据
     *
     * @param loginName 请求数据
     * @param password  请求数据
     * @return 返回
     */
    @FormUrlEncoded
    @POST("login")
    Observable<BaseResponse<LoginBean>> loginPostString(@Field("loginName") String loginName, @Field("password") String password
            , @Field("appid") String appid);

    /**
     * demo:post方式发送bean
     * 注意：@Body标签不能同时和@FormUrlEncoded、@Multipart标签同时使用。
     * 同时后台接收使用@RequestBody接收，否则可能导致接收不到
     * @param entity 请求体
     * @return 返回体
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("login")
    Observable<BaseResponse<LoginBean>> loginPostBean(@Body LoginBean entity);

    /**
     * demo:post方式发送bean
     *
     * @param map 请求体
     * @return 返回体
     */
    @POST("login")
    Observable<BaseResponse<LoginBean>> loginPost(@FieldMap Map<String, String> map);

}
