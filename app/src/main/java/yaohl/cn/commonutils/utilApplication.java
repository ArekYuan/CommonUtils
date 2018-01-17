package yaohl.cn.commonutils;

import android.app.Application;

import com.yaohl.retrofitlib.log.YLog;


/**
 * 作者：袁光跃
 * 日期：2018/1/3
 * 描述：
 * 邮箱：813665242@qq.com
 */

public class utilApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        YLog yLog = YLog.getInstance();
        yLog.setISDEBUG(true, this);//初始化log
    }
}
