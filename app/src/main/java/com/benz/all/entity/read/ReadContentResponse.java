package com.benz.all.entity.read;

import com.benz.all.entity.ResponseMessage;

/**
 * Created by xubenliang on 2017/6/25.
 */
public class ReadContentResponse extends ResponseMessage{

    private Content body;

    private Head head;

    public Content getBody() {
        return body;
    }

    public void setBody(Content body) {
        this.body = body;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }
}
