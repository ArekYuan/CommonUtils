package com.yaohl.retrofitlib.utils.deamo;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * 作者：
 * 日期：2018/1/29
 * 描述：进程守护
 * 邮箱：813665242@qq.com
 */

public class Daemon {

    private static final String TAG = Daemon.class.getSimpleName();
    private static final String BIN_DIR_NAME = "bin";
    private static final String DAEMON_BIN_NAME = "daemon";
    public static final int INTERVAL_ONE_MINUTE = 60;
    public static final int INTERVAL_ONE_HOUR = 3600;

    public Daemon() {
    }

    private static void start(Context context, Class<?> daemonClazzName, int interval) {
        String cmd = context.getDir("bin", 0).getAbsolutePath() + File.separator + "daemon";
        StringBuilder cmdBuilder = new StringBuilder();
        cmdBuilder.append(cmd);
        cmdBuilder.append(" -p ");
        cmdBuilder.append(context.getPackageName());
        cmdBuilder.append(" -s ");
        cmdBuilder.append(daemonClazzName.getName());
        cmdBuilder.append(" -t ");
        cmdBuilder.append(interval);

        try {
            Runtime.getRuntime().exec(cmdBuilder.toString()).waitFor();
        } catch (InterruptedException | IOException var6) {
            Log.e(TAG, "start daemon error: " + var6.getMessage());
        }

    }

    private static void run(final Context context, final Class<?> daemonServiceClazz, final int interval) {
        (new Thread(new Runnable() {
            public void run() {
                Command.install(context, "bin", "daemon");
                Daemon.start(context, daemonServiceClazz, interval);
            }
        })).start();
    }

    public static void run(final Context context, String packageName, final Class<?> daemonServiceClazz, final int interval) throws PackageNameErrorException {
        if (!TextUtils.isEmpty(packageName) && "cn.yaohl".equals(packageName)) {
            (new Thread(new Runnable() {
                public void run() {
                    Command.install(context, "bin", "daemon");
                    Daemon.start(context, daemonServiceClazz, interval);
                }
            })).start();
        } else {
            throw new PackageNameErrorException("传入的包名错误");
        }
    }

}
