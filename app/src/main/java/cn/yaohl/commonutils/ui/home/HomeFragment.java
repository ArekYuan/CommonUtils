package cn.yaohl.commonutils.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.yaohl.retrofitlib.log.YLog;

import cn.yaohl.commonutils.R;
import cn.yaohl.commonutils.alert.MToast;
import cn.yaohl.commonutils.ui.BaseFragment;

import static android.content.Context.LOCATION_SERVICE;

/**
 * 作者：袁光跃
 * 日期：2018/1/18
 * 描述：
 * 邮箱：813665242@qq.com
 */

public class HomeFragment extends BaseFragment {

    private TextView locationTxt;
    private LocationManager lm;
    private MyLocationListener listener;
    private static final int MY_PERMISSION_REQUEST_CODE = 10000;

    @Override
    protected int getContentViewId() {
        return R.layout.home_fragment_layout;
    }

    @Override
    public void onViewInitialized(View view, Bundle savedInstanceState) {
        initView(view);
    }

    private void initView(View view) {
        locationTxt = (TextView) view.findViewById(R.id.locationTxt);
        locationTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();

            }
        });
    }

    private void doLocation() {

        lm = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        listener = new MyLocationListener();
        Criteria criteria = new Criteria();
        criteria.setCostAllowed(true); // 是否允许使用付费
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 获取位置的精度
        String provider = lm.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lm.requestLocationUpdates(provider, 0, 0, listener);
    }

    class MyLocationListener implements LocationListener {
        // 位置改变时获取经纬度
        @Override
        public void onLocationChanged(Location location) {
            String j = "jingdu:" + location.getLongitude();
            String w = "weidu:" + location.getLatitude();
            YLog.d("j---->" + j + "w--->" + w);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

            YLog.d("provider--0-->" + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            YLog.d("provider--1-->" + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            YLog.d("provider--2-->" + provider);
        }
    }

    public void checkPermission() {
        /**
         * 第 1 步: 检查是否有相应的权限
         */
        boolean isAllGranted = checkPermissionAllGranted(
                new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.CAMERA
                }
        );
        // 如果这3个权限全都拥有, 则直接执行读取短信代码
        if (isAllGranted) {
            doLocation();
            MToast.showMsg(mContext, "所有权限已经授权！");
            return;
        }

        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSION_REQUEST_CODE
        );
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                MToast.showMsg(mContext, "检查权限！");
                return false;
            }
        }
        return true;
    }

    /**
     * 第 3 步: 申请权限结果返回处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了, 则执行读取短信代码
                doLocation();
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                MToast.showMsg(mContext, "需要授权！");
            }
        }
    }

}
