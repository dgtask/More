package com.benz.all.entity.movie.tiantan;

import java.io.Serializable;

/**
 * Created by xubenliang on 2017/6/24.
 */
public class Sources implements Serializable{

    private int vid;

    private String name;

    private int aid;

    private String playUrl;

    public void setVid(int vid){
        this.vid = vid;
    }
    public int getVid(){
        return this.vid;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setAid(int aid){
        this.aid = aid;
    }
    public int getAid(){
        return this.aid;
    }
    public void setPlayUrl(String playUrl){
        this.playUrl = playUrl;
    }
    public String getPlayUrl(){
        return this.playUrl;
    }

}
