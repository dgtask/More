package com.benz.all.entity.read;

/**
 * Created by xubenliang on 2017/6/25.
 */
public class Head {

    private int code;

    private String message;

    private boolean has_more;

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
    public void setHas_more(boolean has_more){
        this.has_more = has_more;
    }
    public boolean getHas_more(){
        return this.has_more;
    }
}
