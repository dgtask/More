package com.benz.all.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.lang.reflect.ParameterizedType;

import butterknife.ButterKnife;

/**
 * MVPPlugin
 * Created by xubenliang on 2017/6/4.
 */
public abstract class MVPBaseActivity<V extends BaseView, T extends BasePresenterImpl<V>> extends BaseActivity implements BaseView {

    public T mPresenter;

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mPresenter = getInstance(this, 1);
        if (mPresenter != null)
            mPresenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    public Context getContext() {
        return this;
    }

    public <T> T getInstance(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }


}
