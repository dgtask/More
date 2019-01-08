package com.benz.all.entity.movie;

import com.benz.all.entity.ResponseMessage;
import com.benz.all.entity.movie.tiantan.Subjects;

import java.util.List;

/**
 * Created by xubenliang on 2017/6/19.
 */
public class MovieTopicItemResponse extends ResponseMessage {
    private static final long serialVersionUID = -8975445550903461897L;

    private int code;

    private String message;

    private List<Subjects> body;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public List<Subjects> getBody() {
        return body;
    }

    public void setBody(List<Subjects> body) {
        this.body = body;
    }
}
