package cn.yaohl.MayorOnline.ui.home.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.ui.BaseActivity;

@Route(path = "/mayor_online/news_detail")
public class NewsDetailActivity extends BaseActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
