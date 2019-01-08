package com.benz.all.entity.movie.tiantan;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xubenliang on 2017/6/19.
 */
public class Detail implements Serializable {

    private static final long serialVersionUID = -4444517884788481678L;

    private String area;

    private String img;

    private String doubanId;

    private String updateDate;

    private List<Sources> sources ;

    private List<SourceTypes> sourceTypes ;

    private String release;

    private String typeName;

    private int movieId;

    private String type;

    private String actors;

    private String score;

    private String status;

    private String desc;

    public void setArea(String area){
        this.area = area;
    }
    public String getArea(){
        return this.area;
    }
    public void setImg(String img){
        this.img = img;
    }
    public String getImg(){
        return this.img;
    }
    public void setDoubanId(String doubanId){
        this.doubanId = doubanId;
    }
    public String getDoubanId(){
        return this.doubanId;
    }
    public void setUpdateDate(String updateDate){
        this.updateDate = updateDate;
    }
    public String getUpdateDate(){
        return this.updateDate;
    }
    public void setSources(List<Sources> sources){
        this.sources = sources;
    }
    public List<Sources> getSources(){
        return this.sources;
    }
    public void setSourceTypes(List<SourceTypes> sourceTypes){
        this.sourceTypes = sourceTypes;
    }
    public List<SourceTypes> getSourceTypes(){
        return this.sourceTypes;
    }
    public void setRelease(String release){
        this.release = release;
    }
    public String getRelease(){
        return this.release;
    }
    public void setTypeName(String typeName){
        this.typeName = typeName;
    }
    public String getTypeName(){
        return this.typeName;
    }
    public void setMovieId(int movieId){
        this.movieId = movieId;
    }
    public int getMovieId(){
        return this.movieId;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setActors(String actors){
        this.actors = actors;
    }
    public String getActors(){
        return this.actors;
    }
    public void setScore(String score){
        this.score = score;
    }
    public String getScore(){
        return this.score;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setDesc(String desc){
        this.desc = desc;
    }
    public String getDesc(){
        return this.desc;
    }
}
