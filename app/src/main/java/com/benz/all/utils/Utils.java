package com.benz.all.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.benz.all.app.AppContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import okhttp3.ResponseBody;

/**
 * 工具类
 * Created by xubenliang on 2017/6/3.
 */
public class Utils {

    /**
     * 地址拼接
     *
     * @param params
     * @return
     */
    public static String getUrl(HashMap<String, String> params) {
        //此处URL为web给你的链接地址
        String url = "";
        // 添加url参数
        if (params != null) {
            Iterator<String> it = params.keySet().iterator();
            StringBuffer sb = null;
            while (it.hasNext()) {
                String key = it.next();
                String value = params.get(key);
                if (sb == null) {
                    sb = new StringBuffer();
                } else {
                    sb.append("&");
                }
                sb.append(key);
                sb.append("=");
                sb.append(value);
            }
            url += sb.toString();
        }
        return url;
    }

    /**
     * 分享内容
     *
     * @param mContext
     * @param content
     */
    public static void share(Context mContext, String content) {
        if (content == null) {
            return;
        }
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        shareIntent.setType("text/plain");
        mContext.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    /**
     * 字符串判空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 返回不为null的字符串
     *
     * @param str
     * @return
     */
    public static String noNull(String str) {
        return isEmpty(str) ? "" : str;
    }

    /**
     * 时间戳转换为日期
     * @param time
     * @return
     */
    public static String formatDate(String time){
        String date = "";
        SimpleDateFormat sdf = null;
        try {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.format(new Date(Long.parseLong(time) * 1000L));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 下载文件
     *
     * @param body
     * @param filePath
     * @return
     */
    public static boolean writeResponseBodyToDisk(ResponseBody body, String filePath) {
        try {
            File futureStudioIconFile = new File(filePath);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
//                long fileSize = body.contentLength();
//                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
//                    fileSizeDownloaded += read;
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    public static Drawable getDrawable(int resid) {
        return ContextCompat.getDrawable(AppContext.getInstance(), resid);
    }

    public static int getColor(int resid) {
        return ContextCompat.getColor(AppContext.getInstance(), resid);
    }

    public static String getString(int resid) {
        return AppContext.getInstance().getResources().getString(resid);
    }
}
