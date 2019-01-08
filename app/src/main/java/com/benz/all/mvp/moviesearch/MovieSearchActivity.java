package com.benz.all.mvp.moviesearch;


import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;

import com.benz.all.R;
import com.benz.all.entity.movie.MovieSearchResponse;
import com.benz.all.entity.movie.tiantan.SearchItem;
import com.benz.all.entity.movie.tiantan.Subjects;
import com.benz.all.mvp.MVPBaseActivity;
import com.benz.all.mvp.moviedetail.MovieDetailActivity;
import com.benz.all.ui.views.RecyclerViewHelper;
import com.benz.all.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */
public class MovieSearchActivity extends MVPBaseActivity<MovieSearchContract.View, MovieSearchPresenter> implements MovieSearchContract.View {

    /**
     * 列表帮助类
     */
    private RecyclerViewHelper mRecyclerViewHelper;
    /**
     * 适配器
     */
    private BaseQuickAdapter<SearchItem, BaseViewHolder> mCommonAdapter;

    @BindView(R.id.searchView)
    SearchView searchView;

    /**
     * 搜索关键字
     */
    String keywords;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_search;
    }

    @Override
    protected void initViewsAndEvents() {

        mCommonAdapter = mPresenter.getCommonAdapter(new ArrayList<SearchItem>());

        mRecyclerViewHelper = mPresenter.getRecyclerViewHelper(this, mCommonAdapter);

        searchView.setQueryHint("搜索电影");
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(true);
        searchView.onActionViewExpanded();
        searchView.setFocusable(true);
        searchView.requestFocusFromTouch();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(Utils.isEmpty(query)){
                    return false;
                }
                keywords = query;
                mRecyclerViewHelper.autoRefresh();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean queryTextFocused) {
                if(!queryTextFocused) {
                    searchView.setQuery("", false);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        call = mPresenter.getMovieSearch(keywords);
    }

    @Override
    public void onItemClick(int position, ImageView imageView) {
        SearchItem searchItem = mCommonAdapter.getItem(position);
        Subjects subject = new Subjects(false, null);
        subject.setImg(searchItem.getImg());
        subject.setMovieId(searchItem.getMovieId());
        subject.setName(searchItem.getName());
        subject.setScore(searchItem.getScore());
        subject.setAlbum(searchItem.isAlbum());
        MovieDetailActivity.goActivity(this, subject, imageView);
    }

    @Override
    public void onSuccess(List<SearchItem> searchItemList) {
        mRecyclerViewHelper.onLoadComplete();
        mRecyclerViewHelper.onLoadData("没有电影", 1, searchItemList);
    }

    @Override
    public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
        mRecyclerViewHelper.onLoadComplete();
        mRecyclerViewHelper.showError("网络异常");
    }

}