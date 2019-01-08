package com.benz.all.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.benz.all.entity.ResponseMessage;

import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by xubenliang on 2017/6/9.
 */
public abstract class BaseActivity<T extends ResponseMessage> extends AppCompatActivity {

    /**
     * 全局通用请求，用于取消请求
     */
    protected Call<T> call;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
            ButterKnife.bind(this);
            initViewsAndEvents();
        }
    }

    @Override
    protected void onDestroy() {
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
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
