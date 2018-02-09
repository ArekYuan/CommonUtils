package com.yaohl.retrofitlib.filter;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yaohl.retrofitlib.utils.CommonConfig;
import com.yaohl.retrofitlib.utils.MyRouter;


@Interceptor(priority = 8, name = "登录拦截器")
public class LoginInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if (!CommonConfig.LOGIN) {
            callback.onInterrupt(null);
            ARouter.getInstance().build(MyRouter.ROUTER_LOGIN).greenChannel().navigation();
        } else {
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {

    }
}
