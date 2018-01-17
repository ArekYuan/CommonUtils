package yaohl.cn.commonutils.util.http.service;


import com.google.gson.JsonObject;
import com.yaohl.retrofitlib.retrofit.BaseApiService;

import java.util.HashMap;

import rx.Observable;


/**
 * 作者：袁光跃
 * 日期：2018/1/17
 * 描述：登录请求
 * 邮箱：813665242@qq.com
 */

public class LoginApiService extends BaseApiService {

    public Observable<JsonObject> getLoginInfo(HashMap<String, String> reqParams) {
        return apiService.login(reqParams);
    }
}
