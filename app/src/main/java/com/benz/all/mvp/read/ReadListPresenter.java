package com.benz.all.mvp.read;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.benz.all.R;
import com.benz.all.api.Api;
import com.benz.all.app.Constants;
import com.benz.all.callback.OnLoadMoreListener;
import com.benz.all.entity.read.Article;
import com.benz.all.entity.read.ReadResponse;
import com.benz.all.mvp.BasePresenterImpl;
import com.benz.all.net.RetrofitRead;
import com.benz.all.ui.views.RecyclerViewHelper;
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
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ReadListPresenter extends BasePresenterImpl<ReadListContract.View> implements ReadListContract.Presenter {

    @Override
    public void getCache() {
        ReadResponse result = TasksUtils.getDataFromSp(Constants.READ_RESPONSE_CACHE_KEY);
        mView.onCache(result != null ? result.getBody().getArticle() : null);
    }

    @Override
    public BaseQuickAdapter<Article, BaseViewHolder> getCommonAdapter(List<Article> list) {
        return new BaseQuickAdapter<Article, BaseViewHolder>(R.layout.fragment_read_item, list) {
            @Override
            protected void convert(BaseViewHolder holder, Article article) {
                holder.setText(R.id.tvTitle, Utils.noNull(article.getTitle()));
                holder.setText(R.id.tvBrief, Utils.noNull(article.getBrief()));
                holder.setText(R.id.tvAuthor, Utils.noNull(article.getAuthor()));
                holder.setText(R.id.tvTime, Utils.formatDate(article.getCreate_time()));
                holder.setText(R.id.tvReadNum, "阅读量：" + Utils.noNull(article.getRead_num()));

                ImageView ivThumb = holder.getView(R.id.ivThumb);
                String imageUrl = article.getHeadpic();
                if (!Utils.isEmpty(imageUrl)) {
                    Picasso.with(mContext)
                            .load(imageUrl)
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
    public RecyclerViewHelper getRecyclerViewHelper(final Context mContext, View mRootView, BaseQuickAdapter<Article, BaseViewHolder> mCommonAdapter) {
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
                }).setComplete();
    }

    @Override
    public Call<ReadResponse> getArticleList(int page) {
        return getArticles(page, null, null);
    }

    @Override
    public Call<ReadResponse> getArticleMoreList(String create_time, String update_time) {
        return getArticles(-1, create_time, update_time);
    }

    /**
     * 获取阅读列表
     *
     * @param page
     * @param create_time
     * @param update_time
     * @return
     */
    private Call<ReadResponse> getArticles(final int page, String create_time, String update_time) {
        Api api = RetrofitRead.getInstance().create(Api.class);
        Call<ReadResponse> call;
        if (page == 1)
            call = api.getReadList(Constants.PAGE_SIZE, page - 1);// 请求默认从第0页开始
        else
            call = api.getReadMoreList(Constants.PAGE_SIZE, create_time, update_time);
        call.enqueue(new Callback<ReadResponse>() {
            @Override
            public void onResponse(Call<ReadResponse> call, Response<ReadResponse> response) {
                KLog.json(JsonUtils.toJson(response.body()));
                // 只缓存第一页数据
                if (page == 1) {
                    TasksUtils.saveDataToSpAsyn(Constants.READ_RESPONSE_CACHE_KEY, response.body());
                }
                if (mView == null) {
                    return;
                }
                mView.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ReadResponse> call, Throwable t) {
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
