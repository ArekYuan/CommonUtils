package cn.yaohl.MayorOnline.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.yaohl.retrofitlib.log.YLog;
import com.yaohl.retrofitlib.utils.deamo.Daemon;
import com.yaohl.retrofitlib.utils.deamo.PackageNameErrorException;

import java.io.PrintWriter;
import java.io.StringWriter;

import cn.yaohl.MayorOnline.MayorApplication;
import cn.yaohl.MayorOnline.ui.HomeActivity;
import cn.yaohl.MayorOnline.util.CommonUtils;

/**
 * 作者：袁光跃
 * 日期：2018/1/26
 * 描述：进程保活
 * 邮箱：813665242@qq.com
 */
public class CoreService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            YLog.d("CoreService----onCreate");
            Daemon.run(this, this.getPackageName(), CoreService.class,
                    (int) (Daemon.INTERVAL_ONE_MINUTE * 0.1));

//            boolean isForeground = CommonUtils.isForeground(BctidApplication.getApplication(),
//                    "com.yueworld.bctid.ui.menu.activity.ApprovalActivity");
            boolean isRunningForeground = CommonUtils.isRunningForeground(MayorApplication.getApplication());
            if (!isRunningForeground) {
                YLog.d("CoreService----跳转到首页");
                // 启动相应的业务服务
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("isRunningForeground", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //如果activity存在，拿到最顶端,不会启动新的Activity
                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(intent);
            }
        } catch (PackageNameErrorException e) {
            e.printStackTrace();
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            YLog.d(sw.toString());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        YLog.d("CoreService----onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        YLog.d("CoreService----onDestroy");
    }
}
