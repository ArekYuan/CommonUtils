package cn.yaohl.MayorOnline.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yaohl.retrofitlib.utils.MyRouter;
import com.yaohl.retrofitlib.utils.StringUtil;

import java.util.HashSet;
import java.util.Set;

import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.sharepref.SharePref;
import cn.yaohl.MayorOnline.ui.BaseActivity;
import cn.yaohl.MayorOnline.ui.HomeActivity;
import cn.yaohl.MayorOnline.ui.login.beans.LoginResp;
import cn.yaohl.MayorOnline.ui.login.presenter.LoginPresenter;
import cn.yaohl.MayorOnline.util.CommonUtils;
import cn.yaohl.MayorOnline.util.Constant;

@Route(path = MyRouter.ROUTER_LOGIN)
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mobileETxt;//手机号输入
    private EditText passWordETxt;//密码输入
    private Button loginBtn;//登录按钮
    private CheckBox checkCBox;//同意条款复选框
    private LoginPresenter pressenter;
    private int keyHeight = 0;
    private View rootView;
    private LinearLayout loginRootView;
    private TextView registerTxt;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showToolBar(View.GONE);
        pressenter = new LoginPresenter(LoginActivity.this);
        initTitleBar();
        initView();
        initSaveUserName();
    }


    private void initTitleBar() {
        setTitleTxt("登录");
        setRightTxt("注册");
        int screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        keyHeight = screenHeight / 3;

    }

    private void initView() {
        mobileETxt = (EditText) findViewById(R.id.mobileETxt);
        passWordETxt = (EditText) findViewById(R.id.passWordETxt);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginRootView = (LinearLayout) findViewById(R.id.loginRootView);
        checkCBox = (CheckBox) findViewById(R.id.checkCBox);
        rootView = findViewById(R.id.rootView);
        registerTxt = (TextView) findViewById(R.id.registerTxt);

        loginBtn.setOnClickListener(this);
        registerTxt.setOnClickListener(this);
        checkCBox.setOnCheckedChangeListener(checkedChangeListener);
    }


    private void initSaveUserName() {
        boolean isSave = new SharePref(mContext).getBooleanValue(Constant.SAVEUSERNAME, false);
        if (isSave) {
            if (!new SharePref(mContext).getStringValue(Constant.LOGINUESRNAME).equals("")) {
                String name = new SharePref(mContext).getStringValue(Constant.LOGINUESRNAME);
                mobileETxt.setText(name);
                mobileETxt.setSelection(name.length());
            }
            checkCBox.setChecked(true);
        }

    }

    private CompoundButton.OnCheckedChangeListener checkedChangeListener
            = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                new SharePref(mContext).setBooleanValue(Constant.SAVEUSERNAME, true);
            } else {
                new SharePref(mContext).setBooleanValue(Constant.SAVEUSERNAME, false);
            }
        }
    };


    @Override
    public void onClick(View v) {
        String userName = mobileETxt.getText().toString();
        String pwdStr = passWordETxt.getText().toString();
        switch (v.getId()) {
            case R.id.loginBtn:
                if (checked(userName, pwdStr)) {
                    pressenter.doLoginReq(userName, pwdStr);
                }
                break;
            case R.id.registerTxt:
                Intent intent = new Intent(mContext,RegisterActivity.class);
                startActivity(intent);
                break;

        }
    }

    private boolean checked(String userName, String pwdStr) {
        boolean isChecked;

        if (StringUtil.isEmpty(userName) && userName.equals("")) {
            showShortToast("用户名不得为空");
            isChecked = false;
        } else if (StringUtil.isEmpty(pwdStr) && pwdStr.equals("")) {
            showShortToast("密码不得为空");
            isChecked = false;
        } else {
            isChecked = true;
        }
        return isChecked;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        CommonUtils.hideSoft(event, LoginActivity.this);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void dissmissDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                dismissLoadingDialog();
            }
        });
    }

    /**
     * @param msg
     */
    public void loginFail(String msg) {
        showShortToast(msg);
    }

    public void setSuccess(LoginResp resp) {
        new SharePref(mContext).setBooleanValue(Constant.IS_FIRST_LOGIN, true);
        new SharePref(mContext).setStringValue(Constant.LOGINUESRNAME,
                                               resp.getData().getUsername());
        new SharePref(mContext).setStringValue(Constant.LOGINUESRIMG, resp.getData().getIcon());
        new SharePref(mContext).setStringValue(Constant.LOGIN_REALNAME, resp.getData().getName());
        new SharePref(mContext).setStringValue(Constant.LOGIN_POSITION,
                                               resp.getData().getPosition());
        Set<String> tags = new HashSet<>();
        tags.add(resp.getData().getUsername());

//        JPushUtil.jpushRegisteredTags(mContext, tags);
        new SharePref(mContext).setStringValue(Constant.SECURITY_TGC, resp.getData().getTGC());

        Intent intent = new Intent(mContext, HomeActivity.class);
        mContext.startActivity(intent);
        finish();
    }

}
