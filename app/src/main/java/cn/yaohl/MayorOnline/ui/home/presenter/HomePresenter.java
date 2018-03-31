package cn.yaohl.MayorOnline.ui.home.presenter;

import android.view.SurfaceView;

import com.alivc.player.AliVcMediaPlayer;
import com.aliyun.vodplayerview.utils.NetWatchdog;

import cn.yaohl.MayorOnline.ui.BasePresenter;
import cn.yaohl.MayorOnline.ui.home.HomeFragment;

/**
 * Created by Administrator on 2018/3/31 0031.
 */

public class HomePresenter extends BasePresenter {

    public HomeFragment fragment;

    public HomePresenter(HomeFragment fragment) {
        this.fragment = fragment;
    }


    /**
     * 初始化 直播
     *  @param mUrl
     * @param player
     * @param netWatchdog
     * @param mSurfaceView
     */
    public void initLive(String mUrl, AliVcMediaPlayer player, NetWatchdog netWatchdog, SurfaceView mSurfaceView) {

    }

}
