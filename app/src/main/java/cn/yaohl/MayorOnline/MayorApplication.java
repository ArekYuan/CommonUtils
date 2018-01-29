package cn.yaohl.MayorOnline;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.yaohl.retrofitlib.log.YLog;

import java.io.File;
import java.util.Stack;

import cn.yaohl.MayorOnline.sharepref.SharePref;
import cn.yaohl.MayorOnline.ui.BaseActivity;


/**
 * 作者：袁光跃
 * 日期：2018/1/3
 * 描述：
 * 邮箱：813665242@qq.com
 */

public class MayorApplication extends Application {

    /**
     * 本地栈
     */
    private Stack<BaseActivity> activitys = null;

    /**
     * app 实例
     */
    private static MayorApplication PDM_APPLICATION = null;

    public static Context applicationContext;


    public synchronized static MayorApplication getInstance() {
        if (null == PDM_APPLICATION) {
            PDM_APPLICATION = new MayorApplication();
        }
        return PDM_APPLICATION;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        PDM_APPLICATION = this;
        if (activitys == null) {
            activitys = new Stack<>();
        }
        YLog pdmLog = YLog.getInstance();
        pdmLog.setISDEBUG(true, this);//初始化log
//        initApp();
        // 初始化ImageLoader
        ImageLoader.getInstance().init(getImageLoaderConfiguration(getApplication()));
//        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);
    }

    /**
     * <添加activity> <功能详细描述>
     *
     * @param activity activity 返 回 类 型：void
     */
    public void addActivity(BaseActivity activity) {
        activitys.add(activity);
    }

    /**
     * <获得当前栈顶Activity> <功能详细描述>
     *
     * @return activity 返 回 类 型：Activity
     */
    public BaseActivity currentActivity() {
        BaseActivity activity = null;
        if (!activitys.empty()) {
            activity = activitys.lastElement();
        }
        return activity;
    }

    /**
     * <删除activity栈中一个activity> <功能详细描述>
     *
     * @param activity activity 返 回 类 型：void
     */
    public void deleteActivity(Activity activity) {
        if (activity != null) {
            activitys.remove(activity);
            // 取消请求
            activity.finish();
            activity = null;
        }
    }

    /**
     * <退出应用程序，清除activity栈中所有的activity> <功能详细描述> 返 回 类 型：void
     */
    public void exit() {
        for (Activity activity : activitys) {
            activity.finish();
            activity = null;
        }
        activitys.clear();
        // 退出程序 (会导致出现异常时，重启应用程序两次)
        // android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /***
     * <清除所有activitys> <功能详细描述> 返回类型:void
     */
    public void clearAllActivities() {
        for (Activity activity : activitys) {
            activity.finish();
            activity = null;
        }
        activitys.clear();
    }

    /**
     * <获取本地activity栈> <功能详细描述>
     *
     * @return 返 回 类 型：Stack<Activity>
     */
    public Stack<BaseActivity> getActivitys() {
        return PDM_APPLICATION.activitys;
    }

    public static MayorApplication getApplication() {
        return PDM_APPLICATION;
    }

    /**
     * <获得图片加载器ImageLoader的配置参数对象> <功能详细描述>
     *
     * @param context context
     * @return 返 回 类 型：ImageLoaderConfiguration
     */
    public static ImageLoaderConfiguration getImageLoaderConfiguration(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "cache/suning");
        ImageLoaderConfiguration imageLoaderConfiguration =
                new ImageLoaderConfiguration.Builder(context).memoryCacheExtraOptions(480, 800) // maxwidth, max
                        // height，即保存的每个缓存文件的最大长宽
                        .threadPoolSize(3)
                        // 线程池内加载的数量
                        .threadPriority(Thread.NORM_PRIORITY - 2)
                        .denyCacheImageMultipleSizesInMemory()
                        .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                        // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                        .memoryCacheSize(2 * 1024 * 1024)
                        .discCacheSize(20 * 1024 * 1024)
                        .discCacheFileNameGenerator(new Md5FileNameGenerator())
                        // 将保存的时候的URI名称用MD5 加密
                        .tasksProcessingOrder(QueueProcessingType.LIFO)
                        .discCacheFileCount(50)
                        // 缓存的文件数量
                        .discCache(new UnlimitedDiscCache(cacheDir))
                        // 自定义缓存路径
                        // .defaultDisplayImageOptions(option)
                        // .imageDownloader(new BaseImageDownloader(this,5 * 1000, 30 * 1000)) // connectTimeout (5 s),
                        // readTimeout (30 s)超时时间
                        .writeDebugLogs()
                        // Remove for releaseapp
                        .build();// 开始构建
        return imageLoaderConfiguration;
    }

    /**
     * 退出 登录
     *
     * @param mContext
     */
    public void logintOut(Context mContext) {
        SharePref pref = new SharePref(mContext);
        pref.clearData();
    }
}
