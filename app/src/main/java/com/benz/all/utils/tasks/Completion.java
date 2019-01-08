package com.benz.all.utils.tasks;

import android.content.Context;

/**
 * @author xubenliang
 */
public interface Completion<T> {
    void onSuccess(Context context, T result);
    void onError(Context context, Exception e);
}
