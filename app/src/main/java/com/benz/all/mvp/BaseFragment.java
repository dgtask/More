package com.benz.all.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benz.all.entity.ResponseMessage;

import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by xubenliang on 2017/6/9.
 */
public abstract class BaseFragment<T extends ResponseMessage> extends Fragment {


    protected View mRootView;

    protected Context mContext;

    /**
     * 全局通用请求对象，可用于取消请求
     */
    protected Call<T> call;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        initPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), null);
            ButterKnife.bind(this, mRootView);
            initViewsAndEvents();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (call != null) {
            call.cancel();
        }
    }

    /**
     * 初始化Presenter
     */
    protected void initPresenter() {

    }

    /**
     * 获取子View
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化View与事件
     */
    protected abstract void initViewsAndEvents();

    /**
     * 初始化 Toolbar
     *
     * @param toolbar
     * @param homeAsUpEnabled
     * @param title
     */
    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        ((MVPBaseActivity) getActivity()).initToolBar(toolbar, homeAsUpEnabled, title);
    }
}
