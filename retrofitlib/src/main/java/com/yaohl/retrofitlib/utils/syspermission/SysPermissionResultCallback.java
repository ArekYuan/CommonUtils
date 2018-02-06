package com.yaohl.retrofitlib.utils.syspermission;

import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;


import com.yaohl.retrofitlib.log.YLog;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 权限请求结果相应
 */
public abstract class SysPermissionResultCallback {

    private static final String TAG = SysPermissionResultCallback.class.getSimpleName();
    private final Set<String> mPermissions = new HashSet<>(1);
    private Looper mLooper = Looper.getMainLooper();

    public SysPermissionResultCallback() {
    }


    /**
     * 允许permission时调用
     */
    public abstract void onGranted();

    /**
     * 该方法在permission被拒绝时被调用
     *
     * @param permission 被拒绝的 permission.
     */
    public abstract void onDenied(String permission);


    /**
     * 没有安卓版本中，没有找到的权限
     *
     * @param permission 不存在于安卓版本的权限
     */
    @SuppressWarnings({"WeakerAccess", "SameReturnValue"})
    public synchronized boolean shouldIgnorePermissionNotFound(String permission) {
        YLog.d("Permission not found: " + permission);
        return true;
    }

    /**
     * 动作结果回掉
     */
    @SuppressWarnings("WeakerAccess")
    @CallSuper
    protected synchronized final boolean onResult(final @NonNull String permission, int result) {
        if (result == PackageManager.PERMISSION_GRANTED) {
            return onResult(permission, SysPermissionType.GRANTED);
        } else {
            return onResult(permission, SysPermissionType.DENIED);
        }
    }

    /**
     * 当特定权限已更改时，该方法被调用。
     */
    @SuppressWarnings("WeakerAccess")
    @CallSuper
    public synchronized final boolean onResult(final @NonNull String permission,
                                               SysPermissionType result) {
        mPermissions.remove(permission);
        if (result == SysPermissionType.GRANTED) {
            if (mPermissions.isEmpty()) {
                new Handler(mLooper).post(new Runnable() {
                    @Override
                    public void run() {
                        onGranted();
                    }
                });
                return true;
            }
        } else if (result == SysPermissionType.DENIED) {
            new Handler(mLooper).post(new Runnable() {
                @Override
                public void run() {
                    onDenied(permission);
                }
            });
            return true;
        } else if (result == SysPermissionType.NOT_FOUND) {
            if (shouldIgnorePermissionNotFound(permission)) {
                if (mPermissions.isEmpty()) {
                    new Handler(mLooper).post(new Runnable() {
                        @Override
                        public void run() {
                            onGranted();
                        }
                    });
                    return true;
                }
            } else {
                new Handler(mLooper).post(new Runnable() {
                    @Override
                    public void run() {
                        onDenied(permission);
                    }
                });
                return true;
            }
        }
        return false;
    }

    /**
     * 注册指定的权限数组
     *
     * @param perms 被监听的权限permissions
     */
    @SuppressWarnings("WeakerAccess")
    @CallSuper
    public synchronized final void registerPermissions(@NonNull String[] perms) {
        Collections.addAll(mPermissions, perms);
    }
}
