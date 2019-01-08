package com.benz.all.net;

import com.benz.all.app.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 电影请求
 * Created by xubenliang on 2017/6/27.
 */
public class RetrofitMovie {

    // 单例
    private static RetrofitMovie INSTANCE;

    // Retrofit 对象
    private Retrofit mRetrofit;

    private RetrofitMovie() {
        // 配置头部
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Accept-Encoding", "gzip")
                                .addHeader("Connection", "Keep-Alive")
                                .addHeader("platform", "android")
                                .addHeader("appVersion", "5.2.0")
                                .build();
                        return chain.proceed(request);
                    }
                }).build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_MOVIE) // 添加baseurl
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create()) //create中可以传入其它json对象，默认Gson
                .build();
    }

    public static RetrofitMovie getInstance() {
        if (INSTANCE == null) {
            synchronized (RetrofitMovie.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetrofitMovie();
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
