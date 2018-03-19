package cn.yaohl.MayorOnline.ui.splash;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.yaohl.retrofitlib.log.YLog;
import com.yaohl.retrofitlib.utils.NetUtil;
import com.yaohl.retrofitlib.utils.syspermission.SysPermissionManager;
import com.yaohl.retrofitlib.utils.syspermission.SysPermissionResultCallback;

import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.sharepref.SharePref;
import cn.yaohl.MayorOnline.ui.BaseActivity;
import cn.yaohl.MayorOnline.ui.HomeActivity;
import cn.yaohl.MayorOnline.ui.login.LoginActivity;
import cn.yaohl.MayorOnline.util.Constant;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showToolBar(View.GONE);
        initEvents();
    }

    /**
     * 初始化权限检查
     */
    private void initEvents() {
        if (!NetUtil.isNetworkConnected(mContext)) {
            showShortToast("请检查网络设置");
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {//检查当前手机版本的SDK
            checkStart();//走流程(登录，直接进入首页)
        } else {
            boolean b = new SharePref(mContext).getBooleanValue(Constant.FIRST_CHECK, true);
            if (b) {
                checkPermission();
            } else {
                checkStart();
            }
        }
    }


    /**
     * 检查权限
     */
    private void checkPermission() {
        SysPermissionManager.getInstance()
                .requestAllManifestPermissionsIfNecessary(this, new SysPermissionResultCallback() {
                    @Override
                    public void onGranted() {
                        YLog.d("权限 已被授予---->granted");
                        checkStart();
                    }

                    @Override
                    public void onDenied(String permission) {
                        YLog.d("denied:" + permission);
                    }
                });
    }

    /**
     * 走流程(登录，直接进入首页)
     */
    private void checkStart() {
        boolean isFirstLogin = new SharePref(mContext).getBooleanValue(Constant.IS_FIRST_LOGIN, false);
        if (!isFirstLogin) {
            //第一次登录，进入导航页
            jumpToHome();
        } else {
            //不是第一次登录，则检查是否需要登录...
            jumpToHome();
        }

    }

    private void jumpToHome() {
        Intent intent = new Intent(mContext, HomeActivity.class);
        mContext.startActivity(intent);
        finish();
    }

    private void jumpToLogin() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean b = new SharePref(mContext).getBooleanValue(Constant.FIRST_CHECK, true);
        if (b) {
            String rePermissions = "";
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    rePermissions += permissions[i] + ",";
                }
            }
            if (rePermissions.equals("")) {
                checkStart();
            } else {
                permissions = rePermissions.split(",");
                ActivityCompat.requestPermissions(SplashActivity.this, permissions, 1);
            }
            new SharePref(mContext).setBooleanValue(Constant.FIRST_CHECK, false);
        } else {
            checkStart();
        }
    }
}
