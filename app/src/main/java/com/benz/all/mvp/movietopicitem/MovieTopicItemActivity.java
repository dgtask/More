package com.benz.all.mvp.movietopicitem;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.benz.all.R;
import com.benz.all.entity.movie.MovieTopicItemResponse;
import com.benz.all.entity.movie.tiantan.Subjects;
import com.benz.all.mvp.MVPBaseActivity;
import com.benz.all.mvp.moviedetail.MovieDetailActivity;
import com.benz.all.ui.views.RecyclerViewHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */
public class MovieTopicItemActivity extends MVPBaseActivity<MovieTopicItemContract.View, MovieTopicItemPresenter> implements MovieTopicItemContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    /**
     * 列表帮助类
     */
    RecyclerViewHelper mRecyclerViewHelper;
    /**
     * 适配器
     */
    BaseQuickAdapter<Subjects, BaseViewHolder> mCommonAdapter;

    int pageIndex = 1;

    Subjects subjects;

    /**
     * 跳转到电影详情
     * @param mContext
     * @param subjects
     */
    public static void goActivity(Context mContext, Subjects subjects){
        Intent intent = new Intent(mContext, MovieTopicItemActivity.class);
        intent.putExtra("Subjects", subjects);
        mContext.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_topic_item;
    }

    @Override
    protected void initViewsAndEvents() {

        subjects = (Subjects)getIntent().getSerializableExtra("Subjects");

        initToolBar(mToolbar, true, subjects.getParentName());

        mCommonAdapter = mPresenter.getCommonAdapter(new ArrayList<Subjects>());

        mRecyclerViewHelper = mPresenter.getRecyclerViewHelper(this, mCommonAdapter);
        mRecyclerViewHelper.autoRefresh();
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        call = mPresenter.getMovieTopicItems(pageIndex, subjects.getParentId());
    }

    @Override
    public void onLoadMore() {
        ++pageIndex;
        call = mPresenter.getMovieTopicItems(pageIndex, subjects.getParentId());
    }

    @Override
    public void onItemClick(int position, ImageView imageView) {
        MovieDetailActivity.goActivity(this, mCommonAdapter.getItem(position), imageView);
    }

    @Override
    public void onSuccess(MovieTopicItemResponse response) {
        mRecyclerViewHelper.onLoadComplete();
        mRecyclerViewHelper.onLoadData("暂无电影", pageIndex, response.getBody());

        // 添加头部空白区域
        if(mCommonAdapter.getHeaderLayoutCount() == 0){
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.height = 20;
            TextView view = new TextView(this);
            view.setLayoutParams(layoutParams);
            mRecyclerViewHelper.setHeaderView(view);
        }
    }

    @Override
    public void onFailure(Call<MovieTopicItemResponse> call, Throwable t) {
        mRecyclerViewHelper.onLoadComplete();
        mRecyclerViewHelper.showError("网络异常");
    }
}