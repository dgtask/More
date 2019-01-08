package com.benz.all.mvp.readcontent;

import com.benz.all.entity.read.ReadContentResponse;
import com.benz.all.entity.read.ReadResponse;
import com.benz.all.mvp.BasePresenter;
import com.benz.all.mvp.BaseView;

import retrofit2.Call;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ReadContentContract {
    interface View extends BaseView {
        void onSuccess(String html);
        void onFailure(Call<ReadContentResponse> call, Throwable t);
    }

    interface Presenter extends BasePresenter<View> {
        Call<ReadContentResponse> getReadContent(Long id);
    }
}
