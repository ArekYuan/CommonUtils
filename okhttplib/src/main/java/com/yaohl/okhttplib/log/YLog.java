package com.yaohl.okhttplib.log;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者： 袁光跃 on 2017/9/12 13:51
 * 邮箱：813665242@qq.com
 * 描述：简单封装日志打印，
 * 将崩溃日志打印到本地文件，
 * 或者将崩溃日志文件上传给服务器，由服务器反馈给客户端处理
 */

public final class YLog {
    private static Context mContext;
    private static String customTagPrefix = "Y_";
    private static final String ERROR_LOG_SUFFIX = ".log";

    private YLog() {

    }

    /**
     * 是否处于开发模式
     */
    private static boolean IS_DEBUG = false;


    public static boolean ISDEBUG() {
        return IS_DEBUG;
    }

    /**
     * 在打包发布的时候 设置为false
     *
     * @param ISDEBUG 是否处于开发模式
     */
    public static void setISDEBUG(boolean ISDEBUG, Context context) {
        IS_DEBUG = ISDEBUG;
        mContext = context;
    }


    /**
     * 打印级别为 verbose 的日志
     *
     * @param msg
     */
    public static void v(String msg) {
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        printInSystemLog(Log.VERBOSE, tag, msg, "");
    }

    /**
     * @param tr
     */
    public static void v(Throwable tr) {
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        printInSystemLog(Log.VERBOSE, tag, "", getStackTraceString(tr));
    }

    /**
     * 打印级别为debug 的日志
     *
     * @param msg
     */
    public static void d(String msg) {
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        printInSystemLog(Log.DEBUG, tag, msg, "");
    }

    /**
     * @param tr
     */
    public static void d(Throwable tr) {
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        printInSystemLog(Log.DEBUG, tag, "", getStackTraceString(tr));
    }

    /**
     * 打印级别为 info 的日志
     *
     * @param msg
     */
    public static void i(String msg) {
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        printInSystemLog(Log.INFO, tag, msg, "");
    }

    /**
     * 打印级别为info 的错误日志
     *
     * @param tr
     */
    public static void i(Throwable tr) {
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        printInSystemLog(Log.INFO, tag, "", getStackTraceString(tr));
    }

    /**
     * 打印级别为warn 的错误日志
     *
     * @param msg
     */
    public static void w(String msg) {
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        printInSystemLog(Log.WARN, tag, msg, "");
    }

    /**
     * 打印级别为warn 的错误日志
     *
     * @param tr
     */
    public static void w(Throwable tr) {
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        printInSystemLog(Log.WARN, tag, "", getStackTraceString(tr));
    }

    /**
     * 打印级别为error 的日志
     *
     * @param msg
     */
    public static void e(String msg) {
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        printInSystemLog(Log.ERROR, tag, msg, "");
    }

    /**
     * @param tr
     */
    public static void e(Throwable tr) {
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        printInSystemLog(Log.ERROR, tag, "", getStackTraceString(tr));
    }

    /**
     * 如果该文件有崩溃则将日志文件输出到本地日志，写入SD卡中，没有则打印日志
     * 假如处于debug模式，未上线，不需要将日志输出在本地，直接在控制台打印日志
     *
     * @param tag
     * @param crashMsg
     */
    private static void printInSystemLog(final int priority, final String tag, final String msg, final String crashMsg) {

        if (ISDEBUG()) {
            Log.println(priority, tag, msg + "\n" + crashMsg);
        } else {
            if (!crashMsg.equals("") || null != crashMsg) {
                new Thread() {
                    @Override
                    public void run() {
                        write(time() + "--->" + tag + " [DEBUG] --> " + crashMsg);
                    }
                }.start();
            }
        }
    }

    /**
     * 将崩溃日志写入本地文件
     *
     * @param crashContent 崩溃日志内容
     */
    private static void write(String crashContent) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(getCrashPath(mContext), true);
            writer.write(crashContent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 获取当前时间
     *
     * @return
     */
    private static String time() {
        return "[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())) + "] ";
    }


    /**
     * 得到一个错误信息
     *
     * @param tr
     * @return
     */
    private static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        return sw.toString();
    }


    /**
     * @return
     */
    private static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }


    /**
     * 获取需要打印日志的位置(哪个文件,哪个方法)信息
     *
     * @param caller
     * @return
     */
    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(Line:%d)"; // 占位符
        String callerClazzName = caller.getClassName(); // 获取到类名
        callerClazzName = callerClazzName.substring(callerClazzName
                .lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(),
                caller.getLineNumber()); // 替换
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":"
                + tag;
        return tag;
    }

    /**
     * 获取崩溃日志目录
     *
     * @param context
     * @return
     */
    public static String getCrashPath(Context context) {
        String crashpath = createNewFile(getCacheFilePath(context) + "YLog" + File.separator + "crash" + ERROR_LOG_SUFFIX);
        return crashpath;
    }


    /**
     * 缓存目录
     *
     * @param context
     * @return
     */
    public static String getCacheFilePath(Context context) {
        String CACHEFILEPATH = getSDPath(context) + File.separator;
        return CACHEFILEPATH;
    }

    /**
     * 获取SD卡路径
     *
     * @param context
     * @return
     */
    public static String getSDPath(Context context) {
        // 判断sdcard是否存在
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 获取根目录
            File sdDir = Environment.getExternalStorageDirectory();
            return sdDir.getPath();
        }
        return "/data/data/" + context.getPackageName();
    }

    /**
     * <创建新文件，返回目录路径> <功能详细描述>
     *
     * @param path 路径
     * @return 返 回 类 型：String
     */
    public static String createNewFile(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return path;
    }
}
