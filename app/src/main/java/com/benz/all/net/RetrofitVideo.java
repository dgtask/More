package com.benz.all.net;

import com.benz.all.app.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 视频请求
 * Created by xubenliang on 2017/6/28.
 */
public class RetrofitVideo {
    // 单例
    private static RetrofitVideo INSTANCE;
    // Retrofit 对象
    private Retrofit mRetrofit;

    private RetrofitVideo() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_VIDEO) // 添加baseurl
                .addConverterFactory(GsonConverterFactory.create()) //create中可以传入其它json对象，默认Gson
                .build();
    }

    public static RetrofitVideo getInstance() {
        if (INSTANCE == null) {
            synchronized (RetrofitVideo.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetrofitVideo();
                }
            }
        }

        return INSTANCE;
    }

    /**
     * 转换为对象的Service
     *
     * @param service
     * @param <T>
     * @return 传入的类型
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }
}
