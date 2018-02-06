package cn.yaohl.MayorOnline.util.retrofitLib;


import com.google.gson.JsonObject;
import com.yaohl.retrofitlib.utils.URLUtils;

import java.util.HashMap;

import cn.yaohl.MayorOnline.ui.login.beans.LoginResp;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
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
    Observable<LoginResp> login(@FieldMap HashMap<String, String> reqParams);

    @GET(URLUtils.GET_DUOJING_METHOD)
    Observable<JsonObject> GetDJingData(@QueryMap HashMap<String, String> reqParams);

}
