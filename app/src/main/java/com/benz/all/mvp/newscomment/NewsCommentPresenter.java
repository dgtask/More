package com.benz.all.mvp.newscomment;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.benz.all.R;
import com.benz.all.api.Api;
import com.benz.all.api.CnBetaParam;
import com.benz.all.app.Constants;
import com.benz.all.entity.news.CommentItem;
import com.benz.all.entity.news.NewsCommentResponse;
import com.benz.all.mvp.BasePresenterImpl;
import com.benz.all.net.RetrofitNews;
import com.benz.all.ui.views.RecyclerViewHelper;
import com.benz.all.utils.FormatTimeUtils;
import com.benz.all.utils.JsonUtils;
import com.benz.all.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.socks.library.KLog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * MVPPlugin
 * Created by xubenliang on 2017/6/4.
 */
public class NewsCommentPresenter extends BasePresenterImpl<NewsCommentContract.View> implements NewsCommentContract.Presenter {

    @Override
    public BaseQuickAdapter<CommentItem, BaseViewHolder> getCommonAdapter(List<CommentItem> list) {
        return new BaseQuickAdapter<CommentItem, BaseViewHolder>(R.layout.activity_news_comment_item, list) {
            @Override
            protected void convert(BaseViewHolder holder, CommentItem commentItem) {
                holder.setText(R.id.comment_username, commentItem.getUesrname() == null ? "匿名人士" : commentItem.getUesrname());
                holder.setText(R.id.comment_time, Utils.noNull(FormatTimeUtils.getTimeRange(commentItem.getCreated_time())));
                holder.setText(R.id.comment_support, Utils.noNull(commentItem.getSupport()));
                holder.setText(R.id.comment_against, Utils.noNull(commentItem.getAgainst()));
                holder.setText(R.id.comment_content, Utils.noNull(commentItem.getContent()));
            }
        };
    }

    @Override
    public RecyclerViewHelper getRecyclerViewHelper(Activity mContext, BaseQuickAdapter<CommentItem, BaseViewHolder> mCommonAdapter) {
        return RecyclerViewHelper.init(mContext)
                .setSwipeToLoadLayout((SwipeRefreshLayout) mContext.findViewById(R.id.swipeRefreshLayout))
                .setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false))
                .setRecyclerView((RecyclerView) mContext.findViewById(R.id.recyclerView))
                .setCommonAdapter(mCommonAdapter)
                .setEnableDivider(true)
                .setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mView.onRefresh();
                    }
                }).setComplete();
    }

    @Override
    public Call<NewsCommentResponse> getNewsComment(String articleId, int pageIndex) {
        Api api = RetrofitNews.getInstance().create(Api.class);
        CnBetaParam param = new CnBetaParam();
        param.setPage(pageIndex);
        param.setSid(articleId);
        param.setTimestamp(System.currentTimeMillis());
        param.setMethod(Constants.CNBETA_TYPE_NEWS_COMMENT);
        Call<NewsCommentResponse> call = api.getNewsComment(param.getParam());
        call.enqueue(new retrofit2.Callback<NewsCommentResponse>() {
            @Override
            public void onResponse(Call<NewsCommentResponse> call, Response<NewsCommentResponse> response) {
                KLog.json(JsonUtils.toJson(response.body()));
                if (mView == null) {
                    return;
                }
                mView.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<NewsCommentResponse> call, Throwable t) {
                if (mView == null) {
                    return;
                }
                mView.onFailure(call, t);
            }
        });
        return call;
    }
}