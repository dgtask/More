package com.benz.all.entity.read;

import java.io.Serializable;

/**
 * Created by xubenliang on 2017/6/9.
 */
public class Article implements Serializable {

    private static final long serialVersionUID = -5375715243156897579L;

    private Long id;

    private String title;

    private String headpic;

    private String raw_headpic;

    private String author;

    private String brief;

    private String read_num;

    private String wechat_url;

    private String url;

    private String create_time;

    private String update_time;

    // 内容
    private String content;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public String getHeadpic() {
        return this.headpic;
    }

    public void setRaw_headpic(String raw_headpic) {
        this.raw_headpic = raw_headpic;
    }

    public String getRaw_headpic() {
        return this.raw_headpic;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getBrief() {
        return this.brief;
    }

    public void setRead_num(String read_num) {
        this.read_num = read_num;
    }

    public String getRead_num() {
        return this.read_num;
    }

    public void setWechat_url(String wechat_url) {
        this.wechat_url = wechat_url;
    }

    public String getWechat_url() {
        return this.wechat_url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCreate_time() {
        return this.create_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getUpdate_time() {
        return this.update_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
