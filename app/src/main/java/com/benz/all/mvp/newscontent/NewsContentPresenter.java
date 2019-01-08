package com.benz.all.mvp.newscontent;

import com.benz.all.api.Api;
import com.benz.all.api.CnBetaParam;
import com.benz.all.app.AppContext;
import com.benz.all.app.Constants;
import com.benz.all.entity.news.NewsContent;
import com.benz.all.entity.news.NewsContentResponse;
import com.benz.all.mvp.BasePresenterImpl;
import com.benz.all.net.RetrofitNews;
import com.benz.all.utils.JsonUtils;
import com.orhanobut.hawk.Hawk;
import com.socks.library.KLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Response;

/**
 * MVPPlugin
 * Created by xubenliang on 2017/6/4.
 */
public class NewsContentPresenter extends BasePresenterImpl<NewsContentContract.View> implements NewsContentContract.Presenter {


    @Override
    public Call<NewsContentResponse> getNewsContent(String articleId) {
        Api api = RetrofitNews.getInstance().create(Api.class);
        CnBetaParam param = new CnBetaParam();
        param.setSid(articleId);
        param.setTimestamp(System.currentTimeMillis());
        param.setMethod(Constants.CNBETA_TYPE_NEWS_CONTENT);
        Call<NewsContentResponse> call = api.getNewsContent(param.getParam());
        call.enqueue(new retrofit2.Callback<NewsContentResponse>() {
            @Override
            public void onResponse(Call<NewsContentResponse> call, final Response<NewsContentResponse> response) {
                if (mView == null) {
                    return;
                }
                String html = getHtml(response.body().getResult());
                mView.onSuccess(html);
            }

            @Override
            public void onFailure(Call<NewsContentResponse> call, Throwable t) {
                KLog.json(JsonUtils.toJson(call.request()));
                if (mView == null) {
                    return;
                }
                mView.onFailure(call, t);
            }
        });
        return call;
    }

    /**
     * 转换对应的HTML
     *
     * @param newsContent
     * @return
     */
    private String getHtml(NewsContent newsContent) {
        if (newsContent == null) {
            return null;
        }
        InputStreamReader inputReader = null;
        try {
            boolean isNight = Hawk.get(Constants.NIGHT_MODE, false);
            inputReader = new InputStreamReader(AppContext.getInstance().getResources().getAssets().open(isNight ? "news_content_night.htm" : "news_content.htm"));
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
        str = str.replace("${Title}", newsContent.getTitle());
        str = str.replace("${Source}", newsContent.getShorttitle());
        str = str.replace("${Time}", newsContent.getTime());
        str = str.replace("${Summary}", newsContent.getHometext());
        str = str.replace("${Content}", newsContent.getBodytext());
        return str;
    }
}
