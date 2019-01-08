package com.benz.all.mvp.video;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.benz.all.R;
import com.benz.all.api.Api;
import com.benz.all.app.AppContext;
import com.benz.all.app.Constants;
import com.benz.all.callback.OnLoadMoreListener;
import com.benz.all.entity.video.Video;
import com.benz.all.entity.video.VideoResponse;
import com.benz.all.mvp.BasePresenterImpl;
import com.benz.all.net.RetrofitVideo;
import com.benz.all.ui.views.RecyclerViewHelper;
import com.benz.all.ui.views.ResizableImageView;
import com.benz.all.utils.JsonUtils;
import com.benz.all.utils.TasksUtils;
import com.benz.all.utils.Utils;
import com.benz.all.utils.ViewUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.socks.library.KLog;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.GrayscaleTransformation;
import retrofit2.Call;
import retrofit2.Response;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class VideoPresenter extends BasePresenterImpl<VideoContract.View> implements VideoContract.Presenter {

    @Override
    public void getCache() {
        VideoResponse result = TasksUtils.getDataFromSp(Constants.VIDEO_RESPONSE_CACHE_KEY);
        mView.onCache(result != null ? result.getData().getList() : null);
    }

    @Override
    public BaseQuickAdapter<Video, BaseViewHolder> getCommonAdapter(List<Video> list) {
        return new BaseQuickAdapter<Video, BaseViewHolder>(R.layout.fragment_video_item, list) {
            @Override
            protected void convert(BaseViewHolder holder, Video video) {

                holder.setText(R.id.tvTitle, Utils.noNull(video.getSubject()));
                holder.setText(R.id.tvIntro, Utils.noNull(video.getIntro()));
                ImageView ivThumb = holder.getView(R.id.ivThumb);
                Picasso.with(mContext)
                        .load(Constants.BASE_URL_VIDEO + video.getImage())
                        .placeholder(R.color.gray_white)
                        .error(R.color.gray_white)
                        .config(Bitmap.Config.RGB_565)
                        .tag(mContext)
                        .into(ivThumb);
            }
        };
    }

    @Override
    public RecyclerViewHelper getRecyclerViewHelper(Context mContext, View mRootView, BaseQuickAdapter<Video, BaseViewHolder> mCommonAdapter) {
        return RecyclerViewHelper.init(mContext)
                .setSwipeToLoadLayout((SwipeRefreshLayout) mRootView.findViewById(R.id.swipeRefreshLayout))
                .setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false))
                .setRecyclerView((RecyclerView) mRootView.findViewById(R.id.recyclerView))
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
                        mView.onItemClick(position);
                    }
                }).setComplete();
    }

    @Override
    public Call<VideoResponse> getVideos(String mod, final String rtype, final int page) {
        Api api = RetrofitVideo.getInstance().create(Api.class);
        Call<VideoResponse> call = api.getVideos(mod, rtype, page);
        call.enqueue(new retrofit2.Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, final Response<VideoResponse> response) {
                KLog.json(JsonUtils.toJson(response.body()));
                if (rtype == null && page == 1) {
                    TasksUtils.saveDataToSpAsyn(Constants.VIDEO_RESPONSE_CACHE_KEY, response.body());
                }
                if (mView == null) {
                    return;
                }
                mView.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
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
