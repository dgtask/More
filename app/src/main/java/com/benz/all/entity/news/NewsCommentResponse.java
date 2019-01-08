package com.benz.all.entity.news;

import com.benz.all.entity.ResponseMessage;

import java.util.List;

/**
 * Created by xubenliang on 2017/6/4.
 */
public class NewsCommentResponse extends ResponseMessage{

    private List<CommentItem> result;

    public List<CommentItem> getResult() {
        return result;
    }

    public void setResult(List<CommentItem> result) {
        this.result = result;
    }
}
