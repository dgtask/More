package com.benz.all.ui.views;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.benz.all.R;
import com.benz.all.app.Constants;
import com.benz.all.callback.OnLoadMoreListener;
import com.benz.all.ui.views.divider.Divider;
import com.benz.all.ui.views.divider.DividerItemDecoration;
import com.benz.all.utils.NetworkUtils;
import com.benz.all.utils.Utils;
import com.benz.all.utils.ViewUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * RecyclerView帮助类
 * Created by xubenliang on 2017/4/25.
 */
public class RecyclerViewHelper {
    /**
     * 初次主动刷新延迟时间
     */
    private final int AUTO_REFRESH_DELAY_MILLIS = 400;
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 下拉刷新，上拉加载
     */
    private SwipeRefreshLayout mSwipeRefreshLayout;
    /**
     * ListView, GridView列表
     */
    private RecyclerView mRecyclerView;
    /**
     * 适配器
     */
    private BaseQuickAdapter mCommonAdapter;
    /**
     * 布局方式
     */
    private RecyclerView.LayoutManager mLayoutManager;

    /**
     * 下拉刷新、上拉加载回调
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener;
    /**
     * 加载更多回掉
     */
    private OnLoadMoreListener mOnLoadMoreListener;
    /**
     * Item点击事件
     */
    private BaseQuickAdapter.OnItemClickListener mOnItemClickListener;
    /**
     * Item长按点击事件
     */
    private BaseQuickAdapter.OnItemLongClickListener mOnItemLongClickListener;
    /**
     * Item子控件点击事件
     */
    private BaseQuickAdapter.OnItemChildClickListener mOnItemChildClickListener;
    /**
     * Item子控件长安点击事件
     */
    private BaseQuickAdapter.OnItemChildLongClickListener mOnItemChildLongClickListener;
    /**
     * 滚动监听
     */
    private RecyclerView.OnScrollListener mOnScrollListener;
    /**
     * 是否允许下拉刷新
     */
    private boolean refreshEnabled;
    /**
     * 是否允许加载更多
     */
    private boolean loadMoreEnable;
    /**
     * 是否需要分割线
     */
    private boolean enableDivider;
    /**
     * 是否显示空布局
     */
    private boolean showEmptyView = true;
    /**
     * 空数据，错误提示布局
     */
    private View errorView;

    public RecyclerViewHelper(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 快速初始化
     *
     * @param mContext
     * @return
     */
    public static RecyclerViewHelper init(Context mContext) {
        return new RecyclerViewHelper(mContext);
    }

    /**
     * 设置下拉控件
     *
     * @param mSwipeToLoadLayout
     */
    public RecyclerViewHelper setSwipeToLoadLayout(SwipeRefreshLayout mSwipeToLoadLayout) {
        this.mSwipeRefreshLayout = mSwipeToLoadLayout;
        return this;
    }

    /**
     * 设置列表
     *
     * @param recyclerView
     */
    public RecyclerViewHelper setRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        return this;
    }

    /**
     * 获取列表
     *
     * @return
     */
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    /**
     * 设置Item点击事件
     * 注意：如果RecyclerView添加了Header，需要在Item设置点击事件才不会报数组越界错误。
     *
     * @param mOnItemClickListener
     */
    public RecyclerViewHelper setOnItemClickListener(BaseQuickAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
        return this;
    }

    /**
     * 设置Item长按点击事件
     *
     * @param mOnItemLongClickListener
     */
    public RecyclerViewHelper setOnItemLongClickListener(BaseQuickAdapter.OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
        return this;
    }

    /**
     * 设置Item子控件点击事件
     *
     * @param mOnItemChildClickListener
     */
    public RecyclerViewHelper setOnItemChildClickListener(BaseQuickAdapter.OnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
        return this;
    }

    /**
     * 设置Item子控件长按点击事件
     *
     * @param mOnItemChildLongClickListener
     */
    public RecyclerViewHelper setOnItemChildLongClickListener(BaseQuickAdapter.OnItemChildLongClickListener mOnItemChildLongClickListener) {
        this.mOnItemChildLongClickListener = mOnItemChildLongClickListener;
        return this;
    }

    /**
     * 设置Item长按点击事件
     *
     * @param mOnScrollListener
     */
    public RecyclerViewHelper setOnScrollListener(RecyclerView.OnScrollListener mOnScrollListener) {
        this.mOnScrollListener = mOnScrollListener;
        return this;
    }

    /**
     * 添加分割线
     */
    public RecyclerViewHelper setEnableDivider(boolean enableDivider) {
        this.enableDivider = enableDivider;
        return this;
    }

