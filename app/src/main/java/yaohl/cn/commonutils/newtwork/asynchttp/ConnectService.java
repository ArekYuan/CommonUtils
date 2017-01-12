package yaohl.cn.commonutils.newtwork.asynchttp;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;

import javax.net.ssl.SSLException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by asus on 2016/11/16.
 */

public class ConnectService
{

    public static final int SOCKET_TIMEOUT = 20 * 1000;//默认超时时间

    private static ConnectService instance = null;

    //    public static final int DEFAULT_SOCKET_TIMEOUT = 10 * 1000;

    /**
     * 用于取消请求
     *
     * @return
     */
    public static AsyncHttpClient getClient()
    {
        return client;
    }

    // 实例话对象
    private static AsyncHttpClient client = new AsyncHttpClient();

    static
    {
//        client.setConnectTimeout(SOCKET_TIMEOUT);  //连接时间
//        client.setResponseTimeout(SOCKET_TIMEOUT); //响应时间
        client.setTimeout(SOCKET_TIMEOUT); // 设置连接超时，如果不设置，默认为10s
    }

    private PersistentCookieStore cookieStore;

    private ConnectService()
    {

    }


    /**
     * 单例模式
     *
     * @return
     */
    public static ConnectService getInstance()
    {
        if (instance == null)
        {
            synchronized (ConnectService.class)
            {
                if (instance == null)
                {
                    instance = new ConnectService();
                }
            }
        }
        return instance;
    }

    /**
     * GET 请求 不带参数
     *
     * @param callBacks
     * @param obj
     * @param <T>
     */
    public <T> void get(String TAG, Context mContext, String serverUrl, final UiCallBack callBacks, Class<T> obj)
    {
//        String serverUrl = CommonConstants.SERVER + url;
        Log.i(TAG, "-->" + serverUrl);
        doJsonGetNoParamsRequest(TAG, mContext, serverUrl, new INetCallBack()
        {
            @Override
            public void onSuccess(Object ob)
            {
                callBacks.onComplete(ob);
            }

            @Override
            public void onFail(String errorMessage)
            {
                callBacks.onFail(errorMessage);
            }

        }, obj);

    }


