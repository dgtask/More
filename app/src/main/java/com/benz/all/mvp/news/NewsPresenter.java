package com.benz.all.mvp.news;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.benz.all.R;
import com.benz.all.api.Api;
import com.benz.all.api.CnBetaParam;
import com.benz.all.app.Constants;
import com.benz.all.callback.OnLoadMoreListener;
import com.benz.all.entity.news.NewsItem;
import com.benz.all.entity.news.NewsResponse;
import com.benz.all.mvp.BasePresenterImpl;
import com.benz.all.net.RetrofitNews;
import com.benz.all.ui.views.RecyclerViewHelper;
import com.benz.all.utils.FormatTimeUtils;
import com.benz.all.utils.JsonUtils;
import com.benz.all.utils.TasksUtils;
import com.benz.all.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.socks.library.KLog;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Response;

/**
 * MVPPlugin
 * Created by xubenliang on 2017/6/4.
 */
public class NewsPresenter extends BasePresenterImpl<NewsContract.View> implements NewsContract.Presenter {

    @Override
    public void getCache() {
        NewsResponse result = TasksUtils.getDataFromSp(Constants.NEWS_RESPONSE_CACHE_KEY);
        mView.onCache(result != null ? result.getResult() : null);
    }

    @Override
    public BaseQuickAdapter<NewsItem, BaseViewHolder> getCommonAdapter(List<NewsItem> list) {
        return new BaseQuickAdapter<NewsItem, BaseViewHolder>(R.layout.fragment_news_item, list) {
            @Override
            protected void convert(BaseViewHolder holder, NewsItem newsItem) {

                holder.setText(R.id.tvTitle, Utils.noNull(newsItem.getTitle()));
                holder.setText(R.id.tvBrief, Utils.noNull(newsItem.getSummary()));
                holder.setText(R.id.tvTime, FormatTimeUtils.getTimeRange(newsItem.getPubtime()) + "");
                holder.setText(R.id.tvReadNum, "阅读量：" + newsItem.getCounter());

                ImageView ivThumb = holder.getView(R.id.ivThumb);
                if (newsItem.getThumb() != null) {
                    Picasso.with(mContext)
                            .load(newsItem.getThumb())
                            .placeholder(R.mipmap.ic_news_placeholder)
                            .error(R.mipmap.ic_news_placeholder)
                            .config(Bitmap.Config.RGB_565)
                            .transform(new CropCircleTransformation())
                            .tag(mContext)
                            .into(ivThumb);
                } else {
                    ivThumb.setImageResource(R.mipmap.ic_news_placeholder);
                }
            }
        };
    }

    @Override
    public RecyclerViewHelper getRecyclerViewHelper(final Context mContext, View mRootView, final BaseQuickAdapter<NewsItem, BaseViewHolder> mCommonAdapter) {
        return RecyclerViewHelper.init(mContext)
                .setSwipeToLoadLayout((SwipeRefreshLayout) mRootView.findViewById(R.id.swipeRefreshLayout))
                .setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false))
                .setRecyclerView((RecyclerView) mRootView.findViewById(R.id.recyclerView))
                .setCommonAdapter(mCommonAdapter)
                .setEnableDivider(true)
                .setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mView.onRefresh();
                    }
                }).setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        mView.onLoadMore();
                    }
                }).setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        mView.onItemClick(position);
                    }
                })/*.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        final Picasso picasso = Picasso.with(mContext);
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            picasso.resumeTag(mContext);
                        } else {
                            picasso.pauseTag(mContext);
                        }
                        super.onScrollStateChanged(recyclerView, newState);
                    }
                })*/.setComplete();
    }


    @Override
    public Call<NewsResponse> getNews() {
        Api api = RetrofitNews.getInstance().create(Api.class);
        CnBetaParam param = new CnBetaParam();
        param.setMethod(Constants.CNBETA_TYPE_NEWS_LISTS);
        param.setTimestamp(System.currentTimeMillis());
        Call<NewsResponse> call = api.getNews(param.getParam());
        call.enqueue(new retrofit2.Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, final Response<NewsResponse> response) {
                KLog.json(JsonUtils.toJson(response.body()));
                TasksUtils.saveDataToSpAsyn(Constants.NEWS_RESPONSE_CACHE_KEY, response.body());
                if (mView == null) {
                    KLog.e("mView is null");
                    return;
                }
                mView.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                KLog.json(JsonUtils.toJson(call.request()));
                if (mView == null) {
                    KLog.e("mView is null");
                    return;
                }
                mView.onFailure(call, t);
            }
        });
        return call;
    }

    @Override
    public Call<NewsResponse> getNewsMore(String articleId) {
        Api api = RetrofitNews.getInstance().create(Api.class);
        CnBetaParam param = new CnBetaParam();
        param.setMethod(Constants.CNBETA_TYPE_NEWS_LISTS);
        param.setTimestamp(System.currentTimeMillis());
        param.setEnd_sid(articleId);
        Call<NewsResponse> call = api.getNewsMore(param.getParam());
        call.enqueue(new retrofit2.Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, final Response<NewsResponse> response) {
                KLog.json(JsonUtils.toJson(response.body()));
                if (mView == null) {
                    return;
                }
                mView.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                KLog.json(JsonUtils.toJson(call.request()));
                if (mView == null) {
                    return;
                }
                mView.onFailure(call, t);
            }
        });
        return call;
    }
}