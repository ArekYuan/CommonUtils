package cn.yaohl.MayorOnline.util.retrofitLib;


import com.yaohl.retrofitlib.utils.SeverConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：袁光跃
 * 日期：2018/1/17
 * 描述：retrofit 管理类
 * 邮箱：813665242@qq.com
 */

public class RetrofitManager {

    private static final long CONNECT_TIME_OUT = 20;
    private static final long READ_TIME_OUT = 20;

    public static Retrofit getInstance() {
        Retrofit retrofit;
        synchronized (Retrofit.class) {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();
            client.dispatcher().setMaxRequests(30);

            retrofit = new Retrofit.Builder()
                    .baseUrl(SeverConstants.SERVER_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
