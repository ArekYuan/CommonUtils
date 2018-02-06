package com.yaohl.retrofitlib.utils.syspermission;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;


import com.yaohl.retrofitlib.log.YLog;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 系统权限管理类
 */
public class SysPermissionManager {

    private static final String TAG = SysPermissionManager.class.getSimpleName();

    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
    private static SysPermissionManager mInstance = null;
    private final Set<String> mPendingRequests = new HashSet<>(1);
    private final Set<String> mPermissions = new HashSet<>(1);
    private final List<WeakReference<SysPermissionResultCallback>> mPendingActions = new ArrayList<>(
            1);

    private SysPermissionManager() {
        initializePermissionsMap();
    }

    public synchronized static SysPermissionManager getInstance() {
        if (mInstance == null) {
            mInstance = new SysPermissionManager();
        }
        return mInstance;
    }

    /**
     * 应用程序，打开安卓设置屏幕权限。
     */
    public static void openSettingsScreen(@NonNull Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.parse("package:" + context.getPackageName());
        intent.setData(uri);
        context.startActivity(intent);
    }

    /**
     * @return true 检查是否有系统弹框权限
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static boolean isSystemAlertGranted(@NonNull Context context) {
        return Settings.canDrawOverlays(context);
    }

    /**
     * 检查消息通知栏显示是否开启，该检测支队4.3已经以上系统有效
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isNotificationEnabled(Context context) {
        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        Class appOpsClass = null; /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW,
                    Integer.TYPE, Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            Integer value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg)
                    == AppOpsManager.MODE_ALLOWED);
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 通过反射来读取清单类中的所有权限
     */
    private synchronized void initializePermissionsMap() {
        Field[] fields = Manifest.permission.class.getFields();
        for (Field field : fields) {
            String name = null;
            try {
                name = (String) field.get("");
            } catch (IllegalAccessException e) {
                YLog.d("Could not access field" + e.getMessage());
            }
            mPermissions.add(name);
        }
    }

    /**
     * 读取我们程序清单文件中的所有权限
     */
    @NonNull
    private synchronized String[] getManifestPermissions(@NonNull final Activity activity) {
        PackageInfo packageInfo = null;
        List<String> list = new ArrayList<>(1);
        try {
            YLog.d(activity.getPackageName());
            packageInfo = activity.getPackageManager()
                    .getPackageInfo(activity.getPackageName(), PackageManager.GET_PERMISSIONS);
        } catch (PackageManager.NameNotFoundException e) {
            YLog.d("=========检索权限时出现的问题========" + e.getMessage());
        }
        if (packageInfo != null) {
            String[] permissions = packageInfo.requestedPermissions;
            if (permissions != null) {
                for (String perm : permissions) {
                    YLog.d("Manifest 包含 permission: " + perm);
                    list.add(perm);
                }
            }
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * @param permissions
     * @param callback
     */
    private synchronized void addPendingAction(@NonNull String[] permissions,
                                               @Nullable SysPermissionResultCallback callback) {
        if (callback == null) {
            return;
        }
        callback.registerPermissions(permissions);
        mPendingActions.add(new WeakReference<>(callback));
    }

    /**
     * @param callback
     */
    private synchronized void removePendingAction(@Nullable SysPermissionResultCallback callback) {
        for (Iterator<WeakReference<SysPermissionResultCallback>> iterator = mPendingActions.iterator();
             iterator.hasNext(); ) {
            WeakReference<SysPermissionResultCallback> weakRef = iterator.next();
            if (weakRef.get() == callback || weakRef.get() == null) {
                iterator.remove();
            }
        }
        callback.onGranted();
    }

    /**
     * 检查是否有特定权限
     */
    @SuppressWarnings("unused")
    public synchronized boolean hasPermission(@Nullable Context context, @NonNull String permission) {
        return context != null && (ActivityCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED || !mPermissions.contains(permission));
    }

    /**
     * 检查是否有若干特定权限
     */
    @SuppressWarnings("unused")
    public synchronized boolean hasAllPermissions(@Nullable Context context,
                                                  @NonNull String[] permissions) {
        if (context == null) {
            return false;
        }
        boolean hasAllPermissions = true;
        for (String perm : permissions) {
            hasAllPermissions &= hasPermission(context, perm);
        }
        return hasAllPermissions;
    }

    /**
     * 在 Activity 中批量请求权限
     */
    @SuppressWarnings("unused")
    public synchronized void requestAllManifestPermissionsIfNecessary(
            final @Nullable Activity activity,
            final @Nullable SysPermissionResultCallback callback) {
        if (activity == null) {
            return;
        }
        String[] perms = getManifestPermissions(activity);
        requestPermissionsIfNecessaryForResult(activity, perms, callback);
    }

    /**
     * 用于Activity中申请权限时调用
     */
    @SuppressWarnings("unused")
    public synchronized void requestPermissionsIfNecessaryForResult(@Nullable Activity activity,
                                                                    @NonNull String[] permissions,
                                                                    @Nullable SysPermissionResultCallback callback) {
        if (activity == null) {
            return;
        }
        addPendingAction(permissions, callback);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            doPermissionWorkBeforeAndroidM(activity, permissions, callback);
        } else {
            List<String> permList = getPermissionsListToRequest(activity, permissions, callback);
            if (permList.isEmpty()) {
                // 如果没有许可的要求，没有必要保持
                removePendingAction(callback);
            } else {
                String[] permsToRequest = permList.toArray(new String[permList.size()]);
                mPendingRequests.addAll(permList);
                ActivityCompat.requestPermissions(activity, permsToRequest, 1);
            }
        }
    }

    /**
     * 用于Fragment中进行权限申请时候调用
     */
    @SuppressWarnings("unused")
    public synchronized void requestPermissionsIfNecessaryForResult(@NonNull Fragment fragment,
                                                                    @NonNull String[] permissions,
                                                                    @Nullable SysPermissionResultCallback callback) {
        Activity activity = fragment.getActivity();
        if (activity == null) {
            return;
        }
        addPendingAction(permissions, callback);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            doPermissionWorkBeforeAndroidM(activity, permissions, callback);
        } else {
            List<String> permList = getPermissionsListToRequest(activity, permissions, callback);
            if (permList.isEmpty()) {
                // 如果没有许可的要求，没有必要保持
                removePendingAction(callback);
            } else {
                String[] permsToRequest = permList.toArray(new String[permList.size()]);
                mPendingRequests.addAll(permList);
                fragment.requestPermissions(permsToRequest, 1);
            }
        }
    }

