package cn.yaohl.MayorOnline.ui.home.activity;

import android.os.Bundle;

import cn.yaohl.MayorOnline.MayorApplication;
import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.ui.BaseActivity;

public class AboutUsActivity extends BaseActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleTxt("关于我们");
        setLeftImgBg(R.mipmap.back_white);
    }

    @Override
    protected void onLeftClick() {
        super.onLeftClick();
        finish();
        MayorApplication.getInstance().deleteActivity(this);
    }
}
