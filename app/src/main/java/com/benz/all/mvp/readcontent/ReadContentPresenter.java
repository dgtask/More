package com.benz.all.mvp.readcontent;

import com.benz.all.api.Api;
import com.benz.all.app.AppContext;
import com.benz.all.app.Constants;
import com.benz.all.entity.read.Article;
import com.benz.all.entity.read.ReadContentResponse;
import com.benz.all.mvp.BasePresenterImpl;
import com.benz.all.net.RetrofitRead;
import com.benz.all.utils.JsonUtils;
import com.benz.all.utils.Utils;
import com.orhanobut.hawk.Hawk;
import com.socks.library.KLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MVPPlugin
 */
public class ReadContentPresenter extends BasePresenterImpl<ReadContentContract.View> implements ReadContentContract.Presenter {

    @Override
    public Call<ReadContentResponse> getReadContent(Long id) {
        Api api = RetrofitRead.getInstance().create(Api.class);
        Call<ReadContentResponse> call = api.getReadContent(id);
        call.enqueue(new Callback<ReadContentResponse>() {
            @Override
            public void onResponse(Call<ReadContentResponse> call, Response<ReadContentResponse> response) {
                KLog.json(JsonUtils.toJson(response.body()));
                if (mView == null) {
                    return;
                }
                mView.onSuccess(getHtml(response.body().getBody().getArticle()));
            }

            @Override
            public void onFailure(Call<ReadContentResponse> call, Throwable t) {
                KLog.json(JsonUtils.toJson(call.request()));
                if (mView == null) {
                    return;
                }
                mView.onFailure(call, t);
            }
        });
        return null;
    }

    /**
     * 转换对应的HTML
     *
     * @param article
     * @return
     */
    private String getHtml(Article article) {
        if (article == null) {
            return null;
        }
        InputStreamReader inputReader = null;
        try {
            boolean isNight = Hawk.get(Constants.NIGHT_MODE, false);
            inputReader = new InputStreamReader(AppContext.getInstance().getResources().getAssets().open(isNight ? "read_content_night.htm" : "read_content.htm"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputReader);
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Insert data
        String str = sb.toString();
        str = str.replace("${Title}", article.getTitle());
        str = str.replace("${Source}", article.getAuthor());
        str = str.replace("${Time}", Utils.formatDate(article.getCreate_time()));
        str = str.replace("${Content}", article.getContent());
        str = str.replace("${ReadNum}", "阅读量：" + article.getRead_num());
        return str;
    }
}
