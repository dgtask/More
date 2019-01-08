package com.benz.all.utils.tasks;

import android.os.AsyncTask;
import android.util.Log;


/**
 * @author xubenliang
 */
abstract class AbstractTask<T> extends AsyncTask<Void, Void, T> {

    private BackgroundWork<T> backgroundWork;
    private Exception exception;

    AbstractTask(BackgroundWork<T> backgroundWork) {
        this.backgroundWork = backgroundWork;
    }

    @Override
    protected final T doInBackground(Void... params) {
        try {
            return backgroundWork.doInBackground();
        } catch (Exception e) {
            exception = e;
            return null;
        }
    }

    @Override
    protected final void onPostExecute(T result) {
        if (!isCancelled()) {
            if (exception == null) {
                onSuccess(result);
            } else {
                Log.w(this.getClass().getSimpleName(), exception);
                onError(exception);
            }
        }
    }

    protected abstract void onSuccess(T result);
    protected abstract void onError(Exception e);
}
