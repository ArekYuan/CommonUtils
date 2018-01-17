package yaohl.cn.commonutils.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yaohl.retrofitlib.log.YLog;

import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import rx.functions.Action1;
import yaohl.cn.commonutils.R;
import yaohl.cn.commonutils.ui.mvp.model.LoginResp;
import yaohl.cn.commonutils.util.http.service.LoginApiService;
import yaohl.cn.commonutils.util.http.RxUtil;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    TextView textBtn, textContent;
    Context mContext = null;
    private LoginApiService apiService = new LoginApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        textBtn = (TextView) findViewById(R.id.textBtn);
        textContent = (TextView) findViewById(R.id.textContent);

        textBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();

            }
        });
    }

    private void doLogin() {
        final Gson gson = new Gson();
        HashMap<String, String> reqParams = new HashMap<>();
        reqParams.put("loginname", "admin");
        reqParams.put("loginpwd", "asdfsdf");
        String json = gson.toJson(reqParams);
        HashMap<String,String> data = new HashMap<>();
        data.put("data",json);
        apiService.getLoginInfo(data)
                .compose(RxUtil.<JsonObject>rxSchedulerHelper())
                .subscribe(new Action1<JsonObject>() {
                    @Override
                    public void call(JsonObject jsonObject) {
                        YLog.d("------>" + jsonObject.toString());

                        LoginResp resp = gson.fromJson(jsonObject, LoginResp.class);
                        String code = resp.getCode();

                        YLog.d("------>" + code);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        YLog.d("taskinfo error:" + throwable.getMessage());
                    }
                });
    }

}
