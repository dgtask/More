package com.benz.all.entity.news;

import java.io.Serializable;

/**
 * Created by xubenliang on 2017/6/2.
 */
public class NewsItem implements Serializable {

    private static final long serialVersionUID = -5375715293156897879L;

    private String sid;
    private String title;
    private String pubtime;
    private String summary;
    private String topic_logo;
    private String thumb;

    private int topic;
    private int counter;
    private int comments;
    private int ratings;
    private int score;
    private int ratings_story;
    private int score_story;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubtime() {
        return pubtime;
    }

    public void setPubtime(String pubtime) {
        this.pubtime = pubtime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTopic_logo() {
        return topic_logo;
    }

    public void setTopic_logo(String topic_logo) {
        this.topic_logo = topic_logo;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getTopic() {
        return topic;
    }

    public void setTopic(int topic) {
        this.topic = topic;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRatings_story() {
        return ratings_story;
    }

    public void setRatings_story(int ratings_story) {
        this.ratings_story = ratings_story;
    }

    public int getScore_story() {
        return score_story;
    }

    public void setScore_story(int score_story) {
        this.score_story = score_story;
    }
}
