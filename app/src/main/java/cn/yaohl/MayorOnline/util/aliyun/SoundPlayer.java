package cn.yaohl.MayorOnline.util.aliyun;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/*
 * Copyright (C) 2010-2017 Alibaba Group Holding Limited.
 */
public class SoundPlayer {

    private SoundPool soundPool;//声明一个SoundPool
    private int lastSoundId = -1;
    private Context mContext;

    public SoundPlayer(Context context, int rawId) {
        mContext = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder().build();
        } else {
            //noinspection deprecation
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
        lastSoundId = soundPool.load(mContext, rawId, 1);
    }

    public void play() {
        soundPool.play(
                lastSoundId,
                0.25f,   //左耳道音量【0~1】
                0.25f,   //右耳道音量【0~1】
                0,     //播放优先级【0表示最低优先级】
                0,     //循环模式【0表示播放一次，-1表示一直循环，其他表示数字+1表示当前数字对应的循环次数】
                1     //播放速度【1是正常，范围从0~2】
        );
    }

    public void release() {

        soundPool.release();
    }
}
