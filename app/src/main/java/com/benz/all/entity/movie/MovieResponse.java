package com.benz.all.entity.movie;

import com.benz.all.entity.ResponseMessage;
import com.benz.all.entity.movie.tiantan.Index;


/**
 * Created by xubenliang on 2017/6/17.
 */
public class MovieResponse extends ResponseMessage{

    private static final long serialVersionUID = -8975445550903461897L;

    private int code;

    private String message;

    private Index body;

    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
    public void setIndex(Index index){
        this.body = index;
    }
    public Index getIndex(){
        return this.body;
    }
}
