package com.benz.all.entity.video;

import java.util.List;

/**
 * Created by xubenliang on 2017/6/28.
 */
public class Videos {

    private int nextpage;

    private String formhash;

    private List<Video> list;

    public void setNextpage(int nextpage){
        this.nextpage = nextpage;
    }
    public int getNextpage(){
        return this.nextpage;
    }
    public void setFormhash(String formhash){
        this.formhash = formhash;
    }
    public String getFormhash(){
        return this.formhash;
    }

    public List<Video> getList() {
        return list;
    }

    public void setList(List<Video> list) {
        this.list = list;
    }
}
