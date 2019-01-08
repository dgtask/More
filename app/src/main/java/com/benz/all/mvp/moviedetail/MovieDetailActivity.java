package com.benz.all.mvp.moviedetail;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.benz.all.R;
import com.benz.all.entity.movie.MovieDetailResponse;
import com.benz.all.entity.movie.tiantan.Detail;
import com.benz.all.entity.movie.tiantan.Subjects;
import com.benz.all.mvp.MVPBaseActivity;
import com.benz.all.ui.activity.PlayVideoActivity;
import com.benz.all.ui.views.statusbar.StatusBarUtil;
import com.benz.all.utils.ToastUtils;
import com.benz.all.utils.Utils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import jp.wasabeef.picasso.transformations.BlurTransformation;
import retrofit2.Call;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MovieDetailActivity extends MVPBaseActivity<MovieDetailContract.View, MovieDetailPresenter> implements MovieDetailContract.View {

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.fabPlay)
    FloatingActionButton fabPlay;

    @BindView(R.id.ivThumb)
    ImageView ivThumb;
    @BindView(R.id.ivBlurBg)
    ImageView ivBlurBg;

    /**
     * 详情
     */
    @BindView(R.id.tvRelease)
    TextView tvRelease;
    @BindView(R.id.tvArea)
    TextView tvArea;
    @BindView(R.id.tvType)
    TextView tvType;
    @BindView(R.id.tvSource)
    TextView tvSource;

    @BindView(R.id.tvReleaseDetail)
    TextView tvReleaseDetail;
    @BindView(R.id.tvStatusDetail)
    TextView tvStatusDetail;
    @BindView(R.id.tvTypeDetail)
    TextView tvTypeDetail;
    @BindView(R.id.tvActorsDetail)
    TextView tvActorsDetail;
    @BindView(R.id.tvAreaDetail)
    TextView tvAreaDetail;
    @BindView(R.id.tvScoreDetail)
    TextView tvScoreDetailDetail;
    @BindView(R.id.tvUpdateDateDetail)
    TextView tvUpdateDateDetail;
    @BindView(R.id.tvDescDetail)
    TextView tvDescDetail;

    Subjects subjects;

    /**
     * 跳转到电影详情
     *
     * @param mContext
     * @param subjects
     * @param imageView
     */
    public static void goActivity(Activity mContext, Subjects subjects, ImageView imageView) {
        Intent intent = new Intent(mContext, MovieDetailActivity.class);
        intent.putExtra("Subjects", subjects);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(mContext,
                        imageView, Utils.getString(R.string.transition_movie_img));// 与xml文件对应
        ActivityCompat.startActivity(mContext, intent, options.toBundle());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected void initViewsAndEvents() {

        subjects = (Subjects) getIntent().getSerializableExtra("Subjects");
        if (subjects == null) {
            return;
        }
        // 设置标题栏
        toolbarLayout.setTitle(subjects.getName());
        toolbarLayout.setCollapsedTitleTextColor(Utils.getColor(R.color.colorAccent));
        initToolBar(mToolbar, true, subjects.getName());

        // 设置标题栏透明
        StatusBarUtil.setTranslucentForImageView(this, 0, mToolbar);

        // 设置图片
        setImageView();

        // 获取网络请求
        call = mPresenter.getMovieDetail(subjects.getMovieId(), subjects.getAlbum());
    }

    @Override
    public void onSuccess(final MovieDetailResponse response) {
        try {

            // 设置播放点击事件
            fabPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 播放地址
                    /*String videoUrl = response.getBody().getSources().get(0).getPlayUrl();
                    PlayVideoActivity.start(MovieDetailActivity.this, subjects.getName(), videoUrl);*/
                    PlayVideoActivity.start(MovieDetailActivity.this, subjects.getName(), subjects.getMovieId());
                }
            });
            // 设置电影详情
            setMovieDetail(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call<MovieDetailResponse> call, Throwable t) {
        ToastUtils.show("网络异常");
    }

    /**
     * 设置封面图与背景图
     */
    private void setImageView() {
        // 设置封面图
        Picasso.with(this)
                .load(subjects.getImg())
                .placeholder(R.mipmap.ic_movie_placeholder)
                .error(R.mipmap.ic_movie_placeholder)
                .config(Bitmap.Config.RGB_565)
                .into(ivThumb);

        // 设置高斯模糊背景
        Picasso.with(this)
                .load(subjects.getImg())
                .transform(new BlurTransformation(this, 70))
                .config(Bitmap.Config.RGB_565)
                .into(ivBlurBg);
    }

    /**
     * 设置详情
     *
     * @param detail
     */
    private void setMovieDetail(Detail detail) {
        if (detail != null) {
            tvRelease.setText("上映：" + detail.getRelease());
            tvType.setText("类型：" + detail.getType());
            tvArea.setText("地区：" + detail.getArea());
            tvSource.setText("来源：" + detail.getSourceTypes().get(0).getName() );

            tvReleaseDetail.setText("上映：" + detail.getRelease());
            tvStatusDetail.setText("状态：" + detail.getStatus());
            tvTypeDetail.setText("类型：" + detail.getType());
            tvActorsDetail.setText("主演：" + detail.getActors());
            tvAreaDetail.setText("地区：" + detail.getArea());
            tvScoreDetailDetail.setText("影片评分：" + detail.getScore());
            tvUpdateDateDetail.setText("更新日期：" + detail.getUpdateDate());
            tvDescDetail.setText("简介：" + detail.getDesc());
        }
    }

}