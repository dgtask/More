package com.benz.all.mvp.moviesearch;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.benz.all.entity.movie.MovieSearchResponse;
import com.benz.all.entity.movie.tiantan.SearchItem;
import com.benz.all.entity.news.NewsItem;
import com.benz.all.entity.news.NewsResponse;
import com.benz.all.mvp.BasePresenter;
import com.benz.all.mvp.BaseView;
import com.benz.all.ui.views.RecyclerViewHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import retrofit2.Call;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MovieSearchContract {
    interface View extends BaseView {

        void onRefresh();

        void onItemClick(int position, ImageView imageView);

        void onSuccess(List<SearchItem> searchItemList);

        void onFailure(Call<MovieSearchResponse> call, Throwable t);
    }

    interface  Presenter extends BasePresenter<View> {
        BaseQuickAdapter<SearchItem, BaseViewHolder> getCommonAdapter(List<SearchItem> list);

        RecyclerViewHelper getRecyclerViewHelper(Activity mContext, BaseQuickAdapter<SearchItem, BaseViewHolder> mCommonAdapter);

        Call<MovieSearchResponse> getMovieSearch(String keywords);
    }
}
