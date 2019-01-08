package com.benz.all.mvp.moviedetail;

import com.benz.all.api.Api;
import com.benz.all.app.AppContext;
import com.benz.all.app.Constants;
import com.benz.all.entity.movie.MovieDetailResponse;
import com.benz.all.mvp.BasePresenterImpl;
import com.benz.all.net.RetrofitMovie;
import com.benz.all.utils.JsonUtils;
import com.orhanobut.hawk.Hawk;
import com.socks.library.KLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MovieDetailPresenter extends BasePresenterImpl<MovieDetailContract.View> implements MovieDetailContract.Presenter {

    @Override
    public Call<MovieDetailResponse> getMovieDetail(Long videoId, boolean isAlbum) {
        Api api = RetrofitMovie.getInstance().create(Api.class);
        Call<MovieDetailResponse> call = api.getMovieDetail(videoId, isAlbum);
        call.enqueue(new Callback<MovieDetailResponse>() {
            @Override
            public void onResponse(Call<MovieDetailResponse> call, Response<MovieDetailResponse> response) {
                KLog.json(JsonUtils.toJson(response.body()));
                if (mView == null) {
                    return;
                }
                mView.onSuccess(response.body());

            }

            @Override
            public void onFailure(Call<MovieDetailResponse> call, Throwable t) {
                KLog.e("onFailure = " + JsonUtils.toJson(call.request()));
                if (mView == null) {
                    return;
                }
                mView.onFailure(call, t);
            }
        });
        return call;
    }
}
