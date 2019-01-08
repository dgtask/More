package com.benz.all.mvp.movie;


import android.widget.ImageView;

import com.benz.all.R;
import com.benz.all.app.AppContext;
import com.benz.all.entity.movie.MovieTopResponse;
import com.benz.all.entity.movie.tiantan.Subjects;
import com.benz.all.mvp.MVPBaseFragment;
import com.benz.all.mvp.moviedetail.MovieDetailActivity;
import com.benz.all.mvp.movietopicitem.MovieTopicItemActivity;
import com.benz.all.ui.adapter.MovieSectionAdapter;
import com.benz.all.ui.views.RecyclerViewHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * MVPPlugin
 * Created by xubenliang on 2017/6/4.
 */

public class MovieFragment extends MVPBaseFragment<MovieContract.View, MoviePresenter> implements MovieContract.View {

    /**
     * 列表帮助类
     */
    private RecyclerViewHelper mRecyclerViewHelper;
    /**
     * 适配器
     */
    private MovieSectionAdapter mCommonAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie;
    }

    @Override
    protected void initViewsAndEvents() {

        mCommonAdapter = mPresenter.getCommonAdapter(new ArrayList<Subjects>());
        mRecyclerViewHelper = mPresenter.getRecyclerViewHelper(mContext, mRootView, mCommonAdapter);

        mPresenter.getCache();
    }

    @Override
    public void onCache(List<Subjects> list) {
        if (list != null) {
            mRecyclerViewHelper.onLoadData("暂无电影", 1, list);
        }
        if (!AppContext.recreate) {
            mRecyclerViewHelper.autoRefresh();
        }
    }

    @Override
    public void onRefresh() {
        call = mPresenter.getMovieTopList();
    }

    @Override
    public void onItemClick(int position, ImageView imageView) {
        Subjects subjects = mCommonAdapter.getItem(position);
        if (subjects.isHeader) {
            MovieTopicItemActivity.goActivity(mContext, subjects);
        } else {
            MovieDetailActivity.goActivity(getActivity(), subjects, imageView);
        }
    }

    @Override
    public void onSuccess(List<Subjects> list) {
        mRecyclerViewHelper.onLoadComplete();
        mRecyclerViewHelper.onLoadData("暂无电影", 1, list);
    }

    @Override
    public void onFailure(Call<MovieTopResponse> call, Throwable t) {
        mRecyclerViewHelper.onLoadComplete();
        mRecyclerViewHelper.showError("暂无电影");
    }
}