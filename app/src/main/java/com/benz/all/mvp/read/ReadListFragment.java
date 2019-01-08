package com.benz.all.mvp.read;


import com.benz.all.R;
import com.benz.all.app.AppContext;
import com.benz.all.entity.read.Article;
import com.benz.all.entity.read.ReadResponse;
import com.benz.all.mvp.MVPBaseFragment;
import com.benz.all.mvp.readcontent.ReadContentActivity;
import com.benz.all.ui.views.RecyclerViewHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * MVPPlugin
 */
public class ReadListFragment extends MVPBaseFragment<ReadListContract.View, ReadListPresenter> implements ReadListContract.View {

    /**
     * 列表帮助类
     */
    RecyclerViewHelper mRecyclerViewHelper;
    /**
     * 适配器
     */
    BaseQuickAdapter<Article, BaseViewHolder> mCommonAdapter;

    int pageIndex = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_read_list;
    }

    @Override
    protected void initViewsAndEvents() {

        mCommonAdapter = mPresenter.getCommonAdapter(new ArrayList<Article>());
        mRecyclerViewHelper = mPresenter.getRecyclerViewHelper(mContext, mRootView, mCommonAdapter);

        mPresenter.getCache();
    }

    @Override
    public void onCache(List<Article> result) {
        if (result != null) {
            mRecyclerViewHelper.onLoadData(1, result);
        }
        if (!AppContext.recreate) {
            mRecyclerViewHelper.autoRefresh();
        }
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        call = mPresenter.getArticleList(pageIndex);
    }

    @Override
    public void onLoadMore() {
        ++pageIndex;
        int size = mCommonAdapter.getData().size();
        Article last = mCommonAdapter.getItem(size - 1);
        call = mPresenter.getArticleMoreList(last.getCreate_time(), last.getUpdate_time());
    }

    @Override
    public void onItemClick(int position) {
        Article article = mCommonAdapter.getItem(position);
        ReadContentActivity.start(getContext(), article);
    }

    @Override
    public void onSuccess(ReadResponse response) {
        mRecyclerViewHelper.onLoadComplete();
        mRecyclerViewHelper.onLoadData("暂无文章", pageIndex, response.getBody().getArticle());
    }

    @Override
    public void onFailure(Call<ReadResponse> call, Throwable t) {
        mRecyclerViewHelper.onLoadComplete();
        mRecyclerViewHelper.showError("网络异常");
    }
}