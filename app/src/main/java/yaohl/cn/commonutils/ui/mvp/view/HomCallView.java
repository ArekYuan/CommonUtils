package yaohl.cn.commonutils.ui.mvp.view;


import yaohl.cn.commonutils.ui.mvp.model.MarketsResp;

/**
 * 接口返回
 * <p>
 * Created by Administrator on 2017/4/25.
 */

public interface HomCallView
{
    void getMarketSuccess(MarketsResp resp, int code);

    void getMarketFailed(String msg, int code);
}
