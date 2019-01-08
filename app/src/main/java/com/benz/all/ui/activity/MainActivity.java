package com.benz.all.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

import com.benz.all.R;
import com.benz.all.app.AppContext;
import com.benz.all.app.Constants;
import com.benz.all.mvp.BaseActivity;
import com.benz.all.mvp.movie.MovieFragment;
import com.benz.all.mvp.moviesearch.MovieSearchActivity;
import com.benz.all.mvp.music.MusicFragment;
import com.benz.all.mvp.news.NewsFragment;
import com.benz.all.mvp.read.ReadListFragment;
import com.benz.all.mvp.video.VideoFragment;
import com.benz.all.utils.ToastUtils;
import com.benz.all.utils.tasks.Tasks;
import com.orhanobut.hawk.Hawk;
import com.socks.library.KLog;

import butterknife.BindView;

/**
 * 主页
 * Created by xubenliang on 2017/6/1.
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    /**
     * 搜索菜单
     */
    MenuItem searchItem;
    /**
     * 当前页面
     */
    private Fragment mCurrentFragment;
    /**
     * 新闻
     */
    private NewsFragment mNewsFragment;
    /**
     * 阅读
     */
    private ReadListFragment mReadFragment;
    /**
     * 音乐 nav_music
     */
//    private MusicFragment mMusicFragment;
    /**
     * 电影
     */
