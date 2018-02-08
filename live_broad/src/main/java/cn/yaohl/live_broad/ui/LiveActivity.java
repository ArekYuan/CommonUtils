package cn.yaohl.live_broad.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.yaohl.live_broad.R;

@Route(path = "/live_broad/live")
public class LiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_activity_live);
    }
}
