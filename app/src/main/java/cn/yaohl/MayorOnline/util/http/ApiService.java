package cn.yaohl.MayorOnline.util.http;


import java.util.HashMap;

import cn.yaohl.MayorOnline.ui.login.beans.LoginResp;
import rx.Observable;


/**
 * 作者：袁光跃
 * 日期：2018/1/17
 * 描述：所有的接口请求 统一管理
 * 邮箱：813665242@qq.com
 */

public class ApiService extends BaseApiService {

    public Observable<LoginResp> getLoginInfo(HashMap<String, String> reqParams) {
        return apiService.login(reqParams).compose(RxUtil.<LoginResp>parseResponseResult());
    }
}
