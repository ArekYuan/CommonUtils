package com.yaohl.okhttplib.okhttp;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.yaohl.okhttplib.log.YLog;
import com.yaohl.okhttplib.okhttp.DownLoadFileCallBack;
import com.yaohl.okhttplib.okhttp.ErrorResponse;
import com.yaohl.okhttplib.okhttp.FileProgressCallBack;
import com.yaohl.okhttplib.okhttp.GsonHelp;
import com.yaohl.okhttplib.okhttp.INetCallBack;
import com.yaohl.okhttplib.okhttp.OkUiCallBack;
import com.yaohl.okhttplib.utils.SeverConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
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

public class OkHttpService {

    /**
     * 默认连接超时时间
     */
    public static final int CONNECTION_TIME_OUT_DEFAULT = 15000;

    /**
     * 默认读取超时时间
     */
    public static final int READ_TIME_OUT_DEFAULT = 10000;

    /**
     * 默认写超时时间
     */
    public static final int WRITE_TIME_OUT_DEFAULT = 10000;

    /**
     * 获取成功
     */
    public static final int ON_SUCCESS_REQUIRED = 100001;

    /**
     * 获取 失败
     */
    public static final int ON_FAILED_REQUIRED = 100002;

    /**
     * 成功下载文件
     */
    public static final int ON_DOWNLOAD_FILE_SUCCESS = 100003;

    /**
     * 下载文件失败
     */
    public static final int ON_DOWNLOAD_FILE_FAILED = 100004;

    /**
     * 获取Cookie 成功
     */
    public static final int ON_COOKIE_SUCCESS = 100005;

    public static OkUiCallBack callBack = null;

    public static final int ON_COOKIE_FAILED = 100006;

    public static final int ON_JOSNREQUEST_SUCCESS = 100007;

    public static final int ON_JOSNREQUEST_FAILED = 100008;

    public static final int ON_UPLOAD_SUCCESS = 100009;

    public static final int ON_UPLOAD_FAILED = 100010;

    private static final int ON_GET_PARAMS_SUCCESS = 100011;

    private static final int ON_GET_PARAMS_FAILED = 100012;

    /**
     * 缓存文件最大限制大小20M
     */
    private static final long cacheSize = 1024 * 1024 * 20;

    /**
     * 设置缓存文件路径
     */
    private static String cacheDirectory = Environment.getExternalStorageDirectory() + "/lan_gold/caches";
    private static Cache cache = new Cache(new File(cacheDirectory), cacheSize);

    /**
     * hanler  子线程
     */
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ON_SUCCESS_REQUIRED://获取json成功
                    if (callBack != null) {
                        Object ob = msg.obj;
                        callBack.onComplete(ob);
                    }
                    break;
                case ON_FAILED_REQUIRED://获取json失败
                    if (callBack != null) {
                        String errorMessage = (String) msg.obj;
                        callBack.onFail(errorMessage);
                    }
                    break;
                case ON_COOKIE_SUCCESS:
                    if (callBack != null) {
                        String cookie = (String) msg.obj;
//                        callBack.onCookie(cookie);
                    }
                    break;
                case ON_COOKIE_FAILED:
                    if (callBack != null) {
                        callBack.onFail("");
                    }
                    break;
                case ON_JOSNREQUEST_SUCCESS:
                    if (callBack != null) {
                        Object ob = msg.obj;
                        callBack.onComplete(ob);
                    }
                    break;

                case ON_JOSNREQUEST_FAILED:
                    if (callBack != null) {
                        String message = (String) msg.obj;
                        callBack.onFail(message);
                    }
                    break;

                case ON_UPLOAD_SUCCESS:
                    if (callBack != null) {
                        String message = (String) msg.obj;
                        callBack.onComplete(message);
                    }
                    break;

                case ON_UPLOAD_FAILED:
                    if (callBack != null) {
                        String message = (String) msg.obj;
                        callBack.onFail(message);
                    }
                    break;

