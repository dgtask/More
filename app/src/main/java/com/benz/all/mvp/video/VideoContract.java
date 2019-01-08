package com.benz.all.mvp.video;

import android.content.Context;

import com.benz.all.entity.news.NewsItem;
import com.benz.all.entity.news.NewsResponse;
import com.benz.all.entity.video.Video;
import com.benz.all.entity.video.VideoResponse;
import com.benz.all.mvp.BasePresenter;
import com.benz.all.mvp.BaseView;
import com.benz.all.mvp.news.NewsContract;
import com.benz.all.ui.views.RecyclerViewHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Query;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class VideoContract {

    interface View extends BaseView {

        void onCache(List<Video> result);

        void onRefresh();

        void onLoadMore();

        void onItemClick(int position);

        void onSuccess(VideoResponse response);

        void onFailure(Call<VideoResponse> call, Throwable t);
    }

    interface Presenter extends BasePresenter<VideoContract.View> {

        void getCache();

        BaseQuickAdapter<Video, BaseViewHolder> getCommonAdapter(List<Video> list);

        RecyclerViewHelper getRecyclerViewHelper(Context mContext, android.view.View mRootView, BaseQuickAdapter<Video, BaseViewHolder> mCommonAdapter);

        Call<VideoResponse> getVideos(String mod, String rtype, int page);
    }
}
