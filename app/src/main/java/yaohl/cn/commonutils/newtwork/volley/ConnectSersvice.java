package yaohl.cn.commonutils.newtwork.volley;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import yaohl.cn.commonutils.util.CMLog;


/**
 * 文 件 名: ConnectSersvice.java
 */
public class ConnectSersvice
{
    /**
     * 访问网络单子例对象
     */
    private static ConnectSersvice CONNECTSERVICESINGLE = null;

    /**
     * <返回访问网络单子例对象> <功能详细描述>
     *
     * @return 返 回 类 型：ConnectSersvice
     */
    public static ConnectSersvice instance()
    {
        if (CONNECTSERVICESINGLE == null)
        {
            CONNECTSERVICESINGLE = new ConnectSersvice();
        }
        return CONNECTSERVICESINGLE;
    }

    /**
     * json请求
     */
    private HttpRequestHandler handler;

    /**
     * request
     */
    private StringRequest request;

    /**
     * <请求网络,netBack里为Ui线程，返回体为T或者null> <功能详细描述>
     *
     * @param httpRequestParams 请求对象
     * @param callback          回调
     * @param obj               类
     * @param <T>               response
     * @param serverUrl        服务器接口名
     * @param context           context 返 回 类 型：void
     */
    public <T> void connectService(yaohl.cn.commonutils.newtwork.volley.HttpRequestParams httpRequestParams, final yaohl.cn.commonutils.newtwork.volley.UICallBack callback, Class<T> obj,
                                   final Context context, final String serverUrl)
    {
//        String serviceUrl = CommonConstants.SERVER + methodName;
        doJsonRequest(httpRequestParams, new INetCallBack()
        {
            @Override
            public void onSuccess(Object ob)
            {
                callback.onComplete(ob);
            }

            @Override
            public void onFail(String errorMessage)
            {
                callback.onFail(serverUrl);
            }

            @Override
            public void onSysFail(int sysCode)
            {
                callback.onSysFail(sysCode, serverUrl);
            }

            @Override
            public void onReqFailed(String msg)
            {
                callback.onReqFailed(msg);
            }
        }, obj, context, serverUrl);
    }

