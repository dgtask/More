package com.benz.all.mvp.newscontent;

import com.benz.all.entity.news.NewsContentResponse;
import com.benz.all.mvp.BasePresenter;
import com.benz.all.mvp.BaseView;

import retrofit2.Call;

/**
 * MVPPlugin
 * Created by xubenliang on 2017/6/4.
 */

public class NewsContentContract {

    interface View extends BaseView {
        void onSuccess(String html);

        void onFailure(Call<NewsContentResponse> call, Throwable t);
    }

    interface Presenter extends BasePresenter<View> {
        Call<NewsContentResponse> getNewsContent(String articleId);
    }
}
