package cn.yaohl.MayorOnline.ui.home;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alivc.player.AliVcMediaPlayer;
import com.alivc.player.MediaPlayer;
import com.alivc.player.VcPlayerLog;
import com.aliyun.vodplayerview.utils.NetWatchdog;
import com.google.gson.Gson;
import com.yaohl.retrofitlib.utils.StringUtil;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.alipay.AlipayOrderInfo;
import cn.yaohl.MayorOnline.ui.BaseFragment;
import cn.yaohl.MayorOnline.ui.home.adapter.CommentAdapter;
import cn.yaohl.MayorOnline.ui.home.adapter.HistoryAdapter;
import cn.yaohl.MayorOnline.ui.home.beans.CommentResp;
import cn.yaohl.MayorOnline.ui.home.beans.HistoryVideoResp;
import cn.yaohl.MayorOnline.ui.home.beans.OrderResp;
import cn.yaohl.MayorOnline.ui.home.presenter.HomePresenter;
import cn.yaohl.MayorOnline.util.CashierInputFilter;
import cn.yaohl.MayorOnline.util.CommonUtils;
import cn.yaohl.MayorOnline.util.Constant;
import cn.yaohl.MayorOnline.util.PayUtil;
import cn.yaohl.MayorOnline.util.Pop1Window;
import cn.yaohl.MayorOnline.util.ToastUtils;
import cn.yaohl.MayorOnline.util.aliyun.Formatter;
import cn.yaohl.MayorOnline.util.view.MarqueeTextView;
import cn.yaohl.MayorOnline.wxapi.WeiChatCustomOrder;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 作者：袁光跃
 * 日期：2018/1/18
 * 描述：
 * 邮箱：813665242@qq.com
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private MarqueeTextView messageContentTxt;

    //点赞
    private RelativeLayout praiseRLayout;
    private TextView praiseNumTxt;

    //信息
    private RelativeLayout messageRLayout;
    private TextView messageNumTxt;

    //禮物
    private RelativeLayout giftRLayout;
    private TextView giftNumTxt;

    //转发
    private RelativeLayout forwardRLayout;

    //更多往期内容
    private TextView moreVideoTxt;

    private RecyclerView historyViewListView;
    private HistoryAdapter histroyAdapter;

    int spanCount = 1; // 只显示一行


    //评论区
    private TextView moreVCommentTxt;
    private RecyclerView commentRclView;
    private CommentAdapter commentAdapter;


    private SurfaceView mSurfaceView;
    private LinearLayout progress_layout;
    private ImageView playBtn;
    //    private Button pauseBtn;
