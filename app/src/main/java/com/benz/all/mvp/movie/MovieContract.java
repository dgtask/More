package com.benz.all.mvp.movie;

import android.content.Context;
import android.widget.ImageView;

import com.benz.all.entity.movie.MovieTopResponse;
import com.benz.all.entity.movie.tiantan.Subjects;
import com.benz.all.mvp.BasePresenter;
import com.benz.all.mvp.BaseView;
import com.benz.all.ui.adapter.MovieSectionAdapter;
import com.benz.all.ui.views.RecyclerViewHelper;

import java.util.List;

import retrofit2.Call;

/**
 * MVPPlugin
 *  Created by xubenliang on 2017/6/4.
 */
public class MovieContract {

    interface View extends BaseView {
        void onCache(List<Subjects> list);

        void onRefresh();

        void onItemClick(int position, ImageView imageView);

        void onSuccess(List<Subjects> list);

        void onFailure(Call<MovieTopResponse> call, Throwable t);
    }

    interface Presenter extends BasePresenter<MovieContract.View> {

        void getCache();

        MovieSectionAdapter getCommonAdapter(List<Subjects> subjectses);

        RecyclerViewHelper getRecyclerViewHelper(Context mContext, android.view.View mRootView, MovieSectionAdapter mCommonAdapter);

        Call<MovieTopResponse> getMovieTopList();
    }
}
