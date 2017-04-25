package com.yaohl.volleylib.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * 文 件 名:  RequestQueueManager.java
 * 版    权:
 * 描    述:  <发送网络请求类>
 * 版    本： <版本号>
 * 创 建 人:
 * 创建时间:  2015年10月19日
 */
public class RequestQueueHandler
{

    /**
     * 请求队列
     */
    private RequestQueue requestQueue;

    /**
     * 文 件 名:  RequestQueueManager.java
     * 版    权:
     * 描    述:  <创建请求队列管理类对象>
     * 版    本： <版本号>
     * 创 建 人:  姜飞
     * 创建时间:  2015年10月19日
     */
    static class HttpHolder
    {
        /**
         * 创建请求队列管理类对象
         */
        private static RequestQueueHandler INSTANCE = new RequestQueueHandler();
    }

    public static RequestQueueHandler getInstance()
    {
        return HttpHolder.INSTANCE;
    }

    /**
     * <创建请求队列对象>
     * <功能详细描述>
     *
     * @param context Context
     *                返 回 类 型：void
     */
    public void init(Context context)
    {
        if (context == null)
        {
            throw new IllegalArgumentException("context must not be null");
        }
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    /**
     * <添加请求>
     * <功能详细描述>
     *
     * @param request request
     * @param <T>     返 回 类 型：void
     */
    public <T> void addRequest(Request<T> request)
    {
        requestQueue.add(request);
    }

    /**
     * <取消所有的请求>
     * <功能详细描述>
     *
     * @param tag 返 回 类 型：void
     */
    public void cancelAll(Object tag)
    {
        requestQueue.cancelAll(tag);
    }
}