//    private Button replayBtn;
//    private Button stopBtn;
    private CheckBox muteOnBtn;
    private float speed = 1.0f;
    private MediaPlayer.VideoRotate roate = MediaPlayer.VideoRotate.ROTATE_0;
    private MediaPlayer.VideoMirrorMode mirrorMode = MediaPlayer.VideoMirrorMode.VIDEO_MIRROR_MODE_NONE;
    private TextView positionTxt;
    private TextView durationTxt;
    private SeekBar progressBar;
    private boolean mMute = false;
    private AliVcMediaPlayer mPlayer;
    private boolean inSeek = false;
    private boolean isCompleted = false;
    private LinearLayout switchLiveLayout;
    private String mUrl = null;

    private NetWatchdog netWatchdog;
    private int flag = 1;//0，录播，1、直播
    HomePresenter presenter;

    private String[] titleStr = new String[]{"2018年6月28日10:00南京市长蓝绍敏做客市长在线!",
            "2018年7月28日10:00苏州市长蓝绍敏做客市长在线!",
            "2018年8月28日10:00常州市长蓝绍敏做客市长在线!",
            "2018年9月28日10:00无锡市长蓝绍敏做客市长在线!",
            "2018年10月28日10:00镇江市长蓝绍敏做客市长在线!"};

    private InputFilter[] filters = {
            new CashierInputFilter()
    };

    @Override
    protected int getContentViewId() {
        return R.layout.home_fragment_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        presenter = new HomePresenter(this);
        if (flag == 0) {//录播
            mUrl = "http://player.alicdn.com/video/aliyunmedia.mp4";
        } else if (flag == 1) {//直播
            mUrl = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
//            mUrl = "rtmp:// vhost=www.zhidingmingcheng.com/AppName/StreamName";
        }
    }

    @Override
    public void onViewInitialized(View view, Bundle savedInstanceState) {
        initView(view);
        initListener();
        initHisData();
        initCommentData();
    }

    private void initView(View v) {
        messageContentTxt = (MarqueeTextView) v.findViewById(R.id.messageContentTxt);
        switchLiveLayout = (LinearLayout) v.findViewById(R.id.switchLiveLayout);
        praiseRLayout = (RelativeLayout) v.findViewById(R.id.praiseRLayout);
        praiseNumTxt = (TextView) v.findViewById(R.id.praiseNumTxt);

        messageRLayout = (RelativeLayout) v.findViewById(R.id.messageRLayout);
        messageNumTxt = (TextView) v.findViewById(R.id.messageNumTxt);

        giftRLayout = (RelativeLayout) v.findViewById(R.id.giftRLayout);
        giftNumTxt = (TextView) v.findViewById(R.id.giftNumTxt);

        forwardRLayout = (RelativeLayout) v.findViewById(R.id.forwardRLayout);

        moreVideoTxt = (TextView) v.findViewById(R.id.moreVideoTxt);
        historyViewListView = (RecyclerView) v.findViewById(R.id.historyViewListView);

        commentRclView = (RecyclerView) v.findViewById(R.id.commentRclView);
        moreVCommentTxt = (TextView) v.findViewById(R.id.moreVCommentTxt);

        mSurfaceView = (SurfaceView) v.findViewById(R.id.surfaceView);
        playBtn = (ImageView) v.findViewById(R.id.play);
//        stopBtn = (Button) v.findViewById(stop);
//        pauseBtn = (Button) v.findViewById(R.id.pause);
//        replayBtn = (Button) v.findViewById(R.id.replay);
        muteOnBtn = (CheckBox) v.findViewById(R.id.muteOn);
        progress_layout = (LinearLayout) v.findViewById(R.id.progress_layout);

        positionTxt = (TextView) v.findViewById(R.id.currentPosition);
        durationTxt = (TextView) v.findViewById(R.id.totalDuration);
        progressBar = (SeekBar) v.findViewById(R.id.progress);

        playBtn.setOnClickListener(this);
        switchLiveLayout.setOnClickListener(this);
//        stopBtn.setOnClickListener(this);
//        pauseBtn.setOnClickListener(this);
//        replayBtn.setOnClickListener(this);

        //        initAutoScaleModeFit();
        netWatchdog = new NetWatchdog(getActivity());
        mPlayer = new AliVcMediaPlayer(mContext, mSurfaceView);
        initRote();
        initMirror();
        doSwitch(flag, true);

        muteOnBtn.setOnCheckedChangeListener(changeListener);
        mSurfaceView.getHolder().addCallback(callback);
        netWatchdog.startWatch();

        messageContentTxt.setTextArraysAndClickListener(titleStr,
                new MarqueeTextView.MarqueeTextViewClickListener() {
                    @Override
                    public void onClick(View view) {
                        showShortToast("市长来啦");
                    }
                });

    }

    /**
     * 切换
     *
     * @param flag
     */
    private void doSwitch(int flag, boolean b) {
        if (flag == 0) {//录播
            initSpeed();
            initVodPlayer();
            progressBar.setVisibility(View.VISIBLE);
            progress_layout.setVisibility(View.VISIBLE);
            progressBar.setOnSeekBarChangeListener(seekBarChangeListener);
            netWatchdog.setNetChangeListener(netVodChangeListener);
        } else if (flag == 1) {//直播
//            pauseBtn.setVisibility(View.GONE);
//            replayBtn.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            progress_layout.setVisibility(View.GONE);
            presenter.initLive(mUrl, mPlayer, netWatchdog, mSurfaceView);
            initLivePlayer();
            netWatchdog.setNetChangeListener(netLiveChangeListener);
        }
    }

    private void initLivePlayer() {
        mPlayer.setPreparedListener(new MyPreparedListener(this));
        mPlayer.setErrorListener(new MyErrorLiveListener(this));
        mPlayer.setCompletedListener(new MyPlayerCompletedListener(this));
        mPlayer.enableNativeLog();
    }


    /**
     * 监听网络变化
     */
    private NetWatchdog.NetChangeListener netLiveChangeListener = new NetWatchdog.NetChangeListener() {
        @Override
        public void onWifiTo4G() {
            if (mPlayer.isPlaying()) {
                pause();
            }
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
            alertDialog.setTitle("网络切换为4G");
            alertDialog.setMessage("是否继续播放？");
            alertDialog.setPositiveButton("是",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            replay();
                        }
                    });
            alertDialog.setNegativeButton("否", null);
            AlertDialog alert = alertDialog.create();
            alert.show();

            Toast.makeText(mContext, "网络切换为4G", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void on4GToWifi() {
            Toast.makeText(mContext, "网络切换为WIFI", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onNetDisconnected() {
            Toast.makeText(mContext, "网络已断开", Toast.LENGTH_SHORT).show();
        }
    };

    private static class MyErrorLiveListener implements MediaPlayer.MediaPlayerErrorListener {

        private WeakReference<HomeFragment> activityWeakReference;

        public MyErrorLiveListener(HomeFragment activity) {
            activityWeakReference = new WeakReference<HomeFragment>(activity);
        }

        @Override
        public void onError(int i, String s) {
            HomeFragment activity = activityWeakReference.get();
            if (activity != null) {
                activity.onErrorLive(i, s);
            }
        }
    }

    private void onErrorLive(int i, String msg) {
        Toast.makeText(mContext,
                "失败！！！！原因：" + msg, Toast.LENGTH_SHORT).show();
    }

    private static class MyPreparedListener implements MediaPlayer.MediaPlayerPreparedListener {

        private WeakReference<HomeFragment> activityWeakReference;

        public MyPreparedListener(HomeFragment activity) {
            activityWeakReference = new WeakReference<HomeFragment>(activity);
        }

        @Override
        public void onPrepared() {
            HomeFragment activity = activityWeakReference.get();
            if (activity != null) {
                activity.onPreparedLive();
            }
        }

    }

    void onPreparedLive() {
        Toast.makeText(mContext, "准备成功", Toast.LENGTH_SHORT).show();
    }


    private static class MyPlayerCompletedListener
            implements MediaPlayer.MediaPlayerCompletedListener {

        private WeakReference<HomeFragment> activityWeakReference;

        public MyPlayerCompletedListener(HomeFragment activity) {
            activityWeakReference = new WeakReference<HomeFragment>(activity);
        }

        @Override
        public void onCompleted() {
            HomeFragment activity = activityWeakReference.get();
            if (activity != null) {
                activity.onCompletedLive();
            }
        }

    }

    private void onCompletedLive() {
        isCompleted = true;
    }

    private void initListener() {
        praiseRLayout.setOnClickListener(this);
        messageRLayout.setOnClickListener(this);
        forwardRLayout.setOnClickListener(this);
        giftRLayout.setOnClickListener(this);
        moreVideoTxt.setOnClickListener(this);
    }

    private void initHisData() {
        histroyAdapter = new HistoryAdapter(mContext);
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(spanCount,
                        StaggeredGridLayoutManager.HORIZONTAL);
        historyViewListView.setLayoutManager(layoutManager);
        historyViewListView.setFocusable(false);
        historyViewListView.setAdapter(histroyAdapter);
        List<HistoryVideoResp> mData = new ArrayList<>();
        mData.add(new HistoryVideoResp(R.mipmap.pic2, "藍紹敏", "南京市長藍紹敏做客市長在線", "12,360"));
        mData.add(new HistoryVideoResp(R.mipmap.pic3, "藍紹敏", "常州市長藍紹敏做客市長在線", "12,361"));
        mData.add(new HistoryVideoResp(R.mipmap.pic2, "藍紹敏", "武漢市長藍紹敏做客市長在線", "12,362"));

        histroyAdapter.setmData(mData);
        histroyAdapter.setOnItemClickListener(new HistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(mContext, position + "", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initCommentData() {
        commentRclView.setLayoutManager(
                new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                });
        commentAdapter = new CommentAdapter(mContext);
//        commentRclView.addItemDecoration(
//                new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        commentRclView.setAdapter(commentAdapter);
        List<CommentResp> mDataList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mDataList.add(new CommentResp(R.mipmap.tx2, "南京用户xxxx6" + i, "2017-9-1" + i,
                    getResources().getString(R.string.comment_content_txt),
                    getResources().getString(R.string.mayor_content_txt)));
        }

        commentAdapter.setmData(mDataList);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (flag == 0) {
            doPause();
        } else if (flag == 1) {
            stop();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.praiseRLayout:
                Toast.makeText(mContext, "点赞能待开发", Toast.LENGTH_SHORT).show();
                break;
            case R.id.messageRLayout:
                Toast.makeText(mContext, "消息能待开发", Toast.LENGTH_SHORT).show();
                break;
            case R.id.giftRLayout:
                showAdmirePopupWindow(giftRLayout);
//                Toast.makeText(mContext, "禮物能待开发", Toast.LENGTH_SHORT).show();
                break;
            case R.id.forwardRLayout:
                doShare();
//                Toast.makeText(mContext, "转发功能待开发", Toast.LENGTH_SHORT).show();
                break;
            case R.id.moreVideoTxt:
                Toast.makeText(mContext, "更多", Toast.LENGTH_SHORT).show();
                break;
            case R.id.play:
                doPlay(flag, true);
                break;
//            case stop:
//                stop();
//                break;
            case R.id.pause:
                doPause();
                break;
            case R.id.replay:
                doRePlay();
                break;
            case R.id.switchLiveLayout:
                final Pop1Window pop1Window = new Pop1Window(mContext, switchLiveLayout);
                pop1Window.setOnItemClickListener(new Pop1Window.OnItemClickListener() {
                    @Override
                    public void onLiveClick() {
//                        if (flag != 1) {
                        isPrepare = false;
                        flag = 1;
                        doSwitchSth(flag, false);
//                        }
                        pop1Window.dismiss();
                    }

                    @Override
                    public void onVideoClick() {
//                        if (flag != 0) {
                        isPrepare = false;
                        flag = 0;
                        doSwitchSth(flag, false);
//                        }
                        pop1Window.dismiss();
                    }
                });
                pop1Window.showPop(switchLiveLayout);
                break;
        }
    }

    private String payActionType;
    private PopupWindow window;
    private EditText et_money;

    private void showAdmirePopupWindow(View parent) {

        payActionType = "Alipay";
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.admire_popupwindow_layout, null);
        //头像，打赏金额区域
        RelativeLayout rl_pop = (RelativeLayout) view.findViewById(R.id.admire_pop);
        final LinearLayout ll_icon_money = (LinearLayout) view.findViewById(R.id.ll_icon_money);
        ImageView iv_cancel = (ImageView) view.findViewById(R.id.iv_cancel);
        CircleImageView iv_user = (CircleImageView) view.findViewById(R.id.iv_user_img);
        et_money = (EditText) view.findViewById(R.id.et_admire_money);
        et_money.setFilters(filters);//筛选器，只能输入金额
        TextView tv_random_money = (TextView) view.findViewById(R.id.tv_random_money);
        //支付方式选择区域
        final LinearLayout ll_pay_action = (LinearLayout) view.findViewById(R.id.ll_pay_action);
        final ImageView iv_check_alipay = (ImageView) view.findViewById(R.id.iv_check_pay);
        final ImageView iv_check_weichat = (ImageView) view.findViewById(R.id.iv_check_weixin);

        final TextView tv_admire = (TextView) view.findViewById(R.id.tv_admire);
        window = new PopupWindow(view);
        window.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        window.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new BitmapDrawable());
        window.setFocusable(true);
        window.setOutsideTouchable(false);
        window.setTouchable(true);
        window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        window.showAtLocation(parent, Gravity.CENTER, 0, 0);
        window.update();
        CommonUtils.setWindow(0.2f, getActivity());

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
            }
        });
        tv_random_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float a = (float) (Math.random() * 20);
                float numb = a;
                DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                String p = decimalFormat.format(numb);//format 返回的是字符串
                if (p.startsWith(".")) {
                    p = "0" + p;
                }
                et_money.setText(p);
            }
        });
        iv_check_alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payActionType.equals("Alipay")) {
                    return;
                }
                payActionType = "Alipay";
                iv_check_alipay.setImageResource(R.mipmap.icon_check_true);
                iv_check_weichat.setImageResource(R.mipmap.icon_default_check);
            }
        });
        iv_check_weichat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payActionType.equals("WEIXIN")) {
                    return;
                }
                payActionType = "WEIXIN";
                iv_check_alipay.setImageResource(R.mipmap.icon_default_check);
                iv_check_weichat.setImageResource(R.mipmap.icon_check_true);
            }
        });
        tv_admire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ll_icon_money.getVisibility() == View.VISIBLE) {
                    if (StringUtil.isEmpty(et_money.getText().toString())) {
                        ToastUtils.showToast("请输入打赏金额");
                        return;
                    }
                    float money = Float.parseFloat(et_money.getText().toString());
                    if (Float.parseFloat(et_money.getText().toString()) < Float.parseFloat("0.01")) {
                        ToastUtils.showToast("打赏金额不能少于0.01元");
                        return;
                    }
                    CommonUtils.hideSoft(getActivity());
                    ll_icon_money.setVisibility(View.GONE);
                    ll_pay_action.setVisibility(View.VISIBLE);
                    tv_admire.setText("确认");

                } else {
//                    showLoadingDialog("");
                    pay(payActionType, et_money.getText().toString());
                }
            }
        });
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                window = null;
                CommonUtils.setWindow(1, getActivity());
            }
        });
        rl_pop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    CommonUtils.hideSoft(getActivity());
                }
                return true;
            }
        });
    }

    private void pay(String payType, String admireMoney) {
        presenter.doPay("", admireMoney, "", payType);
    }

    public void getNewsAdmireWeChatOrderSuccess(OrderResp resp) {
        Constant.WEIWHAT_PAY_TYPE = 1;
        Constant.ADMIRE_PAY_MONRY = et_money.getText().toString();
        Gson gson = new Gson();
        Object orderObject = resp.getData();
        WeiChatCustomOrder order = new WeiChatCustomOrder();
        order.parseDataFromJsonStr(gson.toJson(orderObject));
        PayUtil.startWeiChatPay(getActivity(), order);
    }

    public void getNewsAdmireAlipayOrderSuccess(OrderResp resp) {
        Object orderObject = resp.getData();
        Gson gson = new Gson();
        AlipayOrderInfo orderInfo = new AlipayOrderInfo();
        orderInfo.parseDataFromJsonStr(gson.toJson(orderObject));
        PayUtil.startAlipay(getActivity(), orderInfo.getAlipayOrderInfo(), new PayUtil.AlipayResultCallback() {
            @Override
            public void paySuccess() {
//                Intent intent = new Intent(getActivity(), AdmireNewsResultActivity.class);
//                intent.putExtra("admireMoney", et_money.getText().toString());
//                startActivity(intent);
                ToastUtils.showToast("支付成功");
            }

            @Override
            public void payFail() {
                showShortToast("支付失败");
            }
        });
    }

    public void getNewsAdmireOrderFail(String msg) {
        showShortToast(msg);
    }

    /**
     * 切换按钮
     *
     * @param flag
     */
    private void doSwitchSth(int flag, boolean b) {
        doSwitch(flag, b);
        if (flag == 0) {//录播
            mUrl = "http://player.alicdn.com/video/aliyunmedia.mp4";
        } else if (flag == 1) {//直播
            mUrl = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
//            mUrl = "rtmp:// vhost=www.zhidingmingcheng.com/AppName/StreamName";
        }
        doPlay(flag, b);

    }

    /**
     * 分享
     */
    private void doShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("市长在线");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(mContext);
    }

    /**
     * 监听网络变化
     */
    private NetWatchdog.NetChangeListener netVodChangeListener = new NetWatchdog.NetChangeListener() {
        @Override
        public void onWifiTo4G() {
            if (mPlayer.isPlaying()) {
                pause();
            }
            if (netChangeDialog == null || !netChangeDialog.isShowing()) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setTitle("网络切换为4G");
                alertDialog.setMessage("是否继续播放？");
                alertDialog.setPositiveButton("是",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int playerState = mPlayer.getPlayerState();
                                if (playerState == AliVcMediaPlayer.STOPPED) {
                                    seekTo(progressBar.getProgress());
                                    mPlayer.prepareAndPlay(mUrl);
                                } else if (playerState == AliVcMediaPlayer.PAUSED) {
                                    resume();
                                } else {
                                    stop();
                                    seekTo(progressBar.getProgress());
                                    start();
                                }
                            }
                        });
                alertDialog.setNegativeButton("否", null);
                netChangeDialog = alertDialog.create();
                netChangeDialog.show();
            }

            Toast.makeText(mContext.getApplicationContext(),
                    "网络切换为4G", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void on4GToWifi() {
            Toast.makeText(mContext.getApplicationContext(),
                    "网络切换为WIFI", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onNetDisconnected() {
            Toast.makeText(mContext.getApplicationContext(),
                    "网络已断开", Toast.LENGTH_SHORT).show();
        }
    };


    /**
     * 播放器 回调
     */
    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
//                holder.setType(SurfaceHolder.SURFACE_TYPE_GPU);
            holder.setKeepScreenOn(true);
            Log.e("lfj0930", "surfaceCreated ");
            // Important: surfaceView changed from background to front, we need reset surface to mediaplayer.
            // 对于从后台切换到前台,需要重设surface;部分手机锁屏也会做前后台切换的处理
            if (mPlayer != null) {
                mPlayer.setVideoSurface(holder.getSurface());
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (mPlayer != null) {
                mPlayer.setSurfaceChanged();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.e("lfj0930", "surfaceDestroyed ");
        }
    };


    /**
     * 进度条监听
     */
    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (mPlayer != null) {
                seekTo(seekBar.getProgress());
                Log.d("lfj0929", "onStopTrackingTouch , inSeek= " + inSeek);
            }
        }
    };

    /**
     * 静音 监听
     */
    private CheckBox.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.isChecked()) {
                mMute = true;
                if (mPlayer != null) {
                    mPlayer.setMuteMode(mMute);
                }
            } else {
                mMute = false;
                if (mPlayer != null) {
                    mPlayer.setMuteMode(mMute);
                }
            }
        }
    };

    /**
     * 初始化 镜像文件
     */
    private void initMirror() {
        if (mPlayer != null) {
            mPlayer.setRenderMirrorMode(mirrorMode);
        }
    }

    /**
     * 初始化 视频旋转角度 0度
     */
    private void initRote() {
        if (mPlayer != null) {
            mPlayer.setRenderRotate(roate);
        }
    }

    /**
     * 初始化 播放速度 1.0x
     */
    private void initSpeed() {
        if (mPlayer != null) {
            mPlayer.setPlaySpeed(speed);
        }
    }

    /**
     * 原屏幕比例
     */
    private void initAutoScaleModeFit() {
        if (mPlayer != null) {
            mPlayer.setVideoScalingMode(
                    MediaPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT);
        }
    }

    /**
     * 剪裁占满全屏幕
     */
    private void initAutoScaleModeFill() {
        if (mPlayer != null) {
            mPlayer.setVideoScalingMode(
                    MediaPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        }
    }

    private void seekTo(int position) {
        if (mPlayer == null) {
            return;
        }

        if (isCompleted) {
            inSeek = false;
            return;
        }


        if (lastSeekTime < 0) {
            lastSeekTime = System.currentTimeMillis();

            inSeek = true;
            mPlayer.seekTo(position);
        } else {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastSeekTime > 1000) {//1000ms
                inSeek = true;
                mPlayer.seekTo(position);
                lastSeekTime = currentTime;
            }
        }

    }

    private long lastSeekTime = -1;

    private AlertDialog netChangeDialog = null;

    private void initVodPlayer() {

        mPlayer.setPreparedListener(new MyPrepareListener(this));
        mPlayer.setPcmDataListener(new MyPcmDataListener(this));
        mPlayer.setCircleStartListener(new MyCircleStartListener(this));
        mPlayer.setFrameInfoListener(new MyFrameInfoListener(this));
        mPlayer.setErrorListener(new MyErrorListener(this));
        mPlayer.setCompletedListener(new MyCompletedListener(this));
        mPlayer.setSeekCompleteListener(new MySeekCompleteListener(this));
        //打开、关闭播放器日志
        mPlayer.enableNativeLog();
//        mPlayer.disableNativeLog();
    }


    private void doRePlay() {
        isCompleted = false;
        inSeek = false;
        replay();
    }

    private void doPause() {
        if (mPlayer.isPlaying()) {
            pause();
//            pauseBtn.setText("继续");
        } else {
            resume();
//            pauseBtn.setText("暂停");
        }
    }

    private static int FLAG_POSI_VIDEO = 0;//录播

    private static int FLAG_POSI_LIVE = 0;//直播


    private void doPlay(int flag, boolean b) {
        if (flag == 0) {//录播
            if (b) {
                if (FLAG_POSI_VIDEO == 0) {
                    if (isPrepare) {
                        doPause();
                    } else {
                        start();
                    }
                    playBtn.setSelected(true);
                    FLAG_POSI_VIDEO = 1;
                } else if (FLAG_POSI_VIDEO == 1) {
                    playBtn.setSelected(false);
                    doPause();
                    FLAG_POSI_VIDEO = 0;
                }
            } else {
                doPause();
                start();
            }

            mPlayer.setPlaySpeed(speed);
        } else if (flag == 1) {//直播
            if (b) {
                if (FLAG_POSI_LIVE == 0) {
                    replay();
                    playBtn.setSelected(true);
                    FLAG_POSI_LIVE = 1;
                } else if (FLAG_POSI_LIVE == 1) {
                    stop();
                    playBtn.setSelected(false);
                    FLAG_POSI_LIVE = 0;
                }
            } else {
                stop();
                replay();
            }

        }
        initAutoScaleModeFill();
        if (mMute) {
            mPlayer.setMuteMode(mMute);
        }
    }

    private static class MyPrepareListener implements MediaPlayer.MediaPlayerPreparedListener {
        private WeakReference<HomeFragment> vodModeActivityWeakReference;

        public MyPrepareListener(HomeFragment vodModeActivity) {
            vodModeActivityWeakReference = new WeakReference<HomeFragment>(vodModeActivity);
        }

        @Override
        public void onPrepared() {
            HomeFragment vodModeActivity = vodModeActivityWeakReference.get();
            if (vodModeActivity != null) {
                vodModeActivity.onPrepared();
            }
        }
    }

    private void onPrepared() {
        Toast.makeText(mContext, "准备成功",
                Toast.LENGTH_SHORT).show();
        mPlayer.play();
        inSeek = false;
//        pauseBtn.setText("暂停");
    }

    private static class MyPcmDataListener implements MediaPlayer.MediaPlayerPcmDataListener {

        private WeakReference<HomeFragment> vodModeActivityWeakReference;

        public MyPcmDataListener(HomeFragment vodModeActivity) {
            vodModeActivityWeakReference = new WeakReference<HomeFragment>(vodModeActivity);
        }


        @Override
        public void onPcmData(byte[] bytes, int i) {
            HomeFragment vodModeActivity = vodModeActivityWeakReference.get();
            if (vodModeActivity != null) {
                vodModeActivity.onPcmData(bytes, i);
            }
        }
    }

    private void onPcmData(byte[] bytes, int i) {
        //pcm数据获取到了
    }

    private static class MyCircleStartListener
            implements MediaPlayer.MediaPlayerCircleStartListener {
        private WeakReference<HomeFragment> vodModeActivityWeakReference;

        public MyCircleStartListener(HomeFragment vodModeActivity) {
            vodModeActivityWeakReference = new WeakReference<HomeFragment>(vodModeActivity);
        }

        @Override
        public void onCircleStart() {

            HomeFragment vodModeActivity = vodModeActivityWeakReference.get();
            if (vodModeActivity != null) {
                vodModeActivity.onCircleStart();
            }
        }
    }

    private void onCircleStart() {
        //循环播放开始
        Log.d("lfj0929", "MediaPlayerCircleStartListener onCircleStart  ");
    }

    private static class MyErrorListener implements MediaPlayer.MediaPlayerErrorListener {

        private WeakReference<HomeFragment> vodModeActivityWeakReference;

        public MyErrorListener(HomeFragment vodModeActivity) {
            vodModeActivityWeakReference = new WeakReference<HomeFragment>(vodModeActivity);
        }


        @Override
        public void onError(int i, String msg) {
            HomeFragment vodModeActivity = vodModeActivityWeakReference.get();
            if (vodModeActivity != null) {
                vodModeActivity.onError(i, msg);
            }
        }
    }

    private void onError(int i, String msg) {
        pause();
        Toast.makeText(mContext,
                "失败！！！！原因：" + msg, Toast.LENGTH_SHORT).show();
    }

    private static class MyCompletedListener implements MediaPlayer.MediaPlayerCompletedListener {

        private WeakReference<HomeFragment> vodModeActivityWeakReference;

        public MyCompletedListener(HomeFragment vodModeActivity) {
            vodModeActivityWeakReference = new WeakReference<HomeFragment>(vodModeActivity);
        }

        @Override
        public void onCompleted() {
            HomeFragment vodModeActivity = vodModeActivityWeakReference.get();
            if (vodModeActivity != null) {
                vodModeActivity.onCompleted();
            }
        }
    }

    private void onCompleted() {
        isCompleted = true;
        showVideoProgressInfo();
        stopUpdateTimer();
    }

    private static class MySeekCompleteListener
            implements MediaPlayer.MediaPlayerSeekCompleteListener {


        private WeakReference<HomeFragment> vodModeActivityWeakReference;

        public MySeekCompleteListener(HomeFragment vodModeActivity) {
            vodModeActivityWeakReference = new WeakReference<HomeFragment>(vodModeActivity);
        }

        @Override
        public void onSeekCompleted() {
            HomeFragment vodModeActivity = vodModeActivityWeakReference.get();
            if (vodModeActivity != null) {
                vodModeActivity.onSeekCompleted();
            }
        }
    }

    private void onSeekCompleted() {
        inSeek = false;
    }


    private static class MyFrameInfoListener implements MediaPlayer.MediaPlayerFrameInfoListener {

        private WeakReference<HomeFragment> vodModeActivityWeakReference;

        public MyFrameInfoListener(HomeFragment vodModeActivity) {
            vodModeActivityWeakReference = new WeakReference<HomeFragment>(vodModeActivity);
        }

        @Override
        public void onFrameInfoListener() {
            HomeFragment vodModeActivity = vodModeActivityWeakReference.get();
            if (vodModeActivity != null) {
                vodModeActivity.onFrameInfoListener();
            }
        }
    }

    private void onFrameInfoListener() {
        inSeek = false;
        showVideoProgressInfo();
    }


    private void showVideoProgressInfo() {

        if (mPlayer != null) {

            int curPosition = (int) mPlayer.getCurrentPosition();
            int duration = (int) mPlayer.getDuration();
            int bufferPosition = mPlayer.getBufferPosition();
            Log.d("lfj0929",
                    "curPosition = " + curPosition + " , duration = " + duration + " ， inSeek = " + inSeek);

            if (!inSeek) {
                positionTxt.setText(Formatter.formatTime(curPosition));
                durationTxt.setText(Formatter.formatTime(duration));
                progressBar.setMax(duration);
                progressBar.setSecondaryProgress(bufferPosition);
                progressBar.setProgress(curPosition);
            }
        }

        startUpdateTimer();
    }

    private void startUpdateTimer() {
        progressUpdateTimer.removeMessages(0);
        progressUpdateTimer.sendEmptyMessageDelayed(0, 1000);
    }

    private void stopUpdateTimer() {
        progressUpdateTimer.removeMessages(0);
    }

    private Handler progressUpdateTimer = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            showVideoProgressInfo();
        }
    };
    private boolean isPrepare;

    private void start() {

        Log.e("lfj0929", "VodmodeAtivity start() mPlayer  =  " + mPlayer);
        if (mPlayer != null) {
            mPlayer.prepareToPlay(mUrl);
            isPrepare = true;
        }
    }

    private void startLive() {
        if (mPlayer != null) {
            mPlayer.prepareAndPlay(mUrl);
        }
    }

    private void pause() {
        if (mPlayer != null) {
            mPlayer.pause();
//            pauseBtn.setText("继续");
        }
    }

    private void stop() {

        if (mPlayer != null) {
            mPlayer.stop();
        }
    }

    private void resume() {
        if (mPlayer != null) {
            VcPlayerLog.d("lfj0927", "mPlayer.play");
            mPlayer.play();
//            pauseBtn.setText("暂停");
        }
    }

    private void destroy() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.destroy();
        }
    }

    private void replay() {
        stop();
        startLive();
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("lfj0930", "VodmodeAtivity onResume()");

    }

    @Override
    public void onStop() {
        super.onStop();
        savePlayerState();
        Log.e("lfj0930", "VodmodeAtivity onStop()");
    }

    private void savePlayerState() {
        if (mPlayer.isPlaying()) {
            //we pause the player for not playing on the background
            // 不可见，暂停播放器
            pause();
        }
    }

    @Override
    public void onDestroy() {
        stop();
        destroy();
        stopUpdateTimer();
        progressUpdateTimer = null;
        netWatchdog.stopWatch();
        Log.e("lfj0930", "VodmodeAtivity onDestroy()");
        super.onDestroy();
    }
}