    /**
     * 设置布局方式
     * new LinearLayoutManager(mContext);
     * new GridLayoutManager(mContext, 2);
     * new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
     *
     * @param layoutManager
     */
    public RecyclerViewHelper setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        return this;
    }

    /**
     * 获取布局方式
     *
     * @return
     */
    public RecyclerView.LayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    /**
     * 设置适配器
     *
     * @param mCommonAdapter
     */
    public RecyclerViewHelper setCommonAdapter(BaseQuickAdapter mCommonAdapter) {
        this.mCommonAdapter = mCommonAdapter;
        return this;
    }

    /**
     * 添加头部
     *
     * @param headerView
     */
    public RecyclerViewHelper setHeaderView(View headerView) {
        mCommonAdapter.setHeaderView(headerView);
        return this;
    }

    /**
     * 添加底部
     *
     * @param footerView
     */
    public RecyclerViewHelper setFooterView(View footerView) {
        mCommonAdapter.setFooterView(footerView);
        return this;
    }

    /**
     * 设置下拉刷新监听
     * 设置监听，则支持下拉刷新
     *
     * @param mOnRefreshListener
     */
    public RecyclerViewHelper setOnRefreshListener(final SwipeRefreshLayout.OnRefreshListener mOnRefreshListener) {
        refreshEnabled = mOnRefreshListener != null;
        this.mOnRefreshListener = mOnRefreshListener;
        return this;
    }

    /**
     * 设置加载更多监听
     * 设置监听，则支持加载更多
     *
     * @param mOnLoadMoreListener
     */
    public RecyclerViewHelper setOnLoadMoreListener(final OnLoadMoreListener mOnLoadMoreListener) {
        loadMoreEnable = mOnLoadMoreListener != null;
        this.mOnLoadMoreListener = mOnLoadMoreListener;
        return this;
    }

    /**
     * 是否显示空布局
     *
     * @param showEmptyView
     */
    public RecyclerViewHelper setShowEmptyView(boolean showEmptyView) {
        this.showEmptyView = showEmptyView;
        return this;
    }

    /**
     * 配置完成，最后一定要调用此方法
     */
    public RecyclerViewHelper setComplete() {
        if (mSwipeRefreshLayout == null || mRecyclerView == null || mCommonAdapter == null || mLayoutManager == null) {
            throw new NullPointerException("请先配置RecyclerViewHelper，最后调用setComplete方法");
        }
        setCommonAdapter();
        setRecyclerView();
        setErrorView();
        return this;
    }

    /**
     * 设置适配器
     */
    private void setCommonAdapter() {
        // 开启动画
//        mCommonAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        /*mCommonAdapter.isFirstOnly(false);
        mCommonAdapter.setNotDoAnimationCount(12);
        mCommonAdapter.openLoadAnimation(new BaseAnimation() {
            @Override
            public Animator[] getAnimators(View view) {
                return new Animator[]{
                        ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1),
                        ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1)
                };
            }
        });*/
        // 自动加载更多
        mCommonAdapter.setEnableLoadMore(loadMoreEnable);
        if (loadMoreEnable) {
            mCommonAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    mOnLoadMoreListener.onLoadMore();
                }
            }, mRecyclerView);
        }
        // 设置点击事件
        if (mOnItemClickListener != null) {
            mCommonAdapter.setOnItemClickListener(mOnItemClickListener);
        }
        if (mOnItemLongClickListener != null) {
            mCommonAdapter.setOnItemLongClickListener(mOnItemLongClickListener);
        }
        if (mOnItemChildClickListener != null) {
            mCommonAdapter.setOnItemChildClickListener(mOnItemChildClickListener);
        }
        if (mOnItemChildLongClickListener != null) {
            mCommonAdapter.setOnItemChildLongClickListener(mOnItemChildLongClickListener);
        }
    }

    /**
     * 设置列表
     */
    private void setRecyclerView() {
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mCommonAdapter);
        // 是否添加分割线
        if (enableDivider) {
            final Divider divider = new Divider.Builder().size(1).color(mContext.getResources().getColor(R.color.divider)).build();
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration();
            dividerItemDecoration.setDividerLookup(new DividerItemDecoration.DividerLookup() {
                @Override
                public Divider getVerticalDivider(int position) {
                    return divider;
                }

                @Override
                public Divider getHorizontalDivider(int position) {
                    return divider;
                }
            });
            mRecyclerView.addItemDecoration(dividerItemDecoration);
        }
        // 滚动监听
        if (mOnScrollListener != null) {
            mRecyclerView.addOnScrollListener(mOnScrollListener);
        }
        // 下拉刷新
        mSwipeRefreshLayout.setEnabled(refreshEnabled);
        if (refreshEnabled) {
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            mOnRefreshListener.onRefresh();
                        }
                    });
                }
            });
        }
    }

    /**
     * 设置错误状态布局
     */
    private void setErrorView() {
        errorView = ViewUtils.inflateView(mContext, R.layout.recycleview_error_view);
        TextView retry = (TextView) errorView.findViewById(R.id.retry_button);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoRefresh();
            }
        });
    }

    /**
     * 初次主动刷新
     */
    public void autoRefresh() {
        autoRefresh(AUTO_REFRESH_DELAY_MILLIS);
    }

    /**
     * 初次主动刷新
     *
     * @param delayMillis
     */
    public void autoRefresh(int delayMillis) {
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mOnRefreshListener.onRefresh();
            }
        }, delayMillis);
    }


    /**
     * 加载数据
     *
     * @param pageIndex 页码
     * @param list      列表数据
     */
    public void onLoadData(int pageIndex, List list) {
        onLoadData("暂无数据", 0, pageIndex, Constants.PAGE_SIZE, list);
    }

    /**
     * 加载数据
     *
     * @param emptyMsg  空数据提示
     * @param pageIndex 页码
     * @param list      列表数据
     */
    public void onLoadData(String emptyMsg, int pageIndex, List list) {
        onLoadData(emptyMsg, 0, pageIndex, Constants.PAGE_SIZE, list);
    }

    /**
     * 加载数据
     *
     * @param emptyMsg  空数据提示
     * @param pageIndex 页码
     * @param pageSize  每一页数量
     * @param list      列表数据
     */
    public void onLoadData(String emptyMsg, int pageIndex, int pageSize, List list) {
        onLoadData(emptyMsg, 0, pageIndex, pageSize, list);
    }

    /**
     * 加载数据
     *
     * @param emptyMsg    空布局文字
     * @param emptyImgRes 空布局图片
     * @param pageIndex   页码
     * @param pageSize    每一页数量
     * @param list        列表数据
     */
    public void onLoadData(String emptyMsg, int emptyImgRes, int pageIndex, int pageSize, List list) {
        if (pageIndex <= 1) {// 下拉刷新
            // 空数据显示空布局
            if ((list == null || list.isEmpty())) {
                mCommonAdapter.getData().clear();
                mCommonAdapter.notifyDataSetChanged();
                this.showEmpty(emptyMsg, emptyImgRes);
                return;
            }
            mCommonAdapter.setNewData(list);
        } else {// 加载更多
            // 加载完毕
            if (list == null || list.isEmpty()) {
                mCommonAdapter.loadMoreEnd();
                return;
            }
            mCommonAdapter.addData(list);
        }
        // 加载完成
        if (list != null) {
            if (list.size() < pageSize) mCommonAdapter.loadMoreEnd();
        }
    }

    /**
     * 加载完成
     */
    public void onLoadComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
        if (loadMoreEnable) {
            if (!NetworkUtils.isAvailable()) {
                mCommonAdapter.loadMoreFail();
            } else {
                mCommonAdapter.setEnableLoadMore(true);
                mCommonAdapter.loadMoreComplete();
            }
        }
    }

    /**
     * 是否正在刷新
     *
     * @return
     */
    public boolean isRefreshing() {
        return mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing();
    }

    /**
     * 显示空布局
     *
     * @param emptyMsg
     * @param emptyImgRes
     */
    private void showEmpty(String emptyMsg, int emptyImgRes) {
        if (showEmptyView) {
            mCommonAdapter.setEmptyView(errorView);
            if (!Utils.isEmpty(emptyMsg)) {
                TextView tvErrorMsg = (TextView) errorView.findViewById(R.id.error_text);
                tvErrorMsg.setText(emptyMsg);
            }
            if (emptyImgRes != 0) {
                ImageView ivError = (ImageView) errorView.findViewById(R.id.error_image);
                ivError.setImageResource(emptyImgRes);
            }
        }
    }

    /**
     * 显示错误
     *
     * @param errorMsg    错误消息
     * @param errorImgRes 错误图片
     */
    public void showError(String errorMsg, int errorImgRes) {
        if (mCommonAdapter.getData() != null && mCommonAdapter.getData().size() > 0) {
            mCommonAdapter.loadMoreFail();
        } else {
            showEmpty(errorMsg, errorImgRes);
        }
    }

    /**
     * 显示错误
     *
     * @param errorMsg 错误消息
     */
    public void showError(String errorMsg) {
        showError(errorMsg, 0);
    }
}