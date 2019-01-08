package com.benz.all.entity.news;

import com.benz.all.entity.ResponseMessage;

import java.util.List;

/**
 * Created by xubenliang on 2017/6/2.
 */
public class NewsResponse extends ResponseMessage{

    protected String status;

    private List<NewsItem> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<NewsItem> getResult() {
        return result;
    }

    public void setResult(List<NewsItem> result) {
        this.result = result;
    }
}
