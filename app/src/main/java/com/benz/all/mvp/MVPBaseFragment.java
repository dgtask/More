package com.benz.all.mvp;

import android.content.Context;
import java.lang.reflect.ParameterizedType;

/**
 * MVPPlugin
 * Created by xubenliang on 2017/6/4.
 */
public abstract class MVPBaseFragment<V extends BaseView, T extends BasePresenterImpl<V>> extends BaseFragment implements BaseView {

    public T mPresenter;

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mPresenter = getInstance(this, 1);
        if (mPresenter != null)
            mPresenter.attachView((V) this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    public Context getContext() {
        return super.getContext();
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
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}