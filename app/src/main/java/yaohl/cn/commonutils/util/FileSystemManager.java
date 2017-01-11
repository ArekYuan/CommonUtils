package yaohl.cn.commonutils.util;

import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.Date;

/**
 * 文 件 名:  FileSystemManager.java
 * 版    权:  com
 * 描    述:  <描述>
 * 版    本： <版本号>
 * 创 建 人:  YuanGuangYue
 * 创建时间:  2016年8月1日
 */
public class FileSystemManager
{
    /**
     * 根目录缓存目录
     */
    private static String CACHEFILEPATH;

    /**
     * 列表页面图片缓存目录
     */
    private static String CACHEIMGFILEPATH;

    /**
     * 用户头像缓存目录
     */
    private static String USERHEADPATH;

    /**
     * 崩溃日志缓存目录（上传成功则删除）
     */
    private static String CRASHPATH;

    /**
     * 临时目录
     */
    private static String TEMPORARYPATH;

    /**
     * 版本更新的目录
     */
    private static String VERSIONUPDATE;

    /**
     * 患者上传图片缓存目录
     */
    public static String PATINETIMGPATH;

    /**
     * <根目录缓存目录>
     * <功能详细描述>
     *
     * @param context context
     *
     * @return 返 回 类 型：String
     */
    public static String getCacheFilePath(Context context)
    {
        CACHEFILEPATH = FileUtil.getSDPath(context) + File.separator;
        return CACHEFILEPATH;
    }

    /**
     * <列表页面图片缓存目录>
     * <功能详细描述>
     *
     * @param context context
     *
     * @return 返 回 类 型：String
     */
    public static String getCacheImgFilePath(Context context, String url)
    {
        //        CACHEIMGFILEPATH =
        //            FileUtil.createNewFile(getCacheFilePath(context) + "Cttq" + File.separator + "Cache" + File.separator);
        //        CACHEIMGFILEPATH = getCacheFilePath(context) + "Cttq" + File.separator + "Cache" + File.separator;
        //        CACHEIMGFILEPATH = new ImageLoader().getDiscCache().get("").getPath();
        CACHEIMGFILEPATH = ImageLoader.getInstance().getDiscCache().get(url).getPath();
        return CACHEIMGFILEPATH;
    }

    /**
     * <用户头像缓存目录>
     * <功能详细描述>
     *
     * @param context context
     *
     * @return 返 回 类 型：String
     */
    public static String getUserHeadPath(Context context)
    {
        USERHEADPATH = FileUtil.createNewFile(getCacheFilePath(context) + "blinroom" + File.separator + "cache"
                                                      + File.separator + "student" + File.separator);
        return USERHEADPATH;
    }

    /**
     * <用户头像缓存目录>
     * <功能详细描述>
     *
     * @param context context
     *
     * @return 返 回 类 型：String
     */
    public static String getUserHeadPathTemp(Context context)
    {
        USERHEADPATH = FileUtil.createNewFile(getCacheFilePath(context) + "blinroom" + File.separator + "cache"
                                                      + File.separator + "student" + File.separator + "head_temp" + File.separator);
        return USERHEADPATH;
    }

    /**
     * <用户认证图片缓存目录>
     * <功能详细描述>
     *
     * @param context context
     *
     * @return 返 回 类 型：String
     */
    public static String getRegisterPathTemp(Context context)
    {
        USERHEADPATH = FileUtil.createNewFile(getCacheFilePath(context) + "blinroom" + File.separator + "cache"
                                                      + File.separator + "student" + File.separator + "register" + File.separator);
        return USERHEADPATH;
    }

    /**
     * <用户头像缓存目录>
     * <功能详细描述>
     *
     * @param context context
     *
     * @return 返 回 类 型：String
     */
    public static String getPatientImgPath(Context context)
    {
        USERHEADPATH = FileUtil.createNewFile(getCacheFilePath(context) + "blinroom" + File.separator + "cache"
                                                      + File.separator + "student" + File.separator);
        return USERHEADPATH;
    }

    /**
     * <用户头像缓存目录>
     * <功能详细描述>
     *
     * @param context context
     *
     * @return 返 回 类 型：String
     */
    public static String getPatientUserHeadPathTemp(Context context)
    {
        USERHEADPATH = FileUtil.createNewFile(getCacheFilePath(context) + "blinroom" + File.separator + "cache"
                                                      + File.separator + "student" + File.separator + "head_temp" + File.separator);
        return USERHEADPATH;
    }

    /**
     * <患者上传图片缓存目录>
     * <功能详细描述>
     *
     * @param context context
     *
     * @return 返 回 类 型：String
     */
    public static String getPatientImgPathTemp(Context context)
    {
        PATINETIMGPATH = FileUtil.createNewFile(getCacheFilePath(context) + "blinroom" + File.separator + "cache"
                                                        + File.separator + "student" + File.separator + "imgs" + File.separator);
        return PATINETIMGPATH;
    }

    /**
     *
     * <崩溃日志缓存目录（上传成功则删除）>
     * <功能详细描述>
     * @param context context
     * @return
     * 返 回 类 型：String
     */
    //    public static String getCrashPath(Context context)
    //    {
    //        CRASHPATH =
    //            FileUtil.createNewFile(getCacheFilePath(context)
    //                + "cttq"
    //                + File.separator
    //                + "crash"
    //                + File.separator
    //                + DateFormatUtils.splitToLocalDateHour(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date(
    //                    System.currentTimeMillis()))) + FileUtil.ERROR_LOG_SUFFIX);
    //        return CRASHPATH;
    //    }

    /**
     * <崩溃日志缓存目录（上传成功则删除）>
     * <功能详细描述>
     *
     * @param context context
     *
     * @return 返 回 类 型：String
     */
    public static String getCrashPath(Context context)
    {
        CRASHPATH = FileUtil.createNewFile(getCacheFilePath(context) + "blinroom" + File.separator + "crash"
                                                   + File.separator + new Date(System.currentTimeMillis()) + FileUtil.ERROR_LOG_SUFFIX);
        return CRASHPATH;
    }

    /**
     * <临时图片缓存目录>
     * <功能详细描述>
     *
     * @param context context
     *
     * @return 返 回 类 型：String
     */
    public static String getTemporaryPath(Context context)
    {
        TEMPORARYPATH = FileUtil.createNewFile(getCacheFilePath(context) + "temp" + File.separator);
        return TEMPORARYPATH;
    }

    /**
     * <版本更新目录>
     * <功能详细描述>
     *
     * @param context context
     *
     * @return 返 回 类 型：String
     */
    public static String getVersionUpdatePath(Context context)
    {
        VERSIONUPDATE = FileUtil.createNewFile(getCacheFilePath(context) + "versionupdate" + File.separator);
        return VERSIONUPDATE;
    }

}