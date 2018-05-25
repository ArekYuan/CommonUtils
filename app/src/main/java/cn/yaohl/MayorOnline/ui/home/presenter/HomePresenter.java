package cn.yaohl.MayorOnline.ui.home.presenter;

import android.view.SurfaceView;

import com.alivc.player.AliVcMediaPlayer;
import com.aliyun.vodplayerview.utils.NetWatchdog;
import com.yaohl.retrofitlib.utils.GsonHelp;

import java.net.SocketTimeoutException;
import java.util.HashMap;

import cn.yaohl.MayorOnline.ui.BasePresenter;
import cn.yaohl.MayorOnline.ui.home.HomeFragment;
import cn.yaohl.MayorOnline.ui.home.beans.OrderResp;
import cn.yaohl.MayorOnline.util.retrofitLib.ApiException;
import cn.yaohl.MayorOnline.util.retrofitLib.ApiService;
import cn.yaohl.MayorOnline.util.retrofitLib.RxUtil;
import rx.functions.Action1;

/**
 * Created by Administrator on 2018/3/31 0031.
 */

public class HomePresenter extends BasePresenter {

    public HomeFragment fragment;
    private ApiService apiService;

    public HomePresenter(HomeFragment fragment) {
        this.fragment = fragment;
        apiService = new ApiService();
    }


    /**
     * 初始化 直播
     *
     * @param mUrl
     * @param player
     * @param netWatchdog
     * @param mSurfaceView
     */
    public void initLive(String mUrl, AliVcMediaPlayer player, NetWatchdog netWatchdog, SurfaceView mSurfaceView) {

    }

    public void doPay(String tgc, String admireMoney, String id, final String payType) {
        HashMap<String, String> reqParams = new HashMap<>();
        reqParams.put("TGC", tgc);
        reqParams.put("price", admireMoney);
        reqParams.put("deviceType", "ANDROID");
        reqParams.put("id", id);
        reqParams.put("payType", payType);
        String jsonStr = GsonHelp.objectToJsonString(reqParams);
        addSubscribe(apiService.getNewsAdmireOrder(jsonStr)
                .compose(RxUtil.<OrderResp>rxSchedulerHelper())
                .subscribe(new Action1<OrderResp>() {
                    @Override
                    public void call(OrderResp resp) {
                        if (resp.getCode().equals("0")) {
                            if (payType.equals("WEIXIN")) {
                                fragment.getNewsAdmireWeChatOrderSuccess(resp);
                            } else {
                                fragment.getNewsAdmireAlipayOrderSuccess(resp);
                            }
                        } else {
                            fragment.getNewsAdmireOrderFail(resp.getMsg());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof SocketTimeoutException) {
                            fragment.getNewsAdmireOrderFail("获取支付信息失败");
                        } else if (throwable instanceof ApiException) {
                            fragment.getNewsAdmireOrderFail(throwable.getMessage());
                        } else {
                            fragment.getNewsAdmireOrderFail("获取支付信息失败");
                        }
                    }
                }));
    }
}
