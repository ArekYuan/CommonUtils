package com.yaohl.retrofitlib.retrofit.service;


import com.google.gson.JsonObject;
import com.yaohl.retrofitlib.utils.URLUtils;

import java.util.HashMap;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 作者：袁光跃
 * 日期：2018/1/17
 * 描述：接口总汇
 * 邮箱：813665242@qq.com
 */

public interface ApiInterface {

    /**
     * 登录接口
     *
     * @param reqParams
     * @return
     */
    @POST(URLUtils.POST_LOGIN_METHOD)
    @FormUrlEncoded
    Observable<JsonObject> login(@FieldMap HashMap<String, String> reqParams);

}
