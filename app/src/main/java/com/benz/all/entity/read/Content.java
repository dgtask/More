package com.benz.all.entity.read;

import java.io.Serializable;

/**
 * Created by xubenliang on 2017/6/25.
 */
public class Content implements Serializable {

    private static final long serialVersionUID = -5375715243156897579L;

    private Article article;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
