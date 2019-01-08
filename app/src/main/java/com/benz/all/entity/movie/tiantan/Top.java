package com.benz.all.entity.movie.tiantan;

import java.io.Serializable;
import java.util.List;

/**
 * 更多榜单
 * Created by xubenliang on 2017/6/19.
 */
public class Top implements Serializable{

    private static final long serialVersionUID = 1527601401215085364L;

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
