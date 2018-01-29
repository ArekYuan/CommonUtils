package com.yaohl.retrofitlib.utils.deamo;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 作者：袁光跃
 * 日期：2018/1/29
 * 描述：
 * 邮箱：813665242@qq.com
 */

public class Command {
    private static final String TAG = Command.class.getSimpleName();
    public static final String BIN_DIR_NAME = "bin";

    public Command() {
    }

    private static void copyFile(File file, InputStream is, String mode) throws IOException, InterruptedException {
        String abspath = file.getAbsolutePath();
        FileOutputStream out = new FileOutputStream(file);
        byte[] buf = new byte[1024];

        int len;
        while((len = is.read(buf)) > 0) {
            out.write(buf, 0, len);
        }

        out.close();
        is.close();
        Runtime.getRuntime().exec("chmod " + mode + " " + abspath).waitFor();
    }

    public static void copyRawFile(Context context, int resid, File file, String mode) throws IOException, InterruptedException {
        InputStream is = context.getResources().openRawResource(resid);
        copyFile(file, is, mode);
    }

    public static void copyAssets(Context context, String assetsFilename, File file, String mode) throws IOException, InterruptedException {
        AssetManager manager = context.getAssets();
        InputStream is = manager.open(assetsFilename);
        copyFile(file, is, mode);
    }

    public static boolean install(Context context, String destDir, String filename) {
        String abi = Build.CPU_ABI;
        if(!abi.startsWith("arm")) {
            return false;
        } else {
            try {
                File f = new File(context.getDir(destDir, 0), filename);
                if(f.exists()) {
                    Log.d(TAG, "binary has existed");
                    return false;
                } else {
                    copyAssets(context, filename, f, "0755");
                    return true;
                }
            } catch (Exception var5) {
                Log.e(TAG, "installBinary failed: " + var5.getMessage());
                return false;
            }
        }
    }

    public static void install(Context context, String filename) {
        install(context, "bin", filename);
    }

}
