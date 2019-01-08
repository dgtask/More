package com.benz.all.app;

/**
 * 常量配置
 * Created by xubenliang on 2015/9/17.
 */
public class Constants {

    /**
     * CnBeta接口
     */
    public static final String BASE_URL_NEWS = "http://api.cnbeta.com/";
    /**
     * CnBeta首页缓存Key
     */
    public static final String NEWS_RESPONSE_CACHE_KEY = "NewsResponseCache";
    /**
     * CnBeta新闻类型
     */
    public static final String CNBETA_TYPE_NEWS_LISTS = "Article.Lists";
    public static final String CNBETA_TYPE_NEWS_CONTENT = "Article.NewsContent";
    public static final String CNBETA_TYPE_NEWS_COMMENT = "Article.Comment";

    /***********************************************************************************************/

    /**
     * 锤子阅读接口
     */
    public static final String BASE_URL_READ = "http://app.idaxiang.org/";
    /**
     * 阅读列表缓存
     */
    public static final String READ_RESPONSE_CACHE_KEY = "ReadResponseCache";

    /***********************************************************************************************/

    /**
     * 翻片视频
     */
    public static final String BASE_URL_VIDEO = "http://morguo.com/";
    /**
     * 翻片视频缓存Key
     */
    public static final String VIDEO_RESPONSE_CACHE_KEY = "VideoResponseCache";

    /**
     * 动态获取电影天堂接口
     */
    public static final String GET_BASE_URL_MOVIE = "http://233movie.com/";
    /**
     * 电影天堂接口
     */
    public static final String BASE_URL_MOVIE = "http://43.241.224.161/newmovie/";//43.241.227.35
    /**
     * 电影首页缓存
     */
    public static final String MOVIE_INDEX_CACHE_KEY = "MovieIndexCache";
    /**
     * 豆瓣电影接口
     */
    private final static String BASE_URL_MOVIE_DOUBAN = "https://api.douban.com/";

    /***********************************************************************************************/

    /**
     * 存储夜间模式状态
     */
    public static final String NIGHT_MODE = "NightMode";
    /**
     * 列表每一页的条数
     */
    public static final int PAGE_SIZE = 20;

    /**
     * Fragment标签
     */
    public static final String FRAGMENT_TAG = "FragmentTag";
    public static final int TAG_MAIN_NEWS = 0;
    public static final int TAG_MAIN_READ = 1;
    public static final int TAG_MAIN_MUSIC = 2;
    public static final int TAG_MAIN_MOVIE = 3;

    /**
     * 腾讯Bugly ID
     */
    public static final String BUGLY_APP_ID = "900036131";
}
