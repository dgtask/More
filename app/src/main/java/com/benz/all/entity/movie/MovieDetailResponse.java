package com.benz.all.entity.movie;

import com.benz.all.entity.ResponseMessage;
import com.benz.all.entity.movie.tiantan.Detail;

/**
 * Created by xubenliang on 2017/6/19.
 */
public class MovieDetailResponse extends ResponseMessage {

    private int code;

    private String message;

    private Detail body;

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

    public Detail getBody() {
        return body;
    }

    public void setBody(Detail body) {
        this.body = body;
    }
}
