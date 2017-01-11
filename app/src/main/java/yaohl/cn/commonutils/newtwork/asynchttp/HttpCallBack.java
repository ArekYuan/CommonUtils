package yaohl.cn.commonutils.newtwork.asynchttp;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;

/**
 * Created by asus on 2016/11/16.
 */

public abstract class HttpCallBack<T> extends BaseJsonHttpResponseHandler<T>
{
    @Override
    public abstract void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, T response);

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, T errorResponse)
    {
        Log.i("onFailure", throwable.getMessage());
    }


    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onFinish()
    {
        super.onFinish();
    }

    @Override
    public void onUserException(Throwable error)
    {
        super.onUserException(error);
    }

    @Override
    protected T parseResponse(String rawJsonData, boolean isFailure) throws Throwable
    {
        Log.i("rawJsonData", rawJsonData);
        Log.i("isFailure", isFailure + "");

        T t = new Gson().fromJson(rawJsonData, getSuperclassTypeParameter(getClass()));

        return t;
    }

    /**
     * 返回gson类型
     */
    public static Type getSuperclassTypeParameter(Class<?> subclass)
    {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class)
        {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }
}
