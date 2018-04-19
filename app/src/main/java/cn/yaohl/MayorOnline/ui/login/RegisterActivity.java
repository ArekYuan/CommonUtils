package cn.yaohl.MayorOnline.ui.login;

import android.os.Bundle;

import cn.yaohl.MayorOnline.MayorApplication;
import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.ui.BaseActivity;

public class RegisterActivity extends BaseActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLeftImgBg(R.mipmap.back_white);
        setTitleTxt("用户注册");
    }

    @Override
    protected void onLeftClick() {
        super.onLeftClick();
        finish();
        MayorApplication.getInstance().deleteActivity(this);
    }
}
