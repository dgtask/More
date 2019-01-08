package com.benz.all.mvp.movietopicitem;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.benz.all.R;
import com.benz.all.api.Api;
import com.benz.all.callback.OnLoadMoreListener;
import com.benz.all.entity.movie.MovieTopicItemResponse;
import com.benz.all.entity.movie.tiantan.Subjects;
import com.benz.all.mvp.BasePresenterImpl;
import com.benz.all.net.RetrofitMovie;
import com.benz.all.ui.views.RecyclerViewHelper;
import com.benz.all.utils.JsonUtils;
import com.benz.all.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.socks.library.KLog;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MovieTopicItemPresenter extends BasePresenterImpl<MovieTopicItemContract.View> implements MovieTopicItemContract.Presenter {

    @Override
    public BaseQuickAdapter<Subjects, BaseViewHolder> getCommonAdapter(List<Subjects> list) {
        return new BaseQuickAdapter<Subjects, BaseViewHolder>(R.layout.fragment_movie_index_item, list) {
            @Override
            protected void convert(BaseViewHolder holder, Subjects subjects) {
                ImageView ivThumb = holder.getView(R.id.ivThumb);
                holder.setText(R.id.tvName, Utils.noNull(subjects.getName()));
                holder.setText(R.id.tvScore, "" + subjects.getScore());
                if (subjects.getImg() != null) {
                    Picasso.with(mContext)
                            .load(subjects.getImg())
                            .placeholder(R.mipmap.ic_movie_placeholder)
                            .error(R.mipmap.ic_movie_placeholder)
                            .config(Bitmap.Config.RGB_565)
                            .tag(mContext)
                            .into(ivThumb);
                } else {
                    ivThumb.setImageResource(R.mipmap.ic_news_placeholder);
                }
            }
        };
    }

    @Override
    public RecyclerViewHelper getRecyclerViewHelper(Activity mContext, BaseQuickAdapter<Subjects, BaseViewHolder> mCommonAdapter) {
        return RecyclerViewHelper.init(mContext)
                .setSwipeToLoadLayout((SwipeRefreshLayout) mContext.findViewById(R.id.swipeRefreshLayout))
                .setLayoutManager(new GridLayoutManager(mContext, 3))
                .setRecyclerView((RecyclerView) mContext.findViewById(R.id.recyclerView))
                .setCommonAdapter(mCommonAdapter)
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
                        mView.onItemClick(position, (ImageView) view.findViewById(R.id.ivThumb));
                    }
                }).setComplete();
    }

    @Override
    public Call<MovieTopicItemResponse> getMovieTopicItems(int page, String id) {
        Api api = RetrofitMovie.getInstance().create(Api.class);
        Call<MovieTopicItemResponse> call = api.getMovieTopicItems(page, id);
        call.enqueue(new Callback<MovieTopicItemResponse>() {
            @Override
            public void onResponse(Call<MovieTopicItemResponse> call, Response<MovieTopicItemResponse> response) {
                KLog.json(JsonUtils.toJson(response.body().getBody()));
                if (mView == null) {
                    return;
                }
                mView.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<MovieTopicItemResponse> call, Throwable t) {
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
