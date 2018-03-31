package cn.yaohl.MayorOnline.util.aliyun;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by lifujun on 2017/11/14.
 */

public class BaseAppCompatActivity extends AppCompatActivity {

    @Override
    protected void onDestroy() {
        CleanLeakUtils.fixInputMethodManagerLeak(this);
        CleanLeakUtils.fixHuaWeiMemoryLeak(this);
        CleanLeakUtils.fixTextLineCacheLeak();
        super.onDestroy();
    }

}
