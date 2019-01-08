package com.benz.all.mvp.video;


import com.benz.all.R;
import com.benz.all.app.AppContext;
import com.benz.all.entity.video.Video;
import com.benz.all.entity.video.VideoResponse;
import com.benz.all.mvp.MVPBaseFragment;
import com.benz.all.ui.activity.PlayVideoActivity;
import com.benz.all.ui.views.RecyclerViewHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class VideoFragment extends MVPBaseFragment<VideoContract.View, VideoPresenter> implements VideoContract.View {

//    http://morguo.com/forum.php?mod=threadvideo&androidflag=1&page=1
//    http://morguo.com/forum.php?mod=threadvideoranking&rtype=week&androidflag=1&page=1
//    http://morguo.com/forum.php?mod=threadvideoranking&rtype=month&androidflag=1&page=1
//    http://morguo.com/forum.php?mod=threadvideoranking&rtype=all&androidflag=1&page=1

    public static final String MOD_THREAD_VIDEO = "threadvideo";
    public static final String MOD_THREAD_VIDEO_RANKING = "threadvideoranking";

    public static final String RTYPE_WEEK = "week";
    public static final String RTYPE_MONTH = "month";
    public static final String RTYPE_ALL = "all";
    public static final int PAGE_SIZE = 10;

    /**
     * 列表帮助类
     */
    private RecyclerViewHelper mRecyclerViewHelper;
    /**
     * 适配器
     */
    private BaseQuickAdapter<Video, BaseViewHolder> mCommonAdapter;

    /**
     * 请求参数
     */
    private String mod = MOD_THREAD_VIDEO;
    private String rtype;

    private int pageIndex = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initViewsAndEvents() {

        mCommonAdapter = mPresenter.getCommonAdapter(new ArrayList<Video>());

        mRecyclerViewHelper = mPresenter.getRecyclerViewHelper(mContext, mRootView, mCommonAdapter);

        mPresenter.getCache();
    }

    @Override
    public void onCache(List<Video> result) {
        if (result != null) {
            mRecyclerViewHelper.onLoadData("暂无视频", pageIndex, PAGE_SIZE, result);
        }
        if (!AppContext.recreate) {
            mRecyclerViewHelper.autoRefresh();
        }
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        call = mPresenter.getVideos(mod, rtype, pageIndex);
    }

    @Override
    public void onLoadMore() {
        ++pageIndex;
        call = mPresenter.getVideos(mod, rtype, pageIndex);
    }

    @Override
    public void onItemClick(int position) {
        Video video = mCommonAdapter.getItem(position);
        PlayVideoActivity.start(getActivity(), video.getSubject(), video.getVideourl());
    }

    @Override
    public void onSuccess(VideoResponse response) {
        mRecyclerViewHelper.onLoadComplete();
        mRecyclerViewHelper.onLoadData("暂无视频", pageIndex, PAGE_SIZE, response.getData().getList());
    }

    @Override
    public void onFailure(Call<VideoResponse> call, Throwable t) {
        mRecyclerViewHelper.onLoadComplete();
        mRecyclerViewHelper.showError("网络异常");
    }
}
