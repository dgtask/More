package com.benz.all.utils.tasks;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import com.socks.library.KLog;

import java.util.concurrent.Executor;

/**
 * 线程任务工具类
 * @author xubenliang
 */
public final class Tasks {

    private Tasks() {
        throw new UnsupportedOperationException();
    }

    private volatile static Handler handler;

    public static Handler getHandler() {
        if (handler == null) {
            synchronized (Handler.class) {
                if (handler == null) {
                    handler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return handler;
    }

    /**
     * UI线程
     * @param run
     */
    public static void post(Runnable run) {
        getHandler().post(run);
    }

    /**
     * UI线程，延迟执行
     * @param run
     * @param delay
     */
    public static void postDelayed(Runnable run, long delay) {
        getHandler().postDelayed(run, delay);
    }

    /**
     * 简单的异步任务
     *
     * @param run
     */
    public static void postAsync(final Runnable run) {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    run.run();
                } catch (Exception e) {
                    KLog.e("postAsync", e.getMessage());
                }
                return null;
            }
        };
        task.execute();
    }

    /**
     * 异步任务
     * @param context
     * @param backgroundWork
     * @param <T>
     */
    public static <T> void executeInBackground(Context context, BackgroundWork<T> backgroundWork) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            executeInBackground(context, backgroundWork, null, AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            new Task<>(context, backgroundWork, null).execute();
        }
    }

    /**
     * 异步任务，有回调到UI线程
     * @param context
     * @param backgroundWork
     * @param completion
     * @param <T>
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static <T> void executeInBackground(Context context, BackgroundWork<T> backgroundWork, Completion<T> completion) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            executeInBackground(context, backgroundWork, completion, AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            new Task<>(context, backgroundWork, completion).execute();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static <T> void executeInBackground(Context context, BackgroundWork<T> backgroundWork, Completion<T> completion, Executor executor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new Task<>(context, backgroundWork, completion).executeOnExecutor(executor);
        } else {
            throw new RuntimeException("you cannot use title_bg custom executor on pre honeycomb devices");
        }
    }



}
