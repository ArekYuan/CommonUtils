package cn.yaohl.MayorOnline.ui.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alivc.player.AliVcMediaPlayer;
import com.alivc.player.MediaPlayer;
import com.alivc.player.VcPlayerLog;
import com.aliyun.vodplayerview.utils.NetWatchdog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.yaohl.MayorOnline.R;
import cn.yaohl.MayorOnline.ui.BaseFragment;
import cn.yaohl.MayorOnline.ui.home.adapter.CommentAdapter;
import cn.yaohl.MayorOnline.ui.home.adapter.HistoryAdapter;
import cn.yaohl.MayorOnline.ui.home.beans.CommentResp;
import cn.yaohl.MayorOnline.ui.home.beans.HistoryVideoResp;
import cn.yaohl.MayorOnline.ui.home.presenter.HomePresenter;
import cn.yaohl.MayorOnline.util.aliyun.Formatter;

/**
 * 作者：袁光跃
 * 日期：2018/1/18
 * 描述：
 * 邮箱：813665242@qq.com
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

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
    private Button playBtn;
    private Button pauseBtn;
    private Button replayBtn;
    private Button stopBtn;
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

    private String mUrl = null;

    private NetWatchdog netWatchdog;
    private int flag = 1;
    HomePresenter presenter;

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
        playBtn = (Button) v.findViewById(R.id.play);
        stopBtn = (Button) v.findViewById(R.id.stop);
        pauseBtn = (Button) v.findViewById(R.id.pause);
        replayBtn = (Button) v.findViewById(R.id.replay);
        muteOnBtn = (CheckBox) v.findViewById(R.id.muteOn);
        progress_layout = (LinearLayout) v.findViewById(R.id.progress_layout);

        positionTxt = (TextView) v.findViewById(R.id.currentPosition);
        durationTxt = (TextView) v.findViewById(R.id.totalDuration);
        progressBar = (SeekBar) v.findViewById(R.id.progress);

        playBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        pauseBtn.setOnClickListener(this);
        replayBtn.setOnClickListener(this);

        //        initAutoScaleModeFit();
        netWatchdog = new NetWatchdog(getActivity());
        mPlayer = new AliVcMediaPlayer(mContext, mSurfaceView);
        initRote();
        initMirror();
        if (flag == 0) {//录播
            initSpeed();
            initVodPlayer();
            progressBar.setOnSeekBarChangeListener(seekBarChangeListener);
            netWatchdog.setNetChangeListener(netVodChangeListener);
        } else if (flag == 1) {//直播
            pauseBtn.setVisibility(View.GONE);
            replayBtn.setVisibility(View.GONE);
            progress_layout.setVisibility(View.GONE);
            presenter.initLive(mUrl, mPlayer, netWatchdog, mSurfaceView);
            initLivePlayer();
            netWatchdog.setNetChangeListener(netLiveChangeListener);
        }

        muteOnBtn.setOnCheckedChangeListener(changeListener);
        mSurfaceView.getHolder().addCallback(callback);
        netWatchdog.startWatch();

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
                Toast.makeText(mContext, "禮物能待开发", Toast.LENGTH_SHORT).show();
                break;
            case R.id.forwardRLayout:
                Toast.makeText(mContext, "转发功能待开发", Toast.LENGTH_SHORT).show();
                break;
            case R.id.moreVideoTxt:
                Toast.makeText(mContext, "更多", Toast.LENGTH_SHORT).show();
                break;
            case R.id.play:
                doPlay(flag);
                break;
            case R.id.stop:
                stop();
                break;
            case R.id.pause:
                doPause();
                break;
            case R.id.replay:
                doRePlay();
                break;
        }
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
            pauseBtn.setText("继续");
        } else {
            resume();
            pauseBtn.setText("暂停");
        }
    }

    private void doPlay(int flag) {
        if (flag == 0) {//录播
            start();
            mPlayer.setPlaySpeed(speed);
        } else if (flag == 1) {//直播
            replay();
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
        pauseBtn.setText("暂停");
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

    private void start() {

        Log.e("lfj0929", "VodmodeAtivity start() mPlayer  =  " + mPlayer);
        if (mPlayer != null) {
            mPlayer.prepareToPlay(mUrl);
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
            pauseBtn.setText("继续");
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
            pauseBtn.setText("暂停");
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
