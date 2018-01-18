package cn.yaohl.commonutils.ui;

import android.content.Context;

import com.yaohl.retrofitlib.retrofit.OkUiCallBack;


/**
 * Created by 袁光跃 on 2017/4/19.
 */

public abstract class BaseLogic implements OkUiCallBack
{
    @Override
    public void onFail(String code, String errorMessage) {

    }

    /**
     * <取得上下文> <功能详细描述>
     *
     * @return 返回类型:Context
     */
    public abstract Context getContext();
}
