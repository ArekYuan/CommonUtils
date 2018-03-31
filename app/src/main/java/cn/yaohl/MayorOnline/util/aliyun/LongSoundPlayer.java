package cn.yaohl.MayorOnline.util.aliyun;

import android.content.Context;
import android.media.MediaPlayer;

/*
 * Copyright (C) 2010-2017 Alibaba Group Holding Limited.
 */
public class LongSoundPlayer {


    private MediaPlayer soundPool;//声明一个SoundPool
    private int mRawId = -1;
    private Context mContext;

    public LongSoundPlayer(Context context, int rawId) {
        mContext = context;
        mRawId = rawId;
    }

    public void play() {
        if (soundPool != null) {
            soundPool.reset();
            soundPool.release();
        }
        soundPool = MediaPlayer.create(mContext, mRawId);
        soundPool.setVolume(0.25f, 0.25f);
        soundPool.start();//开始播放
    }

    public void release() {
        if (soundPool == null){
            return;
        }

        if (soundPool.isPlaying()) {
            soundPool.stop();
        }
        soundPool.release();
    }
}
