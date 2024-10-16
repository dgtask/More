package com.benz.all.mvp;

/**
 * MVPPlugin
 *  Created by xubenliang on 2017/6/4.
 */
public class BasePresenterImpl<V extends BaseView> implements BasePresenter<V>{

    protected V mView;

    @Override
    public void attachView(V view) {
        mView=view;
    }

    @Override
    public void detachView() {
        mView=null;
    }
}
