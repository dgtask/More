package com.benz.all.mvp.newscomment;


import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;

import com.benz.all.R;
import com.benz.all.api.CBUrlHelper;
import com.benz.all.entity.news.CommentItem;
import com.benz.all.entity.news.NewsCommentResponse;
import com.benz.all.mvp.MVPBaseActivity;
import com.benz.all.ui.views.RecyclerViewHelper;
import com.benz.all.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;


/**
 * MVPPlugin
 *  Created by xubenliang on 2017/6/4.
 */

public class NewsCommentActivity extends MVPBaseActivity<NewsCommentContract.View, NewsCommentPresenter> implements NewsCommentContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    /**
     * 列表帮助类
     */
    private RecyclerViewHelper mRecyclerViewHelper;
    /**
     * 适配器
     */
    private BaseQuickAdapter<CommentItem, BaseViewHolder> mCommonAdapter;

    private int pageIndex = 1;

    private String articleId;

    /**
     * 跳转到新闻详情
     *
     * @param mContext
     * @param articleId
     */
    public static void start(Context mContext, String articleId) {
        Intent intent = new Intent(mContext, NewsCommentActivity.class);
        intent.putExtra("ArticleId", articleId);
        mContext.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_comment;
    }

    @Override
    protected void initViewsAndEvents() {

        initToolBar(mToolbar, true, "评论");

        articleId = getIntent().getStringExtra("ArticleId");

        mCommonAdapter = mPresenter.getCommonAdapter(new ArrayList<CommentItem>());

        mRecyclerViewHelper = mPresenter.getRecyclerViewHelper(this, mCommonAdapter);

        mRecyclerViewHelper.autoRefresh();
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        call = mPresenter.getNewsComment(articleId, pageIndex);
    }

    @Override
    public void onSuccess(NewsCommentResponse response) {
        mRecyclerViewHelper.onLoadComplete();
        mRecyclerViewHelper.onLoadData("暂无评论", pageIndex, response.getResult());
    }

    @Override
    public void onFailure(Call<NewsCommentResponse> call, Throwable t) {
        mRecyclerViewHelper.onLoadComplete();
        mRecyclerViewHelper.showError("网络异常");
    }
}