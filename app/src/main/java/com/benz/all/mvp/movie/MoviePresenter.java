package com.benz.all.mvp.movie;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.benz.all.R;
import com.benz.all.api.Api;
import com.benz.all.app.Constants;
import com.benz.all.entity.movie.MovieTopResponse;
import com.benz.all.entity.movie.tiantan.Subjects;
import com.benz.all.entity.movie.tiantan.Top;
import com.benz.all.mvp.BasePresenterImpl;
import com.benz.all.net.RetrofitMovie;
import com.benz.all.ui.adapter.MovieSectionAdapter;
import com.benz.all.ui.views.RecyclerViewHelper;
import com.benz.all.utils.JsonUtils;
import com.benz.all.utils.TasksUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MVPPlugin
 *  Created by xubenliang on 2017/6/4.
 */

public class MoviePresenter extends BasePresenterImpl<MovieContract.View> implements MovieContract.Presenter{

    @Override
    public void getCache() {
        List<Subjects> list = TasksUtils.getDataFromSp(Constants.MOVIE_INDEX_CACHE_KEY);
        mView.onCache(list);
    }

    @Override
    public MovieSectionAdapter getCommonAdapter(List<Subjects> list) {
        return new MovieSectionAdapter(R.layout.fragment_movie_index_item, R.layout.fragment_movie_index_head, list);
    }

    @Override
    public RecyclerViewHelper getRecyclerViewHelper(final Context mContext, View mRootView, MovieSectionAdapter mCommonAdapter) {
        return RecyclerViewHelper.init(mContext)
                .setSwipeToLoadLayout((SwipeRefreshLayout) mRootView.findViewById(R.id.swipeRefreshLayout))
                .setLayoutManager(new GridLayoutManager(mContext, 3))
                .setRecyclerView((RecyclerView) mRootView.findViewById(R.id.recyclerView))
                .setCommonAdapter(mCommonAdapter)
                .setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mView.onRefresh();
                    }
                }).setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        mView.onItemClick(position, (ImageView) view.findViewById(R.id.ivThumb));
                    }
                }).setComplete();
    }

    @Override
    public Call<MovieTopResponse> getMovieTopList() {
        Api api = RetrofitMovie.getInstance().create(Api.class);
        Call<MovieTopResponse> call = api.getMovieTopList();
        call.enqueue(new Callback<MovieTopResponse>() {
            @Override
            public void onResponse(Call<MovieTopResponse> call, Response<MovieTopResponse> response) {
                KLog.json(JsonUtils.toJson(response.body()));
                if(mView == null || response == null || response.body() == null){
                    return;
                }
                List<Subjects> list = flattenData(response.body().getBody());
                TasksUtils.saveDataToSpAsyn(Constants.MOVIE_INDEX_CACHE_KEY, list);
                mView.onSuccess(list);
            }

            @Override
            public void onFailure(Call<MovieTopResponse> call, Throwable t) {
                KLog.json(JsonUtils.toJson(call.request()));
                if(mView == null){
                    return;
                }
                mView.onFailure(call, t);
            }
        });
        return call;
    }

    /**
     * 拍扁数据
     * @param tops
     * @return
     */
    private List<Subjects> flattenData(List<Top> tops) {
        final List<Subjects> items = new ArrayList<>();
        if(tops != null) {
            for (Top top : tops) {
                Subjects subjects = new Subjects(true, top.getName());
                subjects.setParentId(top.getId());
                subjects.setParentName(top.getName());
                items.add(subjects);
                items.addAll(top.getSubjects());
            }
        }
        return items;
    }

    /**
     * 获取动态IP地址
     */
    /*private void getBaseMovieUrl(){
        Api api = RetrofitMovie.getInstance().create(Api.class);
        Call<ResponseBody> call = api.getBaseMovieUrl();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                KLog.json(JsonUtils.toJson(response.body()));
                if (response.isSuccessful()) {
                    KLog.d("server contacted and has file");
                    Utils.writeResponseBodyToDisk(response.body(), "/sdcard/movie.txt");
                } else {
                    KLog.d("server contact failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                KLog.json(JsonUtils.toJson(call.request()));
            }
        });
    }*/
}