    private <T> void doJsonRequest(yaohl.cn.commonutils.newtwork.volley.HttpRequestParams httpRequestParams, final INetCallBack netBack, final Class<T> obj,
                                   final Context context, final String serviceUrl)
    {
        // 请求参数
        final Map<String, String> reqParams = new HashMap<String, String>();
        // 请求方法
        yaohl.cn.commonutils.newtwork.volley.HttpRequestParams.HttpRequestMethod httpRequestMethod = httpRequestParams.getHttpRequestMethod();
        int method = getMethod(httpRequestMethod);
        // 服务器地址
        final String url = bindParamsIfNeed(method, serviceUrl, reqParams);
        CMLog.e("requestUrl:", url, context);
        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                onFail("链接服务器失败", netBack);
                error.printStackTrace();
                CMLog.e("connect server error", "error message:" + error, context);
            }
        };
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    String codeStr = response.getString("code");
                    int code = Integer.parseInt(codeStr);
                    if (code == 2000)
                    {
                        CMLog.e("请求成功---->url:" + url + ":-->", response.toString(), context);
                        Gson gson = new Gson();
                        Object object = gson.fromJson(response.toString(), obj);
                        onSuccess(object, netBack);
                    }
                    else
                    {
                        CMLog.e("服务器处理失败---->url:" + url + ":-->", response.toString(), context);
                        String message = response.getString("message");
                        onReqFailed(message, netBack);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                catch (IllegalStateException e1)
                {
                    e1.printStackTrace();
                }
                catch (JsonSyntaxException e2)
                {
                    e2.printStackTrace();
                }
                // return;
            }
        };
        handler = new HttpRequestHandler(method, url, httpRequestParams.getJsonObj(), listener, errorListener, context);
        // 设置超时时间
        if (3000 == httpRequestParams.getTimeOut())
        {// app版本更新
            handler.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
        }
        else
        {
            handler.setRetryPolicy(new DefaultRetryPolicy(10000, 0, 1f));
        }
        // 请求tag
        if (TextUtils.isEmpty(httpRequestParams.getTag()))
        {
            handler.setTag(httpRequestParams.getTag());
        }
        yaohl.cn.commonutils.newtwork.volley.RequestQueueHandler.getInstance().addRequest(handler);
    }

    /**
     * <请求网络,netBack里为Ui线程，返回体为T或者null> <特殊路径的重载，如果有其他服务器的URL，就不需要做拼接直接调用此方法>
     * get方式请求数据
     *
     * @param tag
     * @param callback  回调
     * @param obj       类
     * @param <T>       response
     * @param reqParams
     */

    public <T> void connectGetService(final String tag, final yaohl.cn.commonutils.newtwork.volley.UICallBack callback, final Class<T> obj,
                                      final Map<String, String> reqParams, Context mContext, final String serverUrl)
    {
//        String serviceUrl = CommonConstants.SERVER + methodName;
        doJsonRequestGet(Request.Method.GET, new INetCallBack()
        {
            @Override
            public void onSuccess(Object ob)
            {
                callback.onComplete(ob);
            }

            @Override
            public void onFail(String errorMessage)
            {
                callback.onFail(serverUrl);
            }

            @Override
            public void onSysFail(int sysCode)
            {
                callback.onSysFail(sysCode, serverUrl);
            }

            @Override
            public void onReqFailed(String msg)
            {
                callback.onReqFailed(msg);
            }
        }, obj, reqParams, mContext, serverUrl, tag);
    }

    /***
     * get 方式请求数据
     *
     * @param method
     * @param netBack
     * @param obj
     * @param reqParams
     * @param serviceUrl
     * @param <T>
     */
    private <T> void doJsonRequestGet(int method, final INetCallBack netBack, final Class<T> obj, Map<String, String> reqParams, final Context mContext, String serviceUrl, final String tag)
    {
       final String url = bindParamsIfNeed(Request.Method.GET, serviceUrl, reqParams);
        //  String url = serviceUrl + "?market=" + req.getMarket() + "&timeType=" + req.getTimeType() + "&timeCell=" + req.getTimeCell();
        CMLog.e("doJsonRequestGet1", url);
        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                onFail("链接服务器失败", netBack);
                error.printStackTrace();
                CMLog.e(tag + "---->doJsonRequestGet", "链接服务器失败");
            }
        };
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {

                try
                {
                    String codeStr = response.getString("code");

                    int code = Integer.parseInt(codeStr);
                    if (code == 2000)
                    {
                        CMLog.e(tag + "请求成功---->url:" + url + ":-->", response.toString(), mContext);
                        Gson gson = new Gson();
                        Object object = gson.fromJson(response.toString(), obj);
                        onSuccess(object, netBack);
                    }
                    else
                    {
                        CMLog.e(tag + "服务器处理失败---->url:" + url + ":-->", response.toString(), mContext);
                        String message = response.getString("message");
                        onReqFailed(message, netBack);
                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        };

        handler = new HttpRequestHandler(method, url, null, listener, errorListener, mContext);
        // 设置超时时间
        handler.setRetryPolicy(new DefaultRetryPolicy(10000, 0, 1f));
        handler.setTag(tag);
        yaohl.cn.commonutils.newtwork.volley.RequestQueueHandler.getInstance().addRequest(handler);
    }

    /**
     * <获取HTTP请求方式> <功能详细描述>
     *
     * @param httpRequestMethod 请求方法
     *
     * @return 返 回 类 型：int
     */
    public int getMethod(yaohl.cn.commonutils.newtwork.volley.HttpRequestParams.HttpRequestMethod httpRequestMethod)
    {
        int method = -1;
        switch (httpRequestMethod)
        {
            case POST:
                method = com.android.volley.Request.Method.POST;
                break;
            case GET:
                method = com.android.volley.Request.Method.GET;
                break;
            case PUT:
                method = com.android.volley.Request.Method.PUT;
                break;
            case DELETE:
                method = com.android.volley.Request.Method.DELETE;
                break;
            default:
                method = com.android.volley.Request.Method.POST;
                break;
        }
        return method;
    }

    /**
     * <请求失败message> <功能详细描述>
     *
     * @param errorMessage    错误信息
     * @param requestListener requestListener 返 回 类 型：void
     */
    public void onFail(String errorMessage, INetCallBack requestListener)
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
    public void onSuccess(Object response, INetCallBack requestListener)
    {
        if (requestListener != null)
        {
            // try
            // {
            requestListener.onSuccess(response);
            // }
            // catch (Exception e)
            // {
            // e.printStackTrace();
            // CMLog.e(this.getClass().getName(), e.getMessage());
            // onFail(e.getMessage(), requestListener);
            // }
        }
    }

    /**
     * @param msg
     * @param requestListener
     */
    public void onReqFailed(String msg, INetCallBack requestListener)
    {
        if (requestListener != null)
        {
            requestListener.onReqFailed(msg);
        }
    }

    /**
     * <拼接URL> <功能详细描述>
     *
     * @param method 方法
     * @param url    服务器URL
     * @param params 请求参数
     *
     * @return 返 回 类 型：String
     */
    public String bindParamsIfNeed(int method, String url, Map<String, String> params)
    {
        StringBuilder builder = new StringBuilder(url);
        if (method == com.android.volley.Request.Method.GET || method == com.android.volley.Request.Method.DELETE)
        {
            if (params == null || params.isEmpty())
            {
                return builder.toString();
            }
            builder.append("?");
            Set<String> set = params.keySet();
            for (String key : set)
            {
                String value = params.get(key);
                builder.append(key).append("=").append(value).append("&");
            }
            builder.deleteCharAt(builder.lastIndexOf("&"));
        }
        return builder.toString();
    }

}
