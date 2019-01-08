package com.benz.all.api;

import com.benz.all.app.Constants;
import com.benz.all.utils.MD5Util;
import com.benz.all.utils.Utils;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 参数与签名
 * Created by xubenliang on 2017/6/6.
 */
public class CnBetaParam {

    /**
     * 签名密钥
     */
    private final String CNBETA_SIGN_KEY = "mpuffgvbvbttn3Rc";

    private String app_key = "10000";
    private String format = "json";
    private String method;
    private String topicid = "0";
    private String v = "1.0";
    private String end_sid;
    private String sid;
    private int page;
    private long timestamp;

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTopicid() {
        return topicid;
    }

    public void setTopicid(String topicid) {
        this.topicid = topicid;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getEnd_sid() {
        return end_sid;
    }

    public void setEnd_sid(String end_sid) {
        this.end_sid = end_sid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 获取参数
     *
     * @return
     */
    public HashMap<String, String> getParam() {
        // 注意参数顺序，所以必须使用LinkedHashMap，具体顺序参考CBUrlHelper
        HashMap<String, String> param = new LinkedHashMap();
        param.put("app_key", app_key);
        switch (method) {
            case Constants.CNBETA_TYPE_NEWS_LISTS:
                if (end_sid == null) {
                    param.put("format", format);
                    param.put("method", method);
                    param.put("timestamp", timestamp + "");
                    param.put("v", v);
                } else {
                    param.put("end_sid", end_sid);
                    param.put("format", format);
                    param.put("method", method);
                    param.put("timestamp", timestamp + "");
                    param.put("topicid", topicid);
                    param.put("v", v);
                }
                break;
            case Constants.CNBETA_TYPE_NEWS_CONTENT:
                param.put("format", format);
                param.put("method", method);
                param.put("sid", sid);
                param.put("timestamp", timestamp + "");
                param.put("v", v);
                break;
            case Constants.CNBETA_TYPE_NEWS_COMMENT:
                param.put("format", format);
                param.put("method", method);
                param.put("page", page + "");
                param.put("sid", sid);
                param.put("timestamp", timestamp + "");
                param.put("v", v);
                break;
        }
        param.put("sign", MD5Util.MD5(Utils.getUrl(param) + "&" + CNBETA_SIGN_KEY));
        return param;
    }
}
