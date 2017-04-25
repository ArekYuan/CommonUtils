package yaohl.cn.commonutils.ui.mvp.presenter;

import android.content.Context;

import com.yaohl.okhttplib.okhttp.OkHttpService;

import yaohl.cn.commonutils.ui.BaseLogic;
import yaohl.cn.commonutils.ui.mvp.model.MarketsResp;
import yaohl.cn.commonutils.ui.mvp.view.HomCallView;


/**
 * 持久层
 * <p>
 * Created by Administrator on 2017/4/19.
 */

public class HomeLogic extends BaseLogic
{
    Context mContext;

    HomCallView callView;

    public HomeLogic(Context mContext, HomCallView callView)
    {
        this.mContext = mContext;
        this.callView = callView;
    }

    /**
     * GET 请求market
     *
     * @param tag
     * @param url
     */
    public void doGetMarket(String tag, String url)
    {
        OkHttpService.doGet(tag, url, this, MarketsResp.class);
    }

    @Override
    public void onComplete(Object result)
    {
        if (result instanceof MarketsResp)
        {
            MarketsResp resp = (MarketsResp) result;
            if (resp.getCode() == 2000)
            {
                callView.getMarketSuccess(resp, resp.getCode());
            }
            else
            {
                callView.getMarketFailed(resp.getMessage(), resp.getCode());
            }
        }
    }

    @Override
    public Context getContext()
    {
        return mContext;
    }
}