                case ON_GET_PARAMS_SUCCESS:
                    if (callBack != null) {
                        Object ob = msg.obj;
                        callBack.onComplete(ob);
                    }
                    break;

                case ON_GET_PARAMS_FAILED:
                    if (callBack != null) {
                        String message = (String) msg.obj;
                        callBack.onFail(message);
                    }
                    break;

                case ON_DOWNLOAD_FILE_SUCCESS://下载文件成功
                    break;
                case ON_DOWNLOAD_FILE_FAILED://下载文件失败
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 访问网络单子例对象
     */
    private static OkHttpClient client = null;

    private OkHttpService() {
    }

    /**
     * <返回访问网络单子例对象> <功能详细描述>
     *
     * @return 返 回 类 型：ConnectSersvice
     */
    public static OkHttpClient getInstance() {
        if (client == null) {
            synchronized (OkHttpService.class) {
                if (client == null) {
                    client = new OkHttpClient();
                    client.newBuilder().connectTimeout(CONNECTION_TIME_OUT_DEFAULT, TimeUnit.MILLISECONDS)
                            .readTimeout(READ_TIME_OUT_DEFAULT, TimeUnit.MILLISECONDS)
                            .writeTimeout(WRITE_TIME_OUT_DEFAULT, TimeUnit.MICROSECONDS)
                            .retryOnConnectionFailure(true)
                            .cache(cache);
                }
            }
        }
        return client;
    }

    /***
     * Get请求
     * @param method
     * @param callBacks
     * @param obj
     * @param <T>
     */
    public static <T> void doGet(String method, final OkUiCallBack callBacks, final Class<T> obj) {
        callBack = callBacks;
        final Message message = new Message();
        String url = SeverConstants.SERVER_URL + method;
        doJsonGetData(url, new INetCallBack() {
            @Override
            public void onSuccess(Object ob) {
                message.obj = ob;
                message.what = ON_SUCCESS_REQUIRED;
                handler.sendMessage(message);
            }

            @Override
            public void onFail(String errorMessage) {
                message.obj = errorMessage;
                message.what = ON_FAILED_REQUIRED;
                handler.sendMessage(message);
            }

        }, obj);

    }

