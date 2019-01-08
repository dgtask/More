package com.benz.all.mvp.readcontent;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.benz.all.R;
import com.benz.all.entity.read.Article;
import com.benz.all.entity.read.ReadContentResponse;
import com.benz.all.mvp.MVPBaseActivity;
import com.benz.all.ui.views.XWebView;
import com.benz.all.utils.NetworkUtils;
import com.benz.all.utils.Utils;

import butterknife.BindView;
import ezy.ui.layout.LoadingLayout;
import retrofit2.Call;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ReadContentActivity extends MVPBaseActivity<ReadContentContract.View, ReadContentPresenter> implements ReadContentContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.webView)
    XWebView webView;
    /**
     * 加载状态
     */
    LoadingLayout loadingLayout;
    /**
     * 真实地址
     */
    Article article;

    /**
     * 页面跳转
     *
     * @param mContext
     * @param article
     */
    public static void start(Context mContext, Article article) {
        Intent intent = new Intent(mContext, ReadContentActivity.class);
        intent.putExtra("Article", article);
        mContext.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_read_content;
    }

    @Override
    protected void initViewsAndEvents() {
        article = (Article) getIntent().getSerializableExtra("Article");
        loadingLayout = LoadingLayout.wrap(webView);
        if (article == null) {
            loadingLayout.showError();
            return;
        }
        initToolBar(mToolbar, true, "详情");
        loadingLayout.showLoading();
        call = mPresenter.getReadContent(article.getId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_read_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_share:
                Utils.share(this, article.getWechat_url());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(String html) {
        loadHtml(html);
    }

    @Override
    public void onFailure(Call<ReadContentResponse> call, Throwable t) {
        loadHtml(null);
    }

    /**
     * 加载Html
     *
     * @param html
     */
    private void loadHtml(String html) {
        if (!NetworkUtils.isConnected()) {
            loadingLayout.showError();
            loadingLayout.setRetryListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadingLayout.showLoading();
                    call = mPresenter.getReadContent(article.getId());
                }
            });
            return;
        }
        if (html != null) {
            webView.loadDataWithBaseURL(null, html, "text/html", "charset=UTF-8", null);
            webView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingLayout.showContent();
                }
            }, 200);
        } else {
            webView.loadUrl(article.getWechat_url());
            webView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingLayout.showContent();
                }
            }, 200);
        }
    }
}