package com.benz.all.entity.news;

import com.benz.all.entity.ResponseMessage;

/**
 * Created by xubenliang on 2017/6/4.
 */
public class NewsContentResponse extends ResponseMessage {

    private NewsContent result;

    public NewsContent getResult() {
        return result;
    }

    public void setResult(NewsContent result) {
        this.result = result;
    }
}
