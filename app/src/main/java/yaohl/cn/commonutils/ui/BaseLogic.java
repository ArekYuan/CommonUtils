package yaohl.cn.commonutils.ui;

import android.content.Context;
import android.util.Log;

import com.yaohl.okhttplib.okhttp.OkHttpRequestParams;
import com.yaohl.okhttplib.okhttp.OkUiCallBack;

import org.json.JSONException;


/**
 * Created by Administrator on 2017/4/19.
 */

public abstract class BaseLogic implements OkUiCallBack
{
    public <T> OkHttpRequestParams getBaseParam(String tag, Context mContext, T baseRequest)
    {
        OkHttpRequestParams requestParams = new OkHttpRequestParams();
        try
        {
            requestParams.setJsonObj(new BaseJSONObject(mContext, baseRequest));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Log.e("getBaseParam", "error:" + e.getLocalizedMessage());
        }
        return requestParams;
    }

    @Override
    public void onFail(String errorMessage)
    {
        Context context = getContext();

    }

    @Override
    public void onSysFail(int sysErrorCode, String methodName)
    {
        Context context = getContext();


    }

    /**
     * <取得上下文> <功能详细描述>
     *
     * @return 返回类型:Context
     */
    public abstract Context getContext();
}
