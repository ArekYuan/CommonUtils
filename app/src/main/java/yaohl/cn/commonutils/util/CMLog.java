package yaohl.cn.commonutils.util;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文 件 名:  CMLog.java
 * 版    权:  com
 * 描    述:  <描述>
 * 版    本： <版本号>
 * 创 建 人:  YuanGuangYue
 * 创建时间:  2016年8月1日
 */
public final class CMLog
{
    /**
     * TAG
     */
    private static final String TAG = CMLog.class.getName();

    /**
     * 错误日志后缀
     */
    public static final String ERROR_LOG_SUFFIX = ".log";

    /**
     * Define the log priority.
     */
    private static LogType LOGTYPE;

    /**
     * 是否输出日志 未上綫 不用寫在本地
     */
    public static boolean isDebugModel = false;

    static
    {
        //  发布的时候改成asset
        setLogType(LogType.asset);
    }

    private CMLog()
    {
    }

    /**
     * <获得log级别>
     * <功能详细描述>
     *
     * @return 返 回 类 型：LogType
     */
    public static LogType getLogType()
    {
        return LOGTYPE;
    }

    /**
     * <设置log级别>
     * <功能详细描述>
     *
     * @param logType 返 回 类 型：void
     */
    public static void setLogType(LogType logType)
    {
        CMLog.LOGTYPE = logType;
        i(TAG, "logType: " + logType);
    }

    /**
     * <发送verbose日志>
     * <功能详细描述>
     *
     * @param tag tag
     * @param msg message
     *
     * @return 返 回 类 型：int
     */
    public static int v(String tag, String msg)
    {
        return cloudCentralPrintln(LogType.verbose.value(), tag, msg);
    }

    /**
     * <发送verbose日志消息和日志异常>
     * <功能详细描述>
     *
     * @param tag tag
     * @param msg message
     * @param tr  异常信息
     *
     * @return 返 回 类 型：int
     */
    public static int v(String tag, String msg, Throwable tr)
    {
        return cloudCentralPrintln(LogType.verbose.value(), tag, msg + '\n' + getStackTraceString(tr));
    }

    /**
     * <发送debug日志>
     * <功能详细描述>
     *
     * @param tag tag
     * @param msg message
     *
     * @return 返 回 类 型：int
     */
    public static int d(String tag, String msg)
    {
        return cloudCentralPrintln(LogType.debug.value(), tag, msg);
    }

    /**
     * <发送debug日志和异常信息>
     * <功能详细描述>
     *
     * @param tag tag
     * @param msg message
     * @param tr  异常信息
     *
     * @return 返 回 类 型：int
     */
    public static int d(String tag, String msg, Throwable tr)
    {
        return cloudCentralPrintln(LogType.debug.value(), tag, msg + '\n' + getStackTraceString(tr));
    }

    /**
     * <发送info日志>
     * <功能详细描述>
     *
     * @param tag tag
     * @param msg message
     *
     * @return 返 回 类 型：int
     */
    public static int i(String tag, String msg)
    {
        return cloudCentralPrintln(LogType.info.value(), tag, msg + "");
    }

    /**
     * <发送info日志和异常信息>
     * <功能详细描述>
     *
     * @param tag tag
     * @param msg message
     * @param tr  异常信息
     *
     * @return 返 回 类 型：int
     */
    public static int i(String tag, String msg, Throwable tr)
    {
        return cloudCentralPrintln(LogType.info.value(), tag, msg + '\n' + getStackTraceString(tr));
    }

    /**
     * <发送warn日志信息>
     * <功能详细描述>
     *
     * @param tag tag
     * @param msg message
     *
     * @return 返 回 类 型：int
     */
    public static int w(String tag, String msg)
    {
        return cloudCentralPrintln(LogType.warn.value(), tag, msg + "");
    }

    /**
     * <发送warn日志和异常信息>
     * <功能详细描述>
     *
     * @param tag tag
     * @param msg message
     * @param tr  异常信息
     *
     * @return 返 回 类 型：int
     */
    public static int w(String tag, String msg, Throwable tr)
    {
        return cloudCentralPrintln(LogType.warn.value(), tag, msg + '\n' + getStackTraceString(tr));
    }

    /**
     *
     * <检查log的级别>
     * <功能详细描述>
     * @param tag tag
     * @param level 检查LogType。log的级别
     * @return
     * 返 回 类 型：boolean
     */
    //public static native boolean isLoggable(String tag, int level);

