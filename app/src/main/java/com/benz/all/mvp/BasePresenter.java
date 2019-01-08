package com.benz.all.mvp;

/**
 * MVPPlugin
 *  Created by xubenliang on 2017/6/4.
 */

public interface  BasePresenter <V extends BaseView>{
    void attachView(V view);

    void detachView();
}
