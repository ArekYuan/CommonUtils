package cn.yaohl.MayorOnline.util.retrofitLib;


import java.util.HashMap;

import cn.yaohl.MayorOnline.ui.home.beans.OrderResp;
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

    /**
     * 获取新闻打赏订单
     *
     * @param jsonStr
     * @return
     */
    public Observable<OrderResp> getNewsAdmireOrder(String jsonStr) {
        return apiService.getNewsAdmireOrder(jsonStr).compose(RxUtil.<OrderResp>parseResponseResult());
    }
}