    /**
     * <发送warn日志信息>
     * <功能详细描述>
     *
     * @param tag tag
     * @param tr  tr
     *
     * @return 返 回 类 型：int
     */
    public static int w(String tag, Throwable tr)
    {
        return cloudCentralPrintln(LogType.warn.value(), tag, getStackTraceString(tr));
    }

    /**
     * <发送error日志信息>
     * <功能详细描述>
     *
     * @param tag tag
     * @param msg message
     *
     * @return 返 回 类 型：int
     */
    public static int e(final String tag, final String msg)
    {
        return cloudCentralPrintln(LogType.error.value(), tag, msg + "");
    }

    /**
     * <发送error日志信息>
     * <功能详细描述>
     *
     * @param tag tag
     * @param msg message
     *
     * @return 返 回 类 型：int
     */
    public static int e(final String tag, final String msg, final Context context)
    {
        if (isDebugModel)
        {
            new Thread()
            {
                @Override
                public void run()
                {
                    write(time() + tag + " [DEBUG] --> " + msg + "\n", context);
                }

                ;

            }.start();
        }
        return cloudCentralPrintln(LogType.error.value(), tag, msg);
    }

    /**
     * <发送error日志和异常信息>
     * <功能详细描述>
     *
     * @param tag tag
     * @param msg message
     * @param tr  tr
     *
     * @return 返 回 类 型：int
     */
    public static int e(final String tag, final String msg, Throwable tr)
    {
        return cloudCentralPrintln(LogType.error.value(), tag, msg + "\n" + getStackTraceString(tr));
    }

    /**
     * Handy function to get a loggable stack trace from a Throwable
     *
     * @param tr
     *            An exception to log
     */
    /**
     * <得到一个错误信息>
     * <功能详细描述>
     *
     * @param tr tr
     *
     * @return 返 回 类 型：String
     */
    private static String getStackTraceString(Throwable tr)
    {
        if (tr == null)
        {
            return "";
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * <打印优先级别高于设置级别的日志信息>
     * <功能详细描述>
     *
     * @param priority 优先级
     * @param tag      tag
     * @param msg      message
     *
     * @return 返 回 类 型：int
     */
    private static int cloudCentralPrintln(int priority, String tag, String msg)
    {
        int logType;
        if (priority >= LOGTYPE.value())
        {
            logType = android.util.Log.println(priority, tag, msg + "");
        }
        else
        {
            logType = -1;
        }
        return logType;
    }

    /**
     * 文 件 名:  CMLog.java
     * 版    权:  cttq
     * 描    述:  <设置日志级别>
     * 版    本： <版本号>
     * 创 建 人:  姜飞
     * 创建时间:  2015年10月14日
     */
    public static enum LogType
    {
        /**
         * 日志对应级别
         */
        verbose(2), debug(3), info(4), warn(5), error(6), asset(7);

        /**
         * 级别类型
         */
        private final int type;

        /**
         * <默认构造函数>
         */
        private LogType(int type)
        {
            this.type = type;
        }

        /**
         * <级别值>
         * <功能详细描述>
         *
         * @return 返 回 类 型：int
         */
        public int value()
        {
            return type;
        }

        /**
         * <获得日志对应的级别>
         * <功能详细描述>
         *
         * @param i 值
         *
         * @return 返 回 类 型：LogType
         */
        public static LogType instanse(int i)
        {
            LogType type = verbose;
            switch (i)
            {
                case 2:
                    type = verbose;
                    break;
                case 3:
                    type = debug;
                    break;
                case 4:
                    type = info;
                    break;
                case 5:
                    type = warn;
                    break;
                case 6:
                    type = error;
                    break;
                case 7:
                    type = asset;
                    break;
                default:
                    type = null;
                    break;
            }
            return type;
        }
    }

    /**
     * 标识每条日志产生的时间
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    private static String time()
    {
        return "[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())) + "] ";
    }

    /**
     * 保存到日志文件
     *
     * @param content
     */
    public static synchronized void write(String content, Context context)
    {

        try
        {
            FileWriter writer = new FileWriter(getCrashPath(context), true);
            writer.write(content);
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

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
//        String crashpath = FileUtil.createNewFile(getCacheFilePath(context) + "com" + File.separator + "crash"
//                                                          + File.separator + new Date(System.currentTimeMillis()) + FileUtil.ERROR_LOG_SUFFIX);

        String crashpath = FileUtil.createNewFile(getCacheFilePath(context) + "hft_android" + File.separator + "crash" + FileUtil.ERROR_LOG_SUFFIX);

        return crashpath;
    }

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
        String CACHEFILEPATH = FileUtil.getSDPath(context) + File.separator;
        return CACHEFILEPATH;
    }
}
