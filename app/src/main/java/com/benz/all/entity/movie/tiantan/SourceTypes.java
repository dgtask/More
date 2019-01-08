package com.benz.all.entity.movie.tiantan;

import java.io.Serializable;

/**
 * Created by xubenliang on 2017/6/24.
 */
public class SourceTypes implements Serializable{

    private String name;

    private String type;

    private String desc;

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setDesc(String desc){
        this.desc = desc;
    }
    public String getDesc(){
        return this.desc;
    }
}
