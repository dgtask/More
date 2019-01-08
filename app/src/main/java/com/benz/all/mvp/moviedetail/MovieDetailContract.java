package com.benz.all.mvp.moviedetail;

import com.benz.all.entity.movie.MovieDetailResponse;
import com.benz.all.mvp.BasePresenter;
import com.benz.all.mvp.BaseView;

import retrofit2.Call;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MovieDetailContract {
    interface View extends BaseView {
        void onSuccess(MovieDetailResponse response);

        void onFailure(Call<MovieDetailResponse> call, Throwable t);
    }

    interface Presenter extends BasePresenter<View> {
        Call<MovieDetailResponse> getMovieDetail(Long videoId, boolean isAlbum);
    }
}
