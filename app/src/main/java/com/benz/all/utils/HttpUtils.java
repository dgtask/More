package com.benz.all.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by xubenliang on 2017/6/12.
 */

public class HttpUtils {

    /**
     * 获取重定向地址
     *
     * @param str
     * @return
     * @throws MalformedURLException
     */
    public static String getRedirectUrl(final String str){
        URL url;
        String realURL = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(str);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            conn.getResponseCode();
            realURL = conn.getURL().toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return realURL;
    }
}
