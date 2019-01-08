package com.benz.all.app;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.v7.app.AppCompatDelegate;

import com.benz.all.BuildConfig;
import com.benz.all.utils.tasks.Tasks;
import com.orhanobut.hawk.Hawk;
import com.socks.library.KLog;

/**
 * Created by xubenliang on 2017/6/1.
 */
public class AppContext extends Application {

    public static boolean recreate;
    /**
     * 全局上下文
     */
    private static Context context;

    public static Context getInstance() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        // 严格模式，检测耗时操作
        if (BuildConfig.LEO_DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode
                    .ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode
                    .VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }

        // 配置数据存储
        Hawk.init(context).build();

        Tasks.postAsync(new Runnable() {
            @Override
            public void run() {
                // 配置日志打印
                KLog.init(BuildConfig.LEO_DEBUG, "ALL");
                // 设置夜间模式
                boolean isNight = Hawk.get(Constants.NIGHT_MODE, false);
                AppCompatDelegate.setDefaultNightMode(isNight ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
    }
}