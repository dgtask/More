package com.benz.all.entity.movie.tiantan;

import com.chad.library.adapter.base.entity.SectionEntity;

import java.io.Serializable;

/**
 * Created by xubenliang on 2017/6/17.
 */
public class Subjects extends SectionEntity implements Serializable {

    private static final long serialVersionUID = 868602461537764440L;

    /**
     * 上级类目名称
     */
    private String parentName;
    /**
     * 上级类目编号
     */
    private String parentId;

    private double score;

    private String doubanId;

    private String topicId;

    private String img;

    private boolean album;

    private String name;

    private boolean show;

    private int index;

    private String pid;

    private Long movieId;

    private int id;

    private boolean v3Show;

    private String desc;

    public Subjects(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getScore() {
        return this.score;
    }

    public void setDoubanId(String doubanId) {
        this.doubanId = doubanId;
    }

    public String getDoubanId() {
        return this.doubanId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopicId() {
        return this.topicId;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return this.img;
    }

    public void setAlbum(boolean album) {
        this.album = album;
    }

    public boolean getAlbum() {
        return this.album;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public boolean getShow() {
        return this.show;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPid() {
        return this.pid;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getMovieId() {
        return this.movieId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setV3Show(boolean v3Show) {
        this.v3Show = v3Show;
    }

    public boolean getV3Show() {
        return this.v3Show;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
