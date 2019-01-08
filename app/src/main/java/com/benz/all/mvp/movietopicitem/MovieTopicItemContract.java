package com.benz.all.mvp.movietopicitem;

import android.app.Activity;
import android.widget.ImageView;

import com.benz.all.entity.movie.MovieTopicItemResponse;
import com.benz.all.entity.movie.tiantan.Subjects;
import com.benz.all.mvp.BasePresenter;
import com.benz.all.mvp.BaseView;
import com.benz.all.ui.views.RecyclerViewHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import retrofit2.Call;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MovieTopicItemContract {
    interface View extends BaseView {

        void onRefresh();

        void onLoadMore();

        void onItemClick(int position, ImageView imageView);

        void onSuccess(MovieTopicItemResponse response);

        void onFailure(Call<MovieTopicItemResponse> call, Throwable t);
    }

    interface  Presenter extends BasePresenter<View> {

        BaseQuickAdapter<Subjects, BaseViewHolder> getCommonAdapter(List<Subjects> list);

        RecyclerViewHelper getRecyclerViewHelper(Activity mContext, BaseQuickAdapter<Subjects, BaseViewHolder> mCommonAdapter);

        Call<MovieTopicItemResponse> getMovieTopicItems(int page, String id);
    }
}
