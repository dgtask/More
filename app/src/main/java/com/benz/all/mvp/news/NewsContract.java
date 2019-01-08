package com.benz.all.mvp.news;

import android.content.Context;

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
 * Created by xubenliang on 2017/6/4.
 */
public class NewsContract {

    interface View extends BaseView {
        void onCache(List<NewsItem> result);

        void onRefresh();

        void onLoadMore();

        void onItemClick(int position);

        void onSuccess(NewsResponse response);

        void onFailure(Call<NewsResponse> call, Throwable t);
    }

    interface Presenter extends BasePresenter<View> {
        void getCache();

        BaseQuickAdapter<NewsItem, BaseViewHolder> getCommonAdapter(List<NewsItem> list);

        RecyclerViewHelper getRecyclerViewHelper(Context mContext, android.view.View mRootView, BaseQuickAdapter<NewsItem, BaseViewHolder> mCommonAdapter);

        Call<NewsResponse> getNews();

        Call<NewsResponse> getNewsMore(String articleId);
    }
}
