package com.benz.all.utils;

import android.content.Context;
import android.widget.Toast;

import com.benz.all.app.AppContext;

/**
 * Toast工具
 * @author xubenliang
 */
public class ToastUtils {

    private static Context context = AppContext.getInstance();

	private ToastUtils() {
        throw new AssertionError();
    }

    public static void show(int resId) {
        if(context != null) {
            show(context.getResources().getText(resId), Toast.LENGTH_SHORT);
        }
    }

    public static void show(int resId, int duration) {
        if(context != null) {
            show(context.getResources().getText(resId), duration);
        }
    }

    public static void show(CharSequence text) {
        if(context != null){
            show(text, Toast.LENGTH_SHORT);
        }
    }

    public static void show(CharSequence text, int duration) {
        if(context != null) {
            Toast.makeText(context, text, duration).show();
        }
    }
    
}
