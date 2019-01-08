package com.benz.all.entity.read;

import com.benz.all.entity.ResponseMessage;

/**
 * Created by xubenliang on 2017/6/9.
 */
public class ReadResponse extends ResponseMessage {

    private int code;

    private ArticleList body;

    private Head head;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArticleList getBody() {
        return body;
    }

    public void setBody(ArticleList body) {
        this.body = body;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }
}