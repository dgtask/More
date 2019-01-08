package com.benz.all.mvp.news;


import com.benz.all.R;
import com.benz.all.app.AppContext;
import com.benz.all.entity.news.NewsItem;
import com.benz.all.entity.news.NewsResponse;
import com.benz.all.mvp.MVPBaseFragment;
import com.benz.all.mvp.newscontent.NewsContentActivity;
import com.benz.all.ui.views.RecyclerViewHelper;
import com.benz.all.utils.JsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * MVPPlugin
 * Created by xubenliang on 2017/6/4.
 */
public class NewsFragment extends MVPBaseFragment<NewsContract.View, NewsPresenter> implements NewsContract.View {

    /**
     * 列表帮助类
     */
    private RecyclerViewHelper mRecyclerViewHelper;
    /**
     * 适配器
     */
    private BaseQuickAdapter<NewsItem, BaseViewHolder> mCommonAdapter;

    private int pageIndex = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initViewsAndEvents() {

        mCommonAdapter = mPresenter.getCommonAdapter(new ArrayList<NewsItem>());

        mRecyclerViewHelper = mPresenter.getRecyclerViewHelper(mContext, mRootView, mCommonAdapter);

        mPresenter.getCache();
    }

    @Override
    public void onCache(List<NewsItem> result) {
        KLog.json("onCache", JsonUtils.toJson(result));
        if (result != null) {
            mRecyclerViewHelper.onLoadData(pageIndex, result);
        }
        if (!AppContext.recreate) {
            mRecyclerViewHelper.autoRefresh();
        }
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        call = mPresenter.getNews();
    }

    @Override
    public void onLoadMore() {
        ++pageIndex;
        int size = mCommonAdapter.getData().size();
        call = mPresenter.getNewsMore(mCommonAdapter.getItem(size - 1).getSid());
    }

    @Override
    public void onItemClick(int position) {
        NewsContentActivity.start(mContext, mCommonAdapter.getItem(position));
    }

    @Override
    public void onSuccess(NewsResponse response) {
        mRecyclerViewHelper.onLoadComplete();
        mRecyclerViewHelper.onLoadData("暂无新闻", pageIndex, response.getResult());
    }

    @Override
    public void onFailure(Call<NewsResponse> call, Throwable t) {
        mRecyclerViewHelper.onLoadComplete();
        mRecyclerViewHelper.showError("网络异常");
    }
}