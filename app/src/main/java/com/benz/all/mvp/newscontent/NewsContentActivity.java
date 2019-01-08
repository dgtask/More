package com.benz.all.mvp.newscontent;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.benz.all.R;
import com.benz.all.entity.news.NewsContentResponse;
import com.benz.all.entity.news.NewsItem;
import com.benz.all.mvp.MVPBaseActivity;
import com.benz.all.mvp.newscomment.NewsCommentActivity;
import com.benz.all.ui.views.XWebView;
import com.benz.all.utils.Utils;

import butterknife.BindView;
import ezy.ui.layout.LoadingLayout;
import retrofit2.Call;

/**
 * MVPPlugin
 * Created by xubenliang on 2017/6/4.
 */
public class NewsContentActivity extends MVPBaseActivity<NewsContentContract.View, NewsContentPresenter> implements NewsContentContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.webView)
    XWebView webView;

    private LoadingLayout loadingLayout;

    private NewsItem newsItem;

    /**
     * 跳转到新闻详情
     *
     * @param mContext
     * @param newsItem
     */
    public static void start(final Context mContext, final NewsItem newsItem) {
        Intent intent = new Intent(mContext, NewsContentActivity.class);
        intent.putExtra("NewsItem", newsItem);
        mContext.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_content;
    }

    @Override
    protected void initViewsAndEvents() {

        initToolBar(mToolbar, true, "详情");

        newsItem = (NewsItem) getIntent().getSerializableExtra("NewsItem");

        loadingLayout = LoadingLayout.wrap(webView);

        if (newsItem != null) {
            loadingLayout.showLoading();
            call = mPresenter.getNewsContent(newsItem.getSid());
        } else {
            loadingLayout.showError();
        }
    }

    @Override
    public void onSuccess(String html) {
        loadHtml(html);
    }

    @Override
    public void onFailure(Call<NewsContentResponse> cl, Throwable t) {
        loadingLayout.showError();
        loadingLayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingLayout.showLoading();
                call = mPresenter.getNewsContent(newsItem.getSid());
            }
        });
    }

    /**
     * 加载数据
     *
     * @param html
     */
    private void loadHtml(final String html) {
        if (html != null) {
            webView.loadDataWithBaseURL(null, html, "text/html", "charset=UTF-8", null);
            webView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingLayout.showContent();
                }
            }, 200);
        } else {
            loadingLayout.showEmpty();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_news_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_comment:
                NewsCommentActivity.start(this, newsItem.getSid());
                break;
            case R.id.action_share:
                String url = "http://www.cnbeta.com/articles/" + newsItem.getSid() + ".htm";
                Utils.share(this, url);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}