    /**
     * 通知权限改变
     */
    @SuppressWarnings("unused")
    public synchronized void notifyPermissionsChange(@NonNull String[] permissions,
                                                     @NonNull SysPermissionType[] results) {
        int size = permissions.length;
        if (results.length < size) {
            size = results.length;
        }
        Iterator<WeakReference<SysPermissionResultCallback>> iterator = mPendingActions.iterator();
        while (iterator.hasNext()) {
            SysPermissionResultCallback callback = iterator.next().get();
            for (int n = 0; n < size; n++) {
                if (callback == null || callback.onResult(permissions[n], results[n])) {
                    iterator.remove();
                    break;
                }
            }
        }
        for (int n = 0; n < size; n++) {
            mPendingRequests.remove(permissions[n]);
        }
    }

    /**
     * 请求权限处理 在 Android M (Android 6.0, API Level 23) 之前的设备
     *
     * @param activity 检查权限的 activity
     * @param callback 回调对象，包含了在权限检查之后我们将做什么
     */
    private void doPermissionWorkBeforeAndroidM(@NonNull Activity activity,
                                                @NonNull String[] permissions,
                                                @Nullable SysPermissionResultCallback callback) {
        for (String perm : permissions) {
            if (callback != null) {
                if (!mPermissions.contains(perm)) {
                    callback.onResult(perm, SysPermissionType.NOT_FOUND);
                } else if (ActivityCompat.checkSelfPermission(activity, perm)
                        != PackageManager.PERMISSION_GRANTED) {
                    callback.onResult(perm, SysPermissionType.DENIED);
                } else {
                    callback.onResult(perm, SysPermissionType.GRANTED);
                }
            }
        }
    }

    /**
     * 过滤器的权限列表：
     * 如果未授予权限，则将其添加到结果列表中
     * 如果一个权限被授予，则做授予的工作，不将其添加到结果列表中
     *
     * @param activity    检查权限的 activity
     * @param permissions 检查的 permissions
     * @param callback    在权限检查之后的回掉
     * @return 返回为授权成功的的列表
     */
    @NonNull
    private List<String> getPermissionsListToRequest(@NonNull Activity activity,
                                                     @NonNull String[] permissions,
                                                     @Nullable SysPermissionResultCallback callback) {
        List<String> permList = new ArrayList<>(permissions.length);
        for (String perm : permissions) {
            if (!mPermissions.contains(perm)) {
                if (callback != null) {
                    callback.onResult(perm, SysPermissionType.NOT_FOUND);
                }
            } else if (ActivityCompat.checkSelfPermission(activity, perm)
                    != PackageManager.PERMISSION_GRANTED) {
                if (!mPendingRequests.contains(perm)) {
                    permList.add(perm);
                }
            } else {
                if (callback != null) {
                    callback.onResult(perm, SysPermissionType.GRANTED);
                }
            }
        }
        return permList;
    }

}
