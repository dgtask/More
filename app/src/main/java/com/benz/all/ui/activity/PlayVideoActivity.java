package com.benz.all.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.KeyEvent;

import com.benz.all.R;
import com.benz.all.mvp.BaseActivity;
import com.dl7.player.media.IjkPlayerView;

import butterknife.BindView;

/**
 * Created by xubenliang on 2017/6/20.
 */
public class PlayVideoActivity extends BaseActivity {

    @BindView(R.id.videoplayer)
    IjkPlayerView videoPlayer;

    /**
     * 跳转到电影详情
     *
     * @param mContext
     * @param videoUrl
     */
    public static void start(Activity mContext, String title, String videoUrl) {
        Intent intent = new Intent(mContext, PlayVideoActivity.class);
        intent.putExtra("Title", title);
        intent.putExtra("VideoUrl", videoUrl);
        mContext.startActivity(intent);
    }

    /**
     * 跳转到电影详情
     *
     * @param mContext
     * @param movieId
     */
    public static void start(Activity mContext, String title, Long movieId) {
        Intent intent = new Intent(mContext, PlayVideoActivity.class);
        intent.putExtra("Title", title);
        intent.putExtra("MovieId", movieId);
        mContext.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_play_video;
    }

    @Override
    protected void initViewsAndEvents() {
        String title = getIntent().getStringExtra("Title");
        String videoUrl = getIntent().getStringExtra("VideoUrl");// "http://43.241.227.35/btmovie/MoviePlay.m3u8?movieid=1945330";
        Long movidId = getIntent().getLongExtra("MovieId", 0);
        if (videoUrl == null) {
            videoUrl = "http://43.241.227.35/btmovie/MoviePlay.m3u8?movieid=" + movidId;
        }
        // 初始化播放器
        videoPlayer.init()
                .setTitle(title)
                .alwaysFullScreen()
                .setVideoPath(videoUrl)
                .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH)
                .start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onPause();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        videoPlayer.configurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (videoPlayer.handleVolumeKey(keyCode)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (videoPlayer.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoPlayer.onDestroy();
    }
}
