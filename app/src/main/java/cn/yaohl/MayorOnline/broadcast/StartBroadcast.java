package cn.yaohl.MayorOnline.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yaohl.retrofitlib.log.YLog;

import cn.yaohl.MayorOnline.service.CoreService;

/**
 * 作者：袁光跃
 * 日期：2018/1/26
 * 描述：开机 启动服务
 * 邮箱：813665242@qq.com
 */

public class StartBroadcast extends BroadcastReceiver {

    private String action_boot = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(action_boot)) {
            YLog.d("---开机广播--->" + action_boot);
            context.startService(new Intent(context, CoreService.class));
        }
    }
}
