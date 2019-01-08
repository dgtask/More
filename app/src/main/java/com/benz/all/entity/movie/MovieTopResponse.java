package com.benz.all.entity.movie;

import com.benz.all.entity.ResponseMessage;
import com.benz.all.entity.movie.tiantan.Top;

import java.util.List;

/**
 * 更多榜单
 * Created by xubenliang on 2017/6/19.
 */

public class MovieTopResponse extends ResponseMessage {

    private static final long serialVersionUID = -8975445550903461897L;

    private int code;

    private String message;

    private List<Top> body;

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

    public List<Top> getBody() {
        return body;
    }

    public void setBody(List<Top> body) {
        this.body = body;
    }
}
