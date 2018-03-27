package cn.yaohl.MayorOnline.ui.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.yaohl.MayorOnline.MayorApplication;
import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.ui.BaseActivity;
import cn.yaohl.MayorOnline.ui.personal.activity.InfoDetailActivity;
import cn.yaohl.MayorOnline.ui.personal.activity.MyAnswerActivity;
import cn.yaohl.MayorOnline.util.Constant;
import cn.yaohl.MayorOnline.util.view.RoundImageView;

public class PersonalInfoActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout headRLayout;
    private RoundImageView headIV;
    private TextView mobileTxt;

    private TextView nameTxt;
    private TextView myAnswerTxt;
    private TextView myHistoryTxt;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleTxt("我的");
        setLeftImgBg(R.mipmap.back_white);
        initView();
        initListener();
    }

    @Override
    protected void onLeftClick() {
        super.onLeftClick();
        finish();
        MayorApplication.getInstance().deleteActivity(this);
    }

    private void initListener() {
        headRLayout.setOnClickListener(this);
        headIV.setOnClickListener(this);
        myAnswerTxt.setOnClickListener(this);
        myHistoryTxt.setOnClickListener(this);
    }


    private void initView() {
        headRLayout = (RelativeLayout) findViewById(R.id.headRLayout);
        headIV = (RoundImageView) findViewById(R.id.headIV);

        mobileTxt = (TextView) findViewById(R.id.mobileTxt);
        nameTxt = (TextView) findViewById(R.id.nameTxt);
        myAnswerTxt = (TextView) findViewById(R.id.myAnswerTxt);
        myHistoryTxt = (TextView) findViewById(R.id.myHistoryTxt);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.headRLayout:
                intent = new Intent(mContext, InfoDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.headIV:
                break;
            case R.id.myAnswerTxt:
                intent = new Intent(mContext, MyAnswerActivity.class)
                        .putExtra(Constant.MINE_ANSWER_FLAG, 1001);
                startActivity(intent);
                break;
            case R.id.myHistoryTxt:
                intent = new Intent(mContext, MyAnswerActivity.class)
                        .putExtra(Constant.MINE_ANSWER_FLAG, 1002);
                startActivity(intent);
                break;
        }
    }
}
