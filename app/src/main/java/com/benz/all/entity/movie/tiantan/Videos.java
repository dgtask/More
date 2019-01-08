package com.benz.all.entity.movie.tiantan;

import java.io.Serializable;

/**
 * Created by xubenliang on 2017/6/19.
 */
public class Videos implements Serializable {

    private static final long serialVersionUID = 582766057327589354L;

    private int vid;

    private String playerUrl;

    private String infoUrl;

    private String videoName;

    private int index;

    private int aid;

    public void setVid(int vid) {
        this.vid = vid;
    }

    public int getVid() {
        return this.vid;
    }

    public void setPlayerUrl(String playerUrl) {
        this.playerUrl = playerUrl;
    }

    public String getPlayerUrl() {
        return this.playerUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    public String getInfoUrl() {
        return this.infoUrl;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoName() {
        return this.videoName;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getAid() {
        return this.aid;
    }


}
