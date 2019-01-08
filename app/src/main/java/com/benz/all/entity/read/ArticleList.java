package com.benz.all.entity.read;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xubenliang on 2017/6/9.
 */
public class ArticleList implements Serializable {

    private static final long serialVersionUID = -5374715243155897579L;

    private List<Article> article;

    public List<Article> getArticle() {
        return article;
    }

    public void setArticle(List<Article> article) {
        this.article = article;
    }
}
