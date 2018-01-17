package yaohl.cn.commonutils.ui;

import android.content.Context;
import android.util.Log;


import com.yaohl.retrofitlib.retrofit.OkUiCallBack;

import org.json.JSONException;


/**
 * Created by Administrator on 2017/4/19.
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
