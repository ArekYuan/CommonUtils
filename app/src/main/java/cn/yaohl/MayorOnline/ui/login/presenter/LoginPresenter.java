package cn.yaohl.MayorOnline.ui.login.presenter;

import com.yaohl.retrofitlib.utils.GsonHelp;

import java.net.SocketTimeoutException;
import java.util.HashMap;

import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.ui.BasePresenter;
import cn.yaohl.MayorOnline.ui.login.LoginActivity;
import cn.yaohl.MayorOnline.ui.login.beans.LoginResp;
import cn.yaohl.MayorOnline.util.retrofitLib.ApiException;
import cn.yaohl.MayorOnline.util.retrofitLib.ApiService;
import cn.yaohl.MayorOnline.util.retrofitLib.RxUtil;
import rx.functions.Action1;

/**
 * 作者：袁光跃
 * 日期：2018/2/6
 * 描述：
 * 邮箱：813665242@qq.com
 */

public class LoginPresenter extends BasePresenter {

    private LoginActivity activity;
    private ApiService loginService;

    public LoginPresenter(LoginActivity activity) {
        this.activity = activity;
        loginService = new ApiService();
    }

    public void doLoginReq(String userName, String pwdStr) {
        HashMap<String, String> reqParams = new HashMap<>();
        reqParams.put("loginname", userName);
        reqParams.put("loginpwd", pwdStr);
        String jsonStr = GsonHelp.objectToJsonString(reqParams);
        HashMap<String, String> data = new HashMap<>();
        data.put("data", jsonStr);
        addSubscribe(loginService.getLoginInfo(data)
                .compose(RxUtil.<LoginResp>rxSchedulerHelper())
                .subscribe(new Action1<LoginResp>() {
                    @Override
                    public void call(LoginResp resp) {
                        activity.setSuccess(resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof SocketTimeoutException) {
                            activity.loginFail(
                                    String.format(activity.getString(R.string.common_load_timeout), "登录"));
                        } else if (throwable instanceof ApiException) {
                            activity.loginFail(throwable.getMessage());
                        } else {
                            activity.loginFail(
                                    String.format(activity.getString(R.string.common_load_fail), "登录"));
                        }
                    }
                }));
    }
}
