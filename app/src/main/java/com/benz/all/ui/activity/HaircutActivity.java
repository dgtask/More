package com.benz.all.ui.activity;

import android.app.Service;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.benz.all.R;
import com.benz.all.mvp.BaseActivity;

import butterknife.BindView;

public class HaircutActivity extends BaseActivity {

    @BindView(R.id.ivHaircut)
    ImageView ivHaircut;

    MediaPlayer mediaPlayer;

    boolean isPlaying;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_haircut;
    }

    @Override
    protected void initViewsAndEvents() {


        ivHaircut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    stopVoice();
                    ivHaircut.setImageResource(R.mipmap.haircut_off);
                    ivHaircut.clearAnimation();
                } else {
                    playVoice();
                    ivHaircut.setImageResource(R.mipmap.haircut_on);
                    ivHaircut.startAnimation(AnimationUtils.loadAnimation(HaircutActivity.this, R.anim.shake));
                }
            }
        });
    }

    /**
     * 开始播放
     */
    public void playVoice() {
        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.works);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            vibrate(100000000L);
            isPlaying = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止播放
     */
    public void stopVoice() {
        if (null != mediaPlayer) {
            mediaPlayer.stop();
            virateCancle();
            isPlaying = false;
        }
    }

    /**
     * 开始震动
     * @param milliseconds
     */
    public void vibrate(long milliseconds) {
        Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    /**
     * 取消震动
     */
    public void virateCancle(){
        Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        vib.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
