package com.benz.all.api;

import com.benz.all.entity.movie.MovieDetailResponse;
import com.benz.all.entity.movie.MovieResponse;
import com.benz.all.entity.movie.MovieSearchResponse;
import com.benz.all.entity.movie.MovieTopResponse;
import com.benz.all.entity.movie.MovieTopicItemResponse;
import com.benz.all.entity.movie.douban.HotMovieBean;
import com.benz.all.entity.movie.douban.MovieDetailBean;
import com.benz.all.entity.news.NewsCommentResponse;
import com.benz.all.entity.news.NewsContentResponse;
import com.benz.all.entity.news.NewsResponse;
import com.benz.all.entity.read.ReadContentResponse;
import com.benz.all.entity.read.ReadResponse;
import com.benz.all.entity.video.VideoResponse;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * CnBeta API接口
 * Created by xubenliang on 2017/6/5.
 */
public interface Api {

    /*******************************新闻*******************************/

    /**
     * 获取新闻
     *
     * @param options 请求参数
     * @return
     */
    @GET("capi")
    Call<NewsResponse> getNews(@QueryMap Map<String, String> options);

    /**
     * 获取更多新闻
     *
     * @param options 请求参数
     * @return
     */
    @GET("capi")
    Call<NewsResponse> getNewsMore(@QueryMap Map<String, String> options);

    /**
     * 获取新闻详情
     *
     * @param options 请求参数
     * @return
     */
    @GET("capi")
    Call<NewsContentResponse> getNewsContent(@QueryMap Map<String, String> options);

    /**
     * 获取新闻评论
     *
     * @param options 请求参数
     * @return
     */
    @GET("capi")
    Call<NewsCommentResponse> getNewsComment(@QueryMap Map<String, String> options);


    /*******************************阅读*******************************/

    /**
     * 获取文章列表
     *
     * @param pageSize
     * @param page
     * @return
     */
    @GET("api/v1_0/art/list")
    Call<ReadResponse> getReadList(@Query("pageSize") int pageSize,
                                   @Query("page") int page);

    /**
     * 获取更多文章
     * @param pageSize
     * @return
     */
    @GET("api/v1_0/art/list")
    Call<ReadResponse> getReadMoreList(@Query("pageSize") int pageSize,
                                       @Query("create_time") String create_time,
                                       @Query("update_time") String update_time);

    /**
     * 获取文章内容
     *
     * @param id
     * @return
     */
    @GET("api/v1_0/art/info")
    Call<ReadContentResponse> getReadContent(@Query("id") Long id);


    /*******************************电影天堂*******************************/

    /**
     * 获取电影接口
     * @return
     */
    @GET("newip.txt")
    Call<ResponseBody> getBaseMovieUrl();

    /**
     * 获取电影首页
     *
     * @return
     */
    @GET("api/index")
    Call<MovieResponse> getMovieIndex();

    /**
     * 获取电影更多榜单，默认直接获取所有榜单
     *
     * @return
     */
    /*@Headers({
            "userId: VfeNc4iZKHQDAITtjfO+iOnw",
            "platform: android",
            "appVersion: 5.2.0",
            "platVersion: 5.1",
            "Host: 43.241.224.161",
            "Connection: Keep-Alive",
            "Accept-Encoding: gzip"
    })*/
    @GET("api/more_doubantopic_list")//?page=1&pageSize=20
    Call<MovieTopResponse> getMovieTopList();

    /**
     * 获取榜单里电影列表
     *
     * @param page
     * @param id
     * @return
     */
    @GET("/api/more_douban_topic_items?&pageSize=21")//&page=1&id=movie_score
    Call<MovieTopicItemResponse> getMovieTopicItems(@Query("page") int page,
                                                    @Query("id") String id);

    /**
     * 获取电影详情
     *
     * @param videoId
     * @param isAlbum
     * @return
     */
    @GET("api/video")
    Call<MovieDetailResponse> getMovieDetail(@Query("videoId") Long videoId,
                                       @Query("isAlbum") boolean isAlbum);


    /**
     * 搜索电影
     * @param keywords
     * @return
     */
    @GET("/api/videos")
    Call<MovieSearchResponse> getSearchMovie(@Query("keywords") String keywords);

    /*******************************豆瓣电影*******************************/
    /**
     * 豆瓣热映电影，每日更新
     */
    @GET("v2/movie/in_theaters")
    Call<HotMovieBean> getHotMovie();

    /**
     * 获取电影详情
     *
     * @param id 电影bean里的id
     */
    @GET("v2/movie/subject/{id}")
    Call<MovieDetailBean> getMovieDetail(@Path("id") String id);

    /**
     * 获取豆瓣电影top250
     *
     * @param start 从多少开始，如从"0"开始
     * @param count 一次请求的数目，如"10"条，最多100
     */
    @GET("v2/movie/top250")
    Call<HotMovieBean> getMovieTop250(@Query("start") int start, @Query("count") int count);

    /*******************************翻片视频*******************************/

    @GET("forum.php?androidflag=1")
    Call<VideoResponse> getVideos(@Query("mod") String mod,
                                 @Query("rtype") String rtype,
                                 @Query("page") int page);

//    http://morguo.com/forum.php?mod=threadvideo&androidflag=1&page=1
//    morguo.com/forum.php?mod=threadvideoranking&rtype=week&androidflag=1&page=1
//    morguo.com/forum.php?mod=threadvideoranking&rtype=month&androidflag=1&page=1
//    morguo.com/forum.php?mod=threadvideoranking&rtype=all&androidflag=1&page=1


}
