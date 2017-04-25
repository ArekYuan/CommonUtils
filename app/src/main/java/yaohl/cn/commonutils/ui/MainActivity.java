package yaohl.cn.commonutils.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import yaohl.cn.commonutils.R;
import yaohl.cn.commonutils.ui.mvp.model.MarketsResp;
import yaohl.cn.commonutils.ui.mvp.presenter.HomeLogic;
import yaohl.cn.commonutils.ui.mvp.view.HomCallView;

public class MainActivity extends AppCompatActivity implements HomCallView
{
    private static final String TAG = "MainActivity";
    TextView textBtn, textContent;
    Context mContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        textBtn = (TextView) findViewById(R.id.textBtn);
        textContent = (TextView) findViewById(R.id.textContent);

        textBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                initDataFromNet();
            }
        });
    }

    private void initDataFromNet()
    {
        String serverUrl = "api.szzc.com/api/web/index.php/markets";
        HomeLogic logic = new HomeLogic(mContext, this);
        logic.doGetMarket(TAG, serverUrl);
    }

    @Override
    public void getMarketSuccess(MarketsResp resp, int code)
    {
        List<MarketsResp.DataBean.MarketsBean> dataList = resp.getData().getMarkets();
        MarketsResp.DataBean.MarketsBean marketsBean = dataList.get(0);
        textContent.setText(marketsBean.getBefore() + "   " + marketsBean.getMarket());
    }

    @Override
    public void getMarketFailed(String msg, int code)
    {

    }
}
