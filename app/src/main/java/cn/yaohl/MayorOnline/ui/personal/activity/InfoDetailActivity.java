package cn.yaohl.MayorOnline.ui.personal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.yaohl.MayorOnline.MayorApplication;
import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.sharepref.SharePref;
import cn.yaohl.MayorOnline.ui.BaseActivity;
import cn.yaohl.MayorOnline.util.Constant;

public class InfoDetailActivity extends BaseActivity implements View.OnClickListener {

    private EditText infoNameEdiTxt;
    private EditText infoBirthEdiTxt;
    private EditText infoIdCardEdiTxt;
    private EditText infoIphoneEdiTxt;
    private EditText infoPtcEdiTxt;
    private EditText infoAddressEdiTxt;
    private Button updateBtn;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_info_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleTxt("个人资料");
        setLeftImgBg(R.mipmap.back_white);
        initView();
        initData();
    }

    private void initView() {
        infoNameEdiTxt = (EditText) findViewById(R.id.infoNameEdiTxt);
        infoBirthEdiTxt = (EditText) findViewById(R.id.infoBirthEdiTxt);
        infoIdCardEdiTxt = (EditText) findViewById(R.id.infoIdCardEdiTxt);
        infoIphoneEdiTxt = (EditText) findViewById(R.id.infoIphoneEdiTxt);
        infoPtcEdiTxt = (EditText) findViewById(R.id.infoPtcEdiTxt);
        infoAddressEdiTxt = (EditText) findViewById(R.id.infoAddressEdiTxt);
        updateBtn = (Button) findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(this);
    }

    @Override
    protected void onLeftClick() {
        super.onLeftClick();
        finish();
        MayorApplication.getInstance().deleteActivity(this);
    }

    @Override
    public void onClick(View v) {
        String name = infoNameEdiTxt.getText().toString();
        String birthday = infoBirthEdiTxt.getText().toString();
        String idCard = infoIdCardEdiTxt.getText().toString();
        String iphone = infoIphoneEdiTxt.getText().toString();
        String ptc = infoPtcEdiTxt.getText().toString();
        String address = infoAddressEdiTxt.getText().toString();

        switch (v.getId()) {
            case R.id.updateBtn:
                doSaveInfo(name, birthday, idCard, iphone, ptc, address);
                break;

        }
    }


    private void initData() {
        if (!new SharePref(mContext).getStringValue(Constant.USER_INFO_NAME).equals("")) {
            infoNameEdiTxt.setText(new SharePref(mContext).getStringValue(Constant.USER_INFO_NAME));
        } else if (!new SharePref(mContext).getStringValue(Constant.USER_INFO_BIRTHDAY).equals(
                "")) {
            infoBirthEdiTxt.setText(
                    new SharePref(mContext).getStringValue(Constant.USER_INFO_BIRTHDAY));
        } else if (!new SharePref(mContext).getStringValue(Constant.USER_INFO_ID_CARD).equals("")) {
            infoIdCardEdiTxt.setText(
                    new SharePref(mContext).getStringValue(Constant.USER_INFO_ID_CARD));
        } else if (!new SharePref(mContext).getStringValue(Constant.USER_INFO_IPHONE).equals("")) {
            infoIphoneEdiTxt.setText(
                    new SharePref(mContext).getStringValue(Constant.USER_INFO_IPHONE));
        } else if (!new SharePref(mContext).getStringValue(Constant.USER_INFO_PTC).equals("")) {
            infoPtcEdiTxt.setText(new SharePref(mContext).getStringValue(Constant.USER_INFO_PTC));
        } else if (!new SharePref(mContext).getStringValue(Constant.USER_INFO_ADDRESS).equals("")) {
            infoAddressEdiTxt.setText(
                    new SharePref(mContext).getStringValue(Constant.USER_INFO_ADDRESS));
        }
    }

    /**
     * 保存用户信息
     *
     * @param name
     * @param birthday
     * @param idCard
     * @param iphone
     * @param ptc
     * @param address
     */
    private void doSaveInfo(String name,
                            String birthday,
                            String idCard,
                            String iphone,
                            String ptc,
                            String address) {
        new SharePref(mContext).setStringValue(Constant.USER_INFO_NAME, name);
        new SharePref(mContext).setStringValue(Constant.USER_INFO_BIRTHDAY, birthday);
        new SharePref(mContext).setStringValue(Constant.USER_INFO_ID_CARD, idCard);
        new SharePref(mContext).setStringValue(Constant.USER_INFO_IPHONE, iphone);
        new SharePref(mContext).setStringValue(Constant.USER_INFO_PTC, ptc);
        new SharePref(mContext).setStringValue(Constant.USER_INFO_ADDRESS, address);

        finish();
    }
}
