package com.benz.all.entity.movie.tiantan;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xubenliang on 2017/6/17.
 */
public class DoubanTopicList implements Serializable {

    private static final long serialVersionUID = 9155773180853057835L;

    private List<Subjects> subjects;

    private String name;

    private String id;

    public void setSubjects(List<Subjects> subjects) {
        this.subjects = subjects;
    }

    public List<Subjects> getSubjects() {
        return this.subjects;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

}