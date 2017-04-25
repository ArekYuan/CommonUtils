package com.yaohl.volleylib.volley;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

import com.duowan.mobile.netroid.Network;
import com.duowan.mobile.netroid.RequestQueue;
import com.duowan.mobile.netroid.stack.HttpClientStack;
import com.duowan.mobile.netroid.stack.HttpStack;
import com.duowan.mobile.netroid.stack.HurlStack;
import com.duowan.mobile.netroid.toolbox.BasicNetwork;

import org.apache.http.protocol.HTTP;

/**
 * 文 件 名:  NetRequestQueueHandler.java
 * 版    权:
 * 描    述:  <下载大文件请求队列管理类>
 * 版    本： <版本号>
 * 创 建 人:
 * 创建时间:  2015年10月22日
 */
public class NetRequestQueueHandler
{
    /**
     * 请求队列
     */
    private static RequestQueue NETROIDREQUESTQUEUE;

    /**
     * 默认为4
     */
    private int poolSize = RequestQueue.DEFAULT_NETWORK_THREAD_POOL_SIZE;

    /**
     * 文 件 名:  RequestQueueManager.java
     * 版    权:  cttq
     * 描    述:  <创建下载请求队列管理类对象>
     * 版    本： <版本号>
     * 创 建 人:  姜飞
     * 创建时间:  2015年10月19日
     */
    static class NetroidHttpHolder
    {
        /**
         * 创建请求队列管理类对象
         */
        private static NetRequestQueueHandler NETROIDINSTANCE = new NetRequestQueueHandler();
    }

    /**
     * <获取NetRequestQueueHandler对象>
     * <功能详细描述>
     *
     * @return 返 回 类 型：NetRequestQueueHandler
     */
    public static NetRequestQueueHandler getInstance()
    {
        return NetroidHttpHolder.NETROIDINSTANCE;
    }

    /**
     * <获取RequestQueue对象>
     * <功能详细描述>
     *
     * @return 返 回 类 型：RequestQueue
     */
    public static RequestQueue getNetroidRequestQueue()
    {
        return NETROIDREQUESTQUEUE;
    }

    /**
     * <建议并行任务数上限不超过3，在手机带宽有限的条件下，并行任务数的扩大无法加快下载速度。>
     * <如果并行任务数上限大于或等于RequestQueue中的总线程数，将被视为不合法而抛出异常。>
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
        HttpStack stack;
        String userAgent = "netroid/0";
        try
        {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        }
        catch (NameNotFoundException e)
        {
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
        {
            stack = new HurlStack(userAgent, null);
        }
        else
        {
            // Prior to Gingerbread, HttpUrlConnection was unreliable.
            // See: http://android-developers.blogspot.com/2011/09/androids-http-clients.html
            stack = new HttpClientStack(userAgent);
        }

        Network network = new BasicNetwork(stack, HTTP.UTF_8);
        NETROIDREQUESTQUEUE = new RequestQueue(network, poolSize, null);
    }
}
