package com.benz.all.entity.video;

import com.benz.all.entity.ResponseMessage;

/**
 * Created by xubenliang on 2017/6/28.
 */
public class VideoResponse extends ResponseMessage{
    private Videos data;

    public Videos getData() {
        return data;
    }

    public void setData(Videos data) {
        this.data = data;
    }
}