    /**
     * get 不带参数请求
     *
     * @param serverUrl
     * @param iNetCallBack
     * @param obj
     * @param <T>
     */
    private <T> RequestHandle doJsonGetNoParamsRequest(final String TAG, Context mContext, final String serverUrl, final INetCallBack iNetCallBack, final Class<T> obj)
    {
        RequestHandle requestHandle = client.get(mContext, serverUrl, new JsonHttpResponseHandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();
                Log.i(TAG, "-->" + serverUrl);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                super.onSuccess(statusCode, headers, response);
                Log.i(TAG, "-->" + response.toString());
                String responseString = response.toString();
                Gson gson = new Gson();
                Object object = gson.fromJson(responseString, obj);
                onSuccessMethod(object, iNetCallBack);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
            {
                onFailMethod(throwable.getMessage(), iNetCallBack);
            }

            @Override
            public void onFinish()
            {
                super.onFinish();
            }
        });
        return requestHandle;
    }

    /**
     * GET 方法带参数
     *
     * @param mContext
     * @param serverUrl
     * @param params
     * @param uiCallBack
     * @param obj
     * @param <T>
     */
    public <T> void get(String TAG, Context mContext, String serverUrl, RequestParams params, final UiCallBack uiCallBack, Class<T> obj)
    {
//        String serverUrl = CommonConstants.SERVER + url;
        Log.i(TAG, "-->" + client.getUrlWithQueryString(true, serverUrl, params));

        doJsonGetRequest(TAG, mContext, serverUrl, params, new INetCallBack()
        {
            @Override
            public void onSuccess(Object ob)
            {
                uiCallBack.onComplete(ob);
            }

            @Override
            public void onFail(String errorMessage)
            {
                uiCallBack.onFail(errorMessage);
            }
        }, obj);
    }


    /**
     * get 请求  带参数
     *
     * @param mContext
     * @param serverUrl
     * @param params
     * @param iNetCallBack
     * @param obj
     * @param <T>
     *
     * @return
     */
    private <T> void doJsonGetRequest(final String TAG, Context mContext, String serverUrl, RequestParams params, final INetCallBack iNetCallBack, final Class<T> obj)
    {
        client.get(mContext, serverUrl, params, new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                Gson gson = new Gson();
                String response = null;
                try
                {
                    response = new String(responseBody, "UTF-8");
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
                Log.i(TAG, "-->" + response);
                Object object = gson.fromJson(response, obj);
                onSuccessMethod(object, iNetCallBack);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {
                onFailMethod(error.getMessage(), iNetCallBack);
            }

            @Override
            public void onFinish()
            {
                super.onFinish();
            }
        });
    }

    /**
     * POST  带参数
     *
     * @param serverUrl
     * @param params
     * @param uiCallBack
     *
     * @return
     */
    public <T> void post(String TAG, Context mContext, String serverUrl, RequestParams params, final UiCallBack uiCallBack, Class<T> obj)
    {
//        String serverUrl = CommonConstants.SERVER + url;
        Log.i(TAG, "-->" + client.getUrlWithQueryString(true, serverUrl, params));
        doJsonPostRequest(TAG, mContext, serverUrl, params, new INetCallBack()
        {
            @Override
            public void onSuccess(Object ob)
            {
                uiCallBack.onComplete(ob);
            }

            @Override
            public void onFail(String errorMessage)
            {
                uiCallBack.onFail(errorMessage);
            }
        }, obj);
    }


    /**
     * POST json请求
     *
     * @param serverUrl
     * @param params
     * @param iNetCallBack
     *
     * @return
     */
    private <T> void doJsonPostRequest(final String TAG, Context mContext, String serverUrl, RequestParams params, final INetCallBack iNetCallBack, final Class<T> obj)
    {
        client.post(mContext, serverUrl, params, new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                Gson gson = new Gson();
                String response = null;
                try
                {
                    response = new String(responseBody, "UTF-8");
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
                Log.i(TAG, "-->" + response);
                Object object = gson.fromJson(response, obj);
                onSuccessMethod(object, iNetCallBack);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {
                onFailMethod(error.getMessage(), iNetCallBack);
            }

            @Override
            public void onFinish()
            {
                super.onFinish();
            }
        });
    }

    /**
     * <请求失败message> <功能详细描述>
     *
     * @param errorMessage    错误信息
     * @param requestListener requestListener 返 回 类 型：void
     */
    public void onFailMethod(String errorMessage, INetCallBack requestListener)
    {
        if (requestListener != null)
        {
            requestListener.onFail(errorMessage);
        }
    }

    /**
     * <请求成功返回结果> <功能详细描述>
     *
     * @param response        response
     * @param requestListener requestListener 返 回 类 型：void
     */
    public void onSuccessMethod(Object response, INetCallBack requestListener)
    {
        if (requestListener != null)
        {
            requestListener.onSuccess(response);
        }
    }

    /**
     * 设置Cookie
     *
     * @param context
     */
    public void setCookie(Context context)
    {
        cookieStore = new PersistentCookieStore(context);
        client.setCookieStore(cookieStore);
    }

    /**
     * 清除Cookie
     */
    public void clearSession()
    {
        if (cookieStore != null)
        {
            cookieStore.clear();
        }
    }

    /**
     * 设置重试机制
     */
    public void setRetry()
    {
        client.setMaxRetriesAndTimeout(2, SOCKET_TIMEOUT);
        client.allowRetryExceptionClass(SocketTimeoutException.class);
        client.blockRetryExceptionClass(SSLException.class);
    }

    /**
     * 取消所有请求
     *
     * @param context
     */
    public void cancelAllRequests(Context context)
    {
        if (client != null)
        {
            Log.i("ConnectService-->", "cancel");
            client.cancelRequests(context, true); //取消请求
            client.cancelAllRequests(true);
        }
    }

    /*
     * 文件下载
     *
     * @param paramString
     * @param paramBinaryHttpResponseHandler
     */
    public void downFile(String paramString,
                         BinaryHttpResponseHandler paramBinaryHttpResponseHandler)
    {
        try
        {
            client.get(paramString, paramBinaryHttpResponseHandler);
            return;
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
            Log.d("ConnectService-->", "URL路径不正确！！");
        }
    }

}
