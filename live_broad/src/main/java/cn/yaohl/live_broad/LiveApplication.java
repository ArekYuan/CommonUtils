package cn.yaohl.live_broad;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.squareup.leakcanary.LeakCanary;
import com.yaohl.retrofitlib.log.YLog;

/**
 * 作者： 袁光躍 on 2018/2/8 11:26
 * 邮箱：813665242@qq.com
 * 描述：
 */

public class LiveApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        YLog yLog = YLog.getInstance();
        yLog.setISDEBUG(true, this);//初始化log
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        if (yLog.ISDEBUG()) {
            LeakCanary.install(this);
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);
    }
}
