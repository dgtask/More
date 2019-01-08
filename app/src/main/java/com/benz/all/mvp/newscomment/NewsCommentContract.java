package com.benz.all.mvp.newscomment;

import android.app.Activity;

import com.benz.all.entity.news.CommentItem;
import com.benz.all.entity.news.NewsCommentResponse;
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
public class NewsCommentContract {

    interface View extends BaseView {
        void onRefresh();

        void onSuccess(NewsCommentResponse response);

        void onFailure(Call<NewsCommentResponse> call, Throwable t);
    }

    interface Presenter extends BasePresenter<View> {
        BaseQuickAdapter<CommentItem, BaseViewHolder> getCommonAdapter(List<CommentItem> list);

        RecyclerViewHelper getRecyclerViewHelper(Activity mContext, BaseQuickAdapter<CommentItem, BaseViewHolder> mCommonAdapter);

        Call<NewsCommentResponse> getNewsComment(String articleId, int pageIndex);
    }
}
