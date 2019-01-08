package com.benz.all.utils.tasks;

/**
 * @author xubenliang
 */
public interface BackgroundWork<T> {
    T doInBackground() throws Exception;
}
