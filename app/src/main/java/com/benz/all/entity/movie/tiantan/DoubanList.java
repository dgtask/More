package com.benz.all.entity.movie.tiantan;

import java.io.Serializable;

/**
 * Created by xubenliang on 2017/6/17.
 */
public class DoubanList implements Serializable{

    private static final long serialVersionUID = -1033495024949595882L;

    private int followers_count;

    private String id;

    private String title;

    private String merged_cover_url;

    public void setFollowers_count(int followers_count){
        this.followers_count = followers_count;
    }
    public int getFollowers_count(){
        return this.followers_count;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setMerged_cover_url(String merged_cover_url){
        this.merged_cover_url = merged_cover_url;
    }
    public String getMerged_cover_url(){
        return this.merged_cover_url;
    }
}