    /**
     * get 请求方式
     *
     * @param url
     * @param iNetCallBack
     * @param obj
     * @param <T>
     */
    private static <T> void doJsonGetData(final String url, final INetCallBack iNetCallBack, final Class<T> obj) {
        final Request request = new Request.Builder()
                .cacheControl(new CacheControl.Builder().maxAge(5, TimeUnit.MINUTES).build())
                .url(url)
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                YLog.d("url-->" + e.getMessage());
                onFailMethod(e.getMessage(), iNetCallBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody;
                Gson gson = new Gson();
                if (response.isSuccessful()) {
                    responseBody = response.body().string();
                    YLog.d(url + "<--->" + responseBody);
                    Object object = gson.fromJson(responseBody, obj);
                    onSuccessMethod(object, iNetCallBack);
                } else {
                    if (response.cacheResponse() != null) {
                        responseBody = response.cacheResponse().body().toString();
                        Object object = gson.fromJson(responseBody, obj);
                        onSuccessMethod(object, iNetCallBack);
                    } else {
                        doSetMessage(response.code(), obj, iNetCallBack);
                    }
                }


                //关闭防止内存泄漏
                if (response.body() != null) {
                    response.body().close();
                }
            }
        });
    }

    /**
     * 服务器错误 则返回 提示
     *
     * @param code
     * @param obj
     * @param iNetCallBack
     */
    private static <T> void doSetMessage(int code, Class<T> obj, INetCallBack iNetCallBack) {
        ErrorResponse resp = getErrorResponse(code);
        Gson gson = new Gson();
        String josnStr = GsonHelp.objectToJsonString(resp);
        Object object = gson.fromJson(josnStr, obj);
        iNetCallBack.onSuccess(object);

    }

    private static ErrorResponse getErrorResponse(int code) {
        ErrorResponse resp = new ErrorResponse();
        resp.setCode(String.valueOf(code));
        switch (code) {
            case 400:
                resp.setMsg("请求无效");
                break;
            case 404:
                resp.setMsg("资源无法访问");
                break;
            case 500:
                resp.setMsg("服务器错误");
                break;
        }
        resp.setData(new Object());
        return resp;
    }

    /**
     * get 带参数传递
     *
     * @param method    接口地址
     * @param params    参数
     * @param callBacks 回调
     * @param <T>       泛型
     */
    public static <T> void doGetParams(String method, Map<String, String> params, final OkUiCallBack callBacks) {
        callBack = callBacks;
        final Message message = new Message();
        String url = SeverConstants.SERVER_URL + method;
        doGetParamsData(url, params, new INetCallBack() {
            @Override
            public void onSuccess(Object ob) {
                message.what = ON_GET_PARAMS_SUCCESS;
                message.obj = ob;
                handler.sendMessage(message);
            }

            @Override
            public void onFail(String errorMessage) {
                message.what = ON_GET_PARAMS_FAILED;
                message.obj = errorMessage;
                handler.sendMessage(message);
            }
        });
    }

    /**
     * get 带参数传递
     *
     * @param method
     * @param params
     * @param iNetCallBack
     * @param <T>
     */
    private static <T> void doGetParamsData(String method, Map<String, String> params, final INetCallBack iNetCallBack) {
        StringBuilder tempParams = new StringBuilder();
        try {
            //处理参数
            int pos = 0;
            for (String key : params.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                //对参数进行URLEncoder
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(params.get(key), "utf-8")));
                pos++;
            }
            final String url = SeverConstants.SERVER_URL + method;
            //补全请求地址
            String requestUrl = String.format("%s?%s", url, tempParams.toString());
            final Request request = new Request.Builder()
                    .url(requestUrl)
                    .build();
            Call call = getInstance().newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onFailMethod(e.getLocalizedMessage(), iNetCallBack);
                    YLog.e(url + "<---->" + e.getLocalizedMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
//                        Gson gson = new Gson();
                        String respBody = response.body().string();
//                        Object object = gson.fromJson(respBody, obj);
                        YLog.d(url + "---->" + respBody);
                        onSuccessMethod(respBody, iNetCallBack);
                    }
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Post请求发送键值对数据
     *
     * @param method
     * @param mapParams
     * @param callBacks
     */
    public static <T> void doPost(String method, Map<String, String> mapParams, final OkUiCallBack callBacks, final Class<T> obj) {
        callBack = callBacks;
        final Message message = new Message();
        final String url = SeverConstants.SERVER_URL + method;
        doJsonPostData(url, mapParams, new INetCallBack() {
            @Override
            public void onSuccess(Object ob) {
                message.obj = ob;
                message.what = ON_SUCCESS_REQUIRED;
                handler.sendMessage(message);
//                callBacks.onComplete(ob);
            }

            @Override
            public void onFail(String errorMessage) {
                message.obj = errorMessage;
                message.what = ON_FAILED_REQUIRED;
                handler.sendMessage(message);
//                callBacks.onFail(errorMessage);
            }

        }, obj);
    }


    /**
     * post  请求 获取json 格式的数据
     *
     * @param url
     * @param mapParams
     * @param iNetCallBack
     * @param obj
     * @param <T>
     */
    private static <T> void doJsonPostData(final String url, Map<String, String> mapParams, final INetCallBack iNetCallBack, final Class<T> obj) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : mapParams.keySet()) {
            builder.add(key, mapParams.get(key));
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                YLog.d(url + "<---->" + e.getMessage());
                onFailMethod(e.getMessage(), iNetCallBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String responseBody = response.body().string();
                    Object object = gson.fromJson(responseBody, obj);
                    onSuccessMethod(object, iNetCallBack);
                    YLog.d(url + "<---->" + responseBody);


                } else {
                    doSetMessage(response.code(), obj, iNetCallBack);
                }

                //关闭防止内存泄漏
                if (response.body() != null) {
                    response.body().close();
                }
            }
        });
    }

    /**
     * 获取 Cookie
     *
     * @param method
     * @param mapParams
     * @param callBacks
     * @param <T>
     */
    public static <T> void doPostCookie(String method, Map<String, String> mapParams, final OkUiCallBack callBacks) {
        callBack = callBacks;
        final Message message = new Message();
        final String url = SeverConstants.SERVER_URL + method;
        doPostCookieData(url, mapParams, new INetCallBack() {
            @Override
            public void onSuccess(Object ob) {

            }

            @Override
            public void onFail(String errorMessage) {
                message.what = ON_COOKIE_FAILED;
                handler.sendMessage(message);
            }

//            @Override
//            public void onCookieSuccess(String cookie) {
//                message.obj = cookie;
//                message.what = ON_COOKIE_SUCCESS;
//                handler.sendMessage(message);
//            }
        });
    }

    /**
     * 获取 Cookie
     *
     * @param url
     * @param mapParams
     * @param iNetCallBack
     * @param <T>
     */
    public static <T> void doPostCookieData(final String url, Map<String, String> mapParams, final INetCallBack iNetCallBack) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : mapParams.keySet()) {
            builder.add(key, mapParams.get(key));
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                YLog.d(url + "<---->" + e.getMessage());
                onFailMethod(e.getMessage(), iNetCallBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Headers requestHeaders = response.networkResponse().request().headers();
                String cookie = response.headers().get("Set-Cookie");
                if (!TextUtils.isEmpty(cookie)) {
                    String cookieRepl = cookie.substring(0, 43);
//                    onPostSuccessCookie(cookieRepl, iNetCallBack);
                    YLog.d(url + "<---->" + cookieRepl);
                } else {
                    String message = "获取Cookie失败";
                    onFailMethod(message, iNetCallBack);
                    YLog.d(url + "<---->" + message);
                }

            }
        });

    }

    /**
     * Post请求发送JSON数据
     *
     * @param mthod
     * @param jsonParams
     * @param callBacks
     */
    public static <T> void doPostJson(String mthod, String jsonParams, final OkUiCallBack callBacks, final Class<T> obj) {
        callBack = callBacks;
        final Message message = new Message();
        final String url = SeverConstants.SERVER_URL + mthod;
        doPostData(url, jsonParams, new INetCallBack() {
            @Override
            public void onSuccess(Object ob) {
                message.obj = ob;
                message.what = ON_JOSNREQUEST_SUCCESS;
                handler.sendMessage(message);
            }

            @Override
            public void onFail(String errorMessage) {
                message.obj = errorMessage;
                message.what = ON_JOSNREQUEST_FAILED;
                handler.sendMessage(message);
            }

        }, obj);
    }

    /**
     * 使用json 格式请求数据
     *
     * @param url
     * @param jsonParams
     * @param iNetCallBack
     * @param obj
     * @param <T>
     */
    private static <T> void doPostData(final String url, String jsonParams, final INetCallBack iNetCallBack, final Class<T> obj) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8")
                , jsonParams);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                YLog.d(url + "<---->" + e.getMessage());
                onFailMethod(e.getLocalizedMessage(), iNetCallBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    Gson gson = new Gson();
                    String responseBody = response.body().string();
                    YLog.d(url + "<---->" + responseBody);
                    Object object = gson.fromJson(responseBody, obj);
                    onSuccessMethod(object, iNetCallBack);
                } else {
                    doSetMessage(response.code(), obj, iNetCallBack);
                }

                //关闭防止内存泄漏
                if (response.body() != null) {
                    response.body().close();
                }
            }
        });
    }

    /**
     * 上传文件
     *
     * @param pathName
     * @param fileName
     * @param uiCallBack
     */
    public static void doUpLoadFile(String method, String pathName, String fileName, OkUiCallBack uiCallBack) {
        callBack = uiCallBack;
        final Message message = new Message();
        final String url = SeverConstants.SERVER_URL + method;
        doUpLoadFileStream(url, pathName, fileName, new INetCallBack() {
            @Override
            public void onSuccess(Object ob) {
                message.obj = ob;
                message.what = ON_UPLOAD_SUCCESS;
                handler.sendMessage(message);
            }

            @Override
            public void onFail(String errorMessage) {
                message.obj = errorMessage;
                message.what = ON_UPLOAD_FAILED;
                handler.sendMessage(message);
            }
        });
    }

    /**
     * @param url
     * @param pathName
     * @param fileName
     * @param iNetCallBack
     */
    private static void doUpLoadFileStream(final String url, String pathName, String fileName, final INetCallBack iNetCallBack) {
        //判断文件类型
        MediaType MEDIA_TYPE = MediaType.parse(judgeType(pathName));
        //创建文件参数
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(MEDIA_TYPE.type(), fileName,
                        RequestBody.create(MEDIA_TYPE, new File(pathName)));
        //发出请求参数
        Request request = new Request.Builder()
//                .addHeader("Authorization", "Client-ID " + "9199fdef135c122")
                .url(url)
//                .addHeader("Cookie", "JSESSIONID=51B8AE1810240EE88DBAD600A8B1B1EF")
                .post(builder.build())
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                YLog.d(url + "<---->" + e.getLocalizedMessage());
                onFailMethod(e.getLocalizedMessage(), iNetCallBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String strS = response.body().string();
                    YLog.d(url + "<---->" + strS);
                    onSuccessMethod("上传成功", iNetCallBack);
                }
            }
        });
    }

    /**
     * 根据文件路径判断MediaType
     *
     * @param path
     * @return
     */
    private static String judgeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }


    /**
     * 下载文件 用户更新 文件时需要
     *
     * @param method
     * @param fileDir
     * @param fileName
     */
    public static void downLoadFile(String method, final String fileDir, final String fileName, final FileProgressCallBack callBack) {
        String url = SeverConstants.SERVER_URL + method;
        doDownloadFile(url, fileDir, fileName, new DownLoadFileCallBack() {
            @Override
            public void onSuccess(long total, long current) {
                callBack.fileProgressChange(total, current);
            }

            @Override
            public void onDownLoadFinish(File file) {
                callBack.fileFinishDownLoad(file);
            }

            @Override
            public void onFail(String errorMessage) {
                callBack.fileProgressFailed(errorMessage);
            }
        });

    }

    /**
     * 下载 文件
     *
     * @param url
     * @param fileDir
     * @param fileName
     * @param callBack
     */
    private static void doDownloadFile(final String url, String fileDir, String fileName, final DownLoadFileCallBack callBack) {
        final File file = new File(fileDir, fileName);
        if (file.exists()) {
            file.deleteOnExit();
        }
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFail(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    long total = response.body().contentLength();
                    YLog.d(url + "<--total-->" + total);
                    long current = 0;
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        current += len;
                        fos.write(buf, 0, len);
                        YLog.d(url + "<--current-->" + current);
                        callBack.onSuccess(total, current);
                        if (total == current) {
                            callBack.onDownLoadFinish(file);
                        }
                    }
                    fos.flush();
                } catch (IOException e) {
                    YLog.d(e);
                    callBack.onFail("下载失败");
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        YLog.d(e);
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
    public static void onFailMethod(String errorMessage, INetCallBack requestListener) {
        if (requestListener != null) {
            requestListener.onFail(errorMessage);
        }
    }

    /**
     * <请求成功返回结果> <功能详细描述>
     *
     * @param response        response
     * @param requestListener requestListener 返 回 类 型：void
     */
    public static void onSuccessMethod(Object response, INetCallBack requestListener) {
        if (requestListener != null) {
            requestListener.onSuccess(response);
        }
    }

    /**
     * @param cookie
     * @param requestListener
     */
    public static void onPostSuccessCookie(String cookie, INetCallBack requestListener) {
        if (requestListener != null) {
//            requestListener.onCookieSuccess(cookie);
        }
    }

}
