package com.benz.all.entity.movie;

import com.benz.all.entity.ResponseMessage;
import com.benz.all.entity.movie.tiantan.SearchItem;

import java.util.List;

/**
 * Created by xubenliang on 2017/6/21.
 */
public class MovieSearchResponse extends ResponseMessage {

    private static final long serialVersionUID = -8975445550903461897L;

    private int code;

    private String message;

    private List<SearchItem> body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SearchItem> getBody() {
        return body;
    }

    public void setBody(List<SearchItem> body) {
        this.body = body;
    }
}