//    private MovieFragment mMovieFragment;
    private VideoFragment mMovieFragment;
    /**
     * 标签
     */
    private Integer fragmentTag;

    private long mExitTime = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents() {
        // 初始化DrawerLayout
        initDrawerLayout(mDrawerLayout, mNavView);
        // 设置默认加载页面
        setDefaultFragment();
    }

    /**
     * 设置默认加载的页面，默认新闻
     */
    private void setDefaultFragment() {
        fragmentTag = Hawk.get(Constants.FRAGMENT_TAG, Constants.TAG_MAIN_NEWS);
        mToolbar.setTitle(getTitleString(fragmentTag));
        mNavView.setCheckedItem(getNavId(fragmentTag));
        mCurrentFragment = getTargetFragment(fragmentTag);
        switchFragment(R.id.fl_container, null, mCurrentFragment);
        searchItem.setVisible(fragmentTag == Constants.TAG_MAIN_MOVIE);
    }


    /**
     * 初始化 DrawerLayout
     *
     * @param drawerLayout DrawerLayout
     * @param navView      NavigationView
     */
    private void initDrawerLayout(DrawerLayout drawerLayout, NavigationView navView) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            //将侧边栏顶部延伸至status bar
            drawerLayout.setFitsSystemWindows(true);
            //将主页面顶部延伸至status bar
            drawerLayout.setClipToPadding(false);
        }

        // 显示侧滑图标
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);

        // 设置菜单栏，只在电影搜索页面使用
        mToolbar.inflateMenu(R.menu.activity_main_search);
        searchItem = mToolbar.getMenu().findItem(R.id.action_search);
        Intent intent = new Intent(this, MovieSearchActivity.class);
        searchItem.setIntent(intent);

        // 设置夜间/日间模式文字
        boolean isNight = Hawk.get(Constants.NIGHT_MODE, false);
        navView.getMenu().findItem(R.id.nav_night).setTitle(isNight ? R.string.day_mode : R.string.night_mode);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        if (item.isChecked()) {
            return true;
        }
        setNavigationItemSelected(item);
        return true;
    }

    /**
     * 设置点击事件
     *
     * @param item
     */
    private void setNavigationItemSelected(MenuItem item) {
        mToolbar.setTitle(item.getTitle());
        switch (item.getItemId()) {
            case R.id.nav_setting:
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                return;
            case R.id.nav_night:
                Hawk.put(Constants.FRAGMENT_TAG, fragmentTag);
                AppContext.recreate = true;
                boolean isNight = Hawk.get(Constants.NIGHT_MODE, false);
                AppCompatDelegate.setDefaultNightMode(isNight ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES);
                item.setTitle(isNight ? R.string.day_mode : R.string.night_mode);
                Hawk.put(Constants.NIGHT_MODE, !isNight);
                recreate();
                Tasks.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AppContext.recreate = false;
                    }
                }, 3000);
                return;
        }
        switch (item.getItemId()) {
            case R.id.nav_news:
                fragmentTag = Constants.TAG_MAIN_NEWS;
                searchItem.setVisible(false);
                break;
            case R.id.nav_read:
                fragmentTag = Constants.TAG_MAIN_READ;
                searchItem.setVisible(false);
                break;
//            case R.id.nav_music:
//                fragmentTag = Constants.TAG_MAIN_MUSIC;
//                searchItem.setVisible(false);
//                break;
            case R.id.nav_movie:
                fragmentTag = Constants.TAG_MAIN_MOVIE;
                searchItem.setVisible(true);
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mDrawerLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                Fragment toFragment = getTargetFragment(fragmentTag);
                switchFragment(R.id.fl_container, mCurrentFragment, toFragment);
                mCurrentFragment = toFragment;
                Hawk.put(Constants.FRAGMENT_TAG, fragmentTag);
            }
        }, 250);
    }

    /**
     * 获取标题
     *
     * @param tag
     * @return
     */
    private String getTitleString(int tag) {
        switch (tag) {
            case Constants.TAG_MAIN_NEWS:
                return getString(R.string.main_news);
            case Constants.TAG_MAIN_READ:
                return getString(R.string.main_read);
            case Constants.TAG_MAIN_MUSIC:
                return getString(R.string.main_music);
            case Constants.TAG_MAIN_MOVIE:
                return getString(R.string.main_movie);
        }
        return getString(R.string.main_news);
    }

    /**
     * 获取导航id
     *
     * @param tag
     * @return
     */
    private int getNavId(int tag) {
        switch (tag) {
            case Constants.TAG_MAIN_NEWS:
                return R.id.nav_news;
            case Constants.TAG_MAIN_READ:
                return R.id.nav_read;
//            case Constants.TAG_MAIN_MUSIC:
//                return R.id.nav_music;
            case Constants.TAG_MAIN_MOVIE:
                return R.id.nav_movie;
        }
        return R.id.nav_news;
    }

    /**
     * 根据标签来获取Fragment
     *
     * @param tag
     * @return
     */
    private Fragment getTargetFragment(int tag) {
        switch (tag) {
            case Constants.TAG_MAIN_NEWS:
                if (mNewsFragment == null) mNewsFragment = new NewsFragment();
                return mNewsFragment;
            case Constants.TAG_MAIN_READ:
                if (mReadFragment == null) mReadFragment = new ReadListFragment();
                return mReadFragment;
//            case Constants.TAG_MAIN_MUSIC:
//                if (mMusicFragment == null) mMusicFragment = new MusicFragment();
//                return mMusicFragment;
            case Constants.TAG_MAIN_MOVIE:
                if (mMovieFragment == null) mMovieFragment = new VideoFragment();
                return mMovieFragment;
        }
        return mNewsFragment;
    }

    /**
     * 切换Fragment
     *
     * @param containerViewId fragment布局编号
     * @param from            当前Fragment
     * @param to              目标Fragment
     */
    protected void switchFragment(int containerViewId, Fragment from, Fragment to) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // 如果from的Fragment不存在，则代表添加
        if (from == null) {
            transaction.add(containerViewId, to);
            transaction.commit();
            return;
        }
        // 先判断是否被add过
        if (!to.isAdded()) {
            // 隐藏当前的fragment，add下一个到Activity中
            transaction.hide(from).add(containerViewId, to).commitAllowingStateLoss();
        } else {
            // 隐藏当前的fragment，显示下一个
            transaction.hide(from).show(to).commitAllowingStateLoss();
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            exit();
        }
    }

    /**
     * 退出
     */
    private void exit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            ToastUtils.show("再按一次退出程序");
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
