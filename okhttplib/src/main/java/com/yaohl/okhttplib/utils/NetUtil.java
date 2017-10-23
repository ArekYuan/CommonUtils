package com.yaohl.okhttplib.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 作者： 袁光跃 on 2017/10/20 10:22
 * 邮箱：813665242@qq.com
 * 描述：网络管理类
 */

public class NetUtil {

    /**
     * 判断网络是否连接
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 通过判断wifi和mobile两种方式是否能够连接网络
     */
    public static boolean isNetAvailable(Context context) {
        boolean isWifi = isWifiAvailable(context);
        boolean isMobile = isMobileAvailable(context);
        // 如果两个渠道都无法使用，提示用户设置网络信息
        if (isWifi == false && isMobile == false) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否Wifi处于连接状态
     */
    public static boolean isWifiAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null) {
            return networkInfo.isConnected();
        }
        return false;
    }

    /**
     * 判断是否APN列表中某个渠道处于连接状态
     */
    public static boolean isMobileAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkInfo != null) {
            return networkInfo.isConnected();
        }
        return false;
    }

    /**
     * 打开网络配置
     */
    public static void openSetNetWork(Context context) {
        // 判断手机系统的版本 即API大于10 就是3.0或以上版本
        if (android.os.Build.VERSION.SDK_INT > 10) {
            // 3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
            context.startActivity(new Intent(
                    android.provider.Settings.ACTION_SETTINGS));
        } else {
            context.startActivity(new Intent(
                    android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        }
    }
}
