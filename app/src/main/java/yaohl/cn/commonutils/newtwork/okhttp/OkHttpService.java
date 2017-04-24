package yaohl.cn.commonutils.newtwork.okhttp;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * okHttp service
 * <p>
 * Created by ygy on 2017/4/19.
 */

public class OkHttpService
{

    /**
     * 默认连接超时时间
     */
    public static final int CONNECTION_TIME_OUT_DEFAULT = 20000;

    /**
     * 默认读取超时时间
     */
    public static final int READ_TIME_OUT_DEFAULT = 60000;

    /**
     * 默认写超时时间
     */
    public static final int WRITE_TIME_OUT_DEFAULT = 60000;

    /**
     * 访问网络单子例对象
     */
    private static OkHttpClient client = null;

    private OkHttpService()
    {
    }

    /**
     * <返回访问网络单子例对象> <功能详细描述>
     *
     * @return 返 回 类 型：ConnectSersvice
     */
    public static OkHttpClient getInstance()
    {
        if (client == null)
        {
            synchronized (OkHttpService.class)
            {
                if (client == null)
                {
                    client = new OkHttpClient();
                    client.newBuilder().connectTimeout(CONNECTION_TIME_OUT_DEFAULT, TimeUnit.MILLISECONDS)
                            .readTimeout(READ_TIME_OUT_DEFAULT, TimeUnit.MILLISECONDS)
                            .writeTimeout(WRITE_TIME_OUT_DEFAULT, TimeUnit.MICROSECONDS);
                }
            }
        }
        return client;
    }

    /***
     * Get请求
     * @param tag
     * @param url
     * @param callBacks
     * @param obj
     * @param <T>
     */
    public static <T> void doGet(String tag, String url, final UiCallBack callBacks, final Class<T> obj)
    {
        doJsonGetData(tag, url, new INetCallBack()
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
     * get 请求方式
     *
     * @param tag
     * @param url
     * @param iNetCallBack
     * @param obj
     * @param <T>
     */
    private static <T> void doJsonGetData(final String tag, final String url, final INetCallBack iNetCallBack, final Class<T> obj)
    {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                Log.e(tag, url + "<---->" + e.getMessage());
                onFailMethod(e.getMessage(), iNetCallBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                if (response.isSuccessful())
                {
                    Gson gson = new Gson();
                    String responseBody = response.body().toString();
                    Object object = gson.fromJson(responseBody, obj);
                    onSuccessMethod(object, iNetCallBack);
                }

                //关闭防止内存泄漏
                if (response.body() != null)
                {
                    response.body().close();
                }
            }
        });
    }

    /**
     * Post请求发送键值对数据
     *
     * @param url
     * @param mapParams
     * @param callback
     */
    /**
     * Post请求发送键值对数据
     *
     * @param url
     * @param mapParams
     * @param callBacks
     */
    public static <T> void doPost(String tag, String url, Map<String, String> mapParams, final UiCallBack callBacks, final Class<T> obj)
    {

        doJsonPostData(tag, url, mapParams, new INetCallBack()
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
     * post  请求 获取json 格式的数据
     *
     * @param tag
     * @param url
     * @param mapParams
     * @param iNetCallBack
     * @param obj
     * @param <T>
     */
    private static <T> void doJsonPostData(final String tag, final String url, Map<String, String> mapParams, final INetCallBack iNetCallBack, final Class<T> obj)
    {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : mapParams.keySet())
        {
            builder.add(key, mapParams.get(key));
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                Log.e(tag, url + "<---->" + e.getMessage());
                onFailMethod(e.getMessage(), iNetCallBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                if (response.isSuccessful())
                {
                    Gson gson = new Gson();
                    String responseBody = response.body().string();
                    Object object = gson.fromJson(responseBody, obj);
                    onSuccessMethod(object, iNetCallBack);
                }

                //关闭防止内存泄漏
                if (response.body() != null)
                {
                    response.body().close();
                }
            }
        });
    }

    /**
     * Post请求发送JSON数据
     *
     * @param url
     * @param jsonParams
     * @param callback
     */
    public static void doPost(String url, String jsonParams, Callback callback)
    {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8")
                , jsonParams);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }

    /**
     * 上传文件
     *
     * @param url
     * @param pathName
     * @param fileName
     * @param callback
     */
    public static void doUpLoadFile(String url, String pathName, String fileName, Callback callback)
    {
        //判断文件类型
        MediaType MEDIA_TYPE = MediaType.parse(judgeType(pathName));
        //创建文件参数
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(MEDIA_TYPE.type(), fileName,
                                 RequestBody.create(MEDIA_TYPE, new File(pathName)));
        //发出请求参数
        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + "9199fdef135c122")
                .url(url)
                .post(builder.build())
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);

    }

    /**
     * 根据文件路径判断MediaType
     *
     * @param path
     * @return
     */
    private static String judgeType(String path)
    {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null)
        {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }


    /**
     * 下载文件 用户更新 文件时需要
     *
     * @param url
     * @param fileDir
     * @param fileName
     */
    public static void downLoadFile(final String tag, String url, final String fileDir, final String fileName, final FileProgressCallBack callBack, Context mContext)
    {
        doDownloadFile(tag, url, fileDir, fileName, new DownLoadFileCallBack()
        {
            @Override
            public void onSuccess(long total, long current)
            {
                callBack.fileProgressChange(total, current);
            }

            @Override
            public void onFail(String errorMessage)
            {
                callBack.fileProgressFailed(errorMessage);
            }
        }, mContext);

    }

    /**
     * 下载 文件
     *
     * @param tag
     * @param url
     * @param fileDir
     * @param fileName
     * @param callBack
     * @param mContext
     */
    private static void doDownloadFile(final String tag, String url, String fileDir, String fileName, final DownLoadFileCallBack callBack, Context mContext)
    {
        final File file = new File(fileDir, fileName);
        if (file.exists())
        {
            file.deleteOnExit();
        }
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                callBack.onFail(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try
                {
                    long total = response.body().contentLength();
                    Log.e(tag, "total------>" + total);
                    long current = 0;
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1)
                    {
                        current += len;
                        fos.write(buf, 0, len);
                        Log.e(tag, "current------>" + current);
                        callBack.onSuccess(total, current);
                    }
                    fos.flush();
                }
                catch (IOException e)
                {
                    Log.e(tag, e.toString());
                    callBack.onFail("下载失败");
                } finally
                {
                    try
                    {
                        if (is != null)
                        {
                            is.close();
                        }
                        if (fos != null)
                        {
                            fos.close();
                        }
                    }
                    catch (IOException e)
                    {
                        Log.e(tag, e.toString());
                    }
                }
            }
        });
    }

    /**
     * <请求失败message> <功能详细描述>
     *
     * @param errorMessage    错误信息
     * @param requestListener requestListener 返 回 类 型：void
     */
    public static void onFailMethod(String errorMessage, INetCallBack requestListener)
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
    public static void onSuccessMethod(Object response, INetCallBack requestListener)
    {
        if (requestListener != null)
        {
            requestListener.onSuccess(response);
        }
    }

}
