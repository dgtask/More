package com.benz.all.entity.movie.tiantan;

import java.util.List;

/**
 * 首页
 * Created by xubenliang on 2017/6/17.
 */
public class Index {

    private List<DoubanList> doubanList;

    private List<DoubanTopicList> doubanTopicList;

    private List<Subjects> suggestions;

    public void setDoubanList(List<DoubanList> doubanList) {
        this.doubanList = doubanList;
    }

    public List<DoubanList> getDoubanList() {
        return this.doubanList;
    }

    public void setDoubanTopicList(List<DoubanTopicList> doubanTopicList) {
        this.doubanTopicList = doubanTopicList;
    }

    public List<DoubanTopicList> getDoubanTopicList() {
        return this.doubanTopicList;
    }

    public void setSuggestions(List<Subjects> suggestions) {
        this.suggestions = suggestions;
    }

    public List<Subjects> getSuggestions() {
        return this.suggestions;
    }
}
