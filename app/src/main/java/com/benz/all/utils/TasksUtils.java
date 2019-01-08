package com.benz.all.utils;

import android.content.Context;

import com.benz.all.app.AppContext;
import com.benz.all.callback.Callback;
import com.benz.all.utils.tasks.BackgroundWork;
import com.benz.all.utils.tasks.Completion;
import com.benz.all.utils.tasks.Tasks;
import com.orhanobut.hawk.Hawk;
import com.socks.library.KLog;


/**
 * 异步任务
 * Hawk保存配置信息
 * ACache缓存网络数据
 * Created by xubenliang on 2016/5/30.
 */
public class TasksUtils {

    /*************************Hawk*************************/

    /**
     * 保存SharedPreferences数据
     *
     * @param key
     * @param value
     */
    public static <T> void saveDataToSp(final String key, final T value) {
        Hawk.put(key, value);
    }

    /**
     * 异步保存SharedPreferences数据
     *
     * @param key
     * @param value
     */
    public static <T> void saveDataToSpAsyn(final String key, final T value) {
        saveDataToSpAsyn(key, value, null);
    }

    /**
     * 异步保存SharedPreferences数据
     *
     * @param key
     * @param value
     * @param callback
     */
    public static <T> void saveDataToSpAsyn(final String key, final T value, final Callback callback) {
        Tasks.executeInBackground(AppContext.getInstance(), new BackgroundWork<Boolean>() {
            @Override
            public Boolean doInBackground() throws Exception {
                return Hawk.put(key, value);
            }
        }, new Completion<Boolean>() {
            @Override
            public void onSuccess(Context context, Boolean result) {
                if (callback != null) {
                    callback.onCallback(result);
                }
            }

            @Override
            public void onError(Context context, Exception e) {
                if (callback != null) {
                    callback.onCallback(false);
                }
            }
        });
    }

    /**
     * 获取SharedPreferences数据
     *
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getDataFromSp(final String key) {
        return Hawk.get(key);
    }

    /**
     * 异步获取SharedPreferences数据
     *
     * @param key
     * @param callback
     * @param <T>
     */
    public static <T> void getDataFromSpAsyn(final String key, final Callback<T> callback) {
        Tasks.executeInBackground(AppContext.getInstance(), new BackgroundWork<T>() {
            @Override
            public T doInBackground() throws Exception {
                return Hawk.get(key);
            }
        }, new Completion<T>() {
            @Override
            public void onSuccess(Context context, T result) {
                if (callback != null) {
                    callback.onCallback(result);
                }
            }

            @Override
            public void onError(Context context, Exception e) {

            }
        });
    }

    /**
     * 删除SharedPreferences数据
     */
    public static void deleteDataFromSp(String key) {
        if (key != null) {
            Hawk.delete(key);
        }
    }

    /**
     * 数据是否包含在SharedPreferences
     */
    public static boolean isContainsFromSP(String key) {
        return key == null ? false : Hawk.contains(key);
    }
}