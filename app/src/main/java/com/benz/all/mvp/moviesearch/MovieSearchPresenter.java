package com.benz.all.mvp.moviesearch;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.benz.all.R;
import com.benz.all.api.Api;
import com.benz.all.entity.movie.MovieSearchResponse;
import com.benz.all.entity.movie.tiantan.SearchItem;
import com.benz.all.mvp.BasePresenterImpl;
import com.benz.all.net.RetrofitMovie;
import com.benz.all.ui.views.RecyclerViewHelper;
import com.benz.all.utils.JsonUtils;
import com.benz.all.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.socks.library.KLog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MovieSearchPresenter extends BasePresenterImpl<MovieSearchContract.View> implements MovieSearchContract.Presenter {

    @Override
    public BaseQuickAdapter<SearchItem, BaseViewHolder> getCommonAdapter(List<SearchItem> list) {
        return new BaseQuickAdapter<SearchItem, BaseViewHolder>(R.layout.fragment_movie_index_item, list) {
            @Override
            protected void convert(BaseViewHolder holder, SearchItem searchItem) {
                ImageView ivThumb = holder.getView(R.id.ivThumb);
                holder.setText(R.id.tvName, Utils.noNull(searchItem.getName()));
                holder.setText(R.id.tvScore, "" + searchItem.getScore());
                if (searchItem.getImg() != null) {
                    Picasso.with(mContext)
                            .load(searchItem.getImg())
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
    public RecyclerViewHelper getRecyclerViewHelper(Activity mContext, BaseQuickAdapter<SearchItem, BaseViewHolder> mCommonAdapter) {
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
                })
                .setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        mView.onItemClick(position, (ImageView) view.findViewById(R.id.ivThumb));
                    }
                }).setComplete();
    }

    @Override
    public Call<MovieSearchResponse> getMovieSearch(String keywords) {
        Api api = RetrofitMovie.getInstance().create(Api.class);
        Call<MovieSearchResponse> call = api.getSearchMovie(keywords);
        call.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                KLog.json(JsonUtils.toJson(response.body().getBody()));
                if (mView == null) {
                    return;
                }
                mView.onSuccess(filterMovie(response.body().getBody()));
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
                KLog.e(JsonUtils.toJson(call.request()));
                mView.onFailure(call, t);
            }
        });
        return call;
    }

    /**
     * 只显示电影结果
     *
     * @param searchItemList
     * @return
     */
    private List<SearchItem> filterMovie(List<SearchItem> searchItemList) {
        List<SearchItem> itemList = new ArrayList<>();
        if (searchItemList == null) {
            return itemList;
        }
        for (SearchItem searchItem : searchItemList) {
            if (searchItem.getMovieTypeName().equals("电影")) {
                itemList.add(searchItem);
            }
        }
        return itemList;
    }
}
