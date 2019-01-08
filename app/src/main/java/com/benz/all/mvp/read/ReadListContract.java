package com.benz.all.mvp.read;

import android.content.Context;

import com.benz.all.entity.read.Article;
import com.benz.all.entity.read.ReadResponse;
import com.benz.all.mvp.BasePresenter;
import com.benz.all.mvp.BaseView;
import com.benz.all.ui.views.RecyclerViewHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import retrofit2.Call;

/**
 * MVPPlugin
 */
public class ReadListContract {

    interface View extends BaseView {
        void onCache(List<Article> result);

        void onRefresh();

        void onLoadMore();

        void onItemClick(int position);

        void onSuccess(ReadResponse response);

        void onFailure(Call<ReadResponse> call, Throwable t);
    }

    interface  Presenter extends BasePresenter<View> {

        void getCache();

        BaseQuickAdapter<Article, BaseViewHolder> getCommonAdapter(List<Article> list);

        RecyclerViewHelper getRecyclerViewHelper(Context mContext, android.view.View mRootView, BaseQuickAdapter<Article, BaseViewHolder> mCommonAdapter);

        Call<ReadResponse> getArticleList(int page);

        Call<ReadResponse> getArticleMoreList(String create_time, String update_time);

    }
}
