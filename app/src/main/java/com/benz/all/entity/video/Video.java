package com.benz.all.entity.video;

import java.io.Serializable;

/**
 * Created by xubenliang on 2017/6/28.
 */
public class Video implements Serializable{
    private static final long serialVersionUID = -4113994479046880592L;

    private String tid;

    private String authorid;

    private String author;

    private String dateline;

    private String subject;

    private String replies;

    private String liketimes;

    private String special;

    private int isliked;

    private String movietid;

    private String image;

    private int width;

    private int height;

    private String videourl;

    private String intro;

    public void setTid(String tid){
        this.tid = tid;
    }
    public String getTid(){
        return this.tid;
    }
    public void setAuthorid(String authorid){
        this.authorid = authorid;
    }
    public String getAuthorid(){
        return this.authorid;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public String getAuthor(){
        return this.author;
    }
    public void setDateline(String dateline){
        this.dateline = dateline;
    }
    public String getDateline(){
        return this.dateline;
    }
    public void setSubject(String subject){
        this.subject = subject;
    }
    public String getSubject(){
        return this.subject;
    }
    public void setReplies(String replies){
        this.replies = replies;
    }
    public String getReplies(){
        return this.replies;
    }
    public void setLiketimes(String liketimes){
        this.liketimes = liketimes;
    }
    public String getLiketimes(){
        return this.liketimes;
    }
    public void setSpecial(String special){
        this.special = special;
    }
    public String getSpecial(){
        return this.special;
    }
    public void setIsliked(int isliked){
        this.isliked = isliked;
    }
    public int getIsliked(){
        return this.isliked;
    }
    public void setMovietid(String movietid){
        this.movietid = movietid;
    }
    public String getMovietid(){
        return this.movietid;
    }
    public void setImage(String image){
        this.image = image;
    }
    public String getImage(){
        return this.image;
    }
    public void setWidth(int width){
        this.width = width;
    }
    public int getWidth(){
        return this.width;
    }
    public void setHeight(int height){
        this.height = height;
    }
    public int getHeight(){
        return this.height;
    }
    public void setVideourl(String videourl){
        this.videourl = videourl;
    }
    public String getVideourl(){
        return this.videourl;
    }
    public void setIntro(String intro){
        this.intro = intro;
    }
    public String getIntro(){
        return this.intro;
    }
}
